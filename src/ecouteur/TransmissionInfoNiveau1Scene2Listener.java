package ecouteur;

import java.util.EventListener;

import physique.Vector2d;

/**
 * Cette méthode permet de transmettre des informations de la deuxième scène du niveau 1 vers le panel droit 1 afin que l'utilisateur puisse connaitre ces informations.
 * @author Kevin Takla
 * @version 2.0
 *
 */
public interface TransmissionInfoNiveau1Scene2Listener extends EventListener{
	void getVitesseParticule1(double vitesse);
	void getVitesseParticule2(double vitesse);
	void getVitesseCoccinelle(double vitesse);
	void getChargePart(double charge, int nbPart);
	void getChargeCocc(double charge);
	void getForce(Vector2d force,int nbPart);
	void getMasse(double masse, int nbPart);
	void bloquerParametres(boolean b);
	void niveauSwitch();
}
