package controller;

import java.util.List;

import com.example.application.repository.OSServicoRepository;

import model.OSPeca;
import model.OSServico;
import model.OrdemServico;

public class OSServicoController {

     private final OSServicoRepository osservicoRepository;

    public OSServicoController() {
        try {
            this.osservicoRepository = new OSServicoRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public List<OSServico> getOsServicoByOrdemServicoId(OrdemServico os) {
        try {
            int idOs = os.getId();
            return osservicoRepository.getOsServicoByOrdemServicoId(idOs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
