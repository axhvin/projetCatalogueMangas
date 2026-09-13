import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection{
    public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection("jdbc:sqlite:manga.db");
	}
}