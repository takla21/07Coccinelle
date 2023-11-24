
package physique;

import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;

import ecouteur.TransmissionInfoNiveau1Scene2Listener;
import ecouteur.TransmissionInfoNiveau4Listener;

/**
 * La classe de physique ou on souvegarde tous les formules physiques pour la réfraction de la lumière, le champ magnétique et la collision inélastique
 * @author Kevin Takla ; collaborateur Zi Zheng Wang
 * @version 2.0
 */

public class ClassePhysique {
	private final static double CONSTANTE_LOI_COULOMB = 9*Math.pow(10,9);
	public final static double EPSILON = 1e-10;
	public final static double INFINITY = Double.POSITIVE_INFINITY;

	/**
	 * Méthode permettant de calculer l'angle de réfraction entre 2 milieux quelconques.
	 * @param n1 le premier milieu
	 * @param n2 le deuxième milieu 
	 * @param vecteurIncident le vecteur initiale de la lumière
	 * @param normale la normale du milieu.
	 * @return le vecteur finale de la lumière.
	 */
	public static Vector2d refraction (double n1, double n2, Vector2d vecteurIncident,Vector2d normale) throws RuntimeException {
		double rapportN = n1/n2;
		Vector2d vecteurInverse = vecteurIncident.multiply(-1);
		
		double multiplicationInvparNormal = vecteurInverse.dot(normale);
		double partieRacine =  (1-(Math.pow(rapportN, 2) * (1- Math.pow(multiplicationInvparNormal,2))));
		double resultatPremierePartie = (rapportN*multiplicationInvparNormal) - Math.sqrt(partieRacine);
		Vector2d partieDeux = normale.multiply(resultatPremierePartie);
		Vector2d partieUn = vecteurIncident.multiply(rapportN);
		Vector2d somme = partieUn.add(partieDeux);
		return somme;
	}
	/**
	 * Cette méthode permet de calculer la réflexion de la lumière par rapport à un mirroir plan.
	 * @param vecteurIncident le vecteur initiale de la lumière
	 * @param normale la normale du mirroir.
	 * @return le vecteur finale de la lumière.
	 */
	public static Vector2d reflexion(Vector2d vecteurIncident, Vector2d normale){
		Vector2d vecteurInverse = vecteurIncident.multiply(-1);
		double premierePartieFormule = Vector2d.produitScalaire(vecteurInverse, normale);
		Vector2d vecteurPremierePartie = normale.multiply(2*premierePartieFormule);
		Vector2d vecteurResultat = vecteurPremierePartie.add(vecteurIncident);
		return vecteurResultat.vecteurUnitaire();
	}

	/**
	 * Cette méthode permet de savoir si la lumière fait une réflexion total interne lorsqu'il traverse le milieu.
	 * @param v le vecteur incident.
	 * @param N le vecteur normale
	 * @param n1 l'indice de réfraction incident
	 * @param n2 l'indice de réfraction de réfraction.
	 * @return <p><b>true</b> la lumière fait une reflexion
	 * <p><b>false</b> la lumière entre dans le milieu
	 */
	public static boolean isTotalInternalReflection(Vector2d v, Vector2d N, double n1, double n2){
		double rapportN = n1/n2;
		Vector2d vecteurInverse = v.multiply(-1);
		
		double multiplicationInvparNormal = vecteurInverse.dot(N);
		double partieRacine =  (1-(Math.pow(rapportN, 2) * (1- Math.pow(multiplicationInvparNormal,2))));
		if(partieRacine < 0){
			return true;
		}else{
			return false;
		}
	}

