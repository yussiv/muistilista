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
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wad.auth.AuthenticatedUser;
import wad.domain.Person;
import wad.domain.TodoItem;
import wad.repository.PersonRepository;
import wad.repository.TodoItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {
    
    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;
    
    @Autowired
    private TodoItemRepository itemRepo;
    @Autowired
    private AuthenticatedUser authenticatedUser;
    @Autowired
    private PersonRepository personRepo;
        
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        
        if(personRepo.findByUsername("user") == null){
            //set up test user
            Person testUser = new Person();
            testUser.setName("Test User");
            testUser.setUsername("user");
            testUser.setPassword("password");

            personRepo.save(testUser);
        }
    }
    
    @Test
    @WithMockUser
    public void frontpageContainsAllItems() throws Exception {
        MvcResult result =mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("itemNode"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        for(TodoItem item : itemRepo.findAllByOwner(authenticatedUser.get())) {
            assertTrue("Front page does not contain all TodoItems of user", content.contains(item.getDescription()));
        }
    }
    
    @Test
    @WithMockUser
    public void canAddItemToList() throws Exception {
        String description = UUID.randomUUID().toString();
        addItemToList(description);
        
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("itemNode"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        assertTrue("Front page does not contain new TodoItem " + description, content.contains(description));
        
    }
    
    @Test
    @WithMockUser
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
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
