package com.example.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.example.database.Database;
import com.example.models.Produto;
import com.example.models.ProdutoConferido;
import com.example.models.ProdutoProducao;
import com.example.models.ProdutoReprovado;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProdutoController {
    
   
    @FXML private Button btnDetalhado;
    @FXML private Button btnAtualizarLimpar;
    @FXML private Button btnDetalhadoSair;
    @FXML private Button btnReprovarRevisao;
    @FXML private Button btnAprovarRevisao;
    @FXML private Button btnViewLote;
    @FXML private CheckBox checkSelo;
    @FXML private TextArea textAtualizarDescricao;
    @FXML private DatePicker dateSaidaProducao;
    @FXML private TableView<ProdutoProducao> tableAtualizarTotal;
    @FXML private TableView<ProdutoConferido> tableConferidos;
    @FXML private TableView<Produto> tableRevisao;
    @FXML private ComboBox<String> comboAtualizarStatus;
    @FXML private ComboBox<String> comboViewStatus;
    @FXML private ComboBox<String> comboAtualizarAuditor;
    @FXML private Button btnBuscarLoteProducao;
    @FXML private TextField textAtualizarLote;
    @FXML private TextField textViewLote;
    @FXML private TableColumn<ProdutoConferido, Integer> columChegadaConferidos;
    @FXML private TableColumn<Produto, Integer> columChegadaRevisao;
    @FXML private TableColumn<ProdutoConferido, String> columDescriConferidos;
    @FXML private TableColumn<Produto, String> columDescriRevisao;
    @FXML private TableColumn<ProdutoConferido, Integer> columIdConferidos;
    @FXML private TableColumn<Produto, Integer> columidRevisao;
    @FXML private TableColumn<ProdutoConferido, Integer> columLoteConferidos;
    @FXML private TableColumn<Produto, Integer> columLoteRevisao;
    @FXML private TableColumn<ProdutoConferido, String> columProdutoConferidos;
    @FXML private TableColumn<Produto, String> columProdutoRevisao;
    @FXML private TableColumn<ProdutoConferido, Integer> columSaidaConferidos;
    @FXML private TableColumn<Produto, Integer> columSaidaRevisao;
    @FXML private TableColumn<ProdutoConferido, String> columSeloConferidos;
    @FXML private TableColumn<Produto, String> columSeloRevisao;
    @FXML private TableColumn<ProdutoConferido, String> columStatusConferidos;
    @FXML private TableColumn<Produto, String> columStatusRevisao;
    @FXML private TableColumn<ProdutoProducao, Integer> columIdProducao;
    @FXML private TableColumn<ProdutoProducao, Integer> columLoteProducao;
    @FXML private TableColumn<ProdutoProducao, String> columChegadaProducao;
    @FXML private TableColumn<ProdutoProducao, String> columDescriProducao;
    @FXML private TableColumn<ProdutoProducao, String> columProdutoProducao;
    @FXML private TableColumn<ProdutoProducao, String> columStatusProducao;
    @FXML private TableColumn<ProdutoReprovado, Integer> columChegadaConferidos1;
    @FXML private TableColumn<ProdutoReprovado, String> columDescriConferidos1;
    @FXML private TableColumn<ProdutoReprovado, Integer> columIdConferidos1;
    @FXML private TableColumn<ProdutoReprovado, Integer> columLoteConferidos1;
    @FXML private TableColumn<ProdutoReprovado, Integer> columSaidaConferidos1;
    @FXML private TableColumn<ProdutoReprovado, String> columSeloConferidos1;
    @FXML private TableColumn<ProdutoReprovado, String> columStatusConferidos1;
    @FXML private TableColumn<ProdutoReprovado, String> columProdutoConferidos1;
    @FXML private TableView<ProdutoReprovado> tableRecusados;
    

    private final ObservableList<Produto> listaConferencia = FXCollections.observableArrayList();
    private final ObservableList<ProdutoConferido> listaConcluida = FXCollections.observableArrayList();
    private final ObservableList<ProdutoProducao> listaProducao = FXCollections.observableArrayList();
    private final ObservableList<ProdutoReprovado> listaReprovado = FXCollections.observableArrayList();
    

    private FuncionarioController funcionarioController; 

     

    @FXML
    public void btnTelaDetalhado() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/DetalhadoView.fxml"));
        Parent root = loader.load();
        DetalhadoController detalhadoController = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        detalhadoController.setCheckSelo(checkSelo);
        
    }

    public void setFuncionarioController(FuncionarioController funcionarioController) {
        this.funcionarioController = funcionarioController;
    }

    
    
    @FXML
    public void btnAtualizarLimparAct(ActionEvent event) {
        textAtualizarDescricao.clear();
        dateSaidaProducao.setValue(null);
        comboAtualizarAuditor.setValue(null);
        comboAtualizarStatus.setValue(null);
    }


    @FXML
    public void btnBuscarLoteProducaoAct(ActionEvent event) {
        String textoLote = textAtualizarLote.getText().trim();

   
        if (textoLote.isEmpty()) {
            tableAtualizarTotalProducao(); 
            return;
        }

        try {int numeroLote = Integer.parseInt(textoLote);
            try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tableProducao WHERE lote = ?")) {

                stmt.setInt(1, numeroLote);
                ResultSet rs = stmt.executeQuery();
                listaProducao.clear();

                while (rs.next()) {
                    listaProducao.add(new ProdutoProducao(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getString("status"),
                        rs.getString("produtos"),
                        rs.getInt("lote"),
                        rs.getDate("chegada")
                    ));
                }

                tableAtualizarTotal.setItems(listaProducao);

            } catch (SQLException e) {
            }

            } catch (NumberFormatException e) {
            }
    }

    @FXML
    public void btnViewLoteAct(ActionEvent event) {
        String textoLote = textViewLote.getText().trim();

   
        if (textoLote.isEmpty()) {
            tableConferenciaRevisao(); 
            return;
        }

        try {int numeroLote = Integer.parseInt(textoLote);
            try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tableRevisao WHERE lote = ?")) {

                stmt.setInt(1, numeroLote);
                ResultSet rs = stmt.executeQuery();
                listaConferencia.clear();

                while (rs.next()) {
                    listaConferencia.add(new Produto(
                        rs.getInt("id"),
                        rs.getString("selo"),
                        rs.getString("descricao"),
                        rs.getString("status"),
                        rs.getString("produtos"),
                        rs.getInt("lote"),
                        rs.getDate("chegada"),
                        rs.getDate("saida")
                    ));
                }

                tableRevisao.setItems(listaConferencia);

            } catch (SQLException e) {
            }

            } catch (NumberFormatException e) {
            }

    }


    @FXML
    public void checkSeloAct(ActionEvent event) {
        if (checkSelo.isSelected()) {
            checkSeloValor();
        } else {
            exibirAlertaSelo();
        }
    }

    private String checkSeloValor() {
        if (checkSelo.isSelected()) {
            return "APROVADO";
        } else {
            return null; 
        }
    }

    private void exibirAlertaSelo() {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText("Selo de Qualidade obrigatório para ser Aprovado");
        alerta.setContentText("Você precisa marcar o checkbox para continuar.");
        alerta.showAndWait();
    }


    @FXML
    public void btnAtualizarAutorizarAct(ActionEvent event) {
        String statusLote = comboAtualizarStatus.getValue();
        String auditorSelecionado = comboAtualizarAuditor.getValue();
        String descricaoAuditor = textAtualizarDescricao.getText();
        LocalDate dataChegada = dateSaidaProducao.getValue();
        ProdutoProducao produtoSelecionado = tableAtualizarTotal.getSelectionModel().getSelectedItem();
        checkSelo.setDisable(true);
        
        if (produtoSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum produto selecionado!");
            return;
        }
        if (auditorSelecionado == null || auditorSelecionado.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione um auditor!");
            return;
        }
        if (statusLote == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum Status selecionado!");
            return;
        }
        if (dataChegada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Você precisa selecionar uma data!");
            return;
        }
       
        
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText("Aprovar lote");
        confirmacao.setContentText("Tem certeza que deseja este lote como aprovado?");
        ButtonType sim = new ButtonType("Sim");
        ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacao.getButtonTypes().setAll(sim, nao);

        confirmacao.showAndWait().ifPresent(resposta -> {
        if (resposta == sim) {
            String checkvalor = checkSeloValor();
            Date sqlDate = Date.valueOf(dataChegada);
            String auditorCompleto = auditorSelecionado + " - " + descricaoAuditor;

            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO tableRevisao (selo, descricao, status, produtos, lote, chegada, saida) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                
                stmt.setString(1, checkvalor);
                stmt.setString(2, auditorCompleto);
                stmt.setString(3, statusLote);
                stmt.setString(4, produtoSelecionado.getProdutos());
                stmt.setInt(5, produtoSelecionado.getLote());
                stmt.setDate(6, produtoSelecionado.getChegada());
                stmt.setDate(7, sqlDate);
                stmt.executeUpdate();

                tableConferenciaRevisao();
                listaProducao.clear();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto sendo revisado - salvo com sucesso!");

            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao salvar o produto! " + e.getMessage());
            }
        } else {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelado", "A operação foi cancelada pelo usuário.");
            }
        });
    }

    @FXML
    public void btnAtualizarReprovar(ActionEvent event) {
        ProdutoProducao produtoSelecionado = tableAtualizarTotal.getSelectionModel().getSelectedItem();
        String produtoStatus = comboAtualizarStatus.getSelectionModel().getSelectedItem();
        LocalDate dataChegada = dateSaidaProducao.getValue();
        String seloReprovado = "REPROVADO";
        String statusLote = "REPROVADO";
        String auditorSelecionado = comboAtualizarAuditor.getValue();
        String descricaoAuditor = textAtualizarDescricao.getText();
        

        if (produtoSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum produto selecionado!");
            return;
        }
        if (auditorSelecionado == null || auditorSelecionado.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione um auditor!");
            return;
        }
        if (produtoStatus == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum Status selecionado!");
            return;
        }
        if (dataChegada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Você precisa selecionar uma data!");
            return;
        }

        if (bloqueadorAprovados()) {
            btnReprovarRevisao.setDisable(true);
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Lote Conferido Aprovado - produto selecionado!");
            btnReprovarRevisao.setDisable(false); 
        } else {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação");
            confirmacao.setHeaderText("Reprovar Produto");
            confirmacao.setContentText("Tem certeza que deseja reprovar este produto?");
            ButtonType sim = new ButtonType("Sim");
            ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmacao.getButtonTypes().setAll(sim, nao);

            confirmacao.showAndWait().ifPresent(resposta -> {
                if (resposta == sim) {
                    Date sqlDate = Date.valueOf(dataChegada);
                    String auditorCompleto = auditorSelecionado + " - " + descricaoAuditor;

                    try (Connection conn = Database.getConnection();
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO tableRevisao (selo, descricao, status, produtos, lote, chegada, saida) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                        stmt.setString(1, seloReprovado);
                        stmt.setString(2, auditorCompleto);
                        stmt.setString(3, statusLote);
                        stmt.setString(4, produtoSelecionado.getProdutos());
                        stmt.setInt(5, produtoSelecionado.getLote());
                        stmt.setDate(6, produtoSelecionado.getChegada());
                        stmt.setDate(7, sqlDate);
                        stmt.executeUpdate();

                        tableConferenciaRevisao();
                        diminuirAbril();
                    
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto recusado salvo com sucesso!");
                } catch (SQLException e) {
                    e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao salvar o produto! " + e.getMessage());
                }
            } else {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelado", "Reprovação cancelada.");
                }
            });
        }
    }

    
    private String statusConferido() {
        return "Verificado";
    }
    public void resetarTabela() {
        btnAprovarRevisaoAct(); 
    }

    public void contadorAprovadosDash(){
        try {Connection conn = Database.getConnection();
            PreparedStatement tableDash = conn.prepareStatement("UPDATE tableDash SET aprovados = aprovados + 1 WHERE id = 1");
                tableDash.executeUpdate();

                if (funcionarioController != null) {
                    funcionarioController.carregarDadosDash();
                }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao Informar o Aprovar o Lote! " + e.getMessage());
        }
    }

    public void carregarDadosDash() {
        try {Connection conn = Database.getConnection();
            PreparedStatement tableDash = conn.prepareStatement("SELECT * FROM tableDash WHERE id = 1");
                tableDash.executeUpdate();

                if (funcionarioController != null) {
                    funcionarioController.carregarDadosDash();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void contadorReprovadosDash(){
        try {Connection conn = Database.getConnection();
            PreparedStatement tableDash = conn.prepareStatement("UPDATE tableDash SET reprovados = reprovados + 1 WHERE id = 1");
                tableDash.executeUpdate();

                if (funcionarioController != null) {
                    funcionarioController.carregarDadosDash();
                }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao Informar o Reprovar o Lote! " + e.getMessage());
        }
    }

    public void aumentarAbril(){
        
        try {Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE barChart SET quantidade = quantidade + 1 WHERE id = 4");
            stmt.executeUpdate();
                    
        } catch (Exception e) {
             e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao Unputar o Grafico de Aprovados! " + e.getMessage());
        }
        
    }

    public void diminuirAbril(){
        
        try {Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE barChart1 SET quantidade = quantidade + 1 WHERE id = 4");
            stmt.executeUpdate();
                    
        } catch (Exception e) {
             e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao Unputar o Grafico de Reprovados! " + e.getMessage());
        }
        
    }

    



  
    @FXML
    public void btnAprovarRevisaoAct() {
        Produto produtoSelecionado = tableRevisao.getSelectionModel().getSelectedItem();

        
        
         
        if (produtoSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum produto selecionado!");
            return;
        }

        if (bloqueadorRecusados()) {
            btnAprovarRevisao.setDisable(true);
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Lote Conferido Reprovado - produto selecionado!");
            btnAprovarRevisao.setDisable(false); 
        } else {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação");
            confirmacao.setHeaderText("Aprovar Produto");
            confirmacao.setContentText("Tem certeza que deseja aprovar este produto?");
    
            ButtonType sim = new ButtonType("Sim");
            ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmacao.getButtonTypes().setAll(sim, nao);

            confirmacao.showAndWait().ifPresent(resposta -> {
                if (resposta == sim) {
                    try (Connection conn = Database.getConnection();
                        PreparedStatement stmt = conn.prepareStatement("INSERT INTO tableConferidos (selo, descricao, status, produtos, lote, chegada, saida) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                        stmt.setString(1, produtoSelecionado.getSelo());
                        stmt.setString(2, produtoSelecionado.getDescricao());
                        stmt.setString(3, statusConferido());
                        stmt.setString(4, produtoSelecionado.getProdutos());
                        stmt.setInt(5, produtoSelecionado.getLote());
                        stmt.setDate(6, produtoSelecionado.getChegada());
                        stmt.setDate(7, produtoSelecionado.getSaida());
                        stmt.executeUpdate();

                        deletarbtnRevisaoRecusados();
                        tableConferenciaRevisao();
                        contadorAprovadosDash();
                        carregarDadosDash();

                        
                        aumentarAbril();
                        

                        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto revisado salvo com sucesso!");

                    } catch (SQLException e) {
                        e.printStackTrace();
                        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao salvar o produto! " + e.getMessage());
                    }
            } else {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelado", "Aprovação cancelada.");
                }
            });
                btnAprovarRevisao.setDisable(false);
                resetarTabela();
                tableConferenciaRevisao();
                listaConferencia.clear();
            
        }

        
    }


    @FXML
    public void btnReprovarRevisaoAct(ActionEvent event) {
        Produto produtoSelecionado = tableRevisao.getSelectionModel().getSelectedItem();

        if (produtoSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum produto selecionado!");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText("Reprovar Produto");
        confirmacao.setContentText("Tem certeza que deseja reprovar este produto?");
        ButtonType sim = new ButtonType("Sim");
        ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacao.getButtonTypes().setAll(sim, nao);

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == sim) {
                try (Connection conn = Database.getConnection();
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO tableRecusados (selo, descricao, status, produtos, lote, chegada, saida) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                    stmt.setString(1, produtoSelecionado.getSelo());
                    stmt.setString(2, produtoSelecionado.getDescricao());
                    stmt.setString(3, produtoSelecionado.getStatus());
                    stmt.setString(4, produtoSelecionado.getProdutos());
                    stmt.setInt(5, produtoSelecionado.getLote());
                    stmt.setDate(6, produtoSelecionado.getChegada());
                    stmt.setDate(7, produtoSelecionado.getSaida());
                    stmt.executeUpdate();

                
                    deletarbtnRevisaoRecusados();
                    tableConferenciaRevisao();
                    tableRevisaoRecusados();
                    contadorReprovadosDash();
                    carregarDadosDash();

                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto recusado salvo com sucesso!");
                } catch (SQLException e) {
                    e.printStackTrace();
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao salvar o produto! " + e.getMessage());
                }
            } else {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelado", "Reprovação cancelada.");
                }
            });
    }

    


    @FXML
    public void initialize() {
       
        columidRevisao.setCellValueFactory(new PropertyValueFactory<>("id"));
        columSeloRevisao.setCellValueFactory(new PropertyValueFactory<>("selo"));
        columDescriRevisao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        columStatusRevisao.setCellValueFactory(new PropertyValueFactory<>("status"));
        columProdutoRevisao.setCellValueFactory(new PropertyValueFactory<>("produtos"));
        columLoteRevisao.setCellValueFactory(new PropertyValueFactory<>("lote"));
        columChegadaRevisao.setCellValueFactory(new PropertyValueFactory<>("chegada"));
        columSaidaRevisao.setCellValueFactory(new PropertyValueFactory<>("saida"));

        columIdConferidos.setCellValueFactory(new PropertyValueFactory<>("id"));
        columSeloConferidos.setCellValueFactory(new PropertyValueFactory<>("selo"));
        columDescriConferidos.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        columStatusConferidos.setCellValueFactory(new PropertyValueFactory<>("status"));
        columProdutoConferidos.setCellValueFactory(new PropertyValueFactory<>("produtos"));
        columLoteConferidos.setCellValueFactory(new PropertyValueFactory<>("lote"));
        columChegadaConferidos.setCellValueFactory(new PropertyValueFactory<>("chegada"));
        columSaidaConferidos.setCellValueFactory(new PropertyValueFactory<>("saida"));

        columIdProducao.setCellValueFactory(new PropertyValueFactory<>("id"));
        columDescriProducao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        columStatusProducao.setCellValueFactory(new PropertyValueFactory<>("status"));
        columProdutoProducao.setCellValueFactory(new PropertyValueFactory<>("produtos"));
        columLoteProducao.setCellValueFactory(new PropertyValueFactory<>("lote"));
        columChegadaProducao.setCellValueFactory(new PropertyValueFactory<>("chegada"));

        columIdConferidos1.setCellValueFactory(new PropertyValueFactory<>("id"));
        columSeloConferidos1.setCellValueFactory(new PropertyValueFactory<>("selo"));
        columDescriConferidos1.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        columStatusConferidos1.setCellValueFactory(new PropertyValueFactory<>("status"));
        columProdutoConferidos1.setCellValueFactory(new PropertyValueFactory<>("produtos"));
        columLoteConferidos1.setCellValueFactory(new PropertyValueFactory<>("lote"));
        columChegadaConferidos1.setCellValueFactory(new PropertyValueFactory<>("chegada"));
        columSaidaConferidos1.setCellValueFactory(new PropertyValueFactory<>("saida"));

        
        comboAtualizarAuditor.getItems().addAll("Antonio","Pedro");
        comboAtualizarStatus.getItems().addAll("Revisão");
        
        tableConferenciaRevisao();
        tableRevisaoRecusados();
        tableConferenciaConferidos();
        tableAtualizarTotalProducao();
        checkSelo.setDisable(true);
        

       
    }

    public void tableAtualizarTotalProducao(){
        listaProducao.clear();
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tableProducao")) {

            while (rs.next()) {
                listaProducao.add(new ProdutoProducao(rs.getInt("id"), rs.getString("descricao"), rs.getString("status"), rs.getString("produtos"), rs.getInt("lote"), rs.getDate("chegada")));
            }
            tableAtualizarTotal.setItems(listaProducao);
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }

    public void tableConferenciaRevisao(){
        listaConferencia.clear(); 
        
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tableRevisao")) {

            while (rs.next()) {
                listaConferencia.add(new Produto(rs.getInt("id"), rs.getString("selo"), rs.getString("descricao"), rs.getString("status"), rs.getString("produtos"), rs.getInt("lote"), rs.getDate("chegada"), rs.getDate("saida")));
            }
            tableRevisao.setItems(listaConferencia);
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }

    public void tableConferenciaConferidos(){
        listaConcluida.clear();  
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tableConferidos")) {

            while (rs.next()) {
                listaConcluida.add(new ProdutoConferido(rs.getInt("id"), rs.getString("selo"), rs.getString("descricao"), rs.getString("status"), rs.getString("produtos"), rs.getInt("lote"), rs.getDate("chegada"), rs.getDate("saida")));
            }
            tableConferidos.setItems(listaConcluida);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR,"Erro","Erro ao carregar: " + e.getMessage());
        }
        
    }
    
    public void tableRevisaoRecusados(){
        listaReprovado.clear();  
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tableRecusados")) {

            while (rs.next()) {
                listaReprovado.add(new ProdutoReprovado(rs.getInt("id"), rs.getString("selo"), rs.getString("descricao"), rs.getString("status"), rs.getString("produtos"), rs.getInt("lote"), rs.getDate("chegada"), rs.getDate("saida")));
            }
            tableRecusados.setItems(listaReprovado);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR,"Erro","Erro ao carregar: " + e.getMessage());
        }
        
    }


    public void deletarbtnRevisaoRecusados(){
        Produto produtoSelecionado = tableRevisao.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM tableRevisao WHERE id = ?")) {

                stmt.setInt(1, produtoSelecionado.getId());
                stmt.executeUpdate();

                tableConferenciaConferidos();
                mostrarAlerta(Alert.AlertType.INFORMATION,"Sucesso" , "Produto excluído com sucesso!");
                tableRevisao.getSelectionModel().select(produtoSelecionado);

            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao excluir o produto!" + e.getMessage());
            }
        }
    }

    public void deletarbtnProducaoAprovados(){
        Produto produtoSelecionado = tableRevisao.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM tableProducao WHERE id = ?")) {

                stmt.setInt(1, produtoSelecionado.getId());
                stmt.executeUpdate();

                tableConferenciaConferidos();
                mostrarAlerta(Alert.AlertType.INFORMATION,"Sucesso" , "Produto excluído com sucesso!");
                tableRevisao.getSelectionModel().select(produtoSelecionado);

            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao excluir o produto!" + e.getMessage());
            }
        }
    }

    public boolean bloqueadorRecusados() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT status FROM tableRevisao")) {
    
            while (rs.next()) {
                String status = rs.getString("status");
                if ("REPROVADO".equalsIgnoreCase(status)) {
                    return true; 
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return false; 
    }

    public boolean bloqueadorAprovados() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT status FROM tableRevisao")) {
    
            while (rs.next()) {
                String status = rs.getString("selo");
                if ("APROVADO".equalsIgnoreCase(status)) {
                    return true; 
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return false; 
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    
    
       
        
}

  



    

   
    

