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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProdutoController {
    @FXML private TextField txtNome;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtPreco;
    @FXML private TableView<Produto> tableProdutos;
    @FXML private TableColumn<Produto, Integer> colId;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, Integer> colQuantidade;
    @FXML private TableColumn<Produto, Double> colPreco;

    // Campos para atualização
    @FXML private TextField txtIdAtualizar;
    @FXML private TextField txtNomeAtualizar;
    @FXML private TextField txtQuantidadeAtualizar;
    @FXML private TextField txtPrecoAtualizar;

    //import dos filtros
    @FXML private TextField filtroNome;
    @FXML private TextField filtroQuantidade;
    @FXML private TextField filtroPreco;
    @FXML private Button btnLimparFiltro;

    //Controle de tabs
    @FXML private TabPane tabPane;
    @FXML private Tab tabAtualizar;
    @FXML private Tab tabListaProdutos;

    public ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();

    @FXML
    public void salvarProduto() {
        String nome = txtNome.getText().trim();
        String precoTexto = txtPreco.getText().trim();
        String quantidadeTexto = txtQuantidade.getText().trim();
    
        // Verificação de campos vazios
        if (nome.isEmpty() || precoTexto.isEmpty() || quantidadeTexto.isEmpty()) {
            exibirAlerta("Erro", "Campos obrigatórios!", "Preencha todos os campos antes de salvar.");
            return;
        }
    
        double preco;
        int quantidade;
    
        try {
            preco = Double.parseDouble(precoTexto);
        } catch (NumberFormatException e) {
            exibirAlerta("Erro", "Preço inválido!", "Digite um valor numérico válido para o preço.");
            return;
        }
    
        try {
            quantidade = Integer.parseInt(quantidadeTexto);
        } catch (NumberFormatException e) {
            exibirAlerta("Erro", "Quantidade inválida!", "Digite um número válido para a quantidade.");
            return;
        }
    
        String sql = "INSERT INTO produto (nome, preco, quantidade) VALUES (?, ?, ?)";
    
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, nome);
            stmt.setDouble(2, preco);
            stmt.setInt(3, quantidade);
            stmt.executeUpdate();
    
            carregarProdutos();
    
            // Exibir alerta de sucesso
            exibirAlerta("Sucesso", "Produto cadastrado!", "O produto foi cadastrado com sucesso.");
    
            // Limpar os campos após cadastro
            txtNome.clear();
            txtPreco.clear();
            txtQuantidade.clear();
    
        } catch (SQLException e) {
            exibirAlerta("Erro", "Erro ao cadastrar", "Não foi possível cadastrar o produto. Detalhes: " + e.getMessage());
        }
    }

    @FXML
    public void atualizarProduto() {
        int id = Integer.parseInt(txtIdAtualizar.getText());
        String nome = txtNomeAtualizar.getText();
        int quantidade = Integer.parseInt(txtQuantidadeAtualizar.getText());
        double preco = Double.parseDouble(txtPrecoAtualizar.getText());

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE produto SET nome = ?, quantidade = ?, preco = ? WHERE id = ?")) {

            stmt.setInt(4, id);
            stmt.setString(1, nome);
            stmt.setInt(2, quantidade);
            stmt.setDouble(3, preco);
            stmt.executeUpdate();

            carregarProdutos();
        } catch (SQLException e) {
            exibirAlerta("Erro", "Erro ao atualizar", "Não foi possível atualizar o produto.");
        }
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        carregarProdutos();

        tableProdutos.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                preencherCamposAtualizacao();
            }
        });
    }

    public void preencherCamposAtualizacao() {
        Produto produtoSelecionado = tableProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            txtIdAtualizar.setText(String.valueOf(produtoSelecionado.getId()));
            txtNomeAtualizar.setText(produtoSelecionado.getNome());
            txtQuantidadeAtualizar.setText(String.valueOf(produtoSelecionado.getQuantidade()));
            txtPrecoAtualizar.setText(String.valueOf(produtoSelecionado.getPreco()));

            tabPane.getSelectionModel().select(tabAtualizar);
        }
    }

    @FXML
    public void limparCamposAtualizacao(){
        txtIdAtualizar.clear();
        txtNomeAtualizar.clear();
        txtQuantidadeAtualizar.clear();
        txtPrecoAtualizar.clear();

        tabPane.getSelectionModel().select(tabListaProdutos);
    }

    // Função para filtrar os produtos
    @FXML
    public void filtrarProdutos() {
        FilteredList<Produto> dadosFiltrados = new FilteredList<>(listaProdutos, p -> true);

        dadosFiltrados.setPredicate(produto -> {
            boolean match = true;

            // Verificação para o filtro de nome
            if (!filtroNome.getText().isEmpty() && !produto.getNome().toLowerCase().contains(filtroNome.getText().toLowerCase())) {
                match = false;
            }

            // Verificação para o filtro de quantidade
            if (!filtroQuantidade.getText().isEmpty()) {
                try {
                    int quantidadeFiltro = Integer.parseInt(filtroQuantidade.getText());
                    if (produto.getQuantidade() != quantidadeFiltro) {
                        match = false;
                    }
                } catch (NumberFormatException e) {
                    match = false; // Caso o valor digitado não seja um número
                }
            }

            // Verificação para o filtro de preço
            if (!filtroPreco.getText().isEmpty()) {
                try {
                    double precoFiltro = Double.parseDouble(filtroPreco.getText());
                    if (produto.getPreco() != precoFiltro) {
                        match = false;
                    }
                } catch (NumberFormatException e) {
                    match = false; // Caso o valor digitado não seja um número
                }
            }

            return match;
        });

        tableProdutos.setItems(dadosFiltrados);
    }

    @FXML
    public void limparFiltro() {
        filtroNome.clear();
        filtroQuantidade.clear();
        filtroPreco.clear();
        tableProdutos.setItems(listaProdutos);  // Volta a exibir todos os produtos
    }

    @FXML
    public void excluirProduto() {
        Produto produtoSelecionado = tableProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            // Criar o alerta de confirmação de exclusão
            Alert alertConfirmacao = new Alert(AlertType.CONFIRMATION);
            alertConfirmacao.setTitle("Confirmação de Exclusão");
            alertConfirmacao.setHeaderText("Você realmente deseja excluir o produto?");
            alertConfirmacao.setContentText("Essa ação não pode ser desfeita.");

            // Exibir o alerta e esperar pela resposta
            alertConfirmacao.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Se o usuário clicar em "OK", realizar a exclusão
                    try (Connection conn = Database.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("DELETE FROM produto WHERE id = ?")) {

                        stmt.setInt(1, produtoSelecionado.getId());
                        stmt.executeUpdate();

                        // Remover produto da lista após exclusão
                        listaProdutos.remove(produtoSelecionado);

                        // Mostrar alerta de sucesso
                        Alert alertSucesso = new Alert(AlertType.INFORMATION);
                        alertSucesso.setTitle("Exclusão realizada");
                        alertSucesso.setHeaderText("Produto excluído com sucesso!");
                        alertSucesso.setContentText("O produto foi removido da lista.");
                        alertSucesso.showAndWait();

                    } catch (SQLException e) {
                        // Caso ocorra algum erro durante a exclusão
                        Alert alertErro = new Alert(AlertType.ERROR);
                        alertErro.setTitle("Erro de exclusão");
                        alertErro.setHeaderText("Não foi possível excluir o produto.");
                        alertErro.setContentText("Detalhes do erro: " + e.getMessage());
                        alertErro.showAndWait();
                    }
                } else {
                    // Caso o usuário não confirme a exclusão
                    Alert alertCancelado = new Alert(AlertType.INFORMATION);
                    alertCancelado.setTitle("Exclusão cancelada");
                    alertCancelado.setHeaderText("A exclusão foi cancelada.");
                    alertCancelado.showAndWait();
                }
            });
        } else {
            // Caso não haja produto selecionado
            Alert alertSelecionarProduto = new Alert(AlertType.WARNING);
            alertSelecionarProduto.setTitle("Nenhum produto selecionado");
            alertSelecionarProduto.setHeaderText("Selecione um produto para excluir.");
            alertSelecionarProduto.showAndWait();
        }
    }

    public void carregarProdutos() {
        listaProdutos.clear();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM produto")) {

            while (rs.next()) {
                listaProdutos.add(new Produto(rs.getInt("id"), rs.getString("nome"), rs.getInt("quantidade"), rs.getDouble("preco"), rs.getString("descricao")));
            }
            tableProdutos.setItems(listaProdutos);
        } catch (SQLException e) {
            exibirAlerta("Erro", "Erro ao carregar produtos", "Não foi possível carregar os produtos.");
        }
    }

    private void exibirAlerta(String titulo, String cabecalho, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
