package com.example.controllers;

import com.example.database.Database;
import com.example.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;
import java.time.LocalDate;

public class FluxoController {
    @FXML private DatePicker DataTransacao;
    @FXML private ChoiceBox<String> cmbSetor;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtValor;
    @FXML private ChoiceBox<String> cmbCategoria;
    @FXML private ChoiceBox<String> cmbPagto;
    @FXML private DatePicker DataVencimento; 
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
    @FXML private TableColumn<PagFuncionarios, String> colPagStatus;
    @FXML private TextField txtFuncionarioPagto;
    @FXML private ComboBox<String> cmbStatusPagto;
    @FXML private DatePicker filtroPagto;




    //Tabela Relatorio
    @FXML private TableView<Fluxo> tableRelatorio;
    @FXML private TableColumn<Fluxo, String> colReData;
    @FXML private TableColumn<Fluxo, String> colReSetor;
    @FXML private TableColumn<Fluxo, String> colReDescricao;
    @FXML private TableColumn<Fluxo, Double> colReValor;
    @FXML private TableColumn<Fluxo, String> colReCategoria;
    @FXML private TableColumn<Fluxo, Double> colReForma;
    @FXML private TableColumn<Fluxo, String> colReVencimento;
    @FXML private TableColumn<Fluxo, String> colReStatus;

    @FXML private DatePicker dataInicio;
    @FXML private DatePicker dataFim;

    //Campos para atualização
    @FXML private DatePicker DataTransacaoAtualizar;
    @FXML private ComboBox<String> cmbSetorAtualizar;
    @FXML private TextField txtDescricaoAtualizar;
    @FXML private TextField txtValorAtualizar;
    @FXML private ChoiceBox<String> cmbCategoriaAtualizar;
    @FXML private ChoiceBox<String> cmbPagtoAtualizar;
    @FXML private DatePicker DataVencimentoAtualizar; 
    @FXML private ComboBox<String> cmbStatusAtualizar;

    //import dos filtros
    @FXML private TextField filtroData;
    @FXML private ChoiceBox<String> filtroSetor;
    @FXML private TextField filtroDescricao;
    @FXML private TextField filtroValor;
    @FXML private ChoiceBox<String> filtroCategoria;
    @FXML private TextField filtroVencimento;
    @FXML private ChoiceBox<Boolean> filtroStatus;


   //Declaração de Tabs
    @FXML private TabPane tabPane;
    @FXML private Tab tabFluxo;
    @FXML private Tab tabSolicitacoes;
    @FXML private Tab tabPagFuncionarios;
    @FXML private Tab tabRelatorio;
    @FXML private Tab tabAtualizar;


    
        private ObservableList<Fluxo> listaFluxo = FXCollections.observableArrayList();
        private ObservableList<Solicitacoes> listaSolicitacoes = FXCollections.observableArrayList();
        private ObservableList<PagFuncionarios> listaFuncionarios = FXCollections.observableArrayList();
        private ObservableList<Fluxo> listaRelatorio = FXCollections.observableArrayList();

