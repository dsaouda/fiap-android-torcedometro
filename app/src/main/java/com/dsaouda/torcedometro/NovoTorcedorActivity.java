package com.dsaouda.torcedometro;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dsaouda.torcedometro.dao.ClubeDAO;
import com.dsaouda.torcedometro.dao.TorcedorDAO;
import com.dsaouda.torcedometro.model.Clube;
import com.dsaouda.torcedometro.model.Torcedor;

import java.util.List;

public class NovoTorcedorActivity extends AppCompatActivity {

    public final static int CODE_NOVO_TORCEDOR = 1002;
    private TextInputLayout tilNomeTorcedor;
    private Spinner spClube;
    private List<Clube> clubes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_torcedor);

        tilNomeTorcedor = (TextInputLayout) findViewById(R.id.tilNomeTorcedor);
        spClube = (Spinner) findViewById(R.id.spClube);
        ClubeDAO clubeDAO = new ClubeDAO(this);
        clubes = clubeDAO.getAll();

        ArrayAdapter<Clube> adapter = new ArrayAdapter<Clube>(getApplicationContext(), R.layout.clube_spinner_item, clubes);
        adapter.setDropDownViewResource(R.layout.clube_spinner_item);
        spClube.setAdapter(adapter);
    }

    public void cadastrar(View v) {
        TorcedorDAO torcedorDAO = new TorcedorDAO(this);
        Torcedor torcedor = new Torcedor();
        torcedor.setNome(tilNomeTorcedor.getEditText().getText().toString());
        torcedor.setClube((Clube)spClube.getSelectedItem());
        torcedorDAO.add(torcedor);
        retornaParaTelaAnterior();
    }

    public void retornaParaTelaAnterior() {
        Intent intentMessage = new Intent();
        setResult(CODE_NOVO_TORCEDOR, intentMessage);
        finish();
    }
}
