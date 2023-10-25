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
public class FXMLDisplayStationController implements Initializable {

    @FXML
    private TableView<stationModel> tableStation;
    @FXML
    private TextField txtSearchStation;
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
        ObservableList<stationModel> data = FXMLDocumentController.dtStation.Load();

        if (data != null) {
            tableStation.getColumns().clear();
            tableStation.getItems().clear();
            TableColumn col = new TableColumn("Station ID");
            col.setCellValueFactory(new PropertyValueFactory<stationModel, String>("idStation"));
            tableStation.getColumns().addAll(col);
            col = new TableColumn("Name");
            col.setCellValueFactory(new PropertyValueFactory<stationModel, String>("stationName"));
            tableStation.getColumns().addAll(col);
            col = new TableColumn("City");
            col.setCellValueFactory(new PropertyValueFactory<stationModel, String>("city"));
            tableStation.getColumns().addAll(col);

            tableStation.setItems(data);
        } else {
            System.out.println(data);
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();
            tableStation.getScene().getWindow().hide();
        }
    }

    @FXML
    private void searchStation(KeyEvent event) {
        String key = txtSearchStation.getText();
        if ("".equals(key)) {
            showData();
        } else {
            ObservableList<stationModel> data = FXMLDocumentController.dtStation.search(key, key);
            if (data != null) {
                tableStation.getColumns().clear();
                tableStation.getItems().clear();

                TableColumn col = new TableColumn("Station ID");
                col.setCellValueFactory(new PropertyValueFactory<stationModel, String>("idStation"));
                tableStation.getColumns().addAll(col);
                col = new TableColumn("Name");
                col.setCellValueFactory(new PropertyValueFactory<stationModel, String>("stationName"));
                tableStation.getColumns().addAll(col);
                col = new TableColumn("City");
                col.setCellValueFactory(new PropertyValueFactory<stationModel, String>("city"));
                tableStation.getColumns().addAll(col);

                tableStation.setItems(data);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
                a.showAndWait();
                tableStation.getScene().getWindow().hide();
            }
        }
    }

    @FXML
    private void btnAddAct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputStation.fxml"));
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
        stationModel s = new stationModel();
        s = tableStation.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLDocumentController.dtStation.delete(s.getIdStation())) {
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
        stationModel s = new stationModel();
        s = tableStation.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputStation.fxml"));
            Parent root = (Parent) loader.load();
            FXMLInputStationController isiDt = (FXMLInputStationController) loader.getController();
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
        tableStation.getSelectionModel().selectFirst();
        tableStation.requestFocus();
    }

    @FXML
    private void btnPrevAct(ActionEvent event) {
        tableStation.getSelectionModel().selectAboveCell();
        tableStation.requestFocus();
    }

    @FXML
    private void btnNextAct(ActionEvent event) {
        tableStation.getSelectionModel().selectBelowCell();
        tableStation.requestFocus();
    }

    @FXML
    private void btnLastAct(ActionEvent event) {
        tableStation.getSelectionModel().selectLast();
        tableStation.requestFocus();
    }

}
