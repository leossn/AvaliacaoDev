package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.soc.sistema.vo.CompromissoVo;

public class CompromissoDao extends Dao {

	public void insertCompromisso(CompromissoVo compromissoVo) {
		StringBuilder query = new StringBuilder(
				"INSERT INTO compromisso (cd_funcionario, cd_agenda, dt_compromisso, hr_compromisso) values (?, ?, ?, ?)");
		try (Connection con = getConexao(); PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setInt(1, Integer.parseInt(compromissoVo.getCodigoFuncionario()));
			ps.setInt(2, Integer.parseInt(compromissoVo.getCodigoAgenda()));
			String dataStr = compromissoVo.getData().trim();
			java.sql.Date dataSql;
			if (dataStr.contains("/")) {
				java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
				dataSql = java.sql.Date.valueOf(java.time.LocalDate.parse(dataStr, fmt));
			} else {
				dataSql = java.sql.Date.valueOf(dataStr);
			}

			ps.setDate(3, dataSql);
			ps.setString(4, compromissoVo.getHorario());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
				vo.setData(rs.getString("dt_compromisso"));
				vo.setHorario(rs.getString("hr_compromisso"));

				compromissos.add(vo);
			}
			return compromissos;
		} catch (SQLException e) {
			e.printStackTrace();
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
	        ps.setDate(1, java.sql.Date.valueOf(dataInicial));
	        ps.setDate(2, java.sql.Date.valueOf(dataFinal));

	        try (ResultSet rs = ps.executeQuery()) {
	            java.text.SimpleDateFormat sdfExibicao = new java.text.SimpleDateFormat("dd/MM/yyyy");
	            
	            while (rs.next()) {
	                CompromissoVo comp = new CompromissoVo();
	                comp.setRowid(rs.getString("id_compromisso"));
	                
	                java.sql.Date dt = rs.getDate("data");
	                if (dt != null) {
	                    comp.setData(sdfExibicao.format(dt));
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
	        e.printStackTrace();
	    }
	    return compromissos;
	}
}