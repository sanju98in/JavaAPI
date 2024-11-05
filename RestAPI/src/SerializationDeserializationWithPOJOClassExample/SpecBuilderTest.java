package SerializationDeserializationWithPOJOClassExample;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {

	public static void main(String[] args) {
		
		List<String> types = new ArrayList<String>();
		addPlace aPlace = new addPlace();
		aPlace.setAccuracy(50);
		aPlace.setAddress("Xrbia Riverfront, Pune");
		aPlace.setLanguage("en-us");
		aPlace.setPhoneNumber("(+91) 942 230 6044");
		aPlace.setWebsite("http://google.com");
		aPlace.setName("Sanjeev Kumar Sinha");
		types.add("Shoe Park");
		types.add("Shop");
		aPlace.setTypes(types);
		
		location l = new location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		aPlace.setLocation(l);
		
		RequestSpecification request = new RequestSpecBuilder()
		.setBaseUri("https://rahulshettyacademy.com/") 
		.addQueryParam("key","qaclick123")
		.setContentType(ContentType.JSON)
		.build();
		
		ResponseSpecification responseSpec= 
				new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();	
		
		RequestSpecification response=
		given()
			.spec(request)
			.body(aPlace);
		
		Response res = 
			response
			.when()
				.post("maps/api/place/add/json")
			.then()
				.spec(responseSpec)
				.extract().response();
		
		String responseString = res.asString();
		System.out.println(responseString);
		
	}
}
