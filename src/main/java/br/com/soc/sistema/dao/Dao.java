package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.soc.sistema.exception.TechnicalException;

public abstract class Dao implements AutoCloseable {
	private static final Logger logger = LogManager.getLogger(Dao.class);
	private static boolean primeiraInicializacao = true;
	private Connection con = null;

	public Dao() {
		conectar();
	}

	private void conectar() {
		StringBuilder urlBuilder = new StringBuilder("jdbc:h2:mem:avaliacao;").append("DB_CLOSE_DELAY=-1;")
				.append("DATABASE_TO_UPPER=false;");

		if (primeiraInicializacao) {
			urlBuilder.append("INIT=runscript from 'classpath:CRIA_TABELAS_E_INSERE_REGISTROS_INICIAIS.sql';");
			primeiraInicializacao = false;
		}

		try {
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection(urlBuilder.toString());
		} catch (SQLException ex) {
			logger.error("Falha ao estabelecer conexão com o banco H2", ex);
			throw new TechnicalException("Ocorreu um problema na tentativa de conexão com o banco de dados", ex);
		} catch (ClassNotFoundException e) {
			logger.error("Driver JDBC do H2 não encontrado no classpath", e);
			throw new TechnicalException("Driver de banco de dados não localizado", e);
		}
	}

	private void fechar() throws SQLException {
		if (con == null)
			throw new TechnicalException("Conexao nao foi criada");

		if (con.isClosed())
			throw new TechnicalException("Conexao ja foi encerrada");

		con.close();
	}

	@Override
	public void close() throws Exception {
		try {
			fechar();
		} catch (SQLException e) {
			logger.error("Erro ao encerrar conexão JDBC", e);
		}
	}

	protected Connection getConexao() throws SQLException {
		if (con == null || con.isClosed())
			conectar();
		return con;
	}
}