<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de Funcionários</title>
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
</head>
<body class="bg-light">
	<s:set var="paginaAtiva" value="%{'funcionario'}" scope="request"/>
	<jsp:include page="/navbar.jsp" />
	
	<div class="container mt-4">
		<div class="card mb-4 shadow-sm">
			<div class="card-body">
				<s:form action="filtrarFuncionarios" method="post" theme="simple"
					cssClass="row g-3 align-items-center">
					<div class="col-auto">
						<label class="col-form-label fw-bold text-secondary">Pesquisar
							por:</label>
					</div>
					<div class="col-md-3">
						<s:select name="filtrar.opcoesCombo" list="listaOpcoesCombo"
							listKey="codigo" listValue="descricao" headerKey=""
							headerValue="Selecione..." cssClass="form-select" />
					</div>
					<div class="col-md-4">
						<s:textfield name="filtrar.valorBusca" cssClass="form-control"
							placeholder="Digite o valor..." />
					</div>
					<div class="col-auto">
						<s:submit value="Pesquisar" cssClass="btn btn-primary px-4" />
						<s:url action="todosFuncionarios" var="urlTodos" />
						<a href="${urlTodos}" class="btn btn-outline-secondary">Limpar</a>
					</div>
				</s:form>
			</div>
		</div>

		<div class="card shadow-sm">
			<div class="card-header bg-white py-3">
				<div class="row align-items-center">
					<div class="col">
						<h4 class="mb-0">Lista de Funcionários</h4>
					</div>
					<div class="col text-end">
						<s:url action="novoFuncionarios" var="urlNovo" />
						<a href="${urlNovo}" class="btn btn-primary">Novo Funcionário</a>
					</div>
				</div>
			</div>
			<div class="card-body">
				<table class="table table-striped table-hover align-middle">
					<thead>
						<tr>
							<th>ID</th>
							<th>Nome do Funcionário</th>
							<th class="text-end">Ações</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="funcionarios">
							<tr>
								<td><s:property value="rowid" /></td>
								<td><s:property value="nome" /></td>
								<td class="text-end"><s:url action="editarFuncionarios"
										var="urlEditar">
										<s:param name="funcionarioVo.rowid" value="rowid" />
									</s:url> <s:url action="excluirFuncionarios" var="urlExcluir">
										<s:param name="funcionarioVo.rowid" value="rowid" />
									</s:url> <a href="${urlEditar}" class="btn btn-warning btn-sm">Editar</a>
									<a href="${urlExcluir}" class="btn btn-danger btn-sm">Excluir</a>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<s:if test="exibirModalCascata">
		<div class="modal fade" id="modalCascata" tabindex="-1"
			data-bs-backdrop="static" data-bs-keyboard="false">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content shadow-lg border-0">
					<div class="modal-header bg-warning text-dark">
						<h5 class="modal-title">Atenção: Funcionário com Compromissos</h5>
					</div>
					<div class="modal-body">
						<p class="mb-1">
							<s:actionerror />
						</p>
						<p class="text-muted small">Deseja realmente prosseguir com a
							exclusão? Todos os compromissos associados a este funcionário
							também serão apagados.</p>
					</div>
					<div class="modal-footer bg-light">
						<s:url action="todosFuncionarios" var="urlCancelar" />
						<a href="${urlCancelar}" class="btn btn-secondary btn-sm">Cancelar</a>

						<s:url action="excluirFuncionarios" var="urlConfirmar">
							<s:param name="funcionarioVo.rowid"
								value="%{funcionarioVo.rowid}" />
							<s:param name="exibirModalCascata" value="true" />
						</s:url>
						<a href="${urlConfirmar}" class="btn btn-danger btn-sm">Sim,
							Excluir Tudo</a>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			document.addEventListener("DOMContentLoaded", function() {
				var modal = new bootstrap.Modal(document
						.getElementById('modalCascata'));
				modal.show();
			});
		</script>
	</s:if>

	<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>