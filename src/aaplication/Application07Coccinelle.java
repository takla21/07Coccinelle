
package aaplication;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ecouteur.MainPageListener;
/**
 *  
 * Classe de menu principal pour le jeu coccienlle, démarrer l'application
 * 
 * 
 * @author Zi Zheng Wang
 * @version 3.0
 */
public class Application07Coccinelle extends JFrame {

	private JPanel contentPane;
	private JButton btnNewButton;
	private JButton btnModeDditeur;
	private JButton btnOption;
	private JButton btnQuitter;
	private BTableauJeu tableauJeu;
	private DPanelLoading panelParametre;
    private CEditeurTableau editeurTableau;
    private JLabel lblBackGround;
	private Image img = null;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	private JMenuItem menuItem_1;
	private JMenuItem menuItem_2;
	private JMenuItem menuItem_3;
	private JMenuItem menuItem_4;
	private EJeuDessais jeuDessais = new EJeuDessais();
	/**
	 * Launch application
	 * @param args souris
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application07Coccinelle frame = new Application07Coccinelle();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * un frame qui est le menu principal du jeu coccinelle, il contient principalement un menu Aide et quatre boutons (Jouer, Zone d'édition, Option, Quitter)
	 */
	public Application07Coccinelle() {
		URL urlFichier = getClass().getClassLoader().getResource("can.wav");
		AudioClip monClip = Applet.newAudioClip(urlFichier);
		// chemin de backGround image
		URL fich1 = getClass().getClassLoader().getResource("backGround1.png");
		// img loader
		if (fich1 == null) {
			JOptionPane.showMessageDialog(null,	"Fichier d'image introuvable!");
		} else {
			//terminer l'acquisition de l'image ici
			// img = ...
			try{
				img=ImageIO.read(fich1);
			}
			catch(IOException e){
				//System.out.println("Erreur pendant la lecture du fichier d'image");
			}
		}
		tableauJeu = new BTableauJeu();
		tableauJeu.addMainPageListener(new MainPageListener(){
			public void retournerAuMenuP() {
				setVisible(true);	
			}
			public void activerBTableauJeu() {	
			}
			public void activerCEditeur() {
				if (!editeurTableau.isVisible()) {
					editeurTableau.setVisible(true);
					setVisible(false);
				}
				
			}
			public void activerOption() {
				if (!panelParametre.isVisible()) {
					panelParametre.setVisible(true);
				}
			}
			@Override
			public void envoyerCollectioNNom(ArrayList<String> collectionNom) {
				
			}
		});
		panelParametre = new DPanelLoading();
		editeurTableau = new CEditeurTableau();
		editeurTableau.addMainPageListener(new MainPageListener(){
			public void retournerAuMenuP() {
				setVisible(true);	
			}
			public void activerBTableauJeu() {
				if (!tableauJeu.isVisible()) {
					tableauJeu.setVisible(true);
					tableauJeu.commencerLanimation();
					setVisible(false);
				}
			}
			public void activerCEditeur() {
			}
			public void activerOption() {
			}
			@Override
			public void envoyerCollectioNNom(ArrayList<String> collectionNom) {
				tableauJeu.getCollectionNom(collectionNom);
			}
		});
		setTitle("La grande aventure de la coccinelle ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 550);
		setResizable(false);
		
		menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.menu);
		setJMenuBar(menuBar);
		
		menu = new JMenu("Aide");
		menu.setForeground(new Color(0, 0, 128));
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Jeux d'essais");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jeuDessais.setVisible(true);
				jeuDessais.changeIndex(0);
			}
		});
		menu.add(menuItem);
		
		menuItem_1 = new JMenuItem("Instruction compl\u00E8tes");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jeuDessais.setVisible(true);
				jeuDessais.changeIndex(1);
			}
		});
		menu.add(menuItem_1);
		
		menuItem_2 = new JMenuItem("Concepts scientifiques");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jeuDessais.setVisible(true);
				jeuDessais.changeIndex(2);
			}
		});
		menu.add(menuItem_2);
		
		menuItem_3 = new JMenuItem("Sources");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jeuDessais.setVisible(true);
				jeuDessais.changeIndex(3);
				//JOptionPane.showMessageDialog(null, "l'image au fond de niveauDeux : http://wallpaperswide.com/purple_texture-wallpapers.html ; source wallPaper \n" + "l'image au fond de nvieauTrois : http://www.photo-wallpaper.net/14810-wallpaper-texture-stripes-obliquely-shadow-background-black \n" + "Le codage de RungeKutta et Derivee : Mathieu Payette \n" + "le son de clic: http://www.universal-soundbank.com/wav/sons-wav.htm \n" + "le son de briser bloc : http://soundbible.com/1658-Mirror-Breaking.html \n" + "l'image du lézard : http://www.renders-graphiques.fr/galerie/Reptiles-92/Lezard-75634.htm \n" + "image de la coqurelle: http://www.desertmuseum.org/images/nhsd_cockroch.gif \n", "Sources", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menu.add(menuItem_3);
		
		menuItem_4 = new JMenuItem("\u00C0 propos");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jeuDessais.setVisible(true);
				jeuDessais.changeIndex(4);
			}
		});
		menu.add(menuItem_4);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnNewButton = new JButton("Jouer");
		btnNewButton.setForeground(Color.GREEN);
		btnNewButton.setOpaque(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				btnNewButton.setForeground(Color.BLUE);
				btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
			}
			public void mouseExited(MouseEvent e) {
				btnNewButton.setForeground(Color.GREEN);
				btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		btnNewButton.setFocusPainted(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monClip.play();
				if (!tableauJeu.isVisible()) {
					tableauJeu.setVisible(true);
					tableauJeu.commencerLanimation();
					setVisible(false);
				}
			}
		});
		btnNewButton.setBounds(95, 105, 235, 43);
		contentPane.add(btnNewButton);
		
		btnModeDditeur = new JButton("Zone d'\u00E9dition");
		btnModeDditeur.setOpaque(false);
		btnModeDditeur.setContentAreaFilled(false);
		btnModeDditeur.setBorderPainted(false);
		btnModeDditeur.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				btnModeDditeur.setForeground(Color.BLUE);
				btnModeDditeur.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
			}
			public void mouseExited(MouseEvent e) {
				btnModeDditeur.setForeground(Color.gray);
				btnModeDditeur.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
			}
		});
		btnModeDditeur.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		btnModeDditeur.setForeground(Color.gray);
		btnModeDditeur.setFocusPainted(false);
		btnModeDditeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monClip.play();
				if (!editeurTableau.isVisible()) {
					editeurTableau.setVisible(true);
					setVisible(false);
				}
			}
		});
		btnModeDditeur.setBounds(95, 195, 235, 43);
		contentPane.add(btnModeDditeur);
		
		btnOption = new JButton("\u00C0 propos");
		btnOption.setOpaque(false);
		btnOption.setContentAreaFilled(false);
		btnOption.setBorderPainted(false);
		btnOption.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				btnOption.setForeground(Color.BLUE);
				btnOption.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
			}
			public void mouseExited(MouseEvent e) {
				btnOption.setForeground(Color.gray);
				btnOption.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
			}
		});
		btnOption.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		btnOption.setForeground(Color.gray);
		btnOption.setFocusPainted(false);
		btnOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monClip.play();
				if (!jeuDessais.isVisible()) {
					jeuDessais.setVisible(true);
					jeuDessais.changeIndex(4);
				}
				
			}

		});
		btnOption.setBounds(95, 285, 235, 43);
		contentPane.add(btnOption);
		
		btnQuitter = new JButton("Quitter");
		btnQuitter.setOpaque(false);
		btnQuitter.setContentAreaFilled(false);
		btnQuitter.setBorderPainted(false);
		btnQuitter.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				btnQuitter.setForeground(Color.RED);
				btnQuitter.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
			}
			public void mouseExited(MouseEvent e) {
				btnQuitter.setForeground(Color.gray);
				btnQuitter.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
			}
		});
		btnQuitter.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		btnQuitter.setForeground(Color.gray);
		btnQuitter.setFocusPainted(false);
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monClip.play();
				System.exit(0);
			}
		});
		btnQuitter.setBounds(95, 375, 235, 43);
		contentPane.add(btnQuitter);
		//set BackGround image
		ImageIcon imgThisImg = new ImageIcon(fich1);
		lblBackGround = new JLabel("");
		lblBackGround.setBounds(0, 0, imgThisImg.getIconWidth(),imgThisImg.getIconHeight());
		lblBackGround.setIcon(imgThisImg);
		contentPane.add(lblBackGround);
	}
}
