
package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.ValidationException;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
public class Person extends AbstractPersistable<Long> {
    @NotEmpty(message="Yritä nyt edes jotain")
    private String name;
    
    @Size(min=2, max=20)
    @Column(unique=true)
    private String username;
    
    @Size(min=8, max=140)
    private String password;
    
    private String salt;
    
    @OneToMany(mappedBy="owner")
    private List<TodoItem> todos = new ArrayList<>();
    
    @OneToMany(mappedBy="owner")
    private List<Category> categories = new ArrayList<>();

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public List<TodoItem> getTodos() {
        return todos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        if(password.length() < 3)
            throw new ValidationException("Password is too short");
        this.salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, salt);
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setTodos(List<TodoItem> todos) {
        this.todos = todos;
    }
    
}
