package sceneanimation;

import ecouteur.TransmissionInfoNiveau4Listener;
import geometrie.ChampMagnetique;
import geometrie.Coccinelle;
import geometrie.Coqurelle;
import geometrie.Cyclotron;
import geometrie.Plaque;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import physique.ClassePhysique;
import physique.Vector2d;
import physique.Vector3d;

/**
 * Cette classe permet de créer le niveau 4 du jeu.
 * @author Kevin Takla
 * @version 2.0
 */
public class NiveauQuatre extends JPanel implements Runnable {
	private static final long serialVersionUID = -218906068015857583L;
	private double diametrePt = 6;
	private AudioClip musicFond;
	private ArrayList<Integer> coordX;
	private ArrayList<Integer> coordY;
	private Coccinelle coccinelle;
	private boolean animation = false;
	private boolean animationEnCours = false;
	private double deltaT = 0.06;
	private int compteur = 0;
	private final double SIZE_BUG = 3;
	private boolean firstTime = true;
	private boolean permetStock = false;
	private Ellipse2D.Double fosse;
	private final double SIZE_FOSSE = 3;
	private final double LARGEUR_MONDE_DEFAUT = 150;
	private double largeurMonde =  100;
	private final double DIAMETRE_CYCLOTRON =50;
	double hauteurMondeSansDistorsion;
	private AffineTransform matMC;
	private boolean premierFois = true;
	private double pixelsParUniteX;
	private EventListenerList OBJETS_ENREGISTRES = new EventListenerList();
	private double pixelsParUniteY;
	private final double POSX_INITIALE_COCCINELLE = 144;
	private final double POSY_INITIALE_COCCINELLE = 85;
	private final double POSX_FOSSE =75;
	private final double POSY_FOSSE =10;
	private final double POS_X_PLAQUE = 20;
	private final double POS_Y_PLAQUE = 27;
	private final double LONGUEUR_PLAQUES = 60;
	private final double EPAISSEUR_MUR =2;
	private final double HAUTEUR_CYCLOTRON = 62.5;
	private final double POS_Y_INITIALE_COQUERELLES1 = 131.17;
	private final double POS_Y_INITIALE_COQUEREKKES2 = 101;
	private final int DIFFERENCE_ENTRE_DEUX_PLAQUES = 20;
	private final double ECRAN_DEFAUT_X = -50;
	private final double ECRAN_DEFAUT_Y = -40;
	private double ecranX = ECRAN_DEFAUT_X;
	private double ecranY = ECRAN_DEFAUT_Y;
	private ArrayList<Coqurelle> coqurelles;
	private Cyclotron cyclotron;
	private ArrayList<Plaque> plaques;
	private ArrayList<ChampMagnetique> champs;
	private ArrayList<Rectangle2D.Double> murs;
	private boolean[] premiereFoisCoqurelle = {true,true,true,true,true,true};
	private int[] premierInverse ={0,0,0,0,0,0};
	private BufferedImage imageBackground;
	private TexturePaint arrierePlan;
	private boolean animationCoccinelleDeplacement = false;
	private boolean terminer = false;
	private Vector2d accelerationCoccinelle =new Vector2d();
	private boolean coccinelleAccelere = false;
	private ArrayList<Shape> listeShapes;
	private boolean matMCNonIni = true;
	private boolean initialiserVersionGrande = false;
	private final double LIMITES_COQUERELLES_CONSTANTES[] = {92.67,60.5,71};
	private final double HAUTEUR_MONDE_SANS_DISTORSTION_DEFAUT =112.5;
	private Rectangle2D.Double zoneContactPremierPartie;
	private final double POS_X_COQUERELLES_INIT = 108 + ECRAN_DEFAUT_X;
	private boolean premiereFoisCoccinelleSpectrometre = true;
	private double rayonRotation ;
	private Shape zoneDessinTransfo;
	private double largeurChampsRotation;
	private boolean premiereFoisRotation = true;
	private double champsMagnetiqueRotation=0;
	private boolean premiereFoisSortie = true;
	private boolean scientifique = false;
	private String nomComposantSouris = "";
	private boolean sourisNullePart = true;
	private Rectangle2D.Double zoneDessin;
	
