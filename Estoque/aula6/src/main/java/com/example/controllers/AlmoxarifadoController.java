package com.example.controllers;
import javafx.collections.transformation.FilteredList;

import com.example.database.Database;
import com.example.models.almoxarifado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class AlmoxarifadoController {

    @FXML private TextField txtNome;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtMarca;
    @FXML private TextField txtFornecedor;
    @FXML private TextField txtLocalizacao;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtPrecoDeCusto;
    @FXML private ComboBox<String> cmbCategoria;

    @FXML private TableView<almoxarifado> tableProdutos;
    @FXML private TableColumn<almoxarifado, Integer> colId;
    @FXML private TableColumn<almoxarifado, String> colNome;
    @FXML private TableColumn<almoxarifado, Integer> colQuantidade;
    @FXML private TableColumn<almoxarifado, String> colMarca;
    @FXML private TableColumn<almoxarifado, String> colFornecedor;
    @FXML private TableColumn<almoxarifado, String> colLocalizacao;
    @FXML private TableColumn<almoxarifado, String> colCodigo;
    @FXML private TableColumn<almoxarifado, Double> colPrecoDeCusto;
    @FXML private TableColumn<almoxarifado, String> colCategoria;

    @FXML private TabPane tabPaneAlmoxarifado;
    @FXML private Tab tabCadastroProduto;
    @FXML private Tab tabVizualizacaoProduto;
    @FXML private Tab tabAtualizarProduto;

    private ObservableList<almoxarifado> listaProdutos = FXCollections.observableArrayList();

//atualização//
@FXML private TextField txtIdAtualizarProduto;
@FXML private TextField txtNomeAt ;
@FXML private TextField txtQuantidadeAt;
@FXML private TextField txtMarcaAt;
@FXML private TextField txtFornecedorAt;
@FXML private TextField txtLocalizacaoAt;
@FXML private TextField txtCodigoAt;
@FXML private TextField txtPrecoAt;
@FXML private ComboBox<String> cbmCategoriaAt;



//filtro//


@FXML private TextField filtroNome;
@FXML private TextField filtroQuantidade;
@FXML private TextField filtroMarca;
@FXML private TextField filtroFornecedor;
@FXML private TextField filtroLocalizacao;
@FXML private TextField filtroCodigo;
@FXML private TextField filtroPreco;
@FXML private ComboBox<String> cbmFiltro;
@FXML private Button btnLimparFiltro;

//salvar informações do produto
    @FXML
    private void salvarProduto() {
        String nome = txtNome.getText();
        int quantidade = Integer.parseInt(txtQuantidade.getText());
        String marca = txtMarca.getText();
        String fornecedor = txtFornecedor.getText();
        String localizacao = txtLocalizacao.getText();
        String codigo = txtCodigo.getText();
        double precoDeCusto = Double.parseDouble(txtPrecoDeCusto.getText());
        String categoria = cmbCategoria.getValue();

        String sql = "INSERT INTO estoque (nome, quantidade, marca, fornecedor, localizacao, codigo, preco_de_custo, categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setInt(2, quantidade);
            stmt.setString(3, marca);
            stmt.setString(4, fornecedor);
            stmt.setString(5, localizacao);
            stmt.setString(6, codigo);
            stmt.setDouble(7, precoDeCusto);
            stmt.setString(8, categoria);

            stmt.executeUpdate();
            carregarProdutos();
        //mensagem de alerta
        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto salvo com sucesso!");
    } catch (SQLException e) {
        e.printStackTrace();
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao salvar o produto!" + e.getMessage());
    }
}

   
//carregar funções de inicialização
@FXML
public void initialize() {
    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
    colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
    colFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
    colLocalizacao.setCellValueFactory(new PropertyValueFactory<>("localizacao"));
    colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
    colPrecoDeCusto.setCellValueFactory(new PropertyValueFactory<>("precoDeCusto"));
    colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

    cmbCategoria.getItems().addAll("placas", "Peças", "Utilitários", "materiais", "equipamentos", "outros..");
    cbmFiltro.getItems().addAll("placas", "Peças", "Utilitários", "materiais", "equipamentos", "outros..");
    cbmCategoriaAt.getItems().addAll("placas", "Peças", "Utilitários", "materiais", "equipamentos", "outros..");

    carregarProdutos();
    carregarProdutos();

    // Verificar se os campos são encontrados corretamente
    System.out.println(txtIdAtualizarProduto);  // Deve exibir o TextField
    // Adiciona um listener para a seleção de itens na tabela
    tableProdutos.setOnMouseClicked((MouseEvent event) -> {
        if (event.getClickCount() > 1) {
            preencherCamposAtualizacao();
        }
    });
}

