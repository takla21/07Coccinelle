package geometrie;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import physique.Position;
import physique.RungeKutta;
import physique.Vector2d;
/**
 * 
 * Classe de géometrie qui permet de créer l'objet ressort
 * 
 * @author Zi Zheng Wang
 * @version 1.0
 */
public class Ressort implements Serializable{
	private static final long serialVersionUID = 666917129642122613L;
	private final double gravite = 9.8066;  // Constante gravitationnelle (m/s²)
	private double masse = 5; // Masse (kg)
	private double u = 0.96; // Coefficient de frottement (aucun unité)
	private double vitesse = 0; // Vitesse (m/s)
	private double k = 10; // Constante du ressort (N/m)
	private double acceleration; // Accélération (m/s²)
	private double dx; // Déplacement (cm)
	private double distanceBlocOrigine; // Distance de l'origine (cm)

	private final double NOMBRE_LIAISON = 17;
	private final double LARG_CORE = 12;
	private final double LONGEUR_MAX = 100; //longeur total du system ressort
	private final int NB_GRADS = 20;
	private boolean enDeplacement = false;
	private double x = 0, y = 0; // valeur par défaut
	private double origine = x + LONGEUR_MAX/2;
	private double oldBlocPos;
	private double xRessort;
	private double positionReelleBloc = origine; // position X du bloc, initialisée à 50m
	private double gapRessortGauche = 1.0; // espace entre chaque sommets du ressort, cette variable est utilisée pour le réalisme du ressort
	private double forceRessort;
	private double forceFrottement;
	private int nbDeLoop;
	private int transparensite = 255;
	private int dtNonSignificatif;
	private int tempsDArret;
	private Path2D.Double ressortGauche,ressortDroit;
	private Ellipse2D.Double noyau;
	private Rectangle2D.Double mur;
	private Rectangle2D.Double sol;
	private Path2D.Double solTop;
	private Position blocPos;
	private RungeKutta RK4;
	private double longeurMax = LONGEUR_MAX;
	private double xRessortDroit;
	private double gapeRessortDroit;
	private double largeurNoyau = LARG_CORE;
	private double hauteurRessort = largeurNoyau/3;
	String[] grads = new String[NB_GRADS];
	private Shape core;
	private double rotation = 0;
	/**
	 * Constructeur de la classe Ressort, créer un objet ressort
	 * @param x - largeur en cm de l'objet ressort
	 * @param y - hauteur en cm de l'objet ressort
	 * @param largeurSystem - hauteur en cm du system ressort
	 */
	public Ressort(double x, double y, double largeurSystem) {

		this.x = x;
		this.y = y;
		this.dx = 0.0;
		this.vitesse = 0.0;
		this.distanceBlocOrigine = 0.0;
		this.blocPos = new Position(0,0);
		this.RK4 = new RungeKutta();
		this.xRessort = x;
		this.longeurMax = largeurSystem;
		this.origine = LONGEUR_MAX/2 + x;
		this.positionReelleBloc = origine;
		this.xRessortDroit = longeurMax;
		this.gapRessortGauche = (positionReelleBloc - this.x) / NOMBRE_LIAISON; // 17 est le nombre de lineTo's pour dessiner le ressort
		this.gapeRessortDroit = (longeurMax - (positionReelleBloc + largeurNoyau)) / NOMBRE_LIAISON;
		for (int i = -45, k = 0; k < NB_GRADS; i += 5, k ++)
		{
			grads[k] = "" + i;
		}
		genererSystemRessort();
	}
	/**
	 * Méthode pour créer les composants graphics du ressort
	 */
	private void genererSystemRessort() {
		
		gapRessortGauche = (positionReelleBloc - x) / NOMBRE_LIAISON;
		ressortGauche = new Path2D.Double();
		ressortGauche.moveTo(x, y);
		for(int compteur = 0; compteur <= NOMBRE_LIAISON; compteur++){
			if(compteur%2 == 0){
				ressortGauche.lineTo(xRessort += gapRessortGauche, y - hauteurRessort / 2);	
			}else{
				ressortGauche.lineTo(xRessort += gapRessortGauche, y + hauteurRessort / 2);	
			}
		}
		xRessort = x;

		gapeRessortDroit = (longeurMax - (positionReelleBloc + largeurNoyau)) / NOMBRE_LIAISON;
		ressortDroit = new Path2D.Double();
		ressortDroit.moveTo(xRessortDroit, y);
		for(int compteurSeconde = 0; compteurSeconde <= NOMBRE_LIAISON; compteurSeconde++){
			if(compteurSeconde%2 == 0){
				ressortDroit.lineTo(xRessortDroit -= gapeRessortDroit, y - hauteurRessort / 2);
			}else{
				ressortDroit.lineTo(xRessortDroit -= gapeRessortDroit, y + hauteurRessort / 2);
			}
		}
		xRessortDroit = longeurMax;

		noyau = new Ellipse2D.Double(positionReelleBloc, y - largeurNoyau / 2 , largeurNoyau, largeurNoyau);
	}
	/**
	 * dessiner l'objet ressort le Graphics2D passé en parametre
	 * 
	 * @param g2d - Graphics2D par défaut
	 * @param matTransfo - la matrice monde réel
	 * @param rota - l'angle de rotation
	 */
	public void dessiner(Graphics2D g2d, AffineTransform matTransfo, double rota) {

		AffineTransform matLocale = new AffineTransform(matTransfo);
		matLocale.rotate(rota,origine + LARG_CORE/2, y + LARG_CORE/2);
		Shape ressorLeft = matLocale.createTransformedShape(ressortGauche); 
		Shape ressortRight = matLocale.createTransformedShape(ressortDroit); 
		core = matLocale.createTransformedShape(noyau); 
		g2d.setColor(new Color(255, 255, 255, transparensite));
		g2d.draw(ressorLeft);
		g2d.draw(ressortRight);
		g2d.setColor(Color.lightGray);
		g2d.fill(core);
		g2d.setColor(Color.gray);
		g2d.draw(core);
	}
	/**
	 * dessiner l'objet ressort le Graphics2D passé en parametre (une version sans paramètre de l'angle de rotation, serve pour l'éditeur de niveau)
	 * 
	 * @param g2d - Graphics2D par défaut
	 * @param matTransfo - la matrice monde réel
	 */
	public void dessiner(Graphics2D g2d, AffineTransform matTransfo) {

		AffineTransform matLocale = new AffineTransform(matTransfo);
		matLocale.rotate(rotation,origine + LARG_CORE/2, y + LARG_CORE/2);
		Shape ressorLeft = matLocale.createTransformedShape(ressortGauche); 
		Shape ressortRight = matLocale.createTransformedShape(ressortDroit); 
		core = matLocale.createTransformedShape(noyau); 
		g2d.setColor(new Color(255, 255, 255, transparensite));
		g2d.draw(ressorLeft);
		g2d.draw(ressortRight);
		g2d.setColor(Color.lightGray);
		g2d.fill(core);
		g2d.setColor(Color.gray);
		g2d.draw(core);
	}
	/**
	 * Calcule les nouvelles valeurs physiques (accélération, vitesse, position, frottement, force du ressort) apres un certain temps et redessine l'objet ressort
	 * 
	 * @param deltaT - La variation du temps depuis la derniere mise a jour de la position de l'obejt ressort
	 */
	public void mettrePositionAJour(double deltaT) {

		oldBlocPos = blocPos.pos;
		blocPos.pos = positionReelleBloc - origine;

		if (vitesse != 0.0) {
			forceFrottement = ((-u) * gravite * masse) * vitesse / Math.abs(vitesse);
			//System.out.println("forcefrottement"+forceFrottement);
		} else {
			forceFrottement = 0;
		}

		forceRessort = -(k * distanceBlocOrigine);
		//System.out.println("forceRessort"+forceRessort);
		acceleration = (forceFrottement + forceRessort) / masse;
		blocPos.a = acceleration;

		blocPos = RK4.integrate(blocPos, (int)(deltaT * nbDeLoop * 10) / 10.0, deltaT, acceleration);
        //arreter ressort
		if (blocPos.pos / Math.abs(blocPos.pos) == oldBlocPos / Math.abs(oldBlocPos)) {
			dtNonSignificatif++;
		} else {
			dtNonSignificatif = 0;	
		}

		if (dtNonSignificatif * deltaT >= tempsDArret) {
			enDeplacement = false;
		} else {
			distanceBlocOrigine = blocPos.pos;
			vitesse = blocPos.v;
			positionReelleBloc = distanceBlocOrigine + origine;
			//System.out.println("position réelle du bloc"+positionReelleBloc);
			genererSystemRessort();
			nbDeLoop++;
		}
	}
	/**
	 * Permet de déplacer le bloc dans la scène d'animation 
	 * 
	 * @param dx -  le deplacement en m du bloc 
	 */
	public void setDeplacement(double dx) {
		this.dx = dx;
		if (this.positionReelleBloc + this.dx >= origine) {
			if (this.positionReelleBloc + largeurNoyau + this.dx <= 100.000) {
				this.positionReelleBloc += this.dx;
			} else {
				positionReelleBloc = 100.000 - largeurNoyau; 
			}
		} else {
			positionReelleBloc = origine; 
		}

		this.distanceBlocOrigine = positionReelleBloc - origine;
		genererSystemRessort();
	}
	public void reinitialiser() {
		this.positionReelleBloc = origine;
		this.dx = 0.0;
		this.vitesse = 0.0;
		this.acceleration = 0.0;
		this.blocPos.reset();
		this.nbDeLoop = 0;
		this.transparensite = 255;
		this.dtNonSignificatif = 0;
		this.forceFrottement = 0;
		this.forceRessort = 0;
		genererSystemRessort();
	}
	public double getX() {
		return  this.x;
	}
	/**
	 * 
	 * @return la hauteur du bloc
	 */
	public double getY() {
		return this.y;
	}
	public Vector2d getPosition() {
		return new Vector2d(this.x,this.y);
	}
    public double getRotation(){
    	return this.rotation;
    }
	/**
	 * 
	 * @return la position du bloc en m dans la scène
	 */
	public double getPositionReelleBloc() {
		return this.positionReelleBloc;
	}

