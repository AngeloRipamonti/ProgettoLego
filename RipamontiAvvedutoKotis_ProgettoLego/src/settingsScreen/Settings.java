package settingsScreen;

/**
 * Interfaccia utile per creare una classe che gestisce un impostazione predefinita
 */
public interface Settings {

    /**
     * Metodo che incrementa il valore dell'impostazione
     */
    public void more();

    /**
     * Metodo che decrementa il valore dell'impostazione
     */
    public void less();

    /**
     * Metodo che riporta l'impostazione al valore predefinito
     */
    public void reset();

    /**
     * Metodo che imposta il valore predefinito dell'impostazione
     * @param s il valore predefinito
     */
    public void standard(float s);
}
