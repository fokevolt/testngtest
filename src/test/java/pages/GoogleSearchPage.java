package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author Vitalii Bondariev
 */

public class GoogleSearchPage extends BasePageObject {
    public static final String BASE_URL = "https://www.google.com/";

    private final By searchFieldLocator = By.xpath("//input[@name=\"q\"]");
    private final By searchButtonLocator = By.xpath("//input[@name=\"btnK\"]");
    private final By luckyButtonLocator = By.xpath("//input[@name=\"btnI\"]");

    public GoogleSearchPage(WebDriver driver) {
        super(driver);
        if (!driver.getCurrentUrl().contains(BASE_URL)
                || (driver.findElements(searchFieldLocator).size() == 0)) {
            throw new IllegalStateException("This is not Google main page!");
        }
    }

    public GoogleResultPage searchFor(String textToSearch) {
        driver.findElement(searchFieldLocator).sendKeys(textToSearch);
        driver.findElement(searchFieldLocator).submit();
        return new GoogleResultPage(driver);
    }

    public GoogleSearchPage typeToSearchField(String textToType) {
        driver.findElement(searchFieldLocator).sendKeys(textToType);
        return this;
    }

    public GoogleResultPage pressSearchButton() throws IllegalStateException{
        if (!this.searchFieldIsEmpty()) {
            driver.findElement(searchButtonLocator).click();
            return new GoogleResultPage(driver);
        } else {
            throw new IllegalStateException("Ther's nothing to search!");
        }
    }

    public BasePageObject pressImLuckyButton() {
        driver.findElement(luckyButtonLocator).click();
        return new BasePageObject(driver);
    }

    public boolean searchFieldIsEmpty() {
        return driver.findElement(searchFieldLocator).getText().length() == 0;
    }
}
