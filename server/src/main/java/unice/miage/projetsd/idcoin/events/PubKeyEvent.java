package unice.miage.projetsd.idcoin.events;

public class PubKeyEvent {
    /**
     * @JSONProp username
     */
    private String key;
    /**
     * @JSONProp item
     */
    private String emitter;

    public String getKey() {
        return key;
    }

    public String getEmitter() {
        return emitter;
    }
}
