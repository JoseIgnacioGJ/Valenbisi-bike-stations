package com.example.myapplication;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Array;
import java.util.Vector;

//Clase donde se guarda la info de la parada que queremos mostrar en la Práctica 1
public class Parada implements Parcelable {

    //Práctica 1
    public int numero;
    public String direccion;
    public int libres;

    //Práctica 2
    public int total;
    public int disponibles;
    public double coordenadasX;
    public double coordenadasY;

    //Práctica 1
    public Parada(int n, String d, int l)
    {
        numero = n;
        direccion = d;
        libres = l;
    }

    public Parada()
    {
    }

    //Práctica 2
    public Parada(int n, String d, int l, int t, int di, JSONArray c) throws JSONException {
        numero = n;
        direccion = d;
        libres = l;

        total  = t;
        disponibles = di;
        coordenadasX = c.getDouble(0);
        coordenadasY = c.getDouble(1);
    }


    protected Parada(Parcel in) {
        numero = in.readInt();
        direccion = in.readString();
        libres = in.readInt();

        total  = in.readInt();
        disponibles  = in.readInt();
        coordenadasX  = in.readDouble();
        coordenadasY  = in.readDouble();
    }

    public static final Creator<Parada> CREATOR = new Creator<Parada>() {
        @Override
        public Parada createFromParcel(Parcel in) {
            return new Parada(in);
        }

        @Override
        public Parada[] newArray(int size) {
            return new Parada[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(numero);
        parcel.writeString(direccion);
        parcel.writeInt(libres);
        parcel.writeInt(total);
        parcel.writeInt(disponibles);
        parcel.writeDouble(coordenadasX);
        parcel.writeDouble(coordenadasY);
    }
}
