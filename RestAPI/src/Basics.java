import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ResusableMethods;
import files.payload;
public class Basics {

public static void main(String[] args) {
		
	//Scenario: Add a Place --> Update the existing Place with new Address --> Get updated place to validate if new address is present or not
	
		//Rest API works on three principles Given, When and Then
		//Give - All input details
		//When - Submit the API - accepts resources and https method
		//Then - validate the response
	
	
	//validate if Add Place API is working as expected
		System.out.println("*************Add Place*****************");
		RestAssured.baseURI="https://rahulshettyacademy.com/";
		String response = 
			given().log().all().queryParam("key", "qaclick123").header("Content-type","application/json")
				.body(payload.AddPlace())
			.when()
				.post("maps/api/place/add/json")
			.then()
				.assertThat().
				statusCode(200) // validation, match the status code
				.body("scope", equalTo("APP")) // validation, match the body part returned
				//.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)") // validate if the header returned is true and matches the server specified
				.extract().asString(); //extract data as string
				System.out.println(response); // print the response
				JsonPath js = new JsonPath(response);//for paring json
				String placeId = js.get("place_id"); // get the json data and store it in a variable
		
		System.out.println(placeId); // print the json data in string format.
		
		//update place
		System.out.println("*************Update Place*****************");
		String newAddress="My New Home Addrss updated";
		given()
			.log().all()
			.queryParam("key", "qaclick123").
			header("Content-Type","application/json")
			.body("{\r\n"
				+ "    \"place_id\": \""+placeId+"\",\r\n"
				+ "    \"address\": \""+newAddress+"\",\r\n"
				+ "    \"key\": \"qaclick123\"\r\n"
				+ "}").
		when()
			.put("maps/api/place/update/json") 
		.then()
			.assertThat().log().all().statusCode(200)
			.body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		System.out.println("*************Get New Place*****************");
		
	String getPlaceResponse= 
	given()
		.log().all()
		.queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
	.when()
		.get("maps/api/place/get/json")
	.then()
		.assertThat().log().all()
		.statusCode(200)
		.extract().response().asString();
	
	//JsonPath js1 = new JsonPath(getPlaceResponse);
	JsonPath js1= ResusableMethods.rawToJson(getPlaceResponse);
	String newExtractedAddress =js1.getString("address");		
	System.out.println(newExtractedAddress);
	Assert.assertEquals(newExtractedAddress,newAddress);
		
	}

}
