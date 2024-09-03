package com.example.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.OSPeca;
import model.OSServico;

public class OSServicoRepository {
    private Connection connection;
    
    public OSServicoRepository() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public List<OSServico> getOsServicoByOrdemServicoId(int idOs) {
        List<OSServico> osServicoList = new ArrayList<>();
        String sql = "SELECT * FROM os_servicos WHERE id_os = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOs);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                OSServico osServico = new OSServico();
                osServico.setOrdemServico(new OSRepository().OrdemServicoById(result.getInt("OrdemServico")));
                osServico.setServico(new ServicosRepository().getServicoById(result.getInt("Servico")));
                osServicoList.add(osServico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return osServicoList;
    }
}
