package com.example.application.views.peças;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

import controller.MarcaController;
import controller.PecaController;

import java.util.ArrayList;
import java.util.List;

import model.Marca;
import model.Peca;
import model.Servicos;
import java.text.NumberFormat;
import java.util.Locale;

@PageTitle("Peças")
@Route(value = "my-view3", layout = MainLayout.class)
public class PeçasView extends Composite<VerticalLayout> {

    MarcaController controller = new MarcaController();
    PecaController controller2 = new PecaController();
    private TextField textField;
    private TextField textField2;
    private Grid<Peca> grid;
    private ComboBox<Marca> comboBox = new ComboBox<>("Marca");
    private Integer pecaId;  // Armazena o ID do serviço em edição
    private TextField textField3; // Campo de pesquisa
    private Button buttonPrimary2; // Botão de pesquisa
    
    public PeçasView() {
        controller2 = new PecaController();

        // Formulário
        FormLayout formLayout2Col = new FormLayout();
        textField = new TextField();
        textField2 = new TextField();
        HorizontalLayout layoutRow = new HorizontalLayout();
        comboBox = new ComboBox();
        Button buttonTertiary = new Button();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Hr hr = new Hr();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        textField3 = new TextField();
        buttonPrimary2 = new Button();
        Hr hr2 = new Hr();
        VerticalLayout layout = new VerticalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        formLayout2Col.setWidth("100%");
        textField.setPlaceholder("Descrição");
        textField.addClassName("rounded-text-field");
        textField.setWidth("min-content");
        textField2.setPlaceholder("Preço");
        textField2.addClassName("rounded-text-field");
        textField2.setWidth("min-content");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("70px");
        layoutRow.setAlignItems(Alignment.END);
        layoutRow.setJustifyContentMode(JustifyContentMode.START);
        comboBox.setPlaceholder("Marca");
        comboBox.addClassName("rounded-text-field");
        comboBox.setWidth("min-content");
        setComboBoxData(comboBox);
        buttonTertiary.setText("+ Marca");
        buttonTertiary.setWidth("min-content");
        buttonTertiary.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonTertiary.addClickListener(event -> openDialog());
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutRow2.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutRow2.setAlignItems(Alignment.CENTER);
        layoutRow2.setJustifyContentMode(JustifyContentMode.END);
        buttonPrimary.setText("Salvar");
        buttonPrimary.getStyle().set("border-radius", "25px");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(e -> saveOrUpdatePeca());
        layoutRow3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.setHeight("70px");
        layoutRow3.setAlignItems(Alignment.END);
        layoutRow3.setJustifyContentMode(JustifyContentMode.END);
        textField3.setWidth("min-content");
        textField3.addClassName("rounded-text-field");
        buttonPrimary2.setIcon(VaadinIcon.SEARCH.create());
        buttonPrimary2.getStyle().set("border-radius", "50%");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.addClickListener(e -> searchPeca());
        hr.getStyle().set("box-shadow", "0 1px 4px rgba(0, 0, 0, 0.2)");
        hr2.getStyle().set("box-shadow", "0 -1px 4px rgba(0, 0, 0, 0.1)");
        formLayout2Col.add(textField);
        formLayout2Col.add(textField2);
        layoutRow.add(comboBox);
        layoutRow.add(buttonTertiary);
        layout.add(formLayout2Col, layoutRow);
        getContent().add(layout);
        layoutRow2.add(buttonPrimary);
        getContent().add(layoutRow2);
        layoutColumn2.add(hr);
        layoutColumn2.add(layoutRow3);
        getContent().add(layoutColumn2);
        layoutRow3.add(textField3);
        layoutRow3.add(buttonPrimary2);
        layoutColumn2.add(hr2);

        grid = createGrid();
        getContent().add(grid);

    }

