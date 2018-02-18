package remindme.android.com.sirioibanes.dtos;

import java.util.HashMap;

public class Event extends HashMap<String, Object> {

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

    public SocialNetwork getSocialNetworks() {
        return (SocialNetwork) get("redes");
    }
}
