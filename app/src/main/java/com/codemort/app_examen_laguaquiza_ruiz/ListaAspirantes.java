package com.codemort.app_examen_laguaquiza_ruiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaAspirantes extends AppCompatActivity {

    //instancia del elemento de la vista
    ListView listView;
    //se crean variables para el conjunto de datos de la lista
    ArrayList<String> listado;


    //para que no colapese la vista
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_aspirantes);
        listView = (ListView) findViewById(R.id.listView);

        //se ejecuta la funcion
        CargarListado();

       // a la lista instanciada se le agrega el evento para dar clic en cualquier item de la lista
     /*   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListaAspirantes.this, listado.get(position), Toast.LENGTH_SHORT).show();
                ///se declaran variables para guardar los datos del item
                String codigo = listado.get(position).split(" ")[0];
                String nombre = listado.get(position).split(" ")[1];
                String credito = listado.get(position).split(" ")[2];
                String sueldo = listado.get(position).split(" ")[3];
                Toast.makeText(ListaAspirantes.this, "ID: " + codigo, Toast.LENGTH_SHORT).show();
                /// enviamos parametros paa modificar
               Intent intent = new Intent(ListaAspirantes.this, Modificar.class);
                intent.putExtra("codigo", codigo);
                intent.putExtra("nombre", nombre);
                intent.putExtra("credito", credito);
                intent.putExtra("sueldo", sueldo);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });*/

        ////////7 para poner la flechita de atras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void CargarListado() {
        listado = ListaPersonas();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listado);
        listView.setAdapter(adapter);
    }

    private ArrayList<String> ListaPersonas() {
        ArrayList<String> datos = new ArrayList<String>();
        Connection helper = new Connection(this, "bdlaguaquiza_ruiz", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM aspirantes";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                //se llena la lista con los datos guardados
                String linea = c.getString(0) + " " + c.getString(1)+" "+c.getString(2)+" "+c.getString(3);
                datos.add(linea);
            } while (c.moveToNext());

        }
        db.close();
        return datos;
    }
}
