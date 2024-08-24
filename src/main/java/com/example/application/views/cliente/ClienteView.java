package com.example.application.views.cliente;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import controller.ClienteController;
import model.Cliente;

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

    public ClienteView() {
        // clienteController = new ClienteController();
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
        TextField textField6 = new TextField();
        Button buttonPrimary2 = new Button();
        Hr hr2 = new Hr();
        VerticalLayout layout = new VerticalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        formLayout2Col.setWidth("100%");
        textField.setPlaceholder("Nome");
        textField.addClassName("rounded-text-field");
        textField.setWidth("min-content");
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
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        
        buttonPrimary.addClickListener(e -> saveCliente());


        buttonPrimary.setText("Salvar");
        buttonPrimary.getStyle().set("border-radius", "25px");
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.END, buttonPrimary);
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("70px");
        layoutRow.setAlignItems(Alignment.END);
        layoutRow.setJustifyContentMode(JustifyContentMode.END);
        textField6.setPlaceholder("Pesquisar");
        textField6.addClassName("rounded-text-field");
        textField6.setWidth("min-content");
        buttonPrimary2.setIcon(VaadinIcon.SEARCH.create());
        buttonPrimary2.getStyle().set("border-radius", "50%");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        hr.getStyle().set("box-shadow", "0 1px 4px rgba(0, 0, 0, 0.2)");
        hr2.getStyle().set("box-shadow", "0 -1px 4px rgba(0, 0, 0, 0.2)");
        getContent().add(formLayout2Col);
        formLayout2Col.add(textField);
        formLayout2Col.add(textField2);
        getContent().add(formLayout3Col);
        formLayout3Col.add(textField3);
        formLayout3Col.add(textField4);
        formLayout3Col.add(textField5);
        layout.add(formLayout2Col, formLayout3Col);
        getContent().add(layout);
        getContent().add(layoutColumn2);
        layoutColumn2.add(buttonPrimary);
        layoutColumn2.add(hr);
        layoutColumn2.add(layoutRow);
        layoutRow.add(textField6);
        layoutRow.add(buttonPrimary2);
        layoutColumn2.add(hr2);
    }

    private void saveCliente() {
        clienteController = new ClienteController();
        Cliente cliente = new Cliente();
        cliente.setNome(textField.getValue());
        cliente.setEndereco(textField2.getValue());
        cliente.setCpf(textField3.getValue());
        cliente.setCidade(textField4.getValue());
        cliente.setTelefone(textField5.getValue());

        boolean success = clienteController.saveCliente(cliente);
        System.out.println("Entrou em salvar");
        if (success) {
            System.out.println("Cliente salvo com sucesso!");
        } else {
            System.out.println("Erro ao salvar cliente.");
        }
    }
    
}