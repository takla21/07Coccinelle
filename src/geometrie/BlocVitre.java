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

import ecouteur.TransmissionInfoNiveau1Listener;
import physique.ClassePhysique;
import physique.Vector2d;
/**
 * 
 * Classe de géometrie qui permet de créer l'objet bloc de vitre
 * 
 * @author Kevin Takla colloboration de Zi Zheng Wang
 * @version 1.0
 */
public class BlocVitre {
	private double posXBloc;
	private double posYBloc;
	private double longeur;
	private double largeur;
	private double angle;
	private Rectangle2D.Double bloc;
	private Ellipse2D.Double pointCentre;
	private Shape rectangle;
	private double indiceDeRefraction = 1.33; 
	private ArrayList<Vector2d> listesNormales;
	private final double DIAMETRE_POINT = 1;
	private ArrayList<Ellipse2D.Double> listePoint;
	private AffineTransform transfoZone, rotaZone;
	private ArrayList <Path2D.Double> normaleDessin;
	private boolean normaleSelect[] = {false,true,false,false};
	private ArrayList<Line2D.Double> lignesNormale, listeLigneTransfo;

	
    /**
     * Constructeur de la classe BlocVitre qui sert à créer un objet blocVitre 
     * @param posX - sa coordonnée x
     * @param posY - sa coordonnée y
     * @param longeur - le longeur du blocVitre
     * @param largeur - le largeur du blocVitre
     * @param angle - angle de rotation pour le blocVitre
     */
	public BlocVitre(double posX, double posY, double longeur, double largeur, double angle){
		this.posXBloc = posX;
		this.posYBloc = posY;
		this.longeur = longeur;
		this.largeur = largeur;
		this.angle = angle;
		initialiserMatrices();
		creetBloc();
		pointCentre = new Ellipse2D.Double(bloc.getX() + bloc.getWidth()/2 - DIAMETRE_POINT/2, bloc.getY() + bloc.getHeight()/2 - DIAMETRE_POINT/2,DIAMETRE_POINT,DIAMETRE_POINT);
		creerPointNormale();
		creerVecteur();
		creerNormaleDessin();
	
		creerLigne();
	}
	/**
	 * Méthode qui sert à créer un blocVitre identifique à partir d'un autre blocVitre
	 * @param autreBlocVitre - objet blocVitre
	 */
	public BlocVitre(BlocVitre autreBlocVitre){
		posXBloc = autreBlocVitre.getX();
		posYBloc = autreBlocVitre.getY();
		largeur = autreBlocVitre.getLargeur();
		longeur = autreBlocVitre.getLongueur();
		angle = autreBlocVitre.getAngle();
		initialiserMatrices();
		creetBloc();
		pointCentre = new Ellipse2D.Double(bloc.getX() + bloc.getWidth()/2 - DIAMETRE_POINT/2, bloc.getY() + bloc.getHeight()/2 - DIAMETRE_POINT/2,DIAMETRE_POINT,DIAMETRE_POINT);
		creerPointNormale();
		creerVecteur();
		creerNormaleDessin();
		normaleSelect[0] = false;
		normaleSelect[1] = false;
		normaleSelect[2] = false;
		normaleSelect[3] = false;
		creerLigne();
	}
	/**
	 * Méthode qui sert à créer le composant graphic du blocVitre
	 */
	private void creetBloc() {
		bloc = new Rectangle2D.Double(posXBloc,posYBloc,longeur,largeur);

	}
	/**
	 * Cette méthode permet de créer les zones de contact(lignes)
	 */
	private void creerLigne(){
		lignesNormale = new ArrayList<Line2D.Double>();
		lignesNormale.add(new Line2D.Double(posXBloc,posYBloc,posXBloc + longeur,posYBloc));
		lignesNormale.add(new Line2D.Double(posXBloc + longeur,posYBloc,posXBloc + longeur,posYBloc + largeur));
		lignesNormale.add(new Line2D.Double(posXBloc,posYBloc + largeur,posXBloc + longeur,posYBloc + largeur));
		lignesNormale.add(new Line2D.Double(posXBloc,posYBloc,posXBloc,posYBloc + largeur));
	}
	/**
	 * Cette méthode permet d'initialiser les matrices.
	 */
	private void initialiserMatrices(){
		transfoZone = new AffineTransform();
		rotaZone = new AffineTransform();
	}
	/**
	 * Cette méthode permet de créer les points qui sont dans les normales.
	 */
	private void creerPointNormale(){
		listePoint = new ArrayList<Ellipse2D.Double>();
		listePoint.add(new Ellipse2D.Double(bloc.getX() + bloc.getWidth()/2 - DIAMETRE_POINT/2, bloc.getY()  - DIAMETRE_POINT/2,DIAMETRE_POINT,DIAMETRE_POINT));
		listePoint.add(new Ellipse2D.Double(bloc.getX() + bloc.getWidth() + DIAMETRE_POINT/2, bloc.getY() + bloc.getHeight()/2 - DIAMETRE_POINT/2,DIAMETRE_POINT,DIAMETRE_POINT));
		listePoint.add(new Ellipse2D.Double(bloc.getX() + bloc.getWidth()/2 - DIAMETRE_POINT/2, bloc.getY() + bloc.getHeight() + DIAMETRE_POINT/2,DIAMETRE_POINT,DIAMETRE_POINT));
		listePoint.add(new Ellipse2D.Double(bloc.getX()  - DIAMETRE_POINT/2, bloc.getY() + bloc.getHeight()/2 - DIAMETRE_POINT/2,DIAMETRE_POINT,DIAMETRE_POINT));
	}
	
