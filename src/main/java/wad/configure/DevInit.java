
package wad.configure;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wad.domain.TodoItem;
import wad.repository.TodoItemRepository;

@Component
public class DevInit {
    
    @Autowired
    private TodoItemRepository itemRepo;

    @PostConstruct
    public void populateDB() {
        TodoItem item = new TodoItem();
        item.setDescription("Tee wepan harkkatyö");
        itemRepo.save(item);
    }
}
