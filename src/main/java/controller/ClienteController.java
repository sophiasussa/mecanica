package controller;

import model.Cliente;
import java.util.List;

import com.example.application.repository.ClienteRepository;

public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController() {
        try {
            this.clienteRepository = new ClienteRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean saveCliente(Cliente cliente) {
        try {
            return clienteRepository.saveCliente(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cliente> getAllClientes() {
        try {
            return clienteRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateCliente(Cliente cliente) {
        try {
            return clienteRepository.update(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCliente(Cliente cliente) {
        try {
            return clienteRepository.delete(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
