package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HomeDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public HomeDAO(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public void inserirLastCheck(Date lastCheck) {

        // pega data atual do sistema
        //java.util.Date dataUtil = new java.util.Date();
        // Date dataAtual = new Date(dataUtil.getTime());

        ContentValues values = new ContentValues();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (lastCheck == null) {
            //String date = sdf.format(dataAtual);

            String date = "2019-11-20";
            values.put("lastCheck", "2019-11=12");
            banco.insert("home", null, values);
        } else {
            String[] id = new String[] {"1"};
            String date = sdf.format(lastCheck);
            values.put("lastCheck", date);
            banco.update("home", values, "_id=?", id);
        }
    }

    public String buscarLastCheck() {
        String lastCheck = null;
        String[] colunas = new String[]{"_id", "lastCheck"};
        Cursor cursor = banco.query("home", colunas,
                null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            lastCheck = cursor.getString(1);

        }
        cursor.close();

        return lastCheck;
    }
}
