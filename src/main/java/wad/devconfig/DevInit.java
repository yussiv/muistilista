
package wad.devconfig;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import wad.domain.Category;
import wad.domain.Person;
import wad.domain.TodoItem;
import wad.repository.CategoryRepository;
import wad.repository.PersonRepository;
import wad.repository.TodoItemRepository;

@Component
@Profile("development")
public class DevInit {
    
    @Autowired
    private TodoItemRepository itemRepo;
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private CategoryRepository catRepo;

    @PostConstruct
    public void populateDB() {
        Person p = new Person();
        p.setName("Matti");
        p.setPassword("matti");
        p.setUsername("matti");
        personRepo.save(p);
        
        Category cat = new Category();
        cat.setName("haksaus");
        cat.setOwner(p);
        catRepo.save(cat);
        
        TodoItem item = new TodoItem();
        item.setDescription("Tee wepan harkkatyö");
        item.setOwner(p);
        item.setCategory(cat);
        item.setPriority(TodoItem.Priority.NORMAL);
        itemRepo.save(item);
        
        Category cat2 = new Category();
        cat2.setName("wepa");
        cat2.setOwner(p);
        cat2.setParentCategory(cat);
        catRepo.save(cat2);
        
        TodoItem item2 = new TodoItem();
        item2.setDescription("Hoida autentikointi");
        item2.setOwner(p);
        item2.setCategory(cat2);
        item2.setPriority(TodoItem.Priority.HIGH);
        itemRepo.save(item2);
        
        p.getTodos().add(item);
        personRepo.save(p);
    }
    
}
