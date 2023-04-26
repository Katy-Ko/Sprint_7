package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@Story("POST /api/v1/courier - создание курьера")
@RunWith(Parameterized.class)
public class CreateCourierWithoutParamsTest {
    private static CourierClient courierClient;
    private final Courier courier;
    private final int expectedStatusCode;
    private final String expectedMessage;

    public CreateCourierWithoutParamsTest(Courier courier, int expectedStatusCode, String expectedMessage) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getCourierDetails() {
        return new Object[][] {
                {new Courier(null, "123", "Joe Black"), 400, "Недостаточно данных для создания учетной записи"},
                {new Courier("Foo-foo", null, "Joe Black"), 400, "Недостаточно данных для создания учетной записи"},
        };
    }

    @BeforeClass
    public static void setUp(){
        courierClient = new CourierClient();
    }

    @Test
    public void createCourierWithoutRequiredParamsReturnsError(){

        ValidatableResponse createResponse = courierClient.create(courier);

        int statusCode = createResponse.extract().statusCode();
        String detailMessage = createResponse.extract().path("message");
        Assert.assertEquals(expectedStatusCode, statusCode);
        Assert.assertEquals(expectedMessage, detailMessage);

    }

}
