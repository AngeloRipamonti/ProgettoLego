package Wifi;

import processing.core.PApplet;
import websockets.*;

/**
 * Classe per la comunicazione tramite WiFi
 */
public class WifiConnection {
    private WebsocketServer ws;
    private int now;
    private PApplet a;

    /**
     * @param a Oggetto PApplet
     * Costruttore
     */
    public WifiConnection(PApplet a) {
        this.a = a;
        ws = new WebsocketServer(a, 8025, "");
        now = a.millis();
    }


    /**
     * Metodo per mandare messaggi
     */
    public void write(Object c){
        if(a.millis()>now+5000){
            ws.sendMessage(c.toString());
            now=a.millis();
        }
    }

    /**
     * @param msg messaggio dal client
     * Metodo per la ricezione
     */
    public void webSocketServerEvent(String msg){
        System.out.println(msg);
    }
}