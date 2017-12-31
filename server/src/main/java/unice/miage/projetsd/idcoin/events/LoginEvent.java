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
     * @JSONProp password
     */
    private String password;

    /**
     * Get username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get password
     * @return password
     */
    public String getPassword() {
        return password;
    }
}
