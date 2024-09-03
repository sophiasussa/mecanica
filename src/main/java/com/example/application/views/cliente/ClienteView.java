package com.example.application.views.cliente;

import com.example.application.repository.ClienteRepository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import controller.ClienteController;
import model.Cliente;
import model.User;

import java.util.List;

@PageTitle("Cliente")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ClienteView extends Composite<VerticalLayout> {

    private ClienteController clienteController;
    private TextField textField;
    private TextField textField2;
    private TextField textField3;
    private TextField textField4;
    private TextField textField5;
    private Grid<Cliente> grid;
    private Integer clienteId;  // Armazena o ID do cliente em edição
    private TextField textField6; // Campo de pesquisa
    private Button buttonPrimary2; // Botão de pesquisa

    public ClienteView() {
        // // Verifica se o usuário está autenticado
        // User user = (User) getUI().get().getSession().getAttribute(User.class);
        // if (user == null) {
        //     // Redireciona para a tela de login se o usuário não estiver autenticado
        //     getUI().ifPresent(ui -> ui.navigate("login"));
        //     return; // Interrompe a execução do construtor se o usuário não estiver autenticado
        // }

        clienteController = new ClienteController();

        // Formulário
        FormLayout formLayout2Col = new FormLayout();
        textField = new TextField();
        textField2 = new TextField();
        FormLayout formLayout3Col = new FormLayout();
        textField3 = new TextField();
        textField4 = new TextField();
        textField5 = new TextField();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Button buttonPrimary = new Button();
        Hr hr = new Hr();
        HorizontalLayout layoutRow = new HorizontalLayout();
        textField6 = new TextField(); // Inicializa o campo de pesquisa
        textField6.addClassName("rounded-text-field");
        buttonPrimary2 = new Button(); // Inicializa o botão de pesquisa
        Hr hr2 = new Hr();
        hr.getStyle().set("box-shadow", "0 1px 4px rgba(0, 0, 0, 0.2)");
        hr2.getStyle().set("box-shadow", "0 -1px 4px rgba(0, 0, 0, 0.2)");

        // Configurações do layout
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        formLayout2Col.setWidth("100%");


        textField.setPlaceholder("Nome");
        textField.addClassName("rounded-text-field");
        textField.setWidth("min-content");
        textField.setRequiredIndicatorVisible(true);



        textField2.setPlaceholder("Endereço");
        textField2.addClassName("rounded-text-field");
        textField2.setWidth("min-content");
        formLayout3Col.setWidth("100%");
        formLayout3Col.setResponsiveSteps(new ResponsiveStep("0", 1), new ResponsiveStep("250px", 2),
                new ResponsiveStep("500px", 3));
        textField3.setPlaceholder("CPF");
        textField3.addClassName("rounded-text-field");
        textField3.setWidth("min-content");
        textField4.setPlaceholder("Cidade");
        textField4.addClassName("rounded-text-field");
        textField4.setWidth("min-content");


        textField5.setPlaceholder("Telefone");
        textField5.addClassName("rounded-text-field");
        textField5.setWidth("min-content");
        textField5.setRequiredIndicatorVisible(true); // Telefone



        buttonPrimary.setText("Salvar");
        buttonPrimary.getStyle().set("border-radius", "25px");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonPrimary.addClickListener(e -> saveOrUpdateCliente());

        // Configurações do botão de pesquisa
        buttonPrimary2.setIcon(VaadinIcon.SEARCH.create());
        buttonPrimary2.getStyle().set("border-radius", "50%");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.addClickListener(e -> searchClientes());

        layoutColumn2.setWidthFull();
        layoutColumn2.add(buttonPrimary);
        layoutColumn2.add(hr);
        layoutColumn2.add(layoutRow);
        layoutColumn2.setAlignItems(Alignment.END);

        layoutRow.add(textField6);
        layoutRow.add(buttonPrimary2);
        layoutColumn2.add(hr2);

        layoutColumn2.add(createGrid());

        layoutRow.addClassName("gap-m");
        layoutRow.setWidth("100%");
        layoutRow.setHeight("70px");
        layoutRow.setAlignItems(FlexComponent.Alignment.END);
        layoutRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        getContent().add(formLayout2Col, formLayout3Col, layoutColumn2);

        formLayout2Col.add(textField);
        formLayout2Col.add(textField2);
        formLayout3Col.add(textField3);
        formLayout3Col.add(textField4);
        formLayout3Col.add(textField5);

        layoutColumn2.setFlexGrow(1.0, layoutRow);
    }

    private Grid<Cliente> createGrid() {
        grid = new Grid<>(Cliente.class, false);
        grid.addColumn(Cliente::getNome).setHeader("Nome").setSortable(true);
        grid.addColumn(Cliente::getEndereco).setHeader("Endereço").setSortable(true);
        grid.addColumn(Cliente::getCpf).setHeader("CPF").setSortable(true);
        grid.addColumn(Cliente::getCidade).setHeader("Cidade").setSortable(true);
        grid.addColumn(Cliente::getTelefone).setHeader("Telefone").setSortable(true);

        grid.addComponentColumn(cliente -> {
            Button deleteButton = new Button(VaadinIcon.TRASH.create(), e -> deleteCliente(cliente));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            return deleteButton;
        }).setHeader("Ações");

        grid.addItemDoubleClickListener(e -> editCliente(e.getItem()));

        grid.setItems(clienteController.getAllClientes());
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        return grid;
    }

    private void saveOrUpdateCliente() {
        String nome = textField.getValue();
        String endereco = textField2.getValue();
        String cpf = textField3.getValue();
        String cidade = textField4.getValue();
        String telefone = textField5.getValue();
    
        if (nome == null || nome.isEmpty() || endereco == null || endereco.isEmpty()) {
            Notification.show("Nome e Endereço não podem estar vazios.");
            return;
        }
    
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEndereco(endereco);
        cliente.setCpf(cpf);
        cliente.setCidade(cidade);
        cliente.setTelefone(telefone);
    
        boolean success;
        if (clienteId != null && clienteId > 0) {
            // Atualiza o cliente existente
            cliente.setId(clienteId); // Defina o ID para atualização
            success = clienteController.updateCliente(cliente);
            if (success) {
                Notification.show("Cliente atualizado com sucesso!");
            } else {
                Notification.show("Falha ao atualizar o cliente.");
            }
        } else {
            // Insere um novo cliente
            success = clienteController.saveCliente(cliente);
            if (success) {
                Notification.show("Cliente salvo com sucesso!");
            } else {
                Notification.show("Falha ao salvar o cliente.");
            }
        }
    
        if (success) {
            // Limpeza após sucesso
            textField.clear();
            textField2.clear();
            textField3.clear();
            textField4.clear();
            textField5.clear();
            clienteId = null; // Limpa o ID do cliente após a operação
            refreshGrid();
        }
    }

    private void searchClientes() {
        String searchTerm = textField6.getValue();
        List<Cliente> clientes;
        
        if (searchTerm == null || searchTerm.isEmpty()) {
            clientes = clienteController.getAllClientes();
        } else {
            clientes = clienteController.searchClientes(searchTerm);
        }
        
        grid.setItems(clientes);
    }

    public boolean deleteCliente(Cliente cliente) {
        if (clienteController.isClienteInUse(cliente)) {
            // Exibe uma notificação ao usuário informando que o cliente está associado a veículos
            Notification.show("Não é possível excluir o cliente. O cliente está associado a um ou mais veículos.");
            return false;
        }
        
        boolean isDeleted = clienteController.deleteCliente(cliente);
        
        if (isDeleted) {
            // Atualiza a grid para refletir a exclusão do cliente
            refreshGrid();
            // Exibe uma notificação ao usuário confirmando a exclusão
            Notification.show("Cliente excluído com sucesso.");
        } else {
            // Se houver algum erro na exclusão, exibe uma notificação ao usuário
            Notification.show("Erro ao excluir o cliente.");
        }
        
        return isDeleted;
    }
    
    private void editCliente(Cliente cliente) {
        clienteId = cliente.getId(); // Armazena o ID do cliente em edição
        textField.setValue(cliente.getNome());
        textField2.setValue(cliente.getEndereco());
        textField3.setValue(cliente.getCpf());
        textField4.setValue(cliente.getCidade());
        textField5.setValue(cliente.getTelefone());
    }

    private void refreshGrid() {
        List<Cliente> clientes = clienteController.getAllClientes();
        grid.setItems(clientes);
    }

    private void clearForm() {
        clienteId = null; // Reseta o ID para garantir que o próximo save crie um novo cliente
        textField.clear();
        textField2.clear();
        textField3.clear();
        textField4.clear();
        textField5.clear();
    }
}