	/**
	 * Méthode qui sert à trouver les normales des quatre zones.
	 */
	private void creerVecteur(){
		listesNormales = new ArrayList<Vector2d>();
		
		Shape pointCentreTransfo = transfoZone.createTransformedShape(rotaZone.createTransformedShape(pointCentre));
		Rectangle pointC = pointCentreTransfo.getBounds();
		double posX = pointC.getX();
		double posY = pointC.getY();

		for(int nbNormale = 0; nbNormale < listePoint.size(); nbNormale++){
			Ellipse2D.Double point = listePoint.get(nbNormale);
			Shape pointRotationner = transfoZone.createTransformedShape(rotaZone.createTransformedShape(point));
			Rectangle posNormale= pointRotationner.getBounds();
			listesNormales.add(nbNormale,new Vector2d(posNormale.getX() - posX,posNormale.getY() - posY));
		}
	}
	/**
	 * Cette méthode permet de creer les formes géométrique des normales
	 */
	private void creerNormaleDessin(){
		final double largeurTrait = 5;
		normaleDessin = new ArrayList<Path2D.Double>();
		Path2D.Double ligne = new Path2D.Double();
		ligne.moveTo(listePoint.get(0).getX(), listePoint.get(0).getY());
		ligne.lineTo(listePoint.get(0).getX(), listePoint.get(0).getY() - largeurTrait);
		normaleDessin.add(ligne);
		ligne = new Path2D.Double();
		ligne.moveTo(listePoint.get(1).getX(), listePoint.get(1).getY());
		ligne.lineTo(listePoint.get(1).getX() + largeurTrait , listePoint.get(1).getY());
		normaleDessin.add(ligne);
		ligne = new Path2D.Double();
		ligne.moveTo(listePoint.get(2).getX(), listePoint.get(2).getY());
		ligne.lineTo(listePoint.get(2).getX(), listePoint.get(2).getY() + largeurTrait );
		normaleDessin.add(ligne);
		ligne = new Path2D.Double();
		ligne.moveTo(listePoint.get(3).getX(), listePoint.get(3).getY());
		ligne.lineTo(listePoint.get(3).getX()  - largeurTrait , listePoint.get(3).getY());
		normaleDessin.add(ligne);
	}
	
