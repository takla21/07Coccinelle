
package physique;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
/**
 * 
 * Classe de physique qui permet de créer un vecteur physique qui contient deux cordonées x et y pour les calculs mathémathiques
 * 
 * @author Zi Zheng Wang
 * @version 1.0
 */
public class Vector2d implements Serializable{
	private static final long serialVersionUID = -5100001821714692841L;
	final private static double  VALEUR_DEFAUT = 0.0;
	private double x;	//composante x du vecteur 2d
	private double y;	//composante y du vecteur 2d
	/**
	 * Constructeur vide représentant un vecteur 2d à l'origine d'un système d'axe xyz.
	 */
	public Vector2d(){
		x = VALEUR_DEFAUT;
		y = VALEUR_DEFAUT;
	}
	/**
	 * Constructeur avec composante x et z du vecteur 2d.
	 * @param x La composante x du vecteur.
	 * @param y La composante y du vecteur.
	 */
	public Vector2d(double x, double y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Méthode qui donne accès à la coordonnée x du vecteur.
	 * @return La coordonnée x.
	 */
	public double getX(){
		return x; 
	}

	/**
	 * Méthode qui donne accès à la coordonnée y du vecteur.
	 * @return La coordonnée y.
	 */
	public double getY(){ 
		return y; 
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void clear(){
		this.x = VALEUR_DEFAUT;
		this.y = VALEUR_DEFAUT;
	}
	/**
	 * Méthode qui retourne <b> l'addition </b> de deux vecteurs. 
	 * @param v - Le vecteur à ajouter au vecteur présent.
	 * @return La somme des deux vecteurs.
	 */
	public Vector2d add(Vector2d v){	
		return new Vector2d(x + v.getX(), y + v.getY());
	}
	/**
	 * Méthode qui fait <b> l'addidion </b> de deux vecteurs en 2 dimensions.
	 * @param v1 - Le première vecteur à additionner.
	 * @param v2 - Le deuxième vecteur à additionner. 
	 * @return La somme des deux vecteurs.
	 */
	public static Vector2d add(Vector2d v1, Vector2d v2){ 
		return v1.add(v2); 
	}

	/**
	 * Méthode qui retourne la <b> soustraction </b> de deux vecteurs. 
	 * @param v - Le vecteur à soustraire au vecteur présent.
	 * @return La soustraction des deux vecteurs.
	 */
	public Vector2d substract(Vector2d v){
		return new Vector2d(x - v.getX(), y - v.getY());
	}
	/**
	 * Méthode qui fait la <b> soustraction </b> de deux vecteurs en 2 dimensions.
	 * Il est important de respecter l'ordre des vecteurs, car A-B = -(B-A). 
	 * @param v1 - Le première vecteur.
	 * @param v2 - Le deuxième vecteur à soustraire. 
	 * @return La soustraction des deux vecteurs.
	 */
	public static Vector2d substract(Vector2d v1, Vector2d v2){ 
		return v1.substract(v2);
	}
	/**
	 * Méthode qui retourne les valeurs absolues d'un vector.
	 * @param v un vecteur
	 * @return Le résultat absolue de la vector.
	 */
	public static Vector2d absolue(Vector2d v){
		return new Vector2d(Math.abs(v.getX()),Math.abs(v.getY()));
	}
	/**
	 * Méthode qui effectue la <b> multiplication par une scalaire </b> sur un vecteur.
	 * @param m - Le muliplicateur.
	 * @return Le résultat de la multiplication par un scalaire m sur le vecteur.
	 */
	public Vector2d multiply(double m){
		return new Vector2d(m*x, m*y);
	}

	/**
	 * Méthode qui effectue la <b> multiplication par une scalaire </b> m sur un vecteur v. 
	 * @param v - Le vecteur à multiplier par un scalaire.
	 * @param m - Le multiplicateur.
	 * @return Une référence sur l'objet couleur après une modification de multiplication.
	 */
	public static Vector2d multiply(Vector2d v, double m){ return v.multiply(m); }

	/**
	 * Méthode pour obtenir le <b> module </b> d'un vecteur.
	 * @return Le module du vecteur.
	 */
	public double modulus(){
		return Math.sqrt((x*x) + (y*y));
	}
	/**
	 * Méthode pour obtenir <b> le module </b> d'un vecteur.
	 * @return Le module du vecteur.
	 */
	public double module(){ 
		return modulus(); 
	}
	/**
	 * Méthode pour <b> normaliser </b> un vecteur à 2 dimensions. 
	 * Un vecteur normalisé possède la même orientation, mais possède une <b> longeur unitaire </b>.
	 * Si le <b> module du vecteur est nul </b>, le vecteur normalisé sera le <b> vecteur nul </b> (0.0, 0.0).
	 * @return Le vecteur normalisé.
	 */
	public Vector2d normalize(){
		double mod = modulus();			//obtenir le module du vecteur

		if(mod > 0.0)
			return new Vector2d(x/mod, y/mod);
		else
			return new Vector2d(VALEUR_DEFAUT, VALEUR_DEFAUT);
	}
	/**
	 * Méthode pour trouver la normal du vecteur
	 * @param v - vecteur 
	 * @return la valeur normale du vecteur
	 */
	public static double normale(Vector2d v){
		double xDegreDeux = Math.pow(v.getX(), 2);
		double yDegreDeux = Math.pow(v.getY(), 2);
		double a = Math.pow((xDegreDeux + yDegreDeux), 0.5);
		return a;
	}
	/**
	 * Méthode pour effectuer le <b> produit scalaire </b> avec un autre vecteur v.
	 * @param v - L'autre vecteur en produit scalaire.
	 * @return Le produit scalaire entre les deux vecteurs.
	 */

	public double dot(Vector2d v){
		return (x*v.getX() + y*v.getY());
	}
	/**
	 * Méthode pour effectuer le <b> produit scalaire </b> entre un vecteur v1 et un vecteur v2.
	 * @param v1 - Le premier vecteur.
	 * @param v2 - Le deuxième vecteur.
	 * @return Le produit scalaire entre les deux vecteurs.
	 */
	public static double dot(Vector2d v1, Vector2d v2){ 
		return v1.dot(v2);
	}

	/**
	 * Méthode pour effectuer le <b> produit scalaire </b> avec un autre vecteur v.
	 * @param v - L'autre vecteur en produit scalaire.
	 * @return Le produit scalaire entre les deux vecteurs.
	 */
	public double produitScalaire(Vector2d v){ 
		return dot(v);
	}
	/**
	 * Méthode pour effectuer le <b> produit scalaire </b> entre un vecteur v1 et un vecteur v2.
	 * @param v1 - Le premier vecteur.
	 * @param v2 - Le deuxième vecteur.
	 * @return Le produit scalaire entre les deux vecteurs.
	 */
	public static double produitScalaire(Vector2d v1, Vector2d v2){
		return dot(v1, v2);
	}
	/**
	 * Méthode permet de calculer le vecteur unitaire
	 * @return un vecteur unitaire
	 */
	public  Vector2d vecteurUnitaire(){
		double longueur = modulus();
		double posX = 1/longueur * this.x;
		double posY = 1/longueur * this.y;
		return new Vector2d(posX,posY);
	}
	
	public void modifierNormeVecteur(double nouvelleNorme){
		double longueur = modulus();
		x = nouvelleNorme/longueur * this.x;
		y = nouvelleNorme/longueur * this.y;
	}
	/**
	 * Méthode pour écrire les paramètres xy du vecteur dans un fichier en format txt. Le format de l'écriture est "x"  "y" comme l'exemple suivant : 0.6  0.2
	 * @param bw Le buffer d'écriture.
	 * @throws IOException S'il y a une erreur avec le buffer d'écriture.
	 * @see BufferedWriter
	 */
	public void write(BufferedWriter bw)throws IOException{
		bw.write(toString());
	}
	/**
	 * Méthode qui retourne un String énumérant la valeur des composantes du vecteur.
	 * @return Un String du vecteur dans le format [x,y].
	 */
	public String toString(){
		return "[" + x + ", " + y + "]";		
	}
	
	/**
	 * Cette méthode permet de savoir si deux vecteurs sont égaux
	 * @param autreVecteur un autre vecteur
	 * @return  <p><b>true</b> les deux vecteur sont égaux
	 * <p><b>false</b> les deux vecteur ne sont pas égaux
	 */
	public boolean equal(Vector2d autreVecteur){
		return (this.x == autreVecteur.getX() && this.y == autreVecteur.getY());
	}
}
