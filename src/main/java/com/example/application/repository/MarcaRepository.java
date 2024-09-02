package com.example.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Marca;
import model.Peca;

public class MarcaRepository {

    private Connection connection;
    
        public MarcaRepository() throws SQLException {
            this.connection = DBConnection.getInstance().getConnection();
        }

        public boolean saveMarca(Marca marca) {
            String sql = "INSERT INTO marca (nome) VALUES (?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                // Não define o ID, pois é auto-incremento
                stmt.setString(1, marca.getNome());
        
                int rowsInserted = stmt.executeUpdate();
        
                if (rowsInserted > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            marca.setId(generatedId); // Defina o ID no objeto peça
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

    public boolean update(Marca marca) {
        String sql = "UPDATE marca SET nome = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, marca.getNome());
            stmt.setInt(2, marca.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public boolean delete(Marca marca) {
        String sql = "DELETE FROM marca WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, marca.getId());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Marca pesquisar(int id) {
		try {
			String consulta = "SELECT * from marca where id = ?";
			Marca marca = new Marca();
			PreparedStatement preparedStatement = connection.prepareStatement(consulta);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				marca.setId(resultSet.getInt("id"));
				marca.setNome(resultSet.getString("nome"));
			}
			return marca;
		} catch (Exception e) {
            e.printStackTrace();
			return null;
		}
	}

    public List<Marca> pesquisarTodos() {
        try {
            String consulta = "SELECT * from marca";
            List<Marca> lista = new ArrayList<Marca>();
            Marca marca;
            PreparedStatement preparedStatement = connection.prepareStatement(consulta);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                marca = new Marca();
                marca.setId(resultSet.getInt("id"));
                marca.setNome(resultSet.getString("nome"));
                lista.add(marca);
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
