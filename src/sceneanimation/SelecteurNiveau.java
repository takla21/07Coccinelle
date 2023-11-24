package sceneanimation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import ecouteur.SelecNiveauListener;
import geometrie.Cible;

import javax.swing.JLabel;

import java.awt.Rectangle;
import javax.swing.SwingConstants;
/**
 * Cette méthode permet de dessiner le menu de sélection du niveau et en même temps de décider le niveau désiré.
 * @author Kevin Takla contribution de zi zheng wang (animation et changement de mouseEntered et  mouseExited)
 * @version 3.0
 */
public class SelecteurNiveau extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	private ArrayList<BufferedImage> listeImageNiveau;
	private BufferedImage loadImg;
	private Rectangle2D.Double loadRect;
	private Ellipse2D.Double addFond,loadFond,menuFond,sceneUnFond,sceneDeuxFond,sceneTroisFond,sceneQuatreFond;
	private ArrayList<Rectangle2D.Double> listeRect;
	private BufferedImage addImg;
	private Rectangle2D.Double addRect;
	private BufferedImage returnImg;
	private Rectangle2D.Double returnRect;
	private Image background;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList();
	//cible
	private final int MIN_DIAMETRE = 20;	//taille min d'une cible
	private final int MAX_DIAMETRE = 80;	//taille max d'une cible
	private final int MIN_ANNEAUX = 2;		//nombre minimum d'anneaux dans une cible
	private final int MAX_ANNEAUX = 9;		//nombre maximum d'anneaux dans une cible
	private ArrayList<Cible> cibles;
	private final int NB_CIBLES = 175;
	private boolean lasteCible = false;
	private geometrie.StyleDessin style = geometrie.StyleDessin.PLEIN,style2 = geometrie.StyleDessin.LIGNES;
	private double a = 0;
	private final double INCREMENT = 1.3;
	private boolean animer = false;
	private Thread porc = new Thread();
	private boolean encoursAnimation = false;
	private int i=0, x=0;
	private boolean afficheAddFond = false;
	private boolean afficheLoadFond = false;
	private boolean afficheMenuPFond = false;
	private JLabel lbladd;
	private JLabel lblload;
	private JLabel lblMenu;
	private JLabel lblNiveauUn;
	private JLabel labelNiveauDeux;
	private JLabel labelNiveauTrois;
	private JLabel labelNiveauQuatre;
	private boolean afficheUn, afficheDeux, afficheTrois, afficheQuatre;
	/**
	 * C'est le constructeur du sélecteur du niveau.
	 */
	public SelecteurNiveau() {
	
		chercherImage();
		creerRectangle();
		addRect  = new Rectangle2D.Double(875,55,loadImg.getWidth(),loadImg.getHeight());
		loadRect = new Rectangle2D.Double(875,250,loadImg.getWidth(),loadImg.getHeight());
		returnRect  = new Rectangle2D.Double(875,435,loadImg.getWidth(),loadImg.getHeight());
		addFond = new Ellipse2D.Double(890, 125, loadImg.getWidth(), loadImg.getHeight()-40);
		loadFond = new Ellipse2D.Double(890,310,loadImg.getWidth(),loadImg.getHeight()-40);
		menuFond = new Ellipse2D.Double(890,485,loadImg.getWidth(),loadImg.getHeight()-40);
		setPreferredSize(new Dimension(1183, 684));
		setBackground(Color.BLACK);
		setLayout(null);
		
		lbladd = new JLabel();
		lbladd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				afficheAddFond = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				afficheAddFond = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				arreter();
				for(SelecNiveauListener ecout : OBJETS_ENREGISTRES.getListeners(SelecNiveauListener.class) ) {
					ecout.creationNiveau();
				}
			}
		});
		lbladd.setBounds(860,50,125,125);
		add(lbladd);
		
		lblload = new JLabel();
		lblload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				afficheLoadFond = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				afficheLoadFond = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				for(SelecNiveauListener ecout : OBJETS_ENREGISTRES.getListeners(SelecNiveauListener.class) ) {
					ecout.load();
				}
			}
		});
		lblload.setBounds(860,245,125,125);
		add(lblload);
		
		lblMenu = new JLabel();
		lblMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				afficheMenuPFond = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				afficheMenuPFond = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				arreter();
				for(SelecNiveauListener ecout : OBJETS_ENREGISTRES.getListeners(SelecNiveauListener.class) ) {
					ecout.returnMenu();
				}
			}
		});
		lblMenu.setBounds(860,425,125,125);
		add(lblMenu);
		cibles = new ArrayList<Cible>();
		demarrer();
	}
	/**
	 * Méthdoe sert à créer des quatre icons de niveau dans le sélecteurNiveau
	 */
	private void creerRectangle(){
		listeRect = new ArrayList<Rectangle2D.Double>();
		BufferedImage img = listeImageNiveau.get(0);
		listeRect.add(new Rectangle2D.Double(100,50,img.getWidth(),img.getHeight()));
		lblNiveauUn = new JLabel();
		lblNiveauUn.setFont(new Font("Tahoma", Font.BOLD, 50));
		lblNiveauUn.setHorizontalAlignment(SwingConstants.CENTER);
		lblNiveauUn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNiveauUn.setText("Jouer");
				afficheUn = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNiveauUn.setText("");
				afficheUn = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				arreter();
				for(SelecNiveauListener ecout : OBJETS_ENREGISTRES.getListeners(SelecNiveauListener.class) ) {
					ecout.selectionNiveau(0);
				}
			}
		});
		lblNiveauUn.setForeground(Color.ORANGE);
		lblNiveauUn.setBounds(100,50,img.getWidth(),img.getHeight());
		add(lblNiveauUn);
		
		img = listeImageNiveau.get(1);
		listeRect.add(new Rectangle2D.Double(450,50,img.getWidth(),img.getHeight()));
		labelNiveauDeux = new JLabel();
		labelNiveauDeux.setFont(new Font("Tahoma", Font.BOLD, 50));
		labelNiveauDeux.setHorizontalAlignment(SwingConstants.CENTER);
		labelNiveauDeux.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				labelNiveauDeux.setText("Jouer");
				afficheDeux = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				labelNiveauDeux.setText("");
				afficheDeux = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				arreter();
				for(SelecNiveauListener ecout : OBJETS_ENREGISTRES.getListeners(SelecNiveauListener.class) ) {
					ecout.selectionNiveau(1);
				}
			}
		});
		labelNiveauDeux.setForeground(Color.ORANGE);
		labelNiveauDeux.setBounds(450,50,img.getWidth(),img.getHeight());
		add(labelNiveauDeux);
		
		img = listeImageNiveau.get(2);
		listeRect.add(new Rectangle2D.Double(100,325,img.getWidth(),img.getHeight()));
		labelNiveauTrois = new JLabel();
		labelNiveauTrois.setFont(new Font("Tahoma", Font.BOLD, 50));
		labelNiveauTrois.setHorizontalAlignment(SwingConstants.CENTER);
		labelNiveauTrois.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				labelNiveauTrois.setText("Jouer");
				afficheTrois = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				labelNiveauTrois.setText("");
				afficheTrois = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				arreter();
				for(SelecNiveauListener ecout : OBJETS_ENREGISTRES.getListeners(SelecNiveauListener.class) ) {
					ecout.selectionNiveau(2);
				}
			}
		});
		labelNiveauTrois.setForeground(Color.ORANGE);
		labelNiveauTrois.setBounds(100,325,img.getWidth(),img.getHeight());
		add(labelNiveauTrois);
		
		img = listeImageNiveau.get(3);
		listeRect.add(new Rectangle2D.Double(450,325,img.getWidth(),img.getHeight()));
		labelNiveauQuatre = new JLabel();
		labelNiveauQuatre.setFont(new Font("Tahoma", Font.BOLD, 50));
		labelNiveauQuatre.setHorizontalAlignment(SwingConstants.CENTER);
		labelNiveauQuatre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				labelNiveauQuatre.setText("Jouer");
				afficheQuatre = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				labelNiveauQuatre.setText("");
				afficheQuatre = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				arreter();
				for(SelecNiveauListener ecout : OBJETS_ENREGISTRES.getListeners(SelecNiveauListener.class) ) {
					ecout.selectionNiveau(3);
				}
			}
		});
		labelNiveauQuatre.setForeground(Color.ORANGE);
		labelNiveauQuatre.setBackground(Color.WHITE);
		labelNiveauQuatre.setBounds(450,325,img.getWidth(),img.getHeight());
		add(labelNiveauQuatre);
	}
	/**
	 * Méthode sert à loading les images 
	 */
	private void chercherImage(){
		listeImageNiveau = new ArrayList<BufferedImage>();
		URL urlNiveau1 = getClass().getClassLoader().getResource("NIVEAU1final.png");
		URL urlNiveau2 = getClass().getClassLoader().getResource("NIVEAU2final.png");
		URL urlNiveau3 = getClass().getClassLoader().getResource("NIVEAU3final.png");
		URL urlNiveau4 = getClass().getClassLoader().getResource("NIVEAU4final.png");
		URL urlSave = getClass().getClassLoader().getResource("loadFin.gif");
		URL urlAdd = getClass().getClassLoader().getResource("addFin.gif");
		URL urlReturn = getClass().getClassLoader().getResource("homeFIn.gif");
		URL urlBackground = getClass().getClassLoader().getResource("ocean.jpg");
		try{
			listeImageNiveau.add(ImageIO.read(urlNiveau1));
			listeImageNiveau.add(ImageIO.read(urlNiveau2));
			listeImageNiveau.add(ImageIO.read(urlNiveau3));
			listeImageNiveau.add(ImageIO.read(urlNiveau4));
			loadImg= ImageIO.read(urlSave);
			addImg = ImageIO.read(urlAdd);
			returnImg = ImageIO.read(urlReturn);
			background = ImageIO.read(urlBackground);
		}catch (Exception e) {
			//System.out.println("Erreur de lecture du fichier de texture");
		}
	}
	/**
	 * Cette méthode permet de dessiner le sélecteur de niveau.
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(background,0,0,getWidth(),getHeight(),null);
		//cible
		AffineTransform matALEntree = g2d.getTransform();
		genererCiblesAleatoires();
		if(lasteCible == false){
			styleChangement();
			for (int k=0; k<x-1; k++) {
				cibles.get(k).dessiner(g2d);
			}
			cibles.get(x).dessiner(g2d);
		}else{  
			for (int k=0; k<NB_CIBLES; k++) { 
				g2d.translate(a, a);
				cibles.get(k).dessiner(g2d);
			}
			g2d.setTransform(matALEntree);
		}
        g2d.setColor(new Color(255,255,0,150));
		if(afficheAddFond){ 
			g2d.fill(addFond);
		}
		if(afficheLoadFond ){ 
			g2d.fill(loadFond);
		}
		if(afficheMenuPFond ){ 
			g2d.fill(menuFond);
		}
		
		for(int nbImg =0; nbImg < listeImageNiveau.size(); nbImg++){
			TexturePaint img = new TexturePaint(listeImageNiveau.get(nbImg),listeRect.get(nbImg));
			g2d.setPaint(img);
			g2d.fill(listeRect.get(nbImg));
		}

		TexturePaint load = new TexturePaint(loadImg,loadRect);
		g2d.setPaint(load);
		g2d.fill(loadRect);

		TexturePaint add = new TexturePaint(addImg,addRect);
		g2d.setPaint(add);
		g2d.fill(addRect);

		TexturePaint retour = new TexturePaint(returnImg,returnRect);
		g2d.setPaint(retour);
		g2d.fill(returnRect);

		//Color couleurTxt= new Color(73,193,201);
		for(int i=0; i < 2 ; i++){
			if(i ==0){
				g2d.setFont(new Font("Stencil", Font.BOLD, 30));
				g2d.setColor(Color.black);
			}else{
				g2d.setFont(new Font("Stencil", Font.BOLD, 28));
				g2d.setColor(Color.LIGHT_GRAY);
			}
			g2d.drawString("Niveau 1", 160 + i, 260);
			g2d.drawString("Niveau 2", 510 + i, 260);
			g2d.drawString("Niveau 3", 160 + i, 535);
			g2d.drawString("Niveau 4", 510 + i, 535);
			g2d.drawString("Création du niveau", 800 + 5*i , 181);
			g2d.drawString("Charger un niveau", 800 + 5*i, 367);
			g2d.drawString("Menu principale", 800 + 5*i, 540);
		}
		g2d.setColor(Color.YELLOW);
		if(afficheUn){ 
			g2d.drawString("Niveau 1", 160 + 1, 260);
		}
		if(afficheDeux){ 
			g2d.drawString("Niveau 2", 510 + 1, 260);
		}
		if(afficheTrois){ 
			g2d.drawString("Niveau 3", 160 + 1, 535);
		}
		if(afficheQuatre){ 
			g2d.drawString("Niveau 4", 510 + 1, 535);
		}
		if(afficheAddFond){ 
			g2d.drawString("Création du niveau", 800 + 5*1 , 181);
		}
		if(afficheLoadFond ){ 
			g2d.drawString("Charger un niveau", 800 + 5*1, 367);
		}
		if(afficheMenuPFond ){ 
			g2d.drawString("Menu principale", 800 + 5*1, 540);
		}
	}
	@Override
	/**
	 * Méthode runnable par défaut
	 */
	public void run() {
		while(encoursAnimation){
			//pour cible
			if(animer){	
				a= a-INCREMENT;	
			}	
			if(a*(-1)>= 250){
				a= 80;	
			}
			if(lasteCible == false){
				x++;
			}
			if(x >= NB_CIBLES){
				x=0;
				lasteCible = true;
				animer = true ;
			}
			repaint();
			try{
				Thread.sleep(100);
				i += 50;
			}
			catch (InterruptedException e) {
				//System.out.println("Processus interrompu!"); 
			}
		}
	}
	/**
	 * Méthode crée des objets cibles dans l'animation du arrière plan
	 */
	private void genererCiblesAleatoires() {
		Cible uneCible;
		Random gen = new Random();
		for (int k=0; k<NB_CIBLES; k++) {
			uneCible = new Cible(	gen.nextInt(getWidth()),	gen.nextInt(getHeight()),
					MIN_DIAMETRE+gen.nextInt(1+MAX_DIAMETRE-MIN_DIAMETRE),	MIN_ANNEAUX+gen.nextInt(1+MAX_ANNEAUX-MIN_ANNEAUX));
			cibles.add(uneCible); //ajouter la cible a la collection
		} //fin for
	}//fin methode
	/**
	 * Méthode commencer l'animation 
	 */
	public void demarrer() {
		porc = new  Thread(this);
		porc.start();
		encoursAnimation = true;
	}//fin methode
	/**
	 * Méthode arrêter animation
	 */
	public void arreter() {
		encoursAnimation = false;
	}//fin methode
	/**
	 * méthode initialiser pour l'animation du cible
	 */
	public void recommencer(){
		cibles = new ArrayList<Cible>();
		lasteCible = false;
		a = 0;
		animer = false;
		encoursAnimation = false;
		i=0;
		x=0;
		demarrer();
	}
	/**
	 * méthode sert à changer le comportement de cible
	 */
	public void styleChangement(){
		double a = Math.random()*100;
		if(a >= 65){
			cibles.get(x).setStyle(style2);
		}else{
			cibles.get(x).setStyle(style);
		}
	}
	/**
	 * méthode sert à ajouter selectNiveauListener dans SelecteurNiveau
	 * @param objEcouteur - par défaut
	 */
	public void addSelecNiveauListener(SelecNiveauListener objEcouteur) {
		OBJETS_ENREGISTRES.add(SelecNiveauListener.class,objEcouteur);

	}
}
