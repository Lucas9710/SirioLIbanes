package remindme.android.com.sirioibanes.dtos;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.AbstractMap;

public class User implements Parcelable {
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

    protected User(final Parcel in) {
        email = in.readString();
        nickName = in.readString();
        phone = in.readString();
        name = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(final Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(final int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(email);
        dest.writeString(nickName);
        dest.writeString(phone);
        dest.writeString(name);
    }
}
