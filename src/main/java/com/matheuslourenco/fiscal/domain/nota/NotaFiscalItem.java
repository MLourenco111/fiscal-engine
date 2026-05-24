package com.matheuslourenco.fiscal.domain.nota;

import com.matheuslourenco.fiscal.domain.shared.Imposto;
import com.matheuslourenco.fiscal.domain.shared.Item;
import com.matheuslourenco.fiscal.domain.shared.UnidadeMedida;

import java.math.BigDecimal;
import java.util.List;


public class NotaFiscalItem {
    private String cfop;
    private final BigDecimal quantidade;
    private final BigDecimal valorUnitario;
    private final BigDecimal valorDesconto;// preciso descobrir se vou salvar valor total do desconto ou se valor unitario
    private BigDecimal valorTotal;
    private BigDecimal pesoBruto;
    private BigDecimal pesoLiquido;
    private UnidadeMedida unidadeMedida;
    private List<Imposto> impostos;
    private Item item;

    public NotaFiscalItem(BigDecimal valorUnitario, BigDecimal quantidade, BigDecimal valorDesconto){
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.valorDesconto = valorDesconto;
        calcularValorTotal();
    }

    private void calcularValorTotal() {
        if(this.valorUnitario == null || BigDecimal.ZERO.compareTo(this.valorUnitario) >= 0){
            throw new RuntimeException();
        }

        if(this.quantidade == null || BigDecimal.ZERO.compareTo(this.quantidade) >= 0){
            throw new RuntimeException();
        }

        if(this.valorDesconto == null || BigDecimal.ZERO.compareTo(this.valorDesconto) > 0){
            throw new RuntimeException();
        }

        if(this.valorDesconto.compareTo(this.valorUnitario.multiply(this.quantidade)) > 0){
            throw new RuntimeException();
        }

        this.valorTotal = this.valorUnitario.multiply(this.quantidade).subtract(this.valorDesconto);
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

}
