package com.example.application.repository;

import model.Servicos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicosRepository {

 private Connection connection;
    
        public ServicosRepository() throws SQLException {
            this.connection = DBConnection.getInstance().getConnection();
        }
    
    

        public boolean saveServicos(Servicos servico) {
            String sql = "INSERT INTO Servicos (descricaoServico, preco) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                // Não define o ID, pois é auto-incremento
                stmt.setString(1, servico.getDescricaoServico());
                stmt.setDouble(2, servico.getPreco());
        
                int rowsInserted = stmt.executeUpdate();
        
                if (rowsInserted > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            servico.setId(generatedId); // Defina o ID no objeto servico
                            return true;
                        }
                    }
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public List<String> getAllServicos() {
            List<String> servicos = new ArrayList<>();
            String sql = "SELECT descricaoServico FROM Servicos";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                ResultSet result = stmt.executeQuery();
                while (result.next()) {
                    servicos.add(result.getString("descricaoServico"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return servicos;
        }
        

    public List<Servicos> getAll() {
        List<Servicos> servicos = new ArrayList<>();
        String sql = "SELECT * FROM Servicos";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Servicos servico = new Servicos();
                servico.setId(result.getInt("id"));
                servico.setDescricaoServico(result.getString("descricaoServico"));
                servico.setPreco(result.getDouble("preco"));
                servicos.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicos;
    }

    public boolean update(Servicos servico) {
        String sql = "UPDATE Servicos SET descricaoServico = ?, preco = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, servico.getDescricaoServico());
            stmt.setDouble(2, servico.getPreco());
            stmt.setInt(3, servico.getId()); // Corrigido para 3
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public boolean delete(Servicos servico) {
        String sql = "DELETE FROM Servicos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, servico.getId());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Servicos> searchServicos(String searchTerm) {
        List<Servicos> servicos = new ArrayList<>();
        String sql = "SELECT * FROM Servicos WHERE descricaoServico LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%"); // Pesquisa com LIKE
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Servicos servico = new Servicos();
                servico.setId(result.getInt("id"));
                servico.setDescricaoServico(result.getString("descricaoServico"));
                servico.setPreco(result.getDouble("preco"));
                servicos.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicos;
    }
        
}
