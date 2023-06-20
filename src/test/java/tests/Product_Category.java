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

public class Product_Category {
	
	String categoryName;
	
	int id;
	Response res;
	
	public int generateRandomNumber(int n) {
		Random rand = new Random();
		return rand.nextInt(n);
	}
	
	@BeforeTest
	public void setUp() {
		
	}
	
	// get all product
	@Test (priority = 4, enabled=false)
	public void getAllProduct() {
		given()
		.when()
		.get("http://skyschooling.com:8081/api/productCategory")
		.then()	
		.statusCode(200)
		.log().all();     // Chain method
		
	}//method
	
	@Test (priority = 1)
	void createProductCategory_ValidateResponse() {
		HashMap<String, String> create = new HashMap <String, String>();
		categoryName = "test_category_name_ " + System.currentTimeMillis();
		create.put("categoryName", categoryName);
		
		res =given()
			.contentType("application/json")
			.body(create)
		.when()
		.post("http://skyschooling.com:8081/api/productCategory");
//		.then()
//		.statusCode(201)
//		.log().all();
		
		
		Assert.assertEquals(res.statusCode(), 201);
		
		JSONObject resBody = new JSONObject(res.body().asString());
		System.out.println("Response body = " + resBody);
		id = resBody.getInt("id");
		System.out.println("id = " + id);
		
		System.out.println("response status code= " + res.statusCode());
		System.out.println("response status line= " + res.statusLine());
		
	}//method
	
	@Test(priority=2)
	public void updateCategory_modify_category_name() {
		HashMap<String, String> create = new HashMap <String, String>();
		categoryName = "test_update_category_name_" + generateRandomNumber(1000000);
		create.put("categoryName", categoryName);
		
		res =given()
			.contentType("application/json")
			.body(create)
		.when()
		.put("http://skyschooling.com:8081/api/productCategory/"+ id);

		
		Assert.assertEquals(res.statusCode(), 200);
		
		JSONObject resBody = new JSONObject(res.body().asString());
		System.out.println("Update Response body = " + resBody);
		
		Assert.assertEquals(resBody.get("categoryName"), categoryName);
	
	
		
		System.out.println("Category name =  " + resBody.get("categoryName"));

		
	}//method
	
	@Test (priority = 3,enabled=false)
	void deleteProduct() {
		given()
		.when()
		.delete("http://skyschooling.com:8081/api/productCategory/" + id)
		.then()
		.statusCode(204)
		.log().all();
	}//method
	
	@Test (priority = 3, enabled=false)
	void getSingleProduct_WhichIsAlreadyDeleted() {
		given()
		.when()
		.get("http://skyschooling.com:8081/api/productCategory/" + id)
		.then()
		.statusCode(404)
		.log().all();
	}//method

}//class
