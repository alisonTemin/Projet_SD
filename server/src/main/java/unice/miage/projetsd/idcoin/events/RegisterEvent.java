package unice.miage.projetsd.idcoin.events;

/**
 * A Simple login event
 */
public class RegisterEvent {

    /**
     * @JSONProp name
     */
    private String name;

    /**
     * @JSONProp username
     */
    private String username;
    /**
     * @JSONProp password
     */
    private String password;

    /**
     * Get name
     * @return name
     */
    public String getName() {
        return name;
    }


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
