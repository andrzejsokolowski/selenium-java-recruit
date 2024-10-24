package pageobjects;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;



import static org.assertj.core.api.Assertions.assertThat;
import static utils.SeleniumCommands.getCommands;
import static utils.StringUtils.extractVariantIDFromString;

public class BagPage {

  private static final By BAG_PAGE = By.cssSelector("[data-locator-id='miniBag-component']");
  private static final By BAG_ITEMS = By.cssSelector("[data-locator-id^='miniBag-miniBagItem']");
  public static final String GS_LOCATOR_ATTRIBUTE = "data-locator-id";

  public BagPage() {
    getCommands().waitForAndGetVisibleElementLocated(BAG_PAGE);
  }

  public List<Long> getVariantIdsInBag() {
    return getBagItems().stream()
      .map(this::getVariantId)
      .collect(Collectors.toList());
  }

  private List<WebElement> getBagItems() {
    return getCommands().waitForAndGetAllVisibleElementsLocated(BAG_ITEMS);
  }

  private long getVariantId(WebElement bagItem) {
    return extractVariantIDFromString(getCommands().getAttributeFromElement(bagItem, GS_LOCATOR_ATTRIBUTE));
  }

  public void removeProduct(Long productId) {
    // Assert that there is a product in the Bag initially
    assertThat(this.getVariantIdsInBag()).isNotEmpty();

    // Convert the productId to a String
    String productIdString = productId.toString();
    By removeButton = By.cssSelector("[data-locator-id='miniBag-remove-" + productIdString + "-remove']");

    // Click the remove button
    getCommands().waitForAndClickOnElementLocated(removeButton);
  }



  public void changeProductQuantity(Long productId, int quantity) {
    By amountDropdown = By.cssSelector("[class='qty-selector_dropdown__R7OIE']");
    By quantityButton = By.cssSelector("option[value='" + quantity + "']");
    getCommands().waitForAndClickOnElementLocated(amountDropdown);
    getCommands().waitForAndClickOnElementLocated(quantityButton);
    try {
      //wait for the load
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // Restore the interrupted status
      throw new RuntimeException(e);
    }
  }


  public int getProductQuantity(Long productId) {
    By quantityElement = By.cssSelector("[class^='qty-selector_text']");
    String quantityText = getCommands().waitForAndGetVisibleElementLocated(quantityElement).getText();

    // Extract the number
    String quantityNumber = quantityText.split(": ")[1].trim(); // Split by ": " and get the second part

    // Parse the extracted number as an integer
    return Integer.parseInt(quantityNumber);
  }


}
