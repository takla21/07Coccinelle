package sceneanimation;

import java.awt.Color;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import ecouteur.Niveau2Listener;
import ecouteur.NiveauLoadListener;

import javax.swing.JButton;

import java.awt.SystemColor;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollBar;

import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;

import javax.swing.SwingConstants;
import javax.swing.JPopupMenu;

import java.awt.Canvas;

import javax.swing.JTree;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;

/**
 * Classe panelDroitLoad du jeu de coccinelle, le panelDroitLoad est associé avec le niveauLoad pour afficher et loader les paramètres
 * @author Zizheng Wang
 * @version 2.0
 */
public class PanelDroitLoad extends JPanel {
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList(); //collection
	private JButton btnAnimation;
	private JButton btnReinitialiser;
	private JButton buttonScientifique;
	private JButton btnArreter;
	private JPanel panel_1;
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JEditorPane editorPane;
	private JEditorPane editorPlaquePane;
	private JEditorPane editorBallonPane;
	private JScrollPane scrollPane;
	private JScrollBar scrollBar;
	private JPanel panelRessortUn;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JSlider slider;
	private JSpinner spinnerMasseA;
	private JSpinner spinnerDeplacementA;
	private JSpinner spinnerConstantA;
	private JComboBox comboBoxCoeficientA;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JPanel panelRessortDeux;
	private JLabel label_8;
	private JLabel label_9;
	private JLabel label_10;
	private JLabel label_11;
	private JLabel label_12;
	private JSlider slider_1;
	private JSpinner spinner_3;
	private JSpinner spinner_4;
	private JSpinner spinner_5;
	private JScrollPane scrollPane_3;
	private JComboBox comboBox_1;
	private JLabel label_13;
	private JLabel label_14;
	private JLabel label_15;
	private JPanel panelRessortTrois;
	private JLabel label_16;
	private JLabel label_17;
	private JLabel label_18;
	private JLabel label_19;
	private JLabel label_20;
	private JSlider slider_2;
	private JSpinner spinner_6;
	private JSpinner spinner_7;
	private JSpinner spinner_8;
	private JEditorPane editorPaneCercle;
	private JComboBox comboBox_2;
	private JLabel label_21;
	private JLabel label_22;
	private JLabel label_23;
	private JLabel lblNewLabel;
	private BufferedImage img3;
	private BufferedImage img2;
	private BufferedImage img;
	private boolean afficherBoussole = false;
	private JLabel lblAfficherEffet;
	private double constant[] = {0.95, 0.64, 0.40, 0.22, 0.1};
	private JScrollPane scrlPlaque;
	private JScrollPane scrollPane_2;
	private JPanel pnlPlaque;
	private JPanel pnlChamps;
	private JScrollPane scrollPane_1;
	private JEditorPane editorPaneChamps;
	private JButton btnNewButton;
	/**
	 * Create the panel.
	 */
	public PanelDroitLoad() {
		setPreferredSize(new Dimension(325, 600));
		setLayout(null);
		lireLesTextures();
		
		btnAnimation = new JButton("Animation");
		btnAnimation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.demarrer(0);
				}
				btnReinitialiser.setEnabled(true);
				btnArreter.setEnabled(true);
				btnAnimation.setEnabled(false);
			}
		});
		btnAnimation.setFocusable(false);
		btnAnimation.setBounds(35, 500, 117, 34);
		add(btnAnimation);
		
		btnReinitialiser = new JButton("Reinitialiser");
		btnReinitialiser.setEnabled(false);
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.initialiser();
				}
				btnReinitialiser.setEnabled(false);
			}
		});
		btnReinitialiser.setFocusable(false);
		btnReinitialiser.setBounds(176, 500, 117, 34);
		add(btnReinitialiser);
		
		buttonScientifique = new JButton("Scientifique");
		buttonScientifique.addActionListener(new ActionListener() {
			private boolean scientifique;

			public void actionPerformed(ActionEvent arg0) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					if(!scientifique){
						ecout.scientifique(true);
						scientifique = true;
						buttonScientifique.setText("Nomral");
					}else{
						ecout.scientifique(false);
						scientifique = false;
						buttonScientifique.setText("Scientifique");
					}
					
				}
			}
		});
		buttonScientifique.setFocusable(false);
		buttonScientifique.setBounds(35, 552, 117, 34);
		add(buttonScientifique);
		
		btnArreter = new JButton("Arreter");
		btnArreter.setEnabled(false);
		btnArreter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.arreter();
				}
				btnArreter.setEnabled(false);
				btnAnimation.setEnabled(true);
			}
		});
		btnArreter.setFocusable(false);
		btnArreter.setBounds(176, 552, 117, 34);
		add(btnArreter);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(8, 11, 310, 478);
		add(tabbedPane);
		
		panel_1 = new JPanel();
		tabbedPane.addTab("Panel de cercle", null, panel_1, null);
		panel_1.setLayout(null);
		panel_1.setBackground(SystemColor.activeCaptionBorder);
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 10, 285, 400);
		scrollPane_3.setBackground(Color.DARK_GRAY);
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_1.add(scrollPane_3);
		
		editorPaneCercle = new JEditorPane();
		editorPaneCercle.setText("  Essayez toi");
		editorPaneCercle.setForeground(new Color(238, 130, 238));
		editorPaneCercle.setEditable(false);
		editorPaneCercle.setBackground(Color.DARK_GRAY);
		editorPaneCercle.setBounds(0, 0, 266, 398);
		scrollPane_3.add(editorPaneCercle);
		scrollPane_3.setViewportView(editorPaneCercle);
		
		pnlPlaque = new JPanel();
		tabbedPane.addTab("Panneau plaque", null, pnlPlaque, null);
		pnlPlaque.setLayout(null);
		
		scrlPlaque = new JScrollPane();
		scrlPlaque.setBounds(10, 10, 285, 400);
		pnlPlaque.add(scrlPlaque);
		scrlPlaque.setBackground(Color.DARK_GRAY);
		scrlPlaque.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrlPlaque.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		editorPlaquePane = new JEditorPane();
		scrlPlaque.setViewportView(editorPlaquePane);
		editorPlaquePane.setForeground(new Color(238, 130, 238));
		editorPlaquePane.setBackground(Color.DARK_GRAY);
		editorPlaquePane.setEditable(false);
		editorPlaquePane.setText("  Essayez toi");
		
		pnlChamps = new JPanel();
		tabbedPane.addTab("Panneau champs magnétique", null, pnlChamps, null);
		pnlChamps.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBackground(Color.DARK_GRAY);
		scrollPane_1.setBounds(10, 10, 285, 400);
		pnlChamps.add(scrollPane_1);
		
		editorPaneChamps = new JEditorPane();
		editorPaneChamps.setText("  Essayez toi");
		editorPaneChamps.setForeground(new Color(238, 130, 238));
		editorPaneChamps.setEditable(false);
		editorPaneChamps.setBackground(Color.DARK_GRAY);
		scrollPane_1.setViewportView(editorPaneChamps);
		
		panel = new JPanel();
		panel.setFocusable(false);
		tabbedPane.addTab("Panneau de charge", null, panel, null);
		panel.setLayout(null);
		panel.setBackground(SystemColor.activeCaptionBorder);
		
		ImageIcon imgThisIconBouChamp = new ImageIcon(img);
		lblNewLabel = new JLabel("New label");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
                   if(!afficherBoussole){
                	   afficherBoussole = true;
                	   //System.out.println("Yess");
                	   lblAfficherEffet.setText("Effet boussole");
                   }else{
                	   afficherBoussole = false;
                	   //System.out.println("Nooo");
                	   lblAfficherEffet.setText("il n'y a pas d'effet");
                   }
                   for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
   					ecout.effetBoussole(afficherBoussole);
   				}
			}
		});
		lblNewLabel.setBackground(Color.GREEN);
		lblNewLabel.setBounds(15, 11, 270, 203);
		lblNewLabel.setIcon(imgThisIconBouChamp);
		panel.add(lblNewLabel);
		
		String c = "";
		if(afficherBoussole){
			c = "effet boussole";
		}else{
			c = "il n'y a pas d'effet";
		}
		lblAfficherEffet = new JLabel(c);
		lblAfficherEffet.setHorizontalTextPosition(SwingConstants.CENTER);
		lblAfficherEffet.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAfficherEffet.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblAfficherEffet.setBounds(104, 225, 181, 30);
		panel.add(lblAfficherEffet);
		
		btnNewButton = new JButton("Inverser la charge");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.inverseCharge();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setBounds(75, 320, 160, 35);
		panel.add(btnNewButton);
		
		panel_2 = new JPanel();
		tabbedPane.addTab("Panneau de ressort", null, panel_2, null);
		panel_2.setLayout(null);
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		
		scrollBar = new JScrollBar();
		scrollBar.addAdjustmentListener(new AdjustmentListener() {
			double incre = 0, increIni = 0, increment = 0;
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				incre = scrollBar.getValue();
				increment = -3*(incre - increIni) +increment;
				increIni = incre;
				//System.out.println(increment);
				panelRessortUn.setBounds(5, (int) (11 + increment), 285, 200);
				panelRessortDeux.setBounds(5, (int) (222 + increment) , 285, 200);
				panelRessortTrois.setBounds(5, (int) (434 + increment), 285, 200);
			}
		});
		scrollBar.setBounds(288, 0, 17, 434);
		panel_2.add(scrollBar);
		
		panelRessortUn = new JPanel();
		panelRessortUn.setLayout(null);
		panelRessortUn.setBackground(Color.LIGHT_GRAY);
		panelRessortUn.setBounds(5, 11, 285, 200);
		panel_2.add(panelRessortUn);
		panelRessortUn.setVisible(false);
		
		label = new JLabel("Masse :");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(10, 25, 60, 25);
		panelRessortUn.add(label);
		
		label_1 = new JLabel("Vitesse :");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(10, 125, 60, 25);
		panelRessortUn.add(label_1);
		
		label_2 = new JLabel("Constant ressort :");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(10, 75, 110, 25);
		panelRessortUn.add(label_2);
		
		label_3 = new JLabel("Coeficient :");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_3.setBounds(10, 100, 75, 25);
		panelRessortUn.add(label_3);
		
		label_4 = new JLabel("Deplacement initial :");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_4.setBounds(10, 50, 125, 25);
		panelRessortUn.add(label_4);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setVitesse(0,slider.getValue());
				}
			}
		});
		slider.setValue(300);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMaximum(1000);
		slider.setMajorTickSpacing(150);
		slider.setBackground(Color.LIGHT_GRAY);
		slider.setBounds(45, 150, 200, 43);
		panelRessortUn.add(slider);
		
		spinnerMasseA = new JSpinner();
		spinnerMasseA.setModel(new SpinnerNumberModel(5, 0, 100, 1));
		spinnerMasseA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setMasse(0,(int)spinnerMasseA.getValue());
				}
			}
		});
		spinnerMasseA.setBounds(170, 25, 60, 25);
		panelRessortUn.add(spinnerMasseA);
		
		spinnerDeplacementA = new JSpinner();
		spinnerDeplacementA.setModel(new SpinnerNumberModel(30, 0, 150, 5));
		spinnerDeplacementA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setDeplacement(0,(int)spinnerDeplacementA.getValue());
				}
			}
		});
		spinnerDeplacementA.setBounds(170, 50, 60, 25);
		panelRessortUn.add(spinnerDeplacementA);
		
		spinnerConstantA = new JSpinner();
		spinnerConstantA.setModel(new SpinnerNumberModel(10, 0, 150, 5));
		spinnerConstantA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setConstantRessort(0,(int)spinnerConstantA.getValue());
				}
			}
		});
		spinnerConstantA.setBounds(170, 75, 60, 25);
		panelRessortUn.add(spinnerConstantA);
		
		comboBoxCoeficientA = new JComboBox();
		comboBoxCoeficientA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setCoeficient(0,constant[comboBoxCoeficientA.getSelectedIndex()]);
				}
			}
		});
		comboBoxCoeficientA.setFont(new Font("Tahoma", Font.BOLD, 12));
		comboBoxCoeficientA.setModel(new DefaultComboBoxModel(new String[] {"Acier-Plomb (0.95)", "Nickel-Acier (0.64)", "Aluminium-Aluminium (0.40)", "Bonze-Fer (0.22)", "expérimental (0.1)"}));
		comboBoxCoeficientA.setSelectedIndex(4);
		comboBoxCoeficientA.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxCoeficientA.setBounds(95, 102, 163, 20);
		panelRessortUn.add(comboBoxCoeficientA);
		
		label_5 = new JLabel("kg");
		label_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_5.setBounds(232, 25, 26, 25);
		panelRessortUn.add(label_5);
		
		label_6 = new JLabel("m");
		label_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_6.setBounds(232, 50, 26, 25);
		panelRessortUn.add(label_6);
		
		label_7 = new JLabel("n/m");
		label_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_7.setBounds(232, 75, 26, 25);
		panelRessortUn.add(label_7);
		
		panelRessortDeux = new JPanel();
		panelRessortDeux.setLayout(null);
		panelRessortDeux.setBackground(Color.LIGHT_GRAY);
		panelRessortDeux.setBounds(5, 222, 285, 200);
		panel_2.add(panelRessortDeux);
		panelRessortDeux.setVisible(false);
		
		label_8 = new JLabel("Masse :");
		label_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_8.setBounds(10, 25, 60, 25);
		panelRessortDeux.add(label_8);
		
		label_9 = new JLabel("Vitesse :");
		label_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_9.setBounds(10, 125, 60, 25);
		panelRessortDeux.add(label_9);
		
		label_10 = new JLabel("Constant ressort :");
		label_10.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_10.setBounds(10, 75, 110, 25);
		panelRessortDeux.add(label_10);
		
		label_11 = new JLabel("Coeficient :");
		label_11.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_11.setBounds(10, 100, 75, 25);
		panelRessortDeux.add(label_11);
		
		label_12 = new JLabel("Deplacement initial :");
		label_12.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_12.setBounds(10, 50, 125, 25);
		panelRessortDeux.add(label_12);
		
		slider_1 = new JSlider();
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setVitesse(1,slider_1.getValue());
				}
			}
		});
		slider_1.setValue(400);
		slider_1.setSnapToTicks(true);
		slider_1.setPaintTicks(true);
		slider_1.setPaintLabels(true);
		slider_1.setMaximum(1000);
		slider_1.setMajorTickSpacing(150);
		slider_1.setBackground(Color.LIGHT_GRAY);
		slider_1.setBounds(45, 150, 200, 43);
		panelRessortDeux.add(slider_1);
		
		spinner_3 = new JSpinner();
		spinner_3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setMasse(1,(int)spinner_3.getValue());
				}
			}
		});
		spinner_3.setModel(new SpinnerNumberModel(10, 0, 100, 1));
		spinner_3.setBounds(170, 25, 60, 25);
		panelRessortDeux.add(spinner_3);
		
		spinner_4 = new JSpinner();
		spinner_4.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setDeplacement(1,(int)spinner_4.getValue());
				}
			}
		});
		spinner_4.setModel(new SpinnerNumberModel(30, 0, 150, 5));
		spinner_4.setBounds(170, 50, 60, 25);
		panelRessortDeux.add(spinner_4);
		
		spinner_5 = new JSpinner();
		spinner_5.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setConstantRessort(0,(int)spinner_5.getValue());
				}
			}
		});
		spinner_5.setModel(new SpinnerNumberModel(10, 0, 100, 5));
		spinner_5.setBounds(170, 75, 60, 25);
		panelRessortDeux.add(spinner_5);
		
		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setCoeficient(0,constant[comboBox_1.getSelectedIndex()]);
				}
			}
		});
		comboBox_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Acier-Plomb (0.95)", "Nickel-Acier (0.64)", "Aluminium-Aluminium (0.40)", "Bonze-Fer (0.22)", "expérimental (0.1)"}));
		comboBox_1.setSelectedIndex(4);
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_1.setBounds(95, 102, 163, 20);
		panelRessortDeux.add(comboBox_1);
		
		label_13 = new JLabel("kg");
		label_13.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_13.setBounds(232, 25, 26, 25);
		panelRessortDeux.add(label_13);
		
		label_14 = new JLabel("m");
		label_14.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_14.setBounds(232, 50, 26, 25);
		panelRessortDeux.add(label_14);
		
		label_15 = new JLabel("n/m");
		label_15.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_15.setBounds(232, 75, 26, 25);
		panelRessortDeux.add(label_15);
		
		panelRessortTrois = new JPanel();
		panelRessortTrois.setLayout(null);
		panelRessortTrois.setBackground(Color.LIGHT_GRAY);
		panelRessortTrois.setBounds(5, 434, 285, 200);
		panel_2.add(panelRessortTrois);
		panelRessortTrois.setVisible(false);
		
		label_16 = new JLabel("Masse :");
		label_16.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_16.setBounds(10, 25, 60, 25);
		panelRessortTrois.add(label_16);
		
		label_17 = new JLabel("Vitesse :");
		label_17.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_17.setBounds(10, 125, 60, 25);
		panelRessortTrois.add(label_17);
		
		label_18 = new JLabel("Constant ressort :");
		label_18.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_18.setBounds(10, 75, 110, 25);
		panelRessortTrois.add(label_18);
		
		label_19 = new JLabel("Coeficient :");
		label_19.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_19.setBounds(10, 100, 75, 25);
		panelRessortTrois.add(label_19);
		
		label_20 = new JLabel("Deplacement initial :");
		label_20.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_20.setBounds(10, 50, 125, 25);
		panelRessortTrois.add(label_20);
		
		slider_2 = new JSlider();
		slider_2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setVitesse(2,slider_2.getValue());
				}
			}
		});
		slider_2.setValue(200);
		slider_2.setSnapToTicks(true);
		slider_2.setPaintTicks(true);
		slider_2.setPaintLabels(true);
		slider_2.setMaximum(1000);
		slider_2.setMajorTickSpacing(150);
		slider_2.setBackground(Color.LIGHT_GRAY);
		slider_2.setBounds(45, 150, 200, 43);
		panelRessortTrois.add(slider_2);
		
		spinner_6 = new JSpinner();
		spinner_6.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setMasse(2,(int)spinner_6.getValue());
				}
			}
		});
		spinner_6.setModel(new SpinnerNumberModel(5, 0, 100, 1));
		spinner_6.setBounds(170, 25, 60, 25);
		panelRessortTrois.add(spinner_6);
		
		spinner_7 = new JSpinner();
		spinner_7.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setDeplacement(2,(int)spinner_7.getValue());
				}
			}
		});
		spinner_7.setModel(new SpinnerNumberModel(60, 0, 150, 5));
		spinner_7.setBounds(170, 50, 60, 25);
		panelRessortTrois.add(spinner_7);
		
		spinner_8 = new JSpinner();
		spinner_8.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setConstantRessort(2,(int)spinner_8.getValue());
				}
			}
		});
		spinner_8.setModel(new SpinnerNumberModel(5, 0, 100, 1));
		spinner_8.setBounds(170, 75, 60, 25);
		panelRessortTrois.add(spinner_8);
		
		comboBox_2 = new JComboBox();
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(NiveauLoadListener ecout : OBJETS_ENREGISTRES.getListeners(NiveauLoadListener.class) ) {
					ecout.setCoeficient(2,constant[comboBox_2.getSelectedIndex()]);
				}
			}
		});
		comboBox_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Acier-Plomb (0.95)", "Nickel-Acier (0.64)", "Aluminium-Aluminium (0.40)", "Bonze-Fer (0.22)", "expérimental (0.1)"}));
		comboBox_2.setSelectedIndex(3);
		comboBox_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		comboBox_2.setBounds(95, 102, 163, 20);
		panelRessortTrois.add(comboBox_2);
		
		label_21 = new JLabel("kg");
		label_21.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_21.setBounds(232, 25, 26, 25);
		panelRessortTrois.add(label_21);
		
		label_22 = new JLabel("m");
		label_22.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_22.setBounds(232, 50, 26, 25);
		panelRessortTrois.add(label_22);
		
		label_23 = new JLabel("n/m");
		label_23.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_23.setBounds(232, 75, 26, 25);
		panelRessortTrois.add(label_23);
		
		panel_3 = new JPanel();
		tabbedPane.addTab("Panel de ballon", null, panel_3, null);
		panel_3.setLayout(null);
		panel_3.setBackground(SystemColor.activeCaptionBorder);
		
		editorBallonPane = new JEditorPane();
		editorBallonPane.setForeground(Color.CYAN);
		editorBallonPane.setBackground(Color.DARK_GRAY);
		editorBallonPane.setEditable(false);
		editorBallonPane.setBounds(10, 10, 270, 410);
		editorBallonPane.setText("  Essayez toi");
		
		scrollPane_2 = new JScrollPane(editorBallonPane);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBackground(Color.DARK_GRAY);
		scrollPane_2.setBounds(10, 10, 285, 400);
		panel_3.add(scrollPane_2);
		
		panel_4 = new JPanel();
		tabbedPane.addTab("Information loading", null, panel_4, null);
		panel_4.setBackground(SystemColor.activeCaptionBorder);
		panel_4.setLayout(null);
		tabbedPane.setSelectedIndex(4);
		
		editorPane = new JEditorPane();
		editorPane.setForeground(Color.WHITE);
		editorPane.setBackground(Color.DARK_GRAY);
		editorPane.setEditable(false);
		editorPane.setBounds(10, 10, 270, 410);
		editorPane.setText("  Essayez toi");
		
		scrollPane = new JScrollPane(editorPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 10, 285, 400);
		panel_4.add(scrollPane);
	}
	/**
	 * Méthode pour loader les imgaes dans le resource folder
	 */
	private void lireLesTextures() {
		//lecture des images qui servent de texture
		URL url = getClass().getClassLoader().getResource("bouChamp.png");
		URL url2 = getClass().getClassLoader().getResource("bouChamp.png");
		URL url3 = getClass().getClassLoader().getResource("bouChamp.png");
		try {
			img3 = ImageIO.read(url3);
			img2 = ImageIO.read(url2);
			img = ImageIO.read(url);
		} catch (Exception e) {
			//System.out.println("Erreur de lecture du fichier de texture");
		}
	}
	/**
	 * Méthode sert à mémoriser le NiveauLoadListener dans le panel
	 * @param objEcouteur l'écouteur
	 */
	public void addNiveauLoadListener(NiveauLoadListener objEcouteur) {
		OBJETS_ENREGISTRES.add(NiveauLoadListener.class, objEcouteur);
	}
	/**
	 * Méthode sert à setter le message affiché dans le textArea
	 * @param message - l'information sur des objets mémorisés
	 */
	public void setMessage(String message) {
		editorPane.setText(message);
	}
	/**
	 * Méthode sert à afficher l'information des cercles
	 * @param message - l'information des cercles
	 */
	public void setCercleInfo(String message) {
		editorPaneCercle.setText(message);
		
	}
	/**
	 * Méthode sert à afficher l'information des ballons
	 * @param message - l'informations des ballons
	 */
	public void setBallonInfo(String message) {
		editorBallonPane.setText(message);
		
	}
	/**
	 * Méthode sert à modéliser l'affiche des panels contrôls des ressorts
	 * @param a - l'indice des ressorts
	 */
	public void afficherRessortPanel(int a) {
		if(a == 0){
			panelRessortUn.setVisible(true);
		} else if( a == 1){
			panelRessortDeux.setVisible(true);
		} else if( a == 2){
			panelRessortTrois.setVisible(true);
		}
		
	}
	/**
	 * Cette méthode permet d'afficher les informations sur les plaques dans le niveau
	 * @param message l'information des plaques dans le niveau
	 */
	public void afficherPlaque(String message) {
		editorPlaquePane.setText(message);
	}
	/**
	 * Cette méthode permet d'afficher les informations sur les champs magnétiques dans le niveau
	 * @param message l'information des champs magnétiques dans le niveau
	 */
	public void afficherChamps(String message) {
		editorPaneChamps.setText(message);
		
	}
	/**
	 * Cette méhode permet d'activer certains boutons
	 */
	public void action() {
		btnArreter.setEnabled(true);
		btnReinitialiser.setEnabled(true);
	}
}
