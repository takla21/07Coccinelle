
package geometrie;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import physique.Vector2d;

/**
 * 
 * Classe de géometrie qui permet de créer l'objet coccinelle
 * 
 * @author Zi Zheng Wang
 * @version 3.0
 */
public class Coccinelle {

	private double posX;
	private double posY;
	private final double SIZE_BUG = 3;
	private double rotateAngle = 0;
	private Ellipse2D.Double bug;
	private Line2D.Double ligne;
	private Shape shadowBug;
	private final double CHARGE_PARTICULE_ELEMENTAIRE = 1.6*Math.pow(10,-19);
	private double charge = CHARGE_PARTICULE_ELEMENTAIRE;
	private double chargeAvant = charge;
	private boolean animationCommencer = false;
	private Vector2d vitesse = new Vector2d();
	private double masse = 0.0000002;
	private boolean chargeModifie = false;
	private boolean particuleModifieHumain = false;
	private Vector2d accelerationSave = new Vector2d();
    private Image coccinelleImage,coccinelleImageDeux;
    private int imageSize = 50;
    private Vector2d vitesseAvant = new Vector2d();
    private int compteur = 0;
    private boolean scientifique = false;
	/**
	 * Constructeur pour créer l'objet coccinelle
	 * @param posX - la coordonnée x
	 * @param posY - la coordinnée y
	 * @param orientation - l'angle de rotation
	 */
	public Coccinelle(double posX,double posY,double orientation){
		this.posX = posX;
		this.posY = posY;
		this.rotateAngle = orientation;
        compteur = 0;
		creerShapesSousJacentes();
		creerImageCocc();
	}
	/**
	 * Ce constructeur permet de créer la coccinelle lorsqu'on connait sa charge.
	 * @param posX - la coordonnée x
	 * @param posY - la coordinnée y
	 * @param orientation - l'angle de rotation
	 * @param charge la charge de la coccinelle
	 */
	public Coccinelle(double posX,double posY,double orientation,double charge){
		this.posX = posX;
		this.posY = posY;
		this.rotateAngle = orientation;
		this.charge = charge*Math.pow(10, -9);
		compteur = 0;
		particuleModifieHumain =true;
		creerShapesSousJacentes();
		creerImageCocc();
	}
	/**
	 * Méthode sert à créer les composants graphics de l'objet coccinelle
	 */
	private void creerShapesSousJacentes() {
		bug = new Ellipse2D.Double(posX - SIZE_BUG/2,posY - SIZE_BUG/2,SIZE_BUG,SIZE_BUG);
		ligne = new Line2D.Double(posX - SIZE_BUG/2,posY,posX + SIZE_BUG - SIZE_BUG/2,posY);
	}
	private void creerImageCocc(){
		URL url = getClass().getClassLoader().getResource("coccinelle.png");
		URL url2 = getClass().getClassLoader().getResource("coccDeux.png");
		if(url == null || url2 == null){
			//System.out.println("erreur");
		}
		try{
			coccinelleImage = ImageIO.read(url);
			coccinelleImageDeux = ImageIO.read(url2);
		}
		catch(IOException e ){
			//System.out.println("erreur");
		}
	}
	/**
	 * Méthode sert à dessiner l'objet coccinelle
	 * @param g2d - Graphics2d par défaut
	 * @param aff - la matrice monde réel
	 * @param translation  ca fait une translation lorsque l'image est décalé.
	 */
	
