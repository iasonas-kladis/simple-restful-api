package API;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.sql.*;

/**
 * @author Iasonas Kladis
 * The Service class serves the data from the mariaDB database to the API class.
 */
public class Service {

	public static JSONArray get_data() throws JSONException
	{
		JSONArray response = new JSONArray();
		JSONObject response_i = null;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/database", "root", "admin");	
			Statement stmt = connection.createStatement();
			
			String sql_find = "select * from data";		
			stmt.executeQuery(sql_find);
			ResultSet rs = stmt.getResultSet();

	        if(!rs.next())
	        {
	        	response_i = new JSONObject("{\"Error (404)\":\"No data found\"}");
	        	response.put(response_i);
	        }
	        while(rs != null) 
	        {
	        	String name = rs.getString(1);
	        	String surname = rs.getString(2);
	        	String currency = rs.getString(3);
	        	String amount = rs.getString(4);

	        	response_i = new JSONObject("{\"name\":"+name+",\"surname\":"+surname+",\"amount\":"+amount+",\"currency\":"+currency+"}");
				response.put(response_i);
	        	if(!rs.next()) break;
	        }
	        return response;

		} catch (SQLException e) {	
			e.printStackTrace();
			response_i = new JSONObject("{\"Error (500)\":\"Get data failed to get results\"}");
			if(response.length() == 0)
			{
				response.put(response_i);
			}
			return response;
		}	
	}
	
	public static JSONObject post_data(String name, String surname, String amount, String currency) throws JSONException
	{
		JSONObject response = null;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/database", "root", "admin");	
			Statement stmt = connection.createStatement();
			String sql = "insert into data "
					+ "(name, surname, amount, currency) "
					+ "values('"
					+ name + "', '"
					+ surname + "', '"
					+ Double.parseDouble(amount) + "', '"
					+ currency + "')";
			
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			int id=-1;
			ResultSet set = stmt.getGeneratedKeys();
			if (set.next()){
	            id=set.getInt(1);
	        }
			response = new JSONObject("{\"OK (201)\": \"Data Added! ID: "+id+"\"}");
			return response;
		} catch (SQLException e) {	
			e.printStackTrace();
			response = new JSONObject("{\"Error\":\"Add data failed!}");
			return response;
		}
	}
}