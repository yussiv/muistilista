
package wad.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Model object representing a category node containing TodoItems and subcategories
 */
public class TodoItemCategoryNode {
    private String name;
    private List<TodoItem> items = new ArrayList<>();
    private List<TodoItemCategoryNode> subcategories = new ArrayList<>();

    public String getName() {
        return name;
    }

    public List<TodoItem> getItems() {
        return items;
    }

    public List<TodoItemCategoryNode> getSubcategories() {
        return subcategories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<TodoItem> items) {
        this.items = items;
    }

    public void setSubcategories(List<TodoItemCategoryNode> subcategories) {
        this.subcategories = subcategories;
    }
    
    
}
