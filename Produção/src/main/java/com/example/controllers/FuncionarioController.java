package com.example.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.database.Database;
import com.example.models.Funcionario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class FuncionarioController {

    @FXML private TextField txtFuncionarioNome;
    @FXML private TextField txtFuncionarioSetor;

    @FXML private TableView<Funcionario> tableFuncionarios;
    @FXML private TableColumn<Funcionario, Integer> colFuncionarioId;
    @FXML private TableColumn<Funcionario, String> colFuncionarioNome;
    @FXML private TableColumn<Funcionario, String> colFuncionarioSetor;

    @FXML private TextField filtroNome;
    @FXML private TextField filtroSetor;
    @FXML private Button btnLimparFiltro;

    private ObservableList<Funcionario> listaFuncionario = FXCollections.observableArrayList();

    @FXML
    public void salvarFuncionario() {
        String nome = txtFuncionarioNome.getText().trim();
        String setor = txtFuncionarioSetor.getText().trim();
    
        if (nome.isEmpty() || setor.isEmpty()) {
            exibirAlerta("Erro", "Campos obrigatórios!", "Preencha todos os campos antes de salvar.");
            return;
        }
    
        String sql = "INSERT INTO funcionario (nome, setor) VALUES (?, ?)";
    
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, nome);
            stmt.setString(2, setor);
            stmt.executeUpdate();
    
            carregarFuncionarios();
            exibirAlerta("Sucesso", "Funcionário cadastrado!", "O funcionário foi cadastrado com sucesso.");
    
            // Limpar os campos após cadastro
            txtFuncionarioNome.clear();
            txtFuncionarioSetor.clear();
    
        } catch (SQLException e) {
            exibirAlerta("Erro", "Erro ao cadastrar", "Não foi possível cadastrar o funcionário.");
        }
    }
    


    @FXML
    public void initialize() {
        colFuncionarioId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFuncionarioNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFuncionarioSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));

        carregarFuncionarios();

        tableFuncionarios.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
            }
        });
    }

    @FXML
    public void filtrarFuncionario() {
        FilteredList<Funcionario> dadosFiltrados = new FilteredList<>(listaFuncionario, p -> true);

        dadosFiltrados.setPredicate(funcionario -> {
            if (!filtroNome.getText().isEmpty() && !funcionario.getNome().toLowerCase().contains(filtroNome.getText().toLowerCase())) {
                return false;
            }
            else if (!filtroSetor.getText().isEmpty() && !funcionario.getSetor().toLowerCase().contains(filtroSetor.getText().toLowerCase())) {
                return false;
            }
            return true;
        });

        tableFuncionarios.setItems(dadosFiltrados);
    }

    @FXML
    public void limparFiltro() {
        filtroNome.clear();
        filtroSetor.clear();
        tableFuncionarios.setItems(listaFuncionario);
    }

    @FXML
    public void excluirFuncionario() {
        Funcionario funcionarioSelecionado = tableFuncionarios.getSelectionModel().getSelectedItem();

        if (funcionarioSelecionado != null) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM funcionario WHERE id = ?")) {

                stmt.setInt(1, funcionarioSelecionado.getId());
                stmt.executeUpdate();

                carregarFuncionarios();
                exibirAlerta("Sucesso", "Funcionário excluído!", "O funcionário foi excluído com sucesso.");
            } catch (SQLException e) {
                exibirAlerta("Erro", "Erro ao excluir", "Não foi possível excluir o funcionário.");
            }
        } else {
            exibirAlerta("Aviso", "Nenhum funcionário selecionado", "Selecione um funcionário para excluir.");
        }
    }

    public void carregarFuncionarios() {
        listaFuncionario.clear();
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM funcionario");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaFuncionario.add(new Funcionario(rs.getInt("id"), rs.getString("nome"), rs.getString("setor")));
            }
            tableFuncionarios.setItems(listaFuncionario);
        } catch (SQLException e) {
            exibirAlerta("Erro", "Erro ao carregar", "Não foi possível carregar os funcionários.");
        }
    }

    private void exibirAlerta(String titulo, String cabecalho, String conteudo) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(conteudo);
        alerta.showAndWait();
    }

    @SuppressWarnings("exports")
    public Button getBtnLimparFiltro() {
        return btnLimparFiltro;
    }

    public ObservableList<Funcionario> getListaFuncionarios() {
        return listaFuncionario;
    }

    public void setListaFuncionarios(ObservableList<Funcionario> listaFuncionarios) {
        this.listaFuncionario = listaFuncionarios;
    }
}
