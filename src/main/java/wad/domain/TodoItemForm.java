
package wad.domain;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class TodoItemForm {
    private String categoryName;
    private String parentCategory;
    @NotNull
    @NotEmpty
    private String description;
    private TodoItem.Priority priority = TodoItem.Priority.NORMAL;

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }

    public TodoItem.Priority getPriority() {
        return priority;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(TodoItem.Priority priority) {
        this.priority = priority;
    }
    
}
