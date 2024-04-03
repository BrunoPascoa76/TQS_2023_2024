package ua.tqs.bp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class WebController {
    @GetMapping("/")
    public String home(HttpSession session) {
        int userId = (Integer) session.getAttribute("userId");
        return "index";
    }
}
