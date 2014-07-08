package settings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class ProjectSettings {

	//toggle behavior of server for production or development
	public static final Boolean launchForProduction = false;
	
	//whatever is used as the primary key for the objects in the database
	//they should all be the same so we can avoid text parsing
	public static final String sqlObjectPrimaryKeyName = "id";
	
	//these are the allowed fields to be parsed in the sql manager.
	//These should reflect every possible type of variable the sql database will encounter
	public static final Set<String> allowedFields = new HashSet<String>(Arrays.asList("int", "java.lang.String", "double", "java.lang.Double", "java.lang.Integer"));
}
