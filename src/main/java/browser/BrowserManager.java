package browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sun.source.tree.CaseTree;
import config.ConfigReader;
import constants.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import java.nio.file.Paths;
import java.util.List;

public class BrowserManager {

    private static final Logger LOG = LoggerFactory.getLogger(BrowserManager.class);
    private static final ConfigReader config = ConfigReader.getInstance();

    //Thread-Local Storages
    private static final ThreadLocal<Playwright> playwrightTL = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserTL = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContextTL = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageTL = new ThreadLocal<>();
    private static final ThreadLocal<String> currentBrowserTL = new ThreadLocal<>();


    private static Browser launchBrowser(Playwright playwright, BrowserType  browserType) {
        boolean headless = config.get("headless").equals("true");
        LaunchOptions options = new LaunchOptions().setHeadless(headless)
                .setArgs(List.of("--start-maximized"))
                .setSlowMo(3000);
        return switch (browserType){
            case CHROME -> playwright.chromium().launch(options.setChannel("chrome"));
            case MSEDGE -> playwright.chromium().launch(options.setChannel("msedge"));
            case FIREFOX ->  playwright.firefox().launch(options);
            case WEBKIT ->  playwright.webkit().launch(options);
        };
    }


}
