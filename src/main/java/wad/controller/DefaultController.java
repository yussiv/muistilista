
package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import wad.repository.TodoItemRepository;

@Controller
public class DefaultController {
    
    @Autowired
    private TodoItemRepository itemRepo;
    
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("items", itemRepo.findAll());
        return "index";
    }
}
