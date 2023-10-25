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
public class FXMLInputTrainController implements Initializable {

    @FXML
    private Button btnExit;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtTrainNumber;
    @FXML
    private TextField txtTrainName;
    @FXML
    private TextField txtTrainType;
    @FXML
    private TextField txtCapacity;

    private boolean editdata = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void execute(trainModel d) {
        if (!d.getIdTrain().isEmpty()) {
            editdata = true;
            txtId.setText(d.getIdTrain());
            txtTrainName.setText(d.getTrainName());
            txtTrainNumber.setText(d.getTrainNumber());
            txtTrainType.setText(d.getTrainType());
            txtCapacity.setText(Integer.toString(d.getPassengerCapacity()));
            txtId.setEditable(false);
            txtTrainName.requestFocus();
        }
    }

    @FXML
    private void saveAct(ActionEvent event) {
        trainModel n = new trainModel();
        n.setTrainName(txtTrainName.getText());
        n.setTrainNumber(txtTrainNumber.getText());
        n.setTrainType(txtTrainType.getText());
        n.setPassengerCapacity(Integer.parseInt(txtCapacity.getText()));
        n.setIdTrain(txtId.getText());
        FXMLDocumentController.dtTrain.setDtTrain(n);
        if (editdata) {
            if (FXMLDocumentController.dtTrain.update()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil diubah", ButtonType.OK);
                a.showAndWait();
                txtId.setEditable(true);
                cancelAct(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } else if (FXMLDocumentController.dtTrain.validasi(n.getIdTrain()) <= 0) {
            if (FXMLDocumentController.dtTrain.insert()) {
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
            txtTrainName.requestFocus();
        }
    }

    @FXML
    private void cancelAct(ActionEvent event) {
        txtId.setText("");
        txtTrainName.setText("");
        txtTrainNumber.setText("");
        txtTrainType.setText("");
        txtCapacity.setText("");
    }

    @FXML
    private void exitAct(ActionEvent event) {
        btnExit.getScene().getWindow().hide();
    }

}
