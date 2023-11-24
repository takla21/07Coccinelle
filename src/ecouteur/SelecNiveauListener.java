package ecouteur;

import java.util.ArrayList;
import java.util.EventListener;
/**
 * 
 * Classe d'�couteur qui �tabli les inter�actions entre le s�lecteur de niveau et les Jframes de aaplication
 * 
 * @author Zi Zheng Wang
 * @version 3.0
 */
public interface SelecNiveauListener extends EventListener{
	void selectionNiveau(int nbNiv);
	void load();
	void loadParDefaut(String c);
	void returnMenu();
	void creationNiveau();
	void envoyerListNom(ArrayList<String> collectionNom);
}
