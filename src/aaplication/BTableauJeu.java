
package aaplication;
import ecouteur.CoccinelleListener;
import ecouteur.MainPageListener;
import ecouteur.Niveau1Listener;
import ecouteur.Niveau2Listener;
import ecouteur.Niveau3Listener;
import ecouteur.Niveau4Listener;
import ecouteur.NiveauLoadListener;
import ecouteur.SelecNiveauListener;
import ecouteur.TransmissionInfoNiveau1Listener;
import ecouteur.TransmissionInfoNiveau1Scene2Listener;
import ecouteur.TransmissionInfoNiveau4Listener;
import ecouteur.TransmissionInfoNiveauLoadListener;
import geometrie.BallonChasseur;
import geometrie.BlocVitre;
import geometrie.ChampMagnetique;
import geometrie.Particules;
import geometrie.Plaque;
import geometrie.Prisme;
import geometrie.Ressort;

import java.awt.Color;
import java.awt.Shape;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;

import physique.Vector2d;
import physique.Vector3d;
import sceneanimation.NiveauDeux;
import sceneanimation.NiveauLoad;
import sceneanimation.NiveauQuatre;
import sceneanimation.NiveauTrois;
import sceneanimation.NiveauUn;
import sceneanimation.NiveauUnScene2;
import sceneanimation.PanelDroit1;
import sceneanimation.PanelDroit2;
import sceneanimation.PanelDroit3;
import sceneanimation.PanelDroit4;
import sceneanimation.PanelDroitLoad;
import sceneanimation.SelecteurNiveau;
/**
 * 
 * Classe de table du jeu ou contient les quatre niveaux du jeu
 * 
 * @author Zi Zheng Wang
 * @version 3.0
 */
