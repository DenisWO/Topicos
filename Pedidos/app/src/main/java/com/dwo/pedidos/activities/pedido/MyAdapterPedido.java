package com.dwo.pedidos.activities.pedido;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dwo.pedidos.R;
import com.dwo.pedidos.model.bean.Pedido;

import java.util.List;

public class MyAdapterPedido extends RecyclerView.Adapter<MyAdapterPedido.MeuViewHolder>{

    public static RecyclerInterfacePedido recyclerInterfacePedido;
    private Context context;
    private List<Pedido> pedidos;

    public MyAdapterPedido(Context context, List pedidos, RecyclerInterfacePedido clickRecyclerInterfacePedido){
        this.context = context;
        this.pedidos = pedidos;
        this.recyclerInterfacePedido = clickRecyclerInterfacePedido;

    }
    @NonNull
    @Override
    public MeuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_pedidos, viewGroup, false);
        return new MeuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MeuViewHolder viewHolder, final int i) {
        Pedido pedido = this.pedidos.get(i);

        viewHolder.txtIdPedido.setText("Codigo do pedido: " + String.valueOf(pedido.getIdPedido()));
        viewHolder.txtDescProduto.setText("Produto: " + String.valueOf(pedido.getItem().getIdProduto()) + " - " + pedido.getItem().getDescricao());
        viewHolder.txtPreco.setText("Preço: " + String.valueOf(pedido.getItem().getPreco()));
    }

    @Override
    public int getItemCount() {
        return this.pedidos.size();
    }

    public class MeuViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtIdPedido;
        protected TextView txtDescProduto;
        protected TextView txtPreco;

        public MeuViewHolder(final View itemView) {
            super(itemView);
            txtIdPedido =  itemView.findViewById(R.id.txtIdPedido);
            txtDescProduto =  itemView.findViewById(R.id.txtDescProduto);
            txtPreco = itemView.findViewById(R.id.txtPrecoProduto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerInterfacePedido.onItemClick(pedidos.get(getLayoutPosition()));
                }
            });
        }
    }
}
