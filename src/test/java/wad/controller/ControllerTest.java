package wad.controller;

import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wad.domain.TodoItem;
import wad.repository.TodoItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {
    
    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;
    
    @Autowired
    private TodoItemRepository itemRepo;
    
        
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }
    
    @Test
    public void frontpageContainsAllItems() throws Exception {
        MvcResult result =mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("items"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        for(TodoItem item : itemRepo.findAll()) {
            assertTrue("Front page does not contain all TodoItems", content.contains(item.getDescription()));
        }
    }
    
    @Test
    public void canAddItemToList() throws Exception {
        String description = UUID.randomUUID().toString();
        addItemToList(description);
        
        MvcResult result =mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("items"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        assertTrue("Front page does not contain new TodoItem " + description, content.contains(description));
        
    }
    
    @Test
    public void canRemoveItemFromList() throws Exception {
        String description = UUID.randomUUID().toString();
        
        addItemToList(description);
        
        TodoItem item = itemRepo.findOneByDescription(description);
        
        assertNotNull("Item with given description not found in repository: "+description,item);
        
        mockMvc.perform(delete("/todo/"+item.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        
        assertNull("Item was not deleted correctly", itemRepo.findOne(item.getId()));
        
    }
    
    private void addItemToList(String description) throws Exception {
        mockMvc.perform(
                    post("/todo")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("description", description)
                    .param("categoryName", "testing")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
