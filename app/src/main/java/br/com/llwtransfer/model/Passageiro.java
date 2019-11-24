package br.com.llwtransfer.model;

public class Passageiro
{
    private String nome = null;
    private String endereco = null;
    private String telefone = null;
    private String email = null;
    private String foto = null;

    public Passageiro(String nome, String endereco, String telefone, String email, String foto) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.foto = foto;
    }

    public Passageiro() {

    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) { this.foto = foto; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
