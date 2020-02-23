package com.example.portaria.cadastro_condominio.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.portaria.cadastro_condominio.R;
import com.example.portaria.cadastro_condominio.util.Ligar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.portaria.cadastro_condominio.util.Ligar.pesquisar;

public class tela2 extends AppCompatActivity {


    private Button btSalvar;
    private Button btLimpar;
    private TextView edtCasa;
    private TextView edtNome;
    private TextView edtFixo;
    private TextView edtCel1;
    private TextView edtCel2;
    private TextView edtCarro1;
    private TextView edtCarro2;
    private TextView edtCarro3;
    private TextView edtCarro4;
    private TextView edtCarro5;
    private TextView edtDica;
    private TextView edtDiarista;
    private TextView edtCampo;
    private ArrayList <Integer> item = new ArrayList <>();
    private int indexP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tela2 );
        //setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        btSalvar = findViewById( R.id.btSalvar );
        btLimpar = findViewById( R.id.btLimpar );
        edtCasa = findViewById( R.id.edtCasa );
        edtNome = findViewById( R.id.edtNome );
        edtFixo = findViewById( R.id.edtFixo );
        edtCel1 = findViewById( R.id.edtCel1 );
        edtCel2 = findViewById( R.id.edtCel2 );
        edtCarro1 = findViewById( R.id.edtCarro1 );
        edtCarro2 = findViewById( R.id.edtCarro2 );
        edtCarro3 = findViewById( R.id.edtCarro3 );
        edtCarro4 = findViewById( R.id.edtCarro4 );
        edtCarro5 = findViewById( R.id.edtCarro5 );
        edtDica = findViewById( R.id.edtDica );
        edtDiarista = findViewById( R.id.edtDiarista );
        edtCampo = findViewById( R.id.edtCampo );


        if (Ligar.ok == true) {
            if (pesquisar.isEmpty() || Ligar.pesquisar.equals( "Pesquise aqui por nome ou placa" )) {
                Mostrar();
            } else {
                pesquisar();
            }
        }

        btLimpar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Limpar();
            }
        } );

        btSalvar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editar();

            }
        } );
    }



    //usado para veificar se a entrada do usuario existe em algum arquivo
    public void pesquisar() {

        if(Ligar.condominio.equals( "nenhum condominio cadastrado" ) | Ligar.casa.equals( "nenhuma casa cadastrada" )){
            Toast.makeText( getApplicationContext(), R.string.msg_spCasa, Toast.LENGTH_LONG ).show();

        }else{
            boolean terminar = false;
            //n1 numero da casa
            //n2 index da palavra na lista
            //n3 index no array ordenar
            int n1 = 0, n2 ,n3 =-1;


            File pasta = new File( Environment.getExternalStorageDirectory() + "/.Cadastro_Condominio/" + Ligar.condominio + "/" );
            //resultado recebe o nome das casas que contem o que foi pesquisado
            final ArrayList <String> resultado = new ArrayList <>();
            //recebe o nome dos arquivos que estão na pasta
            ArrayList <String> casas = new ArrayList <>();

            if (pasta.list() != null && pasta.list().length != 0) {
                String[] s = pasta.list();
                for (int i = 0; i < s.length; i++) {
                    casas.add( s[i] );
                }
            }
            //recebe o nome dos arquivos em ordem
            ArrayList<String> ordenar = ordenar( casas );
            do {
                n1++;
                String casa = "casa_" + Integer.toString( n1 );
                //se lista de casas tiver a casa procurada
                if (ordenar.contains( casa )) {
                    n3++;

                    //verifica o arquivo e se contiver a palavra pesquisada add o nome do arquivo ao array resultado
                    if (lerStringPalavras( ordenar.get( n3 )).contains( Ligar.pesquisar )) {
                        resultado.add(ordenar.get( n3 ) );
                        n2 = -1;
                        //percorre todas as linha verificando se tem a palavra procurada, se tiver essa linha sera o n2
                      do {
                            n2++;
                            if (lerString( casa ).get( n2 ).contains( Ligar.pesquisar )) {
                                n2++;
                                item.add( n2 );
                                n2 = 13;
                            }
                        } while (n2 < 13);
                    }

                    //se
                    if (n3 >= pasta.list().length -1) {
                        terminar = true;

                        if (resultado.size() == 0) {
                            Toast.makeText( getApplicationContext(), R.string.erro_arquivo, Toast.LENGTH_LONG ).show();
                            finish();
                        }
                    }
                }
            }while (terminar == false);


           if (resultado.size() > 1) {
                // msg apresentada no alertDialog
                StringBuilder msg = new StringBuilder();
                msg.append( "existe " ).append( Ligar.pesquisar ).append( " nas casas " );

                // add o layout xml a view que abrira na tela
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate( R.layout.ad_resultado,null);

                // novo alertDialog
                AlertDialog.Builder ab = new AlertDialog.Builder( this );
                ab.setTitle( "aviso!" );
                ab.setMessage( msg );
                //add o layout modificado  a view
                ab.setView( view );
                // faz referencia ao spinner do layout ad_resultado
                final Spinner spinner_alert = view.findViewById( R.id. spinner_alert );
                // adiciona o arraylist resultado ao spinner
                ArrayAdapter itens = new ArrayAdapter <>( this, android.R.layout.simple_spinner_dropdown_item, resultado );
                itens.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinner_alert.setAdapter( itens );

                //muda a ação dos botoes
                ab.setPositiveButton( "ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        indexP = item.get( resultado.indexOf( spinner_alert.getSelectedItem().toString() ) );
                        addMostrar( lerString( spinner_alert.getSelectedItem().toString() ) );
                        Toast.makeText( getApplicationContext(),spinner_alert.getSelectedItem().toString() , Toast.LENGTH_LONG ).show();

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
           } else if (resultado.size() == 1) {
                indexP = item.get( 0 );
                addMostrar( lerString( resultado.get( 0 ) ) );
           }else {
               finish();
           }

        }


    }


    //metodo que muda todos os campos para vazio
    public void Limpar() {
        edtCasa.setText( "" );
        edtNome.setText( "" );
        edtFixo.setText( "" );
        edtCel1.setText( "" );
        edtCel2.setText( "" );
        edtCarro1.setText( "" );
        edtCarro2.setText( "" );
        edtCarro3.setText( "" );
        edtCarro4.setText( "" );
        edtCarro5.setText( "" );
        edtDica.setText( "" );
        edtDiarista.setText( "" );
        edtCampo.setText( "" );
    }

    //MUDA OS CAMPOS DO APP PARA OS ENCONTRADOS NO ARQUIVO DE TEXTO usa o metodo (addmostrar + lerString)
    public void Mostrar() {
        addMostrar( lerString( Ligar.casa ) );
    }

    //usado no metodo (mostrar) para add texto as views
    public void addMostrar(ArrayList <String> array) {
        edtCasa.setText( array.get( 0 ) );
        edtNome.setText( array.get( 1 ) );
        edtFixo.setText( array.get( 2 ) );
        edtCel1.setText( array.get( 3 ) );
        edtCel2.setText( array.get( 4 ) );
        edtCarro1.setText( array.get( 5 ) );
        edtCarro2.setText( array.get( 6 ) );
        edtCarro3.setText( array.get( 7 ) );
        edtCarro4.setText( array.get( 8 ) );
        edtCarro5.setText( array.get( 9 ) );
        edtDica.setText( array.get( 10 ) );
        edtDiarista.setText( array.get( 11 ) );
        edtCampo.setText( array.get( 12 ) );

        if (!item.isEmpty()) {
            switch (indexP+1) {
                case 1:
                    edtCasa.requestFocus();
                    break;
                case 2:
                    edtNome.requestFocus();
                    break;
                case 3:
                    edtFixo.requestFocus();
                    break;
                case 4:
                    edtCel1.requestFocus();
                    break;
                case 5:
                    edtCel2.requestFocus();
                    break;
                case 6:
                    edtCarro1.requestFocus();
                    break;
                case 7:
                    edtCarro2.requestFocus();
                    break;
                case 8:
                    edtCarro3.requestFocus();
                    break;
                case 9:
                    edtCarro4.requestFocus();
                    break;
                case 10:
                    edtCarro5.requestFocus();
                    break;
                case 11:
                    edtDica.requestFocus();
                    break;
                case 12:
                    edtDiarista.requestFocus();
                    break;
                case 13:
                    edtCampo.requestFocus();
                    break;
                default:
                    edtCasa.requestFocus();
            }
        } else {
            edtCasa.requestFocus();

        }

    }

    //usado no metodo(mostrar) para ler o arquivo de texto @paran arquivo é o nome do arquivo q sera lido
    public ArrayList <String> lerString(String arquivo) {
        try {

            File arq2 = new File( Environment.getExternalStorageDirectory() + "/.Cadastro_Condominio/" + Ligar.condominio + "/" + arquivo );
            BufferedReader br = new BufferedReader( new FileReader( arq2 ) );
            //scanner faz a leitura do arquivo linha por linha
            Scanner sc = new Scanner( br);
            //armazena o conteudo lido pelo scanner
            ArrayList <String> linhas = new ArrayList <>();

            //condicional para ler linha a linha e add ao array
            while (sc.hasNextLine()){
                linhas.add( sc.nextLine() );
            }


            //codigo usado para que edtCampo seja apresentado com quebra de linha no TextView
            if (linhas.size() >= 12) {
                StringBuffer campo = new StringBuffer();
                int n = 11;
                while (n < linhas.size() - 1) {
                    n++;
                    campo.append( linhas.get( n ) + "\n" );

                }
                while (linhas.size() > 13) {
                    n--;
                    linhas.remove( n );
                }
                linhas.add( 12, campo.toString() );
            }

            return linhas;

        } catch (FileNotFoundException e) {
            Toast.makeText( getApplicationContext(), R.string.erro_arquivo, Toast.LENGTH_SHORT ).show();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText( getApplicationContext(), R.string.erro_arquivo, Toast.LENGTH_SHORT ).show();
            return null;
        }

    }

    //usado no metodo(pesquisar) para ler o arquivo de texto
    public ArrayList <String> lerStringPalavras(String arquivo) {
        try {
            File arq2 = new File( Environment.getExternalStorageDirectory() + "/.Cadastro_Condominio/" + Ligar.condominio + "/" + arquivo );
            BufferedReader br = new BufferedReader( new FileReader( arq2 ) );
            //scanner faz a leitura do arquivo linha por linha
            Scanner sc = new Scanner( new FileReader( arq2 ) );
            ArrayList <String> palavras = new ArrayList <>();
            do {
                palavras.add( sc.next() );
            } while (sc.hasNext());

            return palavras;
        } catch (FileNotFoundException e) {
            Toast.makeText( getApplicationContext(), R.string.erro_arquivo, Toast.LENGTH_SHORT ).show();
            return null;
        }
    }

    //usado no metodo (editar) para criar um arraylist com os textos pegos das views
    public void addArrays(String casa) {
        ArrayList <String> arc1 = new ArrayList <>();
        arc1.add( edtCasa.getText().toString() + "\n" );
        arc1.add( edtNome.getText().toString() + "\n" );
        arc1.add( edtFixo.getText().toString() + "\n" );
        arc1.add( edtCel1.getText().toString() + "\n" );
        arc1.add( edtCel2.getText().toString() + "\n" );
        arc1.add( edtCarro1.getText().toString() + "\n" );
        arc1.add( edtCarro2.getText().toString() + "\n" );
        arc1.add( edtCarro3.getText().toString() + "\n" );
        arc1.add( edtCarro4.getText().toString() + "\n" );
        arc1.add( edtCarro5.getText().toString() + "\n" );
        arc1.add( edtDica.getText().toString() + "\n" );
        arc1.add( edtDiarista.getText().toString() + "\n" );
        arc1.add( edtCampo.getText().toString() );

        StringBuffer txt = new StringBuffer();
        int n = -1;
        while (n < arc1.size() - 1) {
            n++;
            txt.append( arc1.get( n ) );
        }
        salvarArquivo( txt.toString() );
    }

    // usado para se comunicar com usuario e salvar os dados atraves do metodo (addArrays)
    public void Editar() {
        AlertDialog.Builder perg1 = new AlertDialog.Builder( this );
        perg1
                .setMessage( R.string.input_pergunta )
                .setCancelable( false )
                .setPositiveButton( "sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        if (!edtCasa.getText().toString().equals( "" )) {
                            addArrays( "casa_" + Integer.parseInt( edtCasa.getText().toString() ) );
                            finish();
                        } else {
                            Toast.makeText( getApplicationContext(), R.string.erro_dig, Toast.LENGTH_SHORT ).show();
                        }
                    }

                } )
                .setNegativeButton( "cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText( getApplicationContext(), R.string.cancelar, Toast.LENGTH_SHORT ).show();
                    }
                } );

        AlertDialog alert = perg1.create();
        alert.show();


    }

    //usado no metodo (addArrays) para salvar o array criado  em um arquivo de txt
    public void salvarArquivo(String conteudoArquivo) {

        try {
            File arquivo = new File( Environment.getExternalStorageDirectory() + "/.Cadastro_Condominio/" + Ligar.condominio + "/casa_" + Integer.parseInt( edtCasa.getText().toString() ) );
            FileOutputStream salvar = new FileOutputStream( arquivo);
            salvar.write( conteudoArquivo.getBytes() );
            salvar.close();
            Toast.makeText( this, R.string.arquivo_salvo, Toast.LENGTH_SHORT ).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText( this, R.string.erro_gravarArquivo, Toast.LENGTH_SHORT ).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText( this, R.string.erro_gravarArquivo, Toast.LENGTH_SHORT ).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText( this, R.string.erro_gravarArquivo, Toast.LENGTH_SHORT ).show();
        }
    }

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


}