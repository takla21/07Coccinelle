package physique;

import java.io.Serializable;

/**
 * Cette méthode permet de créer un vecteur à 3 dimensions.
 * @author Kevin Takla
 * @version 2.0
 */
public class Vector3d implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2426382261652212519L;
	final private static double  VALEUR_DEFAUT = 0.0;
	private double x;	
	private double y;
	private double z;
	
	/**
	 * Cette méthode permet de créer un vecteur dans l'espace
	 * @param x la position horizontale du vecteur.
	 * @param y la position verticale du vecteur.
	 * @param z la position frontale(en z) du vecteur.
	 */
	public Vector3d(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Cette méthode permet de transformer un vecteur de 2 dimension en 3 dimension.
	 * @param vecteurATransformer un vecteur à 2 dimension.
	 */
	public Vector3d(Vector2d vecteurATransformer){
		this.x = vecteurATransformer.getX();
		this.y = vecteurATransformer.getY();
		this.z = VALEUR_DEFAUT;
	}
	/**
	 * Cette méthode permet d'obtenir la position horizontale du vecteur.
	 * @return la position en x du vecteur.
	 */
	public double getX(){
		return x;
	}
	/**
	 * Cette méthode permet d'obtenir la position verticale du vecteur.
	 * @return la position en y du vecteur.
	 */
	public double getY(){
		return y;
	}
	/**
	 * Cette méthode permet d'obtenir la position en z du vecteur.
	 * @return la position en z du vecteur.
	 */
	public double getZ(){
		return z;
	}
	
	/**
	 * Cette méthode permet de mutliplier le vecteur par un scalaire
	 * @param scalaire le scalaire multiplié
	 */
	public void multiply(double scalaire){
		x = scalaire*x;
		y = scalaire*y;
		z = scalaire*z;
	}
	
	/**
	 * Cette méthode permet de faire le produit scalaire entre 2 vecteurs.
	 * @param vecteur1 le premier vecteur.
	 * @param vecteur2 le deuxième vecteur.
	 * @return le vecteur résultant du produit vectoriel
	 */
	public static Vector3d produitVectoriel(Vector3d vecteur1, Vector3d vecteur2){
		vecteur1.produitVectoriel(vecteur2);
		return vecteur1;
	}
	/**
	 * Cette méthode permet de faire le produit scalaire d'un vecteur avec un autre.
	 * @param autreVecteur le vecteur qui sera utilisé dans le produit vectoriel.
	 */
	private void produitVectoriel(Vector3d autreVecteur){
		double Xtemp = this.x;
		double Ytemp = this.y;
		double Ztemp = this.z;
		this.x = (Ytemp*autreVecteur.getZ()) - (Ztemp*autreVecteur.getY());
		this.y = (Ztemp*autreVecteur.getX()) - (Xtemp*autreVecteur.getZ());
		this.z = (Xtemp*autreVecteur.getY()) - (Ytemp*autreVecteur.getX());
	}
	
	/**
	 * Cette méthode permet de trouver la norme de ce vecteur.
	 * @return la norme du vecteur.
	 */
	public double modulus(){
		return Math.sqrt(Math.pow(x, 2)+ Math.pow(y, 2) + Math.pow(z, 2) );
	}
	
	/**
	 * Cette méthode permet de pouver convertir le vecteur en une chaine.
	 */
	public String toString(){
		return "[" + x + ", " + y + ", " + z + "]";		
	}
	
	/**
	 * Cette méthode permet de faire la somme de deux vecteur de 3 dimension
	 * @param autreVecteur L'autre vecteur
	 * @return la somme des 2 vecteurs
	 */
	public Vector3d add(Vector3d autreVecteur) {
		this.x += autreVecteur.getX();
		this.y += autreVecteur.getY();
		this.z += autreVecteur.getZ();
		return new Vector3d(x,y,z);
	}
	/**
	 * Cette méthode permet de soustraire deux vecteur.
	 * @param autreVecteur le vecteur soustrait
	 * @return le résultat de la soustraction des 2 vecteurs.
	 */
	public Vector3d substract(Vector3d autreVecteur) {
		this.x -= autreVecteur.getX();
		this.y -= autreVecteur.getY();
		this.z -= autreVecteur.getZ();
		return new Vector3d(x,y,z);
	}
	/**
	 * Cette méthode permet de normaliser le vecteur
	 * @return le vecteur normalisé
	 */
	public Vector3d normalize() {
		double mod = modulus();			

		if(mod > 0.0)
			return new Vector3d(x/mod, y/mod,z/mod);
		else
			return new Vector3d(VALEUR_DEFAUT, VALEUR_DEFAUT,VALEUR_DEFAUT);
	}
	/**
	 * Cette méthode permet de faire le produit scalaire avec un autre vecteur
	 * @param v l'autre vecteur.
	 * @return le résultat du produit scalaire
	 */
	public double dot(Vector3d v){
		return (x*v.getX() + y*v.getY());
	}
}
