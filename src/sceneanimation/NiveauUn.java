package sceneanimation;

import ecouteur.TransmissionInfoNiveau1Listener;
import geometrie.Bloc;
import geometrie.BlocVitre;
import geometrie.Coccinelle;
import geometrie.Mirroire;
import geometrie.Particules;
import geometrie.Prisme;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import physique.Vector2d;

/**
 * Cette classe permet de créer la première scène du niveau un.
 * @author Kevin Takla
 * @version 3.0
 */

public class NiveauUn extends JPanel implements Runnable {
	private static final long serialVersionUID = -218906068015857583L;
	private double largeurMonde = 100;
	private double diametrePt = 6;
	private ArrayList<Integer> coordX;
	private ArrayList<Integer> coordY;
	private Coccinelle coccinelle;
	private BlocVitre blocVitre;
	private Prisme prisme;
	private final double POS_X_FOSSE = 85;
	private final double POS_Y_FOSSE = 27;
	private double translatBlocX = 0;
	private double translatBlocY = 0;
	private double translatTriX = 0;
	private double translatTriY = 0;
	private boolean animation = false;
	private boolean animationEnCours = false;
	private long sleepTime = 60;
	private int compteur = 0;
	private final double SIZE_BUG = 5;
	private AffineTransform matMC;  
	private double pixelsParUniteX;
	private double pixelsParUniteY;
	private boolean premierFois = true;
	private boolean firstTime = true;
	private boolean permetStock = false;
	private boolean blocSelectionne = false;
	private boolean prismeSelectionne = false;
	private double xBlocPrecedent;
	private double yBlocPrecedent;
	private double xPrismePrecedent;
	private double yPrismePrecedent;
	private ArrayList<ArrayList> translatMirroir;
	private ArrayList<Vector2d> preceMirroirs;
	private boolean[] mirroirsSelectionne = {false,false,false};
	private double hauteurMondeSansDistorsion;
	private Rectangle2D.Double murHaut,murBas,murJardin,murCour;
	private Shape murH,murB,murG,murD;
	private Ellipse2D.Double fosse;
	private ArrayList<Shape>listeShapes;
	private Particules particule1, particule2;
	private ArrayList<Bloc>  bloc;
	private Image img = null;
	private final double SIZE_FOSSE = 3;
	private Path2D.Double lazer;
	private Color green = new Color(0,128,128);
	private ArrayList<Vector2d> posPrisme, posBlocVitre;
	private ArrayList<Mirroire> mirroirs;
	private int nbPosPrisme, nbPosBlocVitre;
	private ArrayList<Particules> particules;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList();
	private final double LONGUEUR_BLOCVITRE = 10;
	private final double LARGEUR_BLOCVITRE = 20;
	private final double[] POS_MIROIRS = {20,1,50,30,38,45};
	private AudioClip musicFond;
	private final double[] POS_PRISME = {20, 50, 20, 68, 35, 60};
	private final double POS_X_BLOCVITRE = 30;
	private final double POS_Y_BLOCVITRE = 20;
	private int[] cptMirroir= {0,0,0};
	private Ellipse2D.Double pointCollision;
	private boolean[] collisionNonPrevuMirs = {false,false,false};
	private boolean scientifique = false;
	private int nbMirPrecedent = 0;
	private ArrayList<Rectangle2D.Double> murs;
	private boolean[] contactBloc = {false,false,false};
	private ArrayList<Vector2d> listePointLaser;
	private boolean modificationEnvironnement = false;
	private boolean niveauTerminer= false;
	private Rectangle2D.Double zoneEnMovement;



