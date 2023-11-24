package geometrie;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
/**
 * 
 * Classe de géometrie qui permet de dessiner l'objet cible
 * 
 * @author Zi Zheng Wang
 * @version 2.0
 */
public class Cible {
	private double diametre;
	private double origX;
	private double origY;
	private int nbCercles;
	private StyleDessin style = StyleDessin.PLEIN; //par defaut
	private Color rose = new Color(255,108,156);
	private double diffDeDiametre; 	//difference de diametre entre un cercle et le suivant
	private double diffDePosition; //difference de position entre un cercle et le suivant
	private double v,b,t;
	public Cible(double origX, double origY, double diametre, int nbCecles) {
		this.origX = origX;
		v = origX;
		this.origY = origY;
		b = origY;
		this.diametre = diametre;
		t = diametre;
		this.nbCercles = nbCecles;
		diffDeDiametre = diametre/nbCercles;  	      
		diffDePosition = diametre/nbCercles/2.0;  
		
	}//fin constructeur


	public void dessiner(Graphics2D g2d) {
		Ellipse2D.Double c;
		//AffineTransform matALEntree = g2d.getTransform();
		//Color couleurALentree = g2d.getColor();
		for (int k=0; k<nbCercles; k++) {
			if (k > 0) {
			origX=origX+diffDePosition;
			origY=origY+diffDePosition;
			diametre=diametre-diffDeDiametre;
			}
			if (k%2 == 0) {
				g2d.setColor(Color.black);
			} else {
				
				g2d.setColor(Color.white);
			}

			//la ligne new ci-dessous devra evidemment etre modifiee!!!!!
			c = new Ellipse2D.Double(origX, origY, diametre, diametre);

			switch (style) {
			case PLEIN:
				g2d.fill(c);
				break;
			case LIGNES:
				g2d.draw(c);
				break;
			}//fin switch
		}//fin for
		//g2d.setTransform(matALEntree);
		//g2d.setColor(couleurALentree);
		origX=v;
		origY=b;
		diametre=t;
	}// fin dessiner
	
	/**
	 * Cette méthode permet de connaitre le style de dessin de la cible.
	 * @param s le style de dessin de la cible.
	 */
	public void setStyle(StyleDessin s) {
		this.style = s;
	}

	/**
	 * Cette méthode permet d'obtenir la position en x de la cible.
	 * @return a position en x de la cible.
	 */
	public double getX() {
		// TODO Auto-generated method stub
		return this.origX;
	}

	/**
	 * Cette méthode permet d'obtenir la position en y de la cible.
	 * @return a position en y de la cible.
	 */
	public double getY() {
		// TODO Auto-generated method stub
		return this.origY;
	}
	
	
} //fin classe
