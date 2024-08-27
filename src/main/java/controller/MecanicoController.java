package controller;

import model.Mecanico;
import java.util.List;

import com.example.application.repository.MecanicoRepository;

public class MecanicoController {

    private final MecanicoRepository mecanicoRepository;

    public MecanicoController() {
        try {
            this.mecanicoRepository = new MecanicoRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean saveMecanico(Mecanico mecanico) {
        try {
            return mecanicoRepository.saveMecanico(mecanico);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Mecanico> getAllMecanicos() {
        try {
            return mecanicoRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateMecanico(Mecanico mecanico) {
        try {
            return mecanicoRepository.update(mecanico);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMecanico(Mecanico mecanico) {
        try {
            return mecanicoRepository.delete(mecanico);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Mecanico getMecanicoById(int id) {
        return mecanicoRepository.getMecanicoById(id);
    }

    public List<Mecanico> searchMecanicos(String searchTerm) {
        return mecanicoRepository.searchMecanicos(searchTerm);
    }

}