        @FXML
        private void adicionarFluxo() {

            if (DataTransacao.getValue() == null || DataVencimento.getValue() == null || 
        cmbSetor.getValue() == null || txtDescricao.getText().isEmpty() || 
        txtValor.getText().isEmpty() || cmbStatus.getValue() == null || 
        cmbCategoria.getValue() == null || cmbPagto.getValue() == null) {

        mostrarAlerta(Alert.AlertType.ERROR, "Atenção!", "Todos os campos devem ser preenchidos!");
    }

           LocalDate dataTran = DataTransacao.getValue();
           LocalDate dataVen = DataVencimento.getValue();
            String setor = String.valueOf(cmbSetor.getValue());
            String descricao = txtDescricao.getText();
            double valor = Double.parseDouble(txtValor.getText());
            String status = String.valueOf(cmbStatus.getValue());
            Boolean status_validado;
            Integer setor_validado = null;

            if("Concluida".equals(status)){
                status_validado = true;           
            }else{
                status_validado = false;
            }

              
            if ("RH".equals(setor)) {
                setor_validado = 1;
            } else if ("AUTOMAÇÃO".equals(setor)) {
                setor_validado = 2;
            } else if ("PRODUÇÃO".equals(setor)) {
                setor_validado = 3;
            } else if ("ESTOQUE".equals(setor)) {
                setor_validado = 4;
            } else if ("CONTROLE DE QUALIDADE".equals(setor)) {
                setor_validado = 5;
            } else if ("FINANCEIRO".equals(setor)) {
                setor_validado = 6;
            }
        
      
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO fluxo (data_transacao, fk_setor, descricao, valor, categoria, forma_pagto, vencimento, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                
                stmt.setDate(1, dataTran != null ? java.sql.Date.valueOf(dataTran) : null);
                stmt.setInt(2, setor_validado);
                stmt.setString(3, descricao);
                stmt.setDouble(4, valor);
                stmt.setString(5, cmbCategoria.getValue());
                stmt.setString(6, cmbPagto.getValue());
                stmt.setDate(7, dataVen != null ? java.sql.Date.valueOf(dataVen) : null);
                stmt.setBoolean(8, status_validado);
                
                stmt.executeUpdate();
                       
                listaFluxo(); 
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso!", "Adicionado com sucesso!");
        
            } catch (SQLException e) {
                e.printStackTrace(); 
                mostrarAlerta(Alert.AlertType.ERROR, "Erro!", "Erro ao adicionar!"+ e.getMessage());
            }
        }
        

    @FXML
    private void atualizarFluxo() {

        if (DataTransacaoAtualizar.getValue() == null || DataVencimentoAtualizar.getValue() == null || 
        cmbSetorAtualizar.getValue() == null || txtDescricaoAtualizar.getText().isEmpty() || 
        txtValorAtualizar.getText().isEmpty() || cmbStatusAtualizar.getValue() == null || 
        cmbCategoriaAtualizar.getValue() == null || cmbPagtoAtualizar.getValue() == null) {
        mostrarAlerta(Alert.AlertType.ERROR, "Atenção!", "Todos os campos devem ser preenchidos!");
    }

        
        LocalDate dataTran = DataTransacaoAtualizar.getValue();
        LocalDate dataVen = DataVencimentoAtualizar.getValue();
        String setor = String.valueOf(cmbSetorAtualizar.getValue());
        String descricao = txtDescricaoAtualizar.getText();
        double valor = Double.parseDouble(txtValorAtualizar.getText());
        String status = String.valueOf(cmbStatusAtualizar.getValue());
        Boolean status_validado;
        Integer setor_validado = null;
        Fluxo fluxoSelecionado = tableFluxo.getSelectionModel().getSelectedItem();

        if("Concluida".equals(status)){
            status_validado = true;           
        }else{
            status_validado = false;
        }

        if ("RH".equals(setor)) {
            setor_validado = 1;
        } else if ("AUTOMAÇÃO".equals(setor)) {
            setor_validado = 2;
        } else if ("PRODUÇÃO".equals(setor)) {
            setor_validado = 3;
        } else if ("ESTOQUE".equals(setor)) {
            setor_validado = 4;
        } else if ("CONTROLE DE QUALIDADE".equals(setor)) {
            setor_validado = 5;
        } else if ("FINANCEIRO".equals(setor)) {
            setor_validado = 6;
        }

      
        try (Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE fluxo SET data_transacao = ?, fk_setor = ?, descricao = ?, valor = ?, categoria = ?, forma_pagto = ?, vencimento = ?, status = ? WHERE id_fluxo = ?")) {
        
            stmt.setDate(1, dataTran != null ? java.sql.Date.valueOf(dataTran) : null);
            stmt.setInt(2, setor_validado);
            stmt.setString(3, descricao);
            stmt.setDouble(4, valor);
            stmt.setString(5, cmbCategoriaAtualizar.getValue());
            stmt.setString(6, cmbPagtoAtualizar.getValue());
            stmt.setDate(7, dataVen != null ? java.sql.Date.valueOf(dataVen) : null);
            stmt.setBoolean(8, status_validado);
            stmt.setInt(9, fluxoSelecionado.getId());
            stmt.executeUpdate();

            listaFluxo();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Atualizado com sucesso!");
            tabPane.getSelectionModel().select(tabFluxo);


        } catch (SQLException e){
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao atualizar!");
        }
    }

