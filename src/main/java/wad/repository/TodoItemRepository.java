
package wad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Category;
import wad.domain.Person;
import wad.domain.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem,Long> {

    public TodoItem findOneByDescription(String description);

    public List<TodoItem> findAllByOwnerAndCategory(Person owner, Category category);

    public List<TodoItem> findAllByOwner(Person owner);

}