	/**
	 * C'est le constructeur du niveau un partie un.
	 */
	public NiveauUn() {
		setBackground(Color.LIGHT_GRAY);
		creerMusic();
		setPreferredSize(new Dimension(800, 600));
		coordX = new ArrayList<Integer>();
		coordY = new ArrayList<Integer>();
		listeShapes = new ArrayList<Shape>();
		bloc = new ArrayList<Bloc>();
		creerBug();
		creerBloc();
		creerPrisme();
		creerMirroirs();
		creerParticules();
		initialiserVecteurDeplacement();
		pointCollision = new Ellipse2D.Double();
		URL fich1 = getClass().getClassLoader().getResource("leavesGreen.jpg");
		if (fich1 == null) {
			JOptionPane.showMessageDialog(null,	"Fichier d'image introuvable!");
		} else {
			//terminer l'acquisition de l'image ici
			// img = ...
			try{
				img=ImageIO.read(fich1);
			}
			catch(IOException e){
				//System.out.("Erreur pendant la lecture du fichier d'image");
			}
		}
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				if(permetStock){
				
				}else{
					if(zoneEnMovement.contains(arg0.getX(), arg0.getY())){
					Shape shapeTemp;
					Vector2d vecteurBloc,vecteurPrisme, vecteurMi;
					if(blocSelectionne){
						modificationEnvironnement = true;
						shapeTemp = listeShapes.get(0) ;
						if(!analyserIntersection(shapeTemp) ){
							translatBlocX += (arg0.getX() - xBlocPrecedent);
							translatBlocY += (arg0.getY() - yBlocPrecedent);
							xBlocPrecedent = arg0.getX();
							yBlocPrecedent = arg0.getY();

						}else{
							vecteurBloc = posBlocVitre.get(nbPosBlocVitre-2);
							translatBlocX = vecteurBloc.getX();
							translatBlocY = vecteurBloc.getY();
						}
						blocSelectionne = !analyserIntersection(shapeTemp);
						vecteurBloc = new Vector2d(translatBlocX,translatBlocY);
						posBlocVitre.add(vecteurBloc);
						nbPosBlocVitre ++;
						repaint();	
					}else if(prismeSelectionne){
						modificationEnvironnement = true;
						shapeTemp = prisme.getShape() ;
						if(!analyserIntersection(shapeTemp)){
							translatTriX += (arg0.getX() - xPrismePrecedent);
							translatTriY += (arg0.getY() - yPrismePrecedent);
							xPrismePrecedent = arg0.getX();
							yPrismePrecedent = arg0.getY();
						}else{
							vecteurPrisme = posPrisme.get(nbPosPrisme-2);
							translatTriX = vecteurPrisme.getX();
							translatTriY = vecteurPrisme.getY();
						}
						prismeSelectionne = !analyserIntersection(shapeTemp);
						vecteurPrisme = new Vector2d(translatTriX,translatTriY);
						posPrisme.add(vecteurPrisme);
						nbPosPrisme ++;
						repaint();	
					}else{
						int mirEnCours = trouverMirroirCollision();
						double translatMiX =0;
						double translatMiY =0;
						ArrayList<Vector2d> listeTranslatMirroir = new ArrayList<Vector2d>();
						Mirroire mTemp;
						if (mirEnCours >= 0){
							modificationEnvironnement = true;
							mTemp =mirroirs.get(trouverMirroirCollision());
							shapeTemp = mTemp.getShape();
							listeTranslatMirroir = new ArrayList<Vector2d>(translatMirroir.get(mirEnCours));
							Vector2d vecteurTemp = preceMirroirs.get(trouverMirroirCollision());
							if(cptMirroir[mirEnCours] != 0 ){

								Vector2d vectTransl = listeTranslatMirroir.get(cptMirroir[mirEnCours]-1);
								translatMiX = vectTransl.getX() + (arg0.getX() - (vecteurTemp.getX()));
								translatMiY = vectTransl.getY() + (arg0.getY() - (vecteurTemp.getY()));
							}else{
								translatMiX = (arg0.getX() - (vecteurTemp.getX()));
								translatMiY = (arg0.getY() - (vecteurTemp.getY()));
							}
							preceMirroirs.set(mirEnCours,new Vector2d(arg0.getX(),arg0.getY()));
							cptMirroir[mirEnCours]++;
							mirroirsSelectionne[mirEnCours] = !analyserIntersection(shapeTemp);
							Vector2d vecteurRetourner = new Vector2d(translatMiX,translatMiY);
							listeTranslatMirroir.add(vecteurRetourner);
							nbMirPrecedent = mirEnCours;
							translatMirroir.set(MirroirEnContact(mirEnCours,nbMirPrecedent), listeTranslatMirroir);
						}else{
							if(mirroirContact()){
								mTemp = mirroirs.get(nbMirPrecedent);
								shapeTemp = mTemp.getShape();
								listeTranslatMirroir = translatMirroir.get(nbMirPrecedent);
								vecteurMi = listeTranslatMirroir.get(cptMirroir[nbMirPrecedent]-3);
								translatMiX = vecteurMi.getX();
								translatMiY = vecteurMi.getY();
								cptMirroir[nbMirPrecedent]++;
								mirroirsSelectionne[nbMirPrecedent] = !analyserIntersection(shapeTemp);
								Vector2d vecteurRetourner = new Vector2d(translatMiX,translatMiY);
								listeTranslatMirroir.add(vecteurRetourner);
								translatMirroir.set(MirroirEnContact(mirEnCours,nbMirPrecedent), listeTranslatMirroir);
							}
						}
					}
					if(modificationEnvironnement && !niveauTerminer){
						modificationEnvironnement = false;
						for(TransmissionInfoNiveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Listener.class) ) {
							ecout.bloquerParametres();
						}
					}
					repaint();
					}
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(firstTime){
					if(Math.abs(e.getX()/pixelsParUniteX - (coccinelle.getPosX()+SIZE_BUG/2))<= 4 && Math.abs(e.getY()/pixelsParUniteY - (coccinelle.getPosY()+SIZE_BUG/2))<= 4){
						firstTime = false;
					}else{
						if (blocVitre.contains(e.getX(), e.getY())){
							blocSelectionne = true;
							xBlocPrecedent = e.getX();
							yBlocPrecedent = e.getY();
						}else if (prisme.contains(e.getX(), e.getY()) ){
							prismeSelectionne = true;
							xPrismePrecedent = e.getX();
							yPrismePrecedent = e.getY();	
						}else{ 
							selectionnerMirroirs(e.getX(), e.getY());
						}
					}
				} 


			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(permetStock){
					animation = true;
					permetStock = false;
					demarrer();
				}else{
					blocSelectionne = false;
					prismeSelectionne = false;
					for(int nbMir = 0; nbMir < mirroirsSelectionne.length ; nbMir++){
						mirroirsSelectionne[nbMir] = false;
					}
				}
			}
		});
	}
	/**
	 * Cette méthode permet de jouer la musique
	 * @param b <p><b>true</b> la musique peut jouer
	 * <p><b>false</b> la musique est arrêté.
	 */
	public void debutNiveau(boolean b){
		if(b){
			musicFond.loop();
		}else{
			musicFond.stop();
		}
	}
	
	/**
	 * Cette méthode permet de verifier si un mirroir est en contact avec un objet du niveau.
	 * @return <p><b>true</b> le miroir est en contact avec un objet
	 * <p><b>false</b> le miroir n'est pas en contact avec un objet
	 */
	private boolean mirroirContact(){
		for(int cptMir = 0; cptMir< mirroirs.size(); cptMir++){
			Mirroire mirTemp = mirroirs.get(cptMir);
			Shape shapeTemp = mirTemp.getShape();
			if(analyserIntersection(shapeTemp)){
				return true;
			}
		}
		return false;
	}

	/**
	 * cette méthode permet trouver quel mirroir est en mouvement.
	 * @param nbMir1 le mirroir sauvgarder au départ.
	 * @param nbMir2 le mirroir sauvgarder plus tard.
	 * @return
	 */
	private int MirroirEnContact(int nbMir1,int nbMir2){
		if(nbMir1 == nbMir2){
			return nbMir1;
		}else{
			if(nbMir1 >= 0 || nbMir2 >= 0){
				if(nbMir1 >= 0){
					return nbMir1;
				}else
					return nbMir2;
			}else{
				return -1;
			}
		}
	}

	/**
	 * Cette méthode permet d'initialiser les listes chainés qui contiendront les vecteurs des objets déplacables.
	 */
	private void initialiserVecteurDeplacement(){
		posPrisme = new ArrayList<Vector2d>(); 
		posBlocVitre = new ArrayList<Vector2d>();
		translatMirroir = new ArrayList<ArrayList>();
		for(int cpt = 0; cpt<mirroirs.size();cpt++){
			translatMirroir.add(new ArrayList<Vector2d>());
		}

	}
	
	private void creerMusic(){
		URL urlFichier  = getClass().getClassLoader().getResource("musiqueNiveau1Fond.wav");
		musicFond = Applet.newAudioClip(urlFichier);
	}


	/**
	 * Permet de savoir quelle mirroir est actuellement selectionné par la souris
	 * @param x la position en X de la souris.
	 * @param y la position en Y de la souris.
	 */
	private void selectionnerMirroirs(double x, double y){
		for(int nbMir = 0 ; nbMir< mirroirs.size() ; nbMir++){
			Mirroire MirroirTemp = mirroirs.get(nbMir);
			if(MirroirTemp.contains(x,y)){
				mirroirsSelectionne[nbMir] = true;
				preceMirroirs.set(nbMir, new Vector2d(x,y));
			}
			//System.err.("Mirroir "+ nbMir + " est "+ mirroirsSelectionne[nbMir]);
		}
	}
	/**
	 * Cette méthode permet de vérifié si chaque mirroir est est en contact avec la souris.
	 * @return le tableau des valeurs de vérité des mirroirs.
	 */
	private int trouverMirroirCollision(){
		for(int nbMir = 0 ; nbMir< 3 ; nbMir++){
			if(mirroirsSelectionne[nbMir]){
				return nbMir;
			}
		}
		return -1;
	}	
	/**
	 * Cette méthode permet créer les particules ainsi que ses blocs qui le protège
	 */
	private void creerParticules(){
		particule1 = new Particules(67.5,69.5);
		particule2 = new Particules(8.5,21.5);
		bloc.add(new Bloc(66,68,particule1.getRayon()));
		bloc.add(new Bloc(7,20,particule2.getRayon()));
		particules = new ArrayList<Particules>();
		particules.add(particule1);
		particules.add(particule2);
	}

	private void creerMurs(){
		final double GRANDEUR_MUR = 3;
		murs = new ArrayList<Rectangle2D.Double>();
		murs.add(new Rectangle2D.Double(0,-GRANDEUR_MUR,largeurMonde,GRANDEUR_MUR));
		murs.add(new Rectangle2D.Double(largeurMonde,0,GRANDEUR_MUR,hauteurMondeSansDistorsion));
		murs.add(new Rectangle2D.Double(0,hauteurMondeSansDistorsion,largeurMonde,GRANDEUR_MUR));
		murs.add(new Rectangle2D.Double(-GRANDEUR_MUR,0,GRANDEUR_MUR,hauteurMondeSansDistorsion));
	}

	/**
	 * Cette méthode permet de dessiner l'environnement de la première scène du niveau 1.
	 */
	public void paintComponent(Graphics g) {
		Ellipse2D.Double cercle;
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		listeShapes.clear();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(premierFois){
			calculerMatriceMondeVersComposant(largeurMonde);
			creerMurs();
			premierFois = false;
			zoneEnMovement = new Rectangle2D.Double(0,0,getWidth(),getHeight());
		}
		if(scientifique){
			setBackground(Color.black);
			double incrementationX = largeurMonde/2/20;
			double incrementationY = hauteurMondeSansDistorsion/2/15;
			int cpt =0;
			boolean premierMotierFait = true;
			final int decalageY = 6;
			final int decalageX = 8;
			final int POS_TEXT_X =127;
			final int POS_TEXT_Y = 14;
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
					}
				}else{
					if(premierMotierFait){
						cpt =0;
						premierMotierFait=false;
					}
					if((cpt+1)%2 ==0){
						g2d.drawLine(getWidth()/2 - 2,y ,getWidth()/2 + 2,y );
						g2d.drawString((cpt*incrementationY)+"", getWidth()/2 + 10,y + decalageY);
					}
				}
				cpt++;
			}
		}else{
			g2d.drawImage(img,0,0,null);
		}
		g2d.setColor(Color.green);
		for (int k = compteur; k < coordX.size(); k++) {
			cercle = new Ellipse2D.Double( coordX.get(k)-diametrePt/2, coordY.get(k)-diametrePt/2, diametrePt, diametrePt);
			g2d.fill(cercle);
		}
		murH = matMC.createTransformedShape(murHaut);
		murB = matMC.createTransformedShape(murBas);
		murG = matMC.createTransformedShape(murJardin);
		murD = matMC.createTransformedShape(murCour);
		g2d.setColor(Color.DARK_GRAY);
		bloc.add(new Bloc(POS_X_FOSSE,POS_Y_FOSSE,SIZE_FOSSE));
		fosse = new Ellipse2D.Double(POS_X_FOSSE,POS_Y_FOSSE,SIZE_FOSSE,SIZE_FOSSE); // la fossé
		g2d.fill(matMC.createTransformedShape(fosse));


		AffineTransform ratio = new AffineTransform(matMC); // matrice pour bloc
		ratio.translate(translatBlocX/pixelsParUniteX, translatBlocY/pixelsParUniteY);
		blocVitre.dessiner(g2d, ratio, green,matMC,scientifique);
		ecouteurBloc(ratio);


		AffineTransform rat = new AffineTransform(matMC); // matrice pour prisme
		rat.translate(translatTriX/pixelsParUniteX, translatTriY/pixelsParUniteY);
		prisme.dessiner(g2d, rat, Color.blue,matMC,scientifique,pixelsParUniteX,pixelsParUniteY);
		ecouteurPrisme(rat);
		ArrayList<Vector2d>listeTrans;

		for(int nbMir = 0; nbMir < mirroirs.size(); nbMir++){
			AffineTransform transfo  = new AffineTransform(matMC);
			Mirroire mirroir = mirroirs.get(nbMir);
			listeTrans = translatMirroir.get(nbMir);
			if(!listeTrans.isEmpty() && cptMirroir[nbMir] >= 0){
				Vector2d vectTranslat = listeTrans.get(cptMirroir[nbMir] - 1);
				mirroir.setTranslatText(vectTranslat.getX(), vectTranslat.getY());
				transfo.translate(vectTranslat.getX()/pixelsParUniteX, vectTranslat.getY()/pixelsParUniteY);
				mirroir.dessiner(g2d, transfo, Color.GRAY,matMC,scientifique,pixelsParUniteX,pixelsParUniteY);
			}
			mirroir.dessiner(g2d, transfo, Color.GRAY,matMC,scientifique,pixelsParUniteX,pixelsParUniteY);
		}

		coccinelle.dessiner(g2d,matMC,false);
		g2d.setColor(new Color((int)(Math.random()*250),(int)(Math.random()*250),(int)(Math.random()*250)));
		g2d.setStroke(new BasicStroke(5));
		g2d.draw(matMC.createTransformedShape(creerLigneChamp(coccinelle.getPosX() - SIZE_BUG/2 ,coccinelle.getPosY() - SIZE_BUG/2,SIZE_BUG)));
		g2d.setStroke(new BasicStroke(1));
		
		g2d.setColor(Color.DARK_GRAY);
		particule1.dessiner(g2d, matMC,"négative");
		particule2.dessiner(g2d, matMC,"négative");

		lazer = new Path2D.Double();
		trajectoireLazer();

		g2d.setColor(Color.cyan);
		for(int nbBloc = 0; nbBloc < 3; nbBloc++){
			Bloc blocTemp = bloc.get(nbBloc);
			blocTemp.dessiner(g2d, matMC);
		}

		g2d.setColor(Color.black);
		g2d.fill(matMC.createTransformedShape(pointCollision));
		placeShape();

		g2d.setColor(Color.red);
		g2d.setStroke(new BasicStroke(4));
		g2d.draw(matMC.createTransformedShape(lazer));

		if(terminerScene1(contactBloc)){
			for(TransmissionInfoNiveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Listener.class) ) {
				ecout.changementScene(true);
			}
		}

	}
	/**
	 * Cette méthode permet de dessiner le x
	 * @param x la position horizontale du x.
	 * @param y la position verticale du x.
	 * @param largeur  la largeur du x
	 * @return le x
	 */
	private Path2D.Double creerLigneChamp(double x, double y,double largeur){
		Path2D.Double champ = new Path2D.Double();
		champ.moveTo(x, y);
		champ.lineTo(x+largeur,y+largeur);
		champ.moveTo(x+largeur, y);
		champ.lineTo(x,y+largeur);
		return champ;
	}
	/**
	 * Cette méthode permet de placer tout les objeT(sous forme de Shape) dans un ArrayList.
	 */
	private void placeShape(){
		listeShapes.add(blocVitre.getShape());
		listeShapes.add(prisme.getShape());
		for(int i=0; i< mirroirs.size(); i++){
			Mirroire mirTemp = mirroirs.get(i);
			listeShapes.add(mirTemp.getShape());
		}
		listeShapes.add(coccinelle.getShape());
		for(int nbBloc = 0; nbBloc < 3 ; nbBloc++){
			Bloc blocPlace = bloc.get(nbBloc);
			if(blocPlace.getDessiable()){
				listeShapes.add(blocPlace.getShape());
			}else{
				if(nbBloc < particules.size()){
					Particules particuleTemp = particules.get(nbBloc);
					listeShapes.add(particuleTemp.getShape());
				}
			}
		}
		for(int nbMur = 0; nbMur < murs.size() ; nbMur++){
			Shape mur = murs.get(nbMur);
			listeShapes.add(matMC.createTransformedShape(mur));
		}

	}
	/**
	 * Cette méthode permet de vérifier si une aire quelconque est en collision avec un des objets du niveau.
	 * @param aire1 l'aire en question.
	 * @return la valeur de vérité de la méthode: <p><b>true</b> L'aire est en contact avec un des objets du niveau.
	 * <p><b>false</b> le point n'est pas en contact avec un des objets du niveau.
	 */
	private boolean analyserIntersection(Shape aire1){
		Shape  aire2;
		int posAire = compatibilite(aire1);
		for(int i = 0; i < listeShapes.size() ; i++){
			aire2 = listeShapes.get(i);
			if(!(i == posAire)){
				if(collision(aire1, aire2)){
					//System.out.("aire " + posAire + " en contact avec aire " + i);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Cette méthode permet de retourner la position d'un objet quelconque afin que, dans la méthode analyserIntersection(), on ne vérifie pas les mêmes aires.
	 * @param shape la forme géométrique de l'objet en question.
	 * @return la position de la forme géometrique par rapport à l'ArrayList qui contient tout les objets.
	 */
	private int compatibilite(Shape shape){
		for(int cpt = 0; cpt< listeShapes.size() ; cpt++){
			Shape shapeTemp = listeShapes.get(cpt);
			if(shapeTemp.equals(shape)){
				return cpt;
			}
		}
		return listeShapes.size();
	}
	/**
	 * Cette méthode permet de créer la coccinelle.
	 */
	private void creerBug() {
		coccinelle = new Coccinelle(3.5,50,0);

	}
	/**
	 * Cette méthode permet de créer le milieu bloc Vitre(milieu vert)
	 */
	private void creerBloc(){
		blocVitre = new BlocVitre(POS_X_BLOCVITRE,POS_Y_BLOCVITRE,LONGUEUR_BLOCVITRE,LARGEUR_BLOCVITRE,0);
	}
	/**
	 *  Cette méthode permet de créer le milieu prisme(milieu bleu)
	 */
	private void creerPrisme(){
		prisme = new Prisme(POS_PRISME[0], POS_PRISME[1], POS_PRISME[2],POS_PRISME[3], POS_PRISME[4], POS_PRISME[5], 0);
	}
	/**
	 * Cette méthode permet de créer tout les mirroirs du niveau.
	 */
	private void creerMirroirs(){
		mirroirs = new ArrayList<Mirroire>();
		mirroirs.add(new Mirroire(POS_MIROIRS[0],POS_MIROIRS[1],1,8,2*Math.PI*45/360,"miroir 1"));
		mirroirs.add( new Mirroire(POS_MIROIRS[2],POS_MIROIRS[3],1,8,-2*Math.PI*45/360,"miroir 2"));
		mirroirs.add(new Mirroire(POS_MIROIRS[4],POS_MIROIRS[5],1,8,2*Math.PI*45/360,"miroir 3"));
		translatMirroir= new ArrayList<ArrayList>();
		preceMirroirs = new ArrayList<Vector2d>();
		for(int i = 0; i< mirroirs.size(); i++){
			preceMirroirs.add(new Vector2d());
		}
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
		//System.out.("adj: " + adj);
		//System.out.("oppo: " + oppo);
		angle = Math.atan(oppo/adj) + (Math.PI/2);
		if(adj < 0){
			angle = angle + Math.PI;
		}
		return angle;

	}

	/**
	 * Cette méthode permet de modifier le diamètre du chemin de la coccinelle.
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
		while (animationEnCours) {
			compteur++;
			if(compteur < coordX.size()){		
				coccinelle.setPas((coordX.get(compteur-1)-SIZE_BUG/2)/pixelsParUniteX,(coordY.get(compteur-1)-SIZE_BUG/2)/pixelsParUniteY);
				coccinelle.setOrientation(calculAngle(coordX.get(compteur-1),coordY.get(compteur-1),coordX.get(compteur),coordY.get(compteur)));
			}
			//System.out.("animation en cours");
			repaint();
			if(compteur == coordX.size()){
				arreter();	
				//System.out.println("arreter!");
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
	/**
	 * Cette méthode permet de faire commencer l'animation.
	 */
	public void demarrer() {
		if (!animationEnCours) {
			Thread t = new Thread(this);
			t.start();
			animationEnCours = true;

		}
	}
	/**
	 * Cette méthode fait arrêter l'animation.
	 */
	public void arreter() {
		animationEnCours = false;

	}
	/**
	 * Cette méthode remet la coccinelle à sa position initiale.
	 */
	public void initialiser(){
		setBackground(Color.LIGHT_GRAY);
		animation = false;
		animationEnCours = false;
		compteur = 0;
		firstTime = true;
		permetStock = false;
		coordX.clear();
		coordY.clear();
		coccinelle.setPas(3.5,50);
		initialiserPosObjet();
		repaint();
	}
	/**
	 * Cette méthode permet de placer les objets dans leur position initiale
	 */
	private void initialiserPosObjet(){
		creerBloc();
		translatBlocX =0;
		translatBlocY =0;
		creerPrisme();
		translatTriX = 0;
		translatTriY = 0;

		mirroirs.get(0).setPos(POS_MIROIRS[0], POS_MIROIRS[1]);
		initialiseMiroir(0);
		mirroirs.get(1).setPos(POS_MIROIRS[2], POS_MIROIRS[3]);
		initialiseMiroir(1);
		mirroirs.get(2).setPos(POS_MIROIRS[4], POS_MIROIRS[5]);
		initialiseMiroir(2);

		preceMirroirs = new ArrayList<Vector2d>();
		for(int i = 0; i< mirroirs.size(); i++){
			preceMirroirs.add(new Vector2d());
		}


	}
	/**
	 * Cette méthode permet d'enlever les translations d'un mirroirs.
	 * @param nbMir
	 */
	private void initialiseMiroir(int nbMir){
		translatMirroir.get(nbMir).clear();
		cptMirroir[nbMir] = 0;
		mirroirs.get(nbMir).setTranslatText(0, 0);
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
	}
	/**
	 * Cette méthode permet de modifier l'orientation du blocVitre.
	 * @param radians la nouvelle orientation du blocVitre
	 */
	public void setAngleRec(double radians) {
		blocVitre.setOrientation(radians);
		if(analyserIntersection(blocVitre.getShape())){
			blocVitre.setOrientation(0);
			for(TransmissionInfoNiveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Listener.class) ) {
				ecout.remiseRotationMilieu(0);
			}
		}
		repaint();

	}
	/**
	 * Cette méthode permet de modifier l'orientation du prisme.
	 * @param radians la nouvelle orientation du prisme.
	 */
	public void setAnglePrisme(double radians) {
		prisme.setOrientation(radians);
		if(analyserIntersection(prisme.getShape())){
			prisme.setOrientation(0);
			for(TransmissionInfoNiveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Listener.class) ) {
				ecout.remiseRotationMilieu(1);
			}
		}
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
	 * Cette méthode permet de calculer l'orientation du laser selon son environnement.
	 */
	public void trajectoireLazer(){
		contactBloc[0] = false;
		contactBloc[1] = false;
		contactBloc[2] = false;
		listePointLaser = new ArrayList<Vector2d>();
		boolean premierContactPrisme = true;
		boolean premierContactBlocVitre = true;
		int cptDeviationMirroir =0;
		int cptDeviationMilieuPrisme = 0;
		int cptDeviationMilieuBlocVitre = 0;
		int normaleUtilise = -1;
		int normaleRep[] = {9,9,9,9,9};
		Vector2d posLazer = new Vector2d(largeurMonde-5,0);
		Vector2d posPrece = new Vector2d();
		Vector2d incrementation = new Vector2d(0,1);
		Vector2d vecteurDepart;
		lazer.moveTo(posLazer.getX(),posLazer.getY());
		ArrayList<Line2D.Double> listeShapeCollision = new ArrayList<Line2D.Double>();
		ArrayList<AffineTransform> listeTransfo = new ArrayList<AffineTransform>();
		do{
			for(int nbMir = 0; nbMir < mirroirs.size(); nbMir++){
				Mirroire mirTemp = mirroirs.get(nbMir);
				listeShapeCollision = mirTemp.envoieLigneCollision();
				listeTransfo.clear();
				listeTransfo = mirTemp.envoieTransfo();
				Line2D.Double ligneTest = new Line2D.Double(posLazer.getX()*pixelsParUniteX, posLazer.getY()*pixelsParUniteY,posPrece.getX()*pixelsParUniteX,posPrece.getY()*pixelsParUniteY);
				normaleUtilise = collisionAvecZone(listeShapeCollision,new Line2D.Double(posLazer.getX()*pixelsParUniteX, posLazer.getY()*pixelsParUniteY,posPrece.getX()*pixelsParUniteX,posPrece.getY()*pixelsParUniteY),listeTransfo.get(0),listeTransfo.get(1));
				collisionNonPrevuMirs[nbMir] = mirTemp.collisionIllegal(ligneTest);
				if(collisionNonPrevuMirs[nbMir]){
					break;
				}

				if(normaleUtilise >= 0 && !collisionNonPrevuMirs[nbMir]){
					if(normaleUtilise != normaleRep[2 + nbMir]){
						vecteurDepart = calculerVecteurDepart(posLazer.getX(),posLazer.getY(),posPrece.getX(), posPrece.getY());
						incrementation = mirTemp.deviationMirroir(vecteurDepart,normaleUtilise,cptDeviationMirroir,OBJETS_ENREGISTRES, nbMir);
						normaleRep[2 + nbMir] = normaleUtilise;
						cptDeviationMirroir++;
						lazer = new Path2D.Double(correcteurLaser(lazer,listePointLaser));
					}
				}
			}
			if(collisionNonPrevuMirs[0] || collisionNonPrevuMirs[1] ||collisionNonPrevuMirs[2]){
				break;
			}
			listeShapeCollision = prisme.envoieLigneCollision();
			listeTransfo.clear();
			listeTransfo = prisme.envoieTransfo();
			normaleUtilise = collisionAvecZone(listeShapeCollision,new Line2D.Double(posLazer.getX()*pixelsParUniteX, posLazer.getY()*pixelsParUniteY,posPrece.getX()*pixelsParUniteX,posPrece.getY()*pixelsParUniteY),listeTransfo.get(0),listeTransfo.get(1));
			if(normaleUtilise >= 0 && (prisme.contains(posLazer.getX()*pixelsParUniteX, posLazer.getY()*pixelsParUniteY) || prisme.contains(posPrece.getX()*pixelsParUniteX, posPrece.getY()*pixelsParUniteY))){
				if(premierContactPrisme){
					if(normaleUtilise != normaleRep[0]){
						vecteurDepart =  calculerVecteurDepart(posLazer.getX(),posLazer.getY(),posPrece.getX(), posPrece.getY());
						incrementation = prisme.calculerAngleDeviation(vecteurDepart,cptDeviationMilieuPrisme,posLazer.getX(),posLazer.getY(),normaleUtilise,OBJETS_ENREGISTRES,incrementation);
						premierContactPrisme = false;
						cptDeviationMilieuPrisme++;
						normaleRep[0] = normaleUtilise;
						lazer = new Path2D.Double(correcteurLaser(lazer,listePointLaser));
					}
				}
			}else{
				if(!prisme.contains(posLazer.getX()*pixelsParUniteX, posLazer.getY()*pixelsParUniteY)){
					cptDeviationMilieuPrisme = 0;
				}
				premierContactPrisme = true;
			}
			listeShapeCollision = blocVitre.envoieLigneCollision();
			listeTransfo.clear();
			listeTransfo = blocVitre.envoieTransfo();
			normaleUtilise = collisionAvecZone(listeShapeCollision,new Line2D.Double(posLazer.getX()*pixelsParUniteX, posLazer.getY()*pixelsParUniteY,posPrece.getX()*pixelsParUniteX,posPrece.getY()*pixelsParUniteY),listeTransfo.get(0),listeTransfo.get(1));
			if(normaleUtilise >= 0 && (blocVitre.contains(posLazer.getX()*pixelsParUniteX, posLazer.getY()*pixelsParUniteY) || blocVitre.contains(posPrece.getX()*pixelsParUniteX, posPrece.getY()*pixelsParUniteY))){
				if(premierContactBlocVitre){
					if(normaleUtilise != normaleRep[1]){
						vecteurDepart =  calculerVecteurDepart(posLazer.getX(),posLazer.getY(),posPrece.getX(), posPrece.getY());
						incrementation = blocVitre.calculerAngleDeviation(vecteurDepart, cptDeviationMilieuBlocVitre,normaleUtilise,OBJETS_ENREGISTRES,incrementation);
						premierContactBlocVitre = false;
						cptDeviationMilieuBlocVitre++;
						normaleRep[1] = normaleUtilise;
						lazer = new Path2D.Double(correcteurLaser(lazer,listePointLaser));
					}
				}
			}else{
				if(!blocVitre.contains(posLazer.getX()*pixelsParUniteX, posLazer.getY()*pixelsParUniteY)){
					cptDeviationMilieuBlocVitre = 0;
				}
				premierContactBlocVitre= true;
			}
			incrementation.normalize();
			for(int nbBloc = 0; nbBloc < 3; nbBloc++){
				Bloc blocTemp = bloc.get(nbBloc);
				if(blocTemp.contient(posLazer.getX(),posLazer.getY())){
					blocTemp.debloquerBloc(false);
					contactBloc[nbBloc] = true;
				}else{
					if(!contactBloc[nbBloc]){
						blocTemp.debloquerBloc(true);
					}
				}
				bloc.set(nbBloc, blocTemp);
			}
			incrementation.modifierNormeVecteur(0.05);
			posPrece = new Vector2d(posLazer.getX(), posLazer.getY());
			listePointLaser.add(posLazer);
			posLazer = posLazer.add(incrementation);
			lazer.lineTo(posPrece.getX(),posPrece.getY());
		}while(posLazer.getX() <= largeurMonde && posLazer.getX() >= 0 && posLazer.getY() <= hauteurMondeSansDistorsion && posLazer.getY() > 0 );
	} 
	/**
	 * Cette méthode permet de calculer l'angle de réfraction de la lumière dans un milieu quelqconque.
	 * @param n1 l'indice de réfraction du milieu incident.
	 * @param n2 l'indice de réfraction du milieu réfracté.
	 * @param angleDepart l'angle de départ.
	 * @return l'angle réfracté
	 */
	public double angleDeRefraction(double n1, double n2,double angleDepart){
		double angleResultat = Math.asin(n1*Math.sin(angleDepart)/n2);
		return angleResultat;
	}
	/**
	 * Cette méthode permet de modifier l'indice de réfraction du milieu vert.
	 * @param valeur la nouvelle indice de réfraction du mulieu vert.
	 */
	public void setIndiceRefractionVert(double valeur){
		blocVitre.setIndiceRefraction(valeur,OBJETS_ENREGISTRES);
		repaint();
	}
	/**
	 *  Cette méthode permet de modifier l'indice de réfraction du prisme.
	 * @param valeur la nouvelle indice de réfraction du prisme.
	 */
	public void setIndiceRefractionBleu(double valeur){
		prisme.setIndiceRefraction(valeur,OBJETS_ENREGISTRES);
		repaint();
	}
	/**
	 * Cette méthode permet d'enregister les informations ainsi que les transformation du bloc vitre.
	 * @param mat la matric de transformation du bloc vitre.
	 */
	public void ecouteurBloc(AffineTransform mat){
		for(TransmissionInfoNiveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Listener.class) ) {
			ecout.getPositionBlocVitre(blocVitre,mat);
		}
	}
	/**
	 * * Cette méthode permet d'enregister les informations ainsi que les transformation du prisme.
	 * @param mat la matric de transformation du prisme.
	 */
	public void ecouteurPrisme(AffineTransform mat){
		for(TransmissionInfoNiveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Listener.class) ) {
			ecout.getPositionPrisme(prisme,mat);
		}
	}
	/**
	 * Cette méthode permet de créer les écouteurs pour povoir transmettre des info sur la scène suivante.
	 * @param objEcouteur l'ecouteur utiliser.
	 */
	public void addTransmissionInfoNiveau1Listener(TransmissionInfoNiveau1Listener objEcouteur) {
		OBJETS_ENREGISTRES.add(TransmissionInfoNiveau1Listener.class, objEcouteur);
	}
	/**
	 * Cette méthode permet de calculer le vecteur de départ
	 * @param x2 la position en x actuelle
	 * @param y2 la position en y actuelle
	 * @param x1 la position en x actuelle
	 * @param y1 la position en y actuelle
	 * @return le vecteur résultat.
	 */
	private Vector2d calculerVecteurDepart(double x2, double y2,double x1, double y1){
		return new Vector2d(x2-x1,y2-y1);
	}

	/**
	 * Cette méthode permet de savoir si les trois bloc sont débloquer par le laser.
	 * @param contactBloc le tableau des valeur de vérité des états des blocs.
	 * @return la valeur de vérité de cette méthode: <p><b>true</b> Tous les blocs sont débloqués.
	 * <p><b>false</b> Certains blocs sont encore bloqués.
	 */
	private boolean terminerScene1(boolean[] contactBloc){
		if(contactBloc[0] && contactBloc[1] && contactBloc[2]){
			niveauTerminer = true;
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Cette méthode permet de determiner si le niveau est en mode scientifique ou pas.
	 * @param b <b>true</b> le niveau est en mode scientifique
	 * <p><b>false</b> le niveau est en mode normal
	 */
	public void setScientifique(boolean b) {
		scientifique = b;
		repaint();
	}
	/**
	 * Cette méthode permet de modifier une des normales d'un des mirroirs
	 * @param nbMir le mirroir choisi.
	 * @param nbNormale la normale choisi
	 * @param dessinable <b>true</b> la normale sera dessiner.
	 * <p><b>false</b> la normale ne sera pas dessiner.
	 */
	public void choisirNormaleMir(int nbMir, int nbNormale, boolean dessinable){
		Mirroire mir =mirroirs.get(nbMir);
		mir.dessinerNormale(nbNormale,dessinable);
		mirroirs.set(nbMir, mir);
		repaint();
	}

	/**
	 * *
	 * Cette méthode permet de modifier une des normales du prisme
	 * @param nbNormale la normale choisi
	 * @param dessinable <b>true</b> la normale sera dessiner.
	 * <p><b>false</b> la normale ne sera pas dessiner.
	 */
	public void choisirNormaleBleu(int nbNormale, boolean dessinable){
		prisme.dessinerNormale(nbNormale, dessinable);
		repaint();
	}

	/**
	 * Cette méthode permet de modifier une des normales du bloc vitre
	 * @param nbNormale la normale choisi
	 * @param dessinable <b>true</b> la normale sera dessiner.
	 * <p><b>false</b> la normale ne sera pas dessiner.
	 */
	public void choisirNormaleVert(int nbNormale, boolean dessinable){
		blocVitre.dessinerNormale(nbNormale, dessinable);
		repaint();
	}
	/**
	 * Cette méthode permet de savoir si 2 particules peut se fusionner.
	 * @param charge1 la charge de la particule 1
	 * @param charge2 la charge de la particule 2
	 * @return <b>true</b> les particules peuvent se fusionner.
	 * <p><b>false</b> les particules ne peuvent pas se fusionner.
	 */

	private int collisionAvecZone(ArrayList<Line2D.Double> zones,Line2D.Double laser,AffineTransform transfo1, AffineTransform transfo2){
		int normaleSelectionne[] = {-1,-1,-1,-1};
		int cptRep =0;
		for(int i =0; i < zones.size() ; i++){
			Line2D.Double zone = zones.get(i);
			if(zone.intersectsLine(laser)){
				normaleSelectionne[cptRep] = i;
				cptRep++;
			}
		}
		if(cptRep > 1){
			Line2D.Double zone1 = zones.get(normaleSelectionne[0]);
			Line2D.Double zone2 = zones.get(normaleSelectionne[1]);
			double dist1 = zone1.ptLineDist(laser.getX2(), laser.getY2());
			double dist2 = zone2.ptLineDist(laser.getX2(), laser.getY2());
			if(dist1 <= dist2){
				return normaleSelectionne[0];
			}else{
				return normaleSelectionne[1];
			}
		}else{
			return normaleSelectionne[0];
		}
	}

	/**
	 * Cette méthode permet d'enlever le modifier le tracé du laser afin de le rendre plus réaliste (l'imperfection des test de collision).
	 * @param laser le tracé du laser en cours
	 * @return le laser modifier.
	 */
	/*
	private Path2D.Double correctionLaser(Path2D.Double laser){

		Path2D.Double laserTransfo = new Path2D.Double();
		boolean premierTour = true;
		final PathIterator iterator = laser.getPathIterator(null);
		double[] coord = new double[6];
		final PathIterator iteratorTemoin = laser.getPathIterator(null);
		iteratorTemoin.next();
		while(!iteratorTemoin.isDone()){	
			iterator.next();
			iteratorTemoin.next();
			iterator.currentSegment(coord);
			if(premierTour){
				laserTransfo.moveTo(coord[0], coord[1]);
				premierTour= false;
			}else{
				laserTransfo.lineTo(coord[0], coord[1]);
			}
		}

		return laserTransfo;
	}
	 */

	private Path2D.Double correcteurLaser(Path2D.Double laser, ArrayList<Vector2d> listePoints){
		Path2D.Double laserTransfo = new Path2D.Double();
		boolean premiereFois = true;
		if(listePoints.size() > 2){
			for(int i =0; i< listePoints.size() -2 ; i++){
				Vector2d point = listePoints.get(i);
				if(premiereFois){
					premiereFois = false;
					laserTransfo.moveTo(point.getX(), point.getY());
				}else{
					laserTransfo.lineTo(point.getX(), point.getY());
				}
			}
			return laserTransfo;
		}else{
			return laser;
		}
	}
}
