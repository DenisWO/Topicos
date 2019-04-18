package com.dwo.pedidos.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dwo.pedidos.connection.Conexao;
import com.dwo.pedidos.model.bean.Pedido;
import com.dwo.pedidos.model.bean.Produto;

import java.util.ArrayList;

public class PedidoDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;
    private String tabela;
    private String query;
    private Context context;

    public  PedidoDAO(Context context){
        this.conexao = new Conexao(context);
        this.context = context;
        this.banco = this.conexao.getWritableDatabase();
        this.tabela = "PEDIDO";
    }

    public long inserirPedido(Pedido pedido){
        ContentValues values = new ContentValues();
        values.put("idCliente", pedido.getCliente().getIdCliente());
        values.put("idProduto", pedido.getItem().getIdProduto() );

        return banco.insert(this.tabela, null, values);
    }

    public ArrayList<Pedido> selecionaTodosPedidos(){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String[] args = {"idPedido", "idCliente", "idProduto"};
        Cursor cursor = this.banco.query(this.tabela, args, null, null, null, null, null);

        while(cursor.moveToNext()){
            ClienteDAO clienteDAO = new ClienteDAO(this.context);
            ProdutoDAO produtoDAO = new ProdutoDAO(this.context);
            Pedido pedido = new Pedido();

            pedido.setIdPedido(cursor.getInt(0));
            pedido.setCliente(clienteDAO.selecionaClienteById(cursor.getInt(1)));
            pedido.setItem(produtoDAO.selecionaProdutoById(cursor.getInt(2)));

            pedidos.add(pedido);
        }

        return pedidos;
    }

    public Pedido selecionaPedidoById(int id){
        Pedido pedido = new Pedido();
        String[] args = {"idPedido", "idCliente"};
        String[] params = {String.valueOf(id)};
        Cursor cursor = this.banco.query(this.tabela, args, "IDPEDIDO = ?", params, null, null, null);

        if(cursor.moveToNext()){
            ClienteDAO clienteDAO = new ClienteDAO(this.context);
            ProdutoDAO produtoDAO = new ProdutoDAO(this.context);

            pedido.setIdPedido(cursor.getInt(0));
            pedido.setCliente(clienteDAO.selecionaClienteById(cursor.getInt(1)));
            pedido.setItem(produtoDAO.selecionaProdutoById(cursor.getInt(2)));
        }
        return pedido;
    }
    public void deletarTodosPedidos(){
        this.banco.delete(this.tabela, null, null);
        this.banco.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + this.tabela + "'");
    }

}
