package api.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @GetMapping("/auth/login")
    public String login(@RequestParam(required = false) String redirectUrl, Model model) {
        model.addAttribute("redirectUrl", redirectUrl);
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam String redirectUrl) {
        // Simulate authentication (replace with real logic)
        if ("user".equals(username) && "password".equals(password)) {
            return "redirect:" + redirectUrl;  // Redirect after successful login
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(@RequestParam(required = false) String redirectUrl, Model model) {
        model.addAttribute("redirectUrl", redirectUrl);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String redirectUrl) {
        // Simulate registration (replace with real logic)
        return "redirect:" + redirectUrl;  // Redirect after successful registration
    }
}
