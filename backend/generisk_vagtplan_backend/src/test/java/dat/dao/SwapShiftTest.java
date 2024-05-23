package dat.dao;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SwapShiftTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:7070";
        RestAssured.basePath = "/api";
    }

    @Test
    public void testCreateAndAcceptSwapRequest() {
        // Using shifts with IDs 2 and 3 and users with IDs 1 and 2 from the database

        int shift1Id = 3;
        int shift2Id = 2;
        int userId1 = 2; // The original user for shift 2
        int userId2 = 1; // The original user for shift 3

        // Create Swap Request
        Response createResponse = given()
                .contentType("application/json")
                .body(String.format("{ \"shift1\": { \"id\": %d }, \"shift2\": { \"id\": %d }, \"isAccepted\": \"\" }", shift1Id, shift2Id))
                .post("/swaprequests")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .response();

        int requestId = createResponse.jsonPath().getInt("id");
        assertNotNull(requestId);

        // Accept Swap Request
        given()
                .queryParam("accepted", true)
                .post("/swaprequests/{id}/approve", requestId)
                .then()
                .log().all()
                .statusCode(204);

        // Verify Swap Shift Creation
        Response swapShiftResponse = given()
                .get("/swapshifts")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        int swapShiftId = swapShiftResponse.jsonPath().getInt("[0].id");
        assertNotNull(swapShiftId);

        // Accept Swap Shift
        given()
                .queryParam("accepted", true)
                .post("/swapshifts/{id}/approve", swapShiftId)
                .then()
                .log().all()
                .statusCode(204);

        // Verifying shifts have been swapped by checking the users IDs
        Response shift1Response = given()
                .get("/shifts/" + shift1Id)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        Response shift2Response = given()
                .get("/shifts/" + shift2Id)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        int newShift1UserId = shift1Response.jsonPath().getInt("userId");
        int newShift2UserId = shift2Response.jsonPath().getInt("userId");

        // Log the new user IDs for debugging
        System.out.println("New user ID for Shift 1: " + newShift1UserId);
        System.out.println("New user ID for Shift 2: " + newShift2UserId);

        // Verify the shifts have been swapped
        assertEquals(userId2, newShift1UserId, "Shift 1 should now belong to User 2");
        assertEquals(userId1, newShift2UserId, "Shift 2 should now belong to User 1");
    }
}