	// les méthodes de collision by Zi Zheng
	// La détection de collision entre deux sphères en mouvement à vitesse constante (détecter collision)
	/**
	 * Méthode qui trouver la position finale apres un delta temps. 
	 * @param posInitial - la posiiton Initiale.
	 * @param vitesse - la vitesse du déplacement.
	 * @param temps - delta temps.
	 * @return un vecteur qui représente la position finale après un temps données .
	 */
	public static Vector2d deplacement(Vector2d posInitial, Vector2d vitesse, double temps){
		Vector2d posFinal = posInitial.add(vitesse.multiply(temps));
		return posFinal;
	}
	/**
	 * Méthode qui trouver la distance entre deux centre des sphères au moment de la collision. 
	 * @param positionA - la posiiton de l'objet A.
	 * @param positionB - la position de l'objet B.
	 * @return un vecteur qui représente la distance entres le centre de l'objet A et l'objet B.
	 */
	public static Vector2d distanceCollision(Vector2d positionA, Vector2d positionB){
		Vector2d DistanceCollision = positionA.substract(positionB);
		DistanceCollision = Vector2d.absolue(DistanceCollision);
		return DistanceCollision;
	}
	/**
	 * Méthode qui prévoit le moment de collision. 
	 * @param posIniA - la posiiton de l'objet A.
	 * @param posIniB - la position de l'objet B.
	 * @param vitesseA - la vitesse de l'objet A.
	 * @param vitesseB - la vitesse de l'objet B.
	 * @param rayonA - le rayon de l'objetA.
	 * @param rayonB - le rayon de l'objetB.
	 * @return un temps (double) qui représente la collision dans les prochaines temps.
	 * La détection de collision entre deux sphèresen mouvement à vitesse constante
	 */
	public static double prevuDeltaTempsCollision(Vector2d posIniA, Vector2d posIniB, Vector2d vitesseA,Vector2d vitesseB, double rayonA, double rayonB){
		double temprevoit = 0;
		Vector2d positionIni = posIniB.substract(posIniA);
		Vector2d vitesse = vitesseB.substract(vitesseA);
		double distanceInter = rayonA + rayonB;
		double a = Vector2d.produitScalaire(vitesse, vitesse);
		double b = Vector2d.produitScalaire(positionIni, vitesse)*2;
		double c = Vector2d.produitScalaire(positionIni, positionIni) - Math.pow(distanceInter,2);
		double calculInter = Math.pow(b, 2) - 4*a*c;
		if(calculInter < 0){
			temprevoit = -1; //impossible
		}else{
			double t1 = (-b + Math.pow(calculInter, 0.5)) / 2*a;
			double t2 = (-b - Math.pow(calculInter, 0.5)) / 2*a;
			if(calculInter == 0){
				temprevoit = t1;
			}else{
				if(t1 > 0 && t2 <= 0){
					temprevoit = t1;
				}else if(t1 <= 0 && t2 > 0){
					temprevoit = t2;
				}
			}
		}
		return temprevoit;
	}
	// Les collisions inélastiques
	/** 
	 * la méthode qui sert à trouver un scalaire par rapport la position d'objA et d'objB
	 * @param positionA - un vecteur qui représente la position de l'objet A
	 * @param positionB - un vecteur qui représente la position de l'objet B
	 * @return le scalaire qui représente le normale de la surface de l'objet B
	 */
	public static Vector2d normaleSurfaceObjB(Vector2d positionA,Vector2d positionB){
		Vector2d différenceDeuxVec = positionA.substract(positionB);
		double normale = Math.abs(Vector2d.normale(différenceDeuxVec));
		Vector2d scalaire = différenceDeuxVec.multiply((1/normale));
		return scalaire;
	}
	/**
	 * Méthode qui sert à trouver le vecteur réciproque 
	 * @param scalaire - vecteur initial (entrer)
	 * @return le vecteur perpendiculaire de scalaire
	 */
	public static Vector2d reciproque(Vector2d scalaire){
		double x = scalaire.getX();
		double y = -1*scalaire.getY();
		Vector2d reciproque = new Vector2d(y, x);
		return reciproque;
	}
	/** 
	 * Méthode pour détecter la vitesse de rapprochement dans une collision à deux objets non ponctuels
	 * @param vitesseIniA - vitesseInitial de l'objet A
	 * @param vitesseIniB - vitesseInitial de l'objet B
	 * @param NormalSurfaceObjB - la normale à la surface de l'objet B poitant vers l'extérieur de B
	 * @return La vitesse relative de A par rapport à B selon l'axe n (m/s)
	 */
	public double vitesseRapprochement(Vector2d vitesseIniA, Vector2d vitesseIniB, Vector2d NormalSurfaceObjB){
		double vitesseRelativeA = Vector2d.produitScalaire((vitesseIniA.substract(vitesseIniB)),NormalSurfaceObjB);
		return vitesseRelativeA;
	}
	/** 
	 * Méthode pour détecter la position de A qui est relativement en lien avec la position de B
	 * @param vitesseRelativeA - vitesse relative de A par rapport à B
	 * @return true = s'éloigner / false = se rapprocher
	 */
	public boolean sens(double vitesseRelativeA){
		return (vitesseRelativeA > 0 ); 
	}
	/** 
	 * Méthode qui retourne une valeur d'impulsion qui sert à déterminer la vitesse relative de A et B plus tard
	 * @param e - le coefficient de restitution
	 * @param masseA - masse de l'objetA
	 * @param masseB - masse de l'objetB
	 * @param vitesseIniA - vitesse de l'objet A
	 * @param vitesseIniB - vitesse de l'objet B
	 * @param normaleSurfaceB - la normale à la surface de l'objet B poitant vers l'extérieur de B
	 * @return la valeur d'impulsion
	 */
	public static double getImpulsion(double e, double masseA, double masseB, Vector2d vitesseIniA, Vector2d vitesseIniB, Vector2d normaleSurfaceB){
		double produitScalaire = Vector2d.produitScalaire((vitesseIniA.substract(vitesseIniB)),normaleSurfaceB);
		double numerateur = -1*(1+e);
		double denominateur = (1/masseA + 1/masseB);
		double impulsion = (numerateur/denominateur)*produitScalaire;
		return impulsion;
	}
	/** 
	 * Méthode qui retourne un vecteur qui représente la vitesse finale de l'objet A
	 * @param vitesseIniA - vitesse initiale de l'objet A
	 * @param impulsion - la valeur d'impulsion qui sert à déterminer la vitesse finale
	 * @param masseA - masse de l'objet A
	 * @param normaleSurfaceB - la normale à la surface de l'objet B poitant vers l'extérieur de B
	 * @return le vecteur qui représente la vitesse finale de l'objet A
	 */
	public static Vector2d vitesseFinaleA(Vector2d vitesseIniA, double impulsion, double masseA, Vector2d normaleSurfaceB){
		Vector2d produite = Vector2d.multiply(normaleSurfaceB, ((double)(impulsion/masseA)));
		Vector2d vitesseFinaleA = vitesseIniA.add(produite);
		return vitesseFinaleA;
	}
	/** 
	 * Méthode qui retourne un vecteur qui représente la vitesse finale de l'objet B
	 * @param vitesseIniB - vitesse initiale de l'objet B
	 * @param impulsion - la valeur d'impulsion qui sert à déterminer la vitesse finale
	 * @param masseB - masse de l'objet B
	 * @param normaleSurfaceB - la normale à la surface de l'objet B poitant vers l'extérieur de B
	 * @return le vecteur qui représente la vitesse finale de l'objet B
	 */
	public static Vector2d vitesseFinaleB(Vector2d vitesseIniB, double impulsion, double masseB, Vector2d normaleSurfaceB){
		Vector2d produite = Vector2d.multiply(normaleSurfaceB, ((double)(impulsion/masseB)));
		Vector2d vitesseFinaleB = vitesseIniB.substract(produite);
		return vitesseFinaleB;
	}
	/** 
	 * Méthode qui sert à calculer l'angle réfléchi
	 * @param vecteur- vecteur intré
	 * @param normal- normal d'un surface
	 * @return un vector qui représente l'angle réfléchi du vecteur incident
	 */
	public static Vector2d AngleReflexion(Vector2d vecteur, Vector2d normal){
		Vector2d e = vecteur.multiply(-1);
		double inter = 2*Vector2d.produitScalaire(e,normal);
		Vector2d r = (normal.multiply(inter)).add(vecteur);
		return r;
	}
	/** 
	 * Méthode pour calculer l'angle interne des deux vecteurs
	 * @param vecteurUn - vecteur A
	 * @param vecteurDeux - vecteur B
	 * @return angle interne des deux vecteur en radian
	 */

