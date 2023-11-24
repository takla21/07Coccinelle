
package sceneanimation;
import ecouteur.CoccinelleListener;
import geometrie.BallonChasseur;
import geometrie.Coccinelle;
import geometrie.VecteurGraphique;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import physique.ClassePhysique;
import physique.Vector2d;

import javax.swing.JLabel;
/**
 * 
 * Classe niveau trois du jeu de coccinelle, le niveau de collision
 * 
 * @author Zi Zheng Wang
 * @version 3.0
 */
public class NiveauTrois extends JPanel implements Runnable {
	private static final long serialVersionUID = -218906068015857583L;
	private double diametrePt = 6;
	private ArrayList<Integer> coordX;
	private ArrayList<Integer> coordY;
	private Coccinelle coccinelle;
	private boolean animation = false;
	private boolean animationEnCours = false;
	private int compteur = 0;
	private final double SIZE_BUG = 3;
	private boolean firstTime = true;
	private boolean permetStock = false;
	private Ellipse2D.Double fosse,centreSwich,teteSoleil,flashCercle;
	private final double SIZE_FOSSE = 3;
	private double largeurMonde = 100;
	private AffineTransform matMC;
	private boolean premierFois = true;
	private double pixelsParUniteX;
	private double pixelsParUniteY;
	private double angleRotation = 0,angleBUn = 0,angleBDeux = 0,angleBTrois = 0,angleBQuatre = 0;
	private int portion = 2;
	private int VitesseList[] = {160,128,96,64,32};
	private int sens = -1;
	private BallonChasseur ballonCentral,ballonUn,ballonDeux,ballonTrois,ballonQuatre,ballonGauche, ballonDroit/*ballonUnShadow,ballonDeuxShadow,ballonTroisShadow,ballonQuatreShadow*/;
	private Rectangle2D.Double murHaut,murBas,murJardin,murCour;
	private Shape murH,murB,murG,murD,trou,core;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList(); //collection 
	private double deltaTemps = 0.03;
	private double e[] = {1/2, 5/9, 8/9, 15/16, 19/20}; // la coeficient de restitution pour la collision inélastique
	private double diametreBallon = 10;
	private double masseBallonUn = 5;
	private double masseBallonDeux = 5;
	private double masseBallonTrois = 5;
	private double masseBallonQuatre = 5;
	private Vector2d centreSoleil = new Vector2d(45.5,35);;
	private Vector2d positionUn, positionDeux, positionTrois, positionQuatre, normalMurVer, normalMurHor;
	private Vector2d positionFinaleUn,positionFinaleDeux,positionFinaleTrois,positionFinaleQuatre;
	private Vector2d vitesseUn, vitesseDeux, vitesseTrois, vitesseQuatre;
	private Vector2d vitesseFinalUn, vitesseFinalDeux, vitesseFinalTrois, vitesseFinalQuatre;
	private Vector2d positionBallonGauche,positionBallonDroit;
	private Vector2d vitesseGauche,vitesseDroit;
	private Vector2d defaut = new Vector2d(0,0);
	private ArrayList<Vector2d> collectionVitesse= new ArrayList<Vector2d>();
	private TexturePaint face;
	private Color mauve = new Color(117,81,162);
	private Image img = null;
	private Image img2 = null;
	private Image img3 = null;
	private int coeficientIndex = 3;
	private boolean booleanCentral[] = new boolean[5];
	private int compteurCentrale[] = new int[5];
	private boolean booleanMurHor[] =  new boolean[5];
	private int compteurMurHor[] = new int[5];
	private boolean booleanMurVer[] =  new boolean[5];
	private int compteurMurVer[] = new int[5];
	private boolean booleanBallon[] = new boolean[14];
	private int compteurBallon[] =  new int[14];
	private double delaisRecollision = 8;  // un delais du temps pour controler les méthodes de collision ne répeter pas à deux coup de suit
	private double masseInfini = 99999;
	private double scalaireImpulsion = 1.5; //une grandeur de scalaire raisonnable pour les billes rebondissent
	private boolean colorChange = false;
	private boolean collisionCentral = false;
	private double grandeurVitesseDomine = -20; //Une grandeur de vitesse que j'ai mit par défaut pour le ballon gauche et droit.
	private Color red = new Color(255,0,0,100);
	private Color blue = new Color(0,0,255,100);
	private boolean scientifique = false;
	private Color yellow = new Color(255,255,64,100);
	private Color axeBlue = new Color(128,158,182);
	private int echelle = 0;
	private VecteurGraphique vecteurUn, vecteurDeux, vecteurTrois, vecteurQuatre;
	private double controleVitesseRotation = 64;
	private AudioClip musicFond;
	private boolean drawLaser = false;
	private boolean vitesseRotationNegative = false;
	private boolean champBlack = false;
	private int compteurInverse = 0;
	private int compteurBonSens = 0;
	private boolean correctionSens = false;
	private int delaisCorrectionRotation = 10;
	/**
	 * Constructeur de NiveauTrois du jeu de coccinelle
	 */
	public NiveauTrois() {
		setBackground(Color.BLACK);
		creerMusic();
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);
		creerBug();
		creerSoleil();
		creerMur();
		creerBallon();
		creerVecteur();
		lireLesTextures();
		coordX = new ArrayList<Integer>();
		coordY = new ArrayList<Integer>();
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				if(permetStock){
					if(!animation){
						if(arg0.getX() <= 0){
							coordX.add( 1 );
							coordY.add( arg0.getY() );	
						}
						if(arg0.getX() >= getWidth()){
							coordX.add( getWidth() - 1 );
							coordY.add( arg0.getY() );	
						}
						if(arg0.getY() <= 0){
							coordX.add( arg0.getX() );
							coordY.add( 1 );	
						}
						if(arg0.getY() >= getHeight()){
							coordX.add( arg0.getX() );
							coordY.add( getHeight() - 1 );	
						}else{
							coordX.add( arg0.getX() );
							coordY.add( arg0.getY() );	
						}
						repaint();
					}
				}
			}
			
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
			@Override
			public void mousePressed(MouseEvent e) {
				animation = false;
				compteur = 0;
				firstTime = true;
				permetStock = false;
				animationEnCours = false;
				if(firstTime){
					if(Math.abs(e.getX()/pixelsParUniteX - (coccinelle.getPosX()+SIZE_BUG/2))<= 4 && Math.abs(e.getY()/pixelsParUniteY - (coccinelle.getPosY()+SIZE_BUG/2))<= 4){
						firstTime = false;
						permetStock = true;
						for(CoccinelleListener ecout : OBJETS_ENREGISTRES.getListeners(CoccinelleListener.class) ) {
							ecout.action();
						}
					}else{
						//System.out.println("faut commencer près de coccinelle!!!");
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(permetStock){
					animation = true;
					permetStock = false;
					demarrer();
					//System.out.println("a");
				}
			}
		});
	}
	/**
	 * Cette méthode permet de jouer la musique
	 * @param b <p><b>true</b> la musique peut jouer
	 * <p><b>false</b> la musique est arrêté.
	 */
	public void commencerMusic(boolean b){
		if(b){
			musicFond.loop();
		}else{
			musicFond.stop();
		}
	}
	/**
	 * Cette méthoder permet de chercher la musique de fond
	 */
	private void creerMusic(){
		URL urlFichier = getClass().getClassLoader().getResource("saintCroix.wav");
		musicFond = Applet.newAudioClip(urlFichier);
	}
	
	/**
	 * Méthode pour dessiner les composants graphiques dans le niveau Trois
	 */
	URL urlFichier = getClass().getClassLoader().getResource("cerclelaser.wav");
	AudioClip monClip = Applet.newAudioClip(urlFichier);
	URL urlFichierDeux = getClass().getClassLoader().getResource("champBlue.wav");
	AudioClip monClipDeux = Applet.newAudioClip(urlFichierDeux);
	public void paintComponent(Graphics g) {
		Ellipse2D.Double cercle;
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(!scientifique){
			if(!collisionCentral){
				champBlack = false;
				g2d.drawImage(img,0,0,null);
			}else{
				if(!champBlack){
					monClipDeux.play();
					champBlack = true;
				}
				g2d.drawImage(img2,0,0,null);
			}
		}else{
			g2d.drawImage(img3,0,0,null);
			//axe
			echelle = (int)(5*(this.getWidth()/largeurMonde));
			g2d.setColor(axeBlue);
			for(int a = 0 ; a <= this.getHeight() ; a++){
				g2d.drawLine(0,this.getHeight()-a*echelle,8,this.getHeight()-a*echelle);
				for(int k = 0 ; k <= 4 ; k++){
					g2d.drawLine(0,this.getHeight()-a*echelle-k*echelle/5,3,this.getHeight()-a*echelle-k*echelle/5);
				}
				if(!(a ==0)){
					g2d.drawString(a*5+"",9,this.getHeight()-a*echelle);
				}
			}
			for(int a = 0 ; a <= this.getWidth() ; a++){
				g2d.drawLine(echelle*a-1, this.getHeight(),echelle*a-1, this.getHeight()-8);
				for(int k = 0 ; k <= 4 ; k++){
					g2d.drawLine(echelle*a+k*echelle/5-1, this.getHeight(),echelle*a+k*echelle/5-1, this.getHeight()-3);
				}
				if(!(a ==0)){
					g2d.drawString(a*5+"",echelle*a-1, this.getHeight()-9);
				}
			}

		}
		if(premierFois){
			calculerMatriceMondeVersComposant(largeurMonde);
			premierFois = false;
		}	
		//Mur
		//murHaut,murBas,murJardin,murCour
		murH = matMC.createTransformedShape(murHaut);
		murB = matMC.createTransformedShape(murBas);
		murG = matMC.createTransformedShape(murJardin);
		murD = matMC.createTransformedShape(murCour);
		
		g2d.setColor(Color.DARK_GRAY);
		fosse = new Ellipse2D.Double(15,55,SIZE_FOSSE,SIZE_FOSSE);
		trou = matMC.createTransformedShape(fosse);
		g2d.fill(trou);
		centreSwich = new Ellipse2D.Double(15+SIZE_FOSSE/2-1,55+SIZE_FOSSE/2-1,2,2);
		core = matMC.createTransformedShape(centreSwich);
		//le visage de soleil
		teteSoleil = new Ellipse2D.Double(largeurMonde/2 - 14.5,75/2 - 12.5,20,20);
		Shape tete = matMC.createTransformedShape(teteSoleil);
		g2d.setPaint(face);
		//g2d.fill(tete);
		g2d.setColor(Color.gray);
		g2d.setStroke(new BasicStroke(2));
		//g2d.draw(tete);
		if(colorChange){
			if(!scientifique){
				g2d.setColor(red);
			}else{
				g2d.setColor(blue);
			}
			if(!drawLaser){
				monClip.play();
				drawLaser = true;
			}
		g2d.setStroke(new BasicStroke(35));
		g2d.draw(matMC.createTransformedShape(flashCercle));
		g2d.setStroke(new BasicStroke(1));
		}
		//le ballonCentral(shadow)
		ballonCentral.dessiner(g2d,matMC,sens*angleRotation,"Ballon Central");
		//coccinelle
		g2d.setColor(Color.green);	
		for (int k = compteur; k < coordX.size(); k++) {
			cercle = new Ellipse2D.Double( coordX.get(k)-diametrePt/2, coordY.get(k)-diametrePt/2, diametrePt, diametrePt);
			g2d.fill(cercle);
		}
		coccinelle.dessiner(g2d, matMC,false);
		//ballon
		setBallonVisible();
		//System.out.println("Ballon Un angle"+angleBUn);
		ballonUn.dessiner(g2d,matMC,angleBUn,"  Ballon A");
		ballonDeux.dessiner(g2d,matMC,angleBDeux,"  Ballon B");
		ballonTrois.dessiner(g2d,matMC,angleBTrois,"  Ballon C");
		ballonQuatre.dessiner(g2d,matMC,angleBQuatre,"  Ballon D");
		ballonGauche.dessiner(g2d,matMC,sens*angleRotation,45.5,35);
		ballonDroit.dessiner(g2d,matMC,sens*angleRotation,45.5,35);
		//vecteur
		if(scientifique){
		vecteurUn.dessiner(g2d, matMC, Color.green);
		vecteurDeux.dessiner(g2d, matMC, Color.red);
		vecteurTrois.dessiner(g2d, matMC, Color.pink);
		vecteurQuatre.dessiner(g2d, matMC, Color.blue);
		}
		//System.err.println("vitesseRotationNegative est "+ vitesseRotationNegative);
		positionBallonGauche = new Vector2d(ballonGauche.getBallX()/pixelsParUniteX + diametreBallon/2,ballonGauche.getBallY()/pixelsParUniteY + diametreBallon/2);
		vitesseGauche = vitesseRotationPointP(angleRotation + (Math.PI)/2,positionBallonGauche,sens,vitesseRotationNegative).multiply(grandeurVitesseDomine*sens);
		//System.out.println("centre de la ballonGauche est "+ positionBallonGauche);
		//System.out.println("la vitesse de ballon Un est " + ballonUn.getVitesse());
		positionBallonDroit = new Vector2d(ballonDroit.getBallX()/pixelsParUniteX + diametreBallon/2,ballonDroit.getBallY()/pixelsParUniteY + diametreBallon/2);
		vitesseDroit = vitesseRotationPointP(angleRotation - (Math.PI)/2,positionBallonDroit,sens,vitesseRotationNegative).multiply(grandeurVitesseDomine*sens);
		//System.out.println("centre de la ballonDroit est "+ positionBallonDroit);
		//System.out.println("la vitesse de droit est " + vitesseRotationPointP(angleRotation - (Math.PI)/2,positionBallonDroit,sens,vitesseRotationNegative).multiply(grandeurVitesseDomine*sens));
		//System.out.println("la vitesse de ballon Un est " + ballonUn.getVitesse());
		//System.out.println("Impulsion" + scalaireImpulsion);
		//System.out.println("Scientifique " + scientifique);
		//collision aux ballons gauche et droits
		calculCollisionCentrale();
		//set rotation angle
		setBallonAngle();
		if(collision(coccinelle.getShape(),ballonUn.getShape()) || collision(coccinelle.getShape(),ballonDeux.getShape()) || collision(coccinelle.getShape(),ballonTrois.getShape()) || collision(coccinelle.getShape(),ballonQuatre.getShape()) || collision(coccinelle.getShape(),ballonGauche.getShape()) || collision(coccinelle.getShape(),ballonDroit.getShape())){
			collisionInitialiser();
		}	
		if(coccinelle.collision(fosse)){
			initialiser();
			for(CoccinelleListener ecout : OBJETS_ENREGISTRES.getListeners(CoccinelleListener.class) ) {
				ecout.niveauSwitch();
			}
		}
		/*if(collision(coccinelle.getShape(),murH)){
			System.out.println("Murrr haut!");
		}
		if(collision(coccinelle.getShape(),murB)){
			System.out.println("Murrr bas!");
		}
		if(collision(coccinelle.getShape(),murG)){
			System.out.println("Murrr gauche!");
		}
		if(collision(coccinelle.getShape(),murD)){
			System.out.println("Murrr droit!");
		}*/
		//ecouteur
		for(CoccinelleListener ecout : OBJETS_ENREGISTRES.getListeners(CoccinelleListener.class) ) {
			ecout.getCenter(centreSoleil);
			ecout.getRotation(((angleRotation%(2*Math.PI))*360)/(2*Math.PI),sens,Vector2d.normale(vitesseGauche));
			ecout.getBallonAInformation(positionUn, Vector2d.normale(ballonUn.getVitesse()),ClassePhysique.calculAngle(ballonUn.getVitesse()));
			ecout.getBallonBInformation(positionDeux, Vector2d.normale(ballonDeux.getVitesse()),ClassePhysique.calculAngle(ballonDeux.getVitesse()));
			ecout.getBallonCInformation(positionTrois, Vector2d.normale(ballonTrois.getVitesse()),ClassePhysique.calculAngle(ballonTrois.getVitesse()));
			ecout.getBallonDInformation(positionQuatre, Vector2d.normale(ballonQuatre.getVitesse()),ClassePhysique.calculAngle(ballonQuatre.getVitesse()));
			ecout.coeficientCollsiion(e[coeficientIndex]);
		}
		//System.out.println("cord X "+centreSoleil.getX()*pixelsParUniteX);
		//System.out.println("cord Y "+centreSoleil.getY()*pixelsParUniteY);
		//System.out.println("langle interne est: "+ ClassePhysique.calculAngle(ballonUn.getVitesse()));
	}
	/**
	 * Une méthode détecte collision entre les ballons
	 * @param collectionVitesse - une collection des deux vitesses de ballon A et de ballon B
	 * @param positionFinaleA - position de ballon A
	 * @param positionFinaleB - position de ballon B
	 * @param e - coeficient de collision
	 * @param masseA - masse de ballon A
	 * @param masseB - masse de ballon B
	 * @param vitesseA - vitesse de ballon A
	 * @param vitesseB - vitesse de ballon B
	 * @param chiffre - un chiffre qui symbolise le compteur et le boolean pour debuger la collision double 
	 * @return une collection des deux vecteurs qui représentent la vitesse de A et la vitesse de B
	 */
	private ArrayList<Vector2d> collisionBallon(ArrayList<Vector2d> collectionVitesse,Vector2d positionFinaleA,Vector2d positionFinaleB, double e,double masseA, double masseB, Vector2d vitesseA,Vector2d vitesseB, int chiffre){
		collectionVitesse.add(0,vitesseA);
		collectionVitesse.add(1,vitesseB);
		if(!booleanBallon[chiffre]){
			compteurBallon[chiffre] ++;
		}
		if(compteurBallon[chiffre] == delaisRecollision){
			booleanBallon[chiffre] = true;
			compteurBallon[chiffre] = 0;
		}
		if((Vector2d.normale(ClassePhysique.distanceCollision(positionFinaleA, positionFinaleB)) <= 10.25) && booleanBallon[chiffre] ){
			//System.out.println("collision ");
		Vector2d scalaire = ClassePhysique.normaleSurfaceObjB(positionFinaleA,positionFinaleB);
		double impulsion = ClassePhysique.getImpulsion(e, masseA, masseB, vitesseA.multiply(scalaireImpulsion), vitesseB.multiply(scalaireImpulsion), scalaire);
		vitesseA = ClassePhysique.vitesseFinaleA(vitesseA, impulsion, masseA, scalaire);
		vitesseB = ClassePhysique.vitesseFinaleB(vitesseB, impulsion, masseB, scalaire);
		collectionVitesse.add(0,vitesseA);
		collectionVitesse.add(1,vitesseB);
		booleanBallon[chiffre] = false;
		}
		return collectionVitesse;
	}
	/**
	 * Une méthode détecte collision entre les ballons et le ballon central
	 * @param positionFinaleA - position de ballon qui est en collision avec le ballon central
	 * @param e - coeficient de collision
	 * @param masseA - masse de ballon A
	 * @param vitesseA - vitesse de ballon A
	 * @return une vecteur qui représente la vitesse de A
	 */
	private Vector2d collisionBallonCentral(Vector2d positionFinaleA, double e, double masseA, Vector2d vitesseA, int chiffre){
		if(!booleanCentral[chiffre]){
			compteurCentrale[chiffre] ++;
		}
		if(compteurCentrale[chiffre] == delaisRecollision){
			booleanCentral[chiffre] = true;
			compteurCentrale[chiffre] = 0;
		}
		if(Vector2d.normale(ClassePhysique.distanceCollision(positionFinaleA, centreSoleil)) <= 15.25 & booleanCentral[chiffre]){
			//System.out.println("collision central");
			Vector2d scalaire = ClassePhysique.reciproque(ClassePhysique.normaleSurfaceObjB(positionFinaleA,centreSoleil));
			double impulsion = ClassePhysique.getImpulsion(e, masseA, masseInfini, vitesseA, defaut, scalaire);
			vitesseA = ClassePhysique.vitesseFinaleA(vitesseA, impulsion, masseA, scalaire).multiply(-1); // (-1) la vitesse négative pour la ballon rebondir
			booleanCentral[chiffre] = false;
			if(!collisionCentral){
				collisionCentral = true;
			}else{
				collisionCentral = false;
			}
		}
		return vitesseA;
	}
	/**
	 * Une méthode qui détecte la collision avec les murs horizontals
	 * @param position - la position de l'objet
	 * @param e - le matériel de l'objet
	 * @param vitesse - la vitesse de l'objet
	 * @return la nouvelle vitesse en vecteur pour l'objet
	 */
	private Vector2d collisionHorizontal(Vector2d position, Vector2d vitesse, int chiffre){
		if(!booleanMurHor[chiffre]){
			compteurMurHor[chiffre] ++;
		}
		if(compteurMurHor[chiffre] == delaisRecollision){
			booleanMurHor[chiffre] = true;
			compteurMurHor[chiffre] = 0;
		}
		Vector2d vitesseFinal = vitesse;
		if((position.getY() <= 5 || position.getY() >= 70 )&& booleanMurHor[chiffre]){
			//System.out.println("collision top");
			vitesseFinal = ClassePhysique.AngleReflexion(vitesse, normalMurHor);
			booleanMurHor[chiffre] = false;
		}
		return vitesseFinal;
	}
	/**
	 * Une méthode qui détecte la collision avec les murs verticals
	 * @param position - la position de l'objet
	 * @param e - le matériel de l'objet
	 * @param vitesse - la vitesse de l'objet
	 * @return la nouvelle vitesse en vecteur pour l'objet
	 */
	private Vector2d collisionVertical(Vector2d position, Vector2d vitesse, int chiffre){
		if(!booleanMurVer[chiffre]){
			compteurMurVer[chiffre] ++;
		}
		if(compteurMurVer[chiffre] == delaisRecollision){
			booleanMurVer[chiffre] = true;
			compteurMurVer[chiffre] = 0;
		}
		Vector2d vitesseFinal = vitesse;
		if((position.getX() <= 5 || position.getX() >= 95) && booleanMurVer[chiffre]){
			//System.out.println("collision cote");
			vitesseFinal = ClassePhysique.AngleReflexion(vitesse, normalMurVer);
			booleanMurVer[chiffre] = false;
		}
		return vitesseFinal;
	}
	/**
	 * Méthode pour calculer la collision des quatre ballons avec la ballon centrale, le cercle gauche et le cercle droit
	 */
	private void calculCollisionCentrale(){
		collisionBallon(collectionVitesse,positionFinaleQuatre,positionBallonGauche,e[coeficientIndex],masseBallonQuatre,masseInfini,vitesseFinalQuatre,vitesseGauche,6);
		vitesseFinalQuatre = collectionVitesse.get(0);
		collisionBallon(collectionVitesse,positionFinaleTrois,positionBallonGauche,e[coeficientIndex],masseBallonTrois,masseInfini,vitesseFinalTrois,vitesseGauche,7);
		vitesseFinalTrois = collectionVitesse.get(0);		
		collisionBallon(collectionVitesse,positionFinaleDeux,positionBallonGauche,e[coeficientIndex],masseBallonDeux,masseInfini,vitesseFinalDeux,vitesseGauche,8);
		vitesseFinalDeux = collectionVitesse.get(0);
		collisionBallon(collectionVitesse,positionFinaleUn,positionBallonGauche,e[coeficientIndex],masseBallonUn,masseInfini,vitesseFinalUn,vitesseGauche,9);
		vitesseFinalUn = collectionVitesse.get(0);
		
		collisionBallon(collectionVitesse,positionFinaleQuatre,positionBallonDroit,e[coeficientIndex],masseBallonQuatre,masseInfini,vitesseFinalQuatre,vitesseDroit,10);
		vitesseFinalQuatre = collectionVitesse.get(0);
		collisionBallon(collectionVitesse,positionFinaleTrois,positionBallonDroit,e[coeficientIndex],masseBallonTrois,masseInfini,vitesseFinalTrois,vitesseDroit,11);
		vitesseFinalTrois = collectionVitesse.get(0);		
		collisionBallon(collectionVitesse,positionFinaleDeux,positionBallonDroit,e[coeficientIndex],masseBallonDeux,masseInfini,vitesseFinalDeux,vitesseDroit,12);
		vitesseFinalDeux = collectionVitesse.get(0);
		collisionBallon(collectionVitesse,positionFinaleUn,positionBallonDroit,e[coeficientIndex],masseBallonUn,masseInfini,vitesseFinalUn,vitesseDroit,13);
		vitesseFinalUn = collectionVitesse.get(0);
	}
	/**
	 * calculer les collisons entre les ballons et les quatres murs
	 */
	private void calculCollision(){
		collisionBallon(collectionVitesse,positionFinaleUn,positionFinaleDeux,e[coeficientIndex],masseBallonUn,masseBallonDeux,vitesseFinalUn,vitesseFinalDeux,0);
		vitesseFinalUn = collectionVitesse.get(0);
		vitesseFinalDeux = collectionVitesse.get(1);
		collisionBallon(collectionVitesse, positionFinaleUn,positionFinaleTrois,e[coeficientIndex],masseBallonUn,masseBallonTrois,vitesseFinalUn,vitesseFinalTrois,1);
		vitesseFinalUn = collectionVitesse.get(0);
		vitesseFinalTrois = collectionVitesse.get(1);
		collisionBallon(collectionVitesse, positionFinaleUn,positionFinaleQuatre,e[coeficientIndex],masseBallonUn,masseBallonQuatre,vitesseFinalUn,vitesseFinalQuatre,2);
		vitesseFinalUn = collectionVitesse.get(0);
		vitesseFinalQuatre = collectionVitesse.get(1);
		collisionBallon(collectionVitesse, positionFinaleDeux,positionFinaleTrois,e[coeficientIndex],masseBallonDeux,masseBallonTrois,vitesseFinalDeux,vitesseFinalTrois,3);
		vitesseFinalDeux = collectionVitesse.get(0);
		vitesseFinalTrois = collectionVitesse.get(1);
		collisionBallon(collectionVitesse, positionFinaleDeux,positionFinaleQuatre,e[coeficientIndex],masseBallonDeux,masseBallonQuatre,vitesseFinalDeux,vitesseFinalQuatre,4);
		vitesseFinalDeux = collectionVitesse.get(0);
		vitesseFinalQuatre = collectionVitesse.get(1);
	    collisionBallon(collectionVitesse, positionFinaleTrois,positionFinaleQuatre,e[coeficientIndex],masseBallonTrois,masseBallonQuatre,vitesseFinalTrois,vitesseFinalQuatre,5);
	    vitesseFinalTrois = collectionVitesse.get(0);
	    vitesseFinalQuatre = collectionVitesse.get(1);
	    //collision central
	    vitesseFinalUn = collisionBallonCentral(positionFinaleUn, e[coeficientIndex],masseBallonUn,vitesseFinalUn,1);
	    vitesseFinalDeux = collisionBallonCentral(positionFinaleDeux, e[coeficientIndex],masseBallonDeux,vitesseFinalDeux,2);
	    vitesseFinalTrois = collisionBallonCentral(positionFinaleTrois, e[coeficientIndex],masseBallonTrois,vitesseFinalTrois,3);
	    vitesseFinalQuatre = collisionBallonCentral(positionFinaleQuatre, e[coeficientIndex],masseBallonQuatre,vitesseFinalQuatre,4);
	    //collision mur horizontal
		vitesseFinalUn = collisionHorizontal(positionFinaleUn,vitesseFinalUn,1);
	    vitesseFinalDeux = collisionHorizontal(positionFinaleDeux,vitesseFinalDeux,2);
	    vitesseFinalTrois = collisionHorizontal(positionFinaleTrois,vitesseFinalTrois,3);
	    vitesseFinalQuatre = collisionHorizontal(positionFinaleQuatre,vitesseFinalQuatre,4);
	    //collision mur vertical
	    vitesseFinalUn = collisionVertical(positionFinaleUn,vitesseFinalUn,1);
	    vitesseFinalDeux = collisionVertical(positionFinaleDeux,vitesseFinalDeux,2);
	    vitesseFinalTrois = collisionVertical(positionFinaleTrois,vitesseFinalTrois,3);
	    vitesseFinalQuatre = collisionVertical(positionFinaleQuatre,vitesseFinalQuatre,4);
	}
	/**
	 * calcule la vitesse de rotation d'un point par rapport au centre de  soleil
	 * @param angle - angle de rotation au centre
	 * @param position - le cordonné d'un point
	 * @param sens - (1 ou -1) pour indiquer le sens de l'angle
	 * @return un vecteur qui représente la vitesse de ce point
	 */
	private Vector2d vitesseRotationPointP(double angle, Vector2d position, double sens, boolean vitesseRotationNegative){
		double angleTotal = sens*angle;
		double w = 2*Math.PI/VitesseList[portion];
		double r = Vector2d.normale(position.substract(centreSoleil));
		double vX = sens*r*w*(Math.cos(angleTotal));
		double vY = sens*r*w*(Math.sin(angleTotal)); //(y est inverse sur le plan, alors pas de -1)
		Vector2d vitesse = new Vector2d(vX,vY);
		if(vitesseRotationNegative){
			vitesse = vitesse.multiply(-0.7);
		}
		return vitesse;
	}
	/**
	 * Méthode pour créer le cercle de soleil
	 */
	private void creerSoleil(){
		flashCercle = new Ellipse2D.Double(centreSoleil.getX() - 20,centreSoleil.getY() - 20,40,40);
	}
	/**
	 * Méthode pour créer le coccinelle
	 */
	private void creerBug() {
		coccinelle = new Coccinelle(75,20,0);
	}
	/**
	 * Méthode pour créer les quatres ballons
	 */
	private void creerBallon(){
		positionUn = new Vector2d(13,17);// set position initiale
		positionDeux = new Vector2d(17,65);//
		positionTrois = new Vector2d(83,15);//
		positionQuatre = new Vector2d(90,60);//
		positionFinaleUn = positionUn;
		positionFinaleDeux = positionDeux;
		positionFinaleTrois = positionTrois;
		positionFinaleQuatre = positionQuatre;
		vitesseUn =new Vector2d(0,48);// set vitesse initiale
		vitesseDeux =new Vector2d(0,-48);// set vitesse initiale
		vitesseTrois =new Vector2d(16,64);// set vitesse initiale
		vitesseQuatre =new Vector2d(32,-48);// set vitesse initiale
		vitesseFinalUn = vitesseUn;
		vitesseFinalDeux = vitesseDeux;
		vitesseFinalTrois = vitesseTrois;
		vitesseFinalQuatre = vitesseQuatre;
		ballonUn = new BallonChasseur(positionUn,diametreBallon,angleBUn,masseBallonUn,vitesseUn);
		ballonDeux = new BallonChasseur(positionDeux,diametreBallon,angleBDeux,masseBallonDeux,vitesseDeux);
		ballonTrois = new BallonChasseur(positionTrois,diametreBallon,angleBTrois,masseBallonTrois,vitesseTrois);
		ballonQuatre = new BallonChasseur(positionQuatre,diametreBallon,angleBQuatre,masseBallonQuatre,vitesseQuatre);
		//creer Ballon stable
		positionBallonGauche =new Vector2d(30,35);
		vitesseGauche = new Vector2d(0,0);
		ballonGauche = new BallonChasseur(positionBallonGauche,diametreBallon,1,99999,vitesseGauche);
		positionBallonDroit =new Vector2d(61,35);
		vitesseDroit = new Vector2d(0,0);
		ballonDroit = new BallonChasseur(positionBallonDroit,diametreBallon,1,99999,vitesseDroit);
		ballonCentral = new BallonChasseur(centreSoleil,20,1,99999,defaut);
	}
	/**
	 * Méthdoe pour calculer l'angle de rotation des quatre ballons
	 */
	private void setBallonAngle(){
		double vitesseAngulaire[] = {Math.toRadians(ClassePhysique.calculAngle(vitesseFinalUn)),Math.toRadians(ClassePhysique.calculAngle(vitesseFinalDeux)),Math.toRadians(ClassePhysique.calculAngle(vitesseFinalTrois)),Math.toRadians(ClassePhysique.calculAngle(vitesseFinalQuatre))};
		for(int k = 0 ; k < 3 ; k++ ){
			if(vitesseAngulaire[k] > Math.PI){
				vitesseAngulaire[k] = -1*vitesseAngulaire[k] + Math.PI;
			}
		}
		angleBUn = angleBUn + vitesseAngulaire[0]/controleVitesseRotation;
		angleBDeux = angleBDeux +  vitesseAngulaire[1]/controleVitesseRotation;
		angleBTrois = angleBTrois + vitesseAngulaire[2]/controleVitesseRotation;
		angleBQuatre = angleBQuatre + vitesseAngulaire[3]/controleVitesseRotation;
	}
	/**
	 * Méthode pour modifier l'apparence des quatre ballons pour adapter au mode scientifique
	 */
	private void setBallonVisible(){
	    ballonUn.setVisible(scientifique);
	    ballonDeux.setVisible(scientifique);
	    ballonTrois.setVisible(scientifique);
	    ballonQuatre.setVisible(scientifique);
	    ballonCentral.setVisible(scientifique);
	    ballonGauche.setVisible(scientifique);
	    ballonDroit.setVisible(scientifique);
	}
	/**
	 * Méthode pour créer les vecteurs de vitesse pour les quatre ballons
	 */
	private void creerVecteur(){
		vecteurUn = new VecteurGraphique(positionUn,positionFinaleUn);
		vecteurDeux = new VecteurGraphique(positionDeux,positionFinaleDeux);
		vecteurTrois = new VecteurGraphique(positionTrois,positionFinaleTrois);
		vecteurQuatre = new VecteurGraphique(positionQuatre,positionFinaleQuatre);
	}
	/**
	 * Méthode pour créer les quatre murs et leurs normals de surface
	 */
	private void creerMur(){
		murHaut = new Rectangle2D.Double(0,-10,largeurMonde,10);
		murBas = new Rectangle2D.Double(0,75,largeurMonde,10);
		murJardin = new Rectangle2D.Double(-10,0,10,75);
		murCour = new Rectangle2D.Double(largeurMonde,0,10,75);
		Vector2d pointJardinUn = new Vector2d(0,0);
		Vector2d pointJardinDeux = new Vector2d(0,75);
		normalMurVer = ClassePhysique.reciproque(ClassePhysique.normaleSurfaceObjB(pointJardinUn, pointJardinDeux));
		Vector2d pointHUn = new Vector2d(0,0);
		Vector2d pointHDeux = new Vector2d(largeurMonde,0);
		normalMurHor = ClassePhysique.reciproque(ClassePhysique.normaleSurfaceObjB(pointHUn, pointHDeux));
		//System.out.println("jardin!" +normalMurVer);
		//System.out.println("Horizon!" +normalMurHor);
	}
	//Pour calculer l'angle de rotation 
	/**
	 * Pour calculer l'angle de rotation 
	 * @param x1 - la coordonée x de la position initiale
	 * @param y1 - la coordonée y de la position initiale
	 * @param x2 - la coordonée x de la position finale
	 * @param y2 - la coordonée y de la position finale
	 * @return angle en radian
	 */
	private double calculAngle(double x1,double y1,double x2,double y2){
		double angle = 0;
		double adj = (x2 - x1);
		double oppo = (y2 - y1);
		//System.out.println("adj: " + adj);
		//System.out.println("oppo: " + oppo);
		angle = Math.atan(oppo/adj) + (Math.PI/2);
		if(adj < 0){
			angle = angle + Math.PI;
		}
		return angle;

	}
	/**
	 * Méthode pour modifier le diametre de coccinelle
	 * @param diametre - le diametre de coccinelle
	 */
	public void setDiametre(int diametre) {
		this.diametrePt = diametre;
		repaint();
	}
	/**
	 * Méthode pour initialiser l'arraylist de coordX et celui de coordY
	 * 
	 */
	public void effacer() {
		coordX.clear();
		coordY.clear();
		repaint();
	}
	@Override
	/**
	 * Méthode run dans la classe niveauDeux
	 */
	public void run() {
		while (animationEnCours) {
			compteur++;
			if(booleanBallon[6] && booleanBallon[7] && booleanBallon[8] && booleanBallon[9] && booleanBallon[10] && booleanBallon[11] && booleanBallon[12] && booleanBallon[13] || correctionSens){
				angleRotation = angleRotation + 2*Math.PI/VitesseList[portion];
				vitesseRotationNegative = false;
				if(!scientifique){
					ballonGauche.setColor(Color.LIGHT_GRAY);
					ballonDroit.setColor(Color.LIGHT_GRAY);
				}else{
					ballonGauche.setColor(yellow);
					ballonDroit.setColor(yellow);	
				}
				colorChange = false;
				drawLaser = false;
				compteurInverse = 0;
				if(correctionSens){
					compteurBonSens++;
				}
				if(compteurBonSens >= delaisCorrectionRotation){
					correctionSens = false;
					compteurBonSens = 0;
				}
			}else {
				compteurInverse ++;
				angleRotation = angleRotation - (2*Math.PI/VitesseList[portion])/2;
				vitesseRotationNegative = true;
				if(!scientifique){
					ballonGauche.setColor(Color.red);
					ballonDroit.setColor(Color.red);
				}else{
					ballonGauche.setColor(Color.blue);
					ballonDroit.setColor(Color.blue);	
				}
				colorChange = true;
				if(compteurInverse >= delaisCorrectionRotation){
					correctionSens = true;
					compteurInverse = 0;
				}
			}
			//le mouvement du ballon
			positionUn = ballonUn.deplacer(deltaTemps);
			//System.out.println("positionUn"+positionUn);
			positionDeux = ballonDeux.deplacer(deltaTemps);
			//System.out.println("positionDeux"+positionDeux);
			positionTrois = ballonTrois.deplacer(deltaTemps);
			//System.out.println("positionTrois"+positionTrois);
			positionQuatre = ballonQuatre.deplacer(deltaTemps);
			//System.out.println("positionQuatre"+positionQuatre);

			//setBallonAngle();
			//partie ou on set sa vitesse Initiale
			ballonUn.setVitesse(vitesseFinalUn);
			ballonDeux.setVitesse(vitesseFinalDeux);
			ballonTrois.setVitesse(vitesseFinalTrois);
			ballonQuatre.setVitesse(vitesseFinalQuatre);

			//partie ou on prévois sa prochaine déplacement
			positionFinaleUn = ballonUn.nextStep(deltaTemps);
			//ballonUnShadow.setPosition(positionFinaleUn);
			//System.err.println("NextStep ballon Un"+ positionFinaleUn);
			positionFinaleDeux = ballonDeux.nextStep(deltaTemps);
			//ballonDeuxShadow.setPosition(positionFinaleDeux);
			//System.err.println("NextStep ballon Deux"+ positionFinaleDeux);
			positionFinaleTrois = ballonTrois.nextStep(deltaTemps);
			//ballonTroisShadow.setPosition(positionFinaleTrois);
			//System.err.println("NextStep ballon Trois"+ positionFinaleTrois);
			positionFinaleQuatre = ballonQuatre.nextStep(deltaTemps);
			//ballonQuatreShadow.setPosition(positionFinaleQuatre);
			//System.err.println("NextStep ballon Quatre"+ positionFinaleQuatre);
			
			//collision entre les ballons
			calculCollision();
			//set rotation angle
		    setBallonAngle();
		    //System.err.println("vitesseBallonUn" + ballonUn.getVitesse());
		    //System.err.println("vitesseFinaleBallonUn" + vitesseFinalUn.toString());
		    /*if(rotate == false && compteur%30 == 10){
		    	rotate = true;
		    }*/
		    //vecteur
		    if(scientifique){
		    	vecteurUn.setPosition(positionUn);
		    	vecteurUn.setProchainePas(positionFinaleUn);
		    	vecteurDeux.setPosition(positionDeux);
		    	vecteurDeux.setProchainePas(positionFinaleDeux);
		    	vecteurTrois.setPosition(positionTrois);
		    	vecteurTrois.setProchainePas(positionFinaleTrois);
		    	vecteurQuatre.setPosition(positionQuatre);
		    	vecteurQuatre.setProchainePas(positionFinaleQuatre);
		    }
			if(compteur < coordX.size()){		
			//System.out.println(calculAngle(coordX.get(compteur-1),coordY.get(compteur-1),coordX.get(compteur),coordY.get(compteur)));
			coccinelle.setPas((coordX.get(compteur-1)-SIZE_BUG/2)/pixelsParUniteX,(coordY.get(compteur-1)-SIZE_BUG/2)/pixelsParUniteY);
			coccinelle.setOrientation(calculAngle(coordX.get(compteur-1),coordY.get(compteur-1),coordX.get(compteur),coordY.get(compteur)));
			}
			//ecouteur
			for(CoccinelleListener ecout : OBJETS_ENREGISTRES.getListeners(CoccinelleListener.class) ) {
				ecout.getCenter(centreSoleil);
				ecout.getRotation(((angleRotation%(2*Math.PI))*360)/(2*Math.PI),sens,Vector2d.normale(vitesseGauche));
				ecout.getBallonAInformation(positionUn, Vector2d.normale(ballonUn.getVitesse()),ClassePhysique.calculAngle(ballonUn.getVitesse()));
				ecout.getBallonBInformation(positionDeux, Vector2d.normale(ballonDeux.getVitesse()),ClassePhysique.calculAngle(ballonDeux.getVitesse()));
				ecout.getBallonCInformation(positionTrois, Vector2d.normale(ballonTrois.getVitesse()),ClassePhysique.calculAngle(ballonTrois.getVitesse()));
				ecout.getBallonDInformation(positionQuatre, Vector2d.normale(ballonQuatre.getVitesse()),ClassePhysique.calculAngle(ballonQuatre.getVitesse()));
				ecout.coeficientCollsiion(e[coeficientIndex]);
			}
			//System.out.println("animation en cours");
			repaint();
			if(compteur == coordX.size()){
				arreter();	
				//System.out.println("arreter!");
			}
			try {
				Thread.sleep((long)(deltaTemps*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}
	/**
	 * Méthode sert à commencer l'animation
	 */
	public void demarrer() {
		if (!animationEnCours) {
			Thread t = new Thread(this);
			t.start();
			animationEnCours = true;

		}
	}
	/**
	 * Méthode sert à arreter l'animation
	 */
	
	public void arreter() {
		animationEnCours = false;
		
	}
	/**
	 * Méthode sert à replacer coccinelle apres collision
	 */
	public void collisionInitialiser(){
		animation = false;
		animationEnCours = false;
		compteur = 0;
		firstTime = true;
		permetStock = false;
		coordX.clear();
		coordY.clear();
		coccinelle.setPas(45.5,35);
		repaint();
	}
	/**
	 * Méthode sert à initialiser les ressorts
	 */
	public void initialiser(){
		setBackground(Color.BLACK);
		creerBallon();
		creerVecteur();
		collisionCentral = false;
		angleRotation = 0;
		sens = -1;
		setBallonVisible();
		collisionInitialiser();
		coccinelle.setPas(75,20);
	}
	/**
	 * Méthode pour détercter une collision entre deux Shape
	 * @param forme1 - Shape un
	 * @param forme2 - Shape deux
	 * @return <b>true</b> il y a uen collision
	 * <p><b>false</b> il n'y a pas de collision
	 */
	private boolean inclut(Shape forme1, Shape forme2){
		boolean inclut = false;
		Area a1 = new Area(forme1);
		Area a2 = new Area(forme2);
		Area vide = new Area(a2);
		vide.subtract(a1);
		if(vide.isEmpty()){
			inclut = true;
		}
		return inclut;
	}
	/**
	 * Méthode sert à trouver la matrice monde réel
	 * @param largeurMonde - l'unité monde réel
	 */
	private  void calculerMatriceMondeVersComposant( double largeurMonde ) {
		matMC = new AffineTransform();
		pixelsParUniteX =  getWidth() / largeurMonde;
		double ratio = getHeight()/(double)getWidth();
        double hauteurMondeSansDistorsion = largeurMonde*ratio;
		pixelsParUniteY = getHeight() / hauteurMondeSansDistorsion ;
		matMC.scale( pixelsParUniteX, pixelsParUniteY ); 
	}
	/**
	 * Méthode sert à modifier le sens de rotation
	 * @param sens - le sens de rotation
	 */
	public void setSens(int sens){
		this.sens = sens;
		repaint();
	}
	/**
	 * Méthode sert à modifier la vitesse de rotation
	 * @param a - la vitesse de rotation
	 */
	public  void setRotationVitesse(int a){
		this.portion = a;
		repaint();
	}
	/**
	 * Méthode sert à modifier la grandeur d'impulsion pendant la collision
	 * @param b - grandeur de collision
	 */
	public void setImpulsion(double b){
		this.scalaireImpulsion = b;
		repaint();
	}
	/**
	 * Méthode sert à modifier le coefficient de frottement
	 * @param a le coefficient de frottement
	 */
	public void setCoefficient(int a){
		coeficientIndex = a;
		repaint();
	}
	/**
	 * Méthode sert à modifier le temps d'animation
	 * @param temps - le temps d'animation
	 */
	public void setSlowMotion(double temps){
		deltaTemps = temps;
		repaint();
	}
	/**
	 * Méthode sert à modifier la masse des quatre ballons
	 * @param a - l'indice des ballons
	 * @param masse - la masse des ballons
	 */
	public void setMasseBallon(int a,double masse){
		switch(a){
		case 1:
			masseBallonUn = masse;
			break;
		case 2:
			masseBallonDeux = masse;
			break;
		case 3:
			masseBallonTrois = masse;
			break;
		case 4:
			masseBallonQuatre = masse;
			break;
		}
		repaint();
		
	}
	/**
	 * Méthode sert à activer le mode scientifique
	 * @param b - <b>true</b> activer le mode scientifique
	 * <p><b>false</b> désactiver le mode scientifique
	 */
	public void setScientifique(boolean b){
		this.scientifique = b;
		setBallonVisible();
		repaint();
	}
	/**
	 * Cette méthode permet de vérrifier si deux formes géométriques entrent en collision.
	 * @param forme1 la première forme géométrique 
	 * @param forme2 la première forme géométrique 
	 * @return la valeur de vérité de cette méthode: <p><b>true</b> les deux objets entrent en collision.
	 * <p><b>false</b> les deux objets n'entrent pas en collisio.
	 */
	private boolean collision(Shape forme1, Shape forme2) {
		Area a1 = new Area(forme1);
		Area a2 = new Area(forme2); 
		Area inters = new Area(a1);
		inters.intersect(a2);
		if (!inters.isEmpty()) {
			return(true);
		} else {
			return(false);
		}
	}
	/**
	 * Méthode pour loader les imgaes dans le resource folder
	 */
	private void lireLesTextures() {
		//lecture des images qui servent de texture
		URL url = getClass().getClassLoader().getResource("leavesBlackOut.PNG");
		URL url2 = getClass().getClassLoader().getResource("leavesYellow2.PNG");
		URL url3 = getClass().getClassLoader().getResource("leavesYellow.PNG");
		try {
			img3 = ImageIO.read(url);
			img2 = ImageIO.read(url2);
			img = ImageIO.read(url3);
		} catch (Exception e) {
			//System.out.println("Erreur de lecture du fichier de texture");
		}
		
	}
	/**
	 * Cette méthode permet de déclarer l'écouteur 
	 * @param objEcouteur - l'écouteur CoccinelleListener
	 */
	public void addCoccinelleListener(CoccinelleListener objEcouteur) {
		OBJETS_ENREGISTRES.add(CoccinelleListener.class, objEcouteur);
	}
}
