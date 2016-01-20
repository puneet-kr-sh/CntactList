package com.rapidsofttechnologies.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME ="name";
    public static final String CONTACTS_COLUMN_PHONE="phone";
    public static final String CONTACTS_COLUMN_EMAIL="email";
    public static final String CONTACTS_COLUMN_CITY="city";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+CONTACTS_TABLE_NAME+"("+CONTACTS_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CONTACTS_COLUMN_NAME+ " TEXT, "+CONTACTS_COLUMN_PHONE+" TEXT, "+CONTACTS_COLUMN_EMAIL+" TEXT, "+CONTACTS_COLUMN_CITY+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+CONTACTS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertContact(String name,String phone, String email,String city ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME,name);
        contentValues.put(CONTACTS_COLUMN_PHONE, phone);
        contentValues.put(CONTACTS_COLUMN_EMAIL, email);
        contentValues.put(CONTACTS_COLUMN_CITY, city);
        long insertCode = db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        db.close();
        if(insertCode==-1)
            return false;
        else
            return true;
    }

/*    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from contacts where id ="+id+"",null);
        return result;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,CONTACTS_TABLE_NAME);
        return numRows;
    }*/

    public boolean updateContact(Integer id, String name, String phone, String email, String city)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME,name);
        contentValues.put(CONTACTS_COLUMN_PHONE,phone);
        contentValues.put(CONTACTS_COLUMN_EMAIL, email);
        contentValues.put(CONTACTS_COLUMN_CITY, city);
        int res = db.update(CONTACTS_TABLE_NAME,contentValues,"id=?",new String[]{Integer.toString(id)});
        db.close();
        Log.d("UPDATE TAG :", "edit id ="+id+"updateContact() called. return value = "+res);
        return true;
    }

    public Boolean deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(CONTACTS_TABLE_NAME,"id=?",new String[]{ Integer.toString(id)});
        Log.d("DELETE TAG :", "delete id = "+res);
        if(res!=0)
            return true;
        else
            return false;
    }

    public ArrayList<Contact> getAllContacts()
    {
        ArrayList<Contact> contact_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+CONTACTS_TABLE_NAME ,null);
        res.moveToFirst();
        while(!res.isAfterLast())
        {
            Contact contact = new Contact();
            contact.setName(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            contact.setPhone(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PHONE)));
            contact.setEmail(res.getString(res.getColumnIndex(CONTACTS_COLUMN_EMAIL)));
            contact.setCity(res.getString(res.getColumnIndex(CONTACTS_COLUMN_CITY)));
            contact.setId(Integer.parseInt(res.getString(res.getColumnIndex(CONTACTS_COLUMN_ID))));
            contact_list.add(contact);
            res.moveToNext();
        }
        res.close();
        db.close();
        return contact_list;
    }
}
