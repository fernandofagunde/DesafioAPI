package com.rpe;


import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.json.simple.JSONObject;

public class Suite {

	@BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }
	
	@Test
	public void singleUser() {
		 given()
         .when()
           .get("/api/users/2")
         .then()
           .log().all()
           .statusCode(200)
           .body("data.id", equalTo(2)) 
           .body("data.email", equalTo("janet.weaver@reqres.in"))
		   .body("data.first_name", equalTo("Janet"))
		   .body("data.last_name", equalTo("Weaver"))
		   .body("data.avatar", equalTo("https://reqres.in/img/faces/2-image.jpg"));
          
	}	
	
	@Test
	public void singleResource() {
		 given()
         .when()
           .get("/api/unknown/2")
         .then()
           .log().all()
           .statusCode(200)
           .body("data.id", equalTo(2)) 
           .body("data.name", equalTo("fuchsia rose"))
		   .body("data.year", equalTo(2001))
		   .body("data.color", equalTo("#C74375"))
		   .body("data.pantone_value", equalTo("17-2031"));
          
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void create_user() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "Fernando");
		requestParams.put("job", "QA");
		given()
        .header("Content-Type", "application/json")
          .body(requestParams.toJSONString())
        .when()
          .post("/api/users")
        .then()
          .log().all()
          .statusCode(201)
          .body("name", equalTo("Fernando")) 
          .body("job", equalTo("QA"))
          .body("id",  notNullValue())
          .body("createdAt", notNullValue());
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void login_successfull() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("email", "eve.holt@reqres.in");
		requestParams.put("password", "cityslicka");
		given()
        .header("Content-Type", "application/json")
          .body(requestParams.toJSONString())
        .when()
          .post("/api/login")
        .then()
          .log().all()
          .statusCode(200)
          .body("token", notNullValue());
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void login_unsuccessfull() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("email", "eve.holt@reqres.in");
		given()
        .header("Content-Type", "application/json")
          .body(requestParams.toJSONString())
        .when()
          .post("/api/login")
        .then()
          .log().all()
          .statusCode(400)
          .body("error", equalTo("Missing password"));
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void register_successful() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("email", "eve.holt@reqres.in");
		requestParams.put("password", "pistol");
		given()
        .header("Content-Type", "application/json")
          .body(requestParams.toJSONString())
        .when()
          .post("/api/register")
        .then()
          .log().all()
          .statusCode(200)
          .body("id", notNullValue())
          .body("token", notNullValue());
	
	}
	@SuppressWarnings("unchecked")
	@Test
	public void register_unsuccessful() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("email", "eve.holt@reqres.in");
		given()
        .header("Content-Type", "application/json")
          .body(requestParams.toJSONString())
        .when()
          .post("/api/register")
        .then()
          .log().all()
          .statusCode(400)
          .body("error",equalTo("Missing password"));
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void update_user_put() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "Fernando");
		requestParams.put("job", "QA");
		given()
        .header("Content-Type", "application/json")
          .body(requestParams.toJSONString())
        .when()
          .put("/api/users/2")
        .then()
          .log().all()
          .statusCode(200)
          .body("name", equalTo("Fernando")) 
          .body("job", equalTo("QA"))
          .body("updatedAt", notNullValue());
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void update_user_patch() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "Fernando Fagundes");
		given()
        .header("Content-Type", "application/json")
          .body(requestParams.toJSONString())
        .when()
        
          .put("/api/users/3")
        .then()
          .log().all()
          .statusCode(200)
          .body("name", equalTo("Fernando Fagundes")) 
          .body("updatedAt", notNullValue());
	
	}
	@SuppressWarnings("unchecked")
	@Test
	public void delete_user() {
		given()
        .header("Content-Type", "application/json")
        .when()
          .delete("/api/users/4")
        .then()
          .log().all()
          .statusCode(204);
         
	
	}
	

}

