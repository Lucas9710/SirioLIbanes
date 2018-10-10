package android.com.sirioibanes.dtos;

import java.util.HashMap;

public class Gift extends HashMap {

    public String getKey() {
        return (String) get("key");
    }

    public String getName() {
        return (String) ((HashMap) get("value")).get("name");
    }

    public String getLink() {
        return (String) ((HashMap) get("value")).get("link");
    }
}
