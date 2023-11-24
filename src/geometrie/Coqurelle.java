package geometrie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
/**
 * Cette méthode permet de dessiner la coqurelle.
 * @author Kevin Takla
 * @version 2.0
 */
public class Coqurelle {
	private double posX, posY;
	private final double LONGUEUR_COQURELLE = 20;
	private final double LARGEUR_COQURELLE =22.67;
	private	Image coqurelle;
	private double angle;
	private Ellipse2D.Double shapeCoqurelle;
	private final double GROSSEUR_SHAPE =8;
	private double acceleration = 0.5;
	
	
	/**
	 * C'est le constructeur de la classe Coqurelle.
	 * @param x la position intiale en x de la coqurelle.
	 * @param y la position intiale en y de la coqurelle.
	 * @param angle  l'angle de la coquerelle
	 */
	public Coqurelle(double x,double y,double angle){
		posX = x;
		posY = y;
		this.angle = angle;
		chercherImage();
		creerShape();
	}
	
	/**
	 *Cette méthode permet de chercher l'image la coqurelle. 
	 */
	private void chercherImage(){
		URL url = getClass().getClassLoader().getResource("bebitteVersionModif1.gif");
		if(url == null){
			//System.err.println("erreur");
		}
		try{
			 coqurelle = ImageIO.read(url);
		}
		catch(IOException e ){
			//System.err.println("erreur");
		}
	}
	
	private void creerShape(){
		if(angle == 0){
			shapeCoqurelle = new Ellipse2D.Double(posX + LONGUEUR_COQURELLE/2 -GROSSEUR_SHAPE/2, posY +GROSSEUR_SHAPE/2,GROSSEUR_SHAPE,LARGEUR_COQURELLE - 5);
		}else{
			shapeCoqurelle = new Ellipse2D.Double(posX + LONGUEUR_COQURELLE/2 -GROSSEUR_SHAPE/2 - 20, posY,GROSSEUR_SHAPE,LARGEUR_COQURELLE - 5);
		}
	}
	/**
	 * Cette méthode permet de dessiner la coqurelle
	 * @param g2d le contexte graphique
	 * @param matMC la matrice monde réelle.
	 * @param facteurX le facteur x pour convertir en monde réel
	 * @param facteurY le facteur y pour convertir en monde réel
	 */
	public void dessiner(Graphics2D g2d, AffineTransform matMC, double facteurX, double facteurY){
		AffineTransform matLocal = new AffineTransform(matMC);
		AffineTransform transfog2d = g2d.getTransform();
		g2d.setTransform(matLocal);
		g2d.rotate(angle,posX,(posY + LARGEUR_COQURELLE/2));
		g2d.drawImage(coqurelle,(int)(posX),(int)(posY),(int)(LONGUEUR_COQURELLE),(int)(LARGEUR_COQURELLE),null);
		g2d.setTransform(transfog2d);
		
		/*g2d.rotate(angle,posX*facteurX,(posY + LARGEUR_COQURELLE/2)*facteurY);
		g2d.drawImage(coqurelle,(int)(posX*facteurX),(int)(posY*facteurY),(int)(LONGUEUR_COQURELLE*facteurX),(int)(LARGEUR_COQURELLE*facteurY),null);
		g2d.setTransform(matLocal);
		*/
		
		
	}
	/**
	 * Cette méthode permet d'avoir la forme géométrique representant la coqurelle.
	 * @return la forme géométrique de la coqurelle
	 */
	public Shape getShape(){
		return shapeCoqurelle;
	}
	/**
	 * Cette méthode permet de faire avancer la coqurelle.
	 */
	public void avancer(){
		if(angle == 0){
			posY -= acceleration;
		}else{
			posY += acceleration;
		}
		creerShape();
	}
	/**
	 * Cette méthode permet d'inverser la direction de la coqurelle.
	 */
	public void inverseDirection(){
		acceleration = -acceleration;
	}
	/**
	 * Cette méthode permet de connaitre sa position verticale de la coqurelle.
	 * @return la position verticale de la coqurelle
	 */
	public double getY(){
		return posY;
	}
	/**
	 * Cette méthode permet de connaitre la largeur de la coqurelle.
	 * @return  la largeur de la coqurelle.
	 */
	public double getLargeur(){
		return LARGEUR_COQURELLE;
	}
	/**
	 * Cette méthode permet de connaitre l'orientation de la coqurelle.
	 * @return l'orientation de la coqurelle.
	 */
	public double getAngle() {
		return angle;
	}
	/**
	 * Cette méthode permet de modifier la vitesse de la coquerelle.
	 * @param vitesse la vitesse de la coquerelle.
	 */
	public void setVitesse(double vitesse) {
		acceleration = vitesse;
		
	}
}
