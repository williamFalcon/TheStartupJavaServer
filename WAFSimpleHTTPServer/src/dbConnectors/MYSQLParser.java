package dbConnectors;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MYSQLParser {

	/**
	 * Give it an object as a template and a result set row and it will parse it for you
	 * and give back the parsed result
	 * 
	 * Query has to be SELECT *
	 * @param templateObject
	 * @param setObject
	 * @return parsedObject
	 * @throws Exception
	 * @author waf04
	 */
	public static Object mapSQLResultToObject(Object template, ResultSet resultObject) throws SQLException{

		//Init result
		Object result = (Object)template;

		//Get the object's fields
		Field [] objectFields = template.getClass().getDeclaredFields();

		//Set which fields are not relationships
		ArrayList<String>allowedFields = new ArrayList<String>();
		allowedFields.add("int");
		allowedFields.add("java.lang.String");
		allowedFields.add("java.sql.Timestamp");
		allowedFields.add("double");

		//Only the fields to insert for the object
		ArrayList<Field> mainObjectQueryFields = new ArrayList<Field>();

		//Remove all array types
		for (Field field : objectFields) {

			//Get the name of that type
			String type = field.getType().getName();

			//If the array has the field, add to array for main insert
			if (allowedFields.contains(type)) {
				mainObjectQueryFields.add(field);
			}
		}

		//Iterate over result set results
		for (Field field : mainObjectQueryFields) {
			field.setAccessible(true);

			try {

				//Get the value from the DB object
				Object dbValue = resultObject.getObject(field.getName());

				if (dbValue!=null) {
					//Set the value in the object
					field.set(result, dbValue);
				}
			} catch (Exception e) {
				System.err.println("Self generated " +e.getMessage());

			}
		}

		//Return the result
		return result;
	}
	
	/**
	 * Transfers a resultSet into an arrayList of results
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<Object> resultSetToArrayList(ResultSet rs) throws SQLException{

		//Get meta from resultSet
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		
		ArrayList<Object> results = new ArrayList<Object>();
		
		//Parse results
		while (rs.next()){
			
			//Results will be in a generic hashMap
			HashMap<String, Object> row = new HashMap<String, Object>(columns);
			
			//map column names to rows
			for(int i=1; i<=columns; ++i){ 
				row.put(md.getColumnName(i),rs.getObject(i));
			}
			
			results.add(row);
		}

		return results;
	}
}
