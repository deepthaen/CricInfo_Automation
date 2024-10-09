package stepDefinitions;

import common.utils.ConfigReader;
import driverManager.WebManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepDefinitions {

    private static final Logger logger = LogManager.getLogger(StepDefinitions.class);
    private WebDriver driver;
    ConfigReader configReader = new ConfigReader();
    WebManager webManager = new WebManager();
    @Given("the browser is open")
    public void the_browser_is_open() {
        driver = webManager.getDriver(configReader.getProperty("browser"));
    driver.get(webManager.getUrl());
    }
    @When("I navigate to the ESPN Cricinfo homepage")
    public void i_navigate_to_the_ESPN_Cricinfo_homepage() {
        webManager.getUrl();
    }



    @Then("the homepage should load with all elements visible")
    public void the_homepage_should_load_with_all_elements_visible() {
        String pageTitle = driver.getTitle();
        System.out.println("Page title  :: "+pageTitle);
        Assert.assertTrue(pageTitle.contains("ESPNcricinfo"));
    }

    @When("I search for a player by name {string}")
    public void i_search_for_a_player_by_name(String playerName) {
        WebElement searchpath = driver.findElement(By.xpath("//i[contains(@class, 'icon-search-outlined')]"));
        searchpath.click();
        driver.findElement(By.xpath("//input[@placeholder='Search Players, Teams or Series']")).sendKeys(playerName);
        driver.findElement(By.xpath("//*[contains(@class, 'icon-arrow_forward-filled')]")).click();
    }

    @Then("the relevant player profile should appear in the search results")
    public void the_relevant_player_profile_should_appear_in_the_search_results() {
        WebElement results = driver.findElement(By.xpath("//h2[text()='Results in players (1)']"));
        Assert.assertTrue(results.isDisplayed());
        if(results.isDisplayed()){
            logger.info("Player name :: "+ driver.findElement(By.xpath("//*[@class='alphabetical-name']")).getText());
        }
    }

    @When("the user logs in with username {string} and password {string}")
    public void the_user_logs_in_with_username_and_password(String username, String password) {
        driver.findElement(By.id("login_button")).click();
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("submit_login")).click();
    }

    @Then("the user should be logged in successfully")
    public void the_user_should_be_logged_in_successfully() {
        Assert.assertTrue(driver.findElement(By.cssSelector("logged_in_element")).isDisplayed());
    }

    @When("I navigate to the \"Teams\" section")
    public void i_navigate_to_the_teams_section() {
        driver.findElement(By.linkText("Teams")).click();
    }

    @Then("the \"Teams\" page should load successfully with team details")
    public void the_teams_page_should_load_successfully_with_team_details() {
        By by = By.xpath("//h1[text()='Cricket Teams']");
        webManager.waitUntil(driver,by);
        WebElement teams = driver.findElement(by);
        Assert.assertTrue(teams.isDisplayed());
        logger.info("Teams header :: "+ teams.getText());

        List<WebElement> hrefs = driver.findElements(By.xpath("//*[@href='/teams']"));

        for (int i = 1; i <hrefs.size(); i++) {
            logger.info("Internation team name header :: "+hrefs.get(i).getText());
            List<WebElement> teamslist = driver.findElements(By.xpath("(//*[@href='/teams'])["+i+"]/../../../div[2]/div/a"));
            for (int j = 1; j <=teamslist.size(); j++) {
                By team = By.xpath("(//*[@href='/teams'])["+i+"]/../../../div[2]/div/a["+j+"]/div/span");
                String teamName  = driver.findElement(team).getText();
                logger.info("Team name :: "+teamName);
            }

            logger.info("***************************");

        }

   
    }

    // Steps for navigating to "Stats" section
    @When("I navigate to the {string} section")
    public void i_navigate_to_the_stats_section(String score) {
        driver.findElement(By.xpath("//*[@title='"+score+"']")).click();
    }
    @Then("the live scores should be visible and updated in real-time")
    public void i_navigate_to_liceScore() {
        webManager.waitUntil(driver,By.xpath("//*[text()='Live Cricket Matches']"));
        WebElement matches = driver.findElement(By.xpath("//*[text()='Live Cricket Matches']"));
        Assert.assertTrue(matches.isDisplayed());
        logger.info("Live matches are visible");
        List<WebElement> elements = driver.findElements(By.xpath("(//*[text()='RESULT'])"));
        logger.info("Total Live Matches :: "+elements.size());
        for (int i = 1; i <=elements.size(); i++) {
            List<WebElement> teams = driver.findElements(
                    By.xpath("(//*[text()='RESULT'])["+i+"]/../../../../div[2]/div/div"));
            for (int j = 1; j <=teams.size(); j++) {
                String teamname = driver.findElement(
                        By.xpath("(//*[text()='RESULT'])["+i+"]/../../../../div[2]/div/div["+j+"]/div[1]")).getText();
                String score = driver.findElement(
                        By.xpath("(//*[text()='RESULT'])["+i+"]/../../../../div[2]/div/div["+j+"]/div[2]")).getText();
                logger.info("Team name : "+teamname+"  score : "+score);
            }

        }
    }



    @Then("the player/team statistics should be displayed accurately")
    public void the_player_team_statistics_should_be_displayed_accurately() {
        Assert.assertTrue(driver.findElement(By.cssSelector("selector_for_stats_page")).isDisplayed());
    }

    // Steps for verifying the website's mobile responsiveness
    @Given("the website is opened on a mobile device or emulator")
    public void the_website_is_opened_on_a_mobile_device_or_emulator() {
        // This can be handled using Chrome DevTools options in Selenium for mobile emulation
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String,String> map = new HashMap<>();
        map.put("deviceName", "iPhone X");
        chromeOptions.setExperimentalOption("mobileEmulation", map);
        driver = new ChromeDriver(chromeOptions);
    }

    @When("I check the layout and responsiveness")
    public void i_check_the_layout_and_responsiveness() {
        // Verifying that elements are responsive and visible on the mobile layout
        Assert.assertTrue(driver.findElement(By.cssSelector("mobile_responsive_selector")).isDisplayed());
    }

    // Steps for verifying advertisement display
    @When("I verify the advertisement display")
    public void i_verify_the_advertisement_display() {
        // Locate and verify the presence of advertisements
        Assert.assertTrue(driver.findElement(By.cssSelector("ads_selector")).isDisplayed());
    }

    @Then("the ads should be displayed correctly without obstructing content")
    public void the_ads_should_be_displayed_correctly_without_obstructing_content() {
        // Further validation to ensure ads are not obstructing any content
        WebElement ads = driver.findElement(By.cssSelector("ads_selector"));
        Assert.assertTrue(ads.isDisplayed());
        Assert.assertFalse(ads.getCssValue("z-index").equals("obstructing_content"));
    }



    @When("100+ users visit the website")
    public void simulate_heavy_load() {
        for (int i = 0; i < 100; i++) {
            WebDriver userDriver = webManager.getDriver(configReader.getProperty("browser"));
            webManager.getUrl();
            userDriver.quit();
        }
    }

    @Then("the website should respond within acceptable limits under heavy load")
    public void the_website_should_respond_within_acceptable_limits_under_heavy_load() {
        // Verifying that the page loads within an acceptable time frame
        long startTime = System.currentTimeMillis();
        driver.get("https://www.espncricinfo.com");
        long loadTime = System.currentTimeMillis() - startTime;
        Assert.assertTrue("Page took too long to load", loadTime < 5000); // e.g., page should load in under 5 seconds
    }

    // Steps for navigating to match information page
    @When("I navigate to the match information page")
    public void i_navigate_to_the_match_information_page() {
        driver.findElement(By.linkText("Match Info")).click();
    }

    @Then("the match details should be accurate and displayed without issues")
    public void the_match_details_should_be_accurate_and_displayed_without_issues() {
        // Verifying that match details are displayed correctly
        Assert.assertTrue(driver.findElement(By.cssSelector("match_info_selector")).isDisplayed());
    }

    // General cleanup step
    @Then("I close the browser")
    public void i_close_the_browser() {
        if (driver != null) {
            driver.close();
        }
    }

    public  WebDriver getDriver() {
        return driver;
    }



}
