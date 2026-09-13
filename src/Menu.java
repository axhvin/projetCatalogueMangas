import java.util.Scanner;

public class Menu{
    public static int choixMenu(Scanner scan){
		System.out.println("________Menu________");
		System.out.println(" 0 - Déconnexion ");
		System.out.println(" 1 - Admin ");
		System.out.println(" 2 - User ");
		System.out.println("Entrer un choix : ");
		return scan.nextInt();
	}
	public static int choixMenu1(Scanner scan){
		System.out.println("________Admin________");
		System.out.println(" 0 - Déconnexion ");
		System.out.println(" 1 - Insérer un manga ");
		System.out.println(" 2 - Suppression d'un manga ");
		System.out.println(" 3 - Recherche d'un manga ");
		return scan.nextInt();
	}
	public static int choixMenu2(Scanner scan){
		System.out.println("________User________");
		System.out.println(" 0 - Déconnexion ");
		System.out.println(" 1 - Catalogue");
		System.out.println(" 2 - Recherche d'un manga ");
		return scan.nextInt();
	}
}