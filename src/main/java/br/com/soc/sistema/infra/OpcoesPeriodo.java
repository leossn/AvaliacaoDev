package br.com.soc.sistema.infra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import br.com.soc.sistema.exception.BusinessException;

public enum OpcoesPeriodo {
    MANHA("1", "Manha"),
    TARDE("2", "Tarde"),
    AMBOS("3", "Ambos");
    
    private String codigo;
    private String descricao;
    private final static Map<String, OpcoesPeriodo> opcoes = new HashMap<>();
    
    static {
        Arrays.asList(OpcoesPeriodo.values())
        .forEach(opcao -> opcoes.put(opcao.getCodigo(), opcao));
    }
    
    private OpcoesPeriodo(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    
    public static OpcoesPeriodo buscarPor(String codigo) {
        if(codigo == null)
            throw new IllegalArgumentException("Informe um codigo valido");
        
        return getOpcao(codigo)
                .orElseThrow(() -> new BusinessException("Periodo informado nao existe"));
    }
    
    private static Optional<OpcoesPeriodo> getOpcao(String codigo){
        return Optional.ofNullable(opcoes.get(codigo));
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getDescricao() {
        return descricao;
    }
}