package controller;

import model.Cliente;
import java.util.List;
import com.example.application.repository.ClienteRepository;

public class ClienteController {

    private ClienteRepository clienteRepository;

    public ClienteController() {
        try {
            this.clienteRepository = new ClienteRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }    

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
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

    public boolean isClienteInUse(Cliente cliente) {
        return clienteRepository.isClienteInUse(cliente);
    }

    public boolean deleteCliente(Cliente cliente) {
        if (isClienteInUse(cliente)) {
            System.out.println("Não é possível excluir o cliente. O cliente está associado a um ou mais veículos.");
            return false;
        }
        return clienteRepository.delete(cliente);
    }

    public Cliente getClienteById(int id) {
        return clienteRepository.getClienteById(id);
    }

    public List<Cliente> searchClientes(String searchTerm) {
        return clienteRepository.searchClientes(searchTerm);
    }
}
