package com.example.models;

import java.sql.Date;

public class ProdutoProducao{
    private int id;
    private String descricao;
    private int lote;
    private Date chegada;
    private String status;
    private String produtos;

            public ProdutoProducao (int id, String descricao, String status, String produtos, int lote, Date chegada){
                this.id = id;
                this.descricao = descricao;
                this.status = status;
                this.produtos = produtos;
                this.lote = lote;
                this.chegada = chegada;
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
            public String getStatus() {
                return status;
            }
            public void setStatus(String status) {
                this.status = status;
            }

}
