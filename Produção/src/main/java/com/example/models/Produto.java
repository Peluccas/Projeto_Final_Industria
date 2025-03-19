package com.example.models;

public class Produto {
    private int id;
    private String nome;
    private int quantidade;
    private double preco;
    private String descricao;

    public Produto(int id, String nome, int quantidade, double preco, String descricao) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }
    public void setPreco(Double preco){
        this.preco = preco;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
}