	/**
	 * 
	 * @return la vitesse du bloc en m/s
	 */
	public double getVitesse() {
		return this.vitesse;
	}

	/**
	 * 
	 * @return l'accélération du bloc en m/s²
	 */
	public double getAcceleration() {
		return this.acceleration;
	}

	/**
	 * 
	 * @return la force de frottement en Newton entre le bloc et le sol
	 */
	public double getForceFrottement() {
		return this.forceFrottement;
	}

	/**
	 * 
	 * @return la force du ressort appliquée au bloc en Newton
	 */
	public double getForceRessort() {
		return this.forceRessort;
	}

	/**
	 * 
	 * @return la force gravitationelle appliquée au bloc
	 */
	public double getNormale(){
		return this.masse*this.gravite;
	}

	/**
	 * 
	 * @return une chaine de toute les graduations de l'axe X de notre systeme bloc-ressort
	 */
	public String[] getLabels() {
		return this.grads;
	}

	/**
	 * 
	 * @return la largeur en m du bloc du systeme
	 */
	public double getLargeurBloc() {
		return this.largeurNoyau;
	}

	/**
	 * 
	 * @return la hauteur du bloc en m
	 */
	public double getHauteurBloc() {
		return this.largeurNoyau;
	}
	
	/**
	 * 
	 * @return la distance entre le bloc et le 0 en m
	 */
	public double getDistanceBlocOrigine() {
		return this.distanceBlocOrigine;
	}
	/**
	 * Méthode retourne une Shape de bloc dans le ressort
	 * @return core - la Shape de bloc
	 */
	public Shape getShapeBloc(){
		return core;
	}
	public double getLongeurMax() {
		
		return this.longeurMax;
	}
	/**
	 * Méthode retourne un vecteur qui représente la position de bloc dans le system ressort
	 * @return un vecteur qui contient les deux coordonnées de la position de ressort
	 */
    public Vector2d getPositionShape(){
    	double x = core.getBounds().getX();
		double y = core.getBounds().getY();
		Vector2d central = new Vector2d(x,y);
		return central;
	}
	/**
	 * 
	 * @return l'etat de mouvement du bloc
	 */
	public boolean isEnDeplacement() {
		return this.enDeplacement;
	}

