package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@Story("GET /api/v1/orders - получение списка заказов")
public class OrderListTest {

    private OrderClient orderClient;

    @Before
    public void setUp(){
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Успешный запрос возвращает список заказов")
    public void bodyContainsListOfOrders(){
        ValidatableResponse orderListResponse = orderClient.getOrderList();

        int statusCode = orderListResponse.extract().statusCode();
        Object orderList = orderListResponse.extract().path("orders");
        Assert.assertEquals(200, statusCode);
        assertThat(orderList, notNullValue());

    }

}
