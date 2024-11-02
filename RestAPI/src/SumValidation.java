import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	JsonPath js= new JsonPath(payload.CoursePrice());
	int count =js.getInt("courses.size()");
	
	//4. Print All course titles and their respective Prices
	@Test
	public void PrintAllCourseTitleAndPrices() {		
		for(int i=0;i<count;i++) {
			System.out.println();
			String courseTitle= js.get("courses[" + i + "].title");
			System.out.print(courseTitle + "\t");
			int coursePrice = js.getInt("courses[" + i + "].price");
			System.out.print(coursePrice + "\t");		
		}
	}
			
	//5. Print no of copies sold by RPA Course	
	@Test
	public void PrintNoOfCopies() {			
		for(int i=0;i<count;i++) {
			System.out.println();
			String courseTitle= js.get("courses[" + i + "].title");
			int noOfCopies = js.getInt("courses[" + i + "].copies");
			if(courseTitle.equalsIgnoreCase("appium")) {
				System.out.println("Total number of Copies sold by " + courseTitle + " is " + noOfCopies);
				break;
			}
		}
	}
			
	//6. Verify if Sum of all Course prices matches with Dashboard Total Purchase Amount
	@Test
	public void SumOfCourses() {
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		js= new JsonPath(payload.CoursePrice());
		
		int sumOfCoursePrices=0;
		int coursePrices =0;
		int noOfCopies=0;
		System.out.println();
		for(int i=0;i<count;i++) {
			coursePrices = js.getInt("courses[" + i + "].copies");
			noOfCopies = js.getInt("courses[" + i + "].price");
			int sum = coursePrices*noOfCopies;
			System.out.println("Sum of course and prices are " + sum);
			sumOfCoursePrices += coursePrices*noOfCopies;			
		}
		System.out.println("Total Sum of all course and prices are " + sumOfCoursePrices);
		if(totalAmount==sumOfCoursePrices)
			System.out.println("Sum of all courses price is " + sumOfCoursePrices + " is equal to " + totalAmount);
		else
			System.out.println("Sum of all courses price is " + sumOfCoursePrices + " is not equal to " + totalAmount);
	}	
	
}
