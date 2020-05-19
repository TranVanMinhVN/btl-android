package com.mtv.news.entity.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mtv.news.entity.Author;

import java.util.ArrayList;

public class AuthorDAO extends SQLiteOpenHelper{

    public  static  final String TableName = "Author";
    public  static  final String authorId = "author_id";
    public  static  final String name = "name";
    public  static  final String address = "address";
    public  static  final String phoneNumber = "phone_number";
    public AuthorDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String sqlCreate = "Create table if not exists " + TableName + "("
                + authorId + " Integer Primary Key, "
                + name + " Text, "
                + address + " Text, "
                + phoneNumber + " Text "
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
    public ArrayList<Author> getAllAuthor(){
        ArrayList<Author> list = new ArrayList<>();
        String sql = "Select * from " + TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                Author author = new Author(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3));
                list.add(author);
            }
        }
        return list;
    }

    public void addAuthor(Author author){

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(authorId,author.getAuthorId());
        value.put(name,author.getName());
        value.put(address,author.getAddress());
        value.put(phoneNumber,author.getPhoneNumber());
        db.insert(TableName,null,value);
    }
    public void updateAuthor(int authorId,Author author){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(name,author.getName());
        value.put(address,author.getAddress());
        value.put(phoneNumber,author.getPhoneNumber());
        db.update(TableName,value,authorId+"=?",new String[]{String.valueOf(authorId)});
        db.close();
    }

    public Author getAuthorById(int authorId){
        SQLiteDatabase db = getWritableDatabase();
        Author author = null;
        String sql = "SELECT * FROM "+TableName +" WHERE author_id="+authorId;
        Cursor cursor = db.rawQuery(sql, null);
        if( cursor != null && cursor.moveToFirst() ){
            author = new Author(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3));
            db.close();
        }
        return author;
    }


    public void deleteAuthor(int authorId){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM "+TableName +" WHERE Sbd="+authorId;
        db.execSQL(sql);
        db.close();
    }

}