    @FXML
    private void excluirFluxo() {
       Fluxo fluxoSelecionado = tableFluxo.getSelectionModel().getSelectedItem();
       if (fluxoSelecionado != null){
          try(Connection conn = Database.getConnection();
          PreparedStatement stmt = conn.prepareStatement("DELETE FROM fluxo WHERE id_fluxo = ?")) { 

            stmt.setInt(1, fluxoSelecionado.getId());
            stmt.executeUpdate();
            listaFluxo();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Excluído com sucesso!");
            tabPane.getSelectionModel().select(tabFluxo);

         } catch (SQLException e){
          e.printStackTrace();
          mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao excluir!" + e.getMessage());
         }

        }
    }

    @FXML
    private void listaFluxo(){
        listaFluxo.clear();
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT fluxo.id_fluxo, fluxo.data_transacao, setores.nome_setor, fluxo.descricao, fluxo.valor, fluxo.categoria, fluxo.forma_pagto, fluxo.vencimento, fluxo.status FROM fluxo JOIN setores ON fluxo.fk_setor = setores.id_setores ")) {
                while (rs.next()) {
                    listaFluxo.add(new 
                    Fluxo(rs.getInt("id_fluxo"), 
                    rs.getDate("data_transacao"), 
                    rs.getString("nome_setor"), 
                    rs.getString("descricao"), 
                    rs.getDouble("valor"), 
                    rs.getString("categoria"), 
                    rs.getString("forma_pagto"), 
                    rs.getDate("vencimento"), 
                    rs.getBoolean("status")));
                }
                tableFluxo.setItems(listaFluxo);  
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void listaRelatorio(){
        listaRelatorio.clear();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT fluxo.id_fluxo, fluxo.data_transacao, setores.nome_setor, fluxo.descricao, fluxo.valor, fluxo.categoria, fluxo.forma_pagto, fluxo.vencimento, fluxo.status FROM fluxo JOIN setores ON fluxo.fk_setor = setores.id_setores")) {
                while (rs.next()) {
                    listaRelatorio.add(new 
                    Fluxo(rs.getInt("id_fluxo"),
                     rs.getDate("data_transacao"),
                     rs.getString("nome_setor"),
                     rs.getString("descricao"), 
                     rs.getDouble("valor"), 
                       rs.getString("categoria"), 
                       rs.getString("forma_pagto"),
                        rs.getDate("vencimento"), 
                        rs.getBoolean("status")));
                }
                tableRelatorio.setItems(listaRelatorio);  
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    
    private void preencherCamposAtualizacao() {
        Fluxo fluxoSelecionado = tableFluxo.getSelectionModel().getSelectedItem();

        if (fluxoSelecionado != null) {
            DataTransacaoAtualizar.setValue(fluxoSelecionado.getData_transacao().toLocalDate());
            cmbSetorAtualizar.setValue(fluxoSelecionado.getFk_setor());
            txtDescricaoAtualizar.setText(fluxoSelecionado.getDescricao());
            txtValorAtualizar.setText(String.valueOf(fluxoSelecionado.getValor()));
            cmbCategoriaAtualizar.setValue(fluxoSelecionado.getCategoria());
            cmbPagtoAtualizar.setValue(fluxoSelecionado.getForma_pagto());
            DataVencimentoAtualizar.setValue(fluxoSelecionado.getVencimento().toLocalDate());
            cmbStatusAtualizar.setValue(String.valueOf(fluxoSelecionado.getStatus() ? "Concluído" : "Pendente"));
            tabPane.getSelectionModel().select(tabAtualizar);
        }
       
    }
    

    public void limparCampos() {
        DataTransacao.setValue(null);
        cmbSetor.setValue(null);
        txtDescricao.clear();
        txtValor.clear();
        cmbCategoria.setValue(null);
        cmbPagto.setValue(null);
        DataVencimento.setValue(null);
        cmbStatus.setValue(null);
    }

    public void limparCampos1() {
        filtroPagto.setValue(null);
        txtFuncionarioPagto.clear();
        cmbStatusPagto.setValue(null);
    }

    public void limparCampos2() {
        dataInicio.setValue(null);
        dataFim.setValue(null);
    }

    public void limparCampos3() {
        DataTransacaoAtualizar.setValue(null);
        cmbSetorAtualizar.setValue(null);
        txtDescricaoAtualizar.clear();
        txtValorAtualizar.clear();
        cmbCategoriaAtualizar.setValue(null);
        cmbPagtoAtualizar.setValue(null);
        DataVencimentoAtualizar.setValue(null);
        cmbStatusAtualizar.setValue(null);
       
    }

    @FXML
    private void listaSolicitacoes(){
            listaSolicitacoes.clear();
           try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT solicitacoes.id_solicitacoes, solicitacoes.data_solicitacao, setores.nome_setor, solicitacoes.descricao, solicitacoes.quantidade, solicitacoes.valor, solicitacoes.prazo, solicitacoes.status FROM solicitacoes JOIN setores ON solicitacoes.fk_setor = setores.id_setores")) {
                   while (rs.next()) {
                       listaSolicitacoes.add(new
                        Solicitacoes(rs.getInt("id_solicitacoes"), 
                        rs.getString("data_solicitacao"), 
                        rs.getString("nome_setor"), 
                        rs.getString("descricao"), 
                        rs.getString("quantidade"), 
                        rs.getDouble("valor"), 
                        rs.getString("prazo"), 
                        rs.getString("status")));
                   }
                   tableSolicitacoes.setItems(listaSolicitacoes);  
           }catch (SQLException e){
               e.printStackTrace();
           }
       }
   
       @FXML
       private void aceitar() {
       
           Solicitacoes SolicitacaoSelecionada = tableSolicitacoes.getSelectionModel().getSelectedItem();
       
           if (SolicitacaoSelecionada != null && "Pendente".equals(SolicitacaoSelecionada.getStatus())) {
       
               try (Connection conn = Database.getConnection();
                    PreparedStatement stmt = conn.prepareStatement("UPDATE solicitacoes SET status = 'Aprovada' WHERE id_solicitacoes = ?")) {
       
                   stmt.setInt(1, SolicitacaoSelecionada.getId());
                   stmt.executeUpdate();
       
                   listaSolicitacoes();
                   mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso!", "Solicitação aceita com sucesso!");
       
               } catch (SQLException e) {
                   e.printStackTrace();
                   mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao aceitar! " + e.getMessage());
               }
           } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro!", "Solicitação não está pendente!");
     
           }
       }
   
