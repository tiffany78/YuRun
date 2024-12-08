package com.example.YuRun.Admin.Homepage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeController {
    @Autowired
    private HomeRepository repo;
    
    @GetMapping
    public String index(Model model){
        List<Race> list = this.repo.findRace();
        System.out.println(list);
        model.addAttribute("race", list);
        return "Admin/HomePage/index";
    }
}
