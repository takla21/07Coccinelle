/**
 * 
 * Pannel de controle pour NiveauTrois
 * 
 * @author Zi Zheng Wang
 * @version 1.0
 */
package sceneanimation;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.event.EventListenerList;

import ecouteur.Niveau3Listener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.Font;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;

import java.awt.Component;

import javax.swing.JSlider;
import javax.swing.JTabbedPane;

import physique.Vector2d;

import java.awt.SystemColor;
import java.net.URL;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
/**
 * Classe panelDroit3 du jeu de coccinelle, le panelDroit3 est associé avec le niveauTrois pour afficher et loader les paramètres
 * @author Zizheng Wang
 * @version 2.0
 */
public class PanelDroit3 extends JPanel {
	private JButton btnAnimation;
	private JButton btnArreter;
	private JButton btnReinitialiser;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList(); //collection
	private JRadioButton rdbtnGauche;
	private JRadioButton rdbtnDroit;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JSpinner spinnerVitesse;
	private JPanel panelSoleil;
	private JComboBox comboBox;
	private JSpinner masseA;
	private JPanel panel;
	private JSpinner masseB;
	private JSpinner masseC;
	private JSpinner masseD;
	private JLabel label_Materieux;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel lblA;
	private JLabel lblB;
	private JLabel lblC;
	private JLabel lblD;
	private JButton buttonScientifique;
	private JSlider slider;
	private JLabel lblGrandeurDimpulsion;
	private JLabel lblTourne;
	private JLabel lblTourne_1;
	private JTabbedPane tabbedPane_1;
	private JPanel panel_1;
	private boolean scientifique = false;
	private JPanel panel_2;
	private JLabel lbl_AngleRotation;
	private JLabel lblLeRoueCentral;
	private JLabel lblRotationVitesse;
	private JPanel panel_3;
	private JPanel panel_4;
	private JLabel lblVitesseA;
	private JLabel lblBallonC;
	private JLabel lblVitesseC;
	private JLabel lblBallonB;
	private JLabel lblBallond;
	private JLabel labelVitesseB;
	private JLabel labelVitesseD;
	private JLabel lblCoeficientDeCollision;
	private JLabel labelPositionCentral;
	private JLabel lblDirection;
	private JLabel lblDirection_1;
	private JLabel lblDirection_2;
	private JLabel lblDirection_3;
	private JLabel lbl_AfficheCentre;
	private JLabel lbl_AfficherRotaAngle;
	private JLabel lbl_AfficherSens;
	private JLabel lbl_AfficherRotVitesse;
	private JLabel labelAfficherCoeficient;
	private JLabel label_AffPositionA;
	private JLabel label_affPositionC;
	private JLabel label_affPositionD;
	private JLabel label_affPositionB;
	private JLabel label_affVitesseA;
	private JLabel label_affVitesseC;
	private JLabel label_affVitesseD;
	private JLabel label_affVitesseB;
	private JLabel label_affDireA;
	private JLabel label_affDireC;
	private JLabel label_affDireD;
	private JLabel label_affDirecB;
	private double e[] = {1/2, 5/9, 8/9, 15/16, 19/20};
	private int choice = 3;
	private JSpinner spinnerSlowMotion;
	private JLabel lblSlowMotionDetatemps;
	private JLabel lblStep;
	private boolean musicEncours = false;
	private Image img = null;
	private Image img2 = null;
	private Image img3 = null;
	private JLabel lblBackGround;
	private JScrollPane scrollPane;
	private JEditorPane dtrpnLeProposDu;
	private BufferedImage img4;
	/**
	 * Create the panel.
	 */
	public PanelDroit3() {
		setPreferredSize(new Dimension(325, 600));
		setLayout(null);
		lireLesTextures();
		btnAnimation = new JButton("Commencer");
		btnAnimation.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				btnAnimation.setForeground(Color.WHITE);
				btnAnimation.setFont(new Font("Tahoma", Font.BOLD, 12));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAnimation.setForeground(Color.RED);
				btnAnimation.setFont(new Font("Tahoma", Font.PLAIN, 11));
			}
		});
		btnAnimation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAnimation.setForeground(Color.RED);
		btnAnimation.setBackground(SystemColor.inactiveCaption);
		btnAnimation.setFocusable(false);
		btnAnimation.setToolTipText("Commencer l'animation");
		btnAnimation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.demarrer();
				}
				btnArreter.setEnabled(true);
				btnAnimation.setEnabled(false);
				btnReinitialiser.setEnabled(true);
			}
		});
		btnAnimation.setBounds(35, 500, 117, 34);
		add(btnAnimation);
		
		btnArreter = new JButton("Arreter");
		btnArreter.setEnabled(false);
		btnArreter.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				btnArreter.setForeground(Color.WHITE);
				btnArreter.setFont(new Font("Tahoma", Font.BOLD, 12));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnArreter.setForeground(Color.RED);
				btnArreter.setFont(new Font("Tahoma", Font.PLAIN, 11));
			}
		});
		btnArreter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnArreter.setForeground(Color.RED);
		btnArreter.setBackground(SystemColor.inactiveCaption);
		btnArreter.setFocusable(false);
		btnArreter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.arreter();
				}
				btnArreter.setEnabled(false);
				btnAnimation.setEnabled(true);
			}
		});
		btnArreter.setBounds(176, 552, 117, 34);
		add(btnArreter);
		
		btnReinitialiser = new JButton("Initialiser");
		btnReinitialiser.setEnabled(false);
		btnReinitialiser.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				btnReinitialiser.setForeground(Color.WHITE);
				btnReinitialiser.setFont(new Font("Tahoma", Font.BOLD, 12));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnReinitialiser.setForeground(Color.RED);
				btnReinitialiser.setFont(new Font("Tahoma", Font.PLAIN, 11));
			}
		});
		btnReinitialiser.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnReinitialiser.setForeground(Color.RED);
		btnReinitialiser.setBackground(SystemColor.inactiveCaption);
		btnReinitialiser.setFocusable(false);
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.initialiser();
				}
				btnReinitialiser.setEnabled(false);
				masseA.setValue(5);
				masseB.setValue(5);
				masseC.setValue(5);
				masseD.setValue(5);
				comboBox.setSelectedIndex(3);
			}
		});
		btnReinitialiser.setBounds(176, 500, 117, 34);
		add(btnReinitialiser);
		
		buttonScientifique = new JButton("Scientifique");
		buttonScientifique.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				buttonScientifique.setForeground(Color.WHITE);
				buttonScientifique.setFont(new Font("Tahoma", Font.BOLD, 12));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				buttonScientifique.setForeground(Color.RED);
				buttonScientifique.setFont(new Font("Tahoma", Font.PLAIN, 11));
			}
		});
		buttonScientifique.setFont(new Font("Tahoma", Font.PLAIN, 12));
		buttonScientifique.setForeground(Color.RED);
		buttonScientifique.setBackground(SystemColor.inactiveCaption);
		buttonScientifique.setFocusable(false);
		buttonScientifique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!scientifique){
					buttonScientifique.setText("Mode normal");
					tabbedPane_1.setSelectedIndex(1);
					scientifique = true;
					lblBackGround.setVisible(true);
					btnAnimation.setBackground(Color.BLACK);
					btnArreter.setBackground(Color.BLACK);
					buttonScientifique.setBackground(Color.BLACK);
					btnReinitialiser.setBackground(Color.BLACK);
				}else{
					buttonScientifique.setText("Scientifique");
					tabbedPane_1.setSelectedIndex(0);
					scientifique = false;
					lblBackGround.setVisible(false);
					btnAnimation.setBackground(SystemColor.inactiveCaption);
					btnArreter.setBackground(SystemColor.inactiveCaption);
					buttonScientifique.setBackground(SystemColor.inactiveCaption);
					btnReinitialiser.setBackground(SystemColor.inactiveCaption);
				}
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.setScientifique(scientifique);
				}
			}
		});
		buttonScientifique.setBounds(35, 552, 117, 34);
		add(buttonScientifique);
		
		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(8, 11, 310, 478);
		add(tabbedPane_1);
		
		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.menu);
		tabbedPane_1.addTab("Panneau de contrôle", null, panel_1, null);
		panel_1.setLayout(null);
		
		panelSoleil = new JPanel();
		panelSoleil.setBackground(SystemColor.menu);
		panelSoleil.setBounds(0, 0, 305, 79);
		panel_1.add(panelSoleil);
		panelSoleil.setFont(new Font("Tahoma", Font.BOLD, 13));
		panelSoleil.setForeground(Color.BLACK);
		panelSoleil.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Soleil", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSoleil.setLayout(null);
		
		rdbtnGauche = new JRadioButton("Gauche");
		rdbtnGauche.setBackground(SystemColor.menu);
		rdbtnGauche.setBounds(217, 17, 82, 23);
		panelSoleil.add(rdbtnGauche);
		rdbtnGauche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.setSens(-1);
				}
			}
		});
		rdbtnGauche.setSelected(true);
		buttonGroup.add(rdbtnGauche);
		
		rdbtnDroit = new JRadioButton("Droite");
		rdbtnDroit.setBackground(SystemColor.menu);
		rdbtnDroit.setBounds(217, 43, 67, 23);
		panelSoleil.add(rdbtnDroit);
		rdbtnDroit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.setSens(1);
				}
			}
		});
		buttonGroup.add(rdbtnDroit);
		
		spinnerVitesse = new JSpinner();
		spinnerVitesse.setBounds(20, 16, 82, 55);
		panelSoleil.add(spinnerVitesse);
		spinnerVitesse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//System.out.println(spinnerVitesse.getValue());
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.setVitesse((int) spinnerVitesse.getValue()-1);
				}
				
			}
		});
		spinnerVitesse.setFont(new Font("Tahoma", Font.BOLD, 16));
		spinnerVitesse.setModel(new SpinnerNumberModel(2, 1, 5, 1));
		
		lblTourne = new JLabel("tourne \u00E0 :");
		lblTourne.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTourne.setBounds(155, 16, 67, 25);
		panelSoleil.add(lblTourne);
		
		lblTourne_1 = new JLabel("tourne \u00E0 :");
		lblTourne_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTourne_1.setBounds(155, 42, 67, 25);
		panelSoleil.add(lblTourne_1);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBounds(0, 90, 305, 197);
		panel_1.add(panel);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ballon", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setLayout(null);
		
		masseA = new JSpinner();
		masseA.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent arg0) {
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.setBallonMasse(1,(int)masseA.getValue());
				}
			}
		});
		masseA.setModel(new SpinnerNumberModel(5, 1, 20, 1));
		masseA.setBounds(35, 15, 82, 25);
		panel.add(masseA);
		masseA.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		masseB = new JSpinner();
		masseB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.setBallonMasse(2,(int)masseB.getValue());
				}
			}
		});
		masseB.setModel(new SpinnerNumberModel(5, 1, 20, 1));
		masseB.setFont(new Font("Tahoma", Font.BOLD, 16));
		masseB.setBounds(35, 44, 82, 25);
		panel.add(masseB);
		
		masseC = new JSpinner();
		masseC.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.setBallonMasse(3,(int)masseC.getValue());
				}
			}
		});
		masseC.setModel(new SpinnerNumberModel(5, 1, 20, 1));
		masseC.setFont(new Font("Tahoma", Font.BOLD, 16));
		masseC.setBounds(181, 15, 82, 25);
		panel.add(masseC);
		
		masseD = new JSpinner();
		masseD.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.setBallonMasse(4,(int)masseD.getValue());
				}
			}
		});
		masseD.setModel(new SpinnerNumberModel(5, 1, 20, 1));
		masseD.setFont(new Font("Tahoma", Font.BOLD, 16));
		masseD.setBounds(181, 44, 82, 25);
		panel.add(masseD);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				choice = comboBox.getSelectedIndex();
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.setCoefficientRes(comboBox.getSelectedIndex());
				}
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"bois            1 / 2 ", "li\u00E8ge           5 / 9 ", "ivoire          8 / 9", "verre          15 / 16 ", "acier          19 / 20 "}));
		comboBox.setSelectedIndex(3);
		comboBox.setBounds(121, 77, 166, 23);
		panel.add(comboBox);
		
		label_Materieux = new JLabel("Mat\u00E9rieux :");
		label_Materieux.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_Materieux.setBounds(25, 80, 82, 19);
		panel.add(label_Materieux);
		
		lblNewLabel = new JLabel("KG");
		lblNewLabel.setBounds(121, 15, 20, 25);
		panel.add(lblNewLabel);
		
		label = new JLabel("KG");
		label.setBounds(121, 44, 20, 25);
		panel.add(label);
		
		label_1 = new JLabel("KG");
		label_1.setBounds(267, 44, 20, 25);
		panel.add(label_1);
		
		label_2 = new JLabel("KG");
		label_2.setBounds(267, 15, 20, 25);
		panel.add(label_2);
		
		lblA = new JLabel("A :");
		lblA.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblA.setBounds(15, 15, 20, 25);
		panel.add(lblA);
		
		lblB = new JLabel("B :");
		lblB.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblB.setBounds(15, 44, 20, 25);
		panel.add(lblB);
		
		lblC = new JLabel("C :");
		lblC.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblC.setBounds(161, 15, 20, 25);
		panel.add(lblC);
		
		lblD = new JLabel("D :");
		lblD.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblD.setBounds(161, 44, 20, 25);
		panel.add(lblD);
		
		slider = new JSlider();
		slider.setBackground(SystemColor.menu);
		slider.setMajorTickSpacing(10);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//System.out.println("yolo"+slider.getValue());
				//System.out.println("valeur de e: "+ e[choice]);
				double b = ((double)(slider.getValue()+100)/100);
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.setImpulsion(b);
				}
			}
		});
		slider.setBounds(35, 141, 228, 45);
		panel.add(slider);
		
		lblGrandeurDimpulsion = new JLabel("Grandeur d'impulsion :");
		lblGrandeurDimpulsion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGrandeurDimpulsion.setBounds(25, 118, 166, 19);
		panel.add(lblGrandeurDimpulsion);
		
		spinnerSlowMotion = new JSpinner();
		spinnerSlowMotion.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				for(Niveau3Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau3Listener.class) ) {
					ecout.setSlowMotion((double)spinnerSlowMotion.getValue());
				}
			}
		});
		spinnerSlowMotion.setModel(new SpinnerNumberModel(0.03, 0.02, 0.05, 0.005));
		spinnerSlowMotion.setFont(new Font("Tahoma", Font.BOLD, 16));
		spinnerSlowMotion.setBounds(181, 298, 82, 32);
		panel_1.add(spinnerSlowMotion);
		
		lblSlowMotionDetatemps = new JLabel("Slow Motion mode :");
		lblSlowMotionDetatemps.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSlowMotionDetatemps.setBounds(27, 298, 133, 32);
		panel_1.add(lblSlowMotionDetatemps);
		
		lblStep = new JLabel("Step");
		lblStep.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStep.setBounds(268, 298, 34, 32);
		panel_1.add(lblStep);
		
		panel_2 = new JPanel();
		tabbedPane_1.addTab("Paramètres scientifiques", null, panel_2, null);
		panel_2.setLayout(null);
		panel_2.setBackground(SystemColor.menu);
		
		panel_3 = new JPanel();
		panel_3.setBackground(Color.DARK_GRAY);
		panel_3.setBounds(10, 11, 285, 125);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		lbl_AngleRotation = new JLabel("L'angle de rotation :");
		lbl_AngleRotation.setForeground(Color.LIGHT_GRAY);
		lbl_AngleRotation.setBounds(10, 60, 123, 30);
		panel_3.add(lbl_AngleRotation);
		lbl_AngleRotation.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		lblLeRoueCentral = new JLabel("Le roue central tourne  \u00E0 ");
		lblLeRoueCentral.setForeground(Color.LIGHT_GRAY);
		lblLeRoueCentral.setBounds(10, 90, 147, 30);
		panel_3.add(lblLeRoueCentral);
		lblLeRoueCentral.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		lblRotationVitesse = new JLabel("Rotation vitesse :");
		lblRotationVitesse.setForeground(Color.LIGHT_GRAY);
		lblRotationVitesse.setBounds(10, 30, 147, 30);
		panel_3.add(lblRotationVitesse);
		lblRotationVitesse.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		labelPositionCentral = new JLabel("Le centre du roue :");
		labelPositionCentral.setForeground(Color.LIGHT_GRAY);
		labelPositionCentral.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelPositionCentral.setBounds(10, 0, 123, 30);
		panel_3.add(labelPositionCentral);
		
		lbl_AfficheCentre = new JLabel("");
		lbl_AfficheCentre.setForeground(Color.LIGHT_GRAY);
		lbl_AfficheCentre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_AfficheCentre.setBounds(162, 0, 123, 30);
		panel_3.add(lbl_AfficheCentre);
		
		lbl_AfficherRotaAngle = new JLabel("");
		lbl_AfficherRotaAngle.setForeground(Color.LIGHT_GRAY);
		lbl_AfficherRotaAngle.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_AfficherRotaAngle.setBounds(162, 60, 123, 30);
		panel_3.add(lbl_AfficherRotaAngle);
		
		lbl_AfficherSens = new JLabel("");
		lbl_AfficherSens.setForeground(Color.LIGHT_GRAY);
		lbl_AfficherSens.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_AfficherSens.setBounds(162, 90, 123, 30);
		panel_3.add(lbl_AfficherSens);
		
		lbl_AfficherRotVitesse = new JLabel("");
		lbl_AfficherRotVitesse.setForeground(Color.LIGHT_GRAY);
		lbl_AfficherRotVitesse.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_AfficherRotVitesse.setBounds(162, 30, 123, 30);
		panel_3.add(lbl_AfficherRotVitesse);
		
		panel_4 = new JPanel();
		panel_4.setBackground(Color.DARK_GRAY);
		panel_4.setLayout(null);
		panel_4.setBounds(10, 147, 285, 292);
		panel_2.add(panel_4);
		
		JLabel lblBallonA = new JLabel("BallonA :");
		lblBallonA.setForeground(Color.LIGHT_GRAY);
		lblBallonA.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBallonA.setBounds(10, 0, 55, 30);
		panel_4.add(lblBallonA);
		
		lblVitesseA = new JLabel("Vitesse :");
		lblVitesseA.setForeground(Color.LIGHT_GRAY);
		lblVitesseA.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVitesseA.setBounds(10, 60, 50, 30);
		panel_4.add(lblVitesseA);
		
		lblBallonC = new JLabel("BallonC :");
		lblBallonC.setForeground(Color.LIGHT_GRAY);
		lblBallonC.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBallonC.setBounds(142, 0, 55, 30);
		panel_4.add(lblBallonC);
		
		lblVitesseC = new JLabel("Vitesse :");
		lblVitesseC.setForeground(Color.LIGHT_GRAY);
		lblVitesseC.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVitesseC.setBounds(142, 60, 50, 30);
		panel_4.add(lblVitesseC);
		
		lblBallonB = new JLabel("BallonB :");
		lblBallonB.setForeground(Color.LIGHT_GRAY);
		lblBallonB.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBallonB.setBounds(10, 132, 55, 30);
		panel_4.add(lblBallonB);
		
		lblBallond = new JLabel("BallonD :");
		lblBallond.setForeground(Color.LIGHT_GRAY);
		lblBallond.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBallond.setBounds(142, 132, 55, 30);
		panel_4.add(lblBallond);
		
		labelVitesseB = new JLabel("Vitesse :");
		labelVitesseB.setForeground(Color.LIGHT_GRAY);
		labelVitesseB.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelVitesseB.setBounds(10, 192, 50, 30);
		panel_4.add(labelVitesseB);
		
		labelVitesseD = new JLabel("Vitesse :");
		labelVitesseD.setForeground(Color.LIGHT_GRAY);
		labelVitesseD.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelVitesseD.setBounds(142, 192, 50, 30);
		panel_4.add(labelVitesseD);
		
		lblCoeficientDeCollision = new JLabel("Coeficient de collision :");
		lblCoeficientDeCollision.setForeground(Color.LIGHT_GRAY);
		lblCoeficientDeCollision.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCoeficientDeCollision.setBounds(10, 255, 138, 30);
		panel_4.add(lblCoeficientDeCollision);
		
		lblDirection = new JLabel("Direction :");
		lblDirection.setForeground(Color.LIGHT_GRAY);
		lblDirection.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDirection.setBounds(10, 90, 63, 30);
		panel_4.add(lblDirection);
		
		lblDirection_1 = new JLabel("Direction :");
		lblDirection_1.setForeground(Color.LIGHT_GRAY);
		lblDirection_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDirection_1.setBounds(142, 90, 63, 30);
		panel_4.add(lblDirection_1);
		
		lblDirection_2 = new JLabel("Direction :");
		lblDirection_2.setForeground(Color.LIGHT_GRAY);
		lblDirection_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDirection_2.setBounds(10, 222, 63, 30);
		panel_4.add(lblDirection_2);
		
		lblDirection_3 = new JLabel("Direction :");
		lblDirection_3.setForeground(Color.LIGHT_GRAY);
		lblDirection_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDirection_3.setBounds(142, 222, 63, 30);
		panel_4.add(lblDirection_3);
		
		labelAfficherCoeficient = new JLabel("");
		labelAfficherCoeficient.setForeground(Color.LIGHT_GRAY);
		labelAfficherCoeficient.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelAfficherCoeficient.setBounds(142, 255, 138, 30);
		panel_4.add(labelAfficherCoeficient);
		
		label_AffPositionA = new JLabel("");
		label_AffPositionA.setForeground(Color.GREEN);
		label_AffPositionA.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_AffPositionA.setBounds(30, 30, 111, 30);
		panel_4.add(label_AffPositionA);
		
		label_affPositionC = new JLabel("");
		label_affPositionC.setForeground(Color.PINK);
		label_affPositionC.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_affPositionC.setBounds(172, 30, 111, 30);
		panel_4.add(label_affPositionC);
		
		label_affPositionD = new JLabel("");
		label_affPositionD.setForeground(Color.CYAN);
		label_affPositionD.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_affPositionD.setBounds(172, 162, 111, 30);
		panel_4.add(label_affPositionD);
		
		label_affPositionB = new JLabel("");
		label_affPositionB.setForeground(Color.RED);
		label_affPositionB.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_affPositionB.setBounds(30, 162, 111, 30);
		panel_4.add(label_affPositionB);
		
		label_affVitesseA = new JLabel("");
		label_affVitesseA.setForeground(Color.GREEN);
		label_affVitesseA.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_affVitesseA.setBounds(70, 60, 63, 30);
		panel_4.add(label_affVitesseA);
		
		label_affVitesseC = new JLabel("");
		label_affVitesseC.setForeground(Color.PINK);
		label_affVitesseC.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_affVitesseC.setBounds(202, 60, 63, 30);
		panel_4.add(label_affVitesseC);
		
		label_affVitesseD = new JLabel("");
		label_affVitesseD.setForeground(Color.CYAN);
		label_affVitesseD.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_affVitesseD.setBounds(202, 192, 63, 30);
		panel_4.add(label_affVitesseD);
		
		label_affVitesseB = new JLabel("");
		label_affVitesseB.setForeground(Color.RED);
		label_affVitesseB.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_affVitesseB.setBounds(70, 192, 63, 30);
		panel_4.add(label_affVitesseB);
		
		label_affDireA = new JLabel("");
		label_affDireA.setForeground(Color.GREEN);
		label_affDireA.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_affDireA.setBounds(70, 90, 63, 30);
		panel_4.add(label_affDireA);
		
		label_affDireC = new JLabel("");
		label_affDireC.setForeground(Color.PINK);
		label_affDireC.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_affDireC.setBounds(202, 90, 63, 30);
		panel_4.add(label_affDireC);
		
		label_affDireD = new JLabel("");
		label_affDireD.setForeground(Color.CYAN);
		label_affDireD.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_affDireD.setBounds(202, 222, 63, 30);
		panel_4.add(label_affDireD);
		
		label_affDirecB = new JLabel("");
		label_affDirecB.setForeground(Color.RED);
		label_affDirecB.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_affDirecB.setBounds(70, 222, 63, 30);
		panel_4.add(label_affDirecB);
		
		dtrpnLeProposDu = new JEditorPane();
		dtrpnLeProposDu.setFont(new Font("Tahoma", Font.BOLD, 13));
		dtrpnLeProposDu.setForeground(Color.WHITE);
		dtrpnLeProposDu.setBackground(new Color(47, 79, 79));
		dtrpnLeProposDu.setEditable(false);
		dtrpnLeProposDu.setBounds(10, 10, 270, 410);
		dtrpnLeProposDu.setText("  L'objetif du niveau trois :\r\n\r\n  Enqu\u00EAte : Il vous faut tracer un chemin\r\n  avec la souris pour la coccinelle \u00E0 \r\n  d\u00E9placer de son d\u00E9bat au foss\u00E9.\r\n\r\n  * vous pouvez observer la collision \r\n  in\u00E9lastique entre les ballons comme un \r\n  obstacle dans ce niveau.\r\n");
		
		ImageIcon imgBorture = new ImageIcon(img4);
		JLabel label= new JLabel();
		label.setBounds(0, 409, 290, 20);
		label.setIcon(imgBorture);
		dtrpnLeProposDu.add(label);
		
		scrollPane = new JScrollPane(dtrpnLeProposDu);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane_1.addTab(" ? Comment jouer ? ", null, scrollPane, null);
		
		ImageIcon imgBack = new ImageIcon(img);
		lblBackGround = new JLabel();
		lblBackGround.setIcon(imgBack);
		lblBackGround.setBounds(0, 0, 325, 600);
		lblBackGround.setVisible(false);
		add(lblBackGround);
	}
	/**
	 * Méthode sert à ajouter le Niveau3Listener dans panel
	 * @param objEcouteur - par défaut
	 */
	public void addNiveau3Listener(Niveau3Listener objEcouteur) {
		OBJETS_ENREGISTRES.add(Niveau3Listener.class, objEcouteur);
	}
	/**
	 * Méthode sert à modifier l'affiche de la position du ballon central
	 * @param centre - la position du ballon
	 */
	public void setRoueCenter(Vector2d centre){
		lbl_AfficheCentre.setText(centre.toString()+" cm");
	}
	/**
	 * Méthode sert à  modifier l'affiche des paramètres des roues
	 * @param rotation - la rotation en radians
	 * @param sens - le sens de rotation
	 * @param vitesse - la vitesse angulaireàà
	 */
	public void setRoueParametre(double rotation, double sens, double vitesse){
		lbl_AfficherRotaAngle.setText((int)rotation+"°");
		if(sens == 1){
			lbl_AfficherSens.setText("droite");
		}else{
			lbl_AfficherSens.setText("gauche");
		}
		lbl_AfficherRotVitesse.setText(round(vitesse,2)+" m/s");
		
	}
	/**
	 * Méthode sert à modifier le coeficient de collision des ballons
	 * @param collision - l'indice de collision
	 */
	public void setCoeficient(double collision) {
		labelAfficherCoeficient.setText(e[choice] + " unité");
	}
	/**
	 * Méthode sert à modifier les paramètres de la vitesse et de la direction du ballonA
	 * @param position - la position du ballon
	 * @param vitesse - la vitesse du ballon
	 * @param direction - la diretion du ballon
	 */
	public void setBallonAParametre(Vector2d position, double vitesse, double direction){
		label_AffPositionA.setText("["+round(position.getX(),2) + ", "+ round(position.getY(),2) + "] cm");
		label_affVitesseA.setText(round(vitesse,2) + " m/s");
		label_affDireA.setText((int)direction+"°");
	}
	/**
	 * MMéthode sert à modifier les paramètres de la vitesse et de la direction du ballonB
	 * @param position - la position du ballon
	 * @param vitesse - la vitesse du ballon
	 * @param direction - la diretion du ballon
	 */
	public void setBallonBParametre(Vector2d position, double vitesse, double direction){
		label_affPositionB.setText("["+round(position.getX(),2) + ", "+ round(position.getY(),2) + "] cm");
		label_affVitesseB.setText(round(vitesse,2) + " m/s");
		label_affDirecB.setText((int)direction+"°");
	}
	/**
	 * Méthode sert à modifier les paramètres de la vitesse et de la direction du ballonC
	 * @param position - la position du ballon
	 * @param vitesse - la vitesse du ballon
	 * @param direction - la diretion du ballon
	 */
	public void setBallonCParametre(Vector2d position, double vitesse, double direction){
		label_affPositionC.setText("["+round(position.getX(),2) + ", "+ round(position.getY(),2) + "] cm");
		label_affVitesseC.setText(round(vitesse,2) + " m/s");
		label_affDireC.setText((int)direction+"°");
	}
	/**
	 * Méthode sert à modifier les paramètres de la vitesse et de la direction du ballonD
	 * @param position - la position du ballon
	 * @param vitesse - la vitesse du ballon
	 * @param direction - la diretion du ballon
	 */
	public void setBallonDParametre(Vector2d position, double vitesse, double direction){
		label_affPositionD.setText("["+round(position.getX(),2) + ", "+ round(position.getY(),2) + "] cm");
		label_affVitesseD.setText(round(vitesse,2) + " m/s");
		label_affDireD.setText((int)direction+"°");
	}
	/**
	 * Méthode sert à modéliser la précision d'un chiffre
	 * @param valueToRound - chiffre
	 * @param numberOfDecimalPlaces - nombre de décimal pour la précision
	 * @return un chiffre modélisé
	 */
	public static double round(double valueToRound, int numberOfDecimalPlaces){
	    double multipicationFactor = Math.pow(10, numberOfDecimalPlaces);
	    double interestedInZeroDPs = valueToRound * multipicationFactor;
	    return Math.round(interestedInZeroDPs) / multipicationFactor;
	}

	/**
	 * Méthode sert à changer le panel de tabedbPane selon son numéro
	 * @param a - le numéro de panel
	 */
	public void setTabPaneSelectedIndex(int a){
		tabbedPane_1.setSelectedIndex(a);
	}
	/**
	 * Méthode pour loader les imgaes dans le resource folder
	 */
	private void lireLesTextures() {
		//lecture des images qui servent de texture
		URL url = getClass().getClassLoader().getResource("leavesBlackOut.PNG");
		URL url2 = getClass().getClassLoader().getResource("leavesYellow2.PNG");
		URL url3 = getClass().getClassLoader().getResource("signature.PNG");
		URL url4 = getClass().getClassLoader().getResource("borture.PNG");
		try {
			img4 = ImageIO.read(url4);
			img3 = ImageIO.read(url);
			img2 = ImageIO.read(url2);
			img = ImageIO.read(url3);
		} catch (Exception e) {
			//System.out.println("Erreur de lecture du fichier de texture");
		}
		
	}
	/**
	 * Cette méthode permet d'activer certains boutons
	 */
	public void activer() {
		btnArreter.setEnabled(true);
		btnReinitialiser.setEnabled(true);
	}
}
