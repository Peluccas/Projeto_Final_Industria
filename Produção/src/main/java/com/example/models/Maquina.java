package com.example.models;

public class Maquina {
    private String nome;
    private int id;
    private String setor;
    private String descricao;
    
        public Maquina(int id, String nome, String descricao, String setor) {
            this.id = id;
            this.nome = nome;
            this.descricao = descricao;
            this.setor = setor;
        }
        public int getId() {
            return id;
        }
        public String getSetor(){
            return setor;
        }
        public String getNome() {
            return nome;
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
        public void setDescricao(String descricao){
            this.descricao = descricao;
        }
        public void setSetor(String setor){
            this.setor = setor;
        }

}