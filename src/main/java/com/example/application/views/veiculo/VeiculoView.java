package com.example.application.views.veiculo;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import java.io.IOException;  // Add this import

@PageTitle("Veiculo")
@Route(value = "my-view2", layout = MainLayout.class)
public class VeiculoView extends Composite<VerticalLayout> {

    public VeiculoView() {
        FormLayout formLayout3Col = new FormLayout();
        TextField descricaoField = new TextField();
        TextField placaField = new TextField();
        TextField anoField = new TextField();

        // Dropdown for Clients
        ComboBox<String> clienteComboBox = new ComboBox<>("Cliente");
        clienteComboBox.setItems("Cliente 1", "Cliente 2", "Cliente 3"); // Example items

        // Drag-and-drop upload for image
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.setMaxFiles(1); // Limit to one file
        upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB

        upload.addSucceededListener(event -> {
            try {
                byte[] imageBytes = buffer.getInputStream().readAllBytes();
                // Here you can handle the imageBytes, e.g., set it to a Veiculo object
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception or log it appropriately
                Notification.show("Failed to upload image: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });


        VerticalLayout layoutColumn2 = new VerticalLayout();
        Button buttonPrimary = new Button();
        Hr hr = new Hr();
        HorizontalLayout layoutRow = new HorizontalLayout();
        TextField searchField = new TextField();
        Button searchButton = new Button();
        Hr hr2 = new Hr();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        VerticalLayout layout = new VerticalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        formLayout3Col.setWidth("100%");
        formLayout3Col.setResponsiveSteps(new ResponsiveStep("0", 1), new ResponsiveStep("250px", 2),
                new ResponsiveStep("500px", 3));
        descricaoField.setPlaceholder("Descrição");
        descricaoField.addClassName("rounded-text-field");
        descricaoField.setWidth("min-content");
        placaField.setPlaceholder("Placa");
        placaField.addClassName("rounded-text-field");
        placaField.setWidth("min-content");
        anoField.setPlaceholder("Ano");
        anoField.setWidth("min-content");
        anoField.addClassName("rounded-text-field");

        // Add fields to layout
        formLayout3Col.add(descricaoField, placaField, anoField);
        formLayout3Col.add(clienteComboBox, upload); // Add dropdown and upload to the form layout

        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.START);
        layoutColumn2.setAlignItems(Alignment.END);
        buttonPrimary.setText("Salvar");
        buttonPrimary.getStyle().set("border-radius", "25px");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("70px");
        layoutRow.setAlignItems(Alignment.END);
        layoutRow.setJustifyContentMode(JustifyContentMode.END);
        searchField.setPlaceholder("Pesquisar");
        searchField.addClassName("rounded-text-field");
        searchField.setWidth("min-content");
        searchButton.setIcon(VaadinIcon.SEARCH.create());
        searchButton.getStyle().set("border-radius", "50%");
        searchButton.setWidth("min-content");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutColumn3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        hr.getStyle().set("box-shadow", "0 1px 4px rgba(0, 0, 0, 0.2)");
        hr2.getStyle().set("box-shadow", "0 -1px 4px rgba(0, 0, 0, 0.2)");
        layout.add(formLayout3Col);
        getContent().add(layout);
        getContent().add(layoutColumn2);
        layoutColumn2.add(buttonPrimary);
        layoutColumn2.add(hr);
        layoutColumn2.add(layoutRow);
        layoutRow.add(searchField);
        layoutRow.add(searchButton);
        layoutColumn2.add(hr2);
        layoutColumn2.add(layoutColumn3);
    }
}
