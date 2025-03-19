package com.example.controllers;

import com.example.database.Database;
import com.example.models.AutomacaoRH;
import com.example.models.Produto;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.*;

public class AutomacaoController {
    @FXML private TextField txtNomeDaAutomacao;
    @FXML private TextField txtResponsavel;
    @FXML private TextArea txtDescricao;
    @FXML private TableView<AutomacaoRH> tablesAutomacaoRH;
    @FXML private TableColumn<AutomacaoRH, String> colId;
    @FXML private TableColumn<AutomacaoRH, String> colNomeAutomacao;
    @FXML private TableColumn<AutomacaoRH, String> colResponsavel;
    @FXML private TableColumn<AutomacaoRH, String> colSetor;
    @FXML private TableColumn<AutomacaoRH, String> colDescricao;
    @FXML private TableColumn<AutomacaoRH, String> colLocalizacao;
    @FXML private TableColumn<AutomacaoRH, String> colCategoria;
    @FXML private TableColumn<AutomacaoRH, String> colOperacao;
    @FXML private TableColumn<AutomacaoRH, String> colPrioridade;
    @FXML private TableColumn<AutomacaoRH, String> colSituacao;
    @FXML private TableColumn<AutomacaoRH, String> colObservacao;
    @FXML private ComboBox<String> cmbCategoria;
    @FXML private ComboBox<String> cmbPrioridade;
    @FXML private ComboBox<String> cmbSituacao;
    @FXML private TextField txtLocalizacao;
    @FXML private TextField txtOperacao;
    @FXML private TextField txtSetor;
    @FXML private TextArea txtObservacao;

    //import dos filtros
    @FXML private TextField filtroNomeAut;
    @FXML private TextField filtroSetorAut;
    @FXML private TextField filtroResponsavelAut;
    @FXML private TextField filtroLocalizacaoAut;
    @FXML private TextField filtroOperacaoAut;
    @FXML private Button btnLimparFiltro;

    private ObservableList<AutomacaoRH> listaAutomacaoRH = FXCollections.observableArrayList();

    @FXML
    private void salvarAutomacaoRH() {
  
       

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO automacaoRH (nome_automacao, responsavel, categoria, descricao, operacao, setor, observacao, localizacao, situacao, prioridade) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, txtNomeDaAutomacao.getText());
            stmt.setString(2, txtResponsavel.getText());
            stmt.setString(3, cmbCategoria.getValue());                  
            stmt.setString(4, txtDescricao.getText());
            stmt.setString(5, txtOperacao.getText());
            stmt.setString(6, txtSetor.getText());
            stmt.setString(7, txtObservacao.getText());
            stmt.setString(8, txtLocalizacao.getText());
            stmt.setString(9, cmbSituacao.getValue());
            stmt.setString(10, cmbPrioridade.getValue());

            stmt.executeUpdate();

            carregarAutomacaoRH();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Automação salva com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
             mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao salvar a automação!" +  e.getMessage());
        }
    }



    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeAutomacao.setCellValueFactory(new PropertyValueFactory<>("nomeAutomacao"));
        colResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
        colSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        colLocalizacao.setCellValueFactory(new PropertyValueFactory<>("localizacao"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colOperacao.setCellValueFactory(new PropertyValueFactory<>("operacao"));
        colSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
        colPrioridade.setCellValueFactory(new PropertyValueFactory<>("prioridade"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colObservacao.setCellValueFactory(new PropertyValueFactory<>("observacao"));
        



        cmbCategoria.getItems().addAll( "Recrutamento e Seleção", "Folha De Pagamento", "Treinamento e Desenvolvimento", "Comunicação Interna");
        cmbPrioridade.getItems().addAll("Baixa","Média","Alta");
        cmbSituacao.getItems().addAll("ativo", "inativo");

        carregarAutomacaoRH();

        tablesAutomacaoRH.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                preencherCamposAtualizacao();
            }
        });
    }
    private void preencherCamposAtualizacao() {
        AutomacaoRH automacaoSelecionada = tablesAutomacaoRH.getSelectionModel().getSelectedItem();
        if (automacaoSelecionada != null) {
            txtNomeDaAutomacao.setText(automacaoSelecionada.getNomeAutomacao());
            txtResponsavel.setText(automacaoSelecionada.getResponsavel());
            cmbCategoria.setValue(automacaoSelecionada.getCategoria());
            txtDescricao.setText(automacaoSelecionada.getDescricao());
            txtOperacao.setText(automacaoSelecionada.getOperacao());
            txtSetor.setText(automacaoSelecionada.getSetor());
            txtObservacao.setText(automacaoSelecionada.getObservacao());
            txtLocalizacao.setText(automacaoSelecionada.getLocalizacao());
            cmbSituacao.setValue(automacaoSelecionada.getSituacao());
            cmbPrioridade.setValue(automacaoSelecionada.getPrioridade());
        }
    }
    @FXML
