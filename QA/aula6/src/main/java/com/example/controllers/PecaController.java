package com.example.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.database.Database;
import com.example.models.Peca;

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

public class PecaController {
    @FXML private TextField txtNomePeca;
    @FXML private TextField txtQuantidadePeca;
    @FXML private TextField txtPrecoPeca;
    @FXML private TableView<Peca> tablePeca;
    @FXML private TableColumn<Peca, Integer> colId;
    @FXML private TableColumn<Peca, String> colNome;
    @FXML private TableColumn<Peca, Integer> colQuantidade;
    @FXML private TableColumn<Peca, Double> colPreco;
    @FXML private TableColumn<Peca, String> colLocalizacao;
    @FXML private TableColumn<Peca, String> colCategoria;
    @FXML private ComboBox<String> cmbCategoria1;
    @FXML private TextField txtLocalizacaoPeca;

    
    // Campos para atualização
    @FXML private TextField txtIdAtualizarPeca;
    @FXML private TextField txtNomeAtualizarPeca;
    @FXML private TextField txtQuantidadeAtualizarPeca;
    @FXML private TextField txtPrecoAtualizarPeca;
    @FXML private ComboBox<String> cmbCategoriaAtualizarPeca;
    @FXML private TextField txtLocalizacaoAtualizarPeca;

    //import dos filtros
    @FXML private TextField filtroNomePeca;
    @FXML private TextField filtroQuantidadePeca;
    @FXML private TextField filtroPrecoPeca;
    @FXML private TextField filtroLocalizacaoPeca;
    @FXML private ComboBox<String> filtroCategoriaPeca;
    @FXML private Button btnLimparFiltroPeca;

    //Controle de tabs
    @FXML private TabPane tabPanePeca;
    @FXML private Tab tabAtualizarPeca;
    @FXML private Tab tabListaPeca;

    private ObservableList<Peca> listaPeca = FXCollections.observableArrayList();
    
    @FXML
    private void salvarPeca() {
        String nome = txtNomePeca.getText();
        int quantidade = Integer.parseInt(txtQuantidadePeca.getText());
        double preco = Double.parseDouble(txtPrecoPeca.getText());

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Peca (peca_nome, peca_quantidade, peca_preco, peca_localizacao, peca_categoria) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, nome);
            stmt.setInt(2, quantidade);
            stmt.setDouble(3, preco);
            stmt.setString(4, txtLocalizacaoPeca.getText());
            stmt.setString(5, cmbCategoria1.getValue());
            stmt.executeUpdate();

