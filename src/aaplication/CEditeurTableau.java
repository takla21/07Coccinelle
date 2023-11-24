
package aaplication;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import javax.swing.JButton;

import ecouteur.MainPageListener;
import ecouteur.SelecNiveauListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import sceneanimation.EditeurNiveau;

import javax.swing.JMenuBar;

import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
/**
 * 
 * Classe d'éditeur de niveau ou l'utilisateur peut créer un nvieau
 * 
 * @author Zi Zheng Wang
 * @version 3.0
 */
public class CEditeurTableau extends JFrame {

	private JPanel contentPane;
	private JButton btnBack;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList(); //collection
	private EditeurNiveau editeurNiveau;
	private JMenuBar menuBar;
	private JButton btnPlay;
	private EJeuDessais jeuDessais = new EJeuDessais();
	private ArrayList<String> collectionNom;
	/**
	 * Create the frame.
	 */
	public CEditeurTableau() {
		setForeground(Color.WHITE);
		setTitle("\u00C9diteur de niveau");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		menuBar.setForeground(SystemColor.windowBorder);
		menuBar.setBounds(0, 0, 995, 25);
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		editeurNiveau = new EditeurNiveau();
		editeurNiveau.addSelecNiveauListener(new SelecNiveauListener(){
			public void selectionNiveau(int nbNiv) {
			}
			public void load() {
			}
			public void loadParDefaut(String c) {
			}
			public void returnMenu() {
			}
			public void creationNiveau() {
			}
			public void envoyerListNom(ArrayList<String> collectionNom) {
				loadingNom(collectionNom);
				for(MainPageListener ecout : OBJETS_ENREGISTRES.getListeners(MainPageListener.class) ) {
					ecout.envoyerCollectioNNom(collectionNom);
				}
			}
		});
		editeurNiveau.setBounds(2, 35, 990, 735);
		contentPane.add(editeurNiveau, BorderLayout.CENTER);
		
		btnBack = new JButton("Retour au menu");
		btnBack.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnBack);
		btnBack.setFocusable(false);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				for(MainPageListener ecout : OBJETS_ENREGISTRES.getListeners(MainPageListener.class) ) {
					ecout.retournerAuMenuP();
				}
			}
		});
		
		btnPlay = new JButton("Jouer");
		btnPlay.setBackground(Color.LIGHT_GRAY);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!editeurNiveau.getDejaSauver()){
					editeurNiveau.sauvegarder();
					editeurNiveau.setDejaSauver(true);
				}
				for(MainPageListener ecout : OBJETS_ENREGISTRES.getListeners(MainPageListener.class) ) {
					ecout.activerBTableauJeu();
					ecout.envoyerCollectioNNom(collectionNom);
				}
				setVisible(false);
			}
		});
		btnPlay.setFocusable(false);
		menuBar.add(btnPlay);
		
		JMenu mnAide_1 = new JMenu("Aide");
		menuBar.add(mnAide_1);
		
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
			public void actionPerformed(ActionEvent e) {
				jeuDessais.setVisible(true);
				jeuDessais.changeIndex(1);
			}
		});
		mnAide_1.add(mntmInstructionCompltes);
		
		JMenuItem mntmConceptsScientifiques = new JMenuItem("Concepts scientifiques");
		mntmConceptsScientifiques.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				//JOptionPane.showMessageDialog(null, "l'image au fond de niveauDeux : http://wallpaperswide.com/purple_texture-wallpapers.html ; source wallPaper \n" + "l'image au fond de nvieauTrois : http://www.photo-wallpaper.net/14810-wallpaper-texture-stripes-obliquely-shadow-background-black \n" + "Le codage de RungeKutta et Derivee : Mathieu Payette \n" + "le son de clic: http://www.universal-soundbank.com/wav/sons-wav.htm \n" + "le son de briser bloc  http://soundbible.com/1658-Mirror-Breaking.html \n" + "l'image du lézard : http://www.renders-graphiques.fr/galerie/Reptiles-92/Lezard-75634.htm \n", "Sources", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnAide_1.add(mntmSources);
		
		JMenuItem mntmPropos = new JMenuItem("\u00C0 propos");
		mntmPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jeuDessais.setVisible(true);
				jeuDessais.changeIndex(4);
			}
		});
		mnAide_1.add(mntmPropos);
		collectionNom = new ArrayList<String>();
	}
	/**
	 * Méthode sert à mémoriser la liste des noms de niveau crées par l'utilisateur
	 * @param collectionNom une collection de chaines
	 */
	public void loadingNom(ArrayList<String> collectionNom){
		this.collectionNom = collectionNom;
	}
	/**
	 * Méthode sert à ajouter main page listener dans cette classe
	 * @param mainPageListener type d'écouteur
	 */
	public void addMainPageListener(MainPageListener mainPageListener) {
		OBJETS_ENREGISTRES.add(MainPageListener.class, mainPageListener);
		
	}
}
