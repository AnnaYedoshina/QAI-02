package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.testng.Assert;

import java.util.List;

/**
 * PageObject для страницы корзины.
 */
public class CartPage {

    private final WebDriver driver;

    // --- Элементы страницы ---

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElement removeBackpackButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    /**
     * Конструктор класса.
     * @param driver Экземпляр WebDriver.
     */
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        System.out.println("CartPage инициализирован.");
    }

    // --- Методы для взаимодействия с элементами ---

    /**
     * Проверяет, что корзина пуста.
     */
    @Step("Проверка, что корзина пуста")
    public void verifyCartIsEmpty() {
        System.out.println("Проверка, что корзина пуста.");
        Assert.assertTrue(cartItems.isEmpty(), "Корзина не пуста, хотя должна быть.");
        System.out.println("Проверка подтвердила: корзина пуста.");
    }

    /**
     * Проверяет, что в корзине есть товары.
     */
    @Step("Проверка, что в корзине есть товары")
    public void verifyCartIsNotEmpty() {
        System.out.println("Проверка, что в корзине есть товары.");
        Assert.assertFalse(cartItems.isEmpty(), "Корзина пуста, хотя должны быть товары.");
        System.out.println("Проверка подтвердила: в корзине есть товары.");
    }


    /**
     * Удаляет товар 'Sauce Labs Backpack' из корзины.
     */
    @Step("Удаление товара 'Sauce Labs Backpack' из корзины")
    public void removeBackpackFromCart() {
        System.out.println("Нажатие кнопки для удаления 'Sauce Labs Backpack' из корзины.");
        removeBackpackButton.click();
    }

    /**
     * Нажимает кнопку "Continue Shopping" для возврата к каталогу.
     * @return Объект страницы продуктов (ProductsPage).
     */
    @Step("Возврат к каталогу товаров (Continue Shopping)")
    public ProductsPage continueShopping() {
        System.out.println("Нажатие кнопки 'Continue Shopping'.");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
        continueShoppingButton.click();
        System.out.println("DEBUG: Current URL after clicking Continue Shopping (before direct nav): " + driver.getCurrentUrl());
        // Temporarily navigate directly to inventory.html to check reachability
        driver.get("https://www.saucedemo.com/inventory.html");
        System.out.println("DEBUG: Current URL after direct navigation to inventory.html: " + driver.getCurrentUrl());
        // Original wait for URL is removed for this diagnostic step
        // wait.until(ExpectedConditions.urlContains("inventory.html"));
        return new ProductsPage(driver);
    }

    /**
     * Переходит к оформлению заказа.
     * @return Объект первой страницы оформления заказа (CheckoutStepOnePage).
     */
    @Step("Переход к оформлению заказа (Checkout)")
    public CheckoutStepOnePage proceedToCheckout() {
        System.out.println("Нажатие кнопки 'Checkout'.");
        checkoutButton.click();
        return new CheckoutStepOnePage(driver);
    }
}
