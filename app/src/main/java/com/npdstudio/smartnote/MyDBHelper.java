package com.npdstudio.smartnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;


public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_note.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTE = "NOTE";
    private static final String COL_ID = "ID";
    private static final String COL_TITLE = "TITLE";
    private static final String COL_TAG = "TAG";
    private static final String COL_CONTENT = "CONTENT";
    private static final String COL_TIME = "TIME";

    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tự động gọi nếu chưa có database
        String queryCreateTable = "CREATE TABLE " + TABLE_NOTE
                + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " NVARCHAR (255), "
                + COL_TAG + " NVARCHAR(255), "
                + COL_CONTENT + " NVARCHAR(255), "
                + COL_TIME + " NVARCHAR(255)"
                +")";
        db.execSQL(queryCreateTable);
    }
    //Thực hiện khi có version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //xóa bản cũ
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NOTE);
        //tạo bản mới
        onCreate(db);
    }
    public ArrayList<Note> loadAllData(){
        ArrayList<Note> arrNotes = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT "
                +COL_ID+","
                +COL_TITLE+","
                +COL_TAG+","
                +COL_CONTENT+","
                +COL_TIME+
                " FROM "+TABLE_NOTE;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String tag = cursor.getString(2);
            String content = cursor.getString(3);
            String time = cursor.getString(4);
            arrNotes.add(new Note(id,title,tag,content,time));
            cursor.moveToNext();
        }
        cursor.close();
        return arrNotes;
    }
    //Cập nhật
    public void updateNote(int ID_NOTE, Note note) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_NOTE+" SET TITLE=?, TAG = ?, CONTENT = ?, TIME = ? where id = ?",
                new String[]{note.getmTitle(),
                        note.getmTag() + "",
                        note.getmContent() + "",
                        note.getmTime()+"",
                        ID_NOTE+""});
    }

    //Chèn mới
    public void insertNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO NOTE (TITLE,TAG,CONTENT,TIME) VALUES (?,?,?,?)",
                new String[]{note.getmTitle(),
                        note.getmTag() + "",
                        note.getmContent() + "",
                        note.getmTime()+""});
    }

    //Xoá note khỏi DB
    public void deleteNoteByID(int ID_Note) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM NOTE where id = "+ID_Note);
    }
    public Note getNoteByID(int ID_Note){
        Note note = new Note();
        SQLiteDatabase db = getReadableDatabase();
        //db.
        return note;
    }
}