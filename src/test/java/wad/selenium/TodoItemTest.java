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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoItemTest extends FluentTest {
    
    public WebDriver webDriver = new HtmlUnitDriver(true);
    private String baseUrl;

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }
    
    @LocalServerPort
    private Integer port;
    
    @Before
    public void setVariables() {
        this.baseUrl = "http://localhost:" + port + "/";
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
        submit(find("form").first());
        
        assertTrue("Wrong url", webDriver.getCurrentUrl().equals(baseUrl));
        
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
