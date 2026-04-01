package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * PageObject для первого шага оформления заказа (ввод данных пользователя).
 */
public class CheckoutStepOnePage {

    private final WebDriver driver;

    // --- Элементы страницы ---

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement zipCodeInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    /**
     * Конструктор класса.
     * @param driver Экземпляр WebDriver.
     */
    public CheckoutStepOnePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        System.out.println("CheckoutStepOnePage инициализирован.");
    }

    // --- Методы для взаимодействия с элементами ---

    /**
     * Вводит имя.
     * @param firstName Имя.
     */
    @Step("Ввод имени: {firstName}")
    public void enterFirstName(String firstName) {
        System.out.printf("Ввод имени: '%s'%n", firstName);
        firstNameInput.sendKeys(firstName);
    }

    /**
     * Вводит фамилию.
     * @param lastName Фамилия.
     */
    @Step("Ввод фамилии: {lastName}")
    public void enterLastName(String lastName) {
        System.out.printf("Ввод фамилии: '%s'%n", lastName);
        lastNameInput.sendKeys(lastName);
    }

    /**
     * Вводит почтовый индекс.
     * @param zipCode Почтовый индекс.
     */
    @Step("Ввод почтового индекса: {zipCode}")
    public void enterZipCode(String zipCode) {
        System.out.printf("Ввод почтового индекса: '%s'%n", zipCode);
        zipCodeInput.sendKeys(zipCode);
    }

    /**
     * Нажимает кнопку 'Continue'.
     * @return Объект второй страницы оформления заказа (CheckoutStepTwoPage).
     */
    @Step("Нажатие кнопки 'Continue'")
    public CheckoutStepTwoPage clickContinue() {
        System.out.println("Нажатие на кнопку 'Continue'.");
        continueButton.click();
        return new CheckoutStepTwoPage(driver);
    }

    /**
     * Комплексный метод для заполнения формы и перехода к следующему шагу.
     * @param firstName Имя.
     * @param lastName Фамилия.
     * @param zipCode Почтовый индекс.
     * @return Объект второй страницы оформления заказа (CheckoutStepTwoPage).
     */
    @Step("Заполнение информации о пользователе и переход далее")
    public CheckoutStepTwoPage fillUserInfo(String firstName, String lastName, String zipCode) {
        System.out.println("Начало заполнения информации о пользователе.");
        enterFirstName(firstName);
        enterLastName(lastName);
        enterZipCode(zipCode);
        System.out.println("Информация о пользователе заполнена.");
        return clickContinue();
    }
}
