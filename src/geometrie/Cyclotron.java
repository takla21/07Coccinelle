package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import javax.swing.event.EventListenerList;

import ecouteur.TransmissionInfoNiveau4Listener;
import physique.Vector2d;
import physique.Vector3d;

/**
 * Cette m�thode permet de dessiner un cyclotron
 * @author Kevin Takla
 * @version 2.0
 */
public class Cyclotron {
	private double posX, posY;
	private final double DIAMETRE_CYCLOTRON = 50;
	private final double LONGUEUR_SORTIE = 7;
	private final double LARGEUR_SORTIE = 12;
	private final double HAUTEUR_ZONE = 5;
	private double differencePotentielle =0;
	private double differencePotentielle0 = 30;
	private double temps;
	private double periode;
	private boolean inversePotentielleCercle = false;
	private Ellipse2D.Double cerclePrincipale;
	private Path2D.Double cercleBas, cercleHaut;
	private Rectangle2D.Double zoneContact;
	private Ellipse2D.Double pointDepart;
	private Vector3d champMagnetique = new Vector3d(0,0,200);
	private double champElectrique;
	private Rectangle2D.Double voieSortie;
	private Shape cycloTransfo;
	private Rectangle2D.Double voieSortieCollision;

	/**
	 * C'est le constructeur de la classe Cyclotron
	 * @param x c'est la position horizontale du cyclotron
	 * @param y c'est la position verticale du cyclotron
	 */
	public Cyclotron(double x, double y){
		posX = x;
		posY = y;
		creerFormes();
	}
	/**
	 * Cette m�thode permet de creer les diff�rents formes g�ometrique qui constitue un cyclotron.
	 */
	private void creerFormes(){
		final double LONGUEUR_VOIE_SORTIE = 3;
		final double LONGUEUR_VOIE_COLLISION = 0.5;
		final double LARGEUR_SHAPE_COLLISION =10;
		cerclePrincipale = new Ellipse2D.Double(posX,posY,DIAMETRE_CYCLOTRON,DIAMETRE_CYCLOTRON);
		zoneContact = new Rectangle2D.Double(posX,posY+DIAMETRE_CYCLOTRON/2-HAUTEUR_ZONE/2,DIAMETRE_CYCLOTRON,HAUTEUR_ZONE);
		cercleHaut = creerDemiCercle(posX,posY+DIAMETRE_CYCLOTRON/2-HAUTEUR_ZONE/2,-1);
		cercleBas =  creerDemiCercle(posX,posY+DIAMETRE_CYCLOTRON/2+HAUTEUR_ZONE/2,1);
		voieSortie = new Rectangle2D.Double(LONGUEUR_VOIE_SORTIE,posY - DIAMETRE_CYCLOTRON/2 + HAUTEUR_ZONE + 19,LONGUEUR_SORTIE,LARGEUR_SORTIE);
		pointDepart = new Ellipse2D.Double(posX+DIAMETRE_CYCLOTRON/2-HAUTEUR_ZONE/2,posY+DIAMETRE_CYCLOTRON/2-HAUTEUR_ZONE/2,HAUTEUR_ZONE,HAUTEUR_ZONE);
		voieSortieCollision = new Rectangle2D.Double(3,posY - DIAMETRE_CYCLOTRON/2 + HAUTEUR_ZONE + 19 + LARGEUR_SORTIE,LONGUEUR_VOIE_COLLISION,LARGEUR_SHAPE_COLLISION);
	}


	/**
	 * Cette m�thode permet de cr�er les demis-cercles du cyclotron.
	 * @param posXInitiale la position en x du demi-cercle.
	 * @param posYInitiale la position en y du demi-cercle.
	 * @param facteurUn l'orientation du demi-cercle <p><b>si facteurUn = 1</b> le demi cercle est vers le bas.
	 * <p><b>si facteurUn = -1</b> le demi cercle est vers le haut.
	 * @return le demi-cercle qui est cr�er � partir de cette m�thode-ci.
	 */
	private Path2D.Double creerDemiCercle(double posXInitiale, double posYInitiale, int facteurUn){
		Path2D.Double futurCercle = new Path2D.Double();
		futurCercle.moveTo(posXInitiale, posYInitiale);
		futurCercle.quadTo(posXInitiale + HAUTEUR_ZONE,posYInitiale+ (facteurUn)*(DIAMETRE_CYCLOTRON/2 - HAUTEUR_ZONE/2),posXInitiale+DIAMETRE_CYCLOTRON/2,posYInitiale + (facteurUn)*(DIAMETRE_CYCLOTRON/2 - HAUTEUR_ZONE/2));
		futurCercle.quadTo(posXInitiale -HAUTEUR_ZONE + DIAMETRE_CYCLOTRON,posYInitiale+ (facteurUn)*(DIAMETRE_CYCLOTRON/2 - HAUTEUR_ZONE/2),posXInitiale+DIAMETRE_CYCLOTRON,posYInitiale);
		futurCercle.closePath();
		return futurCercle;
	}
	/**
	 * Cette m�thode permet de creer les formes g�om�trique des signes des plaques.
	 * @param posX sa position horizontale.
	 * @param posY sa position verticale.
	 * @param charge la charge.
	 * @return la forme g�om�trique de la plaque
	 */
	private Path2D.Double creerSigne(double posX, double posY, int charge){
		Path2D.Double signe = new Path2D.Double();
		final double LARGEUR_TRAIT =3;
		if(charge < 0){
			signe.moveTo(posX -LARGEUR_TRAIT/2, posY);
			signe.lineTo(posX +LARGEUR_TRAIT/2, posY);
		}else{
			signe.moveTo(posX -LARGEUR_TRAIT/2, posY);
			signe.lineTo(posX +LARGEUR_TRAIT/2, posY);
			signe.moveTo(posX , posY-LARGEUR_TRAIT/2);
			signe.lineTo(posX , posY+LARGEUR_TRAIT/2);
		}
		return signe;
	}

