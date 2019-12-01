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
        ContentValues values = new ContentValues();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(lastCheck);

        values.put("lastCheck", date);
        banco.insert("home", null, values);
    }

    public Date buscarLastCheck() {
        Cursor cursor = banco.query("home", new String[]{"lastCheck"}
                , null, null, null, null, null);

        Date lastCheck = null;

        if (cursor.moveToFirst()) {
            String dataString = cursor.getString(0);

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
