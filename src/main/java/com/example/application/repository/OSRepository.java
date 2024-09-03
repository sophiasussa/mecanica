package com.example.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import model.Cliente;
import model.Mecanico;
import model.OrdemServico;
import model.Peca;
import model.Servicos;
import model.Veiculo;

public class OSRepository {

    private Connection connection;
    
    public OSRepository() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public boolean saveOrdemServico(OrdemServico os, List<Peca> pecas, List<Servicos> servicos) {
    String sqlOS = "INSERT INTO os (numero_os, data_abertura_os, data_encerramento_os, valor_total, id_cliente, id_mecanico, id_veiculo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String sqlOsPecas = "INSERT INTO os_pecas (id_os, id_pecas) VALUES (?, ?)";
    String sqlOsServicos = "INSERT INTO os_servicos (id_os, id_servicos) VALUES (?, ?)";

    try (PreparedStatement stmtOS = connection.prepareStatement(sqlOS, PreparedStatement.RETURN_GENERATED_KEYS)) {
        stmtOS.setInt(1, os.getNumeroOS());
        stmtOS.setDate(2, java.sql.Date.valueOf(os.getDataAbertura()));
        stmtOS.setDate(3, java.sql.Date.valueOf(os.getDataEncerramento()));        
        stmtOS.setDouble(4, os.getValorTotal());
        stmtOS.setInt(5, os.getCliente().getId());
        stmtOS.setInt(6, os.getMecanico().getId());
        stmtOS.setInt(7, os.getVeiculo().getId());

        int rowsInserted = stmtOS.executeUpdate();

        if (rowsInserted > 0) {
            try (ResultSet generatedKeys = stmtOS.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idOs = generatedKeys.getInt(1);

                    try (PreparedStatement stmtOsPecas = connection.prepareStatement(sqlOsPecas)) {
                        for (Peca peca : pecas) {
                            stmtOsPecas.setInt(1, idOs);
                            stmtOsPecas.setInt(2, peca.getId());
                            stmtOsPecas.addBatch();
                        }
                        stmtOsPecas.executeBatch();
                    }

                    try (PreparedStatement stmtOsServicos = connection.prepareStatement(sqlOsServicos)) {
                        for (Servicos servico : servicos) {
                            stmtOsServicos.setInt(1, idOs);
                            stmtOsServicos.setInt(2, servico.getId());
                            stmtOsServicos.addBatch();
                        }
                        stmtOsServicos.executeBatch();
                    }

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

    public List<OrdemServico> getAllOrdensServico() {
        List<OrdemServico> ordens = new ArrayList<>();
        String sqlOS = "SELECT * FROM os";
        String sqlPecas = "SELECT p.* FROM pecas p " +
                        "JOIN os_pecas op ON p.id = op.id_pecas " +
                        "WHERE op.id_os = ?";
        String sqlServicos = "SELECT s.* FROM servicos s " +
                            "JOIN os_servicos os ON s.id = os.id_servicos " +
                            "WHERE os.id_os = ?";
        
        try (PreparedStatement stmtOS = connection.prepareStatement(sqlOS);
            ResultSet rsOS = stmtOS.executeQuery()) {

            while (rsOS.next()) {
                OrdemServico os = new OrdemServico();
                os.setId(rsOS.getInt("id"));
                os.setNumeroOS(rsOS.getInt("numero_os"));
                os.setDataAbertura(rsOS.getDate("data_abertura_os").toLocalDate());
                os.setDataEncerramento(rsOS.getDate("data_encerramento_os").toLocalDate());
                os.setValorTotal(rsOS.getDouble("valor_total"));
                os.setCliente(new ClienteRepository().getClienteById(rsOS.getInt("id_cliente")));
                os.setMecanico(new MecanicoRepository().getMecanicoById(rsOS.getInt("id_mecanico")));
                os.setVeiculo(new VeiculosRepository().getVeiculoById(rsOS.getInt("id_veiculo")));

                
                ordens.add(os);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ordens;
    }

    public boolean updateOrdemServico(OrdemServico os, List<Peca> pecas, List<Servicos> servicos) {
        String sqlOS = "UPDATE os SET numero_os = ?, data_abertura_os = ?, data_encerramento_os = ?, valor_total = ?, id_cliente = ?, id_mecanico = ?, id_veiculo = ? WHERE id = ?";
        String deleteOsPecas = "DELETE FROM os_pecas WHERE id_os = ?";
        String insertOsPecas = "INSERT INTO os_pecas (id_os, id_pecas) VALUES (?, ?)";
        String deleteOsServicos = "DELETE FROM os_servicos WHERE id_os = ?";
        String insertOsServicos = "INSERT INTO os_servicos (id_os, id_servicos) VALUES (?, ?)";
        
        try (PreparedStatement stmtOS = connection.prepareStatement(sqlOS)) {
            stmtOS.setInt(1, os.getNumeroOS());
            stmtOS.setDate(2, java.sql.Date.valueOf(os.getDataAbertura()));
            stmtOS.setDate(3, java.sql.Date.valueOf(os.getDataEncerramento()));
            stmtOS.setDouble(4, os.getValorTotal());
            stmtOS.setInt(5, os.getCliente().getId());
            stmtOS.setInt(6, os.getMecanico().getId());
            stmtOS.setInt(7, os.getVeiculo().getId());
            stmtOS.setInt(8, os.getId());
    
            int rowsUpdated = stmtOS.executeUpdate();
            
            if (rowsUpdated > 0) {
                try (PreparedStatement stmtDeletePecas = connection.prepareStatement(deleteOsPecas)) {
                    stmtDeletePecas.setInt(1, os.getId());
                    stmtDeletePecas.executeUpdate();
                }
                
                try (PreparedStatement stmtInsertPecas = connection.prepareStatement(insertOsPecas)) {
                    for (Peca peca : pecas) {
                        stmtInsertPecas.setInt(1, os.getId());
                        stmtInsertPecas.setInt(2, peca.getId());
                        stmtInsertPecas.addBatch();
                    }
                    stmtInsertPecas.executeBatch();
                }
    
                try (PreparedStatement stmtDeleteServicos = connection.prepareStatement(deleteOsServicos)) {
                    stmtDeleteServicos.setInt(1, os.getId());
                    stmtDeleteServicos.executeUpdate();
                }
                
                try (PreparedStatement stmtInsertServicos = connection.prepareStatement(insertOsServicos)) {
                    for (Servicos servico : servicos) {
                        stmtInsertServicos.setInt(1, os.getId());
                        stmtInsertServicos.setInt(2, servico.getId());
                        stmtInsertServicos.addBatch();
                    }
                    stmtInsertServicos.executeBatch();
                }
    
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteOrdemServico(int idOs) {
        String sqlDeletePecas = "DELETE FROM os_pecas WHERE id_os = ?";
        String sqlDeleteServicos = "DELETE FROM os_servicos WHERE id_os = ?";
        String sqlDeleteOS = "DELETE FROM os WHERE id = ?";
        
        try (PreparedStatement stmtDeletePecas = connection.prepareStatement(sqlDeletePecas);
             PreparedStatement stmtDeleteServicos = connection.prepareStatement(sqlDeleteServicos);
             PreparedStatement stmtDeleteOS = connection.prepareStatement(sqlDeleteOS)) {
            
            stmtDeletePecas.setInt(1, idOs);
            stmtDeletePecas.executeUpdate();
    
            stmtDeleteServicos.setInt(1, idOs);
            stmtDeleteServicos.executeUpdate();
    
            stmtDeleteOS.setInt(1, idOs);
            int rowsDeleted = stmtDeleteOS.executeUpdate();
    
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<OrdemServico> searchOS(String searchTerm) {
        List<OrdemServico> ordens = new ArrayList<>();
        String sqlOS = "SELECT * FROM os WHERE numero_os LIKE ?";
        String sqlPecas = "SELECT p.* FROM pecas p " +
                          "JOIN os_pecas op ON p.id = op.id_pecas " +
                          "WHERE op.id_os = ?";
        String sqlServicos = "SELECT s.* FROM servicos s " +
                             "JOIN os_servicos os ON s.id = os.id_servicos " +
                             "WHERE os.id_os = ?";
    
        try (PreparedStatement stmtOS = connection.prepareStatement(sqlOS)) {
            stmtOS.setString(1, "%" + searchTerm + "%");
            ResultSet rsOS = stmtOS.executeQuery();
    
            while (rsOS.next()) {
                OrdemServico os = new OrdemServico();
                os.setId(rsOS.getInt("id"));
                os.setNumeroOS(rsOS.getInt("numero_os"));
                os.setDataAbertura(rsOS.getDate("os.data_abertura_os").toLocalDate());
                os.setDataEncerramento(rsOS.getDate("os.data_encerramento_os").toLocalDate());
                os.setValorTotal(rsOS.getDouble("valor_total"));
                os.setCliente(new ClienteRepository().getClienteById(rsOS.getInt("id_cliente")));
                os.setMecanico(new MecanicoRepository().getMecanicoById(rsOS.getInt("id_mecanico")));
                os.setVeiculo(new VeiculosRepository().getVeiculoById(rsOS.getInt("id_veiculo")));
    
                ordens.add(os);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return ordens;
    }

    public OrdemServico OrdemServicoById(int id) {
        String sql = "SELECT os.*, c.*, m.*, v.* " +
                     "FROM os " +
                     "LEFT JOIN Cliente c ON os.id_cliente = c.id " +
                     "LEFT JOIN Mecanico m ON os.id_mecanico = m.id " +
                     "LEFT JOIN Veiculo v ON os.id_veiculo = v.id " +
                     "WHERE os.id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                OrdemServico ordemServico = new OrdemServico();
                ordemServico.setId(result.getInt("os.id"));
                ordemServico.setNumeroOS(result.getInt("os.numero_os"));
                ordemServico.setDataAbertura(result.getDate("os.data_abertura_os").toLocalDate());
                ordemServico.setDataEncerramento(result.getDate("os.data_encerramento_os").toLocalDate());
                ordemServico.setValorTotal(result.getDouble("os.valor_total"));
                ordemServico.setCliente(new ClienteRepository().getClienteById(result.getInt("id_cliente")));
                ordemServico.setMecanico(new MecanicoRepository().getMecanicoById(result.getInt("id_mecanico")));
                ordemServico.setVeiculo(new VeiculosRepository().getVeiculoById(result.getInt("id_veiculo")));
    
                return ordemServico;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
