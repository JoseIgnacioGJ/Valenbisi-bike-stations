package com.example.myapplication;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PrivilegedActionException;
import java.util.ArrayList;


public class AdapterParadas extends BaseAdapter {

    //Creamos una lista de todas las paradas
    private ArrayList<Parada> paradas = new ArrayList<Parada>();
    Context context;

    //Textos donde irá la info
    static class ViewHolder {
        TextView number;
        TextView address;
        TextView frees;
    }

    public AdapterParadas(Context c){
        context = c;
        Init();
    }

    public void Init() {
        // We read the JSON file and fill the “paradas” (bike stations) ArrayList
        InputStream is = context.getResources().openRawResource(R.raw.valenbisi);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try
        {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while((n = reader.read(buffer)) != -1) {
                writer.write(buffer,0,n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //The String writer.toString() must be parsed in the bike stations ArrayList by
        //using JSONArray and JSONObject
        try
        {
            JSONObject object = new JSONObject(writer.toString());
            JSONArray array = object.getJSONArray("features");

            //Cogemos de cada parada la info del JSON
            for(int i=0; i<array.length();i++)
            {
                JSONObject properties = array.getJSONObject(i).getJSONObject("properties");
                JSONObject geometry = array.getJSONObject(i).getJSONObject("geometry");
                Parada p = new Parada(properties.getInt("number"),
                        properties.getString("address"),
                        properties.getInt("free"),
                        properties.getInt("total"),
                        properties.getInt("available"),
                        geometry.getJSONArray("coordinates"));
                paradas.add(p);
            }

        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

    }



    class HTTPConnector extends AsyncTask<String, Void, ArrayList> {
        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList paradas=new ArrayList<Parada>();

            //Perform the request and get the answer
            String url = "http://mapas.valencia.es/lanzadera/opendata/Valenbisi/JSON";
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                //add request header
                con.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                con.setRequestProperty("accept", "application/json;");
                con.setRequestProperty("accept-language", "es");
                con.connect();
                int responseCode = con.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),
                        "UTF-8"));
                int n;
                while ((n = in.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
                in.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                JSONObject JSONobj = new JSONObject( writer.toString());
                JSONArray jsonArray = JSONobj.getJSONArray("features");

                int num = jsonArray.length();

                for (int i = 0; i < num; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    JSONObject properties = jsonObject.getJSONObject("properties");
                    JSONObject geometry = jsonObject.getJSONObject("geometry");
                    JSONArray coordinates = geometry.getJSONArray("coordinates");

                    Parada parada = new Parada();

                    parada.direccion = properties.getString("address");
                    parada.numero = Integer.parseInt(properties.getString("number"));
                    parada.total = Integer.parseInt(properties.getString("total"));
                    parada.libres = Integer.parseInt(properties.getString("free"));
                    parada.disponibles = Integer.parseInt(properties.getString("available"));
                    parada.coordenadasX = (Double) (coordinates.get(0));
                    parada.coordenadasY = (Double) (coordinates.get(1));

                    paradas.add(parada);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return paradas;
}


       protected void onPostExecute(ArrayList paradas) {

        }

    }

    @Override
    public int getCount() {
        return paradas.size();
    }

    @Override
    public Parada getItem(int position) {
        return paradas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return paradas.get(position).numero;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // We use the ViewHolder pattern to store the views of every list element
        //to display them faster when the list is moved
        View v = convertView;
        ViewHolder holder = null;

        if (v == null) {
            // If is null, we create it from “layout”
            LayoutInflater li =(LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.listparadaview, null);
            holder = new ViewHolder();

            //Cogemos los textos de listparadaview.xml por medio de sus IDs
            holder.number = (TextView) v.findViewById(R.id.paradaviewnumber);
            holder.address = (TextView) v.findViewById(R.id.paradaviewaddress);
            holder.frees = (TextView) v.findViewById(R.id.paradaviewpartes);

            v.setTag(holder);
        } else {
            // If is not null, we re-use it to update it.
            holder = (ViewHolder) v.getTag();
        }
        //Fill in the “holder” with the bike station information which is in the
        // position “position” of the ArrayList


        //Metemos la info del JSON en los textos
        holder.number.setText("" + paradas.get(position).numero);
        holder.address.setText(paradas.get(position).direccion);
        holder.frees.setText("" + paradas.get(position).libres);


        return v;
    }

}