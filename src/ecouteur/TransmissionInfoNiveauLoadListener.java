package ecouteur;

import java.util.EventListener;
/**
 * Cette classe permet de transmettre des informations de niveauLoad à panelLoad
 * @author zi zheng wang
 * @version 2.0
 */
public interface TransmissionInfoNiveauLoadListener extends EventListener{
      void getCercleMessage(String message);
      void getBallonMessage(String message);
      void afficheRessortPanel(int a);
      void getPlaqueInfo(String message);
      void getChampsInfo(String message);
      void action();
}
