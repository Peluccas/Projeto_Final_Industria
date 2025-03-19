package com.example.models;


public class Fluxo{
    private int id;
    private String data_transacao;
    private int fk_setor;
    private String descricao;
    private double valor;
    private String categoria;
    private String forma_pagto;
    private String vencimento;
    private boolean status;


public Fluxo(int id, String data_transacao, int fk_setor, String descricao, double valor, String categoria, String forma_pagto, String vencimento, boolean status){
    this.id = id;
    this.data_transacao = data_transacao;
    this.fk_setor = fk_setor;
    this.descricao = descricao;
    this.valor = valor;
    this.categoria = categoria;
    this.forma_pagto = forma_pagto;
    this.vencimento = vencimento;
    this.status = status;
}

public int getId(){
    return id;
}

public void setId(int id){
    this.id = id;
}

public String getData_transacao(){
    return data_transacao;
}

public void setData_transacao(String data_transacao){
    this.data_transacao = data_transacao;
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

public double getValor(){
    return valor;
}

public void setValor(double valor){
    this.valor = valor;
}

public String getCategoria(){
    return categoria;
}

public void setCategoria(String categoria){
    this.categoria = categoria;
}

public String getForma_pagto() {
    return forma_pagto;
}

public void setForma_pagto(String forma_pagto) {
    this.forma_pagto = forma_pagto;
}

public String getVencimento(){
    return vencimento;
}

public void setVencimento(String vencimento){
    this.vencimento = vencimento;
}

public boolean getStatus(){
    return status;
}

public void setStatus(boolean status){
    this.status = status;
}
}