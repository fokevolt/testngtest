package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BasePageObject;
import pages.GoogleResultPage;
import pages.GoogleResultPage.SearchResult;
import pages.GoogleSearchPage;

import java.util.concurrent.TimeUnit;

/**
 * @author Vitalii Bondariev
 */

public class GoogleSearchTest {
    public WebDriver driver;
    private GoogleSearchPage searchPage;
    private GoogleResultPage resultsPage;

    @BeforeClass
    public void setupDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
    }

    @BeforeMethod
    public void goToBaseUrl() {
        driver.get(GoogleSearchPage.BASE_URL);
        searchPage = new GoogleSearchPage(driver);
    }

    @Test
    public void assureThatItIsPossibleToSearchAndViewResultsInGoogle() {
        //Test data
        String textToSearch = "selenium + testng";
        int resultToOpen = 5;

        SearchResult result = searchPage.searchFor(textToSearch).getSearchResultByNumber(resultToOpen);
        result.click();
        Assert.assertTrue(driver.getCurrentUrl().contains(result.getUrlFromDescription()));
        Assert.assertEquals(resultToOpen, result.getNumber());
    }

    @Test
    public void assureThatIfWeClickImLuckyButtonWeWillGoDirectlyToSite() {
        BasePageObject somePage = searchPage.typeToSearchField("selenium webdriver").pressImLuckyButton();
        Assert.assertFalse(somePage.getUrl().contains(GoogleSearchPage.BASE_URL));
    }

    @Test
    public void assureThatIfWeGetBackToSearchPageThenSearchFieldIsEmpty() {
        resultsPage = searchPage.searchFor("Global Logic");
        searchPage = resultsPage.goToSearchPage();
        Assert.assertTrue(searchPage.getUrl().contains(GoogleSearchPage.BASE_URL));
        Assert.assertTrue(searchPage.searchFieldIsEmpty());
    }

    @AfterClass
    public void tearDown() {
        driver.close();
    }
}
