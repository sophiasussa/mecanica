package com.example.application.views.mecanico;

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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import controller.MecanicoController;
import model.Mecanico;

import java.util.List;

@PageTitle("Mecanico")
@Route(value = "my-view", layout = MainLayout.class)
public class MecanicoView extends Composite<VerticalLayout> {

    private MecanicoController mecanicoController;
    private TextField textField;
    private TextField textField2;
    private TextField textField3;
    private TextField textField4;
    private TextField textField5;
    private Grid<Mecanico> grid;
    private Integer mecanicoId;  // Armazena o ID do mecânico em edição
    private TextField textField6; // Campo de pesquisa
    private Button buttonPrimary2; // Botão de pesquisa

    public MecanicoView() {
        mecanicoController = new MecanicoController();

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

        buttonPrimary.setText("Salvar");
        buttonPrimary.getStyle().set("border-radius", "25px");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonPrimary.addClickListener(e -> saveOrUpdateMecanico());

        // Configurações do botão de pesquisa
        buttonPrimary2.setIcon(VaadinIcon.SEARCH.create());
        buttonPrimary2.getStyle().set("border-radius", "50%");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.addClickListener(e -> searchMecanicos());

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

    private Grid<Mecanico> createGrid() {
        grid = new Grid<>(Mecanico.class, false);
        grid.addColumn(Mecanico::getNome).setHeader("Nome").setSortable(true);
        grid.addColumn(Mecanico::getEndereco).setHeader("Endereço").setSortable(true);
        grid.addColumn(Mecanico::getCpf).setHeader("CPF").setSortable(true);
        grid.addColumn(Mecanico::getCidade).setHeader("Cidade").setSortable(true);
        grid.addColumn(Mecanico::getTelefone).setHeader("Telefone").setSortable(true);

        grid.addComponentColumn(mecanico -> {
            Button deleteButton = new Button(VaadinIcon.TRASH.create(), e -> deleteMecanico(mecanico));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            return deleteButton;
        }).setHeader("Ações");

        grid.addItemDoubleClickListener(e -> editMecanico(e.getItem()));

        grid.setItems(mecanicoController.getAllMecanicos());
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        return grid;
    }

    private void saveOrUpdateMecanico() {
        String nome = textField.getValue();
        String endereco = textField2.getValue();
        String cpf = textField3.getValue();
        String cidade = textField4.getValue();
        String telefone = textField5.getValue();
    
        if (nome == null || nome.isEmpty() || endereco == null || endereco.isEmpty()) {
            Notification.show("Nome e Endereço não podem estar vazios.");
            return;
        }
    
        Mecanico mecanico = new Mecanico();
        mecanico.setNome(nome);
        mecanico.setEndereco(endereco);
        mecanico.setCpf(cpf);
        mecanico.setCidade(cidade);
        mecanico.setTelefone(telefone);
    
        boolean success;
        if (mecanicoId != null && mecanicoId > 0) {
            // Atualiza o mecânico existente
            mecanico.setId(mecanicoId); // Defina o ID para atualização
            success = mecanicoController.updateMecanico(mecanico);
            if (success) {
                Notification.show("Mecânico atualizado com sucesso!");
            } else {
                Notification.show("Falha ao atualizar o mecânico.");
            }
        } else {
            // Insere um novo mecânico
            success = mecanicoController.saveMecanico(mecanico);
            if (success) {
                Notification.show("Mecânico salvo com sucesso!");
            } else {
                Notification.show("Falha ao salvar o mecânico.");
            }
        }
    
        if (success) {
            // Limpeza após sucesso
            textField.clear();
            textField2.clear();
            textField3.clear();
            textField4.clear();
            textField5.clear();
            mecanicoId = null; // Limpa o ID do mecânico após a operação
            refreshGrid();
        }
    }

    private void searchMecanicos() {
        String searchTerm = textField6.getValue();
        List<Mecanico> mecanicos;
        
        if (searchTerm == null || searchTerm.isEmpty()) {
            mecanicos = mecanicoController.getAllMecanicos();
        } else {
            mecanicos = mecanicoController.searchMecanicos(searchTerm);
        }
        
        grid.setItems(mecanicos);
    }

    private void deleteMecanico(Mecanico mecanico) {
        boolean success = mecanicoController.deleteMecanico(mecanico);
        if (success) {
            refreshGrid();
        } else {
            System.out.println("Erro ao excluir mecânico.");
        }
    }

    private void editMecanico(Mecanico mecanico) {
        mecanicoId = mecanico.getId(); // Armazena o ID do mecânico em edição
        textField.setValue(mecanico.getNome());
        textField2.setValue(mecanico.getEndereco());
        textField3.setValue(mecanico.getCpf());
        textField4.setValue(mecanico.getCidade());
        textField5.setValue(mecanico.getTelefone());
    }

    private void refreshGrid() {
        List<Mecanico> mecanicos = mecanicoController.getAllMecanicos();
        grid.setItems(mecanicos);
    }

    private void clearForm() {
        mecanicoId = null; // Reseta o ID do mecânico em edição
        textField.clear();
        textField2.clear();
        textField3.clear();
        textField4.clear();
        textField5.clear();
    }
}
