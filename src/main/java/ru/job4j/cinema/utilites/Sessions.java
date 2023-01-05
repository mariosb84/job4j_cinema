package ru.job4j.cinema.utilites;

import org.springframework.ui.Model;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

public final class Sessions {

    private Sessions() {

    }

    public static void userSession(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername("Гость");
        }
        model.addAttribute("user", user);
    }

    public static void ticketSession(Model model, HttpSession session) {
        Ticket ticket = (Ticket) session.getAttribute("ticket");
        if (ticket == null) {
            ticket = new Ticket();
        }
        model.addAttribute("ticket", ticket);
    }

    public static void sessionSession(Model model, HttpSession session) {
        Session methodSessions = (Session) session.getAttribute("session");
        if (methodSessions == null) {
            methodSessions = new Session();
        }
        model.addAttribute("session", methodSessions);
    }

}
