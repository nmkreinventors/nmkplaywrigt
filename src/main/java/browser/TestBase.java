package browser;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import config.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reports.ExtentReportManager;
import utils.ScreenshotUtil;

import java.lang.reflect.Method;

public abstract class TestBase {

    protected static final Logger log = LoggerFactory.getLogger(TestBase.class);
    protected static final ConfigReader config = ConfigReader.getInstance();

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        ExtentReportManager.initReports();
        log.info("=== Suite started ===");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        ExtentReportManager.flushReports();
        log.info("=== Suite finished. Report: {} ===", ExtentReportManager.getReportFilePath());
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void beforeMethod(@Optional("") String browser, Method method, ITestContext context) {
        String effectiveBrowser = browser.isBlank() ? config.getBrowser() : browser;
        log.info("[Thread-{}] Starting test: {} on browser: {}",
                Thread.currentThread().getId(), method.getName(), effectiveBrowser);

        BrowserManager.initBrowser(effectiveBrowser);

        // Create Extent test entry
        String testDescription = method.isAnnotationPresent(Test.class)
                ? method.getAnnotation(Test.class).description()
                : "";
        ExtentReportManager.createTest(method.getName(), testDescription)
                .assignCategory(effectiveBrowser.toUpperCase())
                .assignCategory(context.getSuite().getName());

        // Navigate to base URL
        navigateToBaseUrl();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        boolean failed = result.getStatus() == ITestResult.FAILURE;
        String testName = result.getMethod().getMethodName();

        if (failed) {
            log.error("Test FAILED: {}", testName);
            String screenshotPath = ScreenshotUtil.capture(testName + "_FAIL");
            ExtentReportManager.getTest()
                    .fail("Test failed. Screenshot attached.")
                    .fail(MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

            if (result.getThrowable() != null && ExtentReportManager.getTest() != null) {
                ExtentReportManager.getTest().fail(result.getThrowable());
            }
        }else if (result.getStatus() == ITestResult.SUCCESS) {
            log.info("Test PASSED: {}", testName);
            if (ExtentReportManager.getTest() != null) {
                ExtentReportManager.getTest().log(Status.PASS, "Test passed");
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            log.warn("Test SKIPPED: {}", testName);
            if (ExtentReportManager.getTest() != null) {
                ExtentReportManager.getTest().log(Status.SKIP, "Test skipped");
            }
        }
        BrowserManager.tearDown(failed, testName);
        ExtentReportManager.removeTest();
    }

    private void navigateToBaseUrl() {
        String url = config.getBaseUrl();
        log.info("Navigating to base URL: {}", url);
        BrowserManager.getPage().navigate(url);
    }


}
