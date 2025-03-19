package com.example.controllers;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.database.Database;
import com.example.models.Maquina;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MaquinaController {
    
    @FXML private TextField txtMaquinaNome;
    @FXML private TextField txtMaquinaSetor;
    @FXML private TextField txtMaquinaDescricao;

    @FXML private TableView<Maquina> tableMaquinas;
    @FXML private TableColumn<Maquina, Integer> colMaquinaId;
    @FXML private TableColumn<Maquina, String> colMaquinaNome;
    @FXML private TableColumn<Maquina, String> colMaquinaSetor;
    @FXML private TableColumn<Maquina, String> colMaquinaDescricao;

    @FXML private TextField txtIdAtualizar;
    @FXML private TextField txtNomeAtualizar;
    @FXML private TextField txtSetorAtualizar;
    @FXML private TextField txtDescricaoAtualizar;

    @FXML private TextField filtroNome;
    @FXML private TextField filtroSetor;
    @FXML private TextField filtroDescricao;

    public ObservableList<Maquina>listaMaquina = FXCollections.observableArrayList();

    @FXML
    public void salvarMaquina() {
        String nome = txtMaquinaNome.getText();
        String setor = txtMaquinaSetor.getText();
        String descricao = txtMaquinaDescricao.getText();
    
        String sql = "INSERT INTO maquina (nome, setor, descricao) VALUES (?, ?, ?)";
    
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, nome);
            stmt.setString(2, setor);
            stmt.setString(3, descricao);
            stmt.executeUpdate();
    
            carregarMaquinas();
            mostrarAviso("Sucesso", "Máquina salva com sucesso!");
        } catch (SQLException e) {
            mostrarErro("Erro ao salvar", e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        colMaquinaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMaquinaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colMaquinaSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        colMaquinaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        carregarMaquinas();

        tableMaquinas.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                preencherCamposAtualizacao();
            }
        });
    }

    @FXML
    public void editarMaquina() {
        try {
            int id = Integer.parseInt(txtIdAtualizar.getText());
            String nome = txtNomeAtualizar.getText();
            String setor = txtSetorAtualizar.getText();
            String descricao = txtDescricaoAtualizar.getText();

            String sql = "UPDATE maquina SET nome = ?, setor = ?, descricao = ? WHERE id = ?";
            
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, nome);
                stmt.setString(2, setor);
                stmt.setString(3, descricao);
                stmt.setInt(4, id);
                stmt.executeUpdate();

                carregarMaquinas();
                mostrarAviso("Sucesso", "Máquina atualizada com sucesso!");
            }
        } catch (NumberFormatException e) {
            mostrarErro("Erro", "ID inválido! Certifique-se de selecionar uma máquina.");
        } catch (SQLException e) {
            mostrarErro("Erro ao atualizar", e.getMessage());
        }
    }

        @FXML
    public void filtrarMaquina() {
        FilteredList<Maquina> dadosFiltrados = new FilteredList<>(listaMaquina, p -> true);

        dadosFiltrados.setPredicate(produto -> {
            if (!filtroNome.getText().isEmpty() && !produto.getNome().toLowerCase().contains(filtroNome.getText().toLowerCase())) {
                return false;
            }
            else if (!filtroSetor.getText().isEmpty() && !produto.getSetor().toLowerCase().contains(filtroSetor.getText().toLowerCase())) {
                return false;
            }
            else if (!filtroDescricao.getText().isEmpty() && !String.valueOf(produto.getDescricao()).contains(filtroDescricao.getText())) {
                return false;
            }

            return true;
        });

        tableMaquinas.setItems(dadosFiltrados);
    }

    @FXML
    public void excluirMaquina() {
        Maquina maquinaSelecionada = tableMaquinas.getSelectionModel().getSelectedItem();
        
        if (maquinaSelecionada != null) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM maquina WHERE id = ?")) {

                stmt.setInt(1, maquinaSelecionada.getId());
                stmt.executeUpdate();

                carregarMaquinas();
                mostrarAviso("Sucesso", "Máquina excluída com sucesso!");
            } catch (SQLException e) {
                mostrarErro("Erro ao excluir", e.getMessage());
            }
        } else {
            mostrarErro("Aviso", "Nenhuma máquina selecionada para exclusão!");
        }
    }

    public void carregarMaquinas() {
        listaMaquina.clear();
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM maquina");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaMaquina.add(new Maquina(rs.getInt("id"), rs.getString("nome"), rs.getString("setor"), rs.getString("descricao")));
            }
            tableMaquinas.setItems(listaMaquina);
        } catch (SQLException e) {
            mostrarErro("Erro ao carregar", e.getMessage());
        }
    }

    private void mostrarAviso(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void preencherCamposAtualizacao() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
