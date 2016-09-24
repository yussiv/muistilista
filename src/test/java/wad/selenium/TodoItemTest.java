package wad.selenium;

import java.util.Iterator;
import java.util.List;
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
    
    public WebDriver webDriver = new HtmlUnitDriver();
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
        assertFalse(pageSource().contains(desc));
        
        fillFormAndSubmit(desc);
        
        assertTrue(pageSource().contains(desc));
    }
    
    @Test
    public void canEditTodoItem() {
        goTo(baseUrl);
        
        String desc = "Edit selenium item";
        assertFalse(pageSource().contains(desc));
        
        fillFormAndSubmit(desc);
        
        assertTrue(pageSource().contains(desc));
        
        click(find("li", withText().contains(desc)).find("a", withText("Muokkaa")));
        
        assertTrue(webDriver.getCurrentUrl().matches(baseUrl + "todo/\\d$"));
        
        String newDesc = "Edited selenium item";
        
        fillFormAndSubmit(newDesc);
        
        assertTrue(webDriver.getCurrentUrl().equals(baseUrl));
        
        assertFalse(pageSource().contains(desc));
        
        assertTrue(pageSource().contains(newDesc));
    }
    
    private void fillFormAndSubmit(String description) {
        fill(find("input[name='description']")).with(description);
        submit(find("form").first());
    }
}
