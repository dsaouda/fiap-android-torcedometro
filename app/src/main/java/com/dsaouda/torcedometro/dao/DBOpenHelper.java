package com.dsaouda.torcedometro.dao;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dsaouda.torcedometro.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "torcedometro.db";
    private static final int VERSAO_BANCO = 1;

    private Context ctx;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSAO_BANCO);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // cria a estrutura do banco
        lerEExecutarSQLScript(db, ctx, R.raw.db_criar);
        // Insere os dados iniciais
        lerEExecutarSQLScript(db, ctx, R.raw.insere_dados_iniciais);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; ++i) {
            String migrationFileName = String.format("from_%d_to_%d", i, (i + 1));
            log("Looking for migration file: " + migrationFileName);
            int migrationFileResId = ctx.getResources()
                    .getIdentifier(migrationFileName, "raw", ctx.getPackageName());
            if (migrationFileResId != 0) {
                // execute script
                log("Found, executing");
                lerEExecutarSQLScript(db, ctx, migrationFileResId);
            } else {
                log("Not found!");
            }
        }
    }

    // Metodo auxiliar para realizar a leitura dos scripts
    private void lerEExecutarSQLScript(SQLiteDatabase db, Context ctx, Integer sqlScriptResId) {

        Resources res = ctx.getResources();

        try {
            InputStream is = res.openRawResource(sqlScriptResId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            executarSQLScript(db, reader);
            reader.close();
            is.close();
        } catch (IOException e) {
            throw new RuntimeException("Não foi possivel ler o arquivo SQLite", e);
        }
    }

    // Método auxiliar para executar o script
    private void executarSQLScript(SQLiteDatabase db, BufferedReader reader) throws IOException {
        String line;
        StringBuilder statement = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            statement.append(line);
            statement.append("\n");
            if (line.endsWith(";")) {
                String toExec = statement.toString();
                log("Executing script: " + toExec);
                db.execSQL(toExec);
                statement = new StringBuilder();
            }
        }
    }

    private void log(String msg) {
        Log.d(DBOpenHelper.class.getSimpleName(), msg);
    }
}
