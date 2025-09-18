package automaticScreen;

/**
 * Enumerativa utile al direzionamento della macchina in un sistema autonomo
 * @author Ripamonti Angelo, Kotis Alexandros, Avveduto Luca
 * @version 1.0
 */
public enum Directions {
    AHEAD("w",90),
    BACK("s",-90),
    LEFT("a",0),
    RIGHT("d",180);

    private String cmd;
    private int dir;

    /**
     * Costruttore dell'Enumerativa utile a inizializzare gli attributi delle singole etichette
     * @param cmd il carattere della direzione
     * @param dir la direzione angolare
     */
    private Directions(String cmd,int dir) {
        this.cmd = cmd;
        this.dir = dir;
    }

    /**
     * Metodo che fa ottenere il carattere direzionale dell'etichetta
     * @return la direzione in carattere
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * Metodo che fa ottenere l'angolo direzionale dell'etichetta
     * @return la direzione dell'angolo
     */
    public int getDir() {
        return dir;
    }

    /**
     * Metodo che imposta la nuava direzione angolare all'etichetta
     * @param dir il nuovo angolo
     */
    public void setDir(int dir) {
        this.dir = dir;
    }
}


