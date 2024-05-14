import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SwapShiftsApiTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7070;
        RestAssured.basePath = "/api";
    }


    @Test
    public void testGetSwaps() {
        given()
                .when()
                .get("/swapshifts")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThanOrEqualTo(3)) // Ensure there are at least 3 items
                .body("[0].shiftId", notNullValue()); // Ensure the first item has a shiftId
    }

    @Test
    public void testApproveSwap() {

        Response response = given()
                .when()
                .get("/swapshifts")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();

        // Find a swap in the response
        int swapId = response.jsonPath().getInt("[0].shiftId");

        // Approve the swap
        given()
                .contentType(ContentType.JSON)
                .body("{ \"isAccepted\": \"Approved\" }")
                .when()
                .post("/swapshifts/" + swapId + "/approve")
                .then()
                .statusCode(200)
                .body(equalTo("Swap status updated to: Approved"));

        // Verifying the swap was approved
        given()
                .when()
                .get("/swapshifts")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("find { it.shiftId == " + swapId + " }.isAccepted", equalTo("Approved"));
    }
}
