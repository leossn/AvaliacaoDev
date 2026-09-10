package br.com.soc.sistema.business;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.AgendaDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.AgendaFilter;
import br.com.soc.sistema.vo.AgendaVo;

public class AgendaBusiness {

	private AgendaDao dao;

	public AgendaBusiness() {
		this.dao = new AgendaDao();
	}

	public List<AgendaVo> trazerTodasAsAgendas() {
		return dao.findAllAgendas();
	}

	public void salvarAgenda(AgendaVo agendaVo) {
		try {
			if (agendaVo.getNome() == null || agendaVo.getNome().isEmpty())
				throw new IllegalArgumentException("Nome da agenda nao pode ser em branco");

			if (agendaVo.getPeriodo() == null || agendaVo.getPeriodo().isEmpty())
				throw new IllegalArgumentException("Periodo da agenda deve ser informado");

			dao.insertAgenda(agendaVo);
		} catch (Exception e) {
			throw new BusinessException("Não foi possível realizar a inclusão da agenda");
		}
	}

	public void atualizarAgenda(AgendaVo agendaVo) {
		try {
			if (agendaVo.getNome() == null || agendaVo.getNome().isEmpty())
				throw new IllegalArgumentException("Nome da agenda nao pode ser em branco");

			dao.updateAgenda(agendaVo);
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a alteracao da agenda");
		}
	}

	public AgendaVo buscarAgendaPor(String codigo) {
		try {
			Integer cod = Integer.parseInt(codigo);
			return dao.findByCodigo(cod);
		} catch (NumberFormatException e) {
			throw new BusinessException("Foi informado um caracter no lugar de um numero");
		}
	}

	public void excluirAgenda(String codigo) {
	    try {
	        Integer cod = Integer.parseInt(codigo);
	        
	        AgendaDao agendaDao = new AgendaDao();
	        boolean possuiCompromissos = agendaDao.hasCompromissos(cod);
	        
	        if (possuiCompromissos) {
	            throw new BusinessException("Não é possível excluir esta agenda pois existem compromissos vinculados a ela.");
	        }
	        
	        agendaDao.deleteAgenda(cod);
	    } catch (NumberFormatException e) {
	        throw new BusinessException("Foi informado um caracter no lugar de um numero");
	    } catch (BusinessException e) {
	        throw e; 
	    } catch (Exception e) {
	        throw new BusinessException("Não foi possível realizar a exclusão da agenda");
	    }
	}

	public List<AgendaVo> listarAgendasParaRelatorio() {
		AgendaDao dao = new AgendaDao();
		return dao.listarParaRelatorio();
	}

	public List<AgendaVo> filtrarAgendas(AgendaFilter filter) {
		List<AgendaVo> agendas = new ArrayList<>();

		if (filter == null || filter.getOpcoesCombo() == null) {
			return trazerTodasAsAgendas();
		}

		switch (filter.getOpcoesCombo()) {
			case ID:
				try {
					Integer codigo = Integer.parseInt(filter.getValorBusca());
					AgendaVo vo = dao.findByCodigo(codigo);
					if (vo != null)
						agendas.add(vo);
				} catch (NumberFormatException e) {
					throw new BusinessException("Foi informado um caracter no lugar de um numero");
				}
				break;

			case NOME:
				if (filter.getValorBusca() != null) {
					agendas.addAll(dao.findAllByNome(filter.getValorBusca()));
				}
				break;

			case PERIODO:
				try {
					String termoBusca = filter.getValorBusca() != null ? filter.getValorBusca().trim().toLowerCase() : "";
					String codigoBusca = termoBusca;
					
					if (termoBusca.contains("manh") || termoBusca.equals("1")) {
						codigoBusca = "1";
					} else if (termoBusca.contains("tard") || termoBusca.equals("2")) {
						codigoBusca = "2";
					} else if (termoBusca.contains("amb") || termoBusca.equals("3")) {
						codigoBusca = "3";
					}
					
					agendas.addAll(dao.findAllByPeriodo(codigoBusca));
				} catch (Exception e) {
					throw new BusinessException("Erro ao filtrar por período");
				}
				break;
		}

		return agendas;
	}
}