	public static double calculAngle(Vector2d vecteurUn, Vector2d vecteurDeux){
		double scalaire = Vector2d.produitScalaire(vecteurUn, vecteurDeux);
		double a = Vector2d.normale(vecteurUn);
		double b = Vector2d.normale(vecteurDeux);
		double angle = scalaire/(a*b);
		angle = Math.acos(angle);
		return angle;
	}
	/**
	 * Méthode pour calculer l'angle en dégree 
	 * @param vitesse - vitesse d'un ballon
	 * @return angle en dégrée
	 */
	public static double calculAngle(Vector2d vitesse){
		double angle = 0;
		double sens = -1;
		double x = vitesse.getX();
		double y = vitesse.getY();
		angle = Math.atan(y/x)*360/(2*Math.PI);
		if(x > 0 && y < 0 ){
			angle = angle*sens;
		}
		else if(x < 0 && y < 0 ){
			angle = 180 - angle;
		}
		else if(x < 0 && y > 0 ){
			angle = 180 - angle;
		}
		else if(x > 0 && y > 0 ){
			angle = 360 - angle;
		}
		else if( x > 0 && y == 0){
			angle = 0;
		}
		else if( x < 0 && y == 0){
			angle = 180;
		}
		else if( x == 0 && y > 0){
			angle = 270;
		}
		else if( x == 0 && y < 0){
			angle = 90;
		}
		else if( x == 0 && y == 0){
			angle = 0;
		}
		return angle;
	}


