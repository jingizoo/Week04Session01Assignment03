package com.jalaj.firstapp.autosearchproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by jalajmehta on 7/16/16.
 */

public class SearchDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME ="EMPLOYEE_DB";
    public static final int DB_VERSION = 1;
    public static final String saTableName = "EMPLOYEE";
    public static final String saColNameArray[] = {"emp_id","emp_name","emp_age","emp_pic"};
    public static final String bracketOpen = "(";
    public static final String bracketClose = ")";
    SQLiteDatabase searchDatabase;

    public  SearchDBHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table = "Create Table "+saTableName+ bracketOpen +saColNameArray[0]+" INTEGER PRIMARY KEY," + saColNameArray[1] +" TEXT, "+ saColNameArray[2] +" INTEGER, "  + saColNameArray[3] +" BLOB "+bracketClose;
        db.execSQL(create_table);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String[] getEmployeeData(int empId)
    {
        searchDatabase = getReadableDatabase();
        String selectSQL = "SELECT "+saColNameArray[0]+" ,"+saColNameArray[1]+", "+saColNameArray[2]+" FROM "+saTableName;
        String whereClause = " WHERE "+saColNameArray[0]+" =?";
        String [] param = {empId+""};
        Cursor cursor = searchDatabase.rawQuery(selectSQL+whereClause,param);
        cursor.moveToFirst();
        String[] returnParam = {cursor.getString(1),cursor.getString(2)};
        return  returnParam;
    }
    public Bitmap getEmployeeImage(int empId)
    {
        searchDatabase = getReadableDatabase();
        String selectSQL = "SELECT "+saColNameArray[3]+" FROM "+saTableName;
        String whereClause = " WHERE "+saColNameArray[0]+" =?";
        String [] param = {empId+""};
        Cursor cursor = searchDatabase.rawQuery(selectSQL+whereClause,param);
        cursor.moveToFirst();

                byte [] image = cursor.getBlob(0);
        Bitmap returnParam = BitmapFactory.decodeByteArray(image,0,image.length);
        return  returnParam;
    }


    public void buildDatabase() throws IOException {
        searchDatabase = getWritableDatabase();

        ContentValues values;
        for (int i =0; i<1;i++){
           values = new ContentValues();
            values.put(saColNameArray[1],"Default"+i);
            values.put(saColNameArray[2],i+22);
            FileInputStream fileInputStream = new FileInputStream("/storage/emulated/0/DCIM/Camera/_20150731_111423.JPG");
            byte [] image = new byte[fileInputStream.available()];
            fileInputStream.read(image);
            values.put(saColNameArray[3],image);
            searchDatabase.insert(saTableName,null,values);
            fileInputStream.close();
        }
searchDatabase.close();

    }
}
