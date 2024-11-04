package SerializationDeserializationWithPOJOClassExample;

import static io.restassured.RestAssured.given;

import java.util.List;

import io.restassured.path.json.JsonPath;

public class DeseralizationUsingPojoClassExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String response =
        given() 
        	.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
            .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
            .formParams("grant_type", "client_credentials")
            .formParams("scope", "trust")
        .when().log().all()
        	.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
			System.out.println(response);
			JsonPath jsonPath = new JsonPath(response);
		    String accessToken = jsonPath.getString("access_token");
		    System.out.println(accessToken);
		
		//get the course details
	    GetCourse getCourseDetails=    
		given()
			.queryParams("access_token", accessToken)
		.when()
           .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
           .as(GetCourse.class);
	    	System.out.println("***********************************");
	    	System.out.println(getCourseDetails.getInstructor());
	    	System.out.println(getCourseDetails.getLinkedIn());
	    	System.out.println(getCourseDetails.getExpertise());
	    
	    	//find the price of soapui course
	    	List <Api> courseTitles = getCourseDetails.getCourses().getApi();
	    	for(int i=0;i<courseTitles.size();i++) {
	    		if(courseTitles.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
	    			System.out.println(courseTitles.get(i).getCourseTitle());
	    			System.out.println(courseTitles.get(i).getPrice());		
	    		}
			}
	    	
	    	//print all API courses
	    	System.out.println("***********************************");
	    	courseTitles = getCourseDetails.getCourses().getApi();
	    	for(int i=0;i<courseTitles.size();i++) {
	    			System.out.println(courseTitles.get(i).getCourseTitle());
	    			System.out.println(courseTitles.get(i).getPrice());		
	    	}
	    	
	    	//print all Mobile data
	    	System.out.println("***********************************");
	    	List <Mobile> mobileData = getCourseDetails.getCourses().getMobile();
	    	for(int i=0;i<mobileData.size();i++) {
	    			System.out.println(mobileData.get(i).getCourseTitle());
	    			System.out.println(mobileData.get(i).getPrice());		
	    	}
	    	
	    	//print all WebAutomation data
	    	System.out.println("***********************************");
	    	List <WebAutomation> webAutomationData = getCourseDetails.getCourses().getWebAutomation();
	    	for(int i=0;i<webAutomationData.size();i++) {
	    			System.out.println(webAutomationData.get(i).getCourseTitle());
	    			System.out.println(webAutomationData.get(i).getPrice());		
	    	}
	    	
	    	//print price of Cypress WebAutomation data
	    	System.out.println("***********************************");
	    	webAutomationData = getCourseDetails.getCourses().getWebAutomation();
	    	for(int i=0;i<webAutomationData.size();i++) {
	    		if(webAutomationData.get(i).getCourseTitle().equalsIgnoreCase("Cypress")) {
	    			System.out.println(webAutomationData.get(i).getCourseTitle());
	    			System.out.println(webAutomationData.get(i).getPrice());		
	    		}
	    			
	    	}
	    	
	    	//print all prices of WebAutomation data
	    	System.out.println("***********************************");
	    	int sum=0;
	    	webAutomationData = getCourseDetails.getCourses().getWebAutomation();
	    	for(int i=0;i<webAutomationData.size();i++) {
	    		sum += Integer.parseInt(webAutomationData.get(i).getPrice());
	    	}
	    	System.out.println("sum of all webautomation course price is " + sum);
		}
}
