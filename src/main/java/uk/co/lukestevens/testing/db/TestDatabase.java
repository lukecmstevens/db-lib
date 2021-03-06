package uk.co.lukestevens.testing.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import uk.co.lukestevens.jdbc.AbstractDatabase;

/**
 * A test database with hardcoded credentials to use a H2
 * in memory database for testing
 * 
 * @author Luke Stevens
 */
public class TestDatabase extends AbstractDatabase {
	
	final String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE";
	final String username = "sa";
	final String password = "";
	
	@Override
	protected Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	/**
	 * Executes a sql file in the sql resources folder
	 * @param file The file name. Note the folder and .sql suffix does not need to be included.
	 * To execute /sql/setup.sql you would use the string 'setup'
	 * @throws IOException
	 * @throws SQLException
	 */
	public void executeFile(String file) throws IOException, SQLException {
		String sql = null;
		try (InputStream input = TestDatabase.class.getResourceAsStream("/sql/" + file + ".sql");
			BufferedReader reader = new BufferedReader(new InputStreamReader(input))){
				
			StringBuilder builder = new StringBuilder();
			reader.lines().forEach(line -> builder.append(line + System.lineSeparator()));	
			sql = builder.toString();
		}
		
		this.update(sql);
	}
	
	public Properties getProperties() {
		Properties props = new Properties();
		props.put("database.url", url);
		props.put("database.username", username);
		props.put("database.password", password);
		return props;
	}


}
