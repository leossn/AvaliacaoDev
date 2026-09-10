package br.com.soc.sistema.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.soc.sistema.business.AgendaBusiness;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.AgendaFilter;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.infra.OpcoesComboBuscarAgenda;
import br.com.soc.sistema.infra.OpcoesPeriodo;
import br.com.soc.sistema.vo.AgendaVo;

public class AgendaAction extends Action {
	private static final long serialVersionUID = 1L;
	private List<AgendaVo> agendas = new ArrayList<>();
	private AgendaBusiness business = new AgendaBusiness();
	private AgendaVo agendaVo = new AgendaVo();
	private boolean exibirModalVinculo = false;

	public String todos() {
		agendas.addAll(business.trazerTodasAsAgendas());
		return SUCCESS;
	}

	public String novo() {
		if (agendaVo.getNome() == null)
			return INPUT;

		if (agendaVo.getRowid() == null || agendaVo.getRowid().isEmpty()) {
			business.salvarAgenda(agendaVo);
		} else {
			business.atualizarAgenda(agendaVo);
		}

		return REDIRECT;
	}

	public String editar() {
		if (agendaVo.getRowid() == null)
			return REDIRECT;

		agendaVo = business.buscarAgendaPor(agendaVo.getRowid());
		return INPUT;
	}

	public String excluir() {
	    if (agendaVo.getRowid() == null)
	        return REDIRECT;

	    try {
	        business.excluirAgenda(agendaVo.getRowid());
	    } catch (BusinessException e) {
	        addActionError(e.getMessage());
	        
	        AgendaBusiness agendaBusiness = new AgendaBusiness();
	        agendas = agendaBusiness.trazerTodasAsAgendas();
	        
	        exibirModalVinculo = true;
	        return SUCCESS; 
	    }
	    
	    return REDIRECT;
	}

	public boolean isExibirModalVinculo() {
	    return exibirModalVinculo;
	}

	public void setExibirModalVinculo(boolean exibirModalVinculo) {
	    this.exibirModalVinculo = exibirModalVinculo;
	}

	public List<OpcoesPeriodo> getListaOpcoesPeriodo() {
		return Arrays.asList(OpcoesPeriodo.values());
	}

	public List<AgendaVo> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<AgendaVo> agendas) {
		this.agendas = agendas;
	}

	public AgendaVo getAgendaVo() {
		return agendaVo;
	}

	public void setAgendaVo(AgendaVo agendaVo) {
		this.agendaVo = agendaVo;
	}

	private List<AgendaVo> listaRelatorio;

	public String relatorio() {
		AgendaBusiness business = new AgendaBusiness();
		this.listaRelatorio = business.listarAgendasParaRelatorio();
		return "success";
	}

	public List<AgendaVo> getListaRelatorio() {
		return listaRelatorio;
	}

	public void setListaRelatorio(List<AgendaVo> listaRelatorio) {
		this.listaRelatorio = listaRelatorio;
	}

	private AgendaFilter filtrar = new AgendaFilter();

	public String filtrar() {
		if (filtrar.isNullOpcoesCombo())
			return REDIRECT;

		agendas = business.filtrarAgendas(filtrar);
		return SUCCESS;
	}

	public List<OpcoesComboBuscarAgenda> getListaOpcoesComboAgenda() {
		return Arrays.asList(OpcoesComboBuscarAgenda.values());
	}

	public AgendaFilter getFiltrar() {
		return filtrar;
	}

	public void setFiltrar(AgendaFilter filtrar) {
		this.filtrar = filtrar;
	}
}