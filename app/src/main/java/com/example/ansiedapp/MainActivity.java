package com.example.ansiedapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etNivelPreocupacion;
    private Button btnAgregar;
    private ListView listView;
    private TextView tvSumaTotal;
    private ArrayList<String> listaItems;
    private ArrayAdapter<String> adapter;
    private int sumaTotal = 0;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_KEY = "sharedPrefs";
    private static final String ITEMS_KEY = "items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.et_nombre);
        etNivelPreocupacion = findViewById(R.id.et_nivel_preocupacion);
        btnAgregar = findViewById(R.id.btn_agregar);
        listView = findViewById(R.id.list_view);
        tvSumaTotal = findViewById(R.id.tv_suma_total);

        sharedPreferences = getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        listaItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaItems);
        listView.setAdapter(adapter);

        loadData();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String nivelPreocupacion = etNivelPreocupacion.getText().toString();
                int nivel = Integer.parseInt(nivelPreocupacion);
                sumaTotal += nivel;
                String item = nombre + ", Nivel de preocupación: " + nivelPreocupacion;
                listaItems.add(item);
                adapter.notifyDataSetChanged();
                etNombre.setText("");
                etNivelPreocupacion.setText("");
                tvSumaTotal.setText("Ansiedad total: " + sumaTotal);
                saveData();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String item = listaItems.get(position);
                int nivel = extractNivelPreocupacion(item);
                sumaTotal -= nivel;
                listaItems.remove(position);
                adapter.notifyDataSetChanged();
                tvSumaTotal.setText("Ansiedad total " + sumaTotal);
                saveData();
                return true;
            }
        });
    }

    private int extractNivelPreocupacion(String item) {
        int index = item.lastIndexOf(":");
        String nivelPreocupacion = item.substring(index + 1).trim();
        return Integer.parseInt(nivelPreocupacion);
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(listaItems);
        editor.putStringSet(ITEMS_KEY, set);
        editor.putInt("sumaTotal", sumaTotal);
        editor.apply();
    }

    private void loadData() {
        Set<String> set = sharedPreferences.getStringSet(ITEMS_KEY, null);
        if (set != null) {
            listaItems.addAll(set);
            adapter.notifyDataSetChanged();
        }
        sumaTotal = sharedPreferences.getInt("sumaTotal", 0);
        tvSumaTotal.setText("Ansiedad total: " + sumaTotal);
    }
}