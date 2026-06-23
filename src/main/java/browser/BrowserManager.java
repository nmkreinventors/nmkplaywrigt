package browser;

import com.microsoft.playwright.*;
import com.sun.source.tree.CaseTree;
import config.ConfigReader;
import constants.BrowserType;
import constants.FrameworkConstants;
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

    public static void initBrowser(String browserName){

        BrowserType browserType = BrowserType.fromValue(browserName);
        LOG.info("Thread-{} Intilaizing browser: {}", Thread.currentThread().getId(), browserName);

        Playwright playwright = Playwright.create();
        playwrightTL.set(playwright);
        currentBrowserTL.set(browserType.getValue());

        Browser browser = launchBrowser(playwright, browserType);
        browserTL.set(browser);

        BrowserContext browserContext = createContext(browser);
        browserContextTL.set(browserContext);

        Page page = browserContext.newPage();
        pageTL.set(page);

    }

    public static Page getPage(){
        Page page = pageTL.get();
        return page;
    }

    public static BrowserContext getBrowserContext(){
        return   browserContextTL.get();
    }

    public static Browser getBrowser(){
        return   browserTL.get();
    }

    public static String getCurrentBrowser(){
        return currentBrowserTL.get();
    }



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

    private static BrowserContext createContext(Browser browser) {

        int width = Integer.parseInt(config.get("viewport.width"));
        int height = Integer.parseInt(config.get("viewport.height"));
        Browser.NewContextOptions ctxOptions = new Browser.NewContextOptions()
                .setViewportSize(width, height)
                .setIgnoreHTTPSErrors(true)
                .setAcceptDownloads(true);

        BrowserContext context = browser.newContext(ctxOptions);

        String traceMode = config.get("trace.mode");
        if(traceMode.equals(FrameworkConstants.MODE_ALWAYS)){
            context.tracing().start(new Tracing.StartOptions().setSources(true).setScreenshots(true)
                    .setSnapshots(true).setName(FrameworkConstants.TRACE_NAME));
        }

        context.setDefaultNavigationTimeout(Integer.parseInt(config.get("navigation.timeout")));
        context.setDefaultTimeout(Integer.parseInt(config.get("default.timeout")));

        return context;

    }

    //Tear down steps to clear objects will be created later
    public static void tearDown(){
        BrowserContext browserContext = browserContextTL.get();
        String traceMode = config.get("trace.mode");
        if(traceMode.equals(FrameworkConstants.MODE_ALWAYS)) {
            browserContext.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(FrameworkConstants.TRACE_DIR, FrameworkConstants.TRACE_NAME + ".zip")));
        }

        Playwright pw = playwrightTL.get();
        pw.close();
        browserContextTL.remove();
        browserTL.remove();
        pageTL.remove();
        playwrightTL.remove();
    }





}
