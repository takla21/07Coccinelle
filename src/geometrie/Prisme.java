
package geometrie;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import physique.ClassePhysique;
import physique.Vector2d;
import physique.Vector3d;
import ecouteur.TransmissionInfoNiveau1Listener;

/**
 * 
 * Classe de géometrie qui permet de créer l'objet prisme
 * 
 * @author Kevin Takla; collaborateur Zi Zheng Wang
 * @version 1.0
 */
public class Prisme {
	private double posXA;
	private double posYA;
	private double posXB;
	private double posYB;
	private double posXC;
	private double posYC;
	private double angle;
	private Path2D.Double prisme;
	private Shape triangle;
	private double indiceDeRefraction = 2; 
	private ArrayList<Vector2d> listesNormales;
	private ArrayList<Ellipse2D.Double> posNormale;
	private Ellipse2D.Double pointCentre;
	private AffineTransform transforPoint;
	private AffineTransform transfoZone, rotaZone;
	private final double DIAMETRE_POINT =1;
	private ArrayList <Path2D.Double> normaleDessin;
	private boolean normaleSelect[] = {false,true,false};
	private final double ORIENTATION_DEPART = -2*Math.PI*28/360;
	private ArrayList<Line2D.Double> lignesNormale, listeLigneTransfo;
	private boolean premiereFois[] = {true,true,true};

	AffineTransform transfoZones, rotaZones;
	/**
	 *C'est le constructeur de la classe Prisme
	 * @param posXA coordonnée en x du premier point
	 * @param posYA coordonnée en y du premier point
	 * @param posXB coordonnée en x du deuxième point
	 * @param posYB coordonnée en y du deuxième point
	 * @param posXC coordonnée en x du troisième point
	 * @param posYC coordonnée en y du troisième point
	 * @param angle l'angle du prisme
	 */
	public Prisme(double posXA, double posYA, double posXB, double posYB, double posXC, double posYC, double angle){
		this.posXA = posXA;
		this.posYA = posYA;
		this.posXB = posXB;
		this.posYB = posYB;
		this.posXC = posXC;
		this.posYC = posYC;
		this.angle = angle;
		creerLigne();
		transforPoint = new AffineTransform();
		transfoZone = new AffineTransform();
		rotaZone = new AffineTransform();
		creerPrisme();
		creerPointCentre();
		creerPointNormale();
		creerVecNormale();
		creerNormaleDessin();
	}


	/**
	 * C'est le deuxième constructeur de la classe prisme qui permet de copier les valeurs d'un objet du type Prisme vers un nouveau prisme.
	 * @param autrePrisme prisme qu'on souhaite modifier.
	 */
	public Prisme(Prisme autrePrisme){
		this.posXA = autrePrisme.getPosXA();
		this.posXB = autrePrisme.getPosXB();
		this.posXC = autrePrisme.getPosXC();
		this.posYA = autrePrisme.getPosYA();
		this.posYB = autrePrisme.getPosYB();
		this.posYC = autrePrisme.getPosYC();
		this.angle = autrePrisme.getAngle();
		creerLigne();
		transforPoint = new AffineTransform();
		transfoZone = new AffineTransform();
		rotaZone = new AffineTransform();
		creerPrisme();
		creerPointCentre();
		creerPointNormale();
		creerVecNormale();
		creerNormaleDessin();
		normaleSelect[0] = false;
		normaleSelect[1] = false;
		normaleSelect[2] = false;
	}
	/**
	 * Cette méthode permet de créer les zones de contact du prisme (lignes).
	 */
	private void creerLigne(){
		lignesNormale = new ArrayList<Line2D.Double>();
		lignesNormale.add(new Line2D.Double(posXA,posYA,posXB,posYB));
		lignesNormale.add(new Line2D.Double(posXB,posYB,posXC,posYC));
		lignesNormale.add(new Line2D.Double(posXC,posYC,posXA,posYA));
	}

	/**
	 * Cette méthode permet de positioner les points des normales.
	 */
	private void creerPointNormale(){
		posNormale = new ArrayList<Ellipse2D.Double>();
		posNormale.add(new Ellipse2D.Double(posXA - DIAMETRE_POINT/2,posYA + (posYB-posYA)/2 - DIAMETRE_POINT/2,DIAMETRE_POINT,DIAMETRE_POINT));
		posNormale.add(new Ellipse2D.Double(posXC - (posXC - posXB)/2 - DIAMETRE_POINT/2,posYC - (posYC-posYB)/2 - DIAMETRE_POINT/2,DIAMETRE_POINT,DIAMETRE_POINT));
		posNormale.add(new Ellipse2D.Double(posXC - (posXC - posXB)/2 - DIAMETRE_POINT/2,posYC - (posYC-posYA)/2 - DIAMETRE_POINT/2,DIAMETRE_POINT,DIAMETRE_POINT));
	}

