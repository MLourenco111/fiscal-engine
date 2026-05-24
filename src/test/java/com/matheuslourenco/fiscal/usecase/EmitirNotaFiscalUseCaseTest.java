package com.matheuslourenco.fiscal.usecase;

import com.matheuslourenco.fiscal.domain.filial.Filial;
import com.matheuslourenco.fiscal.domain.nota.EmitirNotaFiscalCommand;
import com.matheuslourenco.fiscal.domain.nota.NotaFiscal;
import com.matheuslourenco.fiscal.domain.nota.NotaFiscalItem;
import com.matheuslourenco.fiscal.domain.nota.StatusNotaFiscal;
import com.matheuslourenco.fiscal.domain.pessoa.Pessoa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmitirNotaFiscalUseCaseTest {

    @Test
    @DisplayName("Deve retornar nota fiscal emitida")
    void deveRetornarNotaFiscalEmitida() {
        EmitirNotaFiscalCommand command = new EmitirNotaFiscalCommand(
                List.of(new NotaFiscalItem(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO)),
                new Filial(),
                new Pessoa()
        );

        EmitirNotaFiscalUseCase useCase = new EmitirNotaFiscalUseCase();
        NotaFiscal notaFiscal = useCase.executar(command);

        assertNotNull(notaFiscal);
        assertThat(notaFiscal.getStatus()).isEqualTo(StatusNotaFiscal.EMITIDA);
        assertNotNull(notaFiscal.getDataEmissao());
        assertThat(notaFiscal.getValorNotaFiscal()).isEqualByComparingTo(new BigDecimal("10"));
        assertThat(notaFiscal.getItens().size()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Nao deve executar com command nulo")
    void naoDeveExecutarComCommandNulo(){
        EmitirNotaFiscalUseCase useCase = new EmitirNotaFiscalUseCase();

        assertThrows(
                RuntimeException.class,
                () -> useCase.executar(null)
        );
    }
}
