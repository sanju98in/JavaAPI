package JIRAIntegration;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class GetUploadedAttachedFileFromJira {
	
	public static void main(String[] args) {
		/*//validate if attachment is uploaded correctly and get the description of bug and attached file name

		RestAssured.baseURI="https://rahulshettyacademy.com/";
		
		String getResponse =
		given()
			.log()
			.all()
		.header("Content-Type","application/json")
		.header("Authorization","Basic c2FuamVldjEwMUBnbWFpbC5jb206QVRBVFQzeEZmR0YwbzAyLW5nRmltbFpiajAtd2ZNQ18tR1ZIMXJ4SEt0SnE5bnU1QXdXTHgwS2FqWnJIUFAxeGVGN2xucUN1VmM4NWxWaVpyVjVlbnoxVzF2U1ItVi1zMmRLWUdKbnpZdUp1ZFFwd0xsZjNsSU9RdHE5Q3Q4TWJtTk14VFVNRGRISVdyMzJVb1Y3UnhLTDRVV1FySG8ySjVzNkpPOGl2Mi1xSUI1WTdVQTlJekxJPTRBMEFFRjYw")
			.log().all()
		.post("rest/api/3/issue/10007")
		.then()
			.log().all().assertThat()
			.statusCode(200)
			.extract().response().asString();
		
		JsonPath js= new JsonPath(getResponse);
		
		String fileName= js.getString("attachment.filename");
		System.out.println("FileName " + fileName);*/
	}
	
	
}
