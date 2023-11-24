/**
 * 
 * Classe de géometrie qui permet de créer la charge 
 * 
 * @author Kevin Takla ;collaborateur Zi Zheng Wang
 * @version 1.0
 */
package geometrie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.io.Serializable;
import physique.Vector2d;

public class Particules implements Serializable{
	private static final long serialVersionUID = -2703880278476404834L;
	private final double DIAMETRE = 3;
	private final double DISTANCE_LIGNE = DIAMETRE/4;
	private Ellipse2D.Double particule;
	private Path2D.Double ligneHor,ligneVer;
	private final double CHARGE_PARTICULE_ELEMENTAIRE = 1.6*Math.pow(10,-19);
	private double charge = -CHARGE_PARTICULE_ELEMENTAIRE;
	private double posX, posY, posXIni, posYIni;
	private Shape particuleRetourner;
	private Vector2d vitesse;
	private final double MASSE_PARTICULE_POSITIF = 1.67*Math.pow(10,-27);
	private final double MASSE_PARTICULE_NEGATIF = 9.11*Math.pow(10,-31);
	private Vector2d posAvant;
	private VecteurGraphique fleche;
	private boolean particuleCible = false;
	private boolean particuleCollision = false;
	private double chargePrece = charge;

	/**
	 *  C'est le constructeur de la classe Particules
	 * @param posX la position de la particule en X.
	 * @param posY la position de la particule en Y.
	 */
	public Particules(double posX,double posY){
		this.posX = posX;
		this.posY = posY;
		this.posXIni = posX;
		this.posYIni = posY;
		posAvant = new Vector2d(posX,posY);
		vitesse = new Vector2d();
		creerParticules();
	}
	/**
	 * Cette méthode permet de créer la particule
	 */
	private void creerParticules(){
		particule = new Ellipse2D.Double(posX - DIAMETRE/2,posY - DIAMETRE/2,DIAMETRE,DIAMETRE);
		ligneHor = new Path2D.Double();
		ligneHor.moveTo(posX- DIAMETRE/2 +DISTANCE_LIGNE, posY);
		ligneHor.lineTo(posX- DIAMETRE/2 +DIAMETRE-DISTANCE_LIGNE,posY);
		ligneVer = new Path2D.Double();
		ligneVer.moveTo(posX ,posY - DIAMETRE/2 + DISTANCE_LIGNE);
		ligneVer.lineTo(posX ,posY - DIAMETRE/2 + DIAMETRE - DISTANCE_LIGNE );
	}

