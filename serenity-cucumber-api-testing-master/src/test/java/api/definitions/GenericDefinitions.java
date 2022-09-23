package api.definitions;


import au.com.bytecode.opencsv.CSVReader;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static org.hamcrest.Matchers.*;

public class GenericDefinitions {

    @Steps
    
    String BaseURI ="https://localhost:3000";
    String order= null;
    String ExpectedOrderId,ExpectedLevel,ExpectedCode,ExpectedParent,ExpectedDescription,ExpectedThisItemIncludes1,ExpectedThisItemAlsoIncludes,ExpectedRulings,ExpectedThisItemIncludes2,ExpectedReference;

    public Response response;

    //Method used for passing the API method in URL
    @Given("passing the {string} url parameters")
    public void theUrlParameters(String APImethod) {
        BaseURI = BaseURI+"/"+APImethod;
    }

    //Method used for get the getNaceAPI details
    @When("get the getNaceDetails API with {String}")
    public void getNaceDetails(String orderId) {
    	order = orderId;
    	response = SerenityRest.given().contentType("application/json").header("Content-Type", "application/json").when().get(BaseURI+ orderId);
    }

    // Method used to verify the response status code
    @When("verify the response status code")
    public void verifyStatusCode() {
    	SerenityRest.restAssuredThat(response -> response.statusCode(200));
    	
    }

    //Method to verify the response with actual data in csv file
    @Then("verify the response with actual data in csv file")
    public void VerifyNaceDetails() throws IOException {
    	SerenityRest.restAssuredThat(response -> response.statusCode(200));
    	
    		String FilePath = ".\testdata.csv";

    		File inputFile = new File(FilePath);

    		// Read existing file

    		CSVReader reader = new CSVReader(new FileReader(inputFile), ',');

    		List<String[]> csvBody = reader.readAll();

    		// get CSV row column and replace with by using row and column

    		for(int i=0; i<csvBody.size(); i++){

    		String[] strArray = csvBody.get(i);
    		
    		if(strArray[0].equalsIgnoreCase(order)){ 

    		ExpectedOrderId = csvBody.get(i)[0];
    		ExpectedLevel= csvBody.get(i)[1];
    		ExpectedCode =csvBody.get(i)[2];
    		ExpectedParent =csvBody.get(i)[3];
    		ExpectedDescription =csvBody.get(i)[4];
    		ExpectedThisItemIncludes1 =csvBody.get(i)[5];
    		ExpectedThisItemAlsoIncludes =csvBody.get(i)[6];
    		ExpectedRulings =csvBody.get(i)[7];
    		ExpectedThisItemIncludes2 =csvBody.get(i)[8];
    		ExpectedReference=csvBody.get(i)[9];
    		}

    		}
    		SerenityRest.restAssuredThat(response -> response.body("NaceDetails.Order", equalTo(ExpectedOrderId)));
    		SerenityRest.restAssuredThat(response -> response.body("NaceDetails.Level", equalTo(ExpectedLevel)));
    		SerenityRest.restAssuredThat(response -> response.body("NaceDetails.Code", equalTo(ExpectedCode)));
    		SerenityRest.restAssuredThat(response -> response.body("NaceDetails.Parent", equalTo(ExpectedParent)));
    		SerenityRest.restAssuredThat(response -> response.body("NaceDetails.Description", equalTo(ExpectedDescription)));
    		SerenityRest.restAssuredThat(response -> response.body("NaceDetails.ThisItemIncludes[0]", equalTo(ExpectedThisItemIncludes1)));
    		SerenityRest.restAssuredThat(response -> response.body("NaceDetails.ThisItemAlsoIncludes", equalTo(ExpectedThisItemAlsoIncludes)));
    		SerenityRest.restAssuredThat(response -> response.body("NaceDetails.Rulings", equalTo(ExpectedRulings)));
    		SerenityRest.restAssuredThat(response -> response.body("NaceDetails.ThisItemIncludes[1]", equalTo(ExpectedThisItemIncludes2)));
    		SerenityRest.restAssuredThat(response -> response.body("NaceDetails.ReferenceToISICRev.4", equalTo(ExpectedReference)));

    		}
            
        
    }
    

    