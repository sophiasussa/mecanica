package com.example.application.views.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.UI;
import controller.UserController;
import model.User;

@Route("login")
public class UserView extends Div {

    private UserController userController = new UserController();

    public UserView() {
        // Verifica se o usuário já está autenticado
        User currentUser = (User) UI.getCurrent().getSession().getAttribute(User.class);
        if (currentUser != null) {
            // Se o usuário já está autenticado, redireciona para a tela principal
            getUI().ifPresent(ui -> ui.navigate(""));
            return;
        }

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
                    ui.navigate("");  // Redireciona para a MainView ou outra tela padrão
                });
            } else {
                // Falha no login: mostre uma mensagem de erro
                loginForm.setError(true);
            }
        });

        add(loginForm);
        loginForm.getElement().setAttribute("no-autofocus", "");

        Button registerButton = new Button("Register", e -> {
            getUI().ifPresent(ui -> ui.navigate("register"));
        });
        add(registerButton);
    }
}
