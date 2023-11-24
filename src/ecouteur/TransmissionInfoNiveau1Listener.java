package ecouteur;

import geometrie.BlocVitre;
import geometrie.Prisme;

import java.awt.geom.AffineTransform;
import java.util.EventListener;

/**
 * Cette classe permet de transmettre des informations de la première scène du niveau 1 vers le panel droit 1 afin que l'utilisateur puisse connaitre ces informations.
 * @author Kevin Takla
 * @version 2.0
 */

public interface TransmissionInfoNiveau1Listener extends EventListener {
	public void getPositionBlocVitre(BlocVitre blocVitre, AffineTransform matMC);
	public void getPositionPrisme(Prisme prisme, AffineTransform matMC);
	public void changementScene(boolean b);
	public void getAngleIncidentMir(int nbMir, double angle);
	public void getAngleReflechieMir(int nbMir,double angle);
	public void getAngleIncidentMilieu1(int nbMilieu, double angle);
	public void getAngleReflechiMilieu1(int nbMilieu, double angle);
	public void getAngleCritiqueMilieu(int nbMilieu, double angle);
	public void getAngleReflechiMilieu2(int nbMilieu, double angle);
	public void getAngleIncidentMilieu2(int nbMilieu, double angle);
	public void remiseRotationMilieu(int nbMilieu);
	public void bloquerParametres();
}