//carregamento da tabela
    private void carregarProdutos() {
        listaProdutos.clear();
        String sql = "SELECT * FROM estoque";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                listaProdutos.add(new almoxarifado(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("quantidade"),
                        rs.getString("marca"),
                        rs.getString("fornecedor"),
                        rs.getString("localizacao"),
                        rs.getString("categoria"),
                        rs.getString("codigo"),
                        rs.getDouble("preco_de_custo")
                ));
            }
            tableProdutos.setItems(listaProdutos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@FXML
//atualização de produtos
public void atualizarprodutos() {
    int id = Integer.parseInt(txtIdAtualizarProduto.getText());
     String nome = txtNomeAt.getText();
    String quantidade = txtQuantidadeAt.getText();
    String marca= txtMarcaAt.getText();
    String fornecedor = txtFornecedorAt.getText();
    String localizacao = txtLocalizacaoAt.getText();
    String codigo = txtCodigoAt.getText();
    String preco = txtPrecoAt.getText();
    String categoria = cbmCategoriaAt.getValue();

    //conexão com o banco
    try (Connection conn = Database.getConnection();
    //update do banco de dados
         PreparedStatement stmt = conn.prepareStatement("UPDATE funcionarios SET nome = ?, quantidade = ?, marca = ?, fornecedor = ?, localizacao = ?, codigo = ?, preco = ?, categoria = ?")) {
        stmt.setString(1, nome );
        stmt.setString (2,  quantidade);
        stmt.setString(3, marca);
        stmt.setString(4, fornecedor);
        stmt.setString(5, localizacao);
        stmt.setString(6, codigo);
        stmt.setString(7, preco);
        stmt.setString(8, categoria);
        stmt.executeUpdate();
        carregarProdutos();
//mensagem de alerta
        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "produto atualizado com sucesso!");
    } catch (SQLException e) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao atualizar produto: " + e.getMessage());
    }

}


//filtro
@FXML
private void filtrar() {
    FilteredList<almoxarifado> dadosFiltrados = new FilteredList<>(listaProdutos, p -> true);


    dadosFiltrados.setPredicate(produto -> {
        if (!filtroNome.getText().isEmpty() && !produto.getNome().toLowerCase().contains(filtroNome.getText().toLowerCase())) {
            return false;
        }
        if (!filtroQuantidade.getText().isEmpty() && !String.valueOf(produto.getQuantidade()).contains(filtroQuantidade.getText())) {
            return false;
        }
        if (!filtroPreco.getText().isEmpty() && !String.valueOf(produto.getPrecoDeCusto()).contains(filtroPreco.getText())) {
            return false;
        }
        if (!filtroLocalizacao.getText().isEmpty() && !produto.getLocalizacao().toLowerCase().contains(filtroLocalizacao.getText().toLowerCase())) {
            return false;
        }
        if (!filtroMarca.getText().isEmpty() && !produto.getMarca().toLowerCase().contains(filtroMarca.getText().toLowerCase())) {
            return false;
        }
        if (!filtroFornecedor.getText().isEmpty() && !produto.getFornecedor().toLowerCase().contains(filtroFornecedor.getText().toLowerCase())) {
            return false;
        }
        if (cbmFiltro.getValue() != null && !produto.getCategoria().toLowerCase().contains(cbmFiltro.getValue().toLowerCase())) {
            return false;
        }
        return true;
    });

    tableProdutos.setItems(dadosFiltrados);


}






//prencher campos para a atualização
private void preencherCamposAtualizacao() {
    almoxarifado produtoSelecionado = tableProdutos.getSelectionModel().getSelectedItem();
    if (produtoSelecionado != null) {
        txtIdAtualizarProduto.setText(String.valueOf(produtoSelecionado.getId()));
        txtNomeAt.setText(produtoSelecionado.getNome());
        txtQuantidadeAt.setText(String.valueOf(produtoSelecionado.getQuantidade()));
        txtMarcaAt.setText(String.valueOf(produtoSelecionado.getMarca()));
        txtFornecedorAt.setText(String.valueOf(produtoSelecionado.getFornecedor()));
        txtLocalizacaoAt.setText(String.valueOf(produtoSelecionado.getLocalizacao()));
        txtCodigoAt.setText(String.valueOf(produtoSelecionado.getCodigo()));
        txtPrecoAt.setText(String.valueOf(produtoSelecionado.getPrecoDeCusto()));
        cbmCategoriaAt.setValue(produtoSelecionado.getCategoria());

        tabPaneAlmoxarifado.getSelectionModel().select(tabAtualizarProduto);
    }
}


//mensagem de alerta
private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensagem);
    alerta.showAndWait();
}
}
