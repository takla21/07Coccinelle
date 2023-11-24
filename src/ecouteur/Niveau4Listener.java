
package ecouteur;
import java.util.EventListener;
/**
 * 
 * Classe d'écouteur qui établi les interéactions entre la classe NiveauQuatre et la classe PanelDroitQuatre
 * 
 * @author Zi Zheng Wang
 * @version 1.0
 */
public interface Niveau4Listener extends EventListener {

	void quitter();
	void arreter();
	void demarrer();
	void initialiser();
	void setPotentielleElectrique(int potentielle);
	void setChampsMagnetique(int nbChamps, double champs);
	void setChampsElectrique(double champs);
	void setVitesseCoquerelles(double vitesse);
	void setMasse(double masse);
	void setCharge(double charge);
	void zoom(boolean typeZoom);
	void modeScientifique(boolean b);
	void appelCalculPeriode();
}
