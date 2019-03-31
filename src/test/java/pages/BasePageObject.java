package pages;

import org.openqa.selenium.WebDriver;

/**
 * @author Vitalii Bondariev
 */

public class BasePageObject {
    protected final WebDriver driver;

    protected BasePageObject(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }
}
