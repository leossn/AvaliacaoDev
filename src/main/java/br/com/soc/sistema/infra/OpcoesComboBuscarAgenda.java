package br.com.soc.sistema.infra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import br.com.soc.sistema.exception.BusinessException;

public enum OpcoesComboBuscarAgenda {
    ID("1", "ID"), 
    NOME("2", "NOME"), 
    PERIODO("3", "PERÍODO");

    private String codigo;
    private String descricao;
    private final static Map<String, OpcoesComboBuscarAgenda> opcoes = new HashMap<>();

    static {
        Arrays.asList(OpcoesComboBuscarAgenda.values())
              .forEach(opcao -> opcoes.put(opcao.getCodigo(), opcao));
    }

    private OpcoesComboBuscarAgenda(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static OpcoesComboBuscarAgenda buscarPor(String codigo) {
        if (codigo == null || codigo.isEmpty())
            throw new IllegalArgumentException("Informe um codigo valido");

        return getOpcao(codigo)
                .orElseThrow(() -> new BusinessException("Opção de filtro informada não existe"));
    }

    private static Optional<OpcoesComboBuscarAgenda> getOpcao(String codigo) {
        return Optional.ofNullable(opcoes.get(codigo));
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}