package com.templatemela.instagramzy.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TMStringParcebleModel implements Parcelable {
    public static final Parcelable.Creator<TMStringParcebleModel> CREATOR = new Parcelable.Creator<TMStringParcebleModel>() {
        public TMStringParcebleModel createFromParcel(Parcel parcel) {
            return new TMStringParcebleModel(parcel);
        }

        public TMStringParcebleModel[] newArray(int i) {
            return new TMStringParcebleModel[i];
        }
    };
    String data;

    public int describeContents() {
        return 0;
    }

    public TMStringParcebleModel(String str) {
        data = str;
    }

    protected TMStringParcebleModel(Parcel parcel) {
        data = parcel.readString();
    }

    public String getData() {
        return data;
    }

    public void setData(String str) {
        data = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(data);
    }
}
