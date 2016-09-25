
package wad.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import wad.domain.Category;
import wad.service.CategoryService;
import wad.service.TodoItemService;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService catService;
    @Autowired
    private HttpSession session;
    @Autowired
    private TodoItemService itemService;
    
    @RequestMapping(method=RequestMethod.POST)
    public String addCategory(@Valid @ModelAttribute Category category, BindingResult bindingResult){
            
        if (bindingResult.hasErrors()) {
            return "form/addCategory";
        }
        catService.create(category);
        
        return "redirect:/";
    }
}
