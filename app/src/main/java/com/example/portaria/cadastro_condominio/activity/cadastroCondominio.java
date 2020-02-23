package com.example.portaria.cadastro_condominio.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.portaria.cadastro_condominio.R;

import java.io.File;

public class cadastroCondominio extends AppCompatActivity {

    TextView nomeNC ;
    Button salvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_condominio);


        nomeNC= findViewById(R.id.edtNomeNC);
        salvar= findViewById(R.id.btn_salvar_cadastro);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nomeNC.getText().toString().equals("")) {
                    criarDiretorios(nomeNC.getText().toString());

                }else{
                    Toast.makeText(getApplicationContext(),"escreva o nome do condominio para cadastro",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void criarDiretorios(String nome) {

        File pasta = new File(Environment.getExternalStorageDirectory() + "/.Cadastro_Condominio/" + nome + "/");


        if (!pasta.exists()) {
            pasta.mkdirs();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(R.string.sucesso_arquivo);
            alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.create();
            alert.show();
        }
    }
}
