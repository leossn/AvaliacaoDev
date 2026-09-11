<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><s:text name="agenda.titulo.pagina.consulta" /></title>
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
</head>
<body class="bg-light">
	<s:set var="paginaAtiva" value="%{'agenda'}" scope="request"/>
	<jsp:include page="/navbar.jsp"></jsp:include>

	<div class="container mt-4">
		<div class="card mb-4 shadow-sm">
			<div class="card-body">
				<s:form action="filtrarAgendas" method="post" theme="simple"
					cssClass="row g-3 align-items-center">
					<div class="col-auto">
						<label class="col-form-label fw-bold text-secondary"><s:text
								name="label.buscar.por" /></label>
					</div>
					<div class="col-md-3">
						<s:select name="filtrar.opcoesCombo" list="listaOpcoesComboAgenda"
							listKey="codigo" listValue="descricao" headerKey=""
							headerValue="Selecione..." cssClass="form-select" />
					</div>
					<div class="col-md-4">
						<s:textfield name="filtrar.valorBusca" cssClass="form-control"
							placeholder="..." />
					</div>
					<div class="col-auto">
						<s:submit value="Pesquisar" cssClass="btn btn-primary px-4" />
						<s:url action="todosAgendas" var="urlTodos" />
						<a href="${urlTodos}" class="btn btn-outline-secondary">Limpar</a>
					</div>
				</s:form>
			</div>
		</div>

		<div class="card shadow-sm">
			<div class="card-header bg-white py-3">
				<div class="row align-items-center">
					<div class="col">
						<h4 class="mb-0">
							<s:text name="agenda.titulo.pagina.consulta" />
						</h4>
					</div>
					<div class="col text-end">
						<s:url action="novoAgendas" var="urlNovo" />
						<a href="${urlNovo}" class="btn btn-primary"><s:text
								name="agenda.botao.nova" /></a>
						<s:url action="prepararRelatorioCompromissos" var="urlRelatorio" />
						<a href="${urlRelatorio}" class="btn btn-outline-secondary">Relatório</a>
					</div>
				</div>
			</div>
			<div class="card-body">
				<table class="table table-striped table-hover align-middle">
					<thead>
						<tr>
							<th><s:text name="agenda.tabela.id" /></th>
							<th><s:text name="agenda.tabela.nome" /></th>
							<th><s:text name="agenda.tabela.periodo" /></th>
							<th class="text-end"><s:text name="agenda.tabela.acoes" /></th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="agendas">
							<tr>
								<td><s:property value="rowid" /></td>
								<td><s:property value="nome" /></td>
								<td><s:property value="descricaoPeriodo" /></td>
								<td class="text-end"><s:url action="editarAgendas"
										var="urlEditar">
										<s:param name="agendaVo.rowid" value="rowid" />
									</s:url> <s:url action="excluirAgendas" var="urlExcluir">
										<s:param name="agendaVo.rowid" value="rowid" />
									</s:url> <a href="${urlEditar}" class="btn btn-warning btn-sm"><s:text
											name="label.editar" /></a> <a href="${urlExcluir}"
									class="btn btn-danger btn-sm"><s:text name="label.excluir" /></a>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<s:if test="exibirModalVinculo">
		<div class="modal fade" id="modalVinculo" tabindex="-1"
			data-bs-backdrop="static" data-bs-keyboard="false">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content shadow-lg border-0">
					<div class="modal-header bg-danger text-white">
						<h5 class="modal-title">Ação Bloqueada: Agenda em Uso</h5>
					</div>
					<div class="modal-body">
						<p class="mb-1">
							<s:actionerror />
						</p>
						<p class="text-muted small">Não é possível excluir esta agenda
							pois existem compromissos vinculados a ela.</p>
					</div>
					<div class="modal-footer bg-light">
						<s:url action="todosAgendas" var="urlVoltar" />
						<a href="${urlVoltar}" class="btn btn-secondary btn-sm">Entendido</a>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			document.addEventListener("DOMContentLoaded", function() {
				var modal = new bootstrap.Modal(document
						.getElementById('modalVinculo'));
				modal.show();
			});
		</script>
	</s:if>

	<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>