            salvarPeca();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Peca salvo com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao salvar o Peca!" + e.getMessage());
        }
    }
    @FXML
    private void atualizarProduto() {
        int id = Integer.parseInt(txtIdAtualizarPeca.getText());
        String nome = txtNomeAtualizarPeca.getText();
        int quantidade = Integer.parseInt(txtQuantidadeAtualizarPeca.getText());
        double preco = Double.parseDouble(txtPrecoAtualizarPeca.getText());

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Peca SET peca_nome = ?, peca_quantidade = ?, peca_preco = ?, peca_localizacao = ?, peca_categoria = ? WHERE id = ?")) {

            stmt.setString(1, nome);
            stmt.setInt(2, quantidade);
            stmt.setDouble(3, preco);
            stmt.setString(4, txtLocalizacaoAtualizarPeca.getText());
            stmt.setString(5, cmbCategoriaAtualizarPeca.getValue());
            stmt.setInt(6, id);
            stmt.executeUpdate();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Peca atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao atualizar o peca!" + e.getMessage());
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

        cmbCategoria1.getItems().addAll("Eletrônicos", "Máquinas", "Peças", "Utilitários");
        cmbCategoriaAtualizarPeca.getItems().addAll("Eletrônicos", "Máquinas", "Peças", "Utilitários");
        filtroCategoriaPeca.getItems().addAll("Eletrônicos", "Máquinas", "Peças", "Utilitários");


        carregarProdutos();
        
          // Adiciona um listener para a seleção de itens na tabela
          tablePeca.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                preencherCamposAtualizacaoPeca();
            }
        });
    }
    private void preencherCamposAtualizacaoPeca() {
        Peca PecaSelecionado = tablePeca.getSelectionModel().getSelectedItem();
        if (PecaSelecionado != null) {
            txtIdAtualizarPeca.setText(String.valueOf(PecaSelecionado.getId()));
            txtNomeAtualizarPeca.setText(PecaSelecionado.getNome());
            txtQuantidadeAtualizarPeca.setText(String.valueOf(PecaSelecionado.getQuantidade()));
            txtPrecoAtualizarPeca.setText(String.valueOf(PecaSelecionado.getPreco()));
            txtLocalizacaoAtualizarPeca.setText(PecaSelecionado.getLocalizacao());
            cmbCategoriaAtualizarPeca.setValue(PecaSelecionado.getCategoria());

            // Muda para a aba de atualização
            tabPanePeca.getSelectionModel().select(tabAtualizarPeca);
        }
    }

       @FXML
    private void limparCamposAtualizacaoPeca(){
        txtIdAtualizarPeca.clear();
        txtNomeAtualizarPeca.clear();
        txtQuantidadeAtualizarPeca.clear();
        txtPrecoAtualizarPeca.clear();
        txtLocalizacaoAtualizarPeca.clear();
        cmbCategoriaAtualizarPeca.setValue(null);

        //volta para a tab de visualização
        tabPanePeca.getSelectionModel().select(tabListaPeca);
    }

    //função para filtrar os produtos
    @FXML
    private void filtrarProdutos() {
        FilteredList<Peca> dadosFiltrados = new FilteredList<>(listaPeca, p -> true);

        dadosFiltrados.setPredicate(produto -> {
            if (!filtroNomePeca.getText().isEmpty() && !produto.getNome().toLowerCase().contains(filtroNomePeca.getText().toLowerCase())) {
                return false;
            }
            if (!filtroQuantidadePeca.getText().isEmpty() && !String.valueOf(produto.getQuantidade()).contains(filtroQuantidadePeca.getText())) {
                return false;
            }
            if (!filtroPrecoPeca.getText().isEmpty() && !String.valueOf(produto.getPreco()).contains(filtroPrecoPeca.getText())) {
                return false;
            }
            if (!filtroLocalizacaoPeca.getText().isEmpty() && !produto.getLocalizacao().toLowerCase().contains(filtroLocalizacaoPeca.getText().toLowerCase())) {
                return false;
            }
            if (filtroCategoriaPeca.getValue() != null && !filtroCategoriaPeca.getValue().isEmpty() && !produto.getCategoria().toLowerCase().contains(filtroCategoriaPeca.getValue().toLowerCase())) {
                return false;
            }
            return true;
        });

        tablePeca.setItems(dadosFiltrados);
    }

    @FXML
    private void limparFiltroPeca() {
        filtroNomePeca.clear();
        filtroQuantidadePeca.clear();
        filtroPrecoPeca.clear();
        filtroLocalizacaoPeca.clear();
        filtroCategoriaPeca.setValue(null);
        tablePeca.setItems(listaPeca);
    }

    @FXML
    private void excluirProdutoPeca(){
        Peca PecaSelecionado = tablePeca.getSelectionModel().getSelectedItem();
        if (PecaSelecionado != null) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM Peca WHERE id = ?")) {

                stmt.setInt(1, PecaSelecionado.getId());
                stmt.executeUpdate();

                carregarProdutos();

                mostrarAlerta(Alert.AlertType.INFORMATION,"Sucesso" , "Peca excluído com sucesso!");

                //volta para a tab de visualização
                tabPanePeca.getSelectionModel().select(tabListaPeca);

            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao excluir o Peca!" + e.getMessage());
            }
        }
    }


    private void carregarProdutos() {
        listaPeca.clear();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Peca")) {

            while (rs.next()) {
                listaPeca.add(new Peca(rs.getInt("peca_id"), rs.getString("peca_nome"), rs.getInt("peca_quantidade"), rs.getDouble("peca_preco"), rs.getString("peca_localizacao"), rs.getString("peca_categoria"), null));
            }
            tablePeca.setItems(listaPeca);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR,"Erro","Erro ao carregar Peca: " + e.getMessage());
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
    

