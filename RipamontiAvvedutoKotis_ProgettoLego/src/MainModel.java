import Wifi.WifiConnection;
import automaticScreen.AutoScreen;
import bluetoothScreen.RemoteScreen;
import controlP5.ControlP5;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PImage;
import processing.serial.Serial;
import settingsScreen.SettingsScreen;

import java.util.Arrays;

/**
 * Classe MVC che gestisce il Model del Main
 * @author Ripamonti Angelo, Avveduto Luca, Kotis Alexandros
 * @version 1.0
 */
public class MainModel {
    private PApplet a;
    public Object tx;
    public SettingsScreen settingsScreen;
    public RemoteScreen bluetoothScreen;
    public AutoScreen automaticScreen;
    private boolean [] screens;
    private Minim m;
    private AudioPlayer song;
    private PImage sfondo;
    private PImage remotebg;
    public ControlP5 cp5;

    public MainModel(PApplet a)throws IllegalArgumentException{
        if(a==null){
            throw new IllegalArgumentException("Implementazione di Processing fallita!");
        }
        this.a=a;
        //Seriale / WiFi

        tx = new Serial(this.a, "COM4", 9600);
        //tx = new WifiConnection(this.a);

        //ControlP5
        cp5=new ControlP5(this.a);
        //Audio
        m = new Minim(this.a);
        song = m.loadFile("data/canzone.mp3");
        song.loop();
        //Schermate
        bluetoothScreen=new RemoteScreen(this.a,tx);
        automaticScreen=new AutoScreen(this.a,tx);
        settingsScreen=new SettingsScreen(this.a,song,cp5);
        //Gestione Schermate
        screens=new boolean[3];
        Arrays.fill(screens, false);
        screens[0]=true;
        //Background
        sfondo = this.a.loadImage("Sfondo.png");
        sfondo.resize(this.a.width,this.a.height);
        remotebg = this.a.loadImage("Background.png");
        remotebg.resize(this.a.width,this.a.height);
    }

    public boolean getScreen(int index) throws IndexOutOfBoundsException {
        return screens[index];
    }

    public PImage getSfondo() {
        return sfondo;
    }

    public PImage getRemotebg() {
        return remotebg;
    }

    public void setScreen(int index, boolean screen) throws IndexOutOfBoundsException {
        screens[index] = screen;
    }

}
