package com.example.controllers;

import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;


public class DetalhadoController{
    
    @FXML private RadioButton s1;
    @FXML private RadioButton s10;
    @FXML private RadioButton s11;
    @FXML private RadioButton s12;
    @FXML private RadioButton s13;
    @FXML private RadioButton s14;
    @FXML private RadioButton s15;
    @FXML private RadioButton s16;
    @FXML private RadioButton s17;
    @FXML private RadioButton s18;
    @FXML private RadioButton s2;
    @FXML private RadioButton s3;
    @FXML private RadioButton s4;
    @FXML private RadioButton s5;
    @FXML private RadioButton s6;
    @FXML private RadioButton s7;
    @FXML private RadioButton s8;
    @FXML private RadioButton s9;
    @FXML private RadioButton n1;
    @FXML private RadioButton n10;
    @FXML private RadioButton n11;
    @FXML private RadioButton n12;
    @FXML private RadioButton n13;
    @FXML private RadioButton n14;
    @FXML private RadioButton n15;
    @FXML private RadioButton n16;
    @FXML private RadioButton n17;
    @FXML private RadioButton n18;
    @FXML private RadioButton n2;
    @FXML private RadioButton n3;
    @FXML private RadioButton n4;
    @FXML private RadioButton n5;
    @FXML private RadioButton n6;
    @FXML private RadioButton n7;
    @FXML private RadioButton n8;
    @FXML private RadioButton n9;
    @FXML private Label textResult;
    @FXML private CheckBox checkSelo;
    @FXML private Button btnConfirmarDetalhado;
    @FXML private Button btnSairDetalhado;
    
    private CheckBox checkSeloMain;
    private List<RadioButton> simButtons;
    private List<RadioButton> naoButtons;

    @FXML
    void btnConfirmarDetalhadoAct(ActionEvent event) {        
        Stage stage = (Stage) btnConfirmarDetalhado.getScene().getWindow();
        mostrarAlerta(Alert.AlertType.INFORMATION,"Sucesso","Lote Conferido ");
        habilitarCheckSelo();
        stage.close();
    }

    @FXML
    void btnSairDetalhadoAct(ActionEvent event) {
        Stage stage = (Stage) btnConfirmarDetalhado.getScene().getWindow();
        mostrarAlerta(Alert.AlertType.INFORMATION,"Reprovado","Teste de Validação de Lote foi Reprovado ");
        stage.close();
    }


     
    @FXML
    public void initialize() {
        btnConfirmarDetalhado.setDisable(true);
        simButtons = Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18);
        naoButtons = Arrays.asList(n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18);

        for (int i = 0; i < simButtons.size(); i++) {
            final int index = i;

            simButtons.get(i).setOnAction(e -> {
                if (simButtons.get(index).isSelected()) {
                    naoButtons.get(index).setSelected(false);
                }
                verificarStatus();
            });

            naoButtons.get(i).setOnAction(e -> {
                if (naoButtons.get(index).isSelected()) {
                    simButtons.get(index).setSelected(false);
                }
                verificarStatus();
            });
        }
        btnSairDetalhado.setDisable(true);
    }

    public void setCheckSelo(CheckBox checkSelo) {
        this.checkSeloMain = checkSelo;
    }

    private void verificarStatus() {
        int totalNao = 0;
        int totalSim = 0;

        for (int i = 0; i < naoButtons.size(); i++) {
            if (naoButtons.get(i).isSelected()) totalNao++;
            if (simButtons.get(i).isSelected()) totalSim++;
        }
        if (totalSim == simButtons.size()) {
            textResult.setText("Aprovado com Sucesso (Verificado)");
            btnConfirmarDetalhado.setDisable(false);
            
        } else if (totalNao > 1) {
            textResult.setText("Reprovado Mais de 1 erro (Conserto)");
            desabilitarCheckSelo();
            btnSairDetalhado.setDisable(false);
            btnConfirmarDetalhado.setDisable(true);  
        } else {
            textResult.setText("Em avaliação");
            desabilitarCheckSelo();
            btnConfirmarDetalhado.setDisable(true);
        }
    }

    private void habilitarCheckSelo() {
        if (checkSeloMain != null) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação");
            confirmacao.setHeaderText("Teste de Aprovação de lote");
            confirmacao.setContentText("Tem certeza que todos Requisitos estão aprovado?");
            ButtonType sim = new ButtonType("Sim");
            ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmacao.getButtonTypes().setAll(sim, nao);
            confirmacao.showAndWait().ifPresent(resposta -> {
            checkSeloMain.setDisable(false);  });
        }
    }

    private void desabilitarCheckSelo() {
        if (checkSeloMain != null) {
            checkSeloMain.setDisable(true);  
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
        



    
}
