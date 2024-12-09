package com.example.YuRun.Admin.UserInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserInformationController {
    @Autowired
    private final UserInformationRepository userInformationRepository;

    public UserInformationController(UserInformationRepository userInformationRepository) {
        this.userInformationRepository = userInformationRepository;
    }

    // @GetMapping
    // public String getSetoranMembers(@RequestParam(value = "filter", required = false) String filter, Model model) {
    //     model.addAttribute("filter", filter);
    //     List<User> setoranList = userInformationRepository.findAll(filter);

    //     model.addAttribute("setoranList", setoranList);
    //     return "/Admin/UserInformationAdmin/index"; 
    // }

    @GetMapping("/info1")
    public String getSetoranMembers() {
        return "/Admin/UserInformation/index2"; 
    }

    @GetMapping("/info2")
    public String index() {
        return "/Admin/UserInformation/index"; 
    }
}
