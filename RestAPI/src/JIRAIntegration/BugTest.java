package JIRAIntegration;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class BugTest {
	
	public static void main(String[] args) {
		
		RestAssured.baseURI="https://sanjeevkumarsinha.atlassian.net/";
	
		String createResponse = given()
		.header("Content-Type","application/json")
		.header("Authorization","Basic c2FuamVldjEwMUBnbWFpbC5jb206QVRBVFQzeEZmR0YwbzAyLW5nRmltbFpiajAtd2ZNQ18tR1ZIMXJ4SEt0SnE5bnU1QXdXTHgwS2FqWnJIUFAxeGVGN2xucUN1VmM4NWxWaVpyVjVlbnoxVzF2U1ItVi1zMmRLWUdKbnpZdUp1ZFFwd0xsZjNsSU9RdHE5Q3Q4TWJtTk14VFVNRGRISVdyMzJVb1Y3UnhLTDRVV1FySG8ySjVzNkpPOGl2Mi1xSUI1WTdVQTlJekxJPTRBMEFFRjYw")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"SCRUM\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"radio buttons are not working - automation rest api bug\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}")
		.log().all()
		.post("rest/api/3/issue")
		.then()
		.log().all().assertThat()
			.statusCode(201)
		.extract().response().asString();
		
		JsonPath js= new JsonPath(createResponse);
		
		String issueId= js.getString("id");
		System.out.println("Issue Id " + issueId);
		
		given()
		.pathParam("key", issueId)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic c2FuamVldjEwMUBnbWFpbC5jb206QVRBVFQzeEZmR0YwbzAyLW5nRmltbFpiajAtd2ZNQ18tR1ZIMXJ4SEt0SnE5bnU1QXdXTHgwS2FqWnJIUFAxeGVGN2xucUN1VmM4NWxWaVpyVjVlbnoxVzF2U1ItVi1zMmRLWUdKbnpZdUp1ZFFwd0xsZjNsSU9RdHE5Q3Q4TWJtTk14VFVNRGRISVdyMzJVb1Y3UnhLTDRVV1FySG8ySjVzNkpPOGl2Mi1xSUI1WTdVQTlJekxJPTRBMEFFRjYw")
		.multiPart("file", new File("C:\\Users\\Public\\KING OF CRICKET.jpg"))
		.log().all()
		.post("rest/api/3/issue/{key}/attachments")
		.then().log().all()
		.assertThat().statusCode(200);
	}

}
