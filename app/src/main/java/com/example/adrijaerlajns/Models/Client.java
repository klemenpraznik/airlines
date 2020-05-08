package com.example.adrijaerlajns.Models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Parcelable {
    private String name;
    private String surname;
    private String gender;
    private String email;
    private String phoneNumber;

    //PARCELER CODE
    public Client(Parcel in){
        this.name = in.readString();
        this.surname = in.readString();
        this.gender = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
    }

    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel source) {
            return new Client(source);
        }

        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeString(this.gender);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
    }
}
