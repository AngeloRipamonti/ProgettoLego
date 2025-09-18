package bluetoothScreen.keyStrokes;

import processing.core.*;

/**
 * Classe MVC che permette la modelizzazione della KeyStrokes
 * Questa classe gestisce tutti gli attributi utili alla KeyStrokes
 * @version 1.0
 * @author Angelo Ripamonti, Luca Avveduto, Alexandros Kotis
 */
public class KeyStrokesModel {
    private PApplet a;
    private float x;
    private float y;
    private int color;
    private int length;
    private int altitude;
    private String letter;

    /**
     * Costruttore per la creazione degl'attributi della KeyStrokes
     * @param a oggetto PApplet per l'implementazione di Processing
     * @param x coordinata x del tasto
     * @param y coordinata y del tasto
     * @param color colore predefinito del tasto
     * @param length lunghezza del tasto
     * @param altitude altezza del tasto
     * @param letter lettera del tasto
     * @throws IllegalArgumentException se i dati non sono validi
     * @throws NullPointerException se l'oggetto PApplet è nullo
     */
    public KeyStrokesModel(PApplet a, float x, float y, int color, int length, int altitude, String letter) throws IllegalArgumentException, NullPointerException{
        if(a==null){
            throw new NullPointerException("");
        }
        this.a=a;
        if((x>this.a.width || x < 0 || y > this.a.height || y < 0 || length > this.a.width || length < 10 || altitude > this.a.height || altitude < 10)){
            throw new IllegalArgumentException("");
        }
        this.x = x;
        this.y = y;
        this.color = color;
        this.length = length;
        this.altitude=altitude;
        this.letter=letter;
    }

    /**
     * Metodo getter che permette l'ottenimento dell'altezza del tasto
     * @return l'altezza del tasto
     */
    public int getAltitude(){
        return altitude;
    }

    /**
     * Metodo setter che permette di impostare l'altezza del tasto
     * @param altitude altezza del tasto
     * @throws IllegalArgumentException se altitude non è valido
     */
    public void setAltitude(int altitude)throws IllegalArgumentException{
        if(altitude > this.a.height || altitude < 10){
            throw new IllegalArgumentException("");
        }
        this.altitude=altitude;
    }

    /**
     * Metodo getter che permette l'ottenimento della coordinata x del tasto
     * @return la coordinata x del tasto
     */
    public float getX() {
        return x;
    }

    /**
     * Metodo setter che permette di impostare la coordinata x del tasto
     * @param x la coordinata x del tasto
     * @throws IllegalArgumentException se x non è valido
     */
    public void setX(float x)throws IllegalArgumentException {
        if((x>a.width || x < 0)){
            throw new IllegalArgumentException("");
        }
        this.x = x;
    }

    /**
     * Metodo getter che permette l'ottenimento della coordinata y del tasto
     * @return la coordinata y del tasto
     */
    public float getY() {
        return y;
    }

    /**
     * Metodo setter che permette di impostare la coordinata y del tasto
     * @param y coordinata y del tasto
     * @throws IllegalArgumentException se la y non è valida
     */
    public void setY(float y)throws IllegalArgumentException {
        if((y > a.height || y< 0)){
            throw new IllegalArgumentException("");
        }
        this.y = y;
    }

    /**
     * Metodo getter che permette l'ottenimento del colore del tasto
     * @return il colore del tasto
     */
    public int getColor() {
        return color;
    }

    /**
     * Metodo setter che permette di impostare il colore del tasto
     * @param color il colore del tasto
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Metodo getter che permette l'ottenimento della lunghezza del tasto
     * @return la lunghezza del tasto
     */
    public int getLength() {
        return length;
    }

    /**
     * Metodo setter che permette di impostare la lunghezza del tasto
     * @param length la lunghezza del tasto
     * @throws IllegalArgumentException se la lunghezza non è valida
     */
    public void setLength(int length) throws IllegalArgumentException{
        if((length > a.width || length < 10)){
            throw new IllegalArgumentException("");
        }
        this.length = length;
    }

    /**
     * Metodo getter che permette l'ottenimento della lettera del tasto
     * @return la lettera del tasto
     */
    public String getLetter(){
        return letter;
    }
}
