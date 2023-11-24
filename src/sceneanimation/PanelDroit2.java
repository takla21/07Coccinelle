/**
 * 
 * Pannel de controle pour NiveauDeux
 * 
 * @author Zi Zheng Wang
 * @version 1.0
 */
package sceneanimation;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.event.EventListenerList;

import ecouteur.Niveau2Listener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;

import java.awt.SystemColor;

import javax.swing.UIManager;
import javax.swing.JLabel;

import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JScrollBar;

import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JScrollPane;

import java.awt.Component;

import javax.swing.ScrollPaneConstants;
/**
 * Classe panelDroit2 du jeu de coccinelle, le panelDroit2 est associé avec le niveauDeux pour afficher et loader les paramètres
 * @author Zizheng Wang
 * @version 2.0
 */
public class PanelDroit2 extends JPanel {
	private JButton btnStart;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList(); //collection
	private JButton buttonArreter;
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblMasse;
	private JLabel lblVitesse;
	private JLabel lblConstantRessort;
	private JLabel lblCoeficientDeFrottement;
	private JLabel lblDeplacement;
	private JSlider sliderVitesseA;
	private JLabel lblDeltaTemps;
	private JButton btnInitialiser;
	private JButton button_1;
	private JSpinner spinnerMasseA;
	private JSpinner spinnerDeplacementA;
	private JSpinner spinnerConstantA;
	private JComboBox comboBoxCoeficientA;
	private JPanel panel_3;
	private JLabel labelMasseB;
	private JLabel labelVitesseB;
	private JLabel labelConstantRessortB;
	private JLabel labelCoeficientB;
	private JLabel labelDeplacementB;
	private JSlider sliderVitesseB;
	private JSpinner spinnerMasseB;
	private JSpinner spinnerDeplacementB;
	private JSpinner spinnerConstantB;
	private JComboBox comboBoxCoeficientB;
	private JSpinner spinnerDeltaTemps;
	private JLabel lblKg;
	private JLabel lblM;
	private JLabel lblNm;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel lblSec;
	private JPanel panel_4;
	private double constant[] = {0.95, 0.64, 0.40, 0.22, 0.1};
	private JPanel panel_5;
	private JRadioButton rdbtnPositif;
	private JRadioButton rdbtnNgative;
	private JRadioButton rdbtnNeutre;
	private JPanel panel_6;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	private JPanel panel_7;
	private JRadioButton radioButton_3;
	private JRadioButton radioButton_4;
	private JRadioButton radioButton_5;
	private JLabel lblNewLabel;
	private JLabel lblParticuleDeux;
	private JLabel lblParticuleTrois;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private boolean scientifique = false;
	private int charge[] = {1, -1, 0};
	private String chargeNom[] = {"Positive", "Négative", "Neutre"};
	private JPanel panel_8;
	private JLabel lblNewLabel_1;
	private JLabel lblLaCharge;
	private boolean musicEncours = false;
	private JScrollBar scrollBar;
	private JLabel lblLaVitesse;
	private JLabel lblLeDplacement;
	private JLabel lblLaMasse;
	private JLabel lblLeCoef;
	private JLabel lblNeutre;
	private JLabel lblVitesseRessortUn;
	private JLabel lblRessortDeplacementUn;
	private JLabel lblMasAffiUn;
	private JLabel lblExprimentalUn;
	private JLabel lblConstant;
	private JLabel lblAfficheConstant;
	private JPanel panel_9;
	private JLabel lblRessortDeux;
	private JLabel label_4;
	private JScrollBar scrollBar_1;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_8;
	private JLabel label_9;
	private JLabel lblPositive;
	private JLabel label_AfficheVitesseDeux;
	private JLabel label_AfficheDeplacementDeux;
	private JLabel label_AfficheMasseDeux;
	private JLabel label_AfficheCoeficientFDeux;
	private JLabel label_AfficheConstantDeux;
	private JPanel panel_10;
	private JLabel lblRessortTrois;
	private JLabel label_17;
	private JScrollBar scrollBar_2;
	private JLabel label_18;
	private JLabel label_19;
	private JLabel label_20;
	private JLabel label_21;
	private JLabel label_22;
	private JLabel lblNgative;
	private JLabel label_AfficheVitesseTrois;
	private JLabel label_AfficheDeplacementTrois;
	private JLabel label_AfficheMasseTrois;
	private JLabel label_AfficheEXperimentalTrois;
	private JLabel label_AfficheConstantTrois;
	private JScrollPane scrollPane;
	private JEditorPane dtrpnLobjetifDuNiveau;
	private BufferedImage img;
	/**
	 * Create the panel.
	 */
	public PanelDroit2() {
		setPreferredSize(new Dimension(325, 600));
		setLayout(null);
		lireLesTextures();
		btnStart = new JButton("Animation");
		btnStart.setToolTipText("Commencer l'animation");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.demarrer(0);
				}
				buttonArreter.setEnabled(true);
				btnStart.setEnabled(false);
				btnInitialiser.setEnabled(true);
			}
		});
		btnStart.setBounds(35, 500, 117, 34);
		add(btnStart);
		
		buttonArreter = new JButton("Arreter");
		buttonArreter.setEnabled(false);
		buttonArreter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.arreter();
				}
				buttonArreter.setEnabled(false);
				btnStart.setEnabled(true);
			}
		});
		buttonArreter.setBounds(176, 552, 117, 34);
		add(buttonArreter);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(8, 0, 310, 489);
		add(tabbedPane);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		tabbedPane.addTab("Panneau de ressort", null, panel, null);
		panel.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(10, 35, 285, 200);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		lblMasse = new JLabel("Masse :");
		lblMasse.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMasse.setBounds(10, 25, 60, 25);
		panel_2.add(lblMasse);
		
		lblVitesse = new JLabel("Vitesse :");
		lblVitesse.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVitesse.setBounds(10, 125, 60, 25);
		panel_2.add(lblVitesse);
		
		lblConstantRessort = new JLabel("Constant ressort :");
		lblConstantRessort.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblConstantRessort.setBounds(10, 75, 110, 25);
		panel_2.add(lblConstantRessort);
		
		lblCoeficientDeFrottement = new JLabel("Coeficient :");
		lblCoeficientDeFrottement.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCoeficientDeFrottement.setBounds(10, 100, 75, 25);
		panel_2.add(lblCoeficientDeFrottement);
		
		lblDeplacement = new JLabel("Deplacement initial :");
		lblDeplacement.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDeplacement.setBounds(10, 50, 125, 25);
		panel_2.add(lblDeplacement);
		
		sliderVitesseA = new JSlider();
		sliderVitesseA.setBackground(Color.LIGHT_GRAY);
		sliderVitesseA.setMaximum(1000);
		sliderVitesseA.setValue(300);
		sliderVitesseA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setVitesse(1,(double)sliderVitesseA.getValue());
				}
			}
		});
		sliderVitesseA.setMajorTickSpacing(150);
		sliderVitesseA.setSnapToTicks(true);
		sliderVitesseA.setPaintTicks(true);
		sliderVitesseA.setPaintLabels(true);
		sliderVitesseA.setBounds(45, 150, 200, 43);
		panel_2.add(sliderVitesseA);
		
		spinnerMasseA = new JSpinner();
		spinnerMasseA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setMasse(1,(int)spinnerMasseA.getValue());
				}
				lblMasAffiUn.setText((int)spinnerMasseA.getValue() + " kg");
				label_AfficheMasseTrois.setText((int)spinnerMasseA.getValue() + " kg");
			}
		});
		spinnerMasseA.setModel(new SpinnerNumberModel(5, 1, 50, 1));
		spinnerMasseA.setBounds(170, 25, 60, 25);
		panel_2.add(spinnerMasseA);
		
		spinnerDeplacementA = new JSpinner();
		spinnerDeplacementA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setDeplacement(1,(int)spinnerDeplacementA.getValue());
				}
			}
		});
		spinnerDeplacementA.setModel(new SpinnerNumberModel(30, 5, 35, 5));
		spinnerDeplacementA.setBounds(170, 50, 60, 25);
		panel_2.add(spinnerDeplacementA);
		
		spinnerConstantA = new JSpinner();
		spinnerConstantA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setConstantRessort(1,(int)spinnerConstantA.getValue());
				}
				lblAfficheConstant.setText((int)spinnerConstantA.getValue() + " n/m");
				label_AfficheConstantTrois.setText((int)spinnerConstantA.getValue() + " n/m");
			}
		});
		spinnerConstantA.setModel(new SpinnerNumberModel(10, 5, 15, 1));
		spinnerConstantA.setBounds(170, 75, 60, 25);
		panel_2.add(spinnerConstantA);
		
		comboBoxCoeficientA = new JComboBox();
		comboBoxCoeficientA.setModel(new DefaultComboBoxModel(new String[] {"Acier-Plomb (0.95)", "Nickel-Acier (0.64)", "Aluminium-Aluminium (0.40)", "Bonze-Fer (0.22)", "expérimental (0.1)"}));
		comboBoxCoeficientA.setSelectedIndex(4);
		comboBoxCoeficientA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setCoeficient(1,constant[comboBoxCoeficientA.getSelectedIndex()]);
				}
				lblExprimentalUn.setText((String)comboBoxCoeficientA.getSelectedItem());
				label_AfficheEXperimentalTrois.setText((String)comboBoxCoeficientA.getSelectedItem());
			}
		});
		comboBoxCoeficientA.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxCoeficientA.setBounds(95, 102, 163, 20);
		panel_2.add(comboBoxCoeficientA);
		
		lblKg = new JLabel("kg");
		lblKg.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblKg.setBounds(232, 25, 26, 25);
		panel_2.add(lblKg);
		
		lblM = new JLabel("m");
		lblM.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblM.setBounds(232, 50, 26, 25);
		panel_2.add(lblM);
		
		lblNm = new JLabel("n/m");
		lblNm.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNm.setBounds(232, 75, 26, 25);
		panel_2.add(lblNm);
		
		lblDeltaTemps = new JLabel("Delta temps :");
		lblDeltaTemps.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDeltaTemps.setBounds(20, 5, 96, 25);
		panel.add(lblDeltaTemps);
		
		panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		panel_3.setLayout(null);
		panel_3.setBounds(10, 240, 285, 200);
		panel.add(panel_3);
		
		labelMasseB = new JLabel("Masse :");
		labelMasseB.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelMasseB.setBounds(10, 25, 60, 25);
		panel_3.add(labelMasseB);
		
		labelVitesseB = new JLabel("Vitesse :");
		labelVitesseB.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelVitesseB.setBounds(10, 125, 60, 25);
		panel_3.add(labelVitesseB);
		
		labelConstantRessortB = new JLabel("Constant ressort :");
		labelConstantRessortB.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelConstantRessortB.setBounds(10, 75, 110, 25);
		panel_3.add(labelConstantRessortB);
		
		labelCoeficientB = new JLabel("Coeficient :");
		labelCoeficientB.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelCoeficientB.setBounds(10, 100, 75, 25);
		panel_3.add(labelCoeficientB);
		
		labelDeplacementB = new JLabel("Deplacement initial :");
		labelDeplacementB.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelDeplacementB.setBounds(10, 50, 125, 25);
		panel_3.add(labelDeplacementB);
		
		sliderVitesseB = new JSlider();
		sliderVitesseB.setBackground(Color.LIGHT_GRAY);
		sliderVitesseB.setMaximum(1000);
		sliderVitesseB.setValue(350);
		sliderVitesseB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setVitesse(2,(double)sliderVitesseA.getValue());
				}
			}
		});
		sliderVitesseB.setMajorTickSpacing(150);
		sliderVitesseB.setSnapToTicks(true);
		sliderVitesseB.setPaintTicks(true);
		sliderVitesseB.setPaintLabels(true);
		sliderVitesseB.setBounds(45, 150, 200, 43);
		panel_3.add(sliderVitesseB);
		
		spinnerMasseB = new JSpinner();
		spinnerMasseB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setMasse(2,(int)spinnerMasseA.getValue());
				}
			}
		});
		spinnerMasseB.setModel(new SpinnerNumberModel(10, 1, 50, 1));
		spinnerMasseB.setBounds(170, 25, 60, 25);
		panel_3.add(spinnerMasseB);
		
		spinnerDeplacementB = new JSpinner();
		spinnerDeplacementB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setDeplacement(2,(int)spinnerDeplacementA.getValue());
				}
			}
		});
		spinnerDeplacementB.setModel(new SpinnerNumberModel(25, 5, 35, 5));
		spinnerDeplacementB.setBounds(170, 50, 60, 25);
		panel_3.add(spinnerDeplacementB);
		
		spinnerConstantB = new JSpinner();
		spinnerConstantB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setConstantRessort(2,(int)spinnerConstantA.getValue());
				}
			}
		});
		spinnerConstantB.setModel(new SpinnerNumberModel(10, 5, 15, 5));
		spinnerConstantB.setBounds(170, 75, 60, 25);
		panel_3.add(spinnerConstantB);
		
		comboBoxCoeficientB = new JComboBox();
		comboBoxCoeficientB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setCoeficient(2,constant[comboBoxCoeficientA.getSelectedIndex()]);
				}
			}
		});
		comboBoxCoeficientB.setModel(new DefaultComboBoxModel(new String[] {"Acier-Plomb (0.95)", "Nickel-Acier (0.64)", "Aluminium-Aluminium (0.40)", "Bonze-Fer (0.22)", "expérimental (0.1)"}));
		comboBoxCoeficientB.setSelectedIndex(4);
		comboBoxCoeficientB.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxCoeficientB.setBounds(95, 102, 163, 20);
		panel_3.add(comboBoxCoeficientB);
		
		label = new JLabel("kg");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(232, 25, 26, 25);
		panel_3.add(label);
		
		label_1 = new JLabel("m");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(232, 50, 26, 25);
		panel_3.add(label_1);
		
		label_2 = new JLabel("n/m");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(232, 75, 26, 25);
		panel_3.add(label_2);
		
		spinnerDeltaTemps = new JSpinner();
		spinnerDeltaTemps.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setDeltaTemps((double)spinnerDeltaTemps.getValue());
				}
			}
		});
		spinnerDeltaTemps.setModel(new SpinnerNumberModel(0.016, 0.008, 0.035, 0.001));
		spinnerDeltaTemps.setBounds(180, 5, 60, 25);
		panel.add(spinnerDeltaTemps);
		
		lblSec = new JLabel("sec");
		lblSec.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSec.setBounds(242, 9, 26, 25);
		panel.add(lblSec);
		
		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.menu);
		tabbedPane.addTab("Panneau des charges", null, panel_1, null);
		panel_1.setLayout(null);
		
		panel_5 = new JPanel();
		panel_5.setBackground(Color.LIGHT_GRAY);
		panel_5.setBounds(10, 35, 285, 80);
		panel_1.add(panel_5);
		panel_5.setLayout(null);
		
		rdbtnPositif = new JRadioButton("Positive");
		rdbtnPositif.setBackground(Color.LIGHT_GRAY);
		rdbtnPositif.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setCharge(1,charge[0]);
				}
				lblNeutre.setText(chargeNom[0]);
				lblNeutre.setForeground(Color.CYAN);
				lblVitesseRessortUn.setForeground(Color.CYAN);
				lblRessortDeplacementUn.setForeground(Color.CYAN);
				lblMasAffiUn.setForeground(Color.CYAN);
				lblExprimentalUn.setForeground(Color.CYAN);
				lblAfficheConstant.setForeground(Color.CYAN);
			}
		});
		buttonGroup.add(rdbtnPositif);
		rdbtnPositif.setBounds(6, 24, 70, 35);
		panel_5.add(rdbtnPositif);
		
		rdbtnNgative = new JRadioButton("N\u00E9gative");
		rdbtnNgative.setBackground(Color.LIGHT_GRAY);
		rdbtnNgative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setCharge(1,charge[1]);
				}
				lblNeutre.setText(chargeNom[1]);
				lblNeutre.setForeground(Color.RED);
				lblVitesseRessortUn.setForeground(Color.RED);
				lblRessortDeplacementUn.setForeground(Color.RED);
				lblMasAffiUn.setForeground(Color.RED);
				lblExprimentalUn.setForeground(Color.RED);
				lblAfficheConstant.setForeground(Color.RED);
			}
		});
		buttonGroup.add(rdbtnNgative);
		rdbtnNgative.setBounds(105, 24, 88, 35);
		panel_5.add(rdbtnNgative);
		
		rdbtnNeutre = new JRadioButton("Neutre");
		rdbtnNeutre.setBackground(Color.LIGHT_GRAY);
		rdbtnNeutre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setCharge(1,charge[2]);
				}
				lblNeutre.setText(chargeNom[2]);
				lblNeutre.setForeground(Color.LIGHT_GRAY);
				lblVitesseRessortUn.setForeground(Color.LIGHT_GRAY);
				lblRessortDeplacementUn.setForeground(Color.LIGHT_GRAY);
				lblMasAffiUn.setForeground(Color.LIGHT_GRAY);
				lblExprimentalUn.setForeground(Color.LIGHT_GRAY);
				lblAfficheConstant.setForeground(Color.LIGHT_GRAY);
			}
		});
		rdbtnNeutre.setSelected(true);
		buttonGroup.add(rdbtnNeutre);
		rdbtnNeutre.setBounds(209, 24, 70, 35);
		panel_5.add(rdbtnNeutre);
		
		panel_6 = new JPanel();
		panel_6.setBackground(Color.LIGHT_GRAY);
		panel_6.setLayout(null);
		panel_6.setBounds(10, 145, 285, 80);
		panel_1.add(panel_6);
		
		radioButton = new JRadioButton("Positive");
		radioButton.setBackground(Color.LIGHT_GRAY);
		radioButton.setSelected(true);
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setCharge(2,charge[0]);
				}
				lblPositive.setText(chargeNom[0]);
				lblPositive.setForeground(Color.CYAN);
				label_AfficheVitesseDeux.setForeground(Color.CYAN);
				label_AfficheDeplacementDeux.setForeground(Color.CYAN);
				label_AfficheMasseDeux.setForeground(Color.CYAN);
				label_AfficheCoeficientFDeux.setForeground(Color.CYAN);
				label_AfficheConstantDeux.setForeground(Color.CYAN);
			}
		});
		buttonGroup_1.add(radioButton);
		radioButton.setBounds(6, 24, 70, 35);
		panel_6.add(radioButton);
		
		radioButton_1 = new JRadioButton("N\u00E9gative");
		radioButton_1.setBackground(Color.LIGHT_GRAY);
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setCharge(2,charge[1]);
				}
				lblPositive.setText(chargeNom[1]);
				lblPositive.setForeground(Color.RED);
				label_AfficheVitesseDeux.setForeground(Color.RED);
				label_AfficheDeplacementDeux.setForeground(Color.RED);
				label_AfficheMasseDeux.setForeground(Color.RED);
				label_AfficheCoeficientFDeux.setForeground(Color.RED);
				label_AfficheConstantDeux.setForeground(Color.RED);
			}
		});
		buttonGroup_1.add(radioButton_1);
		radioButton_1.setBounds(105, 24, 88, 35);
		panel_6.add(radioButton_1);
		
		radioButton_2 = new JRadioButton("Neutre");
		radioButton_2.setBackground(Color.LIGHT_GRAY);
		radioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setCharge(2,charge[2]);
				}
				lblPositive.setText(chargeNom[2]);
				lblPositive.setForeground(Color.LIGHT_GRAY);
				label_AfficheVitesseDeux.setForeground(Color.LIGHT_GRAY);
				label_AfficheDeplacementDeux.setForeground(Color.LIGHT_GRAY);
				label_AfficheMasseDeux.setForeground(Color.LIGHT_GRAY);
				label_AfficheCoeficientFDeux.setForeground(Color.LIGHT_GRAY);
				label_AfficheConstantDeux.setForeground(Color.LIGHT_GRAY);
			}
		});
		buttonGroup_1.add(radioButton_2);
		radioButton_2.setBounds(209, 24, 70, 35);
		panel_6.add(radioButton_2);
		
		panel_7 = new JPanel();
		panel_7.setBackground(Color.LIGHT_GRAY);
		panel_7.setLayout(null);
		panel_7.setBounds(10, 255, 285, 80);
		panel_1.add(panel_7);
		
		radioButton_3 = new JRadioButton("Positive");
		radioButton_3.setBackground(Color.LIGHT_GRAY);
		radioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setCharge(3,charge[0]);
				}
				lblNgative.setText(chargeNom[0]);
				lblNgative.setForeground(Color.CYAN);
				label_AfficheVitesseTrois.setForeground(Color.CYAN);
				label_AfficheDeplacementTrois.setForeground(Color.CYAN);
				label_AfficheMasseTrois.setForeground(Color.CYAN);
				label_AfficheEXperimentalTrois.setForeground(Color.CYAN);
				label_AfficheConstantTrois.setForeground(Color.CYAN);
			}
		});
		buttonGroup_2.add(radioButton_3);
		radioButton_3.setBounds(6, 24, 70, 35);
		panel_7.add(radioButton_3);
		
		radioButton_4 = new JRadioButton("N\u00E9gative");
		radioButton_4.setBackground(Color.LIGHT_GRAY);
		radioButton_4.setSelected(true);
		radioButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setCharge(3,charge[1]);
				}
				lblNgative.setText(chargeNom[1]);
				lblNgative.setForeground(Color.RED);
				label_AfficheVitesseTrois.setForeground(Color.RED);
				label_AfficheDeplacementTrois.setForeground(Color.RED);
				label_AfficheMasseTrois.setForeground(Color.RED);
				label_AfficheEXperimentalTrois.setForeground(Color.RED);
				label_AfficheConstantTrois.setForeground(Color.RED);
			}
		});
		buttonGroup_2.add(radioButton_4);
		radioButton_4.setBounds(105, 24, 88, 35);
		panel_7.add(radioButton_4);
		
		radioButton_5 = new JRadioButton("Neutre");
		radioButton_5.setBackground(Color.LIGHT_GRAY);
		radioButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.setCharge(3,charge[2]);
				}
				lblNgative.setText(chargeNom[2]);
				lblNgative.setForeground(Color.LIGHT_GRAY);
				label_AfficheVitesseTrois.setForeground(Color.LIGHT_GRAY);
				label_AfficheDeplacementTrois.setForeground(Color.LIGHT_GRAY);
				label_AfficheMasseTrois.setForeground(Color.LIGHT_GRAY);
				label_AfficheEXperimentalTrois.setForeground(Color.LIGHT_GRAY);
				label_AfficheConstantTrois.setForeground(Color.LIGHT_GRAY);
			}
		});
		buttonGroup_2.add(radioButton_5);
		radioButton_5.setBounds(209, 24, 70, 35);
		panel_7.add(radioButton_5);
		
		lblNewLabel = new JLabel("Particule Un :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(21, 11, 119, 22);
		panel_1.add(lblNewLabel);
		
		lblParticuleDeux = new JLabel("Particule Deux :");
		lblParticuleDeux.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblParticuleDeux.setBounds(20, 120, 120, 22);
		panel_1.add(lblParticuleDeux);
		
		lblParticuleTrois = new JLabel("Particule Trois :");
		lblParticuleTrois.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblParticuleTrois.setBounds(20, 230, 120, 22);
		panel_1.add(lblParticuleTrois);
		
		panel_4 = new JPanel();
		tabbedPane.addTab("Panneau Scientifique", null, panel_4, null);
		panel_4.setLayout(null);
		
		panel_8 = new JPanel();
		panel_8.setBackground(Color.DARK_GRAY);
		panel_8.setBounds(10, 11, 285, 135);
		panel_4.add(panel_8);
		panel_8.setLayout(null);
		
		lblNewLabel_1 = new JLabel("Ressort Un ");
		lblNewLabel_1.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 11, 79, 24);
		panel_8.add(lblNewLabel_1);
		
		lblLaCharge = new JLabel("La charge :");
		lblLaCharge.setForeground(Color.LIGHT_GRAY);
		lblLaCharge.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLaCharge.setBounds(10, 38, 79, 24);
		panel_8.add(lblLaCharge);
		
		scrollBar = new JScrollBar();
		scrollBar.addAdjustmentListener(new AdjustmentListener() {
			double incre = 0, increIni = 0, increment = 0;
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				incre = scrollBar.getValue();
				increment = -1*(incre - increIni) +increment;
				increIni = incre;
				lblNewLabel_1.setBounds(10, (int)(11 + increment), 79, 24);
				lblLaCharge.setBounds(10, (int)(38 + increment), 79, 24);
				lblLaVitesse.setBounds(10, (int)(62 + increment), 79, 24);
				lblLeDplacement.setBounds(10, (int)(86 + increment), 115, 24);
				lblLaMasse.setBounds(10, (int)(110 + increment), 115, 24);
				lblLeCoef.setBounds(10, (int)(134 + increment), 211, 24);
				lblConstant.setBounds(10, (int)(182 + increment), 150, 24);
				lblNeutre.setBounds(157, (int)(38 + increment), 79, 24);
				lblVitesseRessortUn.setBounds(157, (int)(62 + increment), 79, 24);
				lblRessortDeplacementUn.setBounds(157, (int)(86 + increment), 79, 24);
				lblMasAffiUn.setBounds(157, (int)(110 + increment), 79, 24);
				lblExprimentalUn.setBounds(135,(int)(158 + increment), 110, 24);
				lblAfficheConstant.setBounds(157,(int)(182 + increment), 100, 24);
			}
		});
		scrollBar.setBounds(268, 0, 17, 135);
		panel_8.add(scrollBar);
		
		lblLaVitesse = new JLabel("La vitesse :");
		lblLaVitesse.setForeground(Color.LIGHT_GRAY);
		lblLaVitesse.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLaVitesse.setBounds(10, 62, 79, 24);
		panel_8.add(lblLaVitesse);
		
		lblLeDplacement = new JLabel("Le d\u00E9placement :");
		lblLeDplacement.setForeground(Color.LIGHT_GRAY);
		lblLeDplacement.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLeDplacement.setBounds(10, 86, 115, 24);
		panel_8.add(lblLeDplacement);
		
		lblLaMasse = new JLabel("La masse :");
		lblLaMasse.setForeground(Color.LIGHT_GRAY);
		lblLaMasse.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLaMasse.setBounds(10, 110, 115, 24);
		panel_8.add(lblLaMasse);
		
		lblLeCoef = new JLabel("Le coeficient de frottement :");
		lblLeCoef.setForeground(Color.LIGHT_GRAY);
		lblLeCoef.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLeCoef.setBounds(10, 134, 211, 24);
		panel_8.add(lblLeCoef);
		
		lblConstant = new JLabel("Le constant du ressort :");
		lblConstant.setForeground(Color.LIGHT_GRAY);
		lblConstant.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblConstant.setBounds(10, 182, 150, 24);
		panel_8.add(lblConstant);
		
		lblNeutre = new JLabel("Neutre");
		lblNeutre.setForeground(Color.LIGHT_GRAY);
		lblNeutre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNeutre.setBounds(157, 38, 79, 24);
		panel_8.add(lblNeutre);
		
		lblVitesseRessortUn = new JLabel("0.3 m/s");
		lblVitesseRessortUn.setForeground(Color.LIGHT_GRAY);
		lblVitesseRessortUn.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVitesseRessortUn.setBounds(157, 62, 79, 24);
		panel_8.add(lblVitesseRessortUn);
		
		lblRessortDeplacementUn = new JLabel("30 m ");
		lblRessortDeplacementUn.setForeground(Color.LIGHT_GRAY);
		lblRessortDeplacementUn.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRessortDeplacementUn.setBounds(157, 86, 79, 24);
		panel_8.add(lblRessortDeplacementUn);
		
		lblMasAffiUn = new JLabel("5 kg");
		lblMasAffiUn.setForeground(Color.LIGHT_GRAY);
		lblMasAffiUn.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMasAffiUn.setBounds(157, 110, 79, 24);
		panel_8.add(lblMasAffiUn);
		
		lblExprimentalUn = new JLabel("exp\u00E9rimental (0.1)");
		lblExprimentalUn.setForeground(Color.LIGHT_GRAY);
		lblExprimentalUn.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExprimentalUn.setBounds(135, 158, 125, 24);
		panel_8.add(lblExprimentalUn);
		
		lblAfficheConstant = new JLabel("10 n/m ");
		lblAfficheConstant.setForeground(Color.LIGHT_GRAY);
		lblAfficheConstant.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAfficheConstant.setBounds(157, 182, 100, 24);
		panel_8.add(lblAfficheConstant);
		
		panel_9 = new JPanel();
		panel_9.setLayout(null);
		panel_9.setBackground(Color.DARK_GRAY);
		panel_9.setBounds(10, 155, 285, 135);
		panel_4.add(panel_9);
		
		lblRessortDeux = new JLabel("Ressort Deux");
		lblRessortDeux.setForeground(Color.LIGHT_GRAY);
		lblRessortDeux.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRessortDeux.setBounds(10, 11, 115, 24);
		panel_9.add(lblRessortDeux);
		
		label_4 = new JLabel("La charge :");
		label_4.setForeground(Color.LIGHT_GRAY);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_4.setBounds(10, 38, 79, 24);
		panel_9.add(label_4);
		
		scrollBar_1 = new JScrollBar();
		scrollBar_1.addAdjustmentListener(new AdjustmentListener() {
			double incre = 0, increIni = 0, incrementDeux = 0;
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				incre = scrollBar_1.getValue();
				incrementDeux = -1*(incre - increIni) +incrementDeux;
				increIni = incre;
				lblRessortDeux.setBounds(10, (int)(11 + incrementDeux), 115, 24);
				label_4.setBounds(10, (int)(38 + incrementDeux), 79, 24);
				label_5.setBounds(10, (int)(62 + incrementDeux), 79, 24);
				label_6.setBounds(10, (int)(86 + incrementDeux), 115, 24);
				label_7.setBounds(10, (int)(110 + incrementDeux), 115, 24);
				label_8.setBounds(10, (int)(134 + incrementDeux), 211, 24);
				label_9.setBounds(10, (int)(182 + incrementDeux), 150, 24);
				lblPositive.setBounds(157, (int)(38 + incrementDeux), 79, 24);
				label_AfficheVitesseDeux.setBounds(157, (int)(62 + incrementDeux), 79, 24);
				label_AfficheDeplacementDeux.setBounds(157, (int)(86 + incrementDeux), 79, 24);
				label_AfficheMasseDeux.setBounds(157, (int)(110 + incrementDeux), 79, 24);
				label_AfficheCoeficientFDeux.setBounds(135,(int)(158 + incrementDeux), 110, 24);
				label_AfficheConstantDeux.setBounds(157,(int)(182 + incrementDeux), 100, 24);
			}
		});
		scrollBar_1.setBounds(268, 0, 17, 135);
		panel_9.add(scrollBar_1);
		
		label_5 = new JLabel("La vitesse :");
		label_5.setForeground(Color.LIGHT_GRAY);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_5.setBounds(10, 62, 79, 24);
		panel_9.add(label_5);
		
		label_6 = new JLabel("Le d\u00E9placement :");
		label_6.setForeground(Color.LIGHT_GRAY);
		label_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_6.setBounds(10, 86, 115, 24);
		panel_9.add(label_6);
		
		label_7 = new JLabel("La masse :");
		label_7.setForeground(Color.LIGHT_GRAY);
		label_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_7.setBounds(10, 110, 115, 24);
		panel_9.add(label_7);
		
		label_8 = new JLabel("Le coeficient de frottement :");
		label_8.setForeground(Color.LIGHT_GRAY);
		label_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_8.setBounds(10, 134, 211, 24);
		panel_9.add(label_8);
		
		label_9 = new JLabel("Le constant du ressort :");
		label_9.setForeground(Color.LIGHT_GRAY);
		label_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_9.setBounds(10, 182, 150, 24);
		panel_9.add(label_9);
		
		lblPositive = new JLabel("Positive");
		lblPositive.setForeground(Color.CYAN);
		lblPositive.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPositive.setBounds(157, 38, 79, 24);
		panel_9.add(lblPositive);
		
		label_AfficheVitesseDeux = new JLabel("0.3 m/s");
		label_AfficheVitesseDeux.setForeground(Color.CYAN);
		label_AfficheVitesseDeux.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_AfficheVitesseDeux.setBounds(157, 62, 79, 24);
		panel_9.add(label_AfficheVitesseDeux);
		
		label_AfficheDeplacementDeux = new JLabel("30 m ");
		label_AfficheDeplacementDeux.setForeground(Color.CYAN);
		label_AfficheDeplacementDeux.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_AfficheDeplacementDeux.setBounds(157, 86, 79, 24);
		panel_9.add(label_AfficheDeplacementDeux);
		
		label_AfficheMasseDeux = new JLabel("5 kg");
		label_AfficheMasseDeux.setForeground(Color.CYAN);
		label_AfficheMasseDeux.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_AfficheMasseDeux.setBounds(157, 110, 79, 24);
		panel_9.add(label_AfficheMasseDeux);
		
		label_AfficheCoeficientFDeux = new JLabel("exp\u00E9rimental (0.1)");
		label_AfficheCoeficientFDeux.setForeground(Color.CYAN);
		label_AfficheCoeficientFDeux.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_AfficheCoeficientFDeux.setBounds(135, 158, 125, 24);
		panel_9.add(label_AfficheCoeficientFDeux);
		
		label_AfficheConstantDeux = new JLabel("10 n/m ");
		label_AfficheConstantDeux.setForeground(Color.CYAN);
		label_AfficheConstantDeux.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_AfficheConstantDeux.setBounds(157, 182, 100, 24);
		panel_9.add(label_AfficheConstantDeux);
		
		panel_10 = new JPanel();
		panel_10.setLayout(null);
		panel_10.setBackground(Color.DARK_GRAY);
		panel_10.setBounds(10, 299, 285, 135);
		panel_4.add(panel_10);
		
		lblRessortTrois = new JLabel("Ressort Trois ");
		lblRessortTrois.setForeground(Color.LIGHT_GRAY);
		lblRessortTrois.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRessortTrois.setBounds(10, 11, 115, 24);
		panel_10.add(lblRessortTrois);
		
		label_17 = new JLabel("La charge :");
		label_17.setForeground(Color.LIGHT_GRAY);
		label_17.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_17.setBounds(10, 38, 79, 24);
		panel_10.add(label_17);
		
		scrollBar_2 = new JScrollBar();
		scrollBar_2.addAdjustmentListener(new AdjustmentListener() {
			double incre = 0, increIni = 0, incrementTrois = 0;
			public void adjustmentValueChanged(AdjustmentEvent e) {
				incre = scrollBar_2.getValue();
				incrementTrois = -1*(incre - increIni) +incrementTrois;
				increIni = incre;
				lblRessortTrois.setBounds(10, (int)(11 + incrementTrois), 115, 24);
				label_17.setBounds(10, (int)(38 + incrementTrois), 79, 24);
				label_18.setBounds(10, (int)(62 + incrementTrois), 79, 24);
				label_19.setBounds(10, (int)(86 + incrementTrois), 115, 24);
				label_20.setBounds(10, (int)(110 + incrementTrois), 115, 24);
				label_21.setBounds(10, (int)(134 + incrementTrois), 211, 24);
				label_22.setBounds(10, (int)(182 + incrementTrois), 150, 24);
				lblNgative.setBounds(157, (int)(38 + incrementTrois), 79, 24);
				label_AfficheVitesseTrois.setBounds(157, (int)(62 + incrementTrois), 79, 24);
				label_AfficheDeplacementTrois.setBounds(157, (int)(86 + incrementTrois), 79, 24);
				label_AfficheMasseTrois.setBounds(157, (int)(110 + incrementTrois), 79, 24);
				label_AfficheEXperimentalTrois.setBounds(135,(int)(158 + incrementTrois), 110, 24);
				label_AfficheConstantTrois.setBounds(157,(int)(182 + incrementTrois), 100, 24);
			}
		});
		scrollBar_2.setBounds(268, 0, 17, 135);
		panel_10.add(scrollBar_2);
		
		label_18 = new JLabel("La vitesse :");
		label_18.setForeground(Color.LIGHT_GRAY);
		label_18.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_18.setBounds(10, 62, 79, 24);
		panel_10.add(label_18);
		
		label_19 = new JLabel("Le d\u00E9placement :");
		label_19.setForeground(Color.LIGHT_GRAY);
		label_19.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_19.setBounds(10, 86, 115, 24);
		panel_10.add(label_19);
		
		label_20 = new JLabel("La masse :");
		label_20.setForeground(Color.LIGHT_GRAY);
		label_20.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_20.setBounds(10, 110, 115, 24);
		panel_10.add(label_20);
		
		label_21 = new JLabel("Le coeficient de frottement :");
		label_21.setForeground(Color.LIGHT_GRAY);
		label_21.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_21.setBounds(10, 134, 211, 24);
		panel_10.add(label_21);
		
		label_22 = new JLabel("Le constant du ressort :");
		label_22.setForeground(Color.LIGHT_GRAY);
		label_22.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_22.setBounds(10, 182, 150, 24);
		panel_10.add(label_22);
		
		lblNgative = new JLabel("N\u00E9gative");
		lblNgative.setForeground(Color.RED);
		lblNgative.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNgative.setBounds(157, 38, 79, 24);
		panel_10.add(lblNgative);
		
		label_AfficheVitesseTrois = new JLabel("0.3 m/s");
		label_AfficheVitesseTrois.setForeground(Color.RED);
		label_AfficheVitesseTrois.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_AfficheVitesseTrois.setBounds(157, 62, 79, 24);
		panel_10.add(label_AfficheVitesseTrois);
		
		label_AfficheDeplacementTrois = new JLabel("30 m ");
		label_AfficheDeplacementTrois.setForeground(Color.RED);
		label_AfficheDeplacementTrois.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_AfficheDeplacementTrois.setBounds(157, 86, 79, 24);
		panel_10.add(label_AfficheDeplacementTrois);
		
		label_AfficheMasseTrois = new JLabel("5 kg");
		label_AfficheMasseTrois.setForeground(Color.RED);
		label_AfficheMasseTrois.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_AfficheMasseTrois.setBounds(157, 110, 79, 24);
		panel_10.add(label_AfficheMasseTrois);
		
		label_AfficheEXperimentalTrois = new JLabel("exp\u00E9rimental (0.1)");
		label_AfficheEXperimentalTrois.setForeground(Color.RED);
		label_AfficheEXperimentalTrois.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_AfficheEXperimentalTrois.setBounds(135, 158, 125, 24);
		panel_10.add(label_AfficheEXperimentalTrois);
		
		label_AfficheConstantTrois = new JLabel("10 n/m ");
		label_AfficheConstantTrois.setForeground(Color.RED);
		label_AfficheConstantTrois.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_AfficheConstantTrois.setBounds(157, 182, 100, 24);
		panel_10.add(label_AfficheConstantTrois);
		
		dtrpnLobjetifDuNiveau = new JEditorPane();
		dtrpnLobjetifDuNiveau.setFont(new Font("Tahoma", Font.BOLD, 13));
		dtrpnLobjetifDuNiveau.setForeground(Color.WHITE);
		dtrpnLobjetifDuNiveau.setBackground(new Color(47, 79, 79));
		dtrpnLobjetifDuNiveau.setEditable(false);
		dtrpnLobjetifDuNiveau.setBounds(10, 10, 270, 410);
		dtrpnLobjetifDuNiveau.setText("  L'objetif du niveau deux :\r\n\r\n Enqu\u00EAte : Il vous faut tracer un chemin\r\n avec la souris pour la coccinelle \u00E0 d\u00E9placer\r\n de son d\u00E9bat \u00E0 la foss\u00E9 sans avoir cogn\u00E9\r\n par les blocs de ressort.\r\n\r\n * vous pouvez observer le\r\n changement du champ magn\u00E9tique\r\n ayant le d\u00E9placement des particules.\r\n ");
		
		ImageIcon imgBorture = new ImageIcon(img);
		JLabel label= new JLabel();
		label.setBounds(0, 420, 290, 20);
		label.setIcon(imgBorture);
		dtrpnLobjetifDuNiveau.add(label);
		
		scrollPane = new JScrollPane(dtrpnLobjetifDuNiveau);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab(" ? Comment jouer ? ", null, scrollPane, null);
		
		btnInitialiser = new JButton("Initialiser");
		btnInitialiser.setEnabled(false);
		btnInitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					ecout.initialiser();
				}
				btnInitialiser.setEnabled(false);
				spinnerMasseA.setValue(5);
				spinnerMasseB.setValue(10);
				spinnerDeplacementA.setValue(30);
				spinnerDeplacementB.setValue(25);
				spinnerConstantA.setValue(10);
				spinnerConstantB.setValue(10);
				comboBoxCoeficientA.setSelectedIndex(4);
				comboBoxCoeficientB.setSelectedIndex(4);
				sliderVitesseA.setValue(300);
				sliderVitesseB.setValue(350);
			}
		});
		btnInitialiser.setFocusable(false);
		btnInitialiser.setBounds(176, 500, 117, 34);
		add(btnInitialiser);
		
		button_1 = new JButton("Scientifique");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Niveau2Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau2Listener.class) ) {
					if(!scientifique){
						ecout.modeScientifique(true);
						tabbedPane.setSelectedIndex(2);
						button_1.setText("Mode normal");
						scientifique = true;
					}else{
						ecout.modeScientifique(false);
						tabbedPane.setSelectedIndex(0);
						button_1.setText("Scientifique");
						scientifique = false;
					}
				}
			}
		});
		button_1.setFocusable(false);
		button_1.setBounds(35, 552, 117, 34);
		add(button_1);
	}
	/**
	 * Méthode sert à ajouter le Niveau2Listener dans panel
	 * @param objEcouteur - par défaut
	 */
	public void addNiveau2Listener(Niveau2Listener objEcouteur) {
		OBJETS_ENREGISTRES.add(Niveau2Listener.class, objEcouteur);
	}
	/**
	 * Méthode sert à terminer le song
	 */

	/**
	 * Méthode sert à modifier l'affiche de vitesse des ressorts
	 * @param i - le numéro de ressort
	 * @param vitesse - le vitesse du ressort
	 */
	public void setRessortVitesse(int i, double vitesse) {
		if(i == 1){
			lblVitesseRessortUn.setText(round(vitesse,2) + " m/s");
			label_AfficheVitesseTrois.setText(round(vitesse,2) + " m/s");
		}else{
			label_AfficheVitesseDeux.setText(round(vitesse,2) + " m/s");
		}
	}
	/**
	 * Méthode sert à modifier l'affiche de déplacement des ressorts
	 * @param i - le numéro de ressort
	 * @param positionReelleBloc - le déplacement du ressort
	 */
	public void setRessortPosition(int i, double positionReelleBloc) {
		if(i == 1){
			lblRessortDeplacementUn.setText(round(positionReelleBloc,2) + " m");
			label_AfficheDeplacementTrois.setText(round(positionReelleBloc,2) + " m");
		}else{
			label_AfficheDeplacementDeux.setText(round(positionReelleBloc,2) + " m");
		}
		
	}
	/**
	 * Méthode sert à changer le panel de tabedbPane selon son numéro
	 * @param a - le numéro de panel
	 */
	public void setTabPaneSelectedIndex(int a){
		tabbedPane.setSelectedIndex(a);
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
	 * Méthode sert à lire les images buffer
	 */
	private void lireLesTextures() {
		//lecture des images qui servent de texture
		URL url = getClass().getClassLoader().getResource("borture.PNG");
		try {
			img = ImageIO.read(url);
		} catch (Exception e) {
			//System.out.println("Erreur de lecture du fichier de texture");
		}
		
	}
	/**
	 * Cette méthode permet d'activer certains boutons
	 */
	public void activer() {
		buttonArreter.setEnabled(true);
		btnInitialiser.setEnabled(true);
	}
}
