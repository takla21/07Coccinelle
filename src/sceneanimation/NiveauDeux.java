
package sceneanimation;
import ecouteur.CoccinelleListener;
import ecouteur.TransmissionInfoNiveau1Scene2Listener;
import geometrie.Boussole;
import geometrie.Coccinelle;
import geometrie.Particules;
import geometrie.Ressort;
import geometrie.VecteurGraphique;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BasicStroke;
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
import java.awt.geom.Path2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import physique.ClassePhysique;
import physique.Vector2d;
/**
 * 
 * Classe niveau deux du jeu de coccinelle, le niveau de ressort avec le champ magnétique
 * 
 * @author Zi Zheng Wang
 * @version 3.0
 */
public class NiveauDeux extends JPanel implements Runnable {
	private static final long serialVersionUID = -218906068015857583L;
	private double diametrePt = 6;
	private ArrayList<Integer> coordX;
	private ArrayList<Integer> coordY;
	private Coccinelle coccinelle;
	private boolean animation = false;
	private boolean animationEnCours = false;
	private long sleepTime = 60;
	private int compteur = 0;
	private final double SIZE_BUG = 3;
	private final double SIZE_BILLARDRESSORTUN = 10;
	private final double SIZE_BILLARDRESSORTDEUX = 12;
	private double rayonBlocUn = SIZE_BILLARDRESSORTUN/2;
	private double rayonBlocDeux = SIZE_BILLARDRESSORTDEUX/2;
	private double correctionRotation = 0.75;
	private boolean firstTime = true;
	private boolean permetStock = false;
	private Ellipse2D.Double fosse, cercle, bouleCharge1,bouleCharge2,bouleCharge3;
	private final double SIZE_FOSSE = 3;
	private final double SIZE_CERCLE = 100;
	private Path2D.Double path;
	private int c = 1;		//positif ou négatif
	private int frequence = 0;
	private AffineTransform matMC;
	private boolean premierFois = true;
	private double pixelsParUniteX;
	private double pixelsParUniteY;
	private double largeurMonde = 100;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList(); //collection 
	private double hauteurMonde = 75;
	private Ressort ressortUn;
	private String[] grads;
	private int indEtatAnim = 2;
	private double deltaT = 0.016;
	private int nbDeTours = 0;
	private boolean animer = false;
	private Ressort ressortDeux;
	private Particules particule1,particule2,particule3;
	private BufferedImage img3, img2, img;
	private Vector2d positionPonc;
	private static double chargePositive = (1.6)*Math.pow(10,-7.5);
	private static double chargeNegative = (-1.6)*Math.pow(10,-7.5);
	private static double chargeNeutre = 0.0;
	private double chargeUn = chargeNeutre;
	private double chargeDeux = chargePositive;
	private double chargeTrois = chargeNegative;
	private VecteurGraphique vecteurOrientation;
	private boolean scientifique = false;
	private int echelle;
	private Vector2d posUn;
	private Vector2d posDeux;
	private AudioClip musicFond;
	private Vector2d posTrois;
	private String charge[] = {"négative","neutre","positive"};
	private int trois = 0;
	private int un = 1;
	private int deux = 2;
	/**
	 * Constructeur du niveuDeux du jeu coccinelle
	 */
	public NiveauDeux() {
		creerMusic();
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(800, 600));
		lireLesTextures();
		creerRessort();
		creerParticules();
		this.grads = ressortUn.getLabels();
	
