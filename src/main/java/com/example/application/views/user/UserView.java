package com.example.application.views.user;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.Route;
import controller.UserController;
import model.User;

@Route("login")
public class UserView extends Div {

    private UserController userController = new UserController();

    public UserView() {
        getStyle()
            .set("background-color", "var(--lumo-contrast-5pct)")
            .set("display", "flex")
            .set("justify-content", "center")
            .set("padding", "var(--lumo-space-l)");

        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(e -> {
            String username = e.getUsername();
            String password = e.getPassword();

            User user = userController.login(username, password);
            if (user != null) {
                // Login bem-sucedido: armazene o usuário na sessão e redirecione
                getUI().ifPresent(ui -> {
                    ui.getSession().setAttribute(User.class, user);
                    ui.navigate("main");
                });
            } else {
                // Falha no login: mostre uma mensagem de erro
                loginForm.setError(true);
            }
        });

        add(loginForm);
        loginForm.getElement().setAttribute("no-autofocus", "");
    }
}
