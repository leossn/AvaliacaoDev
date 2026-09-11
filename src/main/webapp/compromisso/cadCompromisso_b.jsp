<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Consulta de Compromissos</title>
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
</head>
<body class="bg-light">
	<s:set var="paginaAtiva" value="%{'compromisso'}" scope="request"/>
	<jsp:include page="/navbar.jsp" />
	<div class="container mt-4">
		<div class="card shadow-sm">
			<div class="card-header bg-white py-3">
				<div class="row align-items-center">
					<div class="col">
						<h4 class="mb-0">Lista de Compromissos</h4>
					</div>
					<div class="col text-end">
						<s:url action="novoCompromissos" var="urlNovo" />
						<a href="${urlNovo}" class="btn btn-primary">Novo Compromisso</a>
					</div>
				</div>
			</div>
			<div class="card-body">
				<table class="table table-striped table-hover align-middle">
					<thead>
						<tr>
							<th>ID</th>
							<th>Funcionário</th>
							<th>Agenda</th>
							<th>Data</th>
							<th>Horário</th>
							<th class="text-end">Ações</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="compromissos">
							<tr>
								<td><s:property value="rowid" /></td>
								<td><s:property value="nomeFuncionario" /></td>
								<td><s:property value="nomeAgenda" /></td>
								<td><s:property value="data" /></td>
								<td><s:property value="horario" /></td>
								<td class="text-end">
									<s:url action="editarCompromissos" var="urlEditar">
										<s:param name="compromissoVo.rowid" value="rowid" />
									</s:url>
									<s:url action="excluirCompromissos" var="urlExcluir">
										<s:param name="compromissoVo.rowid" value="rowid" />
									</s:url>
									<a href="${urlEditar}" class="btn btn-warning btn-sm">Editar</a>
									<a href="${urlExcluir}" class="btn btn-danger btn-sm">Excluir</a>
								</td>
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