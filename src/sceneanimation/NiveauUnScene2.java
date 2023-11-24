package sceneanimation;

import ecouteur.TransmissionInfoNiveau1Scene2Listener;
import geometrie.BlocVitre;
import geometrie.Coccinelle;
import geometrie.Lezard;
import geometrie.Particules;
import geometrie.Prisme;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import physique.ClassePhysique;
import physique.Vector2d;


/**
 * Cette classe permet la création de l'environnement de la scène 2 du niveau 1.
 * @author Kevin Takla
 * @version 2.0
 */
public class NiveauUnScene2 extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private Coccinelle coccinelle;
	private BlocVitre blocVitre;
	private AudioClip musicFond;
	private Prisme prisme;
	private Lezard lezard;
	private Ellipse2D.Double fosse;
	private Rectangle2D.Double zone1, zone2;
	private ArrayList<Particules> particules;
	private ArrayList<Vector2d> coordCoccinelle;
	private int compteurCoccinelle = 0;
	private int compteurAnimation = 0;
	private final int premierePartieAnimation =24;
	private double deltaT = 0.06;
	private final double POS_X_PARTICULE1_INITALE = 67.5;
	private final double POS_Y_PARTICULE1_INITALE = 69.5;
	private final double POS_X_PARTICULE2_INITALE = 8.5;
	private final double POS_Y_PARTICULE2_INITALE = 21.5;
	private final double POS_X_FOSSE = 85;
	private Rectangle2D.Double rectFond;
	private final double POS_Y_FOSSE = 27;
	private final double SIZE_FOSSE = 3;
	private final double SIZE_COCCINELLE = 3;
	private final double DIAMETRE_CHEMIN = 6;
	private double posXPart1 =  POS_X_PARTICULE1_INITALE;
	private double posYPart1 = POS_Y_PARTICULE1_INITALE;
	private double posXPart2 =  POS_X_PARTICULE2_INITALE;
	private double posYPart2 = POS_Y_PARTICULE2_INITALE;	
	private double posXLezard =99;
	private double posYLezard = -19;
	private double hauteurMondeSansDistorsion;
	private final double POS_X_COCCINELLE_DEPART = 3.5;
	private final double POS_Y_COCCINELLE_DEPART = 50;
	private double pixelsParUniteX, pixelsParUniteY;
	private AffineTransform matMC;
	private boolean premiereFois = true;
	private double largeurMonde = 100;
	private boolean animationCoccinelle = false;
	private boolean animationNiveau = false;
	private boolean permetStock = false;
	private boolean firstTime = true;
	private boolean scientifique = false;
	private Thread proc = null;
	private AffineTransform transfPrisme, transfBloc;
	private ArrayList<Shape> listeShape;
	private ArrayList<Rectangle2D.Double> murs;
	private boolean coccinelleRecommencer = false;
	private int nbParticuleArrive = 0;
	private boolean[] premierContactPartCocc = {true,true};
	private boolean[] premierContactParticuleArrive = {true,true};
	private BufferedImage img;
	private TexturePaint arrierePlan;
	private EventListenerList OBJETS_ENREGISTRES = new EventListenerList();
	private Rectangle2D.Double zoneDessin;
	private Color green = new Color(0,128,128);
	private boolean niveauTerminer = false;

	/**
	 * C'est le constructeur de la classe Niveau 1 scène 2.
	 */
	public NiveauUnScene2() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(firstTime){
					if(Math.abs(e.getX()/pixelsParUniteX - (coccinelle.getPosX()+SIZE_COCCINELLE/2))<= 4 && Math.abs(e.getY()/pixelsParUniteY - (coccinelle.getPosY()+SIZE_COCCINELLE/2))<= 4){
						firstTime = false;
						permetStock = true;
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(permetStock){
					animationCoccinelle = true;
					permetStock = false;
					for(TransmissionInfoNiveau1Scene2Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Scene2Listener.class) ) {
						ecout.bloquerParametres(false);
					}
				}
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				Vector2d posSouris;
				if(permetStock){
					if(!animationCoccinelle){
						if((!detectionPosAvecEnvironnement(arg0.getX(), arg0.getY()) || listeShape.get(7).contains(arg0.getX(),arg0.getY()))&& rectFond.contains(arg0.getX(),arg0.getY())){
							posSouris = new Vector2d(arg0.getX(), arg0.getY());	
							coordCoccinelle.add(posSouris);
							
						}else{
							JOptionPane.showMessageDialog(null, "CHEMIN NON-VALIDE!","ERREUR",JOptionPane.ERROR_MESSAGE);
							coordCoccinelle.clear();
							permetStock = false;
							firstTime = true;
						}
						repaint();
					}
				}
			}
		});
		creerMusic();
		creerImageArrierePlan();
		coordCoccinelle = new ArrayList<Vector2d>();

		coccinelle = new Coccinelle(POS_X_COCCINELLE_DEPART ,POS_Y_COCCINELLE_DEPART ,0);
		blocVitre = new BlocVitre(0,0,0,0,0);
		prisme = new Prisme(0,0,0,0,0,0,0);
		lezard = new Lezard(posXLezard,posYLezard);
		transfBloc = new AffineTransform();
		transfPrisme = new AffineTransform();
		zone1 = new Rectangle2D.Double(15,25,75,40);
		zone2 = new Rectangle2D.Double(15,5,60,20);
		listeShape = new ArrayList<Shape>();
		zoneDessin = new Rectangle2D.Double(0,0,getWidth(),getHeight());
		
		creerParticules();

		fosse = new Ellipse2D.Double(POS_X_FOSSE,POS_Y_FOSSE, SIZE_FOSSE, SIZE_FOSSE);
	}
	/**
	 * Cette méthode permet de vérifier si un point quelconque touche avec un objet du niveau.
	 * @param posX la position en x du point.
	 * @param posY la position en x du point.
	 * @return la valeur de vérité de cette méthode: <p><b>true</b> le point est en collision avec un objet du niveau.
	 * <p><b>false</b>  le point n'est pas en collision avec un objet du niveau.
	 */
	private boolean detectionPosAvecEnvironnement(double posX, double posY){
		for(int nbShape = 0; nbShape < listeShape.size(); nbShape++){
			Shape shapeTemp = listeShape.get(nbShape);
			shapeTemp = matMC.createTransformedShape(shapeTemp);
			if(shapeTemp.contains(posX*pixelsParUniteX, posY*pixelsParUniteY)){
				return true;
			}
		}
		return false;
	}
	/**
	 * Cette méthode permet de chercher l'image de l'arrière plan du niveau.
	 */
	private void creerImageArrierePlan(){
		URL fich1 = getClass().getClassLoader().getResource("leavesViolet.jpg");

		if (fich1 == null) {
			JOptionPane.showMessageDialog(null,	"Fichier d'image introuvable!");
		} else {
			try{
				img=ImageIO.read(fich1);
			}
			catch(IOException e){
				//System.out.println("Erreur pendant la lecture du fichier d'image");
			}
		}
	}
	/**
	 * Cette méthode permet de chercher la musique de fond
	 */
	private void creerMusic(){
		URL urlFichier  = getClass().getClassLoader().getResource("musiqueNiveau1Partie2Fond.wav");
		musicFond = Applet.newAudioClip(urlFichier);
	}
	
	/**
	 * Cette méthode crée les particules
	 */
	private void creerParticules(){
		particules = new ArrayList<Particules>();
		particules.add(new Particules(posXPart1,posYPart1));
		particules.add(new Particules(posXPart2,posYPart2));
	}
	/**
	 * Cette méthode permet de créer les murs du niveau.
	 */
	private void creerMurs(){
		final double GRANDEUR_MUR = 3;
		murs = new ArrayList<Rectangle2D.Double>();
		murs.add(new Rectangle2D.Double(0,-GRANDEUR_MUR+1,largeurMonde,GRANDEUR_MUR));
		murs.add(new Rectangle2D.Double(largeurMonde-1,0,GRANDEUR_MUR,hauteurMondeSansDistorsion));
		murs.add(new Rectangle2D.Double(0,hauteurMondeSansDistorsion-1,largeurMonde,GRANDEUR_MUR));
		murs.add(new Rectangle2D.Double(-GRANDEUR_MUR +1,0,GRANDEUR_MUR,hauteurMondeSansDistorsion));
	}

	/**
	 * Cette méthode fait commencer l'animation dès que ce niveau est visible.
	 * @param animer la valeur de vérité de l'animation du niveau.
	 * 
	 */
	public void commencerAnimation(boolean animer){
		if(animer){
			niveauTerminer = false;
			demarrer();
		}else{
			animationNiveau = false;
		}
	}
	/**
	 * Cette méthode permet de faire jouer la musique lorsque c'est nécessaire.
	 * @param musicEnCours  <p><b>true</b> le miroir est en contact avec un objet
	 * <p><b>false</b> le miroir n'est pas en contact avec un objet
	 */
	public void commencerMusic(boolean musicEnCours){
		if(musicEnCours){
			musicFond.loop();
		}else{
			musicFond.stop();
		}
	}

	@Override
	/**
	 * Cette méthode permet de dessiner l'environnement du niveau 1 partie 2
	 */
	public void paintComponent(Graphics g){
		Ellipse2D.Double chemin;
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rectFond = new Rectangle2D.Double(0,0,getWidth(),getHeight());
		
		if(scientifique){
			setBackground(Color.black);
			double incrementationX = largeurMonde/2/20;
			double incrementationY = hauteurMondeSansDistorsion/2/15;
			int cpt =0;
			boolean premierMotierFait = true;
			final int decalageY = 6;
			final int decalageX = 8;
			final int POS_TEXT_X =127;
			final int POS_TEXT_Y = 20;
			g2d.setColor(Color.DARK_GRAY);
			g2d.drawString("Axe des abscisses (m)", getWidth() - POS_TEXT_X, getHeight()/2 - decalageY);
			g2d.drawString("Axe des ordonnées (m)", getWidth()/2 - POS_TEXT_X,POS_TEXT_Y);
			for(int x = 0; x < getWidth() ; x+= 20){
				if(x == getWidth()/2){
					g2d.setColor(Color.cyan);
				}else{
					g2d.setColor(Color.DARK_GRAY);
				}
				g2d.drawLine(x, 0, x, getHeight());
				g2d.setColor(Color.cyan);
				if(x < getWidth()/2){
					if(cpt%2 ==0){
					g2d.drawLine(x ,getHeight()/2 -2 ,x ,getHeight()/2 + 2);	
					g2d.drawString(((int)((largeurMonde/2) -  (cpt*incrementationX))) +"", x -decalageX,getHeight()/2 + 2*decalageY + 5);
					}
				}else{
					if(premierMotierFait){
						cpt =0;
						premierMotierFait=false;
					}
					if(cpt%2 ==0){
					g2d.drawLine(x ,getHeight()/2 -2 ,x ,getHeight()/2 + 2);	
					g2d.drawString((int)(cpt*incrementationX)+"", x -decalageX,(int)getHeight()/2 + 2*decalageY + 5);
					}
				}
				cpt++;
			}
			cpt =0;
			premierMotierFait = true;
			for(int y=0; y< getHeight(); y+= 20){
				if(y == getHeight()/2){
					g2d.setColor(Color.cyan);
				}else{
					g2d.setColor(Color.DARK_GRAY);
				}
				g2d.drawLine(0, y, getWidth(), y);
				g2d.setColor(Color.cyan);
				if(y < getHeight()/2){
					if(cpt%2 ==0){
					g2d.drawLine(getWidth()/2 - 2,y ,getWidth()/2 + 2,y );
					g2d.drawString((((hauteurMondeSansDistorsion/2) -  (cpt*incrementationY))) +"", getWidth()/2 + 10,y + decalageY);
					//System.out.println((hauteurMondeSansDistorsion/2) -  (cpt*incrementationY));
					}
				}else{
					if(premierMotierFait){
						cpt =0;
						premierMotierFait=false;
					}
					if((cpt+1)%2 ==0){
					g2d.drawLine(getWidth()/2 - 2,y ,getWidth()/2 + 2,y );
					g2d.drawString((cpt*incrementationY)+"", getWidth()/2 + 10,y + decalageY);
					//System.out.println((cpt*incrementationY));
					}
				}
				cpt++;
			}
		}else{
			arrierePlan = new TexturePaint(img,rectFond);
			g2d.setPaint(arrierePlan);
			g2d.fill(rectFond);
		}
		if(premiereFois){
			calculerMatriceMondeVersComposant();
			creerMurs();
			premiereFois = false;
		}
		g2d.setColor(Color.GREEN);
		for (int k = compteurCoccinelle; k < coordCoccinelle.size(); k++) {
			Vector2d point = (Vector2d) coordCoccinelle.get(k);
			chemin = new Ellipse2D.Double( point.getX()-DIAMETRE_CHEMIN/2, point.getY()-DIAMETRE_CHEMIN/2, DIAMETRE_CHEMIN, DIAMETRE_CHEMIN);
			g2d.fill(chemin);
		}
		g2d.setColor(Color.GRAY);
		g2d.fill(matMC.createTransformedShape(fosse));
		coccinelle.dessiner(g2d,matMC,false);

		g2d.setColor(Color.DARK_GRAY);
		for(int cpt = 0; cpt < particules.size(); cpt++){
			Particules particuleDessiner = (Particules) particules.get(cpt);
			particuleDessiner.dessiner(g2d,matMC,scientifique);
		}


		if(scientifique){
			g2d.setColor(Color.white);
		}else{
			g2d.setColor(Color.black);
		}
		blocVitre.dessiner(g2d,transfBloc,green,matMC,scientifique);
		prisme.dessiner(g2d,transfPrisme,Color.blue,matMC,scientifique,0,0);
		lezard.dessiner(g2d, pixelsParUniteX, pixelsParUniteX, matMC,scientifique);

		g2d.setColor(Color.black);
		for(int i =0 ; i < murs.size() ; i++){
			Rectangle2D.Double rect = murs.get(i);
			g2d.fill(matMC.createTransformedShape(rect));
			murs.set(i, rect);
		}
		creerListesShape();
	}
	/**
	 * Cette méthode permet de créer une liste de toute les formes géométrique du niveau.
	 */
	private void creerListesShape(){
		listeShape = new ArrayList<Shape>();
		listeShape.add(prisme.getShape());
		listeShape.add(blocVitre.getShape());
		for(int nbMur = 0; nbMur < murs.size() ; nbMur++){
			Shape mur = murs.get(nbMur);
			listeShape.add(matMC.createTransformedShape(mur));
		}
		listeShape.add(matMC.createTransformedShape(lezard.getZoneContact()));
		listeShape.add(matMC.createTransformedShape(fosse));
	}

	/**
	 * Cette méthode permet de calculer la matrice qui va convertir le niveau en monde réel.
	 */
	public void calculerMatriceMondeVersComposant(){
		matMC = new AffineTransform();
		pixelsParUniteX =  getWidth() / largeurMonde;

		double ratio = getHeight()/(double)getWidth();
		hauteurMondeSansDistorsion = largeurMonde*ratio;
		pixelsParUniteY = getHeight() / hauteurMondeSansDistorsion ;
		matMC.scale( pixelsParUniteX, pixelsParUniteY ); 
	}
	@Override
	/**
	 * Cette méthode s'occupe de l'animation du niveau.
	 */
	public void run() {
		while(animationNiveau){
			Vector2d vecteurLangue = new Vector2d();
			if(compteurAnimation < premierePartieAnimation ){
				lezard.avancerLezard();
			}else{
				if(lezard.mouvementEnCours()){
					lezard.avancerLangue();
				}else{
					vecteurLangue = genererPosDanger();
					lezard.setPositionLangue(vecteurLangue.getX(), vecteurLangue.getY());
					lezard.initialser();
				}
			}
			if(animationCoccinelle){
				compteurCoccinelle++;
				if(compteurCoccinelle < coordCoccinelle.size()){
					Vector2d vecteurTemp = (Vector2d) coordCoccinelle.get(compteurCoccinelle -1);
					Vector2d vecteurTemp2 = (Vector2d) coordCoccinelle.get(compteurCoccinelle);
					Vector2d accelerationPart = new Vector2d();
					double posX = vecteurTemp.getX();
					double posY = vecteurTemp.getY();
					double posX2 = vecteurTemp2.getX();
					double posY2 = vecteurTemp2.getY();
					coccinelle.calculerVitesse(deltaT, posX/pixelsParUniteX, posX2/pixelsParUniteX, posY/pixelsParUniteY, posY2/pixelsParUniteY);

					if(coccinelle.collision(lezard.getLangue())){
						//System.out.println("TOUCHER!");
						initialiser();
					}else{
						for(int nbPart = 0; nbPart < particules.size(); nbPart++ ){
							Particules partUtiliser = particules.get(nbPart);
							Particules autrePart = particules.get(positionAutreParticule(nbPart));
							accelerationPart = ClassePhysique.calculerAccelerationParticuleAvec3Particules(partUtiliser.getCharge(), coccinelle.getCharge(),autrePart.getCharge(),partUtiliser.getMasse(), calculerDistanceParticuleCoccinelle(partUtiliser.getX(), partUtiliser.getY(), coccinelle.getPosX(), coccinelle.getPosY() ), calculerDistanceParticuleCoccinelle(autrePart.getX(), autrePart.getY(), partUtiliser.getX(), partUtiliser.getY() ),OBJETS_ENREGISTRES,nbPart);
							if(detectionEnvironnement(partUtiliser)){
								if(collision(partUtiliser.getShape(),listeShape.get(7))){ // la listeShape va chercher le shape du fossé. 
									partUtiliser.particuleDansFosse(true);
									if(premierContactParticuleArrive[nbPart]){
										if(nbParticuleArrive ==0){
											moitierFait();
											compteurCoccinelle = coordCoccinelle.size();
											nbParticuleArrive++;
										}else{
											nbParticuleArrive =0;
											JOptionPane.showMessageDialog(null,"TERMINÉ!");
											initialiser();
											 niveauTerminer = true;
											for(TransmissionInfoNiveau1Scene2Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Scene2Listener.class) ) {
												ecout.niveauSwitch();
											}

										}
										premierContactParticuleArrive[nbPart] = false;
										break;
									}
								}else{
									accelerationPart = accelerationPart.multiply(-1);
									partUtiliser.stabiliser();
									partUtiliser.posAvant();
								}
							}else{
								if(collision(coccinelle.getShape(),partUtiliser.getShape()) && ((liaisonParticules(partUtiliser.getCharge(), coccinelle.getCharge())|| !premierContactPartCocc[nbPart]))){
									if(premierContactPartCocc[nbPart]){
										coccinelle.setChargeParContact(0);
										partUtiliser.particuleCollision();
										premierContactPartCocc[nbPart] = false;
									}
									partUtiliser.setPosition(new Vector2d((posX-SIZE_COCCINELLE/2)/pixelsParUniteX,(posY-SIZE_COCCINELLE/2)/pixelsParUniteY));
								}else{
									partUtiliser.avancerParticule(accelerationPart, deltaT);
								}
								particules.set(nbPart, partUtiliser);
							}
						}
						if(coccinelleRecommencer ||  niveauTerminer){
							coccinelleRecommencer = false;
							compteurCoccinelle = 0;
						}else{
							coccinelle.setPas((posX-SIZE_COCCINELLE/2)/pixelsParUniteX,(posY-SIZE_COCCINELLE/2)/pixelsParUniteY);
							coccinelle.setOrientation(calculAngle(posX,posY,posX2,posY2));
						}
					}
				}else{
					animationCoccinelle = false;
				}
			}
			for(TransmissionInfoNiveau1Scene2Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Scene2Listener.class) ) {
				ecout.getMasse(particules.get(0).getMasse(), 0);
				ecout.getMasse(particules.get(1).getMasse(), 1);
			}
			trouverVitesseParticule();
			repaint();
			compteurAnimation++;
			try{
				Thread.sleep((long) (deltaT*1000));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
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
	 * Cette méthode permet de demarrer l'animation
	 */
	public void demarrer(){
		if(!animationNiveau){
			proc = new Thread(this);
			proc.start();
			animationNiveau = true;
		}
	}
	/**
	 * Cette méthode fait arreter l'animation
	 */
	public void arreter(){
		animationNiveau = false;
	}
	/**
	 * Cette méthode remet la coccinelle à sa place initiale.
	 */
	public void initialiser(){
		coordCoccinelle.clear();
		particules.get(0).restart();
		particules.get(1).restart();
		firstTime = true;
		coccinelle.setPas(POS_X_COCCINELLE_DEPART,POS_Y_COCCINELLE_DEPART);
		coccinelle.setOrientation(0);
		compteurCoccinelle = 0;
		coccinelle.restart();
		premierContactParticuleArrive[0] = true;
		premierContactParticuleArrive[1] = true;	
		premierContactPartCocc[0] = true;
		premierContactPartCocc[1] = true;
		nbParticuleArrive = 0;
		for(TransmissionInfoNiveau1Scene2Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Scene2Listener.class) ) {
			ecout.getForce(new Vector2d(), 0);
			ecout.getForce(new Vector2d(), 1);
			ecout.bloquerParametres(true);
		}
		repaint();
	}
	/**
	 * Cette méthode permet de placer le prisme selon sa position lors de la première scène.
	 * @param prisme les paramètres du prisme de l'autre scène.
	 * @param mat les déplacements du prisme de l'autre scène.
	 */
	public void setPosPrimse(Prisme prisme, AffineTransform mat){
		this.prisme = new Prisme(prisme);
		transfPrisme = new AffineTransform(mat);
		repaint();
	}
	/**
	 * Cette méthode permet de placer le blocVitre selon sa position lors de la première scène.
	 * @param blocVitreTemp  les paramètres du blocVitre de l'autre scène.
	 * @param mat  les déplacements du blocVitre de l'autre scène.
	 */
	public void setPosBlocVitre(BlocVitre blocVitreTemp,AffineTransform mat){
		blocVitre = new BlocVitre(blocVitreTemp);
		transfBloc = new AffineTransform(mat);
		repaint();
	}

	/**
	 * Cette méthode permet de génerer aléatoirement la position finale de la pointe de langue du lézard.
	 * @return la position finale du lézard.
	 */
	private Vector2d genererPosDanger(){
		Random generateur = new Random();
		boolean collision = false;
		int posX = 0;
		int posY = 0;
		do{
			posX = 15 + generateur.nextInt(75);
			posY = 15 + generateur.nextInt(40);
			if(prisme.contains(posX*pixelsParUniteX,posY*pixelsParUniteY) || blocVitre.contains(posX*pixelsParUniteX,posY*pixelsParUniteY)){
				collision = true;
			}

		}while((!(zone1.contains(posX,posY) || zone2.contains(posX,posY)) && collision));
		Vector2d vecteurTransmis = new Vector2d(posX,posY);
		return vecteurTransmis;
	}

	/**
	 * Cette méthode permet de calculer la distance entre un particule et la coccinelle
	 * @param xPar la position horizontale de la particule.
	 * @param yPar la position verticale de la particule.
	 * @param xCocc la position horizontale de la coccinelle.
	 * @param yCocc la position verticale de la coccinelle.
	 * @return le vecteur de la distance entre les deux.
	 */
	private Vector2d calculerDistanceParticuleCoccinelle(double xPar,double yPar,double xCocc, double yCocc){
		return new Vector2d(-(xCocc-xPar),-(yCocc - yPar));
	}
	/**
	 * Cette méthode permet de trouver la position de la particule qui n'est pas utilisée durant le calcul de l'accérlération.
	 * @param posActuelle la position de la particule qui est utilisé
	 * @return la position de la particule non utilisé
	 */
	private int positionAutreParticule(int posActuelle){
		if(posActuelle == 0){
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 * Cette méthode permet de savoir si une particule entre en collision avec un objet du niveau.
	 * @param particule la particule en question.
	 * @return la valeur de vérité de cette méthode: <p><b>true</b> La particule entre en collision avec un objet du niveau.
	 * <p><b>false</b>  La particule n'est pas en collision avec un objet du niveau.
	 */
	private boolean detectionEnvironnement(Particules particule){
		for(int nbShape=0; nbShape < listeShape.size(); nbShape++){
			Shape shapeTemp = listeShape.get(nbShape);
			if(collision(shapeTemp,particule.getShape())){
				return true;
			}
		}
		return false;
	}
	/**
	 * Cette méthode permet de vérifier si deux formes géométrique sont en collison.
	 * @param shape1 la première forme géométrique.
	 * @param shape2 la deuxième forme géométrique.
	 * @return la valeur de vérité de cette méthode: <p><b>true</b> les deux formes géométriques sont en collison.
	 * <p><b>false</b> les deux formes géométriques ne sont pas en collison.
	 */
	private boolean collision(Shape shape1, Shape shape2){
		Area aire1 = new Area(shape1);
		Area aire2 = new Area(shape2);
		Area intersection = new Area(aire1);
		intersection.intersect(aire2);
		if(!intersection.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Cette méthode permet de changer l'état du niveau
	 * @param b la nouvelle état de l'application: <p><b>true</b> le niveau est en mode scientifique.
	 * <p><b>false</b> le niveau est en mode normale.
	 */
	public void setScientifique(boolean b){
		scientifique = b;
		repaint();
	}
	/**
	 * Cette méthode permet de replacer la coccinelle sans affecter l'état des particules.
	 */
	private void moitierFait(){
		firstTime = true;
		coccinelleRecommencer= true;
		animationCoccinelle = false;
		coordCoccinelle.clear();
		premierContactPartCocc[0] = true;
		premierContactPartCocc[1] = true;
		coccinelle.restart();
		coccinelle.setPas(POS_X_COCCINELLE_DEPART,POS_Y_COCCINELLE_DEPART);
		coccinelle.setOrientation(0);
		for(TransmissionInfoNiveau1Scene2Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Scene2Listener.class) ) {
			ecout.bloquerParametres(true);
		}
		repaint();
	}
	/**
	 * Cette méthode permet de transmettre les vitesses actuelles des particules vers son panel droit afin que l'utilisateur puisse voir cette vitesse.
	 */
	private void trouverVitesseParticule(){
		Particules part1 = particules.get(0);
		Particules part2 = particules.get(1);
		for(TransmissionInfoNiveau1Scene2Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Scene2Listener.class) ) {
			ecout.getVitesseParticule1(part1.getVitesse());
			ecout.getVitesseParticule2(part2.getVitesse());
			ecout.getVitesseCoccinelle(coccinelle.getVitesseModuler());
		}
	}

	public void addTransmissionInfoNiveau1Scene2Listener(TransmissionInfoNiveau1Scene2Listener objEcouteur) {
		OBJETS_ENREGISTRES.add(TransmissionInfoNiveau1Scene2Listener.class, objEcouteur);
	}
	/**
	 * Cette méthode permet de modifier la charge d'un des particules.
	 * @param nbPart la particule choisit
	 * @param charge sa nouvelle charge.
	 */
	public void setChargeParticule(int nbPart, double charge){
		Particules part = particules.get(nbPart);
		part.setCharge(charge);
		particules.set(nbPart, part);
		repaint();
		for(TransmissionInfoNiveau1Scene2Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Scene2Listener.class) ) {
			ecout.getChargePart(charge,nbPart);
			ecout.getMasse(particules.get(nbPart).getMasse(), nbPart);
		}
	}
	/**
	 * Cette méthode permet de modifier la charge de la coccinelle
	 * @param charge la nouvelle charge de la coccinelle.
	 */
	public void setChargeCoccinelle(double charge){
		coccinelle.setCharge(charge);
		for(TransmissionInfoNiveau1Scene2Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Scene2Listener.class) ) {
			ecout.getChargeCocc(charge);
		}
		repaint();
	}
	/**
	 * Cette méthode permet de vérifier si deux particules sont compatibles pour fusionner.
	 * @param charge1 la charge de la première particule.
	 * @param charge2 la charge de la deuxième particule.
	 * @return <p><b>true</b> les deux particules peuvent se fusionner.
	 * <p><b>false</b> les deux particules ne peuvent pas se fusionner, car soit les deux particules ont la meme charge ou une des deux est neutre.
	 */
	private boolean liaisonParticules(double charge1, double charge2){
		if(charge1 != 0 && charge2 !=0){
			if(charge1 == charge2){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
}
