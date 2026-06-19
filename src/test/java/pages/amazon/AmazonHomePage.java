package pages.amazon;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import pages.BasePage;

public class AmazonHomePage extends BasePage {

    //-------------Locators---------------------
    private final Locator searchAmazonProduct = page().getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Search Amazon"));
    private final Locator clickSearchBtn = page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Go").setExact(true));
    private final Locator productdropdown = page().locator("#searchDropdownBox");

   public AmazonSearchResultsPage searchFor(String searchProduct){
       fill(searchAmazonProduct, searchProduct);
       click(clickSearchBtn);
       return new AmazonSearchResultsPage();
   }

   public AmazonHomePage selectProduct(String product){
       selectoption(productdropdown, product);
       return this;
   }





}
