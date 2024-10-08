package com.colorpalettegenerator.web;

import com.colorpalettegenerator.dto.UserRegistrationDto;
import com.colorpalettegenerator.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto()
    {
        return new UserRegistrationDto();
    }
    @GetMapping
    public String showRegistrationForm(Model model)
    {
        model.addAttribute("user",new UserRegistrationDto());
        return "registration";
    }
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto)
    {
        userService.save(registrationDto);
        return "redirect:/registration?success";

    }
}
