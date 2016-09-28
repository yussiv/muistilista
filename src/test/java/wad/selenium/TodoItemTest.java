package wad.selenium;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import wad.domain.Person;
import wad.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value="test")
public class TodoItemTest extends FluentTest {
    
    public WebDriver webDriver = new HtmlUnitDriver(true);
    private String baseUrl;
    
    @Autowired
    private PersonRepository personRepo;

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }
    
    @LocalServerPort
    private Integer port;
    
    @Before
    public void setVariables() {
        this.baseUrl = "http://localhost:" + port + "/";
        
        // add test user
        if(personRepo.findByUsername("user") == null){
            //set up test user
            Person testUser = new Person();
            testUser.setName("Test User");
            testUser.setUsername("user");
            testUser.setPassword("password");

            personRepo.save(testUser);
        }
        // login with credentials created
        goTo(baseUrl + "login");
        fill(find("input[type='text']")).with("user");
        fill(find("input[type='password']")).with("password");
        submit(find("form").first());
    }

    @Test
    public void canAddTodoItem() {
        goTo(baseUrl);
        
        String desc = "Do seleniumtests";
        assertFalse("Page already contais item: " + desc, pageSource().contains(desc));
        
        fillFormAndSubmit(desc);
        
        assertTrue("Page does not contain item: " + desc, pageSource().contains(desc));
    }
    
    @Test
    public void canEditTodoItem() {
        goTo(baseUrl);
        
        String desc = "Edit selenium item";
        assertFalse(pageSource().contains(desc));
        
        fillFormAndSubmit(desc);
        
        assertTrue(pageSource().contains(desc));
        
        click(find("li", withText().contains(desc)).find("a.edit-item"));
        
        assertTrue("Wrong url", webDriver.getCurrentUrl().matches(baseUrl + "todo/\\d$"));
        
        String newDesc = "Edited selenium item";
        
        fill(find("input[name='description']")).with(newDesc);
        submit(find("form#form-edit-item").first());
        
        assertEquals("Wrong url", baseUrl, webDriver.getCurrentUrl());
        
        assertFalse("Old description is still present", pageSource().contains(desc));
        
        assertTrue("Modified description not found", pageSource().contains(newDesc));
    }
    
    @Test
    public void canRemoveTodoItem() throws InterruptedException {
        goTo(baseUrl);
        
        String desc = "Delete item with selenium";
        assertFalse("A item with the same name already exists", pageSource().contains(desc));
        
        fillFormAndSubmit(desc);
        
        assertTrue("New item is not found on page", pageSource().contains(desc));
        
        //System.out.println("found: " + find("li", withText().contains(desc)).find(".delete-item").toString() + " at url: " + webDriver.getCurrentUrl());
        submit(find("li", withText().contains(desc)).find(".delete-item").first());
        assertEquals("Wrong url", baseUrl, webDriver.getCurrentUrl());
        
        assertFalse("Item was not removed", pageSource().contains(desc));
        
    }
    
    private void fillFormAndSubmit(String description) {
        fill(find(".add-item input[name='description']")).with(description);
        submit(find(".add-item").first());
    }
}
