 
package physique;

/** la classe Derivee de physique, permet de cr�er l'objet pour la d�rivation 
 * @author Mathieu Payette; Implanter par Zi Zheng Wang
 * @version 1.0
 */
public class Derivee {
        public double dp;       // delta position
        public double dv;       // delta vitesse
        /**
         *  Constructeur de la classe Derivee, permet de cr�er l'objet Deriv�e qui contient deux param�tres : delta position et delta temps
         * @param dp - la position d�rivi�e
         * @param dv - le temps d�rivi�
         */
        public Derivee(double dp, double dv){
                this.dp = dp;
                this.dv = dv;
        }
}
