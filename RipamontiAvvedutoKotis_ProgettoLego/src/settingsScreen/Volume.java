package settingsScreen;
import ddf.minim.*;

/**
 * Classe che gestisce l'impostazione del volume dell'applicazione
 * @version 1.0
 * @author Angelo Ripamonti, Luca Avveduto, Alexandros Kotis
 */
public class Volume implements Settings{
    private float standard;
    private float vol;
    private AudioPlayer output;


    /**
     * Costruttore che inizializza gli attributi
     * @param output l'oggetto che controlla l'audio dell'applicazione
     * @param s il valore predefinito del volume
     * @param output variabile con volume da cambiare
     * @throws IllegalArgumentException se s è maggiore di 1 o minore di 0
     * @throws NullPointerException se output è null
     */
    public Volume(AudioPlayer output, float s) throws IllegalArgumentException,NullPointerException{
        standard(s);
        vol=standard;
        if(output != null) {
            this.output = output;
        }else {
            throw new NullPointerException();
        }
    }

    /**
     * Metodo che incrementa il volume
     */
    @Override
    public void more() {
        if(vol <= 0) {
            vol += 10;
            output.setGain(vol);
        }else {
            vol += 2;
            output.setGain(vol);
        }
    }

    /**
     * Metodo che decrementa il volume
     */
    @Override
    public void less() {
        if(vol > -39.5) {
            vol -= 10;
            output.setGain(vol);
        }
    }

    /**
     * Metodo che resetta il volume al valore predefinito
     */
    @Override
    public void reset() {
        vol=standard;
    }

    /**
     * Metodo che imposta il volume predefinito
     * @param s il valore predefinito
     * @throws IllegalArgumentException se s non è compreso nel range 1 - 0
     */
    @Override
    public void standard(float s) throws IllegalArgumentException{
        if(s>1 || s<0){
            throw new IllegalArgumentException();
        }
        standard=s;
    }
}
