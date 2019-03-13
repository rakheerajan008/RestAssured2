package RestAssured2.RestAssured2;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
public class Test1 {
	@Test(priority=0)
	public void GetWeatherDetails()
	{
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Mumbai");
		System.out.println("Response Body is =>  " + response.asString());
	}
	  @Test(priority=1)
	  public void GetStatusCodeForValidCity()
	 	{
	 		// Specify the base URL to the RESTful web service
	 		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

	 		// Get the RequestSpecification of the request that you want to sent
	 		// to the server. The server is specified by the BaseURI that we have
	 		// specified in the above step.
	 		RequestSpecification httpRequest = RestAssured.given();

	 		// Make a GET request call directly by using RequestSpecification.get() method.
	 		// Make sure you specify the resource name.
	 		Response response = httpRequest.get("/Mumbai");
	 		int StatusCode = response.getStatusCode();
	 		System.out.println(StatusCode+"<-----Status Code");
	 		//Validate if the status code is correct
	 		Assert.assertEquals(StatusCode/*actual Code*/, 200/*expected code*/,"Correct Status Code is returned");
	 		
	 	}
	  @Test(priority=2)
	  public void GetStatusCodeForInValidCity()
	 	{
	 		// Specify the base URL to the RESTful web service
	 		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

	 		// Get the RequestSpecification of the request that you want to sent
	 		// to the server. The server is specified by the BaseURI that we have
	 		// specified in the above step.
	 		RequestSpecification httpRequest = RestAssured.given();

	 		// Make a GET request call directly by using RequestSpecification.get() method.
	 		// Make sure you specify the resource name.
	 		Response response = httpRequest.get("/Indore");
	 		int StatusCode = response.getStatusCode();
	 		System.out.println(StatusCode+"<-----Status Code");
	 		//Validate if the status code is correct
	 		Assert.assertEquals(StatusCode/*actual Code*/, 200/*expected code*/,"Correct Status Code is returned");
	 		

	 		// Response.asString method will directly return the content of the body
	 		// as String.
	 		//System.out.println("Response Body is =>  " + response.asString());
	 	}
	   @Test(priority=3)
	   public void GetWeatherHeaders() {
		   RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		   RequestSpecification httpRequest = RestAssured.given();
		   Response response = httpRequest.get("/Mumbai");
		   
		   String contentType = response.header("Content-Type");
		   System.out.println(contentType+"<--- ContentType");
		   Assert.assertEquals(contentType/*actual value*/,"application/json"/*expected value*/,"Correct Content Type");
		   
		   String serverType = response.header("Server");
		   System.out.println(serverType+"<--- Server");
		   Assert.assertEquals(serverType/*actual value*/,"nginx"/*expected value*/,"Correct Server Type");
		   
		   String acceptLanguage = response.header("Content-Encoding");
		   System.out.println(acceptLanguage+"<--- Content-Encoding");
		   Assert.assertEquals(acceptLanguage/*actual value*/,"gzip"/*expected value*/,"Correct Encoding Type");
		   
		   
		   //Iterate over all the headers
		   System.out.println("-------------------------------------------------------");
		   Headers allHeaders = response.headers();
		   for(io.restassured.http.Header header : allHeaders) {
			   System.out.println("Key:" + header.getName()+ "Value: "+ header.getValue());
		   }
	   }
	   @Test(priority=4)
	   public void getBodyDetails() {
		   RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		   RequestSpecification httpRequest = RestAssured.given();
		   Response response = httpRequest.get("/Mumbai");
		   
		   //get message body
		   ResponseBody body=response.getBody();
		   String bodyAsString= body.asString();
		   System.out.println(bodyAsString);
		   Assert.assertEquals(bodyAsString.contains("Mumbai"),true,"Response Body contains Mumbai");
		   
	   }
	   /*Function VerifyCityinJSONBody() is used for uses 
	    * JsonPathEvaluator which runs over JSON and finds 
	    * the value of a particular node*/
	   @Test(priority=5)
	   public void VerifyCityinJSONBody() {
		   RestAssured.baseURI="http://restapi.demoqa.com/utilities/weather/city";
		   RequestSpecification httpRequest =RestAssured.given();
		   Response response = httpRequest.get("/Mumbai");
		   
		   JsonPath JsonPathEvaluator = response.jsonPath();
		   String city = JsonPathEvaluator.get("City");
		   
		   System.out.println("City Recieved from Json Response " + city);
		   Assert.assertEquals(city,"Mumbai","Correct City has been present in response");
		   
	   }
	   //Function RegisterUser() uses post method for adding the Customer on the link if the same customer is used then function fails
	   @Test(priority=6)
	   public void RegisterUser() throws JSONException {
		   RestAssured.baseURI="http://restapi.demoqa.com/customer";
		   RequestSpecification request = RestAssured.given();
		   
		   JSONObject requestParams = new JSONObject();
		   requestParams.put("FirstName","Rasika");
		   requestParams.put("LastName","Dravid");
		   requestParams.put("UserName","rasika123");
		   requestParams.put("Password","password1");
		   requestParams.put("Email","rasika.dravid@gmail.com");
		   
		   request.body(requestParams.toString());
		   Response response = request.post("/register");
		   
		   int statusCode=response.getStatusCode();
		   System.out.println("Status Code for registration is " + statusCode);
		   Assert.assertEquals(statusCode, 201);
		   String successCode=response.jsonPath().getString("SuccessCode");
		   System.out.println(successCode);
		   Assert.assertEquals(successCode, "OPERATION_SUCCESS","Correct Success code was returned");
	   }


	}
