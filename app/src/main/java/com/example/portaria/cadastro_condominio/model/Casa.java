package com.example.portaria.cadastro_condominio.model;

public class Casa {

    int numero;
    String nome;
    String telefone;
    String celular1;
    String celular2;
    String carro1;
    String carro2 ;
    String carro3 ;
    String carro4 ;
    String carro5 ;
    String diarista;
    String dica;

    public Casa() {
    }

    public Casa(int numero, String nome) {
        this.numero = numero;
        this.nome = nome;
    }



    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular1() {
        return celular1;
    }

    public void setCelular1(String celular1) {
        this.celular1 = celular1;
    }

    public String getCelular2() {
        return celular2;
    }

    public void setCelular2(String celular2) {
        this.celular2 = celular2;
    }

    public String getCarro1() {
        return carro1;
    }

    public void setCarro1(String carro1) {
        this.carro1 = carro1;
    }

    public String getCarro2() {
        return carro2;
    }

    public void setCarro2(String carro2) {
        this.carro2 = carro2;
    }

    public String getCarro3() {
        return carro3;
    }

    public void setCarro3(String carro3) {
        this.carro3 = carro3;
    }

    public String getCarro4() {
        return carro4;
    }

    public void setCarro4(String carro4) {
        this.carro4 = carro4;
    }

    public String getCarro5() {
        return carro5;
    }

    public void setCarro5(String carro5) {
        this.carro5 = carro5;
    }

    public String getDiarista() {
        return diarista;
    }

    public void setDiarista(String diarista) {
        this.diarista = diarista;
    }

    public String getDica() {
        return dica;
    }

    public void setDica(String dica) {
        this.dica = dica;
    }
}
