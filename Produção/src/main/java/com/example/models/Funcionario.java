package com.example.models;

public class Funcionario {
    private String nome;
    private String setor;
    private int id;
    
        public Funcionario(int id, String nome, String setor) {
            this.id = id;
            this.nome = nome;
            this.setor = setor;
        }
        public int getId() {
            return id;
        }
    
        public String getNome() {
            return nome;
        }
    
        public String getSetor() {
            return setor;
        }
        public void setId(int id){
            this.id = id;
        }

        public void setNome(String nome){
            this.nome = nome;
        }

        public void setSetor(String setor){
            this.setor = setor;
        }

}