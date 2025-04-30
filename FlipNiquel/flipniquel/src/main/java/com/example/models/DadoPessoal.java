package com.example.models;

public class DadoPessoal {
    private int id;
    private String nome_completo;
    private String data_nascimento;
    private String sexo;
    private String estado_civil;
    private String conjuge;
    private String dependentes;
    private String nacionalidade;
    private String naturalidade;
    private String cpf;
    private String rg;
    private String endereco;
    private String telefone;
    private String email;
    private String filiacao;
    private String tipo_sanguineo;
    private String contato_emergencia;

    public DadoPessoal(int id, String nome_completo, String data_nascimento, String sexo,  String estado_civil, String conjuge, String dependentes, String nacionalidade, String naturalidade,
    String cpf, String rg, String endereco, String telefone, String email, String filiacao, String tipo_sanguineo, String contato_emergencia) {
        this.id = id;
        this.nome_completo = nome_completo;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
        this.estado_civil = estado_civil;
        this.conjuge = conjuge;
        this.dependentes = dependentes;
        this.nacionalidade = nacionalidade;
        this.naturalidade = naturalidade;
        this.cpf = cpf;
        this.rg = rg;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.filiacao = filiacao;
        this.tipo_sanguineo = tipo_sanguineo;
        this.contato_emergencia = contato_emergencia;
}

public int getId() {
    return id;
}
public void setId(int id){
    this.id = id;
}

public String getNome_completo() {
    return nome_completo;
}
public void setNome_completo(String nome_completo){
    this.nome_completo = nome_completo;
}

public String getData_nascimento() {
    return data_nascimento;
}
public void setData_nascimento(String data_nascimento){
    this.data_nascimento = data_nascimento;
}

public String getSexo() {
    return sexo;
}
public void setSexo(String sexo){
    this.sexo = sexo;
}

public String getEstado_civil() {
    return estado_civil;
}
public void setEstado_civil(String estado_civil){
    this.estado_civil = estado_civil;
}

public String getConjuge() {
    return conjuge;
}
public void setConjuge(String conjuge){
    this.conjuge = conjuge;
}

public String getDependentes(){
    return dependentes;
}
public void setDependentes(String dependentes){
    this.dependentes = dependentes;
}

public String getNacionalidade() {
    return nacionalidade;
}
public void setNacionalidade(String nacionalidade){
    this.nacionalidade = nacionalidade;
}

public String getNaturalidade() {
    return naturalidade;
}
public void setNaturalidade(String naturalidade){
    this.naturalidade = naturalidade;
}

public String getCpf(){
    return cpf;
}
public void setCpf(String cpf){
    this.cpf = cpf;
}

public String getRg() {
    return rg;
}
public void setRg(String rg){
    this.rg = rg;
}

public String getEndereco() {
    return endereco;
}
public void setEndereco(String endereco){
    this.endereco = endereco;
}

public String getTelefone(){
    return telefone;
}
public void setTelefone(String telefone){
    this.telefone = telefone;
}

public String getEmail(){
    return email;
}
public void setEmail(String email){
    this.email = email;
}

public String getFiliacao() {
    return filiacao;
}
public void setFiliacao(String filiacao){
    this.filiacao = filiacao;
}

public String getTipo_sanguineo(){
    return tipo_sanguineo;
}
public void setTipo_sanguineo(String tipo_sanguineo){
    this.tipo_sanguineo = tipo_sanguineo;
}

public String getContato_emergencia() {
    return contato_emergencia;
}
public void setContato_emergencia(String contato_emergencia){
    this.contato_emergencia = contato_emergencia;
}

}