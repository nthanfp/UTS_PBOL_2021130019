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
public class DBStation {

    private stationModel dtStation = new stationModel();

    public stationModel getDtStation() {
        return dtStation;
    }

    public void setDtStation(stationModel dtStation) {
        this.dtStation = dtStation;
    }

    public ObservableList<stationModel> Load() {
        try {
            ObservableList<stationModel> tableData = FXCollections.observableArrayList();
            DBConnection conn = new DBConnection();
            conn.bukaKoneksi();
            conn.statement = conn.dbKoneksi.createStatement();
            ResultSet rs = conn.statement.executeQuery("SELECT `id_station`, `station_name`, `city` FROM `stations`");
            int i = 1;
            while (rs.next()) {
                stationModel d = new stationModel();
                d.setIdStation(rs.getString("id_station"));
                d.setStationName(rs.getString("station_name"));
                d.setCity(rs.getString("city"));

                tableData.add(d);
                i++;
            }
            conn.tutupKoneksi();
            return tableData;
        } catch (SQLException e) {
            return null;
        }
    }

    public ObservableList<stationModel> search(String kode, String nama) {
        try {
            ObservableList<stationModel> tableData;
            tableData = FXCollections.observableArrayList();
            DBConnection conn = new DBConnection();
            conn.bukaKoneksi();
            conn.statement = (Statement) conn.dbKoneksi.createStatement();
            ResultSet rs = (ResultSet) conn.statement.executeQuery("SELECT * FROM `stations` WHERE `id_station` LIKE '" + kode + "%' OR `station_name` LIKE '" + nama + "%'");
            int i = 1;
            while (rs.next()) {
                stationModel d = new stationModel();
                d.setIdStation(rs.getString("id_station"));
                d.setStationName(rs.getString("station_name"));
                d.setCity(rs.getString("city"));
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
            ResultSet rs = conn.statement.executeQuery("SELECT COUNT(*) AS `jml` FROM `stations` WHERE `id_station`='" + nomor + "'");
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
            conn.preparedStatement = conn.dbKoneksi.prepareStatement("INSERT INTO `stations` (`station_name`, `city`) values (?,?)");
            conn.preparedStatement.setString(1, getDtStation().getStationName());
            conn.preparedStatement.setString(2, getDtStation().getCity());
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
            conn.preparedStatement = conn.dbKoneksi.prepareStatement("DELETE FROM `stations` WHERE `id_station`=?");
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
            conn.preparedStatement = conn.dbKoneksi.prepareStatement("UPDATE `stations` SET `station_name`=?, `city`=? WHERE `id_station`=?");
            conn.preparedStatement.setString(1, getDtStation().getStationName());
            conn.preparedStatement.setString(2, getDtStation().getCity());
            conn.preparedStatement.setString(3, getDtStation().getIdStation());
            System.out.println(conn.preparedStatement);
            System.out.println(getDtStation().getIdStation());
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
