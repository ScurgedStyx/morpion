package app;
/**
 * Enumération des paramètres liés à une cellule.
 * @author Styx6
 *
 */
public enum Cellule_Etat {

	X(-1), O(1), V(0);
	
	int val;
	
	private Cellule_Etat(int val) {
		this.val = val;
	}
}