import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** 
 * Cette classe va gérer le test de Manga.
 * @author Thayananthan Axhvin
 */
 
public class TestManga{
	public static void main(String[] args){
		try (
			Connection connection = DriverManager.getConnection("jdbc:sqlite:manga.db");
			Statement statement = connection.createStatement();
			)
			{
				statement.executeUpdate("drop table if exists Manga");
				statement.executeUpdate("create table if not exists Manga (id INTEGER PRIMARY KEY AUTOINCREMENT, titre TEXT NOT NULL, auteur TEXT NOT NULL, tome INTEGER, prix REAL, stock INTEGER)");
				statement.executeUpdate("insert into Manga values(1,'One Piece','Eichiro Oda',1,6.90,20)");
				ResultSet resultat = statement.executeQuery("select * from Manga");
				while (resultat.next()){
					int id = resultat.getInt("id");
					String titre = resultat.getString("titre");
					String auteur = resultat.getString("auteur");
					int tome = resultat.getInt("tome");
					double prix = resultat.getDouble("prix");
					int stock = resultat.getInt("stock");
					System.out.println(titre + " tome " + tome + " par " + auteur + " prix : " + prix + "euros (stock : " + stock + ")");
				}
		} catch(SQLException e){
			e.printStackTrace(System.err);
		}
	}
}
