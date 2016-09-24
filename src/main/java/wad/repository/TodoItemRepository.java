
package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem,Long> {

    public TodoItem findOneByDescription(String description);

}
