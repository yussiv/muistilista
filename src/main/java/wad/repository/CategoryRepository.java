
package wad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Category;
import wad.domain.Person;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    public Category findOneByName(String name);

    public List<Category> findAllByOwner(Person owner);

    public List<Category> findAllByOwnerAndParentCategory(Person owner, Category category);
}
