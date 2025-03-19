package com.example.models;



public class Setores {
    private int id;
    private String nome_setor;
    


public Setores(int id, String nome_setor){
    this.id = id;
    this.nome_setor = nome_setor;
}

public int getId(){
    return id;
}

public void setId(int id){
    this.id = id;
}

public String getNome_setor(){
    return nome_setor;
}

public void setNome_setor(String nome_setor){
    this.nome_setor = nome_setor;
}

}