	//les méthodes pour la charge ponctuel by Zi Zheng
	/**
	 * Méthode permet d'avoir l'orientation du champ électrique à position entreé
	 * @param charge - la position de particule chargé
	 * @param position - l'endroit ou on vas évaluer le champ
	 * @return un veteur qui représente l'orientation du champ magnétique qui est généré par la charge ponctuelle
	 */
	public static Vector2d orientationChampElectrique(Vector2d charge, Vector2d position){
		Vector2d deplacement = position.substract(charge);
		double distance = deplacement.modulus();
		Vector2d orientation = deplacement.multiply(1/distance);
		return orientation;
	}
	/**
	 * Méthode permet d'avoir le champ électrique à position entreé
	 * @param charge - la charge du particule
	 * @param distance - la distance entre la particule et la position 
	 * @param orientation - l'orientation du champ en vecteur
	 * @return un vecteur qui représente le champ magnétique qui est généré par la charge ponctuelle
	 */
	public static Vector2d champElectrique(double charge, double distance, Vector2d orientation){
		Vector2d champ = orientation.multiply(CONSTANTE_LOI_COULOMB*charge/(Math.pow(distance, 2)));
		return champ;
	}
	// méthodes fait par Kevin Takla
	/**
	 * 
	* Cette méthode permet de calculer l'accélération d'une particule en fonction des forces des particules.
	 * @param chargeParticuleUtilise la charge de la particule en question.
	 * @param chargeCoccinelle la charge de la coccinelle.
	 * @param chargeTrois la charge de l'autre particule.
	 * @param masseParticule la masse de la particule en question.
	 * @param distancePC la distance entre la particule et la coccinelle. 
	 * @param distancePP la distance entre la particule de départ et l'autre particule.
	 * @param OBJETS_ENREGISTRES c'est l'écouteur qui permettera de transmettre les forces appliquer sur les particules vers le panel 1.
	 * @param nbPart le nombre de particule
	 * @return l'accéleration en x et en y de la particule.
	 */
	public static Vector2d calculerAccelerationParticuleAvec3Particules(double chargeParticuleUtilise,double chargeCoccinelle,double chargeTrois,double masseParticule, Vector2d distancePC, Vector2d distancePP,EventListenerList OBJETS_ENREGISTRES, int nbPart){
		Vector2d forceCoccPart = calculerForceElectrique(chargeParticuleUtilise, chargeCoccinelle, distancePC);
		Vector2d forceEntrePart = calculerForceElectrique(chargeParticuleUtilise,chargeTrois,distancePP);
		Vector2d sommeForce = forceCoccPart.substract(forceEntrePart);
		if(OBJETS_ENREGISTRES != null){
			for(TransmissionInfoNiveau1Scene2Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau1Scene2Listener.class) ) {
				ecout.getForce(sommeForce, nbPart);
			}
		}
		return sommeForce.multiply(1/masseParticule);
	}
	/**
	 * Cette méthode permet de calcuer la force électrique de deux particules.
	 * @param charge1 la charge de la première particule.
	 * @param charge2 la charge de la deuxième particule.
	 * @param distance la distance entre les deux.
	 * @return la force appliquer par les particules.
	 */
	public static Vector2d calculerForceElectrique(double charge1, double charge2, Vector2d distance){
		Vector2d distanceUnitaire = distance.vecteurUnitaire();
		double distanceEntre2 = distance.module();
		double partieScalaire = CONSTANTE_LOI_COULOMB*charge1*charge2/Math.pow(distanceEntre2,2);
		return distanceUnitaire.multiply(partieScalaire);
	}
	/**
	 * Cette méthode permet de calculer l'accéleration de la coccinelle dans un champ électrique
	 * @param charge la charge de la coccinelle.
	 * @param masse sa masse
	 * @param champElectrique le champ électrique
	 * @return l'accéleration de la coccinelle.
	 */
	//utilisé dans cyclotron
	public static Vector2d calculerVitesseCoccinelleCyclotron(double charge,double masse,double champElectrique){
		double acceleration = (charge*champElectrique)/masse;
		Vector2d accelerationVect = new Vector2d(0,acceleration);
		return accelerationVect;
	}

