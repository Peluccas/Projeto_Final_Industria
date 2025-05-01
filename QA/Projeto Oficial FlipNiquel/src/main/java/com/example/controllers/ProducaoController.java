package com.example.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.example.database.Database;
import com.example.models.ProdutoConferido;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class ProducaoController {
  

    @FXML private TableView<ProdutoConferido> tableEnvios;
    @FXML private TableColumn<ProdutoConferido, Integer> columChegadaAuto;
    @FXML private TableColumn<ProdutoConferido, String> columDescriAuto;
    @FXML private TableColumn<ProdutoConferido, Integer> columIdAuto;
    @FXML private TableColumn<ProdutoConferido, Integer> columLoteAuto;
    @FXML private TableColumn<ProdutoConferido, String> columProduAuto;
    @FXML private TableColumn<ProdutoConferido, Integer> columSaidaAuto;
    @FXML private TableColumn<ProdutoConferido, String> columSeloAuto;
    @FXML private Button btnBuscarEnvios;
    @FXML private TextField textEnvios;
    @FXML private TableColumn<ProdutoConferido, String> columStatusAuto;
    @FXML private Button enviarLote;
    @FXML private TextField nomeCliente;
    @FXML private RadioButton pac;
    @FXML private RadioButton sedex;
    @FXML private DatePicker dateLote;

    private ObservableList<ProdutoConferido> listaConcluida = FXCollections.observableArrayList();
    private List <RadioButton> pacSedex;

    @FXML
    void enviarLoteAct(ActionEvent event) {
        ProdutoConferido produtoSelecionado = tableEnvios.getSelectionModel().getSelectedItem();
        String nomecliente = nomeCliente.getText();
        LocalDate datalote = dateLote.getValue();
        pacSedex = Arrays.asList(pac, sedex);

        if (nomeCliente == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum Nome de Cliente Encontrado!");
            return;
        }
        if (produtoSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum produto selecionado!");
            return;
        }
        if (!pac.isSelected() && !sedex.isSelected()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum método de envio selecionado.");
            return;
        }
        if (datalote == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum Data de Entrega Encontrada!");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText("Finalizar lote para " + nomecliente);
        confirmacao.setContentText("Tem certeza que deseja envir este Lote como  com a Data de entrega até " + datalote + " e como Finalizado? ");
        ButtonType sim = new ButtonType("Sim");
        ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacao.getButtonTypes().setAll(sim, nao);

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == sim){
                mostrarAlerta(Alert.AlertType.INFORMATION,"Sucesso" , "Produto Conferido e Finalizado e preste a ser enviado com sucesso!");
                mostrarAlerta(Alert.AlertType.INFORMATION,"Sucesso" , "Obrigado a todos por dividir o peso desse jornada, nunca é um adeus, agora vem o Churras!!!!");
            }else{
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelado", "Aprovação cancelada.");
            }

        });


    }

   



    @FXML
    public void initialize() {
      
      columIdAuto.setCellValueFactory(new PropertyValueFactory<>("id"));
      columSeloAuto.setCellValueFactory(new PropertyValueFactory<>("selo"));
      columDescriAuto.setCellValueFactory(new PropertyValueFactory<>("descricao"));
      columStatusAuto.setCellValueFactory(new PropertyValueFactory<>("status"));
      columProduAuto.setCellValueFactory(new PropertyValueFactory<>("produtos"));
      columLoteAuto.setCellValueFactory(new PropertyValueFactory<>("lote"));
      columChegadaAuto.setCellValueFactory(new PropertyValueFactory<>("chegada"));
      columSaidaAuto.setCellValueFactory(new PropertyValueFactory<>("saida"));
      
      tableConferenciaConferidos();

        pac.setOnAction(event -> {
            if (pac.isSelected()) {
                sedex.setSelected(false);
            }
        });

        sedex.setOnAction(event -> {
            if (sedex.isSelected()) {
                pac.setSelected(false);
            }
        });

    }

    public void finalizarLote(){
        
    }

    
    public void tableConferenciaConferidos(){
        listaConcluida.clear();  
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tableConferidos")) {

            while (rs.next()) {
                listaConcluida.add(new ProdutoConferido(rs.getInt("id"), rs.getString("selo"), rs.getString("descricao"), rs.getString("status"), rs.getString("produtos"), rs.getInt("lote"), rs.getDate("chegada"), rs.getDate("saida")));
            }
            tableEnvios.setItems(listaConcluida);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR,"Erro","Erro ao carregar: " + e.getMessage());
        }
        
    }
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
      Alert alerta = new Alert(tipo);
      alerta.setTitle(titulo);
      alerta.setHeaderText(null);
      alerta.setContentText(mensagem);
      alerta.showAndWait();
    }

    @FXML
    public void btnBuscarEnviosAct(ActionEvent event) {
        String textoLote = textEnvios.getText().trim();

        if (textoLote.isEmpty()) {
            tableConferenciaConferidos(); 
            return;
        }

        try {int numeroLote = Integer.parseInt(textoLote);
            try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tableConferidos WHERE lote = ?")) {

                stmt.setInt(1, numeroLote);
                ResultSet rs = stmt.executeQuery();
                listaConcluida.clear();

                while (rs.next()) {
                    listaConcluida.add(new ProdutoConferido(rs.getInt("id"), rs.getString("selo"), rs.getString("descricao"), rs.getString("status"), rs.getString("produtos"), rs.getInt("lote"), rs.getDate("chegada"), rs.getDate("saida")));
                }

                tableEnvios.setItems(listaConcluida);

            } catch (SQLException e) {
            }

            } catch (NumberFormatException e) {
            }

    }



  
} 

