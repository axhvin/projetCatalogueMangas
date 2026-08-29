/**
 * Cette classe sert à initialiser un manga avec ses références.
 * @author Thayananthan Axhvin
 */

public class Manga{
	private String titre;
	private String auteur;
	private int numeroTome;
	private double prix;
	private int stock;
	private static int cpt = 0;
	public final int id;
	public Manga(String titre, String auteur, int numeroTome, double prix, int stock){
		this.titre = titre;
		this.auteur = auteur;
		this.numeroTome = numeroTome;
		this.prix = prix;
		this.stock = stock;
		cpt++;
		id = cpt;
	}
	public String getTitre(){
		return titre;
	}
	public String getAuteur(){
		return auteur;
	}
	public int getNumeroTome(){
		return numeroTome;
	}
	public double getPrix(){
		return prix;
	}
	public void setPrix(double p){
		prix = p;
	}
	public int getStock(){
		return stock;
	}
	public void setStock(int s){
		stock = s;
	}
	public String toString(){
		return titre + " tome " + numeroTome + " par " + auteur + " prix : " + String.format("%.2f",prix) + "euros (stock:" + stock + ")";
	}
}
