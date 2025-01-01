package com.example.YuRun.Register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService service;

    @GetMapping
    public String register(RegisterUser user) {
        return "/Register/index";
    }

    @PostMapping
    public String registerUser(@Valid RegisterUser user, BindingResult bindingResult, Model model) throws Exception {
        // Validasi custom: periksa nama dan email
        String validationError = service.getValidationError(user);
        if (validationError != null) {
            if(validationError.equals("name")){
                bindingResult.rejectValue(validationError, "ValidationError", "The username is already taken.");
            }
            else{
                bindingResult.rejectValue(validationError, "ValidationError", "The email address is already registered.");
            }
        }

        // Validasi default
        if (bindingResult.hasErrors()) {
            return "/Register/index";
        }

        // Validasi password cocok
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            bindingResult.rejectValue(
                "confirmPassword", // Harus sama persis dengan nama field
                "PasswordMismatch",
                "Passwords do not match"
            );
            return "/Register/index";
        }

        // Proses registrasi
        if (service.register(user)) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Something is wrong. Please try again.");
            return "/Register/index";
        }
    }

}