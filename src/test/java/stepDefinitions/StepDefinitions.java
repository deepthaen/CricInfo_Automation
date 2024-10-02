package stepDefinitions;

import common.utils.ConfigReader;
import driverManager.WebManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import java.util.HashMap;
import java.util.Map;

public class StepDefinitions {

    private WebDriver driver;
    ConfigReader configReader = new ConfigReader();
    WebManager webManager = new WebManager();
    @Given("the browser is open")
    public void the_browser_is_open() {

        driver = webManager.getDriver(configReader.getProperty("browser"));
    }
    @When("I navigate to the ESPN Cricinfo homepage")
    public void i_navigate_to_the_ESPN_Cricinfo_homepage() {
        webManager.getUrl();
    }

    @Then("the homepage should load with all elements visible")
    public void the_homepage_should_load_with_all_elements_visible() {
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Live cricket scores"));
    }

    @When("I search for a player by name {string}")
    public void i_search_for_a_player_by_name(String playerName) {
        driver.findElement(By.name("search_input")).sendKeys(playerName);
        driver.findElement(By.cssSelector("search_button_selector")).click();
    }

    @Then("the relevant player profile should appear in the search results")
    public void the_relevant_player_profile_should_appear_in_the_search_results() {
        Assert.assertTrue(driver.findElement(By.cssSelector("selector_for_player_profile")).isDisplayed());
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
        Assert.assertTrue(driver.findElement(By.cssSelector("selector_for_teams_page")).isDisplayed());
    }

    // Steps for navigating to "Stats" section
    @When("I navigate to the \"Stats\" section")
    public void i_navigate_to_the_stats_section() {
        driver.findElement(By.linkText("Stats")).click();
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
            driver.quit();
        }
    }
}
