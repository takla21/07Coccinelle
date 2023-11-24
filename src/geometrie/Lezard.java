package geometrie;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Cette classe permet de dessiner le l�zard
 * @author Kevin Takla
 * @version 1.0
 */
public class Lezard {
	private double posX, posY;
	private final int LARGEUR_LEZARD = 50;
	private final int LONGUEUER_LEZARD = 60;
	private final double DIAMETRE_POINTE_LANGUE = 3;
	private final double DIAMETRE_TETE = 20;
	private Image teteLezardImg,corpsLezard, teteLezardBlack, corpsLezardBlack;
	private double langueLimiteX, langueLimiteY, langueIniX, langueIniY, langueX,langueY;
	private final double ANGLE_DEFAUT = -2*Math.PI*245/360;
	private double angle = ANGLE_DEFAUT;
	private Path2D.Double langue;
	private boolean penteFini = false;
	private final double vitesse = 3;
	private int nbAnimation;
	private Ellipse2D.Double pointeLangue;
	private int cptAnimationLangue = 0;
	private Rectangle2D.Double zoneContact;

	/**
	 * C'est le constructeur du l�zard
	 * @param posX la position en X du l�zard.
	 * @param posY la position en Y du l�zard.
	 */
	public Lezard(double posX, double posY){
		this.posX = posX;
		this.posY = posY;
		intialiserPosLangue();
		creerImageTete();
		creerImageCorps();
		creerImageTeteBlack();
		creerCorpsBlack();
		initialser();
	}
	/**
	 * Cette m�thode permet d'initialiser la postiion de la langue.
	 */
	private void intialiserPosLangue(){
		langueIniX = (posX + DIAMETRE_TETE/2);
		langueIniY = (posY + DIAMETRE_TETE/2);
	}

	/**
	 * Cette m�thode permet de chercher l'image de la t�te du l�zard
	 */
	private void creerImageTete(){
		URL url = getClass().getClassLoader().getResource("teteNonTransfo.gif");
		if(url == null){
			//System.out.println("erreur");
		}
		try{
			teteLezardImg = ImageIO.read(url);
		}
		catch(IOException e ){
			//System.out.println("erreur");
		}
	}

	private void creerImageTeteBlack(){
		URL url = getClass().getClassLoader().getResource("teteNonTransfoBlack.gif");
		if(url == null){
			//System.out.println("erreur");
		}
		try{
			teteLezardBlack = ImageIO.read(url);
		}
		catch(IOException e ){
			//System.out.println("erreur");
		}
	}

	/**
	 * Cette m�thode permet de chercher l'image du corps du l�zard.
	 */
	private void creerImageCorps(){
		URL url = getClass().getClassLoader().getResource("lezardNonTransfo.png");
		if(url == null){
			//System.out.println("erreur");
		}
		try{
			corpsLezard = ImageIO.read(url);
		}
		catch(IOException e ){
			//System.out.println("erreur");
		}
	}

	private void creerCorpsBlack(){
		URL url = getClass().getClassLoader().getResource("lezardNonTransfoBLACK.png");
		if(url == null){
			//System.out.println("erreur");
		}
		try{
			corpsLezardBlack = ImageIO.read(url);
		}
		catch(IOException e ){
			//System.out.println("erreur");
		}
	}

	/**
	 * Cette m�thode permet d'initialiser le bout de la langue au point de d�part.
	 */
	private void initialiserPositionLangue(){
		langue = new Path2D.Double();
		langue.moveTo(langueIniX, langueIniY);
	}

	/**
	 * Cette m�thode dessine le l�zard
	 * @param g2d le contexte graphique
	 * @param facteurX le facteur x pour convertir en monde r�el
	 * @param facteurY le facteur y pour convertir en monde r�el
	 * @param matMC la matrice transformation monde r�el.
	 * @param scientifique <p><b>true</b> le niveau est en mode scientifique
	 * <p><b>false</b> le niveau n'est pas en mode scientifique
	 */
	public void dessiner(Graphics2D g2d, double facteurX, double facteurY,AffineTransform matMC,boolean scientifique){
		AffineTransform matLocal = g2d.getTransform();
		final double AJUSTEMENT_ANGLE= 2*Math.PI*165/360;
		final double AJUSTEMENT_POSX = 4;
		final double AJUSTEMENT_POSY = 1.3;
		
		zoneContact = new Rectangle2D.Double(posX - AJUSTEMENT_POSX ,posY-DIAMETRE_TETE/2-AJUSTEMENT_POSY,2*DIAMETRE_TETE,DIAMETRE_TETE*2- 6*AJUSTEMENT_POSY );

		Color couleurCourante = g2d.getColor();
		g2d.setColor(Color.red);

		g2d.setStroke(new BasicStroke(12));
		g2d.draw(matMC.createTransformedShape(langue));

		g2d.setStroke(new BasicStroke(1));
		pointeLangue = new Ellipse2D.Double(langueX - DIAMETRE_POINTE_LANGUE/2, langueY - DIAMETRE_POINTE_LANGUE/2, DIAMETRE_POINTE_LANGUE, DIAMETRE_POINTE_LANGUE);
		if(cptAnimationLangue != 0){
			g2d.fill(matMC.createTransformedShape(pointeLangue));
		}


		g2d.rotate(AJUSTEMENT_ANGLE+angle,langueIniX*facteurX,langueIniY*facteurY);
		if(!scientifique){
			g2d.drawImage(teteLezardImg,(int)(posX*facteurX),(int)(posY*facteurY),(int)(DIAMETRE_TETE*facteurX),(int)(DIAMETRE_TETE*facteurY),null);
			g2d.drawImage(corpsLezard, (int)((posX+DIAMETRE_TETE/2-AJUSTEMENT_POSX)*facteurX), (int)((posY-DIAMETRE_TETE/2-AJUSTEMENT_POSY)*facteurY), (int)(LONGUEUER_LEZARD*facteurX) ,(int)(LARGEUR_LEZARD*facteurY), null);
		}else{
			g2d.drawImage(teteLezardBlack,(int)(posX*facteurX),(int)(posY*facteurY),(int)(DIAMETRE_TETE*facteurX),(int)(DIAMETRE_TETE*facteurY),null);
			g2d.drawImage(corpsLezardBlack, (int)((posX+DIAMETRE_TETE/2-AJUSTEMENT_POSX)*facteurX), (int)((posY-DIAMETRE_TETE/2-AJUSTEMENT_POSY)*facteurY), (int)(LONGUEUER_LEZARD*facteurX) ,(int)(LARGEUR_LEZARD*facteurY), null);
		}
		g2d.setTransform(matLocal);
		g2d.setColor(couleurCourante);

	}
	/**
	 * Cette m�thode permet de faire avancer le l�zard.
	 */
	public void avancerLezard(){
		posX--;
		posY++;
		intialiserPosLangue();
	}
	/**
	 * Cette m�thode retourne la position horizontale du l�zard.
	 * @return la position en x du l�zard.
	 */
	public double getX(){
		return posX;
	}
	/**
	 * Cette m�thode retourne la position verticale du l�zard.
	 * @return la position en y du l�zard.
	 */
	public double getY(){
		return posY;
	}

