
package physique;

import java.io.Serializable;

/* Algorithme RK4 adapte � notre application */

/** la classe RungeKutta de physique, permet de calculer la position d'un objet en mouvement � un temps interm�diaire entre deux temps r�el
 * @author Mathieu Payette; Implanter par Zi Zheng Wang
 * @version 1.0
 */
public class RungeKutta implements Serializable{
	private static final long serialVersionUID = 1163496844980203528L;
		public RungeKutta() {}
        /**
         * M�thode integrate qui permet de faire le calcul int�gral pour trouver la prosiiton � un delta temps
         * @param position - position initial
         * @param t - le temps r�el
         * @param dt - delta temps, le temps interm�diaire entre deux temps r�el
         * @param acceleration - l'acc�l�ration de l'objet
         * @return la position qui correspond � un delta temps
         */
        public Position integrate(Position position, double t, double dt, double acceleration) {
                Derivee a = evaluate(position, t, 0, new Derivee(0, 0), acceleration);
                Derivee b = evaluate(position, t + dt * 0.5, dt * 0.5, a, acceleration);
                Derivee c = evaluate(position, t + dt * 0.5, dt * 0.5, b, acceleration);
                Derivee d = evaluate(position, t + dt, dt, c, acceleration);
                
                double dpdt = 1.0 / 6.0 * (a.dp + 2.0 * (b.dp + c.dp) + d.dp);
                double dvdt = 1.0 / 6.0 * (a.dv + 2.0 * (b.dv + c.dv) + d.dv);
                
                position.pos += dpdt * dt;
                position.v += dvdt * dt;
                
                return position;
        }
        /**
         * M�thode pour calculer l'acc�l�ration d'un objet � un temps t et � une position
         * @param position - la position d'un objet
         * @param t - le temps
         * @return l'acc�l�ration de l'objet
         */
        public double acceleration(Position position, double t) {
                double f = position.a;
                System.out.println(position.a);
                return f;
        }
        /**
         * M�thode pour calculer la valeur de delta position et delta temps pour l'objet Derivee
         * @param initiale - objet de type Position
         * @param t - le temps r�el
         * @param dt - le delta temps
         * @param d - la d�rivation
         * @param acceleration - l'acc�l�ration de l'objet
         * @return un objet de Derivee qui contient la nouvelle position et la nouvelle vitesse
         */
        public Derivee evaluate(Position initiale, double t, double dt, Derivee d, double acceleration) {
                Position position = new Position(initiale.pos + d.dp * dt, initiale.v + d.dv * dt);
                return new Derivee(position.v, acceleration);
        }
}