    private void openDialog() {
        Dialog dialog = new Dialog();
        dialog.setWidth("800px");
        dialog.setHeight("600px");

        FormLayout formLayout = new FormLayout();
        TextField nomeField = new TextField("Nome");

        Grid<Marca> grid = new Grid<>(Marca.class);
        grid.setColumns("nome");

        List<Marca> marcas = controller.pesquisarTodos();
        grid.setItems(marcas);

        Editor<Marca> editor = grid.getEditor();
        Binder<Marca> binder = new Binder<>(Marca.class);
        editor.setBinder(binder);

        TextField nomeEditor = new TextField();
        binder.forField(nomeEditor).bind(Marca::getNome, Marca::setNome);
        grid.getColumnByKey("nome").setEditorComponent(nomeEditor);

        grid.addItemDoubleClickListener(event -> editor.editItem(event.getItem()));
        editor.addCloseListener(event -> grid.getDataProvider().refreshItem(event.getItem()));
        
        grid.addComponentColumn(marca -> {
            Button alterarButton = new Button("Alterar", new Icon(VaadinIcon.COG));
            alterarButton.addClickListener(e -> {
                if (editor.isOpen()) {
                    editor.save();
                    Marca editedMarca = editor.getItem();
                    if (controller.update(editedMarca)) {
                        Notification notification = new Notification(
                                "Marca atualizado com sucesso.", 3000);
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        notification.setPosition(Notification.Position.MIDDLE);
                        notification.open();
                        
                        marcas.clear();
                        marcas.addAll(controller.pesquisarTodos());
                        grid.getDataProvider().refreshAll();
                    } else {
                        Notification notification = new Notification(
                                "Erro ao atualizar. Verifique se todos os dados foram preenchidos.", 3000);
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        notification.setPosition(Notification.Position.MIDDLE);
                        notification.open();
                    }
                } else {
                    editor.editItem(marca);
                    nomeEditor.focus();
                }
            });
            return alterarButton;
        }).setHeader("Alterar");
        
        editor.addSaveListener(event -> {
            grid.getDataProvider().refreshItem(event.getItem());
        });

        grid.addComponentColumn(marca -> {
            Button deletarButton = new Button(new Icon(VaadinIcon.TRASH));
            deletarButton.addClickListener(e -> {
                if (controller.delete(marca)) {
                    Notification notification = new Notification(
                            "Marca deletado com sucesso.", 3000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();
                    
                    marcas.clear();
                    marcas.addAll(controller.pesquisarTodos());
                    grid.getDataProvider().refreshAll();
                } else {
                    Notification notification = new Notification(
                            "Erro ao deletar. Tente novamente.", 3000);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();
                }
            });
            return deletarButton;
        }).setHeader("Deletar");
    

        Button confirmarButton = new Button("Salvar", event -> {
            if(nomeField.isEmpty()){
                Notification notification = new Notification(
                    "Erro: O nome não pode estar vazio.", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();
            } else {
                Marca marca = new Marca();
                marca.setNome(nomeField.getValue());
                if (controller.saveMarca(marca) == true) {
                    Notification notification = new Notification(
                            "Marca salvo com sucesso.", 3000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();

                    nomeField.clear();
                    marcas.clear();
                    marcas.addAll(controller.pesquisarTodos());
                    grid.getDataProvider().refreshAll();
                } else {
                    Notification notification = new Notification(
                            "Erro ao salvar. Verifique se todos os dados foram preenchidos.", 3000);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();
                }
            }
        });
        Button cancelarButton = new Button("Fechar", event -> dialog.close());

        cancelarButton.getStyle()
            .set("background-color", "#FF0000")
            .set("color", "#FFFFFF")
            .set("border-radius", "10px")
            .set("box-shadow", "0 4px 8px rgba(0, 0, 0, 0.2)")
            .set("cursor", "pointer");

        confirmarButton.getStyle()
            .set("background-color", "#228B22")
            .set("color", "#FFFFFF")
            .set("border-radius", "10px")
            .set("box-shadow", "0 4px 8px rgba(0, 0, 0, 0.2)")
            .set("cursor", "pointer");

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelarButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonLayout.setPadding(false);
        buttonLayout.setSpacing(true);

        formLayout.add(nomeField, confirmarButton);

        VerticalLayout dialogLayout = new VerticalLayout(formLayout, grid, buttonLayout);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private Grid<Peca> createGrid() {
        grid = new Grid<>(Peca.class, false);
        // Formatador para moeda em Real
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        grid.addColumn(Peca::getDescricao).setHeader("Descrição").setSortable(true);

        grid.addColumn(peca -> currencyFormat.format(peca.getPreco()))
        .setHeader("Valor")
        .setSortable(true);

        grid.addColumn(peca -> peca.getMarca().getId()).setHeader("Marca").setSortable(true);

        grid.addComponentColumn(peca -> {
            Button deleteButton = new Button(VaadinIcon.TRASH.create(), e -> deletePeca(peca));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            return deleteButton;
        }).setHeader("Ações");

        grid.addItemDoubleClickListener(e -> editPeca(e.getItem()));

        grid.setItems(controller2.getAll());
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        return grid;
    }

    private void saveOrUpdatePeca() {
        String descricao = textField.getValue();
        double preco;
        Marca marcaSelecionada = comboBox.getValue();
        try {
            preco = Double.parseDouble(textField2.getValue());
        } catch (NumberFormatException e) {
            Notification.show("Valor inválido.");
            return;
        }
        if (marcaSelecionada == null) {
            Notification.show("Você deve selecionar uma marca.");
            return;
        }

        int idMarca = marcaSelecionada.getId();

        try {
            preco = Double.parseDouble(textField2.getValue());
        } catch (NumberFormatException e) {
            Notification.show("Valor inválido.");
            return;
        }

        if (descricao == null || descricao.isEmpty()) {
            Notification.show("Descrição não pode estar vazia.");
            return;
        }

        Peca peca = new Peca();
        peca.setDescricao(descricao);
        peca.setPreco(preco);
        peca.setMarca(marcaSelecionada);

        boolean success;
        if (pecaId != null && pecaId > 0) {
            peca.setId(pecaId);
            success = controller2.update(peca);
            if (success) {
                Notification.show("Peça atualizada com sucesso!");
            } else {
                Notification.show("Falha ao atualizar o peça.");
            }
        } else {
            success = controller2.savePeca(peca);
            if (success) {
                Notification.show("Peça salva com sucesso!");
            } else {
                Notification.show("Falha ao salvar a peça.");
            }
        }

        if (success) {
            clearForm();
            refreshGrid();
        }
    }

    private void deletePeca(Peca peca) {
        boolean success = controller2.delete(peca);
        if (success) {
            refreshGrid();
        } else {
            System.out.println("Erro ao excluir peca.");
        }
    }

    private void editPeca(Peca peca) {
        pecaId = peca.getId(); // Armazena o ID do serviço em edição
        textField.setValue(peca.getDescricao());
        textField2.setValue(String.valueOf(peca.getPreco()));
        comboBox.setValue(peca.getMarca());
    }

    private void refreshGrid() {
        List<Peca> pecas = controller2.getAll();
        grid.setItems(pecas);
    }

    private void clearForm() {
        pecaId = null; // Reseta o ID do serviço em edição
        textField.clear();
        textField2.clear();
        comboBox.clear();
    }

    private void setComboBoxData(ComboBox<Marca> comboBox) {
        List<Marca> marcas = controller.pesquisarTodos();
        comboBox.setItems(marcas);
        comboBox.setItemLabelGenerator(marca -> marca.getNome());
    }

    private void searchPeca() {
        String searchTerm = textField3.getValue();
        List<Peca> pecas;

        if (searchTerm == null || searchTerm.isEmpty()) {
            pecas = controller2.getAll();
        } else {
            pecas = controller2.search(searchTerm);
        }

        grid.setItems(pecas);
    }
}
