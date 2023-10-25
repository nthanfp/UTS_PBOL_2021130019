/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package uts_pbol_2021130019;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLDisplayTrainController implements Initializable {

    @FXML
    private TableView<trainModel> tableTrain;
    @FXML
    private TextField txtSearchTrain;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnFirst;
    @FXML
    private Button btnPrev;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnLast;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showData();
    }

    public void showData() {
        ObservableList<trainModel> data = FXMLDocumentController.dtTrain.Load();

        if (data != null) {
            tableTrain.getColumns().clear();
            tableTrain.getItems().clear();

            TableColumn col = new TableColumn("Train ID");
            col.setCellValueFactory(new PropertyValueFactory<trainModel, String>("idTrain"));
            tableTrain.getColumns().addAll(col);
            col = new TableColumn("Train Number");
            col.setCellValueFactory(new PropertyValueFactory<trainModel, String>("trainNumber"));
            tableTrain.getColumns().addAll(col);
            col = new TableColumn("Train Name");
            col.setCellValueFactory(new PropertyValueFactory<trainModel, String>("trainName"));
            tableTrain.getColumns().addAll(col);
            col = new TableColumn("Train Type");
            col.setCellValueFactory(new PropertyValueFactory<trainModel, String>("trainType"));
            tableTrain.getColumns().addAll(col);
            col = new TableColumn("Capacity");
            col.setCellValueFactory(new PropertyValueFactory<trainModel, Integer>("passengerCapacity"));
            tableTrain.getColumns().addAll(col);

            tableTrain.setItems(data);
        } else {
            System.out.println(data);
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();
            tableTrain.getScene().getWindow().hide();
        }
    }

    @FXML
    private void searchTrain(KeyEvent event) {
        String key = txtSearchTrain.getText();
        if ("".equals(key)) {
            showData();
        } else {
            ObservableList<trainModel> data = FXMLDocumentController.dtTrain.search(key, key);
            if (data != null) {
                tableTrain.getColumns().clear();
                tableTrain.getItems().clear();

                TableColumn col = new TableColumn("Train ID");
                col.setCellValueFactory(new PropertyValueFactory<trainModel, String>("idTrain"));
                tableTrain.getColumns().addAll(col);
                col = new TableColumn("Train Number");
                col.setCellValueFactory(new PropertyValueFactory<trainModel, String>("trainNumber"));
                tableTrain.getColumns().addAll(col);
                col = new TableColumn("Train Name");
                col.setCellValueFactory(new PropertyValueFactory<trainModel, String>("trainName"));
                tableTrain.getColumns().addAll(col);
                col = new TableColumn("Train Type");
                col.setCellValueFactory(new PropertyValueFactory<trainModel, String>("trainType"));
                tableTrain.getColumns().addAll(col);
                col = new TableColumn("Capacity");
                col.setCellValueFactory(new PropertyValueFactory<trainModel, Integer>("passengerCapacity"));
                tableTrain.getColumns().addAll(col);

                tableTrain.setItems(data);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
                a.showAndWait();
                tableTrain.getScene().getWindow().hide();
            }
        }
    }

    @FXML
    private void btnAddAct(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputTrain.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showData();
        btnFirstAct(event);
    }

    @FXML
    private void btnRemoveAct(ActionEvent event) {
        trainModel s = new trainModel();
        s = tableTrain.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLDocumentController.dtTrain.delete(s.getIdTrain())) {
                Alert b = new Alert(Alert.AlertType.INFORMATION, "Data berhasil dihapus", ButtonType.OK);
                b.showAndWait();
            } else {
                Alert b = new Alert(Alert.AlertType.ERROR, "Data gagal dihapus", ButtonType.OK);
                b.showAndWait();
            }
            showData();
            btnFirstAct(event);
        }
    }

    @FXML
    private void btnUpdateAct(ActionEvent event) {
        trainModel s = new trainModel();
        s = tableTrain.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputTrain.fxml"));
            Parent root = (Parent) loader.load();
            FXMLInputTrainController isiDt = (FXMLInputTrainController) loader.getController();
            isiDt.execute(s);
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showData();
        btnPrevAct(event);
    }

    @FXML
    private void btnFirstAct(ActionEvent event) {
        tableTrain.getSelectionModel().selectFirst();
        tableTrain.requestFocus();
    }

    @FXML
    private void btnPrevAct(ActionEvent event) {
        tableTrain.getSelectionModel().selectAboveCell();
        tableTrain.requestFocus();
    }

    @FXML
    private void btnNextAct(ActionEvent event) {
        tableTrain.getSelectionModel().selectBelowCell();
        tableTrain.requestFocus();
    }

    @FXML
    private void btnLastAct(ActionEvent event) {
        tableTrain.getSelectionModel().selectLast();
        tableTrain.requestFocus();
    }

}
