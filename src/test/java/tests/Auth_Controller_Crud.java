package tests;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class Auth_Controller_Crud {
	
	String randomUserName;
	String randomUserEmail;
	int id;
	String token;

	
	@BeforeTest
	public void setUp() {
		Random random = new Random();
	    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
	    StringBuilder usernameBuilder = new StringBuilder();
	    StringBuilder emailBuilder = new StringBuilder();
	    for (int i = 0; i < 8; i++) {
	        int index = random.nextInt(characters.length());
	        usernameBuilder.append(characters.charAt(index));
	    }
	    for (int i = 0; i < 10; i++) {
	        int index = random.nextInt(characters.length());
	        emailBuilder.append(characters.charAt(index));
	    }
	    randomUserName = usernameBuilder.toString();
	    randomUserEmail = emailBuilder.toString();
	}
	
	@Test(priority=1)
	void get_all_users_functionality() {
		given()
		.when()
		.get("http://skyschooling.com:8081/api/auth/users")
		.then()
		.statusCode(200)
		.log().all();
	}
	
	@Test(priority=2)
	void register_users() {
		
		HashMap<String, String> requestBody = new HashMap<String, String>();
		
		requestBody.put("username", randomUserName);
		requestBody.put("email", randomUserEmail +"@gmail.com");
		requestBody.put("password", "mh1234");
		requestBody.put("address", "2880 Briggs Ave, Bronx, Ny-10458");
		requestBody.put("contact", "123-654-9876");
		
		requestBody.put("firstName", "Mohammad");
		requestBody.put("lastName", "Haque");
		
		  ArrayList<String> roles = new ArrayList<String>();
		    roles.add("primary");
		    requestBody.put("roles", new Gson().toJson(roles));
		
		
			given()
				.contentType("application/json")
				.body(requestBody)
			.when()
			.post("http://skyschooling.com:8081/api/auth/signup")
			.then()
			.statusCode(201)
			.log().all();			
	}
	@Test(priority=3)
	void create_authentic_user() {
		
		HashMap<String, String> reqBody = new HashMap<String, String>();
		
		reqBody.put("password", "mh1234");
		reqBody.put("username", randomUserName);
		
		Response response = given()
		.contentType("application/json")
		.body(reqBody)
		.when()
		.post("http://skyschooling.com:8081/api/auth/signin");
		response.prettyPrint();
	
		Assert.assertEquals(response.statusCode(), 200);
		System.out.println("Status Code=" + response.statusCode());
		
		JSONObject resBody = new JSONObject(response.body().asString());
		id = resBody.getInt("id");
		System.out.println("Id = " + id);	
		
		token = resBody.getString("accessToken");
		
	}
	@Test(priority=4)
	void get_user() {
		given()
		.header("Authorization", "Bearer " + token)
		.header("Content-Type", "application/json")
		.when()
		.get("http://skyschooling.com:8081/api/auth/user/"+ id)
		.then()
		.statusCode(200)
		.log().all();
		
	}
	
	@Test(priority=5)
	void update_user() {
		
	HashMap<String, String> requestBody = new HashMap<String, String>();
	
	requestBody.put("username", "mq"+randomUserName);
	requestBody.put("email", "md"+randomUserEmail +"@gmail.com");
	requestBody.put("password", "mh1234");
	requestBody.put("address", "USA");
	requestBody.put("contact", "123-654-9876");
		
		requestBody.put("firstName", "Mohammad");
		requestBody.put("lastName", "Haque");
		
		
		
//		ArrayList<String> roles = new ArrayList<String>();
//		    roles.add("primary");
//		    requestBody.put("roles", new Gson().toJson(roles));
//		
		
			given()
				.header("Authorization", "Bearer " + token)
				.contentType("application/json")
				.body(requestBody)
			.when()
			.put("http://skyschooling.com:8081/api/auth/user/"+id)
			.then()
			.statusCode(200)
			.log().all();
	
	}
	@Test(priority=6)
	void getuser() {
		given()
		.header("Authorization", "Bearer " + token)
		.header("Content-Type", "application/json")
		.when()
		.get("http://skyschooling.com:8081/api/auth/user/"+ id)
		.then()
		.statusCode(200)
		.log().all();
		
	}
	
	@Test(priority=7)
	void delete_user() {
		given()
		.header("Authorization", "Bearer " + token)
		.header("Content-Type", "application/json")
		.when()
		.delete("http://skyschooling.com:8081/api/auth/user/"+ id)
		.then()
		.statusCode(204)
		.log().all();
		
	}
	@Test(priority=8)
	void get_deleted_user() {
		given()
		.header("Authorization", "Bearer " + token)
		.header("Content-Type", "application/json")
		.when()
		.get("http://skyschooling.com:8081/api/auth/user/"+ id)
		.then()
		.statusCode(401) //but corrected code 404
		.log().all();
		
	}

	
}//class
