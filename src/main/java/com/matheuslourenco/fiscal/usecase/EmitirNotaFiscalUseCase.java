package com.matheuslourenco.fiscal.usecase;

import com.matheuslourenco.fiscal.domain.nota.EmitirNotaFiscalCommand;
import com.matheuslourenco.fiscal.domain.nota.NotaFiscal;

public class EmitirNotaFiscalUseCase {

    public NotaFiscal executar(EmitirNotaFiscalCommand command){
        if(command == null){
            throw new RuntimeException();
        }
        NotaFiscal notaFiscal = new NotaFiscal(command.getItens(),command.getFilial(),command.getCliente());
        notaFiscal.emitir();
        return notaFiscal;
    }
}
