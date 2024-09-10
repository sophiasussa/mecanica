package com.example.application.views.serviços;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import controller.ServicosController;
import model.Servicos;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.List;

@PageTitle("Serviços")
@Route(value = "my-view4", layout = MainLayout.class)
public class ServiçosView  extends Composite<VerticalLayout> {

    private ServicosController servicosController;
    private TextField descricaoField;
    private TextField precoField;
    private Grid<Servicos> grid;
    private Integer servicoId;  // Armazena o ID do serviço em edição
    private TextField searchField; // Campo de pesquisa
    private Button searchButton; // Botão de pesquisa

    public ServiçosView () {
        servicosController = new ServicosController();
        Hr hr = new Hr();
        Hr hr2 = new Hr();
        // Formulário
        FormLayout formLayout = new FormLayout();
        descricaoField = new TextField("Descrição");
        precoField = new TextField("Valor");

        descricaoField.addClassName("rounded-text-field");
        precoField.addClassName("rounded-text-field");

        descricaoField.setWidthFull();
        precoField.setWidthFull();

        formLayout.setWidth("100%");
        formLayout.add(descricaoField, precoField);

        // Botão Salvar
        Button saveButton = new Button("Salvar");
        saveButton.getStyle().set("border-radius", "25px");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(e -> saveOrUpdateServico());

        // Botão de pesquisa
        searchField = new TextField();
        searchField.addClassName("rounded-text-field");
        searchButton = new Button(VaadinIcon.SEARCH.create());
        searchButton.getStyle().set("border-radius", "50%");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.addClickListener(e -> searchServicos());

        // Layout para os botões
        VerticalLayout buttonLayout = new VerticalLayout(saveButton, hr, new HorizontalLayout(searchField, searchButton), hr2);
        buttonLayout.setAlignItems(Alignment.END);
        buttonLayout.setWidthFull();

        hr.getStyle().set("box-shadow", "0 1px 4px rgba(0, 0, 0, 0.1)");
        hr2.getStyle().set("box-shadow", "0 -1px 4px rgba(0, 0, 0, 0.2)");

        // Grid para listar os serviços
        grid = createGrid();

        // Adicionando os componentes ao layout principal
        getContent().add(formLayout, buttonLayout, grid);
    }

    private Grid<Servicos> createGrid() {
        grid = new Grid<>(Servicos.class, false);
    
        // Formatador para moeda em Real
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    
        grid.addColumn(servico -> currencyFormat.format(servico.getPreco()))
            .setHeader("Valor")
            .setSortable(true);
    
        grid.addColumn(Servicos::getDescricaoServico)
            .setHeader("Descrição")
            .setSortable(true);
    
        grid.addComponentColumn(servico -> {
            Button deleteButton = new Button(VaadinIcon.TRASH.create(), e -> deleteServico(servico));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            return deleteButton;
        }).setHeader("Ações");
    
        grid.addItemDoubleClickListener(e -> editServico(e.getItem()));
    
        grid.setItems(servicosController.getAllServicos());
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    
        return grid;
    }
    

    private void saveOrUpdateServico() {
        String descricao = descricaoField.getValue();
        double preco;
    
        // Tentativa de converter o valor digitado para double
        try {
            preco = Double.parseDouble(precoField.getValue());
        } catch (NumberFormatException e) {
            Notification.show("Valor inválido.");
            return;
        }
    
        if (descricao == null || descricao.isEmpty()) {
            Notification.show("Descrição não pode estar vazia.");
            return;
        }
    
        Servicos servico = new Servicos();
        servico.setDescricaoServico(descricao);
        servico.setPreco(preco);  // Salva o valor como número
    
        boolean success;
        if (servicoId != null && servicoId > 0) {
            servico.setId(servicoId);
            success = servicosController.updateServicos(servico);
            if (success) {
                Notification.show("Serviço atualizado com sucesso!");
            } else {
                Notification.show("Falha ao atualizar o serviço.");
            }
        } else {
            success = servicosController.saveServicos(servico);
            if (success) {
                Notification.show("Serviço salvo com sucesso!");
            } else {
                Notification.show("Falha ao salvar o serviço.");
            }
        }
    
        if (success) {
            clearForm();
            refreshGrid();
        }
    }
    

    private void searchServicos() {
        String searchTerm = searchField.getValue();
        List<Servicos> servicos;

        if (searchTerm == null || searchTerm.isEmpty()) {
            servicos = servicosController.getAllServicos();
        } else {
            servicos = servicosController.searchServicos(searchTerm);
        }

        grid.setItems(servicos);
    }

    private void deleteServico(Servicos servico) {
        boolean success = servicosController.deleteServicos(servico);
        if (success) {
            refreshGrid();
        } else {
            System.out.println("Erro ao excluir serviço.");
        }
    }

    private void editServico(Servicos servico) {
        servicoId = servico.getId(); // Armazena o ID do serviço em edição
        descricaoField.setValue(servico.getDescricaoServico());
        precoField.setValue(String.valueOf(servico.getPreco()));
    }

    private void refreshGrid() {
        List<Servicos> servicos = servicosController.getAllServicos();
        grid.setItems(servicos);
    }

    private void clearForm() {
        servicoId = null; // Reseta o ID do serviço em edição
        descricaoField.clear();
        precoField.clear();
    }
}
