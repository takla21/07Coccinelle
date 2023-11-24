package ecouteur;

import java.util.EventListener;

import physique.Vector2d;
import physique.Vector3d;
/**
 * Cette méthode permet de connaitre tout les informations possibles du niveau 4
 * @author Kevin Takla
 * @version 2.0
 */
public interface TransmissionInfoNiveau4Listener extends EventListener {
	void getVitesseCoccinelle(double vitesse);
	void getForceElectrique(Vector3d forceElectrique);
	void getForceMagnetique(int nbChamps,Vector3d forceMagnetique);
	void getSelecteurVitesse(double vitesse);
	void getRayonIdeale(double rayon);
	void getChampsMagnetique(double champsMagnetique);
	void getPosCocc(Vector2d position);
	void getPeriode(double periode);
	void getObjetSelectionne(String nom);
	void getAcceleration(double acceleration);
	void animation(boolean b);
	void jeuxTerminer();
}
