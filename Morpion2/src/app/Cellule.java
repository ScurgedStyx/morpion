package app;
/**
 * La classe Cellule comprend les paramètres associés à chaque cellule par rapport à l'énumération Cellule_Etat.
 * @author Styx6
 *
 */
public class Cellule {
	
	int numero;
	Cellule_Etat etat;
	public Cellule(int numero, Cellule_Etat etat) {
		super();
		this.numero = numero;
		this.etat = etat;
	}
	

}
