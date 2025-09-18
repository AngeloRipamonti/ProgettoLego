package bluetoothScreen.keyStrokes;

import processing.core.*;

/**
 * Metodo MVC che permette la visualizzazione della KeyStrokes
 * Questa classe fa visualizzare il tasto
 * @version 1.0
 * @author Angelo Ripamonti, Luca Avveduto, Alexandros Kotis
 */
public class KeyStrokesView {
    private PApplet a;

    /**
     * Costruttore che inizializza gli attributi
     * @param a l'oggetto PApplet che permette l'implementazione di Processing
     * @throws NullPointerException se a è nullo
     */
    public KeyStrokesView(PApplet a)throws NullPointerException{
        if(a==null) throw new NullPointerException("");
        this.a=a;
    }

    /**
     * Metodo che permette la visualizzazione del tasto a schermo
     * @param model la classe MVC che permette la modelizzaione della KeyStrokes
     */
    public void key(KeyStrokesModel model){
        a.fill(model.getColor());
        a.rect(model.getX(),model.getY(),model.getLength(),model.getAltitude());
        a.textSize(30);
        a.fill(-1); //Testo di color Bianco
        a.textAlign(a.CENTER,a.CENTER);
        a.text(model.getLetter(),model.getX()+(model.getLength()/2),model.getY()+(model.getAltitude()/2));
        a.textAlign(a.BASELINE,a.BASELINE);
    }

}