		coordX = new ArrayList<Integer>();
		coordY = new ArrayList<Integer>();
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
			}
		});
		addMouseListener(new MouseAdapter() {
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
	 * Cette méthode permet de commencer la musique
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
	 * Cette méthode permet de créer la musique
	 */
	private void creerMusic(){
		URL urlFichier = getClass().getClassLoader().getResource("blackMoon.wav");
		musicFond = Applet.newAudioClip(urlFichier);
	}
	
	/**
	 * Méthode sert à dessiner les composants graphiques dans le niveau Deux du jeu coccinelle
	 */
	public void paintComponent(Graphics g) {
		Ellipse2D.Double cercle;
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(! scientifique){
			g2d.drawImage(img,0,0,null);
		}else{
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
		}
		g2d.setColor(Color.green);
		if(premierFois){
			calculerMatriceMondeVersComposant(largeurMonde);
			premierFois = false;
		}	
		for (int k = compteur; k < coordX.size(); k++) {
			cercle = new Ellipse2D.Double( coordX.get(k)-diametrePt/2, coordY.get(k)-diametrePt/2, diametrePt, diametrePt);
			g2d.fill(cercle);
		}
		g2d.setColor(Color.DARK_GRAY);
		fosse = new Ellipse2D.Double(5,65,SIZE_FOSSE,SIZE_FOSSE);
		g2d.fill(matMC.createTransformedShape(fosse));
		//collision coccinelle
		coccinelle.dessiner(g2d, matMC,false);
		//dessiner les ressorts;
		dessinerLesRessorts(g2d, matMC);
		//dessiner les vecteurs;
		if(scientifique){
			setVecteur(g2d,matMC,posUn,posDeux,posTrois);
		}else{
			setBoussole(g2d,matMC,posUn,posDeux,posTrois);
		}	
		//charged dessiner
		setCharge(g2d, matMC);

		for(CoccinelleListener ecout : OBJETS_ENREGISTRES.getListeners(CoccinelleListener.class) ) {
			ecout.getRessortVitesse(1,ressortUn.getVitesse());
			ecout.getRessortPosition(1,ressortUn.getPositionReelleBloc());
			ecout.getRessortVitesse(2,ressortDeux.getVitesse());
			ecout.getRessortPosition(2,ressortDeux.getPositionReelleBloc());
		}
		if(coccinelle.collision(fosse)){
			initialiser();
			for(CoccinelleListener ecout : OBJETS_ENREGISTRES.getListeners(CoccinelleListener.class) ) {
				ecout.niveauSwitch();
			}
		}
	}
	/**
	 * créer le bug
	 */
	private void creerBug() {
		coccinelle = new Coccinelle(75,10,0);
		
	}
	/**
	 *  Création du bloc-Ressort 
	 */
	private void creerRessort() {
		ressortUn = new Ressort(0,hauteurMonde*1/3,largeurMonde);
		ressortUn.setMasse(5);
		ressortUn.setVitesse(300);
		ressortUn.setConstantRessort(10);
		ressortUn.setCoFrottement(0.1);
		ressortUn.setDeplacement(30);
		ressortUn.setLargeurNoyau(SIZE_BILLARDRESSORTUN);
		
		ressortDeux = new Ressort(0,43,largeurMonde);
		ressortDeux.setMasse(10);
		ressortDeux.setVitesse(350);
		ressortDeux.setConstantRessort(10);
		ressortDeux.setCoFrottement(0.1);
		ressortDeux.setDeplacement(20);
		ressortDeux.setLargeurNoyau(SIZE_BILLARDRESSORTDEUX);
		
	}
	/**
	 * créer les particules de la charge
	 */
	private void creerParticules(){
		particule1 = new Particules(66,68);
		particule2 = new Particules(7,20);
		particule3 = new Particules(40,8);
		positionPonc = new Vector2d(60,50);
	}
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
			if(ressortUn.isEnDeplacement()){
				if (indEtatAnim == 1) {
					ressortUn.mettrePositionAJour(deltaT);
					
					ressortDeux.mettrePositionAJour(deltaT);
					
					repaint();
					nbDeTours++;
					animer = false;
				} else {
					ressortUn.mettrePositionAJour(deltaT);
					
					ressortDeux.mettrePositionAJour(deltaT);
					
					repaint();
					nbDeTours++;
					//System.err.println("animation en cours");
				}
			}
			//coccinelle code
			if(compteur < coordX.size()){		
			//System.out.println(calculAngle(coordX.get(compteur-1),coordY.get(compteur-1),coordX.get(compteur),coordY.get(compteur)));
			coccinelle.setPas((coordX.get(compteur-1)-SIZE_BUG/2)/pixelsParUniteX,(coordY.get(compteur-1)-SIZE_BUG/2)/pixelsParUniteY);
			coccinelle.setOrientation(calculAngle(coordX.get(compteur-1),coordY.get(compteur-1),coordX.get(compteur),coordY.get(compteur)));
			}
			//System.out.println("animation en cours");
			for(CoccinelleListener ecout : OBJETS_ENREGISTRES.getListeners(CoccinelleListener.class) ) {
				ecout.getRessortVitesse(1,ressortUn.getVitesse());
				ecout.getRessortPosition(1,ressortUn.getPositionReelleBloc());
				ecout.getRessortVitesse(2,ressortDeux.getVitesse());
				ecout.getRessortPosition(2,ressortDeux.getPositionReelleBloc());
			}
			repaint();
			if(compteur == coordX.size()){
				arreter();	
			}
			try {
				Thread.sleep((long)(deltaT*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}
	/**
	 * Méthode sert à dessiner les systems de ressort
	 * @param g2d - Graphics2D par défaut
	 * @param aff - la matrice monde réel
	 */
	private void dessinerLesRessorts(Graphics2D g2d, AffineTransform aff){
		matMC.translate(3, -3);
		ressortUn.dessiner(g2d, matMC, 0.5);
		if(collision(coccinelle.getShape(),ressortUn.getShapeBloc())){
			//System.out.println("Collisionnnnnnnnnn Un");
			animationEnCours = false;
			animation = false;
			compteur = 0;
			firstTime = true;
			permetStock = false;
			coordX.clear();
			coordY.clear();
			coccinelle.setPas(75,20);
			repaint();
		}
		posUn = new Vector2d(ressortUn.getPositionShape().getX()/pixelsParUniteX + rayonBlocUn + correctionRotation, ressortUn.getPositionShape().getY()/pixelsParUniteY + rayonBlocUn + correctionRotation); 
		matMC.translate(-3, 3);
		g2d.drawString(" Ressort Un ", (int)(posUn.getX()*pixelsParUniteX - 33), (int)(posUn.getY()*pixelsParUniteY + 22));
		
		g2d.setStroke(new BasicStroke(2));
		ressortDeux.dessiner(g2d, matMC, 0);
		if(collision(coccinelle.getShape(),ressortDeux.getShapeBloc())){
			//System.err.println("collision Deux");
			animationEnCours = false;
			animation = false;
			compteur = 0;
			firstTime = true;
			permetStock = false;
			coordX.clear();
			coordY.clear();
			coccinelle.setPas(75,20);
			repaint();
		}
		posDeux = new Vector2d(ressortDeux.getPositionShape().getX()/pixelsParUniteX + rayonBlocDeux, ressortDeux.getPositionShape().getY()/pixelsParUniteY + rayonBlocDeux); 
		g2d.drawString(" Ressort Deux ", (int)(posDeux.getX()*pixelsParUniteX - 39), (int)(posDeux.getY()*pixelsParUniteY + 26));
		
		g2d.setStroke(new BasicStroke(5));
		matMC.translate(-5, 36);
		ressortUn.dessiner(g2d, matMC, 0.31);
		if(collision(coccinelle.getShape(),ressortUn.getShapeBloc())){
			//System.err.println("collision Trois");
			animationEnCours = false;
			animation = false;
			compteur = 0;
			firstTime = true;
			permetStock = false;
			coordX.clear();
			coordY.clear();
			coccinelle.setPas(75,20);
			repaint();
		}
		matMC.translate(5, -36);
		posTrois = new Vector2d(ressortUn.getPositionShape().getX()/pixelsParUniteX + rayonBlocUn + correctionRotation, ressortUn.getPositionShape().getY()/pixelsParUniteY + rayonBlocUn + correctionRotation); 
		g2d.drawString(" Ressort Trois ", (int)(posTrois.getX()*pixelsParUniteX - 40), (int)(posTrois.getY()*pixelsParUniteY + 22));
	}
	/**
	 * Méthode sert à dessiner les objets boussoles
	 * @param g2d - Graphics2D par défaut
	 * @param aff - la matrice monde réel
	 */
	private void setBoussole(Graphics2D g2d, AffineTransform aff,Vector2d chargePositionUn,Vector2d chargePositionDeux,Vector2d chargePositionTrois){
		for(int a = 5; a < 75; a = a + 5){
			for(int k = 5; k < 100; k = k + 5){
				Vector2d position = new Vector2d(k,a);
				//champ Un
				Vector2d orientationUn = ClassePhysique.orientationChampElectrique(chargePositionUn,position);
				double distanceUn = position.substract(chargePositionUn).modulus();
				Vector2d champUn = ClassePhysique.champElectrique(chargeUn,distanceUn,orientationUn);
				//champ Deux
				Vector2d orientationDeux = ClassePhysique.orientationChampElectrique(chargePositionDeux,position);
				double distanceDeux = position.substract(chargePositionDeux).modulus();
				Vector2d champDeux = ClassePhysique.champElectrique(chargeDeux,distanceDeux,orientationDeux);
				//champ Trois
				Vector2d orientationTrois = ClassePhysique.orientationChampElectrique(chargePositionTrois,position);
				double distanceTrois = position.substract(chargePositionTrois).modulus();
				Vector2d champTrois = ClassePhysique.champElectrique(chargeTrois,distanceTrois,orientationTrois);
				//champ Cocc
				Vector2d orientationCocc = ClassePhysique.orientationChampElectrique(coccinelle.getPosition(),position);
				double distanceCocc = position.substract(coccinelle.getPosition()).modulus();
				Vector2d champCocc = ClassePhysique.champElectrique(coccinelle.getCharge()*Math.pow(10, 11),distanceCocc,orientationCocc);
				
				Vector2d champ = champUn.add(champDeux).add(champTrois).add(champCocc);
				Boussole boussole = new Boussole(position.getX(),position.getY());
				Vector2d horizon = new Vector2d(1,0);
				double angle = ClassePhysique.calculAngle(champ,horizon);
				//la correction de l'orientation
				if(champ.getY() < 0){
					angle = -1*angle;
				}
				boussole.dessiner(g2d, matMC, angle);
			}
		}
		dessinerLesRessorts(g2d, aff);
	}
	/**
	 * Méthode sert à dessiner les objets vecteurs
	 * @param g2d - Graphics2D par défaut
	 * @param aff - la matrice monde réel
	 */
	private void setVecteur(Graphics2D g2d, AffineTransform aff,Vector2d chargePositionUn,Vector2d chargePositionDeux,Vector2d chargePositionTrois){
		for(int a = 0; a < 75; a = a + 5){
			for(int k = 0; k < 100; k = k + 5){
				Vector2d position = new Vector2d(k,a);
				//champ Un
				Vector2d orientationUn = ClassePhysique.orientationChampElectrique(chargePositionUn,position);
				double distanceUn = position.substract(chargePositionUn).modulus();
				Vector2d champUn = ClassePhysique.champElectrique(chargeUn,distanceUn,orientationUn);
				//champ Deux
				Vector2d orientationDeux = ClassePhysique.orientationChampElectrique(chargePositionDeux,position);
				double distanceDeux = position.substract(chargePositionDeux).modulus();
				Vector2d champDeux = ClassePhysique.champElectrique(chargeDeux,distanceDeux,orientationDeux);
				//champ Trois
				Vector2d orientationTrois = ClassePhysique.orientationChampElectrique(chargePositionTrois,position);
				double distanceTrois = position.substract(chargePositionTrois).modulus();
				Vector2d champTrois = ClassePhysique.champElectrique(chargeTrois,distanceTrois,orientationTrois);
				//champ Cocc
				Vector2d orientationCocc = ClassePhysique.orientationChampElectrique(coccinelle.getPosition(),position);
				double distanceCocc = position.substract(coccinelle.getPosition()).modulus();
				Vector2d champCocc = ClassePhysique.champElectrique(coccinelle.getCharge()*Math.pow(10, 11),distanceCocc,orientationCocc);
				
				Vector2d champ = champUn.add(champDeux).add(champTrois).add(champCocc);
				if(distanceUn >= (SIZE_BILLARDRESSORTUN/2) && distanceDeux >= (SIZE_BILLARDRESSORTDEUX/2) && distanceTrois >= (SIZE_BILLARDRESSORTUN/2) && distanceCocc >= (SIZE_BILLARDRESSORTUN/2)){
					VecteurGraphique ori = new VecteurGraphique(position,champ,1);
					ori.dessinerChamp(g2d, matMC, Color.RED);
				}
			}
		}
		dessinerLesRessorts(g2d, aff);
	}
	/**
	 * Méthode sert à modifier les charges des particules qui sont au centre des ressorts
	 * @param g2d - Graphics2D par défaut
	 * @param aff - la matrice monde réel
	 */
	private void setCharge(Graphics2D g2d, AffineTransform aff){
		//System.out.println("position de ressort un "+posUn);
		//System.out.println("position de ressort Deux "+posDeux);
		//System.out.println("position de ressort Trois "+posTrois);
		particule1.setPosition(posTrois);
		particule2.setPosition(posUn);
		particule3.setPosition(posDeux);
		particule1.dessiner(g2d, aff,charge[trois]);
		particule2.dessiner(g2d, aff,charge[un]);
		particule3.dessiner(g2d, aff,charge[deux]);
		//g2d.drawString(" Ressort Un ", (int)(posUn.getX()*pixelsParUniteX - 33), (int)(posUn.getY()*pixelsParUniteY + 22));
		//g2d.drawString(" Ressort Deux ", (int)(posDeux.getX()*pixelsParUniteX - 39), (int)(posDeux.getY()*pixelsParUniteY + 26));
		//g2d.drawString(" Ressort Trois ", (int)(posTrois.getX()*pixelsParUniteX - 40), (int)(posTrois.getY()*pixelsParUniteY + 22));
	}
	/**
	 * Méthode sert à initialiser les positions des ressorts et à modifier l'etat de l'animation des objets ressorts
	 * @param i l'état de l'animation
	 */
	public void setEtatAnim(int i) {
		this.indEtatAnim = i;
		if (i == 2) {
			animer = false;
			nbDeTours = 0;
			ressortUn.reinitialiser();
			
			ressortDeux.reinitialiser();
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
			ressortUn.setTempsDArret((int) (10 * 60 * deltaT / (60 * deltaT)));
			ressortUn.setEnDeplacement(true);
			
			ressortDeux.setTempsDArret((int) (10 * 60 * deltaT / (60 * deltaT)));
			ressortDeux.setEnDeplacement(true);
			
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
		setBackground(Color.BLACK);
		ressortUn.reinitialiser();
		ressortUn.setMasse(5);
		ressortUn.setVitesse(300);
		ressortUn.setConstantRessort(10);
		ressortUn.setCoFrottement(0.1);
		ressortUn.setDeplacement(30);
		
		ressortDeux.reinitialiser();
		ressortDeux.setMasse(10);
		ressortDeux.setVitesse(350);
		ressortDeux.setConstantRessort(10);
		ressortDeux.setCoFrottement(0.1);
		ressortDeux.setDeplacement(25);
		animation = false;
		animationEnCours = false;
		compteur = 0;
		firstTime = true;
		permetStock = false;
		coordX.clear();
		coordY.clear();
		coccinelle.setPas(75,10);
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
		vide.subtract(a1);
		if(vide.isEmpty()){
			inclut = true;
		}
		return inclut;
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
	 * Méthode sert à mdoifier le detaTemps pour la vitesse de l'animation
	 * @param temps - vitesse de l'animation
	 */
	public void setDetaTemps(double temps) {
		this.deltaT = temps;
		repaint();
	}
	/**
	 * Méthode sert à modifier la masse des ressorts
	 * @param a - l'indice de ressort
	 * @param masse - la masse de ressort
	 */
	public void setMasseRessort(int a , double masse){
		if(a == 1){
			ressortUn.setMasse(masse);
		}else{
			ressortDeux.setMasse(masse);
		}
	}
	/**
	 * Méthode sert à modifier le deplacement initial
	 * @param a - l'indice de ressort
	 * @param deplacement - le déplacement initial du ressort
	 */
	public void setDeplacementInitial(int a , double deplacement){
		if(a == 1){
			ressortUn.setDeplacement(deplacement);
		}else{
			ressortDeux.setDeplacement(deplacement);
		}
	}
	/**
	 * Méthode sert à modifier le constant des ressorts
	 * @param a - l'indice de ressort
	 * @param c - la constamte des ressorts
	 */
	public void setConstantRessort(int a , double c){
		if(a == 1){
			ressortUn.setConstantRessort(c);
		}else{
			ressortDeux.setConstantRessort(c);
		}
	}
	/**
	 * Méthode sert à modifier le coeficient de frottement des ressorts
	 * @param a - l'indice de ressort
	 * @param coeficient - le coeficient de frottement des ressorts
	 */
	public void setCoeficientFrottement(int a , double coeficient){
		if(a == 1){
			ressortUn.setCoFrottement(coeficient);
		}else{
			ressortDeux.setCoFrottement(coeficient);
		}
	}
	/**
	 * Méthode sert à modifier la vitesse des ressorts
	 * @param a - l'indice de ressort
	 * @param vitesse - la vitesse des ressorts
	 */
	public void setVitesseRessort(int a , double vitesse){
		if(a == 1){
			ressortUn.setVitesse(vitesse);
		}else{
			ressortDeux.setVitesse(vitesse);
		}
	}
	/**
	 * Méthode pour activer le mode scientifique
	 * @param b - <b>true</b> pour afficher le mode scientifique
	 * <p><b>false</b> pour retourner au mode normal
	 */
	public void modeSicentifique(boolean b){
		this.scientifique = b;
		repaint();
	}
	/**
	 * Méthode pour modifier la charge des particules
	 * @param nombre - l'indice des particules
	 * @param charge - la charge :
	 * <p> -1 pour la charge négative
	 * <p> 1 pour la charge positive
	 * <p> 0 pour neutre
	 */
	public void setChargeParticule(int nombre, int charge){
		//System.out.println("cahrgeDeux" + charge);
		if(nombre == 1){
			chargeUn = chargePositive*charge;
			un = charge + 1;
		}else if(nombre == 2){
			chargeDeux = chargePositive*charge;
			deux = charge + 1;
		}else if(nombre == 3){
			chargeTrois = chargePositive*charge;
			trois = charge + 1;
		}
		repaint();
	}
	/**
	 * Méthode pour loader les imgaes dans le resource folder
	 */
	private void lireLesTextures() {
		//lecture des images qui servent de texture
		URL url = getClass().getClassLoader().getResource("leavesBlackOut.PNG");
		URL url2 = getClass().getClassLoader().getResource("leavesYellow2.PNG");
		URL url3 = getClass().getClassLoader().getResource("champSombre.jpg");
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

