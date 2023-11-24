package sceneanimation;


import ecouteur.SelecNiveauListener;
import geometrie.BallonChasseur;
import geometrie.ChampMagnetique;
import geometrie.Particules;
import geometrie.Plaque;
import geometrie.Ressort;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import physique.Vector2d;

import javax.swing.JInternalFrame;

import java.awt.ComponentOrientation;

/**
 * Classe d'EditeurNiveau du jeu de coccinelle, elle sert à l'utilisateur à créer ses propres niveaux avec les objets du paquetage geometrie
 * @author Zizheng Wang contribution de Kevin Takla(champs et plaques)
 * @version 3.0
 */
public class EditeurNiveau extends JPanel {
	private JScrollBar scrollBar;
	private AffineTransform matMC;
	private final double DIAM_BALLE = 3;
	private double pixelsParUniteX;
	private double pixelsParUniteY;
	private double niveauX = 25;
	private double niveauY = 10;
	private double pasInitial = 0;
	private double pas = 0;
	private boolean premierFois = true;
	private double niveauPortionLargeur = 0.125; // une portion de lageurMondeRéel dans le niveau normal du jeu coccinelle (100/800)
	private double largeurMonde;
	private JButton btnOKPlaque;
	private double portion = -2;
	private JMenuItem mntmNewMenuItem;
	private JMenu mnNewMenu;
	private JMenuItem mntmSauveParChemin;
	private JMenuBar menuBar;
	private JMenuBar menuBarScience;
	private JMenu menu;
	private JMenuItem menuItem;
	private JMenuItem menuItem_1;
	private JMenuBar menuBarLire;
	private JMenu menu_1;
	private JMenuItem menuItem_2;
	private JMenuItem mntmLoader;
	private Rectangle2D.Double niveau;
	private JPanel BbortureGauche;
	private JTabbedPane tabbedPane;
	private JPanel panel_backGround;
	private JPanel panel_2;
	private JPanel panel_geometrie;
	private JPanel panel_2_2;
	private JPanel panel_2_1;
	private JPanel panel_ressort;
	private JPanel panel_3_1;
	private JMenuBar menuFondColor;
	private JMenu mnNewMenu_1;
	private JMenuBar menuBarRessort;
	private JMenuBar menuParticule;
	private JMenu mnNewMenu_2;
	private JMenu mnNewMenu_3;
	private JMenuBar menuIconImage;
	private JMenu mnNewMenu_4;
	private JMenu mnNewMenu_4_2;
	private JMenu mnNewMenu_4_1;
	private int largeurTab;
	private int largeurScroll = 17;
	private JScrollBar scrollBar_1;
	private double pasHor;
	private double pasInitialHor;
	private JButton btnNewButton;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private double largeurCharge = 5;
	private boolean dessinerRessorts = false;
	private boolean dessinerParticules[] = {false, false, false};
	private ArrayList<Particules> positive;
	private ArrayList<Particules> negative;
	private ArrayList<Particules> neutre;
	private ArrayList<Particules> chargeMemoire;
	private ArrayList<Ressort> ressortSet;
	private ArrayList<Ressort> ressortMemoire;
	private ArrayList<BallonChasseur> ballonSet;
	private ArrayList<BallonChasseur> ballonMemoire;
	private ArrayList<BallonChasseur> cercleSet;
	private ArrayList<BallonChasseur> cercleMemoire;
	private ArrayList<Plaque> plaqueSet;
	private ArrayList<Plaque> plaqueMemoire;
	private ArrayList<ChampMagnetique> champsSet;
	private ArrayList<ChampMagnetique> champsMemoire;
	private int xPrecedent;
	private int yPrecedent;
	private boolean balleSelectionnee = false;
	private boolean troisCharges[] = {false, false, false};
	private boolean ressortSelectionne = false;
	private int selectionIndexRessort = 0;
	private int selectionIndexCharge[] = {0, 0, 0};
	private int posXRessort;
	private int posYRessort;
	private int posXBalle;
	private int posYBalle;
	private Vector2d nouvellePosition;
	private JButton buttonSetRessort;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnInvisible;
	private JLabel labelAfficheBoussole;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JMenuBar menuTrois;
	private JSlider sliderLongeur;
	private JSlider slider_angle;
	private JButton btnNewButton_Oui;
	private JPanel panelParaRessort;
	private JLabel lblRotation;
	private JMenu menuElectrique;
	private JMenu menuMagnetique;
	private JButton btnPlaque;
	private JButton btnChamps;
	private double rotationRessort = 0;
	private JButton btnNewButtonBallon;
	private int cptClicPlaque= 0;
	private JButton btnCercle;
	private JScrollBar scrollBar_2;
	private ArrayList<JLabel> labelImage;
	private ArrayList<BufferedImage> bufferImage;
	private JColorChooser colorChooser;
	private Color c = Color.LIGHT_GRAY;
	private TexturePaint texture;
	private BufferedImage imgTexture = null;
	private boolean setTexture = false;
	private Vector2d nul = new Vector2d(0,0);
	private boolean dessinerBallon = false;
	private boolean dessinerCercle = false;
	private int selectionIndexBallon;
	private int selectionIndexCercle;
	private int posXBallon;
	private int posYBallon;
	private int posXCercle;
	private int posYCercle;
	private boolean ballonSelectionne = false;
	private boolean cercleSelectionne = false;
	private JPanel panelAfficheInfo;
	private JEditorPane editorPane;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private boolean listingDesComposants = false;
	private boolean afficherGrille = false;
	private Shape paysage;
	private int echelle;
	private JPanel panelControlBallon;
	private JPanel panelControlCercle;
	private JPanel panelChamps;
	private JPanel pnlSettingChamps;
	private JMenuBar champsMagnetique;
	private JMenuBar champsElectrique;
	private JSlider slider;
	private JSpinner spinner;
	private JLabel lblNewLabel_1;
	private JLabel label;
	private JButton btnOokay;
	private JSpinner spinner_1;
	private JLabel label_1;
	private JButton button;
	private String fichierNom = "niveauPop.txt";
	private JSpinner spinChampsMagnetique;
	private JPanel pnlSettingPlaque;
	private JLabel champBoussole;
	private boolean afficherChampBoussole = false;
	private JPanel panel_4_1;
	private JMenuBar menuQuatre;
	private JButton btnCoccinelle;
	private JButton btnFosse;
	private JFileChooser fileChooser;
	private JMenuItem mntmSauveParDefaut;
	private boolean dessinerFosse = false;
	private boolean dessinerCoccinelle = false;
	private boolean dessinerPlaque = false;
	private boolean dessinerChamps = false;
	private double posXFosse = 50;
	private double posYFosse = 50;
	private double posXCoc = 80;
	private double posYCoc = 40;
	private Shape cocc;
	private Shape trou;
	private boolean trouSelectionnee;
	private boolean coccSelectionnee;
	private Shape lig;
	private JSpinner spinChampsElectrique;
	private JMenuItem mntmProjetNouveau;
	private JPanel pnlChampsElectrique;
	private JPanel pnlChampsMagnetique;
	private JMenuItem menuItem_3;
    private int compteurLocal;
	private boolean dejaSauve = false;
	private int indexImage = 0;
	private String tableauChaine[] = {"IconUn.jpg","IconYellow.jpg","IconGreen.jpg","IconInverse.jpg","IconMauve.jpg","IconCosmos.jpg","champSombre.jpg","leavesYellow.jpg","leavesGreen.jpg","leavesInverse.jpg","champMauve.jpg","champCosmos.jpg","chargeNégative.png","chargeNeutre.png","chargePositive.png","ressortImage.PNG","ballonChasseure.PNG","cercle.png","bouChamp.png","coccinelleIcon.PNG","arret.PNG","imageIconPlaque.png","iconChampsMagnetique.png"};
	private boolean selectionImage = false;
	private String stringImage = "IconUn.jpg";
	private int coccDrawSize = 0;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JSlider sldLongueurPlaque;
	private JSlider sldLongueurChamps;
	private JButton btnOKChamps;
	private JSlider sldLargeurChamps;
	private boolean selectionPlaque;
	private double posXPlaque;
	private double posYPlaque;
	private int selectionIndexPlaque;
	private boolean selectionChamps;
	private double posXChamps;
	private double posYChamps;
	private int selectionIndexChamps;
	private boolean oneTime = true;
	private int cptChamps =0;
	private double echelleG = 0;
	private JInternalFrame internalFrame;
	private JTabbedPane tabbedPane_1;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JEditorPane TextTutoriel;
	private JEditorPane TextTutoriel2;
	private JMenuBar menuBar_1;
	private JButton btnNewButton_1;
	private JButton btnNewButton_4;
	private JScrollPane scrollPane_3;
	private JEditorPane TextTutoriel3;
	private BufferedImage background;
	private ArrayList<String> collectionNom;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList();
	/**
	 * Constructeur du EditeurNiveau du jeu coccinelle
	 */
	public EditeurNiveau() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				if (trouSelectionnee) {
					posXFosse += (arg0.getX() - xPrecedent)/pixelsParUniteX ;
					posYFosse += (arg0.getY() - yPrecedent)/pixelsParUniteY ;
					xPrecedent = arg0.getX() ;
					yPrecedent = arg0.getY() ;
					repaint();
				}
				if (coccSelectionnee) {
					posXCoc += (arg0.getX() - xPrecedent)/pixelsParUniteX ;
					posYCoc += (arg0.getY() - yPrecedent)/pixelsParUniteY ;
					xPrecedent = arg0.getX() ;
					yPrecedent = arg0.getY() ;
					repaint();
				}
				if(ballonSelectionne){
					posXBallon += arg0.getX() - xPrecedent; 
					posYBallon += arg0.getY() - yPrecedent;
					nouvellePosition = new Vector2d(posXBallon/pixelsParUniteX, posYBallon/pixelsParUniteY);
					ballonSet.get(selectionIndexBallon).setPosition(nouvellePosition);
					xPrecedent = arg0.getX() ;
					yPrecedent = arg0.getY() ;
					repaint();
				}
				if(cercleSelectionne){
					posXCercle += arg0.getX() - xPrecedent; 
					posYCercle += arg0.getY() - yPrecedent;
					nouvellePosition = new Vector2d(posXCercle/pixelsParUniteX, posYCercle/pixelsParUniteY);
					cercleSet.get(selectionIndexCercle).setPosition(nouvellePosition);
					xPrecedent = arg0.getX() ;
					yPrecedent = arg0.getY() ;
					repaint();
				}
				if(ressortSelectionne){
					posXRessort += arg0.getX() - xPrecedent; 
					posYRessort += arg0.getY() - yPrecedent;
					nouvellePosition = new Vector2d(posXRessort/pixelsParUniteX, posYRessort/pixelsParUniteY);
					ressortSet.get(selectionIndexRessort).setPosition(nouvellePosition);
					xPrecedent = arg0.getX() ;
					yPrecedent = arg0.getY() ;
					repaint();
				}
				if (balleSelectionnee) {
					posXBalle += arg0.getX() - xPrecedent; 
					posYBalle += arg0.getY() - yPrecedent;
					nouvellePosition = new Vector2d(posXBalle/pixelsParUniteX, posYBalle/pixelsParUniteY);
					if(troisCharges[0]){
						positive.get(selectionIndexCharge[0]).setPosition(nouvellePosition);
					}else if (troisCharges[1]){
						negative.get(selectionIndexCharge[1]).setPosition(nouvellePosition);
					}else if (troisCharges[2]){
						neutre.get(selectionIndexCharge[2]).setPosition(nouvellePosition);
					}
					xPrecedent = arg0.getX() ;
					yPrecedent = arg0.getY() ;
					repaint();
				}
				if(selectionPlaque){
					posXPlaque += arg0.getX() - xPrecedent;
					posYPlaque += arg0.getY() - yPrecedent;
					nouvellePosition = new Vector2d(posXPlaque/pixelsParUniteX, posYPlaque/pixelsParUniteY);
					plaqueSet.get(selectionIndexPlaque).setPosition(nouvellePosition);
					xPrecedent = arg0.getX() ;
					yPrecedent = arg0.getY() ;
					repaint();
				}
				if(selectionChamps){
					posXChamps += arg0.getX() - xPrecedent;
					posYChamps += arg0.getY() - yPrecedent;
					nouvellePosition = new Vector2d(posXChamps/pixelsParUniteX, posYChamps/pixelsParUniteY);
					champsSet.get(selectionIndexChamps).setPosition(nouvellePosition);
					xPrecedent = arg0.getX() ;
					yPrecedent = arg0.getY() ;
					repaint();
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(dessinerFosse){
					if (trou.contains(e.getX(), e.getY())){
						trouSelectionnee = true;
						xPrecedent = e.getX() ;
						yPrecedent = e.getY() ;
					}
				}
				if(dessinerCoccinelle){
					if (cocc.contains(e.getX(), e.getY())){
						coccSelectionnee = true;
						xPrecedent = e.getX() ;
						yPrecedent = e.getY() ;
					}
				}
				for(int i = 0; i < ballonSet.size(); i++){
					if(ballonSet.get(i).getShape().contains(e.getX(), e.getY()) ){
						selectionIndexBallon = i;
						posXBallon = (int) (ballonSet.get(i).getShape().getBounds2D().getX() + ballonSet.get(i).getShape().getBounds2D().getWidth()/2 +pas*pixelsParUniteX/2);
						posYBallon = (int) (ballonSet.get(i).getShape().getBounds2D().getY() + ballonSet.get(i).getShape().getBounds2D().getHeight()/2 +pasHor*pixelsParUniteY/2);
						ballonSelectionne = true;
						xPrecedent = e.getX();
						yPrecedent = e.getY();
						panelControlBallon.setVisible(true);
					}
				}
				for(int i = 0; i < cercleSet.size(); i++){
					if(cercleSet.get(i).getShape().contains(e.getX(), e.getY()) ){
						selectionIndexCercle = i;
						posXCercle = (int) (cercleSet.get(i).getShape().getBounds2D().getX() + cercleSet.get(i).getShape().getBounds2D().getWidth()/2 +pas*pixelsParUniteX/2);
						posYCercle = (int) (cercleSet.get(i).getShape().getBounds2D().getY() + cercleSet.get(i).getShape().getBounds2D().getHeight()/2 +pasHor*pixelsParUniteY/2);
						cercleSelectionne = true;
						xPrecedent = e.getX();
						yPrecedent = e.getY();
						panelControlCercle.setVisible(true);
					}
				}
				for(int t = 0; t < ressortSet.size(); t++){
					if(ressortSet.get(t).getShapeBloc().contains(e.getX(), e.getY()) ){
						selectionIndexRessort = t;
						posXRessort = (int) (ressortSet.get(t).getShapeBloc().getBounds2D().getX() + ressortSet.get(t).getShapeBloc().getBounds2D().getWidth()/2 +pas*pixelsParUniteX/2 - 450);
						posYRessort = (int) (ressortSet.get(t).getShapeBloc().getBounds2D().getY() + ressortSet.get(t).getShapeBloc().getBounds2D().getHeight()/2 +pasHor*pixelsParUniteY/2);
						ressortSelectionne = true;
						xPrecedent = e.getX();
						yPrecedent = e.getY();
						panelParaRessort.setVisible(true);
					}
				}
				for(int k = 0; k < positive.size(); k++){
					if(positive.get(k).getShape().contains(e.getX(), e.getY()) ){
						troisCharges[0] = true;
						selectionIndexCharge[0] = k;
						posXBalle = (int) (positive.get(k).getShape().getBounds2D().getX() + positive.get(k).getShape().getBounds2D().getWidth()/2 +pas*pixelsParUniteX/2);
						posYBalle = (int) (positive.get(k).getShape().getBounds2D().getY() + positive.get(k).getShape().getBounds2D().getHeight()/2 +pasHor*pixelsParUniteY/2);
						balleSelectionnee = true;
						xPrecedent = e.getX();
						yPrecedent = e.getY();

					}
				}
				for(int a = 0; a < negative.size(); a++){
					if (negative.get(a).getShape().contains(e.getX(), e.getY()) ){
						troisCharges[1] = true;
						selectionIndexCharge[1] = a;
						posXBalle = (int) (negative.get(a).getShape().getBounds2D().getX() + negative.get(a).getShape().getBounds2D().getWidth()/2 +pas*pixelsParUniteX/2);
						posYBalle = (int) (negative.get(a).getShape().getBounds2D().getY() + negative.get(a).getShape().getBounds2D().getHeight()/2 +pasHor*pixelsParUniteY/2);
						balleSelectionnee = true;
						xPrecedent = e.getX();
						yPrecedent = e.getY();
					}
				}
				for(int b = 0; b < neutre.size(); b++){
					if (neutre.get(b).getShape().contains(e.getX(), e.getY()) ){
						troisCharges[2] = true;
						selectionIndexCharge[2] = b;
						posXBalle = (int) (neutre.get(b).getShape().getBounds2D().getX() + neutre.get(b).getShape().getBounds2D().getWidth()/2 +pas*pixelsParUniteX/2);
						posYBalle = (int) (neutre.get(b).getShape().getBounds2D().getY() + neutre.get(b).getShape().getBounds2D().getHeight()/2 +pasHor*pixelsParUniteY/2);
						balleSelectionnee = true;
						xPrecedent = e.getX();
						yPrecedent = e.getY();
					}
				}
				for(int l =0; l<plaqueSet.size(); l++){

					if(plaqueSet.get(l).getShape().contains(e.getX(), e.getY())){
						selectionIndexPlaque = l;
						posXPlaque = (int)(plaqueSet.get(l).getShape().getBounds2D().getX()   + pas*pixelsParUniteX/2);
						posYPlaque = (int)(plaqueSet.get(l).getShape().getBounds2D().getY()   + pasHor*pixelsParUniteY/2);
						selectionPlaque = true;
						xPrecedent = e.getX();
						yPrecedent = e.getY();
					}
				}
				for(int q=0; q<champsSet.size();q++){
					if(champsSet.get(q).getShapeTransfo().contains(e.getX(), e.getY())){
						selectionIndexChamps = q;
						posXChamps = (int)(champsSet.get(q).getShapeTransfo().getBounds2D().getX()  + pas*pixelsParUniteX/2);
						posYChamps = (int)(champsSet.get(q).getShapeTransfo().getBounds2D().getY() + pasHor*pixelsParUniteY/2);
						selectionChamps = true;
						xPrecedent = e.getX();
						yPrecedent = e.getY();
					}
				}

			} 
			@Override
			public void mouseReleased(MouseEvent e) {
				balleSelectionnee = false;
				troisCharges[0] = false;
				troisCharges[1] = false;
				troisCharges[2] = false;
				ressortSelectionne = false;
				ballonSelectionne = false;
				cercleSelectionne = false;
				trouSelectionnee = false;
				coccSelectionnee = false;
				selectionPlaque = false;
				selectionChamps = false;
				repaint();
			}
		});
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(990, 735));
		setLayout(null);
		creerNiveau();
		lireLesTextures();
		ressortSet = new ArrayList<Ressort>();
		positive = new ArrayList<Particules>();
		negative = new ArrayList<Particules>();
		neutre = new ArrayList<Particules>();
		ballonSet = new ArrayList<BallonChasseur>();
		cercleSet = new ArrayList<BallonChasseur>();
	    plaqueSet = new ArrayList<Plaque>();
		champsSet = new ArrayList<ChampMagnetique>();
		collectionNom = new ArrayList<String>();
		
		
		scrollBar = new JScrollBar();
		scrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				pas = scrollBar.getValue();
				double increment = (pas - pasInitial)/portion;
				matMC.translate(increment, 0); 
				repaint();
				//System.out.println(increment );
				//System.err.println(pas);
				pasInitial = pas;
			}
		});
		scrollBar.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar.setBounds(120, 735 - largeurScroll, 870 - largeurScroll, largeurScroll);
		add(scrollBar);
		
		scrollBar_1 = new JScrollBar();
		scrollBar_1.addAdjustmentListener(new AdjustmentListener() {
             public void adjustmentValueChanged(AdjustmentEvent arg0) {
				pasHor = scrollBar_1.getValue();
				double incrementHor = (pasHor - pasInitialHor)/portion;
				matMC.translate(0, incrementHor);
				repaint();
				//System.out.println(incrementHor);
				//System.err.println(pasHor);
				pasInitialHor = pasHor;
			}
		});
		scrollBar_1.setBounds(990 - largeurScroll, 0, largeurScroll, 735 - largeurScroll);
		add(scrollBar_1);
		
		
		menuBar = new JMenuBar();
		menuBar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		menuBar.setForeground(Color.RED);
		menuBar.setBackground(new Color(0,0,0,0));
		menuBar.setBounds(880 - largeurScroll, 0, 110, 25);
		add(menuBar);
		
		mnNewMenu = new JMenu(" Fichier ");
		mnNewMenu.setBackground(Color.GRAY);
		mnNewMenu.setForeground(Color.LIGHT_GRAY);
		mnNewMenu.setAlignmentX(Component.LEFT_ALIGNMENT);
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenu("Information du niveau");
		panelAfficheInfo = new JPanel();
		panelAfficheInfo.setPreferredSize(new Dimension(300,400));
		mntmNewMenuItem.add(panelAfficheInfo);
		panelAfficheInfo.setLayout(null);
		
		editorPane = new JEditorPane();
		editorPane.setForeground(Color.LIGHT_GRAY);
		editorPane.setBackground(Color.DARK_GRAY);
		editorPane.setEditable(false);
		editorPane.setBounds(15, 40, 255, 330);
		
		scrollPane = new JScrollPane(editorPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(15, 40, 270, 330);
		panelAfficheInfo.add(scrollPane);
		
		lblNewLabel = new JLabel("Liste des objets graphiques :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(20, 15, 300, 15);
		panelAfficheInfo.add(lblNewLabel);
		mnNewMenu.add(mntmNewMenuItem);
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				listingDesComposants = true;
				getNiveauInformation(listingDesComposants);
				String colorSet = new String();
				String imageSet = new String();
				String coChargePositive = " Toutes les charges positives :\n";
				String coChargeNegative = " Toutes les charges négatives :\n";
				String coChargeNeutre = " Toutes les charges neutres :\n";
				String coRessort = " Tous les ressorts :\n";
				String coBallon = " Tous les ballons :\n";
				String coCercle = " Tous les cercles :\n";
				String coPlaque = " Tous les Plaques :\n";
				String coChamps = " Tous les champs :\n";
				if(setTexture){
					imageSet = " L'image du fond est : \n " + tableauChaine[indexImage] + "\n" ;
				}else{
					colorSet = " La couleur du fond est : \n" + c + "\n" ;
					imageSet = " Il n'y a pas d'image au fond \n" ;
				}
				for(int n = 0; n < chargeMemoire.size(); n++){
					if(chargeMemoire.get(n).getCharge() > 0){
						coChargePositive = coChargePositive + chargeMemoire.get(n).getPosition().toString()+ "\n";
					}else if (chargeMemoire.get(n).getCharge() < 0){
						coChargeNegative = coChargeNegative + chargeMemoire.get(n).getPosition().toString()+ "\n";
					}else if (chargeMemoire.get(n).getCharge() == 0){
						coChargeNeutre = coChargeNeutre + chargeMemoire.get(n).getPosition().toString()+ "\n";
					}
				}
				for(int a = 0; a < ressortMemoire.size(); a++){
					coRessort = coRessort + ressortMemoire.get(a).getPosition().toString()+ "\n" +" sa longeur : "+ ressortMemoire.get(a).getLongeurMax() + "\n" + " son angle : " + ressortMemoire.get(a).getRotation() + "\n";
				}
				for(int c = 0; c < ballonMemoire.size(); c++){
					coBallon = coBallon + ballonMemoire.get(c).getPosition().toString()+ "\n";
				}
				for(int v = 0; v < cercleMemoire.size(); v++){
					coCercle = coCercle + cercleMemoire.get(v).getPosition().toString()+ "\n";
				}
				for(int p=0; p< plaqueMemoire.size(); p++){
					coPlaque = coPlaque + plaqueMemoire.get(p).getPosition().toString() + "\n";
				}
				for(int ch=0; ch< champsMemoire.size(); ch++){
					coChamps = coChamps + champsMemoire.get(ch).getPosition().toString() + "\n";
				}
				editorPane.setText(colorSet + imageSet + coChargePositive + coChargeNegative + coChargeNeutre + coRessort + coBallon + coCercle);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				listingDesComposants = false;
			}
		});
		
		mntmSauveParChemin = new JMenuItem("Sauvegarder la création");
		mntmSauveParChemin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sauvegarder();
				dejaSauve = true;
			}
		});
		mnNewMenu.add(mntmSauveParChemin);
		
		mntmSauveParDefaut = new JMenuItem("Sauvegarder par défaut");
		mntmSauveParDefaut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listingDesComposants = true;
				String input = JOptionPane.showInputDialog(null,"le nom pour votre cération :", "niveauPop");
				if(input == null){
					//System.out.println("Calcel presed");
				}else if(!(input.equals(""))){
					//System.out.println("la quantite est : " +chargeMemoire.size() + ressortMemoire.size() + ballonMemoire.size() + cercleMemoire.size());
					ecrireText(input);
					fichierNom = input;
					EnregistrerNom(input);
				}else{
					JOptionPane.showMessageDialog(null,"nom invalide");
				}
				dejaSauve = true;
			}
		});
		mnNewMenu.add(mntmSauveParDefaut);
		
		mntmProjetNouveau = new JMenuItem("Nouveau projet");
		mntmProjetNouveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!dejaSauve){
					sauvegarder();
					dejaSauve = false;
				}
				initialiser();
			}
		});
		mnNewMenu.add(mntmProjetNouveau);
		
		menuBarScience = new JMenuBar();
		menuBarScience.setForeground(Color.BLUE);
		menuBarScience.setBackground(new Color(0,0,0,0));
		menuBarScience.setBounds(880 - largeurScroll, 28, 110, 25);
		add(menuBarScience);
		
		menu = new JMenu("Scientifique");
		menu.setForeground(Color.LIGHT_GRAY);
		menu.setBackground(Color.GRAY);
		menu.setAlignmentX(0.0f);
		menuBarScience.add(menu);
		
		menuItem = new JMenuItem("Afficher le grillage");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!afficherGrille){
					afficherGrille = true;
					menuItem.setText("mode normal");
					
				}else{
					afficherGrille = false;
					menuItem.setText("afficher la grille");
				}
				repaint();
			}
		});
		menu.add(menuItem);
		
		menuItem_1 = new JMenuItem("Tutoriel");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				internalFrame.setVisible(true);
			}
		});
		menu.add(menuItem_1);
		
		menuBarLire = new JMenuBar();
		menuBarLire.setForeground(Color.BLUE);
		menuBarLire.setBackground(new Color(0,0,0,0));
		menuBarLire.setBounds(880 - largeurScroll, 56, 110, 25);
		add(menuBarLire);
		
		menu_1 = new JMenu(" Lecture ");
		menu_1.setForeground(Color.LIGHT_GRAY);
		menu_1.setBackground(Color.GRAY);
		menu_1.setAlignmentX(0.0f);
		menuBarLire.add(menu_1);
		
		menuItem_2 = new JMenuItem("Lire par défaut");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fichierNom != null){
					lireText(fichierNom);	
				}else{
					JOptionPane.showMessageDialog(null,"unKnow source");
				}
				getNiveauInformation(listingDesComposants);
			}
		});
		
		mntmLoader = new JMenuItem("Lire la création");
		mntmLoader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setVisible(true);
				File file = null;
				int result;
				result = fileChooser.showOpenDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					lireText(file);
				} else if (result == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null,"Unknow source");
				}
				getNiveauInformation(listingDesComposants);
			}
		});
		menu_1.add(mntmLoader);
		menu_1.add(menuItem_2);
		
		menuItem_3 = new JMenuItem("Nouveau projet");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!dejaSauve){
					sauvegarder();
					dejaSauve = false;
				}
				initialiser();
			}
		});
		menu_1.add(menuItem_3);
		
		BbortureGauche = new JPanel();
		BbortureGauche.setBackground(Color.LIGHT_GRAY);
		BbortureGauche.setBounds(0, 0, 120, 735);
		add(BbortureGauche);
		BbortureGauche.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(Color.DARK_GRAY);
		tabbedPane.setBackground(Color.LIGHT_GRAY);
		tabbedPane.setBounds(0, 0, 120, 735);
		BbortureGauche.add(tabbedPane);
		
		panel_backGround = new JPanel();
		panel_backGround.setForeground(Color.LIGHT_GRAY);
		panel_backGround.setBackground(Color.GRAY);
		tabbedPane.addTab("Menu fond niveau", null, panel_backGround, null);
		panel_backGround.setLayout(null);
		
		menuFondColor = new JMenuBar();
		menuFondColor.setForeground(Color.LIGHT_GRAY);
		menuFondColor.setBackground(Color.GRAY);
		menuFondColor.setBounds(0, 0, 190, 42);
		panel_backGround.add(menuFondColor);
		
		mnNewMenu_1 = new JMenu("   Couleur simple                              ");
		mnNewMenu_1.setForeground(Color.WHITE);
		menuFondColor.add(mnNewMenu_1);
		
		colorChooser = new JColorChooser();
		colorChooser.getSelectionModel().addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setTexture = false;
				//System.out.println("Votre choix "+colorChooser.getColor().toString());
				c = colorChooser.getColor();
				internalFrame.getContentPane().setBackground(c); 
		        btnNewButton_4.setBackground(c);
		        tabbedPane_1.setBackground(c);
				indexImage = 0;
				repaint();
			}
		});
		mnNewMenu_1.add(colorChooser);
		
		menuIconImage = new JMenuBar();
		menuIconImage.setForeground(Color.LIGHT_GRAY);
		menuIconImage.setBackground(Color.GRAY);
		menuIconImage.setBounds(0, 42, 190, 42);
		panel_backGround.add(menuIconImage);
		
		mnNewMenu_4 = new JMenu("       Image                           ");
		mnNewMenu_4.setForeground(Color.WHITE);
		menuIconImage.add(mnNewMenu_4);
		
		panel_2 = new JPanel();
        panel_2.setPreferredSize(new Dimension(300,600));
		mnNewMenu_4.add(panel_2);
		panel_2.setLayout(null);
		creerCaptureImage();
		scrollBar_2 = new JScrollBar();
		scrollBar_2.addAdjustmentListener(new AdjustmentListener() {
			double incre = 0, increIni = 0, increment = 0;
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				incre = scrollBar_2.getValue();
				increment = -10*(incre - increIni) +increment;
				increIni = incre;
				//System.out.println(increment);
				for (int k=0; k<6; k++) {
					labelImage.get(k).setBounds(10, (int) (65 +k*(203 + 25) + increment), 270, 203);
				}
			}
		});
		scrollBar_2.setBounds(285, 0, 15, 600);
		panel_2.add(scrollBar_2);
		
		panel_ressort = new JPanel();
		panel_ressort.setForeground(Color.LIGHT_GRAY);
		panel_ressort.setBackground(Color.GRAY);
		tabbedPane.addTab("Menu ressort", null, panel_ressort, null);
		panel_ressort.setLayout(null);
		
		menuBarRessort = new JMenuBar();
		menuBarRessort.setForeground(Color.LIGHT_GRAY);
		menuBarRessort.setBackground(Color.GRAY);
		menuBarRessort.setBounds(0, 0, 160, 42);
		panel_ressort.add(menuBarRessort);
		
		mnNewMenu_2 = new JMenu("     Ressort                           ");
		mnNewMenu_2.setForeground(Color.WHITE);
		menuBarRessort.add(mnNewMenu_2);
		panel_2_2 = new JPanel();
		panel_2_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel_2_2.setPreferredSize(new Dimension(300,600));
		mnNewMenu_2.add(panel_2_2);
		panel_2_2.setLayout(null);
		
		ImageIcon imgThisIconRessort = new ImageIcon(bufferImage.get(15));
		buttonSetRessort = new JButton("Ressort");
		buttonSetRessort.setFocusable(false);
		buttonSetRessort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compteurLocal++;
				Ressort ressort = new Ressort(20 + compteurLocal*5,40 + compteurLocal*10,80);
				ressortSet.add(ressort);
				selectionIndexRessort = ressortSet.indexOf(ressort);
				dessinerRessorts = true;
				panelParaRessort.setVisible(true);
				repaint();
				if(compteurLocal == 3){
					buttonSetRessort.setEnabled(false);
				}
				
			}
		});
		buttonSetRessort.setFont(new Font("Tahoma", Font.BOLD, 16));
		buttonSetRessort.setIcon(imgThisIconRessort);
		buttonSetRessort.setBounds(10, 12, 280, 100);
		panel_2_2.add(buttonSetRessort);
		
		panelParaRessort = new JPanel();
		panelParaRessort.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelParaRessort.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Longeur :", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(64, 64, 64)));
		panelParaRessort.setBackground(Color.LIGHT_GRAY);
		panelParaRessort.setBounds(5, 160, 290, 185);
		panelParaRessort.setVisible(false);
		panel_2_2.add(panelParaRessort);
		panelParaRessort.setLayout(null);
		
		sliderLongeur = new JSlider();
		sliderLongeur.setBackground(Color.LIGHT_GRAY);
		sliderLongeur.setMaximum(150);
		sliderLongeur.setValue(30);
		sliderLongeur.setMajorTickSpacing(15);
		sliderLongeur.setSnapToTicks(true);
		sliderLongeur.setPaintTicks(true);
		sliderLongeur.setPaintLabels(true);
		sliderLongeur.setBounds(0, 15, 280, 45);
		panelParaRessort.add(sliderLongeur);
		
		slider_angle = new JSlider();
		slider_angle.setFont(new Font("Tahoma", Font.PLAIN, 8));
		slider_angle.setBackground(Color.LIGHT_GRAY);
		slider_angle.setMaximum(360);
		slider_angle.setValue(0);
		slider_angle.setMajorTickSpacing(20);
		slider_angle.setSnapToTicks(true);
		slider_angle.setPaintTicks(true);
		slider_angle.setPaintLabels(true);
		slider_angle.setBounds(0, 90, 280, 45);
		panelParaRessort.add(slider_angle);
		
		btnNewButton_Oui = new JButton("Oui");
		btnNewButton_Oui.setFocusable(false);
		btnNewButton_Oui.setBackground(Color.LIGHT_GRAY);
		btnNewButton_Oui.setBounds(100, 150, 89, 30);
		panelParaRessort.add(btnNewButton_Oui);
		
		lblRotation = new JLabel("rotation Angle :");
		lblRotation.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRotation.setBounds(10, 65, 100, 25);
		panelParaRessort.add(lblRotation);
		btnNewButton_Oui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelParaRessort.setVisible(false);
			}
		});
		slider_angle.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//System.out.println(slider_angle.getValue());
				ressortSet.get(selectionIndexRessort).setRotation(slider_angle.getValue());
				repaint();
			}
		});
		sliderLongeur.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				ressortSet.get(selectionIndexRessort).setLongeurMax(sliderLongeur.getValue() + 85);
				repaint();
			}
		});
		panel_geometrie = new JPanel();
		panel_geometrie.setForeground(Color.LIGHT_GRAY);
		panel_geometrie.setBackground(Color.GRAY);
		tabbedPane.addTab("Menu géométrie", null, panel_geometrie, null);
		panel_geometrie.setLayout(null);
		
		menuTrois = new JMenuBar();
		menuTrois.setForeground(Color.LIGHT_GRAY);
		menuTrois.setBackground(Color.GRAY);
		menuTrois.setBounds(0, 0, 190, 21);
		panel_geometrie.add(menuTrois);
		
		mnNewMenu_3 = new JMenu("     Ballon                             ");
		mnNewMenu_3.setForeground(Color.WHITE);
		menuTrois.add(mnNewMenu_3);
		panel_3_1 = new JPanel();
        panel_3_1.setPreferredSize(new Dimension(300,600));
		mnNewMenu_3.add(panel_3_1);
		panel_3_1.setLayout(null);
		
		menuQuatre = new JMenuBar();
		menuQuatre.setForeground(Color.LIGHT_GRAY);
		menuQuatre.setBackground(Color.GRAY);
		menuQuatre.setBounds(0, 41, 200, 45);
		panel_geometrie.add(menuQuatre);
		
		mnNewMenu_4_2 = new JMenu("     Coccinelle                               ");
		mnNewMenu_4_2.setForeground(Color.WHITE);
		menuQuatre.add(mnNewMenu_4_2);
		panel_4_1 = new JPanel();
        panel_4_1.setPreferredSize(new Dimension(300,600));
        mnNewMenu_4_2.add(panel_4_1);
        panel_4_1.setLayout(null);
       
        ImageIcon imgThisIconCocc = new ImageIcon(bufferImage.get(19));
        btnCoccinelle = new JButton("New button");
        btnCoccinelle.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dessinerCoccinelle = true;
        		dessinerFosse = true;
        		repaint();
        	}        });
        btnCoccinelle.setBounds(30, 42, 75, 75);
        btnCoccinelle.setIcon(imgThisIconCocc);
        panel_4_1.add(btnCoccinelle);
        
        ImageIcon imgThisIconArret = new ImageIcon(bufferImage.get(20));
        btnFosse = new JButton("Fin");
        btnFosse.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		dessinerCoccinelle = true;
        		dessinerFosse = true;
        		repaint();
        	}
        });
        btnFosse.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnFosse.setBounds(30, 317, 75, 75);
        btnFosse.setIcon(imgThisIconArret);
        panel_4_1.add(btnFosse);
        
		panelControlBallon = new JPanel();
		panelControlBallon.setBackground(Color.LIGHT_GRAY);
		panelControlBallon.setBounds(30, 120, 250, 150);
		panel_3_1.add(panelControlBallon);
		panelControlBallon.setLayout(null);
		panelControlBallon.setVisible(false);
		
		ImageIcon imgThisIconBallon = new ImageIcon(bufferImage.get(16));
		btnNewButtonBallon = new JButton("Ballon");
		btnNewButtonBallon.setToolTipText("Voici une ballon");
		btnNewButtonBallon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("ballon");
				Vector2d vec = new Vector2d(100*Math.random() +20,100*Math.random());
				BallonChasseur b = new BallonChasseur(vec,10,5,0,nul);
				ballonSet.add(b);
				selectionIndexBallon = ballonSet.indexOf(b);
				dessinerBallon = true;
				panelControlBallon.setVisible(true);
				repaint();
			}
		});
		btnNewButtonBallon.setFocusable(false);
		btnNewButtonBallon.setIcon(imgThisIconBallon);
		btnNewButtonBallon.setBounds(30, 42, 75, 75);
		panel_3_1.add(btnNewButtonBallon);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//System.out.println(slider.getValue());
			}
		});
		slider.setBounds(25, 35, 200, 45);
		slider.setBackground(Color.LIGHT_GRAY);
		slider.setMaximum(60);
		slider.setValue(15);
		slider.setMajorTickSpacing(5);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		panelControlBallon.add(slider);
		
		spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//System.out.println(spinner.getValue());
			}
		});
		spinner.setModel(new SpinnerNumberModel(0, 0, 360, 1));
		spinner.setBounds(76, 95, 70, 35);
		panelControlBallon.add(spinner);
		
		lblNewLabel_1 = new JLabel("La vitesse :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(25, 10, 100, 20);
		panelControlBallon.add(lblNewLabel_1);
		
		label = new JLabel("L'angle :");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(25, 100, 100, 20);
		panelControlBallon.add(label);
		
		btnOokay = new JButton("Oui");
		btnOokay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double angle = (int) spinner.getValue();
				Vector2d vitesse = new Vector2d(slider.getValue()*Math.cos(Math.toRadians(angle)),slider.getValue()*Math.sin(Math.toRadians(angle)));
				ballonSet.get(selectionIndexBallon).setVitesse(vitesse);
				//System.out.println(vitesse);
				//System.out.println("ballon selection index"+selectionIndexBallon);
				panelControlBallon.setVisible(false);
			}
		});
		btnOokay.setBounds(160, 95, 65, 35);
		panelControlBallon.add(btnOokay);
		
		label_2 = new JLabel("°");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(146, 100, 46, 14);
		panelControlBallon.add(label_2);
		
		label_3 = new JLabel("m/s");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_3.setBounds(174, 10, 46, 14);
		panelControlBallon.add(label_3);
		
		ImageIcon imgThisIconCercle = new ImageIcon(bufferImage.get(17));
		btnCercle = new JButton("Cercle");
		btnCercle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("cercle");
				Vector2d vec = new Vector2d(100*Math.random() +20,100*Math.random());
				BallonChasseur c = new BallonChasseur(vec,10,10,0,nul);
				cercleSet.add(c);
				selectionIndexCercle = cercleSet.indexOf(c);
				dessinerCercle = true;
				panelControlCercle.setVisible(true);
				repaint();
				
			}
		});
		btnCercle.setFocusable(false);
		btnCercle.setIcon(imgThisIconCercle);
		btnCercle.setBounds(30, 317, 75, 75);
		panel_3_1.add(btnCercle);
		
		panelControlCercle = new JPanel();
		panelControlCercle.setBackground(Color.LIGHT_GRAY);
		panelControlCercle.setBounds(30, 395, 250, 120);
		panel_3_1.add(panelControlCercle);
		panelControlCercle.setLayout(null);
		panelControlCercle.setVisible(false);
		
		spinner_1 = new JSpinner();
		spinner_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//System.out.println(spinner_1.getValue());
				//cercleSet.get((int) (compteur - 1)).setVitesse(spinner_1.getValue());
				repaint();
			}
		});
		spinner_1.setModel(new SpinnerNumberModel(0, -360, 360, 1));
		spinner_1.setBounds(25, 45, 70, 35);
		panelControlCercle.add(spinner_1);
		
		label_1 = new JLabel("la vitesse angulaire :");
		label_1.setBounds(25, 25, 200, 20);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelControlCercle.add(label_1);
		
		button = new JButton("Oui");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double vitesseAngulaire = (int) spinner_1.getValue();
				cercleSet.get(selectionIndexCercle).setAngleRotation(Math.toRadians(vitesseAngulaire));
				cercleSet.get(selectionIndexCercle).setStockAngle(Math.toRadians(vitesseAngulaire));
				//System.out.println(vitesseAngulaire);
				//System.out.println("cercle selection index"+selectionIndexCercle);
				panelControlCercle.setVisible(false);
			}
		});
		button.setBounds(160, 45, 65, 35);
		panelControlCercle.add(button);
		
		label_4 = new JLabel("°/deltaS");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_4.setBounds(95, 55, 65, 14);
		panelControlCercle.add(label_4);
		
		
		
		panelChamps = new JPanel();
		panelChamps.setForeground(Color.LIGHT_GRAY);
		panelChamps.setBackground(Color.GRAY);
		tabbedPane.addTab("   Menu Champs       ", null, panelChamps, null);
		panelChamps.setLayout(null);
		
		menuParticule = new JMenuBar();
		menuParticule.setForeground(Color.LIGHT_GRAY);
		menuParticule.setBackground(Color.GRAY);
		menuParticule.setBounds(0, 0, 200, 42);
		panelChamps.add(menuParticule);
		
		mnNewMenu_4_1 = new JMenu("     Particules                           ");
		mnNewMenu_4_1.setForeground(Color.WHITE);
		menuParticule.add(mnNewMenu_4_1);
		
		panel_2_1 = new JPanel();
        panel_2_1.setPreferredSize(new Dimension(300,600));
		mnNewMenu_4_1.add(panel_2_1);
		panel_2_1.setLayout(null);
		
		ImageIcon imgThisImgPositive = new ImageIcon(bufferImage.get(14));
		btnNewButton = new JButton("positive");
		btnNewButton.setFocusable(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("positive");
				Particules c = new Particules(100*Math.random() +20,100*Math.random());
				c.setCharge(1.6*Math.pow(10,-19));
				positive.add(c);
				dessinerParticules[0] = true;
				repaint();
			}
		});
		btnNewButton.setIcon(imgThisImgPositive);
		btnNewButton.setBounds(10, 35, 75, 75);
		panel_2_1.add(btnNewButton);
		
		ImageIcon imgThisIconNegative = new ImageIcon(bufferImage.get(12));
		btnNewButton_2 = new JButton("négative");
		btnNewButton_2.setFocusable(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("negative");
				Particules c = new Particules(100*Math.random() +20,100*Math.random());
				negative.add(c);
				dessinerParticules[1] = true;
				repaint();
			}
		});
		btnNewButton_2.setIcon(imgThisIconNegative);
		btnNewButton_2.setBounds(110, 35, 75, 75);
		panel_2_1.add(btnNewButton_2);
		
		JLabel lblPositif = new JLabel();
		lblPositif.setText("proton");
		lblPositif.setBounds(30, 20, 120, 12);
		panel_2_1.add(lblPositif);

        JLabel lblnegatif = new JLabel();
        lblnegatif.setText("électron");
        lblnegatif.setBounds(125, 20, 120, 12);
        panel_2_1.add(lblnegatif);

        JLabel lblneutre = new JLabel();
        lblneutre.setText("neutron");
        lblneutre.setBounds(225, 20, 120, 12);
        panel_2_1.add(lblneutre);
		
		ImageIcon imgThisIconNeutre = new ImageIcon(bufferImage.get(13));
		btnNewButton_3 = new JButton("neutre");
		btnNewButton_3.setFocusable(false);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("neutre");
				Particules c = new Particules(100*Math.random() +20,100*Math.random());
				c.setCharge(0);
				neutre.add(c);
				dessinerParticules[2] = true;
				repaint();
			}
		});
		btnNewButton_3.setIcon(imgThisIconNeutre);
		btnNewButton_3.setBounds(210, 35, 75, 75);
		panel_2_1.add(btnNewButton_3);
		
		rdbtnNewRadioButton = new JRadioButton("visible");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherChampBoussole = true;
				champBoussole.setVisible(true);
			}
		});
		rdbtnNewRadioButton.setFocusable(false);
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdbtnNewRadioButton.setBounds(25, 148, 109, 23);
		panel_2_1.add(rdbtnNewRadioButton);
		
		rdbtnInvisible = new JRadioButton("invisible");
		rdbtnInvisible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				afficherChampBoussole = false;
				champBoussole.setVisible(false);
			}
		});
		rdbtnInvisible.setFocusable(false);
		rdbtnInvisible.setSelected(true);
		buttonGroup.add(rdbtnInvisible);
		rdbtnInvisible.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdbtnInvisible.setBounds(150, 148, 109, 23);
		panel_2_1.add(rdbtnInvisible);
		
		labelAfficheBoussole = new JLabel("Effet boussole :");
		labelAfficheBoussole.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelAfficheBoussole.setBounds(15, 115, 130, 38);
		panel_2_1.add(labelAfficheBoussole);
		
		ImageIcon imgThisIconBouChamp = new ImageIcon(bufferImage.get(18));
		champBoussole = new JLabel("New label");
		champBoussole.setIcon(imgThisIconBouChamp);
		champBoussole.setBounds(15, 180, 270, 203);
		panel_2_1.add(champBoussole);
		champBoussole.setVisible(false);
		        
		
		champsElectrique = new JMenuBar();
		champsElectrique.setForeground(Color.LIGHT_GRAY);
		champsElectrique.setBackground(Color.GRAY);
		champsElectrique .setBounds(0, 0, 200, 42);
		panelChamps.add(champsElectrique);
		
		menuElectrique = new JMenu("     Electrique                 ");
		menuElectrique.setForeground(Color.WHITE);
		champsElectrique.add(menuElectrique);
		pnlChampsElectrique =  new JPanel();
		pnlChampsElectrique.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlChampsElectrique.setPreferredSize(new Dimension(300,400));
		menuElectrique.add(pnlChampsElectrique);
		pnlChampsElectrique.setLayout(null);
		
		pnlSettingPlaque = new JPanel();
	    pnlChampsElectrique.add(pnlSettingPlaque);
	    pnlSettingPlaque.setBorder(new TitledBorder(null, "Paramètres", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	    pnlSettingPlaque.setBounds(30, 117, 250, 200);
	    pnlSettingPlaque.setLayout(null);
	    pnlSettingPlaque.setVisible(false);
	    
	    JLabel lblTxtChampsElectrique = new JLabel("Champs électrique: ");
	    lblTxtChampsElectrique.setBounds(15, 25, 120, 12);
	    lblTxtChampsElectrique.setFont(new Font("Tahoma", Font.PLAIN, 12));
	    pnlSettingPlaque.add(lblTxtChampsElectrique);
	    
	    spinChampsElectrique = new JSpinner();
	    spinChampsElectrique.setBounds(133, 23, 56, 20);
	    pnlSettingPlaque.add(spinChampsElectrique);
		spinChampsElectrique.setModel(new SpinnerNumberModel(0.03, -0.1, 0.1, 0.01));
		
		JLabel lblTxtNC = new JLabel(" N/C");
		lblTxtNC.setBounds(191, 25, 50, 12);
		lblTxtNC.setFont(new Font("Tahoma", Font.PLAIN, 12));
	    pnlSettingPlaque.add(lblTxtNC);
		
	    sldLongueurPlaque = new JSlider();
	    sldLongueurPlaque.setBounds(15, 47, 175,100);
	    sldLongueurPlaque.setMaximum(50);
	    sldLongueurPlaque.setValue(25);
	    sldLongueurPlaque.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Longueur de la plaque :", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(64, 64, 64)));
	    sldLongueurPlaque.setPaintTicks(true);
	    sldLongueurPlaque.setPaintLabels(true);
	    sldLongueurPlaque.setMajorTickSpacing(10);
	    sldLongueurPlaque.setPaintTrack(true);
	    pnlSettingPlaque.add(sldLongueurPlaque);
	    
	    btnOKPlaque = new JButton("OK");
	    btnOKPlaque.setBounds(175, 150, 65, 45);
	    pnlSettingPlaque.add(btnOKPlaque);
	    btnOKPlaque.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Plaque plq = new Plaque(100*Math.random() +20,100*Math.random(),(int)sldLongueurPlaque.getValue(),(double)(spinChampsElectrique.getValue())*Math.pow(10,-9));
        		plaqueSet.add(plq);
        		dessinerPlaque = true;
        		repaint();
        	}        });
	    
		ImageIcon imgPlaque = new ImageIcon(bufferImage.get(21));
        btnPlaque = new JButton("New button");
        btnPlaque.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		pnlSettingPlaque.setVisible(cptClicPlaque%2 == 0);
        		cptClicPlaque++;
        	}        });
        btnPlaque.setBounds(30, 42, 75, 75);
        btnPlaque.setIcon(imgPlaque);
        pnlChampsElectrique.add(btnPlaque);
        
        JLabel buttonIndicElec = new JLabel();
        buttonIndicElec.setText("Plaque PPIUC");
        buttonIndicElec.setBounds(30, 120, 100, 12);
        pnlChampsElectrique.add(buttonIndicElec);
		
		champsMagnetique = new JMenuBar();
		champsMagnetique.setForeground(Color.LIGHT_GRAY);
		champsMagnetique.setBackground(Color.GRAY);
		champsMagnetique .setBounds(0, 84, 200, 42);
		panelChamps.add(champsMagnetique);
		
		menuMagnetique = new JMenu("     Magnétique                     ");
		menuMagnetique.setForeground(Color.WHITE);
		champsMagnetique.add(menuMagnetique);
		
		pnlChampsMagnetique =  new JPanel();
		pnlChampsMagnetique.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlChampsMagnetique.setPreferredSize(new Dimension(300,400));
		menuMagnetique .add(pnlChampsMagnetique);
		pnlChampsMagnetique.setLayout(null);
		
		pnlSettingChamps = new JPanel();
		pnlChampsMagnetique.add(pnlSettingChamps);
		pnlSettingChamps.setBorder(new TitledBorder(null, "Paramètres", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlSettingChamps.setBounds(30, 117, 250, 250);
		pnlSettingChamps.setLayout(null);
		pnlSettingChamps.setVisible(false);
		
		JLabel lblTxtChampsMagnetique = new JLabel("    Champs magnétique:                     ");
		lblTxtChampsMagnetique.setBounds(15, 23, 120, 20);
		lblTxtChampsMagnetique.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlSettingChamps.add(lblTxtChampsMagnetique);
	    
	    spinChampsMagnetique = new JSpinner();
	    spinChampsMagnetique.setBounds(133, 23, 56, 20);
	    pnlSettingChamps.add(spinChampsMagnetique);
	    spinChampsMagnetique.setModel(new SpinnerNumberModel(0.03, -0.1, 0.1, 0.01));
	    
	    JLabel lblTxtT = new JLabel(" nT");
	    lblTxtT.setBounds(191, 25, 50, 12);
	    lblTxtT.setFont(new Font("Tahoma", Font.PLAIN, 12));
	    pnlSettingChamps.add(lblTxtT);
	    
	    sldLongueurChamps = new JSlider();
	    sldLongueurChamps.setBounds(15, 47, 175,75);
	    sldLongueurChamps.setMaximum(25);
	    sldLongueurChamps.setValue(10);
	    sldLongueurChamps.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Longueur :", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(64, 64, 64)));
	    sldLongueurChamps.setPaintTicks(true);
	    sldLongueurChamps.setPaintLabels(true);
	    sldLongueurChamps.setMajorTickSpacing(5);
	    sldLongueurChamps.setPaintTrack(true);
	    pnlSettingChamps.add( sldLongueurChamps);
	    
	    sldLargeurChamps = new JSlider();
	    sldLargeurChamps.setBounds(15, 125, 175,75);
	    sldLargeurChamps.setMaximum(25);
	    sldLargeurChamps.setValue(10);
	    sldLargeurChamps.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Largeur :", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(64, 64, 64)));
	    sldLargeurChamps.setPaintTicks(true);
	    sldLargeurChamps.setPaintLabels(true);
	    sldLargeurChamps.setMajorTickSpacing(5);
	    sldLargeurChamps.setPaintTrack(true);
	    pnlSettingChamps.add(sldLargeurChamps);
	    
	    btnOKChamps = new JButton("OK");
	    btnOKChamps.setBounds(175, 200, 65, 45);
	    pnlSettingChamps.add(btnOKChamps);
	    btnOKChamps.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ChampMagnetique champs = new ChampMagnetique(100*Math.random() +20,100*Math.random(),(double)sldLongueurChamps.getValue(),(double)sldLargeurChamps.getValue(),(double)(spinChampsMagnetique.getValue())*Math.pow(10, -9));
        		champsSet.add(champs);
        		dessinerChamps= true;
        		repaint();
        	}        });
		
		ImageIcon imgChamps = new ImageIcon(bufferImage.get(22));
        btnChamps = new JButton("New button");
        btnChamps.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		pnlSettingChamps.setVisible(cptChamps%2 == 0);
        		cptChamps++;
        	}        });
        btnChamps.setBounds(30, 42, 75, 75);
        btnChamps.setIcon(imgChamps);
        pnlChampsMagnetique.add(btnChamps);
        
        JLabel buttonIndicMagn = new JLabel();
        buttonIndicMagn.setText("Champs Magnétique");
        buttonIndicMagn.setBounds(15, 120, 120, 12);
        pnlChampsMagnetique.add(buttonIndicMagn);

        internalFrame = new JInternalFrame("Tutoriel pour niveauEditeur");
        internalFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
        internalFrame.setBounds(656, 112, 310, 425);
        add(internalFrame);
        internalFrame.getContentPane().setLayout(null);
        
        tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane_1.setFocusable(false);
        tabbedPane_1.setForeground(Color.WHITE);
        tabbedPane_1.setBackground(Color.LIGHT_GRAY);
        tabbedPane_1.setBounds(2, 0, 294, 360);
        internalFrame.getContentPane().add(tabbedPane_1);
        
		TextTutoriel = new JEditorPane();
		TextTutoriel.setText("Comment jouer?\r\n\r\n\r\nL’éditeur de niveau est fournit pour que\r\nvous puisse créer votre propre niveau\r\navec les objets physiques selon votre\r\ngoût.\r\n\r\nAu sein d’éditeur, un plan vierge du\r\nniveau est fourni. Vous pouvez modifier\r\nsa couleur de fond et son image de fond\r\navec l’option Color et l’option Image\r\ndans la section de Menu du fond. \r\n\r\nMenu du fond :\r\n· Couleur simple\r\n· Image\r\n\r\nEnsuite, vous pouvez choisir les objets\r\ncomme obstacles pour les mettre dans\r\nle plan vierge. Les objets fournils sont :\r\nressort, particules de charge, ballons,\r\ncercles , PPIUC et champ magnétique.\r\nIls sont regroupés dans les différentes\r\nsections suivantes :\r\n\r\nMenu ressort :\r\n· Ressort\r\n\r\nMenu géométrie : \r\n· Ballon\r\n· Coccinelle\r\n (le rôle principal de notre jeu)\r\n\r\nMenu champs :\r\n· Électrique (PPIUC)\r\n· Magnétique\r\n· Particules\r\n\r\n*Attention, pour sauvegarder un objet,\r\nIl faut le mettre complétement dans le\r\nplan du niveau, sinon l'objet va être\r\nignorer par défaut.");
		TextTutoriel.setFont(new Font("Tahoma", Font.BOLD, 13));
		TextTutoriel.setForeground(Color.WHITE);
		TextTutoriel.setBackground(new Color(47, 79, 79));
		TextTutoriel.setEditable(false);
		
        scrollPane_1 = new JScrollPane(TextTutoriel);
        tabbedPane_1.addTab(" Instruction ", null, scrollPane_1, null);
        
        TextTutoriel2 = new JEditorPane();
		TextTutoriel2.setText("Vous pouvez placer vos propres champs\r\nélectriques et magnétiques dans votre \r\nniveau. Pour cela, allez dans la section\r\n« menu champs ».\r\n\r\nChamps électrique :\r\n\r\nDans cette section, vous pouvez créer\r\nune plaque PPIUC. Tout d’abord,\r\nappuyez sur le bouton créer plaque.\r\nEnsuite, vous pouvez arranger les\r\nparamètres suivants : la longueur et sa\r\ncharge. Enfin, appuyez sur le bouton\r\n« «Ok » afin de créer une plaque PPIUC.\r\n\r\nChamps magnétique : \r\n\r\nDans cet onglet, vous pouvez créer un\r\nchamp magnétique. Pour cela, veuillez,\r\ntout d’abord, appuyez sur le bouton \r\ncréer champ magnétique. Ensuite, vous\r\npouvez arranger les paramètres \r\nsuivants :la longueur, la largeur et son\r\nchamp magnétique. Enfin appuyez sur \r\nle bouton « ok » pour créer le champ\r\nmagnétique.\r\n\r\nParticules:\r\n\r\nVous pouvez également placer des\r\nparticules dans votre niveau. Pour cela\r\nappuyez sur le bouton particule dans \r\nle menu champs. Après, vous allez voir\r\ntrois boutons (protons, électron et\r\nneutron) appuyez sur l'un d'entre eux\r\npour créer une particule.\r\n");
		TextTutoriel2.setFont(new Font("Tahoma", Font.BOLD, 13));
		TextTutoriel2.setForeground(Color.WHITE);
		TextTutoriel2.setBackground(new Color(47, 79, 79));
		TextTutoriel2.setEditable(false);
		
        scrollPane_2 = new JScrollPane(TextTutoriel2);
        tabbedPane_1.addTab(" Les champs ", null, scrollPane_2, null);
        
        TextTutoriel3 = new JEditorPane();
		TextTutoriel3.setText("La section Géométrie :\r\n\r\nLa ballon :\r\nQuand vous créez un ballon dans\r\n le niveau éditeur, si vous voulez un\r\n ballon dynamique et non immobile,\r\n vous devez préciser sa vitesse et son \r\n orientation.\r\nLe cercle :\r\n Quand vous créez un cercle dans\r\n le niveau éditeur, si vous voulez un\r\n cercle dynamique et non immobile,\r\nIl vous faut préciser sa vitesse angulaire.\r\nIl se tourne autour du centre par défaut.");
		TextTutoriel3.setFont(new Font("Tahoma", Font.BOLD, 13));
		TextTutoriel3.setForeground(Color.WHITE);
		TextTutoriel3.setBackground(new Color(47, 79, 79));
		TextTutoriel3.setEditable(false);
        scrollPane_3 = new JScrollPane(TextTutoriel3);
        tabbedPane_1.addTab("Géométries", null, scrollPane_3, null);
        
        btnNewButton_4 = new JButton("Fermer");
        btnNewButton_4.setForeground(Color.WHITE);
        btnNewButton_4.setBackground(Color.LIGHT_GRAY);
        btnNewButton_4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		internalFrame.setVisible(false);
        	}
        });
        btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnNewButton_4.setBounds(75, 366, 140, 23);
        internalFrame.getContentPane().add(btnNewButton_4);
        internalFrame.setVisible(true);
		
		fileChooser = new JFileChooser();
		fileChooser.setBounds(0, 0, 580, 480);
		fileChooser.setVisible(false);
		
		panelAfficheInfo = new JPanel();
		panelAfficheInfo.setBounds(193, 84, 310, 335);
		
		composantRedimensionner();
	}
	/**
	 * Méthode sert à dessiner les composants graphiques dans l'ÉditeurNiveau du jeu coccinelle
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(background,0,0,getWidth(),getHeight(),null);
		composantRedimensionner();
		if(premierFois){
			largeurMonde = niveauPortionLargeur*getWidth();
			calculerMatriceMondeVersComposant(largeurMonde);
            //pour la grille
			echelle = (int)(5*(this.getWidth()/largeurMonde));
			//System.out.println("w et h : "+getWidth()+", "+getHeight() );
			premierFois = false;
		}
		afficherGrille(g2d, afficherGrille);
		paysage = matMC.createTransformedShape(niveau);
		g2d.setColor(c);
		if(setTexture){
			Rectangle2D rect = new Rectangle2D.Double(0 - (pas/2 - niveauX)*pixelsParUniteX, 0 - (pasHor/2 - niveauY)*pixelsParUniteY, imgTexture.getWidth(),imgTexture.getHeight());
			texture = new TexturePaint(imgTexture, rect);
			g2d.setPaint(texture);
		}
		g2d.fill(paysage);
		modeScientifique(g2d, paysage);
		g2d.setColor(Color.GREEN);
		g2d.setStroke(new BasicStroke(2));
		g2d.draw(paysage);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.gray);
		if(dessinerParticules[0]){
			for(int k = 0; k < positive.size(); k++){
				positive.get(k).dessiner(g2d,matMC,"positive");
			}
		}
		if(dessinerParticules[1]){
			for(int k = 0; k < negative.size(); k++){
				negative.get(k).dessiner(g2d,matMC,"négative");
			}
		}
		if(dessinerParticules[2]){
			for(int k = 0; k < neutre.size(); k++){
				neutre.get(k).dessiner(g2d,matMC,"neutre");
			}
		}
		if(dessinerRessorts){
			for(int k = 0; k < ressortSet.size(); k++){
				ressortSet.get(k).dessiner(g2d,matMC);
			}
		}
		if(dessinerBallon){
			for(int i = 0; i < ballonSet.size(); i++){
				ballonSet.get(i).dessiner(g2d,matMC,0,"");
			}
		}
		if(dessinerCercle){
			for(int j = 0; j < cercleSet.size(); j++){
				cercleSet.get(j).dessiner(g2d,matMC,0,100/2,75/2);
			}
		}
		g2d.setStroke(new BasicStroke(1));
		if(dessinerCoccinelle){
			Ellipse2D.Double coccinelle = new Ellipse2D.Double(posXCoc, posYCoc, DIAM_BALLE, DIAM_BALLE); 
			Line2D.Double ligne = new Line2D.Double(posXCoc ,posYCoc + DIAM_BALLE/2 ,posXCoc + DIAM_BALLE ,posYCoc + DIAM_BALLE/2);
			//Line2D.Double ligne = new Line2D.Double(posXCoc, posYCoc,posXCoc + DIAM_BALLE, posYCoc);
			cocc = matMC.createTransformedShape(coccinelle);
			lig = matMC.createTransformedShape(ligne);
			g2d.setColor(Color.RED);
			g2d.fill(cocc);
			g2d.setColor(Color.GREEN);
			g2d.draw(lig);
		}
		if(dessinerFosse){
			g2d.setColor(Color.DARK_GRAY);
			Ellipse2D.Double fosse = new Ellipse2D.Double(posXFosse, posYFosse, DIAM_BALLE, DIAM_BALLE);
			trou = matMC.createTransformedShape(fosse);
			g2d.fill(trou);
		}
		if(dessinerPlaque){
			for(int cptPlaque =0; cptPlaque < plaqueSet.size();  cptPlaque++){
				plaqueSet.get(cptPlaque).dessiner(g2d, matMC);
			}
		}
		
		if(dessinerChamps){
			for(int cptChamps =0; cptChamps< champsSet.size(); cptChamps++){
				champsSet.get(cptChamps).dessiner(g2d, matMC);
			}
		}
		
	}
	/**
	 * créer le plan de niveau
	 */
	private void creerNiveau(){
		niveau = new Rectangle2D.Double(niveauX,niveauY,100,75);
	}
	private void modeScientifique(Graphics2D g2d, Shape paysage2){
		double zeroX = 0 - (pas/2 - niveauX)*pixelsParUniteX;
		double zeroY = 0 - (pasHor/2 - niveauY)*pixelsParUniteY;
		double longeurX = 100*pixelsParUniteX;
		double largeurY = 75*pixelsParUniteY;
		if(oneTime){
			echelleG = 5*((zeroX + longeurX)/largeurMonde);
			oneTime = false;
		}
		if(afficherGrille){
			g2d.setColor(Color.GRAY);
			for(int a = 0; a <= largeurY  ; a++){
				g2d.draw(new Line2D.Double(zeroX ,(zeroY + largeurY)-a*echelleG,zeroX + 10 ,(zeroY + largeurY)-a*echelleG));
				for(int k = 0 ; k <= 4 ; k++){
					g2d.draw(new Line2D.Double(zeroX,(zeroY + largeurY)-a*echelleG-k*echelleG/5,zeroX + 3,(zeroY + largeurY)-a*echelleG-k*echelleG/5));
				}
				if(!(a ==0)){
					g2d.drawString(a*5+"",(int)zeroX + 10,(int)((zeroY + largeurY)-a*echelleG));
				}
			}
			for(int i = 0 ; i <= longeurX  ; i++){
				g2d.draw(new Line2D.Double(zeroX + i*echelleG,zeroY + largeurY - 10 ,zeroX + i*echelleG,zeroY + largeurY));
				for(int k = 0 ; k <= 4 ; k++){
					g2d.draw(new Line2D.Double(echelleG*i+k*echelleG/5-5, zeroY + largeurY,echelleG*i+k*echelleG/5-5, zeroY + largeurY-5));
				}
				if(!(i ==0)){
					g2d.drawString(i*5+"",(int)(zeroX + i*echelleG),(int)(zeroY + largeurY - 10 ));
				}
			}
		}else{
			g2d.setColor(Color.GREEN);
			g2d.setStroke(new BasicStroke(2));
			g2d.draw(new Line2D.Double(zeroX,zeroY + largeurY/2,zeroX + longeurX/9,zeroY + largeurY/2));
			g2d.draw(new Line2D.Double(zeroX + 8*longeurX/9,zeroY + largeurY/2,zeroX + longeurX, zeroY + largeurY/2));
			g2d.setStroke(new BasicStroke(1));
			g2d.draw(new Line2D.Double(zeroX + longeurX/2 - 10 ,zeroY + largeurY/2,zeroX + longeurX/2 + 10 , zeroY + largeurY/2));
			g2d.setStroke(new BasicStroke(2));
			g2d.draw(new Line2D.Double(zeroX + longeurX/2,zeroY,zeroX + longeurX/2,zeroY + largeurY/8));
			g2d.draw(new Line2D.Double(zeroX + longeurX/2,zeroY + 7*largeurY/8,zeroX + longeurX/2,zeroY + largeurY));
			g2d.setStroke(new BasicStroke(1));
			g2d.draw(new Line2D.Double(zeroX + longeurX/2,zeroY + largeurY/2 - 12 ,zeroX + longeurX/2,zeroY + largeurY/2 + 12));
		}
	}
	/**
	 * Méthode sert à calculer le ratio du unité du monde réel dans une matrice
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
	 * Méthode sert à replacer les composants graphics et tous les éléments lorsque l'utilisateur décide à modifier la grandeur du Jpanel
	 */
	private void composantRedimensionner(){
		if(this.getWidth()> 990 ){
			largeurTab = this.getWidth()*4/33 - 120;
			niveauX = 25 + this.getWidth()*0.006;
		}else{
			largeurTab = 0;
			niveauX = 25;
		}
		if(this.getHeight()> 735 ){
			niveauY = 10 + this.getHeight()*0.002;
		}else{
			niveauY = 10;
		}
		creerNiveau();
		BbortureGauche.setBounds(0, 0, 120 + largeurTab, this.getHeight());
		tabbedPane.setBounds(0, 0, 120 + largeurTab, this.getHeight());
		scrollBar.setBounds(120 + largeurTab, this.getHeight() - largeurScroll, this.getWidth() - 120 - largeurScroll - largeurTab, largeurScroll);
		scrollBar_1.setBounds(this.getWidth() - largeurScroll, 0, largeurScroll, this.getHeight() - largeurScroll);
		menuFondColor.setBounds(0, 0, 120 + largeurTab, 42);
		menuIconImage.setBounds(0, 42, 120 + largeurTab, 42);
		menuBarRessort.setBounds(0, 0, 120 + largeurTab, 42);
		menuParticule.setBounds(0, 42, 120 + largeurTab, 42);
		menuTrois.setBounds(0, 0, 120 + largeurTab, 42);
		
		menuBar.setBounds(this.getWidth()*8/9 - largeurScroll, 0, this.getWidth()/9, 25);
		menuBarScience.setBounds(this.getWidth()*8/9 - largeurScroll, 28, this.getWidth()/9, 25);
		menuBarLire.setBounds(this.getWidth()*8/9 - largeurScroll, 56, this.getWidth()/9, 25);
		
	}
	/**
	 * Créer des imageIcons dans le menu de sélection backGround image
	 */
	private void creerCaptureImage() {
		int distanceEntreDeux=25;
		int largeur = 270;
		int hauteur = 203;
		int posx=10, posy= distanceEntreDeux + 40;
		JLabel captureImage;
		labelImage = new ArrayList<JLabel>();
		EcouteurDeClic ecoutClic;
		ecoutClic = new EcouteurDeClic();
		for (int k=0; k<6; k++) {
			ImageIcon imgThisImg = new ImageIcon(bufferImage.get(k));
			captureImage = new JLabel( ""+k );
			captureImage.setBounds(posx, posy, largeur, hauteur); 	
			captureImage.setBackground(Color.white); //fond blanc
			captureImage.setName(k+""); //on associe un nom interne qui nous permettra d'identifier l'index de ce choix plus tard
			captureImage.setText("image" + k);
			captureImage.setIcon(imgThisImg);
			captureImage.addMouseListener( ecoutClic );
			labelImage.add(k,captureImage);
			panel_2.add(labelImage.get(k));	 
			posy = posy + distanceEntreDeux + hauteur;
		}
	}
	/**
	 * Méthode sert à implenter MonseListener aux Jlabels des imageIcons
	 * @author Zizheng Wang
	 *
	 */
	private class EcouteurDeClic implements MouseListener  {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			JLabel clic = (JLabel) e.getSource();  //quel bouton radio a recu le clic?
    		int indice = Integer.parseInt( clic.getName() ); // on retrace l'indice du choix par son nom interne
    		//System.out.println("allo" + indice);
    		indexImage = indice + 6;
    		setTexture = true;
    		imgTexture = bufferImage.get(indice + 6);
    		selectionImage = true;
    		repaint();
		}
		public void mouseEntered(MouseEvent arg0) {
		}
		public void mouseExited(MouseEvent arg0) {	
		}
		public void mousePressed(MouseEvent arg0) {
		}
		public void mouseReleased(MouseEvent arg0) {
		}
   	 }
	/**
	 * Méthode sert à lire tous les images dans le niveau
	 */
	private void lireLesTextures() {
		//lecture des imagesIcons qui servent de texture
		bufferImage = new ArrayList<BufferedImage>();
		URL url = getClass().getClassLoader().getResource(tableauChaine[0]);
		URL url1 = getClass().getClassLoader().getResource(tableauChaine[1]);
		URL url2 = getClass().getClassLoader().getResource(tableauChaine[2]);
		URL url3 = getClass().getClassLoader().getResource(tableauChaine[3]);
		URL url4 = getClass().getClassLoader().getResource(tableauChaine[4]);
		URL url5 = getClass().getClassLoader().getResource(tableauChaine[5]);
		//lecture des images qui servent de texture
		URL url6 = getClass().getClassLoader().getResource(tableauChaine[6]);
		URL url7 = getClass().getClassLoader().getResource(tableauChaine[7]);
		URL url8 = getClass().getClassLoader().getResource(tableauChaine[8]);
		URL url9 = getClass().getClassLoader().getResource(tableauChaine[9]);
		URL url10 = getClass().getClassLoader().getResource(tableauChaine[10]);
		URL url11 = getClass().getClassLoader().getResource(tableauChaine[11]);
		//lecture des charge
		URL url12 = getClass().getClassLoader().getResource(tableauChaine[12]);
		URL url13 = getClass().getClassLoader().getResource(tableauChaine[13]);
		URL url14 = getClass().getClassLoader().getResource(tableauChaine[14]);
		//lecture ressort
		URL url15 = getClass().getClassLoader().getResource(tableauChaine[15]);
		//lecture ballon et cercle
		URL url16 = getClass().getClassLoader().getResource(tableauChaine[16]);
		URL url17 = getClass().getClassLoader().getResource(tableauChaine[17]);
		URL url18 = getClass().getClassLoader().getResource(tableauChaine[18]);
		//lecture coccinelle
		URL url19 = getClass().getClassLoader().getResource(tableauChaine[19]);
		URL url20 = getClass().getClassLoader().getResource(tableauChaine[20]);
		
		URL url21 = getClass().getClassLoader().getResource(tableauChaine[21]);
		URL url22 = getClass().getClassLoader().getResource(tableauChaine[22]);
		//fond
		URL url23 =  getClass().getClassLoader().getResource("metal.jpg");
		try {
			bufferImage.add(0,ImageIO.read(url));
			bufferImage.add(1,ImageIO.read(url1));
			bufferImage.add(2,ImageIO.read(url2));
			bufferImage.add(3,ImageIO.read(url3));
			bufferImage.add(4,ImageIO.read(url4));
			bufferImage.add(5,ImageIO.read(url5));
			bufferImage.add(6,ImageIO.read(url6));
			bufferImage.add(7,ImageIO.read(url7));
			bufferImage.add(8,ImageIO.read(url8));
			bufferImage.add(9,ImageIO.read(url9));
			bufferImage.add(10,ImageIO.read(url10));
			bufferImage.add(11,ImageIO.read(url11));
			bufferImage.add(12,ImageIO.read(url12));
			bufferImage.add(13,ImageIO.read(url13));
			bufferImage.add(14,ImageIO.read(url14));
			bufferImage.add(15,ImageIO.read(url15));
			bufferImage.add(16,ImageIO.read(url16));
			bufferImage.add(17,ImageIO.read(url17));
			bufferImage.add(18,ImageIO.read(url18));
			bufferImage.add(19,ImageIO.read(url19));
			bufferImage.add(20,ImageIO.read(url20));
			bufferImage.add(21,ImageIO.read(url21));
			bufferImage.add(22,ImageIO.read(url22));
			background = ImageIO.read(url23);
			
		} catch (Exception e) {
			//System.out.println("Erreur de lecture du fichier de texture");
		}
		
	}
	/**
	 * Méthode sert à déterminer une collision entre deux shape
	 * @param forme1 - objet graphic un
	 * @param forme2 - objet graphic deux
	 * @return <b>true</b> il y a une collision
	 * <p><b>false</b> il n'y a pas de collision
	 */
	private boolean collision(Shape forme1, Shape forme2){
		boolean collision = false;
		Area a1 = new Area(forme1);
		Area a2 = new Area(forme2);
		Area inter = new Area(a1);
		inter.intersect(a2);
		if(!inter.isEmpty()){
			collision = true;
		}
		return collision;
	}
	/**
	 * Méthode sert à détecter les inclusions des objets créés dans le plan du niveau (L'Éditeur mémorise seulements des objets qui sont complétement dans le plan du niveau)
	 * @param forme1 - l'objet graphic un
	 * @param forme2 - l'objet graphic deux
     * @return <b>true</b> il y a un inclusion
	 * <p><b>false</b> il n'y a pas d'inclusion
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
	 * Méthode sert à vérrifier tous les inclusions des objets dans le plan du niveau
	 * @param listingDesComposants - une commande qui provient de l'extérieur
	 * <b>true</b> pour calculer
	 */
	private void getNiveauInformation(boolean listingDesComposants){
		chargeMemoire = new ArrayList<Particules>();
		ressortMemoire = new ArrayList<Ressort>();
		ballonMemoire = new ArrayList<BallonChasseur>();
		cercleMemoire = new ArrayList<BallonChasseur>();
		plaqueMemoire = new ArrayList<Plaque>();
		champsMemoire = new ArrayList<ChampMagnetique>();
		//System.err.println("NB PLAQUES ET CHAMPS = "+ plaqueSet.size() + " " + champsSet.size());
		if(listingDesComposants){
			//élections
			verificationInclusionParticules(positive);
			verificationInclusionParticules(negative);
			verificationInclusionParticules(neutre);
			for(int s = 0; s < ressortSet.size(); s++){
				if(inclut(paysage,ressortSet.get(s).getShapeBloc())){
					ressortMemoire.add(ressortSet.get(s));
				}
			}
			verificationInclusionBallon(ballonSet , ballonMemoire);
			verificationInclusionBallon(cercleSet , cercleMemoire);
			for(int p =0; p < plaqueSet.size();p++){
				if(inclut(paysage,plaqueSet.get(p).getShape())){
					plaqueMemoire.add(plaqueSet.get(p));
				}
			}
			for(int c =0; c < champsSet.size();c++){
				if(inclut(paysage,matMC.createTransformedShape(champsSet.get(c).getShape()))){
					champsMemoire.add(champsSet.get(c));
				}
			}
		}
	}
	/**
	 * Une méthode pour dessiner la grille sur le Jpanel
	 * @param g2d - Graphics2D par défaut
	 * @param afficherGrille - une commande qui provient de l'extérieur
	 * <b>true</b> pour afficher
	 */
	private void afficherGrille(Graphics g2d, boolean afficherGrille){
		int longeur = this.getWidth();
		int largeur = this.getHeight();
		if(afficherGrille){
			g2d.setColor(Color.LIGHT_GRAY);
			for(int t = 0; t < largeur; t++){
				g2d.drawLine(0,largeur -t*echelle,longeur,largeur -t*echelle);
			}
			for(int i = 0; i < longeur; i++){
				g2d.drawLine(longeur - echelle*i,0,longeur - echelle*i, largeur);
			}
		}g2d.setColor(Color.BLACK);
	}
	/**
	 * Méthode sert à vérrifier l'inclusion de chaque particule dans le plan du niveau
	 * @param objet - un Arraylist qui contient les objets du type de particule
	 */
	private void verificationInclusionParticules(ArrayList<Particules> objet){
		for(int k = 0; k < objet.size(); k++){
			if(inclut(paysage, objet.get(k).getShape())){
				chargeMemoire.add(objet.get(k));
			}
		}
	}
	/**
	 * 
	 * @param objet - un Arraylist qui contient les objets du type de BallonChasseur
	 * @param objetMemoire - un Arraylist qui contient les objets du type de BallonChasseur qui sont dans le plan
	 */
	private void verificationInclusionBallon(ArrayList<BallonChasseur> objet, ArrayList<BallonChasseur> objetMemoire){
		for(int k = 0; k < objet.size(); k++){
			if(inclut(paysage, objet.get(k).getShape())){
				objetMemoire.add(objet.get(k));
			}
		}
	}
	/**
	 * Effacer le designe du créateur (tout recommencer à nouveau)
	 */
	private void initialiser(){
		c = Color.LIGHT_GRAY;
		setTexture = false;
		selectionImage = false;
		dessinerFosse = false;
		dessinerCoccinelle = false;
		positive.clear();
		negative.clear();
		neutre.clear();
		ressortSet.clear();
		ballonSet.clear();
		cercleSet.clear();
		if(listingDesComposants){
		chargeMemoire.clear();
		ressortMemoire.clear();
		ballonMemoire.clear();
		cercleMemoire.clear();
		champsSet.clear();
		plaqueSet.clear();
		champsMemoire.clear();
		plaqueMemoire.clear();
		}
		compteurLocal = 0;
		buttonSetRessort.setEnabled(true);
		dejaSauve = false;
		repaint();
	}
	/**
	 * Méthode sert à sauvegarder le plan designé par le créateur
	 */
	public void sauvegarder(){
		fileChooser.setVisible(true);
		File file = null;
		int result = fileChooser.showSaveDialog(fileChooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			listingDesComposants = true;
			getNiveauInformation(listingDesComposants);
			file = fileChooser.getSelectedFile();
			ecrireText(file);
		} else if (result == JFileChooser.CANCEL_OPTION) {
			//System.out.println("no pointing file");
		}
	}
	/**
	 * Méthode sert à calculer le nombre d'objet additionnés dans le fichier de file (coccinelle et fosse) 
	 * Pour un nombre minimum de 0 à maximum de 2
	 */
	private void calculerCoccSize(){
		if(dessinerCoccinelle || dessinerFosse){
			coccDrawSize = 1;
		}
		if(dessinerCoccinelle && dessinerFosse){
			coccDrawSize = 2;
		}
	}
	/**
     * Méthode sert à écrire un fichier de plan pour sauvegarder
	 * @param ois - ObjectInputStream par défaut (un file reader pour la lecture)
	 * @param fichierDeTravail - le fichier de plan mémorisé
	 */
	private void write(ObjectOutputStream oos, File file){
		try {
			FileOutputStream fichierDeTravail = new FileOutputStream(file);
			oos = new ObjectOutputStream(fichierDeTravail);
			oos.writeObject(chargeMemoire.size() + ressortMemoire.size() + ballonMemoire.size() + cercleMemoire.size() + coccDrawSize +plaqueMemoire.size()+champsMemoire.size()+ 3); // 1 pour couleur du fond 1 pour image backGround 1 pour matrice 
			for(int k = 0; k < chargeMemoire.size(); k++){
				oos.writeObject(chargeMemoire.get(k)); 
			}
			for(int i = 0; i < ressortMemoire.size(); i++){
				oos.writeObject(ressortMemoire.get(i)); 
			}
			for(int a = 0; a < ballonMemoire.size(); a++){
				oos.writeObject(ballonMemoire.get(a)); 
			}
			for(int b = 0; b < cercleMemoire.size(); b++){
				oos.writeObject(cercleMemoire.get(b)); 
			}
			if(dessinerCoccinelle){
				oos.writeObject(cocc); 
			} 
			if(dessinerFosse){
				oos.writeObject(trou); 
			}
			for(int p = 0; p < plaqueMemoire.size(); p++){
				oos.writeObject(plaqueMemoire.get(p)); 
			}
			for(int ch = 0; ch < champsMemoire.size(); ch++){
				oos.writeObject(champsMemoire.get(ch)); 
			}
			oos.writeObject(c);
		    oos.writeObject(tableauChaine[indexImage]);
		    oos.writeObject(new Vector2d(niveauX,niveauY));
			fichierDeTravail.close();
			JOptionPane.showMessageDialog(null,  "\nLe fichier est pret pour la lecture \n il y a " + chargeMemoire.size() + " particules. " + "\n il y a " + ressortMemoire.size() + " ressorts. " + "\n il y a " + ballonMemoire.size() + " ballons. " + "\n il y a " + cercleMemoire.size() + " cercles. "  + "\n il y a " + plaqueMemoire.size() + " plaque. " + "\n il y a " + champsMemoire.size() + " champs. ");
		}
		catch (IOException e) {
			//System.out.println("Erreur à l'écriture:");
			e.printStackTrace();
		}
		finally {
			//on exécutera toujours ceci, erreur ou pas
			try { 
				oos.close();  
				//tos.close();
			}
			catch (IOException e) { 
				JOptionPane.showMessageDialog(null,"Erreur rencontrée lors de la fermeture!"); 
			}
		}//fin finally	
	}
	/**
	 * Méthode sert à écrire un plan mémoriser sauvegardé dans un chemin de source indiqué
	 * @param file - une file crée par JfileChooser (le nom du fichier est donné par l'utilisateur)
	 */
	private void ecrireText(File file){
		ObjectOutputStream oos = null;
		calculerCoccSize();
		if (file != null) {
			write(oos, file);
		}
	}
	/**
	 * Méthode sert à écrire un plan mémoriser sauvegardé dans le dossier par défaut
	 * @param name - le nom du plan qui est donné par l'utilisateur
	 */
	private void ecrireText(String name){
		final String NOM_FICHIER = name;
		File fichier = new File( NOM_FICHIER );
		ObjectOutputStream oos = null;
		calculerCoccSize();
		write(oos, fichier);
	}
	/**
	 * Méthode sert à lire un fichier de plan sauvegardé
	 * @param ois - ObjectInputStream par défaut (un file reader pour la lecture)
	 * @param fichierDeTravail - le fichier de plan mémorisé
	 */
	private void read(ObjectInputStream ois, File fichierDeTravail){
		Particules particulesExemple = new Particules(0,0);
		Ressort ressortExemple = new Ressort(0,0,0);
		BallonChasseur ballonExemple = new BallonChasseur(new Vector2d(),0,0,0,new Vector2d());
		Plaque plq = new Plaque(0,0,0);
		ChampMagnetique ch = new ChampMagnetique(0, 0, 0,0,0);
		Path2D linePath=new Path2D.Double();
		Color d = new Color(0,0,255);
		String t = "lol";
		Particules charge;
		Ressort ressort;
		BallonChasseur ballon,cercle;
		Plaque plaque;
		ChampMagnetique champs;
		int nbTotal = 0;
		String totalParticules = "\n";
		String totalRessort = "\n";
		String totalBallon = "\n";
		String totalCercle = "\n";
		String totalPlaque = "\n";
		String totalChamps= "\n";
		try {
			ois = new ObjectInputStream(new FileInputStream(fichierDeTravail));
			nbTotal = (Integer) ois.readObject();
			for (int k=0; k< nbTotal; k++) { 
				//System.out.println("valeur de k :"+k);
				Object objet = ois.readObject(); 
				//System.out.println("class d'objet :"+objet.getClass());
				if(objet.getClass() == particulesExemple.getClass()){
					charge = (Particules) objet; 
					totalParticules = totalParticules + charge.getCharge() + "\n";
					if(charge.getCharge() == 0){ 
						neutre.add(charge);
						dessinerParticules[2] = true;
					}else if (charge.getCharge() < 0){
						negative.add(charge);
						dessinerParticules[1] = true;
					}else if (charge.getCharge() > 0){
						positive.add(charge);
						dessinerParticules[0] = true;
					}
				}else if(objet.getClass() == ressortExemple.getClass()){
					ressort = (Ressort) objet; 
					totalRessort = totalRessort + ressort.getLongeurMax() + "\n";
					ressortSet.add(ressort);
					dessinerRessorts = true;
				}else if(objet.getClass() == ballonExemple.getClass()){
					if(((BallonChasseur) objet).getOrientation() == 5){
						ballon = (BallonChasseur) objet; 
						totalBallon = totalBallon + ballon.getPosition() + "\n";
						ballonSet.add(ballon);
						dessinerBallon = true;
					}else
						if(((BallonChasseur) objet).getOrientation() == 10){
							cercle = (BallonChasseur) objet; 
							totalCercle = totalCercle + cercle.getPosition() + "\n";
							cercleSet.add(cercle);
							dessinerCercle = true;
						}
							

				}else if(objet.getClass() == linePath.getClass()){
					dessinerFosse = true;
					dessinerCoccinelle = true;
					//System.out.println("coccinelle ");
				}else if(objet.getClass() == d.getClass()){
					c = (Color)objet;
					//System.out.println("color"+c);
				}else if(objet.getClass() == t.getClass()){
					stringImage = (String)objet;
					//System.out.println("string image est  :"+ stringImage);
					for(int b = 0 ; b < 20; b++){
						if(stringImage.equals(tableauChaine[b])&& !stringImage.equals("IconUn.jpg")){
				    		imgTexture = bufferImage.get(b);
				    		setTexture = true;
						}
					}
				}else if(objet.getClass() == plq.getClass()){
					plaque = (Plaque)objet;
					totalPlaque = totalPlaque + plaque.getChampsElectrique() +  "N/C \n";
					plaqueSet.add(plaque);
					dessinerPlaque = true;
				}else if(objet.getClass() == ch.getClass()){
					champs = (ChampMagnetique)objet;
					totalChamps = totalChamps + champs.getChampsMagnetique() + " T \n";
					champsSet.add(champs);
					dessinerChamps = true;
				}
			}
		} 	
		catch (ClassNotFoundException e) {
			//System.out.println("L'objet lu est d'une classe inconnue");
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Fichier  " + fichierDeTravail.getAbsolutePath() + "  introuvable!");
			System.exit(0);
		}

		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur rencontree lors de la lecture");
			e.printStackTrace();
			System.exit(0);
		}

		finally {
			//on exécutera toujours ceci, erreur ou pas
			try { 
				ois.close();
			}
			catch (IOException e) { 
				//System.out.println("Erreur rencontrée lors de la fermeture!"); 
			}
		}//fin finally

		if (nbTotal > 0) {
			JOptionPane.showMessageDialog(null,"Les charges sont : " + totalParticules + "\n  les ressorts sont à longeur " + totalRessort  + "\n  les ballon sont à : "+ totalBallon + "\n  les cercle sont à : " +  "\n  les plaques ont des charges de " + totalPlaque +  "\n  les champs ont des champs de  " + totalChamps  +  totalCercle +" \n en somme on a " + nbTotal +" elements \n ");
		}
	}
	/**
	 * Méthode sert à lire un plan mémoriser sauvegardé dans un chemin de source indiqué
	 * @param file - une file crée par JfileChooser (le nom du fichier est donné par l'utilisateur)
	 */
	private void lireText(File file){
		initialiser();
		ObjectInputStream ois = null;
		File fichierDeTravail = file;
		read(ois, fichierDeTravail);
		
	}
	/**
	 * Méthode sert à lire un plan mémoriser sauvegardé dans le dossier par défaut
	 * @param name - le nom du plan qui est donné par l'utilisateur
	 */
	private void lireText(String name){
		initialiser();
		final String NOM_FICHIER_ETUDIANTS = name;
		ObjectInputStream ois = null;
		File fichierDeTravail = new File( NOM_FICHIER_ETUDIANTS );
		read(ois, fichierDeTravail);
	}
	/**
	 * Méthode sert à mentionner à sauvegarder le niveau création
	 * @return boolean  <b>true</b> déja sauvegarder
	 * <b>false</b> à sauvegarder
	 */
	public boolean getDejaSauver(){
		return dejaSauve;
	}
	/**
	 * Méthode sert à modifier le boolean sauvegarder
	 * @param sauve - booelan <b>true</b> déja sauvegarder
	 * <b>false</b> à sauvegarder
	 */
	public void setDejaSauver(boolean sauve){
		this.dejaSauve = sauve;
	}
	/**
	 * 
	 * @param input
	 */
	private void EnregistrerNom(String input) {
		if(collectionNom.size()<= 4){
			collectionNom.add(input);
		}else{
			collectionNom.remove(0);
			collectionNom.add(input);
		}
		for(SelecNiveauListener ecout : OBJETS_ENREGISTRES.getListeners(SelecNiveauListener.class) ) {
			ecout.envoyerListNom(collectionNom);
		}
	}
	/**
	 * Cette méthode permet d'ajouter un écouteur dans le niveau
	 * @param objEcouteur l'écouteur
	 */
	public void addSelecNiveauListener(SelecNiveauListener objEcouteur) {
		OBJETS_ENREGISTRES.add(SelecNiveauListener.class,objEcouteur);

	}
}
