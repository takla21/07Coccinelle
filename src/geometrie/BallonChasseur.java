
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
 * Classe de g�ometrie qui permet de dessiner les ballons de collision
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
	 * @param diametre - diam�tre du ballonChasseur
	 * @param orientation - l'orientation du d�placement de ballonChasseur
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
	 * M�thode sert � cr�er les composants graphics du ballonChasseur
	 */
	private void creerBallon() {
		ballon = new Ellipse2D.Double(posX - rayon,posY - rayon,diametre,diametre);
		ligneHorizon = new Line2D.Double(posX - rayon*0.75,posY,posX+diametre - rayon*1.25,posY);
		ligneVertical = new Line2D.Double(posX, posY - rayon*0.75,posX,posY + diametre - rayon*1.25);
		
	}
	/**
	 * M�thode sert � dessiner le ballonChasseur
	 * @param g2d - Graphics2D par d�faut
	 * @param aff - la matrice du monde r�el
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
	 * M�thode sert � dessiner les cercles (deuxi�me forme du ballonChasseur)
	 * @param g2d - Graphics2D par d�faut
	 * @param aff - la matrice du monde r�el
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
	 * M�thode pour modifier la position de BallonChasseur
	 * @param posX - la coordonn�e x pour la nouvelle position
	 * @param posY - la coordonn�e y pour la nouvelle position
	 */
	public void setPosition(double posX,double posY){
		this.posX = posX;
		this.posY = posY;
		position = new Vector2d (posX, posY);
		creerBallon();
	}
	/**
	 * M�thode pour modifier la position de BallonChasseur
	 * @param position - un vecteur qui porte le cordonn� x et le cordonn� y pour la nouvelle position
	 */
	public void setPosition(Vector2d position){
		this.position = position;
		this.posX = position.getX();
		this.posY = position.getY();
		creerBallon();
	}
	/**
	 * M�thode pour modifier la vitesse de BallonChasseur
	 * @param vitesse - une vitesse repr�sent�e par un vecteur
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
	 * M�thode pour changer le comportement de ballonChasseur pour le mode scientifique
	 * @param a - boolean <b>true</b> or <b>false</b>
	 * <p><b>true</b> pour le mode normal
	 * <p><b>false</b> pour le mode scientifique
	 */
	public void setVisible(boolean a){
		this.visible = a;
	}
	/**
	 * M�thode pour changer la couleur de ballonChasseur
	 * @param c - la couleur du ballonChasseur
	 */
	public void setColor(Color c){
		colorModifie = c;
	}
	/**
	 * M�thode pour d�placer le ballonChasseur selon un temps donn�
	 * @param deltaTemps - le temps
	 * @return un vecteur qui repr�sente la nouvelle position du ballonChasseur
	 */
	public Vector2d deplacer(double deltaTemps){
		position = position.add(vitesse.multiply(deltaTemps));
		this.posX = position.getX();
		this.posY = position.getY();
		creerBallon();
		return position;
	}
	/**
	 * M�thode pour pr�voir la position de ballonChasseur � prochaine temps
	 * @param deltaTemps - le temps
	 * @return un vecteur qui repr�sente la position future du ballonChasseur
	 */
	public Vector2d nextStep(double deltaTemps){
		Vector2d futurStep = position.add(vitesse.multiply(deltaTemps));
		return futurStep;
	}
	/**
	 * M�thode pour retourner la position de ballonChasseur
	 * @return le vecteur de position
	 */
	public Vector2d getPosition(){
		return position;
	}
	/**
	 * M�thode pour retourner la vitesse de ballonChasseur
	 * @return le vecteur de vitesse
	 */
	public Vector2d getVitesse(){
		return vitesse;
	}
	// Ces trois m�thodes prochaines retournent les cordonn�es pour le Shape du ballonChasseur et non les cordonn�es centrales du ballonChasseur
	/**
	 * M�thode qui retourne le cordonn� x du Shape de ballonChasseur
	 * @return la cordonn�e x du Shape de ballonChasseur
	 */
	public double getBallX(){
		return ball.getBounds2D().getX();	
	}
	/**
	 * M�thode qui retourne le cordonn� y du Shape de ballonChasseur
	 * @return la cordonn�e y du Shape de ballonChasseur
	 */
	public double getBallY(){
		return ball.getBounds2D().getY();	
	}
	/**
	 * M�thode qui retourne la coordonn�e x et la coordonn�e y du Shape de ballonChasseur dans un vecteur
	 * @return un vecteur qui conteint  la cordonn�e x et la cordonn�e y du Shape de ballonChasseur
	 */
	public Vector2d getBallPosition(){
		Vector2d position = new Vector2d(getBallX(), getBallY());
		return position;
	}
	/**
	 * M�thode pour retourner la coordonn�e x du ballonShasseur
	 * @return cordonn� x du ballonChasseur
	 */
	public double getX(){
		return posX;
	}
	/**
	 * M�thode pour retourner le coordonn�e y du ballonShasseur
	 * @return cordonn� y du ballonChasseur
	 */
	public double getY(){
		return posY;
	}
	/**
	 * M�thode pour retourner le rayon du ballonChasseur
	 * @return rayon du ballonChasseur
	 */
	public double getRayon(){
		return rayon;
	}
	/**
	 * M�thode pour retourner le diam�tre du ballonChasseur
	 * @return diam�tre du ballonChasseur
	 */
	public double getDiametre(){
		return diametre;
	}
	/**
	 * M�thode pour retourner la masse du ballonChasseur
	 * @return la masse du ballonChasseur
	 */
	public double getMasse(){
		return masse;
	}
	/**
	 * M�thode pour retourner la shape du ballonChasseur
	 * @return une shape qui repr�sente le ballonChasseur
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
