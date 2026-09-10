<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Relatório de Compromissos por Período</title>
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
</head>
<body class="bg-light">
	<s:set var="paginaAtiva" value="%{'relatorio'}" scope="request"/>
	<jsp:include page="/navbar.jsp" />.

	<div class="container mt-4">
		<div class="card shadow-sm">
			<div class="card-header bg-white py-3">
				<div class="row align-items-center">
					<div class="col">
						<h4 class="mb-0">Resultado do Relatório de Compromissos</h4>
					</div>
					<div class="col text-end">
						<s:url action="exportarExcelCompromissos" var="urlExcel">
							<s:param name="dataInicial" value="dataInicial" />
							<s:param name="dataFinal" value="dataFinal" />
						</s:url>
						<a href="${urlExcel}" class="btn btn-success btn-sm">Exportar Excel</a>

						<button onclick="window.print()"
							class="btn btn-outline-secondary btn-sm">Imprimir / Salvar PDF</button>
							
						<s:url action="prepararRelatorioCompromissos" var="urlNovoFiltro" />
						<a href="${urlNovoFiltro}" class="btn btn-primary btn-sm">Nova Consulta</a>
					</div>
				</div>
			</div>
			<div class="card-body">
				<table class="table table-bordered table-striped align-middle">
					<thead>
						<tr>
							<th>Cód. Funcionário</th>
							<th>Nome Funcionário</th>
							<th>Cód. Agenda</th>
							<th>Nome Agenda</th>
							<th>Data</th>
							<th>Hora</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="listaRelatorio">
							<tr>
								<td><s:property value="codigoFuncionario" /></td>
								<td><s:property value="nomeFuncionario" /></td>
								<td><s:property value="codigoAgenda" /></td>
								<td><s:property value="nomeAgenda" /></td>
								<td><s:property value="data" /></td>
								<td><s:property value="horario" /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>