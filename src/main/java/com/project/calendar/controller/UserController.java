package com.project.calendar.controller;

import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Payment;
import com.project.calendar.domain.Status;
import com.project.calendar.domain.Ticket;
import com.project.calendar.domain.User;
import com.project.calendar.exception.DownloadTicketsException;
import com.project.calendar.service.ExpositionService;
import com.project.calendar.service.PaymentService;
import com.project.calendar.service.TicketService;
import com.project.calendar.service.helper.PDFCreator;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.now;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements PaginationUtility {

    private final ExpositionService expositionService;
    private final TicketService ticketService;
    private final PaymentService paymentService;
    private final PDFCreator pdfCreator;

    @GetMapping("/user")
    public String main(HttpSession session) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        session.setAttribute("user", user);

        return "user-page";
    }

    @GetMapping("user/expositions")
    public ModelAndView viewExpositions(@RequestParam("page") String stringPage,
                                        @RequestParam("rowCount") String stringRowCount) {
        final ModelAndView modelAndView = new ModelAndView("user-view-expositions");

        validatePagination(stringPage, stringRowCount);

        final int page = Integer.parseInt(stringPage);
        final int rowCount = Integer.parseInt(stringRowCount);
        final List<Exposition> expositions = expositionService.showAllNotFinished(page - 1, rowCount);

        paginate(page, rowCount, expositionService.showNotFinishedEntriesAmount(), modelAndView, "/user/expositions");

        modelAndView.addObject("expositions", expositions);

        return modelAndView;
    }

    @PostMapping("user/choose-date")
    public ModelAndView chooseDate(HttpSession session,
                                   @RequestParam("expositionId") Long expositionId) {
        final ModelAndView modelAndView = new ModelAndView("user-choose-date");

        final Exposition exposition = expositionService.showById(expositionId);

        session.setAttribute("exposition", exposition);
        modelAndView.addObject("startDate", now().compareTo(exposition.getStartDate()) >= 0 ?
                now() :
                exposition.getStartDate());

        return modelAndView;
    }

    @PostMapping("user/checkout")
    public ModelAndView checkoutPage(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                     @RequestParam("tickets") Integer ticketAmount,
                                     HttpSession session) {
        final ModelAndView modelAndView = new ModelAndView("user-checkout-page");

        final Exposition exposition = (Exposition) session.getAttribute("exposition");
        session.setAttribute("date", date);
        session.setAttribute("ticketAmount", ticketAmount);

        modelAndView.addObject("ticketAmount", ticketAmount);
        modelAndView.addObject("ticketPrice", exposition.getTicketPrice().doubleValue());

        return modelAndView;
    }

    @PostMapping("user/payment-process")
    public ModelAndView processPayment(HttpSession session) {
        final ModelAndView modelAndView = new ModelAndView("redirect:/user/tickets");

        final User user = (User) session.getAttribute("user");
        final Exposition exposition = (Exposition) session.getAttribute("exposition");
        final Integer ticketAmount = (Integer) session.getAttribute("ticketAmount");
        final LocalDate date = (LocalDate) session.getAttribute("date");

        final Payment payment = Payment.builder()
                .paymentTime(LocalDateTime.now())
                .ticketsAmount(ticketAmount)
                .price(exposition.getTicketPrice().multiply(BigDecimal.valueOf(ticketAmount)))
                .status(Status.PASSED)
                .user(user)
                .exposition(exposition)
                .build();
        paymentService.add(payment);

        final Payment lastPayment = paymentService.showLastByUserId(user.getId());

        for (int i = 0; i < lastPayment.getTicketsAmount(); i++) {
            final Ticket ticket = Ticket.builder()
                    .validDate(date)
                    .user(user)
                    .payment(lastPayment)
                    .exposition(exposition)
                    .build();
            ticketService.add(ticket);
        }

        session.removeAttribute("exposition");
        session.removeAttribute("date");
        session.removeAttribute("ticketAmount");

        return modelAndView;
    }

    @GetMapping("user/tickets")
    public ModelAndView showTickets(HttpSession session) {
        final ModelAndView modelAndView = new ModelAndView("user-show-tickets");

        final User user = (User) session.getAttribute("user");

        final List<Payment> payments = paymentService.showAllByUserId(user.getId());
        final List<Ticket> tickets = ticketService.showAllByUserId(user.getId());
        final List<Integer> ticketsAmount = new ArrayList<>();

        for (Payment payment : payments) {
            if (payment.getStatus() != Status.FAILED) {
                ticketsAmount.add(payment.getTicketsAmount());
            }
        }

        modelAndView.addObject("tickets", tickets);
        modelAndView.addObject("ticketsAmount", ticketsAmount);

        return modelAndView;
    }

    @GetMapping("user/download")
    public void downloadFileFromLocal(HttpServletResponse response, HttpSession session,
                                      @RequestParam("paymentId") Long paymentId) {
        final Long userId = ((User) session.getAttribute("user")).getId();
        final List<Ticket> tickets = ticketService.showAllByPaymentIdAndUserId(paymentId, userId);
        final String filePath = pdfCreator.createPDF(paymentId, tickets);
        File file = new File("tickets/" + filePath);

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\""
                + filePath + "\"");

        try {
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(file);

            IOUtils.copy(in, out);

            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            throw new DownloadTicketsException("File not found", e);
        } catch (IOException e) {
            throw new DownloadTicketsException("Error while downloading tickets", e);
        }

        file.delete();
    }
}
