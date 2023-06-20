package tests;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Random;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.response.Response;


public class Product_Category_Crud {
	
	String userName;
	int id;
	Response response;
	public int generateRandomNumber(int n) {
		Random rand = new Random();
		return rand.nextInt(n);
	}
	@Test(priority=1)
	void post_product_category_functionality() {
		HashMap<String, String> requestBody = new HashMap<String, String>();
		userName = "test_category_name:" + generateRandomNumber(1000000);
		requestBody.put("categoryName", userName);
		response = given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("http://skyschooling.com:8081/api/productCategory");
				
				Assert.assertEquals(response.statusCode(), 201);
				Assert.assertEquals(response.contentType(), "application/json");
				System.out.println(response.contentType());
				System.out.println(response.getHeader("Pragma"));
				System.out.println(response.getStatusLine());
				System.out.println(response.getTime());
	
				
				JSONObject resBody = new JSONObject(response.body().asString());
				System.out.println("Response Body = " + resBody);
				
				id = resBody.getInt("id");
				System.out.println("Id= " + id);
				
	}
	
	@Test(priority=2)
	void get_product_category_byId() {
		
	given()
		.when()
		.get("http://skyschooling.com:8081/api/productCategory/"+ id)
		.then()
		.statusCode(200)
		.log().all();
		
		
	}
	
	@Test(priority=3)
	void update_product_category() {
		HashMap<String, String> requestBody = new HashMap<String, String>();
		userName = "Test_update_category_name:" + generateRandomNumber(1000000);
		requestBody.put("categoryName", userName);
		response = given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.put("http://skyschooling.com:8081/api/productCategory/" + id);
				
				Assert.assertEquals(response.statusCode(), 200);
				Assert.assertEquals(response.contentType(), "application/json");
				System.out.println(response.contentType());
				System.out.println(response.getHeader("Pragma"));
				System.out.println(response.getStatusLine());
				System.out.println(response.getTime());
	
				
				JSONObject resBody = new JSONObject(response.body().asString());
				System.out.println("Response Body = " + resBody);
				
				id = resBody.getInt("id");
				System.out.println("Id= " + id);
		
	}
	@Test(priority=4)
	void get_update_product_category_byId() {
		given()
		.when()
		.get("http://skyschooling.com:8081/api/productCategory/"+ id)
		.then()
		.statusCode(200)
		.log().all();
		
	}
	
	@Test(priority=5)
	void delete_product_category() {
		given()
		.when()
		.delete("http://skyschooling.com:8081/api/productCategory/"+ id)
		.then()
		.statusCode(204)
		.log().all();
	}
	
	@Test(priority=6)
	void get_deleted_product_category() {
		given()
		.when()
		.get("http://skyschooling.com:8081/api/productCategory/"+ id)
		.then()
		.statusCode(404)
		.log().all();
		
	}

}//class
