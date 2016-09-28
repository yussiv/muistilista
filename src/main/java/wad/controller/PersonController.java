
package wad.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Person;
import wad.repository.PersonRepository;

@Controller
public class PersonController {
    
    @Autowired
    private PersonRepository personRepo;
    
    @ModelAttribute("person")
    public Person getPerson() {
        return new Person();
    }
    
    @RequestMapping(value="/signup", method=RequestMethod.GET)
    public String signup() {
        return "signup";
    }
    
    @RequestMapping(value="/signup", method=RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute Person person, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "signup";
        }
        personRepo.save(person);
        return "redirect:/login?signup";
    }
}

