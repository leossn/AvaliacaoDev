package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import br.com.soc.sistema.exception.TechnicalException;
import br.com.soc.sistema.vo.AgendaVo;

public class AgendaDao extends Dao {
	private static final Logger logger = LogManager.getLogger(AgendaDao.class);
	public void insertAgenda(AgendaVo agendaVo) {
		StringBuilder query = new StringBuilder("INSERT INTO agenda (nm_agenda, ds_periodo) values (?, ?)");
		try (Connection con = getConexao(); PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setString(1, agendaVo.getNome());
			ps.setString(2, agendaVo.getPeriodo());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Erro ao inserir agenda no banco de dados. Nome: {}", agendaVo.getNome(), e);
            throw new TechnicalException("Erro ao inserir agenda no banco de dados", e);
		}
	}

	public List<AgendaVo> findAllAgendas() {
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_agenda nome, ds_periodo periodo FROM agenda");
		try (Connection con = getConexao();
				PreparedStatement ps = con.prepareStatement(query.toString());
				ResultSet rs = ps.executeQuery()) {

			AgendaVo vo = null;
			List<AgendaVo> agendas = new ArrayList<>();
			while (rs.next()) {
				vo = new AgendaVo();
				vo.setRowid(rs.getString("id"));
				vo.setNome(rs.getString("nome"));
				vo.setPeriodo(rs.getString("periodo"));

				agendas.add(vo);
			}
			return agendas;
		} catch (SQLException e) {
		    logger.error("Erro ao consultar agendas no banco de dados", e);
		}		

		return Collections.emptyList();
	}

	public AgendaVo findByCodigo(Integer codigo) {
		StringBuilder query = new StringBuilder(
				"SELECT rowid id, nm_agenda nome, ds_periodo periodo FROM agenda WHERE rowid = ?");

		try (Connection con = getConexao(); PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setInt(1, codigo);

			try (ResultSet rs = ps.executeQuery()) {
				AgendaVo vo = null;
				while (rs.next()) {
					vo = new AgendaVo();
					vo.setRowid(rs.getString("id"));
					vo.setNome(rs.getString("nome"));
					vo.setPeriodo(rs.getString("periodo"));
				}
				return vo;
			}
		} catch (SQLException e) {
		    logger.error("Erro ao consultar codigos de agendas no banco de dados", e);
		}
		return null;
	}
	
	public AgendaVo findByCodigo(String codigo) {
		try {
			Integer cod = Integer.parseInt(codigo);
			return findByCodigo(cod);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public void updateAgenda(AgendaVo agendaVo) {
		StringBuilder query = new StringBuilder("UPDATE agenda SET nm_agenda = ?, ds_periodo = ? WHERE rowid = ?");
		try (Connection con = getConexao(); PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setString(1, agendaVo.getNome());
			ps.setString(2, agendaVo.getPeriodo());
			ps.setInt(3, Integer.parseInt(agendaVo.getRowid()));
			ps.executeUpdate();
		} catch (SQLException e) {
            logger.error("Erro ao atualizar agenda ID: {}", agendaVo.getRowid(), e);
            throw new TechnicalException("Erro ao atualizar agenda no banco de dados", e);
        }
	}

	public void deleteAgenda(Integer id) {
		StringBuilder query = new StringBuilder("DELETE FROM agenda WHERE rowid = ?");
		try (Connection con = getConexao(); PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
            logger.error("Erro ao excluir agenda ID: {}", id, e);
            throw new TechnicalException("Erro ao excluir agenda no banco de dados", e);
        }
	}

	public List<AgendaVo> listarParaRelatorio() {
		List<AgendaVo> agendas = new ArrayList<>();
		String sql = "SELECT rowid id, nm_agenda nome, ds_periodo periodo FROM agenda";
		try (Connection con = getConexao();
				PreparedStatement stmt = con.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				AgendaVo vo = new AgendaVo();
				vo.setRowid(rs.getString("id"));
				vo.setNome(rs.getString("nome"));
				vo.setPeriodo(rs.getString("periodo"));
				agendas.add(vo);
			}
		} catch (SQLException e) {
		    logger.error("Erro ao listar relatorios no banco de dados", e);
		}
		return agendas;
	}

	public List<AgendaVo> findAllByNome(String nome) {
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_agenda nome, ds_periodo periodo FROM agenda ")
				.append("WHERE lower(nm_agenda) like lower(?)");

		try (Connection con = getConexao(); PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setString(1, "%" + nome + "%");

			try (ResultSet rs = ps.executeQuery()) {
				List<AgendaVo> agendas = new ArrayList<>();
				while (rs.next()) {
					AgendaVo vo = new AgendaVo();
					vo.setRowid(rs.getString("id"));
					vo.setNome(rs.getString("nome"));
					vo.setPeriodo(rs.getString("periodo"));
					agendas.add(vo);
				}
				return agendas;
			}
		} catch (SQLException e) {
		    logger.error("Erro ao consultar agendas pelo nome no banco de dados", e);
		}
		return Collections.emptyList();
	}

	public List<AgendaVo> findAllByPeriodo(String periodo) {
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_agenda nome, ds_periodo periodo FROM agenda ")
				.append("WHERE ds_periodo = ?");

		try (Connection con = getConexao(); PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setString(1, periodo);

			try (ResultSet rs = ps.executeQuery()) {
				List<AgendaVo> agendas = new ArrayList<>();
				while (rs.next()) {
					AgendaVo vo = new AgendaVo();
					vo.setRowid(rs.getString("id"));
					vo.setNome(rs.getString("nome"));
					vo.setPeriodo(rs.getString("periodo"));
					agendas.add(vo);
				}
				return agendas;
			}
		} catch (SQLException e) {
		    logger.error("Erro ao consultar agendas pelo periodo no banco de dados", e);
		}
		return Collections.emptyList();
	}
	
	public boolean existsCompromissosByAgenda(Integer idAgenda) {
	    StringBuilder query = new StringBuilder("SELECT COUNT(1) total FROM compromisso WHERE cd_agenda = ?");
	    
	    try (Connection con = getConexao();
	         PreparedStatement ps = con.prepareStatement(query.toString())) {
	        
	        ps.setInt(1, idAgenda);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("total") > 0;
	            }
	        }
	    } catch (SQLException e) {
		    logger.error("Erro ao consultar agendas com compromissos existentes no banco de dados", e);
		}
	    
	    return false;
	}
}