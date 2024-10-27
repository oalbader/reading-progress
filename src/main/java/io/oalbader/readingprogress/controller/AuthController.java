package io.oalbader.readingprogress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserController userController;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        UserController.LoginRequest loginRequest = new UserController.LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        ResponseEntity<?> response = userController.loginUser(loginRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/reading-sessions";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
        UserController.RegistrationRequest registrationRequest = new UserController.RegistrationRequest();
        registrationRequest.setUsername(username);
        registrationRequest.setEmail(email);
        registrationRequest.setPassword(password);

        ResponseEntity<?> response = userController.registerUser(registrationRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Registration failed. " + response.getBody());
            return "auth/register";
        }
    }
}
