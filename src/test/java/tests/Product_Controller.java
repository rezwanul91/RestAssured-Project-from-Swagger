package tests;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Product_Controller {
	int id;

		@Test(priority=1)
	    public void CreateProduct() {
	        // Create a new RequestSpecification instance
	     //   RequestSpecification request = RestAssured.given();

	        // Set the content type of the request
	       // request.contentType("application/json");

	        // Create a JSONObject instance to represent the request body
	        JSONObject requestParams = new JSONObject();
	        requestParams.put("active", true);
	        requestParams.put("category", new JSONObject()
	                                        .put("categoryName", "Fish Food")
	                                        .put("id", 50));
	        requestParams.put("dateCreated", "2023-02-27T16:09:02.298Z");
	        requestParams.put("description", "Beef");
	        requestParams.put("imageUrl", "C:\\Users\\muaha\\OneDrive\\Desktop\\Dog Food.jpeg");
	        requestParams.put("lastUpdated", "2023-02-27T16:09:02.298Z");
	        requestParams.put("name", "Blue Buffolo123");
	        requestParams.put("sku", "DF-006");
	        requestParams.put("unitPrice", 15.00);
	        requestParams.put("unitsInStock", 100);

	        // Send a POST request to the server with the request body
	      //  Response response = request.body(requestParams.toString())
	    Response response = given()
				        	.body(requestParams.toString())
				        	.contentType("application/json")
				        	.when()
							.post("http://skyschooling.com:8081/api/v01/product");
				//.then()
				//.statusCode(201)
				//.log().all();
				

	        // Print the response body

	        // Check the status code of the response
	        int statusCode = response.getStatusCode();
	        System.out.println("Status code: " + statusCode);
	        
	        Assert.assertEquals(statusCode, 201);
	        System.out.println("response.getStatusCode()" + statusCode);
	        
	        JSONObject resBody = new JSONObject(response.body().asString());
	        id = resBody.getInt("id");
	        System.out.println("id is ="+ id);
	        
	        String actualValue = resBody.getString("name");
	        
	        Assert.assertEquals(resBody.get("name"), actualValue);
	        System.out.println(actualValue);
	        
	    }
		@Test(priority=2)
		void getProductById() {
			Response response =given()
			.when()
			.get("http://skyschooling.com:8081/api/v01/product/" + id);
//			.then()
//			.statusCode(200)
//			.log().body();
			
			JSONObject resBody = new JSONObject(response.body().asString());
			
		Assert.assertEquals(resBody.getDouble("unitPrice"), 15.00);	;
		}
		
		@Test(priority=3)
		void updateProduct() {
			 // Update a new RequestSpecification instance
	        RequestSpecification request = RestAssured.given();

	        // Set the content type of the request
	        request.contentType("application/json");

	        // Create a JSONObject instance to represent the request body
	        JSONObject requestParams = new JSONObject();
	        requestParams.put("active", true);
	        requestParams.put("category", new JSONObject()
	                                        .put("categoryName", "Fish Food")
	                                        .put("id", 50));
	        requestParams.put("dateCreated", "2023-02-27T16:09:02.298Z");
	        requestParams.put("description", "Beef");
	        requestParams.put("imageUrl", "C:\\Users\\muaha\\OneDrive\\Desktop\\Dog Food.jpeg");
	        requestParams.put("lastUpdated", "2023-02-27T16:09:02.298Z");
	        requestParams.put("name", "Blue Buffolo123");
	        requestParams.put("sku", "DF-006");
	        requestParams.put("unitPrice", 20.00);
	        requestParams.put("unitsInStock", 20);

	        // Send a put request to the server with the request body
	        Response response = request.body(requestParams.toString())
	        						.put("http://skyschooling.com:8081/api/v01/product/" + id);

	        // Print the response body
	        response.prettyPrint();

	        // Check the status code of the response
	        int statusCode = response.getStatusCode();
	        
	        Assert.assertEquals(statusCode, 200);
	        System.out.println("Status code: " + statusCode);
	        JSONObject resBody = new JSONObject(response.body().asString());
	        id = resBody.getInt("id");
	        System.out.println("id is ="+ id);
		}
		
		@Test(priority=4)
		void newGetProductById() {
			given()
			.when()
			.get("http://skyschooling.com:8081/api/v01/product/" + id)
			.then()
			.statusCode(200)
			.log().all();
		}
		
		@Test(priority=5)
		void deleteProduct() {
			given()
			.when()
			.delete("http://skyschooling.com:8081/api/v01/product/" + id)
			.then()
			.statusCode(204)
			.log().all();
		}
		
		@Test(priority=6)
		void GetDeletedProductById() {
			given()
			.when()
			.get("http://skyschooling.com:8081/api/v01/product/" + id)
			.then()
			.statusCode(404)
			.log().all();
		}
	

}//class
