package unice.miage.projetsd.idcoin.events;

import com.google.gson.Gson;

public class EventWrapper {
    private Gson gson;

    public EventWrapper(){
        this.gson = new Gson();
    }

    public Object convertEvent(String eventName, String json, Class eventClass){
        return this.gson.fromJson(json, eventClass);
    }

}
