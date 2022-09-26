package app;

import static app.Constantes.*;

import java.util.stream.IntStream;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * La classe View comprend les méthodes liées à la grille de morpion
 * @author Styx6
 *
 */
public class View {

	static Image croix  = new Image("file:ressources/croix.png", 240, 240, false, false);
	static Image cercle = new Image("file:ressources/cercle.png", 240, 240, false, false);

	private View() {
		
	}

	public static void dessinerCoup(Image img, GraphicsContext gc, int x, int y) {
		gc.drawImage(img, x * L_CASE + 5, y * L_CASE + 5);
	}

	public static void dessinerCoup(Image img, GraphicsContext gc, int num) {
		int x = num % 3;
	    int y = num / 3;

	    gc.drawImage(img, x * L_CASE + 5, y * L_CASE + 5);
	     }

	public static void dessinerTableau(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.setLineWidth(1);
		gc.clearRect(0, 0, L_TABLEAU, L_TABLEAU);
		gc.setFill(Color.BLACK);
		IntStream.range(1, 3).forEach(i -> gc.strokeLine(i * L_CASE, 0, i * L_CASE, L_TABLEAU));
		IntStream.range(1, 3).forEach(j -> gc.strokeLine(0, j * L_CASE, L_TABLEAU, j * L_CASE));
	}
	

}
