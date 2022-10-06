package db.utils;

import java.util.HashMap;

import javax.persistence.Persistence;

/**
 * Class that deletes all data in database
 * 
 * @author Rodrigo Branco
 * @author Vasco Lopes
 * @author Sérgio Ferreira
 */
public class CreateDatabase {

	public static void main(String[] args) {
		HashMap<String, String> properties = new HashMap<>();

		properties.put("javax.persistence.schema-generation.database.action", "drop-and-create");
		properties.put("javax.persistence.schema-generation.create-source", "metadata");
		properties.put("javax.persistence.schema-generation.drop-source", "metadata");
		properties.put("javax.persistence.sql-load-script-source", "META-INF/load-script.sql");

		Persistence.generateSchema("CSS JPA Projeto 1 051", properties);
	}

}
