package stepdefs;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.BagPage;
import pageobjects.ProductDisplayPage;
import stepdefs.hooks.Hooks;
import org.openqa.selenium.By;
import static org.assertj.core.api.Assertions.assertThat;

public class BagStepDefs {
    private final WebDriver driver;
    private BagPage bagPage;
    private Long productId = 39654522814667L; // Sample product ID

    public BagStepDefs() {
        this.driver = Hooks.getDriver();
        this.bagPage = new BagPage();
    }

    @Given("there are products in the Bag")
    public void thereAreProductsInTheBag() {
        driver.get("https://uk.gymshark.com/products/gymshark-speed-t-shirt-black-aw23");

        ProductDisplayPage productPage = new ProductDisplayPage();
        productPage.selectSmallSize().selectAddToBag();

        // there IS a product in the bag
        assertThat(bagPage.getVariantIdsInBag()).isNotEmpty();
    }


    @When("I remove a product")
    public void iRemoveAProduct() {
        // Code to remove the product from the Bag
        bagPage.removeProduct(productId);
    }

    @Then("the product is removed from the Bag")
    public void theProductIsRemovedFromTheBag() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[data-locator-id^='miniBag-miniBagItem']")));
    }

    @When("I add quantity")
    public void iAddQuantity() {
        bagPage.changeProductQuantity(productId, 5);
    }

    @Then("the product quantity is increased")
    public void theProductQuantityIsIncreased() {
        // Validate that the quantity is increased
        int newQuantity = bagPage.getProductQuantity(productId);
        assertThat(newQuantity).isEqualTo(5);
    }

    @When("I remove quantity")
    public void iRemoveQuantity() {
        bagPage.changeProductQuantity(productId, 2);
    }

    @Then("the product quantity is reduced")
    public void theProductQuantityIsReduced() {
        // Validate that the quantity is reduced
        int newQuantity = bagPage.getProductQuantity(productId);
        assertThat(newQuantity).isEqualTo(2);
    }
}
