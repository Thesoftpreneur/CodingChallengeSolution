package com.softprenuer.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class IndexPage {

    WebDriver driver;

    @FindBy(id="match-case")
    WebElement matchCaseCheckbox;
    @FindBy(id="search-input")
    WebElement searchInput;
    @FindBy(id="search-column")
    WebElement searchDropdown;
    @FindBy(id="table-resume")
    WebElement tableResume;
    @FindBy(xpath = "//table[@class=\"table table-hover\"]")
    WebElement table;

    public IndexPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectCheckbox() {
        matchCaseCheckbox.click();
    }

    public void inputTextIntoTheSearchbox(String text) {
        searchInput.sendKeys(text);
    }

    public void chooseFilterColumn(String value) {
        Select dropdown = new Select(searchDropdown);
        dropdown.selectByValue(value);
    }

    public Set<String> getFilteredNamesFromTable() {
        return driver.findElements(By.xpath("//table[@class=\"table table-hover\"]/tbody/tr/td[2]")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toSet());
    }

    public Set<String> getFilteredCitiesFromTable() {
        return driver.findElements(By.xpath("//table[@class=\"table table-hover\"]/tbody/tr/td[4]")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toSet());
    }

    public String getNumberOfResults() {

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(tableResume.getText());
        if(matcher.find()) {
            return matcher.group();
        }else{
            return null;
        }

    }
}
