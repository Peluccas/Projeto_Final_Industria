package com.example.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.database.Database;
import com.example.models.Funcionario;
import com.example.models.QFuncionario;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class QFuncionarioController {
    @FXML private ComboBox<String> cmbCargoFunc;
    @FXML private ComboBox<String> cmbSetorFunc;
    @FXML private BarChart<String, Number> barChart;
    @FXML private BarChart<String, Number> barChart1;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private TabPane tabPaneFuncionario;
    @FXML private Tab tabCadastrarFuncionario;
    @FXML private Tab tabAtualizarFuncionario;
    @FXML private Tab tabListarFuncionario;
    @FXML private Button btnDash;
    @FXML private Button btnDashLote;
    @FXML private LineChart<String, Number> lineChart;
    @FXML private PieChart pieChart;
    @FXML private TableView<QFuncionario> tableDashTela;
    @FXML private ProgressBar progressBar;
    @FXML private ProgressIndicator progressIndica;
    @FXML private TableColumn<QFuncionario, Integer> columApro;
    @FXML private TableColumn<QFuncionario, Integer> columConcerto;
    @FXML private TableColumn<QFuncionario, Integer> columRepro;
    @FXML private TableColumn<QFuncionario, Integer> columTotal;
    @FXML private Button btnAprovarRevisaoAct;

    private ObservableList<QFuncionario> listaDash = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
    
        columApro.setCellValueFactory(new PropertyValueFactory<>("aprovados"));
        columRepro.setCellValueFactory(new PropertyValueFactory<>("reprovados"));
        columConcerto.setCellValueFactory(new PropertyValueFactory<>("concertos"));
        columTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
      
        carregarDadosDash();
        chartBar();
        chartBar1();
        chartLine();
        ChartPie();
    }

    

    public void chartBar() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Aprovados");

        try {Connection conn = Database.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement("SELECT mes, quantidade FROM barChart");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String mes = rs.getString("mes");         
                int quantidade = rs.getInt("quantidade"); 
                series.getData().add(new XYChart.Data<>(mes, quantidade)); 
            }
            barChart.getData().clear(); 
            barChart.getData().add(series); 
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    

    public void chartBar1() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Reprovados");

        try {Connection conn = Database.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement("SELECT mes, quantidade FROM barChart1");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String mes = rs.getString("mes");         
                int quantidade = rs.getInt("quantidade"); 
                series.getData().add(new XYChart.Data<>(mes, quantidade)); 
            }
            barChart1.getData().clear(); 
            barChart1.getData().add(series); 
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   public void chartLine(){
    XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Aprovados");

        try {Connection conn = Database.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement("SELECT mes, quantidade FROM barChart");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String mes = rs.getString("mes");         
                int quantidade = rs.getInt("quantidade"); 
                series.getData().add(new XYChart.Data<>(mes, quantidade)); 
            }
            lineChart.getData().add(series);
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Reprovados");

        try {Connection conn = Database.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement("SELECT mes, quantidade FROM barChart1");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String mes = rs.getString("mes");         
                int quantidade = rs.getInt("quantidade"); 
                series1.getData().add(new XYChart.Data<>(mes, quantidade)); 
            }
            lineChart.getData().add(series1);
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        
        
    }

    public void ChartPie() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tableDash WHERE id = 1");
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int total = rs.getInt("total");
                int aprovados = rs.getInt("aprovados");
                int reprovados = rs.getInt("reprovados");
                int concertos = rs.getInt("concertos");

                pieChartData.add(new PieChart.Data("Total", total));
                pieChartData.add(new PieChart.Data("Aprovados", aprovados));
                pieChartData.add(new PieChart.Data("Reprovados", reprovados));
                pieChartData.add(new PieChart.Data("Concertos", concertos));
            }

        } catch (SQLException e) {
        e.printStackTrace();
        }

        pieChart.setData(pieChartData);
        pieChart.setTitle("Categoria Setor");
        pieChart.setLabelsVisible(true);
    }

  
    @FXML
    public void btnDashLote(){
        btnDash.setDisable(true);
        progressIndica.setProgress(-1);
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                final double progress = i / 100.0; 
                Platform.runLater(() -> progressBar.setProgress(progress));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            Platform.runLater(() -> btnDash.setDisable(false));
        }).start();

        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(50); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> {
                btnDash.setDisable(false);
                progressIndica.setProgress(1); 
            });
        }).start();

        
        chartBar();
        chartBar1();
        chartLine();
        carregarDadosDash();
        
  

    }

    public void carregarDadosDash() {
        listaDash.clear();
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tableDash WHERE id = 1");
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                QFuncionario dash = new QFuncionario(
                    rs.getInt("total"),
                    rs.getInt("aprovados"),
                    rs.getInt("reprovados"),
                    rs.getInt("concertos")
                );
                tableDashTela.getItems().setAll(dash);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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