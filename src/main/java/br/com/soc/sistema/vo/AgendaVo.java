package br.com.soc.sistema.vo;

import br.com.soc.sistema.infra.OpcoesPeriodo;

public class AgendaVo {
    private String rowid;
    private String nome;
    private String periodo;
    
    public AgendaVo() {}
    
    public AgendaVo(String rowid, String nome, String periodo) {
        this.rowid = rowid;
        this.nome = nome;
        this.periodo = periodo;
    }

    public String getRowid() {
        return rowid;
    }
    public void setRowid(String rowid) {
        this.rowid = rowid;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getPeriodo() {
        return periodo;
    }
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    public String getDescricaoPeriodo() {
        if (this.periodo == null || this.periodo.isEmpty()) return "";
        try {
            return OpcoesPeriodo.buscarPor(this.periodo).getDescricao();
        } catch (Exception e) {
            return this.periodo;
        }
    }
    
    @Override
    public String toString() {
        return "AgendaVo [rowid=" + rowid + ", nome=" + nome + ", periodo=" + periodo + "]";
    }
}