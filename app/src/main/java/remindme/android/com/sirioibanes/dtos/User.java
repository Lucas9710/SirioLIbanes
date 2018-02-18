package remindme.android.com.sirioibanes.dtos;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.AbstractMap;

public class User {
    @SerializedName("email")
    private String email;

    @SerializedName("nickname")
    private String nickName;

    @SerializedName("telefono")
    private String phone;

    @SerializedName("nombre")
    private String name;

    @SerializedName("eventos")
    private AbstractMap<String, Boolean> events;

    public User(@NonNull final String name, @NonNull final String nickName, @NonNull final String email,
                @NonNull final String phone, @NonNull final AbstractMap<String, Boolean> events) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.phone = phone;
        this.events = events;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public AbstractMap<String, Boolean> getEvents() {
        return events;
    }
}
