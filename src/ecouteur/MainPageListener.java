
package ecouteur;
import java.util.ArrayList;
import java.util.EventListener;
/**
 * 
 * Classe d'écouteur qui établi les interéactions entre les classes des quatre niveaux et la classe APanelPincipal; le menu main
 * 
 * @author Zi Zheng Wang
 * @version 3.0
 */
public interface MainPageListener extends EventListener{
	void retournerAuMenuP();
	void activerBTableauJeu();
	void activerCEditeur();
	void activerOption();
	void envoyerCollectioNNom(ArrayList<String> collectionNom);
}
