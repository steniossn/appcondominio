package com.example.portaria.cadastro_condominio.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.portaria.cadastro_condominio.R;
import com.example.portaria.cadastro_condominio.util.Ligar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class tela1 extends AppCompatActivity {

    boolean aceito = true;
    private Spinner spCondominio;
    private Spinner spCasa;
    private TextView  lbl_casa;
    private TextInputEditText edtPesquisar;
    private CheckBox cb_fixar;
    private String texto;
    private Button btn_ok;
    File pasta = new File( Environment.getExternalStorageDirectory() + "/.Cadastro_Condominio/" );

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tela1 );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        spCondominio = findViewById( R.id.spinnerCondominio );
        spCasa = findViewById( R.id.spinnerCasa );
        edtPesquisar = findViewById( R.id.edtPesquisar );
        cb_fixar = findViewById( R.id.cb_fixar );
        lbl_casa = findViewById( R.id.lbl_casa );
        btn_ok = findViewById( R.id.btn_ok );


        //barra com nome e menu do app
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

           Criardiretorios();

           // adiciona ação ao botão enter de teclado
           edtPesquisar.setOnKeyListener( new View.OnKeyListener() {
               public boolean onKey(View v, int keyCode, KeyEvent event) {
                   // If the event is a key-down event on the "enter" button
                   if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                           (keyCode == KeyEvent.KEYCODE_ENTER)) {
                       // Perform action on key press
                       ok();
                       return true;
                   }
                   return false;
               }
           } );

            //adicionar ação ao click no campo pesquisar
          edtPesquisar.setOnClickListener( new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                edtPesquisar.setText( "" );
               }
           } );




           //evento de clique do spinner condominio
           spCondominio.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                   //verifica se tem algum nome no spinner condominio e spinner casa
                   preencherSpinnerCasa();


               }

               @Override
               public void onNothingSelected(AdapterView <?> parent) {

               }
           } );

           cb_fixar.setOnClickListener( new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onResume();
               }
           } );

           btn_ok.setOnClickListener( new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                        ok();
               }
           } );

       }


    @Override
    public void onResume() {
        super.onResume();
            if (pasta.exists()) {
                marcardo();
                preencherSpinnerCasa();
            }
           // edtPesquisar.setText( "Pesquise aqui por nome ou placa" );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_tela1, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_cadastrarCon:
                //chamar tela com um novo layout para escrever o nome
                Intent intent_cadastro = new Intent( getApplicationContext(), cadastroCondominio.class );
                startActivity( intent_cadastro );
                break;
            case R.id.action_cadastrarCasa:
                Ligar.ok = false;
                //chamar tela com um novo layout para escrever o nome
                Ligar.condominio = spCondominio.getSelectedItem().toString();
                if (!Ligar.condominio.equals( "nenhum condominio cadastrado" )) {
                    Intent intent_cadastrarCasa = new Intent( getApplicationContext(), tela2.class );
                    startActivity( intent_cadastrarCasa );
                    break;
                } else {
                    Toast.makeText( getApplicationContext(), "cadastre um condominio!", Toast.LENGTH_LONG ).show();
                    break;
                }
           case R.id.action_backup:
                 // File pasta = new File( String.format( "%s/.Cadastro_Condominio/", Environment.getExternalStorageDirectory() ) );
                 //pegar a pasta e enviar para o googledrive

               // novo alertDialog
               AlertDialog.Builder ab = new AlertDialog.Builder( this );
               ab.setTitle( R.string.aviso );
               ab.setMessage( R.string.addfuturo );
               ab.create();
               ab.show();
                break;

            case R.id.action_apagar_tudo:
                deletePasta();
                break;
            case R.id.action_delete:
                Delete();
                break;

        }

        return super.onOptionsItemSelected( item );
    }


    //************************metodos***********************************
    public void Criardiretorios() {

        File noMedia = new File( Environment.getExternalStorageDirectory() + "/.Cadastro_Condominio/.nomedia" );

        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        if (!noMedia.exists()) {
            try {
                noMedia.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    //add os itens do spinner Condominio
    public void preencherSpinnerCondominio() {


        ArrayList <String> condominios = new ArrayList <>();
        if (pasta.list() != null && pasta.list().length != 1) {
            String[] s = pasta.list();
            for (int i = 0; i < s.length; i++) {
                condominios.add( s[i] );
            }
            condominios.remove( ".nomedia" );
            ArrayAdapter <String> adapter = new ArrayAdapter <>( this, android.R.layout.simple_spinner_dropdown_item, condominios );
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            spCondominio.setAdapter( adapter );
        } else {
            String[] vazio = new String[]{getString( R.string.msg_spCondominio )};
            ArrayAdapter <String> adapter = new ArrayAdapter <>( this, android.R.layout.simple_spinner_dropdown_item, vazio );
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            spCondominio.setAdapter( adapter );
        }

    }

    //add os itens do spinner casa
    public void preencherSpinnerCasa() {
        if (!spCondominio.getSelectedItem().toString().equals( "nenhum condominio cadstrado" )) {
            File pasta = new File( Environment.getExternalStorageDirectory() + "/.Cadastro_Condominio/" + spCondominio.getSelectedItem().toString() );
            ArrayList <String> casas = new ArrayList <>();
            if (pasta.list() != null && pasta.list().length != 0) {
                String[] s = pasta.list();
                for (int i = 0; i < s.length; i++) {
                    casas.add( s[i] );
                }
                ArrayAdapter <String> adapter = new ArrayAdapter <>( this, android.R.layout.simple_spinner_dropdown_item, ordenar( casas ) );
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spCasa.setAdapter( adapter );
            } else {
                String[] vazio = new String[]{getString( R.string.msg_spCasa )};
                ArrayAdapter <String> adapter = new ArrayAdapter <>( this, android.R.layout.simple_spinner_dropdown_item, vazio );
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spCasa.setAdapter( adapter );
            }

        } else {
            String[] vazio = new String[]{getString( R.string.msg_spCasa )};
            ArrayAdapter <String> adapter = new ArrayAdapter <>( this, android.R.layout.simple_spinner_dropdown_item, vazio );
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            spCasa.setAdapter( adapter );
        }


    }

    //apaga arquivos dentro das pastas
    public void Delete() {
        Ligar.condominio = spCondominio.getSelectedItem().toString();
        Ligar.casa = spCasa.getSelectedItem().toString();
        if (!Ligar.casa.equals( "nenhuma casa cadastrada" )) {
            final File arquivo = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + "/.Cadastro_Condominio/" + Ligar.condominio + "/" + Ligar.casa );

            if (arquivo.exists()) {

                AlertDialog.Builder alert = new AlertDialog.Builder( this );
                alert.setTitle( R.string.title_alert_apagar );
                alert.setMessage( "apagar " + Ligar.casa + "  apagara todos os dados referentes a ele!\napagar?" );
                alert.setPositiveButton( "sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arquivo.delete();
                        preencherSpinnerCasa();
                    }
                } );
                alert.setNegativeButton( "não", null );

                alert.create();
                alert.show();
            }

        } else {
            Toast.makeText( getApplicationContext(), R.string.msg_erro_arquivo, Toast.LENGTH_LONG ).show();
        }

        onResume();

    }

    //apaga a pasta e tudo q ela conter
    public void deletePasta() {
        Ligar.condominio = spCondominio.getSelectedItem().toString();
        AlertDialog.Builder alert = new AlertDialog.Builder( this );
        alert.setTitle( R.string.title_alert_apagar );
        alert.setMessage( "apagar " + Ligar.condominio + "  apagara todos os dados referentes a ele!\napagar?" );
        alert.setPositiveButton( "sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File pasta = new File( Environment.getExternalStorageDirectory() + "/.Cadastro_Condominio/" + Ligar.condominio + "/" );
                ArrayList <String> casas = new ArrayList <>();
                if (pasta.list() != null && pasta.list().length != 0) {
                    String[] s = pasta.list();
                    for (int i = 0; i < s.length; i++) {
                        casas.add( s[i] );
                    }
                    int n = -1;
                    while (pasta.listFiles().length != 0) {
                        n++;
                        File arquivo = new File(
                                Environment.getExternalStorageDirectory().getAbsolutePath() + "/.Cadastro_Condominio/" + Ligar.condominio + "/" + casas.get( n ) );

                        arquivo.delete();
                    }
                }
                if (pasta.exists()) {
                    pasta.delete();
                    cb_fixar.setChecked( false );
                    Toast.makeText( getApplicationContext(), R.string.msg_apagado, Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( getApplicationContext(), R.string.msg_erro_arquivo, Toast.LENGTH_LONG ).show();
                }

                onResume();
            }
        } );
        alert.setNegativeButton( "não", null );
        alert.create();
        alert.show();
    }

    // organiza em ordem crescente os nomes mostrados no metodo(preencherSpinnerCasa)
    public ArrayList ordenar(ArrayList array) {

        ArrayList <String> ordenado = new ArrayList <>();
        int n = 0;
        //int n2=-1;
        for (int i = 0; ordenado.size() < array.size(); i++) {
            n++;
            if (array.contains( "casa_" + n )) {
                ordenado.add( "casa_" + n );
            }
        }
        return ordenado;
    }

    //verifica se a opção manter selecionado esta marcada
    public void marcardo() {

        if(pasta != null && pasta.exists() ) {
            if ( pasta.list().length < 3) {
                cb_fixar.setVisibility( View.INVISIBLE );
                lbl_casa.setTranslationY( -80 );
                spCasa.setTranslationY( -80 );
            } else {
                cb_fixar.setVisibility( View.VISIBLE );
                lbl_casa.setTranslationY( 0 );
                spCasa.setTranslationY( 0 );
            }
            if (cb_fixar.isChecked()) {
                texto = spCondominio.getSelectedItem().toString();
                String[] fixar = new String[]{texto};
                ArrayAdapter <String> adapter = new ArrayAdapter <>( this, android.R.layout.simple_spinner_dropdown_item, fixar );
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spCondominio.setAdapter( adapter );
                preencherSpinnerCasa();
            } else {
                preencherSpinnerCondominio();
                preencherSpinnerCasa();
            }
        }

    }

    public void ok(){
        //chama outra layout com as informaçoes da casa que estava no spinner casa
        Ligar.ok = true;
      //  Ligar.pesquisar = edtPesquisar.toString();
        Ligar.pesquisar = edtPesquisar.getText().toString();

        if (!spCasa.getSelectedItem().toString().equals( "nenhuma casa cadastrada" ) &&
                !spCondominio.getSelectedItem().toString().equals( "nenhum condominio cadastrado" )
                && !edtPesquisar.getText().toString().isEmpty() ||
                !edtPesquisar.getText().toString().equals( "Pesquise por nome ou placa" ))
        {
            Ligar.condominio = spCondominio.getSelectedItem().toString();
            Ligar.casa = spCasa.getSelectedItem().toString();
            Ligar.pesquisar = edtPesquisar.getText().toString();
            Intent intent = new Intent( getApplicationContext(), tela2.class );
            startActivity( intent );

        } else {
            Toast.makeText( getApplicationContext(), R.string.msg_spCasa, Toast.LENGTH_LONG ).show();

        }
    }


}
