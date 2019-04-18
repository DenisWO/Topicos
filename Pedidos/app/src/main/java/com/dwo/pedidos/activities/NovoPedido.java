package com.dwo.pedidos.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dwo.pedidos.R;
import com.dwo.pedidos.activities.pedido.MyAdapterPedido;
import com.dwo.pedidos.model.bean.Pedido;
import com.dwo.pedidos.model.bean.Produto;
import com.dwo.pedidos.model.dao.ClienteDAO;
import com.dwo.pedidos.model.dao.PedidoDAO;
import com.dwo.pedidos.model.dao.ProdutoDAO;

import java.util.ArrayList;
import java.util.List;

public class NovoPedido extends AppCompatActivity {

    private Spinner spinner;
    private TextView txtPreco;
    private ProdutoDAO produtoDAO;
    private ArrayList<String> elementos;
    private Button btnComprar;
    private Produto produtoComprado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_pedido);

        this.spinner = findViewById(R.id.spProdutos);
        this.txtPreco = findViewById(R.id.txtPreco);
        this.btnComprar = findViewById(R.id.btnComprar);

        this.btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PedidoDAO pedidoDAO = new PedidoDAO(NovoPedido.this);
                ClienteDAO clienteDAO = new ClienteDAO(NovoPedido.this);
                if(produtoComprado.getDescricao().equals("") || produtoComprado.getPreco() == 0.00){
                    Toast.makeText(NovoPedido.this, "Um ocorreu ocorreu!", Toast.LENGTH_LONG).show();
                }
                else{
                    if(produtoDAO == null){
                        produtoDAO = new ProdutoDAO(NovoPedido.this);
                    }
                    pedidoDAO.inserirPedido(new Pedido(clienteDAO.selecionaClienteByEmail(MainActivity.chaveEmail), produtoComprado));
                    Toast.makeText(NovoPedido.this, "Novo pedido realizado", Toast.LENGTH_LONG).show();
                }
            }
        });

        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Produto produto;
                if(produtoDAO == null){
                    produtoDAO = new ProdutoDAO(NovoPedido.this);
                }

                String item = parent.getItemAtPosition(position).toString();
                int index = Integer.parseInt(item.substring(0,2));

                produto = produtoDAO.selecionaProdutoById(index);
                produtoComprado = produto;
                txtPreco.setText("Preço : " + String.format(String.valueOf(produto.getPreco()), "###,###,##0.00"));

                Toast.makeText(NovoPedido.this, "Item selecionado: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        preencheElementos();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elementos);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(arrayAdapter);
    }

    private void preencheElementos(){
        this.produtoDAO = new ProdutoDAO(this);
        this.elementos = new ArrayList<>();

        for(Produto p : this.produtoDAO.selecionaTodosProdutos()){
            if(p.getIdProduto() < 10 ){
                elementos.add("0" + String.valueOf(p.getIdProduto()) + " - " + p.getDescricao());
            }
            else{
                elementos.add(String.valueOf(p.getIdProduto()) + "  - " + p.getDescricao());
            }
        }
    }


}