	/**
	 * Cette méthode permet de dessiner la particule
	 * @param g2d le contexte graphique
	 * @param matMC la transformation vers le monde réel
	 * @param charge la charge de la particule
	 */
	public void dessiner(Graphics2D g2d, AffineTransform matMC, String charge){
		AffineTransform matLocal = new AffineTransform(matMC);
		Color couleurCourant = g2d.getColor();
		g2d.fill(matLocal.createTransformedShape(particule));
		g2d.setStroke(new BasicStroke(2));
		particuleRetourner = matLocal.createTransformedShape(particule);
		g2d.draw(particuleRetourner);
		g2d.setColor(Color.white);
		if(! charge.equals("neutre")){
			if(charge.equals("négative")){
				g2d.setColor(Color.red);
			}
			if(charge.equals("positive")){
				g2d.setColor(Color.cyan);
				g2d.draw(matLocal.createTransformedShape(ligneVer));
			}
			g2d.draw(matLocal.createTransformedShape(ligneHor));
		}
		g2d.draw(particuleRetourner);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(couleurCourant);	
	}
	/**
	 * Cette méthode permet de dessiner la particule lorsquòn connait l'état du niveau(mode scientifique ou non).
	 * @param g2d le contexte graphique
	 * @param matMC la transformation vers le monde réel
	 * @param scientifique l'état du niveau(mode scientifique ou non)
	 */
	public void dessiner(Graphics2D g2d, AffineTransform matMC,boolean scientifique){
		AffineTransform matLocal = new AffineTransform(matMC);
		Color couleurCourant = g2d.getColor();
		if(!particuleCible){
			if(particuleCollision){
				g2d.setColor(Color.blue);
			}
			g2d.fill(matLocal.createTransformedShape(particule));
			g2d.setStroke(new BasicStroke(2));
			particuleRetourner = matLocal.createTransformedShape(particule);
			g2d.draw(particuleRetourner);
			g2d.setColor(Color.white);
			if(charge !=0){
				if(charge < 0){
					g2d.setColor(Color.red);
				}else{
					g2d.setColor(Color.cyan);
					g2d.draw(matLocal.createTransformedShape(ligneVer));
				}
				g2d.draw(matLocal.createTransformedShape(ligneHor));
			}
		}else{
			g2d.setColor(Color.green);
			g2d.fill(matLocal.createTransformedShape(particule));
			g2d.setColor(Color.RED);
			g2d.draw(matLocal.createTransformedShape(particule));
		}
		g2d.draw(particuleRetourner);
		g2d.setStroke(new BasicStroke(1));
		if(scientifique && !particuleCible){
			fleche = new VecteurGraphique(posAvant, new Vector2d(posX,posY));
			fleche.setNorme(3);
			g2d.setColor(Color.gray);
			fleche.dessiner(g2d, matLocal, Color.gray);
		}
		g2d.setColor(couleurCourant);	
	}
	/**
	 * Cette méthode permet de connaitre la position horizontale de la particule.
	 * @return la position en x de la particule.
	 */
	public double getX(){
		return posX;
	}
	/**
	 * Cette méthode permet de connaitre la position verticale de la particule.
	 * @return la position en y de la particule.
	 */
	public double getY(){
		return posY;
	}
	public Vector2d getPosition(){
		return new Vector2d(posX,posY);
	}
	/**
	 * Cette méthode permet de connaitre le rayon de la particule.
	 * @return le rayon de la particule
	 */
	public double getRayon() {
		return DIAMETRE/2;
	}
	/**
	 * Cette méthode permet d'avoir la forme géometrique de la particule
	 * @return la forme géometrique de la particule
	 */
	public Shape getShape() {
		return particuleRetourner;
	}
	/**
	 * Cette méthode retourne la charge de la particule 
	 * @return la charge de la particule en micro Coulomb.
	 */
	public double getCharge(){
		return charge;
	}
	/**
	 * Cette méthode permet de placer la particule à une position précise.
	 * @param position la position souhaité
	 */
	public void setPosition(Vector2d position){
		posAvant = new Vector2d(posX,posY);
		this.posX = position.getX();
		this.posY = position.getY();
		creerParticules();
	}
	/**
	 * Cette méthode permet de changer la charge de la particule.
	 * @param charge la nouvelle charge de la particule.
	 */
	public void setCharge(double charge){
		this.charge = charge;
	}
	/**
	 * Cette méthode permet de faire avancer la particule
	 * @param acceleration l'accélération de la particule
	 * @param deltaT le temps écouler entre le dernier déplacement.
	 */
	public void avancerParticule(Vector2d acceleration,double deltaT){
		calculerVitesse(acceleration,deltaT);
		posAvant = new Vector2d(posX,posY);
		posX += vitesse.getX()*deltaT;
		posY += vitesse.getY()*deltaT;
		creerParticules();
	}
	/**
	 * Cette méthode permet de calculer les vitesses(en x et en y) de la particule.
	 * @param acceleration l'accélération de la particule
	 * @param deltaT le temps écouler entre le dernier déplacement.
	 */
	private void calculerVitesse(Vector2d acceleration, double deltaT){
		vitesse = vitesse.add(acceleration.multiply(deltaT));
	}
	/**
	 * Cette méthode permet de remettre les paramètres à leur valeur intiale.
	 */
	public void restart(){
		vitesse = new Vector2d();
		particuleCible = false;
		posX = posXIni;
		posY = posYIni;
		if(particuleCollision){
			charge = chargePrece;
		}
		posAvant = new Vector2d(posX,posY);
		creerParticules();
		particuleCollision = false;
	}
	/**
	 * Cette méthode permet à l'utilisateur de connaitre la masse de la particule
	 * @return la masse de la particule selon sa charge.
	 */
	public double getMasse(){
		if(charge < 0){
			return MASSE_PARTICULE_NEGATIF;
		}else{
			return MASSE_PARTICULE_POSITIF;
		}
	}
	/**
	 * Cette méthode permet de remettre les vitesses à zéro;
	 */
	public void stabiliser(){
		vitesse.clear();
	}
	/**
	 * Cette méthode permet de placer la particule à sa position précédente.
	 */
	public void posAvant(){
		posX = posAvant.getX();
		posY = posAvant.getY();
		creerParticules();
	}
	/**
	 * Cette méthode permet de confirmer que la particule à atteint le point d'arrivé.
	 * @param verite Ca permet de savoir si la particule est arrivé
	 */
	public void particuleDansFosse(boolean verite){
		if(verite){
			particuleCible = true;
			particuleCollision = false;
		}else{
			particuleCible = true;
		}
		charge = chargePrece;
	}
	/**
	 * Cette méthode permet de connaître la vitesse de la particule.
	 * @return sa vitesse.
	 */
	public double getVitesse(){
		return vitesse.module();
	}
	/**
	 * Cette méthode permet de connaitre la vitesse non modulé de la particule.
	 * @return la vitesse non modulé de la particule(en vecteur).
	 */
	public Vector2d getVitesseActuelle(){
		return vitesse;
	}
	/**
	 * Cette méthode permet d'obtenir la forme géométrique non transformé de la particule.
	 * @return la forme géométrique non transformé de la particule. 
	 */
	public Shape getShapeNonTransfo(){
		return particule;
	}
	/**
	 * Cette méthode permet de savoir que la particule a fusionner avec une autre particule(ou la coccinelle)
	 */
	public void particuleCollision(){
		particuleCollision = true;
		chargePrece = charge;
		charge = 0;
	}
}
