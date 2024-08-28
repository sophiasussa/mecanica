package com.example.application.repository;

import model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeiculosRepository {

    private Connection connection;

    public VeiculosRepository() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public boolean saveVeiculo(Veiculo veiculo) {
        String sql = "INSERT INTO Veiculo (descricao_veiculo, placa, ano_modelo, id_cliente, imagem) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, veiculo.getDescricaoVeiculo());
            stmt.setString(2, veiculo.getPlaca());
            stmt.setString(3, veiculo.getAnoModelo());
            stmt.setInt(4, veiculo.getIdCliente());
            stmt.setBytes(5, veiculo.getImagem());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        veiculo.setId(generatedId);
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

    public List<Veiculo> getAllVeiculos() {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM Veiculo";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(result.getInt("id"));
                veiculo.setDescricaoVeiculo(result.getString("descricao_veiculo"));
                veiculo.setPlaca(result.getString("placa"));
                veiculo.setAnoModelo(result.getString("ano_modelo"));
                veiculo.setIdCliente(result.getInt("id_cliente"));
                veiculo.setImagem(result.getBytes("imagem"));
                veiculos.add(veiculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculos;
    }

    public boolean updateVeiculo(Veiculo veiculo) {
        String sql = "UPDATE Veiculo SET descricao_veiculo = ?, placa = ?, ano_modelo = ?, id_cliente = ?, imagem = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getDescricaoVeiculo());
            stmt.setString(2, veiculo.getPlaca());
            stmt.setString(3, veiculo.getAnoModelo());
            stmt.setInt(4, veiculo.getIdCliente());
            stmt.setBytes(5, veiculo.getImagem());
            stmt.setInt(6, veiculo.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteVeiculo(Veiculo veiculo) {
        String sql = "DELETE FROM Veiculo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, veiculo.getId());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Veiculo> searchVeiculos(String searchTerm) {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM Veiculo WHERE descricao_veiculo LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(result.getInt("id"));
                veiculo.setDescricaoVeiculo(result.getString("descricao_veiculo"));
                veiculo.setPlaca(result.getString("placa"));
                veiculo.setAnoModelo(result.getString("ano_modelo"));
                veiculo.setIdCliente(result.getInt("id_cliente"));
                veiculo.setImagem(result.getBytes("imagem"));
                veiculos.add(veiculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculos;
    }

    
}
