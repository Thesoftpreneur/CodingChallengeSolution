package com.softprenuer.stepdefinitions;

import com.softprenuer.pages.IndexPage;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.Paths;
import java.util.Locale;

import static org.junit.Assert.*;

public class StepDefinitions {

    private static WebDriver driver;
    public static IndexPage indexPage;

    @Given("an open browser")
    public void an_open_browser() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @When("user navigates to the main page")
    public void user_navigates_to_the_main_page() {
        String path = Paths.get("src","main", "resources", "app").toAbsolutePath().toString();
        driver.navigate().to(String.valueOf(Paths.get(path, "index.html")));
        indexPage = new IndexPage(driver);
    }

    @When("user selects the main match-case checkbox")
    public void user_selects_the_main_match_case_checkbox() {
        indexPage.selectCheckbox();
    }

    @And("user inputs {string} into the searchbox")
    public void userInputsIntoTheSearchbox(String text) {
        indexPage.inputTextIntoTheSearchbox(text);
    }

    @And("user chooses {string} filter option")
    public void userChoosesFilterOption(String value) {
        indexPage.chooseFilterColumn(value);
    }


    @Then("only filtered names with {string} are shown regardless of case")
    public void onlyFilteredNamesWithAreShownRegardlessOfCase(String searchPhrase) {
        Assert.assertTrue(indexPage.getFilteredNamesFromTable().stream()
                .allMatch(fullname->fullname.toLowerCase().contains(searchPhrase.toLowerCase())));
    }

    @Then("only filtered cities with {string} are shown regardless of case")
    public void onlyFilteredCitiesWithAreShownRegardlessOfCase(String searchPhrase) {
        Assert.assertTrue(indexPage.getFilteredCitiesFromTable().stream()
                .allMatch(fullname->fullname.toLowerCase().contains(searchPhrase.toLowerCase())));
    }

    @Then("only filtered names with {string} are shown with case sensitivity")
    public void onlyFilteredNamesWithAreShownWithCaseSensitivity(String searchPhrase) {
        Assert.assertTrue(indexPage.getFilteredNamesFromTable().stream()
                .allMatch(fullname->fullname.contains(searchPhrase)));
    }

    @Then("the number of occurences should be equal {int}")
    public void theNumberOfOccurencesShouldBeEqual(int occurences) {

    }

    @Then("the number of results is {string}")
    public void theNumberOfResultsIs(String occurences) {
        Assert.assertEquals(occurences, indexPage.getNumberOfResults());
    }

    @After(value="@Selenium")
    public void cleanUp() {
        driver.quit();
    }

}
