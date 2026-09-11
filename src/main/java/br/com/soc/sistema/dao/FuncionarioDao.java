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
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioDao extends Dao {
    private static final Logger logger = LogManager.getLogger(FuncionarioDao.class);	
	public void insertFuncionario(FuncionarioVo funcionarioVo){
		StringBuilder query = new StringBuilder("INSERT INTO funcionario (nm_funcionario) values (?)");
		try(
			Connection con = getConexao();
			PreparedStatement  ps = con.prepareStatement(query.toString())){
			
			int i=1;
			ps.setString(i++, funcionarioVo.getNome());
			ps.executeUpdate();
		 } catch (SQLException e) {
	            logger.error("Erro ao inserir funcionário no banco de dados", e);
	            throw new TechnicalException("Erro ao salvar funcionário", e);
	        }
	}
	
	public List<FuncionarioVo> findAllFuncionarios(){
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_funcionario nome FROM funcionario");
		try(
			Connection con = getConexao();
			PreparedStatement  ps = con.prepareStatement(query.toString());
			ResultSet rs = ps.executeQuery()){
			
			FuncionarioVo vo =  null;
			List<FuncionarioVo> funcionarios = new ArrayList<>();
			while (rs.next()) {
				vo = new FuncionarioVo();
				vo.setRowid(rs.getString("id"));
				vo.setNome(rs.getString("nome"));	
				
				funcionarios.add(vo);
			}
			return funcionarios;
		} catch (SQLException e) {
		    logger.error("Erro ao consultar funcionarios no banco de dados", e);
		}
		
		return Collections.emptyList();
	}
	
	public List<FuncionarioVo> findAllByNome(String nome){
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_funcionario nome FROM funcionario ")
								.append("WHERE lower(nm_funcionario) like lower(?)");
		
		try(Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())){
			int i = 1;
			
			ps.setString(i, "%"+nome+"%");
			
			try(ResultSet rs = ps.executeQuery()){
				FuncionarioVo vo =  null;
				List<FuncionarioVo> funcionarios = new ArrayList<>();
				
				while (rs.next()) {
					vo = new FuncionarioVo();
					vo.setRowid(rs.getString("id"));
					vo.setNome(rs.getString("nome"));	
					
					funcionarios.add(vo);
				}
				return funcionarios;
			}
		} catch (SQLException e) {
		    logger.error("Erro ao consultar nome de funcionarios no banco de dados", e);
		}
			
		return Collections.emptyList();
	}
	
	public FuncionarioVo findByCodigo(Integer codigo){
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_funcionario nome FROM funcionario ")
								.append("WHERE rowid = ?");
		
		try(Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())){
			int i = 1;
			
			ps.setInt(i, codigo);
			
			try(ResultSet rs = ps.executeQuery()){
				FuncionarioVo vo =  null;
				
				while (rs.next()) {
					vo = new FuncionarioVo();
					vo.setRowid(rs.getString("id"));
					vo.setNome(rs.getString("nome"));	
				}
				return vo;
			}
		} catch (SQLException e) {
		    logger.error("Erro ao consultar códigos de funcionarios no banco de dados", e);
		}		
		return null;
	}
	
	public void deleteFuncionario(Integer id) {
	    StringBuilder deleteCompromissos = new StringBuilder("DELETE FROM compromisso WHERE cd_funcionario = ?");
	    StringBuilder deleteFuncionario = new StringBuilder("DELETE FROM funcionario WHERE rowid = ?");
	    
	    try (Connection con = getConexao()) {
	        con.setAutoCommit(false);
	        
	        try (PreparedStatement psComp = con.prepareStatement(deleteCompromissos.toString());
	             PreparedStatement psFunc = con.prepareStatement(deleteFuncionario.toString())) {
	            
	            psComp.setInt(1, id);
	            psComp.executeUpdate();
	            
	            psFunc.setInt(1, id);
	            psFunc.executeUpdate();
	            
	            con.commit(); 
	        } catch (SQLException e) {
                con.rollback(); 
                logger.error("Erro na transação de exclusão do funcionário ID {}. Rollback executado.", id, e);
                throw new TechnicalException("Falha na exclusão do funcionário e compromissos vinculados", e);
            }
        } catch (SQLException e) {
            logger.error("Erro de conexão ao tentar excluir funcionário ID {}", id, e);
            throw new TechnicalException("Erro de conexão no banco de dados", e);
        }
	}
	
	public void updateFuncionario(FuncionarioVo funcionarioVo){
	    StringBuilder query = new StringBuilder("UPDATE funcionario SET nm_funcionario = ? WHERE rowid = ?");
	    try(
	        Connection con = getConexao();
	        PreparedStatement ps = con.prepareStatement(query.toString())){
	        
	        ps.setString(1, funcionarioVo.getNome());
	        ps.setInt(2, Integer.parseInt(funcionarioVo.getRowid()));
	        ps.executeUpdate();
	        
	    } catch (SQLException e) {
            logger.error("Erro ao atualizar funcionário ID: {}", funcionarioVo.getRowid(), e);
            throw new TechnicalException("Erro ao atualizar funcionário", e);
        }
	}
	
	public boolean existsCompromissoByFuncionario(Integer idFuncionario) {
	    StringBuilder query = new StringBuilder("SELECT COUNT(1) total FROM compromisso WHERE cd_funcionario = ?");
	    try (Connection con = getConexao();
	         PreparedStatement ps = con.prepareStatement(query.toString())) {
	        ps.setInt(1, idFuncionario);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("total") > 0;
	            }
	        }
	    } catch (SQLException e) {
		    logger.error("Erro ao consultar compromissos existentes de funcionarios no banco de dados", e);
		}		
	    return false;
	}
}