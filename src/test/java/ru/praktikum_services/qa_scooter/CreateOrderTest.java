package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@Story("POST /api/v1/orders - создание заказа")
@RunWith(Parameterized.class)
public class CreateOrderTest {

    private static OrderClient orderClient;
    private final Order order;

    public CreateOrderTest (Order order){
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderDetails() {
        return new Object[][] {
                {new Order("Евгений","Пуговкин","ул. Ватутина, 25","Черкизовская","+79111111111",3,"2023-05-19","в 18:00 у подъезда 3",new String[]{"GREY"})},
                {new Order("Лосось","Радужный","Победы, 7","Первомайская","88004005050",4,"2023-05-20","звонить за 30 минут",new String[]{"BLACK"})},
                {new Order("Александра","Суворова","ул. Пушкина, 1","Красные Ворота","+79215556363",6,"2023-05-21","нужны оба",new String[]{"GREY","BLACK"})},
                {new Order("Софья","--","Красная Площадь","Охотный ряд","+79226589654",1,"2023-05-23","цвет значения не имеет",new String[]{})},
        };
    }

    @BeforeClass
    public static void setUp(){
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Успешное создание заказа")
    public void createOrderWithValidParams(){
        ValidatableResponse orderResponse = orderClient.createOrder(order);

        int statusCode = orderResponse.extract().statusCode();
        int track = orderResponse.extract().path("track");
        Assert.assertEquals(201, statusCode);
        assertThat(track, notNullValue());

        ValidatableResponse getResponse = orderClient.getOrder(track);
        int newStatusCode = getResponse.extract().statusCode();
        Object orderInfo = getResponse.extract().path("order"); //это точно object??
        Assert.assertEquals(200, newStatusCode);
        assertThat(orderInfo, notNullValue());

    }

}
