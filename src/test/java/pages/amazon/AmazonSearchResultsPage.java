package pages.amazon;

import com.microsoft.playwright.Locator;
import pages.BasePage;

import java.util.Base64;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AmazonSearchResultsPage extends BasePage {

    //Locators
    private final Locator productResult = page().getByText(Pattern.compile("^\\d+-\\d+ of .* results for"));

    public String getProductResultText(String productResultText){
        return getText(productResult);
    }

}
