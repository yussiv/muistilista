
package wad.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wad.domain.Category;
import wad.domain.TodoItem;
import wad.repository.CategoryRepository;
import wad.repository.TodoItemRepository;
import wad.service.CategoryService;
import wad.service.TodoItemService;

@Controller
public class TodoItemController {
    
    @Autowired
    private TodoItemService itemService;
    @Autowired
    private CategoryService catService;
    
    @RequestMapping(value="/todo/{id}", method=RequestMethod.GET)
    public String editItem(Model model, @PathVariable Long id) {
        model.addAttribute("item", itemService.get(id));
        model.addAttribute("categories", catService.findAll());
        return "form/editItem";
    }
    
    @RequestMapping(value="/todo", method=RequestMethod.POST)
    public String addItem(@Valid @ModelAttribute TodoItem item, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form/addItem";
        }
        itemService.create(item);
        return "redirect:/";
    }
    
    @RequestMapping(value="/todo/{id}", method=RequestMethod.POST)
    public String updateItem(
            @Valid @ModelAttribute TodoItem item, 
            BindingResult bindingResult, @PathVariable Long id, Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", itemService.get(id));
            model.addAttribute("categories", catService.findAll());
            return "form/editItem";
        }
        itemService.update(id, item);
        return "redirect:/";
    }
    
    @RequestMapping(value="/todo/{id}", method=RequestMethod.DELETE)
    public String deleteItem(@PathVariable Long id) {
        itemService.delete(id);
        return "redirect:/";
    }
}
