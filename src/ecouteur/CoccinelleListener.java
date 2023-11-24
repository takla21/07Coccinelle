
package ecouteur;
import java.util.EventListener;
import physique.Vector2d;
/**
 * 
 * Classe d'écouteur qui établi les interéactions entre les classes des quatre niveaux et les classes des quatre pannel
 * 
 * @author Zi Zheng Wang
 * @version 3.0
 */
public interface CoccinelleListener extends EventListener {
      void niveauSwitch();
      //pour les valeurs sorties de niveaux2
   	  void getRessortVitesse(int i, double vitesse);
      void getRessortPosition(int i, double positionReelleBloc);
      void action();
      //pour les valeurs sorties de niveaux3
      void getCenter(Vector2d centre);
      void getRotation(double rotation,double sens, double vitesse);
      void getBallonAInformation(Vector2d position, double vitesse, double angle);
      void getBallonBInformation(Vector2d position, double vitesse, double angle);
      void getBallonCInformation(Vector2d position, double vitesse, double angle);
      void getBallonDInformation(Vector2d position, double vitesse, double angle);
      void coeficientCollsiion(double collsiion);

}
