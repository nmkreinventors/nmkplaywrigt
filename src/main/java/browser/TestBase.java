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

    private static final Logger LOG = LoggerFactory.getLogger(TestBase.class);
    private static final ConfigReader config = ConfigReader.getInstance();

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        ExtentReportManager.initReports();
        LOG.info("----Suite Started---");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        ExtentReportManager.flushReports();
        LOG.info("----Suite Finished---");
        LOG.info("--Extent Report File Path----: {}", ExtentReportManager.getReportFilePath());
    }



    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void beforeMethod(String browser, Method method, ITestContext context) {
        String effectiveBrowser = browser.isBlank() ? config.getBrowser() : browser;
        LOG.info("Thread-{} Intilaizing browser: {}", Thread.currentThread().getId(), effectiveBrowser);

        BrowserManager.initBrowser(effectiveBrowser);
        String testDescription = method.isAnnotationPresent(Test.class)?method.getAnnotation(Test.class).description():"";
        ExtentReportManager.createTest(method.getName(), testDescription);

        navigateToBaseURL();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {

        if(result.getStatus() == ITestResult.SUCCESS) {
            if(ExtentReportManager.getTest() != null) {
                ExtentReportManager.logstep(Status.PASS, "Test passed");
            }
        } else if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ScreenshotUtil.capture(result.getMethod().getMethodName()+ "_FAIL");
            if(ExtentReportManager.getTest() != null) {
                ExtentReportManager.getTest().fail(MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build())
                        .fail("Test Failed, Screenshot attached");
                ExtentReportManager.logstep(Status.FAIL, "Test failure.");
            }
        } else if  (result.getStatus() == ITestResult.SKIP) {
            if(ExtentReportManager.getTest() != null) {
                ExtentReportManager.logstep(Status.SKIP, "Test skipped");
            }
        }

        BrowserManager.tearDown();
        ExtentReportManager.removeTest();

    }

    private void navigateToBaseURL(){
        String baseUrl = config.getBaseUrl();
        LOG.info("Navigating to baseURL: {}", baseUrl);
        BrowserManager.getPage().navigate(baseUrl);
    }




}
