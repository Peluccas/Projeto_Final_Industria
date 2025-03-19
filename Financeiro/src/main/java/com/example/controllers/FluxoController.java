package com.example.controllers;

import com.example.database.Database;
import com.example.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class FluxoController {
    @FXML private TextField txtData;
    @FXML private ChoiceBox<String> cmbSetor;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtValor;
    @FXML private ChoiceBox<String> cmbCategoria;
    @FXML private ChoiceBox<String> cmbPagto;
    @FXML private TextField txtVencimento; 
    @FXML private ChoiceBox<String> cmbStatus;
    @FXML private TableView<Fluxo> tableFluxo;

    //Filto pagamento Funcionario
    @FXML private ChoiceBox<String> cmbFiltroSetor;
    @FXML private ChoiceBox<String> cmbFiltroData;
    @FXML private ChoiceBox<String> cmbFiltroStatus;


    //Tabela tab Fluxo
    @FXML private TableColumn<Fluxo, String> colFluData;
    @FXML private TableColumn<Fluxo, String> colFluSetor;
    @FXML private TableColumn<Fluxo, String> colFluDescricao;
    @FXML private TableColumn<Fluxo, Double> colFluValor;
    @FXML private TableColumn<Fluxo, String> colFluCategoria;
    @FXML private TableColumn<Fluxo, Double> colFluPagto;
    @FXML private TableColumn<Fluxo, String> colFluVencimento;
    @FXML private TableColumn<Fluxo, String> colFluStatus;

    //Tabela tab Solicitações
    @FXML private TableView<Solicitacoes> tableSolicitacoes;
    @FXML private TableColumn<Solicitacoes, String> colSoData;
    @FXML private TableColumn<Solicitacoes, String> colSoSetor;
    @FXML private TableColumn<Solicitacoes, String> colSoDescricao;
    @FXML private TableColumn<Solicitacoes, String> colSoQuantidade;
    @FXML private TableColumn<Solicitacoes, Double> colSoValor;
    @FXML private TableColumn<Solicitacoes, String> colSoPrazo;
    @FXML private TableColumn<Solicitacoes, String> colSoStatus;

    //Tabela tab Pagamento Funcionario
    @FXML private TableView<PagFuncionarios> tablePagamento;
    @FXML private TableColumn<PagFuncionarios, String> colPagFuncionario;
    @FXML private TableColumn<PagFuncionarios, String> colPagSetor;
    @FXML private TableColumn<PagFuncionarios, String> colPagData;
    @FXML private TableColumn<PagFuncionarios, Double> colPagSalario;
    @FXML private TableColumn<PagFuncionarios, Double> colPagDescontos;
    @FXML private TableColumn<PagFuncionarios, Double> colPagLiquido;
    @FXML private TableColumn<PagFuncionarios, Boolean> colPagStatus;

    //Campos para atualização
    @FXML private TextField txtDataAtualizar;
    @FXML private ChoiceBox<String> cmbSetorAtualizar;
    @FXML private TextField txtDescricaoAtualizar;
    @FXML private TextField txtValorAtualizar;
    @FXML private ChoiceBox<String> cmbCategoriaAtualizar;
    @FXML private ChoiceBox<String> cmbPagtoAtualizar;
    @FXML private TextField txtVencimentoAtualizar; 
    @FXML private ChoiceBox<String> cmbStatusAtualizar;

    //import dos filtros
    @FXML private TextField filtroData;
    @FXML private ChoiceBox<String> filtroSetor;
    @FXML private TextField filtroDescricao;
    @FXML private TextField filtroValor;
    @FXML private ChoiceBox<String> filtroCategoria;
    @FXML private TextField filtroVencimento;
    @FXML private ChoiceBox<Boolean> filtroStatus;


    @FXML private TabPane tabPane;
    @FXML private Tab tabFluxo;
    @FXML private Tab tabSolicitacoes;
    @FXML private Tab tabPagFuncionarios;
    @FXML private Tab tabRelatorio;


    
        private ObservableList<Fluxo> listaFluxo = FXCollections.observableArrayList();
        private ObservableList<Solicitacoes> listaSolicitacoes = FXCollections.observableArrayList();
        private ObservableList<PagFuncionarios> listaFuncionarios = FXCollections.observableArrayList();

    @FXML
    private void adicionarFluxo() {
        String data = String.valueOf(txtData.getText());
        String descricao = txtDescricao.getText();
        double valor = Double.parseDouble(txtValor.getText());
        String vencimento = String.valueOf(txtVencimento.getText()); 
        String status = String.valueOf(cmbStatusAtualizar.getValue());
        Boolean status_validado;

        if("Concluida".equals(status)){
            status_validado = true;           
        }else{
            status_validado = false;
        }


        try (Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO fluxo (data_transacao, fk_setor, descricao, valor, categoria, forma_pagto, vencimento, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
           
            stmt.setString(1, data);
            stmt.setString(2, cmbSetor.getValue());
            stmt.setString(3,descricao);
            stmt.setDouble(4, valor);
            stmt.setString(5, cmbCategoria.getValue());
            stmt.setString(6, cmbPagto.getValue());
            stmt.setString(7, vencimento);
            stmt.setBoolean(8, status_validado);
            stmt.executeUpdate();

            listaFluxo();
                
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void atualizarFluxo() {
        String data = String.valueOf(txtDataAtualizar.getText());
        String descricao = txtDescricaoAtualizar.getText();
        double valor = Double.parseDouble(txtValorAtualizar.getText());
        String vencimento = String.valueOf(txtVencimentoAtualizar.getText());
        String status = String.valueOf(cmbStatusAtualizar.getValue());
        Boolean status_validado;

        if("Concluida".equals(status)){
            status_validado = true;           
        }else{
            status_validado = false;
        }

        try (Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE fluxo SET data_transacao = ?, fk_setor = ?, descricao = ?, valor = ?, categoria = ?, forma_pagto = ?, vencimento = ?, status = ? WHERE id = ?")) {
        
            stmt.setString(1, data);
            stmt.setString(2, cmbSetorAtualizar.getValue());
            stmt.setString(3, descricao);
            stmt.setDouble(4, valor);
            stmt.setString(5, cmbCategoriaAtualizar.getValue());
            stmt.setString(6, cmbPagtoAtualizar.getValue());
            stmt.setString(7, vencimento);
            stmt.setBoolean(8, status_validado);

            listaFluxo();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void excluirFluxo() {
       Fluxo fluxoSelecionado = tableFluxo.getSelectionModel().getSelectedItem();
       if (fluxoSelecionado != null){
          try(Connection conn = Database.getConnection();
          PreparedStatement stmt = conn.prepareStatement("DELETE FROM fluxo WHERE id = ?")) { 

            stmt.setInt(1, fluxoSelecionado.getId());
            stmt.executeUpdate();

            listaFluxo();

         } catch (SQLException e){
          e.printStackTrace();
         }

        }
    }

    @FXML
    private void listaFluxo(){
        listaFluxo.clear();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM fluxo")) {
                while (rs.next()) {
                    listaFluxo.add(new Fluxo(rs.getInt("id"), rs.getString("data_transacao"), rs.getInt("fk_setor"), rs.getString("descricao"), rs.getDouble("valor"), rs.getString("categoria"), rs.getString("forma_pagto"), rs.getString("vencimento"), rs.getBoolean("status")));
                }
                tableFluxo.setItems(listaFluxo);  
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /* 
     private void preencherCamposAtualizacao() {
        Fluxo fluxoSelecionado = tableFluxo.getSelectionModel().getSelectedItem();
        if (fluxoSelecionado != null){
            txtDataAtualizar.setText(ValueOf(fluxoSelecionado.getData_transacao()));
            cmbSetorAtualizar.setValue(fluxoSelecionado.getFk_setor());
            txtDescricaoAtualizar.setText(fluxoSelecionado.getDescricao());
            txtValorAtualizar.setText(String.valueOf(fluxoSelecionado.getValor()));
            cmbCategoriaAtualizar.setValue(fluxoSelecionado.getCategoria());
            cmbPagtoAtualizar.setValue(fluxoSelecionado.getForma_pagto());
            txtVencimentoAtualizar.setText(valueOf(fluxoSelecionado.getVencimento()));
            cmbStatusAtualizar.setValue(valueOf(fluxoSelecionado.getStatus()));
             
         tabFluxo.getSelectionModel().select(tabFluxo);

        }
    }
     */

    public void limparCampos() {
        txtDataAtualizar.clear();
        cmbSetorAtualizar.setValue(null);
        txtDescricaoAtualizar.clear();
        txtValorAtualizar.clear();
        cmbCategoriaAtualizar.setValue(null);
        cmbPagtoAtualizar.setValue(null);
        txtVencimentoAtualizar.clear();
        cmbStatusAtualizar.setValue(null);
    }



    @FXML
    public void initialize() {
        colFluData.setCellValueFactory(new PropertyValueFactory<>("data_transacao"));
        colFluSetor.setCellValueFactory(new PropertyValueFactory<>("fk_setor"));
        colFluDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colFluValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colFluCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colFluPagto.setCellValueFactory(new PropertyValueFactory<>("forma_pagto"));
        colFluVencimento.setCellValueFactory(new PropertyValueFactory<>("vencimento"));
        colFluStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        //Inicialização Solicitações
        colSoData.setCellValueFactory(new PropertyValueFactory<>("data_solicitacao"));
        colSoSetor.setCellValueFactory(new PropertyValueFactory<>("fk_setor"));
        colSoDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colSoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colSoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colSoPrazo.setCellValueFactory(new PropertyValueFactory<>("prazo"));
        colSoStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        //Inicialização Pagamentos
        colPagFuncionario.setCellValueFactory(new PropertyValueFactory<>("fk_funcionarios"));
        colPagSetor.setCellValueFactory(new PropertyValueFactory<>("fk_Setor"));
        colPagData.setCellValueFactory(new PropertyValueFactory<>("data_pagto"));
        colPagSalario.setCellValueFactory(new PropertyValueFactory<>("salario_base"));
        colPagDescontos.setCellValueFactory(new PropertyValueFactory<>("descontos"));
        colPagLiquido.setCellValueFactory(new PropertyValueFactory<>("valor_liquido"));
        colPagStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        cmbFiltroSetor.getItems().addAll("RH", "Automação", "Produção", "Estoque", "Controle de Qualidade");
        cmbFiltroData.getItems().addAll("2020", "2021", "2022", "2023", "2024","2025");
        cmbFiltroStatus.getItems().addAll("Concluida", "Pendente");

        listaFuncionarios();

        
        listaSolicitacoes();



        cmbSetor.getItems().addAll("RH", "Automação", "Produção", "Estoque", "Controle de Qualidade");
        cmbCategoria.getItems().addAll("Compra","Venda", "Serviço");
        cmbPagto.getItems().addAll("Cartão de Crédito", "Transferência", "Boleto", "Pix");
        cmbStatus.getItems().addAll("Concluida", "Pendente");

        listaFluxo();

    }

    @FXML
    private void listaSolicitacoes(){
            listaSolicitacoes.clear();
           try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM solicitacoes")) {
                   while (rs.next()) {
                       listaSolicitacoes.add(new Solicitacoes(rs.getInt("id"), rs.getString("data_solicitacao"), rs.getInt("fk_setor"), rs.getString("descricao"), rs.getString("quantidade"), rs.getDouble("valor"), rs.getString("prazo"), rs.getString("status")));
                   }
                   tableSolicitacoes.setItems(listaSolicitacoes);  
           }catch (SQLException e){
               e.printStackTrace();
           }
       }
   
       @FXML
       private void aceitar() {
          
           try (Connection conn = Database.getConnection();
           PreparedStatement stmt = conn.prepareStatement("update solicitacoes set status = 'Aprovada' where id_solicitacoes = ?")) {
           
                     
               listaSolicitacoes();
   
           } catch (SQLException e){
               e.printStackTrace();
           }
       }
   
       @FXML
       private void recusar() {
          
           try (Connection conn = Database.getConnection();
           PreparedStatement stmt = conn.prepareStatement("update solicitacoes set status = 'Recusada' where id_solicitacoes = ?")) {
           
                     
               listaSolicitacoes();
   
           } catch (SQLException e){
               e.printStackTrace();
           }
       }
   
    @FXML
    private void listaFuncionarios(){
            listaFuncionarios.clear();
           try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM pagfuncionarios")) {
                   while (rs.next()) {
                       listaFuncionarios.add(new PagFuncionarios(rs.getInt("id"), rs.getInt("fk_funcionarios"), rs.getInt("fk_setor"), rs.getString("data_pagto"), rs.getDouble("salario_base"), rs.getDouble("descontos"), rs.getDouble("valor_liquido"), rs.getBoolean("status")));
                   }
                   tablePagamento.setItems(listaFuncionarios);  
           }catch (SQLException e){
               e.printStackTrace();
           }
       }


}