package controller;


import com.example.application.repository.VeiculosRepository;
import com.example.application.repository.ClienteRepository;

import model.Cliente;
import model.Veiculo;
import java.util.List;


public class VeiculosController {
private final VeiculosRepository veiculosRepository;
private final ClienteRepository clienteRepository;


    public VeiculosController() {
        try {
            this.veiculosRepository = new VeiculosRepository();
            this.clienteRepository = new ClienteRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean saveVeiculo(Veiculo veiculo) {
        try {
            return veiculosRepository.saveVeiculo(veiculo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Veiculo> getAllVeiculos() {
        try {
            return veiculosRepository.getAllVeiculos();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateVeiculo(Veiculo veiculo) {
        try {
            return veiculosRepository.updateVeiculo(veiculo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteVeiculo(Veiculo veiculo) {
        try {
            return veiculosRepository.deleteVeiculo(veiculo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Veiculo> searchVeiculos(String searchTerm) {
        return veiculosRepository.searchVeiculos(searchTerm);
    }

    public List<Cliente> getAllClientes() {
        try {
            return clienteRepository.getAllClientes();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Veiculo getVeiculoById(int id) {
        try {
            return veiculosRepository.getVeiculoById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
