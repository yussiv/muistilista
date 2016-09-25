
package wad.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.auth.AuthenticatedUser;
import wad.domain.Category;
import wad.domain.Person;
import wad.domain.TodoItem;
import wad.domain.TodoItemCategoryNode;
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
    
    @Transactional
    public TodoItemCategoryNode findAll() {
        return createSubnode(authenticatedUser.get(), null);
    }
    
    // Recursively build a node hierarchy with a list of TodoItems and subnodes (subcategories)
    private TodoItemCategoryNode createSubnode(Person owner, Category category) {
        TodoItemCategoryNode node = new TodoItemCategoryNode();
        node.setItems(itemRepo.findAllByOwnerAndCategory(owner, category));
        node.setName(category == null ? "Luokittelemattomat" : category.getName());
        
        List<Category> categories = catRepo.findAllByOwnerAndParentCategory(owner, category);
        List<TodoItemCategoryNode> subnodes = new ArrayList<>();
        for(Category c : categories){
            subnodes.add(createSubnode(owner, c));
        }
        node.setSubcategories(subnodes);
        return node;
    }
    
    public void create(TodoItem item) {
        item.setOwner(authenticatedUser.get());
        
        itemRepo.save(item);
    }

    public TodoItem get(Long id) {
        return itemRepo.findOne(id);
    }

    public void update(Long id, TodoItem updatedItem) {
        TodoItem item = itemRepo.findOne(id);
        if(item != null) {
            item.setDescription(updatedItem.getDescription());
            item.setCategory(updatedItem.getCategory());
            item.setPriority(updatedItem.getPriority());
            itemRepo.save(item);
        }
    }

    public void delete(Long id) {
        TodoItem item = itemRepo.findOne(id);
        if(item != null) {
            itemRepo.delete(item);
        }
    }
}
