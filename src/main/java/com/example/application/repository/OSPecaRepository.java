package com.example.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.OrdemServico;
import model.OSPeca;

public class OSPecaRepository {

    private Connection connection;
    
    public OSPecaRepository() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public List<OSPeca> getOsPecasByOrdemServicoId(int idOs) {
        List<OSPeca> osPecasList = new ArrayList<>();
        String sql = "SELECT * FROM os_pecas WHERE id_os = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOs);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                OSPeca osPeca = new OSPeca();
                osPeca.setOrdemServico(new OSRepository().OrdemServicoById(result.getInt("id_os")));
                osPeca.setPeca(new PecaRepository().getPecaById(result.getInt("id_pecas")));
                osPecasList.add(osPeca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return osPecasList;
    }
}
