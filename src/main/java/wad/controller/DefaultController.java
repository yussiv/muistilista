
package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Category;
import wad.domain.Person;
import wad.domain.TodoItem;
import wad.service.CategoryService;
import wad.service.TodoItemService;

@Controller
public class DefaultController {
    
    @Autowired
    private TodoItemService itemService;
    @Autowired
    private CategoryService catService;
    
    @ModelAttribute("person")
    public Person getPerson() {
        return new Person();
    }
    
    @RequestMapping("/")
    public String home(Model model, TodoItem item, Category category) {
        model.addAttribute("itemNode", itemService.findAll());
        model.addAttribute("categories", catService.findAll());
        return "index";
    }
    
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String viewLogin(Model model, @RequestParam(required=false) String error) {
        if(error != null)
            model.addAttribute("error", "Sisäänkirjautuminen epäonnistui");
        return "login";
    }
    
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String handleLogin(Authentication auth) {
        if(auth.isAuthenticated())
            return "redirect:/";
        else {
            return "login";
        }
    }
}
