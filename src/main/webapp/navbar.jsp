<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <a class="navbar-brand" href="todosFuncionarios.action">Portal</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link <s:if test='%{#request.paginaAtiva == "funcionario"}'>text-white fw-bold</s:if>" href="todosFuncionarios.action">Funcionários</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <s:if test='%{#request.paginaAtiva == "agenda"}'>text-white fw-bold</s:if>" href="todosAgendas.action">Agendas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <s:if test='%{#request.paginaAtiva == "compromisso"}'>text-white fw-bold</s:if>" href="todosCompromissos.action">Compromissos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <s:if test='%{#request.paginaAtiva == "relatorio"}'>text-white fw-bold</s:if>" href="prepararRelatorioCompromissos.action">Relatório</a>
                </li>
            </ul>
        </div>
    </div>
</nav>