package unice.miage.projetsd.idcoin.events;

/**
 * A Simple login event
 */
public class LoginEvent {

    /**
     * @JSONProp username
     */
    private String username;
    /**
     * @JSONProp item
     */
    private String item;

    /**
     * @JSONProp price
     */
    private String price;

    /**
     * Get username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get item
     * @return item
     */
    public String getItem() {
        return item;
    }

    /**
     * Get price
     * @return price
     */
    public String getPrice() {
        return price;
    }
}
