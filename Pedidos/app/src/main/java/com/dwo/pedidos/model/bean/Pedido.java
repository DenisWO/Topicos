package com.dwo.pedidos.model.bean;

import java.util.ArrayList;

public class Pedido {
    private int idPedido;
    private Cliente cliente;
    private Produto item;

    public Pedido(Cliente cliente, Produto item) {
        this.cliente = cliente;
        this.item = item;
    }

    public Pedido(){
        this.item = null;
        this.cliente = null;
    }
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getItem() {
        return item;
    }

    public void setItem(Produto item) {
        this.item = item;
    }
}
