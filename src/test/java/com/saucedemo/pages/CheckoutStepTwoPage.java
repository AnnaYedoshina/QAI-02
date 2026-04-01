package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

/**
 * PageObject для второго шага оформления заказа (просмотр и подтверждение).
 */
public class CheckoutStepTwoPage {

    private final WebDriver driver;

    // --- Элементы страницы ---

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "finish")
    private WebElement finishButton;

    /**
     * Конструктор класса.
     * @param driver Экземпляр WebDriver.
     */
    public CheckoutStepTwoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        System.out.println("CheckoutStepTwoPage инициализирован.");
    }

    /**
     * Проверяет, что страница 'Checkout: Overview' успешно загружена.
     */
    @Step("Проверка успешной загрузки страницы 'Checkout: Overview'")
    public void verifyPageLoaded() {
        System.out.println("Проверка, что страница 'Checkout: Overview' загружена.");
        Assert.assertTrue(pageTitle.isDisplayed() && "Checkout: Overview".equals(pageTitle.getText()),
                "Страница 'Checkout: Overview' не загрузилась или заголовок неверный.");
        System.out.println("Страница 'Checkout: Overview' успешно загружена.");
    }


    /**
     * Нажимает кнопку 'Finish' для завершения заказа.
     * @return Объект страницы завершения заказа (CheckoutCompletePage).
     */
    @Step("Нажатие кнопки 'Finish' для завершения заказа")
    public CheckoutCompletePage clickFinish() {
        System.out.println("Нажатие на кнопку 'Finish'.");
        finishButton.click();
        return new CheckoutCompletePage(driver);
    }
}
