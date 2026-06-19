package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import config.ConfigReader;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {

    private final Logger log = LoggerFactory.getLogger(ExtentReportManager.class);
    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> extentTestTL = new ThreadLocal<>();

    public static synchronized void initReports(){

        ConfigReader config = ConfigReader.getInstance();
        String reportDir = config.get("extent.report.dir");
        String reportName = config.get("extent.report.name");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String fileName = reportDir + File.separator + reportName+"_"+timestamp+".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle(reportName);
        sparkReporter.config().setReportName(reportName);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Environment", config.getEnv());

    }

    public static ExtentTest createTest(String testName, String testDescription){
        ExtentTest test = extentReports.createTest(testName, testDescription);
        extentTestTL.set(test);
        return test;
    }

    public static synchronized void flushReports(){
        extentReports.flush();
    }

    public static void logstep(Status status, String message){
        extentTestTL.get().log(status, message);
    }


}
