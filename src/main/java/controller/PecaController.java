package controller;

import java.util.List;

import com.example.application.repository.PecaRepository;

import model.Marca;
import model.Peca;

public class PecaController {

     private final PecaRepository pecaRepository;

    public PecaController() {
        try {
            this.pecaRepository = new PecaRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean savePeca(Peca peca) {
        try {
            return pecaRepository.savePeca(peca);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Peca> getAll() {
        try {
            return pecaRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(Peca peca) {
        try {
            return pecaRepository.update(peca);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Peca peca) {
        try {
            return pecaRepository.deletePeca(peca);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Peca getPecaById(Peca peca) {
        try {
            int idPeca = peca.getId();
            return pecaRepository.getPecaById(idPeca);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Peca> search(String searchTerm) {
        return pecaRepository.search(searchTerm);
    }
}
