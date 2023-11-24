package ecouteur;
import java.util.EventListener;
/**
 * 
 * Classe d'écouteur qui établi les interéactions entre la classe NiveauTrois et la classe PanelDroitTrois
 * 
 * @author Zi Zheng Wang
 * @version 1.0
 */
public interface Niveau3Listener extends EventListener {

	void quitter();
	void arreter();
	void demarrer();
	void initialiser();
	void setSens(int a );
	void setVitesse(int b);
	void setCoefficientRes(int a);
    void setBallonMasse(int a,double b);
	void setImpulsion(double b);
	void setScientifique(boolean b);
	void setSlowMotion(double temps);
}