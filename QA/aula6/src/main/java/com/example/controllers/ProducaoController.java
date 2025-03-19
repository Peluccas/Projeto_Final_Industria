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

public class ProducaoController {
  @FXML private TextField txtNomeProducao;
    @FXML private TextField txtQuantidadeProducao;
    @FXML private TextField txtPrecoProducao;
    @FXML private TableView<Produto> tableProducao;
    @FXML private TableColumn<Produto, Integer> colId;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, Integer> colQuantidade;
    @FXML private TableColumn<Produto, Double> colPreco;
    @FXML private TableColumn<Produto, String> colLocalizacao;
    @FXML private TableColumn<Produto, String> colCategoria;
    @FXML private ComboBox<String> cmbCategoria111;
    @FXML private TextField txtLocalizacaoProducao;

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
    private void salvarProducao() {
        String nome = txtNomeProducao.getText();
        int quantidade = Integer.parseInt(txtQuantidadeProducao.getText());
        double preco = Double.parseDouble(txtPrecoProducao.getText());
    

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Producao (producao_nome, producao_quantidade, producao_preco, producao_localizacao, producao_categoria ) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, nome);
            stmt.setInt(2, quantidade);
            stmt.setDouble(3, preco);
            stmt.setString(4, txtLocalizacaoProducao.getText());
            stmt.setString(5, cmbCategoria111.getValue());
            stmt.executeUpdate();

            carregarProdutos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Producao salvo com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao salvar o produto!" + e.getMessage());
        }
    }

    @FXML
    private void atualizarProduto() {
        int id = Integer.parseInt(txtIdAtualizar.getText());
        String nome = txtNomeAtualizar.getText();
        int quantidade = Integer.parseInt(txtQuantidadeAtualizar.getText());
        double preco = Double.parseDouble(txtPrecoAtualizar.getText());

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Producao SET producao_nome = ?,  producao_quantidade = ?, producao_preco = ?, producao_localizacao= ?, producao_categoria = ? ,producao_id = ?")) {

            stmt.setString(1, nome);
            stmt.setInt(2, quantidade);
            stmt.setDouble(3, preco);
            stmt.setString(4, txtLocalizacaoAtualizar.getText());
            stmt.setString(5, cmbCategoriaAtualizar.getValue());
            stmt.setInt(6, id);
            stmt.executeUpdate();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao atualizar o produto!" + e.getMessage());
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

        cmbCategoria111.getItems().addAll("Eletrônicos", "Máquinas", "Peças", "Utilitários");
        cmbCategoriaAtualizar.getItems().addAll("Eletrônicos", "Máquinas", "Peças", "Utilitários");
        filtroCategoria.getItems().addAll("Eletrônicos", "Máquinas", "Peças", "Utilitários");


        carregarProdutos();

          // Adiciona um listener para a seleção de itens na tabela
          tableProducao.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                preencherCamposAtualizacao();
            }
        });
    }



        private void preencherCamposAtualizacao() {
            Produto produtoSelecionado = tableProducao.getSelectionModel().getSelectedItem();
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

        tableProducao.setItems(dadosFiltrados);
    }

    @FXML
    private void limparFiltro() {
        filtroNome.clear();
        filtroQuantidade.clear();
        filtroPreco.clear();
        filtroLocalizacao.clear();
        filtroCategoria.setValue(null);
        tableProducao.setItems(listaProdutos);
    }

    @FXML
    private void excluirProduto(){
        Produto produtoSelecionado = tableProducao.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM Producao WHERE id = ?")) {

                stmt.setInt(1, produtoSelecionado.getId());
                stmt.executeUpdate();

                carregarProdutos();

                mostrarAlerta(Alert.AlertType.INFORMATION,"Sucesso" , "Producao excluído com sucesso!");

                //volta para a tab de visualização
                tabPane.getSelectionModel().select(tabListaProdutos);

            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao excluir o producao!" + e.getMessage());
            }
        }
    }


    private void carregarProdutos() {
        listaProdutos.clear();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Producao")) {

            while (rs.next()) {
                listaProdutos.add(new Produto(rs.getInt("producao_id"), rs.getString("producao_nome"), rs.getInt("producao_quantidade"), rs.getDouble("producao_preco"), rs.getString("producao_localizacao"), rs.getString("producao_categoria"), null, 0));
            }
            tableProducao.setItems(listaProdutos);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR,"Erro","Erro ao carregar Producao: " + e.getMessage());
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

