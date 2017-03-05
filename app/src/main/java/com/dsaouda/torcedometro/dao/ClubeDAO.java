package com.dsaouda.torcedometro.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dsaouda.torcedometro.model.Clube;

import java.util.LinkedList;
import java.util.List;

public class ClubeDAO {

    private DBOpenHelper banco;

    public ClubeDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    public static final String TABELA_CLUBES = "clube";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";

    public List<Clube> getAll() {
        List<Clube> clubes = new LinkedList<>();
        String query = "SELECT * FROM " + TABELA_CLUBES;
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Clube clube = null;
        if (cursor.moveToFirst()) {
            do {
                clube = new Clube();
                clube.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));

                clube.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                clubes.add(clube);
            } while (cursor.moveToNext());
        }
        return clubes;
    }

    public Clube getBy(int id) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[] = { COLUNA_ID, COLUNA_NOME};
        String where = "id = " + id;
        Cursor cursor = db.query(true, TABELA_CLUBES, colunas, where, null, null,
                null, null, null);
        Clube clube = null;
        if(cursor != null)
        {
            cursor.moveToFirst();
            clube = new Clube();
            clube.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
            clube.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
        }
        return clube;
    }


}
