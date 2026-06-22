package utils;

import browser.BrowserManager;
import com.microsoft.playwright.Page;
import constants.FrameworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtil {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final DateTimeFormatter TS_FMT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtil() {}

    public static String capture(String screenshotName) {
        try {
            Page page = BrowserManager.getPage();
            String timestamp = LocalDateTime.now().format(TS_FMT);
            String fileName  = screenshotName + "_" + timestamp + ".png";
            Path dir = Paths.get(FrameworkConstants.SCREENSHOTS_DIR);
            Files.createDirectories(dir);
            Path filePath = dir.resolve(fileName);
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(filePath)
                    .setFullPage(true));
            log.info("Screenshot saved: {}", filePath.toAbsolutePath());
            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            log.error("Failed to save screenshot: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            log.warn("Screenshot skipped (page may be closed): {}", e.getMessage());
            return null;
        }
    }

}
