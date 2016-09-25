
package wad.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.auth.AuthenticatedUser;
import wad.domain.Category;
import wad.repository.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    AuthenticatedUser authenticatedUser;
    @Autowired
    CategoryRepository catRepo;

    public List<Category> findAll() {
        return catRepo.findAllByOwner(authenticatedUser.get());
    }

    public void create(Category category) {
        category.setOwner(authenticatedUser.get());
        catRepo.save(category);
    }
}
