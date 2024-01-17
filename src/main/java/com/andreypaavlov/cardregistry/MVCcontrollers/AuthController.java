package com.andreypaavlov.cardregistry.MVCcontrollers;

import com.andreypaavlov.cardregistry.entities.Role;
import com.andreypaavlov.cardregistry.entities.Specialization;
import com.andreypaavlov.cardregistry.entities.User;
import com.andreypaavlov.cardregistry.repositories.UserRepository;
import com.andreypaavlov.cardregistry.services.SpecializationService;
import com.andreypaavlov.cardregistry.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final SpecializationService specializationService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("newUser", new User());
        return "registration";
    }

    @GetMapping("/registration/doctor")
    public String registrationDoctorForm(Model model) {
        model.addAttribute("spec", specializationService.getAllSpecNames());
        model.addAttribute("doctor", new User());
        return "registration-doctor";
    }

    @GetMapping("/logout")
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/login?logout";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute(name = "newUser") User user) {
        try {
            userService.saveNewUser(user);
            return "redirect:/login?success=true";
        } catch (DataIntegrityViolationException e) {
            return "redirect:/registration?success=false";
        }
    }

    @PostMapping("/registration/doctor")
    public String registrationDoctor(@ModelAttribute(name = "newUser") User doctor, @RequestParam(name = "spec") String specializationName) {
        Specialization specialization = specializationService.getSpecializationByName(specializationName);
        try {
            doctor.setSpecialization(specialization);
            doctor.setRole(Role.DOCTOR);
            userService.saveNewUser(doctor);
        } catch (DataIntegrityViolationException e) {
            return "redirect:/registration/doctor?success=false";
        }
        return "redirect:/registration/doctor?success=true";
    }
}

