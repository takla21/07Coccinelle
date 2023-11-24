
package geometrie;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import ecouteur.TransmissionInfoNiveau1Listener;
import physique.ClassePhysique;
import physique.Vector2d;
/**
 * 
 * Classe de géometrie qui permet de créer l'objet mirroir
 * 
 * @author Zi Zheng Wang colloboration de Kevin Takla.
 * @version 1.0
 */
public class Mirroire {
	private double posX;
	private double posY;
	private double longeur;
	private double largeur;
	private Shape mir;
	private double angle;
	private Rectangle2D.Double mirroire;
	private ArrayList<Vector2d> normale;
	private AffineTransform transfoZone, rotaZone;
	private ArrayList<Path2D.Double> normaleDessin;
	private ArrayList<Line2D.Double> lignesNormale, listeLigneTransfo;
	private ArrayList<Line2D.Double> ligneBlindage, ligneBlindageTransfo;
	private boolean normaleSelect[] = {true,false};
	private String nom;
	private double translatX =0;
	private double translatY =0;

	/**
	 * C'est la méthode qui permet de créer le mirroir
	 * @param posX la position initiale en X.
	 * @param posY la position initiale en Y.
	 * @param longeur la longueur du mirroir.
	 * @param largeur la largeur du mirroir.
	 * @param orientation l'orientation du mirroir.
	 * @param nom le nom du miroir
	 */
	public Mirroire(double posX, double posY, double longeur, double largeur, double orientation, String nom){
		this.posX = posX;
		this.posY = posY;
		this.longeur = longeur;
		this.largeur = largeur;
		this.angle = orientation;
		this.nom = nom;
		creerMirroire();
		creerNormale();
		creerLigne();
		transfoZone = new AffineTransform();
		rotaZone =  new AffineTransform();
		creerNormaleDessin();
	}
	/**
	 * Cette méthode permet de créer le mirroir en question.
	 */
	private void creerMirroire() {
		mirroire = new Rectangle2D.Double(posX,posY,longeur,largeur);
	}
	/**
	 * Cette méthode permet de créer les zones de contacts du miroir(lignes).
	 */
	private void creerLigne(){
		lignesNormale = new ArrayList<Line2D.Double>();
		lignesNormale.add(new Line2D.Double(posX,posY,posX,posY + largeur));
		lignesNormale.add(new Line2D.Double(posX + longeur,posY,posX + longeur,posY + largeur));
		ligneBlindage  = new ArrayList<Line2D.Double>();
		ligneBlindage.add(new Line2D.Double(posX,posY,posX + longeur,posY));
		ligneBlindage.add(new Line2D.Double(posX,posY + largeur,posX + longeur,posY + largeur));
	}

	/**
	 * Cette méthode permet de créer les normales.
	 */
	private void creerNormale(){
		normale = new ArrayList<Vector2d>();
		Vector2d normaleDroit;
		if(angle > 0){
			normaleDroit = new Vector2d(Math.cos(angle) + Math.PI/2,Math.sin(angle) + Math.PI/2);
		}else{
			normaleDroit = new Vector2d(Math.PI/2 + Math.cos(angle),-(Math.PI/2- Math.sin(angle)));
		}

		Vector2d normaleInverse = normaleDroit.multiply(-1);

		normale.add(normaleDroit);
		normale.add(normaleInverse);
	}
	/**
	 * Cette méthode permet de créer les normales qui seront dessiner lorsque le niveau sera en mode scientifique.
	 */
	private void creerNormaleDessin(){
		normaleDessin = new ArrayList<Path2D.Double>();
		final double largeurTrait = 6;
		Path2D.Double ligne = new Path2D.Double();
		ligne.moveTo(posX, posY +largeur/2);
		ligne.lineTo(posX - largeurTrait,  posY +largeur/2);
		normaleDessin.add(ligne);
		ligne = new Path2D.Double();
		ligne.moveTo(posX + longeur, posY +largeur/2);
		ligne.lineTo(posX + longeur + largeurTrait, posY +largeur/2);
		normaleDessin.add(ligne);
	}
	/**
	 ** Cette méthode permet de dessiner le mirroir.
	 * @param g2d le contexte graphique
	 * @param aff la matrice de translation
	 * @param c la couleur du mirroir
	 * @param matMC la matrice monde réel.
	 * @param scientifique <p><b>true</b> le niveau est en mode scientifique
	 * <p><b>false</b> le niveau n'est pas en mode scientifique
	 * @param facteurX un facteur en x qui permet de rendre le miroir plus clair
	 * @param facteurY un facteur en y qui permet de rendre le miroir plus clair
	 */
	public void dessiner(Graphics2D g2d, AffineTransform aff, Color c,AffineTransform matMC,boolean scientifique, double facteurX, double facteurY){
		AffineTransform matt = new AffineTransform(aff);
		AffineTransform rota = new AffineTransform();
		rota.rotate(angle,posX,posY);
		Shape mi = rota.createTransformedShape(mirroire); //rotate
		mir = matt.createTransformedShape(mi); //size Monde réel
		g2d.setColor(c);
		g2d.fill(mir);
		g2d.setColor(Color.white);
		g2d.draw(mir);


		if(scientifique){
			g2d.setColor(Color.red);
			g2d.drawString(nom,(int)((posX*facteurX)+translatX), (int)((posY*facteurY)+translatY));
			g2d.setColor(Color.white);
			float brise[] = {10};
			BasicStroke ligneBrise = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,  BasicStroke.JOIN_MITER,10.0f,brise, 0.0f);
			g2d.setStroke(ligneBrise);
			for(int nbNormale =0; nbNormale < normaleSelect.length ; nbNormale++){
				if(normaleSelect[nbNormale]){
					g2d.draw(matt.createTransformedShape(rota.createTransformedShape(normaleDessin.get(nbNormale))));
				}
			}

		}
		listeLigneTransfo = new ArrayList<Line2D.Double>(lignesNormale);
		ligneBlindageTransfo = new ArrayList<Line2D.Double>(ligneBlindage);
		for(int l =0; l<listeLigneTransfo.size();l++){
			Line2D.Double ligne = listeLigneTransfo.get(l);
			Line2D.Double nonLigne = ligneBlindageTransfo.get(l);
			
			ligne = scaleLigne(ligne,aff.getScaleX(),aff.getScaleY());
			ligne = rotateLigne(ligne, posX * aff.getScaleX(),posY * aff.getScaleY());
			ligne = translateLigne(ligne,aff.getTranslateX(), aff.getTranslateY());
			
			nonLigne = scaleLigne(nonLigne,aff.getScaleX(),aff.getScaleY());
			nonLigne = rotateLigne(nonLigne, posX * aff.getScaleX(),posY * aff.getScaleY());
			nonLigne = translateLigne(nonLigne,aff.getTranslateX(), aff.getTranslateY());
			//creerNormales();
			listeLigneTransfo.set(l,ligne);
			ligneBlindageTransfo.set(l,nonLigne);
		}
		g2d.setStroke(new BasicStroke(1));
		