       @FXML
    private void recusar() {
    Solicitacoes SolicitacaoSelecionada = tableSolicitacoes.getSelectionModel().getSelectedItem();

    if (SolicitacaoSelecionada != null && "Pendente".equals(SolicitacaoSelecionada.getStatus())) {

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE solicitacoes SET status = 'Recusada' WHERE id_solicitacoes = ?")) {

            stmt.setInt(1, SolicitacaoSelecionada.getId());
            stmt.executeUpdate();

            listaSolicitacoes();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso!", "Solicitação recusada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
             mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao recusar! " + e.getMessage());
        }
    } else {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro!", "Solicitação não está pendente!");
    }
}
   

      @FXML
    private void confirmarPagto() {
    PagFuncionarios SolicitacaoSelecionada = tablePagamento.getSelectionModel().getSelectedItem();


    if (SolicitacaoSelecionada != null && !SolicitacaoSelecionada.getStatus()) {

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE pagfuncionarios SET status = ? WHERE id_pagfuncionarios = ?")) {

            stmt.setBoolean(1, true); 
            stmt.setInt(2, SolicitacaoSelecionada.getId());

            stmt.executeUpdate(); 

           
            listaFuncionarios();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso!", "Pagamento confirmado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro!", "O pagamento não foi confirmado!");
        }
    } else {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro!", "O pagamento já foi confirmado!");
    }
}


    @FXML
    private void listaFuncionarios(){
            listaFuncionarios.clear();
           try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT pagfuncionarios.id_pagfuncionarios, funcionarios.nome, setores.nome_setor, pagfuncionarios.data_pagto, pagfuncionarios.salario_base, pagfuncionarios.descontos, pagfuncionarios.valor_liquido, pagfuncionarios.status FROM pagfuncionarios JOIN funcionarios ON pagfuncionarios.fk_funcionarios = funcionarios.id_funcionarios JOIN setores ON pagfuncionarios.fk_setor = setores.id_setores")) {
                   while (rs.next()) {
                       listaFuncionarios.add(new 
                       PagFuncionarios(
                        rs.getInt("id_pagfuncionarios"), 
                       rs.getString("nome"), 
                       rs.getString("nome_setor"),
                       rs.getDate("data_pagto"), 
                       rs.getDouble("salario_base"), 
                       rs.getDouble("descontos"),
                       rs.getDouble("valor_liquido"),
                       rs.getBoolean("status")));
                   }
                   tablePagamento.setItems(listaFuncionarios);  
           }catch (SQLException e){
               e.printStackTrace();
           }
       }


       @FXML
       private void relatorioPeriodico() {
       
           LocalDate dataInicial = dataInicio.getValue();
           LocalDate dataFinal = dataFim.getValue();
       
           if (dataInicial == null || dataFinal == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione as datas de início e fim!");
           }
       
           try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                    "SELECT fluxo.id_fluxo, fluxo.data_transacao, setores.nome_setor, fluxo.descricao, fluxo.valor, fluxo.categoria, fluxo.forma_pagto, fluxo.vencimento, fluxo.status FROM fluxo JOIN setores ON fluxo.fk_setor = setores.id_setores WHERE fluxo.data_transacao BETWEEN ? AND ?")) {
       
               stmt.setDate(1, java.sql.Date.valueOf(dataInicial));
               stmt.setDate(2, java.sql.Date.valueOf(dataFinal));
       
               ResultSet rs = stmt.executeQuery();
               listaRelatorio.clear(); 
       
               while (rs.next()) {
                   listaRelatorio.add(new Fluxo(
                       rs.getInt("id_fluxo"),
                       rs.getDate("data_transacao"),
                       rs.getString("nome_setor"),
                       rs.getString("descricao"),
                       rs.getDouble("valor"),
                       rs.getString("categoria"),
                       rs.getString("forma_pagto"),
                       rs.getDate("vencimento"),
                       rs.getBoolean("status")
                   ));
               }
       
               tableRelatorio.setItems(listaRelatorio);
       
           } catch (SQLException e) {
               e.printStackTrace();
               mostrarAlerta(Alert.AlertType.ERROR, "Erro!", "Erro no banco de dados!"+ e.getMessage());
           }
       }
       

       @FXML
       private void filtroTabPagto() {

           LocalDate dataComeco = filtroPagto.getValue();

           if (dataComeco == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma data para filtrar!");
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT pagfuncionarios.id_pagfuncionarios, funcionarios.nome, setores.nome_setor, pagfuncionarios.data_pagto, pagfuncionarios.salario_base, pagfuncionarios.descontos, pagfuncionarios.valor_liquido, pagfuncionarios.status " +
                 "FROM pagfuncionarios " +
                 "JOIN funcionarios ON pagfuncionarios.fk_funcionarios = funcionarios.id_funcionarios " +
                 "JOIN setores ON pagfuncionarios.fk_setor = setores.id_setores " +
                 "WHERE pagfuncionarios.data_pagto = ?")) {
        
            stmt.setDate(1, dataComeco != null ? java.sql.Date.valueOf(dataComeco) : null);
        
            ResultSet rs = stmt.executeQuery();
            listaFuncionarios.clear();
        
            while (rs.next()) {
                listaFuncionarios.add(new PagFuncionarios(
                    rs.getInt("id_pagfuncionarios"),
                    rs.getString("nome"),
                    rs.getString("nome_setor"),
                    rs.getDate("data_pagto"),
                    rs.getDouble("salario_base"),
                    rs.getDouble("descontos"),
                    rs.getDouble("valor_liquido"),
                    rs.getBoolean("status")
                ));
            }
        
            tablePagamento.setItems(listaFuncionarios);
        
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro!", "Erro no banco de dados!"+ e.getMessage());
        }
       }



       private void preencherCamposPagto() {
        PagFuncionarios funcionarioSelecionado = tablePagamento.getSelectionModel().getSelectedItem();
      
        if (funcionarioSelecionado != null){
            txtFuncionarioPagto.setText(funcionarioSelecionado.getFk_funcionarios());
            cmbStatusPagto.setValue(String.valueOf(funcionarioSelecionado.getStatus()? "Concluído" : "Pendente"));

        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }




    @FXML
    public void initialize() {
        //Inicializacao Fluxo
        colFluData.setCellValueFactory(new PropertyValueFactory<>("data_transacao"));
        colFluSetor.setCellValueFactory(new PropertyValueFactory<>("fk_setor"));
        colFluDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colFluValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colFluCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colFluPagto.setCellValueFactory(new PropertyValueFactory<>("forma_pagto"));
        colFluVencimento.setCellValueFactory(new PropertyValueFactory<>("vencimento"));
        colFluStatus.setCellValueFactory(cellData -> {boolean status = cellData.getValue().getStatus(); 
        String statusFormatado = status ? "Concluído" : "Pendente";
        return new ReadOnlyStringWrapper(statusFormatado);
        }); 

        tableFluxo.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                preencherCamposAtualizacao();
            }
        });

        //Inicialização Solicitações
        colSoData.setCellValueFactory(new PropertyValueFactory<>("data_solicitacao"));
        colSoSetor.setCellValueFactory(new PropertyValueFactory<>("fk_setor"));
        colSoDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colSoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colSoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colSoPrazo.setCellValueFactory(new PropertyValueFactory<>("prazo"));
        colSoStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableSolicitacoes.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                listaSolicitacoes();
            }
        });

        //Inicialização Pagamentos
        colPagFuncionario.setCellValueFactory(new PropertyValueFactory<>("fk_funcionarios"));
        colPagSetor.setCellValueFactory(new PropertyValueFactory<>("fk_setor"));
        colPagData.setCellValueFactory(new PropertyValueFactory<>("data_pagto"));
        colPagSalario.setCellValueFactory(new PropertyValueFactory<>("salario_base"));
        colPagDescontos.setCellValueFactory(new PropertyValueFactory<>("descontos"));
        colPagLiquido.setCellValueFactory(new PropertyValueFactory<>("valor_liquido"));
        colPagStatus.setCellValueFactory(cellData -> {boolean status = cellData.getValue().getStatus(); // ou isStatus(), depende da sua classe
            String statusFormatado = status ? "Concluído" : "Pendente";
            return new ReadOnlyStringWrapper(statusFormatado);
        });

        tablePagamento.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                preencherCamposPagto();
            }
        });

        //Inicialização Relatorio
        colReData.setCellValueFactory(new PropertyValueFactory<>("data_transacao"));
        colReSetor.setCellValueFactory(new PropertyValueFactory<>("fk_setor"));
        colReDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colReValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colReCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colReForma.setCellValueFactory(new PropertyValueFactory<>("forma_pagto"));
        colReVencimento.setCellValueFactory(new PropertyValueFactory<>("vencimento"));
        colReStatus.setCellValueFactory(cellData -> {
            boolean status = cellData.getValue().getStatus(); // ou isStatus(), depende da sua classe
            String statusFormatado = status ? "Concluído" : "Pendente";
            return new ReadOnlyStringWrapper(statusFormatado);
        });
        

        listaFuncionarios();
        
        listaSolicitacoes();

        listaRelatorio();

        cmbSetorAtualizar.getItems().addAll("RH", "AUTOMAÇÃO", "PRODUÇÃO", "ESTOQUE", "CONTROLE DE QUALIDADE");
        cmbCategoriaAtualizar.getItems().addAll("Compra","Venda", "Serviço");
        cmbPagtoAtualizar.getItems().addAll("Cartão de Crédito", "Transferência", "Boleto", "Pix");
        cmbStatusAtualizar.getItems().addAll("Concluida", "Pendente");

        cmbSetor.getItems().addAll("RH", "AUTOMAÇÃO", "PRODUÇÃO", "ESTOQUE", "CONTROLE DE QUALIDADE");
        cmbCategoria.getItems().addAll("Compra","Venda", "Serviço");
        cmbPagto.getItems().addAll("Cartão de Crédito", "Transferência", "Boleto", "Pix");
        cmbStatus.getItems().addAll("Concluida", "Pendente");

        cmbStatusPagto.getItems().addAll("Concluida", "Pendente");

        listaFluxo();

    }

   
}