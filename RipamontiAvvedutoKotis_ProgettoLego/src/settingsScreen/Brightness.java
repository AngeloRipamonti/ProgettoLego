package settingsScreen;

import processing.core.PApplet;

/**
 * Classe che gestisce l'impostazione della luminosità dello schermo
 * @version 1.0
 * @author Angelo Ripamonti, Luca Avveduto, Alexandros Kotis
 */
public class Brightness implements Settings{
    private float standard;
    private int bright;
    private PApplet a;


    /**
     * Costruttore che inizializza gli attributi
     * @param a l'oggetto PApplet che implementa Processing
     * @param s valore della luminosità predefinito
     * @throws IllegalArgumentException se il valore predefinito è superiore a 255 o inferiore a 0
     * @throws NullPointerException se a è nullo
     */
    public Brightness(PApplet a, int s) throws IllegalArgumentException,NullPointerException{
        if (a == null) {
            throw new NullPointerException();
        }
        standard(s);
        bright=(int)standard;
        this.a = a;
    }

    /**
     * Metodo per applicare la luminosità allo schermo
     */
    public void showBrightness (){
        a.fill(0,bright);
        a.rect(0,0,a.width,a.height);
    }

    /**
     * Metodo che incrementa la luminosità
     */
    @Override
    public void more() {
        bright-=15;
    }

    /**
     * Metodo che decrementa la luminosità
     */
    @Override
    public void less() {
        bright+=15;
    }

    /**
     * Metodo che resetta la luminosità
     */
    @Override
    public void reset() {
        bright=(int) standard;
    }

    /**
     * Metodo che imposta la luminosità predefinita
     * @param s il valore predefinito
     * @throws IllegalArgumentException
     */
    @Override
    public void standard(float s) throws IllegalArgumentException{
        if(s > 255 || s < 0){
            throw new IllegalArgumentException();
        }
        standard=s;
    }
}
