package com.codemort.app_examen_laguaquiza_ruiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtCode;
    EditText txtNombre;
    EditText txtCredito;
    EditText txtSueldo;

    Button btnRegister;
    Button btnList;

    Button btnSearch;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        txtCode = (EditText) findViewById(R.id.txtCode);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtCredito = (EditText) findViewById(R.id.txtCredito);
        txtSueldo = (EditText) findViewById(R.id.txtSueldo);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnList = (Button) findViewById(R.id.btnList);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnRegister.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        btnSearch = (Button) findViewById(R.id.btnSearch);


        btnSearch.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String codigo_ = txtCode.getText().toString();
        String nombre = txtNombre.getText().toString();
        String credito = txtCredito.getText().toString();
        String sueldo = txtSueldo.getText().toString();

        Intent intent = null;
        switch (v.getId()){
            case R.id.btnRegister:
                if(!codigo_.isEmpty() || !nombre.isEmpty() || !credito.isEmpty() || !sueldo.isEmpty()){
                    insert(codigo_,nombre,credito,sueldo);
                }else{
                    Toast.makeText(this, "Hay campos vacios.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnList:
              //  intent = new Intent(this, ListaPedidos.class);
               // startActivity(intent);
                break;
            case R.id.btnSearch:
                search(codigo_);
                break;
            case R.id.btnDelete:
                destroy(codigo_);
                break;
        }
        if (intent != null){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void insert (String codigo, String nombre, String credito,String sueldo){
        Connection db = new Connection(this, "bdlaguaquiza_ruiz", null, 1);
        SQLiteDatabase baseDatos = db.getWritableDatabase();


        if ((!codigo.isEmpty()) && (!nombre.isEmpty()) && (!credito.isEmpty()) && (!sueldo.isEmpty()) ) {

            ContentValues registro = new ContentValues();

            Cursor fila = baseDatos.rawQuery("SELECT * FROM aspirantes WHERE codigo = '"+codigo+"'", null);

            if (fila.getCount() <= 0){
                registro.put("codigo", codigo);
                registro.put("nombre", nombre);
                registro.put("credito", credito);
                registro.put("sueldo", sueldo);

                Integer s = Integer.parseInt(sueldo);

                if(s>= 300  && s<=700 ){
                    baseDatos.insert("aspirantes", null, registro);
                    baseDatos.close();


                    //limpiar los campos despues de registrar
                    txtCode.setText("");
                    txtNombre.setText("");
                    txtCredito.setText("");
                    txtSueldo.setText("");
                }else{
                    Toast.makeText(this, "No esta en el rango", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(this, "Se registro un aspirante", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "el código ya existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Hay campos vacios.", Toast.LENGTH_SHORT).show();
        }
    }

    //funcion buscar
    public void search (String codigo){
        Connection db = new Connection(this, "bdlaguaquiza_ruiz", null, 1);
        SQLiteDatabase baseDatos = db.getWritableDatabase();


        if (!codigo.isEmpty()) {
            Cursor fila = baseDatos.rawQuery("SELECT * FROM aspirantes WHERE codigo = '"+codigo+"' ", null);

            if (fila.moveToFirst()) {
                txtNombre.setText(fila.getString(1));
                txtCredito.setText(fila.getString(2));
                txtSueldo.setText(fila.getString(3));
            } else {
                Toast.makeText(this, "No hay registro.", Toast.LENGTH_SHORT).show();
            }
            baseDatos.close();
        } else {
            Toast.makeText(this, "Nada para buscar.", Toast.LENGTH_SHORT).show();
        }

    }

    public void destroy (String codigo_) {
        Connection db = new Connection(this, "bdlaguaquiza_ruiz", null, 1);
        SQLiteDatabase baseDatos = db.getWritableDatabase();

        if(!codigo_.equals("")){
            Cursor fila = baseDatos.rawQuery("SELECT * FROM aspirantes WHERE codigo = '"+codigo_+"' ", null);
            if(fila.getCount() <=0){
                Toast.makeText(this, "Nada para eliminar.", Toast.LENGTH_SHORT).show();
            }else {
                    baseDatos.delete("pedidos","codigo = '"+codigo_+"' ",null);
                    baseDatos.close();
                    Toast.makeText(this, "Se elimino.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Nada para eliminar.", Toast.LENGTH_SHORT).show();
        }
    }
}
