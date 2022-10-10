package com.cieep.a04_creacindevistasporcdigo;

import android.content.Intent;
import android.os.Bundle;

import com.cieep.a04_creacindevistasporcdigo.modelos.AlumnoModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.cieep.a04_creacindevistasporcdigo.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> addAlumnoLauncher;

    // Necesitamos 3 cosas
    // 1. Un elemento para mostrar información
    // 2. El conjunto de datos (ArrayList<AlumnoModel>)
    // 3. Un contenedor donde meter cada uno de los elementos (LinearLayout (Vertical) -> ScrollView)
    // 4. logica para mostrar los elementos
    private ArrayList<AlumnoModel> alumnoModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        alumnoModelArrayList = new ArrayList<>();
        inicializaLauncher();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAlumnoLauncher.launch(new Intent(MainActivity.this, AddAlumnoActivity.class));
            }
        });
    }

    private void inicializaLauncher() {
        addAlumnoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null && result.getData().getExtras() != null) {
                                AlumnoModel alumnoModel = (AlumnoModel) result.getData().getExtras().getSerializable("ALUMNO");
                                if (alumnoModel != null){
                                    alumnoModelArrayList.add(alumnoModel);
                                    mostrarAlumnos();
                                }
                            }
                        }
                    }
                }
        );
    }

    private void mostrarAlumnos() {
        binding.contentMain.contenedorMain.removeAllViews();

        for (AlumnoModel alumno : alumnoModelArrayList) {
            TextView txtAlumno = new TextView(MainActivity.this);
            txtAlumno.setText(alumno.toString());
            binding.contentMain.contenedorMain.addView(txtAlumno);
        }
    }

}