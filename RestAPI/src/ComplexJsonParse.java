import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	/*
	 * 1. Print No of courses returned by API

	2.Print Purchase Amount

	3. Print Title of the first course

	4. Print All course titles and their respective Prices

	5. Print no of copies sold by RPA Course

	6. Verify if Sum of all Course prices matches with Purchase Amount
	*/
	public static void main(String[] args) {
		JsonPath js= new JsonPath(payload.CoursePrice());
		//1. Print No of courses returned by API
		int count =js.getInt("courses.size()");
		System.out.println("Count of courses: " + count);
		
		//2.Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Total Purchase Amount: " + totalAmount);
		
		//3. Print Title of the first course
		String firstTitleCourse = js.get("courses[0].title");
		System.out.println("First Course Title: " + firstTitleCourse);
		
		//4. Print All course titles and their respective Prices
		for(int i=0;i<count;i++) {
			System.out.println();
			String courseTitle= js.get("courses[" + i + "].title");
			System.out.print(courseTitle + "\t");
			int coursePrice = js.getInt("courses[" + i + "].price");
			System.out.print(coursePrice + "\t");		
		}
		//5. Print no of copies sold by RPA Course		
		for(int i=0;i<count;i++) {
			System.out.println();
			String courseTitle= js.get("courses[" + i + "].title");
			int noOfCopies = js.getInt("courses[" + i + "].copies");
			if(courseTitle.equalsIgnoreCase("appium")) {
				System.out.println("Total number of Copies sold by " + courseTitle + " is " + noOfCopies);
				break;
			}
		}
		//6. Verify if Sum of all Course prices matches with Dashboard Total Purchase Amount
		int sumOfCoursePrices=0;
		int coursePrices =0;
		int noOfCopies=0;
		System.out.println();
		for(int i=0;i<count;i++) {
			coursePrices = js.getInt("courses[" + i + "].copies");
			noOfCopies = js.getInt("courses[" + i + "].price");
			sumOfCoursePrices += coursePrices*noOfCopies;
		}
		if(totalAmount==sumOfCoursePrices)
			System.out.println("Sum of all courses price is " + sumOfCoursePrices + " is equal to " + totalAmount);
		else
			System.out.println("Sum of all courses price is " + sumOfCoursePrices + " is not equal to " + totalAmount);
	}
}
