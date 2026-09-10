<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><s:text name="agenda.titulo.pagina.cadastro" /></title>
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
</head>
<body class="bg-secondary">
	<s:set var="paginaAtiva" value="%{'agenda'}" scope="request"/>
	<jsp:include page="/navbar.jsp"></jsp:include>
	<div class="container">
		<s:form action="novoAgendas" method="post" theme="simple">
			<div class="card mt-5">
				<div class="card-header">
					<div class="row">
						<div class="col-sm-5">
							<s:url action="todosAgendas" var="todos" />
							<a href="${todos}" class="btn btn-success">Voltar</a>
						</div>
						<div class="col-sm">
							<h5 class="card-title">
								<s:text name="agenda.titulo.pagina.cadastro" />
							</h5>
						</div>
					</div>
				</div>

				<div class="card-body">
					<s:hidden name="agendaVo.rowid" />

					<div class="row align-items-center mt-3">
						<label class="col-sm-2 col-form-label text-center"><s:text
								name="agenda.label.nome" /></label>
						<div class="col-sm-6">
							<s:textfield name="agendaVo.nome" cssClass="form-control" />
						</div>
					</div>

					<div class="row align-items-center mt-3">
						<label class="col-sm-2 col-form-label text-center"><s:text
								name="agenda.label.periodo" /></label>
						<div class="col-sm-6">
							<s:select name="agendaVo.periodo" list="listaOpcoesPeriodo"
								listKey="codigo" listValue="descricao" headerKey=""
								headerValue="Selecione o Período" cssClass="form-control" />
						</div>
					</div>
				</div>

				<div class="card-footer">
					<div class="form-row">
						<s:submit value="Salvar"
							cssClass="btn btn-primary col-sm-4 offset-sm-1" />
						<button type="reset"
							class="btn btn-secondary col-sm-4 offset-sm-2">Limpar
							Formulário</button>
					</div>
				</div>
			</div>
		</s:form>
	</div>
	<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>