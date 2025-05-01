package com.example.models;

public class almoxarifado {
    private int id;
    private String nome;
    private int quantidade;
    private String marca;
    private String fornecedor;
    private String localizacao;
    private String categoria;
    private String codigo;
    private double precoDeCusto;

    public almoxarifado(int id, String nome, int quantidade, String marca, String fornecedor, 
                        String localizacao, String categoria, String codigo, double precoDeCusto) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.marca = marca;
        this.fornecedor = fornecedor;
        this.localizacao = localizacao;
        this.categoria = categoria;
        this.codigo = codigo;
        this.precoDeCusto = precoDeCusto;
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

    public String getMarca() {
        return marca;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPrecoDeCusto() {
        return precoDeCusto;
    }
    
    public void setPrecoDeCusto(double precoDeCusto) {
        this.precoDeCusto = precoDeCusto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

   

}

    

