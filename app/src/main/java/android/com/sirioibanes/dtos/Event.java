package android.com.sirioibanes.dtos;

import android.support.annotation.NonNull;

import java.util.AbstractMap;
import java.util.HashMap;

public class Event extends HashMap<String, Object> {

    public Event(@NonNull final String key AbstractMap<String, Object> eventObject) {
        for (final String key : eventObject.keySet()) {
            put(key, eventObject.get(key));
        }
    }

    public String getTitle() {
        return (String) get("titulo");
    }

    public String getAddress() {
        return (String) get("lugar");
    }

    public String getDescription() {
        return (String) get("descripcion");
    }

    public boolean getEnabled() {
        return ((String) get("habilitada")).equalsIgnoreCase("true");
    }

    public String getPicture() {
        return (String) get("foto");
    }

    public String getPhone() {
        return (String) get("telefono");
    }

    public String getTimeStamp() {
        return (String) get("timestamp");
    }

    public HashMap getSocialNetworks() {
        return (HashMap) get("redes");
    }
}