	/**
	 * C'est le constructeur de la classe niveau 4.
	 */
	public NiveauQuatre() {
		setBackground(Color.GRAY);
		creerMusic();
		setPreferredSize(new Dimension(800, 600));
		coordX = new ArrayList<Integer>();
		coordY = new ArrayList<Integer>();
		cyclotron = new Cyclotron(0,HAUTEUR_CYCLOTRON);
		zoneDessin = new Rectangle2D.Double(0,HAUTEUR_MONDE_SANS_DISTORSTION_DEFAUT-DIAMETRE_CYCLOTRON,LARGEUR_MONDE_DEFAUT,DIAMETRE_CYCLOTRON);
		creerImageArrierePlan();
		creerBug();
		creerCoqurelle();
		creerPlaques();
		creerChamp();
		creerMurs();
		zoneContactPremierPartie = new Rectangle2D.Double(POS_X_COQUERELLES_INIT - 10, 62, 3, 50);
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				if(permetStock){
					if(!animation){
						if(zoneDessinTransfo .contains(arg0.getX(), arg0.getY())){
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
						}else{
							coordX.clear();
							coordY.clear();
							JOptionPane.showMessageDialog(null, "CHEMIN NON-VALIDE!","ERREUR",JOptionPane.ERROR_MESSAGE);
							permetStock = false;
							firstTime = true;
						}
						repaint();
					}
				}
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				sourisNullePart =true;
				if(cyclotron.contient(e.getX(), e.getY())){
					nomComposantSouris = "Cyclotron";
					sourisNullePart =false;
				}
				if(champs.get(0).contient(e.getX(), e.getY())){
					nomComposantSouris = "Sélecteur de vitesse";
					sourisNullePart =false;
				}
				if(champs.get(1).contient(e.getX(), e.getY())){
					nomComposantSouris = "Spectromètre de masse";
					sourisNullePart =false;
				}
				if(champs.get(2).contient(e.getX(), e.getY())){
					nomComposantSouris = "Le déviateur de particule";
					sourisNullePart =false;
				}
				if(sourisNullePart){
					nomComposantSouris = "";
				}
				for(TransmissionInfoNiveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau4Listener.class) ) {
					ecout.getObjetSelectionne(nomComposantSouris);
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(firstTime){
					//System.out.println(e.getX()/pixelsParUniteX + " - " + e.getY()/pixelsParUniteY);
					//System.err.println("Position debut coccinelle = "+ (e.getX()/pixelsParUniteX - (coccinelle.getPosX()+SIZE_BUG/2) +" - " + Math.abs(e.getY()/pixelsParUniteY - (coccinelle.getPosY()+SIZE_BUG/2))) );
					if(Math.abs(e.getX()/(pixelsParUniteX) - (coccinelle.getPosX()+SIZE_BUG/2) - ecranX)<= 4 && Math.abs(e.getY()/(pixelsParUniteY) - (coccinelle.getPosY()+SIZE_BUG/2) - ecranY)<= 4){
						firstTime = false;
						permetStock = true;
					}else{
						//System.out.println("faut commencer près de coccinelle!!!");
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(permetStock){
					animationCoccinelleDeplacement = true;
					permetStock = false;
					demarrer();
					for(TransmissionInfoNiveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau4Listener.class) ) {
						ecout.animation(false);
					}
				}
			}
		});
	}
	/**
	 * Cette méthode permet de faire commencer l'animation dès que l'utilisateur aura atteint le niveau 4.
	 * @param animer <p><b>true</b> le niveau est ouvert
	 * <p><b>false</b> le niveau est fermé
	 */
	public void commencerNiveau(boolean animer){
		if(animer){
			cyclotron.calculerPeriode(coccinelle.getCharge(), coccinelle.getMasse(), OBJETS_ENREGISTRES);
			demarrer();
		}
	}
	/**
	 * Cette méthode permet de faire jouer la musique lorsque c'est nécessaire
	 * @param musicEnCours <p><b>true</b> la musique peut jouer
	 * <p><b>false</b> la musique est arrêté.
	 */
	public void commencerMusic(boolean musicEnCours){
		if(musicEnCours){
			musicFond.loop();
		}else{
			musicFond.stop();
		}
	}
	/**
	 * Cette méthode permet de chercher l'image de l'arrière-plan.
	 */
	private void creerImageArrierePlan(){
		URL fich1 = getClass().getClassLoader().getResource("champMauve.jpg");
		if (fich1 == null) {
			JOptionPane.showMessageDialog(null,	"Fichier d'image introuvable!");
		} else {
			try{
				imageBackground=ImageIO.read(fich1);
			}
			catch(IOException e){
				//System.out.println("Erreur pendant la lecture du fichier d'image");
			}
		}
	}
	/**
	 * Cette méthode permet d'ajouter tous les murs dans une liste chainée afin que la coccinelle ne sortent pas du niveau.
	 */
	private void creerListesShapes(){
		listeShapes = new ArrayList<Shape>();
		for(int nbMurs =0; nbMurs < murs.size(); nbMurs++){
			Shape mur = murs.get(nbMurs);
			listeShapes.add(mur);
		}
		listeShapes.add(plaques.get(0).getShape());
		listeShapes.add(plaques.get(1).getShape());
	}
	/**
	 * Cette méthode permet de chercher la musique de fond
	 */
	private void creerMusic(){
		URL urlFichier  = getClass().getClassLoader().getResource("musiqueNiveau4Fond.wav");
		musicFond = Applet.newAudioClip(urlFichier);
	}

	/**
	 * Cette méthode permet de dessiner le niveau en entier
	 */
	public void paintComponent(Graphics g) {
		Ellipse2D.Double cercle;
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(premierFois){
			calculerMatriceMondeVersComposant(largeurMonde);
			premierFois = false;
			zoneDessinTransfo = matMC.createTransformedShape(zoneDessin);
		}
		if(!scientifique){
			Rectangle2D.Double rectFond = new Rectangle2D.Double(0,0,getWidth(),getHeight());
			arrierePlan = new TexturePaint(imageBackground,rectFond);
			g2d.setPaint(arrierePlan);
			g2d.fill(rectFond);
		}else{
			setBackground(Color.black);
		}

		cyclotron.dessiner(g2d, matMC,scientifique);
		g2d.setColor(Color.green);

		for (int k = compteur; k < coordX.size(); k++) {
			cercle = new Ellipse2D.Double( coordX.get(k)-diametrePt/2, coordY.get(k)-diametrePt/2, diametrePt, diametrePt);
			g2d.fill(cercle);
		}
		g2d.setColor(Color.LIGHT_GRAY);
		fosse = new Ellipse2D.Double(POSX_FOSSE,POSY_FOSSE,SIZE_FOSSE,SIZE_FOSSE);
		g2d.fill(matMC.createTransformedShape(fosse));
		coccinelle.dessiner(g2d, matMC,initialiserVersionGrande);
		if(coccinelle.collision(fosse)){
			setBackground(Color.cyan);
			animationEnCours = false;
			terminer = true;
		}

		for(int nbPlaque =0; nbPlaque < plaques.size(); nbPlaque++){
			Plaque plaqueTemp = plaques.get(nbPlaque);
			plaqueTemp.dessiner(g2d, matMC);
		}

		for(int nbChamp =0; nbChamp < champs.size(); nbChamp++){
			ChampMagnetique champ = champs.get(nbChamp);
			champ.dessiner(g2d, matMC);
		}

		for(int nbCoqu =0; nbCoqu < coqurelles.size(); nbCoqu++){
			Coqurelle coqTemp = coqurelles.get(nbCoqu);
			coqTemp.dessiner(g2d, matMC, pixelsParUniteX, pixelsParUniteY);
		}
		if(scientifique){
			g2d.setColor(Color.DARK_GRAY);
		}else{
			g2d.setColor(Color.black);
		}
		for(int nbMur=0; nbMur < murs.size(); nbMur++){
			Rectangle2D.Double mur = murs.get(nbMur);
			g2d.fill(matMC.createTransformedShape(mur));
		}
		creerListesShapes();
		if(terminer){
			terminer = false;
			for(TransmissionInfoNiveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau4Listener.class) ) {
				ecout.jeuxTerminer();
			}
			initialiser();
		}
		for(TransmissionInfoNiveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau4Listener.class) ) {
			ecout.getPosCocc(coccinelle.getPosition());
		}
	}
	/**
	 * Cette méthode permet de créer la coccinelle.
	 */
	private void creerBug() {
		coccinelle = new Coccinelle(POSX_INITIALE_COCCINELLE,POSY_INITIALE_COCCINELLE,0,-3);

	}
	/**
	 * Cette méthode permet de créer les plaques PPIUC.
	 */
	private void creerPlaques(){
		plaques = new ArrayList<Plaque>();
		plaques.add(new Plaque(POS_X_PLAQUE,POS_Y_PLAQUE,1000));
		plaques.add(new Plaque(POS_X_PLAQUE,POS_Y_PLAQUE + DIFFERENCE_ENTRE_DEUX_PLAQUES,-1000));
	}
	/**
	 * Cette méthode permet de créer les champs magnétiques.
	 */
	private void creerChamp(){
		largeurChampsRotation =DIFFERENCE_ENTRE_DEUX_PLAQUES - 1;
		champs = new ArrayList<ChampMagnetique>();
		champs.add(new ChampMagnetique(POS_X_PLAQUE,POS_Y_PLAQUE + 1,LONGUEUR_PLAQUES,DIFFERENCE_ENTRE_DEUX_PLAQUES,25.72));
		champs.add(new ChampMagnetique(POS_X_PLAQUE + LONGUEUR_PLAQUES + EPAISSEUR_MUR,0,LARGEUR_MONDE_DEFAUT - (POS_X_PLAQUE + LONGUEUR_PLAQUES + EPAISSEUR_MUR),HAUTEUR_CYCLOTRON,500));
		champs.add(new ChampMagnetique(2,POS_Y_PLAQUE + 1,POS_X_PLAQUE-4,largeurChampsRotation,0));
	}

	/**
	 * Cette méthode permet de créer les coqurelles.
	 */
	private void creerCoqurelle(){
		coqurelles = new ArrayList<Coqurelle>();
		for(int nbCoqurelles =0; nbCoqurelles < 6; nbCoqurelles++){
			if(nbCoqurelles%2 ==0){
				coqurelles.add(new Coqurelle(POS_X_COQUERELLES_INIT + (nbCoqurelles*15), POS_Y_INITIALE_COQUERELLES1 +ECRAN_DEFAUT_Y,0));
			}else{
				coqurelles.add(new Coqurelle(POS_X_COQUERELLES_INIT + (nbCoqurelles*15), POS_Y_INITIALE_COQUEREKKES2 +ECRAN_DEFAUT_Y ,Math.PI));
			}
		}
	}

	private void creerMurs(){
		murs = new ArrayList<Rectangle2D.Double>();
		final double LARGEUR_MUR4 = 21;
		final double LARGEUR_MUR5 = 8.5;
		final double LARGEUR_MUR6 = 14;
		murs.add(new Rectangle2D.Double(POS_X_PLAQUE,HAUTEUR_CYCLOTRON -EPAISSEUR_MUR,LARGEUR_MONDE_DEFAUT - POS_X_PLAQUE, EPAISSEUR_MUR));
		murs.add(new Rectangle2D.Double(LARGEUR_MONDE_DEFAUT - EPAISSEUR_MUR,0,EPAISSEUR_MUR,HAUTEUR_CYCLOTRON));
		murs.add(new Rectangle2D.Double(0,HAUTEUR_MONDE_SANS_DISTORSTION_DEFAUT - 0.5,LARGEUR_MONDE_DEFAUT,EPAISSEUR_MUR));
		murs.add(new Rectangle2D.Double(POS_X_PLAQUE+LONGUEUR_PLAQUES,POSY_FOSSE + 4.5,EPAISSEUR_MUR,LARGEUR_MUR4));
		murs.add(new Rectangle2D.Double(POS_X_PLAQUE+LONGUEUR_PLAQUES,POS_Y_PLAQUE+ 13,EPAISSEUR_MUR,LARGEUR_MUR5));
		murs.add(new Rectangle2D.Double(POS_X_PLAQUE+LONGUEUR_PLAQUES,0,EPAISSEUR_MUR,LARGEUR_MUR5));
		murs.add(new Rectangle2D.Double(POS_X_PLAQUE,POS_Y_PLAQUE + DIFFERENCE_ENTRE_DEUX_PLAQUES,EPAISSEUR_MUR,LARGEUR_MUR6));
		murs.add(new Rectangle2D.Double(POS_X_PLAQUE,0,EPAISSEUR_MUR,POS_Y_PLAQUE));
		murs.add(new Rectangle2D.Double(0-EPAISSEUR_MUR,0,EPAISSEUR_MUR,LARGEUR_MONDE_DEFAUT));
	}

	/**
	 * Cette méthode permet de calculer l'angle de rotation de la coccinelle.
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
	 * * Cette méthode permet de modifier le diamètre du chemin de la coccinelle.
	 * @param diametre le nouveau  diamètre du chemin.
	 */
	public void setDiametre(int diametre) {
		this.diametrePt = diametre;
		repaint();
	}
	/**
	 * Cette méthode permet d'effacer tout les coordonées de la coccienlle sauvgarder dans deux arrayList.
	 */
	public void effacer() {
		coordX.clear();
		coordY.clear();
		repaint();
	}
	/**
	 * Cette méthode permet de faire animer le niveau.
	 */
	@Override
	public void run() {
		calculerSelecteurVitesse();
		while (animationEnCours) {
			if(animationCoccinelleDeplacement){
				compteur++;
				if(compteur < coordX.size()){		
					if(!collisionCoquerelles()){
						coccinelle.setPas((coordX.get(compteur-1)-SIZE_BUG/2)/pixelsParUniteX - ecranX,(coordY.get(compteur-1)-SIZE_BUG/2)/pixelsParUniteY - ecranY);
						coccinelle.setOrientation(calculAngle(coordX.get(compteur-1) ,coordY.get(compteur-1),coordX.get(compteur),coordY.get(compteur)));
						coccinelle.calculerVitesse(deltaT, (coordX.get(compteur))/pixelsParUniteX, (coordX.get(compteur-1))/pixelsParUniteX, (coordY.get(compteur))/pixelsParUniteY, (coordY.get(compteur-1))/pixelsParUniteY);
						verificationContactCoccinelleAvecMurs();
						if(coccinelle.collision(zoneContactPremierPartie) && !initialiserVersionGrande){
							coccinelle.setPas(cyclotron.getPosCentral().getX(),cyclotron.getPosCentral().getY());
						}
						matMCNonIni = false;
					}else{
						initialiser();
					}
				}
			}
			if(matMCNonIni){
				calculerMatriceMondeVersComposant(largeurMonde);
			}
			if(coccinelle.debutAnimation(cyclotron.getPointDepart(),matMC)){
				if(animationCoccinelleDeplacement){
					animationCoccinelleDeplacement = false;
					coordX.clear();
					coordY.clear();
					coccinelle.clearVitesse();
					accelerationCoccinelle = new Vector2d();
					coccinelle.setPas(cyclotron.getPosCentral().getX(),cyclotron.getPosCentral().getY());
					coccinelle.setOrientation(-Math.PI/2);
					cyclotron.calculerPeriode(coccinelle.getCharge(), coccinelle.getMasse(), OBJETS_ENREGISTRES);
					if(!initialiserVersionGrande){
						modifierPositionEcran( -ECRAN_DEFAUT_X, 0);
					}
				}
				if(coccinelle.collision(cyclotron.getCyclotronShape())){ //avancement de la particule dans le cyclotron
					// debut cyclotron
					if(coccinelle.collision(cyclotron.getZoneMilieu())){ // si est en dehors des deux poles
						accelerationCoccinelle.setX(0);
						accelerationCoccinelle = accelerationCoccinelle.add(ClassePhysique.calculerVitesseCoccinelleCyclotron(coccinelle.getCharge(), coccinelle.getMasse(), cyclotron.getChampElectrique()));
						coccinelleAccelere = true;
					}else{ // s'il a entré dans un des poles
						accelerationCoccinelle = ClassePhysique.trouverAccelerationDansChampMagnetique(coccinelle.getCharge(), coccinelle.getMasse(), coccinelle.getVitesseActuelle(), cyclotron.getChampMagnetique(),OBJETS_ENREGISTRES,false);
						coccinelleAccelere = true;
					}
					if(coccinelle.collision(cyclotron.getVoieSortie())){ // des qu'elle sort du cyclotron
						
						// cette condition n'a pas rapport c'est juste pour l'ajustement de la caméra du niveau
						if(!initialiserVersionGrande && premiereFoisSortie){ 
							modifierPositionEcran(0, -ECRAN_DEFAUT_Y);
							premiereFoisSortie = false;
						}
						
						coccinelleAccelere = false;
					}else{
						cyclotron.potentielleCyclotron(deltaT);
					}
					//fin cyclotron
				}
				if(coccinelle.collision(champs.get(2).getShape())){//detection du champs magnétique rotateur.
					if(premiereFoisRotation){
						premiereFoisRotation = false;
						calculerChampsMagnetiqueSelonRotation(coccinelle.getCharge(),coccinelle.getVitesseModuler(),coccinelle.getMasse());
					}
					//System.err.println(coccinelle.getVitesseActuelle().getY());
					if(coccinelle.getVitesseActuelle().getY() < -ClassePhysique.EPSILON){
						accelerationCoccinelle = ClassePhysique.trouverAccelerationDansChampMagnetique(coccinelle.getCharge(), coccinelle.getMasse(), coccinelle.getVitesseActuelle(), new Vector3d(0,0,champsMagnetiqueRotation),OBJETS_ENREGISTRES,false);
						coccinelleAccelere = true;
					}else{
						coccinelle.setVitesseY(0);
						coccinelleAccelere = false;
					}
					//System.err.println("LA VITESSE EST ==== " + coccinelle.getVitesseModuler());
				}
				if(coccinelle.collision(champs.get(0).getShape())){//detection du selecteur de vitesse.
					accelerationCoccinelle = ClassePhysique.calculerAccelerationSelecteurVitesse(coccinelle.getCharge(), coccinelle.getMasse(), plaques.get(0).getChampsElectrique(), coccinelle.getVitesseActuelle(),champs.get(0).getChampsMagnetique(),OBJETS_ENREGISTRES);
					coccinelleAccelere = true;
				}
				if(coccinelle.collision(champs.get(1).getShape())){//detection du spectromètre de masse.
					if(premiereFoisCoccinelleSpectrometre && !initialiserVersionGrande){
						premiereFoisCoccinelleSpectrometre = false;
						modifierPositionEcran(ECRAN_DEFAUT_X, 0);
					}
					accelerationCoccinelle = ClassePhysique.trouverAccelerationDansChampMagnetique(coccinelle.getCharge(), coccinelle.getMasse(), coccinelle.getVitesseActuelle(), champs.get(1).getChampsMagnetique(),OBJETS_ENREGISTRES,true);
					coccinelleAccelere = true;
				}
				if(coccinelleAccelere){
					coccinelle.avancerCoccinelle(accelerationCoccinelle, deltaT);
					coccinelleAccelere = false;
				}else{
					coccinelle.avancerCoccinelleSansAcceleration(deltaT);
				}
				verificationContactCoccinelleAvecMurs();
			}
			for(int nbCoqu =0; nbCoqu < coqurelles.size(); nbCoqu++){
				Coqurelle coqurelle = coqurelles.get(nbCoqu);
				if(premiereFoisCoqurelle[nbCoqu]){
					premiereFoisCoqurelle[nbCoqu] = debutCoqurelle();
				}else{
					if(coqurelle.getAngle() ==0){
						if((coqurelle.getY() >= LIMITES_COQUERELLES_CONSTANTES[0] && premierInverse[nbCoqu] > 0) || coqurelle.getY() <= HAUTEUR_MONDE_SANS_DISTORSTION_DEFAUT-cyclotron.getDiametre()/2 - 6){
							coqurelle.inverseDirection();
						}
					}else{
						if((coqurelle.getY() <=LIMITES_COQUERELLES_CONSTANTES[1] && premierInverse[nbCoqu] > 0) || coqurelle.getY() >=  LIMITES_COQUERELLES_CONSTANTES[2]){
							coqurelle.inverseDirection();
						}
					}
					premierInverse[nbCoqu]++;
					coqurelle.avancer();
				}
			}
			trouverVitesse();
			repaint();
			try {
				Thread.sleep((long) (1000*deltaT));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
	/**
	 *  Cette méthode permet de faire commencer l'animation.
	 */
	public void demarrer() {
		if (!animationEnCours) {
			Thread t = new Thread(this);
			t.start();
			animationEnCours = true;

		}
	}
	/**
	 *  Cette méthode fait arrêter l'animation.
	 */
	public void arreter() {
		animationEnCours = false;

	}
	/**
	 *  Cette méthode remet la coccinelle à sa position initiale.
	 */

	public void initialiser(){
		animationCoccinelleDeplacement =false;
		compteur = 0;
		firstTime = true;
		permetStock = false;
		coordX.clear();
		coordY.clear();
		coccinelle.setPas(POSX_INITIALE_COCCINELLE,POSY_INITIALE_COCCINELLE);
		coccinelle.setOrientation(0);
		coccinelle.enleverAnimationCoccinelle();
		cyclotron.restart();
		coccinelle.restart();
		premiereFoisCoccinelleSpectrometre = true;
		if(!initialiserVersionGrande){
			ecranX = ECRAN_DEFAUT_X;
			ecranY = ECRAN_DEFAUT_Y;
		}
		for(TransmissionInfoNiveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau4Listener.class) ) {
			ecout.animation(true);
		}
		calculerMatriceMondeVersComposant(largeurMonde);
		cyclotron.calculerPeriode(coccinelle.getCharge(), coccinelle.getMasse(), OBJETS_ENREGISTRES);
		repaint();
	}
	/**
	 * Cette méthode permet de calculer la matrice de pixels en monde réel.
	 * @param largeurMonde la largeur du monde souhaité
	 */
	private  void calculerMatriceMondeVersComposant( double largeurMonde ) {
		matMC = new AffineTransform();
		pixelsParUniteX =  getWidth() / largeurMonde;
		double ratio = getHeight()/(double)getWidth();
		hauteurMondeSansDistorsion = largeurMonde*ratio;
		pixelsParUniteY = getHeight() / hauteurMondeSansDistorsion ;
		matMC.scale( pixelsParUniteX, pixelsParUniteY ); 
		matMC.translate(ecranX, ecranY);
	}
	/**
	 * Cette période permet de décider aléatoirement si une coqruelle bouge ou pas.
	 * @return la décision de la méthode: <p><b>true</b> la coqruelle peut commencer à bouger.
	 * <p><b>false</b> la coqruelle devra attendre un peu avant de commencer.
	 */
	private boolean debutCoqurelle(){
		Random generateur = new Random();
		int nmbAleatoire = 1+generateur.nextInt(6);
		if(4 %nmbAleatoire ==0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Cette méthode permet de modifier le potentielle électrique du cyclotron.
	 * @param potentielle le nouveau potentielle électrique du cyclotron.
	 */
	public void setPotentielleElectriqueCyclo(int potentielle) {
		cyclotron.setPotentielleElectrique(potentielle);
		repaint();
	}
	/**
	 * Cette méthode permet de modifier un des champs magnétique
	 * @param nbChamps le champs qu'on veut modifier.
	 * @param nouveauChamps  le nouveau champs électrique.
	 */
	public void setChampsMagnetique(int nbChamps, double nouveauChamps) {
		if(nbChamps <= champs.size()-1){
			ChampMagnetique champ = champs.get(nbChamps);
			champ.setChamps(nouveauChamps);
			champs.set(nbChamps, champ);
			//System.out.println(champ.getChampsMagnetique());
			calculerSelecteurVitesse();
		}else{
			cyclotron.setChampsMagnetique(nouveauChamps);
		}
		repaint();
	}
	/**
	 * Cette méthode permet de modifier le champs électrique du sélecteur de vitesse.
	 * @param champsElectrique le nouveau champs électrique 
	 */
	public void setChampsElectrique(double champsElectrique) {
		Plaque plaque1 = plaques.get(0);
		Plaque plaque2 = plaques.get(1);
		plaque1.setChampsElectrique(champsElectrique);
		plaque2.setChampsElectrique(-champsElectrique);
		plaques.clear();
		plaques.add(plaque1);
		plaques.add(plaque2);
		calculerSelecteurVitesse();
		repaint();
	}
	/**
	 * Cette méthode permet de modifier la vitesse des coquerelles.
	 * @param vitesse la nouvelle vitesse des coquerelles.
	 */
	public void setVitesseCoquerelles(double vitesse) {
		for(int i =0; i< coqurelles.size() ; i++ ){
			Coqurelle coqur = coqurelles.get(i);
			coqur.setVitesse(vitesse);
			coqurelles.set(i, coqur);
		}
		repaint();
	}
	/**
	 * Cette méthode permet de modifier la masse de la coccinelle.
	 * @param masse la nouvelle masse de la coccinelle.
	 */
	public void setMasseCoccinelle(double masse) {
		coccinelle.setMasse(masse);
		cyclotron.calculerPeriode( coccinelle.getCharge(), masse, OBJETS_ENREGISTRES);
		repaint();

	}
	/**
	 * Cette méthode permet de modifier la charge de la coccinelle.
	 * @param charge  la nouvelle charge de la coccinelle.
	 */
	public void setChargeCoccinelle(double charge) {
		coccinelle.setCharge(charge*Math.pow(10,-9));
		cyclotron.calculerPeriode(charge, coccinelle.getMasse(), OBJETS_ENREGISTRES);
		repaint();
	}

	/**
	 * Cette méthode permet de vérifier si la coccinelle est en collision avec une des coquerelles.
	 * @return <p><b>true</b> la coccinelle est en collision avec une des coquerelles.
	 * <p><b>false</b> la coccinelle n'est pas en collision avec une des coquerelles.
	 */
	private boolean collisionCoquerelles(){
		for(int i =0; i<coqurelles.size();i++){
			Coqurelle coqur = coqurelles.get(i);
			if(coccinelle.collision(coqur.getShape())){
				return true;
			}
		}
		return false;
	}
	/**
	 * Cette méthode permet de vérifier si la coccinelle est en contact avec un des murs.
	 */
	private void verificationContactCoccinelleAvecMurs(){
		for(int nbZone =0; nbZone < listeShapes.size(); nbZone++){
			Shape zone = listeShapes.get(nbZone);
			if(coccinelle.collision(zone)){
				//System.out.println("ok");
				initialiser();
				break;
			}
		}
	}
	/**
	 * Cette méthode permet de modifier la position de l'écran par rapport au niveau.
	 * @param incrementationX la modification horizontale de l'écran.
	 * @param incrementationY la modification verticale de l'écran.
	 */
	private void modifierPositionEcran(double incrementationX, double incrementationY){
		ecranX += incrementationX;
		ecranY += incrementationY;
		calculerMatriceMondeVersComposant(largeurMonde);
		repaint();
	}

	/**
	 * Cette méthode permet d'agrandir ou de raptisser la vue du niveau.
	 * @param typeZoom <p><b>true</b> on veut raptisser l'écran.
	 * <p><b>false</b> on veut agrandir l'écran.
	 */
	public void modificationZoom(boolean typeZoom) {
		if(typeZoom){
			ecranX = 0;
			ecranY = 0;
			initialiserVersionGrande = true;
			largeurMonde = LARGEUR_MONDE_DEFAUT;
		}else{
			ecranX = ECRAN_DEFAUT_X;
			ecranY = ECRAN_DEFAUT_Y;
			largeurMonde = 100;
			initialiserVersionGrande = false;
		}
		premierFois = true;
		repaint();
	}
	/**
	 * Cette méthode permet de créer l'écouteur qui permettera de transmettre des informations.
	 * @param objEcouteur l'objet ecouteur
	 */
	public void addTransmissionInfoNiveau4Listener(TransmissionInfoNiveau4Listener objEcouteur) {
		OBJETS_ENREGISTRES.add(TransmissionInfoNiveau4Listener.class, objEcouteur);
	}
	/**
	 * Cette méthode permet de transmettre la vitesse de la coccinelle à l'utilisateur de cette classe.
	 */
	private void trouverVitesse(){
		for(TransmissionInfoNiveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau4Listener.class) ) {
			ecout.getVitesseCoccinelle(coccinelle.getVitesseModuler());
			ecout.getAcceleration(coccinelle.getAcceleration(deltaT));
		}
	}
	/**
	 * Cette méthode permet de calculer la vitesse du sélecteur de vitesse.
	 */
	private void calculerSelecteurVitesse(){
		double vitesse= (plaques.get(0).getChampsElectrique())/(champs.get(0).getChampsMagnetique().modulus());
		for(TransmissionInfoNiveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau4Listener.class) ) {
			ecout.getSelecteurVitesse(vitesse);
			ecout.getRayonIdeale(calculerRayonRotation(vitesse));
		}
	}
	/**
	 * Cette méthode permet de calculer le rayon de rotation prévue par la coccinelle.
	 * @param vitesse vitesse de la coccinelle.
	 * @return le rayon de rotation.
	 */
	private double calculerRayonRotation(double vitesse){
		rayonRotation = (vitesse*coccinelle.getMasse())/(champs.get(1).getChampsMagnetique().modulus()*coccinelle.getCharge());
		return rayonRotation;
	}
	/**
	 * Cette méthode permet de trouver le champs magnétique nécessaire pour faire rotationner la coccinelle sans perdre sa vitesse
	 * @param charge la charge de la coccinelle.
	 * @param vitesse sa vitesse
	 * @param masse sa masse
	 */
	private void calculerChampsMagnetiqueSelonRotation(double charge,double vitesse, double masse){
		champsMagnetiqueRotation = -(masse*vitesse)/(charge*(largeurChampsRotation/2 + SIZE_BUG/2));
		for(TransmissionInfoNiveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau4Listener.class) ) {
			ecout.getChampsMagnetique(champsMagnetiqueRotation);
		}
	}
	/**
	 * Cette méthode permet de savoir si la scène est en mode scientifique ou pas.
	 * @param scientifique <p><b>true</b> le niveau est en mode scientifique
	 * <p><b>false</b> le niveau n'est plus en mode scientifique
	 */
	public void setModeScientifique(boolean scientifique){
		this.scientifique = scientifique;
		coccinelle.modeScientifique(scientifique);
		repaint();
	}
	/**
	 * Cette méthode permet de calculer la période lors que l'application le souhaite.
	 */
	public void calculPeriode() {
		cyclotron.calculerPeriode(coccinelle.getCharge(), coccinelle.getMasse(), OBJETS_ENREGISTRES);
	}
}
