package geometrie;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import physique.Vector2d;

/**
 * 
 * Classe de g�ometrie qui permet de dessiner les vecteurs de force et de champ mang�tique
 * 
 * @author Zi Zheng Wang
 * @version 1.0
 */
public class VecteurGraphique {
	
	private double origX;
	private double origY;
	private double finitudeX;
	private double finitudeY;
	private final double ROT_POINTE = 0.3; //angle entre les traits de tete et la fleche, en radians
	private Line2D.Double corps, traitDeTete; //formes sous jacente
	private Vector2d position;
	private Vector2d positionProchaine;
	private double normeX;
	private double normeY;
	private Shape vecteur;
	private double scale = 5;
	private double portion = 3;
	private double sensContraire = -2;
	private double longeur;
	private double orientation;
	
	private Vector2d champE;
	/**
	 * Constructeur de la classe VecteurGraphique, cr�er l'objet vecteurGraphique
	 * @param position - position initiale en vecteur
	 * @param positionPro - position finale en vecteur
	 */
	public VecteurGraphique(Vector2d position, Vector2d positionPro) {
		this.position = position;
		this.origX = position.getX();
		this.origY = position.getY();
		this.positionProchaine = positionPro.substract(position).multiply(scale);
		this.normeX = positionProchaine.getX();
		this.normeY = positionProchaine.getY();
		orientation = Math.atan2(positionPro.getY()-position.getY(),positionPro.getX()-position.getX());
		creerShapesSousJacentes();
	}
	/**
	 * Constructeur de la classe VecteurGraphique, cr�er l'objet vecteurGraphique
	 * @param position - position initiale en vecteur
	 * @param champ - le champ magn�tique en vecteur
	 * @param scale - une scale de portion grandeur
	 */
	public VecteurGraphique(Vector2d position, Vector2d champ, double scale) {
		this.position = position;
		this.origX = position.getX();
		this.origY = position.getY();
		this.champE = champ.multiply(scale);
		this.longeur = champE.module();
		this.finitudeX = origX + champ.getX();
		this.finitudeY = origY + champ.getY();
		if(longeur >= 5){
			this.finitudeX = origX + champ.getX()/2;
			this.finitudeY = origY + champ.getY()/2;	
		}
		this.scale = scale;
		creerChamp�lectrique();
	}
	/**
	 * M�thode sert � cr�er les composants graphiques de l'objet vecteurGraphique
	 */
	private void creerShapesSousJacentes() {
		corps = new Line2D.Double(origX, origY, origX + normeX, origY + normeY);
		traitDeTete = new Line2D.Double(origX + (2*normeX)/portion, origY + (2*normeY)/portion, origX + normeX, origY + normeY);	
	}
	/**
	 * Dessiner l'objet vecteurGraphique 
	 * @param g2d - Graphics2D par d�faut
	 * @param aff - la matrice monde r�el
	 * @param c - la couleur de l'objet vecteurGraphique 
	 * 
	 */
	public void dessiner(Graphics2D g2d, AffineTransform aff, Color c) {
		AffineTransform matTran = new AffineTransform(aff);
		AffineTransform rotation = new AffineTransform(aff);
		//corps
        vecteur = matTran.createTransformedShape(corps);
		g2d.setColor(c);
		g2d.setStroke(new BasicStroke(2));
		g2d.draw(vecteur);
		//tete
		rotation.rotate(ROT_POINTE, origX + normeX , origY + normeY );
		Shape tete = rotation.createTransformedShape(traitDeTete);
		rotation.rotate(sensContraire*ROT_POINTE, origX + normeX , origY + normeY );
		Shape tete2 = rotation.createTransformedShape(traitDeTete);
		g2d.draw(tete);
		g2d.draw(tete2);
		g2d.setStroke(new BasicStroke(1));
		
	}
	/**
	 * M�thode sert � cr�er l'objet vecteurGraphique dans un champ magn�tique
	 */
	private void creerChamp�lectrique() {
		corps = new Line2D.Double(origX, origY, finitudeX, finitudeY);
		traitDeTete = new Line2D.Double(origX + 2*(finitudeX-origX)/3, origY + 2*(finitudeY-origY)/3, finitudeX, finitudeY);
	}
	/**
	 * Dessiner l'objet vecteurGraphique pour repr�senter un champ magn�tique
	 * @param g2d - Graphics2D par d�faut
	 * @param aff - la matrice monde r�el
	 * @param c - la couleur de l'objet vecteurGraphique 
	 * 
	 */
	public void dessinerChamp(Graphics2D g2d, AffineTransform aff, Color c) {
		AffineTransform matTran = new AffineTransform(aff);
		AffineTransform rotation = new AffineTransform(aff);
		//corps
        vecteur = matTran.createTransformedShape(corps);
		g2d.setColor(c);
		g2d.setStroke(new BasicStroke(2));
		g2d.draw(vecteur);
		//tete
		rotation.rotate(ROT_POINTE, finitudeX , finitudeY );
		Shape tete = rotation.createTransformedShape(traitDeTete);
		rotation.rotate(sensContraire*ROT_POINTE, finitudeX , finitudeY );
		Shape tete2 = rotation.createTransformedShape(traitDeTete);
		g2d.draw(tete);
		g2d.draw(tete2);
		g2d.setStroke(new BasicStroke(1));
		
	}
	/**
	 * M�thode pour modifier la position initiale de l'objet vecteurGraphique
	 * @param position - position initiale en vecteur
	 */
    public void setPosition(Vector2d position){
    	this.position = position;
    	this.origX = position.getX();
		this.origY = position.getY();
    	creerShapesSousJacentes();
    }
	/**
	 * M�thode pour modifier la position procahine de l'objet vecteurGraphique
	 * @param positionPro - position future en vecteur
	 */
    public void setProchainePas(Vector2d positionPro){
    	this.positionProchaine = positionPro.substract(position).multiply(scale);
		this.normeX = positionProchaine.getX();
		this.normeY = positionProchaine.getY();
    	creerShapesSousJacentes();
    }
    /**
     * M�thode qui retourne la coordonn�e x de la position initiale
     * @return origX - la coordonn�e x
     */
    public double getX(){
    	return this.origX;
    }
    /**
     * M�thode qui retourne la coordonn�e Y de la position initiale
     * @return origY - la coordonn�e Y
     */
    public double getY(){
    	return this.origY;
    }
    /**
     * M�thode qui retourne la coordonn�e x d'une shape de l'objet vecteurGraphique
     * @return la coordonn�e x d'une shape de l'objet vecteurGraphique
     */
    public double getShapeX(){
    	return vecteur.getBounds().getX();
    }
    /**
     * M�thode qui retourne la coordonn�e y d'une shape de l'objet vecteurGraphique
     * @return la coordonn�e y d'une shape de l'objet vecteurGraphique
     */
    public double getShapeY(){
    	return vecteur.getBounds().getY();
    }
    // m�thode fait par K.T.
    /**
     * Cette m�thode permet de modifier la norme du vecteur.
     * @param norme la valeur minimum de la nouvelle norme
     */
    public void setNorme(double norme){
    	this.normeX += norme * Math.cos(orientation);
    	this.normeY += norme * Math.sin(orientation);
    	creerShapesSousJacentes();
    }
}//fin classe