	/**
	 * changer la masse du bloc
	 * 
	 * @param m - la masse du bloc desirée en kg
	 */
	public void setMasse(double m) {
		this.masse = m;
	}

	/**
	 * changer le coefficient de frottement entre le sol et le bloc
	 * 
	 * @param u - coefficient de frottement desiré entre le sol et le bloc
	 */
	public void setCoFrottement(double u) {
		this.u = u;
	}

	/**
	 * changer la constante de force du ressort
	 * 
	 * @param k - la constante desiée du ressort
	 */
	public void setConstantRessort(double k) {
		this.k = k;
	}

	/**
	 * changer l'etat de movement du bloc
	 * 
	 * @param b - en mouvement ou non
	 */
	public void setEnDeplacement(boolean b) {
		this.enDeplacement = b;
	}
	/**
	 * changer l'opacité du ressort
	 * 
	 * @param i - valeur de l'opacité
	 */
	public void setAlphaRessort(int i) {
		this.transparensite = i;
		genererSystemRessort();
	}
	/**
	 * 
	 * @param b la largeur du noyau du ressort
	 */
    public void setLargeurNoyau(double b){
    	this.largeurNoyau = b;
    	this.hauteurRessort = largeurNoyau/3;
		genererSystemRessort();
    }
	/**
	 * changer le temps avant l'arret de l'animation
	 * 
	 * @param i - valeur du temps
	 */
	public void setTempsDArret(int i) {
		this.tempsDArret = i;
	}
	public void setVitesse(double vitesse2) {
		this.vitesse = vitesse2;
	}
	/**
	 * Méthode sert à modifier la longeur totale du system ressort
	 * @param longeurMax - longeur totale du system ressort
	 */
	public void setLongeurMax(int longeurMax){
		this.longeurMax = longeurMax;
		this.origine = LONGEUR_MAX/2 + x;
		this.positionReelleBloc = origine;
		genererSystemRessort();
	}
	/**
	 *  Méthode sert à modifier l'angle de rotation pour le system ressort
	 * @param rota - l'angle de rotation
	 */
	public void setRotation(double rota){
		rotation = Math.toRadians(rota);
		genererSystemRessort();
	}
	public void setPosition(Vector2d position){
		this.x = position.getX();
		this.y = position.getY();
		genererSystemRessort();
	}
	/**
	 * placer le bloc immédiatement a un endroit precis
	 * 
	 * @param d - position en valeur reelle
	 */
	public void setPositionReelle(double d) {
		this.positionReelleBloc = d + origine;
		genererSystemRessort();
	}
}

