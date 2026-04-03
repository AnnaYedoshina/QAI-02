package com.saucedemo.tests; // assuming it's in the same package as BaseTest

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Тесты для полного цикла покупки товара на сайте SauceDemo.
 */
public class FullE2EScenarioTest extends BaseTest { // Assuming it extends BaseTest

    @Test
    @Story("Полный цикл покупки товара")
    @TmsLink("TC-001")
    @Description("Тест проверяет полный цикл от входа в систему до успешного оформления заказа.")
    public void fullE2EScenarioTest() {
        // --- Шаг 1: Логин ---
        // Инициализация страницы входа
        LoginPage loginPage = new LoginPage(driver);
        // Выполнение входа и переход на страницу продуктов
        ProductsPage productsPage = loginPage.login("standard_user", "secret_sauce");
        // Проверка, что логин прошел успешно и мы на странице продуктов
        Assert.assertTrue(productsPage.verifyPageLoaded(), "Страница продуктов не загрузилась или заголовок неверный.");
        Assert.assertEquals(productsPage.getPageTitle(), "Products", "Заголовок страницы продуктов не соответствует ожидаемому.");

        // --- Шаг 2: Добавление товара в корзину ---
        productsPage.addBackpackToCart();
        // Переход в корзину
        CartPage cartPage = productsPage.goToCart();
        // Проверка, что товар в корзине
        cartPage.verifyCartIsNotEmpty();

        // --- Шаг 3: Удаление товара из корзины ---
        cartPage.removeBackpackFromCart();
        // Проверка, что корзина стала пустой
        cartPage.verifyCartIsEmpty();

        // --- Шаг 4: Возврат к каталогу ---
        productsPage = cartPage.continueShopping();
        // Проверка, что мы вернулись на страницу продуктов
        Assert.assertTrue(productsPage.verifyPageLoaded(), "Страница продуктов не загрузилась или заголовок неверный.");

        // --- Шаг 5: Добавление другого товара в корзину ---
        productsPage.addBikeLightToCart();
        // Переход в корзину
        cartPage = productsPage.goToCart();
        // Проверка, что товар в корзине
        cartPage.verifyCartIsNotEmpty();

        // --- Шаг 6: Переход к чекауту ---
        CheckoutStepOnePage checkoutStepOnePage = cartPage.proceedToCheckout();

        // --- Шаг 7: Заполнение формы чекаута ---
        CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.fillUserInfo("John", "Doe", "12345");
        // Проверка, что перешли на страницу подтверждения
        checkoutStepTwoPage.verifyPageLoaded();

        // --- Шаг 8: Подтверждение и завершение заказа ---
        CheckoutCompletePage checkoutCompletePage = checkoutStepTwoPage.clickFinish();
        // Проверка, что перешли на финальную страницу
        checkoutCompletePage.verifyPageLoaded();

        // --- Шаг 9: Проверка успешного завершения заказа ---
        String confirmationMessage = checkoutCompletePage.getCompleteHeaderText();
        Assert.assertEquals(confirmationMessage, "Thank you for your order!", "Сообщение о подтверждении заказа неверное.");
    }
}