package br.com.soc.sistema.business;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.AgendaDao;
import br.com.soc.sistema.dao.CompromissoDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;

public class CompromissoBusiness {

    private CompromissoDao dao;
    private AgendaDao agendaDao; 

    public CompromissoBusiness() {
        this.dao = new CompromissoDao();
        this.agendaDao = new AgendaDao();
    }

    public List<CompromissoVo> trazerTodosOsCompromissos() {
        return dao.findAllCompromissos();
    }

    public void salvarCompromisso(CompromissoVo vo) {
        if (vo.getCodigoFuncionario() == null || vo.getCodigoFuncionario().isEmpty()
                || vo.getCodigoAgenda() == null || vo.getCodigoAgenda().isEmpty()
                || vo.getData() == null || vo.getData().isEmpty()
                || vo.getHorario() == null || vo.getHorario().isEmpty()) {
            throw new BusinessException("Todos os campos do compromisso são obrigatórios.");
        }

        AgendaVo agenda = agendaDao.findByCodigoStr(vo.getCodigoAgenda());
        if (agenda == null) {
            throw new BusinessException("A agenda selecionada não foi encontrada.");
        }

        int hora;
        try {
            String[] partesHorario = vo.getHorario().trim().split(":");
            hora = Integer.parseInt(partesHorario[0]);
        } catch (Exception e) {
            throw new BusinessException("O formato do horário deve ser HH:mm (Ex: 14:00).");
        }

        String periodoAgenda = agenda.getPeriodo();

        if ("1".equals(periodoAgenda)) {
            if (hora < 0 || hora >= 12) {
                throw new BusinessException("Esta agenda está disponível apenas no período da MANHÃ (00:00 às 11:59).");
            }
        } else if ("2".equals(periodoAgenda)) {
            if (hora < 12 || hora >= 18) {
                throw new BusinessException("Esta agenda está disponível apenas no período da TARDE (12:00 às 17:59).");
            }
        } else if (!"3".equals(periodoAgenda)) {
            throw new BusinessException("Período de agenda inválido ou não reconhecido pelo sistema.");
        }

        if (vo.getRowid() == null || vo.getRowid().isEmpty()) {
            dao.insertCompromisso(vo);
        } else {
            // Caso precise implementar alteração futuramente
            // dao.updateCompromisso(vo);
        }
    }
    
    public List<CompromissoVo> gerarRelatorio(String dataInicial, String dataFinal) {
        if (dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()) {
            // Se preferir, pode retornar vazio ou todos caso venha em branco
            return new ArrayList<>();
        }
        return dao.findCompromissosByPeriodo(dataInicial, dataFinal);
    }
    
}