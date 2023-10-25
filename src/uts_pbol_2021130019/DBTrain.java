/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uts_pbol_2021130019;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nathan
 */
public class DBTrain {
    private trainModel dtTrain = new trainModel();

    public trainModel getDtTrain() {
        return dtTrain;
    }

    public void setDtTrain(trainModel dtTrain) {
        this.dtTrain = dtTrain;
    }

    public ObservableList<trainModel> Load() {
        try {
            ObservableList<trainModel> tableData = FXCollections.observableArrayList();
            DBConnection conn = new DBConnection();
            conn.bukaKoneksi();
            conn.statement = conn.dbKoneksi.createStatement();
            ResultSet rs = conn.statement.executeQuery("SELECT `id_train`, `train_number`, `train_name`, `train_type`, `passenger_capacity` FROM `trains`");
            int i = 1;
            while (rs.next()) {
                trainModel d = new trainModel();
                d.setIdTrain(rs.getString("id_train"));
                d.setTrainNumber(rs.getString("train_number"));
                d.setTrainName(rs.getString("train_name"));
                d.setTrainType(rs.getString("train_type"));
                d.setPassengerCapacity(rs.getInt("passenger_capacity"));

                tableData.add(d);
                i++;
            }
            conn.tutupKoneksi();
            return tableData;
        } catch (SQLException e) {
            return null;
        }
    }

    public ObservableList<trainModel> search(String kode, String nama) {
        try {
            ObservableList<trainModel> tableData;
            tableData = FXCollections.observableArrayList();
            DBConnection conn = new DBConnection();
            conn.bukaKoneksi();
            conn.statement = (Statement) conn.dbKoneksi.createStatement();
            ResultSet rs = (ResultSet) conn.statement.executeQuery("SELECT * FROM `trains` WHERE `id_train` LIKE '" + kode + "%' OR `station_name` LIKE '" + nama + "%'");
            int i = 1;
            while (rs.next()) {
                trainModel d = new trainModel();
                d.setIdTrain(rs.getString("id_train"));
                d.setTrainNumber(rs.getString("train_number"));
                d.setTrainName(rs.getString("train_name"));
                d.setTrainType(rs.getString("train_type"));
                d.setPassengerCapacity(rs.getInt("passenger_capacity"));
                tableData.add(d);
                i++;
            }
            conn.tutupKoneksi();
            return tableData;
        } catch (SQLException e) {
            return null;
        }
    }

    public int validasi(String nomor) {
        int val = 0;
        try {
            DBConnection conn = new DBConnection();
            conn.bukaKoneksi();
            conn.statement = conn.dbKoneksi.createStatement();
            ResultSet rs = conn.statement.executeQuery("SELECT COUNT(*) AS `jml` FROM `trains` WHERE `id_train`='" + nomor + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            conn.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public boolean insert() {
        boolean berhasil = false;
        DBConnection conn = new DBConnection();
        try {
            conn.bukaKoneksi();
            conn.preparedStatement = conn.dbKoneksi.prepareStatement("INSERT INTO `trains` (`id_train`, `train_number`, `train_name`, `train_type`, `passenger_capacity`) values (?,?,?,?,?)");
            conn.preparedStatement.setString(1, getDtTrain().getIdTrain());
            conn.preparedStatement.setString(2, getDtTrain().getTrainNumber());
            conn.preparedStatement.setString(3, getDtTrain().getTrainName());
            conn.preparedStatement.setString(4, getDtTrain().getTrainType());
            conn.preparedStatement.setInt(5, getDtTrain().getPassengerCapacity());
            conn.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            conn.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean delete(String nomor) {
        boolean berhasil = false;
        DBConnection conn = new DBConnection();
        try {
            conn.bukaKoneksi();;
            conn.preparedStatement = conn.dbKoneksi.prepareStatement("DELETE FROM `trains` WHERE `id_train`=?");
            conn.preparedStatement.setString(1, nomor);
            conn.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean update() {
        boolean berhasil = false;
        DBConnection conn = new DBConnection();
        try {
            conn.bukaKoneksi();
            conn.preparedStatement = conn.dbKoneksi.prepareStatement("UPDATE `trains` SET `train_number`=?, `train_name`=?, `train_type`=?, `passenger_capacity`=? WHERE `id_train`=?");
            conn.preparedStatement.setString(1, getDtTrain().getTrainNumber());
            conn.preparedStatement.setString(2, getDtTrain().getTrainName());
            conn.preparedStatement.setString(3, getDtTrain().getTrainType());
            conn.preparedStatement.setInt(4, getDtTrain().getPassengerCapacity());
            conn.preparedStatement.setString(5, getDtTrain().getIdTrain());
            conn.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            conn.tutupKoneksi();
            return berhasil;
        }
    }

}
