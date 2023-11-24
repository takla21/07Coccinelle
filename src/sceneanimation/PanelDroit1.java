package sceneanimation;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.event.EventListenerList;

import ecouteur.CoccinelleListener;
import ecouteur.Niveau1Listener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSplitPane;

import physique.Vector2d;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

import java.awt.Scrollbar;
import java.awt.Label;

import javax.swing.border.LineBorder;

import java.awt.SystemColor;
import java.awt.Component;

import javax.swing.ScrollPaneConstants;
/**
 * Classe panelDroit1 du jeu de coccinelle, le panelDroit1 est associé avec le niveauUn et NiveauUnScene2 pour afficher et loader les paramètres
 * @author Kevin Takla 
 * @version 2.0
 */
public class PanelDroit1 extends JPanel {
	private JButton btnArreter;
	private JButton buttonScientifique;
	private JButton btnQuitter;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList(); //collection
	private boolean clic = false;
	private JButton buttonInitialiser;
	private JSlider sliderAngleRec;
	private JPanel panelAngleRectangle;
	private JPanel panelAngleTriangle;
	private JSlider sliderAngleTri;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTabbedPane tabbedPane;
	private JPanel panel_3;
	private JSpinner spiIndiceMilieuBleu,  spiIndiceMilieuVert;
	private JRadioButton rdbtnNeutrePart2;
	private JRadioButton rdbtnNgativePart2;
	private JRadioButton rdbtnPositifPart2;
	private JRadioButton rdbtnPositifCocc;
	private JRadioButton rdbtnNgativeCocc;
	private JRadioButton rdbtnNeutreCocc;
	private JRadioButton rdbtnNeutrePart1, rdbtnNgativePart1, rdbtnPositifPart1;
	private final ButtonGroup chargePart1 = new ButtonGroup();
	private final ButtonGroup chargePart2 = new ButtonGroup();
	private final ButtonGroup chargeCocc = new ButtonGroup();
	private JPanel pnlBleuArrive;
	private int cptScientifique =0;
	private JLabel vitesse1,vitesse2,vitesse3;
	private final double CHARGE_ELEMENTAIRE =  1.6*Math.pow(10,-19);
	private JLabel lblCharge1, lblCharge2, lblCharge3;
	private JPanel panel;
	private JLabel lblCharge_1;
	private JPanel panel_6;
	private double chargeAffichableInitale;
	private JLabel lblCharge_2;
	private JPanel panel_7;
	private JLabel lblForce2;
	private JLabel lblForce;
	private JLabel lblForce1;
	private JLabel lblMasse;
	private JLabel lblMasse1;
	private JLabel label_4;
	private JLabel lblMasse2;
	private JPanel pnlScientifiqueScene2;
	private JPanel pnlScientifiqueScene1;
	private JLabel lblAngleMir1Inicident;
	private JLabel lblAngleMir1Reflechi;
	private JPanel pnlMir1;
	private JLabel lblOrientation;
	private JLabel orientationMir1;
	private JPanel pnlMir2;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel lblAngleMir2Inicident;
	private JLabel lblAngleMir2Reflechi;
	private JLabel label_10;
	private JLabel orientationMir2;
	private JPanel pnlMir3;
	private JLabel label_12;
	private JLabel label_13;
	private JLabel lblAngleMir3Inicident;
	private JLabel lblAngleMir3Reflechi;
	private JLabel label_16;
	private JLabel orientationMir3;
	private int nbClics[] ={0,0,0,0,0,0};
	private int nbClicBleu[] ={0,0,0};
	private JCheckBox chkMir1N1;
	private JPanel pnlBleu;
	private JLabel label_8;
	private JLabel label_9;
	private JLabel angleIncidentBleu;
	private JLabel angleRefractBleu;
	private JLabel lblTxtAngleCritiqueBleu;
	private JLabel lblAngleCritiqueBleu;
	private JCheckBox chkMilieuBleu1;
	private JCheckBox chkMilieuBleu3;
	private JCheckBox chkMilieuBleu2;
	private JPanel pnlNormaleBleu;
	private JPanel pnlVert;
	private JLabel label_18;
	private JLabel angleIncidentVert;
	private JLabel angleRefractVert;
	private JLabel lblTxtAngleCritqueVert;
	private JLabel lblAngleCritiqueVert;
	private JPanel pnlNormaleVert;
	private JCheckBox chkMilieuVert1;
	private JCheckBox chkMilieuVert3;
	private JCheckBox chkMilieuVert2;
	private JCheckBox chkMilieuVert4;
	private Scrollbar sclrMiieuBleu;
	private JLabel lblAngleRfract;
	
	private JLabel lblAngleRefractionBleu2;
	private JPanel pnlBleuDepart;
	private JLabel lblAngleIncident2Bleu;
	private JPanel pnlVertArrive;
	private JLabel label_11;
	private JLabel lblAngleIncidentVert2;
	private JLabel lblAngleRefractVert2;
	private Label label_17;
	private Label label_14;
	private Label label_15;
	private JScrollPane scrollPane;
	private JEditorPane dtrpnDansLaPremire;
	