	/**
	 * Cette m�thode permet de dessiner le cyclotron.
	 * @param g2d le contexte graphique.
	 * @param matMC la matrice monde r�elle.
	 * @param scientifique  <p><b>true</b> le niveau est en mode scientifique
	 * <p><b>false</b> le niveau n'est pas en mode scientifique
	 */
	public void dessiner(Graphics2D g2d, AffineTransform matMC,boolean scientifique){
		Color couleurCourante = g2d.getColor();
		Color couleurRougeCyclotron = new Color(251,25,20);
		AffineTransform matLocal = new AffineTransform(matMC);
	
		g2d.setColor(Color.white);
		cycloTransfo = matLocal.createTransformedShape(cerclePrincipale);
		g2d.draw(cycloTransfo);

		if(!scientifique){
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fill(matLocal.createTransformedShape(voieSortie));
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fill(matLocal.createTransformedShape(cerclePrincipale));
			if(inversePotentielleCercle){
				g2d.setColor(Color.cyan);
				g2d.fill(matLocal.createTransformedShape(cercleHaut));
				g2d.setColor(couleurRougeCyclotron);
				g2d.fill(matLocal.createTransformedShape(cercleBas));
			}else{
				g2d.setColor(couleurRougeCyclotron);
				g2d.fill(matLocal.createTransformedShape(cercleHaut));
				g2d.setColor(Color.cyan);
				g2d.fill(matLocal.createTransformedShape(cercleBas));
			}
			g2d.setColor(Color.green);
			g2d.fill(matLocal.createTransformedShape(pointDepart));
		}else{
			if(inversePotentielleCercle){
				g2d.setColor(Color.cyan);
				g2d.draw(matLocal.createTransformedShape(cercleHaut));
				g2d.setColor(couleurRougeCyclotron);
				g2d.draw(matLocal.createTransformedShape(cercleBas));
				g2d.setColor(Color.white);
				g2d.draw(matLocal.createTransformedShape(creerSigne(posX+DIAMETRE_CYCLOTRON/2, posY+DIAMETRE_CYCLOTRON/2-10, +1)));
				g2d.draw(matLocal.createTransformedShape(creerSigne(posX+DIAMETRE_CYCLOTRON/2, posY+DIAMETRE_CYCLOTRON/2+10, -1)));
			}else{
				g2d.setColor(couleurRougeCyclotron);
				g2d.draw(matLocal.createTransformedShape(cercleHaut));
				g2d.setColor(Color.cyan);
				g2d.draw(matLocal.createTransformedShape(cercleBas));
				g2d.setColor(Color.white);
				g2d.draw(matLocal.createTransformedShape(creerSigne(posX+DIAMETRE_CYCLOTRON/2, posY+DIAMETRE_CYCLOTRON/2-10, -1)));
				g2d.draw(matLocal.createTransformedShape(creerSigne(posX+DIAMETRE_CYCLOTRON/2, posY+DIAMETRE_CYCLOTRON/2+10, +1)));
			}
			g2d.setColor(Color.green);
			g2d.draw(matLocal.createTransformedShape(pointDepart));
		}

		g2d.setColor(couleurCourante);
	}
	/**
	 * Cette m�thode permet de faire varier le symbole du potentielle �lectrique selon le temps et sa p�riode.
	 * @param deltaT le temps de variation.
	 */
	public void potentielleCyclotron(double deltaT){
		temps += deltaT;
		if(temps > periode/2){
			temps =0;
			inversePotentielleCercle = !inversePotentielleCercle;
		}
		calculPotentielleElectrique(temps);
	}
	/**
	 * Cette m�thode permet de modifier le potentielle �lectrique du cyclotron selon le temps.
	 * @param temps le temps de variation.
	 */
	public void calculPotentielleElectrique(double temps){
		double accelerationAngulaire = (2*Math.PI)/periode;
		differencePotentielle = differencePotentielle0 * Math.sin(accelerationAngulaire*temps);
		if(inversePotentielleCercle){
			differencePotentielle = -differencePotentielle;
		}
		calculerChampElectrique();
	}
	/**
	 * Cette m�thode permet d'obtenir le champs magn�tique du cyclotron.
	 * @return le champs magn�tique du cyclotron.
	 */
	public Vector3d getChampMagnetique(){
		return champMagnetique;
	}
	/**
	 * Cette m�thode permet d'obtenir la forme g�om�trique du point de d�part.
	 * @return la forme g�om�trique du point de d�part.
	 */
	public Shape getPointDepart() {
		return pointDepart;
	}
	/**
	 * Cette m�thode permet de connaitre la position du milieu du cyclotron.
	 * @return la position du milieu du cyclotron.
	 */
	public Vector2d getPosCentral(){
		return new Vector2d(posX+DIAMETRE_CYCLOTRON/2,posY+DIAMETRE_CYCLOTRON/2);
	}
	/**
	 * Cette m�thode permet de conna�tre le potentielle �lectrique du cyclotron en tout temps.
	 * @return le potentielle �lectrique du cyclotron
	 */
	public double getPotentielle() {
		return differencePotentielle;
	}
	/**
	 * Cette m�thode remet le cyclotron � sa position normale
	 */
	public void restart(){
		inversePotentielleCercle = false; 
		differencePotentielle =0;
		temps =0;
		champElectrique =0;
	}
	/**
	 * Cette m�thode permet de calculer en tout temps le champ �lectrique du cyclotron selon son potentielle �lectrique et le distance entre les deux demi-cerlces
	 */
	private void calculerChampElectrique(){
		champElectrique = -differencePotentielle/HAUTEUR_ZONE;
	}
	/**
	 * Cette m�thode permet de connaitre le champ �lectrique du cyclotron.
	 * @return le champ �lectrique du cyclotron.
	 */
	public double getChampElectrique(){
		return champElectrique;
	}
	/**
	 * Cette m�thode permet de retourner la forme g�om�trique de la zone du milieu du cyclotron.
	 * @return la forme g�om�trique de la zone du milieu du cyclotron.
	 */
	public Shape getZoneMilieu(){
		return zoneContact;
	}
	/**
	 * Cette m�thode permet de calculer la p�riode du cyclotron selon la particule en jeu.
	 * @param charge la charge de la particule.
	 * @param masse la masse de la particule.
	 * @param OBJETS_ENREGISTRES ecouteur
	 */
	public void calculerPeriode(double charge, double masse,EventListenerList OBJETS_ENREGISTRES){
		periode = Math.abs((2*Math.PI*masse)/(charge*champMagnetique.modulus()));
		for(TransmissionInfoNiveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau4Listener.class) ) {
			ecout.getPeriode(periode);
		}
	}
	/**
	 * Cette m�thode permet d'obtenir la forme g�om�trique du cyclotron(cercle principale)
	 * @return la forme g�om�trique du cyclotron(cercle principale)
	 */
	public Shape getCyclotronShape(){
		return cerclePrincipale;
	}
	/**
	 * Cette m�thode permet d'obtenir la forme g�om�trique de la voie de la sortie.
	 * @return la forme g�om�trique de la voie de la sortie.
	 */
	public Shape getVoieSortie(){
		return voieSortieCollision;
	}
	/**
	 * Cette m�thode permet de modifier la tension du cyclotron.
	 * @param potentielle la nouvelle tension.
	 */
	public void setPotentielleElectrique(int potentielle) {
		differencePotentielle0 = potentielle;
	}
	/**
	 * Cette m�thode permet de modifier le champs magn�tique du cyclotron.
	 * @param nouveauChamps le nouveau champs magn�tique.
	 */
	public void setChampsMagnetique(double nouveauChamps) {
		champMagnetique = new Vector3d(0,0,nouveauChamps);
	}
	/**
	 * Cette m�thode permet d'obtenir le diametre du cyclotron.
	 * @return le diametre du cyclotron.
	 */
	public double getDiametre() {
		return DIAMETRE_CYCLOTRON;
	}
	/**
	 * Cette m�thode permet de savoir le cyclotron contient un point.
	 * @param x la position horizotale du point.
	 * @param y la position verticale du point.
	 * @return <p><b>true</b> le point est dans le cyclotron.
	 * <p><b>false</b> le point n'est pas dans le cyclotron.
	 */
	public boolean contient(double x, double y){
		if(cycloTransfo.contains(x, y)){
			return true;
		}else{
			return false;
		}
	}
}
