package controller;


import com.example.application.repository.VeiculosRepository;

import model.Veiculo;
import java.util.List;


public class VeiculosController {
private final VeiculosRepository veiculosRepository;

    public VeiculosController() {
        try {
            this.veiculosRepository = new VeiculosRepository();
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
}
