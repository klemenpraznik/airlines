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
public class ReservationInfo implements Parcelable {
    private int numberOfTravellers;
    private String returnFlight;
    private String flightClass;

    //PARCELER CODE
    public ReservationInfo(Parcel in){
        this.numberOfTravellers = in.readInt();
        this.returnFlight = in.readString();
        this.flightClass = in.readString();
    }

    public static final Parcelable.Creator<ReservationInfo> CREATOR = new Parcelable.Creator<ReservationInfo>() {
        @Override
        public ReservationInfo createFromParcel(Parcel source) {
            return new ReservationInfo(source);
        }

        public ReservationInfo[] newArray(int size) {
            return new ReservationInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.numberOfTravellers);
        dest.writeString(this.returnFlight);
        dest.writeString(this.flightClass);

    }
}