public void atualizarAutomacao() {
    AutomacaoRH automacaoSelecionada = tablesAutomacaoRH.getSelectionModel().getSelectedItem();
    
    if (automacaoSelecionada != null) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE automacaoRH SET nome_automacao = ?, responsavel = ?, categoria = ?, descricao = ?, operacao = ?, setor = ?, observacao = ?, localizacao = ?, situacao = ?, prioridade = ? WHERE id = ?")) {
            
            stmt.setString(1, txtNomeDaAutomacao.getText());
            stmt.setString(2, txtResponsavel.getText());
            stmt.setString(3, cmbCategoria.getValue());
            stmt.setString(4, txtDescricao.getText());
            stmt.setString(5, txtOperacao.getText());
            stmt.setString(6, txtSetor.getText());
            stmt.setString(7, txtObservacao.getText());
            stmt.setString(8, txtLocalizacao.getText());
            stmt.setString(9, cmbSituacao.getValue());
            stmt.setString(10, cmbPrioridade.getValue());
            stmt.setInt(11, automacaoSelecionada.getId());
            
            stmt.executeUpdate();
            
            carregarAutomacaoRH(); // Atualiza a tabela após a atualização
            
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Automação atualizada com sucesso!");
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao atualizar automação: " + e.getMessage());
        }
    } else {
        mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma automação para atualizar!");
    }
}

    @FXML
    private void carregarAutomacaoRH() {
        listaAutomacaoRH.clear();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM automacaoRH")) {

            while (rs.next()) {
                listaAutomacaoRH.add(new AutomacaoRH(rs.getInt("id"), rs.getString("nome_automacao"), rs.getString("responsavel"), rs.getString("categoria"), rs.getString("descricao"), rs.getString("operacao"), rs.getString("setor"), rs.getString("observacao"), rs.getString("localizacao"), rs.getString("situacao"), rs.getString(("prioridade"))));
            }
            tablesAutomacaoRH.setItems(listaAutomacaoRH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//função para filtrar os produtos
   @FXML
    private void filtrarAutomacao() {
        FilteredList<AutomacaoRH> dadosFiltrados = new FilteredList<>(listaAutomacaoRH, p -> true);

        dadosFiltrados.setPredicate(produto -> {
            if (!filtroNomeAut.getText().isEmpty() && !produto.getNome().toLowerCase().contains(filtroNomeAut.getText().toLowerCase())) {
                return false;
            }
            if (!filtroSetorAut.getText().isEmpty() && !String.valueOf(produto.getSetor()).contains(filtroSetorAut.getText())) {
                return false;
            }
            if (!filtroOperacaoAut.getText().isEmpty() && !String.valueOf(produto.getOperacao()).contains(filtroOperacaoAut.getText())) {
                return false;
            }
            if (!filtroLocalizacaoAut.getText().isEmpty() && !produto.getLocalizacao().toLowerCase().contains(filtroLocalizacaoAut.getText().toLowerCase())) {
                return false;
            }
            if (!filtroResponsavelAut.getText().isEmpty() && !produto.getLocalizacao().toLowerCase().contains(filtroResponsavelAut.getText().toLowerCase())) {
                return false;
            }
            return true;
        });

        tablesAutomacaoRH.setItems(dadosFiltrados);
    }

    @FXML
    private void limparFiltro() {
        filtroNomeAut.clear();
        filtroResponsavelAut.clear();
        filtroSetorAut.clear();
        filtroLocalizacaoAut.clear();
        filtroOperacaoAut.clear();
        tablesAutomacaoRH.setItems(listaAutomacaoRH);
    }














    @FXML
public void deleteArh() {
    AutomacaoRH automacaoSelecionada = tablesAutomacaoRH.getSelectionModel().getSelectedItem();
    
    if (automacaoSelecionada != null) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM automacaoRH WHERE id = ?")) {
            
            stmt.setInt(1, automacaoSelecionada.getId());
            stmt.executeUpdate();
            
            carregarAutomacaoRH(); // Atualiza a tabela após a exclusão
            
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Automação excluída com sucesso!");
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao excluir automação: " + e.getMessage());
        }
    } else {
        mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma automação para excluir!");
    }
}

    private void mostrarAlerta(AlertType tipo, String titulo, String mensagem) {
    Platform.runLater(() -> {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.show();
    });
}
}