	/**
	 * Cette mméthode permet de créer un point au centre du prisme.
	 */
	private void creerPointCentre(){
		Rectangle rectTemp= prisme.getBounds();
		pointCentre = new Ellipse2D.Double(posXA +rectTemp.getWidth()/4 + DIAMETRE_POINT/2,posYA + rectTemp.getHeight()/2 - DIAMETRE_POINT/2,DIAMETRE_POINT,DIAMETRE_POINT);
	}

	/**
	 * Cette méthode permet d'initialiser les différents normales du prisme.
	 */
	private void creerVecNormale(){
		listesNormales = new ArrayList<Vector2d>();

		Shape pointCentreTransfo = transfoZone.createTransformedShape(rotaZone.createTransformedShape(pointCentre));
		Rectangle pointC = pointCentreTransfo.getBounds();
		double posX = pointC.getX();
		double posY = pointC.getY();

		for(int nbNormale = 0; nbNormale < posNormale.size(); nbNormale++){
			Ellipse2D.Double point = posNormale.get(nbNormale);
			Shape pointRotationner = transfoZone.createTransformedShape(rotaZone.createTransformedShape(point));
			Rectangle posNormale= pointRotationner.getBounds();
			listesNormales.add(nbNormale,new Vector2d(posNormale.getX() - posX,posNormale.getY() - posY));
		}
	}
	/**
	 * Cette méthode permet de creer les formes géométrique des normales
	 */
	private void creerNormaleDessin(){
		final double largeurTrait = 6;
		normaleDessin = new ArrayList<Path2D.Double>();
		Path2D.Double ligne = new Path2D.Double();
		ligne.moveTo(posNormale.get(0).getX(),posNormale.get(0).getY());
		ligne.lineTo(posNormale.get(0).getX() -  (largeurTrait+1)*Math.cos( ORIENTATION_DEPART),posNormale.get(0).getY() );
		normaleDessin.add(ligne);
		ligne = new Path2D.Double();
		ligne.moveTo(posNormale.get(2).getX(),posNormale.get(2).getY());
		ligne.lineTo(posNormale.get(2).getX() +  largeurTrait*Math.cos( ORIENTATION_DEPART),posNormale.get(2).getY() - largeurTrait*Math.sin((2*Math.PI*90/360) - ORIENTATION_DEPART));
		normaleDessin.add(ligne);
		ligne = new Path2D.Double();
		ligne.moveTo(posNormale.get(1).getX(),posNormale.get(1).getY());
		ligne.lineTo(posNormale.get(1).getX() -  (largeurTrait+1)*Math.cos((2*Math.PI*90/360) - ORIENTATION_DEPART),posNormale.get(1).getY() + (largeurTrait+1)*Math.sin((2*Math.PI*90/360) - ORIENTATION_DEPART));
		normaleDessin.add(ligne);
	}

