package com.example.models;

public class Producao {
        private int id; 
        private String nome; 
        private int quantidade;
        private double preco;
        private String localizacao;
        private String categoria; 
        private String descricao;
            
                public Producao(int id, String nome, int quantidade, double preco, String localizacao, String categoria, String descricao) {
                    this.id = id;
                    this.nome = nome;
                    this.quantidade = quantidade;
                    this.preco = preco;
                    this.localizacao = localizacao;
                    this.categoria = categoria;
                    this.descricao = descricao;
        }

        public int getId() {
            return id;
        }
        public void setId(int id){
            this.id = id;
        }
        public String getNome() {
            return nome;
        }
        public void setNome(String nome){
            this.nome = nome;
        }
        public int getQuantidade() {
            return quantidade;
        }
        public void setQuantidade(int quantidade){
            this.quantidade = quantidade;
        }
        public double getPreco() {
            return preco;
        }  
        public void setPreco(Double preco){
            this.preco = preco;
        } 
        public String getLocalizacao() {
            return localizacao;
        }
        public void setLocalizacao(String localizacao){
            this.localizacao = localizacao;
        }
        public String getCategoria() {
            return categoria;
        }
        public void setCategoria(String categoria){
            this.categoria = categoria;
        }
        public String getDescricao() {
            return descricao;
        }
        public void setDescricao(String descricao){
            this.descricao = descricao;
        }

}
