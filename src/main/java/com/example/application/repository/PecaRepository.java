package com.example.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Peca;
import model.Marca;

public class PecaRepository {


    private Connection connection;
    
    public PecaRepository() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public boolean savePeca(Peca peca) {
        String sql = "INSERT INTO pecas (descricao, preco, id_marca) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Não define o ID, pois é auto-incremento
            stmt.setString(1, peca.getDescricao());
            stmt.setDouble(2, peca.getPreco());
            stmt.setInt(3, peca.getMarca().getId());
    
            int rowsInserted = stmt.executeUpdate();
    
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        peca.setId(generatedId); // Defina o ID no objeto peça
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

    public List<String> getAllPecas() {
        List<String> pecas = new ArrayList<>();
        String sql = "SELECT descricao FROM pecas";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                pecas.add(result.getString("descricao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pecas;
    }
    

    public List<Peca> getAll() {
        List<Peca> pecas = new ArrayList<>();
        String sql = "SELECT * FROM pecas";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Peca peca = new Peca();
                peca.setId(result.getInt("id"));
                peca.setDescricao(result.getString("descricao"));
                peca.setPreco(result.getDouble("preco"));
                Marca marca = new MarcaRepository().pesquisar(result.getInt("idMarca"));
                peca.setMarca(marca);
                pecas.add(peca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pecas;
    }

    public boolean update(Peca peca) {
        String sql = "UPDATE pecas SET descricao = ?, preco = ?, id_marca = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, peca.getDescricao());
            stmt.setDouble(2, peca.getPreco());
            stmt.setInt(3, peca.getMarca().getId());
            stmt.setInt(4, peca.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public boolean delete(Peca peca) {
        String sql = "DELETE FROM pecas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, peca.getId());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Peca> search(String searchTerm) {
        List<Peca> pecas = new ArrayList<>();
        String sql = "SELECT * FROM pecas WHERE descricao LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%"); // Pesquisa com LIKE
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Peca peca = new Peca();
                peca.setId(result.getInt("id"));
                peca.setDescricao(result.getString("descricao"));
                peca.setPreco(result.getDouble("preco"));
                pecas.add(peca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pecas;
    }

}
