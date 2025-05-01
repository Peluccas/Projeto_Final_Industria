package com.example.models;

import java.sql.Date;

public class ProdutoConferido {
    private int id; 
    private String selo;
    private String descricao;
    private int lote;
    private Date chegada;
    private Date saida;
    private String status;
    private String produtos;

            public ProdutoConferido (int id, String selo, String descricao, String status, String produtos, int lote, Date chegada, Date saida){
                this.id = id;
                this.selo = selo;
                this.descricao = descricao;
                this.status = status;
                this.produtos = produtos;
                this.lote = lote;
                this.chegada = chegada;
                this.saida = saida;
            }

            public int getId() {
                return id;
            }
            public void setId(int id){
                this.id = id;
            }
            public String getProdutos() {
                return produtos;
            }
            public void setProdutos(String produtos) {
                this.produtos = produtos;
            }
            public String getSelo() {
                return selo;
            }
            public void setSelo(String selo) {
                this.selo = selo;
            }
            public String getDescricao() {
                return descricao;
            }
            public void setDescricao(String descricao) {
                this.descricao = descricao;
            }
            public int getLote() {
                return lote;
            }
            public void setLote(int lote) {
                this.lote = lote;
            }
            public Date getChegada() {
                return chegada;
            }
            public void setChegada(Date chegada) {
                this.chegada = chegada;
            }
            public Date getSaida() {
                return saida;
            }
            public void setSaida(Date saida) {
                this.saida = saida;
            }
            public String getStatus() {
                return status;
            }
            public void setStatus(String status) {
                this.status = status;
            }

}
