package com.example.YuRun.Register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterRepository repo;
    @Autowired
    private RegisterService service;

    @GetMapping
    public String register() {
        return "/Register/index";
    }

    // @PostMapping
    // public String registerUser(
    //     @RequestParam("name") String name,
    //     @RequestParam("email") String email,
    //     @RequestParam("password") String password,
    //     @RequestParam("confirmpassword") String confirmpassword,
    //     Model model) {
        
    //     // Validasi password
    //     if (!password.equals(confirmpassword)) {
    //         model.addAttribute("error", "Password and Confirm Password do not match.");
    //         return "/Register/index";
    //     }

    //     // Ubah status menjadi true
    //     RegisterUser user = new RegisterUser(name, email, password, confirmpassword, false, true);
        
    //     try {
    //         repo.tambahUser(user);
    //         return "redirect:/login";
    //     } catch (Exception e) {
    //         model.addAttribute("error", "Registration failed: " + e.getMessage());
    //         return "/Register/index";
    //     }
    // }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute("RegisterUser") RegisterUser user, BindingResult bindingResult, Model model){
        boolean isvalid = service.register(user);

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "/Register/index";
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            bindingResult.rejectValue(
                "confirmpassword",  // field
                "PasswordMismatch", // error code 
                "Passwords do not match" // error default message
            );
            return "/Register/index";
        }
        
        if(!isvalid){
            bindingResult.rejectValue(
                "name", 
                "DuplicateName", 
                "Account already exists"
            );
        }

        try {
            repo.tambahUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "/Register/index";
            }        
        
    }
}