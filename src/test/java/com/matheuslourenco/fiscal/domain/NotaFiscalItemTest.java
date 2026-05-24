package com.matheuslourenco.fiscal.domain;

import com.matheuslourenco.fiscal.domain.nota.NotaFiscalItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class NotaFiscalItemTest {

    @Test
    @DisplayName("Deve calcular subtotal do item")
    void deveCalcularSubtotalDoItem() {
        BigDecimal valorUnitario = new BigDecimal(10L);
        BigDecimal valorDesconto = BigDecimal.ZERO;
        BigDecimal quantidade = new BigDecimal(10L);
        NotaFiscalItem item = new NotaFiscalItem(valorUnitario, quantidade,valorDesconto);

        assertEquals(BigDecimal.valueOf(100L),item.getValorTotal());
    }

    @Test
    @DisplayName("Deve aplicar desconto no subtotal do item")
    void deveAplicarDescontoSubTotalItem(){
        BigDecimal valorUnitario = new BigDecimal(10L);
        BigDecimal valorDesconto = new BigDecimal(50L);
        BigDecimal quantidade = new BigDecimal(10L);
        NotaFiscalItem item = new NotaFiscalItem(valorUnitario, quantidade,valorDesconto);

        assertEquals(BigDecimal.valueOf(50L),item.getValorTotal());
    }

    @Test
    @DisplayName("Não deve permitir quantidade zerada")
    void naoDevePermitirQuantidadeZerada() {

        assertThrows(
                RuntimeException.class,
                () -> new NotaFiscalItem(
                        BigDecimal.ZERO,
                        new BigDecimal("10.00"),
                        BigDecimal.ZERO
                )
        );
    }

    @Test
    @DisplayName("Não deve permitir valor unitário zerado")
    void naoDevePermitirValorUnitarioZerado() {

        assertThrows(
                RuntimeException.class,
                () -> new NotaFiscalItem(
                        new BigDecimal("2"),
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                )
        );
    }

    @Test
    @DisplayName("Não deve permitir valor desconto negativo")
    void naoDevePermitirValorDescontoNegativo() {

        assertThrows(
                RuntimeException.class,
                () -> new NotaFiscalItem(
                        new BigDecimal("2"),
                        BigDecimal.ONE,
                        BigDecimal.valueOf(-1L)
                )
        );
    }

    @Test
    @DisplayName("Não deve permitir valor de desconto maior que valor total")
    void naoDevePermitirValorDescontoMaiorQueValorTotal() {

        assertThrows(
                RuntimeException.class,
                () -> new NotaFiscalItem(
                        new BigDecimal("2"),
                        BigDecimal.ONE,
                        BigDecimal.TEN
                )
        );
    }

}
