package com.example.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class ClienteRepository {

        private Connection connection;
    
        public ClienteRepository() throws SQLException {
            this.connection = DBConnection.getInstance().getConnection();
        }
    
    

        public boolean saveCliente(Cliente cliente) {
            String sql = "INSERT INTO Cliente (nome, endereco, cpf, cidade, telefone) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                // Não define o ID, pois é auto-incremento
                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getEndereco());
                stmt.setString(3, cliente.getCpf());
                stmt.setString(4, cliente.getCidade());
                stmt.setString(5, cliente.getTelefone());
        
                int rowsInserted = stmt.executeUpdate();
        
                if (rowsInserted > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            cliente.setId(generatedId); // Defina o ID no objeto cliente
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
        

    public List<String> getAllNomes() {
    List<String> nomes = new ArrayList<>();
    String sql = "SELECT nome FROM Cliente";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            nomes.add(result.getString("nome"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return nomes;
}

    public List<Cliente> getAll() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(result.getInt("id"));
                cliente.setNome(result.getString("nome"));
                cliente.setEndereco(result.getString("endereco"));
                cliente.setCpf(result.getString("cpf"));
                cliente.setCidade(result.getString("cidade"));
                cliente.setTelefone(result.getString("telefone"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public boolean update(Cliente cliente) {
        String sql = "UPDATE Cliente SET nome = ?, endereco = ?, cpf = ?, cidade = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getCidade());
            stmt.setString(5, cliente.getTelefone());
            stmt.setInt(6, cliente.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Cliente cliente) {
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isClienteInUse(Cliente cliente) {
        String sql = "SELECT COUNT(*) FROM Os WHERE cliente_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                int count = result.getInt(1);
                System.out.println("Cliente ID: " + cliente.getId() + " está associado a " + count + " os.");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Se houver um erro na consulta ou não houver resultado, retorne false, permitindo a exclusão.
        return false;
    }
    
    public int getIdByNome(String nome) {
        String sql = "SELECT id FROM Cliente WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return result.getInt("id");
            } else {
                throw new SQLException("Cliente não encontrado: " + nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Erro
        }
    }
    
    public String getNomeById(int id) {
        String sql = "SELECT nome FROM Cliente WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return result.getString("nome");
            } else {
                throw new SQLException("Cliente não encontrado: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Erro
        }
    }

    public Cliente getClienteById(int id) {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(result.getInt("id"));
                cliente.setNome(result.getString("nome"));
                cliente.setEndereco(result.getString("endereco"));
                cliente.setCpf(result.getString("cpf"));
                cliente.setCidade(result.getString("cidade"));
                cliente.setTelefone(result.getString("telefone"));
                return cliente;
            } else {
                return null; // Cliente não encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Em caso de erro
        }
    }

    public List<Cliente> searchClientes(String searchTerm) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente WHERE nome LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%"); // Pesquisa com LIKE
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(result.getInt("id"));
                cliente.setNome(result.getString("nome"));
                cliente.setEndereco(result.getString("endereco"));
                cliente.setCpf(result.getString("cpf"));
                cliente.setCidade(result.getString("cidade"));
                cliente.setTelefone(result.getString("telefone"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public List<Cliente> getAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nome FROM Clientes"; // Adjust the table and column names accordingly

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(result.getInt("id"));
                cliente.setNome(result.getString("nome"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }
    
    
        
}

