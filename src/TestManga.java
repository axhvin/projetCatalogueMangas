import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

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
		System.out.println(" 2 - User ");
		System.out.println("Entrer un choix : ");
		return scan.nextInt();
	}
	public static int choixMenu1(){
		System.out.println("________Admin________");
		System.out.println(" 0 - Déconnexion ");
		System.out.println(" 1 - Insérer un manga ");
		System.out.println(" 2 - Suppression d'un manga ");
		System.out.println(" 3 - Recherche d'un manga ");
		return scan.nextInt();
	}
	public static int choixMenu2(){
		System.out.println("________User________");
		System.out.println(" 0 - Déconnexion ");
		System.out.println(" 1 - Recherche d'un manga ");
		return scan.nextInt();
	}
	public static void main(String[] args){
		int choix = choixMenu();
		try (
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			)
			{
				statement.executeUpdate("drop table if exists Manga");
				statement.executeUpdate("create table if not exists Manga (id INTEGER PRIMARY KEY AUTOINCREMENT, titre TEXT NOT NULL, auteur TEXT NOT NULL, tome INTEGER, prix REAL, stock INTEGER)");
		} catch(SQLException e){
			e.printStackTrace(System.err);
		}
		while (choix!=0){
			switch (choix) {
				case 1:
					scan.nextLine();
					System.out.println("Mot de passe à saisir : ");
					String s = scan.nextLine();
					if (s.equals(System.getenv("MDP"))){
						int choix1 = choixMenu1();
						while (choix1!=0) {
							switch (choix1) {
								case 1:
									System.out.println("Entrez le titre d'un manga : ");
									String titre = scan.nextLine();
									System.out.println("Entrez le nom d'un auteur : ");
									String auteur = scan.nextLine();
									System.out.println("Entrez le numéro du tome : ");
									int tome = scan.nextInt();
									System.out.println("Entrez le prix du manga : ");
									double prix = scan.nextDouble();
									System.out.println("Entrez le stock du manga : ");
									int stock = scan.nextInt();
									try (
										Connection connection = getConnection();
										PreparedStatement pstmt = connection.prepareStatement("insert into Manga values (?, ?, ?, ?, ?)")
										)
										{
											pstmt.setString(1, titre);
											pstmt.setString(2, auteur);
											pstmt.setInt(3, tome);
											pstmt.setDouble(4, prix);
											pstmt.setInt(5, stock);
											pstmt.executeUpdate();
									} catch(SQLException e){
										e.printStackTrace(System.err);
									}
									break;
								case 2:
									System.out.println("Entrez l'identifiant du manga ; ");
									int id = scan.nextInt();
									try (
										Connection connection = getConnection();
										PreparedStatement pstmt = connection.prepareStatement("delete from Manga where id=?");
										)
										{
											pstmt.setInt(1, id);
											pstmt.executeUpdate();
									} catch(SQLException e){
										e.printStackTrace(System.err);
									}
									break;
								case 3:

									break;
								default:
									System.out.println("Choix " + choix1 + " inconnu");
							}
							choix1 = choixMenu1();
						}
					}
					else{
						System.out.println("Mot de passe incorrect");
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
						choix2 = choixMenu2();
					}
					break;
				default:
					System.out.println("Choix " + choix + " inconnu");
			}
			choix = choixMenu();
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
