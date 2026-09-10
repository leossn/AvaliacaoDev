package br.com.soc.sistema.filter;

import br.com.soc.sistema.infra.OpcoesComboBuscar;

public class FuncionarioFilter {
	private OpcoesComboBuscar opcoesCombo;
	private String valorBusca;

	public String getValorBusca() {
		return valorBusca;
	}

	public FuncionarioFilter setValorBusca(String valorBusca) {
		this.valorBusca = valorBusca;
		return this;
	}

	public OpcoesComboBuscar getOpcoesCombo() {
		return opcoesCombo;
	}

	public void setOpcoesCombo(String codigo) {
		if (codigo != null && !codigo.trim().isEmpty()) {
			try {
				this.opcoesCombo = OpcoesComboBuscar.buscarPor(codigo);
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
	
	public static FuncionarioFilter builder() {
		return new FuncionarioFilter();
	}
}
