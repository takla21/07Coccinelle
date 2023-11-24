
package geometrie;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
/**
 * 
 * Classe de géometrie qui permet de dessiner l'objet boussole
 * 
 * @author Zi Zheng Wang
 * @version 1.0
 */
public class Boussole {
	private final double HAUTEUR_HOUSSOLE = 2;
	private final double lONGEUR_BOUSSOLE = 4;
    private double posX;
    private double posY;
	private Path2D.Double coteSud;
	private Path2D.Double coteNord;
	private Shape sud, nord;
	private Color rouge = new Color(255,0,0,150);
	private Color grise = new Color(192,192,192,150);
	/**
	 * Constructeur pour créer l'objet boussole
	 * @param d - le centre de l'objet boussole en coordonnée x 
	 * @param e - le centre de l'objet boussole en coordonnée y
	 */
    public Boussole(double d, double e){
    	this.posX = d;
    	this.posY = e;
    	creerBoussole();
    }
    /**
     * Méthode sert à créer les composants graphics de l'objet boussole
     */
	private void creerBoussole() {
		coteSud = new Path2D.Double();
		coteSud.moveTo(posX, posY);
		coteSud.lineTo(posX, posY - HAUTEUR_HOUSSOLE/2);
		coteSud.lineTo(posX + lONGEUR_BOUSSOLE/2 , posY);
		coteSud.lineTo(posX, posY + HAUTEUR_HOUSSOLE/2);
		coteSud.closePath();
		coteNord = new Path2D.Double();
		coteNord.moveTo(posX, posY);
		coteNord.lineTo(posX, posY - HAUTEUR_HOUSSOLE/2);
		coteNord.lineTo(posX - lONGEUR_BOUSSOLE/2 , posY);
		coteNord.lineTo(posX, posY + HAUTEUR_HOUSSOLE/2);
		coteNord.closePath();
	}
	/**
	 * Méthode sert à dessiner l'objet boussole
	 * @param g2d - Graphics2d par défaut
	 * @param aff - la matrice monde réel
	 * @param rotation - l'angle de rotation
	 */
	public void dessiner(Graphics2D g2d, AffineTransform aff, double rotation){
		AffineTransform matt = new AffineTransform(aff);
		matt.rotate(rotation,posX,posY);
		sud = matt.createTransformedShape(coteSud);
		nord = matt.createTransformedShape(coteNord);
		g2d.setColor(grise);
		g2d.fill(nord);
		g2d.setColor(rouge);
		g2d.fill(sud);
	}
}
