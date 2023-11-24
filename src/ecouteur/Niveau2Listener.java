package ecouteur;
import java.util.EventListener;
/**
 * 
 * Classe d'écouteur qui établi les interéactions entre la classe NiveauDeux et la calsse PanelDroitDeux
 * 
 * @author Zi Zheng Wang
 * @version 1.0
 */
public interface Niveau2Listener extends EventListener {

	void quitter();
	void arreter();
	void demarrer(int a);
	void initialiser();
    void setMasse(int a, double masse);
    void setVitesse(int a, double vitesse);
    void setConstantRessort(int a, double constant);
    void setCoeficient(int a, double coeficient);
    void setDeplacement(int a, double deplacement);
    void setDeltaTemps(double temps);
	void modeScientifique(boolean b);
	void setCharge(int a,int charge);
    
	

}
