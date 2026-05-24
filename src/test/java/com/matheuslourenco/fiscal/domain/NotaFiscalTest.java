package com.matheuslourenco.fiscal.domain;

import com.matheuslourenco.fiscal.domain.filial.Filial;
import com.matheuslourenco.fiscal.domain.nota.NotaFiscal;
import com.matheuslourenco.fiscal.domain.nota.NotaFiscalItem;
import com.matheuslourenco.fiscal.domain.nota.StatusNotaFiscal;
import com.matheuslourenco.fiscal.domain.pessoa.Pessoa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotaFiscalTest {

    @Test
    @DisplayName("Deve criar nota fiscal com um item")
    void deveCriarNotaFiscalComUmItem() {
        NotaFiscalItem item = new NotaFiscalItem(
                BigDecimal.TEN,
                BigDecimal.ONE,
                BigDecimal.ZERO
        );

        NotaFiscal notaFiscal = new NotaFiscal(List.of(item),new Filial(),new Pessoa());

        assertThat(notaFiscal.getItens()).hasSize(1);
    }

    @Test
    @DisplayName("Nao deve criar nota fiscal com lista de itens vazia")
    void naoDeveCriarNotaFiscalComListaDeItensVazia() {

        assertThrows(
                RuntimeException.class,
                () -> new NotaFiscal(List.of(),new Filial(),new Pessoa())
        );
    }

    @Test
    @DisplayName("Nao deve criar nota fiscal com itens null")
    void naoDeveCriarNotaFiscalComItensNull() {

        assertThrows(
                RuntimeException.class,
                () -> new NotaFiscal(null,new Filial(),new Pessoa())
        );
    }

    @Test
    @DisplayName("Deve iniciar nota fiscal com status INICIADA")
    void deveIniciarNotaFiscalComStatusIniciada() {

        NotaFiscalItem item = new NotaFiscalItem(
                BigDecimal.TEN,
                BigDecimal.ONE,
                BigDecimal.ZERO
        );

        NotaFiscal notaFiscal = new NotaFiscal(List.of(item),new Filial(),new Pessoa());

        assertThat(notaFiscal.getStatus())
                .isEqualTo(StatusNotaFiscal.INICIADA);
    }

    @Test
    @DisplayName("Deve somar valor total da nota fiscal")
    void deveSomarValorTotalDaNotaFiscal() {

        List<NotaFiscalItem> itens = new ArrayList<>();

        itens.add(
                new NotaFiscalItem(
                        BigDecimal.TEN,
                        BigDecimal.ONE,
                        BigDecimal.ZERO
                )
        );

        itens.add(
                new NotaFiscalItem(
                        BigDecimal.TEN,
                        BigDecimal.TEN,
                        BigDecimal.ZERO
                )
        );

        NotaFiscal notaFiscal = new NotaFiscal(itens,new Filial(),new Pessoa());

        assertThat(notaFiscal.getValorNotaFiscal())
                .isEqualByComparingTo(BigDecimal.valueOf(110));
    }

    @Test
    @DisplayName("Deve adicionar item apos criacao da nota")
    void deveAdicionarItemAposCriacaoDaNota() {

        NotaFiscalItem itemInicial = new NotaFiscalItem(
                BigDecimal.TEN,
                BigDecimal.ONE,
                BigDecimal.ZERO
        );

        NotaFiscal notaFiscal = new NotaFiscal(List.of(itemInicial),new Filial(),new Pessoa());

        NotaFiscalItem novoItem = new NotaFiscalItem(
                BigDecimal.TEN,
                BigDecimal.ONE,
                BigDecimal.ZERO
        );

        notaFiscal.adicionarItem(novoItem);

        assertThat(notaFiscal.getItens()).hasSize(2);
    }

    @Test
    @DisplayName("Deve atualizar valor total ao adicionar novo item")
    void deveAtualizarValorTotalAoAdicionarNovoItem() {

        NotaFiscalItem itemInicial = new NotaFiscalItem(
                BigDecimal.TEN,
                BigDecimal.ONE,
                BigDecimal.ZERO
        );

        NotaFiscal notaFiscal = new NotaFiscal(List.of(itemInicial),new Filial(),new Pessoa());

        NotaFiscalItem novoItem = new NotaFiscalItem(
                BigDecimal.TEN,
                BigDecimal.TEN,
                BigDecimal.ZERO
        );

        notaFiscal.adicionarItem(novoItem);

        assertThat(notaFiscal.getValorNotaFiscal())
                .isEqualByComparingTo(BigDecimal.valueOf(110));
    }

    @Test
    @DisplayName("Nao deve adicionar item null")
    void naoDeveAdicionarItemNull() {

        NotaFiscalItem itemInicial = new NotaFiscalItem(
                BigDecimal.TEN,
                BigDecimal.ONE,
                BigDecimal.ZERO
        );

        NotaFiscal notaFiscal = new NotaFiscal(List.of(itemInicial),new Filial(),new Pessoa());

        assertThrows(
                RuntimeException.class,
                () -> notaFiscal.adicionarItem(null)
        );
    }

    @Test
    @DisplayName("Deve alterar status para EMITIDA ao emitir nota")
    void deveAlterarStatusParaEmitidaAoEmitirNota() {

        NotaFiscal notaFiscal = criarNotaFiscalValida();

        notaFiscal.emitir();

        assertThat(notaFiscal.getStatus())
                .isEqualTo(StatusNotaFiscal.EMITIDA);
    }

    @Test
    @DisplayName("Deve preencher data emissao ao emitir nota")
    void devePreencherDataEmissaoAoEmitirNota() {

        NotaFiscal notaFiscal = criarNotaFiscalValida();

        notaFiscal.emitir();

        assertNotNull(notaFiscal.getDataEmissao());
    }

    @Test
    @DisplayName("Nao deve permitir adicionar item apos emissao")
    void naoDevePermitirAdicionarItemAposEmissao() {

        NotaFiscal notaFiscal = criarNotaFiscalValida();

        notaFiscal.emitir();

        NotaFiscalItem novoItem = new NotaFiscalItem(
                BigDecimal.TEN,
                BigDecimal.ONE,
                BigDecimal.ZERO
        );

        assertThrows(
                RuntimeException.class,
                () -> notaFiscal.adicionarItem(novoItem)
        );
    }

    @Test
    @DisplayName("Nao deve permitir emitir nota ja emitida")
    void naoDevePermitirEmitirNotaJaEmitida() {

        NotaFiscal notaFiscal = criarNotaFiscalValida();

        notaFiscal.emitir();

        assertThrows(
                RuntimeException.class,
                notaFiscal::emitir
        );
    }

    private NotaFiscal criarNotaFiscalValida() {

        NotaFiscalItem item = new NotaFiscalItem(
                BigDecimal.TEN,
                BigDecimal.ONE,
                BigDecimal.ZERO
        );

       return new NotaFiscal(List.of(item),new Filial(),new Pessoa());
    }
}