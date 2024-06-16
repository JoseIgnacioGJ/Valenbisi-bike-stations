package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    Parada p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Introducimos a la vista el código de activity_main.xml
        setContentView(R.layout.activity_main);

        //Introducimos a la vista el adaptador de paradas
        ListView vista = findViewById(R.id.list_view);
        vista.setAdapter(new AdapterParadas(getApplicationContext()));

        //Cuando hacemos click en la pantalla donde están todas las pantallas...
        vista.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                     {
                                         @Override
                                         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                                         {

                                             //Cogemos la parada que el usuario ha seleccionado
                                             p = (Parada) adapterView.getItemAtPosition(i);

                                             //Creamos un intento para mandar esa parada y para cambiar la pantalla a
                                             //la que nos muestra toda su información
                                             Intent intent = new Intent(getApplicationContext(), Detalle_Parada.class);
                                             intent.putExtra("parada_seleccionada", p);


                                             startActivity(intent);




                                         }
                                     }

        );

    }

}