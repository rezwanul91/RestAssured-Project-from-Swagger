package tests;


	import java.util.HashMap;
	import java.util.Random;

	import org.testng.annotations.BeforeTest;
	import org.testng.annotations.Test;

	import io.restassured.response.Response;
	import static io.restassured.RestAssured.*;


	import org.testng.annotations.Test;


	import static io.restassured.RestAssured.*;
	import static org.hamcrest.Matcher.*;

	public class Auth_Controller {
		 String userName;
		 String token;
		 int id;
		//String email;
		
		
		
		@BeforeTest
		public void setup() {
			userName ="Ripon"+generateRandomNumber(1000000);
			//
			//id =generateRandomNumber(1000000);
			//email =" Ripon"+generateRandomNumber(1000000)+"@gamil.com";
			
		}
		
		
		public int generateRandomNumber(int n)
		{
			Random rand = new Random();
			
			return   rand.nextInt(n);
			
		}

	@Test(priority=1)
		public void signUp() {
			HashMap<String, String> requestBody = new HashMap<String, String>();
			
			requestBody.put("username", userName);
			requestBody.put("firstName", "ashit");
			requestBody.put("lastName", "barua");
			requestBody.put("email", "Ripon"+generateRandomNumber(1000000)+"@gamil.com");
			requestBody.put("password", "7777777");
			requestBody.put("contact", "+88888888");
			requestBody.put("address", "CA");		

		     given()
			.contentType("application/json")
			.body(requestBody)
			.when()
			.post("http://skyschooling.com:8081/api/auth/signup")
			//JSONObject resBody =new JSONObject(response.body().asString());
			//userName = resBody.getString("userName");
			
			
			.then()
			.statusCode(201)
			.log()
			.all();

		}
	@Test(priority=2)
	public void signin() {
	    HashMap<String, String> requestBody = new HashMap<String, String>();
	    requestBody.put("username", userName);
	    requestBody.put("password", "7777777");

	    Response response = given()
	            .contentType("application/json")
	            .body(requestBody)
	            .when()
	            .post("http://skyschooling.com:8081/api/auth/signin");

	    id = response.path("id");
	    System.out.println("User ID: " + id);

	    
	}

	@Test(priority=3)
	public void getOneUser() {
		// get bearer token
		HashMap<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("username", userName);
		requestBody.put("password", "7777777");
		Response response = given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("http://skyschooling.com:8081/api/auth/signin");
		 token = response.getBody().jsonPath().get("accessToken");
		
		System.out.println("Token: "+token);

		// get user by id
		given()
			.header("Authorization", "Bearer " + token)
			.when()
			.get("http://skyschooling.com:8081/api/auth/user/" + id)
			.then()
			.statusCode(200)
			.log()
			.all();
	}
	

	@Test(priority=4)
	public void updateUser() {
	    HashMap<String, String> requestBody = new HashMap<String, String>();
	    requestBody.put("username", "hhhh"+userName);
	    requestBody.put("password", "7777777");
	    requestBody.put("email", "Ripon"+generateRandomNumber(1000000)+"@gamil.com");
	    requestBody.put("firstName", "ashit");
	    requestBody.put("lastName", "barua");
	    requestBody.put("contact", "+88888888");
	    requestBody.put("address", "USA"); 

	    // update user by id
	    given()
	        .header("Authorization", "Bearer " + token)
	        .contentType("application/json")
	        .body(requestBody)
	        .when()
	        .put("http://skyschooling.com:8081/api/auth/user/" + id)
	        .then()
	        .statusCode(200)
	        .log().all();
	        
	}

	}

