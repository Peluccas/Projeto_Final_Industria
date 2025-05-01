package com.example.models;

public class QFuncionario {
    private Integer total;
    private Integer aprovados;
    private Integer reprovados;
    private Integer concertos;


            public QFuncionario(int total, int aprovados, int reprovados, int concertos) {
                this.total = (total);
                this.aprovados = (aprovados);
                this.reprovados = (reprovados);
                this.concertos = (concertos);
            }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getAprovados() {
        return aprovados;
    }

    public void setAprovados(Integer aprovados) {
        this.aprovados = aprovados;
    }

    public Integer getReprovados() {
        return reprovados;
    }

    public void setReprovados(Integer reprovados) {
        this.reprovados = reprovados;
    }
    public Integer getConcertos() {
        return concertos;
    }

    public void setConcertos(Integer concertos) {
        this.concertos = concertos;
    }

}