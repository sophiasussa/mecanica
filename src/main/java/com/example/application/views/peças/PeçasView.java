package com.example.application.views.peças;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Peças")
@Route(value = "my-view3", layout = MainLayout.class)
public class PeçasView extends Composite<VerticalLayout> {

    public PeçasView() {
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        HorizontalLayout layoutRow = new HorizontalLayout();
        ComboBox comboBox = new ComboBox();
        Button buttonTertiary = new Button();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Hr hr = new Hr();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        TextField textField3 = new TextField();
        Button buttonPrimary2 = new Button();
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
        setComboBoxSampleData(comboBox);
        buttonTertiary.setText("+ Marca");
        buttonTertiary.setWidth("min-content");
        buttonTertiary.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        //buttonTertiary.addClickListener(event -> openDialog());
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
        layoutRow3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.setHeight("70px");
        layoutRow3.setAlignItems(Alignment.END);
        layoutRow3.setJustifyContentMode(JustifyContentMode.END);
        textField3.setPlaceholder("Pesquisar");
        textField3.setWidth("min-content");
        textField3.addClassName("rounded-text-field");
        buttonPrimary2.setIcon(VaadinIcon.SEARCH.create());
        buttonPrimary2.getStyle().set("border-radius", "50%");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
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

    }

/*  private void openDialog() {
        Dialog dialog = new Dialog();
        dialog.setWidth("800px"); 
        dialog.setHeight("600px");

        FormLayout formLayout = new FormLayout();
        TextField nomeField = new TextField("Nome");

        Grid<Responsavel> grid = new Grid<>(Responsavel.class);
        grid.setColumns("nome");

        List<Responsavel> responsaveis = controller2.pesquisarTodos();
        grid.setItems(responsaveis);

        Editor<Responsavel> editor = grid.getEditor();
        Binder<Responsavel> binder = new Binder<>(Responsavel.class);
        editor.setBinder(binder);

        TextField nomeEditor = new TextField();
        binder.forField(nomeEditor).bind(Responsavel::getNome, Responsavel::setNome);
        grid.getColumnByKey("nome").setEditorComponent(nomeEditor);

        grid.addItemDoubleClickListener(event -> editor.editItem(event.getItem()));
        editor.addCloseListener(event -> grid.getDataProvider().refreshItem(event.getItem()));
        
        grid.addComponentColumn(responsavel -> {
            Button alterarButton = new Button("Alterar", new Icon(VaadinIcon.COG));
            alterarButton.addClickListener(e -> {
                if (editor.isOpen()) {
                    editor.save();
                    Responsavel editedResponsavel = editor.getItem();
                    if (controller2.alterar(editedResponsavel)) {
                        Notification notification = new Notification(
                                "Responsavel atualizado com sucesso.", 3000);
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        notification.setPosition(Notification.Position.MIDDLE);
                        notification.open();
                        
                        responsaveis.clear();
                        responsaveis.addAll(controller2.pesquisarTodos());
                        grid.getDataProvider().refreshAll();
                    } else {
                        Notification notification = new Notification(
                                "Erro ao atualizar. Verifique se todos os dados foram preenchidos.", 3000);
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        notification.setPosition(Notification.Position.MIDDLE);
                        notification.open();
                    }
                } else {
                    editor.editItem(responsavel);
                    nomeEditor.focus();
                }
            });
            return alterarButton;
        }).setHeader("Alterar");
        
        editor.addSaveListener(event -> {
            grid.getDataProvider().refreshItem(event.getItem());
        });

        grid.addComponentColumn(responsavel -> {
            Button deletarButton = new Button(new Icon(VaadinIcon.TRASH));
            deletarButton.addClickListener(e -> {
                if (controller2.excluir(responsavel)) {
                    Notification notification = new Notification(
                            "Responsavel deletado com sucesso.", 3000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();
                    
                    responsaveis.clear();
                    responsaveis.addAll(controller2.pesquisarTodos());
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
                Responsavel responsavel = new Responsavel();
                responsavel.setNome(nomeField.getValue());
                if (controller2.inserir(responsavel) == true) {
                    Notification notification = new Notification(
                            "Responsavel salvo com sucesso.", 3000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();

                    nomeField.clear();
                    responsaveis.clear();
                    responsaveis.addAll(controller2.pesquisarTodos());
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
    } */

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setComboBoxSampleData(ComboBox comboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        comboBox.setItems(sampleItems);
        comboBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }
}
