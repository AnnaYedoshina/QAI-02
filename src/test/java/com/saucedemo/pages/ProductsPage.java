package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.testng.Assert;

/**
 * PageObject для страницы каталога продуктов.
 */
public class ProductsPage {

    private final WebDriver driver;

    // --- Элементы страницы ---

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "shopping_cart_container")
    private WebElement cartIcon;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addToCartBackpackButton;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    private WebElement addToCartBikeLightButton;

    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElement removeCartBackpackButton;

    /**
     * Конструктор класса.
     * @param driver Экземпляр WebDriver.
     */
    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.and(
            ExpectedConditions.visibilityOf(pageTitle),
            ExpectedConditions.textToBePresentInElement(pageTitle, "Products")
        ));
        System.out.println("ProductsPage инициализирован.");
    }

    // --- Методы для взаимодействия с элементами ---

    /**
     * Получает заголовок страницы.
     * @return Строка с заголовком страницы.
     */
    @Step("Получение заголовка страницы продуктов")
    public String getPageTitle() {
        String title = pageTitle.getText();
        System.out.printf("Заголовок страницы продуктов: '%s'%n", title);
        return title;
    }

    /**
     * Проверяет, что страница продуктов успешно загружена.
     */
    @Step("Проверка успешной загрузки страницы продуктов")
    public boolean verifyPageLoaded() {
        System.out.println("Проверка, что страница продуктов загружена.");
        boolean isLoaded = pageTitle.isDisplayed() && "Products".equals(pageTitle.getText());
        if (isLoaded) {
            System.out.println("Страница продуктов успешно загружена.");
        } else {
            System.out.println("Страница продуктов НЕ загружена или заголовок неверный.");
        }
        return isLoaded;
    }

    /**
     * Добавляет товар 'Sauce Labs Backpack' в корзину.
     */
    @Step("Добавление товара 'Sauce Labs Backpack' в корзину")
    public void addBackpackToCart() {
        System.out.println("Добавление 'Sauce Labs Backpack' в корзину.");
        addToCartBackpackButton.click();
    }

    /**
     * Добавляет товар 'Sauce Labs Bike Light' в корзину.
     */
    @Step("Добавление товара 'Sauce Labs Bike Light' в корзину")
    public void addBikeLightToCart() {
        System.out.println("Добавление 'Sauce Labs Bike Light' в корзину.");
        addToCartBikeLightButton.click();
    }

    /**
     * Удаляет товар 'Sauce Labs Backpack' из корзины.
     */
    @Step("Удаление товара 'Sauce Labs Backpack' из корзины")
    public void removeBackpackFromCart() {
        System.out.println("Удаление 'Sauce Labs Backpack' из корзины.");
        removeCartBackpackButton.click();
    }

    /**
     * Переходит на страницу корзины.
     * @return Объект страницы корзины (CartPage).
     */
    @Step("Переход в корзину")
    public CartPage goToCart() {
        System.out.println("Нажатие на иконку корзины.");
        cartIcon.click();
        return new CartPage(driver);
    }
}
