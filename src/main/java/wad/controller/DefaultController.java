
package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import wad.domain.Category;
import wad.repository.TodoItemRepository;
import wad.service.CategoryService;

@Controller
public class DefaultController {
    
    @Autowired
    private TodoItemRepository itemRepo;
    @Autowired
    private CategoryService catService;
    
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("items", itemRepo.findAll());
        model.addAttribute("categories", catService.findAll());
        return "index";
    }
}
