package com.andreypaavlov.cardregistry.MVCcontrollers;

import com.andreypaavlov.cardregistry.entities.BloodType;
import com.andreypaavlov.cardregistry.entities.Card;
import com.andreypaavlov.cardregistry.entities.User;
import com.andreypaavlov.cardregistry.services.CardService;
import com.andreypaavlov.cardregistry.services.RoomService;
import com.andreypaavlov.cardregistry.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final UserService userService;
    private final RoomService roomService;
    private static final Object [] bloodTypes = Arrays.stream(BloodType.values()).toArray();
    @GetMapping
    public String showCards(HttpSession session,Authentication authentication){
        session.setAttribute("doctor",userService.createUserForSession(authentication));
        return "cards-information";
    }
    @GetMapping("/card/{num}")
    public String showCardInfo(@PathVariable String num, Model model){
        Card card = cardService.getCardByNumber(num).get();
        model.addAttribute("usedCard",card);
        model.addAttribute("blood_types",bloodTypes);
        model.addAttribute("rooms",roomService.getAllRooms());
        return "card-info";
    }
    @GetMapping("/{cardNumber}")
    public String showCards(@PathVariable String cardNumber,Model model){
        Optional<Card> cardOptional = (cardService.getCardByNumber(cardNumber));
        if (cardOptional.isPresent()){
            model.addAttribute("cards", cardOptional.stream().collect(Collectors.toList()));
            model.addAttribute("hasResult",true);
        }else {
            model.addAttribute("hasResult",false);
        }
        return "cards-information";
    }
   @RequestMapping(value = "/{l}/{f}/{p}",method = RequestMethod.GET)
    public String getCardByFullName(@PathVariable("l") String lastName, @PathVariable("f") String firstName, @PathVariable("p") String patronymic,Model model) {
       System.out.println(lastName+firstName+patronymic);
        List<Card> cardList = cardService.getCardsByFullName(lastName,firstName,patronymic);

            model.addAttribute("hasResult",!cardList.isEmpty());
            model.addAttribute("cards",cardList);
        return  "cards-information";
    }

    @GetMapping("/add")
    public String showCardCreationForm(Model model){
        model.addAttribute("newCard",new Card());
        model.addAttribute("blood", bloodTypes);
        return "card-creation";
    }
    @PostMapping("/add")
    public String addNewCard(@ModelAttribute(name = "newCard") Card card, @RequestParam(name = "date") String date,@RequestParam(name = "blood_types")String bloodType){
        System.out.println(bloodType);
        card.setDate(LocalDate.parse(date));
        card.setBloodType(BloodType.findByAnnotation(bloodType));
        cardService.saveCard(card);
        return "redirect:/cards/add";
    }
    @PostMapping("/update")
    public String updateCard(@ModelAttribute("usedCard")Card updated,
                             @RequestParam(name = "updnum")String num,
                             @RequestParam(name = "blood_types",required = false)String type,
                             @RequestParam(name="description",required = false) String description,
                             @RequestParam(name = "d_ate",required = false)String date,
                             @RequestParam(name = "birthdate")String birthDate,
                             @RequestParam(name = "next_room")String nextRoom,
                             HttpSession session) {
        cardService.updateCard(num,updated,description,date,type,birthDate, (User) session.getAttribute("doctor"),nextRoom);
        return "redirect:/cards";
    }


}
