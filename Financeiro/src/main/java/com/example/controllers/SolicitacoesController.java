package com.example.controllers;

import com.example.database.Database;
import com.example.models.Solicitacoes;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;

public class SolicitacoesController {

    @FXML private TableView<Solicitacoes> tableSolicitacoes;
    @FXML private TableColumn<Solicitacoes, Date> colSoData;
    @FXML private TableColumn<Solicitacoes, String> colSoSetor;
    @FXML private TableColumn<Solicitacoes, String> colSoDescricao;
    @FXML private TableColumn<Solicitacoes, String> colSoQuantidade;
    @FXML private TableColumn<Solicitacoes, Double> colSoValor;
    @FXML private TableColumn<Solicitacoes, Date> colSoPrazo;
    @FXML private TableColumn<Solicitacoes, String> colSoStatus;


  


     private ObservableList<Solicitacoes> listaSolicitacoes = FXCollections.observableArrayList();

 @FXML
 private void listaSolicitacoes(){
         listaSolicitacoes.clear();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM solicitacoes")) {
                while (rs.next()) {
                    listaSolicitacoes.add(new Solicitacoes(rs.getInt("id_solicitacoes"), rs.getString("data_solicitacao"), rs.getString("fk_setor"), rs.getString("descricao"), rs.getString("quantidade"), rs.getDouble("valor"), rs.getString("prazo"), rs.getString("status")));
                }
                tableSolicitacoes.setItems(listaSolicitacoes);  
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void aceitar() {
       
        try (Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("update solicitacoes set status = 'Aprovada' where id_solicitacoes = ?")) {
        
                  
            listaSolicitacoes();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void recusar() {
       
        try (Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("update solicitacoes set status = 'Recusada' where id_solicitacoes = ?")) {
        
                  
            listaSolicitacoes();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize(){
        colSoData.setCellValueFactory(new PropertyValueFactory<>("data_solicitacao"));
        colSoSetor.setCellValueFactory(new PropertyValueFactory<>("fk_setor"));
        colSoDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colSoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colSoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colSoPrazo.setCellValueFactory(new PropertyValueFactory<>("prazo"));
        colSoStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        listaSolicitacoes();


    }


}