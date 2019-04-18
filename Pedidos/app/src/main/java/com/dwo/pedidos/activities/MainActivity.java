package com.dwo.pedidos.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dwo.pedidos.R;
import com.dwo.pedidos.activities.pedido.Pedidos;
import com.dwo.pedidos.model.bean.Cliente;
import com.dwo.pedidos.model.bean.Pedido;
import com.dwo.pedidos.model.bean.Produto;
import com.dwo.pedidos.model.dao.ClienteDAO;
import com.dwo.pedidos.model.dao.PedidoDAO;
import com.dwo.pedidos.model.dao.ProdutoDAO;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogin;
    private Button btnCriarConta;
    private Cliente cliente;
    private ClienteDAO clienteDAO;
    public static final String PREFERENCES = "Preferencias";
    public static final String chaveEmail = "chaveEmail";
    public static final boolean chaveGuardarDados = false;
    private SharedPreferences configs;
    private SharedPreferences.Editor editorConfigs;
    private CheckBox chkLembrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.edtEmail = this.findViewById(R.id.edtEmail);
        this.edtSenha = this.findViewById(R.id.edtSenha);
        this.btnLogin = this.findViewById(R.id.btnLogin);
        this.btnCriarConta = this.findViewById(R.id.btnCriarConta);
        this.chkLembrar = this.findViewById(R.id.chkLembrar);

        this.configs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        this.editorConfigs = this.configs.edit();

        preencheDados();
        //preencheCasoTeste();

        if(this.configs.getBoolean(String.valueOf(chaveGuardarDados), false)){
            this.edtEmail.setText(this.configs.getString(chaveEmail, ""));
            startActivity(new Intent(MainActivity.this, Pedidos.class));
        }
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClienteDAO clienteDAO = new ClienteDAO(MainActivity.this);
                Cliente login = clienteDAO.selecionaClienteByEmail(edtEmail.getText().toString());

                if(login.getEmail().equals("") || login.getSenha().equals("")){
                    Toast.makeText(MainActivity.this, "Campos não podem ser vazios!", Toast.LENGTH_LONG).show();
                    edtEmail.requestFocus();
                    return;
                }

                if(login.getSenha().equals(edtSenha.getText().toString())){
                    Toast.makeText(MainActivity.this, "Logado com sucesso!", Toast.LENGTH_LONG).show();

                    if(chkLembrar.isChecked()){
                        editorConfigs.putString(chaveEmail, edtEmail.getText().toString());
                        editorConfigs.putBoolean(String.valueOf(chaveGuardarDados), true);
                        editorConfigs.commit();
                    }
                    else{
                        editorConfigs.putString(chaveEmail, "");
                        editorConfigs.putBoolean(String.valueOf(chaveGuardarDados), false);
                        editorConfigs.commit();
                    }

                    startActivity(new Intent(MainActivity.this, Pedidos.class));
                }
                else{
                    Toast.makeText(MainActivity.this, "Não foi possível fazer login!", Toast.LENGTH_SHORT).show();
                    edtEmail.requestFocus();
                    return;
                }
            }
        });

        this.btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CadastroCliente.class));
            }
        });

    }
    private void preencheCasoTeste(){
        this.edtEmail.setText("amandasilva@gmail.com");
        this.edtSenha.setText("123456");
    }

    private void preencheDados(){
        preencheClientes();
        preencheProdutos();
        preencherPedidos();
    }

    private void preencheClientes(){
        ClienteDAO clienteDAO = new ClienteDAO(this);
        Cliente amanda = new Cliente("Amanda", "Silva", "amandasilva@gmail.com", "123456");
        Cliente bianca = new Cliente("Bianca", "Souza", "biancasouza@gmail.com", "123456");
        Cliente camila = new Cliente("Camila", "Guimaraes", "camilagui@gmail.com", "123456");
        Cliente daniela = new Cliente("Daniela", "Almeida", "danielaalmeida@gmail.com", "123456");
        Cliente ellen = new Cliente("Ellen", "Santos", "ellensantos@gmail.com", "123456");

        clienteDAO.deletarTodosClientes();
        clienteDAO.inserirCliente(amanda);
        clienteDAO.inserirCliente(bianca);
        clienteDAO.inserirCliente(camila);
        clienteDAO.inserirCliente(daniela);
        clienteDAO.inserirCliente(ellen);

    }

    private void preencheProdutos(){
        ProdutoDAO produtoDAO = new ProdutoDAO(this);
        Produto tenis = new Produto("Tenis Nike", 89.90);
        Produto calcaJeans = new Produto("Calca jeans skinny", 129.90);
        Produto camisa = new Produto("Camisa de malha Vingadores", 29.90);
        Produto sandalia = new Produto("Sandalia", 19.90);
        Produto brincos = new Produto("Par de brincos", 1.99);

        produtoDAO.deletarTodosProdutos();
        produtoDAO.inserirProduto(tenis);
        produtoDAO.inserirProduto(calcaJeans);
        produtoDAO.inserirProduto(camisa);
        produtoDAO.inserirProduto(sandalia);
        produtoDAO.inserirProduto(brincos);

    }

    private void preencherPedidos(){
        ProdutoDAO produtoDAO = new ProdutoDAO(this);
        ClienteDAO clienteDAO = new ClienteDAO(this);
        PedidoDAO pedidoDAO = new PedidoDAO(this);
        Pedido pedido = new Pedido();

        pedidoDAO.deletarTodosPedidos();
        pedido.setCliente(clienteDAO.selecionaClienteById(1)); //selecionando cliente 1 para realizar os pedidos
        for(Produto p : produtoDAO.selecionaTodosProdutos()){
            pedido.setItem(p);
            pedidoDAO.inserirPedido(pedido);
        }


    }
}
