package com.example.models;




public class Solicitacoes {
    private int id;
    private String data_solicitacao;
    private int fk_setor;
    private String descricao;
    private String quantidade;
    private double valor;
    private String prazo;
    private String status;


public Solicitacoes(int id, String data_solicitacao, int fk_setor, String descricao, String quantidade, double valor, String prazo, String status){
    this.id = id;
    this.data_solicitacao = data_solicitacao;
    this.fk_setor = fk_setor;
    this.descricao = descricao;
    this.quantidade = quantidade;
    this.valor = valor;
    this.prazo = prazo;
    this.status = status;
}

public int getId(){
    return id;
}

public void setId(int id){
    this.id = id;
}

public String getData_solicitacao(){
    return data_solicitacao;
}

public void setData_solicitacao(String data_solicitacao){
    this.data_solicitacao = data_solicitacao;
}

public int getFk_setor(){
    return fk_setor;
}

public void setFk_setor(int fk_setor){
    this.fk_setor = fk_setor;
}

public String getDescricao(){
    return descricao;
}

public void setDescricao(String descricao){
    this.descricao = descricao;
}

public String getQuantidade(){
    return quantidade;
}

public void setQuantidade(String quantidade){
    this.quantidade = quantidade;
}

public double getValor(){
    return valor;
}

public void setValor(double valor){
    this.valor = valor;
}

public String getPrazo(){
    return prazo;
}

public void setPrazo(String prazo){
    this.prazo = prazo;
}

public String getStatus(){
    return status;
}

public void setStatus(String status){
    this.status = status;
}

}