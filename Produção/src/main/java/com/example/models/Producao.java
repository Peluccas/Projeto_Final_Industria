package com.example.models;

public class Producao {
    private String produto;
    private String setor;
    private String maquina;
    private String responsavel;
    private String data;
    private int quantidade;

    // Construtor
    public Producao(int id, String produto, String maquina, String setor, String responsavel, int quantidade, String data) {
        this.produto = produto;
        this.maquina = maquina;
        this.setor = setor;
        this.responsavel = responsavel;
        this.quantidade = quantidade;
        this.data = data;
    }

    // Getters e Setters
    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getResp() {
        return responsavel;
    }

    public void setResp(String responsavel) {
        this.responsavel = responsavel;
    }

    public int getQuant() {
        return quantidade;
    }

    public void setQuant(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
