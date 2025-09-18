package bluetoothScreen;

import Wifi.WifiConnection;
import processing.core.*;
import bluetoothScreen.keyStrokes.*;
import processing.serial.*;

import java.util.Arrays;

import static processing.core.PConstants.*;

/**
 * Classe che gestisce la prima schermata del ProgettoLego
 * @version 1.0
 * @author Angelo Ripamonti, Luca Avveduto, Alexandros Kotis
 */
public class RemoteScreen {
    private PApplet a;
    private Object s;
    private KeyStrokesController [] keyStrokes;
    private final int BLACK=-16777216;
    private final int BLUE=-11831362;
    public static int rounds = 0;
    private int temp = 0;
    /**
     * Costruttore che inizializza gli attributi
     * e crea una KeyStrokes Mod
     * @param a l'oggetto PApplet che permette l'implementazione di Processing
     * @param s l'oggetto Serial che permette l'utilizzo della porta seriale
     */
    public RemoteScreen(PApplet a, Object s){
        keyStrokes = new KeyStrokesController[7];
        this.a=a;
        this.s=s;
        int size, altitude;
        float x, y;
        size=300;
        altitude=100;
        x=(float) (this.a.width-(float)this.a.width/1.1) -20;
        y=this.a.height- 20 - altitude;
        //Andare Avanti
        keyStrokes[0]=new KeyStrokesController(this.a, x+(size/3), y-(altitude*3), BLACK, size/3,altitude,"W");
        //Andare a Sinistra
        keyStrokes[1]=new KeyStrokesController(this.a, x, y-(altitude*2), BLACK, size/3,altitude,"A");
        //Andare in retromarcia
        keyStrokes[2]=new KeyStrokesController(this.a, x+(size/3), y-(altitude*2), BLACK, size/3,altitude,"S");
        //Andare a destra
        keyStrokes[3]=new KeyStrokesController(this.a, x+((size/3)*2), y-(altitude*2), BLACK, size/3,altitude,"D");
        //Accellerare
        keyStrokes[4]=new KeyStrokesController(this.a, x, y-altitude, BLACK, size/2,altitude,"CTRL");
        //Decellerare
        keyStrokes[5]=new KeyStrokesController(this.a, x+(size/2), y-altitude, BLACK, size/2,altitude,"SHIFT");
        //Frenare (Freno a Mano)
        keyStrokes[6]=new KeyStrokesController(this.a, x, y, BLACK, size,altitude,"SPACE");
    }

    /**
     * Metodo che fa visualizzare la prima è schermata
     * Con in alto a Sinistra tutte le informazioni
     * In basso a Sinistra la KeyStrokesMod
     * ed nel restante spazio a destra un modello di macchina con le frecce indicative
     */
    public void loop(){
        //Dati in alto a sx
        keyStrokesMod();
        a.noFill();
        a.strokeWeight(4);
        a.rect(a.width/2-650,a.height/3-200,a.width/2-200,a.height/2-150);
        a.fill(0);
        a.text("Remote Control Car\n",a.width/2-630,a.height/3-125);
        a.text("Round/m: " + temp + "\n",a.width/2-630,a.height/3-50);
        a.text("Distance from the warning: \n",a.width/2-630,a.height/3+25);
        a.noFill();
        a.rect(a.width/2,a.height/3-200,a.width/3+200,a.height/2);
        a.strokeWeight(1);
        //webcam
    }


    /**
     * Analizza se bisogna mandare un segnale WiFi o Seriale
     * e si usa l'apposita funzione
     */
    public void keyStrokesMod(){
        if(s instanceof Serial){
            Serial obj = (Serial) s;
            keyStrokesFunction(obj);
        }
        else if (s instanceof WifiConnection){
            WifiConnection obj = (WifiConnection) s;
            keyStrokesFunction(obj);
        }
    }

