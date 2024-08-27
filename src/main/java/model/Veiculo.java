package model;
public class Veiculo {
    private int id;
    private String descricao_veiculo;
    private String placa;
    private String ano_modelo;
    private int id_cliente;
    private byte[] imagem; // Novo atributo para imagem

    public Veiculo() {}

    public Veiculo(int id, String descricao_veiculo, String placa, String ano_modelo, int id_cliente, byte[] imagem) {
        this.id = id;
        this.descricao_veiculo = descricao_veiculo;
        this.placa = placa;
        this.ano_modelo = ano_modelo;
        this.id_cliente = id_cliente;
        this.imagem = imagem;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricaoVeiculo() {
        return descricao_veiculo;
    }

    public void setDescricaoVeiculo(String descricao_veiculo) {
        this.descricao_veiculo = descricao_veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getAnoModelo() {
        return ano_modelo;
    }

    public void setAnoModelo(String ano_modelo) {
        this.ano_modelo = ano_modelo;
    }

    public int getIdCliente() {
        return id_cliente;
    }

    public void setIdCliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
