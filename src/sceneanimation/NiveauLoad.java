package sceneanimation;

import ecouteur.TransmissionInfoNiveauLoadListener;
import geometrie.BallonChasseur;
import geometrie.Boussole;
import geometrie.ChampMagnetique;
import geometrie.Coccinelle;
import geometrie.Particules;
import geometrie.Plaque;
import geometrie.Ressort;
import geometrie.VecteurGraphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import physique.ClassePhysique;
import physique.Vector2d;
import physique.Vector3d;
/**
 * Classe de NiveauLoad du jeu de coccinelle, le niveau pour lire la création du niveau de l'utilisateur
 * @author Zizheng Wang contribution de Kevin Takla(plaque et champs magnétique)
 * @version 3.0
 */
public class NiveauLoad extends JPanel implements Runnable {
	private static final long serialVersionUID = -218906068015857583L;
	private double diametrePt = 6;
	private double deltaT = 0.016;
	private double deltaTempsBallon = 0.03;
	private ArrayList<Integer> coordX;
	private ArrayList<Integer> coordY;
	private Coccinelle coccinelle;
	private boolean animation = false;
	private boolean animationEnCours = false;
	private long sleepTime = 60;
	private int compteur = 0;
	private final double SIZE_BUG = 3;
	private boolean firstTime = true;
	private boolean permetStock = false;
	private Ellipse2D.Double fosse;
	private final double SIZE_FOSSE = 3;
	private double largeurMonde = 100;
	private AffineTransform matMC;
	private boolean premierFois = true;
	private double pixelsParUniteX;
	private double pixelsParUniteY;
	private ArrayList<Particules> particuleSet;
	private ArrayList<Vector2d> particuleSetBuffer;
	private ArrayList<Ressort> ressortSet;
	private ArrayList<BallonChasseur> ballonSet;
	private ArrayList<Vector2d> ballonSetPosition;
	private ArrayList<Vector2d> ballonSetPositionFinale;
	private ArrayList<Vector2d> ballonSetVitesseFinale;
	private ArrayList<BallonChasseur> cercleSet;
	private ArrayList<Shape> cocciSet;
	private ArrayList<Plaque> plaqueSet;
	private ArrayList<Vector2d> plaquePositionSet;
	private ArrayList<ChampMagnetique> champsSet;
	private ArrayList<Vector2d> champsPositionSet;
	private boolean animer;
	private int indEtatAnim = 2;
	private Color color = Color.GRAY;
	private double coccPasInitialX = 75;
	private double coccPasInitialY = 20;
	private double fosseX = 40;
	private double fosseY = 65;
	private String stringImage;
	private BufferedImage img;
	private boolean afficherBoussole = false;
	private Rectangle2D.Double murHaut,murBas,murJardin,murCour;
	private boolean booleanMurHor[] = new boolean [20];
	private int compteurMurHor[] = new int[20];
	private boolean booleanMurVer[] = new boolean [20];
	private int compteurMurVer[] = new int[20];
	private boolean booleanBallon[][] = new boolean [20][20];
	private int compteurBallon[][] = new int[20][20];
	private double vitesseAngulaireBallon[] = new double[20];
	private double delaisRecollision = 5;  // un delais du temps pour controler les méthodes de collision ne répeter pas à deux coup de suit
	private double scalaireImpulsion = 1.5; //une grandeur de scalaire raisonnable pour les billes rebondissent
	private Vector2d normalMurVer, normalMurHor;
	private double controleVitesseRotation = 64;
	private ArrayList<Vector2d> collectionVitesse= new ArrayList<Vector2d>();
	private double e[] = {1/2, 5/9, 8/9, 15/16, 19/20}; // la coeficient de restitution pour la collision inélastique
	private int coeficientIndex = 3;
	private double masseBallon = 5;
	private Vector2d posUn;
	private Vector2d posDeux;
	private Vector2d posTrois;
	private boolean particuleSelectionnee = false;
	private double posXBalle = 0;
	private double posYBalle = 0;
	private int xPrecedent = 0;
	private int yPrecedent = 0;
	private Vector2d nouvellePosition = new Vector2d();
	private int selectionIndexCharge = 0;
	private double pas = 25;
	private double pasHor = 10;
	private String cercleInfo = "\n ";
	private String ballonInfo = "\n ";
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList(); //collection 
	private boolean scientifique = false;
	private Vector2d matrice = new Vector2d();
	private int echelle;
	/**
	 * Constructeur du niveauLoad du jeu coccinelle
	 */
	public NiveauLoad() {
		setPreferredSize(new Dimension(800, 600));
		creerMur();
		coordX = new ArrayList<Integer>();
		coordY = new ArrayList<Integer>();
		particuleSet = new ArrayList<Particules>();
		particuleSetBuffer = new ArrayList<Vector2d>();
		ressortSet = new ArrayList<Ressort>();
		ballonSet = new ArrayList<BallonChasseur>();
		ballonSetPosition = new ArrayList<Vector2d>();
		ballonSetPositionFinale = new ArrayList<Vector2d>();
		ballonSetVitesseFinale = new ArrayList<Vector2d>();
		cercleSet = new ArrayList<BallonChasseur>();
		cocciSet = new ArrayList<Shape>();
		plaqueSet = new ArrayList<Plaque>();
		plaquePositionSet = new ArrayList<Vector2d>();
		champsPositionSet = new ArrayList<Vector2d>();
		champsSet = new ArrayList<ChampMagnetique>();
		creerBug();
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
				if (particuleSelectionnee) {
					posXBalle += arg0.getX() - xPrecedent; 
					posYBalle += arg0.getY() - yPrecedent;
					nouvellePosition = new Vector2d(posXBalle/pixelsParUniteX, posYBalle/pixelsParUniteY);
					particuleSet.get(selectionIndexCharge).setPosition(nouvellePosition);
					xPrecedent = arg0.getX() ;
					yPrecedent = arg0.getY() ;
					repaint();
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
			@Override
			public void mousePressed(MouseEvent e) {
				//if(firstTime){
				if(Math.abs(e.getX()/pixelsParUniteX + matrice.getX() - (coccinelle.getPosX()+SIZE_BUG/2))<= 4 && Math.abs(e.getY()/pixelsParUniteY + matrice.getY()  - (coccinelle.getPosY()+SIZE_BUG/2))<= 4){
					animation = false;
					compteur = 0;
					permetStock = false;
					animationEnCours = false;
					firstTime = false;
					permetStock = true;
					for(TransmissionInfoNiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveauLoadListener.class) ) {
						ecout.action();
					}
				}else{
					//System.out.println("faut commencer près de coccinelle!!!");
					//System.out.println("x "+e.getX()/pixelsParUniteX+" , y "+e.getY()/pixelsParUniteY);
				}
				if(particuleSet.size() > 0 ){
					for(int k = 0; k < particuleSet.size(); k++){
						if(particuleSet.get(k).getShape().contains(e.getX(), e.getY()) ){
							selectionIndexCharge = k;
							posXBalle = particuleSet.get(k).getShape().getBounds2D().getX() + particuleSet.get(k).getShape().getBounds2D().getWidth()/2 + pas*pixelsParUniteX;
							posYBalle = particuleSet.get(k).getShape().getBounds2D().getY() + particuleSet.get(k).getShape().getBounds2D().getHeight()/2 + pasHor*pixelsParUniteY;
							particuleSelectionnee = true;
							xPrecedent = e.getX();
							yPrecedent = e.getY();

						}
					}
				}

			}
			@Override
			public void mouseReleased(MouseEvent e) {
				particuleSelectionnee = false;
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
	 * Méthode sert à dessiner les composants graphiques dans le niveau load du jeu coccinelle
	 */
	public void paintComponent(Graphics g) {
		Ellipse2D.Double cercle;
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(!stringImage.equals("IconUn.jpg") && !afficherBoussole && !scientifique){
			g2d.drawImage(img,0,0,null);
		}else{
			if(scientifique){
				echelle = (int)(5*(this.getWidth()/largeurMonde));
				g2d.setColor(Color.RED);
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
				if(!afficherBoussole){
					setVecteur(g2d,matMC);
				}
			}
		}
		setBackground(color);
		//
		g2d.setColor(Color.GRAY);
		if(cocciSet.size() >= 2){
			fosseX = cocciSet.get(1).getBounds2D().getX()/pixelsParUniteX;
			fosseY = cocciSet.get(1).getBounds2D().getY()/pixelsParUniteY;

		}
		fosse = new Ellipse2D.Double(fosseX,fosseY,SIZE_FOSSE,SIZE_FOSSE);
		g2d.fill(matMC.createTransformedShape(fosse));
		//System.out.println("cocc x  " + coccSet.get(0).getBounds2D().getX()/pixelsParUniteX+ " , cocc Y " + coccSet.get(0).getBounds2D().getY()/pixelsParUniteY );
		// System.out.println("Fosse x  " + coccSet.get(1).getBounds2D().getX()/pixelsParUniteX+ " , fosse Y " + coccSet.get(1).getBounds2D().getY()/pixelsParUniteY );
		for(int k = 0; k < particuleSet.size(); k++) {
			String c = null;
			if(particuleSet.get(k).getCharge() == 0){
				c = "neutre";
			}else if(particuleSet.get(k).getCharge() < 0){
				c = "négative";
			}else if(particuleSet.get(k).getCharge() > 0){
				c = "positive";
			}
			particuleSet.get(k).dessiner(g2d, matMC,c);
		}
		if(afficherBoussole){
			setBoussole(g2d, matMC);
		}
		for(int k = 0; k < ressortSet.size(); k++) {
			ressortSet.get(k).dessiner(g2d, matMC);
		}
		for(int k = 0; k < ballonSet.size(); k++) {
			ballonSet.get(k).dessiner(g2d, matMC,vitesseAngulaireBallon[k],"numéro " + k);
		}
		for(int k = 0; k < cercleSet.size(); k++) {
			cercleSet.get(k).dessiner(g2d, matMC,cercleSet.get(k).getAngleRotation(),100/2 + matrice.getX(),75/2 + matrice.getY());
		}
		g2d.setColor(Color.green);	
		for (int k = compteur; k < coordX.size(); k++) {
			cercle = new Ellipse2D.Double( coordX.get(k)-diametrePt/2, coordY.get(k)-diametrePt/2, diametrePt, diametrePt);
			g2d.fill(cercle);
		}

		for(int nbPlaque =0; nbPlaque< plaqueSet.size(); nbPlaque++){
			plaqueSet.get(nbPlaque).dessiner(g2d, matMC);
		}

		for(int nbChamps =0; nbChamps< champsSet.size(); nbChamps++){
			champsSet.get(nbChamps).dessiner(g2d, matMC);
		}
		g2d.setColor(Color.DARK_GRAY);
		/*if(coccSet.size() >= 1){
			coccinelle.setPas(coccSet.get(0).getBounds2D().getX()/pixelsParUniteX, coccSet.get(0).getBounds2D().getY()/pixelsParUniteY);
		}*/
		coccinelle.dessiner(g2d, matMC, false);

		if(coccinelle.collision(fosse)){
			setBackground(Color.cyan);
			//System.out.println("Done!");
		}
		cercleInfo = "\n ";
		if(cercleSet.size() > 0 ){
			for(int i = 0; i < cercleSet.size(); i++){
				String cercleNom = " Cercle " + i + "\n ";
				String vitesseAngulaire = " vitesseAngulaire: " + round(((cercleSet.get(i).getAngleRotation()%(Math.PI))*360)/Math.PI,2) + " rad par second \n ";
				String posCercle = " position:  [ " + round((cercleSet.get(i).getShape().getBounds2D().getX()-matrice.getX())/pixelsParUniteX,2) + " m , " + round((cercleSet.get(i).getShape().getBounds2D().getY()-matrice.getY())/pixelsParUniteY,2) + " m ] \n ";
				String centreRotation = " centreRotation: [ 50,0 m , 37,5 m ] \n " + " \n " + " \n "+ " \n ";
				cercleInfo = cercleInfo + cercleNom + vitesseAngulaire + posCercle + centreRotation ;
			}
			for(TransmissionInfoNiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveauLoadListener.class) ) {
				ecout.getCercleMessage(cercleInfo);
			}
		}
		ballonInfo = "\n ";
		if(ballonSet.size() > 0 ){
			for(int i = 0; i < ballonSet.size(); i++){
				String ballonNom = " Ballon " + i + "\n ";
				String vitesse = " vitesse: [ " + round(ballonSet.get(i).getVitesse().getX(),2) + " m/s , " + round(ballonSet.get(i).getVitesse().getY(),2) + " m/s ] \n ";
				String posBallon = " position:  [ " + round((ballonSet.get(i).getShape().getBounds2D().getX()-matrice.getX())/pixelsParUniteX,2) + " m , " + round((ballonSet.get(i).getShape().getBounds2D().getY()-matrice.getY())/pixelsParUniteY,2) + " m ] \n ";
				String orientation = " orientation: " + round(ballonSet.get(i).getOrientation(),1) + "° \n " + " \n "+ " \n ";
				ballonInfo = ballonInfo + ballonNom + vitesse + posBallon + orientation ;

			}
			for(TransmissionInfoNiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveauLoadListener.class) ) {
				ecout.getBallonMessage(ballonInfo);
			}
			//collision ballon
			for(int i = 0; i < ballonSet.size(); i++){
				ballonSetPosition.remove(i);
				ballonSetPosition.add(i, ballonSet.get(i).deplacer(deltaTempsBallon));
				ballonSet.get(i).setVitesse(ballonSetVitesseFinale.get(i));
				ballonSetPositionFinale.remove(i);
				ballonSetPositionFinale.add(i, ballonSet.get(i).nextStep(deltaTempsBallon));
				//calculCollision();
				//collision mur horizontal
				ballonSetVitesseFinale.add(i,collisionVertica(ballonSetPositionFinale.get(i),ballonSetVitesseFinale.remove(i),i));
				//collision mur vertical
				ballonSetVitesseFinale.add(i,collisionHorizon(ballonSetPositionFinale.get(i),ballonSetVitesseFinale.remove(i),i));
				//setBallonAngle(i, ballonSetVitesseFinale.get(i));
				calculCollision(e[3]);
			}
			
		}

	}
	/**
	 * Méthéode sert à setter les valeurs initiales des ressorts dans le niveau load
	 */
	private void creerRessort() {
		if(ressortSet.size() >= 1){
			ressortSet.get(0).setMasse(5);
			ressortSet.get(0).setVitesse(300);
			ressortSet.get(0).setConstantRessort(10);
			ressortSet.get(0).setCoFrottement(0.1);
			ressortSet.get(0).setDeplacement(30);
			if(ressortSet.size() >= 2){
				ressortSet.get(1).setMasse(10);
				ressortSet.get(1).setVitesse(350);
				ressortSet.get(1).setConstantRessort(10);
				ressortSet.get(1).setCoFrottement(0.1);
				ressortSet.get(1).setDeplacement(20);
			}
			if(ressortSet.size() >= 3){
				ressortSet.get(2).setMasse(5);
				ressortSet.get(2).setVitesse(350);
				ressortSet.get(2).setConstantRessort(10);
				ressortSet.get(2).setCoFrottement(0.1);
				ressortSet.get(2).setDeplacement(20);
			}
		}

	}
	/**
	 * Méthéode sert à setter l'angle angulaire des cercles dans le niveau load
	 */
	private void genererCercleRotation(){
		if(cercleSet.size() > 0 ){
			for(int k = 0 ; k < cercleSet.size() ; k++){
				cercleSet.get(k).setAngleRotation(cercleSet.get(k).getAngleRotation() + cercleSet.get(k).getStockAngle());
			}
		}
	}
	/**
	 * Méthode sert à setter les coordonnées de coccinelle
	 */
	private void creerBug() {
		coccinelle = new Coccinelle(coccPasInitialX,coccPasInitialY,0); //50,20
	}
	/**
	 * Méthode sert à dessiner les objets boussoles
	 * @param g2d - Graphics2D par défaut
	 * @param aff - la matrice monde réel
	 */
	private void setBoussole(Graphics2D g2d, AffineTransform aff){
		for(int a = (int)(5 + matrice.getY()); a < (int)(75 + matrice.getY()); a = a + 5){
			for(int k = (int)(5 + matrice.getX()); k < (int)(100 + matrice.getX()); k = k + 5){
				Double x = 0.0;
				Double y = 0.0;
				Vector2d position = new Vector2d(k,a);
				if(particuleSet.size() > 0){
					for(int i = 0 ; i < particuleSet.size() ; i ++) {
						Vector2d orientation = ClassePhysique.orientationChampElectrique(particuleSet.get(i).getPosition(),position);
						double distance = position.substract(particuleSet.get(i).getPosition()).modulus();
						Vector2d champ = ClassePhysique.champElectrique(particuleSet.get(i).getCharge(),distance,orientation);
						Vector2d orientationCocc = ClassePhysique.orientationChampElectrique(coccinelle.getPosition(),position);
						double distanceDeux = position.substract(coccinelle.getPosition()).modulus();
						Vector2d champCocc = ClassePhysique.champElectrique(coccinelle.getCharge(),distanceDeux,orientationCocc);
						x = x + champ.getX() + champCocc.getX(); 
						y = y + champ.getY() + champCocc.getY();
					}
				}
				Vector2d champTotal = new Vector2d(x,y);
				Boussole boussole = new Boussole(position.getX(),position.getY());
				Vector2d horizon = new Vector2d(1,0);
				double angle = ClassePhysique.calculAngle(champTotal,horizon);
				//la correction de l'orientation
				if(champTotal.getY() < 0){
					angle = -1*angle;
				}
				boussole.dessiner(g2d, matMC, angle);
			}
		}
	}
	/**
	 * Méthode sert à dessiner les objets vecteur
	 * @param g2d - Graphics2D par défaut
	 * @param aff - la matrice monde réel
	 */
	private void setVecteur(Graphics2D g2d, AffineTransform aff){
		for(int a = (int)(5 + matrice.getY()); a < (int)(75 + matrice.getY()); a = a + 5){
			for(int k = (int)(5 + matrice.getX()); k < (int)(100 + matrice.getX()); k = k + 5){
				Double x = 0.0;
				Double y = 0.0;
				Vector2d position = new Vector2d(k,a);
				if(particuleSet.size() > 0){
					for(int i = 0 ; i < particuleSet.size() ; i ++) {
						Vector2d orientation = ClassePhysique.orientationChampElectrique(particuleSet.get(i).getPosition(),position);
						double distance = position.substract(particuleSet.get(i).getPosition()).modulus();
						Vector2d champ = ClassePhysique.champElectrique(particuleSet.get(i).getCharge()*Math.pow(10, 11),distance,orientation);
						Vector2d orientationCocc = ClassePhysique.orientationChampElectrique(coccinelle.getPosition(),position);
						double distanceDeux = position.substract(coccinelle.getPosition()).modulus();
						Vector2d champCocc = ClassePhysique.champElectrique(coccinelle.getCharge()*Math.pow(10, 11),distanceDeux,orientationCocc);
						x = x + champ.getX() + champCocc.getX(); 
						y = y + champ.getY() + champCocc.getY();
					}
				}
				Vector2d champTotal = new Vector2d(x,y);
				if(champTotal.modulus() > 4){
					champTotal = champTotal.normalize().multiply(4);
				}
				VecteurGraphique ori = new VecteurGraphique(position,champTotal,1);
				ori.dessinerChamp(g2d, matMC, Color.RED);
			}
		}
	}
	/**
	 * Méthode sert à calculer le dépacement des particules
	 */
	private void deplacerCharge(){
		for(int nbPart = 0; nbPart < particuleSet.size(); nbPart++ ){
			double forceTotalX =0 , forceTotalY =0;
			Particules partUtiliser = particuleSet.get(nbPart);
			boolean collisonPlaque = false;
			for(int i = 0; i < particuleSet.size() + 1; i++ ){
				if(i != nbPart){
					Vector2d forcePart;
					if(i == particuleSet.size()){
						forcePart = ClassePhysique.calculerForceElectrique(partUtiliser.getCharge(),coccinelle.getCharge(),calculerDistanceDesDeuxCharges(particuleSet.get(nbPart).getPosition(), coccinelle.getPosition()));
					}else{
						Particules autrePart = particuleSet.get(i);
						forcePart = ClassePhysique.calculerForceElectrique(partUtiliser.getCharge(),autrePart.getCharge(),calculerDistanceDesDeuxCharges(particuleSet.get(nbPart).getPosition(), particuleSet.get(i).getPosition()));
					}
					forceTotalX += forcePart.getX();
					forceTotalY += forcePart.getY();
				}

			}
			for(int p=0; p < plaqueSet.size(); p++){
				double largeurPlaque = (plaqueSet.get(p).getPosition().getX()) + (plaqueSet.get(p).getLongueur());
				if(partUtiliser.getX() >= plaqueSet.get(p).getPosition().getX() && partUtiliser.getX() <= largeurPlaque){
					Shape plaqueENCours = plaqueSet.get(p).getShapeNonTransfo();
					Shape shapePart = partUtiliser.getShape();
					if(inclut(matMC.createTransformedShape(plaqueENCours),shapePart)){
						//System.err.println("COLLISION!!!we$~$!@$~!@$!@%$!");
					
						partUtiliser.stabiliser();
						partUtiliser.posAvant();
						collisonPlaque = true;
					}else{
						double forceY = -partUtiliser.getCharge()*plaqueSet.get(p).getChampsElectrique();
						if(distanceParticuleParticule(partUtiliser.getPosition() , plaqueSet.get(p).getPosition()) > 0){
							forceY =  -forceY;
						}
						forceTotalY += forceY;
					}
				}
			}
			for(int ch=0; ch< champsSet.size(); ch++){
				if(inclut(matMC.createTransformedShape(champsSet.get(ch).getShape()),partUtiliser.getShape())){
					Vector3d forceMagne = ClassePhysique.trouverForceMagnetique(partUtiliser.getCharge(), partUtiliser.getVitesseActuelle(), champsSet.get(ch).getChampsMagnetique());
					forceTotalX += forceMagne.getX();
					forceTotalY += forceMagne.getY();
				}
			}
			Vector2d sommeForce;
			if(!collisonPlaque){
				sommeForce = new Vector2d(forceTotalX,forceTotalY);
			}else{
				sommeForce = new Vector2d();
			}
			Vector2d accelerationPart = ClassePhysique.calculerAcceleration(sommeForce, partUtiliser.getMasse());
			partUtiliser.avancerParticule(accelerationPart, deltaT);
			particuleSet.set(nbPart, partUtiliser);
		}
	}
	/**
	 * Cette méthode permet de calculer l'orientation de la coccinelle
	 * @param x1 la position précédente de la cocinelle en X
	 * @param y1 la position précédente de la cocinelle en y
	 * @param x2 la position actuelle de la cocinelle en X
	 * @param y2 la position actuelle de la cocinelle en Y
	 * @return l'orientation de la coccinelle
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
	 * Méthode sert à modifier le diamètre du cercle
	 * @param diametre le diamètre du cercle
	 */
	public void setDiametre(int diametre) {
		this.diametrePt = diametre;
		repaint();
	}
	/**
	 * Méthode sert à initialiser les coordonnées de coccinelle
	 */
	public void effacer() {
		coordX.clear();
		coordY.clear();
		repaint();
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
	/**
	 * Méthode sert à calculer le collision entre les ballons
	 * @param e - l'indice de frottement
	 */
	private void calculCollision(double e) {
		for(int i = 0; i < ballonSet.size(); i++){
			for(int g = 0; g < ballonSet.size(); g++){
				if(g != i){
					if(!booleanBallon[i][g]){
						compteurBallon[i][g] ++;
					}
					if(compteurBallon[i][g] == delaisRecollision){
						booleanBallon[i][g] = true;
						compteurBallon[i][g] = 0;
					}
					if((Vector2d.normale(ClassePhysique.distanceCollision(ballonSet.get(i).getPosition(), ballonSet.get(g).getPosition())) <= 10.25) && booleanBallon[i][g]){
						Vector2d vitesseFinalA = ballonSetVitesseFinale.get(i);
						Vector2d vitesseFinalB = ballonSetVitesseFinale.get(g);
						//System.out.println("collision ");
						Vector2d scalaire = ClassePhysique.normaleSurfaceObjB(ballonSet.get(i).getPosition(),ballonSet.get(g).getPosition());
						double impulsion = ClassePhysique.getImpulsion(e, 5, 5, vitesseFinalA.multiply(scalaireImpulsion), vitesseFinalB.multiply(scalaireImpulsion), scalaire);
						Vector2d vitesseA = ClassePhysique.vitesseFinaleA(vitesseFinalA, impulsion, 5, scalaire);
						Vector2d vitesseB = ClassePhysique.vitesseFinaleB(vitesseFinalB, impulsion, 5, scalaire);
						ballonSet.get(i).setVitesse(vitesseA);
						ballonSet.get(g).setVitesse(vitesseB);
						ballonSetVitesseFinale.remove(i);
						ballonSetVitesseFinale.add(i,vitesseA);
						ballonSetVitesseFinale.remove(g);
						ballonSetVitesseFinale.add(g,vitesseB);
						booleanBallon[i][g] = false;
					}
				}
			}
		}
	}
	/**
	 * Une méthode qui détecte la collision avec les murs horizontals
	 * @param position - la position de l'objet
	 * @param e - le matériel de l'objet
	 * @param vitesse - la vitesse de l'objet
	 * @return la nouvelle vitesse en vecteur pour l'objet
	 */
	private Vector2d collisionVertica(Vector2d position, Vector2d vitesse, int chiffre){
		if(!booleanMurHor[chiffre]){
			compteurMurHor[chiffre] ++;
		}
		if(compteurMurHor[chiffre] == delaisRecollision){
			booleanMurHor[chiffre] = true;
			compteurMurHor[chiffre] = 0;
		}
		Vector2d vitesseFinal = vitesse;
		if((position.getY() <= 5 + matrice.getY() || position.getY() >= 70 + matrice.getY() )&& booleanMurHor[chiffre]){
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
	private Vector2d collisionHorizon(Vector2d position, Vector2d vitesse, int chiffre){
		if(!booleanMurVer[chiffre]){
			compteurMurVer[chiffre] ++;
		}
		if(compteurMurVer[chiffre] == delaisRecollision){
			booleanMurVer[chiffre] = true;
			compteurMurVer[chiffre] = 0;
		}
		Vector2d vitesseFinal = vitesse;
		if((position.getX() <= 5 + matrice.getX() || position.getX() >= 95 + matrice.getX()) && booleanMurVer[chiffre]){
			//System.out.println("collision cote");
			vitesseFinal = ClassePhysique.AngleReflexion(vitesse, normalMurVer);
			booleanMurVer[chiffre] = false;
		}
		return vitesseFinal;
	}
	/**
	 * Méthode sert à calculer l'angle de rotation des ballons
	 * @param i - l'index de ballon
	 * @param vitesse - la vitesse du ballon en vecteur
	 */
	private void setBallonAngle(int i , Vector2d vitesse){
		double vitesseAngulaire = Math.toRadians(ClassePhysique.calculAngle(vitesse));		
		if(vitesseAngulaire > Math.PI){
			vitesseAngulaire = -1*vitesseAngulaire + Math.PI;
		}
		vitesseAngulaireBallon[i] = vitesseAngulaireBallon[i] + vitesseAngulaire/controleVitesseRotation;
	}
	/**
	 * Méthode run dans la classe niveauLoad
	 */
	public void run() {
		while (animationEnCours) {
			compteur++;
			if(ressortSet.size() >= 1){
				if(ressortSet.get(0).isEnDeplacement()){
					ressortSet.get(0).mettrePositionAJour(deltaT);
				}
				if(ressortSet.size() >= 2 && ressortSet.get(1).isEnDeplacement()){
					ressortSet.get(1).mettrePositionAJour(deltaT);
				}
				if(ressortSet.size() >= 3 && ressortSet.get(2).isEnDeplacement()){
					ressortSet.get(2).mettrePositionAJour(deltaT);
				}
				repaint();
				if (indEtatAnim == 1) {
					animer = false;
				} else {
					//System.err.println("animation en cours");
				}
			}
			genererCercleRotation();
			//collision ballon
			if(ballonSet.size() > 0 ){
				for(int i = 0; i < ballonSet.size(); i++){
					ballonSetPosition.remove(i);
					ballonSetPosition.add(i, ballonSet.get(i).deplacer(deltaTempsBallon));
					ballonSet.get(i).setVitesse(ballonSetVitesseFinale.get(i));
					ballonSetPositionFinale.remove(i);
					ballonSetPositionFinale.add(i, ballonSet.get(i).nextStep(deltaTempsBallon));
					//calculCollision();
					//collision mur horizontal
					ballonSetVitesseFinale.add(i,collisionVertica(ballonSetPositionFinale.get(i),ballonSetVitesseFinale.remove(i),i));
					//collision mur vertical
					ballonSetVitesseFinale.add(i,collisionHorizon(ballonSetPositionFinale.get(i),ballonSetVitesseFinale.remove(i),i));
					//setBallonAngle(i, ballonSetVitesseFinale.get(i));
				}
				calculCollision(e[3]);
			}

			if(compteur < coordX.size()){		
				//System.out.println(calculAngle(coordX.get(compteur-1),coordY.get(compteur-1),coordX.get(compteur),coordY.get(compteur)));
				coccinelle.setPas((coordX.get(compteur-1)-SIZE_BUG/2)/pixelsParUniteX + matrice.getX(),(coordY.get(compteur-1)-SIZE_BUG/2)/pixelsParUniteY + matrice.getY());
				coccinelle.setOrientation(calculAngle(coordX.get(compteur-1),coordY.get(compteur-1),coordX.get(compteur),coordY.get(compteur)));
			}

			/*if(particuleSet.size() > 0 ){
				for(int k = 0; k < particuleSet.size(); k++) {
					Vector2d coccPosition = new Vector2d(coccinelle.getPosX(),coccinelle.getPosY());
					Vector2d deplacement = coccPosition.substract(particuleSet.get(k).getPosition());
					deplacement = deplacement.normalize();
					if(particuleSet.get(k).getCharge() < 0){
						particuleSet.get(k).setPosition(new Vector2d(particuleSet.get(k).getPosition().getX() + deplacement.getX()/10,particuleSet.get(k).getPosition().getY() + deplacement.getY()/10));
					}else if(particuleSet.get(k).getCharge() > 0){
						particuleSet.get(k).setPosition(new Vector2d(particuleSet.get(k).getPosition().getX() - deplacement.getX()/10,particuleSet.get(k).getPosition().getY() - deplacement.getY()/10));
					}
				}
			}*/
			deplacerCharge();
			//System.out.println("animation en cours");
			repaint();
			if(compteur == coordX.size()){
				arreter();	
				System.out.println("arreter!");
			}
			try {
				Thread.sleep((long)(deltaTempsBallon*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
	/**
	 * Méthode sert à initialiser les positions des ressorts et à modifier l'etat de l'animation des objets ressorts
	 * @param i l'état de l'animation
	 */
	public void setEtatAnim(int i) {
		this.indEtatAnim = i;
		if (i == 2) {
			animer = false;
			ressortSet.get(0).reinitialiser();
			if(ressortSet.size() >= 2){
				ressortSet.get(1).reinitialiser();
			}
			if(ressortSet.size() >= 3){
				ressortSet.get(2).reinitialiser();
			}
		} else {
			if (i == 0 || i == 1) {
				demarrer();
			}
		}
		repaint();
	}
	/**
	 * Méthode sert à commencer l'animation
	 */
	public void demarrer() {
		if (!animationEnCours) {
			if(ressortSet.size() >= 1){
				ressortSet.get(0).setTempsDArret((int) (10 * 60 * deltaT / (60 * deltaT)));
				ressortSet.get(0).setEnDeplacement(true);
				if(ressortSet.size() >= 2){
					ressortSet.get(1).setTempsDArret((int) (10 * 60 * deltaT / (60 * deltaT)));
					ressortSet.get(1).setEnDeplacement(true);
				}
				if(ressortSet.size() >= 3){
					ressortSet.get(2).setTempsDArret((int) (10 * 60 * deltaT / (60 * deltaT)));
					ressortSet.get(2).setEnDeplacement(true);
				}
			}
			animer = true;
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
	 * Méthode sert à initialiser les ressorts
	 */
	public void initialiser(){
		setBackground(color);
		if(ressortSet.size() >= 1){
			ressortSet.get(0).reinitialiser();
			ressortSet.get(0).setMasse(15);
			ressortSet.get(0).setVitesse(1000);
			ressortSet.get(0).setConstantRessort(10);
			ressortSet.get(0).setCoFrottement(0.1);
			ressortSet.get(0).setDeplacement(300);
			if(ressortSet.size() >= 2){
				ressortSet.get(1).reinitialiser();
				ressortSet.get(1).setMasse(10);
				ressortSet.get(1).setVitesse(1250);
				ressortSet.get(1).setConstantRessort(10);
				ressortSet.get(1).setCoFrottement(0.1);
				ressortSet.get(1).setDeplacement(250);	
			}
			if(ressortSet.size() >= 3){
				ressortSet.get(2).reinitialiser();
				ressortSet.get(2).setMasse(5);
				ressortSet.get(2).setVitesse(1350);
				ressortSet.get(2).setConstantRessort(5);
				ressortSet.get(2).setCoFrottement(0.1);
				ressortSet.get(2).setDeplacement(600);
			}
		}
		coccinelle.setPas(coccPasInitialX, coccPasInitialY);
		animation = false;
		animationEnCours = false;
		compteur = 0;
		firstTime = true;
		permetStock = false;
		coordX.clear();
		coordY.clear();
		if(particuleSet.size() > 0){
			for(int k = 0; k < particuleSet.size(); k ++){
				particuleSet.get(k).restart();
				particuleSet.get(k).setPosition(particuleSetBuffer.get(k));
			}
		}
		repaint();
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
		vide.intersect(a1);
		if(!vide.isEmpty()){
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
	 * Méthode sert à initialiser les objets loading par le fichier mémorisé dans le nvieauLoad
	 * @param particuleSet - tous les particules loadings
	 * @param ressortSet - tous les ressorts loadings
	 * @param ballonSet - tous les ballons loadings
	 * @param cercleSet - tous les cercles loadings
	 * @param coccSet - les coordonnées de coccinelle et ceux de de fossé
	 * @param plaqueSet tous les plaques
	 * @param champsSet tous les champs magnétique
	 * @param c - la couleur du fond
	 * @param stringImage - nom de l'image pour setter l'image du fond
	 * @param mat la matrice
	 */
	public void recevoireObjet(ArrayList<Particules> particuleSet, ArrayList<Ressort> ressortSet, ArrayList<BallonChasseur> ballonSet, ArrayList<BallonChasseur> cercleSet, ArrayList<Shape> coccSet,ArrayList<Plaque> plaqueSet,ArrayList<ChampMagnetique> champsSet, Color c, String stringImage, Vector2d mat) {
		for(TransmissionInfoNiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveauLoadListener.class) ) {
			ecout.getBallonMessage(" ");
			ecout.getCercleMessage(" ");
			ecout.getPlaqueInfo(" ");
			ecout.getChampsInfo(" ");
		}
		calculerMatriceMondeVersComposant(largeurMonde);
		matrice = mat;
		pas = mat.getX();
		pasHor = mat.getY();
		matMC.translate(-1*mat.getX(),-mat.getY());
		cercleInfo = "\n ";
		String ballonInfo = "\n ";
		String plaqueInfo = "";
		String champsInfo = "";
		this.particuleSet = particuleSet;
		for(int k = 0; k < particuleSet.size(); k ++){
			particuleSetBuffer.add(particuleSet.get(k).getPosition());
		}
		this.ressortSet = ressortSet;
		if(ressortSet.size() > 0){
			for(TransmissionInfoNiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveauLoadListener.class) ) {
				ecout.afficheRessortPanel(0);
			}
			if(ressortSet.size() >= 2){
				for(TransmissionInfoNiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveauLoadListener.class) ) {
					ecout.afficheRessortPanel(1);
				}
				if(ressortSet.size() >= 3){
					for(TransmissionInfoNiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveauLoadListener.class) ) {
						ecout.afficheRessortPanel(2);
					}
				}
			}
		}
		this.ballonSet = ballonSet;
		if(ballonSet.size() > 0 ){
			for(int i = 0; i < ballonSet.size(); i++){
				ballonSetPosition.add(i, ballonSet.get(i).getPosition());
				ballonSetPositionFinale.add(i, ballonSet.get(i).getPosition());
				ballonSetVitesseFinale.add(i, ballonSet.get(i).getVitesse());
				String ballonNom = " Ballon " + i + "\n ";
				String vitesse = " vitesse: [ " + round(ballonSet.get(i).getVitesse().getX(),2) + " m/s , " + round(ballonSet.get(i).getVitesse().getY(),2) + " m/s ] \n ";
				String posBallon = " position:  [ " + round((ballonSet.get(i).getShape().getBounds2D().getX()-matrice.getX())/pixelsParUniteX,2) + " m , " + round((ballonSet.get(i).getShape().getBounds2D().getY()-matrice.getY())/pixelsParUniteY,2) + " m ] \n ";
				String orientation = " orientation: " + round(ballonSet.get(i).getOrientation(),1) + "° \n " + " \n "+ " \n ";
				ballonInfo = ballonInfo + ballonNom + vitesse + posBallon + orientation ;

			}
			for(TransmissionInfoNiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveauLoadListener.class) ) {
				ecout.getBallonMessage(ballonInfo);
			}
		}
		if(ballonSet.size() > 20){
			booleanMurHor = new boolean [ballonSet.size()];
		    compteurMurHor = new int[ballonSet.size()];
			booleanMurVer = new boolean [ballonSet.size()];
			compteurMurVer = new int[ballonSet.size()];
			booleanBallon = new boolean [ballonSet.size()][ballonSet.size()];
			compteurBallon = new int[ballonSet.size()][ballonSet.size()];
			vitesseAngulaireBallon = new double[ballonSet.size()];
		}
		this.cercleSet = cercleSet;
		if(cercleSet.size() > 0 ){
			for(int i = 0; i < cercleSet.size(); i++){
				String cercleNom = " Cercle " + i + "\n ";
				String vitesseAngulaire = " vitesseAngulaire: " + round(((cercleSet.get(i).getAngleRotation()%(Math.PI))*360)/Math.PI,2) + " rad par second \n ";
				String posCercle = " position:  [ " + round((cercleSet.get(i).getShape().getBounds2D().getX()-matrice.getX())/pixelsParUniteX,2) + " m , " + round((cercleSet.get(i).getShape().getBounds2D().getY()-matrice.getY())/pixelsParUniteY,2) + " m ] \n ";
				String centreRotation = " centreRotation: [ 50,0 m , 37,5 m] \n " + " \n " + " \n "+ " \n ";
				cercleInfo = cercleInfo + cercleNom + vitesseAngulaire + posCercle + centreRotation ;
			}
			for(TransmissionInfoNiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveauLoadListener.class) ) {
				ecout.getCercleMessage(cercleInfo);
			}
		}
		if(coccSet != null){
			this.cocciSet = coccSet;
			if(coccSet.size() >= 1){
				coccPasInitialX = coccSet.get(0).getBounds2D().getX()/pixelsParUniteX; 
				coccPasInitialY = coccSet.get(0).getBounds2D().getY()/pixelsParUniteY; 
				coccinelle.setPas(coccPasInitialX, coccPasInitialY);
				//System.out.println(coccPasInitialX + "m , " + coccPasInitialY + "m ");
			}
		}
		this.plaqueSet = plaqueSet;
		if(plaqueSet.size() >0){
			for(int p=0; p<plaqueSet.size(); p++){
				plaquePositionSet.add(plaqueSet.get(p).getPosition());
				String plaqueNom = "Plaque " + (p+1) + "\n ";
				String champsElectrique = "Champs électrique " + round(plaqueSet.get(p).getChampsElectrique()*Math.pow(10, 9),2) + "nN/C \n";
				String position = "Position " + plaqueSet.get(p).getPosition() + "\n";
				String dimension = "Longueur =" + plaqueSet.get(p).getLongueur() + " m "+ "largeur =" + 1 + " m \n\n";
				plaqueInfo = plaqueInfo+plaqueNom + champsElectrique + position + dimension;
			}
			for(TransmissionInfoNiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveauLoadListener.class) ) {
				ecout.getPlaqueInfo(plaqueInfo);
			}
		}
		this.champsSet = champsSet;
		if(champsSet.size() >0){
			for(int ch=0; ch<champsSet.size(); ch++){
				champsPositionSet.add(champsSet.get(ch).getPosition());
				String champsNom = "Champ magnétique " + (ch+1 )+"\n";
				String champsMagnetique = "Champs magnétique " + round(champsSet.get(ch).getChampsMagnetique().modulus()*Math.pow(10, 9),2) + "nT \n";
				String position = "Position " + champsSet.get(ch).getPosition() + "\n";
				String dimension = "Longueur =" + champsSet.get(ch).getLongueur() + " m "+ "largeur =" + champsSet.get(ch).getLargeur() + " m \n\n";
				champsInfo = champsInfo+champsNom + champsMagnetique + position + dimension;
			}
			for(TransmissionInfoNiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveauLoadListener.class) ) {
				ecout.getChampsInfo(champsInfo);
			}
		}
		this.color  = c;
		this.stringImage = stringImage;
		creerRessort();
		if(!stringImage.equals("IconUn.jpg")){
			lireLesTextures();
		}
	}
	/**
	 * Méthode sert à lire l'iamge du fond
	 */
	private void lireLesTextures() {
		//lecture des images qui servent de texture
		URL url = getClass().getClassLoader().getResource(stringImage);
		try {
			img = ImageIO.read(url);
		} catch (Exception e) {
			//System.out.println("Erreur de lecture du fichier de texture");
		}
	}
	/**
	 * Méthode sert à afficher le plan des boussoles
	 * @param afficherBoussole - <b>true</b> pour afficher les boussoles
	 * <p><b>false</b> pour ne pas afficher les boussoles
	 */
	public void setEffetBoussole(boolean afficherBoussole) {
		this.afficherBoussole = afficherBoussole;
		repaint();
	}
	/**
	 * Méthode sert à modifier la masse des ressorts
	 * @param a - l'indice de ressort
	 * @param masse - la masse de ressort
	 */
	public void setMasseRessort(int a , double masse){
		if(ressortSet.size() > 0 && a <= ressortSet.size()){
			//System.out.println(a +" ressort"+ masse);
			ressortSet.get(a).setMasse(masse);
		}
	}
	/**
	 * Méthode sert à modifier le deplacement initial
	 * @param a - l'indice de ressort
	 * @param deplacement - le déplacement initial du ressort
	 */
	public void setDeplacementInitial(int a , double deplacement){
		if(ressortSet.size() > 0 && a <= ressortSet.size()){
			//System.out.println(a +" ressort déplacement "+ deplacement);
			ressortSet.get(a).setDeplacement(deplacement);
		}
	}
	/**
	 * Méthode sert à modifier le constant des ressorts
	 * @param a - l'indice de ressort
	 * @param c - la constamte des ressorts
	 */
	public void setConstantRessort(int a , double c){
		if(ressortSet.size() > 0 && a <= ressortSet.size()){
			//System.out.println(a +" ressort constant ressort "+ c);
			ressortSet.get(a).setConstantRessort(c);
		}
	}
	/**
	 * Méthode sert à modifier le coeficient de frottement des ressorts
	 * @param a - l'indice de ressort
	 * @param coeficient - le coeficient de frottement des ressorts
	 */
	public void setCoeficientFrottement(int a , double coeficient){
		if(ressortSet.size() > 0 && a <= ressortSet.size()){
			//System.out.println(a +" ressort frottement "+ coeficient);
			ressortSet.get(a).setCoFrottement(coeficient);
		}
	}
	/**
	 * Méthode sert à modifier la vitesse des ressorts
	 * @param a - l'indice de ressort
	 * @param vitesse - la vitesse des ressorts
	 */
	public void setVitesseRessort(int a , double vitesse){
		if(ressortSet.size() > 0 && a <= ressortSet.size()){
			ressortSet.get(a).setVitesse(vitesse);
		}
	}
	/**
	 * Méthode sert à calculer la distance entre deux charges
	 * @param positionParticule - position Un
	 * @param positionCoccinelle - position Deux
	 * @return un vecteur qui représente la distance entre ces deux objets
	 */
	private Vector2d calculerDistanceDesDeuxCharges(Vector2d positionParticule, Vector2d positionCoccinelle){
		double xPar = positionParticule.getX();
		double yPar = positionParticule.getY();
		double xCocc = positionCoccinelle.getX();
		double yCocc = positionCoccinelle.getY();
		return new Vector2d(-(xCocc-xPar),-(yCocc - yPar));
	}
	/**
	 * Cette méthode permet d'arrondir un chiffre au nombre significatif souhaité
	 * @param valueToRound la valeur à arrondir.
	 * @param numberOfDecimalPlaces le nombre de chiffres significatif
	 * @return la nouvelle valeur
	 */
	public static double round(double valueToRound, int numberOfDecimalPlaces){
		double multipicationFactor = Math.pow(10, numberOfDecimalPlaces);
		double interestedInZeroDPs = valueToRound * multipicationFactor;
		return Math.round(interestedInZeroDPs) / multipicationFactor;
	}
	/**
	 * Cette méthode permet de déclarer l'écouteur 
	 * @param objEcouteur - l'écouteur CoccinelleListener
	 */
	public void addTransmissionInfoNiveauLoadListener(TransmissionInfoNiveauLoadListener objEcouteur) {
		OBJETS_ENREGISTRES.add(TransmissionInfoNiveauLoadListener.class, objEcouteur);
	}
	/**
	 * Cette méthode permet de mettre le niveau en mode scientifique
	 * @param b  <p><b>true</b> le niveau est en mode scientifique
	 * <p><b>false</b> le niveau n'est plus en mode scientifique
	 */
	public void scientifique(boolean b) {
		this.scientifique = b;
		repaint();

	}
	/**
	 * Cette méthode permet de connaitre la différence entre la positon d'une particule et d'une plaque (verticalement).
	 * @param distanceParticule la position de la particule.
	 * @param distancePlaque la position de la plaque.
	 * @return la différence verticale entre une plaque et une particule
	 */
	private double distanceParticuleParticule(Vector2d distanceParticule , Vector2d distancePlaque){
		double yPlaque = distanceParticule.getY();
		double yPart = distancePlaque.getY();
		return yPlaque- yPart;
	}
	/**
	 * 	Méthode sert à inverser la charge de particule
	 */
	public void inverseCharge(){
		if(particuleSet.size()  > 0){
			for(int k = 0; k < particuleSet.size(); k++) {
				particuleSet.get(k).setCharge(-1*particuleSet.get(k).getCharge());
			}
		}
		repaint();
	}
	
}
