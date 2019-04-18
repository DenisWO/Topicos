package com.dwo.pedidos.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.dwo.pedidos.connection.Conexao;
import com.dwo.pedidos.model.bean.Produto;

import java.security.ProtectionDomain;
import java.util.ArrayList;

public class ProdutoDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;
    private String tabela;
    private String query;
    private Context context;

    public ProdutoDAO(Context context){
        this.conexao = new Conexao(context);
        this.context = context;
        this.banco = this.conexao.getWritableDatabase();
        this.tabela = "PRODUTO";
    }

    public long inserirProduto(Produto produto){
        ContentValues values = new ContentValues();
        values.put("descricao", produto.getDescricao());
        values.put("preco", produto.getPreco());

        return banco.insert(this.tabela, null, values);
    }

    public ArrayList<Produto> selecionaTodosProdutos(){
        ArrayList<Produto> produtos = new ArrayList<>();
        String[] args = {"idProduto", "descricao", "preco"};
        Cursor cursor = this.banco.query(this.tabela, args, null, null, null, null, null, null);

        while(cursor.moveToNext()){
            Produto produto = new Produto();
            produto.setIdProduto(cursor.getInt(0));
            produto.setDescricao(cursor.getString(1));
            produto.setPreco(cursor.getDouble(2));

            produtos.add(produto);
        }

        return produtos;
    }
    public ArrayList<String> selecionaDescricaoTodosProdutos(){
        ArrayList<String> produtos = new ArrayList<>();
        String[] args = {"idProduto", "descricao"};
        Cursor cursor = this.banco.query(this.tabela, args, null, null, null, null, null);

        while(cursor.moveToNext()){
            produtos.add(String.valueOf(cursor.getInt(0)) + cursor.getString(1));
        }

        return produtos;
    }
    public Produto selecionaProdutoById(int id){
        Produto produto = new Produto();
        String[] args = {"idProduto", "descricao", "preco"};
        String[] params = {String.valueOf(id)};
        Cursor cursor = this.banco.query(this.tabela, args, "IDPRODUTO = ?", params, null, null, null, null);
        if(cursor.moveToNext()){
            produto.setIdProduto(cursor.getInt(0));
            produto.setDescricao(cursor.getString(1));
            produto.setPreco(cursor.getDouble(2));
        }

        return produto;
    }
    public void deletarTodosProdutos(){
        this.banco.delete(this.tabela, null, null);
        this.banco.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + this.tabela + "'");
    }

}
