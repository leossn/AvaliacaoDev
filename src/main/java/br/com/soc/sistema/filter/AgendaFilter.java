package br.com.soc.sistema.filter;

import br.com.soc.sistema.infra.OpcoesComboBuscarAgenda;

public class AgendaFilter {
    private OpcoesComboBuscarAgenda opcoesCombo;
    private String valorBusca;

    public String getValorBusca() {
        return valorBusca;
    }

    public AgendaFilter setValorBusca(String valorBusca) {
        this.valorBusca = valorBusca;
        return this;
    }

    public OpcoesComboBuscarAgenda getOpcoesCombo() {
        return opcoesCombo;
    }

    public void setOpcoesCombo(String codigo) {
        if (codigo != null && !codigo.trim().isEmpty()) {
            try {
                this.opcoesCombo = OpcoesComboBuscarAgenda.buscarPor(codigo);
            } catch (Exception e) {
                this.opcoesCombo = null;
            }
        } else {
            this.opcoesCombo = null;
        }
    }    
    
    public boolean isNullOpcoesCombo() {
        return this.getOpcoesCombo() == null;
    }
}