package bluetoothScreen.keyStrokes;

import processing.core.*;

/**
 * Classe MVC che controlla la KeyStrokes
 * Una classe per controllare la KeyStrokes ispiratasi alla KeyStrokesMod questa serve per far apparire a schermo un tasto della tastiera a schermo
 * @version 1.0
 * @author Angelo Ripamonti, Luca Avveduto, Alexandros Kotis
 */
public class KeyStrokesController {
    public KeyStrokesView view;
    public KeyStrokesModel model;

    /**
     * Costruttore per creare un tasto della KeyStrokes
     * @param a oggetto PApplet per l'implementazione di Processing
     * @param x coordinata x del tasto
     * @param y coordinata y del tasto
     * @param color colore predefinito del tasto
     * @param size lunghezza del tasto
     * @param altitude altezza del tasto
     * @param letter lettera del tasto
     * @throws NullPointerException in caso gli attributi siano nulli
     * @throws IllegalArgumentException in caso gli attributi abbiano un valore non valido
     */
    public KeyStrokesController(PApplet a, float x, float y, int color, int size, int altitude, String letter) throws NullPointerException, IllegalArgumentException {
        view=new KeyStrokesView(a);
        model=new KeyStrokesModel(a,x,y,color,size,altitude, letter);
    }

    /**
     * Mostra il tasto della KeyStrokes a schermo
     */
    public void show(){
        view.key(model);
    }


}
