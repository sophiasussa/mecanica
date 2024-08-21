package com.example.application.views.serviços;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

@PageTitle("Serviços")
@Route(value = "my-view4", layout = MainLayout.class)
public class ServiçosView extends Composite<VerticalLayout> {

    public ServiçosView() {
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Button buttonPrimary = new Button();
        Hr hr = new Hr();
        HorizontalLayout layoutRow = new HorizontalLayout();
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
        textField2.setPlaceholder("Valor");
        textField2.addClassName("rounded-text-field");
        textField2.setWidth("min-content");
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
        textField3.setPlaceholder("Pesquisar");
        textField3.addClassName("rounded-text-field");
        textField3.setWidth("min-content");
        buttonPrimary2.setIcon(VaadinIcon.SEARCH.create());
        buttonPrimary2.getStyle().set("border-radius", "50%");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        hr.getStyle().set("box-shadow", "0 1px 4px rgba(0, 0, 0, 0.2)");
        hr2.getStyle().set("box-shadow", "0 -1px 4px rgba(0, 0, 0, 0.2)");
        formLayout2Col.add(textField);
        formLayout2Col.add(textField2);
        layout.add(formLayout2Col);
        getContent().add(layout);
        getContent().add(layoutColumn2);
        layoutColumn2.add(buttonPrimary);
        layoutColumn2.add(hr);
        layoutColumn2.add(layoutRow);
        layoutRow.add(textField3);
        layoutRow.add(buttonPrimary2);
        layoutColumn2.add(hr2);
    }
}
