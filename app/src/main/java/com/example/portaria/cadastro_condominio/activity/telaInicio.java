package com.example.portaria.cadastro_condominio.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.portaria.cadastro_condominio.R;

public class telaInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tela_inicio );
        permissao();


    }



    //metodo que verifica se a permissão de escrita no sdCard foi concedida
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent( getApplicationContext(), tela1.class );
                    startActivity( intent );
                    finish();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                }else{
                    AlertDialog.Builder ab = new AlertDialog.Builder( this );
                    ab.setTitle( "aviso!" );
                    ab.setMessage( "Essa permissão de armazenamento é necessaria para o funcionamento do APP!" );
                    ab.setPositiveButton( "ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            permissao();

                        }
                    } );
                    ab.setNeutralButton( "cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }
                    } );

                    //impede que o clic fora do alertDialog saia do alertDialog
                    ab.setCancelable( false );
                    //cria a view
                    ab.create();
                    ab.show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }


            }


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    public void permissao() {
        // explicar a necessidade
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE )) {

            //colocar aqui explicação para o usuario da necessidade da permissão solicitada

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100 );


        }

        else {
            // Nenhuma explicação necessária, podemos solicitar a permissão.

            ActivityCompat.requestPermissions( this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100 );

        }
    }





}
