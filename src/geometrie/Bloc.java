
package geometrie;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.net.URL;

/**
 * Cette classe permet de dessiner les bloc qui emprisoneront les particules et la foss�
 * @author Kevin Takla
 * @version 1.0
 * 
 */
public class Bloc{
	private Shape blocRetourner;
	private Rectangle2D.Double bloc, blocVide;
	private double largeurVide;
	private double largeurBloc;
	private Area aireBloc , aireBlocVide, aireTotal;
	private double posX, posY;
	private boolean dessinable = true;
	private AudioClip son;
	private boolean premierMusic;
	private int repMusic;

	
	/**
	 * C'est le constructeur de la classe bloc.
	 * @param posX la position intiale du bloc en x.
	 * @param posY la postion initale du bloc en y.
	 * @param diametre le diam�tre de l'objet enprisonner dans le bloc.
	 */
	public Bloc(double posX, double posY,double diametre){
		this.posX = posX - diametre/4;
		this.posY = posY - diametre/4;
		double diametreTemp = diametre + 2;
		largeurVide = diametreTemp;
		largeurBloc = diametreTemp *1.5;
		premierMusic = true;
		repMusic = 0;
		creerBloc();
		importerMusic();
	}
	/**
	 * Cette m�thode permet d'aller chercher la musique qui sera enclencher lorsque le laser aura pass� par le bloc
	 */
	private void importerMusic(){
		URL urlFichier  = getClass().getClassLoader().getResource("mirroirbrise.wav");
		son = Applet.newAudioClip(urlFichier);
	}
	/**
	 * Cette m�thode permet de cr�er le bloc.
	 */
	private void creerBloc(){
		bloc = new Rectangle2D.Double(posX - largeurVide/4 ,posY - largeurVide/4 ,largeurBloc, largeurBloc);
		blocVide = new Rectangle2D.Double(posX,posY,largeurVide, largeurVide);
		aireBloc = new Area(bloc);
		aireTotal = new Area(bloc);
		aireBlocVide = new Area(blocVide);
		aireBloc.subtract(aireBlocVide);
	}
	/**
	 * Cette m�thode permet de dessiner le bloc.
	 * @param g2d le contexte graphique.
	 * @param mat la matrice monde r�ele.
	 */
	public void dessiner(Graphics2D g2d, AffineTransform mat){
		if(dessinable){
		repMusic = 0;
		AffineTransform matLocal = new AffineTransform(mat);
		Color couleurCourante = g2d.getColor();
		blocRetourner = matLocal.createTransformedShape(aireBloc);
		g2d.fill(blocRetourner);
		g2d.setColor(couleurCourante);
		}else{
			if(premierMusic){
				son.play();
				premierMusic = false;
				repMusic ++;
			}
		}
	}
	/**
	 * Cette m�thode permet de cr�er une aire � partir de la forme g�ometrique du bloc.
	 * @return l'aire du bloc
	 */
	public Area aire(){
		return aireTotal;
	}
	/**
	 * Cette m�thode permet de faire appara�tre ou dispara�tre le bloc.
	 * @param valeurVerite l'�tat du bloc que l'utilisateur souhaite avoir: <p><b>true</b> le bloc est visible.
	 * <p><b>false</b> le bloc n'est pas visible.
	 */ 
	public void debloquerBloc(boolean valeurVerite){
		dessinable = valeurVerite;
		if(repMusic == 0){
			premierMusic = true;
		}
		
	}
	/**
	 * Cette m�thode retourne l'�tat du bloc
	 * @return la valeur de v�rit� du bloc: <p><b>true</b> le bloc est visible.
	 * <p><b>false</b> le bloc n'est pas visible.
	 */
	public boolean getDessiable(){
		return dessinable;
	}
	/**
	 * Cette m�thode permet de voir si un point quelqcone est dans le bloc.
	 * @param posX la positon en x du point en question.
	 * @param posY la positon en y du point en question.
	 * @return la valeur de v�rit� de la m�thode: <p><b>true</b> le point est dans le bloc.
	 * <p><b>false</b> le point n'est pas dans le bloc.
	 */
	public boolean contient(double posX, double posY){
		if(aireTotal.contains(posX,posY)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Cette m�thode permet d'obtenir la forme g�om�trique du bloc.
	 * @return la forme g�om�trique du bloc.
	 */
	public Shape getShape(){
		return blocRetourner;
	}
	
	
}
