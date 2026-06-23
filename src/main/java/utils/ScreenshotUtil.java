package utils;

import browser.BrowserManager;
import com.microsoft.playwright.Page;
import constants.FrameworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtil(){}

    public static String capture(String screenshotName){
        try {
            Page page = BrowserManager.getPage();
            String filename = screenshotName + "_" + LocalDateTime.now().format(dateTimeFormatter) + ".png";
            Path dir = Paths.get(FrameworkConstants.SCREENSHOT_DIR);
            Files.createDirectories(dir);
            Path filepath = dir.resolve(filename);
            page.screenshot(new Page.ScreenshotOptions().setPath(filepath).setFullPage(true));
            return filepath.toAbsolutePath().toString();
        }catch (Exception e){
            LOG.info(e.getMessage());
            return null;
        }
    }




}
