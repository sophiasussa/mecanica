package controller;

import model.User;
import com.example.application.repository.UserRepository;
import java.sql.SQLException;

public class UserController {

    private UserRepository userRepository;

    public UserController() {
        try {
            this.userRepository = new UserRepository();
        } catch (SQLException e) {
            e.printStackTrace();
            // Se desejar, pode lançar uma RuntimeException ou fazer outro tipo de tratamento
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public boolean register(User user) {
        return userRepository.save(user);
    }
    
    
}
