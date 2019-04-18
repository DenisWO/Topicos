package com.dwo.pedidos.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dwo.pedidos.R;
import com.dwo.pedidos.activities.pedido.Pedidos;
import com.dwo.pedidos.model.bean.Cliente;
import com.dwo.pedidos.model.dao.ClienteDAO;

public class CadastroCliente extends AppCompatActivity {
    private EditText edtNome;
    private EditText edtSobrenome;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtConfirmacaoSenha;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        edtNome = this.findViewById(R.id.edtNome);
        edtSobrenome = this.findViewById(R.id.edtSobrenome);
        edtEmail = this.findViewById(R.id.edtEmail);
        edtSenha = this.findViewById(R.id.edtSenha);
        edtConfirmacaoSenha = this.findViewById(R.id.edtConfirmacaoSenha);
        btnCadastrar = this.findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarCampos()){
                    return;
                }
                if(edtSenha.getText().toString().equals(edtConfirmacaoSenha.getText().toString())){
                    Cliente cliente = new Cliente();
                    ClienteDAO clienteDAO = new ClienteDAO(CadastroCliente.this);

                    cliente.setNome(edtNome.getText().toString());
                    cliente.setSobrenome(edtSobrenome.getText().toString());
                    cliente.setEmail(edtEmail.getText().toString());
                    cliente.setSenha(edtSenha.getText().toString());

                    if(clienteDAO.inserirCliente(cliente) != 0){
                        Toast.makeText(CadastroCliente.this, "Cadastro com sucesso!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CadastroCliente.this, Pedidos.class));
                    }
                }
                else{
                    Toast.makeText(CadastroCliente.this, "Senhas não são iguais!", Toast.LENGTH_SHORT).show();
                    edtSenha.setText("");
                    edtConfirmacaoSenha.setText("");
                    edtSenha.requestFocus();
                }
            }
        });
    }

    private boolean validarCampos(){
        if(this.edtNome.getText().toString().equals("")){
            Toast.makeText(CadastroCliente.this, "Nome não pode ser vazio", Toast.LENGTH_LONG).show();
            this.edtNome.requestFocus();
            return false;
        }
        if(this.edtSobrenome.getText().toString().equals("")){
            Toast.makeText(CadastroCliente.this, "Sobrenome não pode ser vazio!", Toast.LENGTH_LONG).show();
            this.edtSobrenome.requestFocus();
            return false;
        }
        if(this.edtEmail.getText().toString().equals("")){
            Toast.makeText(CadastroCliente.this, "Email não pode ser vazio!", Toast.LENGTH_LONG).show();
            this.edtEmail.requestFocus();
            return false;
        }
        if(this.edtSenha.getText().toString().equals("")){
            Toast.makeText(CadastroCliente.this, "Senha não pode vazia!", Toast.LENGTH_LONG).show();
            this.edtSenha.requestFocus();
            return false;
        }
        if(this.edtConfirmacaoSenha.getText().toString().equals("")){
            Toast.makeText(CadastroCliente.this, "Confirmação de senha não pode ser vazia!", Toast.LENGTH_SHORT).show();
            this.edtConfirmacaoSenha.requestFocus();
            return false;
        }

        return true;
    }
}