	/**
	 * Cette méthode permet de créer le prisme.
	 */
	private void creerPrisme() {
		prisme = new Path2D.Double();
		prisme.moveTo(posXA, posYA);
		prisme.lineTo(posXB, posYB);
		prisme.lineTo(posXC,posYC);
		prisme.closePath();
	}
	/**
	 * 
	 *Cette méthode permet de dessiner le prisme .
	 * @param g2d contexte graphique.
	 * @param aff la matrice de transformation qui a été appliquer sur cette géomértie.
	 * @param c la couleur du prisme.
	 * @param matMC la matrice monde réelle
	 * @param scientifique <p><b>true</b> le niveau est en mode scientifique
	 * <p><b>false</b> le niveau n'est pas en mode scientifique
	 * @param translatX la translation en x du prisme
	 * @param translatY la translation en y du prisme
	 */
	public void dessiner(Graphics2D g2d, AffineTransform aff, Color c, AffineTransform matMC,boolean scientifique,double translatX, double translatY){
		AffineTransform matt = new AffineTransform(aff);

		AffineTransform rota = new AffineTransform();
		rota.rotate(angle,posXB,posYB);
		Shape tri = rota.createTransformedShape(prisme);
		triangle = matt.createTransformedShape(tri);
		if(!scientifique){
			g2d.setColor(c);
		}else{
			g2d.setColor(Color.gray);
			g2d.setStroke(new BasicStroke(4));
		}
		g2d.fill(triangle);
		g2d.setColor(c);
		g2d.draw(triangle);

		if(scientifique){
			g2d.setColor(Color.white);
			float brise[] = {10};
			BasicStroke ligneBrise = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,  BasicStroke.JOIN_MITER,10.0f,brise, 0.0f);
			g2d.setStroke(ligneBrise);
			for(int nbNormale =0; nbNormale < normaleSelect.length ; nbNormale++){
				if(normaleSelect[nbNormale]){
					g2d.draw(matt.createTransformedShape(rota.createTransformedShape(normaleDessin.get(nbNormale))));
				}
			}

			g2d.setStroke(new BasicStroke(1));
		}
		listeLigneTransfo = new ArrayList<Line2D.Double>(lignesNormale);
		for(int l =0; l<listeLigneTransfo.size();l++){
			Line2D.Double ligne = listeLigneTransfo.get(l);{
				ligne = scaleLigne(ligne,aff.getScaleX(),aff.getScaleY());
				ligne = rotateLigne(ligne, posXB * aff.getScaleX(),posYB * aff.getScaleY());
				ligne = translateLigne(ligne,aff.getTranslateX(), aff.getTranslateY());
				//creerNormales();
				listeLigneTransfo.set(l,ligne);
			}

		}
		
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.CYAN);
		g2d.draw(listeLigneTransfo.get(0));
		g2d.setColor(Color.green);
		g2d.draw(listeLigneTransfo.get(1));
		g2d.setColor(Color.magenta);
		g2d.draw(listeLigneTransfo.get(2));
		g2d.setStroke(new BasicStroke(1));
		
		
		transforPoint = new AffineTransform(matMC);
		transfoZone = new AffineTransform(aff);
		rotaZone = new AffineTransform(rota);
		creerVecNormale();
	}


	/**
	 * Cette méthode permet de modifier l'orientation du prisme.
	 * @param angle la nouvelle angle du prisme
	 */
	public void setOrientation(double angle){
		this.angle = angle;
	}
	/**
	 * Cette méthode permet de calculer l'angle Critique 
	 * @param n1 le milieu 1
	 * @param n2 le milieu 2
	 * @return l'angle maximale qu'un rayon peut entrer.
	 */
	private void calculerAngleCritique(double n1, double n2, EventListenerList OBJETS_ENREGISTRES){
		double angle = Math.atan(n2/n1);
		for(TransmissionInfoNiveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Listener.class) ) {
			ecout.getAngleCritiqueMilieu(1, angle);
		}
	}
	/**
	 * Cette méthose permet de modifier la position du prisme
	 * @param posX la nouvelle position en x du prisme.
	 * @param posY la nouvelle position en y du prisme.
	 */
	public void setPosition(double posX,double posY){
		this.posXA = posX;
		this.posYA = posY;
		creerPrisme();
	}
	/**
	 * Cette méthode permet de connaitre la positon hoizontale du prisme.
	 * @return la position en X du prisme.
	 */
	public double getX(){
		return posXA;
	}
	/**
	 * Cette méthode permet de connaitre la positon verticale du prisme.
	 * @return la position en Y du prisme.
	 */
	public double getY(){
		return posYA;
	}
	/**
	 * La méthode suivante permet de connaitre l'orientation du prisme.
	 * @return l'angle de rotation du prisme.
	 */
	public double getAngle(){
		return angle;
	}
	/**
	 * Cette méthode vérifie si un point donnée est dans le prisme.
	 * @param x la position horizontale du point en question.
	 * @param y la position vertdu point en question.
	 * @return la valeur de vérité de la méthode <p><b>true</b> le point est dans le prisme.
	 * <p><b>false</b> le point n'est pas dans le prisme.
	 */
	public boolean contains(double x, double y) {
		if(triangle.contains(x, y)){
			return true;
		}else{
			return false;
		}

	}
	/**
	 * Cette méthode permet d'obtenir la forme géométrique du prisme.
	 * @return la forme géométrique du prisme.
	 */
	public Shape getShape(){
		return triangle;
	}
	/**
	 * La méthode suivante permet de connaitre son indice de réfraction.
	 * @return l'indice de réfraction.
	 */
	public double getIndiceRefraction(){
		return indiceDeRefraction;
	}
	/**
	 * Cette méthode permet de modifier le milieu de réfraction du prisme
	 * @param n le nouveau milieu de réfraction du prisme.
	 * @param OBJETS_ENREGISTRES écouteur
	 */
	public void setIndiceRefraction(double n, EventListenerList OBJETS_ENREGISTRES){
		indiceDeRefraction = n;
		calculerAngleCritique(1, n,OBJETS_ENREGISTRES);

	}
	/**
	 * Cette méthode permet de créer un aire à partir du prisme.
	 * @return l'aire formée par le prisme.
	 */
	public Area aire(){
		Area aire = new Area(prisme);
		return aire;
	}
	/**
	 * Cette méthode permet de retourner la position horizontale du premier point du prisme.
	 * @return la position en X du premier point
	 */
	public double getPosXA() {
		return posXA;
	}
	/**
	 * Cette méthode permet de retourner la position verticale du premier point du prisme.
	 * @return la position en Y du premier point
	 */
	public double getPosYA() {
		return posYA;
	}
	/**
	 * Cette méthode permet de retourner la position horizontale du deuxième point du prisme.
	 * @return la position en X du deuxième point
	 */
	public double getPosXB() {
		return posXB;
	}
	/**
	 * Cette méthode permet de retourner la position verticale du deuxième point du prisme.
	 * @return la position en Y du deuxième point
	 */
	public double getPosYB() {
		return posYB;
	}
	/**
	 * Cette méthode permet de retourner la position horizontale du troisième point du prisme.
	 * @return la position en X du troisième  point
	 */
	public double getPosXC() {
		return posXC;
	}
	/**
	 * Cette méthode permet de retourner la position verticale du troisième point du prisme.
	 * @return la position en Y du troisième point.
	 */
	public double getPosYC() {
		return posYC;
	}
	/**
	 * Cette méthode permet de calculer la réfraction de la lumière dans le bloc vitre.
	 * @param vecteurIncident le rayon incident
	 * @param cptDeviation le nombre de déviation 
	 * @param posXLazer la position du laser lors de la collision en x
	 * @param posYLazer la position du laser lors de la collision en y
	 * @param normaleUtilise la normale utilisé.
	 * @param OBJETS_ENREGISTRES l'écouteur qui transmettra les informations.
	 * @param extra extra l'incrementation actuelle du laser.
	 * @return le rayon réfracté
	 */
	  
	 
	public Vector2d calculerAngleDeviation(Vector2d vecteurIncident,int cptDeviation,double posXLazer, double posYLazer ,int normaleUtilise, EventListenerList OBJETS_ENREGISTRES,Vector2d extra){
		vecteurIncident = vecteurIncident.vecteurUnitaire();
		extra = extra.vecteurUnitaire();
		Vector2d vecteurNormalUnitaire =listesNormales.get(normaleUtilise).vecteurUnitaire();
		Vector2d vecteurRefelchi;
		if(cptDeviation <= 0){
			if(!ClassePhysique.isTotalInternalReflection(vecteurIncident, vecteurNormalUnitaire,1,indiceDeRefraction )){
				vecteurRefelchi= extra.add(ClassePhysique.refraction(1, indiceDeRefraction, vecteurIncident, vecteurNormalUnitaire));
			}else{
				vecteurRefelchi = ClassePhysique.reflexion(vecteurIncident, vecteurNormalUnitaire.multiply(-1));
			}
			for(TransmissionInfoNiveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Listener.class) ) {
				ecout.getAngleIncidentMilieu1(1, ClassePhysique.calculAngle(vecteurIncident, vecteurNormalUnitaire));
				ecout.getAngleReflechiMilieu1(1, ClassePhysique.calculAngle(vecteurRefelchi, vecteurNormalUnitaire.multiply(-1)));
			}
		}else{
			if(!ClassePhysique.isTotalInternalReflection(vecteurIncident, vecteurNormalUnitaire,indiceDeRefraction, 1 )){
				if(cptDeviation > 1){
					//System.out.println("collision "+cptDeviation);
					vecteurRefelchi=  extra.add(ClassePhysique.refraction(indiceDeRefraction, 1, vecteurIncident,vecteurNormalUnitaire.multiply(-1)));
				}else{
					//System.err.println("collision "+cptDeviation);
					vecteurRefelchi=  extra.add(ClassePhysique.refraction(indiceDeRefraction, 1, vecteurIncident,vecteurNormalUnitaire.multiply(-1)));
				}
			}else{
				vecteurRefelchi = ClassePhysique.reflexion(vecteurIncident, vecteurNormalUnitaire.multiply(1));
			}
			for(TransmissionInfoNiveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Listener.class) ) {
				ecout.getAngleIncidentMilieu2(1, ClassePhysique.calculAngle(vecteurIncident, vecteurNormalUnitaire));
				ecout.getAngleReflechiMilieu2(1, ClassePhysique.calculAngle(vecteurRefelchi, vecteurNormalUnitaire.multiply(-1)));
			}
		}

		return vecteurRefelchi;
	}

	/**
	 * Cette méthode permet de savoir si tels normale du prisme sera dessiner ou pas.
	 * @param nbNormale la normale en question.
	 * @param dessinable <p><b>true</b> la normale sera dessiné.
	 * <p><b>false</b> la normale ne sera pas dessiné.
	 */
	public void dessinerNormale(int nbNormale, boolean dessinable) {
		normaleSelect[nbNormale] = dessinable;
	}
	/**
	 * Cette méthode permet d'envoyer les transformations appliquées sur le prisme.
	 * @return une liste des transformations appliquées sur le prisme.
	 */
	public ArrayList<AffineTransform> envoieTransfo(){
		ArrayList<AffineTransform> liste = new ArrayList<AffineTransform>();
		liste.add(transfoZone);
		liste.add(rotaZone);
		return liste;
	}

	/**
	 * Cette méthode permet d'envoyer les zones de contact du prisme.
	 * @return  les zones de contact du prisme.
	 */
	public ArrayList<Line2D.Double> envoieLigneCollision() {
		return listeLigneTransfo;
	}
	/**
	 * Cette méthode permet de faire une translation d'une ligne de facon manuelle.
	 * @param ligneActuelle la ligne à modifier.
	 * @param translatX la translation horizontale.
	 * @param translatY la translation verticale.
	 * @return la nouvelle ligne translaté.
	 */
	private Line2D.Double translateLigne(Line2D.Double ligneActuelle, double translatX, double translatY){
		double posX1 = ligneActuelle.getX1() + translatX;
		double posX2 = ligneActuelle.getX2() + translatX;
		double posY1 = ligneActuelle.getY1() + translatY;
		double posY2 = ligneActuelle.getY2() + translatY;
		return new Line2D.Double(posX1,posY1,posX2,posY2);
	}
	/**
	 * Cette méthode permet de faire une rotation autour d'un point manuellement.
	 * @param ligneActuelle la ligne à modifier.
	 * @param posRotateX la position horizontale de rotation.
	 * @param posRotateY la position verticale de rotation.
	 * @return la nouvelle ligne rotationner.
	 */
	private Line2D.Double rotateLigne(Line2D.Double ligneActuelle, double posRotateX, double posRotateY){
		Line2D.Double ligneTemp = translateLigne(ligneActuelle,-posRotateX,-posRotateY);
		double posX1 = (ligneTemp.getX1()*Math.cos(angle)) +  (ligneTemp.getY1()*-Math.sin(angle));
		double posY1 = (ligneTemp.getX1()*Math.sin(angle)) +  (ligneTemp.getY1()*Math.cos(angle));
		double posX2 = (ligneTemp.getX2()*Math.cos(angle)) +  (ligneTemp.getY2()*-Math.sin(angle));
		double posY2 = (ligneTemp.getX2()*Math.sin(angle)) +  (ligneTemp.getY2()*Math.cos(angle));
		ligneTemp = new Line2D.Double(posX1,posY1,posX2,posY2);
		ligneTemp = translateLigne(ligneTemp,posRotateX,posRotateY);
		return ligneTemp;
	}
	/**
	 * Cette méthode permet d'agrandir une ligne manuellement
	 * @param ligneActuelle la ligne à modifier.
	 * @param scaleX l'agrandissement en x.
	 * @param scaleY l'agrandissement en y.
	 * @return la nouvelle ligne agrandit.
	 */
	private Line2D.Double scaleLigne(Line2D.Double ligneActuelle,double scaleX, double scaleY){
		double posX1 = ligneActuelle.getX1()*scaleX;
		double posY1 = ligneActuelle.getY1()*scaleY;
		double posX2 = ligneActuelle.getX2()*scaleX;
		double posY2 = ligneActuelle.getY2()*scaleY;
		return new Line2D.Double(posX1,posY1,posX2,posY2);
	}

}
