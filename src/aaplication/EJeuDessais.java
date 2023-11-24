
package aaplication;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;

import java.awt.ComponentOrientation;
import java.awt.Component;

import javax.swing.DebugGraphics;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
/**
 * 
 * Classe de Jeu d'essais pour expliquer la fonctionalité de chaque niveau
 * 
 * @author Zi Zheng Wang; contribution de Kevin Takla
 * @version 3.0
 */
public class EJeuDessais extends JFrame {

	private JPanel contentPane;
	private JLabel lblJeuxEssais;
	private JEditorPane editorPaneUn;
	private JTabbedPane tabbedPane;
	private JEditorPane editorPaneDeux;
	private JEditorPane editorPaneTrois;
	private JEditorPane editorPaneQuatre;
	private JEditorPane editorPaneAnnexe;
	private JTabbedPane tabbedPanePincipal;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JEditorPane dtrpnInstructionLe;
	private JEditorPane dtrpnSource;
	private JEditorPane dtrpnAlphaDans;
	private JLabel lblInstructionScientifique;
	private JLabel lblConceptsScientifique;
	private JLabel lblSources;
	private JLabel labelApropos;
	private JTabbedPane tabbedPane_1;
	private JEditorPane editorPane_1;
	private JEditorPane dtrpnLinteractionEntreLes;
	private JScrollPane scrlPnlSelecSepctro;
	private JEditorPane dtrpnRk;
	private JEditorPane dtrpnCollisionLesQuatre;
	private JEditorPane editorPane_6;
	private JEditorPane editorPane_7;
	private JScrollPane scrollPane_4;
	private JScrollPane scrollPane_5;
	JScrollPane scrlPaneMagnetisme;
	private JEditorPane editPaneMagn;
	private JEditorPane dtrpnAvril;
	private JEditorPane dtrpnBetaAppuyez;
	private JScrollPane scrollPane_1;
	private JEditorPane dtrpnAppuyezSur;
    private BufferedImage img, imgCollision, imgCollisionDeux , imgRessort, imgParticule, champEle, champMag, forceElectrique, cycletron, cycletron2, forceMagnetique, PPIUC, collisionEcrit, collisionEcrit2, collisionEcrit3, imgConceptCyclotron, imgConceptLumiere, chargeElectrique, champElectrique, selecSpectro, imgMagnetisme, plaquePPIUCImg, coccimg, fosseImg, jeuxEssai1;
    private JEditorPane editorPaneSpectro;

	/**
	 * un frame pour afficher un tabbedPane pour les commentaires de jeux d'essais
	 */
	public EJeuDessais() {
		setResizable(false);
		setTitle("Jeu d'essais");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 615, 650);
		lireLesTextures();
		contentPane = new JPanel();
		contentPane.setBackground(new Color(95, 158, 160));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPanePincipal = new JTabbedPane(JTabbedPane.RIGHT);
		tabbedPanePincipal.setBounds(10, 11, 592, 599);
		contentPane.add(tabbedPanePincipal);
		
		panel = new JPanel();
		panel.setBackground(new Color(70, 130, 180));
		tabbedPanePincipal.addTab("Jeux d'essais", null, panel, null);
		panel.setLayout(null);
		
