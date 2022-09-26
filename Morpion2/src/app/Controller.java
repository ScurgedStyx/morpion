package app;

import static app.Constantes.*;
import static app.Joueurs.*;
import static app.Cellule_Etat.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
/**
 * 
 * @author Styx6
 * Controller est la classe qui regroupe toutes les méthodes liées au fonctionnement du jeu de morpion.
 * Elle comprend les méthodes :
 * 
 * initialize qui initialise le contexte graphique;
 * initialiser qui initialise le jeu de morpion;
 * start qui permet de lancer le jeu de morpion;
 * reinitialiser qui permet de réinitialiser l'ensemble du jeu en vidant la grille;
 * getClic qui permet au jeu de prendre en considération les clics de souris du joueur HUMAIN afin de les placer sur la grille;
 * updateGagnant qui permet d'afficher le nom de gagnant une fois la partie terminée.
 * 
 */
public class Controller {
	/**
	 * Les variable correspondant aux variables de la vue via Scenebuilder.
	 */
	@FXML
	private Canvas           tableau;
	@FXML
	private CheckBox         ordinateur;
	@FXML
	private CheckBox         humain;
	@FXML
	private Label            gagnant;

	/*
	 * Comme je vais dessiner ma grille de morpion sur un canvas, j'ai besoin d'un contexte graphique introduit ci-après:
	 */
	private GraphicsContext  gc;

	private Game             jeu;

	  
	public Controller() {
		  
	}
	
	@FXML
	public void initialize() {
		/*
		 * initialisation du contexte graphique:
		 */
		gc = tableau.getGraphicsContext2D();
	
	}
	
	@FXML
	public void initialiser() {
		/*
		 * Initialisation du jeu de morpion
		 *  La méthode initialiser correspond au bouton initialiser présent dans l'interface graphique.
		 */
		
		var premierjoueur = humain.isSelected() ? HUMAIN : ORDINATEUR;
		
		
		jeu = new Game(premierjoueur);
		jeu.gagnant.addListener((obs, old, newv) -> updateGagnant());
		jeu.pos.addListener((obs, old, newv) -> View.dessinerCoup(View.croix, gc, jeu.cellule.numero ));
		tableau.setOnMouseClicked(this::getClics);
		View.dessinerTableau(gc);
	}
	
	@FXML
	public void start() {
		/*
		 * La méthode start permet le lancement du jeu de morpion
		 */
		
		var a = new Thread(jeu);
		
		a.start();
	}
	
	@FXML
	public void reinitialiser() {
		/*
		 * La méthode reinitialiser permet de réinitialiser le jeu de morpion et vider la grille
		 */
		 
		jeu.fin = true;
		jeu.coups = FIN;
		jeu.gagnant.set("--------");
		jeu.cancel();
		gc.clearRect(0, 0, L_TABLEAU, L_TABLEAU);
	}
	  
	public void getClics(MouseEvent e) {
		/*
		 * à chaque clique sur la grille, on va récupérer le numéro des cases par rapport aux clics
		 * puis grce à la fonction dessinerClic on va placer correctement l'image en fonction 
		 * du numéro de la ligne et du numéro de la colonne par rapport au clic
		 */
		/**
		 * On récupère la coordonée x/y du clic de la souris, 
		 * si elle est inférieur à la largeur d'une case alors on sera sur la ligne/colonne n°0.
		 * sinon si le clic est inférieur à 500 ( deux cases ) alors on sera sur la ligne/colonne n°1.
		 * et sinon sur la n°3 ( supéreur à 500).
		 * Car on a défini la L_case dans l'énum Invariants avec la valeur 250.
		 */
		if(!jeu.fin) {
			int i = (e.getX() < L_CASE) ? 0 : (e.getX() < 500) ? 1 : 2;
			int j = (e.getY() < L_CASE) ? 0 : (e.getY() < 500) ? 1 : 2;
			
			jeu.coups = ORDINATEUR;
			jeu.tableau.grille[j * 3 + i].etat = O;
			jeu.tableau.update(j * 3 + i, O);
			View.dessinerCoup(View.cercle, gc, i, j);
		}
	}
	
	private void updateGagnant() {
		Platform.runLater(() -> gagnant.setText(jeu.gagnant.get()));
	}
	
	
}
