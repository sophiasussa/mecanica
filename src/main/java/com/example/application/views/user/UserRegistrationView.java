package com.example.application.views.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import controller.UserController;
import model.User;

@Route("register")
public class UserRegistrationView extends FormLayout {

    private UserController userController = new UserController();

    public UserRegistrationView() {
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        PasswordField confirmPasswordField = new PasswordField("Confirm Password");

        Button registerButton = new Button("Register");

        registerButton.addClickListener(e -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            String confirmPassword = confirmPasswordField.getValue();

            if (!password.equals(confirmPassword)) {
                Notification.show("Passwords do not match");
                return;
            }

            User user = new User(username, password);
            boolean userCreated = userController.register(user);

            if (userCreated) {
                Notification.show("User registered successfully");
                getUI().ifPresent(ui -> ui.navigate("login"));
            } else {
                Notification.show("Registration failed, username may already be in use");
            }
        });

        add(usernameField, passwordField, confirmPasswordField, registerButton);
    }
}
