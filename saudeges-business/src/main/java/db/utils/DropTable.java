package db.utils;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DropTable {

	public void resetDB() throws IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("CSS JPA Projeto 1 051");
		new RunSQLScript().runScript(emf, "META-INF/drop-table.sql");
		emf.close();
	}

	public static void main(String[] args) throws IOException {
		new DropTable().resetDB();
	}

}
