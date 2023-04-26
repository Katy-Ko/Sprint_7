package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@Story("POST /api/v1/courier/login - логин курьера в системе")
public class UserLoginTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
    }

    @After
    public void cleanUp(){
        assertThat(courierId, notNullValue());
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Успешная авторизация с валидными данными")
    public void validCredentialsLetUserLogin(){
        ValidatableResponse createResponse = courierClient.create(courier);

        int statusCode = createResponse.extract().statusCode();
        boolean isCourierCreated = createResponse.extract().path("ok");
        Assert.assertEquals(201, statusCode);
        Assert.assertEquals(true, isCourierCreated);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        assertThat(courierId, notNullValue());

    }

}