	/**
	 * Cette m�thode permet de faire avancer la langue du l�zard.
	 */
	public void avancerLangue(){
		cptAnimationLangue++;
		angle = Math.atan2(langueLimiteY - langueY, langueLimiteX - langueX);
		if(penteEnCours(nbAnimation) && penteFini){
			langueX += Math.cos(angle)*vitesse;
			langueY += Math.sin(angle)*vitesse;
			langue.lineTo(langueX,langueY);
		}else{
			angle = ANGLE_DEFAUT;
			penteFini = false;
		}

	}

	/**
	 * Cette m�thode permet de determiner l'orientation de la langue selon la position initale et la destination de la langue.
	 * @param departX la postition d�part en x de la langue.
	 * @param departY la postition d�part en y de la langue.
	 * @param finX la postition finale en x de la langue.
	 * @param finY la postition finale en y de la langue.
	 * @return l'orientation de la langue.
	 */
	private int determinerOrientationLangue(double departX,double departY,double finX,double finY){
		if(departX <= finX){
			if(departY <= finY){
				return 1;
			}else{
				return 2;
			}
		}else{
			if(departY <= finY){
				return 3;
			}else{
				return 4;
			}
		}
	}
	/**
	 * Cette m�thode permet de v�rifier si la langue est arriv�e � sa destination.
	 * @param nombreAnimation l'orientation de la langue.
	 * @return la valeur de verit� par rapport � son chemin: <p><b>true</b> l'animation a toujours lieu.
	 * <p><b>false</b> la pointe de langue est arriv� � sa destination.
	 */
	private boolean penteEnCours(int nombreAnimation){
		switch(nombreAnimation){
		case 1:
			if((langueLimiteX > langueX) && (langueLimiteY > langueY)){
				return true;
			}else{
				return false;
			}
		case 2:
			if((langueLimiteX > langueX ) && (langueLimiteY < langueY )){
				return true;
			}else{
				return false;
			}
		case 3:
			if((langueLimiteX < langueX ) && (langueLimiteY > langueY )){
				return true;
			}else{
				return false;
			}
		case 4:	
			if((langueLimiteX < langueX ) && (langueLimiteY < langueY )){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	/**
	 * Cette m�thode permet de situer le point d'arriver de la langue.
	 * @param limiteX la position en x de sa destination
	 * @param limiteY la position en y de sa destination
	 */
	public void setPositionLangue(double limiteX, double limiteY){
		this.langueLimiteX = limiteX;
		this.langueLimiteY = limiteY;
		cptAnimationLangue = 0;
		penteFini =true;
	}

	/**
	 * Cette m�thode permet d'initialiser la position de la langue
	 */
	public void initialser() {
		langueX = langueIniX;
		langueY = langueIniY;
		nbAnimation = determinerOrientationLangue(langueX,langueY,langueLimiteX,langueLimiteY);
		initialiserPositionLangue();
	}
	/**
	 * Cette m�thode permet de savoir si la langue est toujours en train de se d�placer
	 * @return la valeur de v�rit� sur le mouvement de la langue du l�zard.
	 */
	public boolean mouvementEnCours(){
		return penteFini;
	}
	/**
	 * Cette m�thode permet de connaitre la position en x de la langue.
	 * @return la position en x de la langue.
	 */
	public double getPosLangueX(){
		return langueX;
	}
	/**
	 * Cette m�thode permet de connaitre la position en y de la langue.
	 * @return la position en y de la langue.
	 */
	public double getPosLangueY(){
		return langueY;
	}
	/**
	 * Cette m�thode permet de trouver la forme g�ometrique de la langue.
	 * @return la pointe de langue.
	 */
	public Shape getLangue(){
		Path2D.Double langueRetourner = new Path2D.Double(langue);
		double posLangueActuelleX = langueX;
		double posLangueActuelleY = langueY;
		langueRetourner.lineTo(posLangueActuelleX, posLangueActuelleY + 1);
		langueRetourner.closePath(); 
		return langueRetourner;
	}
	/**
	 * Cette m�thode permet de retourner la zone de contact.
	 * @return la forme g�om�trique de la zone de contact
	 */
	public Shape getZoneContact(){
		return zoneContact;
	}
	
}

