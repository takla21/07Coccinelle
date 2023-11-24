
package ecouteur;
import java.util.EventListener;
/**
 * 
 * Classe d'écouteur qui établi les interéactions entre la classe NiveauUn et la calsse PanelDroitUn
 * 
 * @author Zi Zheng Wang
 * @version 1.0
 */
public interface Niveau1Listener extends EventListener {

	void quitter();
	void arreter();
	void reDemarrer();
	void initialiser();
	void setAngleRec(double radians);
	void setAnglePrisme(double radians);
	void setRefractionVert(double valeur);
	void setRefractionBleu(double valeur);
	void modeScientifique(boolean b);
	void setChargePart1(double charge);
	void setChargePart2(double charge);
	void setChargeCocc(double charge);
	void choisirNormale(int mirroir, int normale, boolean dessinable);
	void choisirNormaleBleu(int normale,boolean dessinable);
	void choisirNormaleVert(int normale,boolean dessinable);
}
