package API;

import static spark.Spark.*;
import com.google.gson.Gson;
import spark.Filter;
import spark.Request;
import spark.Response;
import org.json.*;
import java.util.*;

/**
 * @author Iasonas Kladis
 * The main class is provided here. All subsequent routes
 * can be added here, and implemented by the class Service.
 * The API class serves the json Object to the frontend.
 */
public class API {

	public static Service service = new Service();
	public static void main(String[] args){
		
		//Needed to bypass CORS check in this simple App.
		before((Filter) (request, response) -> {	
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Credentials", "true");
            response.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            response.header("Access-Control-Allow-Methods", "PUT, POST, GET, DELETE, OPTIONS");
        });	
	
		get("/data", (req, res) -> {
			res.type("application/json");
			return Service.get_data();
		});
		
		post("/data", (req, res) -> {
			res.type("application/json");
			JSONObject json = new JSONObject(req.body());
			
			String name = (String) json.get("name");
			String surname = (String) json.get("surname");
			String amount = (String) json.get("amount");
			String currency = (String) json.get("currency");
			
			//System.out.println(name+surname+amount+currency);

			return Service.post_data(name, surname, amount, currency);
		});
		
	}
}
