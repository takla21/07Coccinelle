 
package physique;

/** la classe Derivee de physique, permet de créer l'objet pour la dérivation 
 * @author Mathieu Payette; Implanter par Zi Zheng Wang
 * @version 1.0
 */
public class Derivee {
        public double dp;       // delta position
        public double dv;       // delta vitesse
        /**
         *  Constructeur de la classe Derivee, permet de créer l'objet Derivée qui contient deux paramètres : delta position et delta temps
         * @param dp - la position dériviée
         * @param dv - le temps dérivié
         */
        public Derivee(double dp, double dv){
                this.dp = dp;
                this.dv = dv;
        }
}
