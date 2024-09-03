package controller;

import java.util.List;

import com.example.application.repository.OSRepository;

import model.OrdemServico;
import model.Peca;
import model.Servicos;

public class OSController {

     private final OSRepository osRepository;

    public OSController() {
        try {
            this.osRepository = new OSRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean saveOrdemServico(OrdemServico os, List<Peca> pecas, List<Servicos> servicos) {
        try {
            return osRepository.saveOrdemServico(os, pecas, servicos);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<OrdemServico> getAllOrdensServico() {
        try {
            return osRepository.getAllOrdensServico();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateOrdemServico(OrdemServico os, List<Peca> pecas, List<Servicos> servicos) {
        try {
            return osRepository.updateOrdemServico(os, pecas, servicos);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrdemServico(OrdemServico os) {
        try {
            int idOs = os.getId();
            return osRepository.deleteOrdemServico(idOs);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public OrdemServico OrdemServicoById(OrdemServico os) {
        try {
            int idOs = os.getId();
            return osRepository.OrdemServicoById(idOs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<OrdemServico> searchOS(String searchTerm) {
        return osRepository.searchOS(searchTerm);
    }

}