public class BTableauJeu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnAide;
	private NiveauUn niveauUn;
	private NiveauDeux niveauDeux;
	private NiveauTrois niveauTrois;
	private NiveauQuatre niveauQuatre;
	private NiveauUnScene2 niveau1partie2;
	private SelecteurNiveau selecNiv;
	private JMenuItem mntmSweet;
	private JButton btnMute;
	private PanelDroit1 panelDroit1;
	private PanelDroit2 panelDroit2;
	private PanelDroit3 panelDroit3;
	private PanelDroit4 panelDroit4;
	private JMenuItem mntmNiveau;
	private JMenuItem mntmNiveau_1;
	private JMenuItem mntmNiveau_2;
	private boolean[] affichageActuelle = {false,false,false,false,false,true};
	private JMenuItem mntmNiveau_3;
	private JButton btnBackToHome;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList(); //collection
	private NiveauLoad niveauLoad;
	private PanelDroitLoad panelLoad;
	private JMenu mnLoad;
	private boolean musicEnCours = true;
	private JFileChooser fileChooser;
	private JMenuItem mntmLoad;
	private ArrayList<Particules> particuleSet;
	private ArrayList<Ressort> ressortSet;
	private ArrayList<BallonChasseur> ballonSet;
	private ArrayList<BallonChasseur> cercleSet;
	private ArrayList<Plaque> plaqueSet;
	private ArrayList<ChampMagnetique> champsSet;
	private ArrayList<Shape> coccSet;
	private Color col = Color.LIGHT_GRAY;
	private String stringImage, message;
	private Vector2d mat = new Vector2d();
	private EJeuDessais jeuDessais = new EJeuDessais();
	private DPanelLoading panelLoading;
	/**
	 * un frame ou contient les quatre niveaux du jeu et les quatre panneaux de controle
	 */
	private JButton btnEdi;
	public BTableauJeu() {
		setTitle("La grande aventure de la coccinelle étincelante à travers les milieux effrayants de la physique moderne");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 1183, 684);	
		menuBar = new JMenuBar();
		menuBar.setForeground(SystemColor.windowBorder);
		setJMenuBar(menuBar);
		btnBackToHome = new JButton("Retour au menu\r\n");
		btnBackToHome.setFocusable(false);
		btnBackToHome.setEnabled(false);
		btnBackToHome.setForeground(UIManager.getColor("CheckBox.darkShadow"));
		btnBackToHome.setBackground(UIManager.getColor("CheckBox.background"));
		btnBackToHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisibleNiveaux(false,false,false,false,false,true);
				selecNiv.recommencer();
				setNiveauActuelle(5);
			}
		});
		btnBackToHome.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(btnBackToHome);

		btnEdi = new JButton("Création de niveau");
		btnEdi.setForeground(UIManager.getColor("CheckBox.darkShadow"));
		btnEdi.setBackground(UIManager.getColor("CheckBox.background"));
		btnEdi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(MainPageListener ecout : OBJETS_ENREGISTRES.getListeners(MainPageListener.class) ) {
					ecout.activerCEditeur();
				}
				setVisible(false);
			}
		});
		btnEdi.setFocusable(false);
		menuBar.add(btnEdi);
		
		btnMute = new JButton("Désactiver la musique de fond");
		btnMute.setFocusable(false);
		btnMute.setEnabled(false);
		btnMute.setHorizontalAlignment(SwingConstants.RIGHT);
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(musicEnCours){
					btnMute.setText("Activer la musique de fond");
				}else{
					btnMute.setText("Désactiver la musique de fond");
				}
				musicEnCours = !musicEnCours;
				setVisibleNiveaux(affichageActuelle[0],affichageActuelle[1],affichageActuelle[2],affichageActuelle[3],affichageActuelle[4],affichageActuelle[5]);
			}
		});
		menuBar.add(btnMute);
		
		
		JMenu mnAide_1 = new JMenu("Aide");
		menuBar.add(mnAide_1);

		panelLoading = new DPanelLoading();
		panelLoading.addSelecNiveauListener(new SelecNiveauListener(){
			public void selectionNiveau(int nbNiv) {

			}
			public void load() {
				lireEnChemin();
			}
			public void loadParDefaut(String c) {
				lireParDefaut(c);
			}
			public void returnMenu() {
			}
			public void creationNiveau() {
			}
			public void envoyerListNom(ArrayList<String> collectionNom) {
			}
		});
		
		JMenuItem mntmJeuxDessai = new JMenuItem("Jeux d'essais");
		mntmJeuxDessai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jeuDessais.setVisible(true);
				jeuDessais.changeIndex(0);
			}
		});
		mnAide_1.add(mntmJeuxDessai);

		JMenuItem mntmInstructionCompltes = new JMenuItem("Instruction compl\u00E8tes");
		mntmInstructionCompltes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jeuDessais.setVisible(true);
				jeuDessais.changeIndex(1);
			}
		});
		mnAide_1.add(mntmInstructionCompltes);

		JMenuItem mntmConceptsScientifiques = new JMenuItem("Concepts scientifiques");
		mntmConceptsScientifiques.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jeuDessais.setVisible(true);
				jeuDessais.changeIndex(2);
			}
		});
		mnAide_1.add(mntmConceptsScientifiques);

		JMenuItem mntmSources = new JMenuItem("Sources");
		mntmSources.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jeuDessais.setVisible(true);
				jeuDessais.changeIndex(3);
				//JOptionPane.showMessageDialog(null, "l'image au fond de niveauDeux : http://wallpaperswide.com/purple_texture-wallpapers.html ; source wallPaper \n" + "l'image au fond de nvieauTrois : http://www.photo-wallpaper.net/14810-wallpaper-texture-stripes-obliquely-shadow-background-black \n" + "Le codage de RungeKutta et Derivee : Mathieu Payette \n" + "le son de clic: http://www.universal-soundbank.com/wav/sons-wav.htm \n" + "le son de briser bloc  http://soundbible.com/1658-Mirror-Breaking.html \n" + "l'image du lÃ©zard : http://www.renders-graphiques.fr/galerie/Reptiles-92/Lezard-75634.htm \n" + "image de la coqurelle: http://www.desertmuseum.org/images/nhsd_cockroch.gif \n", "Sources", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnAide_1.add(mntmSources);

		JMenuItem mntmPropos = new JMenuItem("\u00C0 propos");
		mntmPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jeuDessais.setVisible(true);
				jeuDessais.changeIndex(4);
			}
		});
		mnAide_1.add(mntmPropos);

		mnAide = new JMenu("Liste des niveaux\r\n");
		menuBar.add(mnAide);

		mntmSweet = new JMenuItem("Lire par défaut");
		mntmSweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*JOptionPane.showMessageDialog(null, "C'est sweet");
				String c = JOptionPane.showInputDialog(null,"Entrez le nom de fichier", "niveauPop.txt");
				if(c != null){
					if(c == ""){
						JOptionPane.showMessageDialog(null, "nom de fichier invalide");
					}else{
						lireParDefaut(c);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Source inconnue");
				}*/
				panelLoading.setVisible(true);
			}
		});

		fileChooser = new JFileChooser("D :\\");
		mntmLoad = new JMenuItem("Lire");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lireEnChemin();
			}
		});
		mnAide.add(mntmLoad);
		mnAide.add(mntmSweet);

		mntmNiveau = new JMenuItem("Niveau 1");
		mntmNiveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisibleNiveaux(true,false,false,false,false,false);
				setNiveauActuelle(0);
				panelDroit1.setTabPaneSelectedIndex(2);
			}
		});
		mnAide.add(mntmNiveau);

		mntmNiveau_1 = new JMenuItem("Niveau 2");
		mntmNiveau_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisibleNiveaux(false,false,true,false,false,false);
				setNiveauActuelle(2);
				panelDroit2.setTabPaneSelectedIndex(3);
			}
		});
		mnAide.add(mntmNiveau_1);
	
		mntmNiveau_2 = new JMenuItem("Niveau 3");
		mntmNiveau_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisibleNiveaux(false,false,false,true,false,false);
				setNiveauActuelle(3);
				panelDroit3.setTabPaneSelectedIndex(2);
			}
		});
		mnAide.add(mntmNiveau_2);

		mntmNiveau_3 = new JMenuItem("Niveau 4");
		mntmNiveau_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisibleNiveaux(false,false,false,false,true,false);
				setNiveauActuelle(4);
				panelDroit4.setTabPaneSelectedIndex(2);
			}
		});
		mnAide.add(mntmNiveau_3);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		niveauUn = new NiveauUn();
		niveauUn.setBounds(10, 11, 800, 600);
		niveauUn.setVisible(false);
		contentPane.add(niveauUn);
		niveauUn.addTransmissionInfoNiveau1Listener(new TransmissionInfoNiveau1Listener(){
			@Override
			public void getPositionBlocVitre(BlocVitre blocVitre, AffineTransform matMC){
				niveau1partie2.setPosBlocVitre(blocVitre,matMC);
			}
			@Override
			public void getPositionPrisme(Prisme prisme, AffineTransform matMC){
				niveau1partie2.setPosPrimse(prisme,matMC);
			}
			@Override
			public void changementScene(boolean b){
				setVisibleNiveaux(false,true,false,false, false,false);
				panelDroit1.debloquerParametres(true);
				setNiveauActuelle(1);
			}
			@Override
			public void getAngleIncidentMir(int nbMir, double angle) {
				panelDroit1.afficherAngleIncidentMir(nbMir, angle);
			}
			@Override
			public void getAngleReflechieMir(int nbMir, double angle) {
				panelDroit1.afficherAngleReflechiMir(nbMir, angle);
			}
			@Override
			public void getAngleCritiqueMilieu(int nbMilieu, double angle) {
				panelDroit1.afficherAngleCritique(nbMilieu,angle);

			}
			@Override
			public void getAngleReflechiMilieu2(int nbMilieu, double angle) {
				panelDroit1.afficherAngleReflechiMilieu(2,nbMilieu,angle);

			}
			@Override
			public void getAngleIncidentMilieu1(int nbMilieu, double angle) {
				panelDroit1.afficherAngleIncidentMilieu(1,nbMilieu,angle);

			}
			@Override
			public void getAngleReflechiMilieu1(int nbMilieu, double angle) {
				panelDroit1.afficherAngleReflechiMilieu(1,nbMilieu,angle);

			}
			@Override
			public void getAngleIncidentMilieu2(int nbMilieu, double angle) {
				panelDroit1.afficherAngleIncidentMilieu(2,nbMilieu,angle);

			}
			@Override
			public void remiseRotationMilieu(int nbMilieu) {
				panelDroit1.remiseRotationMilieu(nbMilieu);

			}
			@Override
			public void bloquerParametres() {
				panelDroit1.debloquerParametres(false);
				
			}

		});

		niveau1partie2 = new NiveauUnScene2();
		niveau1partie2.setBounds(10, 11, 800, 600);
		contentPane.add(niveau1partie2);
		niveau1partie2.setVisible(false);
		niveau1partie2.addTransmissionInfoNiveau1Scene2Listener(new TransmissionInfoNiveau1Scene2Listener(){

			@Override
			public void getVitesseParticule1(double vitesse) {
				panelDroit1.afficherVitesse1(vitesse);	
			}

			@Override
			public void getVitesseParticule2(double vitesse) {
				panelDroit1.afficherVitesse2(vitesse);
			}

			@Override
			public void getVitesseCoccinelle(double vitesse) {
				panelDroit1.afficherVitesseCoccinelle(vitesse);

			}

			@Override
			public void getChargePart(double charge, int nbPart) {
				panelDroit1.afficherCharge(charge,nbPart);

			}

			@Override
			public void getChargeCocc(double charge) {
				panelDroit1.afficherChargeCocc(charge);
			}

			@Override
			public void getForce(Vector2d force, int nbPart) {
				panelDroit1.afficherForce(force,nbPart);

			}

			@Override
			public void getMasse(double masse, int nbPart) {
				panelDroit1.afficherMasse(masse,nbPart);
			}

			@Override
			public void niveauSwitch() {
				setVisibleNiveaux(false,false,true,false,false,false);
				setNiveauActuelle(2);

			}

			@Override
			public void bloquerParametres(boolean b) {
				panelDroit1.debloquerParametres(b);
				
			}



		});


		niveauDeux = new NiveauDeux();
		niveauDeux.setBounds(10, 11, 800, 600);
		niveauDeux.addCoccinelleListener(new CoccinelleListener() {
			public void niveauSwitch() {
				setVisibleNiveaux(false,false,false,true,false,false);
				panelDroit3.setTabPaneSelectedIndex(2);
			}
			public void getRessortVitesse(int i, double vitesse) {
				panelDroit2.setRessortVitesse(i,vitesse);
			}
			public void getRessortPosition(int i, double positionReelleBloc) {
				panelDroit2.setRessortPosition(i,positionReelleBloc);
			}
			public void getCenter(Vector2d centre) {
			}
			public void getRotation(double rotation, double sens, double vitesse) {
			}
			public void getBallonAInformation(Vector2d position,
					double vitesse, double angle) {
			}
			public void getBallonBInformation(Vector2d position,
					double vitesse, double angle) {
			}
			public void getBallonCInformation(Vector2d position,
					double vitesse, double angle) {
			}
			public void getBallonDInformation(Vector2d position,
					double vitesse, double angle) {
			}
			public void coeficientCollsiion(double collsiion) {
			}
			@Override
			public void action() {
				panelDroit2.activer();
			}
		});
		niveauDeux.setVisible(false);
		contentPane.add(niveauDeux);

		niveauTrois = new NiveauTrois();
		niveauTrois.setBounds(10, 11, 800, 600);
		niveauTrois.addCoccinelleListener(new CoccinelleListener() {
			public void niveauSwitch() {
				setVisibleNiveaux(false,false,false,false,true,false);
				panelDroit4.setTabPaneSelectedIndex(2);
			}
			public void getCenter(Vector2d centre) {
				panelDroit3.setRoueCenter(centre);
			}
			public void getRotation(double rotation, double sens, double vitesse) {
				panelDroit3.setRoueParametre(rotation,sens,vitesse);
			}
			public void getBallonAInformation(Vector2d position, double vitesse,double angle) {
				panelDroit3.setBallonAParametre(position,vitesse,angle);
			}
			public void getBallonBInformation(Vector2d position, double vitesse,double angle) {
				panelDroit3.setBallonBParametre(position,vitesse,angle);
			}
			public void getBallonCInformation(Vector2d position, double vitesse,double angle) {
				panelDroit3.setBallonCParametre(position,vitesse,angle);
			}
			public void getBallonDInformation(Vector2d position, double vitesse,double angle) {
				panelDroit3.setBallonDParametre(position,vitesse,angle);
			}
			public void coeficientCollsiion(double collision) {
				panelDroit3.setCoeficient(collision);
			}
			public void getRessortVitesse(int i, double vitesse) {
			}
			public void getRessortPosition(int i, double positionReelleBloc) {
			}
			@Override
			public void action() {
				panelDroit3.activer();
			}

		});
		niveauTrois.setVisible(false);
		contentPane.add(niveauTrois);

		niveauQuatre = new NiveauQuatre();
		niveauQuatre.setBounds(10, 11, 800, 600);
		niveauQuatre.setVisible(false);
		contentPane.add(niveauQuatre);
		niveauQuatre.addTransmissionInfoNiveau4Listener(new TransmissionInfoNiveau4Listener(){

			@Override
			public void getVitesseCoccinelle(double vitesse) {
				panelDroit4.afficherVitesse(vitesse);

			}

			@Override
			public void getForceElectrique(Vector3d forceElectrique) {
				panelDroit4.afficherForceElectrique(forceElectrique);

			}

			@Override
			public void getForceMagnetique(int nbChamps, Vector3d forceMagnetique) {
				panelDroit4.afficherForceMagnetique(nbChamps,forceMagnetique);

			}

			@Override
			public void getSelecteurVitesse(double vitesse) {
				panelDroit4.afficherSelecteurVitesse(vitesse);
				//System.out.println(vitesse);

			}

			@Override
			public void getRayonIdeale(double rayon) {
				panelDroit4.afficherRayon(rayon);

			}

			@Override
			public void getChampsMagnetique(double champsMagnetique) {
				panelDroit4.afficherChampsMagnetique(champsMagnetique);
				
			}

			@Override
			public void getObjetSelectionne(String nom) {
				panelDroit4.afficherObjetSelectionne(nom);
				
			}

			@Override
			public void animation(boolean b) {
				panelDroit4.debloquerParametres(b);
				
			}

			@Override
			public void jeuxTerminer() {
				setVisibleNiveaux(false,false,false,false,false,true);
				setNiveauActuelle(5);
			}

			@Override
			public void getPosCocc(Vector2d position) {
				panelDroit4.afficherPosition(position);
				
			}

			@Override
			public void getPeriode(double periode) {
				panelDroit4.afficherPeriode(periode);
				
			}

			@Override
			public void getAcceleration(double acceleration) {
				panelDroit4.afficherAcceleration(acceleration);
				
			}
		});




		niveauLoad = new NiveauLoad();
		niveauLoad.setBounds(10, 11, 800, 600);
		niveauLoad.addTransmissionInfoNiveauLoadListener(new TransmissionInfoNiveauLoadListener(){
			public void getCercleMessage(String message) {
				panelLoad.setCercleInfo(message);

			}

			@Override
			public void getBallonMessage(String message) {
				panelLoad.setBallonInfo(message);

			}

			@Override
			public void afficheRessortPanel(int a) {
				panelLoad.afficherRessortPanel(a);

			}

			@Override
			public void getPlaqueInfo(String message) {
				panelLoad.afficherPlaque(message);
				
			}

			@Override
			public void getChampsInfo(String message) {
				panelLoad.afficherChamps(message);
				
			}

			@Override
			public void action() {
				panelLoad.action();
				
			}

		});
		niveauLoad.setVisible(false);
		contentPane.add(niveauLoad);

		panelDroit1 = new PanelDroit1();
		panelDroit1.setBounds(830, 11, 325, 600);
		panelDroit1.setTabPaneSelectedIndex(2);
		panelDroit1.setVisible(false);
		panelDroit1.addNiveau1Listener(new Niveau1Listener() {

			@Override
			public void quitter() {
				System.exit(0);

			}

			@Override
			public void arreter() {
				niveauUn.arreter();
				niveau1partie2.arreter();
			}

			@Override
			public void reDemarrer() {
				niveauUn.demarrer();
				niveau1partie2.demarrer();
			}

			@Override
			public void initialiser() {
				niveauUn.initialiser();
				niveau1partie2.initialiser();

			}

			@Override
			public void setAngleRec(double radians) {
				niveauUn.setAngleRec(radians);

			}

			@Override
			public void setAnglePrisme(double radians) {
				niveauUn.setAnglePrisme(radians);

			}
			@Override
			public void setRefractionVert(double valeur){
				niveauUn.setIndiceRefractionVert(valeur);
			}
			@Override
			public void setRefractionBleu(double valeur){
				niveauUn.setIndiceRefractionBleu(valeur);
			}
			@Override
			public  void modeScientifique(boolean b){
				niveau1partie2.setScientifique(b);
				niveauUn.setScientifique(b);
			}

			@Override
			public void setChargePart1(double charge) {
				niveau1partie2.setChargeParticule(1, charge);
			}

			@Override
			public void setChargePart2(double charge) {
				niveau1partie2.setChargeParticule(0, charge);

			}

			@Override
			public void setChargeCocc(double charge) {
				niveau1partie2.setChargeCoccinelle(charge);
			}

			@Override
			public void choisirNormale(int mirroir, int normale,
					boolean dessinable) {
				niveauUn.choisirNormaleMir(mirroir, normale, dessinable);

			}

			@Override
			public void choisirNormaleBleu(int normale, boolean dessinable) {
				niveauUn.choisirNormaleBleu(normale, dessinable);

			}

			@Override
			public void choisirNormaleVert(int normale, boolean dessinable) {
				niveauUn.choisirNormaleVert(normale, dessinable);
			}

		});
		contentPane.add(panelDroit1);

		panelDroit2 = new PanelDroit2();
		panelDroit2.addNiveau2Listener(new Niveau2Listener() {

			@Override
			public void quitter() {
				// TODO Auto-generated method stub

			}

			@Override
			public void arreter() {
				niveauDeux.arreter();

			}

			@Override
			public void demarrer(int a) {
				niveauDeux.setEtatAnim(a);

			}

			@Override
			public void initialiser() {
				niveauDeux.initialiser();

			}

			@Override
			public void setMasse(int a, double masse) {
				niveauDeux.setMasseRessort(a, masse);

			}

			@Override
			public void setVitesse(int a, double vitesse) {
				niveauDeux.setVitesseRessort(a, vitesse);

			}

			@Override
			public void setConstantRessort(int a, double constant) {
				niveauDeux.setConstantRessort(a,constant);

			}

			@Override
			public void setCoeficient(int a, double coeficient) {
				niveauDeux.setCoeficientFrottement(a,coeficient);

			}

			@Override
			public void setDeplacement(int a, double deplacement) {
				niveauDeux.setDeplacementInitial(a,deplacement);

			}

			@Override
			public void setDeltaTemps(double temps) {
				niveauDeux.setDetaTemps(temps);

			}

			@Override
			public void modeScientifique(boolean b) {
				niveauDeux.modeSicentifique(b);

			}

			@Override
			public void setCharge(int a, int charge) {
				niveauDeux.setChargeParticule(a,charge);

			}

		});
		panelDroit2.setBounds(830, 11, 325, 600);
		panelDroit2.setVisible(false);
		contentPane.add(panelDroit2);

		panelDroit3 = new PanelDroit3();
		panelDroit3.setBounds(830, 11, 325, 600);
		panelDroit3.setVisible(false);
		panelDroit3.addNiveau3Listener(new Niveau3Listener(){

			@Override
			public void quitter() {
				// TODO Auto-generated method stub	
			}
			public void arreter() {
				niveauTrois.arreter();	
			}
			public void demarrer() {
				niveauTrois.demarrer();		
			}
			public void initialiser() {
				niveauTrois.initialiser();	
			}
			public void setSens(int a) {
				niveauTrois.setSens(a);
			}   
			public void setVitesse(int b) {
				niveauTrois.setRotationVitesse(b);
			}
			@Override
			public void setCoefficientRes(int a) {
				niveauTrois.setCoefficient(a);

			}
			@Override
			public void setBallonMasse(int a, double b) {
				niveauTrois.setMasseBallon(a,b);

			}
			@Override
			public void setImpulsion(double b) {
				niveauTrois.setImpulsion(b);

			}
			@Override
			public void setScientifique(boolean b) {
				niveauTrois.setScientifique(b);

			}
			@Override
			public void setSlowMotion(double temps) {
				niveauTrois.setSlowMotion(temps);

			}
		});
		contentPane.add(panelDroit3);

		panelDroit4 = new PanelDroit4();
		panelDroit4.setBounds(830, 11, 325, 600);
		panelDroit4.setVisible(false);
		panelDroit4.addNiveau4Listener(new Niveau4Listener(){
			@Override
			public void quitter() {
				// TODO Auto-generated method stub	
			}
			@Override
			public void arreter() {
				niveauQuatre.arreter();
			}
			@Override
			public void demarrer() {
				niveauQuatre.demarrer();
			}
			@Override
			public void initialiser() {
				niveauQuatre.initialiser();
			}
			@Override
			public void setPotentielleElectrique(int potentielle) {
				niveauQuatre.setPotentielleElectriqueCyclo(potentielle);

			}
			@Override
			public void setChampsMagnetique(int nbChamps, double champs) {
				niveauQuatre.setChampsMagnetique(nbChamps,champs);

			}
			@Override
			public void setChampsElectrique(double champs) {
				niveauQuatre.setChampsElectrique(champs);

			}
			@Override
			public void setVitesseCoquerelles(double vitesse) {
				niveauQuatre.setVitesseCoquerelles(vitesse);

			}
			@Override
			public void setMasse(double masse) {
				niveauQuatre.setMasseCoccinelle(masse);

			}
			@Override
			public void setCharge(double charge) {
				niveauQuatre.setChargeCoccinelle(charge);

			}
			@Override
			public void zoom(boolean typeZoom) {
				niveauQuatre.modificationZoom(typeZoom);
			}
			@Override
			public void modeScientifique(boolean b) {
				niveauQuatre.setModeScientifique(b);
				
			}
			@Override
			public void appelCalculPeriode() {
				niveauQuatre.calculPeriode();
			}
		});
		contentPane.add(panelDroit4);

		panelLoad = new PanelDroitLoad();
		panelLoad.setBounds(830, 11, 325, 600);
		panelLoad.setVisible(false);
		panelLoad.addNiveauLoadListener(new NiveauLoadListener(){

			@Override
			public void quitter() {
			}
			@Override
			public void arreter() {
				niveauLoad.arreter();
			}
			@Override
			public void initialiser() {
				niveauLoad.initialiser();
			}
			@Override
			public void demarrer(int a) {
				niveauLoad.setEtatAnim(a);
			}
			@Override
			public void effetBoussole(boolean afficherBoussole) {
				niveauLoad.setEffetBoussole(afficherBoussole);
			}
			@Override
			public void setMasse(int a, double masse) {
				niveauLoad.setMasseRessort(a,masse);
			}
			@Override
			public void setVitesse(int a, double vitesse) {
				niveauLoad.setVitesseRessort(a,vitesse);
			}
			@Override
			public void setConstantRessort(int a, double constant) {
				niveauLoad.setConstantRessort(a,constant);
			}
			@Override
			public void setCoeficient(int a, double coeficient) {
				niveauLoad.setCoeficientFrottement(a,coeficient);

			}
			@Override
			public void setDeplacement(int a, double deplacement) {
				niveauLoad.setDeplacementInitial(a,deplacement);

			}
			@Override
			public void scientifique(boolean b) {
				niveauLoad.scientifique(b);

			}
			@Override
			public void inverseCharge() {
				niveauLoad.inverseCharge();
			}
		});
		contentPane.add(panelLoad);
		
		selecNiv = new SelecteurNiveau();
		selecNiv.setBounds(0, 0, 1183, 684);
		selecNiv.setVisible(true);
		selecNiv.addSelecNiveauListener(new SelecNiveauListener(){

			@Override
			public void selectionNiveau(int nbNiv) {
				choisirNiveau(nbNiv);
				
			}

			@Override
			public void load() {
				panelLoading.setVisible(true);
				
			}

			@Override
			public void returnMenu() {
				setVisible(false);
				for(MainPageListener ecout : OBJETS_ENREGISTRES.getListeners(MainPageListener.class) ) {
					ecout.retournerAuMenuP();
				}
			}

			@Override
			public void creationNiveau() {
				for(MainPageListener ecout : OBJETS_ENREGISTRES.getListeners(MainPageListener.class) ) {
					ecout.activerCEditeur();
				}
				setVisible(false);
			}
			public void loadParDefaut(String c) {
			}
			public void envoyerListNom(ArrayList<String> collectionNom) {
				// TODO Auto-generated method stub
				
			}

		});
		contentPane.add(selecNiv);
	}
	/**
	 * MÃ©thode sert au changement des quatre niveaux
	 * @param nUn - boolean <b>true</b> afficher niveauUn ;  <b>false</b> dissimuler niveauUn
	 * @param uUnPartieDeux - boolean <b>true</b> : afficher niveauUn partie deux ; <b>false</b> dissimuler niveauUn partie deux
	 * @param nDeux - boolean <b>true</b> : afficher niveauDeux ; <b>false</b> dissimuler niveauDeux
	 * @param nTrois - boolean <b>true</b> : afficher niveauTrois ; <b>false</b> dissimuler niveauTrois
	 * @param nQuatre - boolean <b>true</b> : afficher niveauQuatre ; <b>false</b> dissimuler niveauQuatre
	 *  @param nSelec - boolean <b>true</b> : afficher le selecteur de niveau ; <b>false</b> dissimuler le selecteur de niveau.
	 */
	private void setVisibleNiveaux(boolean nUn,boolean uUnPartieDeux, boolean nDeux,boolean nTrois, boolean nQuatre,boolean nSelec){
		panelDroit1.setVisible(nUn || uUnPartieDeux);
		panelDroit2.setVisible(nDeux);
		panelDroit3.setVisible(nTrois);
		panelDroit4.setVisible(nQuatre);
		niveauUn.setVisible(nUn);
		niveauUn.debutNiveau(nUn && musicEnCours);
		niveau1partie2.setVisible(uUnPartieDeux);
		niveau1partie2.commencerAnimation(uUnPartieDeux);
		niveau1partie2.commencerMusic(musicEnCours && uUnPartieDeux);
		niveauDeux.setVisible(nDeux);
		niveauDeux.commencerMusic(nDeux && musicEnCours);
		niveauTrois.setVisible(nTrois);
		niveauTrois.commencerMusic(nTrois && musicEnCours);
		niveauQuatre.setVisible(nQuatre);
		niveauQuatre.commencerNiveau(nQuatre);
		niveauQuatre.commencerMusic(musicEnCours && nQuatre);
		panelDroit1.debloquerParametreScenes(nUn, uUnPartieDeux);
		niveauLoad.setVisible(false);
		panelLoad.setVisible(false);
		btnBackToHome.setEnabled(!nSelec);
		btnMute.setEnabled(!nSelec);
		selecNiv.setVisible(nSelec);

	}
	/**
	 * Méthode sert à lire un fichier qui contient les informations sur un niveau
	 * @param ois - ObjectInputStream par défaut
	 * @param fichierDeTravail - fichier de type File pour la lecture
	 */
	private void read(ObjectInputStream ois, File fichierDeTravail){
		particuleSet = new ArrayList<Particules>();
		ressortSet = new ArrayList<Ressort>();
		ballonSet = new ArrayList<BallonChasseur>();
		cercleSet = new ArrayList<BallonChasseur>();
		coccSet = new ArrayList<Shape>();
		plaqueSet = new ArrayList<Plaque>();
		champsSet = new ArrayList<ChampMagnetique>();
		Vector2d matrice = new Vector2d();
		Particules particulesExemple = new Particules(0,0);
		Ressort ressortExemple = new Ressort(0,0,0);
		BallonChasseur ballonExemple = new BallonChasseur(new Vector2d(),0,0,0,new Vector2d());
		Plaque plq = new Plaque(0,0,0);
		ChampMagnetique ch = new ChampMagnetique(0, 0, 0,0,0);
		Path2D linePath=new Path2D.Double();
		Color d = new Color(0,0,255);
		String c = "LOL";
		Particules charge;
		Ressort ressort;
		BallonChasseur ballon,cercle;
		Plaque plaque;
		ChampMagnetique champs;
		int nbTotal = 0;
		String totalParticules = " \n  ";
		String totalRessort = " \n  ";
		String totalBallon = " \n  ";
		String totalCercle = " \n  ";
		String totalPlaque = " \n  ";
		String totalChamps = " \n  ";
		try {
			ois = new ObjectInputStream(new FileInputStream(fichierDeTravail));
			nbTotal = (Integer) ois.readObject();
			for (int k=0; k< nbTotal; k++) { 
				Object objet = ois.readObject(); 
				if(objet.getClass() == matrice.getClass()){
					mat = (Vector2d)objet;
				} else if(objet.getClass() == particulesExemple.getClass()){
					charge = (Particules) objet; 
					particuleSet.add(charge);
					totalParticules = totalParticules + charge.getCharge() + " \n  ";
				}else if(objet.getClass() == ressortExemple.getClass()){
					ressort = (Ressort) objet; 
					ressortSet.add(ressort); 
					totalRessort = totalRessort + ressort.getLongeurMax() + " \n  ";
				}else if(objet.getClass() == ballonExemple.getClass()){
					if(((BallonChasseur) objet).getOrientation() == 5){
						ballon = (BallonChasseur) objet; 
						ballonSet.add(ballon);
						totalBallon = totalBallon + ballon.getPosition() + " \n  ";
					}else
						if(((BallonChasseur) objet).getOrientation() == 10){
							cercle = (BallonChasseur) objet; 
							cercleSet.add(cercle);
							totalCercle = totalCercle + cercle.getPosition() + " \n  ";
						}

				}else if(objet.getClass() == linePath.getClass()){
					coccSet.add((Shape) objet); 
					//System.out.println("yolo");
				}else if(objet.getClass() == d.getClass()){
					col = (Color)objet;
					//System.out.println("color choisi est "+col);
				}else if(objet.getClass() == c.getClass()){
					stringImage = (String)objet;
					//System.out.println("stringImage est : " + stringImage);
				}else if(objet.getClass() == plq.getClass()){
					plaque = (Plaque)objet;
					plaqueSet.add(plaque);
					totalPlaque = totalPlaque + plaque.getChampsElectrique() + " \n  ";
				}else if(objet.getClass() == ch.getClass()){
					champs = (ChampMagnetique)objet;
					champsSet.add(champs);
					totalChamps = totalChamps + champs.getChampsMagnetique() + " \n  ";
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
			//on exÃ©cutera toujours ceci, erreur ou pas
			try { 
				ois.close();
			}
			catch (IOException e) { 
				//System.out.println("Erreur rencontrÃ©e lors de la fermeture!"); 
			}
		}//fin finally

		if (nbTotal > 0) {
			message = "  Les charges sont : " + totalParticules + "\n  Les ressorts sont Ã  longeur " + totalRessort  + "\n  Les ballon sont Ã  : "+ totalBallon + "\n  Les cercle sont Ã  : " + totalCercle +" \n  En somme on a " + (nbTotal-2) +" elements \n ";
			JOptionPane.showMessageDialog(null,message);
		}
	}
	/**
	 * Méthode sert la lecture de fichier avec un chemin indiqué par JfileChooser
	 * @param file - un fichier pour la lecture
	 */
	private void lireText(File file){
		ObjectInputStream ois = null;
		File fichierDeTravail = file;
		read(ois, fichierDeTravail);

	}
	/**
	 * Méthode sert la lecture de fichier avec un nom donné par l'utilisateur
	 * @param name - le nom du fichier pour la lecture
	 */
	private void lireText(String name){
		final String NOM_FICHIER_ETUDIANTS = name;
		ObjectInputStream ois = null;
		File fichierDeTravail = new File( NOM_FICHIER_ETUDIANTS );
		read(ois, fichierDeTravail);
	}
	/**
	 * Méthode pour ajouter un Ã©couteur dans la classe
	 * @param mainPageListener - nom d'écouteur
	 */
	public void addMainPageListener(MainPageListener mainPageListener) {
		OBJETS_ENREGISTRES.add(MainPageListener.class, mainPageListener);
	}
	/**
	 * Cette méthode permet de choisir le niveau affiché
	 * @param nbNiveau le niveau qu'on veut afficher.
	 */
	private void choisirNiveau(int nbNiveau){
		boolean listeBoolean[] = {false,false,false,false};
		listeBoolean[nbNiveau] = true;
		if(nbNiveau > 0){
			setNiveauActuelle(nbNiveau + 1);
		}else{
			setNiveauActuelle(nbNiveau);
		}
		setVisibleNiveaux(listeBoolean[0],false,listeBoolean[1],listeBoolean[2],listeBoolean[3],false);
	}
	/** 
	 * Cette méthode sert à lire un niveau créé par le nom en String (chemin par défaut)
	 * @param c - le nom du fichier en String
	 */
	private void lireParDefaut(String c){
		lireText(c);
		setVisibleNiveaux(false,false,false,false,false,false);
		niveauLoad.setVisible(true);
		niveauLoad.recevoireObjet(particuleSet,ressortSet,ballonSet,cercleSet,coccSet,plaqueSet,champsSet,col,stringImage,mat);
		panelLoad.setVisible(true);
		panelLoad.setMessage(message);
	}
	/**
	 * Cette méthode sert à lire un niveau à partir d'un chemin indiqué
	 */
	private void lireEnChemin(){
		File file = null;
		int result;
		result = fileChooser.showOpenDialog(fileChooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			lireText(file);
			setVisibleNiveaux(false,false,false,false,false,false);
			niveauLoad.setVisible(true);
			niveauLoad.recevoireObjet(particuleSet,ressortSet,ballonSet,cercleSet, coccSet,plaqueSet,champsSet,col,stringImage,mat);
			panelLoad.setVisible(true);
			panelLoad.setMessage(message);
		} else if (result == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(null,"Source inconnue");
		}
	}
	/**
	 * Cette méthoder permet de commencer l'animation du sélecteur du niveau
	 */
	public void commencerLanimation() {
		selecNiv.recommencer();
	}
	/**
	 * Cette méthode permet de récupérer une liste de chaîne et l'envoyer dans le panel load.
	 * @param collectionNom2 une liste de chaîne.
	 */
	public void getCollectionNom(ArrayList<String> collectionNom2) {
		panelLoading.getCollectioNom(collectionNom2);
	}
	/**
	 * Cette méthode permet de modifier le tableau des niveaux actifs
	 * @param nivActuelle le niveau qui est affiché actuellement
	 */
	private void setNiveauActuelle(int nivActuelle){
		for(int i=0; i < affichageActuelle.length; i++){
			if(i == nivActuelle){
				affichageActuelle[i] = true;
			}else{
				affichageActuelle[i] = false;
			}
		}
	}

}
