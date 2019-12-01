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
        java.util.Date dataUtil = new java.util.Date();
        Date dataAtual = new Date(dataUtil.getTime());

        ContentValues values = new ContentValues();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (lastCheck == null) {
            String date = sdf.format(dataAtual);

            values.put("lastCheck", date);
            banco.insert("home", null, values);
        } else {
            String date = sdf.format(lastCheck);
            values.put("lastCheck", date);
            banco.update("home", values, "id=1", null);
        }
    }

    public Date buscarLastCheck() {
        Cursor cursor = banco.query("home", new String[]{"id", "lastCheck"}
                , null, null, null, null, null);

        Date lastCheck = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String dataString = cursor.getString(cursor.getColumnIndex("lastCheck"));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                java.util.Date utilDate = sdf.parse(dataString);
                lastCheck = new Date(utilDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

        return lastCheck;
    }
}
