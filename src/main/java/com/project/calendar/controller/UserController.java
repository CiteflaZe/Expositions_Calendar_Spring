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
import com.project.calendar.service.UserService;
import com.project.calendar.service.util.PDFCreator;
import com.project.calendar.service.util.ValidatePagination;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final ExpositionService expositionService;
    private final TicketService ticketService;
    private final PaymentService paymentService;
    private final PDFCreator pdfCreator;
    private final ValidatePagination validatePagination;

    @GetMapping("/user")
    public String main() {
        return "user-page";
    }

    @GetMapping("user/expositions")
    public ModelAndView viewExpositions(@RequestParam("page") String stringPage,
                                        @RequestParam("rowCount") String stringRowCount) {
        final ModelAndView mav = new ModelAndView("user-view-expositions");

        final Integer[] paginationParameters = validatePagination.validate(stringPage, stringRowCount, expositionService.showEntriesAmount(), ValidatePagination.DEFAULT_EXPOSITION_ROW_COUNT);
        final int page = paginationParameters[0];
        final int rowCount = paginationParameters[1];
        final int numberOfPages = paginationParameters[2];

        final List<Exposition> expositions = expositionService.showAll(page - 1, rowCount);

        mav.addObject("expositions", expositions);
        mav.addObject("command", "/user/expositions");
        mav.addObject("numberOfPages", numberOfPages);
        mav.addObject("page", page);
        mav.addObject("rowCount", rowCount);

        return mav;
    }

    @PostMapping("user/choose-date")
    public ModelAndView chooseDate(@RequestParam("exposition") Exposition exposition, HttpSession session) {
        final ModelAndView mav = new ModelAndView("user-choose-date");

        session.setAttribute("exposition", exposition);

        return mav;
    }

    @PostMapping("user/checkout")
    public ModelAndView checkout(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                 @RequestParam("tickets") Integer ticketAmount,
                                 HttpSession session) {
        final ModelAndView mav = new ModelAndView("user-checkout-page");

        final Exposition exposition = (Exposition) session.getAttribute("exposition");
        session.setAttribute("date", date);
        session.setAttribute("ticketAmount", ticketAmount);

        mav.addObject("ticketAmount", ticketAmount);
        mav.addObject("ticketPrice", exposition.getTicketPrice().doubleValue());

        return mav;
    }

    @PostMapping("user/payment-process")
    public ModelAndView processPayment(HttpSession session) {
        final ModelAndView mav = new ModelAndView("redirect:/user/tickets");

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

        return mav;
    }

    @GetMapping("user/tickets")
    public ModelAndView showTickets(HttpSession session) {
        final ModelAndView mav = new ModelAndView("user-show-tickets");

        final User user = (User) session.getAttribute("user");

        final List<Payment> payments = paymentService.showAllByUserId(user.getId());
        final List<Ticket> tickets = new ArrayList<>();
        final List<Integer> ticketsAmount = new ArrayList<>();

        for (Payment payment : payments) {
            if (payment.getStatus() != Status.FAILED) {
                final Ticket ticket = ticketService.showOneByPaymentId(payment.getId());
                tickets.add(ticket);
                ticketsAmount.add(payment.getTicketsAmount());
            }
        }

        mav.addObject("tickets", tickets);
        mav.addObject("ticketsAmount", ticketsAmount);

        return mav;
    }

    @GetMapping("user/download")
    public void downloadFileFromLocal(HttpServletResponse response, HttpSession session, @RequestParam("paymentId") Long paymentId) {
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
