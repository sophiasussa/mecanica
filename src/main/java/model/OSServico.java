package model;

public class OSServico {
    private int id;
    private OrdemServico ordemServico;
    private Servicos servico;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public OrdemServico getOrdemServico() {
        return ordemServico;
    }
    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }
    public Servicos getServico() {
        return servico;
    }
    public void setServico(Servicos servico) {
        this.servico = servico;
    }
    
}
