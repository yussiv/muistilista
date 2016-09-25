
package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import wad.domain.Category;
import wad.domain.TodoItem;
import wad.service.CategoryService;
import wad.service.TodoItemService;

@Controller
public class DefaultController {
    
    @Autowired
    private TodoItemService itemService;
    @Autowired
    private CategoryService catService;
    
    @RequestMapping("/")
    public String home(Model model, TodoItem item, Category category) {
        model.addAttribute("itemNode", itemService.findAll());
        model.addAttribute("categories", catService.findAll());
        return "index";
    }
}
