package tests.amazon;

import browser.TestBase;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.amazon.AmazonHomePage;
import reports.ExtentReportManager;

public class AmazonSearchTest extends TestBase {

    @Test(
            groups = {"smoke", "regression"},
            description = "Verify Amazon home page loads and displays the logo and search box"
    )
    public void verifyHomePageLoads() {
        ExtentReportManager.logstep(Status.INFO, "Verifying Amazon home page loads correctly");
        AmazonHomePage homePage = new AmazonHomePage();
        homePage.selectProduct("Amazon Fresh");
        Assert.assertTrue(homePage.isLogoVisible(), "Amazon logo should be visible on the home page");
        ExtentReportManager.logstep(Status.PASS, "Verifying Amazon home page loads correctly");
    }

    @Test(
            groups = {"smoke", "regression"},
            description = "Verify Amazon home page loads and displays the logo and search box"
    )
    public void verifyHomePageSearchFunctionality() {
        ExtentReportManager.logstep(Status.INFO, "Verifying Home Page Search Functionality");
        AmazonHomePage homePage = new AmazonHomePage();
        homePage.selectProduct("Amazon Fresh");
        Assert.assertTrue(homePage.isLogoVisible(), "Amazon logo should be visible on the home page");
        ExtentReportManager.logstep(Status.PASS, "Verifying Amazon home page loads correctly");

        homePage.searchFor("Apple Laptops");
        ExtentReportManager.logstep(Status.PASS, "Verifying Able to search Amazon products Succesfully");
    }

    @Test(
            groups = {"smoke", "regression"},
            description = "Verify Amazon home page loads and displays the logo and search box"
    )
    public void verifySearchHomePageFunctionality() {
        ExtentReportManager.logstep(Status.INFO, "Verifying Home Page Search Functionality");
        AmazonHomePage homePage = new AmazonHomePage();
        homePage.selectProduct("Amazon Fresh");
        Assert.assertTrue(homePage.isLogoVisible(), "Amazon logo should be visible on the home page");
        ExtentReportManager.logstep(Status.PASS, "Verifying Amazon home page loads correctly");

        homePage.searchFor("Apple Laptops");
        ExtentReportManager.logstep(Status.PASS, "Verifying Able to search Amazon products Succesfully");
    }

    @Test(
            groups = {"smoke", "regression"},
            description = "Verify Amazon home page loads and displays the logo and search box"
    )
    public void verifySeaPageFunctionality() {
        ExtentReportManager.logstep(Status.INFO, "Verifying Home Page Search Functionality");
        AmazonHomePage homePage = new AmazonHomePage();
        homePage.selectProduct("Amazon Fresh");
        Assert.assertTrue(homePage.isLogoVisible(), "Amazon logo should be visible on the home page");
        ExtentReportManager.logstep(Status.PASS, "Verifying Amazon home page loads correctly");

        homePage.searchFor("Apple Laptops");
        ExtentReportManager.logstep(Status.PASS, "Verifying Able to search Amazon products Succesfully");
    }


}
