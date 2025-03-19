package com.example.models;

import java.util.Date;

public class Manutencao {
    private int id;
    private int equipamentoId;
    private String equipamentoNome;
    private String tipoManutencao;
    private Date dataInicio;
    private Date dataConclusao;
    private String status;
    private String descricaoServico;

    public Manutencao(int id, int equipamentoId, String equipamentoNome, String tipoManutencao, 
                      Date dataInicio, Date dataConclusao, String status, String descricaoServico) {
        this.id = id;
        this.equipamentoId = equipamentoId;
        this.equipamentoNome = equipamentoNome;
        this.tipoManutencao = tipoManutencao;
        this.dataInicio = dataInicio;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.descricaoServico = descricaoServico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEquipamentoId() {
        return equipamentoId;
    }

    public void setEquipamentoId(int equipamentoId) {
        this.equipamentoId = equipamentoId;
    }

    public String getEquipamentoNome() {
        return equipamentoNome;
    }

    public void setEquipamentoNome(String equipamentoNome) {
        this.equipamentoNome = equipamentoNome;
    }

    public String getTipoManutencao() {
        return tipoManutencao;
    }

    public void setTipoManutencao(String tipoManutencao) {
        this.tipoManutencao = tipoManutencao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(Date dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    /**
     * Verifica se a manutenção está atrasada comparando com a data atual
     * @return true se estiver atrasada, false caso contrário
     */
    public boolean isAtrasada() {
        if (dataConclusao == null || !status.equals("Em Manutenção")) {
            return false;
        }
        
        return dataConclusao.before(new Date()) && status.equals("Em Manutenção");
    }

    @Override
    public String toString() {
        return equipamentoNome + " - " + tipoManutencao;
    }
}
