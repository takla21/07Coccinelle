
package aaplication;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JSlider;

import ecouteur.MainPageListener;
import ecouteur.SelecNiveauListener;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.List;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.UIManager;

import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.JEditorPane;
/**
 * 
 * Classe de panneau de paramètre pour modifier les paramètres variés
 * 
 * @author Zi Zheng Wang
 * @version 3.0
 */
public class DPanelLoading extends JFrame {

	private JPanel contentPane;
	private JButton buttonBack;
	private JLabel lblPanelDeCharger;
	private JList list;
	private JButton btnCheminLibre;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList();
	private JButton btnLoad;
	private JLabel lblNewLabel;
	private String lecture = "demoBallon";
	private JEditorPane editorPane;
	private String nomTab[] = {"  ", "  ", "  ", "  ", "  "};
	private JScrollPane scrollPane;
	/**
	 * un frame pour les paramètres modifiables du jeu
	 */
	public DPanelLoading() {
		setTitle("Charger votre niveau");
		setBounds(100, 100, 455, 550);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		buttonBack = new JButton("Fermer");
		buttonBack.setBackground(Color.LIGHT_GRAY);
		buttonBack.setForeground(Color.DARK_GRAY);
		buttonBack.setFocusable(false);
		buttonBack.setFont(new Font("Tahoma", Font.BOLD, 15));
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		buttonBack.setBounds(268, 478, 155, 33);
		contentPane.add(buttonBack);
		
		lblPanelDeCharger = new JLabel("Niveau \u00E0 charger :");
		lblPanelDeCharger.setForeground(Color.DARK_GRAY);
		lblPanelDeCharger.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblPanelDeCharger.setBounds(25, 11, 264, 50);
		contentPane.add(lblPanelDeCharger);
		
		list = new JList();
		list.setForeground(Color.WHITE);
		list.setBackground(Color.GRAY);
		list.setFont(new Font("Tahoma", Font.BOLD, 15));
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//début
				btnLoad.setEnabled(true);
				//System.out.println("index selection"+list.getSelectedIndex());
				//System.out.println("index selection"+(String) list.getSelectedValue());
				if(list.getSelectedIndex() != 11){
					lecture = (String) list.getSelectedValue();
					btnLoad.setText("Charger niveau " + lecture);	
				}else{
					lecture = "creature";
					btnLoad.setText("Charger votre niveau ");	
				}
				//fin
				switch (list.getSelectedIndex()) {
				case 4:
					editorPane.setText("Un niveau créé par défaut\npour l'animation du ballon\nUn démo de collision.");
					break;
				case 5:
					editorPane.setText("Un niveau créé par défaut\npour l'animation du cercle\nUn démo de rotation.");
					break;
				case 6:
					editorPane.setText("Un niveau céé par défaut\npour des particules\nchargées et le champ\nélectrique\n* Vous pouvez déplacer\nles charges dans ce\nniveau.");
					break;
				case 7:
					editorPane.setText("Un niveau créé par défaut\npour la superposition des\ndifférentes objets.");
					break;
				case 8:
					editorPane.setText("Un niveau céé par défaut\npour des particules\nchargées et le champ\nmagnétique.\n* Vous pouvez déplacer\nles charges dans ce\nniveau.");
					break;
				case 9:
					editorPane.setText("Un niveau créé par défaut.");
					break;
				case 10:
					editorPane.setText(" ");
					break;
				case 11:
					editorPane.setText("Votre niveau.");
					break;
				case 12:
					editorPane.setText("Votre niveau.");
					break;
				case 13:
					editorPane.setText("Votre niveau.");
					break;
				case 14:
					editorPane.setText("Votre niveau.");
					break;
				case 15:
					editorPane.setText("Votre niveau.");
					break;
				case 16:
					editorPane.setText("Votre niveau.");
					break;
				}
			}
		});
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"","","","", "demoBallon", "demoCercle", "demoCharge", "demoSuperposition", "champMag", "niveauPop.txt", " ", "Le nom de votre niveau  ",nomTab[0],nomTab[1],nomTab[2],nomTab[3],nomTab[4]};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(30, 116, 232, 269);
		scrollPane = new JScrollPane(list);
		scrollPane.setBounds(30, 116, 232, 269);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);
		

		btnCheminLibre = new JButton("Ouvrir sous...");
		btnCheminLibre.setBackground(Color.LIGHT_GRAY);
		btnCheminLibre.setForeground(Color.DARK_GRAY);
		btnCheminLibre.setFocusable(false);
		btnCheminLibre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(SelecNiveauListener ecout : OBJETS_ENREGISTRES.getListeners(SelecNiveauListener.class) ) {
					ecout.load();
				}
				setVisible(false);
			}
		});
		btnCheminLibre.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCheminLibre.setBounds(30, 442, 232, 33);
		contentPane.add(btnCheminLibre);
		
		btnLoad = new JButton("Ouvrir");
		btnLoad.setBackground(Color.LIGHT_GRAY);
		btnLoad.setForeground(Color.DARK_GRAY);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lecture.equals("creature")){
					lecture  = JOptionPane.showInputDialog(null,"Entrez le nom de fichier", "niveauPop.txt");
				}
				if(lecture != null){
					if(lecture == ""){
						JOptionPane.showMessageDialog(null, "nom de fichier invalide");
					}else{
						for(SelecNiveauListener ecout : OBJETS_ENREGISTRES.getListeners(SelecNiveauListener.class) ) {
							ecout.loadParDefaut(lecture);
						}
						lecture = "creature";
					}
				}else{
					JOptionPane.showMessageDialog(null, "Source inconnue");
				}
				setVisible(false);
			}
		});
		btnLoad.setFocusable(false);
		btnLoad.setEnabled(false);
		btnLoad.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLoad.setBounds(30, 396, 393, 33);
		contentPane.add(btnLoad);
		
		lblNewLabel = new JLabel("Demo tutoriel par d\u00E9faut :                 Description :");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(30, 90, 393, 33);
		contentPane.add(lblNewLabel);
		
		editorPane = new JEditorPane();
		editorPane.setFont(new Font("Tahoma", Font.BOLD, 11));
		editorPane.setBackground(SystemColor.menu);
		editorPane.setEditable(false);
		editorPane.setBounds(268, 116, 155, 269);
		contentPane.add(editorPane);
	}
	/**
	 * Méthode sert à ajouter slecNiveauListener dans DPanelLoading
	 * @param objEcouteur - par défaut
	 */
	public void addSelecNiveauListener(SelecNiveauListener objEcouteur) {
		OBJETS_ENREGISTRES.add(SelecNiveauListener.class,objEcouteur);
	}
	/**
	 * Méthode sert à ajouter le nouveau nom du niveau dans la tableau nomTab[x]
	 * @param c le nom du niveau
	 */
	public void addNouveauNiveau(String c){
		if(nomTab[0] == "  "){
			nomTab[0] = c;
		}else{
			if(nomTab[1] == "  "){
				nomTab[1] = c;
			}else{
				if(nomTab[2] == "  "){
					nomTab[2] = c;
				}else{
					if(nomTab[3] == "  "){
						nomTab[3] = c;
					}else{
						nomTab[4] = c;
					}
				}
			}
		}
	}
	/**
	 * Méthode sert à recevoir la liste de nom de niveau
	 * @param collectionNom2 la liste de chaines
	 */
	public void getCollectioNom(ArrayList<String> collectionNom2) {
		if(!collectionNom2.isEmpty()){
			for(int i = 0 ; i< collectionNom2.size(); i++ ){
				nomTab[i] = collectionNom2.get(i);
			}
		}
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"","","","", "demoBallon", "demoCercle", "demoCharge", "demoSuperposition", "champMag", "niveauPop.txt", " ", "Le nom de votre niveau  ",nomTab[0],nomTab[1],nomTab[2],nomTab[3],nomTab[4]};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
	}
}
