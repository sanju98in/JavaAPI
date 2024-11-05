package SerializationDeserializationWithPOJOClassExample;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializeTest {

	public static void main(String[] args) {
		
		List<String> types = new ArrayList<String>();
		addPlace aPlace = new addPlace();
		aPlace.setAccuracy(50);
		aPlace.setAddress("Xrbia Riverfront");
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
		
		RestAssured.baseURI ="https://rahulshettyacademy.com/";
		Response response=
		given().queryParam("key","qaclick123")
		.body(aPlace)
		.when().post("maps/api/place/add/json")
		.then()
			.log().all()
			.assertThat().statusCode(200)
			.extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
		
	}
}
