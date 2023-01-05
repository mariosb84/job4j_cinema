package ru.job4j.cinema.controller;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.utilites.Sessions;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class TicketController {

    private final TicketService ticketService;
    private final SessionService sessionService;

    public TicketController(TicketService ticketService, SessionService sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }


    @PostMapping("/createTicket")
    public String createTicket(Model model, @ModelAttribute Ticket ticket, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute("ticket", ticket);
        model.addAttribute("sessionChoice",
                sessionService.findById(ticket.getSessionId()));
        Sessions.userSession(model, session);
        Sessions.ticketSession(model, session);
        Sessions.sessionSession(model, session);
        return "selectRowTicket";
    }

    @PostMapping("/setRowTicket")
    public String setRowTicket(Model model, @ModelAttribute Ticket ticket, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute("ticket", ticket);
        model.addAttribute("sessionChoiceSecond",
                sessionService.findById(ticket.getSessionId()));
        Sessions.userSession(model, session);
        Sessions.ticketSession(model, session);
        Sessions.sessionSession(model, session);
        return "selectSelTicket";
    }

    @PostMapping("/setSelTicket")
    public String setSelTicket(Model model, @ModelAttribute Ticket ticket, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute("ticket", ticket);
        model.addAttribute("sessionChoiceThird",
                sessionService.findById(ticket.getSessionId()));
        Sessions.userSession(model, session);
        Sessions.ticketSession(model, session);
        Sessions.sessionSession(model, session);
        return "buyingTicket";
    }

    @PostMapping("/buyingTicket")
    public String buyingTicket(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Optional<Ticket> ticket = ticketService.add((Ticket) session.getAttribute("ticket"));
        if (ticket.isEmpty()) {
            model.addAttribute("message", "Ошибка при бронировании билета");
        } else {
            model.addAttribute("message", "Благодарим вас за покупку, билет забронирован!");
        model.addAttribute("ticket", ticket.get());
        model.addAttribute("sessionChoiceFour", sessionService.
                findById(ticket.get().getSessionId()));
        }
        Sessions.userSession(model, session);
        Sessions.ticketSession(model, session);
        Sessions.sessionSession(model, session);
        return "tickets";
    }

}