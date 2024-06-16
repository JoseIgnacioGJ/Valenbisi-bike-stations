package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Detalle_Reporte extends AppCompatActivity {

    TextView report_subject;
    TextView description;
    Spinner status;
    Spinner type;
    ImageButton  insert_update;
    ImageButton delete;

    int parada, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__reporte);

        Intent intento = getIntent();

        parada = intento.getIntExtra("number_parada",0);

        //Recogemos los valores de cada característica del report

        String nombre = intento.getStringExtra("name");
        String descrpicion = intento.getStringExtra("description");
        String estado = intento.getStringExtra("status");
        String tipo = intento.getStringExtra("type");

        //Indicamos cuáles son los espacios donde se muestra la info
        report_subject = (TextView) findViewById(R.id.editReport_subject);
        description = (TextView) findViewById(R.id.editDescription);

        status = (Spinner) findViewById(R.id.editStatus);
        type = (Spinner) findViewById(R.id.editType);

        insert_update = (ImageButton) findViewById(R.id.editInsert_update);
        delete = (ImageButton) findViewById(R.id.editDelete);

        id = intento.getIntExtra("id",0);

        //Si el Report no tiene info
        if(nombre == null)
        {
            //En caso de que se pulse el botón de insertar
            insert_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TasksDataBase db = new TasksDataBase(getApplicationContext(),1);
                    Report report = new Report();

                    //Recogemos
                    report.name = String.valueOf(report_subject.getText());
                    report.description = String.valueOf(description.getText());
                    report.bike_station = parada;
                    report.status = String.valueOf(status.getSelectedItem());
                    report.type = String.valueOf(type.getSelectedItem());

                    db.InsertReport(report);

                      Intent intent = new Intent(Detalle_Reporte.this, AdapterParadas.class);
                    startActivity(intent);
                }
            });

            delete.setAlpha(0.5f);
            delete.setClickable(false);
        }
        else
        {


            report_subject.setText(nombre);
            description.setText(descrpicion);
            status.setSelection(((ArrayAdapter<String>)status.getAdapter()).getPosition(estado));
            type.setSelection(((ArrayAdapter<String>)type.getAdapter()).getPosition(tipo));

            insert_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TasksDataBase db = new TasksDataBase(getApplicationContext(),1);
                    Report report = new Report();

                    report.name = String.valueOf(report_subject.getText());
                    report.description = String.valueOf(description.getText());
                    report.bike_station = parada;
                    report.status = String.valueOf(status.getSelectedItem());
                    report.type = String.valueOf(type.getSelectedItem());

                    db.UpdateReport(report);

                    Intent intent = new Intent(Detalle_Reporte.this, AdapterParadas.class);
                    startActivity(intent);

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TasksDataBase db = new TasksDataBase(getApplicationContext(),1);

                    db.DeleteReport(id);

                     Intent intent = new Intent(Detalle_Reporte.this, AdapterParadas.class);
                    startActivity(intent);

                }
            });
        }
    }
}
