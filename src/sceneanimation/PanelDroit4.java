package sceneanimation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import ecouteur.Niveau4Listener;
import ecouteur.TransmissionInfoNiveau1Listener;

import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;

import physique.Vector2d;
import physique.Vector3d;

import javax.swing.JScrollPane;

import java.awt.Component;

import javax.swing.ScrollPaneConstants;
import javax.swing.JRadioButton;
/**
 * Classe panelDroit4 du jeu de coccinelle, le panelDroit4 est associé avec le niveauQuatre pour afficher et loader les paramètres
 * @author Kevin Takla
 * @version 2.0
 */
public class PanelDroit4 extends JPanel {
	private static final long serialVersionUID = 1L;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList(); //collection
	private int cptArret = 0;
	private JTabbedPane tabbedPane;
	private boolean scientifique = false;
	private JSpinner spnChampMagnetiqueSelec;
	private JLabel lblFreq ;
	private JSpinner spnMasseCocc;
	private JLabel lblPos;
	private JSpinner spnChargeCocc;
	private JSpinner spnPotElec;
	private JSpinner spnChampMagneCyclo;
	private JSpinner spnChampElectriqueSelec;
	private JLabel lblNomObjet;
	private JRadioButton rdbtnFrequence;
	private JSpinner spnChampMagneSpectroM;
	private JButton btnArreter;
	private JToggleButton tglbtnZoom;
	private final ButtonGroup Zoom = new ButtonGroup();
	private JLabel lblVitesseCocc;
	private JToggleButton tglbtnReculer;
	private JPanel pnlSpectrometreScience;
	private JLabel lblTxt1;
	private JLabel lblForceMagnetiqueSpectro;
	private JLabel label_3;
	private JLabel lblRayon;
	private JLabel lblAcclerationCentripte;
	private JRadioButton rdbtnPeriode;
	private JLabel lblChamp;
	private JLabel lblForceElectrique;
	private JLabel lblForceMagnetiqueSelec;
	private JLabel lblVitesseSelecteur;
	private JEditorPane dtrpnDansCeNiveau;
	private JButton btnInitialiser;
	private int cptClicSolution =0;
	private JLabel lblAcceleration ;
	private JButton btnAide;
	private final ButtonGroup btnGrpPerFre = new ButtonGroup();
	/**
	 * Create the panel.
	 */
	public PanelDroit4() {
		setPreferredSize(new Dimension(325, 600));
		setLayout(null);

		btnArreter = new JButton("Arr\u00EAter");
		btnArreter.setEnabled(false);
		btnArreter.setFocusable(false);
		btnArreter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cptArret%2 ==0){
					for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
						ecout.arreter();
					}
					btnArreter.setText("redémarrer");
				}else{
					for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
						ecout.demarrer();
					}
					btnArreter.setText("Arr\u00EAter");
				}
				cptArret++;
				btnInitialiser.setEnabled(true);
			}
		});
		btnArreter.setBounds(34, 503, 117, 34);
		add(btnArreter);

		btnInitialiser = new JButton("Initialiser");
		btnInitialiser.setEnabled(false);
		btnInitialiser.setFocusable(false);
		btnInitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.initialiser();
				}
				btnInitialiser.setEnabled(false);
				debloquerParametres(true);
			}
		});
		btnInitialiser.setBounds(175, 503, 117, 34);
		add(btnInitialiser);

		JButton btnScientifique = new JButton("Scientifique");
		btnScientifique.setFocusable(false);
		btnScientifique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!scientifique){
					scientifique = true;
					btnScientifique.setText("Mode normal");
					tabbedPane.setSelectedIndex(2);
					for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
						ecout.modeScientifique(scientifique);
					}

				}else{
					scientifique = false;
					btnScientifique.setText("Scientifique");
					tabbedPane.setSelectedIndex(0);
					for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
						ecout.modeScientifique(scientifique);
					}
				}
			}
		});
		btnScientifique.setBounds(34, 555, 117, 34);
		add(btnScientifique);

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setFocusable(false);
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnQuitter.setBounds(175, 555, 117, 34);
		add(btnQuitter);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 305, 481);
		add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Panneau de contrôle", null, panel, null);
		panel.setLayout(null);

		JPanel pnlCoccinelle = new JPanel();
		pnlCoccinelle.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Coccinelle", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlCoccinelle.setBounds(6, 306, 284, 47);
		panel.add(pnlCoccinelle);
		pnlCoccinelle.setLayout(null);

		JLabel lblMasseTxt = new JLabel("Masse: ");
		lblMasseTxt.setBounds(6, 20, 52, 14);
		pnlCoccinelle.add(lblMasseTxt);

		spnMasseCocc = new JSpinner();
		spnMasseCocc.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.setMasse((double)(spnMasseCocc.getValue())*Math.pow(10, -6));
				}
			}
		});
		spnMasseCocc.setBounds(53, 17, 52, 20);
		pnlCoccinelle.add(spnMasseCocc);
		spnMasseCocc.setModel(new SpinnerNumberModel(0.2, 0.1, 0.9, 0.05));

		JLabel lblChargeTxt = new JLabel("Charge:");
		lblChargeTxt.setBounds(150, 20, 57, 14);
		pnlCoccinelle.add(lblChargeTxt);

		spnChargeCocc = new JSpinner();
		spnChargeCocc.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.setCharge((double)spnChargeCocc.getValue());
				}
			}
		});
		spnChargeCocc.setBounds(205, 17, 46, 20);
		pnlCoccinelle.add(spnChargeCocc);
		spnChargeCocc.setModel(new SpinnerNumberModel(-3, -9, 9, 0.5));

		JLabel lblUg = new JLabel("\u00B5g");
		lblUg.setBounds(112, 20, 22, 14);
		pnlCoccinelle.add(lblUg);

		JLabel lblC = new JLabel("nC");
		lblC.setBounds(262, 20, 22, 14);
		pnlCoccinelle.add(lblC);

		JPanel pnlCyclotron = new JPanel();
		pnlCyclotron.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Cyclotron", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlCyclotron.setBounds(10, 30, 280, 71);
		panel.add(pnlCyclotron);
		pnlCyclotron.setLayout(null);

		JLabel lblPotentiellelectrique = new JLabel("Potentielle \u00E9lectrique:");
		lblPotentiellelectrique.setBounds(6, 19, 163, 14);
		pnlCyclotron.add(lblPotentiellelectrique);

		spnPotElec = new JSpinner();
		spnPotElec.setBackground(Color.WHITE);
		spnPotElec.setForeground(Color.WHITE);
		spnPotElec.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.setPotentielleElectrique((int)spnPotElec.getValue());

				}
			}
		});
		spnPotElec.setBounds(179, 17, 56, 20);
		pnlCyclotron.add(spnPotElec);
		spnPotElec.setModel(new SpinnerNumberModel(30, 10, 50, 1));

		JLabel lblV = new JLabel("\u0394V");
		lblV.setBounds(245, 20, 25, 14);
		pnlCyclotron.add(lblV);

		JLabel lblChampMagnetique = new JLabel("Champ Magnetique:");
		lblChampMagnetique.setBounds(6, 47, 163, 14);
		pnlCyclotron.add(lblChampMagnetique);

		spnChampMagneCyclo = new JSpinner();
		spnChampMagneCyclo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.setChampsMagnetique(3,(int) spnChampMagneCyclo.getValue());
				}
			}
		});
		spnChampMagneCyclo.setBounds(179, 45, 56, 20);
		pnlCyclotron.add(spnChampMagneCyclo);
		spnChampMagneCyclo.setModel(new SpinnerNumberModel(200, 100, 999, 1));

		JLabel lblT = new JLabel("T\r\n");
		lblT.setBounds(245, 47, 25, 14);
		pnlCyclotron.add(lblT);

		JPanel pnlSelecteurVitesse = new JPanel();
		pnlSelecteurVitesse.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "S\u00E9lecteur de vitesse", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlSelecteurVitesse.setBounds(10, 117, 280, 68);
		panel.add(pnlSelecteurVitesse);
		pnlSelecteurVitesse.setLayout(null);

		JLabel lblChampMagntique = new JLabel("Champ magn\u00E9tique:");
		lblChampMagntique.setBounds(6, 19, 160, 14);
		pnlSelecteurVitesse.add(lblChampMagntique);

		spnChampMagnetiqueSelec = new JSpinner();
		spnChampMagnetiqueSelec.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.setChampsMagnetique(0,(double) spnChampMagnetiqueSelec.getValue());
				}
			}
		});
		spnChampMagnetiqueSelec.setModel(new SpinnerNumberModel(25.0, 10.0, 500.0, 0.01));
		spnChampMagnetiqueSelec.setBounds(176, 13, 54, 20);
		pnlSelecteurVitesse.add(spnChampMagnetiqueSelec);

		JLabel lblT_1 = new JLabel("T");
		lblT_1.setBounds(240, 16, 15, 14);
		pnlSelecteurVitesse.add(lblT_1);

		JLabel lblChamplectrique = new JLabel("Champ \u00E9lectrique:");
		lblChamplectrique.setBounds(6, 44, 160, 14);
		pnlSelecteurVitesse.add(lblChamplectrique);

		spnChampElectriqueSelec = new JSpinner();
		spnChampElectriqueSelec.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.setChampsElectrique((double)spnChampElectriqueSelec.getValue());
				}
			}
		});
		spnChampElectriqueSelec.setModel(new SpinnerNumberModel(1000.0, -1000.0, 1000.0, 0.01));
		spnChampElectriqueSelec.setBounds(176, 38, 54, 20);
		pnlSelecteurVitesse.add(spnChampElectriqueSelec);

		JLabel lblNc = new JLabel("N/C");
		lblNc.setBounds(243, 41, 27, 14);
		pnlSelecteurVitesse.add(lblNc);

		JPanel pnlSpectrometreMasse = new JPanel();
		pnlSpectrometreMasse.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Spectrom\u00E8tre de masse", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlSpectrometreMasse.setBounds(10, 199, 280, 43);
		panel.add(pnlSpectrometreMasse);
		pnlSpectrometreMasse.setLayout(null);

		spnChampMagneSpectroM = new JSpinner();
		spnChampMagneSpectroM.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.setChampsMagnetique(1,(int) spnChampMagneSpectroM.getValue());
				}
			}
		});
		spnChampMagneSpectroM.setBounds(174, 16, 57, 20);
		pnlSpectrometreMasse.add(spnChampMagneSpectroM);
		spnChampMagneSpectroM.setModel(new SpinnerNumberModel(500, -500, 500, 1));

		JLabel lblChampTxt2 = new JLabel("Champ magn\u00E9tique:");
		lblChampTxt2.setBounds(6, 19, 167, 14);
		pnlSpectrometreMasse.add(lblChampTxt2);

		JLabel label = new JLabel("T");
		label.setBounds(241, 19, 15, 14);
		pnlSpectrometreMasse.add(label);

		JPanel pnlCoquerelle = new JPanel();
		pnlCoquerelle.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Coquerelles", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlCoquerelle.setBounds(13, 255, 277, 43);
		panel.add(pnlCoquerelle);
		pnlCoquerelle.setLayout(null);

		JLabel lblVitesseCo = new JLabel("Vitesse:");
		lblVitesseCo.setBounds(10, 19, 70, 14);
		pnlCoquerelle.add(lblVitesseCo);
		lblVitesseCo.setFont(new Font("Tahoma", Font.PLAIN, 11));

		JSpinner spnVitesse = new JSpinner();
		spnVitesse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.setVitesseCoquerelles((double)spnVitesse.getValue());
				}
			}
		});
		spnVitesse.setBounds(165, 16, 57, 20);
		spnVitesse.setModel(new SpinnerNumberModel(0.5, 0.25, 1, 0.05));
		pnlCoquerelle.add(spnVitesse);

		JLabel lblMs = new JLabel("m/s");
		lblMs.setBounds(225, 19, 46, 14);
		pnlCoquerelle.add(lblMs);

		tglbtnZoom = new JToggleButton("R\u00E9duire");
		tglbtnZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.zoom(false);;
				}
			}
		});
		tglbtnZoom.setSelected(true);
		Zoom.add(tglbtnZoom);
		tglbtnZoom.setBounds(10, 400, 121, 23);
		panel.add(tglbtnZoom);

		tglbtnReculer = new JToggleButton("Agrandir");
		tglbtnReculer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.zoom(true);
				}
			}
		});
		tglbtnReculer.setSelected(true);
		Zoom.add(tglbtnReculer);
		tglbtnReculer.setBounds(153, 400, 121, 23);
		panel.add(tglbtnReculer);

		btnAide = new JButton("Aide?");
		btnAide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean incremente = true;
				if(cptClicSolution >=0){
					for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
						ecout.setPotentielleElectrique(40);
						ecout.setChampsMagnetique(3,165);
						ecout.setCharge(-3);
					}
					spnPotElec.setValue((int)40);
					spnChampMagneCyclo.setValue((int)165);
					spnMasseCocc.setValue(0.2);
					spnPotElec.setEnabled(false);
					spnChampMagneCyclo.setEnabled(false);
					spnChargeCocc.setEnabled(false);
					spnMasseCocc.setEnabled(false);
					//System.out.println(cptClicSolution);

				}
				if(cptClicSolution >= 1){
					for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
						ecout.setChampsMagnetique(0,18);
						ecout.setChampsElectrique(1000);
					}
					spnChampElectriqueSelec.setValue((double)1000);
					spnChampMagnetiqueSelec.setValue((double)18);
					spnChampElectriqueSelec.setEnabled(false);
					spnChampMagnetiqueSelec.setEnabled(false);
					//System.out.println(cptClicSolution);
				}
				if(cptClicSolution >= 2){
					for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
						ecout.setChampsMagnetique(1,-356);
					}
					spnChampMagneSpectroM.setValue(-356);
					spnChampMagneSpectroM.setEnabled(false);
					//System.out.println(cptClicSolution);
				}
				if(cptClicSolution == 3){
					JOptionPane.showMessageDialog(null, "Vous avez déjà trouver la solution!");
					//System.out.println(cptClicSolution);
				}
				if(cptClicSolution >= 4){
					spnPotElec.setEnabled(true);
					spnChampMagneCyclo.setEnabled(true);
					spnChargeCocc.setEnabled(true);
					spnMasseCocc.setEnabled(true);
					spnChampElectriqueSelec.setEnabled(true);
					spnChampMagnetiqueSelec.setEnabled(true);
					spnChampMagneSpectroM.setEnabled(true);
					cptClicSolution = 0;
					incremente = false;
					//System.out.println(cptClicSolution);
				}
				if(incremente){
					cptClicSolution++;
				}
			}
		});
		btnAide.setBounds(100, 366, 89, 23);
		panel.add(btnAide);

		dtrpnDansCeNiveau = new JEditorPane();
		dtrpnDansCeNiveau.setFont(new Font("Tahoma", Font.BOLD, 13));
		dtrpnDansCeNiveau.setForeground(Color.WHITE);
		dtrpnDansCeNiveau.setBackground(new Color(47, 79, 79));
		dtrpnDansCeNiveau.setEditable(false);
		dtrpnDansCeNiveau.setBounds(10, 10, 270, 410);
		dtrpnDansCeNiveau.setText("  L'objetif du niveau quatre :\r\n\r\nDans ce niveau, vous devriez, tout\r\nd\u2019abord, dessiner la trajectoire de la\r\ncoccinelle jusqu\u2019au point vert (au centre\r\ndu cyclotron) en d\u00E9jouant les coquerelles.\r\nEnsuite la coccinelle sera d\u00E9sormais\r\ninfluenc\u00E9 par le cyclotron, le champ \r\nmagn\u00E9tique rotateur, le s\u00E9lecteur de\r\nvitesse ainsi que le spectrom\u00E8tre de \r\nmasse. Le but sera d\u2019avoir les bons\r\nparam\u00E8tres afin que la coccinelle \r\nr\u00E9ussisse \u00E0 arriver au point d\u2019arriver\r\nsans toucher aux murs ou aux \r\nobstacles. Si apr\u00E8s un certain\r\nmoment, vous trouvez cela trop\r\ndifficile, le bouton aide est l\u00E0 pour\r\nvous donner des indices.");
		JScrollPane scrollPane = new JScrollPane(dtrpnDansCeNiveau);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab(" ? Comment jouer ? ", null, scrollPane, null);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Paramètres scientifique", null, panel_1, null);
		panel_1.setLayout(null);

		JLabel lblVitesseTxt = new JLabel("Vitesse de la coccinelle:");
		lblVitesseTxt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVitesseTxt.setBounds(26, 45, 147, 14);
		panel_1.add(lblVitesseTxt);

		lblVitesseCocc = new JLabel("0 m/s");
		lblVitesseCocc.setForeground(Color.RED);
		lblVitesseCocc.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVitesseCocc.setBounds(172, 45, 83, 14);
		panel_1.add(lblVitesseCocc);

		JPanel pnlSelecteurVitesseScien = new JPanel();
		pnlSelecteurVitesseScien.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "S\u00E9lecteur de vitesse", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlSelecteurVitesseScien.setBounds(10, 170, 280, 87);
		panel_1.add(pnlSelecteurVitesseScien);
		pnlSelecteurVitesseScien.setLayout(null);

		JLabel lblForcelectriquetxt = new JLabel("Force \u00E9lectrique:");
		lblForcelectriquetxt.setBounds(6, 16, 109, 14);
		pnlSelecteurVitesseScien.add(lblForcelectriquetxt);
		lblForcelectriquetxt.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblForceElectrique = new JLabel("     0 N");
		lblForceElectrique.setBounds(125, 16, 140, 14);
		pnlSelecteurVitesseScien.add(lblForceElectrique);
		lblForceElectrique.setFont(new Font("Cambria Math", Font.BOLD, 13));
		lblForceElectrique.setForeground(Color.RED);

		JLabel lblForceMagntiquetxt = new JLabel("Force magn\u00E9tique:");
		lblForceMagntiquetxt.setBounds(6, 40, 121, 14);
		pnlSelecteurVitesseScien.add(lblForceMagntiquetxt);
		lblForceMagntiquetxt.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblForceMagnetiqueSelec = new JLabel("     0 N");
		lblForceMagnetiqueSelec.setBounds(125, 40, 140, 14);
		pnlSelecteurVitesseScien.add(lblForceMagnetiqueSelec);
		lblForceMagnetiqueSelec.setForeground(Color.RED);
		lblForceMagnetiqueSelec.setFont(new Font("Cambria Math", Font.BOLD, 13));

		JLabel lblVitesseIdale = new JLabel("Vitesse id\u00E9ale:");
		lblVitesseIdale.setBounds(6, 66, 109, 14);
		pnlSelecteurVitesseScien.add(lblVitesseIdale);
		lblVitesseIdale.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblVitesseSelecteur = new JLabel("0 m/s");
		lblVitesseSelecteur.setBounds(145, 66, 84, 14);
		pnlSelecteurVitesseScien.add(lblVitesseSelecteur);
		lblVitesseSelecteur.setForeground(Color.RED);
		lblVitesseSelecteur.setFont(new Font("Tahoma", Font.BOLD, 13));

		pnlSpectrometreScience = new JPanel();
		pnlSpectrometreScience.setLayout(null);
		pnlSpectrometreScience.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Spectrom\u00E8tre de masse", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlSpectrometreScience.setBounds(10, 268, 280, 70);
		panel_1.add(pnlSpectrometreScience);

		lblTxt1 = new JLabel("Rayon pr\u00E9vu:");
		lblTxt1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTxt1.setBounds(6, 41, 146, 14);
		pnlSpectrometreScience.add(lblTxt1);

		lblForceMagnetiqueSpectro = new JLabel("     0 N");
		lblForceMagnetiqueSpectro.setForeground(Color.RED);
		lblForceMagnetiqueSpectro.setFont(new Font("Cambria Math", Font.BOLD, 13));
		lblForceMagnetiqueSpectro.setBounds(124, 16, 146, 14);
		pnlSpectrometreScience.add(lblForceMagnetiqueSpectro);

		label_3 = new JLabel("Force magn\u00E9tique:");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_3.setBounds(6, 16, 121, 14);
		pnlSpectrometreScience.add(label_3);

		lblRayon = new JLabel("0 m");
		lblRayon.setForeground(Color.RED);
		lblRayon.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRayon.setBounds(145, 41, 63, 14);
		pnlSpectrometreScience.add(lblRayon);

		lblAcclerationCentripte = new JLabel("Champs magn\u00E9tique du d\u00E9viateur de particule: ");
		lblAcclerationCentripte.setBounds(6, 348, 284, 14);
		panel_1.add(lblAcclerationCentripte);
		lblAcclerationCentripte.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblChamp = new JLabel("0 T");
		lblChamp.setBounds(118, 362, 63, 14);
		panel_1.add(lblChamp);
		lblChamp.setForeground(Color.RED);
		lblChamp.setFont(new Font("Tahoma", Font.BOLD, 13));

		JLabel lblObjetSlectionn = new JLabel("Objet s\u00E9lectionn\u00E9:");
		lblObjetSlectionn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblObjetSlectionn.setBounds(6, 400, 130, 14);
		panel_1.add(lblObjetSlectionn);

		lblNomObjet = new JLabel("");
		lblNomObjet.setBackground(new Color(192, 192, 192));
		lblNomObjet.setForeground(new Color(51, 51, 153));
		lblNomObjet.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNomObjet.setBounds(118, 400, 172, 14);
		panel_1.add(lblNomObjet);

		JLabel lblPositionDeLa = new JLabel("Position de la coccinelle:");
		lblPositionDeLa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPositionDeLa.setBounds(26, 20, 147, 14);
		panel_1.add(lblPositionDeLa);

		lblPos = new JLabel("");
		lblPos.setForeground(Color.RED);
		lblPos.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPos.setBounds(172, 20, 118, 14);
		panel_1.add(lblPos);

		JPanel pnlCyclotronScien = new JPanel();
		pnlCyclotronScien.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Cyclotron", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlCyclotronScien.setBounds(10, 92, 280, 67);
		panel_1.add(pnlCyclotronScien);
		pnlCyclotronScien.setLayout(null);

		lblFreq = new JLabel("");
		lblFreq.setBackground(Color.LIGHT_GRAY);
		lblFreq.setBounds(120, 24, 147, 21);
		pnlCyclotronScien.add(lblFreq);
		lblFreq.setForeground(Color.RED);
		lblFreq.setFont(new Font("Tahoma", Font.BOLD, 13));

		rdbtnFrequence = new JRadioButton("Fr\u00E9quence");
		rdbtnFrequence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.appelCalculPeriode();
				}
			}
		});
		rdbtnFrequence.setBounds(6, 37, 87, 23);
		pnlCyclotronScien.add(rdbtnFrequence);
		btnGrpPerFre.add(rdbtnFrequence);

		rdbtnPeriode = new JRadioButton("P\u00E9riode");
		rdbtnPeriode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Niveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(Niveau4Listener.class) ) {
					ecout.appelCalculPeriode();
				}
			}
		});
		rdbtnPeriode.setSelected(true);
		rdbtnPeriode.setBounds(6, 16, 87, 23);
		pnlCyclotronScien.add(rdbtnPeriode);
		btnGrpPerFre.add(rdbtnPeriode);

		JLabel lblAcclrationDeLa = new JLabel("Acc\u00E9l\u00E9ration de la coccinelle:");
		lblAcclrationDeLa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAcclrationDeLa.setBounds(26, 67, 172, 14);
		panel_1.add(lblAcclrationDeLa);

		lblAcceleration = new JLabel("0 m/s\u00B2");
		lblAcceleration.setForeground(Color.GREEN);
		lblAcceleration.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAcceleration.setBounds(198, 67, 92, 14);
		panel_1.add(lblAcceleration);
	}
	/**
	 * Cette méthode permet d'ajouter l'écouteur afin de transmettre les modification du niveau fait par les utilisateurs.
	 * @param objEcouteur l'écouteur.
	 */
	public void addNiveau4Listener(Niveau4Listener objEcouteur) {
		OBJETS_ENREGISTRES.add(Niveau4Listener.class, objEcouteur);
	}
	/**
	 * Cette méthode permet de connaitre la vitesse, en tout temps, de la coccinelle durant son périple.
	 * @param vitesse la vitesse actuelle de la coccinelle (modulée).
	 */
	public void afficherVitesse(double vitesse) {
		double vitesseLu = round(vitesse,2);
		lblVitesseCocc.setText(vitesseLu + " m/s");

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
	 * Cette méthode permet d'afficher, en tout temps, la force électrique appliquée sur la coccinelle.
	 * @param forceElectrique la force électrique
	 */
	public void afficherForceElectrique(Vector3d forceElectrique) {
		double forceLuX = round(forceElectrique.getX()*Math.pow(10, 6),2);
		double forceLuY = round(forceElectrique.getY()*Math.pow(10, 6),2);
		lblForceElectrique.setText("( "+forceLuX+"i\u20D7 "+ forceLuY +" j\u20D7) \u00B5N");
	}
	/**
	 * Cette méthode permet d'afficher, en tout temps, la force magnétique appliquée sur la coccinelle.
	 * @param nbChamps le champs en cours
	 * @param forceMagnetique la force magnétique.
	 */
	public void afficherForceMagnetique(int nbChamps, Vector3d forceMagnetique) {
		double forceLuX = round(forceMagnetique.getX()*Math.pow(10, 6),2);
		double forceLuY = round(forceMagnetique.getY()*Math.pow(10, 6),2);
		if(nbChamps == 0){
			lblForceMagnetiqueSelec.setText("( "+forceLuX+"i\u20D7 "+ forceLuY +" j\u20D7) \u00B5N");
			//"( "+posXAffichage+"i\u20D7 "+posYAffichage+"j\u20D7 )E-31 N"
		}else{
			lblForceMagnetiqueSpectro.setText("( "+forceLuX+"i\u20D7 "+ forceLuY +" j\u20D7) \u00B5N");
		}

	}
	/**
	 * Cette méthode permet d'afficher la vitesse idéale pour traverser le sélecteur de vitesse.
	 * @param vitesse la vitesse idéale du sélecteur de vitesse.
	 */
	public void afficherSelecteurVitesse(double vitesse) {
		double vitesseLu = round(vitesse,2);
		lblVitesseSelecteur.setText(vitesseLu + " m/s");
	}
	/**
	 * Cette méthode permet d'afficher le rayon de rotation de la coccinelle lors de son passage dans le spectromètre de masse.
	 * @param rayon le rayon de rotation
	 */
	public void afficherRayon(double rayon) {
		double rayonLu= round(rayon,2);
		//System.err.println(rayon);
		lblRayon.setText(rayonLu + " m");
	}
	/**
	 * Cette méthode permet de connaitre le champs magnétique du champs rotateur.
	 * @param champsMagnetique le champs magnétique du rotateur.
	 */
	public void afficherChampsMagnetique(double champsMagnetique) {
		double champsLu= round(champsMagnetique,2);
		lblChamp.setText(champsLu + " T");
	}
	/**
	 * Cette méthode permet de changer le panneau à afficher.
	 * @param a le panneau à afficher
	 */
	public void setTabPaneSelectedIndex(int a){
		tabbedPane.setSelectedIndex(a);
	}
	/**
	 * Cette méthode permet d'afficher l'objet selectionné
	 * @param nom le nom de l'objet sélectionner
	 */
	public void afficherObjetSelectionne(String nom) {
		lblNomObjet.setText(nom);
	}
	/**
	 * Cette méthode permet de débloquer ou de bloquer certains paramètres.
	 * @param b <p><b>true</b> les paramètres sont débloquer
	 * <p><b>false</b> les paramètres sont bloquer
	 */
	public void debloquerParametres(boolean b) {
		spnPotElec.setEnabled(b);
		spnChampMagneCyclo.setEnabled(b);
		spnChargeCocc.setEnabled(b);
		spnMasseCocc.setEnabled(b);
		spnChampElectriqueSelec.setEnabled(b);
		spnChampMagnetiqueSelec.setEnabled(b);
		spnChampMagneSpectroM.setEnabled(b);
		tglbtnReculer.setEnabled(b);
		tglbtnZoom.setEnabled(b);
		btnAide.setEnabled(b);
		btnArreter.setEnabled(!b);
		btnInitialiser.setEnabled(!b);
	}
	/**
	 * Cette méthode permet d'afficher la position actuelle de la coccinelle dans le niveau.
	 * @param position la position actuelle de la coccinelle.
	 */
	public void afficherPosition(Vector2d position) {
		double xLu = round(position.getX(),1); 
		double yLu = round(position.getY(),1); 
		lblPos.setText("("+xLu+ " - " + yLu +") m");
	}
	/**
	 * Cette méthode permet d'afficher la période du cyclotron.
	 * @param periode la période du cyclotron.
	 */
	public void afficherPeriode(double periode) {
		double periodeLu;
		if(rdbtnPeriode.isSelected()){
			periodeLu = round(periode,2);
			lblFreq.setText(periodeLu + " s");
		}else{
			periodeLu = round(1/periode,2);
			lblFreq.setText(periodeLu + " Hz");
		}

	}
	/**
	 * Cette méthode permet d'afficher l'accélération de la coccinelle
	 * @param acceleration l'accélération de la coccinelle
	 */
	public void afficherAcceleration(double acceleration) {
		double accelerationLu = round(acceleration,2);
		lblAcceleration.setText(accelerationLu + " m/s\u00B2");

	}
}
