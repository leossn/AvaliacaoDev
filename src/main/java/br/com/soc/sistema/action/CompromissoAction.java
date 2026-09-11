package br.com.soc.sistema.action;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.AgendaBusiness;
import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class CompromissoAction extends Action {
	private static final long serialVersionUID = 1L;

	private List<CompromissoVo> compromissos = new ArrayList<>();
	private CompromissoBusiness business = new CompromissoBusiness();
	private CompromissoVo compromissoVo = new CompromissoVo();

	private List<FuncionarioVo> listaFuncionarios = new ArrayList<>();
	private List<AgendaVo> listaAgendas = new ArrayList<>();
	private String dataInicial;
	private String dataFinal;
	private List<CompromissoVo> listaRelatorio = new ArrayList<>();

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<CompromissoVo> getListaRelatorio() {
		return listaRelatorio;
	}

	public void setListaRelatorio(List<CompromissoVo> listaRelatorio) {
		this.listaRelatorio = listaRelatorio;
	}

	public String todos() {
		compromissos.addAll(business.trazerTodosOsCompromissos());
		return SUCCESS;
	}

	public String novo() {
		carregarCombos();
		
		if (compromissoVo == null || compromissoVo.getData() == null) {
			return INPUT;
		}

		try {
			business.salvarCompromisso(compromissoVo);
			return REDIRECT;
		} catch (br.com.soc.sistema.exception.BusinessException e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}
	
	public String editar() {
		carregarCombos();
		
	    if (compromissoVo.getRowid() == null) {
	        return REDIRECT;
	    }
	    compromissoVo = business.buscarCompromissoPor(compromissoVo.getRowid());
	    return INPUT;
	}

	public String excluir() {
	    if (compromissoVo.getRowid() == null) {
	        return REDIRECT;
	    }

	    business.excluirCompromisso(compromissoVo.getRowid());
	    return REDIRECT;
	}

	private void carregarCombos() {
		FuncionarioBusiness funcBusiness = new FuncionarioBusiness();
		AgendaBusiness agendaBusiness = new AgendaBusiness();

		listaFuncionarios = funcBusiness.trazerTodosOsFuncionarios();
		listaAgendas = agendaBusiness.trazerTodasAsAgendas();
	}

	public List<CompromissoVo> getCompromissos() {
		return compromissos;
	}

	public void setCompromissos(List<CompromissoVo> compromissos) {
		this.compromissos = compromissos;
	}

	public CompromissoVo getCompromissoVo() {
		return compromissoVo;
	}

	public void setCompromissoVo(CompromissoVo compromissoVo) {
		this.compromissoVo = compromissoVo;
	}

	public List<FuncionarioVo> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public List<AgendaVo> getListaAgendas() {
		return listaAgendas;
	}

	public String gerarRelatorio() {
		this.listaRelatorio = business.gerarRelatorio(dataInicial, dataFinal);
		return SUCCESS;
	}

	public String prepararRelatorio() {
		return SUCCESS;
	}

	public String exportarExcel() throws Exception {
		List<CompromissoVo> relatorio = business.gerarRelatorio(dataInicial, dataFinal);

		try (org.apache.poi.xssf.usermodel.XSSFWorkbook wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
			org.apache.poi.xssf.usermodel.XSSFSheet sheet = wb.createSheet("Compromissos");

			org.apache.poi.ss.usermodel.CellStyle hStyle = wb.createCellStyle();
			org.apache.poi.ss.usermodel.Font hFont = wb.createFont();
			hFont.setBold(true);
			hFont.setColor(org.apache.poi.ss.usermodel.IndexedColors.WHITE.getIndex());
			hStyle.setFont(hFont);
			hStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.BLACK.getIndex());
			hStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
			hStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);

			org.apache.poi.ss.usermodel.CellStyle pStyle = wb.createCellStyle();
			org.apache.poi.ss.usermodel.CellStyle iStyle = wb.createCellStyle();
			iStyle.setFillForegroundColor(
					new org.apache.poi.xssf.usermodel.XSSFColor(new java.awt.Color(242, 242, 242), null));
			iStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);

			for (org.apache.poi.ss.usermodel.CellStyle st : new org.apache.poi.ss.usermodel.CellStyle[] { hStyle,
					pStyle, iStyle }) {
				st.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
				st.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
				st.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
				st.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
			}

			org.apache.poi.ss.usermodel.Row head = sheet.createRow(0);
			head.setHeightInPoints(25);
			String[] cols = { "Cód. Funcionário", "Nome Funcionário", "Cód. Agenda", "Nome Agenda", "Data", "Hora" };
			for (int i = 0; i < cols.length; i++) {
				org.apache.poi.ss.usermodel.Cell c = head.createCell(i);
				c.setCellValue(cols[i]);
				c.setCellStyle(hStyle);
			}

			int rNum = 1;
			for (CompromissoVo vo : relatorio) {
				org.apache.poi.ss.usermodel.Row row = sheet.createRow(rNum++);
				row.setHeightInPoints(20);
				org.apache.poi.ss.usermodel.CellStyle st = (rNum % 2 == 0) ? iStyle : pStyle;

				org.apache.poi.ss.usermodel.Cell c0 = row.createCell(0);
				if (vo.getCodigoFuncionario() != null && !vo.getCodigoFuncionario().isEmpty())
					c0.setCellValue(Double.parseDouble(vo.getCodigoFuncionario()));
				c0.setCellStyle(st);

				org.apache.poi.ss.usermodel.Cell c1 = row.createCell(1);
				c1.setCellValue(vo.getNomeFuncionario() != null ? vo.getNomeFuncionario() : "");
				c1.setCellStyle(st);

				org.apache.poi.ss.usermodel.Cell c2 = row.createCell(2);
				if (vo.getCodigoAgenda() != null && !vo.getCodigoAgenda().isEmpty())
					c2.setCellValue(Double.parseDouble(vo.getCodigoAgenda()));
				c2.setCellStyle(st);

				org.apache.poi.ss.usermodel.Cell c3 = row.createCell(3);
				c3.setCellValue(vo.getNomeAgenda() != null ? vo.getNomeAgenda() : "");
				c3.setCellStyle(st);
				org.apache.poi.ss.usermodel.Cell c4 = row.createCell(4);
				c4.setCellValue(vo.getData() != null ? vo.getData() : "");
				c4.setCellStyle(st);
				org.apache.poi.ss.usermodel.Cell c5 = row.createCell(5);
				c5.setCellValue(vo.getHorario() != null ? vo.getHorario() : "");
				c5.setCellStyle(st);
			}

			for (int i = 0; i < cols.length; i++) {
				sheet.autoSizeColumn(i);
				sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1500);
			}

			java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
			wb.write(bos);

			javax.servlet.http.HttpServletResponse resp = org.apache.struts2.ServletActionContext.getResponse();
			resp.reset();
			resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			resp.setHeader("Content-Disposition", "attachment; filename=Relatorio_Compromissos.xlsx");
			resp.setContentLength(bos.size());

			try (javax.servlet.ServletOutputStream out = resp.getOutputStream()) {
				bos.writeTo(out);
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
}