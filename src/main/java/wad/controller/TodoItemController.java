
package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.TodoItem;
import wad.repository.TodoItemRepository;

@Controller
public class TodoItemController {
    
    @Autowired
    private TodoItemRepository itemRepo;
    
    @RequestMapping(value="/todo", method=RequestMethod.POST)
    public String addItem(@ModelAttribute TodoItem item) {
        itemRepo.save(item);
        return "redirect:/";
    }
    
    @RequestMapping(value="/todo/{id}", method=RequestMethod.GET)
    public String editItem(Model model, @PathVariable Long id) {
        TodoItem item = itemRepo.findOne(id);
        model.addAttribute("item", item);
        return "edit-item";
    }
    
    @RequestMapping(value="/todo/{id}", method=RequestMethod.POST)
    public String updateItem(@ModelAttribute TodoItem modifiedItem, @PathVariable Long id) {
        TodoItem item = itemRepo.findOne(id);
        if(item != null) {
            item.setDescription(modifiedItem.getDescription());
            itemRepo.save(item);
        }
        return "redirect:/";
    }
    
    @RequestMapping(value="/todo/{id}", method=RequestMethod.DELETE)
    public String deleteItem(@PathVariable Long id) {
        TodoItem item = itemRepo.findOne(id);
        if(item != null) {
            itemRepo.delete(item);
        }
        return "redirect:/";
    }
}
