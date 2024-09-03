package com.example.application.views.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
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
        getStyle()
            .set("background-color", "var(--lumo-contrast-20pct)")
            .set("display", "flex")
            .set("justify-content", "center")
            .set("align-items", "center")
            .set("min-height", "100vh");

        FormLayout formLayout = new FormLayout();
        formLayout.getStyle().set("justify-content", "center");
        formLayout.getStyle().set("align-items", "center");

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

        registerButton.getStyle()
            .set("background-color", "var(--lumo-primary-color)")
            .set("color", "white")
            .set("margin-top", "var(--lumo-space-l)");

            formLayout.add(usernameField, passwordField, confirmPasswordField, registerButton);

            Div formContainer = new Div(formLayout);
            formContainer.getStyle()
                .set("background-color", "white")
                .set("padding", "var(--lumo-space-l)")
                .set("box-shadow", "0px 4px 8px rgba(0, 0, 0, 0.1)")
                .set("border-radius", "var(--lumo-border-radius-m)")
                .set("margin", "0");
            add(formContainer);
    }
}
