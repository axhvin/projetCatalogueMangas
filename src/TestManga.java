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
		System.out.println(" 1 - Catalogue");
		System.out.println(" 2 - Recherche d'un manga ");
		return scan.nextInt();
	}
	public static void main(String[] args){
		int choix = choixMenu();
		try (
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			)
			{
				statement.executeUpdate("create table if not exists Manga (id INTEGER PRIMARY KEY AUTOINCREMENT, titre TEXT NOT NULL, auteur TEXT NOT NULL, tome INTEGER, prix REAL, stock INTEGER, unique(titre, tome))");
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
									scan.nextLine();
									System.out.println("Entrez le titre d'un manga : ");
									String titre = scan.nextLine();
									System.out.println("Entrez le nom d'un auteur : ");
									String auteur = scan.nextLine();
									System.out.println("Entrez le numéro du tome : ");
									int tome = scan.nextInt();
									scan.nextLine();
									System.out.println("Entrez le prix du manga : ");
									double prix = scan.nextDouble();
									scan.nextLine();
									System.out.println("Entrez le stock du manga : ");
									int stock = scan.nextInt();
									scan.nextLine();
									try (
										Connection connection = getConnection();
										PreparedStatement pstmt = connection.prepareStatement("insert into Manga(titre, auteur, tome, prix, stock) values (?, ?, ?, ?, ?)")
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
									scan.nextLine();
									break;
								case 3:
									scan.nextLine();
									System.out.println("Entrez le titre d'un manga : ");
									String titre1 = scan.nextLine();
									System.out.println("Entrez le numéro du tome (-1 pour tous les afficher) : ");
									int tome1 = scan.nextInt();
									String sql = "select * from Manga where lower(titre) like lower(?)";
									boolean hasTome = (tome1!=-1);
									if (hasTome){
										sql += " and tome = ?";
									}
									try (
										Connection connection = getConnection();
										PreparedStatement pstmt = connection.prepareStatement(sql);
										)
										{
											pstmt.setString(1, "%" + titre1 + "%");
											if (hasTome){
												pstmt.setInt(2, tome1);
											}
											ResultSet resultat = pstmt.executeQuery();
											System.out.printf("%-3s | %-20s | %-20s | %-5s | %-6s | %-5s%n", "ID", "Titre", "Auteur", "Tome", "Prix", "Stock");
											System.out.println("----+----------------------+----------------------+-------+--------+-------");
											while (resultat.next()){
												int id2 = resultat.getInt("id");
												String titre2 = resultat.getString("titre");
												String auteur2 = resultat.getString("auteur");
												int tome2 = resultat.getInt("tome");
												double prix2 = resultat.getDouble("prix");
												int stock2 = resultat.getInt("stock");
												System.out.printf("%-3d | %-20s | %-20s | %-5d | %-5.2f€ | %-5d%n", id2, titre2, auteur2, tome2, prix2, stock2);
											}
									} catch(SQLException e){
										e.printStackTrace(System.err);
									}
									scan.nextLine();
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
								scan.nextLine();
								try (
									Connection connection = getConnection();
									Statement statement = connection.createStatement();
								    )
									{
									ResultSet resultat = statement.executeQuery("select * from Manga");
									System.out.printf("%-3s | %-20s | %-20s | %-5s | %-6s | %-5s%n", "ID", "Titre", "Auteur", "Tome", "Prix", "Stock");
									System.out.println("----+----------------------+----------------------+-------+--------+-------");
									while (resultat.next()){
										int id2 = resultat.getInt("id");
										String titre2 = resultat.getString("titre");
										String auteur2 = resultat.getString("auteur");
										int tome2 = resultat.getInt("tome");
										double prix2 = resultat.getDouble("prix");
										int stock2 = resultat.getInt("stock");
										System.out.printf("%-3d | %-20s | %-20s | %-5d | %-5.2f€ | %-5d%n", id2, titre2, auteur2, tome2, prix2, stock2);
									}	
								} catch(SQLException e){
									e.printStackTrace(System.err);
								}
								scan.nextLine();
								break;
							case 2:
								scan.nextLine();
								System.out.println("Entrez le titre d'un manga : ");
								String titre1 = scan.nextLine();
								System.out.println("Entrez le numéro du tome (-1 pour tous les afficher) : ");
								int tome1 = scan.nextInt();
								String sql = "select * from Manga where lower(titre) like lower(?)";
								boolean hasTome = (tome1!=-1);
								if (hasTome){
									sql += " and tome = ?";
								}
								try (
									Connection connection = getConnection();
									PreparedStatement pstmt = connection.prepareStatement(sql);
									)
									{
										pstmt.setString(1, "%" + titre1 + "%");
										if (hasTome){
											pstmt.setInt(2, tome1);
										}
										ResultSet resultat = pstmt.executeQuery();
										System.out.printf("%-3s | %-20s | %-20s | %-5s | %-6s | %-5s%n", "ID", "Titre", "Auteur", "Tome", "Prix", "Stock");
										System.out.println("----+----------------------+----------------------+-------+--------+-------");
										while (resultat.next()){
											int id2 = resultat.getInt("id");
											String titre2 = resultat.getString("titre");
											String auteur2 = resultat.getString("auteur");
											int tome2 = resultat.getInt("tome");
											double prix2 = resultat.getDouble("prix");
											int stock2 = resultat.getInt("stock");
											System.out.printf("%-3d | %-20s | %-20s | %-5d | %-5.2f€ | %-5d%n", id2, titre2, auteur2, tome2, prix2, stock2);
										}
								} catch(SQLException e){
									e.printStackTrace(System.err);
								}
								scan.nextLine();
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
	}
}
