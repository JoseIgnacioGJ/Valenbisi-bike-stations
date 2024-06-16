package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* A class that extends from SQLiteOpenHelper must be implemented. This class will
implement the following functions to work with the database:
- InsertReport
- UpdateReport
- DeleteReport
- FindReportByBikeStation
- FindReportByID
*/
public class TasksDataBase  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Tasks";
    public static final String TABLE_NAME="REPORT";

    /* In addition, it is recommended the creation of an ID column like primary key in the table,
    in order to index and to find a report every time is needed.*/
    public static final String ID ="_id";

    // Rest of the names of the columns
    public static final String C_NAME = "NOMBRE";
    public static final String C_DESCRIPTION = "DESCRIPCION";
    public static final String C_BIKE_STATION = "ESTACION";
    public static final String C_STATUS = "ESTADO";
    public static final String C_TYPE = "TIPO";

    // Must have a public constructor
    public TasksDataBase(Context context,int version){
        super(context, DATABASE_NAME, null, version);
        // The third argument is of type SQLiteDatabase.CursorFactory or null to use the default
    }

    // And a method where the table is created if it does not exist
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + C_NAME + "TEXT,"
                // Rest of columns with their name and type
                + C_DESCRIPTION + "TEXT,"
                + C_BIKE_STATION + "TEXT"
                + C_STATUS + " TEXT,"
                + C_TYPE + " TEXT)";
        db.execSQL(CREATE_TABLE);

    }

    // A method to update the database (due to a version change)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // In a real scenario it would be necessary to extract the data from the BD
        // Create the tables again with the new structrure
        // and store the data in the new tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Método que inserta un nuevo Report
    public void InsertReport(Report report)
    {
        // We request an instance to modify the BD
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put (C_NAME,report.name);
        // Put values for the other columns
        values.put (C_DESCRIPTION,report.description);
        values.put (C_BIKE_STATION,report.bike_station);
        values.put (C_STATUS,report.status);
        values.put (C_TYPE,report.type);

        db.insert(TABLE_NAME,null,values);
    }

    // Método que actualiza un Report
    public void UpdateReport(Report report)
    {
        // We request an instance to modify the BD
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put (C_NAME,report.name);
        // Put values for the other columns
        values.put (C_DESCRIPTION,report.description);
        values.put (C_BIKE_STATION,report.bike_station);
        values.put (C_STATUS,report.status);
        values.put (C_TYPE,report.type);

        //Actualizamos el Report
        db.update(TABLE_NAME, values, ID + " =?", new String[]{Integer.toString(report.id)});
    }

    // Método que elimina un Report concreto
    public void DeleteReport(int id)
    {
        // We request an instance to modify the BD
        SQLiteDatabase db = this.getWritableDatabase();

        //Lo eliminamos usando el ID del Report
        db.delete(TABLE_NAME, ID + "=?", new String[]{String.valueOf(id)});
    }

    /* The functions that return reports must return a CURSOR with the data of the
    executed SELECT */

    // Método que devuelve un Report a partir de una estación
    public Cursor FindReportByBikeStation(int estacion)
    {
        // Request an instance that does not modify the database
        SQLiteDatabase db = this.getReadableDatabase();

        String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE " + C_BIKE_STATION + " = ?";

        Cursor cursor = db.rawQuery(QUERY, new String[]{Integer.toString(estacion)});
        return cursor;

    }

    // Método que devuelve un Report a partir de una ID
    public Cursor FindReportByID(int identificador)
    {
        // Request an instance that does not modify the database
        SQLiteDatabase db = this.getReadableDatabase();

        String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ?";

        Cursor cursor = db.rawQuery(QUERY, new String[]{Integer.toString(identificador)});
        return cursor;
    }
}