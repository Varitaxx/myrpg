package de.varitaxx.myrpg.controller;

import de.varitaxx.myrpg.exception.CharakterNameAlreadyExistsException;
import de.varitaxx.myrpg.exception.CharakterNotFoundException;
import de.varitaxx.myrpg.exception.UserNotFoundException;
import de.varitaxx.myrpg.model.Charakter;
import de.varitaxx.myrpg.service.CharService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Data
@RequiredArgsConstructor
public class CharController {


    private final CharService charService;


    @GetMapping("chars")
    public String chars(Model model){
        Optional<?> role  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst();
        if(role.isPresent() && role.get().toString().equals("ADMIN")){
            return "forward:/admin";
        }
        //Angepasst für Charas
        List<Charakter> charakters = charService.findChars();
        model.addAttribute("charas", charakters);
        return "chars";
    }

    @RequestMapping(value = "chars/delete/{id}")
    String deleteUser(@PathVariable(name = "id")Long id, Model model) throws UserNotFoundException {

        charService.deleteChar(id);
        //return "admin";
        return chars(model);

    }

    @GetMapping("newchars")
    public String newChar(Charakter chara, Model model){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        model.addAttribute("success", true);
        return "newchars";
    }

    @RequestMapping(value = "newchars/save", method = RequestMethod.POST)
    public String SaveStudent(@ModelAttribute("charakter") Charakter  charakter, @RequestParam(value="action", required=true) String action) throws CharakterNameAlreadyExistsException {
        if (action.equals("save")) {
            charService.addChara(charakter);
        }
        // ???
        return "newchars";
    }

    @RequestMapping(value = "newchars/update", method = RequestMethod.POST)
    public String updateStudent(@ModelAttribute("charakter") Charakter charakter, @RequestParam(value="action", required=true) String action) throws CharakterNameAlreadyExistsException, CharakterNotFoundException {
        if (action.equals("update")) {
            charService.updateChar(charakter);
        }
        // ???
        return "newchars";
    }






}
