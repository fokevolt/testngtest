package pages;

import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Bondariev
 */

public class GoogleResultPage extends BasePageObject {
    private final By searchFilterSelector = By.id("hdtb-msb");
    private final By searchResultSelector = By.xpath("//h3[@class = \"LC20lb\"]");
    private final By goToSearchPageSelector = By.xpath("//img[@alt=\"Google\"]");

    private List<SearchResult> searchResults = new ArrayList<>();

    public GoogleResultPage(WebDriver driver) {
        super(driver);
        if (driver.findElements(searchFilterSelector).size() == 0) {
            throw new IllegalStateException("This is not Google search page!");
        }

        for (WebElement element : driver.findElements(searchResultSelector)) {
            searchResults.add(new SearchResult(element, searchResults.size()));
        }
    }

    public SearchResult getSearchResultByNumber(int resultNumber) {
        if (searchResults.size() < resultNumber) {
            throw new IllegalArgumentException("Actual count of results is less than choosen number!");
        }
        return searchResults.get(resultNumber - 1);
    }

    public GoogleSearchPage goToSearchPage() {
        driver.findElement(goToSearchPageSelector).click();
        return new GoogleSearchPage(driver);
    }

    public class SearchResult implements WebElement {

        private final int number;
        private final WebElement element;
        private final String descriptionUrl;
        private final String descriptionText;

        public SearchResult(WebElement element, int number) {
            this.number = number + 1;
            this.element = element;
            descriptionUrl = element.findElements(By.xpath("//cite")).get(number).getText();
            descriptionText = element.findElements(By.xpath("//span[@class=\"st\"]")).get(number).getText();
        }

        public int getNumber() {
            return number;
        }

        public String getUrlFromDescription() {
            return descriptionUrl.replace("...", "");
        }

        public String getTextFromDescription() {
            return descriptionText;
        }

        @Override
        public void click() {
            element.click();
        }

        @Override
        public void submit() {
            element.submit();
        }

        @Override
        public void sendKeys(CharSequence... charSequences) {
            element.sendKeys(charSequences);
        }

        @Override
        public void clear() {
            element.clear();
        }

        @Override
        public String getTagName() {
            return element.getTagName();
        }

        @Override
        public String getAttribute(String s) {
            return element.getAttribute(s);
        }

        @Override
        public boolean isSelected() {
            return element.isSelected();
        }

        @Override
        public boolean isEnabled() {
            return element.isEnabled();
        }

        @Override
        public String getText() {
            return element.getText();
        }

        @Override
        public List<WebElement> findElements(By by) {
            return element.findElements(by);
        }

        @Override
        public WebElement findElement(By by) {
            return element.findElement(by);
        }

        @Override
        public boolean isDisplayed() {
            return element.isDisplayed();
        }

        @Override
        public Point getLocation() {
            return element.getLocation();
        }

        @Override
        public Dimension getSize() {
            return element.getSize();
        }

        @Override
        public Rectangle getRect() {
            return element.getRect();
        }

        @Override
        public String getCssValue(String s) {
            return element.getCssValue(s);
        }

        @Override
        public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
            return element.getScreenshotAs(outputType);
        }
    }
}
