package com.example.c2paplicationmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class List_Transaction_Activity extends AppCompatActivity {

    private TextView textMovimient;
    private ListView listViewMovimient;

    private String names[] ={
            "SE01	Sistema de gestión de transportes",
            "SE07	Estatus de las ordenes de transporte",
            "SE09	Órdenes de transporte Workbench",
            "SE10	Órdenes de transporte Customizong",
            "SE11	Mantenimiento del diccionario de datos",
            "SE12	Visualización del diccionario de datos",
            "SE13	Mantenimiento de tablas",
            "SE14	Diccionario de datos, utilidad para bases de datos",
            "SE15	Sistema del repositorio",
            "SE16	Visor de datos (Datos Browser)",
            "SE17	Visor de tablas",
            "SE30	Análisis de tiempos de ejecución",
            "SE32	Mantenimiento de elementos de texto.",
            "SE35	Mantenimiento de módulos de diálogo"};

    private String numbers[] = {"23571113", "17192329",
            "31374143", "47535961", "67714859",
            "73798389", "97101103", "107109113",
            "127131137", "139149151", "157163167",
            "173587524", "179181191", "193197199"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__transaction__es);


        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, names);
        listViewMovimient.setAdapter(adapter);

        listViewMovimient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textMovimient.setText("Número de Movimiento: "+numbers[i]+ " del "+ listViewMovimient.getItemAtPosition(i));
            }
        });


    }
}
