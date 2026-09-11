package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.soc.sistema.exception.TechnicalException;
import br.com.soc.sistema.vo.CompromissoVo;

public class CompromissoDao extends Dao {
	private static final Logger logger = LogManager.getLogger(CompromissoDao.class);
	private static final SimpleDateFormat SDF_EXIBICAO = new SimpleDateFormat("dd/MM/yyyy");

	private java.sql.Date converterParaSqlDate(String dataStr) {
		if (dataStr == null || dataStr.trim().isEmpty()) {
			return null;
		}
		String d = dataStr.trim();
		if (d.contains("/")) {
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return java.sql.Date.valueOf(LocalDate.parse(d, fmt));
		}
		return java.sql.Date.valueOf(d);
	}

	public void insertCompromisso(CompromissoVo compromissoVo) {
		StringBuilder query = new StringBuilder(
				"INSERT INTO compromisso (cd_funcionario, cd_agenda, dt_compromisso, hr_compromisso) values (?, ?, ?, ?)");
		try (Connection con = getConexao(); PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setInt(1, Integer.parseInt(compromissoVo.getCodigoFuncionario()));
			ps.setInt(2, Integer.parseInt(compromissoVo.getCodigoAgenda()));
			ps.setDate(3, converterParaSqlDate(compromissoVo.getData()));
			ps.setString(4, compromissoVo.getHorario());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("Erro ao agendar compromisso no banco de dados", e);
			throw new TechnicalException("Erro ao agendar compromisso no banco de dados", e);
		}
	}

	public void updateCompromisso(CompromissoVo compromissoVo) {
		StringBuilder query = new StringBuilder(
				"UPDATE compromisso SET cd_funcionario = ?, cd_agenda = ?, dt_compromisso = ?, hr_compromisso = ? WHERE rowid = ?");
		try (Connection con = getConexao(); PreparedStatement ps = con.prepareStatement(query.toString())) {
			ps.setInt(1, Integer.parseInt(compromissoVo.getCodigoFuncionario()));
			ps.setInt(2, Integer.parseInt(compromissoVo.getCodigoAgenda()));
			ps.setDate(3, converterParaSqlDate(compromissoVo.getData()));
			ps.setString(4, compromissoVo.getHorario());
			ps.setInt(5, Integer.parseInt(compromissoVo.getRowid()));
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Erro ao atualizar compromisso ID: {}", compromissoVo.getRowid(), e);
			throw new TechnicalException("Erro ao atualizar compromisso no banco de dados", e);
		}
	}

	public List<CompromissoVo> findAllCompromissos() {
		StringBuilder query = new StringBuilder(
				"SELECT c.rowid id, c.cd_funcionario, f.nm_funcionario, c.cd_agenda, a.nm_agenda, c.dt_compromisso, c.hr_compromisso ")
				.append("FROM compromisso c ").append("JOIN funcionario f ON c.cd_funcionario = f.rowid ")
				.append("JOIN agenda a ON c.cd_agenda = a.rowid");

		try (Connection con = getConexao();
				PreparedStatement ps = con.prepareStatement(query.toString());
				ResultSet rs = ps.executeQuery()) {

			List<CompromissoVo> compromissos = new ArrayList<>();
			while (rs.next()) {
				CompromissoVo vo = new CompromissoVo();
				vo.setRowid(rs.getString("id"));
				vo.setCodigoFuncionario(rs.getString("cd_funcionario"));
				vo.setNomeFuncionario(rs.getString("nm_funcionario"));
				vo.setCodigoAgenda(rs.getString("cd_agenda"));
				vo.setNomeAgenda(rs.getString("nm_agenda"));

				java.sql.Date dt = rs.getDate("dt_compromisso");
				if (dt != null) {
					vo.setData(SDF_EXIBICAO.format(dt));
				}

				vo.setHorario(rs.getString("hr_compromisso"));
				compromissos.add(vo);
			}
			return compromissos;
		} catch (SQLException e) {
			logger.error("Erro ao buscar todos os compromissos", e);
		}
		return Collections.emptyList();
	}

	public List<CompromissoVo> findCompromissosByPeriodo(String dataInicial, String dataFinal) {
		List<CompromissoVo> compromissos = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.rowid as id_compromisso, c.dt_compromisso as data, c.hr_compromisso as hora, ");
		sql.append("f.rowid as id_func, f.nm_funcionario as nome_func, ");
		sql.append("a.rowid as id_agenda, a.nm_agenda as nome_agenda ");
		sql.append("FROM compromisso c ");
		sql.append("INNER JOIN funcionario f ON c.cd_funcionario = f.rowid ");
		sql.append("INNER JOIN agenda a ON c.cd_agenda = a.rowid ");
		sql.append("WHERE c.dt_compromisso BETWEEN ? AND ?");

		try (Connection con = getConexao(); PreparedStatement ps = con.prepareStatement(sql.toString())) {
			ps.setDate(1, converterParaSqlDate(dataInicial));
			ps.setDate(2, converterParaSqlDate(dataFinal));

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					CompromissoVo comp = new CompromissoVo();
					comp.setRowid(rs.getString("id_compromisso"));

					java.sql.Date dt = rs.getDate("data");
					if (dt != null) {
						comp.setData(SDF_EXIBICAO.format(dt));
					}

					comp.setHorario(rs.getString("hora"));
					comp.setCodigoFuncionario(rs.getString("id_func"));
					comp.setNomeFuncionario(rs.getString("nome_func"));
					comp.setCodigoAgenda(rs.getString("id_agenda"));
					comp.setNomeAgenda(rs.getString("nome_agenda"));

					compromissos.add(comp);
				}
			}
		} catch (Exception e) {
			logger.error("Erro ao gerar relatório de compromissos por período", e);
		}
		return compromissos;
	}

	public CompromissoVo findByCodigo(Integer codigo) {
		StringBuilder query = new StringBuilder(
				"SELECT c.rowid id, c.cd_funcionario, f.nm_funcionario, c.cd_agenda, a.nm_agenda, c.dt_compromisso, c.hr_compromisso "
						+ "FROM compromisso c JOIN funcionario f ON c.cd_funcionario = f.rowid "
						+ "JOIN agenda a ON c.cd_agenda = a.rowid WHERE c.rowid = ?");

		try (Connection con = getConexao(); PreparedStatement ps = con.prepareStatement(query.toString())) {
			ps.setInt(1, codigo);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					CompromissoVo vo = new CompromissoVo();
					vo.setRowid(rs.getString("id"));
					vo.setCodigoFuncionario(rs.getString("cd_funcionario"));
					vo.setNomeFuncionario(rs.getString("nm_funcionario"));
					vo.setCodigoAgenda(rs.getString("cd_agenda"));
					vo.setNomeAgenda(rs.getString("nm_agenda"));

					java.sql.Date dt = rs.getDate("dt_compromisso");
					if (dt != null) {
						vo.setData(SDF_EXIBICAO.format(dt));
					}

					vo.setHorario(rs.getString("hr_compromisso"));
					return vo;
				}
			}
		} catch (SQLException e) {
			logger.error("Erro ao buscar compromisso por código ID: {}", codigo, e);
		}
		return null;
	}

	public void deleteCompromisso(Integer id) {
		StringBuilder query = new StringBuilder("DELETE FROM compromisso WHERE rowid = ?");
		try (Connection con = getConexao(); PreparedStatement ps = con.prepareStatement(query.toString())) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Erro ao excluir compromisso ID: {}", id, e);
			throw new TechnicalException("Erro ao excluir compromisso no banco de dados", e);
		}
	}
}