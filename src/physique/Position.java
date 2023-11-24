
package physique;

import java.io.Serializable;

/** la classe Position de physique, permet de cr�er l'objet de position
 * @author Mathieu Payette; Implanter par Zi Zheng Wang
 * @version 1.0
 */
public class Position implements Serializable{
	private static final long serialVersionUID = 2679499706842035605L;
		public double pos;      // position
        public double v;        // vitesse
        public double a;        // acceleration
        /**
         * Constructeur de la classe Position qui permet de cr�er l'objet Position qui contient deux param�tres : la position et la vitesse
         * @param pos - la position 
         * @param v - la vitesse
         */
        public Position(double pos, double v){
        	this.pos = pos;
        	this.v = v;
        	a = 0;
        }
        /**
         * M�thode reset qui permet de modifier la valeur de position, vitesse et acc�l�ration par d�faut (d�faut = 0).
         */
        public void reset() {
        	this.pos = 0;
        	this.v = 0;
        	this.a = 0;
        }
}