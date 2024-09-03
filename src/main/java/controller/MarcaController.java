package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.example.application.repository.DBConnection;
import com.example.application.repository.MarcaRepository;

import model.Marca;

public class MarcaController {

    private final MarcaRepository marcaRepository;

    public MarcaController() {
        try {
            this.marcaRepository = new MarcaRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean saveMarca(Marca marca) {
        try {
            return marcaRepository.saveMarca(marca);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Marca getMarcaById(Marca marca) {
        try {
            int idMarca = marca.getId();
            return marcaRepository.getMarcaById(idMarca);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(Marca marca) {
        try {
            return marcaRepository.update(marca);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Marca marca) {
        try {
            return marcaRepository.delete(marca);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Marca pesquisar(int id) {
        try {
            return marcaRepository.pesquisar(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Marca> pesquisarTodos() {
        try {
            return marcaRepository.pesquisarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
