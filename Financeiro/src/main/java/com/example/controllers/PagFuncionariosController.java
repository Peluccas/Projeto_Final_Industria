package com.example.controllers;

import com.example.database.Database;
import com.example.models.PagFuncionarios;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class PagFuncionariosController {
    @FXML private TableView<PagFuncionarios> tablePagamento;

    @FXML private TableColumn<PagFuncionarios, String> colPagFuncionario;
    @FXML private TableColumn<PagFuncionarios, String> colPagSetor;
    @FXML private TableColumn<PagFuncionarios, Date> colPagData;
    @FXML private TableColumn<PagFuncionarios, Double> colPagSalario;
    @FXML private TableColumn<PagFuncionarios, Double> colPagDescontos;
    @FXML private TableColumn<PagFuncionarios, Double> colPagLiquido;
    @FXML private TableColumn<PagFuncionarios, Boolean> colPagStatus;

    @FXML private ComboBox<String> cmbFiltroSetor;
    @FXML private ComboBox<String> cmbFiltroData;
    @FXML private ComboBox<String> cmbFiltroStatus;

    


    private ObservableList<PagFuncionarios> listaFuncionarios = FXCollections.observableArrayList();

    @FXML
    private void listaFuncionarios(){
            listaFuncionarios.clear();
           try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM pagfuncionarios")) {
                   while (rs.next()) {
                       listaFuncionarios.add(new PagFuncionarios(rs.getInt("id"), rs.getInt("fk_funcionarios"), rs.getInt("fk_setor"), rs.getString("data_pagto"), rs.getDouble("salario_base"), rs.getDouble("descontos"), rs.getDouble("valor_liquido"), rs.getBoolean("status")));
                   }
                   tablePagamento.setItems(listaFuncionarios);  
           }catch (SQLException e){
               e.printStackTrace();
           }
       }



    @FXML
    public void initialize(){
        colPagFuncionario.setCellValueFactory(new PropertyValueFactory<>("fk_funcionarios"));
        colPagSetor.setCellValueFactory(new PropertyValueFactory<>("fk_Setor"));
        colPagData.setCellValueFactory(new PropertyValueFactory<>("data_pagto"));
        colPagSalario.setCellValueFactory(new PropertyValueFactory<>("salario_base"));
        colPagDescontos.setCellValueFactory(new PropertyValueFactory<>("descontos"));
        colPagLiquido.setCellValueFactory(new PropertyValueFactory<>("valor_liquido"));
        colPagStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        cmbFiltroSetor.getItems().addAll("RH", "Automação", "Produção", "Estoque", "Controle de Qualidade");
        cmbFiltroData.getItems().addAll("2020", "2021", "2022", "2023", "2024","2025");
        cmbFiltroStatus.getItems().addAll("Concluida", "Pendente");

        listaFuncionarios();

    }


}