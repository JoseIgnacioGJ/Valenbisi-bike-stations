package com.example.myapplication;
//JOSE IGNACIO GARCÍA JÁVEGA
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Detalle_Parada extends AppCompatActivity {

    TextView number;
    TextView address;
    TextView frees;

    TextView total;
    TextView avaliable;
    TextView coordinatesX;
    TextView coordinatesY;

    ImageButton new_game;
    ImageButton help;

    Parada parada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Decimos cual es el xml donde se muestra la parada detalladamente
        setContentView(R.layout.parada_detallada);

        //Indicamos cuáles son los espacios donde se muestra la info
        number = (TextView) findViewById(R.id.editNumber);
        address = (TextView) findViewById(R.id.editAddress);
        frees = (TextView) findViewById(R.id.editFree);
        total = (TextView) findViewById(R.id.editTotal);
        avaliable = (TextView) findViewById(R.id.editAvailable);
        coordinatesX = (TextView) findViewById(R.id.editCoordinatesX);
        new_game = (ImageButton) findViewById(R.id.new_game);
        help = (ImageButton) findViewById(R.id.help);

        //Creamos un intento para recoger el intento del main, junto con su parada
        parada = getIntent().getParcelableExtra("parada_seleccionada");

        //Metemos la info del JSON en los textos
        number.setText("Number " + String.valueOf(parada.numero));
        address.setText("Address " + String.valueOf(parada.direccion));
        total.setText("Total " + String.valueOf(parada.total));
        frees.setText("Free " + String.valueOf(parada.libres));
        avaliable.setText("Available " + String.valueOf(parada.disponibles));
        coordinatesX.setText("Coordinates " + String.valueOf(parada.coordenadasX) + ", " + String.valueOf(parada.coordenadasY));


        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Detalle_Reporte.class);
                intent.putExtra("number_parada", parada.numero);
                startActivity(intent);
            }});


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri;
                gmmIntentUri = Uri.parse("geo:0,0?q=" + parada.coordenadasX + "," + parada.coordenadasY + "(" + parada.direccion + ")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                //If is installed in the application, the Google Maps activity is launched
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }});

        /* In the Bike Station Detail layout must be inserted a ListView that displays the reports associated
        with the current bike station.
        The following code shows and example about how must be initialized both the Cursor and the Adapter.
        */
        TasksDataBase db = new TasksDataBase(getApplicationContext(),1);
        Cursor partesByParada = db.FindReportByBikeStation(parada.numero);
        PartesCursorAdapter partesAdapter = new PartesCursorAdapter(getApplicationContext(), partesByParada, false);
        ListView lv=(ListView) findViewById(R.id.idlistapartesdetalle);
        lv.setAdapter(partesAdapter);
    }


}




