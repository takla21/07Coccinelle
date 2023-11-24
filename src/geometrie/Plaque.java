package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import physique.Vector2d;
/**
 * Cette m�thode permet de dessiner une plaque �lectrique PPIUC
 * @author Kevin Takla
 * @version 2.0
 */
public class Plaque implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7271608917355130384L;
	private double posX, posY;
	private final double LONGUEUR_PLAQUE_DEFAUT = 60;
	private double longueurPlaque= LONGUEUR_PLAQUE_DEFAUT;
	private final double LARGEUR_PLAQUE = 1;
	private Rectangle2D.Double plaque;
	private double champElectrique;
	private Shape plac;
	
	/**
	 * C'est le constructeur de la plaque
	 * @param x sa position horizontale
	 * @param y sa position verticale
	 * @param champElectrique le champs �lectrique au d�part.
	 */
	public Plaque(double x, double y, double champElectrique){
		posX = x;
		posY = y;
		this.champElectrique = champElectrique;
		creerPlaque();
	}
	/**
	 * 
	 * C'est le constructeur de la plaque losrqu'on choisit la longueur de la plaque.
	 * @param x sa position horizontale
	 * @param y sa position verticale
	 * @param longueur c'est la longueur de la plaque.
	 * @param champElectrique le champs �lectrique au d�part.
	 */
	public Plaque(double x,double y, int longueur, double champElectrique){
		posX = x;
		posY = y;
		longueurPlaque = longueur;
		this.champElectrique = champElectrique;
		creerPlaque();
	}
	/**
	 * Cette m�thode permet de cr�er la forme g�om�trique de la plaque
	 */
	private void creerPlaque(){
		plaque = new Rectangle2D.Double(posX,posY,longueurPlaque,LARGEUR_PLAQUE);
	}
	
	/**
	 * Cette m�thode permet de dessiner la plaque 
	 * @param g2d le contexte graphique
	 * @param matMC la matrice monde r�elle.
	 */
	public void dessiner(Graphics2D g2d,AffineTransform matMC){
		Color couleurCourante = g2d.getColor();
		if(champElectrique > 0){
			g2d.setColor(Color.LIGHT_GRAY);
		}else{
			g2d.setColor(Color.DARK_GRAY);
		}
	    plac = matMC.createTransformedShape(plaque);
		g2d.fill(plac);
		g2d.setColor(Color.white);
		g2d.draw(plac);
		
		g2d.setColor(couleurCourante);
	}
	/**
	 * Cette m�thode permet d'obtenir le champs �lectrique de la plaque
	 * @return le champs �lectrique de la plaque.
	 */
	public double getChampsElectrique(){
		return champElectrique;
	}
	/**
	 * Cette m�thode permet de modifier le champs �lectrique de la plaque.
	 * @param champsElectrique le nouveau champs �lectrique de la plaque.
	 */
	public void setChampsElectrique(double champsElectrique) {
		champElectrique = champsElectrique;
	}
	/**
	 * Cette m�thode permet d'obtenir la forme g�om�trique de la plaque.
	 * @return  la forme g�om�trique de la plaque.
	 */
	public Shape getShape() {
		return plac;
	}
	/**
	 * Cette m�thode permet d'imprimer les param�tres de la plaque en format chaine.
	 */
	public String toString(){
		return "position :" + posX + "-" + posY +"  longueur: " + longueurPlaque + " champs: " + champElectrique;
	}
	/**
	 * Cette m�thode permet de modifier la positon de la plaque � partir d'un vecteur.
	 * @param nouvellePosition le vecteur de la nouvelle position
	 */
	public void setPosition(Vector2d nouvellePosition) {
		posX = nouvellePosition.getX();
		posY = nouvellePosition.getY();
		creerPlaque();
	}
	/**
	 * Cette m�thode permet d'obtenir la position de la plaque.
	 * @return la position de la plaque.
	 */
	public Vector2d getPosition() {
		return new Vector2d(posX,posY);
	}
	/**
	 * Cette m�thode permet d'obtenir la longueur de la plaque.
	 * @return la longueur de la plaque;
	 */
	public double getLongueur(){
		return longueurPlaque;
	}
	/**
	 * Cette m�thode permet d'obtenir la forme g�om�trique de la plaque non transform�e
	 * @return la forme g�om�trique de la plaque non transform�e
	 */
	public Shape getShapeNonTransfo(){
		return plaque;
	}

}