	private boolean scene1EnCours = true;
	private boolean scene2EnCours = false;
	/**
	 * Create the panel.
	 */
	public PanelDroit1() {
		setPreferredSize(new Dimension(325, 600));
		setLayout(null);
		chargeAffichableInitale = round(CHARGE_ELEMENTAIRE,21);
		
		btnArreter = new JButton("Arrêter");
		btnArreter.setFocusable(false);
		btnArreter.setEnabled(false);
		btnArreter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(clic == false){
					for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
						ecout.arreter();
					}
					btnArreter.setText("redémarrer");
					clic = true;
				}else{
					for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
						ecout.reDemarrer();
					}
					btnArreter.setText("Arrêter");
                    clic = false;
				}
			}
		});
		btnArreter.setBounds(35, 500, 117, 34);
		add(btnArreter);
		
		buttonScientifique = new JButton("Scientifique");
		buttonScientifique.setFocusable(false);
		buttonScientifique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean valeurVerite;
				if(cptScientifique %2 ==0){
					valeurVerite = true;
					tabbedPane.setSelectedIndex(1);
					buttonScientifique.setText("Mode normal");
				}else{
					valeurVerite = false;
					tabbedPane.setSelectedIndex(0);
					buttonScientifique.setText("Scientifique");
				}
				cptScientifique++;
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.modeScientifique(valeurVerite);
				}
				
			}
		});
		buttonScientifique.setBounds(35, 552, 117, 34);
		add(buttonScientifique);
		
		btnQuitter = new JButton("Quitter");
		btnQuitter.setFocusable(false);
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.quitter();
				}	
			}
			
		});
		btnQuitter.setBounds(176, 552, 117, 34);
		add(btnQuitter);
		
		buttonInitialiser = new JButton("Initialiser");
		buttonInitialiser.setEnabled(false);
		buttonInitialiser.setFocusable(false);
		buttonInitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.initialiser();
					debloquerParametres(true);
				}
			}
		});
		buttonInitialiser.setBounds(176, 500, 117, 34);
		add(buttonInitialiser);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(8, 11, 310, 478);
		add(tabbedPane);
		
		panel_2 = new JPanel();
		tabbedPane.addTab("Panneau de contrôle", null, panel_2, null);
		panel_2.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Rotation des milieux", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 11, 281, 173);
		panel_2.add(panel_1);
		panel_1.setLayout(null);
		
		panelAngleTriangle = new JPanel();
		panelAngleTriangle.setBounds(35, 122, 216, 40);
		panel_1.add(panelAngleTriangle);
		panelAngleTriangle.setLayout(null);
		
		sliderAngleTri = new JSlider();
		sliderAngleTri.setMaximum(180);
		sliderAngleTri.setValue(0);
		sliderAngleTri.setMajorTickSpacing(15);
		sliderAngleTri.setSnapToTicks(true);
		sliderAngleTri.setPaintTicks(true);
		sliderAngleTri.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.setAnglePrisme(Math.toRadians(sliderAngleTri.getValue()));
					}
			}
		});
		sliderAngleTri.setValue(0);
		sliderAngleTri.setBounds(5, 0, 210, 37);
		panelAngleTriangle.add(sliderAngleTri);
		
		JLabel lblRotationBlocBleu = new JLabel("rotation du bloc bleu: ");
		lblRotationBlocBleu.setBounds(33, 106, 167, 14);
		panel_1.add(lblRotationBlocBleu);
		
		panelAngleRectangle = new JPanel();
		panelAngleRectangle.setBounds(35, 36, 216, 40);
		panel_1.add(panelAngleRectangle);
		panelAngleRectangle.setLayout(null);
		
		sliderAngleRec = new JSlider();
		sliderAngleRec.setMaximum(180);
		sliderAngleRec.setValue(0);
		sliderAngleRec.setMajorTickSpacing(15);
		sliderAngleRec.setSnapToTicks(true);
		sliderAngleRec.setPaintTicks(true);
		sliderAngleRec.setBounds(5, 0, 210, 37);
		panelAngleRectangle.add(sliderAngleRec);
		sliderAngleRec.setValue(0);
		
		JLabel lblRotationVert = new JLabel("Rotation du milieu vert:");
		lblRotationVert.setBounds(36, 21, 149, 14);
		panel_1.add(lblRotationVert);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Modification de l'indice des milieux", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBounds(10, 195, 281, 74);
		panel_2.add(panel_5);
		panel_5.setLayout(null);
		
		spiIndiceMilieuVert = new JSpinner();
		spiIndiceMilieuVert.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.setRefractionVert((double)spiIndiceMilieuVert.getValue());
				}
			}
		});
		spiIndiceMilieuVert.setModel(new SpinnerNumberModel(1.33, 1, 3, 0.01));
		spiIndiceMilieuVert.setBounds(225, 16, 46, 20);
		panel_5.add(spiIndiceMilieuVert);
		
		JLabel label = new JLabel("Indice de r\u00E9fraction du milieu vert:");
		label.setBounds(6, 19, 209, 14);
		panel_5.add(label);
		
		JLabel label_1 = new JLabel("Indice de r\u00E9fraction du milieu bleu:");
		label_1.setBounds(6, 50, 209, 14);
		panel_5.add(label_1);
		
		spiIndiceMilieuBleu = new JSpinner();
		spiIndiceMilieuBleu.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.setRefractionBleu((double)spiIndiceMilieuBleu.getValue());
				}
			}
		});
		spiIndiceMilieuBleu.setBounds(225, 47, 46, 20);
		spiIndiceMilieuBleu.setModel(new SpinnerNumberModel(2, 1, 3, 0.01));
		panel_5.add(spiIndiceMilieuBleu);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Modification des charges des particules", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(10, 280, 282, 159);
		panel_2.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel label_2 = new JLabel("Charge de la premi\u00E8re particule: ");
		label_2.setBounds(6, 19, 191, 14);
		panel_4.add(label_2);
		
		JLabel label_3 = new JLabel("Charge de la deuxi\u00E8me particule: ");
		label_3.setBounds(6, 66, 191, 14);
		panel_4.add(label_3);
		
		JLabel lblChargeDeLa = new JLabel("Charge de la coccinelle:");
		lblChargeDeLa.setBounds(6, 109, 191, 14);
		panel_4.add(lblChargeDeLa);
		
		rdbtnPositifPart1 = new JRadioButton("Positive\r\n");
		rdbtnPositifPart1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnPositifPart1.isSelected()){
					for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
						ecout.setChargePart1(CHARGE_ELEMENTAIRE);
					}
				}
			}
		});
		chargePart1.add(rdbtnPositifPart1);
		rdbtnPositifPart1.setEnabled(false);
		rdbtnPositifPart1.setBounds(6, 37, 95, 23);
		panel_4.add(rdbtnPositifPart1);
		
		rdbtnNgativePart1 = new JRadioButton("N\u00E9gative");
		rdbtnNgativePart1.setSelected(true);
		chargePart1.add(rdbtnNgativePart1);
		rdbtnNgativePart1.setEnabled(false);
		rdbtnNgativePart1.setBounds(103, 37, 94, 23);
		panel_4.add(rdbtnNgativePart1);
		rdbtnNgativePart1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnNgativePart1.isSelected()){
					for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
						ecout.setChargePart1(-CHARGE_ELEMENTAIRE);
					}
				}
			}
		});

		
		rdbtnNeutrePart1 = new JRadioButton("Neutre");
		chargePart1.add(rdbtnNeutrePart1);
		rdbtnNeutrePart1.setEnabled(false);
		rdbtnNeutrePart1.setBounds(207, 37, 69, 23);
		panel_4.add(rdbtnNeutrePart1);
		rdbtnNeutrePart1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnNeutrePart1.isSelected()){
					for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
						ecout.setChargePart1(0);
					}
				}
				
			}
		});
		
		rdbtnNeutrePart2 = new JRadioButton("Neutre");
		chargePart2.add(rdbtnNeutrePart2);
		rdbtnNeutrePart2.setEnabled(false);
		rdbtnNeutrePart2.setBounds(207, 81, 69, 23);
		panel_4.add(rdbtnNeutrePart2);
		rdbtnNeutrePart2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnNeutrePart2.isSelected()){
					for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
						ecout.setChargePart2(0);
					}
				}
				
			}
		});
		
		rdbtnNgativePart2 = new JRadioButton("N\u00E9gative");
		chargePart2.add(rdbtnNgativePart2);
		rdbtnNgativePart2.setSelected(true);
		rdbtnNgativePart2.setEnabled(false);
		rdbtnNgativePart2.setBounds(103, 81, 94, 23);
		panel_4.add(rdbtnNgativePart2);
		rdbtnNgativePart2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnNgativePart2.isSelected()){
					for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
						ecout.setChargePart2(-CHARGE_ELEMENTAIRE);
					}
				}
			}
		});
		
		rdbtnPositifPart2 = new JRadioButton("Positive\r\n");
		chargePart2.add(rdbtnPositifPart2);
		rdbtnPositifPart2.setEnabled(false);
		rdbtnPositifPart2.setBounds(6, 81, 95, 23);
		panel_4.add(rdbtnPositifPart2);
		rdbtnPositifPart2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnPositifPart2.isSelected()){
					for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
						ecout.setChargePart2(CHARGE_ELEMENTAIRE);
					}
				}
			}
		});
		
		rdbtnPositifCocc = new JRadioButton("Positive\r\n");
		rdbtnPositifCocc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnPositifCocc.isSelected()){
					for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
						ecout.setChargeCocc(CHARGE_ELEMENTAIRE);
					}
				}
			}
		});
		chargeCocc.add(rdbtnPositifCocc);
		rdbtnPositifCocc.setSelected(true);
		rdbtnPositifCocc.setEnabled(false);
		rdbtnPositifCocc.setBounds(6, 129, 95, 23);
		panel_4.add(rdbtnPositifCocc);
		
		rdbtnNgativeCocc = new JRadioButton("N\u00E9gative");
		rdbtnNgativeCocc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnNgativeCocc.isSelected()){
					for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
						ecout.setChargeCocc(-CHARGE_ELEMENTAIRE);
					}
				}
			}
		});
		chargeCocc.add(rdbtnNgativeCocc);
		rdbtnNgativeCocc.setEnabled(false);
		rdbtnNgativeCocc.setBounds(103, 129, 94, 23);
		panel_4.add(rdbtnNgativeCocc);
		
		
		rdbtnNeutreCocc = new JRadioButton("Neutre");
		rdbtnNeutreCocc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnNeutreCocc.isSelected()){
					for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
						ecout.setChargeCocc(0);
					}
				}
				
			}
		});
		chargeCocc.add(rdbtnNeutreCocc);
		rdbtnNeutreCocc.setEnabled(false);
		rdbtnNeutreCocc.setBounds(207, 129, 69, 23);
		panel_4.add(rdbtnNeutreCocc);
		
		panel_3 = new JPanel();
		panel_3.setForeground(Color.WHITE);
		tabbedPane.addTab("Paramètres scientifique", null, panel_3, null);
		panel_3.setLayout(null);
		
		pnlScientifiqueScene2 = new JPanel();
		pnlScientifiqueScene2.setVisible(false);
		
		pnlScientifiqueScene1 = new JPanel();
		pnlScientifiqueScene1.setBackground(new Color(255, 255, 255));
		pnlScientifiqueScene1.setBounds(0, 0, 305, 450);
		panel_3.add(pnlScientifiqueScene1);
		pnlScientifiqueScene1.setLayout(null);
		
		pnlMir1 = new JPanel();
		pnlMir1.setFont(new Font("Tahoma", Font.BOLD, 11));
		pnlMir1.setBackground(SystemColor.menu);
		pnlMir1.setBorder(new TitledBorder(UIManager.getBorder("CheckBoxMenuItem.border"), "Miroir 1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlMir1.setBounds(5, 42, 295, 79);
		pnlScientifiqueScene1.add(pnlMir1);
		pnlMir1.setLayout(null);
		
		JLabel lblAngleIncident = new JLabel("Angle incident:");
		lblAngleIncident.setBounds(6, 16, 104, 14);
		pnlMir1.add(lblAngleIncident);
		lblAngleIncident.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblAngleReflechi = new JLabel("Angle r\u00E9fl\u00E9chie:\r\n");
		lblAngleReflechi.setBounds(158, 16, 90, 14);
		pnlMir1.add(lblAngleReflechi);
		lblAngleReflechi.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblAngleMir1Inicident = new JLabel("0 \u00B0");
		lblAngleMir1Inicident.setBounds(100, 16, 53, 14);
		pnlMir1.add(lblAngleMir1Inicident);
		lblAngleMir1Inicident.setForeground(Color.RED);
		lblAngleMir1Inicident.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		lblAngleMir1Reflechi = new JLabel("0 \u00B0");
		lblAngleMir1Reflechi.setBounds(255, 16, 46, 14);
		pnlMir1.add(lblAngleMir1Reflechi);
		lblAngleMir1Reflechi.setForeground(Color.RED);
		lblAngleMir1Reflechi.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		lblOrientation = new JLabel("Orientation:");
		lblOrientation.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblOrientation.setBounds(158, 47, 83, 14);
		pnlMir1.add(lblOrientation);
		
		orientationMir1 = new JLabel("45 \u00B0");
		orientationMir1.setFont(new Font("Tahoma", Font.BOLD, 13));
		orientationMir1.setForeground(Color.RED);
		orientationMir1.setBounds(255, 47, 46, 14);
		pnlMir1.add(orientationMir1);
		
		JPanel panel_8 = new JPanel();
		panel_8.setFocusable(false);
		panel_8.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel_8.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "normale", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_8.setBounds(6, 32, 147, 40);
		pnlMir1.add(panel_8);
		panel_8.setLayout(null);
		
		chkMir1N1 = new JCheckBox("1");
		chkMir1N1.setSelected(true);
		chkMir1N1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean valeurVerite;
				if(nbClics[0]%2==0){
					valeurVerite = false;
				}else{
					valeurVerite = true;
				}
				nbClics[0]++;
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormale(0, 0, valeurVerite);
				}
			}
		});
		chkMir1N1.setBounds(6, 15, 41, 20);
		panel_8.add(chkMir1N1);
		
		JCheckBox chkMir1N2 = new JCheckBox("2");
		chkMir1N2.setFocusable(false);
		chkMir1N2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean valeurVerite;
				if(nbClics[1]%2==0){
					valeurVerite = true;
				}else{
					valeurVerite = false;
				}
				nbClics[1]++;
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormale(0, 1, valeurVerite);
				}
			}
		});
		chkMir1N2.setBounds(68, 15, 41, 20);
		panel_8.add(chkMir1N2);
		
		pnlMir2 = new JPanel();
		pnlMir2.setBackground(new Color(255, 255, 255));
		pnlMir2.setLayout(null);
		pnlMir2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Miroir 2", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlMir2.setBounds(5, 119, 295, 79);
		pnlScientifiqueScene1.add(pnlMir2);
		
		label_6 = new JLabel("Angle incident:");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_6.setBounds(6, 16, 104, 14);
		pnlMir2.add(label_6);
		
		label_7 = new JLabel("Angle r\u00E9fl\u00E9chie:\r\n");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_7.setBounds(158, 17, 104, 14);
		pnlMir2.add(label_7);
		
		lblAngleMir2Inicident = new JLabel("0 \u00B0");
		lblAngleMir2Inicident.setForeground(Color.RED);
		lblAngleMir2Inicident.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAngleMir2Inicident.setBounds(100, 16, 53, 14);
		pnlMir2.add(lblAngleMir2Inicident);
		
		lblAngleMir2Reflechi = new JLabel("0 \u00B0");
		lblAngleMir2Reflechi.setForeground(Color.RED);
		lblAngleMir2Reflechi.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAngleMir2Reflechi.setBounds(255, 16, 53, 14);
		pnlMir2.add(lblAngleMir2Reflechi);
		
		label_10 = new JLabel("Orientation:");
		label_10.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_10.setBounds(158, 47, 83, 14);
		pnlMir2.add(label_10);
		
		orientationMir2 = new JLabel("-45 \u00B0");
		orientationMir2.setForeground(Color.RED);
		orientationMir2.setFont(new Font("Tahoma", Font.BOLD, 13));
		orientationMir2.setBounds(255, 47, 46, 14);
		pnlMir2.add(orientationMir2);
		
		JPanel panel_9 = new JPanel();
		panel_9.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel_9.setBackground(new Color(255, 255, 255));
		panel_9.setLayout(null);
		panel_9.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "normale", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_9.setBounds(6, 32, 147, 40);
		pnlMir2.add(panel_9);
		
		JCheckBox chkMir2N1 = new JCheckBox("1");
		chkMir2N1.setFocusable(false);
		chkMir2N1.setBackground(new Color(255, 255, 255));
		chkMir2N1.setSelected(true);
		chkMir2N1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean valeurVerite;
				if(nbClics[2]%2==0){
					valeurVerite = false;
				}else{
					valeurVerite = true;
				}
				nbClics[2]++;
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormale(1, 0, valeurVerite);
				}
			}
		});
		chkMir2N1.setBounds(6, 15, 41, 20);
		panel_9.add(chkMir2N1);
		
		JCheckBox chkMir2N2 = new JCheckBox("2");
		chkMir2N2.setFocusable(false);
		chkMir2N2.setBackground(new Color(255, 255, 255));
		chkMir2N2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean valeurVerite;
				if(nbClics[3]%2==0){
					valeurVerite = true;
				}else{
					valeurVerite = false;
				}
				nbClics[3]++;
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormale(1, 1, valeurVerite);
				}
			}
		});
		chkMir2N2.setBounds(68, 15, 41, 20);
		panel_9.add(chkMir2N2);
		
		pnlMir3 = new JPanel();
		pnlMir3.setBackground(SystemColor.menu);
		pnlMir3.setLayout(null);
		pnlMir3.setBorder(new TitledBorder(UIManager.getBorder("CheckBoxMenuItem.border"), "Miroir 3", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlMir3.setBounds(5, 198, 295, 79);
		pnlScientifiqueScene1.add(pnlMir3);
		
		label_12 = new JLabel("Angle incident:");
		label_12.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_12.setBounds(6, 16, 87, 14);
		pnlMir3.add(label_12);
		
		label_13 = new JLabel("Angle r\u00E9fl\u00E9chie:\r\n");
		label_13.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_13.setBounds(158, 16, 104, 14);
		pnlMir3.add(label_13);
		
		lblAngleMir3Inicident = new JLabel("0 \u00B0");
		lblAngleMir3Inicident.setForeground(Color.RED);
		lblAngleMir3Inicident.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAngleMir3Inicident.setBounds(100, 16, 46, 14);
		pnlMir3.add(lblAngleMir3Inicident);
		
		lblAngleMir3Reflechi = new JLabel("0 \u00B0");
		lblAngleMir3Reflechi.setForeground(Color.RED);
		lblAngleMir3Reflechi.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAngleMir3Reflechi.setBounds(255, 16, 46, 14);
		pnlMir3.add(lblAngleMir3Reflechi);
		
		label_16 = new JLabel("Orientation:");
		label_16.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_16.setBounds(158, 47, 83, 14);
		pnlMir3.add(label_16);
		
		orientationMir3 = new JLabel("45 \u00B0");
		orientationMir3.setForeground(Color.RED);
		orientationMir3.setFont(new Font("Tahoma", Font.BOLD, 13));
		orientationMir3.setBounds(255, 47, 46, 14);
		pnlMir3.add(orientationMir3);
		
		JPanel panel_10 = new JPanel();
		panel_10.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel_10.setLayout(null);
		panel_10.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "normale", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_10.setBounds(6, 32, 147, 40);
		pnlMir3.add(panel_10);
		
		JCheckBox chkMir3N1 = new JCheckBox("1");
		chkMir3N1.setFocusable(false);
		chkMir3N1.setSelected(true);
		chkMir3N1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean valeurVerite;
				if(nbClics[4]%2==0){
					valeurVerite = false;
				}else{
					valeurVerite = true;
				}
				nbClics[4]++;
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormale(2, 0, valeurVerite);
				}
			}
		});
		chkMir3N1.setBounds(6, 15, 41, 20);
		panel_10.add(chkMir3N1);
		
		JCheckBox chkMir3N2 = new JCheckBox("2");
		chkMir3N2.setFocusable(false);
		chkMir3N2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean valeurVerite;
				if(nbClics[5]%2==0){
					valeurVerite = true;
				}else{
					valeurVerite = false;
				}
				nbClics[5]++;
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormale(2, 1, valeurVerite);
				}
			}
		});
		chkMir3N2.setBounds(68, 15, 41, 20);
		panel_10.add(chkMir3N2);
		
		JLabel lblVitesseDeLa = new JLabel("Vitesse de la lumi\u00E8re dans le vide:");
		lblVitesseDeLa.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVitesseDeLa.setBounds(15, 17, 223, 14);
		pnlScientifiqueScene1.add(lblVitesseDeLa);
		
		JLabel lblNewLabel = new JLabel("3E8 m/s");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(230, 17, 64, 14);
		pnlScientifiqueScene1.add(lblNewLabel);
		
		pnlBleu = new JPanel();
		pnlBleu.setBackground(new Color(220, 220, 220));
		pnlBleu.setLayout(null);
		pnlBleu.setBorder(null);
		pnlBleu.setBounds(5, 374, 295, 70);
		pnlScientifiqueScene1.add(pnlBleu);
		
		pnlBleuDepart = new JPanel();
		pnlBleuDepart.setBackground(new Color(220, 220, 220));
		pnlBleuDepart.setBorder(new TitledBorder(null, "Entré", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlBleuDepart.setBounds(1, 8, 141, 63);
		pnlBleu.add(pnlBleuDepart);
		pnlBleuDepart.setLayout(null);
		
		pnlVert = new JPanel();
		pnlVert.setBackground(new Color(220, 220, 220));
		pnlVert.setLayout(null);
		pnlVert.setBorder(null);
		pnlVert.setBounds(5, 291, 295, 70);
		pnlScientifiqueScene1.add(pnlVert);
		
		label_8 = new JLabel("Angle incident:");
		label_8.setBounds(6, 16, 96, 23);
		pnlBleuDepart.add(label_8);
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		label_9 = new JLabel("Angle r\u00E9fract\u00E9:");
		label_9.setBounds(6, 29, 96, 34);
		pnlBleuDepart.add(label_9);
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		angleIncidentBleu = new JLabel("0 \u00B0");
		angleIncidentBleu.setBounds(97, 19, 46, 14);
		pnlBleuDepart.add(angleIncidentBleu);
		angleIncidentBleu.setForeground(Color.BLUE);
		angleIncidentBleu.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		angleRefractBleu = new JLabel("0 \u00B0");
		angleRefractBleu.setBounds(97, 39, 46, 14);
		pnlBleuDepart.add(angleRefractBleu);
		angleRefractBleu.setForeground(Color.BLUE);
		angleRefractBleu.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		lblTxtAngleCritiqueBleu = new JLabel("Angle critique:");
		lblTxtAngleCritiqueBleu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTxtAngleCritiqueBleu.setBounds(145, 29, 96, 23);
		pnlBleu.add(lblTxtAngleCritiqueBleu);
		
		lblAngleCritiqueBleu = new JLabel(calculerAngleCritiqueDefaut(1) +" \u00B0");
		lblAngleCritiqueBleu.setForeground(Color.BLUE);
		lblAngleCritiqueBleu.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAngleCritiqueBleu.setBounds(230, 33, 46, 14);
		pnlBleu.add(lblAngleCritiqueBleu);
		
		pnlNormaleBleu = new JPanel();
		pnlNormaleBleu.setBackground(new Color(220, 220, 220));
		pnlNormaleBleu.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "normale", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlNormaleBleu.setBounds(152, 55, 124, 46);
		pnlBleu.add(pnlNormaleBleu);
		pnlNormaleBleu.setLayout(null);
		
		chkMilieuBleu1 = new JCheckBox("1");
		chkMilieuBleu1.setFocusable(false);
		chkMilieuBleu1.setBackground(new Color(220, 220, 220));
		chkMilieuBleu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean valeurVerite;
				if(nbClicBleu[0]%2==0){
					valeurVerite = true;
				}else{
					valeurVerite = false;
				}
				nbClicBleu[0]++;
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormaleBleu(0, valeurVerite);
				}
			}
		});
		chkMilieuBleu1.setBounds(6, 16, 38, 22);
		pnlNormaleBleu.add(chkMilieuBleu1);
		
		chkMilieuBleu3 = new JCheckBox("3");
		chkMilieuBleu3.setFocusable(false);
		chkMilieuBleu3.setBackground(new Color(220, 220, 220));
		chkMilieuBleu3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean valeurVerite;
				if(nbClicBleu[2]%2==0){
					valeurVerite = true;
				}else{
					valeurVerite = false;
				}
				nbClicBleu[2]++;
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormaleBleu(2, valeurVerite);
				}
			}
		});
		chkMilieuBleu3.setBounds(93, 16, 46, 22);
		pnlNormaleBleu.add(chkMilieuBleu3);
		
		chkMilieuBleu2 = new JCheckBox("2");
		chkMilieuBleu2.setFocusable(false);
		chkMilieuBleu2.setBackground(new Color(220, 220, 220));
		chkMilieuBleu2.setSelected(true);
		chkMilieuBleu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean valeurVerite;
				if(nbClicBleu[1]%2==0){
					valeurVerite = false;
				}else{
					valeurVerite = true;
				}
				nbClicBleu[1]++;
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormaleBleu(1, valeurVerite);
				}
			}
		});
		chkMilieuBleu2.setBounds(46, 16, 46, 23);
		pnlNormaleBleu.add(chkMilieuBleu2);
		
		pnlBleuArrive= new JPanel();
		pnlBleuArrive.setBackground(new Color(220, 220, 220));
		pnlBleuArrive.setBounds(1, 77, 141, 63);
		pnlBleu.add(pnlBleuArrive);
		pnlBleuArrive.setBorder(new TitledBorder(null, "Sortie", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlBleuArrive.setLayout(null);
		
		JLabel lblAngleIncident_1 = new JLabel("Angle incident:");
		lblAngleIncident_1.setBackground(new Color(220, 220, 220));
		lblAngleIncident_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAngleIncident_1.setBounds(7, 12, 96, 23);
		pnlBleuArrive.add(lblAngleIncident_1);
		
		lblAngleRfract = new JLabel("Angle r\u00E9fract\u00E9");
		lblAngleRfract.setBackground(new Color(220, 220, 220));
		lblAngleRfract.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAngleRfract.setBounds(7, 27, 96, 34);
		pnlBleuArrive.add(lblAngleRfract);
	
		
		lblAngleRefractionBleu2 = new JLabel("0 \u00B0");
		lblAngleRefractionBleu2.setBackground(new Color(220, 220, 220));
		lblAngleRefractionBleu2.setForeground(Color.BLUE);
		lblAngleRefractionBleu2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAngleRefractionBleu2.setBounds(98, 37, 46, 14);
		pnlBleuArrive.add(lblAngleRefractionBleu2);
		
		lblAngleIncident2Bleu = new JLabel("0 \u00B0");
		lblAngleIncident2Bleu.setBackground(new Color(220, 220, 220));
		lblAngleIncident2Bleu.setForeground(Color.BLUE);
		lblAngleIncident2Bleu.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAngleIncident2Bleu.setBounds(98, 16, 46, 14);
		pnlBleuArrive.add(lblAngleIncident2Bleu);
		
		sclrMiieuBleu = new Scrollbar();
		sclrMiieuBleu.setMaximum(35);
		sclrMiieuBleu.setBounds(278, 1, 15, 67);
		pnlBleu.add(sclrMiieuBleu);
		sclrMiieuBleu.addAdjustmentListener(new AdjustmentListener() {
			double incre = 0, increIni = 0, increment = 0;
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				incre = sclrMiieuBleu.getValue();
				increment = -3*(incre - increIni) +increment;
				increIni = incre;
				pnlBleuDepart.setBounds(1, (int) (8 + increment), 141, 63);
				pnlBleuArrive.setBounds(1, (int) (75 + increment), 141, 63);
				lblTxtAngleCritiqueBleu.setBounds(142, (int) (29 + increment), 96, 23);
				lblAngleCritiqueBleu.setBounds(230, (int) (33 + increment), 46, 14);
				pnlNormaleBleu.setBounds(152,(int)(55 + increment), 124, 46);
			}
		});
		
		pnlVertArrive = new JPanel();
		pnlVertArrive.setBackground(new Color(220, 220, 220));
		pnlVertArrive.setLayout(null);
		pnlVertArrive.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Sortie", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlVertArrive.setBounds(1, 75, 141, 63);
		pnlVert.add(pnlVertArrive);
		
		label_11 = new JLabel("Angle r\u00E9fract\u00E9:");
		label_11.setBackground(new Color(220, 220, 220));
		label_11.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_11.setBounds(7, 36, 96, 22);
		pnlVertArrive.add(label_11);
		
		lblAngleIncidentVert2 = new JLabel("0 \u00B0");
		lblAngleIncidentVert2.setBackground(new Color(220, 220, 220));
		lblAngleIncidentVert2.setForeground(Color.GREEN);
		lblAngleIncidentVert2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAngleIncidentVert2.setBounds(97, 18, 46, 14);
		pnlVertArrive.add(lblAngleIncidentVert2);
		
		lblAngleRefractVert2 = new JLabel("0 \u00B0");
		lblAngleRefractVert2.setBackground(new Color(220, 220, 220));
		lblAngleRefractVert2.setForeground(Color.GREEN);
		lblAngleRefractVert2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAngleRefractVert2.setBounds(97, 39, 46, 14);
		pnlVertArrive.add(lblAngleRefractVert2);
		
		label_17 = new Label("Angle incident:");
		label_17.setBackground(new Color(220, 220, 220));
		label_17.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_17.setBounds(7, 14, 89, 23);
		pnlVertArrive.add(label_17);
		
		
		JPanel pnlVertDepart = new JPanel();
		pnlVertDepart.setBackground(new Color(220, 220, 220));
		pnlVertDepart.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Entr\u00E9", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlVertDepart.setBounds(1, 8, 141, 63);
		pnlVert.add(pnlVertDepart);
		pnlVertDepart.setLayout(null);
		
		label_18 = new JLabel("Angle r\u00E9fract\u00E9:");
		label_18.setBounds(8, 37, 96, 22);
		pnlVertDepart.add(label_18);
		label_18.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		angleIncidentVert = new JLabel("0 \u00B0");
		angleIncidentVert.setBounds(97, 20, 46, 14);
		pnlVertDepart.add(angleIncidentVert);
		angleIncidentVert.setForeground(Color.GREEN);
		angleIncidentVert.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		angleRefractVert = new JLabel("0 \u00B0");
		angleRefractVert.setBounds(97, 41, 46, 14);
		pnlVertDepart.add(angleRefractVert);
		angleRefractVert.setForeground(Color.GREEN);
		angleRefractVert.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		Label lblTxtAngleIncidentVert1 = new Label("Angle incident:");
		lblTxtAngleIncidentVert1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTxtAngleIncidentVert1.setBounds(6, 16, 89, 22);
		pnlVertDepart.add(lblTxtAngleIncidentVert1);
		
		lblTxtAngleCritqueVert = new JLabel("Angle critique:");
		lblTxtAngleCritqueVert.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTxtAngleCritqueVert.setBounds(145, 28, 96, 23);
		pnlVert.add(lblTxtAngleCritqueVert);
		
		lblAngleCritiqueVert = new JLabel(calculerAngleCritiqueDefaut(0) +" \u00B0");
		lblAngleCritiqueVert.setForeground(Color.GREEN);
		lblAngleCritiqueVert.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAngleCritiqueVert.setBounds(232, 32, 46, 14);
		pnlVert.add(lblAngleCritiqueVert);
		
		pnlNormaleVert = new JPanel();
		pnlNormaleVert.setBackground(new Color(220, 220, 220));
		pnlNormaleVert.setLayout(null);
		pnlNormaleVert.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "normale", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlNormaleVert.setBounds(152, 57, 119, 75);
		pnlVert.add(pnlNormaleVert);
		
		chkMilieuVert1 = new JCheckBox("1");
		chkMilieuVert1.setFocusable(false);
		chkMilieuVert1.setBackground(new Color(220, 220, 220));
		chkMilieuVert1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean valeurVerite;
				if(chkMilieuVert1.isSelected()){
					valeurVerite = true;
				}else{
					valeurVerite = false;
				}
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormaleVert(0, valeurVerite);
					}
			}
		});
		chkMilieuVert1.setBounds(17, 16, 46, 22);
		pnlNormaleVert.add(chkMilieuVert1);
		
		chkMilieuVert3 = new JCheckBox("3");
		chkMilieuVert3.setFocusable(false);
		chkMilieuVert3.setBackground(new Color(220, 220, 220));
		chkMilieuVert3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean valeurVerite;
				if(chkMilieuVert3.isSelected()){
					valeurVerite = true;
				}else{
					valeurVerite = false;
				}
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormaleVert(2, valeurVerite);
					}
			}
		});
		chkMilieuVert3.setBounds(65, 16, 46, 22);
		pnlNormaleVert.add(chkMilieuVert3);
		
		chkMilieuVert2 = new JCheckBox("2");
		chkMilieuVert2.setFocusable(false);
		chkMilieuVert2.setBackground(new Color(220, 220, 220));
		chkMilieuVert2.setSelected(true);
		chkMilieuVert2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean valeurVerite;
				if(chkMilieuVert2.isSelected()){
					valeurVerite = true;
				}else{
					valeurVerite = false;
				}
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormaleVert(1, valeurVerite);
					}
			}
		});
		chkMilieuVert2.setBounds(17, 41, 46, 23);
		pnlNormaleVert.add(chkMilieuVert2);
		
		chkMilieuVert4 = new JCheckBox("4");
		chkMilieuVert4.setFocusable(false);
		chkMilieuVert4.setBackground(new Color(220, 220, 220));
		chkMilieuVert4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean valeurVerite;
				if(chkMilieuVert4.isSelected()){
					valeurVerite = true;
				}else{
					valeurVerite = false;
				}
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
					ecout.choisirNormaleVert(3, valeurVerite);
					}
			}
		});
		chkMilieuVert4.setBounds(65, 41, 46, 23);
		pnlNormaleVert.add(chkMilieuVert4);
		
		Scrollbar scrMilieuVert = new Scrollbar();
		scrMilieuVert.setMaximum(35);
		scrMilieuVert.setBounds(278, 1, 15, 67);
		scrMilieuVert.addAdjustmentListener(new AdjustmentListener() {
			double incre = 0, increIni = 0, increment = 0;
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				incre = scrMilieuVert.getValue();
				increment = -3*(incre - increIni) +increment;
				increIni = incre;
				pnlVertDepart.setBounds(1, (int) (8 + increment), 141, 63);
				pnlVertArrive.setBounds(1, (int) (75 + increment), 141, 63);
				lblAngleCritiqueVert.setBounds(230, (int) (33 + increment), 46, 14);
				pnlNormaleVert.setBounds(152,(int) (57 + increment), 119, 75);
				lblTxtAngleCritqueVert.setBounds(141, (int)(28 + increment), 96, 23);
			}
		});
		pnlVert.add(scrMilieuVert);
		
		label_14 = new Label("Milieu Vert :");
		label_14.setFont(new Font("Dialog", Font.BOLD, 12));
		label_14.setBounds(12, 280, 93, 9);
		pnlScientifiqueScene1.add(label_14);
		
		label_15 = new Label("Milieu Bleu :\r\n");
		label_15.setFont(new Font("Dialog", Font.BOLD, 12));
		label_15.setBounds(12, 363, 93, 9);
		pnlScientifiqueScene1.add(label_15);
		
		pnlScientifiqueScene1.setVisible(true);
		pnlScientifiqueScene2.setBounds(0, 0, 305, 450);
		panel_3.add(pnlScientifiqueScene2);
		pnlScientifiqueScene2.setLayout(null);
		
		panel_6 = new JPanel();
		panel_6.setBackground(Color.LIGHT_GRAY);
		panel_6.setBounds(20, 205, 265, 154);
		pnlScientifiqueScene2.add(panel_6);
		panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Particule 2 : ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_6.setLayout(null);
		
		JLabel lblVitesse2 = new JLabel("Vitesse:");
		lblVitesse2.setBounds(26, 29, 56, 14);
		panel_6.add(lblVitesse2);
		lblVitesse2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		vitesse2 = new JLabel("0");
		vitesse2.setBounds(87, 29, 91, 14);
		panel_6.add(vitesse2);
		vitesse2.setForeground(Color.RED);
		vitesse2.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		lblCharge_1 = new JLabel("Charge");
		lblCharge_1.setBounds(26, 50, 46, 24);
		panel_6.add(lblCharge_1);
		lblCharge_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblCharge2 = new JLabel(-chargeAffichableInitale+" C");
		lblCharge2.setBounds(87, 56, 157, 14);
		panel_6.add(lblCharge2);
		lblCharge2.setForeground(Color.RED);
		lblCharge2.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		lblForce2 = new JLabel("0 N");
		lblForce2.setForeground(Color.RED);
		lblForce2.setFont(new Font("Cambria Math", Font.BOLD, 13));
		lblForce2.setBounds(87, 81, 240, 14);
		panel_6.add(lblForce2);
		
		lblForce = new JLabel("Force:");
		lblForce.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblForce.setBounds(26, 81, 46, 14);
		panel_6.add(lblForce);
		
		label_4 = new JLabel("Masse:");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_4.setBounds(26, 106, 46, 14);
		panel_6.add(label_4);
		
		lblMasse2 = new JLabel("0 kg");
		lblMasse2.setForeground(Color.RED);
		lblMasse2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMasse2.setBounds(87, 101, 135, 24);
		panel_6.add(lblMasse2);
		
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(20, 20, 265, 174);
		pnlScientifiqueScene2.add(panel);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Particule 1 : ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setLayout(null);
		
		JLabel lblVitesseParticule = new JLabel("Vitesse:");
		lblVitesseParticule.setBounds(26, 25, 51, 14);
		panel.add(lblVitesseParticule);
		lblVitesseParticule.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		vitesse1 = new JLabel("0");
		vitesse1.setBounds(87, 25, 125, 14);
		panel.add(vitesse1);
		vitesse1.setForeground(Color.RED);
		vitesse1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblCharge = new JLabel("Charge:");
		lblCharge.setBounds(26, 50, 51, 24);
		panel.add(lblCharge);
		lblCharge.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblCharge1 = new JLabel(-chargeAffichableInitale+" C");
		lblCharge1.setBounds(87, 55, 147, 14);
		panel.add(lblCharge1);
		lblCharge1.setForeground(Color.RED);
		lblCharge1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblForceApplique = new JLabel("Force:");
		lblForceApplique.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblForceApplique.setBounds(26, 85, 51, 14);
		panel.add(lblForceApplique);
		
		lblForce1 = new JLabel("0 N");
		lblForce1.setForeground(Color.RED);
		lblForce1.setFont(new Font("Cambria Math", Font.BOLD, 13));
		lblForce1.setBounds(87, 85, 244, 14);
		panel.add(lblForce1);
		
		lblMasse = new JLabel("Masse:");
		lblMasse.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMasse.setBounds(26, 115, 46, 14);
		panel.add(lblMasse);
		
		lblMasse1 = new JLabel("0 kg");
		lblMasse1.setForeground(Color.RED);
		lblMasse1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMasse1.setBounds(87, 110, 125, 24);
		panel.add(lblMasse1);
		
		panel_7 = new JPanel();
		panel_7.setBackground(Color.LIGHT_GRAY);
		panel_7.setBounds(20, 370, 265, 63);
		pnlScientifiqueScene2.add(panel_7);
		panel_7.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Coccinelle : ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_7.setLayout(null);
		
		vitesse3 = new JLabel("0");
		vitesse3.setBounds(89, 16, 91, 14);
		panel_7.add(vitesse3);
		vitesse3.setForeground(Color.RED);
		vitesse3.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblVitesseCoccinelle = new JLabel("Vitesse: ");
		lblVitesseCoccinelle.setBounds(26, 16, 50, 14);
		panel_7.add(lblVitesseCoccinelle);
		lblVitesseCoccinelle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblCharge_2 = new JLabel("Charge: ");
		lblCharge_2.setBounds(26, 37, 58, 21);
		panel_7.add(lblCharge_2);
		lblCharge_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblCharge3 = new JLabel(chargeAffichableInitale+" C");
		lblCharge3.setBounds(89, 40, 142, 14);
		panel_7.add(lblCharge3);
		lblCharge3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCharge3.setForeground(Color.RED);
		
		dtrpnDansLaPremire = new JEditorPane();
		dtrpnDansLaPremire.setFont(new Font("Tahoma", Font.BOLD, 13));
		dtrpnDansLaPremire.setForeground(Color.WHITE);
		dtrpnDansLaPremire.setBackground(new Color(47, 79, 79));
		dtrpnDansLaPremire.setEditable(false);
		dtrpnDansLaPremire.setBounds(10, 10, 270, 410);
		dtrpnDansLaPremire.setText("  L'objetif du niveau un :\r\n\r\nDans la premi\u00E8re partie de ce niveau, vous\r\ndevez briser tous les blocs de vitres afin\r\nde d\u00E9bloquer tous les particules ainsi que \r\nlepoint d\u2019arriv\u00E9. Pour ce faire, vous devez\r\nfaire d\u00E9vier le laser avec les miroirs \r\n(r\u00E9flexion) ainsi que les milieux (r\u00E9fraction)\r\n.Durant cette partie du niveau, vous ne \r\npouvez pas contr\u00F4ler la coccinelle.\r\n\r\nD\u00E8s que vous avez d\u00E9bloqu\u00E9 tous les blocs\r\nde vitres, vous pourriez, enfin, contr\u00F4ler\r\nla coccinelle en dessinant sa trajectoire.\r\nAlors, votre objectif sera d\u2019amener les\r\ndeux particules au point d\u2019arriver sans se \r\nfaire manger par la coccinelle. Vous \r\npouvez ne faire qu\u2019un trac\u00E9 de trajectoire \r\npour amener vos particules. Ne vous \r\ninqui\u00E9tez pas! D\u00E8s que vous amenez une \r\nparticule au point d\u2019arriver, vous pourriez\r\nredessiner une trajectoire pour amener la\r\ndeuxi\u00E8me particule. \r\n");
		dtrpnDansLaPremire.setVisible(true);
	
	
		scrollPane = new JScrollPane(dtrpnDansLaPremire);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVisible(true);
		tabbedPane.addTab(" ? Comment jouer ? ", null, scrollPane, null);
		
		sliderAngleRec.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				for(Niveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau1Listener.class) ) {
				ecout.setAngleRec(Math.toRadians(sliderAngleRec.getValue()));
				}
			}
		});
	}
	public void addNiveau1Listener(Niveau1Listener objEcouteur) {
		OBJETS_ENREGISTRES.add(Niveau1Listener.class, objEcouteur);
	}
	/**
	 * Cette méthode permet de bloquer et de débloquer les bon paramètres pour les deux scènes.
	 * @param valeurVerite1 l'état de la première scène.
	 * @param valeurVerite2 l'état de la deuxième scène.
	 */
	public void debloquerParametreScenes(boolean valeurVerite1, boolean valeurVerite2){
		spiIndiceMilieuVert.setEnabled(valeurVerite1);
		spiIndiceMilieuBleu.setEnabled(valeurVerite1);
		sliderAngleRec.setEnabled(valeurVerite1);
		sliderAngleTri.setEnabled(valeurVerite1);
		rdbtnNgativePart1.setEnabled(valeurVerite2);
		rdbtnPositifPart1.setEnabled(valeurVerite2);
		rdbtnNeutrePart1.setEnabled(valeurVerite2);
		rdbtnNgativePart2.setEnabled(valeurVerite2);
		rdbtnPositifPart2.setEnabled(valeurVerite2);
		rdbtnNeutrePart2.setEnabled(valeurVerite2);
		rdbtnNgativeCocc.setEnabled(valeurVerite2);
		rdbtnPositifCocc.setEnabled(valeurVerite2);
		rdbtnNeutreCocc.setEnabled(valeurVerite2);
		pnlScientifiqueScene2.setVisible(valeurVerite2);
		pnlScientifiqueScene1.setVisible(valeurVerite1);
		btnArreter.setEnabled(false);
		buttonInitialiser.setEnabled(false);
		scene1EnCours = valeurVerite1;
		scene2EnCours = valeurVerite2;
	}
	/**
	 * Cette méthode permet d'afficher la vitesse de la première particule.
	 * @param vitesse la vitesse de la 1er particule.
	 */
	public void afficherVitesse1(double vitesse){
		double vitesseTransforme = round(vitesse,2);
		vitesse1.setText(vitesseTransforme+" m/s");
	}
	/**
	 *  Cette méthode permet d'afficher la vitesse de la deuxième particule.
	 * @param vitesse la vitesse de la 2er particule.
	 */
	public void afficherVitesse2(double vitesse){
		double vitesseTransforme = round(vitesse,2);
		vitesse2.setText(vitesseTransforme+" m/s");
	}
	/**
	 * Cette méthode permet d'afficher la vitesse de la coccinelle.
	 * @param vitesse la vitesse de la coccinelle.
	 */
	public void afficherVitesseCoccinelle(double vitesse) {
		double vitesseTransforme = round(vitesse,2);
		vitesse3.setText(vitesseTransforme+" m/s");
	}
	/**
	 * Cette méthode permet de modifier le nombre de chiffres significatifs sur une valeur. 
	 * @param valueToRound la valeur à modifier.
	 * @param numberOfDecimalPlaces le nombre de chiffres significatif.
	 * @return le nouveau nombre modifié.
	 */
	public static double round(double valueToRound, int numberOfDecimalPlaces){
	    double multipicationFactor = Math.pow(10, numberOfDecimalPlaces);
	    double interestedInZeroDPs = valueToRound * multipicationFactor;
	    return Math.round(interestedInZeroDPs) / multipicationFactor;
	}
	/**
	 * Cette méthode permet d'afficher la charge des particules.
	 * @param charge la charge de la particule.
	 * @param nbPart la particule auquel on viens de modifier sa charge.
	 */
	public void afficherCharge(double charge, int nbPart) {
		double chargeAfficher = round(charge,21);
		if(nbPart ==0){
			lblCharge2.setText(chargeAfficher+" C");
		}else{
			lblCharge1.setText(chargeAfficher+" C");
		}
		
	}
	/**
	 * Cette méthode permet d'afficher la charge de la coccinelle
	 * @param charge la charge de la coccinelle.
	 */
	public void afficherChargeCocc(double charge) {
		double chargeAfficher = round(charge,21);
		lblCharge3.setText(chargeAfficher+" C");
	}
	/**
	 * Cette méthode permet d'afficher les forces appliquées sur les particules.
	 * @param force la force appliquée sur la particules.
	 * @param nbPart la particule auquel la force à été appliquée.
	 */
	public void afficherForce(Vector2d force, int nbPart) {
		double posXAffichage = round(force.getX()*Math.pow(10, 31),2);
		double posYAffichage = round(force.getY()*Math.pow(10, 31),2);
		if(nbPart ==0){
			lblForce2.setText("( "+posXAffichage+"i\u20D7 "+posYAffichage+"j\u20D7 )E-31 N");
		}else{
			lblForce1.setText("( "+posXAffichage+"i\u20D7 "+posYAffichage+"j\u20D7 )E-31 N");
		}
	}
	/**
	 * Cette méthode permet d'afficher la masse des particules.
	 * @param masse la masse de la particule à afficher.
	 * @param nbPart la particule auquel la masse à été modifié.
	 */
	public void afficherMasse(double masse, int nbPart) {
		double masseAffiche = round(masse,33);
		if(nbPart ==0){
			lblMasse2.setText(masseAffiche+" kg");
		}else{
			lblMasse1.setText(masseAffiche+" kg");
		}
		
	}
	/**
	 * Cette méthode permet d'afficher l'angle incident de la lumière par un des miroirs.
	 * @param nbMir le miroir en question.
	 * @param angle l'angle incident.
	 */
	public void afficherAngleIncidentMir(int nbMir, double angle){
		double angleDegree = angle*360/2*Math.PI;
		int nbTours = (int)angleDegree/90;
		angleDegree -= nbTours*90;
		double angleAfficher = round(angleDegree,1);
		//System.out.println(angleAfficher + " " + nbMir);
		switch(nbMir){
			case 0:
				//System.out.println("1");
				lblAngleMir1Inicident.setText(angleAfficher + "\u00B0");
				break;
			case 1:
				//System.out.println("2");
				lblAngleMir2Inicident.setText(angleAfficher + "\u00B0");
				break;
			case 2:
				//System.out.println("3");
				lblAngleMir3Inicident.setText(angleAfficher + "\u00B0");
				break;
		}
	}
	/**
	 * Cette méthode permet d'afficher l'angle réflechi de la lumière par un des miroirs.
	 * @param nbMir le miroir en question.
	 * @param angle l'angle réflechi.
	 */
	public void afficherAngleReflechiMir(int nbMir, double angle) {
		double angleDegree = angle*360/2*Math.PI;
		int nbTours = (int)angleDegree/90;
		angleDegree -= nbTours*90;
		double angleAfficher = round(angleDegree,1);
		//System.out.println(angleAfficher + " " + nbMir);
		switch(nbMir){
			case 0:
				//System.out.println("1");
				lblAngleMir3Reflechi.setText(angleAfficher + "\u00B0");
				break;
			case 1:
				//System.out.println("2");
				lblAngleMir2Reflechi.setText(angleAfficher + "\u00B0");
				break;
			case 2:
				//System.out.println("3");
				lblAngleMir1Reflechi.setText(angleAfficher + "\u00B0");
				break;
		}
	}
	/**
	 * Cette méthode permet d'afficher l'angle critique d'un milieu quelconque.
	 * @param nbMilieu le milieu en question.
	 * @param angle l'angle critique de ce milieu
	 */
	public void afficherAngleCritique(int nbMilieu, double angle) {
		double angleDegree = angle*360/2*Math.PI;
		int nbTours = (int)angleDegree/90;
		angleDegree -= nbTours*90;
		double angleAfficher = round(angleDegree,1);
		if(nbMilieu == 0){
			lblAngleCritiqueVert.setText(angleAfficher + "\u00B0");
		}else{
			lblAngleCritiqueBleu.setText(angleAfficher + "\u00B0");
		}
	}
	
	/**
	 * Cette méthode permet de calculer l'angle critique par defaut du milieu.
	 * @param nbMilieu le milieu en question.
	 * @return l'angle critique du milieu
	 */
	private double calculerAngleCritiqueDefaut(double nbMilieu){
		double angle;
		if(nbMilieu == 0){
			angle = Math.atan(1.33/1);
		}else{
			angle = Math.atan(2/1);
		}
		double angleDegree = angle*360/2*Math.PI;
		int nbTours = (int)angleDegree/90;
		angleDegree -= nbTours*90;
		double angleAfficher = round(angleDegree,1);
		return angleAfficher;
	}
	
	/**
	 * 
	 * Cette méthode permet d'afficher des informations sur l'orientation de la lumière lors du contact avec les milieux
	 * @param i permet de déterminer soit lorsque la lumière entre ou sort du milieu
	 * @param nbMilieu permet de choisir le milieu en question.
	 * @param angle l'angle réfracter de la lumière dans le milieu
	 */
	public void afficherAngleReflechiMilieu(int i, int nbMilieu, double angle) {
		double angleDegree = angle*360/2*Math.PI;
		int nbTours = (int)angleDegree/90;
		angleDegree -= nbTours*90;
		double angleAfficher = round(angleDegree,1);
		if(i==1){
			if(nbMilieu ==0){
				angleRefractVert.setText(angleAfficher + "\u00B0");
			}else{
				angleRefractBleu.setText(angleAfficher + "\u00B0");
			}
		}else{
			if(nbMilieu ==0){
				lblAngleRefractVert2.setText(angleAfficher + "\u00B0");
			}else{
				lblAngleRefractionBleu2.setText(angleAfficher + "\u00B0");
			}
		}
	
		
	}
	/**
	 * Cette méthode permet d'afficher des informations sur l'orientation de la lumière lors du contact avec les milieux
	 * @param i permet de déterminer soit lorsque la lumière entre ou sort du milieu
	 * @param nbMilieu permet de choisir le milieu en question.
	 * @param angle l'angle incident de la lumière dans le milieu
	 */
	public void afficherAngleIncidentMilieu(int i, int nbMilieu, double angle) {
		double angleDegree = angle*360/2*Math.PI;
		int nbTours = (int)angleDegree/90;
		angleDegree -= nbTours*90;
		double angleAfficher = round(angleDegree,1);
		if(i==1){
			if(nbMilieu ==0){
				angleIncidentVert.setText(angleAfficher + "\u00B0");
			}else{
				angleIncidentBleu.setText(angleAfficher + "\u00B0");
			}
		}else{
			if(nbMilieu ==0){
				lblAngleIncidentVert2.setText(angleAfficher + "\u00B0");
			}else{
				lblAngleIncident2Bleu.setText(angleAfficher + "\u00B0");
			}
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
	 * Cette méthode permet de remettre les curseurs à zéro.
	 * @param nbMilieu le milieu qu'on veut remettre son curseur à zéro.
	 */
	public void remiseRotationMilieu(int nbMilieu) {
		if(nbMilieu ==0){
			sliderAngleRec.setValue(0);
		}else{
			sliderAngleTri.setValue(0);
		}
		
	}
	/**
	 * Cette méthode permet de débloquer ou de bloquer certains paramètres.
	 * @param b  <p><b>true</b> les paramètres sont débloquer
	 * <p><b>false</b>  les paramètres sont bloquer
	 */
	public void debloquerParametres(boolean b) {
		if(scene2EnCours){
		rdbtnNeutreCocc.setEnabled(b);
		rdbtnNeutrePart1.setEnabled(b);
		rdbtnNeutrePart2.setEnabled(b);
		rdbtnNgativeCocc.setEnabled(b);
		rdbtnNgativePart1.setEnabled(b);
		rdbtnNgativePart2.setEnabled(b);
		rdbtnPositifCocc.setEnabled(b);
		rdbtnPositifPart1.setEnabled(b);
		rdbtnPositifPart2.setEnabled(b);
		btnArreter.setEnabled(!b);
		}
		buttonInitialiser.setEnabled(!b);
	}
	
}
