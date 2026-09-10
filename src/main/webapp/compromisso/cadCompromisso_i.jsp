<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de Compromisso</title>
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
</head>
<body class="bg-secondary">
	<s:set var="paginaAtiva" value="%{'compromisso'}" scope="request"/>
	<jsp:include page="/navbar.jsp" />

	<div class="container">
		<s:form action="novoCompromissos" method="post" theme="simple">
			<div class="card mt-5">
				<div class="card-header">
					<div class="row">
						<div class="col-sm-5">
							<s:url action="todosCompromissos" var="todos" />
							<a href="${todos}" class="btn btn-success">Voltar</a>
						</div>
						<div class="col-sm">
							<h5 class="card-title">Novo Compromisso</h5>
						</div>
					</div>
				</div>

				<div class="card-body">
					<s:hidden name="compromissoVo.rowid" />

					<div class="row align-items-center mt-3">
						<label class="col-sm-2 col-form-label text-center">Funcionário:</label>
						<div class="col-sm-6">
							<s:select name="compromissoVo.codigoFuncionario"
								list="listaFuncionarios" listKey="rowid" listValue="nome"
								headerKey="" headerValue="Selecione um funcionário..."
								cssClass="form-control" />
						</div>
					</div>

					<div class="row align-items-center mt-3">
						<label class="col-sm-2 col-form-label text-center">Agenda:</label>
						<div class="col-sm-6">
							<s:select name="compromissoVo.codigoAgenda" list="listaAgendas"
								listKey="rowid" listValue="nome" headerKey=""
								headerValue="Selecione uma agenda..." cssClass="form-control" />
						</div>
					</div>

					<div class="row align-items-center mt-3">
						<label class="col-sm-2 col-form-label text-center">Data:</label>
						<div class="col-sm-6">
							<s:textfield name="compromissoVo.data" cssClass="form-control" type="date" />
						</div>
					</div>

					<div class="row align-items-center mt-3">
						<label class="col-sm-2 col-form-label text-center">Horário:</label>
						<div class="col-sm-6">
							<s:textfield name="compromissoVo.horario" cssClass="form-control"
								placeholder="Ex: 14:00" />
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

<s:if test="hasActionErrors()">
    <div class="modal fade" id="modalErroCompromisso" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content shadow-lg border-0">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title">Erro de Validação</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="d-flex align-items-center">
                        <div class="text-danger me-3">
                            <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-exclamation-triangle-fill" viewBox="0 0 16 16">
                                <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5m.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2" />
                            </svg>
                        </div>
                        <div>
                            <p class="fw-bold mb-1 text-secondary">Não foi possível salvar o compromisso:</p>
                            <div class="text-muted mb-0 small">
                                <s:actionerror theme="simple" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer bg-light">
                    <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal">Corrigir Dados</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var meuModal = new bootstrap.Modal(document.getElementById('modalErroCompromisso'));
            meuModal.show();
        });
    </script>
</s:if>

	<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>