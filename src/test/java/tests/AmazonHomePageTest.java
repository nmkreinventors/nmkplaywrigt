package tests;

import browser.TestBase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.annotations.Test;
import pages.amazon.AmazonHomePage;
import pages.amazon.AmazonSearchResultsPage;
import reports.ExtentReportManager;
import utils.ScreenshotUtil;

public class AmazonHomePageTest extends TestBase {


    @Test(
            groups = {"smoke", "regression"},
            description = "Verify Amazon Page loaded Succesfully"
    )
    public void verifyAmazonHomePageLoadedSuccessfully(){

        ExtentReportManager.logstep(Status.INFO,"Verifying Amazon Home Page Loaded Succesfully Functionality");
        AmazonHomePage amazonHomePage = new AmazonHomePage();

        if(amazonHomePage.isLogoVisible()){
            ExtentReportManager.logstep(Status.PASS,"Amazon Logo Loaded Succesfully");
        }else{
            String screenshotPath = ScreenshotUtil.capture("AmazonLogoHomePage_FAIL");
            ExtentReportManager.logstep(Status.FAIL, "Amazon Logo did not Loaded Succesfully");
            ExtentReportManager.getTest().fail(MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }
    }

    @Test(
            groups = {"smoke", "regression"},
            description = "Verify Amazon Serach working succesfully"
    )
    public void verifyAmazonSearchFunctionality(){

        ExtentReportManager.logstep(Status.INFO,"Verifying Amazon Home Page Loaded Succesfully Functionality");
        AmazonHomePage amazonHomePage = new AmazonHomePage();

        if(amazonHomePage.isLogoVisible()){
            ExtentReportManager.logstep(Status.PASS,"Amazon Logo Loaded Succesfully");
        }else{
            String screenshotPath = ScreenshotUtil.capture("AmazonLogoHomePage_FAIL");
            ExtentReportManager.logstep(Status.FAIL, "Amazon Logo did not Loaded Succesfully");
            ExtentReportManager.getTest().fail(MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }

        amazonHomePage.selectProduct("Amazon Fresh");
        ExtentReportManager.logstep(Status.PASS,"Amazon Fresh Product Loaded Succesfully");

        AmazonSearchResultsPage amazonSearchResultsPage = amazonHomePage.searchFor("Apple Products");
        String productresults = amazonSearchResultsPage.getProductResultText();
        ExtentReportManager.logstep(Status.PASS,productresults);

    }

    @Test(
            groups = {"smoke", "regression"},
            description = "Verify Amazon Serach working succesfully"
    )
    public void verifySearchAmazonFunctionality(){

        ExtentReportManager.logstep(Status.INFO,"Verifying Amazon Home Page Loaded Succesfully Functionality");
        AmazonHomePage amazonHomePage = new AmazonHomePage();

        if(amazonHomePage.isLogoVisible()){
            ExtentReportManager.logstep(Status.PASS,"Amazon Logo Loaded Succesfully");
        }else{
            String screenshotPath = ScreenshotUtil.capture("AmazonLogoHomePage_FAIL");
            ExtentReportManager.logstep(Status.FAIL, "Amazon Logo did not Loaded Succesfully");
            ExtentReportManager.getTest().fail(MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }

        amazonHomePage.selectProduct("Amazon Fresh");
        ExtentReportManager.logstep(Status.PASS,"Amazon Fresh Product Loaded Succesfully");

        AmazonSearchResultsPage amazonSearchResultsPage = amazonHomePage.searchFor("Apple Products");
        String productresults = amazonSearchResultsPage.getProductResultText();
        ExtentReportManager.logstep(Status.PASS,productresults);

    }






}