	/**
	 * Méthode pour dessiner l'objet blocVitre
	 * @param g2d - le contexe graphique.
	 * @param aff - la matrice du monde réel
	 * @param c - la couleur de l'objet blocVitre
	 * @param matMC matrice monde réelle
	 * @param scientifique  <p><b>true</b> le niveau est en mode scientifique
	 * <p><b>false</b> le niveau n'est pas en mode scientifique
	 */
	public void dessiner(Graphics2D g2d, AffineTransform aff, Color c,AffineTransform matMC,boolean scientifique){
		AffineTransform matt = new AffineTransform(aff);
		AffineTransform rota = new AffineTransform();
		rota.rotate(angle,posXBloc,posYBloc);
		Shape rect = rota.createTransformedShape(bloc); //rotate
		rectangle = matt.createTransformedShape(rect); //size Monde réel
		if(!scientifique){
			g2d.setColor(c);
			g2d.fill(rectangle);
		}else{
			g2d.setColor(Color.gray);
			g2d.setStroke(new BasicStroke(4));
		}
		g2d.fill(rectangle);
		g2d.setColor(c);
		g2d.draw(rectangle);
		
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
		}
		g2d.setColor(Color.blue);
		g2d.setStroke(new BasicStroke(3));
		listeLigneTransfo = new ArrayList<Line2D.Double>(lignesNormale);
		for(int l =0; l<listeLigneTransfo.size();l++){
			Line2D.Double ligne = listeLigneTransfo.get(l);{
				ligne = scaleLigne(ligne,aff.getScaleX(),aff.getScaleY());
				ligne = rotateLigne(ligne, posXBloc * aff.getScaleX(),posYBloc * aff.getScaleY());
				ligne = translateLigne(ligne,aff.getTranslateX(), aff.getTranslateY());
				g2d.draw(ligne);
				listeLigneTransfo.set(l,ligne);
			}

		}
		g2d.setStroke(new BasicStroke(1));
		
