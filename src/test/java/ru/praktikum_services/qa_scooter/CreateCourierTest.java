package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@Story("POST /api/v1/courier - создание курьера")
public class CreateCourierTest {

    private static CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @BeforeClass
    public static void setUp(){
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp(){
        assertThat(courierId, notNullValue());
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Возможность создать нового пользователя")
    public void createNewCourierWorks(){
        courier = CourierGenerator.getRandom();
        ValidatableResponse createResponse = courierClient.create(courier);

        int statusCode = createResponse.extract().statusCode();
        boolean isCourierCreated = createResponse.extract().path("ok");
        Assert.assertEquals(201, statusCode);
        Assert.assertEquals(true, isCourierCreated);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        assertThat(courierId, notNullValue());
    }

    @Test
    @DisplayName("Только обязательные поля")
    public void onlyWithRequiredParams(){
        courier = CourierGenerator.getRandomWithoutFirstName();
        ValidatableResponse createResponse = courierClient.create(courier);

        int statusCode = createResponse.extract().statusCode();
        boolean isCourierCreated = createResponse.extract().path("ok");
        Assert.assertEquals(201, statusCode);
        Assert.assertEquals(true, isCourierCreated);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        assertThat(courierId, notNullValue());

    }

    @Test
    @DisplayName("Невозможность создать двух одинаковых пользователей")
    public void sameCredentialsReturnError() {
        courier = CourierGenerator.getDefaultCredentials();
        courierClient.create(courier);
        ValidatableResponse createNewResponse = courierClient.create(courier);

        int errorStatusCode = createNewResponse.extract().statusCode();
        String detailMessage = createNewResponse.extract().path("message");
        Assert.assertEquals(409, errorStatusCode);
        Assert.assertEquals("Этот логин уже используется. Попробуйте другой.", detailMessage);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        assertThat(courierId, notNullValue());
    }

}
