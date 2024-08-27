package controller;


import com.example.application.repository.ServicosRepository;

import model.Servicos;
import java.util.List;


public class ServicosController {
    
 private final ServicosRepository servicosRepository;

    public ServicosController() {
        try {
            this.servicosRepository = new ServicosRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean saveServicos(Servicos servico) {
        try {
            return servicosRepository.saveServicos(servico);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Servicos> getAllServicos() {
        try {
            return servicosRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateServicos(Servicos servico) {
        try {
            return servicosRepository.update(servico);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteServicos(Servicos servico) {
        try {
            return servicosRepository.delete(servico);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Servicos> searchServicos(String searchTerm) {
        return servicosRepository.searchServicos(searchTerm);
    }
}
