package settingsScreen;
import controlP5.Button;
import controlP5.ControlP5;
import ddf.minim.*;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.util.Vector;

/**
 * Classe che gestisce la schermata delle Impostazioni nel ProgettoLego
 */
public class SettingsScreen {
    private PApplet a;
    private Brightness bright;
    private Volume vol;
    private ControlP5 cp5;
    private Button[] button;

    private Vector<PImage> imgs;

    private final int SIZE;

    /**
     * Costruttore che inizializza gli attributi
     * @param a oggetto PApplet per l'implementazione di Processing
     * @param out oggetto AudioPlayer per la gestione dell'audio
     * @param cp5 oggetto ControlP5 per la gestione dei bottoni
     * @throws NullPointerException In caso un parametro sia nullo
     * @throws IllegalArgumentException Nel caso un dato non sia valido
     */
    public SettingsScreen(PApplet a, AudioPlayer out, ControlP5 cp5) throws NullPointerException, IllegalArgumentException{
        if(a == null || cp5==null || out == null) {
            throw new NullPointerException("valori non validi");
        }
        //Inizializzazione oggetti librerie
        this.a=a;
        this.cp5=cp5;
        SIZE = 100;
        //Inizializzazione delle classi di supporto
        bright=new Brightness(a,50);
        vol=new Volume(out,0.5f);
        //Inizializzazione dei bottoni per la gestione della schermata
        button = new Button[5];
        float x= (float) a.width / 3;
        float y = (float) a.height / 2;
        imgs = new Vector<>(5);
        imgs.add(a.loadImage("BackToHome.png"));
        imgs.get(0).resize(300,120);
        imgs.add(a.loadImage("IncreaseBr.png"));
        imgs.add(a.loadImage("DecreaseBr.png"));
        imgs.add(a.loadImage("IncreaseVol.png"));
        imgs.add(a.loadImage("DecreaseVol.png"));
        button[0] = cp5.addButton("increaseBright").setValue(100).setSize(SIZE,SIZE).setPosition(x,y+200).setImage(imgs.get(1));
        button[1] = cp5.addButton("decreaseBright").setValue(100).setSize(SIZE,SIZE).setPosition(x+400,y+200).setImage(imgs.get(2));
        button[2] = cp5.addButton("increaseVolume").setValue(100).setSize(SIZE,SIZE).setPosition(x,y).setImage(imgs.get(3));
        button[3] = cp5.addButton("decreaseVolume").setValue(100).setSize(SIZE,SIZE).setPosition(x+400,y).setImage(imgs.get(4));
        button[4] = cp5.addButton("backHome").setValue(100).setSize(300,120).setPosition(x+100,y-225).setImage(imgs.get(0));
        hideAllButton();
    }

    /**
     * Metodo che mostra la luminosità
     */
    public void loopBrightness(){
        bright.showBrightness();
    }

    /**
     * Metodo che gestisce la schermata delle impostazioni
     */
    public void loop(){
        a.fill(255);
        a.textSize(40);
        a.text("SETTINGS",a.width/2-75,a.height/3-100);
        showAllButton();
        a.fill(255);
        a.text("Set the brightness",getButton(0).getPosition()[0],getButton(0).getPosition()[1]-50);
        a.text("Set the Volume",getButton(2).getPosition()[0],getButton(2).getPosition()[1]-50);
    }

    /**
     * Metodo che cambia la luminosità dello schermo
     * @param brightness 0 se si vuole aumentare, 1 se si vuole diminuire
     */
    public void changeBrightness(byte brightness){
        //showAllButton();
        if(brightness == 0){
            bright.more();
        }
        else if (brightness == 1){
            bright.less();
        }
    }

    /**
     * Metodo che cambia il volume dell'applicazione
     * @param volume 0 se si vuole aumentare, 1 se si vuole diminuire
     */
    public void changeVolume(byte volume){
        //showAllButton();
        if(volume == 0){
            vol.more();
        }
        else if (volume == 1){
            vol.less();
        }
    }

    /**
     * Nasconde tutti i bottoni
     */
    public void hideAllButton(){
        for(Button x : button){
            x.hide();
        }
    }

    /**
     * Mostra tutti i bottoni
     */
    public void showAllButton(){
        for(Button x : button){
            x.show();
            isMouseOver(x);
        }
    }

    /**
     * Restituisce il bottone per implementare la CallBack nella classe che estende PApplet
     * @param index l'indice del bottone a cui si vuole accedere
     * @return il bottone
     */
    public Button getButton(int index){
        return button[index];
    }

    public void isMouseOver(Button b) {
        float[] pos = b.getPosition();
        if(b.isMouseOver() && !b.equals(button[4])) {
            a.fill(0,125,125,125);
            a.rect(pos[0]-20,pos[1]-20,SIZE+40,SIZE+40);
        }else if(b.isMouseOver() && b.equals(button[4])) {
            a.fill(0,125,125,125);
            a.rect(pos[0]-20,pos[1]-20,340,160);
        }
    }

}
