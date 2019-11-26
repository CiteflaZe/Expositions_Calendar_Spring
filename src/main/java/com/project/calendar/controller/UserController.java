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

    @GetMapping("/user")
    public String main() {
        return "user-page";
    }

    @GetMapping("/view-expositions")
    public ModelAndView viewExpositions(@RequestParam("page") Integer page,
                                        @RequestParam("rowCount") Integer rowCount) {
        final ModelAndView mav = new ModelAndView();

        final List<Exposition> expositions = expositionService.showAll(page - 1, rowCount);
        int numberOfPage = (int) Math.ceil(userService.showEntriesAmount() * 1.0 / rowCount);

        mav.addObject("expositions", expositions);
        mav.addObject("command", "view-expositions");
        mav.addObject("numberOfPages", numberOfPage);
        mav.addObject("page", page);
        mav.addObject("rowCount", rowCount);
        mav.setViewName("user-view-expositions");

        return mav;
    }

    @PostMapping("/choose-date")
    public ModelAndView chooseDate(@RequestParam("exposition") Exposition exposition, HttpSession session) {
        final ModelAndView mav = new ModelAndView();

        session.setAttribute("exposition", exposition);
        mav.setViewName("user-choose-date");

        return mav;
    }

    @PostMapping("/checkout")
    public ModelAndView checkout(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                 @RequestParam("tickets") Integer ticketAmount,
                                 HttpSession session) {
        final ModelAndView mav = new ModelAndView();

        final Exposition exposition = (Exposition) session.getAttribute("exposition");
        session.setAttribute("date", date);
        session.setAttribute("ticketAmount", ticketAmount);

        mav.addObject("ticketAmount", ticketAmount);
        mav.addObject("ticketPrice", exposition.getTicketPrice().doubleValue());
        mav.setViewName("user-checkout-page");

        return mav;
    }

    @PostMapping("/payment-process")
    public ModelAndView processPayment(HttpSession session) {
        final ModelAndView mav = new ModelAndView();

        final User user = (User) session.getAttribute("user");
        final Exposition exposition = (Exposition) session.getAttribute("exposition");
        final Integer ticketAmount = (Integer) session.getAttribute("ticketAmount");
        final LocalDate date = (LocalDate) session.getAttribute("date");

        final Payment payment = Payment.builder()
                .paymentTime(LocalDateTime.now())
                .ticketAmount(ticketAmount)
                .price(exposition.getTicketPrice().multiply(BigDecimal.valueOf(ticketAmount)))
                .status(Status.PASSED)
                .user(user)
                .exposition(exposition)
                .build();
        paymentService.add(payment);

        final Payment lastPayment = paymentService.showLastByUserId(user.getId());

        for (int i = 0; i < lastPayment.getTicketAmount(); i++) {
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

        mav.setViewName("redirect:/tickets");

        return mav;
    }

    @GetMapping("/tickets")
    public ModelAndView showTickets(HttpSession session) {
        final ModelAndView mav = new ModelAndView();

        final User user = (User) session.getAttribute("user");

        final List<Payment> payments = paymentService.showAllByUserId(user.getId());
        final List<Ticket> tickets = new ArrayList<>();
        final List<Integer> ticketsAmount = new ArrayList<>();

        for (Payment payment : payments) {
            if (payment.getStatus() != Status.FAILED) {
                final Ticket ticket = ticketService.showOneByPaymentId(payment.getId());
                tickets.add(ticket);
                ticketsAmount.add(payment.getTicketAmount());
            }
        }

        mav.addObject("tickets", tickets);
        mav.addObject("ticketsAmount", ticketsAmount);
        mav.setViewName("user-show-tickets");

        return mav;
    }

    @GetMapping("/download")
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

//        Path path = Paths.get("tickets/" + filePath);
//        Resource resource = null;
//        try {
//            resource = new UrlResource(path.toUri());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
    }
}
