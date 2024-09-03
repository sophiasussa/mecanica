package com.example.application.views.os;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
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

import controller.ClienteController;
import controller.MecanicoController;
import controller.PecaController;
import controller.ServicosController;
import controller.VeiculosController;
import model.Peca;
import model.Servicos;
import model.Veiculo;
import model.Cliente;
import model.Marca;
import model.Mecanico;
import model.OrdemServico;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Ordem de Serviço")
@Route(value = "my-view5", layout = MainLayout.class)
public class OSView extends Composite<VerticalLayout> {

    ClienteController clienteController = new ClienteController();
    MecanicoController mecanicoController = new MecanicoController();
    VeiculosController veiculosController = new VeiculosController();
    PecaController pecaController = new PecaController();
    ServicosController servicosController = new ServicosController();

    private ComboBox<Cliente> comboBox = new ComboBox<>("Cliente");
    private ComboBox<Mecanico> comboBox2 = new ComboBox<>("Mecanico");
    private ComboBox<Veiculo> comboBox3 = new ComboBox<>("Veiculo");
    private MultiSelectComboBox<Peca> comboBox4 = new MultiSelectComboBox<>(
        "Pecas");
    private MultiSelectComboBox<Servicos> comboBox5 = new MultiSelectComboBox<>(
        "Servicos");
    private TextField textField;
    private TextField textField2;
    private DatePicker datePicker;
    private DatePicker datePicker2;
    private Grid<OrdemServico> grid;

    public OSView() {
        FormLayout formLayout3Col = new FormLayout();
        comboBox = new ComboBox();
        comboBox2 = new ComboBox();
        comboBox3 = new ComboBox();
        comboBox4 = new MultiSelectComboBox();
        comboBox5 = new MultiSelectComboBox();
        textField = new TextField();
        textField2 = new TextField();
        datePicker = new DatePicker();
        datePicker2 = new DatePicker();
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
        formLayout3Col.setWidth("100%");
        formLayout3Col.setResponsiveSteps(new ResponsiveStep("0", 1), new ResponsiveStep("250px", 2),
                new ResponsiveStep("500px", 3));
        comboBox.setPlaceholder("Cliente");
        comboBox.setWidth("min-content");
        comboBox.addClassName("rounded-text-field");
        setComboBoxClienteData(comboBox);
        comboBox2.setPlaceholder("Mecanico");
        comboBox2.setWidth("min-content");
        comboBox2.addClassName("rounded-text-field");
        setComboBoxMecanicoData(comboBox2);
        comboBox3.setPlaceholder("Veiculo");
        comboBox3.setWidth("min-content");
        comboBox3.addClassName("rounded-text-field");
        setComboBoxVeiculoData(comboBox3);
        comboBox4.setPlaceholder("Peças");
        comboBox4.setWidth("min-content");
        comboBox4.addClassName("rounded-text-field");
        setComboBoxPecaData(comboBox4);
        comboBox5.setPlaceholder("Serviços");
        comboBox5.setWidth("min-content");
        comboBox5.addClassName("rounded-text-field");
        setComboBoxServicoData(comboBox5);
        textField.setPlaceholder("Número da OS");
        textField.addClassName("rounded-text-field");
        textField.setWidth("min-content");
        textField2.setPlaceholder("Valor Total");
        textField2.setWidth("min-content");
        textField2.addClassName("rounded-text-field");
        datePicker.setPlaceholder("Data de Abertura");
        datePicker.setWidth("min-content");
        datePicker.addClassName("rounded-text-field");
        datePicker2.setPlaceholder("Data de Encerramento");
        datePicker2.setWidth("min-content");
        datePicker2.addClassName("rounded-text-field");
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
        hr.getStyle().set("box-shadow", "0 1px 4px rgba(0, 0, 0, 0.2)");
        hr2.getStyle().set("box-shadow", "0 -1px 4px rgba(0, 0, 0, 0.2)");
        buttonPrimary2.setIcon(VaadinIcon.SEARCH.create());
        buttonPrimary2.getStyle().set("border-radius", "50%");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        formLayout3Col.add(comboBox);
        formLayout3Col.add(comboBox2);
        formLayout3Col.add(comboBox3);
        formLayout3Col.add(comboBox4);
        formLayout3Col.add(comboBox5);
        formLayout3Col.add(textField);
        formLayout3Col.add(textField2);
        formLayout3Col.add(datePicker);
        formLayout3Col.add(datePicker2);
        layout.add(formLayout3Col);
        getContent().add(layout);
        getContent().add(layoutColumn2);
        layoutColumn2.add(buttonPrimary);
        layoutColumn2.add(hr);
        layoutColumn2.add(layoutRow);
        layoutRow.add(textField3);
        layoutRow.add(buttonPrimary2);
        layoutColumn2.add(hr2);

  //      grid = createGrid();
    //    getContent().add(grid);
    }

    private void setComboBoxClienteData(ComboBox<Cliente> comboBox) {
        List<Cliente> clientes = clienteController.getAllClientes();
        comboBox.setItems(clientes);
        comboBox.setItemLabelGenerator(cliente -> cliente.getNome());
    }

    private void setComboBoxMecanicoData(ComboBox<Mecanico> comboBox2) {
        List<Mecanico> mecanicos = mecanicoController.getAllMecanicos();
        comboBox2.setItems(mecanicos);
        comboBox2.setItemLabelGenerator(mecanico -> mecanico.getNome());
    }

    private void setComboBoxVeiculoData(ComboBox<Veiculo> comboBox3) {
        List<Veiculo> veiculos = veiculosController.getAllVeiculos();
        comboBox3.setItems(veiculos);
        comboBox3.setItemLabelGenerator(veiculo -> veiculo.getDescricaoVeiculo());
    }

    private void setComboBoxPecaData(MultiSelectComboBox<Peca> comboBox4) {
        List<Peca> pecas = pecaController.getAll();
        comboBox4.setItems(pecas);
        comboBox4.setItemLabelGenerator(peca -> peca.getDescricao());
    }

    private void setComboBoxServicoData(MultiSelectComboBox<Servicos> comboBox5) {
        List<Servicos> servicos = servicosController.getAllServicos();
        comboBox5.setItems(servicos);
        comboBox5.setItemLabelGenerator(servico -> servico.getDescricaoServico());
    }
}
