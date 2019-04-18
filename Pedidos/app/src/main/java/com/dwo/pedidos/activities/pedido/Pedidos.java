package com.dwo.pedidos.activities.pedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dwo.pedidos.R;
import com.dwo.pedidos.activities.NovoPedido;
import com.dwo.pedidos.model.dao.PedidoDAO;

public class Pedidos extends AppCompatActivity implements RecyclerInterfacePedido {

    RecyclerView meuRecyclerView;
    LinearLayoutManager meuLayoutManager;
    MyAdapterPedido adapter;
    PedidoDAO pedidos;
    TextView txtQtdePedidos;
    Button btnNovoPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        this.pedidos = new PedidoDAO(this);
        this.txtQtdePedidos = findViewById(R.id.txtQtdePedidos);
        this.btnNovoPedido = findViewById(R.id.btnNovoPedido);

        meuRecyclerView = (RecyclerView) findViewById(R.id.rvPedido);
        meuLayoutManager = new LinearLayoutManager(this);
        meuRecyclerView.setLayoutManager(meuLayoutManager);
        adapter = new MyAdapterPedido(this, this.pedidos.selecionaTodosPedidos(), this);
        meuRecyclerView.setAdapter(adapter);

        this.btnNovoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Pedidos.this, "Novo pedido!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Pedidos.this, NovoPedido.class));
            }
        });
        if(this.pedidos.selecionaTodosPedidos().size() > 1){
            this.txtQtdePedidos.setText("Voce possui " + this.pedidos.selecionaTodosPedidos().size() + " pedidos");
        }
        else{
            this.txtQtdePedidos.setText("Voce possui " + this.pedidos.selecionaTodosPedidos().size() + " pedido");
        }

    }

    @Override
    public void onItemClick(Object object) {
//
//        Pedido pedido = (Pedido) object;
//        String nome = contato.getNome();
//        String email = contato.getEmail();
//        String telefone = contato.getTelefone();
//
//        editNome.setText(nome);
//        editEmail.setText(email);
//        editFone.setText(telefone);
        Toast.makeText(this, "On item click", Toast.LENGTH_LONG).show();

    }
}
