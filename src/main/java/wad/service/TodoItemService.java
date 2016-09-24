
package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.auth.AuthenticatedUser;
import wad.domain.Category;
import wad.domain.TodoItem;
import wad.domain.TodoItemForm;
import wad.repository.CategoryRepository;
import wad.repository.TodoItemRepository;

@Service
public class TodoItemService {
    
    @Autowired
    private TodoItemRepository itemRepo;
    @Autowired
    private CategoryRepository catRepo;
    @Autowired
    private AuthenticatedUser authenticatedUser;
    
    public void createTodoItem(TodoItemForm form) {
        TodoItem item = new TodoItem();
        item.setDescription(form.getDescription());
        item.setPriority(form.getPriority());
        item.setOwner(authenticatedUser.get());
        
        Category cat = getCategory(form.getCategoryName());
        item.setCategory(cat);
        Category parentCat = catRepo.findOneByName(form.getParentCategory());
        if(parentCat != null)
            cat.setParentCategory(parentCat);
        itemRepo.save(item);
    }

    public TodoItem get(Long id) {
        return itemRepo.findOne(id);
    }

    public void update(Long id, TodoItemForm form) {
        TodoItem item = itemRepo.findOne(id);
        if(item != null) {
            item.setDescription(form.getDescription());
            item.setCategory(getCategory(form.getCategoryName()));
            item.setPriority(form.getPriority());
            itemRepo.save(item);
        }
    }

    public void delete(Long id) {
        TodoItem item = itemRepo.findOne(id);
        if(item != null) {
            itemRepo.delete(item);
        }
    }
    
    private Category getCategory(String name) {
        Category cat = catRepo.findOneByName(name);
        if(cat == null) {
            cat = new Category();
            cat.setName(name);
            cat.setOwner(authenticatedUser.get());
            catRepo.save(cat);
        }
        return cat;
    }
}
