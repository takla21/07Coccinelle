package ecouteur;
import java.util.EventListener;
/**
 * 
 * Classe d'écouteur qui établi les interéactions entre le nvieau load et le panel load
 * 
 * @author Zi Zheng Wang
 * @version 3.0
 */
public interface NiveauLoadListener extends EventListener{
	void quitter();
	void arreter();
	void demarrer(int a);
	void initialiser();
	void effetBoussole(boolean afficherBoussole);
	void setMasse(int a, double masse);
    void setVitesse(int a, double vitesse);
    void setConstantRessort(int a, double constant);
    void setCoeficient(int a, double coeficient);
    void setDeplacement(int a, double deplacement);
	void scientifique(boolean b);
	void inverseCharge();
}
