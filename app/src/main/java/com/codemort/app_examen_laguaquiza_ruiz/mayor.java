package com.codemort.app_examen_laguaquiza_ruiz;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class mayor extends AppCompatActivity {

    TextView lblCode;
    TextView lblNombre;
    TextView lblCredito;
    TextView lblSueldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mayor);

        lblCode = (TextView) findViewById(R.id.lblCode);
        lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblCredito = (TextView) findViewById(R.id.lblCredito);
        lblSueldo = (TextView) findViewById(R.id.lblSueldo);

        first();
    }

    public void first (){
        Connection db = new Connection(this, "bdlaguaquiza_ruiz", null, 1);
        SQLiteDatabase baseDatos = db.getWritableDatabase();

        Cursor fila = baseDatos.rawQuery("SELECT * FROM aspirantes WHERE sueldoasp >= 500 ORDER BY codigoasp DESC LIMIT 1", null);

        if (fila.moveToFirst()) {
            lblCode.setText(fila.getString(0));
            lblNombre.setText(fila.getString(1));
            lblCredito.setText(fila.getString(2));
            lblSueldo.setText(fila.getString(3));
        } else {
            Toast.makeText(this, "No hay registros ,mayor a 500.", Toast.LENGTH_SHORT).show();
        }
        baseDatos.close();

    }
}
