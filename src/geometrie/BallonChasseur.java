
package geometrie;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.Serializable;

import physique.Vector2d;
/**
 * 
 * Classe de géometrie qui permet de dessiner les ballons de collision
 * 
 * @author Zi Zheng Wang
 * @version 1.0
 */
public class BallonChasseur implements Serializable{
	private static final long serialVersionUID = 5439562044944011683L;
	private double posX;
	private double posY;
	private double diametre;
	private double rayon;
	private double orientation;
	private double masse;
	private double angleRotation = 0;
	private double stockAngle = 0;
	private Vector2d position,vitesse;
	private Ellipse2D.Double ballon;
	private Shape ball,ballonRotation;
	private Line2D.Double ligneHorizon,ligneVertical;
	private Color mauve = new Color(117,81,162);
	private Color yellow = new Color(255,255,64,100);
	private Color mer = new Color(14,150,150,100);
	private Color colorModifie = Color.LIGHT_GRAY;
	private boolean visible = false;
	/**
	 * Constructeur de l'objet ballonChasseur
	 * @param position - position initial du ballonChasseur
	 * @param diametre - diamètre du ballonChasseur
	 * @param orientation - l'orientation du déplacement de ballonChasseur
	 * @param masse - la masse du ballonChasseur
	 * @param vitesse - la vitesse du ballonChasseur
	 */
	public BallonChasseur(Vector2d position, double diametre,double orientation, double masse, Vector2d vitesse){
		this.position = position;
		this.posX = position.getX();
		this.posY = position.getY();
		this.diametre = diametre;
		this.orientation = orientation;
		this.rayon = diametre/2;
		this.masse = masse;
		this.vitesse = vitesse;
		creerBallon();
	}
	/**
	 * Méthode sert à créer les composants graphics du ballonChasseur
	 */
	private void creerBallon() {
		ballon = new Ellipse2D.Double(posX - rayon,posY - rayon,diametre,diametre);
		ligneHorizon = new Line2D.Double(posX - rayon*0.75,posY,posX+diametre - rayon*1.25,posY);
		ligneVertical = new Line2D.Double(posX, posY - rayon*0.75,posX,posY + diametre - rayon*1.25);
		
	}
	/**
	 * Méthode sert à dessiner le ballonChasseur
	 * @param g2d - Graphics2D par défaut
	 * @param aff - la matrice du monde réel
	 * @param angle - angle de rotation
	 * @param t  l'identification du ballonChasseur pour le mode scientifique
	 */
	public void dessiner(Graphics2D g2d, AffineTransform aff, double angle, String t){
		AffineTransform matt = new AffineTransform(aff);
		AffineTransform rota = new AffineTransform();
		ball = matt.createTransformedShape(ballon);
		Shape lineUn = matt.createTransformedShape(ligneHorizon);
		Shape lineDeux = matt.createTransformedShape(ligneVertical);
		rota.rotate(angle,lineUn.getBounds().getX()+(lineUn.getBounds().width)/2,lineUn.getBounds().getY()+(lineUn.getBounds().height)/2);
		Shape rotaShapeUn = rota.createTransformedShape(lineUn);
		Shape rotaShapeDeux = rota.createTransformedShape(lineDeux);
		if(!visible){
			g2d.setColor(Color.LIGHT_GRAY);
		}else{
			g2d.setColor(yellow);
		}
		g2d.fill(ball);
		if(!visible){
			g2d.setColor(Color.white);
		}else{
			g2d.setColor(Color.LIGHT_GRAY);
		}
		g2d.setStroke(new BasicStroke(2));
		g2d.draw(rotaShapeUn);
		g2d.draw(rotaShapeDeux);
		if(!visible){
			g2d.setColor(mauve);
		}else{
			g2d.setColor(mer);
			g2d.drawString(t, (int)(ball.getBounds().getX()+ball.getBounds().getWidth()*0.8), (int)(ball.getBounds().getY()+ball.getBounds().getHeight()));
		}
		g2d.setStroke(new BasicStroke(3));
		g2d.draw(ball);

	}
	/**
	 * Méthode sert à dessiner les cercles (deuxième forme du ballonChasseur)
	 * @param g2d - Graphics2D par défaut
	 * @param aff - la matrice du monde réel
	 * @param angle - angle de rotation
	 * @param cordX - positionX qui sert comme centre de rotation
	 * @param cordY - positionY qui sert comme centre de rotation
	 */
	public void dessiner(Graphics2D g2d, AffineTransform aff, double angle,double cordX, double cordY){
		AffineTransform matt = new AffineTransform(aff);
		AffineTransform rotation = new AffineTransform();
		rotation.rotate(angle, cordX, cordY);
		ballonRotation = rotation.createTransformedShape(ballon);
		ball = matt.createTransformedShape(ballonRotation);
		if(!visible){
			g2d.setColor(mauve);
		}else{
			g2d.setColor(mer);
		}
		g2d.setStroke(new BasicStroke(10));
		g2d.draw(ball);
		g2d.setColor(colorModifie);
		g2d.setStroke(new BasicStroke(3));
		g2d.draw(ball);

	}
	/**
	 * Méthode pour modifier la position de BallonChasseur
	 * @param posX - la coordonnée x pour la nouvelle position
	 * @param posY - la coordonnée y pour la nouvelle position
	 */
	public void setPosition(double posX,double posY){
		this.posX = posX;
		this.posY = posY;
		position = new Vector2d (posX, posY);
		creerBallon();
	}
	/**
	 * Méthode pour modifier la position de BallonChasseur
	 * @param position - un vecteur qui porte le cordonné x et le cordonné y pour la nouvelle position
	 */
	public void setPosition(Vector2d position){
		this.position = position;
		this.posX = position.getX();
		this.posY = position.getY();
		creerBallon();
	}
	/**
	 * Méthode pour modifier la vitesse de BallonChasseur
	 * @param vitesse - une vitesse représentée par un vecteur
	 */
	public void setVitesse(Vector2d vitesse){
		this.vitesse = vitesse;
		creerBallon();
	}
	public void setAngleRotation(double angle){
		this.angleRotation = angle;
	}
	public void setStockAngle(double angle){
		this.stockAngle = angle;
	}
	/**
	 * Méthode pour changer le comportement de ballonChasseur pour le mode scientifique
	 * @param a - boolean <b>true</b> or <b>false</b>
	 * <p><b>true</b> pour le mode normal
	 * <p><b>false</b> pour le mode scientifique
	 */
	public void setVisible(boolean a){
		this.visible = a;
	}
	/**
	 * Méthode pour changer la couleur de ballonChasseur
	 * @param c - la couleur du ballonChasseur
	 */
	public void setColor(Color c){
		colorModifie = c;
	}
	/**
	 * Méthode pour déplacer le ballonChasseur selon un temps donné
	 * @param deltaTemps - le temps
	 * @return un vecteur qui représente la nouvelle position du ballonChasseur
	 */
	public Vector2d deplacer(double deltaTemps){
		position = position.add(vitesse.multiply(deltaTemps));
		this.posX = position.getX();
		this.posY = position.getY();
		creerBallon();
		return position;
	}
	/**
	 * Méthode pour prévoir la position de ballonChasseur à prochaine temps
	 * @param deltaTemps - le temps
	 * @return un vecteur qui représente la position future du ballonChasseur
	 */
	public Vector2d nextStep(double deltaTemps){
		Vector2d futurStep = position.add(vitesse.multiply(deltaTemps));
		return futurStep;
	}
	/**
	 * Méthode pour retourner la position de ballonChasseur
	 * @return le vecteur de position
	 */
	public Vector2d getPosition(){
		return position;
	}
	/**
	 * Méthode pour retourner la vitesse de ballonChasseur
	 * @return le vecteur de vitesse
	 */
	public Vector2d getVitesse(){
		return vitesse;
	}
	// Ces trois méthodes prochaines retournent les cordonnées pour le Shape du ballonChasseur et non les cordonnées centrales du ballonChasseur
	/**
	 * Méthode qui retourne le cordonné x du Shape de ballonChasseur
	 * @return la cordonnée x du Shape de ballonChasseur
	 */
	public double getBallX(){
		return ball.getBounds2D().getX();	
	}
	/**
	 * Méthode qui retourne le cordonné y du Shape de ballonChasseur
	 * @return la cordonnée y du Shape de ballonChasseur
	 */
	public double getBallY(){
		return ball.getBounds2D().getY();	
	}
	/**
	 * Méthode qui retourne la coordonnée x et la coordonnée y du Shape de ballonChasseur dans un vecteur
	 * @return un vecteur qui conteint  la cordonnée x et la cordonnée y du Shape de ballonChasseur
	 */
	public Vector2d getBallPosition(){
		Vector2d position = new Vector2d(getBallX(), getBallY());
		return position;
	}
	/**
	 * Méthode pour retourner la coordonnée x du ballonShasseur
	 * @return cordonné x du ballonChasseur
	 */
	public double getX(){
		return posX;
	}
	/**
	 * Méthode pour retourner le coordonnée y du ballonShasseur
	 * @return cordonné y du ballonChasseur
	 */
	public double getY(){
		return posY;
	}
	/**
	 * Méthode pour retourner le rayon du ballonChasseur
	 * @return rayon du ballonChasseur
	 */
	public double getRayon(){
		return rayon;
	}
	/**
	 * Méthode pour retourner le diamètre du ballonChasseur
	 * @return diamètre du ballonChasseur
	 */
	public double getDiametre(){
		return diametre;
	}
	/**
	 * Méthode pour retourner la masse du ballonChasseur
	 * @return la masse du ballonChasseur
	 */
	public double getMasse(){
		return masse;
	}
	/**
	 * Méthode pour retourner la shape du ballonChasseur
	 * @return une shape qui représente le ballonChasseur
	 */
	public Shape getShape(){
		return ball;
	}
	public double getOrientation() {
		return this.orientation;
	}
	public double getAngleRotation(){
		return this.angleRotation;
	}
	public double getStockAngle(){
		return this.stockAngle;
	}
}
