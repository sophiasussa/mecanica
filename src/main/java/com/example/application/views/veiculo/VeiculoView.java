package com.example.application.views.veiculo;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import model.Cliente;
import model.Veiculo;
import controller.ClienteController;
import controller.VeiculosController;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

@PageTitle("Veículo")
@Route(value = "my-view2", layout = MainLayout.class)
public class VeiculoView extends Composite<VerticalLayout> {

    private VeiculosController veiculosController;
    private ClienteController clienteController;
    private TextField descricaoField;
    private TextField placaField;
    private TextField anoField;
    private ComboBox<Cliente> clienteComboBox;
    private Grid<Veiculo> grid;
    private TextField searchField;
    private Button searchButton;
    private Integer veiculoId;
    private String image;

    public VeiculoView() {
        veiculosController = new VeiculosController();
        clienteController = new ClienteController();

        // Layout e campos do formulário
        FormLayout formLayout = new FormLayout();
        descricaoField = new TextField("Descrição");
        placaField = new TextField("Placa");
        anoField = new TextField("Ano");

        // Configuração de largura dos campos
        descricaoField.setWidthFull();
        placaField.setWidthFull();
        anoField.setWidthFull();

        // Dropdown para Clientes
        clienteComboBox = new ComboBox<>("Cliente");
        List<Cliente> clientes = veiculosController.getAllClientes();
        if (clientes != null) {
            clienteComboBox.setItems(clientes);
            clienteComboBox.setItemLabelGenerator(Cliente::getNome);
        } else {
            Notification.show("Erro ao carregar clientes.", 3000, Notification.Position.MIDDLE);
        }

        // Upload de imagem
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.setMaxFiles(1);
        upload.setMaxFileSize(5 * 1024 * 1024); // Limite de 5MB

        upload.addSucceededListener(event -> {
            try {
                String fileName = event.getFileName();
                String uploadDir = "C:/Users/jorda/Downloads/imagensMecanica/"; // Diretório de upload
                File targetFile = new File(uploadDir + fileName);

                try (InputStream inputStream = buffer.getInputStream()) {
                    Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                image = targetFile.getAbsolutePath(); // Caminho absoluto da imagem
                Notification.show("Imagem carregada com sucesso!", 3000, Notification.Position.MIDDLE);
            } catch (IOException e) {
                e.printStackTrace();
                Notification.show("Falha ao fazer upload da imagem: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        // Configuração do layout do formulário
        formLayout.setResponsiveSteps(
            new ResponsiveStep("0", 1),
            new ResponsiveStep("250px", 2),
            new ResponsiveStep("500px", 3)
        );
        formLayout.add(descricaoField, placaField, anoField, clienteComboBox, upload);

        // Botão Salvar
        Button saveButton = new Button("Salvar");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(e -> saveOrUpdateVeiculo());

        // Botão de pesquisa
        searchField = new TextField();
        searchButton = new Button(VaadinIcon.SEARCH.create());
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.addClickListener(e -> searchVeiculos());

        // Layout para os botões
        VerticalLayout buttonLayout = new VerticalLayout(saveButton, new Hr(), new HorizontalLayout(searchField, searchButton), new Hr());
        buttonLayout.setAlignItems(Alignment.END);
        buttonLayout.setWidthFull();

        // Grid para listar os veículos
        grid = createGrid();

        // Adicionando os componentes ao layout principal
        getContent().add(formLayout, buttonLayout, grid);
    }

    private Grid<Veiculo> createGrid() {
        grid = new Grid<>(Veiculo.class, false);
        grid.addColumn(Veiculo::getDescricaoVeiculo).setHeader("Descrição").setSortable(true);
        grid.addColumn(Veiculo::getPlaca).setHeader("Placa").setSortable(true);
        grid.addColumn(Veiculo::getAnoModelo).setHeader("Ano").setSortable(true);
        grid.addColumn(veiculo -> veiculo.getIdCliente()).setHeader("Cliente").setSortable(true);

        // Adicionando coluna de ações (excluir)
        grid.addComponentColumn(veiculo -> {
            Button deleteButton = new Button(VaadinIcon.TRASH.create(), e -> deleteVeiculo(veiculo));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            return deleteButton;
        }).setHeader("Ações");

        grid.addItemDoubleClickListener(e -> editVeiculo(e.getItem()));

        grid.setItems(veiculosController.getAllVeiculos());
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        return grid;
    }

    private void saveOrUpdateVeiculo() {
        String descricao = descricaoField.getValue();
        String placa = placaField.getValue();
        String ano = anoField.getValue();
        Cliente cliente = clienteComboBox.getValue();

        if (descricao == null || descricao.isEmpty()) {
            Notification.show("Descrição não pode estar vazia.");
            return;
        }
        if (placa == null || placa.isEmpty()) {
            Notification.show("Placa não pode estar vazia.");
            return;
        }
        if (ano == null || ano.isEmpty()) {
            Notification.show("Ano não pode estar vazio.");
            return;
        }
        if (cliente == null) {
            Notification.show("Selecione um cliente.");
            return;
        }

        Veiculo veiculo = new Veiculo();
        veiculo.setDescricaoVeiculo(descricao);
        veiculo.setPlaca(placa);
        veiculo.setAnoModelo(ano);
        veiculo.setIdCliente(cliente.getId());

        if (image != null) {
            veiculo.setImagem(image); // Salva o caminho da imagem
        }

        boolean success;
        if (veiculoId != null && veiculoId > 0) {
            veiculo.setId(veiculoId);
            success = veiculosController.updateVeiculo(veiculo);
            if (success) {
                Notification.show("Veículo atualizado com sucesso!");
            } else {
                Notification.show("Falha ao atualizar o veículo.");
            }
        } else {
            success = veiculosController.saveVeiculo(veiculo);
            if (success) {
                Notification.show("Veículo salvo com sucesso!");
            } else {
                Notification.show("Falha ao salvar o veículo.");
            }
        }

        if (success) {
            clearForm();
            refreshGrid();
        }
    }

    private void searchVeiculos() {
        String searchTerm = searchField.getValue();
        List<Veiculo> veiculos;

        if (searchTerm == null || searchTerm.isEmpty()) {
            veiculos = veiculosController.getAllVeiculos();
        } else {
            veiculos = veiculosController.searchVeiculos(searchTerm);
        }

        grid.setItems(veiculos);
    }

    private void deleteVeiculo(Veiculo veiculo) {
        boolean success = veiculosController.deleteVeiculo(veiculo);
        if (success) {
            refreshGrid();
        } else {
            System.out.println("Erro ao excluir veículo.");
        }
    }

    private void editVeiculo(Veiculo veiculo) {
        veiculoId = veiculo.getId(); // Armazena o ID do veículo em edição
        descricaoField.setValue(veiculo.getDescricaoVeiculo());
        placaField.setValue(veiculo.getPlaca());
        anoField.setValue(veiculo.getAnoModelo());
        clienteComboBox.setValue(clienteController.getClienteById(veiculo.getIdCliente()));
    
        // Recupera o caminho da imagem associada ao veículo
        image = veiculo.getImagem();
    
        if (image != null && !image.isEmpty()) {
            Notification.show("Imagem atual: " + image, 3000, Notification.Position.MIDDLE);
            // Opcionalmente, você pode exibir a imagem ou fornecer uma maneira de visualizar ou alterar a imagem
        } else {
            Notification.show("Nenhuma imagem associada.", 3000, Notification.Position.MIDDLE);
        }
    }
    
    private void refreshGrid() {
        grid.setItems(veiculosController.getAllVeiculos());
    }

    private void clearForm() {
        descricaoField.clear();
        placaField.clear();
        anoField.clear();
        clienteComboBox.clear();
        veiculoId = null;
        image = null;
    }
}
