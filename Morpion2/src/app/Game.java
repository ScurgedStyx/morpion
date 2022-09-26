package app;

import static app.Constantes.*;
import static app.Joueurs.*;
import static app.Cellule_Etat.*;

import java.util.Arrays;
import java.util.Random;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

/**
 * La classe Game sert à instancer le fonctionnement du jeu et sa logique, 
 * par exemple quand la partie se termine, 
 * et savoir qui a gagné.
 * Elle comprend une méthode finJeu qui permet de savoir quand la partie est terminée.
 * Une méthode tourHumain correspondant au tour du joueur non-machine
 * Une méthode tourMachine qui permet à la machine de jouer.
 * Ainsi qu'une méthode call qui permet de gérer la logique du jeu de morpion.
 * @author Styx6
 *
 */
public class Game extends Task<Void> {
	
	StringProperty gagnant;
	IntegerProperty pos;
	
	Tableau tableau;
	Cellule cellule;
	boolean fin;
	Joueurs coups;
	int nombredecoups;
	Random rand;

	public Game(Joueurs premierjoueur) {
		
		gagnant = new SimpleStringProperty();
		pos = new SimpleIntegerProperty();
		cellule = new Cellule(-1, V);
		tableau = new Tableau();
		fin = false;
		coups = premierjoueur;
		nombredecoups = NB_COUPS;
		rand = new Random();
		
	}
	
	public boolean finJeu() {
		/*
		 *Le jeu peut s'arrêter si :
		 * on a joué 9 coups
		 * on a une ligne, une diagonale, une colonne pleine avec le même symbole. 
		 */
		fin = tableau.verifGagnant() || nombredecoups == 0;
		
		return fin;
	}
	
	public void tourHumain() {
		/*
		 * dans cette méthode on utilise une boucle while 
		 * pour dire que, tant que c'est à l'humain de jouer, on fait une pause
		 */
		while (coups == HUMAIN) {
			try {
				Thread.sleep(100);	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		

	}
	
	public void tourMachine() {
		/*
		 * La méthode tourMachine va permettre à l'ordinateur de jouer.
		 * On commence par instancer une variable cellulevide qui va contenir les cellules qui n'ont pas été jouées.
		 * Pour ce faire on utilise un stream sur la grille du morpion et un filtre pour vérifier les cellules qui sont vide
		 * Vide = V ( cf. Enum Cellule_Etat) et on renvoie une liste
		 */
		var celluleVide = Arrays.stream(tableau.grille).filter(c -> c.etat == V).toList();
		/*
		 * La cellule vide selectionnée au hasard grace au rand va être rééditée par le string X.
		 * ensuite on met à jou les différents paramètres correspondant à savoir la postion du X sur la grille
		 * et on change le tour.
		 */
		
		cellule = celluleVide.get(rand.nextInt(celluleVide.size()));
		cellule.etat = X;
		pos.set(cellule.numero);
		tableau.update(cellule.numero, X);
		coups = HUMAIN;
	}

	@Override
	protected Void call() throws Exception {
		// TODO Auto-generated method stub
		
		/*
		 * Dans cette methode on va implémenter la boucle du jeu de morpion 
		 * On va préférer utiliser une boucle do while pour réduire la complexité du code
		 * Tant que la partie n'est pas terminée, Soit l'humain joue, Soit l'ordinateur joue.
		 */
		do {
			switch(coups) {
			
			case HUMAIN:
				tourHumain();
				break;
			case ORDINATEUR:
				tourMachine();
				break;
			default:
				break;
				}
			
		} while (!finJeu());
		gagnant.set(coups == ORDINATEUR ? "HUMAIN" : "ORDINATEUR");
		return null;
	
			
	}
	

}
