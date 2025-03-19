package com.example.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.database.Database;
import com.example.models.Produto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class AutomocaoController {
     @FXML private TextField txtNomeAutomocao;
   @FXML private TextField txtQuantidadeAutomocao;
   @FXML private TextField txtPrecoAutomocao;
    @FXML private TableView<Produto> tableProdutos;
    @FXML private TableColumn<Produto, Integer> colId;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, Integer> colQuantidade;
    @FXML private TableColumn<Produto, Double> colPreco;
    @FXML private TableColumn<Produto, String> colLocalizacao;
    @FXML private TableColumn<Produto, String> colCategoria;
    @FXML private ComboBox<String> cmbCategoria11;
    @FXML private TextField txtLocalizacaoAutomocao;

     // Campos para atualização
    @FXML private TextField txtIdAtualizar;
    @FXML private TextField txtNomeAtualizar;
    @FXML private TextField txtQuantidadeAtualizar;
    @FXML private TextField txtPrecoAtualizar;
    @FXML private ComboBox<String> cmbCategoriaAtualizar;
    @FXML private TextField txtLocalizacaoAtualizar;

    //import dos filtros
    @FXML private TextField filtroNome;
    @FXML private TextField filtroQuantidade;
    @FXML private TextField filtroPreco;
    @FXML private TextField filtroLocalizacao;
    @FXML private ComboBox<String> filtroCategoria;
    @FXML private Button btnLimparFiltro;

    //Controle de tabs
    @FXML private TabPane tabPane;
    @FXML private Tab tabAtualizar;
    @FXML private Tab tabListaProdutos;

    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
    
    @FXML
    private void salvarAutomocao() {
        String nome = txtNomeAutomocao.getText();
        int quantidade = Integer.parseInt(txtQuantidadeAutomocao.getText());
        double preco = Double.parseDouble(txtPrecoAutomocao.getText());

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Automocao (automocao_nome, automocao_quantidade, automocao_preco, automocao_localizacao, automocao_categoria) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, nome);
            stmt.setInt(2, quantidade);
            stmt.setDouble(3, preco);
            stmt.setString(4, txtLocalizacaoAutomocao.getText());
            stmt.setString(5, cmbCategoria11.getValue());
            stmt.executeUpdate();

     salvarAutomocao();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Automocao salvo com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao salvar o Automocao!" + e.getMessage());
        }
    }

    @FXML
    private void atualizarProduto() {
        int id = Integer.parseInt(txtIdAtualizar.getText());
        String nome = txtNomeAtualizar.getText();
        int quantidade = Integer.parseInt(colQuantidade.getText());
        double preco = Double.parseDouble(txtPrecoAtualizar.getText());

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Automocao SET automocao_nome = ?, automocao_quantidade = ?, automocao_preco = ?, automocao_localizacao = ?, automocao_categoria = ? WHERE id = ?")) {

            stmt.setString(1, nome);
            stmt.setInt(2, quantidade);
            stmt.setDouble(3, preco);
            stmt.setString(4, txtLocalizacaoAtualizar.getText());
            stmt.setString(5, cmbCategoriaAtualizar.getValue());
            stmt.setInt(6, id);
            stmt.executeUpdate();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Automocao atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao atualizar o automocao!" + e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colLocalizacao.setCellValueFactory(new PropertyValueFactory<>("localizacao"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        cmbCategoria11.getItems().addAll("Eletrônicos", "Máquinas", "Peças", "Utilitários");
        cmbCategoriaAtualizar.getItems().addAll("Eletrônicos", "Máquinas", "Peças", "Utilitários");
        filtroCategoria.getItems().addAll("Eletrônicos", "Máquinas", "Peças", "Utilitários");

        carregarProdutos();

           // Adiciona um listener para a seleção de itens na tabela
          tableProdutos.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                preencherCamposAtualizacao();
            }
        });

}
private void preencherCamposAtualizacao() {
        Produto produtoSelecionado = tableProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            txtIdAtualizar.setText(String.valueOf(produtoSelecionado.getId()));
            txtNomeAtualizar.setText(produtoSelecionado.getNome());
            txtQuantidadeAtualizar.setText(String.valueOf(produtoSelecionado.getQuantidade()));
            txtPrecoAtualizar.setText(String.valueOf(produtoSelecionado.getPreco()));
            txtLocalizacaoAtualizar.setText(produtoSelecionado.getLocalizacao());
            cmbCategoriaAtualizar.setValue(produtoSelecionado.getCategoria());

            // Muda para a aba de atualização
            tabPane.getSelectionModel().select(tabAtualizar);
        }
    }

       @FXML
    private void limparCamposAtualizacao(){
        txtIdAtualizar.clear();
        txtNomeAtualizar.clear();
        txtQuantidadeAtualizar.clear();
        txtPrecoAtualizar.clear();
        txtLocalizacaoAtualizar.clear();
        cmbCategoriaAtualizar.setValue(null);

        //volta para a tab de visualização
        tabPane.getSelectionModel().select(tabListaProdutos);
    }

    //função para filtrar os produtos
    @FXML
    private void filtrarProdutos() {
        FilteredList<Produto> dadosFiltrados = new FilteredList<>(listaProdutos, p -> true);

        dadosFiltrados.setPredicate(produto -> {
            if (!filtroNome.getText().isEmpty() && !produto.getNome().toLowerCase().contains(filtroNome.getText().toLowerCase())) {
                return false;
            }
            if (!filtroQuantidade.getText().isEmpty() && !String.valueOf(produto.getQuantidade()).contains(filtroQuantidade.getText())) {
                return false;
            }
            if (!filtroPreco.getText().isEmpty() && !String.valueOf(produto.getPreco()).contains(filtroPreco.getText())) {
                return false;
            }
            if (!filtroLocalizacao.getText().isEmpty() && !produto.getLocalizacao().toLowerCase().contains(filtroLocalizacao.getText().toLowerCase())) {
                return false;
            }
            if (filtroCategoria.getValue() != null && !filtroCategoria.getValue().isEmpty() && !produto.getCategoria().toLowerCase().contains(filtroCategoria.getValue().toLowerCase())) {
                return false;
            }
            return true;
        });

        tableProdutos.setItems(dadosFiltrados);
    }

    @FXML
    private void limparFiltro() {
        filtroNome.clear();
        filtroQuantidade.clear();
        filtroPreco.clear();
        filtroLocalizacao.clear();
        filtroCategoria.setValue(null);
        tableProdutos.setItems(listaProdutos);
    }

    @FXML
    private void excluirProduto(){
        Produto produtoSelecionado = tableProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM Automocao WHERE id = ?")) {

                stmt.setInt(1, produtoSelecionado.getId());
                stmt.executeUpdate();

                carregarProdutos();

                mostrarAlerta(Alert.AlertType.INFORMATION,"Sucesso" , "Peca excluído com sucesso!");

                //volta para a tab de visualização
                tabPane.getSelectionModel().select(tabListaProdutos);

            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao excluir o Peca!" + e.getMessage());
            }
        }
    }


    private void carregarProdutos() {
        listaProdutos.clear();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Automocao")) {

            while (rs.next()) {
                listaProdutos.add(new Produto(rs.getInt("automocao_id"), rs.getString("automocao_nome"), rs.getInt("automocao_quantidade"), rs.getDouble("automocao_preco"), rs.getString("automocao_localizacao"), rs.getString("automocao_categoria"), null, 0));
            }
            tableProdutos.setItems(listaProdutos);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR,"Erro","Erro ao carregar : " + e.getMessage());
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
