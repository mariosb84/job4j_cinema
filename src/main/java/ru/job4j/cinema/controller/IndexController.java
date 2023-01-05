package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.utilites.Sessions;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class IndexController {

    private final SessionService sessionService;

    public IndexController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/cinemaList")
    public String index(Model model, HttpSession session) {
        model.addAttribute("sessionOne", sessionService.findById(1));
        model.addAttribute("sessionTwo", sessionService.findById(2));
        model.addAttribute("sessionThree", sessionService.findById(3));
        Sessions.userSession(model, session);
        Sessions.sessionSession(model, session);
        return "cinemaList";
    }

}
