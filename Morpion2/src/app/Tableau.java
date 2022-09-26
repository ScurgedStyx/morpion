package app;

import static app.Constantes.*;
import static app.Cellule_Etat.*;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * La classe Tableau permet de gérer le fonctionnement de fin de partie du jeu à savoir les lignes les colonnes et les diagonales.
 * Et également les coups joués sur la grille.
 * La méthode update met à jour les coups.
 * La méthode verifGagnant permet de voir quand trois symboles sont alignés ( fin de partie ).
 * 
 * @author Styx6
 *
 */
public class Tableau {
	Cellule [ ] grille ;
	int [ ] lignes ;
	int [ ] colonnes ;
	int lDiag ;
	int rDiag ;
	
	
	public Tableau( ) {
	  grille = new Cellule [ nombredecellules ];
	  lignes = new int [ nombredecases ];
	  colonnes = new int [ nombredecases ];
	  lDiag = 0 ;
	  rDiag = 0 ;
	  Stream.iterate ( 0 , n -> n + 1 ).limit( nombredecellules).forEach( i -> grille[ i ] = new Cellule ( i , V ) ) ;
	}
	public void update( int num, Cellule_Etat etat) {
	  int c = num % 3 ;
	  int r = num / 3 ;
	  lignes [ r ] += etat.val ;
	  colonnes [ c ] += etat.val ;
	  lDiag += ( r == c ) ? etat.val : 0 ;
	  rDiag += ( r + c == nombredecases - 1 ) ? etat.val : 0 ;
	}
	public boolean  verifGagnant() {
	  return ( Arrays.stream ( lignes).filter ( v -> Math.abs ( v ) == nombredecases ).count ( ) == 1 )
	        ||( Arrays.stream ( colonnes ).filter ( v -> Math.abs ( v ) == nombredecases ).count ( ) == 1 ) || ( Math.abs ( lDiag ) == nombredecases )
	        ||( Math.abs ( rDiag ) == nombredecases );
	}
}
