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
 * Cette méthode permet de dessiner un champ magnétique
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
	 * C'est le constructeur du champ magnétique
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
	 * Cette méthode permet de créer la forme géométrique du champs
	 */
	private void creerChamp(){
		zoneChamp = new Rectangle2D.Double(posX,posY,longueur,largeur);
	}
	/**
	 * Cette méthode permet de dessiner le champs magnétique
	 * @param g2d contexte graphique
	 * @param matMC  matrice monde réelle
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
	 * Cette méthode permet de dessiner un "x" qui represente l'orientation du champ magnétique qui sera toujours orienté vers l'usager.
	 * @param x sa position en x.
	 * @param y sa position en y.
	 * @return la forme géométrique en "x"
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
	 * Cette méthode permet d'obtenir la forme géométrique du champs magnétique.
	 * @return la forme géométrique du champs magnétique.
	 */
	public Shape getShape(){
		return zoneChamp;
	}
	
	public Shape getShapeTransfo(){
		return shapeTransfoChamps;
	}
	/**
	 * Cette méthode permet d'obtenir le champs magnétique.
	 * @return le champs magnétique.
	 */
	public Vector3d getChampsMagnetique(){
		return champMagnetique;
	}
	/**
	 * Cette méthode permet de modifier le champs magnétique.
	 * @param nouveauChamps le nouveau champs magnétique.
	 */
	public void setChamps(double nouveauChamps) {
		champMagnetique = new Vector3d(0,0,nouveauChamps);
	}
	/**
	 * Cette méthode permet de modifier la position du champs magnétique avec un vecteur.
	 * @param nouvellePosition le vecteur position.
	 */
	public void setPosition(Vector2d nouvellePosition) {
		posX = nouvellePosition.getX();
		posY = nouvellePosition.getY();
		creerChamp();
	}
	/**
	 * Cette méthode permet de connaitre la position du champs
	 * @return la position du champs
	 */
	public Vector2d getPosition() {
		return new Vector2d(posX,posY);
	}
	/**
	 * Cette méthode permet de connaitre la largeur du champs magnétique.
	 * @return la largeur du champs magnétique.
	 */
	public double getLargeur() {
		return largeur;
	}
	/**
	 * Cette méthode permet de trouver la longueur du champs magnétique.
	 * @return la longueur du champs magnétique.
	 */
	public double getLongueur() {
		return longueur;
	}
	/**
	 * Cette méthode permet de savoir si un point est dans le champ magnétique.
	 *@param x la position horizotale du point.
	 * @param y la position verticale du point.
	 * @return <p><b>true</b> le point est dans le champ magnétique.
	 * <p><b>false</b> le point n'est pas dans le champ magnétique.
	 */
	public boolean contient(double x, double y){
		if(shapeTransfoChamps.contains(x, y)){
			return true;
		}else{
			return false;
		}
	}
}
