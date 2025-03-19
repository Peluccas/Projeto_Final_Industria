package com.example.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.database.Database;
import com.example.models.Producao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProducaoController {
    
    @FXML private TextField txtProducaoProduto;
    @FXML private TextField txtProducaoSetor;
    @FXML private TextField txtProducaoMaquina;
    @FXML private TextField txtProducaoResp;
    @FXML private TextField txtProducaoData;
    @FXML private TextField txtProducaoQuant;

    @FXML private TableView<Producao> tableProducao;
    @FXML private TableColumn<Producao, String> colProducaoProduto;
    @FXML private TableColumn<Producao, String> colProducaoSetor;
    @FXML private TableColumn<Producao, String> colProducaoMaquina;
    @FXML private TableColumn<Producao, String> colProducaoResp;
    @FXML private TableColumn<Producao, String> colProducaoData;
    @FXML private TableColumn<Producao, Integer> colProducaoQuant;

    @FXML private TextField txtIdAtualizar;
    @FXML private TextField txtProdutoAtualizar;
    @FXML private TextField txtSetorAtualizar;
    @FXML private TextField txtMaquinaAtualizar;
    @FXML private TextField txtRespAtualizar;
    @FXML private TextField txtDataAtualizar;
    @FXML private TextField txtQuantAtualizar;

    @FXML private TextField filtroProduto;
    @FXML private TextField filtroSetor;
    @FXML private TextField filtroMaquina;
    @FXML private TextField filtroQuant;
    @FXML private TextField filtroResp;
    @FXML private TextField filtroData;

    @FXML private Button btnLimparFiltro;


    //Salvar Produção
    private ObservableList<Producao> listaProducao = FXCollections.observableArrayList();
    @FXML
    public void salvarProducao(){
        String produto = txtProducaoProduto.getText();
        String setor = txtProducaoSetor.getText();
        String maquina = txtProducaoMaquina.getText();
        String responsavel = txtProducaoResp.getText();
        int quantidade = Integer.parseInt(txtProducaoQuant.getText());
        String data = txtProducaoData.getText();
    
        //Buscando no banco de dados
        String sql = "INSERT INTO producao (produto, setor, maquina, responsavel, quantidade, data) VALUES (?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, produto);
            stmt.setString(2, setor);
            stmt.setString(3, maquina);
            stmt.setString(4, responsavel);
            stmt.setInt(5, quantidade);
            stmt.setString(6, data);
            stmt.executeUpdate();
    
            carregarProducao();
        } catch (SQLException e) {
        }
    }
    //Editar Produção
    @FXML
    public void editarProducao() {
        int id = Integer.parseInt(txtIdAtualizar.getText());
        String produto = txtProdutoAtualizar.getText();
        String setor = txtSetorAtualizar.getText();
        String maquina = txtMaquinaAtualizar.getText();
        String responsavel = txtRespAtualizar.getText();
        int quantidade = Integer.parseInt(txtProducaoQuant.getText());
        String data = txtDataAtualizar.getText();

        //Buscando no banco de dados
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE producao SET produto = ?, setor = , maquina = ?, responsavel = ?, quantidade = ?, data = ?, WHERE id = ?")) {

            stmt.setString(1, produto);
            stmt.setString(2, setor);
            stmt.setString(3, maquina);
            stmt.setString(4, responsavel);
            stmt.setInt(5, quantidade);
            stmt.setString(6, data);
            stmt.setInt(7, id); 
            stmt.executeUpdate();

            carregarProducao();
        } catch (SQLException e) {
        }
    }
    //Iniciando 
    @FXML
    public void initialize() {
        colProducaoProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colProducaoSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        colProducaoMaquina.setCellValueFactory(new PropertyValueFactory<>("maquina"));
        colProducaoResp.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
        colProducaoQuant.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colProducaoData.setCellValueFactory(new PropertyValueFactory<>("data"));

        carregarProducao();

        tableProducao.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                preencherCamposAtualizacao();
            }
        });
    }

    public void preencherCamposAtualizacao() {
        Producao producaoSelecionado = tableProducao.getSelectionModel().getSelectedItem();
        if (producaoSelecionado != null) {
            txtProdutoAtualizar.setText(producaoSelecionado.getProduto());
            txtRespAtualizar.setText(producaoSelecionado.getResp());
            txtSetorAtualizar.setText(producaoSelecionado.getSetor());
            txtMaquinaAtualizar.setText(producaoSelecionado.getMaquina());
            txtQuantAtualizar.setText(String.valueOf(producaoSelecionado.getQuant()));
            txtDataAtualizar.setText(producaoSelecionado.getData());
        }
    }

    @FXML
    public void limparCamposAtualizacao(){
        txtIdAtualizar.clear();
        txtProdutoAtualizar.clear();
        txtSetorAtualizar.clear();
        txtMaquinaAtualizar.clear();
        txtRespAtualizar.clear();
        txtQuantAtualizar.clear();
        txtSetorAtualizar.clear();
        txtDataAtualizar.clear();
    }

    @FXML
    public void filtrarProducao() {
        FilteredList<Producao> dadosFiltrados = new FilteredList<>(listaProducao, p -> true);

        dadosFiltrados.setPredicate(producao -> {
            if (!filtroProduto.getText().isEmpty() && !producao.getProduto().toLowerCase().contains(filtroProduto.getText().toLowerCase())) {
                return false;
            }
            else if (!filtroSetor.getText().isEmpty() && !producao.getSetor().toLowerCase().contains(filtroSetor.getText().toLowerCase())) {
                return false;
            }
            else if (!filtroMaquina.getText().isEmpty() && !producao.getMaquina().toLowerCase().contains(filtroMaquina.getText().toLowerCase())) {
                return false;
            }
            else if (!filtroQuant.getText().isEmpty() && !String.valueOf(producao.getQuant()).toLowerCase().contains(filtroQuant.getText().toLowerCase())) {
                return false;
            }
            else if (!filtroResp.getText().isEmpty() && !producao.getResp().toLowerCase().contains(filtroResp.getText().toLowerCase())) {
                return false;
            }
            else if (!filtroData.getText().isEmpty() && !producao.getData().toLowerCase().contains(filtroData.getText().toLowerCase())) {
                return false;
            }
            return true;
        });

        tableProducao.setItems(dadosFiltrados);
    }

    @FXML
    public void limparFiltro() {
        filtroProduto.clear();
        filtroSetor.clear();
        filtroMaquina.clear();
        filtroQuant.clear();
        filtroResp.clear();
        filtroData.clear();
        tableProducao.setItems(listaProducao); 
    }

    @FXML
    public void excluirProducao() {
        Producao producaoSelecionado = tableProducao.getSelectionModel().getSelectedItem();
        
        if (producaoSelecionado != null) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM producao WHERE id = ?")) {

                stmt.setInt(1, producaoSelecionado.getId());
                stmt.executeUpdate();

                carregarProducao();

                System.out.println("Produção excluído com sucesso!");
            } catch (SQLException e) {
            }
        } else {
            System.out.println("Nenhuma Produção selecionada!");
        }
    }

    public void carregarProducao() {
        listaProducao.clear();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM producao");) {

            while (rs.next()) {
                listaProducao.add(new Producao(rs.getInt("id"), rs.getString("produto"), rs.getString("maquina"), rs.getString("setor"), rs.getString("responsavel"), rs.getInt("quantidade"), rs.getString("data")));
            }
            tableProducao.setItems(listaProducao); 
        } catch (SQLException e) {
        }
    }

    @SuppressWarnings("exports")
    public Button getBtnLimparFiltro() {
        return btnLimparFiltro;
    }

    public ObservableList<Producao> getListaProducao() {
        return listaProducao;
    }

    public void setListaProducao(ObservableList<Producao> listaProducao) {
        this.listaProducao = listaProducao;
    }
}
