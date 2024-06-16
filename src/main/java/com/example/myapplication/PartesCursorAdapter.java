package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/* The ListView must be fed with a class that extends from CursorAdapter. */
public class PartesCursorAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;

    public PartesCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, 0);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return cursorInflater.inflate(R.layout.listreporteview, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {

        TextView item_reporte = (TextView) view.findViewById(R.id.item_reporte);
        String name = cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE"));
        item_reporte.setText(name);
    }
}
