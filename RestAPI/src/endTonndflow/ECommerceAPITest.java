package endTonndflow;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class ECommerceAPITest {

	public static void main(String[] args) {
		
		RequestSpecification req=
		new RequestSpecBuilder()
		.setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON)
		.build();
		
		//Login Request
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("sanjeev101@gmail.com");
		loginRequest.setUserPassword("Sanju@1978");
		
		RequestSpecification reqLogin = 
		given()
		.log()
		.all()
		.spec(req)
		.body(loginRequest);
		
		LoginResponse loginResponse=
		reqLogin
		.when()
		.post("api/ecom/auth/login")
		.then()
		.extract().response()
		.as(LoginResponse.class);
		
		System.out.println("User Token " +loginResponse.getToken());
		String token = loginResponse.getToken();
		System.out.println("UserId " +loginResponse.getUserId());
		String userId = loginResponse.getUserId();
		System.out.println("Message " +loginResponse.getMessage());
		
		//Add Product
		
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addHeader("authorization", token)
		.build();
		
		RequestSpecification reqAddProduct=
		given()
			.log()
			.all()
			.spec(addProductBaseReq)
			.param("productName", "CasualWear")
			.param("productAddedBy",userId)
			.param("productCategory", "fashion")
			.param("productSubCategory", "Shirts")
			.param("productPrice", "12300")
			.param("productDescription", "Shirt")
			.param("productFor", "men")
			.multiPart("productImage",new File("C:\\Users\\Shagun Sinha\\OneDrive\\Pictures\\shirtImage.jpg"));
		
		String addProductRespnse = 
		reqAddProduct
		.when()
		.post("/api/ecom/product/add-product")
		.then()
			.log()
			.all()
			.extract()
			.response()
			.asString();
		
		JsonPath js = new JsonPath(addProductRespnse);
		String productid=js.get("productId");

		//Create Order		
		RequestSpecification creteOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token)
				.setContentType(ContentType.JSON)
				.build();
		OrderDetails orderDetails= new OrderDetails();
		orderDetails.setCountry("US");
		orderDetails.setProductOrderedId(productid);
		
		List <OrderDetails> orderDetailList = new ArrayList<OrderDetails>();		
		orderDetailList.add(orderDetails);
		Orders orders = new Orders();
		orders.setOrders(orderDetailList);
		
		RequestSpecification createOrderReq = 
		given()
			.log()
			.all()
		.spec(creteOrderBaseReq)
		.body(orders);
		
		String responseAddOrder= createOrderReq.when().post("/api/ecom/order/create-order")
		.then()
			.log()
			.all()
			.extract()
			.response()
			.asString();
		System.out.println("Added Order " + responseAddOrder);
		
		
		//Delete Product
		RequestSpecification deleteRequestSpec = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token)
				.setContentType(ContentType.JSON)
				.build();
		RequestSpecification deleteProdReq=
		given()
		.log()
		.all()
		.spec(deleteRequestSpec)
		.pathParam("productId", productid);
		
		String deleteProductResponse = 
		deleteProdReq
		.when()
		.delete("/api/ecom/product/delete-product/{productId}")
		.then()
		.log()
		.all()
		.extract()
		.response()
		.asString();
		
		JsonPath js1 = new JsonPath(deleteProductResponse);
		Assert.assertEquals("Product Deleted Successfully",js1.get("message"));
		
	
		
	}
}
