import Wifi.WifiConnection;
import processing.core.*;
import controlP5.*;
import processing.serial.Serial;


/**
 * Classe MVC che gestisce il controllo del Main
 * @author Ripamonti Angelo, Avveduto Luca, Kotis Alexandros
 * @version 1.0
 */
public class MainController extends PApplet {
    private MainModel model;
    private MainView view;
    private boolean finished;


    /**
     * Metodo che gestisce la grandezza della finestra
     */
    public void settings(){
        //size(800,800);
        fullScreen();
    }

    /**
     * Metodo che inizializza tutti gli attributi e variabili utili
     */
    public void setup(){
        finished=false;
        model = new MainModel(this);
        view = new MainView(this,model.cp5);
        finished=true;
    }

    /**
     * Metodo che gestisce il programma tramite un loop infinito
     */
    public void draw(){
        background(model.getSfondo());
        model.settingsScreen.loopBrightness();
        if(model.getScreen(0)){
            //menu
            view.showButtons();
        }
        else if(model.getScreen(1)){
            //Settings
            model.settingsScreen.loop();
        }
        else if(model.getScreen(2)){
            //Bluetooth Screen
            background(model.getRemotebg());
            model.settingsScreen.loopBrightness();
            model.bluetoothScreen.loop();
            view.showUndo();
        }
        else{
            //Automatic Screen
            background(model.getRemotebg());
            model.settingsScreen.loopBrightness();
            model.automaticScreen.loop();
            view.showUndo();
        }
    }


    /**
     * Metodo che gestisce la callback del bottone che incrementa la luminosità dello schermo
     * @param e l'evento lanciato dal bottone
     */
    public void increaseBright(ControlEvent e) {
        if(finished && e.isFrom(model.settingsScreen.getButton(0))){
            model.settingsScreen.changeBrightness((byte)0);
        }
    }
    /**
     * Metodo che gestisce la callback del bottone che incrementa il volume dell'applicazione
     * @param e l'evento lanciato dal bottone
     */
    public void increaseVolume(ControlEvent e) {
        if(finished && e.isFrom(model.settingsScreen.getButton(2))){
            model.settingsScreen.changeVolume((byte)0);
        }
    }
    /**
     * Metodo che gestisce la callback del bottone che decrementa la luminosità dello schermo
     * @param e l'evento lanciato dal bottone
     */
    public void decreaseBright(ControlEvent e){
        if(finished && e.isFrom(model.settingsScreen.getButton(1))){
            model.settingsScreen.changeBrightness((byte)1);
        }
    }
    /**
     * Metodo che gestisce la callback del bottone che decrementa il volume dell'applicazione
     * @param e l'evento lanciato dal bottone
     */
    public void decreaseVolume(ControlEvent e){
        if(finished && e.isFrom(model.settingsScreen.getButton(3))){
            model.settingsScreen.changeVolume((byte)1);
        }
    }
    /**
     * Metodo che gestisce la callback del bottone che fa tornare alla Home Page dalla schermata delle impostazioni
     * @param e l'evento lanciato dal bottone
     */
    public void backHome(ControlEvent e){
        if(finished && e.isFrom(model.settingsScreen.getButton(4))){
            model.settingsScreen.hideAllButton();
            view.showButtons();
            view.getUndo().hide();
            model.setScreen(1,false);
            model.setScreen(0,true);
        }
    }

    /**
     * Metodo che gestisce la callback del bottone che fa andare alla schermata del controllo remoto
     * @param e l'evento lanciato dal bottone
     */
    public void remote(ControlEvent e) {
        if(finished && e.isFrom(view.getButton(0))){
            model.settingsScreen.hideAllButton();
            view.hideButtons();
            model.setScreen(0,false);
            model.setScreen(2,true);
        }
    }

    /**
     * Metodo che gestisce la callback del bottone che fa andare alla schermata del controllo autonomo
     * @param e l'evento lanciato dal bottone
     */
    public void auto(ControlEvent e) {
        if(finished && e.isFrom(view.getButton(1))){
            model.settingsScreen.hideAllButton();
            view.hideButtons();
            model.setScreen(0,false);
        }
    }

    /**
     * Metodo che gestisce la callback del bottone che fa andare alla schermata delle impostazioni
     * @param e l'evento lanciato dal bottone
     */
    public void settings(ControlEvent e) {
        if(finished && e.isFrom(view.getButton(2))){
            view.hideButtons();
            view.getUndo().hide();
            model.settingsScreen.showAllButton();
            model.setScreen(0,false);
            model.setScreen(1,true);
        }
    }

    /**
     * Metodo che gestisce la callback del bottone che fa tornare alla Home Page
     * @param e l'evento lanciato dal bottone
     */
    public void undo(ControlEvent e){
        if(finished && e.isFrom(view.getUndo())){
            model.setScreen(0, true);
            model.setScreen(1, false);
            model.setScreen(2, false);
            if(model.tx instanceof Serial){
                Serial s = (Serial) model.tx;
                s.write(" ");
            }
            else if(model.tx instanceof WifiConnection){
                WifiConnection s = (WifiConnection) model.tx;
                s.write(" ");
            }
            view.getUndo().hide();
            model.automaticScreen.clearPos();
        }
    }

    /**
     * Metodo che gestisce la callback del bottone che fa uscire dall'applicazione
     * @param e l'evento lanciato dal bottone
     */
    public void quit(ControlEvent e) {
        if(finished && e.isFrom(view.getButton(3))){
            System.exit(0);
        }
    }

    /**
     * Metodo che gestisce la callback del WebSocket per la ricezione di un messaggio
     * @param msg il messaggio da ricevere
     */
    public void webSocketServerEvent(String msg){
        if(model.tx instanceof WifiConnection) {
            WifiConnection wifi = (WifiConnection) model.tx;
            wifi.webSocketServerEvent(msg);
        }
    }

    /**
     * Metodo che avvia il programma
     * @param passedArgs argomenti extra passati da console
     */
    public static void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "MainController" };
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
