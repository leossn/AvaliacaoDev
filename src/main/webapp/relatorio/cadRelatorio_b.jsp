<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Filtro de Relatório de Compromissos</title>
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
</head>
<body class="bg-light">
	<s:set var="paginaAtiva" value="%{'relatorio'}" scope="request"/>
	<jsp:include page="/navbar.jsp" />.

	<div class="container mt-5">
		<div class="card shadow-sm">
			<div class="card-header bg-white py-3">
				<h4 class="mb-0">Filtrar Relatório de Compromissos por Período</h4>
			</div>
			<div class="card-body">
				<s:form action="gerarRelatorioCompromissos" method="post"
					theme="simple" cssClass="row g-3">
					<div class="col-md-6">
						<label class="form-label fw-bold text-secondary">Data
							Inicial:</label>
						<s:textfield name="dataInicial" cssClass="form-control"
							type="date" />
					</div>
					<div class="col-md-6">
						<label class="form-label fw-bold text-secondary">Data
							Final:</label>
						<s:textfield name="dataFinal" cssClass="form-control" type="date" />
					</div>
					<div class="col-12 mt-4">
						<s:submit value="Gerar Relatório em Tela"
							cssClass="btn btn-primary px-4" />
					</div>
				</s:form>
			</div>
		</div>
	</div>
	<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>