		transfoZone = new AffineTransform(aff);
		rotaZone = new AffineTransform(rota);
		creerVecteur();
		
	}
	/**
	 * Méthode pour modifier l'angle rotation du bloc
	 * @param angle - angle de rotation en radian
	 */
	public void setOrientation(double angle){
		this.angle = angle;
	}
	/**
	 * Méthode pour modifier la position de l'objet blocRessort
	 * @param posX - le cordonné x
	 * @param posY - le cordonné y
	 */
	public void setPosition(double posX,double posY){
		this.posXBloc = posX;
		this.posYBloc = posY;
		creetBloc();
	}
	/**
	 * Méthode qui retourne la coordonneé x de l'objet blocVitre
	 * @return cordonné x de l'objet blocVitre
	 */
	public double getX(){
		return posXBloc;
	}
	/**
	 * Méthode qui retourne la coordonnée y de l'objet blocVitre
	 * @return cordonné y de l'objet blocVitre
	 */
	public double getY(){
		return posYBloc;
	}
	/**
	 * Méthode qui retourne l'angle de rotaion
	 * @return angle de rotation
	 */
	public double getAngle(){
		return angle;
	}
	/**
	 * Méthode qui détecte si certains positions sont à l'intérieur de l'objet blocVitre 
	 * @param x - la coordonnée x pour la position
	 * @param y - la coordonnée y pour la position
	 * @return <b>true</b> si l'objet blocVitre contient la position
	 * <p><b>false</b> si l'objet blocVitre ne contient pas la position
	 */
	public boolean contains(double x, double y) {
		if(rectangle.contains(x, y)){
			return true;
		}else{
			return false;
		}

	}
	/**
	 * Méthode qui retourne une Shape pour l'objet blocVitre
	 * @return une Shape pour l'objet blocVitre
	 */
	public Shape getShape() {
		return rectangle;
	}
	/**
	 * Méthode qui retourne l'indice de réfraction pour l'objet blocVitre
	 * @return l'indice de réfraction
	 */
	public double getIndiceRefraction(){
		return indiceDeRefraction;
	}
	/**
	 * Méthode pour modifier l'indice de réfraction pour le blocVitre
	 * @param n = l'indice de réfraction
	 * @param OBJETS_ENREGISTRES un écouteur
	 */
	public void setIndiceRefraction(double n,EventListenerList OBJETS_ENREGISTRES){
		indiceDeRefraction = n;
		calculerAngleCritique(1, n, OBJETS_ENREGISTRES);
	}
	/**
	 * Méthode qui retourne une aire pour l'objet blocVitre
	 * @return une aire pour l'objet blocVitre
	 */
	public Area aire(){
		Area aire = new Area(bloc);
		return aire;
	}
	/**
	 * Méthode qui retourne longeur de l'objet blocVitre
	 * @return la longeur de l'objet blocVitre
	 */
	public double getLongueur(){
		return longeur;
	}
	/**
	 * Méthode qui retourne largeur de l'objet blocVitre
	 * @return la largeur de l'objet blocVitre
	 */
	public double getLargeur(){
		return largeur;
	}
	/**
	 * Cette méthode permet de calculer la réfraction de la lumière dans le bloc vitre.
	 * @param vecteurIncident le rayon incident
	 * @param cptDeviation le nombre de déviation 
	 * @param nbNormale la normale utilisé.
	 * @param OBJETS_ENREGISTRES l'écouteur qui transmettra les informations.
	 * @param extra l'incrementation actuelle du laser.
	 * @return le rayon réfracté
	 */
	public Vector2d calculerAngleDeviation(Vector2d vecteurIncident,int cptDeviation,int nbNormale,EventListenerList OBJETS_ENREGISTRES,Vector2d extra){
		vecteurIncident = vecteurIncident.vecteurUnitaire();
		Vector2d vecteurNormalUnitaire =listesNormales.get(nbNormale).vecteurUnitaire();
		Vector2d vecteurRefelchi;
		extra = extra.vecteurUnitaire();
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
	 * Méthode qui détecte la collision entre une aire avec l'objet blocVitre
	 * @param ligne - la forme de la zone en question
	 * @param point - la forme du point
	 * @return <b>true</b> s'il y a une collision
	 * <p><b>false</b> s'il n'y a pas de collision
	 */
	public boolean collision(Shape ligne, Shape point){
		Area aire1 = new Area(ligne);
		Area aire2 = new Area(point);
		Area intersect = new Area(aire1);
		intersect.intersect(aire2);
		return !intersect.isEmpty();
	}
	/**
	 * Cette méthode permet de decider si une des normales est dessiner.
	 * @param nbNormale la normale en question.
	 * @param dessinable <b>true</b> la normale sera dessiner.
	 * <p><b>false</b> la normale ne sera pas dessiner.
	 */
	public void dessinerNormale(int nbNormale, boolean dessinable) {
		normaleSelect[nbNormale] = dessinable;
	}
	/**
	 * Cette méthode permet de calculer l'angle critique de ce milieu
	 * @param n1 le premier milieu
	 * @param n2 le deuxième milieu
	 * @param OBJETS_ENREGISTRES l'écouteur qui permettera d'afficher cette angle.
	 */
	private void calculerAngleCritique(double n1, double n2, EventListenerList OBJETS_ENREGISTRES){
		double angle = Math.atan(n2/n1);
		for(TransmissionInfoNiveau1Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Listener.class) ) {
			ecout.getAngleCritiqueMilieu(0, angle);
		}
	}
	
	/**
	 * Cette méthode permet d'envoyer les transformations appliquées sur le bloc vitre.
	 * @return une liste des transformations appliquées sur le bloc vitres.
	 */
	public ArrayList<AffineTransform> envoieTransfo(){
		ArrayList<AffineTransform> liste = new ArrayList<AffineTransform>();
		liste.add(transfoZone);
		liste.add(rotaZone);
		return liste;
	}

	/**
	 * Cette méthode permet d'envoyer les zones de contact du bloc vitre.
	 * @return  les zones de contact du bloc vitre.
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
