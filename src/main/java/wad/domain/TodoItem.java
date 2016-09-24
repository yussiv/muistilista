
package wad.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class TodoItem extends AbstractPersistable<Long> {
    
    public enum Priority { 
        HIGH("Korkea"), NORMAL("Normaali"), LOW("Matala");
        
        private final String displayName;
        
        private Priority(String displayName) {
            this.displayName = displayName;
        }
        public String getName() {
            return this.name();
        }
        public String toString() {
            return displayName;
        }
    }
    
    @NotNull
    @NotEmpty
    private String description;
    private Priority priority;
    @OneToOne
    private Category category;
    @ManyToOne
    private Person owner;

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