		rotaZone = new AffineTransform(rota);
		transfoZone = new AffineTransform(aff);
		creerNormale();
	}
	/**
	 * Cette méthode permet de changer la position du mirrroir.
	 * @param posX la nouvelle position en X.
	 * @param posY la nouvelle position en Y.
	 */
	public void setPosition(double posX,double posY){
		this.posX = posX;
		this.posY = posY;
		creerMirroire();
	}
	/**
	 * Ceci permet de connaitre la position horizontale du mirroir.
	 * @return la position en x du mirroir.
	 */
	public double getX(){
		return posX;
	}
	/**
	 * Ceci permet de connaitre la position verticale du mirroir.
	 * @return la position en y du mirroir.
	 */
	public double getY(){
		return posY;
	}
	/**
	 * Ceçi permet de connaitre l'orientation du mirroir.
	 * @return l'angle du mirroir.
	 */
	public double getAngle(){
		return angle;
	}
	/**
	 * Cette méthode permet de savoir si un point quelconque touche au mirroir.
	 * @param posX2 la position en x du point en question.
	 * @param posY2 la position en y du point en question.
	 * @return la valeur de vérité de cette méthode: <p><b>true</b> le point touche le mirroir.
	 * <p><b>false</b> le point ne touche pas le mirroir.
	 */
	public boolean contains(double posX2, double posY2) {
		if(mir.contains(posX2, posY2)){
			return true;
		}else{
			return false;
		}

	}
	/**
	 * Cette méthode permet d'obtenir la forme géométrique du mirroir
	 * @return la forme géométrique du mirroir
	 */
	public Shape getShape(){
		return mir;
	}
	/**
	 *  Cette méthode permet d'obtenir l'aire du mirroir
	 * @return  l'aire du mirroir
	 */
	public Area aire(){
		Area aire = new Area(mirroire);
		return aire;
	}
	/**
	 * Cette méthode permet de calculer la réflexion du laser sur le mirroir
	 * @param vecteurIncident le vecteur initiale de la lumière
	 * @param cptDeviation le nombre de déviation
	 * @param nbNormale c'est la normale auquel il y a eu collision
	 * @param nbMir le numéro du mir
	 * @param  OBJETS_ENREGISTRES écouteur
	 * @return le vecteur finale de la lumière
	 */
	public Vector2d deviationMirroir(Vector2d vecteurIncident,int nbNormale, int cptDeviation,EventListenerList OBJETS_ENREGISTRES,int nbMir){
		Vector2d normalUtilise = normale.get(nbNormale).vecteurUnitaire();
		Vector2d vecteurResultat;
		vecteurResultat = ClassePhysique.reflexion(vecteurIncident.vecteurUnitaire(),normalUtilise);
		for(TransmissionInfoNiveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Listener.class) ) {
			ecout.getAngleIncidentMir(nbMir, ClassePhysique.calculAngle(vecteurIncident,normalUtilise));
			ecout.getAngleReflechieMir(nbMir, ClassePhysique.calculAngle(vecteurResultat, normalUtilise));
		}
		return vecteurResultat;
	}

	/**
	 * Cette méthode permet de modifier la position du mirroir
	 * @param x la nouvelle position horziontale du mirroir.
	 * @param y la nouvelle position verticale du mirroir.
	 */
	public void setPos(double x, double y){
		posX = x;
		posY = y;
	}
	/**
	 * Cette méthode permet d'autoriser le programme à dessiner la normale en question.
	 * @param nbNormale la normale en question
	 * @param dessinable <p><b>true</b> la normale sera dessiner.
	 * <p><b>false</b> la normale ne sera pas dessiner.
	 */
	public void dessinerNormale(int nbNormale, boolean dessinable) {
		normaleSelect[nbNormale] = dessinable;
	}
	/**
	 * Cette méthode permet de placer le nom du mirroir selon la translation du mirroir.
	 * @param translatX la nouvelle position en x du nom du mirroir.
	 * @param translatY la nouvelle position en y du nom du mirroir.
	 */
	public void setTranslatText(double translatX, double translatY){
		this.translatX = translatX;
		this.translatY = translatY;
	}

	/**
	 * Cette méthode permet d'envoyer les transformations appliquées sur le miroir.
	 * @return une liste des transformations appliquées sur le miroir.
	 */
	public ArrayList<AffineTransform> envoieTransfo(){
		ArrayList<AffineTransform> liste = new ArrayList<AffineTransform>();
		liste.add(transfoZone);
		liste.add(rotaZone);
		return liste;
	}

	/**
	 * Cette méthode permet d'envoyer les zones de contact du miroir.
	 * @return  les zones de contact du miroir.
	 */
	public ArrayList<Line2D.Double> envoieLigneCollision() {
		return listeLigneTransfo;
	}

	/**
	 * Cette méthode permet de faire une translation d'une ligne de facon manuelle.
	 * @param ligneActuelle la ligne à modifier.
	 * @param translatX la translation horizontale.
	 * @param translatY la translation verticale.
	 * @return la nouvelle ligne translaté.
	 */
	private Line2D.Double translateLigne(Line2D.Double ligneActuelle, double translatX, double translatY){
		double posX1 = ligneActuelle.getX1() + translatX;
		double posX2 = ligneActuelle.getX2() + translatX;
		double posY1 = ligneActuelle.getY1() + translatY;
		double posY2 = ligneActuelle.getY2() + translatY;
		return new Line2D.Double(posX1,posY1,posX2,posY2);
	}
	/**
	 * Cette méthode permet de faire une rotation autour d'un point manuellement.
	 * @param ligneActuelle la ligne à modifier.
	 * @param posRotateX la position horizontale de rotation.
	 * @param posRotateY la position verticale de rotation.
	 * @return la nouvelle ligne rotationner.
	 */
	private Line2D.Double rotateLigne(Line2D.Double ligneActuelle, double posRotateX, double posRotateY){
		Line2D.Double ligneTemp = translateLigne(ligneActuelle,-posRotateX,-posRotateY);
		double posX1 = (ligneTemp.getX1()*Math.cos(angle)) +  (ligneTemp.getY1()*-Math.sin(angle));
		double posY1 = (ligneTemp.getX1()*Math.sin(angle)) +  (ligneTemp.getY1()*Math.cos(angle));
		double posX2 = (ligneTemp.getX2()*Math.cos(angle)) +  (ligneTemp.getY2()*-Math.sin(angle));
		double posY2 = (ligneTemp.getX2()*Math.sin(angle)) +  (ligneTemp.getY2()*Math.cos(angle));
		ligneTemp = new Line2D.Double(posX1,posY1,posX2,posY2);
		ligneTemp = translateLigne(ligneTemp,posRotateX,posRotateY);
		return ligneTemp;
	}
	/**
	 * Cette méthode permet d'agrandir une ligne manuellement
	 * @param ligneActuelle la ligne à modifier.
	 * @param scaleX l'agrandissement en x.
	 * @param scaleY l'agrandissement en y.
	 * @return la nouvelle ligne agrandit.
	 */
	private Line2D.Double scaleLigne(Line2D.Double ligneActuelle,double scaleX, double scaleY){
		double posX1 = ligneActuelle.getX1()*scaleX;
		double posY1 = ligneActuelle.getY1()*scaleY;
		double posX2 = ligneActuelle.getX2()*scaleX;
		double posY2 = ligneActuelle.getY2()*scaleY;
		return new Line2D.Double(posX1,posY1,posX2,posY2);
	}
	/**
	 * Cette méthode permet de savoir si le laser fait une collision "illégale" par rapport au miroir
	 * @param laser le laser
	 * @return <p><b>true</b> le laser fait une collision "illégale"
	 * <p><b>false</b> le laser ne fait pas une collision "illégale"
	 */
	public boolean collisionIllegal(Line2D.Double laser){
		boolean collision = false;
		//double dists[] = {0,0};
		for(int l =0; l<ligneBlindageTransfo.size(); l++){
			Line2D.Double ligne = ligneBlindageTransfo.get(l);
			if(ligne.intersectsLine(laser)){
				collision = true;
				//System.out.println("IL Y A COLLISION");
			}
		}
		return collision;
	}

}
