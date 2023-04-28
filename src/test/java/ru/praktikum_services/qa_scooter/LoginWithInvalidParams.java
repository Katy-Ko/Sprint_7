package ru.praktikum_services.qa_scooter;

import io.restassured.response.ValidatableResponse;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class LoginWithInvalidParams {

    private CourierClient courierClient;
    private Courier courier;
    private final CourierCredentials credentials;
    private int courierId;
    private final int expectedStatusCode;
    private final String expectedMessage;

    public LoginWithInvalidParams(CourierCredentials credentials, int expectedStatusCode, String expectedMessage) {
        this.credentials = credentials;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedMessage = expectedMessage;

    }

    @Parameterized.Parameters
    public static Object[][] getCourierDetails() {
        return new Object[][] {
                {new CourierCredentials("", "aaa"), 400, "Недостаточно данных для входа"},
                {new CourierCredentials("Foo-foo", ""), 400, "Недостаточно данных для входа"},
                {new CourierCredentials("blah-blah", "777777"), 404, "Учетная запись не найдена"},
                {new CourierCredentials("Foo-Foq", "aaa"), 404, "Учетная запись не найдена"},
                {new CourierCredentials("Foo-foo", "ooo"), 404, "Учетная запись не найдена"},
        };
    }

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = CourierGenerator.getDefaultCredentials();

        ValidatableResponse createResponse = courierClient.create(courier);
        int statusCode = createResponse.extract().statusCode();
        boolean isCourierCreated = createResponse.extract().path("ok");
        Assert.assertEquals(201, statusCode);
        Assert.assertTrue(isCourierCreated);
    }

    @Test
    public void loginWithInvalidParamsReturnsError(){
        ValidatableResponse loginResponse = courierClient.login(credentials);
        int statusCode = loginResponse.extract().statusCode();
        String detailMessage = loginResponse.extract().path("message");
        Assert.assertEquals(expectedStatusCode, statusCode);
        Assert.assertEquals(expectedMessage, detailMessage);
    }

    @After
    public void cleanUp(){
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        assertThat(courierId, notNullValue());
        assertThat(courierId, notNullValue());
        courierClient.delete(courierId);
    }
}
