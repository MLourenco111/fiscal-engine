package com.matheuslourenco.fiscal.domain.nota;

import com.matheuslourenco.fiscal.domain.filial.Filial;
import com.matheuslourenco.fiscal.domain.shared.Endereco;
import com.matheuslourenco.fiscal.domain.pessoa.Pessoa;
import com.matheuslourenco.fiscal.domain.shared.Frete;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class NotaFiscal {

    // campos — cabeçalho
    private String naturezaOperacao;
    private ZonedDateTime dataEmissao;
    private long numero;
    private long serie;
    private String chave;
    private short modelo;
    private String ambiente;
    private String protocolo;
    private ZonedDateTime dataSaida;
    private StatusNotaFiscal status;

    // campos — emissor
    private Filial filial;
    private Endereco enderecoFilial;

    // campos — destinatário
    private Pessoa cliente;
    private Endereco enderecoEntregaCliente;
    private String telefoneCliente;

    // campos — frete
    private Pessoa transportadora;
    private Frete frete;

    // campos — totais
    private BigDecimal pesoBruto = BigDecimal.ZERO;
    private BigDecimal pesoLiquido = BigDecimal.ZERO;
    private BigDecimal valorNotaFiscal = BigDecimal.ZERO;

    // campos — itens
    private List<NotaFiscalItem> itens = new ArrayList<>();

    // campos — dados adicionais
    private String informacoesComplementares;


    // construtor
    public NotaFiscal(List<NotaFiscalItem> itens, Filial filial, Pessoa cliente) {
        if (itens == null || itens.isEmpty()) {
            throw new RuntimeException();
        }
        if (filial == null) {
            throw new RuntimeException();
        }
        if (cliente == null) {
            throw new RuntimeException();
        }

        this.status = StatusNotaFiscal.INICIADA;
        this.filial = filial;
        this.cliente = cliente;
        itens.forEach(this::adicionarItem);
    }


    // comportamentos
    public void adicionarItem(NotaFiscalItem item) {
        if (isNull(item)) {
            throw new RuntimeException();
        }
        if (!StatusNotaFiscal.INICIADA.equals(this.status)) {
            throw new RuntimeException();
        }
        this.itens.add(item);
        this.valorNotaFiscal = this.valorNotaFiscal.add(item.getValorTotal());
    }

    public void emitir() {
        if (this.status != StatusNotaFiscal.INICIADA) {
            throw new RuntimeException();
        }
        this.status = StatusNotaFiscal.EMITIDA;
        this.dataEmissao = ZonedDateTime.now();
    }


    // getters
    public StatusNotaFiscal getStatus() {
        return status;
    }

    public ZonedDateTime getDataEmissao() {
        return dataEmissao;
    }

    public BigDecimal getValorNotaFiscal() {
        return valorNotaFiscal;
    }

    public List<NotaFiscalItem> getItens() {
        return List.copyOf(itens);
    }
}