package com.cieep.a04_creacindevistasporcdigo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cieep.a04_creacindevistasporcdigo.databinding.ActivityAddAlumnoBinding;
import com.cieep.a04_creacindevistasporcdigo.modelos.AlumnoModel;

public class AddAlumnoActivity extends AppCompatActivity {

    private ActivityAddAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelarAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlumnoModel alumnoModel = crearAlumno();
                if (alumnoModel != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ALUMNO", alumnoModel);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toast.makeText(AddAlumnoActivity.this, "Faltan Datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private AlumnoModel crearAlumno() {
        if (binding.txtNombreAddAlumno.getText().toString().isEmpty() || binding.txtApellidosAddAlumno.getText().toString().isEmpty())
            return null;
        if (binding.spCiclosAddAlumno.getSelectedItemPosition() == 0)
            return null;
        if (!binding.rbGrupoAAddAlumno.isChecked() && !binding.rbGrupoBAddAlumno.isChecked() && !binding.rbGrupoCAddAlumno.isChecked())
            return null;

        String ciclo = (String) binding.spCiclosAddAlumno.getSelectedItem();
        RadioButton rb = findViewById(binding.rgGrupoAddAlumno.getCheckedRadioButtonId());
        char grupo = rb.getText().charAt(0);
        return new AlumnoModel(binding.txtNombreAddAlumno.getText().toString(),
                binding.txtApellidosAddAlumno.getText().toString(), ciclo, grupo);
    }
}