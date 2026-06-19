package pages;

import browser.BrowserManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BasePage {

    private final Logger log =  LoggerFactory.getLogger(BasePage.class);
    private final ConfigReader config = ConfigReader.getInstance();

    protected Page page(){
        return BrowserManager.getPage();
    }

    protected String getTitle(){
        return  page().title();
    }

    protected void navigate(String url){
        page().navigate(url);
        log.info("Navigated to: {}" , url);
    }

    protected void click(Locator locator){
        locator.click();
        log.info("Clicked: {}" , locator);
    }

    protected  void fill(Locator locator, String value){
        locator.fill(value);
        log.info("Filling: {} into {}" , value, locator);
    }

    protected void selectoption(Locator locator, String value){
        locator.selectOption(value);
        log.info("Selecting: {} into {}" , value, locator);
    }

    protected boolean isVisible(Locator locator){
        return locator.isVisible();
    }

    protected String getText(String locator){
        return  page().locator(locator).innerText();
    }

    protected String getText(Locator locator){
        return  locator.innerText();
    }

    protected String getAttribute(Locator locator, String attribute){
        return  locator.getAttribute(attribute);
    }

}
