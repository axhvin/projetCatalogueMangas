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
	private static final Scanner scan = new Scanner(System.in);
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection("jdbc:sqlite:manga.db");
	}
	public static int choixMenu(){
		System.out.println("________Menu________");
		System.out.println(" 0 - Déconnexion ");
		System.out.println(" 1 - Admin ");
		System.out.println(" 2 - Utilisateur ");
		System.out.println("Entrer un choix : ");
		return scan.nextInt();
	}
	public static int choixMenu1(){
		System.out.println("________Admin________");
		System.out.println(" 0 - Déconnexion ");
		System.out.println(" 1 - Insérer un manga ");
		System.out.println(" 2 - Suppression d'un manga ");
		return scan.nextInt();
	}
	public static int choixMenu2(){
		System.out.println("________Utilisateur________");
		System.out.println(" 0 - Déconnexion ");
		System.out.println(" 1 - Recherche d'un manga ");
		return scan.nextInt();
	}
	public static void main(String[] args){
		int choix = choixMenu();
		while (choix!=0){
			switch (choix) {
				case 1:
					int choix1 = choixMenu1();
					while (choix1!=0) {
						switch (choix1) {
							case 1:
								
								break;
							case 2:

								break;
							default:
								System.out.println("Choix " + choix1 + " inconnu");
						}
					}
					break;
				case 2:
					int choix2 = choixMenu2();
					while (choix2!=0) {
						switch (choix2) {
							case 1:

								break;
						
							default:
								System.out.println("Choix " + choix2 + " inconnu");
						}
					}
					break;
				default:
					System.out.println("Choix " + choix + " inconnu");
			}
		}
		try (
			Connection connection = DriverManager.getConnection("jdbc:sqlite:manga.db");
			Statement statement = connection.createStatement();
			)
			{
				statement.executeUpdate("drop table if exists Manga");
				statement.executeUpdate("create table if not exists Manga (id INTEGER PRIMARY KEY AUTOINCREMENT, titre TEXT NOT NULL, auteur TEXT NOT NULL, tome INTEGER, prix REAL, stock INTEGER)");
				statement.executeUpdate("insert into Manga values(1,'One Piece','Eichiro Oda',1,6.90,20)");
				ResultSet resultat = statement.executeQuery("select * from Manga");
				System.out.printf("%-3s | %-20s | %-20s | %-5s | %-6s | %-5s%n", "ID", "Titre", "Auteur", "Tome", "Prix", "Stock");
				System.out.println("----+----------------------+----------------------+-------+--------+-------");
				while (resultat.next()){
					int id = resultat.getInt("id");
					String titre = resultat.getString("titre");
					String auteur = resultat.getString("auteur");
					int tome = resultat.getInt("tome");
					double prix = resultat.getDouble("prix");
					int stock = resultat.getInt("stock");
					System.out.printf("%-3d | %-20s | %-20s | %-5d | %-5.2f€ | %-5d%n", id, titre, auteur, tome, prix, stock);
				}
		} catch(SQLException e){
			e.printStackTrace(System.err);
		}
	}
}
