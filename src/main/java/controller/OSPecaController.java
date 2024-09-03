package controller;

import java.util.List;

import com.example.application.repository.OSPecaRepository;

import model.OSPeca;
import model.OrdemServico;

public class OSPecaController {

     private final OSPecaRepository ospecaRepository;

    public OSPecaController() {
        try {
            this.ospecaRepository = new OSPecaRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public List<OSPeca> getOsPecasByOrdemServicoId(OrdemServico os) {
        try {
            int idOs = os.getId();
            return ospecaRepository.getOsPecasByOrdemServicoId(idOs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
