/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package uts_pbol_2021130019;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLInputStationController implements Initializable {

    @FXML
    private Button btnExit;
    @FXML
    private TextField txtStation;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtId;
    
    private boolean editdata = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.setEditable(false);
    }    

    public void execute(stationModel d) {
        if (!d.getIdStation().isEmpty()) {
            editdata = true;
            txtStation.setText(d.getStationName());
            txtCity.setText(d.getCity());
            txtId.setText(d.getIdStation());
            txtId.setEditable(false);
            txtStation.requestFocus();
        }
    }

    @FXML
    private void saveAct(ActionEvent event) {
        stationModel n = new stationModel();
        n.setStationName(txtStation.getText());
        n.setCity(txtCity.getText());
        n.setIdStation(txtId.getText());
        FXMLDocumentController.dtStation.setDtStation(n);
        if (editdata) {
            if (FXMLDocumentController.dtStation.update()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil diubah", ButtonType.OK);
                a.showAndWait();
                txtId.setEditable(true);
                cancelAct(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } else if (FXMLDocumentController.dtStation.validasi(n.getIdStation()) <= 0) {
            if (FXMLDocumentController.dtStation.insert()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
                a.showAndWait();
                cancelAct(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data sudah ada", ButtonType.OK);
            a.showAndWait();
            txtStation.requestFocus();
        }
    }

    @FXML
    private void cancelAct(ActionEvent event) {
        txtId.setText("");
        txtCity.setText("");
        txtStation.setText("");
    }

    @FXML
    private void exitAct(ActionEvent event) {
        btnExit.getScene().getWindow().hide();
    }
    
}
