package com.example.models;

import java.util.Date;

public class Equipamento {
    private int id;
    private String codigo;
    private String nome;
    private String categoria;
    private String modelo;
    private String numeroSerie;
    private String setor;
    private Date dataAquisicao;
    private double valorAquisicao;
    private String status;
    private boolean manutencaoPeriodica;
    private String observacoes;

    public Equipamento(int id, String codigo, String nome, String categoria, String modelo,
                      String numeroSerie, String setor, Date dataAquisicao, double valorAquisicao,
                      String status, boolean manutencaoPeriodica) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.categoria = categoria;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.setor = setor;
        this.dataAquisicao = dataAquisicao;
        this.valorAquisicao = valorAquisicao;
        this.status = status;
        this.manutencaoPeriodica = manutencaoPeriodica;
        this.observacoes = "";
    }

    // Getters
    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public String getModelo() { return modelo; }
    public String getNumeroSerie() { return numeroSerie; }
    public String getSetor() { return setor; }
    public Date getDataAquisicao() { return dataAquisicao; }
    public double getValorAquisicao() { return valorAquisicao; }
    public String getStatus() { return status; }
    public boolean isManutencaoPeriodica() { return manutencaoPeriodica; }
    public String getObservacoes() { return observacoes; }

    // Setters
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }
    public void setSetor(String setor) { this.setor = setor; }
    public void setDataAquisicao(Date dataAquisicao) { this.dataAquisicao = dataAquisicao; }
    public void setValorAquisicao(double valorAquisicao) { this.valorAquisicao = valorAquisicao; }
    public void setStatus(String status) { this.status = status; }
    public void setManutencaoPeriodica(boolean manutencaoPeriodica) { this.manutencaoPeriodica = manutencaoPeriodica; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    @Override
    public String toString() {
        return codigo + " - " + nome;
    }
}
