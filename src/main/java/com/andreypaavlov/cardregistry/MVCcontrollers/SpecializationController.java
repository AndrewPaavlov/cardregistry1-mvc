package com.andreypaavlov.cardregistry.MVCcontrollers;

import com.andreypaavlov.cardregistry.entities.Specialization;
import com.andreypaavlov.cardregistry.services.SpecializationService;
import com.andreypaavlov.cardregistry.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/spec")
@AllArgsConstructor
public class SpecializationController {
    private final SpecializationService specializationService;
    private final UserService userService;

    @GetMapping()
    public String showSpecForm(Model model) {
        model.addAttribute("newSpec", new Specialization());
        model.addAttribute("specializationMap", specializationService.getAllSpec());
        return "specializations";
    }

    @PostMapping("/add")
    public String addSpec(@ModelAttribute(name = "newSpec") Specialization spec) {
        specializationService.addSpec(spec);
        return "redirect:/spec";
    }

    @PostMapping("/setSpec")
    public String setSpecToUser(@RequestParam(name = "spec") String spec, @RequestParam(name = "e_mail") String email, Model model) {
        try {
            userService.setSpecializationToUser(spec, email);
        } catch (EmptyResultDataAccessException exception) {
            // Добавляем параметр "error" в URL редиректа для указания на наличие ошибки
            return "redirect:/spec?error=true";
        }

        return "redirect:/spec";
    }
}