    /**
     * Metodo che attiva la KeyStrokes
     * Se nessun tasto è premuto allora fa visualizzare i tasti di color Nero
     * altrimenti se si preme uno dei tasti presente esso si colora di blu e viene mandato un segnale alla porta seriale
     */
    public void keyStrokesFunction(Serial s){
        for(int i=0;i<keyStrokes.length;i++){
            keyStrokes[i].show();
        }
        if(a.keyPressed) {
            switch(a.keyCode) {
                case CONTROL:
                    s.write(a.keyCode); //Scrive 17
                    keyStrokes[4].model.setColor(BLUE);
                    rounds = 90;
                    temp = 90;
                    break;
                case SHIFT:
                    s.write(a.keyCode); //Scrive 16
                    keyStrokes[5].model.setColor(BLUE);
                    rounds = 30;
                    temp = 30;
                    break;
                default:
                    switch (a.key) {
                        case 'w':
                        case 'W':
                            s.write(a.key); //Scrive w o W
                            System.out.println(a.key);
                            keyStrokes[0].model.setColor(BLUE);
                            break;
                        case 'a':
                        case 'A':
                            s.write(a.key); //Scrive a o A
                            keyStrokes[1].model.setColor(BLUE);
                            break;
                        case 's':
                        case 'S':
                            s.write(a.key); //Scrive s o S
                            keyStrokes[2].model.setColor(BLUE);
                            break;
                        case 'd':
                        case 'D':
                            s.write(a.key); //Scrive d o D
                            keyStrokes[3].model.setColor(BLUE);
                            break;
                        case ' ':
                            s.write(a.key); //Scrive ' '
                            keyStrokes[6].model.setColor(BLUE);
                            temp = 0;
                            break;
                        case 'c':
                        case 'C':
                            //Serve per suonare il clacson
                            s.write(a.key); // Scrive c o C
                            keyStrokesReset();
                            break;
                        default:
                            s.write(' ');
                            keyStrokesReset();
                    }
            }
        }
        else{
            keyStrokesReset();
        }
    }

    /**
     * Metodo che attiva la KeyStrokes
     * Se nessun tasto è premuto allora fa visualizzare i tasti di color Nero
     * altrimenti se si preme uno dei tasti presente esso si colora di blu e viene mandato un segnale tramite WiFi
     */
    public void keyStrokesFunction(WifiConnection s){
        for(int i=0;i<keyStrokes.length;i++){
            keyStrokes[i].show();
        }
        if(a.keyPressed) {
            switch(a.keyCode) {
                case CONTROL:
                    s.write(a.keyCode); //Scrive 17
                    keyStrokes[4].model.setColor(BLUE);
                    rounds = 90;
                    temp = 90;
                    break;
                case SHIFT:
                    s.write(a.keyCode); //Scrive 16
                    keyStrokes[5].model.setColor(BLUE);
                    rounds = 30;
                    temp = 30;
                    break;
                default:
                    switch (a.key) {
                        case 'w':
                        case 'W':
                            s.write(a.key); //Scrive w o W
                            System.out.println(a.key);
                            keyStrokes[0].model.setColor(BLUE);
                            break;
                        case 'a':
                        case 'A':
                            s.write(a.key); //Scrive a o A
                            keyStrokes[1].model.setColor(BLUE);
                            break;
                        case 's':
                        case 'S':
                            s.write(a.key); //Scrive s o S
                            keyStrokes[2].model.setColor(BLUE);
                            break;
                        case 'd':
                        case 'D':
                            s.write(a.key); //Scrive d o D
                            keyStrokes[3].model.setColor(BLUE);
                            break;
                        case ' ':
                            s.write(a.key); //Scrive ' '
                            keyStrokes[6].model.setColor(BLUE);
                            temp = 0;
                            break;
                        case 'c':
                        case 'C':
                            //Serve per suonare il clacson
                            s.write(a.key); // Scrive c o C
                            keyStrokesReset();
                            break;
                        default:
                            s.write(' ');
                            keyStrokesReset();
                    }
            }
        }
        else{
            keyStrokesReset();
        }
    }

    /**
     * Metodo che riporta i colori dei tasti della KeyStrokes a nero
     */
    public void keyStrokesReset(){
        for(int i=0;i<keyStrokes.length;i++){
            keyStrokes[i].model.setColor(BLACK);
        }
    }
}
