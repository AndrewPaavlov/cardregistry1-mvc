package com.andreypaavlov.cardregistry.MVCcontrollers;

import com.andreypaavlov.cardregistry.services.CardMovementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class CardMovementController {
    private final CardMovementService cardMovementService;

    @GetMapping("/trajectory")
    public String showInfo(){
        return "trajectory";
    }
    @GetMapping("/trajectory/{num}")
    public String showInfoMovement(@PathVariable String num, Model model){
        model.addAttribute("history",cardMovementService.getCardMoments(num));
        return "trajectory";
    }

}