	public void dessiner(Graphics2D g2d, AffineTransform aff, boolean translation){
		compteur ++;
		AffineTransform matt = new AffineTransform(aff);
		double scaleX = aff.getScaleX();
		double scaleY = aff.getScaleY();
		double translateX = aff.getTranslateX();
		double translateY = aff.getTranslateY();
		double effetXTranslat =0;
		double effetYTranslat =0;
		if(translation){
			effetXTranslat =-10;
			effetYTranslat =-10;
		}
		Color couleurCourante = g2d.getColor();
		g2d.setColor(Color.red);
        shadowBug = matt.createTransformedShape(bug);
		g2d.fill(shadowBug);
		g2d.setColor(Color.green);
		matt.rotate(rotateAngle,posX,posY);
		Shape lig = matt.createTransformedShape(ligne);
		g2d.draw(lig);
		g2d.setColor(Color.black);
		g2d.draw(shadowBug);
		
		//coccinelle image setting
		g2d.rotate(rotateAngle,(int)((posX- SIZE_BUG)*scaleX + translateX +effetXTranslat+ imageSize/2),(int)((posY- SIZE_BUG)*scaleY +effetYTranslat+ translateY + imageSize/2));
		if(compteur%5 == 0 || compteur%5 == 1 || compteur%5 == 2){
			g2d.drawImage(coccinelleImage,(int)((posX- SIZE_BUG)*scaleX +effetXTranslat+ translateX),(int)((posY- SIZE_BUG)*scaleY + translateY + effetYTranslat),imageSize,imageSize,null);
		}else{
			g2d.drawImage(coccinelleImageDeux,(int)((posX- SIZE_BUG)*scaleX + translateX + effetXTranslat),(int)((posY- SIZE_BUG)*scaleY + translateY + effetYTranslat),imageSize,imageSize,null);
		}
		g2d.rotate(-rotateAngle,(int)((posX- SIZE_BUG)*scaleX + translateX + imageSize/2  + effetXTranslat),(int)((posY- SIZE_BUG)*scaleY + translateY + imageSize/2 + effetYTranslat));
		//////////////////////////
		 
		if(chargeModifie){
			if(charge ==0){
				g2d.setColor(Color.cyan);
			}else{
				if(charge<0){
					g2d.setColor(Color.yellow);
				}else{
					g2d.setColor(Color.green);
				}
			}
			g2d.fill(matt.createTransformedShape(bug));
			chargeModifie=  false;
		}
		if(animationCommencer && scientifique){
		VecteurGraphique fleche = new VecteurGraphique(new Vector2d(posX,posY).add(vitesse.vecteurUnitaire()), new Vector2d(posX,posY));
		VecteurGraphique fleche2 = new VecteurGraphique(new Vector2d(posX,posY).add(accelerationSave.vecteurUnitaire()), new Vector2d(posX,posY));
		fleche.dessiner(g2d, aff, Color.red);
		fleche2.dessiner(g2d, aff, Color.green);
		}
		
		g2d.setColor(couleurCourante);
	}
	/**
	 * Méthode qui modifie la position de l'objet coccinelle
	 * @param posX - la coordonnée x
	 * @param posY - la coordonnée y
	 */
	public void setPas(double posX,double posY){
		this.posX = posX;
		this.posY = posY;
		creerShapesSousJacentes();
	}
	/**
	 * Méthode qui modifie l'angle de rotation pour l'objet coccinelle
	 * @param orientation - l'angle de rotation
	 */
	public void setOrientation(double orientation){
		this.rotateAngle = orientation;
	}
	/**
	 * Méthode qui retourne la coordonnée x de l'objet occinelle
	 * @return la coordonnée x de l'objet occinelle
	 */
	public double getPosX(){
		return posX;
	}
	/**
	 * Méthode qui retourne la coordonnée y de l'objet occinelle
	 * @return la coordonnée y de l'objet occinelle
	 */
	public double getPosY(){
		return posY;
	}
	public Vector2d getPosition(){
		return new Vector2d(posX,posY);
	}
	/**
	 * Méthode qui retourne la charge de l'objet occinelle
	 * @return la cahrge de l'objet occinelle
	 */
	public double getCharge(){
		return charge;
	}
	/**
	 * Méthode qui permet de modifier la charge de coccinelle
	 * @param nouvelleCharge - la charge de coccinelle
	 * <p> la charge positive = 1
	 * <p> la charge négative = -1
	 * <p> la cahrge neutre = 0
	 */
	public void setCharge(double nouvelleCharge){
		chargeAvant = this.charge;
		this.charge = nouvelleCharge;
		particuleModifieHumain = true;
		chargeModifie = true;
	}
	/**
	 * Cette méthode permet de modifier sa charge lorsqu'il entre en contact avec une particule
	 * @param nouvelleCharge la nouvelle charge de la coccinelle
	 */
	public void setChargeParContact(double nouvelleCharge){
		chargeAvant = this.charge;
		this.charge = nouvelleCharge;
		chargeModifie = true;
		particuleModifieHumain =false;
	}
	/**
	 * Méthode qui retourne l'air de l'objet coccinelle
	 * @return uen aire de l'objet coccinelle
	 */
	public Area aire() {
		Area aireTotale = new Area(bug);
		return aireTotale;
	}
	/**
	 * Méthode qui détecte la collision entre une aire avec l'objet coccinelle
	 * @param uneForme - une Shape de l'objet
	 * @return <b>true</b> s'il y a une collision
	 * <p><b>false</b> s'il n'y a pas de collision
	 */
	public boolean collision(Shape uneForme) {
		Area aireForme = new Area(uneForme);
		Area inter = aire(); 
		inter.intersect(aireForme);

		if (!inter.isEmpty()) {
			return(true);
		} else {
			return(false);
		}
	}
	/**
	 * Méthode qui détecte la collision entre une aire avec l'objet coccinelle
	 * @param uneAire - une Aire de l'objet
	 * @return <b>true</b> s'il y a une collision
	 * <p><b>false</b> s'il n'y a pas de collision
	 */
	public boolean collision(Area uneAire) {
		Area inter = aire(); 
		inter.intersect(uneAire); 

		if (!inter.isEmpty()) {
			return(true);
		} else {
			return(false);
		}
	}
	/**
	 * Méthode qui détecte si l'objet coccinelle est entrée dans une autre Shape
	 * @param uneForme - une Shape de l'objet
	 * @return <b>true</b> si coccinelle est entré dans un objet
	 * <p><b>false</b> si coccinelle n'est pas entré dans un objet
	 */
	public boolean inclut(Shape uneForme){
		boolean inclut = false;
		Area a1 = new Area(bug);
		Area a2 = new Area(uneForme);
		Area vide = new Area(a2);
		vide.subtract(a1);
		if(vide.isEmpty()){
			inclut = true;
		}
		return inclut;
	}
	/**
	 * Méthode qui retourne une Shape de l'objet coccinelle
	 * @return une Shape de l'objet coccinelle
	 */
	public Shape getShape() {
		return shadowBug;
	}
	/**
	 * Méthode qui détecte si certains positions sont à l'intérieur de l'objet coccinelle
	 * @param x - la coordonnée x pour la position
	 * @param y - la coordonnée y pour la position
	 * @return <b>true</b> si l'objet coccinelle contient la position
	 * <p><b>false</b> si l'objet coccinelle ne contient pas la position
	 */
	public boolean contient(double x, double y){
		if(bug.contains(x,y)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Cette méthode permet de remettre la charge de la coccinelle à sa charge d'avant.
	 */
	public void restart(){
		if(!particuleModifieHumain){
		charge = chargeAvant;
		}
		vitesse = new Vector2d();
		vitesseAvant = vitesse;
		accelerationSave = new Vector2d();
	}
	/**
	 * Cette méthode permet de débuter l'animation si et seulement si la coccinelle est sur le point centrale du cyclotron
	 * @param pointDepart la forme géométrique du point centrale du cyclotron.
	 * @param matMC la matrice monde réelle
	 * @return  <p><b>true</b> l'animation peut débutée
	 * <p><b>false</b> l'animation ne peut pas débutée
	 */
	public boolean debutAnimation(Shape pointDepart,AffineTransform matMC){
		if(animationCommencer){
			return true;
		}else{
			if(collision(pointDepart, matMC)){
				animationCommencer = true;
				return true;
			}else{
				return false;
			}
		}
	}
	/**
	 * Cette méthode permet de vérifier si la coccinelle entre en collision avec une autre forme géométrique.
	 * @param autreShape l'autre forme géométrique.
	 * @param matMC la matrice monde réelle
	 * @return
	 */
	private boolean collision(Shape autreShape, AffineTransform matMC){
		shadowBug = matMC.createTransformedShape(bug);
		Shape shapeTransfo = matMC.createTransformedShape(autreShape);
		Area aire1 = new Area(shadowBug);
		Area aire2 = new Area(shapeTransfo);
		Area intersection = new Area(aire1);
		intersection.intersect(aire2);
		if(intersection.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * Cette méthode permet de connaitre la masse de la coccinelle
	 * @return la masse de la coccinelle.
	 */
	public double getMasse(){
		return masse;
	}
	/**
	 *  Cette méthode permet de connaître la vitesse de la coccinelle en vecteur.
	 * @return le vecteur vitesse.
	 */
	public Vector2d getVitesseActuelle(){
		return vitesse;
	}
	/**
	 * Cette méthode permet d'enlever l'animation de la coccinelle
	 */
	public void enleverAnimationCoccinelle(){
		animationCommencer = false;
	}
	/**
	 * Cette méthode permet de calculer la vitesse de la coccinelle selon son déplacement.
	 * @param deltaT le temps de déplacement
	 * @param x1 la première position de la coccinelle horizontale.
	 * @param x2 la deuxième position horizontale de la coccinelle.
	 * @param y1 la première position de la coccinelle verticale
	 * @param y2 la deuxième position verticale de la coccinelle.
	 */
	public void calculerVitesse(double deltaT,double x1,double x2,double y1,double y2){
		vitesseAvant = vitesse;
		vitesse = new Vector2d((x2-x1)/deltaT,(y2-y1)/deltaT);
	}
	/**
	 * Cette méthode permet de connaitre la vitesse (modulé) de la coccinelle.
	 * @return le module de la vitesse.
	 */
	public double getVitesseModuler(){
		return vitesse.module();
	}

	/**
	 * Cette méthode permet de faire avancer la coccinelle selon une accéleration et un temps quelconque.
	 * @param acceleration l'accélération de la coccinelle
	 * @param deltaT le temps de déplacement
	 */
	public void avancerCoccinelle(Vector2d acceleration, double deltaT){
		accelerationSave = acceleration;
		calculerVitesse(acceleration, deltaT);
		posX += vitesse.getX()*deltaT;
		posY += vitesse.getY()*deltaT;
		creerShapesSousJacentes();
	}
	/**
	 * Cette méthode permet de calculer la vitesse de la coccinelle selon l'accélération de la coccinelle.
	 * @param acceleration l'accélération de la coccinelle.
	 * @param deltaT le temps de déplacement
	 */
	private void calculerVitesse(Vector2d acceleration, double deltaT){
		vitesseAvant = vitesse;
		vitesse = vitesse.add(acceleration.multiply(deltaT));
	}
	/**
	 * Cette méthode permet de modifier la vitesse en Y de la coccinelle. 
	 * @param vitesseY la nouvelle vitesse en y de la coccinelle.
	 */
	public void setVitesseY(double vitesseY){
		vitesse.setY(vitesseY);
	}
	/**
	 * Cette méthode permet d'avancer la coccinelle sans accéleration.
	 * @param deltaT le temps de déplacement.
	 */
	public void avancerCoccinelleSansAcceleration(double deltaT){
		posX += vitesse.getX()*deltaT;
		posY += vitesse.getY()*deltaT;
		creerShapesSousJacentes();
	}
	/**
	 * Cette méthode permet de moduler la vitesse et de le transferer vers l'axe horizontale.
	 */
	public void transformerVitesse(){
		double vitesseTransfo = vitesse.module();
		vitesse = new Vector2d(vitesseTransfo,0);
	}
	/**
	 * Cette méthode permet de modifier la masse de la coccinelle.
	 * @param masseEntre la nouvelle masse de la coccinelle.
	 */
	public void setMasse(double masseEntre) {
		masse = masseEntre;
		
	}
	/**
	 * Cette méthode permet d'annuler la vitesse actuelle.
	 */
	public void clearVitesse(){
		vitesse = new Vector2d();
		vitesseAvant = new Vector2d();
	}
	/**
	 * Cette méthode permet de savoir si le niveau est en mode scientifique ou non.
	 * @param scientifique  <p><b>true</b> le niveau est en mode scientifique
	 * <p><b>false</b> le niveau n'est plus en mode scientifique
	 */
	public void modeScientifique(boolean scientifique){
		this.scientifique = scientifique;
	}
	/**
	 * Cette méthode permet de connaitre l'accélération de la coccinelle.
	 * @param deltaT le temps entre 2 déplacement
	 * @return l'accéleration de la coccinelle
	 */
	public double getAcceleration(double deltaT){
		if(!accelerationSave.equal(new Vector2d())){
			return accelerationSave.module();
		}else{
			Vector2d acceleration = new Vector2d((vitesse.getX()-vitesseAvant.getX())/deltaT,(vitesse.getY()-vitesseAvant.getY())/deltaT);
			return acceleration.module();
		}
	}
}