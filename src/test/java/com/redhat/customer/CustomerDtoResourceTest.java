package com.redhat.customer;

import com.redhat.dto.CustomerDto;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestHTTPEndpoint(CustomerResource.class)
public class CustomerDtoResourceTest {

    @Test
    public void getAll() {
        given()
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    public void getById() {
        CustomerDto customer = createCustomer();
        CustomerDto saved = given()
                .contentType(ContentType.JSON)
                .body(customer)
                .post()
                .then()
                .statusCode(201)
                .extract().as(CustomerDto.class);
        CustomerDto got = given()
                .when()
                .get("/{customerId}", saved.getCustomerId())
                .then()
                .statusCode(200)
                .extract().as(CustomerDto.class);
        assertThat(saved).isEqualTo(got);
    }

    @Test
    public void getByIdNotFound() {
        given()
                .when()
                .get("/{customerId}", 987654321)
                .then()
                .statusCode(404);
    }

    @Test
    public void post() {
        CustomerDto customer = createCustomer();
        CustomerDto saved = given()
                .contentType(ContentType.JSON)
                .body(customer)
                .post()
                .then()
                .statusCode(201)
                .extract().as(CustomerDto.class);
        assertThat(saved.getCustomerId()).isNotNull();
    }

    @Test
    public void postFailNoFirstName() {
        CustomerDto customer = createCustomer();
        customer.setFirstName(null);
        given()
                .contentType(ContentType.JSON)
                .body(customer)
                .post()
                .then()
                .statusCode(400);
    }

    @Test
    public void put() {
        CustomerDto customer = createCustomer();
        CustomerDto saved = given()
                .contentType(ContentType.JSON)
                .body(customer)
                .post()
                .then()
                .statusCode(201)
                .extract().as(CustomerDto.class);
        saved.setFirstName("Updated");
        given()
                .contentType(ContentType.JSON)
                .body(saved)
                .put("/{customerId}", saved.getCustomerId())
                .then()
                .statusCode(204);
    }

    @Test
    public void putFailNoLastName() {
        CustomerDto customer = createCustomer();
        CustomerDto saved = given()
                .contentType(ContentType.JSON)
                .body(customer)
                .post()
                .then()
                .statusCode(201)
                .extract().as(CustomerDto.class);
        saved.setLastName(null);
        given()
                .contentType(ContentType.JSON)
                .body(saved)
                .put("/{customerId}", saved.getCustomerId())
                .then()
                .statusCode(400);
    }

    private CustomerDto createCustomer() {
        CustomerDto customer = new CustomerDto();
        customer.setFirstName(RandomStringUtils.randomAlphabetic(10));
        customer.setMiddleName(RandomStringUtils.randomAlphabetic(10));
        customer.setLastName(RandomStringUtils.randomAlphabetic(10));
        customer.setEmail(RandomStringUtils.randomAlphabetic(10) + "@rhenergy.dev");
        customer.setPhone(RandomStringUtils.randomNumeric(10));
        return customer;
    }
}