		lblJeuxEssais = new JLabel("Jeux d'essais");
		lblJeuxEssais.setBounds(20, 5, 132, 39);
		lblJeuxEssais.setForeground(new Color(248, 248, 255));
		panel.add(lblJeuxEssais);
		lblJeuxEssais.setFont(new Font("Tahoma", Font.BOLD, 19));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 50, 464, 531);
		panel.add(tabbedPane);
		
		
		editorPaneUn = new JEditorPane();
		editorPaneUn.setEditable(false);
		editorPaneUn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		editorPaneUn.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
		editorPaneUn.setText("Finale:\r\nSc\u00E8ne 1:\r\n1. Veuillez modifier les indices de r\u00E9fractions pour avoir les valeurs suivantes:\r\nMilieu vert: 1.33\r\nMileu Bleu: 2\r\n2. Veuillez placer vos objet comme l'image suivante:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\nSc\u00E8ne 2:\r\nIl n y a pas eu des modifications majeurs dans cette version, donc vous pouvez\r\nregarder le jeu d'essai de la version Alpha pour cette sc\u00E8ne.\r\n\r\n\r\nAlpha & Pr\u00E9-Alpha:\r\n\r\nSc\u00E8ne 1:\r\n1. Appuyez sur le bouton Liste des niveaux.\r\n2. Choisissez niveau 1\r\n3. Si ce n\u2019est pas le cas, veuillez modifier les indices de r\u00E9fraction afin que le milieu vert \r\nsoit : 1.33 et que le milieu bleu soit : 2.\r\n4. Veuillez placer les objets de cette fa\u00E7on : Placez le mirroir 1 sur le laser afin de toucher \r\nle bloc le plus bas. Placez le miroir 2 jusqu'a ce qu'il touche le bloc le plus \u00E0 gauche du \r\nniveau. Placez le miroir 3 afin que le laser puisse faire une rotation de 90 deg\u00E9e. Enfin, \r\nplacer le milieu bleu et faite le tourner jusqu'a ce qu'il touche le dernier bloc.\r\n\r\nP.S. veuillez \u00E9vitez les pointes du triangle sinon le programme va planter!\r\n\r\nSc\u00E8ne 2:\r\n1. Appuyez sur le bouton Liste des niveaux.\r\n2. Choisissez niveau 1 partie 2.\r\n3. D\u00E8s le d\u00E9but du niveau, un l\u00E9zard va sortir de l\u2019\u00E9cran.\r\n4. Les deux particules devraient etre n\u00E9gative, sinon modifiez-le! La coccinelle devrait \r\n\u00E9galement etre positive.\r\n5.Cliquer et deplacer la coccinelle \u00E0 l'aide de la souris afin de prendre une des deux\r\nparticules afin de l'ammener vers le point noir (tout en \u00E9vitant la langue du l\u00E9zard).\r\n6. Si une des particules a d\u00E9j\u00E0 \u00E9t\u00E9 placer sur le point noir, alors la coccinelle sera \r\nrammener vers le point de d\u00E9part et recommencer l'\u00E9tape 5.\r\n\r\nAutre possibilit\u00E9:\r\nSi vous changez la charge d'un des objets nulle ou la meme charge que l'une des\r\nparticules, alors la coccinelle ne pourra pas attirer la/les particules du niveau.");
		scrollPane = new JScrollPane(editorPaneUn);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Niveau Un", null, scrollPane, null);
		
		ImageIcon jeuxEssaiImg = new ImageIcon(jeuxEssai1);
		JLabel lblEssai = new JLabel();
		lblEssai.setBounds(10, 100,400, 298);
		lblEssai.setIcon(jeuxEssaiImg);		
		editorPaneUn.add(lblEssai);
	
		
		editorPaneDeux = new JEditorPane();
		editorPaneDeux.setEditable(false);
		editorPaneDeux.setText("Finale :\r\n1. Choisissez niveau 2 dans le s\u00E9lecteur de niveaux.\r\n2. Vous pouvez commencer l'animation du jeu tout de suite en appuyant\r\n sur le bouton D\u00E9marrer.\r\n3. Pour consulter le mode Sientifique, vous pouvez peser sur le bouton\r\n Scientifique\r\n4. Inter\u00E9action de collision entre coccinelle et les objets est \u00E9tablie\r\nBeta : \r\n1. Appuyez sur le bouton Liste des niveaux.\r\n2. Choisissez niveau 2.\r\n3. Vous pouvez commencer l'animation du jeu tout de suite en appuyant\r\n sur le bouton D\u00E9marrer.\r\n4. Pour consulter le mode Sientifique, vous pouvez peser sur le bouton\r\n Scientifique\r\n\r\nAlpha: \r\n1. Appuyez sur le bouton Liste des niveaux.\r\n2. Choisissez niveau 2.\r\n3. Vous pouvez commencer l'animation du jeu tout de suite en appuyant\r\n sur le bouton D\u00E9marrer.\r\n4. Pour consulter le mode Sientifique, vous pouvez peser sur le bouton\r\n Scientifique\r\n5. la musique du fond commence d\u00E8s que vous commencez l'animation.\r\n Elle finit lorsque vous changez de niveaux. (elle ne peut pas \u00EAtre interrompue\r\n par un clic sur le bouton Arreter)\r\n\r\n\r\n\r\n\r\nPr\u00E9-Alpha :\r\n1. Appuyez sur le bouton Liste des niveaux.\r\n2. Choisissez niveau 2.\r\n3. Vous pouvez commencer l'animation du jeu tout de suite en appuyant\r\n sur le bouton D\u00E9marrer.\r\n4. Pendant le d\u00E9roulement de l'animation, vous pouvez modifier la charge\r\n des trois ressorts dans le panneau des charges. Vous pouvez donner la\r\n charge au ressort. Vous avez le choix d'entre la charge n\u00E9gative, la charge\r\n positive et neutre.\r\n5. Vous pouvez aussi modifier les param\u00E8tres des trois ressorts sur le\r\n panneau de ressort \u00E0 la main droite pendant qu'il n'y a pas d'animation.\r\n Vous pouvez modifier la masse, le d\u00E9placement initial, la constante du\r\n ressort, le coefficient de frottement et la vitesse initiale.\r\n6. Vous pouvez toujours initialiser tous les param\u00E8tres et recommencer\r\n l'animation \u00E0 la compl\u00E8te en appuyant sur le bouton Initialiser.\r\n7. Vous pouvez aussi appuyer sur le bouton scientifique pour voir le mode \r\nscientifique de l'animation. Les valeurs de param\u00E8tres vont afficher sur \r\n le panneau scientifique \u00E0 la main droite.\r\n8. Pendant l'animation, vous pouvez arr\u00EAter l'animation en appuyant\r\n sur le bouton Arr\u00EAter.\r\n\r\n\r\n");
		scrollPane = new JScrollPane(editorPaneDeux);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Niveau Deux", null, scrollPane, null);
		
		editorPaneTrois = new JEditorPane();
		editorPaneTrois.setEditable(false);
		editorPaneTrois.setText("Finale : \r\n\r\n1. Choisissez niveau 3 dans le s\u00E9lecteur de niveau.\r\n2. Vous pouvez commencer l'animation du jeu tout de suite en appuyant\r\n sur  le bouton D\u00E9marrer.\r\n3. l'inter\u00E9action de la collision entre coccinlle et les ballons est \u00E9tablie\r\n\r\nBeta :\r\n1. Appuyez sur le bouton Liste des niveaux.\r\n2. Choisissez niveau 3.\r\n3. Vous pouvez commencer l'animation du jeu tout de suite en appuyant\r\n sur  le bouton D\u00E9marrer.\r\n\r\nAlpha: \r\n1. Appuyez sur le bouton Liste des niveaux.\r\n2. Choisissez niveau 3.\r\n3. Vous pouvez commencer l'animation du jeu tout de suite en appuyant\r\n sur  le bouton D\u00E9marrer.\r\n4. La musique du fond et le brutage de collision vont jou\u00E9s \r\nautomatiquement. Pour les faire arr\u00EAter, il faut changement de la sc\u00E8ne, le\r\n bouton Arr\u00EAter ne contient pas cette fonctionnalit\u00E9.\r\n\r\n\r\n\r\n\r\n\r\nPr\u00E9-Alpha :\r\n1. Appuyez sur le bouton Liste des niveaux.\r\n2. Choisissez niveau 3.\r\n3. Vous pouvez commencer l'animation du jeu tout de suite en\r\n appuyant sur le bouton D\u00E9marrer.\r\n4. Pendant le d\u00E9roulement de l'animation, vous pouvez modifier les\r\n param\u00E8tres des quatre billes et ceux de soleil sur le panneau de contr\u00F4le\r\n \u00E0 la main droite.\r\n5. Dans le panneau de contr\u00F4le, vous pouvez modifier la vitesse de rotation\r\n de Soleil et son sens de rotation. Vous pouvez \u00E9galement modifier la masse,\r\n le coefficient de frottement et l'impulsion des quatre billes. Vous pouvez\r\n modifier le temps de l'animation aussi.\r\n6. Vous pouvez toujours initialiser tous les param\u00E8tres et recommencer \r\n l'animation \u00E0 la compl\u00E8te en appuyant sur le bouton Initialiser.\r\n7. Vous pouvez aussi appuyer sur le bouton scientifique pour voir le mode \r\nscientifique de l'animation. Les valeurs de param\u00E8tres vont afficher sur \r\n le panneau scientifique \u00E0 la main droite.\r\n8. Pendant l'animation, vous pouvez arr\u00EAter l'animation en appuyant sur\r\n le bouton Arr\u00EAter.\r\n\r\n\r\n\r\n");
		scrollPane = new JScrollPane(editorPaneTrois);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Niveau Trois", null, scrollPane, null);
		

		editorPaneAnnexe = new JEditorPane();
		editorPaneAnnexe.setEditable(false);
		editorPaneAnnexe.setText("Finale :\r\n1. Dans le menu du fond \u00E0 votre main gauche, vous pouvez choisir la couleur\r\n du fond dans Color et d\u00E9cider l'image du fond dans Image\r\n2. vous pouvez commencer \u00E0 cr\u00E9er des objets dans ce plan rectangulaire.\r\n Dans cette version, on vous propose les objets suivants : ressort, particules,\r\n ballon, cercles, champ magn\u00E9tique, PPIUC, coccinelle et la pointe finale.\r\n\u25CF  Ressort est dans le Menu Ressort\r\n\u25CF  Ballon et Coccinelle sont dans le Menu G\u00E9om\u00E9trie\r\n\u25CF   Particules, PPIUC et champ magn\u00E9tique sont dans le Menu Chmap \r\n3. Vous pouvez modifier l'emplacement des objets en les s\u00E9lectionnant.\r\n En suite, vous pouvez les d\u00E9placer avec le souris. La premi\u00E8re apparition de\r\n l'objet sur le plan est au hasard.\r\n4. Une fois vous avez termin\u00E9 le niveau, vous pouvez le sauvegarder par\r\n l'option Sauvegarder la cr\u00E9ation ou Sauvegarder par d\u00E9faut dans l'option\r\n File qui est au c\u00F4t\u00E9 droit en haut du frame\r\n5. Pour jouer votre niveau, vous pouvez peser sur le bouton Jouer pour\r\n interchanger \u00E0 la sc\u00E8ne du jeu, ensuite pesez sur l'option Liste des niveaux ;\r\n choisi Lire ou Lire par d\u00E9faut pour commencer le jeu sur le niveau cr\u00E9e \r\n \u25CF L'option Lire faut indiquer le chemin de lecture, tandis que l'option Lire\r\n par d\u00E9faut n'est que \u00E0 donner le nom de votre fichier m\u00E9moris\u00E9\r\n6. Vous pouvez d\u00E9marrer le jeu en appuyant sur le bouton Animation\r\n\r\nAlpha :\r\nDans cette version, on vous propose deux chemins d'acc\u00E8s pour le niveau\r\n \u00E9diteur\r\n1) dans le menu principal, pesez sur le bouton Niveau \u00C9diteur \r\n2) dans la sc\u00E8ne du jeu, vous avez un bouton Cr\u00E9ation de niveau\r\n\r\n\u25CF Le frame de Nivau \u00C9diteur est redimensionnable, vous pouvez le modifier\r\n selon votre pr\u00E9f\u00E9rence\r\n\u25CF La forme rectangulaire dans Niveau \u00C9diteur est le plan vierge du\r\n niveau, toutes les modifications seront r\u00E9alis\u00E9es dessus.\r\n\u25CF Les objets que vous allez cr\u00E9er vont \u00EAtre m\u00E9moris\u00E9s si seulement s'ils sont\r\n compl\u00E8tement dans la forme rectangulaire (le plan du niveau); sinon, ils\r\n vont \u00EAtre ignor\u00E9s.\r\n\u25CF  Vous pouvez supprimer un objet sur le niveau que vous ne voulez plus en\r\n le s\u00E9lectionnant. Ensuite, vous l'attirez \u00E0 l'ext\u00E9rieur de la forme\r\n rectangulaire, ayant l'objet dans la zone grise ne va pas \u00EAtre m\u00E9moriser\r\n\r\n\r\n\r\n\r\nJeu d'essais \r\n1. Dans le menu du fond \u00E0 votre main gauche, vous pouvez choisir la couleur\r\n du fond dans Color et d\u00E9cider l'image du fond dans Image\r\n2. vous pouvez commencer \u00E0 cr\u00E9er des objets dans ce plan rectangulaire.\r\n Dans cette version, on vous propose les objets suivants : ressort, particules,\r\n ballon, cercles, coccinelle et la pointe finale.\r\n\u25CF  Ressort et Particules sont dans le Menu Ressort\r\n\u25CF  Ballon et Coccinelle sont dans le Menu G\u00E9om\u00E9trie\r\n3. Vous pouvez modifier l'emplacement des objets en les s\u00E9lectionnant.\r\n En suite, vous pouvez les d\u00E9placer avec le souris. La premi\u00E8re apparition de\r\n l'objet sur le plan est au hasard.\r\n4. Une fois vous avez termin\u00E9 le niveau, vous pouvez le sauvegarder par\r\n l'option Sauvegarder la cr\u00E9ation ou Sauvegarder par d\u00E9faut dans l'option\r\n File qui est au c\u00F4t\u00E9 droit en haut du frame\r\n5. Pour jouer votre niveau, vous pouvez peser sur le bouton Jouer pour\r\n interchanger \u00E0 la sc\u00E8ne du jeu, ensuite pesez sur l'option Liste des niveaux ;\r\n choisi Lire ou Lire par d\u00E9faut pour commencer le jeu sur le niveau cr\u00E9e \r\n \u25CF L'option Lire faut indiquer le chemin de lecture, tandis que l'option Lire\r\n par d\u00E9faut n'est que \u00E0 donner le nom de votre fichier m\u00E9moris\u00E9\r\n6. Vous pouvez d\u00E9marrer le jeu en appuyant sur le bouton Animation\r\n\r\n\r\n\r\n\r\n");
		scrollPane = new JScrollPane(editorPaneAnnexe);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Niveau Editeur", null, scrollPane, null);
		
		dtrpnAvril = new JEditorPane();
		dtrpnAvril.setEditable(false);
		dtrpnAvril.setFont(new Font("Tahoma", Font.PLAIN, 11));

		dtrpnAvril.setText(" 4 mai 2015\r\n La remise de version Finale\r\n Les nouvelles fonctionnalit\u00E9s \u00E9tablies dans cette version de notre jeux \r\n depuis la version Beta :\r\n\u25CF Panneau de s\u00E9lecteur de niveau\r\n\u25CF Panneau de s\u00E9lecteur de niveauLoad et niveauDemo\r\n\u25CF L'implantation de collision objets dans niveau deux et niveau trois\r\n\u25CF La correction de r\u00E9fraction de lumi\u00E8re dans niveau Un\r\n\u25CF La cr\u00E9ation des niveaux d\u00E9mo pour l'utilisateur\r\n\u25CF L'am\u00E9lioration de la fonctionalit\u00E9 du niveauLoad\r\n\u25CF Mode sicentifique dans le niveau Quatre\r\n\r\n  * Pour savoir plus, \u00E0 consulter dans  Niveaux \u00C9diteur, Niveau Un, Niveau Deux,\r\n     Niveau Trois, Niveau Quatre et Niveau Load de Jeux d'essais\r\n \r\n 23 avril 2015\r\n La remise de version Beta\r\n Les nouvelles fonctionnalit\u00E9s \u00E9tablies dans cette version de notre jeux \r\n depuis la version Alpha :\r\n  \u25CF Termination du niveau quatre du jeu\r\n  \u25CF Am\u00E9lioration dans le niveau un du jeu\r\n  \u25CF esth\u00E9tique am\u00E9lioration pour le mode scientifique dans le niveau un \r\n  et niveau deux\r\n  \u25CF Mode sicentifique dans le niveau load du jeu\r\n  \u25CF Mode boussole dans le niveau load du jeu\r\n  \u25CF Collision inter\u00E9action dans le niveau load du jeu\r\n  \u25CF Champ \u00E9lectrique dans le niveau load du jeu\r\n  \u25CF Implamentation des plaques de PPIUC et des champs \u00E9lectriques dans niveau Editeur \r\n  \r\n  * Pour savoir plus, \u00E0 consulter dans  Niveaux \u00C9diteur, Niveau Un, Niveau Deux,\r\n     Niveau Trois, Niveau Quatre et Niveau Load de Jeux d'essais\r\n2 avril 2015\r\n La remise de version Alpha\r\n Les nouvelles fonctionnalit\u00E9s \u00E9tablies dans cette version de notre jeux \r\n depuis la version Pr\u00E9Alpha :\r\n \r\n  \u25CF Mode scientifique pour le niveau un du jeu\r\n  \u25CF Mode scientifique pour le niveau deux du jeu\r\n  \u25CF Mode scientifique pour le niveau trois du jeu\r\n  \u25CF Emplacement des objets dans le niveau quatre du jeu (en construction...)\r\n  \u25CF Effet sonore dans le niveau deux\r\n  \u25CF Effet sonore et  brutage de collision dans le niveau trois\r\n  \u25CF niveau \u00E9diteur pour le jeu ( la fonctionnalit\u00E9 d'\u00E9crire dans un fichier et la\r\n  fonctionnalit\u00E9 de lire dans un fichier sont r\u00E9alis\u00E9es)\r\n  \u25CF Cr\u00E9ation du niveauLoad et du panelLoad pour le jeu (la sc\u00E8ne du jeu pour lire\r\n  un fichier ou il contient un niveau cr\u00E9e par l'utilisateur)\r\n \u25CF Le jeu d'essai pour le niveau 1 a \u00E9t\u00E9 modifi\u00E9 \r\n\r\n  * Pour savoir plus, \u00E0 consulter dans  Niveaux \u00C9diteur, Niveau Un, Niveau Deux,\r\n     Niveau Trois, Niveau Quatre de Jeux d'essais\r\n");

		tabbedPane.addTab("Nouveauté", null, dtrpnAvril, null);
		
		dtrpnBetaAppuyez = new JEditorPane();
		dtrpnBetaAppuyez.setEditable(false);
		dtrpnBetaAppuyez.setText("Finale : \r\n\r\n1- Appuyez sur le bouton liste de menu.\r\n2- Choisissez niveau Load\r\n3- Appuyez sur le bouton Commencer pour voir l'animation des obstracles\r\n4- Dans le fileChooser, vous pouvez indiqu\u00E9 le chemin de votre niveau sauvegard\u00E9\r\n5- Vous pouvez \u00E9galement voir le mode sicentifique  et le mode de boussole\r\n(le mode boussole est \u00E0 panel des particules)\r\n7-Vous pouvez tracer un chemin pour coccinelle pendant l'animation pour pouvoir\r\n le d\u00E9placer jusqu'au foss\u00E9 \u00E0 terminer le niveau\r\n\r\n\r\n\r\nBeta:\r\n\r\n1- Appuyez sur le bouton liste de menu.\r\n2- Choisissez niveau Load\r\n3- Appuyez sur le bouton Commencer pour voir l'animation des obstracles\r\n4- Dans le fileChooser, vous pouvez indiqu\u00E9 le chemin de votre niveau sauvegard\u00E9\r\n5- Vous pouvez \u00E9galement voir le mode sicentifique  et le mode de boussole\r\n(le mode boussole est \u00E0 panel des particules)");
		tabbedPane.addTab("Niveau Load", null, dtrpnBetaAppuyez, null);
		tabbedPane.setSelectedIndex(5);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Niveau Quatre"+ "", null, scrollPane_1, null);
		
		dtrpnAppuyezSur = new JEditorPane();
		dtrpnAppuyezSur.setEditable(false);
		dtrpnAppuyezSur.setText("Finale :\r\n\r\n1- Appuyez sur le bouton liste de menu.\r\n2- Choisissez niveau 4\r\n3- Avant tout, pour que vous puissiez voir un trajet gagnant veuillez mettre les \r\nvaleurs suivantes. (ou sinon appuyez sur aide jusqu\u2019\u00E0 ce que tous les\r\nparam\u00E8tres soient bloqu\u00E9s)\r\n\tCyclotron :\r\n\t\tPotentielle \u00E9lectrique : 40 V\r\n\t\tChamps magn\u00E9tique : 165 T\r\n\tS\u00E9lecteur de vitesse :\r\n\t\tChamps magn\u00E9tique : 18 T\r\n\t\tChamps \u00E9lectrique : 1000 N/C\r\n\tSpectrom\u00E8tre de masse :\r\n\t\tChamps magn\u00E9tique : -356T\r\n\tCoccinelle :\r\n\t\tMasse : 0.2 uM\r\n\t\tCharge : -3nC\r\n4- Dessiner la trajectoire afin que la coccinelle se rend jusqu\u2019au point vert(ou \u00E0 \r\nsortir de l\u2019\u00E9cran si vous \u00EAtes en mode rapproch\u00E9) sans se faire toucher par les\r\ncoquerelles.\r\n5- Observer le r\u00E9sultat!\r\n\r\n\r\nBeta:\r\n\r\n1- Appuyez sur le bouton liste de menu.\r\n2- Choisissez niveau 4\r\n3- Avant tout, pour que vous puissiez voir un trajet gagnant veuillez mettre les \r\nvaleurs suivantes. (ou sinon appuyez sur aide jusqu\u2019\u00E0 ce que tous les\r\nparam\u00E8tres soient bloqu\u00E9s)\r\n\tCyclotron :\r\n\t\tPotentielle \u00E9lectrique : 40 V\r\n\t\tChamps magn\u00E9tique : 165 T\r\n\tS\u00E9lecteur de vitesse :\r\n\t\tChamps magn\u00E9tique : 18 T\r\n\t\tChamps \u00E9lectrique : 1000 N/C\r\n\tSpectrom\u00E8tre de masse :\r\n\t\tChamps magn\u00E9tique : -356T\r\n\tCoccinelle :\r\n\t\tMasse : 0.2 uM\r\n\t\tCharge : -3nC\r\n4- Dessiner la trajectoire afin que la coccinelle se rend jusqu\u2019au point vert(ou \u00E0 \r\nsortir de l\u2019\u00E9cran si vous \u00EAtes en mode rapproch\u00E9) sans se faire toucher par les\r\ncoquerelles.\r\n5- Observer le r\u00E9sultat!\r\n\r\n\r\n\r\nAlpha:\r\n\r\nPour l'instant la seule chose que l'utilisateur peut faire est ceci:\r\n\r\n1- cliquer et glisser avec la souris sur la coccinelle vers\r\n     le point vert dans le cyclotron\r\n2- regarder!\r\n\r\n");
		scrollPane_1.setViewportView(dtrpnAppuyezSur);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(186, 85, 211));
		panel_1.setLayout(null);
		tabbedPanePincipal.addTab("Instruction", null, panel_1, null);
		
		dtrpnInstructionLe = new JEditorPane();
		dtrpnInstructionLe.setEditable(false);
		dtrpnInstructionLe.setText("Instructions :\r\nLe but g\u00E9n\u00E9ral de notre application La Grande Aventure de la Coccinelle \r\n\u00C9tincelante \u00E0 travers les Milieux Effrayants de la Physique Moderne est de\r\nd\u00E9montrer \u00E0 l\u2019utilisateur des diff\u00E9rents simulateurs de physique \u00E0 travers\r\nles quatre niveaux. Vous devez tracer un chemin valable avec la souris\r\nentre le d\u00E9but et le foss\u00E9 afin que la coccinelle puisse parcourir le niveau \r\nsans se faire toucher par les obstacles. Une fois que la coccinelle a atteint le \r\nfoss\u00E9, la coccinelle passera au niveau suivant. La coccinelle devra passer \u00E0\r\ntravers quatre niveaux avec plusieurs concepts scientifiques diff\u00E9rents .Vous\r\npouvez observer les forces et les ph\u00E9nom\u00E8nes en arri\u00E8re-plan avec le mode \r\nscientifique. Vous pouvez activer ce mode en appuyant sur le bouton \r\n\u00AB Scientifique \u00BB. Dans le niveau 2 et niveau 3 vous pouvez \u00E9galement \r\ncommencer l\u2019animation avec le bouton \u00AB Animation \u00BB sans avoir trac\u00E9 un\r\nchemin pour la coccinelle, parce que le principe de notre jeu est purement \r\nscientifique.\r\n\r\n\r\n\r\n\r\n\r\n\r\nNiveau un : La r\u00E9fraction de la lumi\u00E8re et l\u2019interaction entre deux particules\r\ncharg\u00E9es\r\n\r\nNiveau Deux : L\u2019oscillation du ressort et le champ \u00E9lectrique produit par\r\nplusieurs charges ponctuelles.\r\n\r\nNiveau Trois : La collision in\u00E9lastique et la collision \u00E9lastique entre les\r\nballons\r\n\r\nNiveau Quatre : L'\u00E9tude du cyclotron, du s\u00E9lecteur de vitesse et du\r\nspectrom\u00E8tre de masse\r\n\r\nDans chaque niveau du jeu, on vous offre un panneau de contr\u00F4le \u00E0 la \r\ndroite de la fen\u00EAtre pour que l\u2019utilisateur puisse modifier les param\u00E8tres afin \r\nd\u2019observer les effets sur le niveau. Vous pouvez \u00E9galement initialiser tous \r\nles modifications en appuyant sur le bouton \u00AB Initialiser \u00BB pour revenir \u00E0 l\u2019\u00E9tat\r\ninitial.\r\n\r\nAnnexe : une liste de niveaux est aussi offert en haut de la sc\u00E8ne du jeu \r\npour l\u2019utilisateur\r\npuisse parcourir tous les niveaux d\u2019une mani\u00E8re rapide.\r\n");
		
		scrollPane = new JScrollPane(dtrpnInstructionLe);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 76, 462, 507);
		panel_1.add(scrollPane);
		
		
		ImageIcon coccImg = new ImageIcon(coccimg);
		JLabel imgCocc = new JLabel();
		imgCocc.setBounds(10, 255, 87, 57);
		imgCocc.setIcon(coccImg);		
		dtrpnInstructionLe.add(imgCocc);
		
		ImageIcon fosse = new ImageIcon(fosseImg);
		JLabel imgFosse = new JLabel();
		imgFosse.setBounds(150, 255, 56, 57);
		imgFosse.setIcon(fosse);
		dtrpnInstructionLe.add(imgFosse);
		
		
		lblInstructionScientifique = new JLabel("Instruction :");
		lblInstructionScientifique.setForeground(new Color(255, 215, 0));
		lblInstructionScientifique.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblInstructionScientifique.setBounds(20, 5, 134, 39);
		panel_1.add(lblInstructionScientifique);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(244, 164, 96));
		panel_2.setLayout(null);
		tabbedPanePincipal.addTab("Concepts", null, panel_2, null);
		
		lblConceptsScientifique = new JLabel("Concepts scientifique :");
		lblConceptsScientifique.setForeground(new Color(0, 0, 255));
		lblConceptsScientifique.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblConceptsScientifique.setBounds(20, 5, 234, 39);
		panel_2.add(lblConceptsScientifique);
		
		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 50, 464, 531);
		panel_2.add(tabbedPane_1);
		
		ImageIcon imgLumiere = new ImageIcon(imgConceptLumiere);
		
		editorPane_1 = new JEditorPane();
		editorPane_1.setEditable(false);
		editorPane_1.setText("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\n\r\n\r\nSOURCES: http://profs.cmaisonneuve.qc.ca/svezina/nyc/note_nyc/NYC_XXI_Chap%202.1a.pdf \nhttp://profs.cmaisonneuve.qc.ca/svezina/nyc/note_nyc/NYC_XXI_Chap%202.2.pdf \nhttp://profs.cmaisonneuve.qc.ca/svezina/nyc/note_nyc/NYC_XXI_Chap%202.4.pdf");
		scrollPane = new JScrollPane(editorPane_1);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JLabel lblContenuLumiere = new JLabel();
		lblContenuLumiere.setBounds(10, 10, 430, 1000);
		lblContenuLumiere.setIcon(imgLumiere);
		editorPane_1.add(lblContenuLumiere);
		tabbedPane_1.addTab("Lois de réflexion et de réfraction de la lumière", null, scrollPane, null);
		
		dtrpnLinteractionEntreLes = new JEditorPane();
		dtrpnLinteractionEntreLes.setText("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
		scrollPane_2 = new JScrollPane(dtrpnLinteractionEntreLes);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane_1.addTab("Champ Électrique", null, scrollPane_2, null);
		
		ImageIcon imgchargeElectrique = new ImageIcon(chargeElectrique);
		JLabel labelchargeElectrique = new JLabel();
		labelchargeElectrique.setBounds(10, 20, 430, 358);
		labelchargeElectrique.setIcon(imgchargeElectrique);
		dtrpnLinteractionEntreLes.add(labelchargeElectrique);
		
		ImageIcon imgPar = new ImageIcon(imgParticule);
		JLabel labelImagePar = new JLabel();
		labelImagePar.setBounds(10, 398, 430, 86);
		labelImagePar.setIcon(imgPar);
		dtrpnLinteractionEntreLes.add(labelImagePar);
		
		ImageIcon imgchampElectrique = new ImageIcon(champElectrique);
		JLabel labelchampElectrique = new JLabel();
		labelchampElectrique.setBounds(10, 504, 430, 758);
		labelchampElectrique.setIcon(imgchampElectrique);
		dtrpnLinteractionEntreLes.add(labelchampElectrique);

		ImageIcon imgChampEle = new ImageIcon(champEle);
		JLabel labelImageEle = new JLabel();
		labelImageEle.setBounds(10, 1282, 430, 498);
		labelImageEle.setIcon(imgChampEle);
		dtrpnLinteractionEntreLes.add(labelImageEle);
		
		ImageIcon imgChmapMag = new ImageIcon(champMag);
		JLabel labelChmapMag = new JLabel();
		labelChmapMag.setBounds(10, 1800, 430, 396);
		labelChmapMag.setIcon(imgChmapMag);
		dtrpnLinteractionEntreLes.add(labelChmapMag);
		
		ImageIcon imgForceElec = new ImageIcon(forceElectrique);
		JLabel labelForceElec = new JLabel();
		labelForceElec.setBounds(10, 2216, 430, 293);
		labelForceElec.setIcon(imgForceElec);
		dtrpnLinteractionEntreLes.add(labelForceElec);
		
		ImageIcon imgPPIUC = new ImageIcon(plaquePPIUCImg);
		JLabel lblPPIUC= new JLabel();
		lblPPIUC.setBounds(10, 2515, 430, 800);
		lblPPIUC.setIcon(imgPPIUC);
		dtrpnLinteractionEntreLes.add(lblPPIUC);
		
		
		dtrpnRk = new JEditorPane();
		dtrpnRk.setText(" RK4 :\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
		scrollPane_3 = new JScrollPane(dtrpnRk);
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane_1.addTab("Oscillation du ressort", null, scrollPane_3, null);
		
		ImageIcon imgRes = new ImageIcon(imgRessort);
		JLabel labelImageR = new JLabel();
		labelImageR.setBounds(10, 20, 430, 720);
		labelImageR.setIcon(imgRes);
		dtrpnRk.add(labelImageR);
		
		editorPaneSpectro = new JEditorPane();
		editorPaneSpectro.setText("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
		editorPaneSpectro.setEditable(false);
		scrlPnlSelecSepctro = new JScrollPane(editorPaneSpectro);
		scrlPnlSelecSepctro.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrlPnlSelecSepctro.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane_1.addTab("Sélecteur de vitesse et Spectromètre de masse", null,scrlPnlSelecSepctro, null);
		
		ImageIcon imgSepSel = new ImageIcon(selecSpectro);
		JLabel lblSpeSel = new JLabel();
		lblSpeSel.setBounds(10, 10, 430 , 920);
		lblSpeSel.setIcon(imgSepSel);
		editorPaneSpectro.add(lblSpeSel);
		
		
		dtrpnCollisionLesQuatre = new JEditorPane();
		dtrpnCollisionLesQuatre.setText("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
		scrollPane_4 = new JScrollPane(dtrpnCollisionLesQuatre);
		scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane_1.addTab("Collision", null, scrollPane_4, null);
		
		ImageIcon imgThisZero = new ImageIcon(collisionEcrit);
		JLabel labelImageZero = new JLabel();
		labelImageZero.setBounds(10, 5, 430, 576);
		labelImageZero.setIcon(imgThisZero);
		dtrpnCollisionLesQuatre.add(labelImageZero);
		
		ImageIcon imgThisCo = new ImageIcon(imgCollision);
		JLabel labelImageCo = new JLabel();
		labelImageCo.setBounds(10, 591, 430, 451);
		labelImageCo.setIcon(imgThisCo);
		dtrpnCollisionLesQuatre.add(labelImageCo);
		
		ImageIcon imgThisZeroSec = new ImageIcon(collisionEcrit2);
		JLabel labelImageZeroSec = new JLabel();
		labelImageZeroSec.setBounds(10, 1052, 430, 369);
		labelImageZeroSec.setIcon(imgThisZeroSec);
		dtrpnCollisionLesQuatre.add(labelImageZeroSec);
		
		ImageIcon imgThisZeroThird = new ImageIcon(collisionEcrit3);
		JLabel labelImageZeroThird = new JLabel();
		labelImageZeroThird.setText("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
		labelImageZeroThird.setBounds(10, 1431, 430, 835);
		labelImageZeroThird.setIcon(imgThisZeroThird);
		dtrpnCollisionLesQuatre.add(labelImageZeroThird);
		
		ImageIcon imgThisCoDeux = new ImageIcon(imgCollisionDeux);
		JLabel labelImageCoDeux = new JLabel();
		labelImageCoDeux.setBounds(10, 2276, 430, 451);
		labelImageCoDeux.setIcon(imgThisCoDeux);
		dtrpnCollisionLesQuatre.add(labelImageCoDeux);
		
		editPaneMagn = new JEditorPane();
		editPaneMagn.setText("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
		editPaneMagn.setEditable(false);
		scrlPaneMagnetisme = new JScrollPane(editPaneMagn);
		scrlPaneMagnetisme.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrlPaneMagnetisme.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane_1.addTab("Champ Magnétique", null, scrlPaneMagnetisme, null);
		
		ImageIcon imgMagne = new ImageIcon(imgMagnetisme);
		JLabel lblMagne = new JLabel();
		lblMagne.setBounds(10, 10, 430, 1300);
		lblMagne.setIcon(imgMagne);
		editPaneMagn.add(lblMagne);
		
		editorPane_6 = new JEditorPane();
		editorPane_6.setText("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
		editorPane_6.setEditable(false);
		scrollPane_5 = new JScrollPane(editorPane_6);
		scrollPane_5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_5.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane_1.addTab("Cyclotron", null, scrollPane_5, null);
		
		ImageIcon imgCyclotron = new ImageIcon(imgConceptCyclotron);
		JLabel labelCycleTron = new JLabel();
		labelCycleTron.setBounds(10,10, 430, 800);
		labelCycleTron.setIcon(imgCyclotron);
		editorPane_6.add(labelCycleTron);
		
		ImageIcon imgCyclotronUn = new ImageIcon(cycletron);
		JLabel labelCycleTronUn = new JLabel();
		labelCycleTronUn.setBounds(10, 680, 430, 538);
		labelCycleTronUn.setIcon(imgCyclotronUn);
		editorPane_6.add(labelCycleTronUn);
		
		ImageIcon imgCyclotronDeux = new ImageIcon(cycletron2);
		JLabel labelCycleTronDeux = new JLabel();
		labelCycleTronDeux.setBounds(10, 1238, 430, 406);
		labelCycleTronDeux.setIcon(imgCyclotronDeux);
		editorPane_6.add(labelCycleTronDeux);
		
		panel_3 = new JPanel();
		panel_3.setBackground(new Color(224, 255, 255));
		panel_3.setLayout(null);
		tabbedPanePincipal.addTab("Sources", null, panel_3, null);
		
		dtrpnSource = new JEditorPane();
		dtrpnSource.setEditable(false);

		String message = "l'image au fond de niveauDeux : http://wallpaperswide.com/purple_texture-wallpapers.html ; source wallPaper \n" + "l'image au fond de nvieauTrois : https://www.google.ca/search?q=texture&espv=2&biw=1366&bih=667&source=lnms&tbm=isch&sa=X&ei=YpdDVfu6KsXfsAT7xIHACw&ved=0CAYQ_AUoAQ#tbm=isch&q=fabric+texture+design&revid=743110354&imgrc=bMPv5w-FnOURRM%253A%3BMjwP-FinmsH-oM%3Bhttp%253A%252F%252Fwww.dazzlingwallpaper.com%252Fimage%252F1389421790_fabric_pattern_hd_wallpaper.jpg%3Bhttp%253A%252F%252Fwww.dazzlingwallpaper.com%252Fpost_view_new.php%253Fpid%253D259%2526pnm%253DTexture%252520and%252520Abstract%252520Design%252520HD%252520Wallpapers%252520and%252520Background%252520in%2525201080p%3B1920%3B1200 \n" + "Le codage de RungeKutta et Derivee : Mathieu Payette \n" + "le son de clic: http://www.universal-soundbank.com/wav/sons-wav.htm \n" + "le son de briser bloc : http://soundbible.com/1658-Mirror-Breaking.html \n" + "l'image du lézard : http://www.renders-graphiques.fr/galerie/Reptiles-92/Lezard-75634.htm \n" + "image de la coqurelle: http://www.desertmuseum.org/images/nhsd_cockroch.gif \n";
		dtrpnSource.setText("Source des images du fond :\r\n  \u25CF l'image du fond de menu principal : dessin de zi zheng\r\n  \u25CF l'image du fond de nvieauUn : modification par zizheng, source originale : http://www.photo-wallpaper.net/14810-wallpaper-texture-stripes-obliquely-shadow-background-black \r\n  \u25CF l'image du fond de niveauDeux : modification par zi zheng, http://wallpaperswide.com/purple_texture-wallpapers.html ; \r\n  \u25CF l'image du fond de nvieauTrois : https://www.google.ca/search?q=texture&espv=2&biw=1366&bih=667&source=lnms&tbm=isch&sa=X&ei=YpdDVfu6KsXfsAT7xIHACw&ved=0CAYQ_AUoAQ#tbm=isch&q=fabric+texture+design&revid=743110354&imgrc=bMPv5w-FnOURRM%253A%3BMjwP-FinmsH-oM%3Bhttp%253A%252F%252Fwww.dazzlingwallpaper.com%252Fimage%252F1389421790_fabric_pattern_hd_wallpaper.jpg%3Bhttp%253A%252F%252Fwww.dazzlingwallpaper.com%252Fpost_view_new.php%253Fpid%253D259%2526pnm%253DTexture%252520and%252520Abstract%252520Design%252520HD%252520Wallpapers%252520and%252520Background%252520in%2525201080p%3B1920%3B1200 \r\n  \u25CF l'image du fond de niveauQuatre : http://wallpaperswide.com/purple_texture-wallpapers.html ; \r\n  \u25CF l'image du l\u00E9zard : http://www.renders-graphiques.fr/galerie/Reptiles-92/Lezard-75634.htm \r\n  \u25CF l'image de la coqurelle: http://www.desertmuseum.org/images/nhsd_cockroch.gif  \r\n \u25CF l'image du fond de NiveauEditeur : http://images.forwallpaper.com/files/images/d/d156/d156d593/227738/texture-background-metal-black-perforation.jpg  \r\n\r\n\r\n\r\nSource de cotage : \r\n  \u25CF Le codage de RungeKutta et Derivee : Mathieu Payette \r\n\r\n\r\n\r\nSource de son :\r\n  \u25CF le son de clic: http://www.universal-soundbank.com/wav/sons-wav.htm \r\n  \u25CF le son de briser bloc : http://soundbible.com/1658-Mirror-Breaking.html \r\n  \u25CF le son de collision central au niveau3 : http://s1download-universal-soundbank.com/mp3/sounds/883.mp3\r\n  \u25CF le son de laser au niveau3 : http://www.sound-fishing.net/bruitages_science-fiction.html\r\n  \u25CF le musique fond pour niveau2 : http://yun.baidu.com/wap/link?uk=790786525&shareid=3107684138&third=0 \u547D\u8FD0\u5341\u5B57\u67B6\r\n  \u25CFle musique fond pour niveau3 :http://5sing.kugou.com/bz/down/1407124### \u8352\u57CE\u56DE\u5ECA\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n  Les images utilis\u00E9es dans la conception scientifique :    \r\n  \u25CF  http://profs.cmaisonneuve.qc.ca/svezina/\r\n  section 203 - NYB : \u00C9lectricit\u00E9 et magn\u00E9tisme\r\n  section Compl\u00E9ment : Physique-Informatique\r\n  de Programme : Sciences informatiques et \r\n  math\u00E9matiques (SIM)\r\n\r\n  Images utilis\u00E9es pour le s\u00E9lecteur de menu:\r\n \r\n cr\u00E9er niveau : https://www.google.ca/search?q=add+icon&hl=en&biw=1920&bih=963&source=lnms&tbm=isch&sa=X&ei=W4w6Vb2JGoy0sAS7nYD4Bg&ved=0CAYQ_AUoAQ#imgrc=NDF06xnmqfrxxM%253A%3Bz78Eh5Rnvt7ggM%3Bhttps%253A%252F%252Fcdn3.iconfinder.com%252Fdata%252Ficons%252Ffiles%252F100%252F236971-file_document_add_plus-512.png%3Bhttps%253A%252F%252Fwww.iconfinder.com%252Ficons%252F114727%252Fadd_document_file_plus_icon%3B512%3B512\r\n\r\n menu principale: https://www.google.ca/search?q=return+icon+black+png&hl=en&source=lnms&tbm=isch&sa=X&ei=QI06Vd6dGIuSsQSR-IHABQ&ved=0CAcQ_AUoAQ&biw=1920&bih=963#imgrc=DG-cODvB2nZw4M%253A%3BEyPHPwJzQzeUWM%3Bhttp%253A%252F%252Fwww.tuxxin.com%252Fwp-content%252Fuploads%252F2013%252F09%252Fhome-icon.png%3Bhttp%253A%252F%252Fimgarcade.com%252F1%252Freturn-home-icon%252F%3B360%3B360\r\n\r\ncharger un niveau: https://www.google.ca/search?q=add+icon&hl=en&biw=1920&bih=963&source=lnms&tbm=isch&sa=X&ei=W4w6Vb2JGoy0sAS7nYD4Bg&ved=0CAYQ_AUoAQ#hl=en&tbm=isch&q=load++files+icon+png+black&imgdii=VWTgW_6EdnA8yM%3A%3BVWTgW_6EdnA8yM%3A%3BOA7_d8F8MzU0MM%3A&imgrc=VWTgW_6EdnA8yM%253A%3B04uEN7r_4IUMxM%3Bhttps%253A%252F%252Fcdn0.iconfinder.com%252Fdata%252Ficons%252Fhuge-basic-icons%252F512%252FOpen_folder.png%3Bhttp%253A%252F%252Fpixgood.com%252Fload-file-icon.html%3B512%3B512\r\n\r\nmusique de fond du niveau 1 (sc\u00E8ne 1): https://www.youtube.com/watch?v=_R4-eL3IdhE entre (0:00 et 5:00)\r\n\r\nmusique de fond du niveau 1(sc\u00E8ne 2) : https://www.youtube.com/watch?v=jUaLw5qqOHE\r\n\r\nmusique de fond du niveau 4 (Version originale): https://www.youtube.com/watch?v=J2ZW0hXtGw8");

		
		scrollPane_2 = new JScrollPane(dtrpnSource);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(10, 50, 462, 507);
		panel_3.add(scrollPane_2);
		
		ImageIcon imgThis = new ImageIcon(img);
		JLabel labelImage = new JLabel();
		labelImage.setBounds(50, 790, 326, 203);
		labelImage.setIcon(imgThis);
		dtrpnSource.add(labelImage);
		
		lblSources = new JLabel("Sources :");
		lblSources.setForeground(new Color(148, 0, 211));
		lblSources.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblSources.setBounds(20, 5, 99, 39);
		panel_3.add(lblSources);
		
		panel_4 = new JPanel();
		panel_4.setBackground(new Color(128, 128, 0));
		panel_4.setLayout(null);
		tabbedPanePincipal.addTab("À propos", null, panel_4, null);
		
		dtrpnAlphaDans = new JEditorPane();
		dtrpnAlphaDans.setToolTipText("kopee raite");
		dtrpnAlphaDans.setEditable(false);
		dtrpnAlphaDans.setText("Version finale :\r\nC'est la version finale de notre application. Elle contient toutes\r\nles fonctionalit\u00E9s qu'on a mentionn\u00E9es dans la soumission du projet. \r\nLa plupart des bugs ont \u00E9t\u00E9 r\u00E9gl\u00E9s.\r\nLe javadoc est mit \u00E0 jour\r\nLes d\u00E9mos de nvieauLoad ont \u00E9t\u00E9 mis \u00E0 jour\r\n\r\nMise \u00E0 jour 2015-05-04\r\n\r\n\r\nBeta :\r\nCette version contient toutes les fonctionnalit\u00E9s \r\npr\u00E9vues pour l\u2019application. Par contre, il y a encore \r\nplusieurs bugs  \u00E0 r\u00E9gler. \r\n\r\nTous les niveaux sont compl\u00E9t\u00E9s, mais certains \r\nd\u2019entre eux ont plusieurs bugs (comme le \r\nniveau 1). L\u2019\u00E9diteur a \u00E9t\u00E9 compl\u00E9ter, mais\r\ncontient quelques petits bugs. Le contenu\r\nscientifique a \u00E9t\u00E9 cr\u00E9\u00E9.\r\n\r\nLe jeu d\u2019essai du niveau 4 a \u00E9t\u00E9 modifi\u00E9.\r\n\r\nLe Javadoc est mise \u00E0 jour.\r\n \r\n\r\nAlpha : \r\n Cette version de notre projet contient environ 80% des\r\n fonctionnalit\u00E9s pr\u00E9vues pour l'application. L'application\r\n d\u00E9montre possiblement plusieurs bugs.\r\n\r\n Les modes scientifiques sont \u00E9tablis \u00E0 part celui de Niveau\r\n Quatre ( en construction) et celui de Niveau Load\r\n\r\n La documentation Javadoc est compl\u00E9t\u00E9e pour toutes les classes\r\n (les classes subissent une modification depuis la version Pr\u00E9-Alpha\r\n  vont \u00EAtre \u00E0 la version 1.2, sinon ils sont \u00E0 version 1.0 )\r\n\r\n Le .jar ex\u00E9cutable de notre projet est enlev\u00E9\r\n\r\n Le projet de Zi Zheng Wang et Kevin Takla\r\n                                                                                               \r\n                           Mise \u00E0 jour le 23 AVRIL 2015\r\n");
		
		scrollPane_3 = new JScrollPane(dtrpnAlphaDans);
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_3.setBounds(10, 76, 462, 507);
		panel_4.add(scrollPane_3);
		
		labelApropos = new JLabel("À propos :");
		labelApropos.setForeground(new Color(240, 255, 240));
		labelApropos.setFont(new Font("Tahoma", Font.BOLD, 19));
		labelApropos.setBounds(20, 5, 234, 39);
		panel_4.add(labelApropos);
	}
	public void changeIndex(int a){
		tabbedPanePincipal.setSelectedIndex(a);
	}
	/**
	 * Méthode sert à lire les images pour inserer dans les éditor panes
	 */
	private void lireLesTextures() {
		//lecture des images qui servent de texture
		URL url = getClass().getClassLoader().getResource("xiaoyue.jpg");
		URL url1 = getClass().getClassLoader().getResource("collision.png");
		URL url2 = getClass().getClassLoader().getResource("collisionDeux.png");
		URL url3 = getClass().getClassLoader().getResource("ressort.png");
		
		URL url4 = getClass().getClassLoader().getResource("particuleAtt.png");
		URL url5 = getClass().getClassLoader().getResource("ChampEle.png");
		URL url6 = getClass().getClassLoader().getResource("ChampMag2.png");
		URL url7 = getClass().getClassLoader().getResource("ForceElectrique.png");
		
		URL url8 = getClass().getClassLoader().getResource("selecSpectroConcept.png");
		URL url9 = getClass().getClassLoader().getResource("cycleTron2.png");
		URL url10 = getClass().getClassLoader().getResource("forceMagnetique.png");
		URL url11 = getClass().getClassLoader().getResource("PPIUC.png");
		
		URL url12 = getClass().getClassLoader().getResource("collisionEcrit.PNG");
		URL url13 = getClass().getClassLoader().getResource("collisionEcrit2.PNG");
		URL url14 = getClass().getClassLoader().getResource("collisionEcrit3.PNG");
		
		URL url15 = getClass().getClassLoader().getResource("CyclotronConcept.png");
		URL url16 = getClass().getClassLoader().getResource("lumiereConcept.png");
		
		URL url17 = getClass().getClassLoader().getResource("chargeElectrique.PNG");
		URL url18 = getClass().getClassLoader().getResource("champEletrique.PNG");
		URL url19 = getClass().getClassLoader().getResource("selecSpectroConcept.png");
		URL url20 = getClass().getClassLoader().getResource("magnetismeConcept.png");
		URL url21 =  getClass().getClassLoader().getResource("PPIUCconcept.png");
		URL url22 =  getClass().getClassLoader().getResource("imageTutoCocc.png");
		URL url23 =  getClass().getClassLoader().getResource("fosseImg.png");
		URL url24 = getClass().getClassLoader().getResource("jeuxDessai1.png");
		try {
			img = ImageIO.read(url);
			imgCollision = ImageIO.read(url1);
			imgCollisionDeux = ImageIO.read(url2);
			imgRessort  = ImageIO.read(url3);
			imgParticule = ImageIO.read(url4);
			champEle = ImageIO.read(url5);
			champMag = ImageIO.read(url6);
			forceElectrique = ImageIO.read(url7);
			
			cycletron = ImageIO.read(url8);
			cycletron2 = ImageIO.read(url9);
			forceMagnetique = ImageIO.read(url10);
			PPIUC = ImageIO.read(url11);
			
			collisionEcrit = ImageIO.read(url12);
			collisionEcrit2 = ImageIO.read(url13);
			collisionEcrit3 = ImageIO.read(url14);
			
			imgConceptCyclotron = ImageIO.read(url15);
			imgConceptLumiere = ImageIO.read(url16);
			
			chargeElectrique = ImageIO.read(url17);
			champElectrique = ImageIO.read(url18);
			selecSpectro = ImageIO.read(url19);
			imgMagnetisme = ImageIO.read(url20);
			plaquePPIUCImg = ImageIO.read(url21);
			coccimg = ImageIO.read(url22);
			fosseImg= ImageIO.read(url23);
			
			jeuxEssai1= ImageIO.read(url24);
			
		} catch (Exception e) {
			//System.out.println("Erreur de lecture du fichier de texture");
		}
	}
}
