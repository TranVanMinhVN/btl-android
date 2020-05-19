package com.mtv.news.entity.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mtv.news.entity.New;

import java.util.ArrayList;

public class NewDTO extends SQLiteOpenHelper {
    public  static  final String TableName = "Newspaper";
    public  static  final String newId = "new_id";
    public  static  final String authorId = "author_id";
    public  static  final String categoryId = "category_id";
    public  static  final String name = "name";
    public  static  final String note = "note";
    public  static  final String content = "content";
    public  static  final String urlImg = "url_img";
    public  static  final String urlImg2 = "url_img2";
    public  static  final String urlImg3 = "url_img3";
    public  static  final String time = "time";
    public  static  final String urlVideo = "url_video";

    public  NewDTO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String sqlCreate = "Create table if not exists " + TableName + "("
                + newId + " Integer Primary Key, "
                + authorId + " Integer, "
                + categoryId + " Integer, "
                + name + " Text, "
                + note + " Text, "
                + content + " Text, "
                + urlImg + " Text, "
                + urlImg2 + " Text, "
                + urlImg3 + " Text, "
                + time + " Text, "
                + urlVideo + " Text "
                + ")";
        // chạy sql tạo bảng
        sqLiteDatabase.execSQL((sqlCreate));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // xóa bảng SinhVienTable đã có
        sqLiteDatabase.execSQL("Drop table if exists " + TableName);
        // tạo lại
        onCreate((sqLiteDatabase));
    }

    // lấy tất cả các dòng của TableContact trả về ArrayList
    public ArrayList<New> getAllNew(){
        ArrayList<New> list = new ArrayList<>();
        String sql = "Select * from " + TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                New n = new New(cursor.getInt(0), cursor.getInt(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10));
                list.add(n);
            }
        }
        return list;
    }
    public ArrayList<New> getNewByCategory(int category_id){
        ArrayList<New> list = new ArrayList<>();
        String sql = "Select * from " + TableName +" WHERE category_id="+category_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                New n = new New(cursor.getInt(0), cursor.getInt(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10));
                list.add(n);
            }
        }
        return list;
    }

    public void addNew(New n){

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(newId,n.getNewId());
        value.put(authorId,n.getAuthorId());
        value.put(categoryId,n.getCategoryId());
        value.put(name,n.getName());
        value.put(note,n.getNote());
        value.put(content,n.getContent());
        value.put(urlImg,n.getUrlImg());
        value.put(urlImg2,n.getUrlImg2());
        value.put(urlImg3,n.getUrlImg3());
        value.put(time,n.getTime());
        value.put(urlVideo,n.getUrlImg());
        db.insert(TableName,null,value);
    }
    public void updateNew(int Id,New n){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(authorId,n.getAuthorId());
        value.put(categoryId,n.getCategoryId());
        value.put(name,n.getName());
        value.put(note,n.getContent());
        value.put(content,n.getContent());
        value.put(urlImg,n.getUrlImg());
        value.put(urlImg2,n.getUrlImg2());
        value.put(urlImg3,n.getUrlImg3());
        value.put(time,n.getTime());
        value.put(urlVideo,n.getUrlImg());
        db.update(TableName,value,newId+"=?",new String[]{String.valueOf(Id)});
        db.close();
    }

    public void deleteNew(int newId){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM "+TableName +" WHERE new_id="+newId;
        db.execSQL(sql);
        db.close();
    }
}
