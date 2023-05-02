package ru.praktikum_services.qa_scooter;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public static Courier getRandom(){
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    public static Courier getRandomWithoutFirstName(){
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, null);
    }

    public static Courier getDefaultCredentials(){
        final String login = "Foo-Foo";
        final String password = "aaa";
        final String firstName = "Царь гусей";
        return new Courier(login, password, firstName);
    }

}
