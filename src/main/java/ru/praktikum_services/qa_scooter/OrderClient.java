package ru.praktikum_services.qa_scooter;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient{
    private static final String CREATE_ORDER_PATH = "api/v1/orders";
    private static final String GET_ORDER_PATH = "api/v1/orders/track?t=";
    private static final String GET_ORDER_LIST = "/api/v1/orders";

    public ValidatableResponse createOrder(Order order){
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then();
    }

    public ValidatableResponse getOrder(int id){
        return given()
                .spec(getBaseSpec())
                .get(GET_ORDER_PATH + id)
                .then();
    }

    public ValidatableResponse getOrderList(){
        return given()
                .spec(getBaseSpec())
                .get(GET_ORDER_LIST)
                .then();
    }
}
