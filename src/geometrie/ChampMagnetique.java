package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import physique.Vector2d;
import physique.Vector3d;

/**
 * Cette m�thode permet de dessiner un champ magn�tique
 * @author Kevin Takla
 * @version 2.0
 */
public class ChampMagnetique implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5400954159465047469L;
	private double posX, posY;
	private double longueur,largeur;
	private Rectangle2D.Double zoneChamp;
	private double LARGEUR_X = 1;
	private Vector3d champMagnetique;
	private Shape shapeTransfoChamps;
	
	/**
	 * C'est le constructeur du champ magn�tique
	 * @param x sa position horizontale
	 * @param y sa position verticale
	 * @param longueur la longueur du champs
	 * @param largeur la largeur du champs
	 * @param champs le champs
	 */
	public ChampMagnetique(double x, double y, double longueur, double largeur, double champs){
		posX = x;
		posY = y;
		this.longueur = longueur;
		this.largeur = largeur;
		champMagnetique = new Vector3d(0,0,champs);
		creerChamp();
	}
	/**
	 * Cette m�thode permet de cr�er la forme g�om�trique du champs
	 */
	private void creerChamp(){
		zoneChamp = new Rectangle2D.Double(posX,posY,longueur,largeur);
	}
	/**
	 * Cette m�thode permet de dessiner le champs magn�tique
	 * @param g2d contexte graphique
	 * @param matMC  matrice monde r�elle
	 */
	public void dessiner(Graphics2D g2d, AffineTransform matMC){
		Color couleurCourante = g2d.getColor();
		g2d.setColor(Color.GRAY);
		shapeTransfoChamps = matMC.createTransformedShape(zoneChamp);
		g2d.draw(shapeTransfoChamps);
		
		final int DECALAGE_X = 1;
		g2d.setColor(Color.white);
		for(int j =DECALAGE_X; j < largeur; j+= 4*LARGEUR_X){
			for(int i =DECALAGE_X; i < longueur; i+= 4*LARGEUR_X){
				if(i -LARGEUR_X < longueur && j -LARGEUR_X < largeur){
				g2d.draw(matMC.createTransformedShape(creerLigneChamp(posX+i, posY+j)));
				}
			}
		}
		g2d.setColor(couleurCourante);
	}
	/**
	 * Cette m�thode permet de dessiner un "x" qui represente l'orientation du champ magn�tique qui sera toujours orient� vers l'usager.
	 * @param x sa position en x.
	 * @param y sa position en y.
	 * @return la forme g�om�trique en "x"
	 */
	private Path2D.Double creerLigneChamp(double x, double y){
		Path2D.Double champ = new Path2D.Double();
		champ.moveTo(x, y);
		champ.lineTo(x+LARGEUR_X,y+LARGEUR_X);
		champ.moveTo(x+LARGEUR_X, y);
		champ.lineTo(x,y+LARGEUR_X);
		return champ;
	}
	/**
	 * Cette m�thode permet d'obtenir la forme g�om�trique du champs magn�tique.
	 * @return la forme g�om�trique du champs magn�tique.
	 */
	public Shape getShape(){
		return zoneChamp;
	}
	
	public Shape getShapeTransfo(){
		return shapeTransfoChamps;
	}
	/**
	 * Cette m�thode permet d'obtenir le champs magn�tique.
	 * @return le champs magn�tique.
	 */
	public Vector3d getChampsMagnetique(){
		return champMagnetique;
	}
	/**
	 * Cette m�thode permet de modifier le champs magn�tique.
	 * @param nouveauChamps le nouveau champs magn�tique.
	 */
	public void setChamps(double nouveauChamps) {
		champMagnetique = new Vector3d(0,0,nouveauChamps);
	}
	/**
	 * Cette m�thode permet de modifier la position du champs magn�tique avec un vecteur.
	 * @param nouvellePosition le vecteur position.
	 */
	public void setPosition(Vector2d nouvellePosition) {
		posX = nouvellePosition.getX();
		posY = nouvellePosition.getY();
		creerChamp();
	}
	/**
	 * Cette m�thode permet de connaitre la position du champs
	 * @return la position du champs
	 */
	public Vector2d getPosition() {
		return new Vector2d(posX,posY);
	}
	/**
	 * Cette m�thode permet de connaitre la largeur du champs magn�tique.
	 * @return la largeur du champs magn�tique.
	 */
	public double getLargeur() {
		return largeur;
	}
	/**
	 * Cette m�thode permet de trouver la longueur du champs magn�tique.
	 * @return la longueur du champs magn�tique.
	 */
	public double getLongueur() {
		return longueur;
	}
	/**
	 * Cette m�thode permet de savoir si un point est dans le champ magn�tique.
	 *@param x la position horizotale du point.
	 * @param y la position verticale du point.
	 * @return <p><b>true</b> le point est dans le champ magn�tique.
	 * <p><b>false</b> le point n'est pas dans le champ magn�tique.
	 */
	public boolean contient(double x, double y){
		if(shapeTransfoChamps.contains(x, y)){
			return true;
		}else{
			return false;
		}
	}
}
