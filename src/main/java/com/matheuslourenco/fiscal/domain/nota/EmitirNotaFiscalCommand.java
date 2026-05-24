package com.matheuslourenco.fiscal.domain.nota;

import com.matheuslourenco.fiscal.domain.filial.Filial;
import com.matheuslourenco.fiscal.domain.pessoa.Pessoa;

import java.util.List;


public class EmitirNotaFiscalCommand {
    private final List<NotaFiscalItem> itens;
    private final Filial filial;
    private final Pessoa cliente;

    public EmitirNotaFiscalCommand(List<NotaFiscalItem> itens, Filial filial, Pessoa cliente) {
        if (itens == null || itens.isEmpty()) {
            throw new RuntimeException();
        }
        if (filial == null) {
            throw new RuntimeException();
        }
        if (cliente == null) {
            throw new RuntimeException();
        }
        this.itens = itens;
        this.filial = filial;
        this.cliente = cliente;
    }

    public List<NotaFiscalItem> getItens() {
        return itens;
    }

    public Filial getFilial() {
        return filial;
    }

    public Pessoa getCliente() {
        return cliente;
    }
}
