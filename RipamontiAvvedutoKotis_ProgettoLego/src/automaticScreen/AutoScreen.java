package automaticScreen;
import bluetoothScreen.RemoteScreen;
import processing.core.*;
import processing.serial.Serial;
import Wifi.*;

import java.util.*;
import java.util.Random;

/**
 * Classe che gestisce la schermata di guida autonoma del ProgettoLego
 * @version 1.0
 * @author Angelo Ripamonti, Luca Avveduto, Alexandros Kotis
 */
public class AutoScreen {
    private PApplet a;
    private Object s;
    private boolean entry = false;
    private float previousMillis = 0;
    private int dir = -1;

    private int x = 0,y = 0,pDir = 0;
    private final int START_X,START_Y;

    private LinkedList<Integer[]> pos;

    /**
     * Costruttore che inizializza gli attributi
     * @param a  Oggetto che permette l'implementazione di Processing
     * @param s Oggetto che permette di gestire la seriale tramite Processing
     */
    public AutoScreen(PApplet a,Object s){
        this.s = s;
        this.a=a;
        START_Y = a.height-a.height/3+200;
        START_X = a.width/2-650 + (a.width/2-600/2);
        y = START_Y;
        x = START_X;
        pos = new LinkedList<>();
    }

    /**
     * Metodo che fa iniziare a muoversi da sola la macchinina
     */
    public void loop(){
        drawInterface();
        if(!entry){
            previousMillis = a.millis();
            entry = true;
        }
        if(a.millis() - previousMillis > 3000) {
            if(!((x < a.width/2-650 || x > (a.width/2-650 + a.width/2-200)) || (y < a.height/3-200 || y > (a.height/3-200 + a.height/2-150)))) {
                x = START_X;
                y = START_Y;
                clearPos();
            }
            Integer temp[] = new Integer[2];
            temp[0] = x;
            temp[1] = y;
            pos.add(temp);
            if(s instanceof Serial){
                Serial obj = (Serial) s;
                dir = autoPilot(obj).getDir();
            }
            else if (s instanceof WifiConnection){
                WifiConnection obj = (WifiConnection) s;
                dir = autoPilot(obj).getDir();
            }
            if(dir == -90) {
                y += 10;
                x = pos.getLast()[0];
            }else{
                y -= 10;
                pDir = dir;
                if(dir == 0) {
                    x -= 10;
                }else if(dir == 180) {
                    x += 10;
                }
            }
            entry = false;
        }
        drawPath();
    }

    /**
     * Metodo che gestisce la implementazione grafica della pagina
     * utile alla guida autonoma
     */
    public void drawInterface(){
        //Information
        a.noFill();
        a.stroke(0);
        a.textSize(40);
        a.strokeWeight(4);
        a.rectMode(a.CORNER);
        a.rect(a.width/2-650,a.height/3-200,a.width/2-200,a.height/2-100);
        a.fill(0);
        a.text("Driving automatic\n",a.width/2-630,a.height/3-125);
        a.text("Round/m: " + RemoteScreen.rounds + "\n",a.width/2-630,a.height/3-50);
        a.text("Angle: " + dir + "\n",a.width/2-630,a.height/3+25);
        a.text("Distance from the warning: \n",a.width/2-630,a.height/3+100);

        //Path
        a.noFill();
        a.rect(a.width/2-650,a.height/2+50,a.width/2-200,a.height/3+100);
        a.rect(a.width/2,a.height/3-200,a.width/3+200,a.height/2+150);
    }

    /**
     * Metodo che disegna il percorso della macchinina tramite pallini
     */
    public void drawPath() {
        a.stroke(0);
        a.strokeWeight(8);
        for(int i = 0; i < pos.size(); i++) {
            if(i == pos.size() - 1) {
                a.stroke(255);
            }
            a.point(pos.get(i)[0],pos.get(i)[1]);
        }
        a.stroke(0);
        a.strokeWeight(1);
    }

    /**
     * Metodo che gestisce il direzionamento automatico della macchina e manda il segnale tramite seriale su dove andare
     * @return La direzione angolare della macchina
     */
    public Directions autoPilot(Serial s) {
        Random rand = new Random();
        Directions values[] = new Directions[4];
        values[0] = Directions.AHEAD;
        values[1] = Directions.BACK;
        values[2] = Directions.LEFT;
        values[3] = Directions.RIGHT;
        int dir = rand.nextInt((values.length)-1);
        s.write(values[dir].getCmd());
        return values[dir];
    }

    /**
     * Metodo che gestisce il direzionamento automatico della macchina e manda il segnale tramite wifi su dove andare
     * @return La direzione angolare della macchina
     */
    public Directions autoPilot(WifiConnection s) {
        Random rand = new Random();
        Directions values[] = new Directions[4];
        values[0] = Directions.AHEAD;
        values[1] = Directions.BACK;
        values[2] = Directions.LEFT;
        values[3] = Directions.RIGHT;
        int dir = rand.nextInt((values.length)-1);
        s.write(values[dir].getCmd());
        return values[dir];
    }

    public void clearPos() {
        if(!pos.isEmpty()) pos.clear();
    }
}
