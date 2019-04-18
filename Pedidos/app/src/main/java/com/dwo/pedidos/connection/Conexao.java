package com.dwo.pedidos.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Conexao extends SQLiteOpenHelper {
    private static final String name = "db";
    private static final int version = 1;
    private String scriptGeral = "";
    private ArrayList<String> scripts;


    public Conexao(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CLIENTE(" +
                " IDCLIENTE INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NOME VARCHAR(50), SOBRENOME VARCHAR(50)," +
                " EMAIL VARCHAR(50), SENHA VARCHAR(50));");

        db.execSQL("CREATE TABLE PRODUTO(" +
                " IDPRODUTO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " DESCRICAO VARCHAR(50), PRECO DOUBLE)");

        db.execSQL("CREATE TABLE PEDIDO ("+
                " IDPEDIDO INTEGER PRIMARY KEY AUTOINCREMENT," +
                " IDCLIENTE INTEGER, " +
                " IDPRODUTO INTEGER," +
                " FOREIGN KEY (IDCLIENTE) REFERENCES CLIENTE (IDCLIENTE) ON DELETE CASCADE ON UPDATE CASCADE,"
                + "FOREIGN KEY (IDPRODUTO) REFERENCES PRODUTO (IDPRODUTO) ON DELETE CASCADE ON UPDATE CASCADE)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
