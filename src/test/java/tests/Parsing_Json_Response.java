package tests;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Parsing_Json_Response {
	@Test
	void testJsonResponseProdCategoryBodyData() {
		Response response = given()
				.contentType(ContentType.JSON)
		.when()
		.get("http://skyschooling.com:8081/api/v01/product/all");
//		.then()
//		.statusCode(200)
//		.log().body();
		
		JSONArray items = new JSONArray(response.body().asString());
		
		int i = 0;
		while(i < items.length()) {
			JSONObject catName = items.getJSONObject(i);
			String itemName = catName.optString("sku");
			System.out.println(itemName);
			i++;
		}
		
		
//		
	}
	@Test
	void parsingProductCategory() {
		Response response = given()
				.contentType(ContentType.JSON)
		.when()
		.get("http://skyschooling.com:8081/api/productCategory");
//		.then()
//		.statusCode(200)
//		.log().body();
		
		JSONArray items = new JSONArray(response.body().asString());
		
		int i = 0;
		while(i < items.length()) {
			JSONObject catName = items.getJSONObject(i);
			//Rose Flowers
			String itemName = catName.optString("categoryName");
			if(itemName.equals("Summer plants")) {
				System.out.println("Item Found= " + itemName);
				//break;
			}
			
			//System.out.println(itemName);
			i++;
		}
		
	}

}//class