/**
 * 
 * Cette méthode permet de calculer l'accéleration d'une particule(coccinelle) dans un champ mégnetique.
 * @param charge la charge de la particule.
 * @param masse la masse de la particule.
 * @param vitesse la vitesse actuelle de la particule.	 
 * @param champMagnetique le champ magnétique..
 * @param OBJETS_ENREGISTRES écouteur
 * @param spectro <p><b>true</b> le calcul est pour le spectromètre de masse
	 * <p><b>false</b> calcul d'un champs normale
 * @return l'accélération de la particule
 */
	//utilisé dans cyclotron
	public static Vector2d trouverAccelerationDansChampMagnetique(double charge,double masse,Vector2d vitesse, Vector3d champMagnetique,EventListenerList OBJETS_ENREGISTRES, boolean spectro){
		Vector3d forceMagnetique = trouverForceMagnetique(charge,vitesse,champMagnetique);
		if(spectro){
			for(TransmissionInfoNiveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau4Listener.class) ) {
				ecout.getForceMagnetique(1, forceMagnetique);
			}
		}
		forceMagnetique.multiply(1/masse);
		return new Vector2d(forceMagnetique.getX(),forceMagnetique.getY());
	}

	/**
	 * Cette méthode permet de calculer la force magnétique d'une particule dans un champ magnétique
	 * @param charge la charge de la particule.
	 * @param vitesse la vitesse de la particule.
	 * @param champMagnetique le champs magnétique.
	 * @return la force magnétique.
	 */
	public static Vector3d trouverForceMagnetique(double charge, Vector2d vitesse, Vector3d champMagnetique){
		Vector3d vitesse3D = new Vector3d(vitesse);
		vitesse3D.multiply(charge);
		Vector3d forceMagnetique = Vector3d.produitVectoriel( vitesse3D, champMagnetique);
		return forceMagnetique;
	}
	/**
	 * Cette méthode permet de calculer l'accélération d'une particule dans un sélécteur de vitesse.
	 * @param charge la vitesse d'une particule.
	 * @param masse la masse d'une particule.
	 * @param champsElectrique le champs électrique du sélecteur de vitesse.
	 * @param vitesse la vitesse de la particule.
	 * @param champMagnetique le champs magnétique de la particule.
	 * @param OBJETS_ENREGISTRES écouteur
	 * @return l'accéleration de la particule.
	 */
	public static Vector2d calculerAccelerationSelecteurVitesse(double charge,double masse, double champsElectrique, Vector2d vitesse, Vector3d champMagnetique,EventListenerList OBJETS_ENREGISTRES){
		Vector3d forceMagnetique = trouverForceMagnetique(charge,vitesse,champMagnetique);
		Vector3d forceElectrique = new Vector3d(new Vector2d(0,champsElectrique));
		forceElectrique.multiply(charge);
		for(TransmissionInfoNiveau4Listener ecout : OBJETS_ENREGISTRES.getListeners(TransmissionInfoNiveau4Listener.class) ) {
			ecout.getForceElectrique(forceElectrique);
			ecout.getForceMagnetique(0, forceMagnetique);
		}
		Vector3d sommeDesForces =forceMagnetique.add(forceElectrique);
		sommeDesForces.multiply(1/masse);
		return new Vector2d(sommeDesForces.getX(),sommeDesForces.getY());
	}
	/**
	 * Cette méthode permet de calculer l'accélération d'un objet selon la force qui est appliquer sur ce dernier.
	 * @param force la force appliquée.
	 * @param masse la masse de l'objet.
	 * @return l'accélération de l'objet.
	 */
	public static Vector2d calculerAcceleration(Vector2d force, double masse){
		return force.multiply(1/masse);
	}
}
