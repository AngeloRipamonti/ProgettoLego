import processing.core.*;
import controlP5.*;

import java.util.Vector;

/**
 * Classe MVC che gestisce la visualizzazione del Main
 * @author Ripamonti Angelo, Avveduto Luca, Kotis Alexandros
 * @version 1.0
 */
public class MainView {
    private PApplet a;
    private ControlP5 cp5;
    private Button menu[];
    private final int[] SIZE;
    private Vector<PImage> imgs;
    private Button undo;

    /**
     * Costruttore che inizializza gli attributi
     * @param a Oggetto che permette l'implementazione di Processing
     * @param cp5 Oggetto che gestisce i bottoni
     * @throws NullPointerException In caso i parametri siano nulli
     */
    public MainView(PApplet a, ControlP5 cp5) throws NullPointerException{
        if(a != null && cp5 != null) {
            this.a = a;
            this.cp5 = cp5;
            imgs = new Vector<>(4);
            imgs.add(a.loadImage("SettingsButton.png"));
            imgs.add(a.loadImage("RemoteButton.png"));
            imgs.add(a.loadImage("AutoPilotButton.png"));
            imgs.add(a.loadImage("ExitButton.png"));
            imgs.add(a.loadImage("BackToHome.png"));
            SIZE = new int[2];
            SIZE[0] = 200;
            SIZE[1] = 100;
            menu = new Button[4];
            menu[0] = cp5.addButton("remote").setValue(100).setSize(SIZE[0],SIZE[1]).setPosition(a.width/2-100,a.height/2-300).setImage(imgs.get(1)).hide();
            menu[1] = cp5.addButton("auto").setValue(100).setSize(SIZE[0],SIZE[1]).setPosition(a.width/2-100,a.height/2-150).setImage(imgs.get(2)).hide();
            menu[2] = cp5.addButton("settings").setValue(100).setSize(SIZE[0],SIZE[1]).setPosition(a.width/2-100,a.height/2).setImage(imgs.get(0)).hide();
            menu[3] = cp5.addButton("quit").setValue(100).setSize(SIZE[0],SIZE[1]).setPosition(a.width/2-100,a.height/2+150).setImage(imgs.get(3)).hide();
            undo = cp5.addButton("undo").setValue(100).setSize(SIZE[0],SIZE[1]).setPosition(a.width/2+300,a.height/2+350).setImage(imgs.get(4)).hide();
        }else {
            throw new NullPointerException("");
        }
    }

    /**
     * Metodo che mostra a schermo tutti i bottoni
     */
    public void showButtons() {
        for(Button b : menu){
            b.show();
            isMouseOver(b);
        }
    }

    /**
     * Metodo che nasconde dallo schermo tutti i bottoni
     */
    public void hideButtons() {
        for(Button b : menu){
            b.hide();
        }
    }

    /**
     * Metodo che mostra il bottome che fa tornare alla Home Page
     */
    public void showUndo() {
        undo.show();
        isMouseOver(undo);
    }

    /**
     * Metodo che serve a gestire la callback del bottone nella classe che estende PApplet
     * @param index l'indice del bottone desiderato
     * @return il bottone desiderato
     * @throws IndexOutOfBoundsException in caso l'index sia minore di 0 o maggiore della quantità di bottoni presenti
     */
    public Button getButton(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index > menu.length) {
            throw new IndexOutOfBoundsException();
        }
        return menu[index];
    }

    /**
     * Metodo che serve a gestire la callback del bottone nella classe che estende PApplet
     * @return il bottone che fa tornare alla Home Page
     */
    public Button getUndo() {
        return undo;
    }

    /**
     * Metodo che serve a verificare graficamente se il cursore è sopra ad un bottone
     * @param b il bottone su cui si è sopra
     */
    public void isMouseOver(Button b) {
        float[] pos = b.getPosition();
        if(b.isMouseOver()) {
            a.fill(0,125,125,125);
            a.rect(pos[0]-20,pos[1]-20,SIZE[0]+40,SIZE[1]+40);
        }
    }
}
