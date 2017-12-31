package unice.miage.projetsd.idcoin.events;

import com.google.gson.Gson;

/**
 * EventWrapper for GSON
 */
public class EventWrapper {
    /**
     * GSON instance
     */
    private Gson gson;

    /**
     * EventWrapper Constructor
     * Initialize Gson instance
     */
    public EventWrapper(){
        this.gson = new Gson();
    }

    /**
     * Convert a raw JSON to an event class
     *
     * @param json
     * @param eventClass
     * @return
     */
    public Object convertEvent(String json, Class eventClass){
        return this.gson.fromJson(json, eventClass);
    }

}
