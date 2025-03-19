package com.example.models;


public class PagFuncionarios {
    private int id;
    private int fk_funcionarios;
    private int fk_setor;
    private String data_pagto;
    private double salario_base;
    private double descontos;
    private double valor_liquido;
    private boolean status;


public PagFuncionarios(int id, int fk_funcionarios, int fk_setor, String data_pagto, double salario_base, double descontos, double valor_liquido, boolean status){
    this.id = id;
    this.fk_funcionarios = fk_funcionarios;
    this.fk_setor = fk_setor;
    this.data_pagto = data_pagto;
    this.salario_base = salario_base;
    this.descontos = descontos;
    this.valor_liquido = valor_liquido;
    this.status = status;
}

public int getId(){
    return id;
}

public void  setId(int id){
    this.id = id;
}

public int getFk_funcionarios(){
    return fk_funcionarios;
}

public void setFk_funcionarios(int fk_funcionarios){
    this.fk_funcionarios = fk_funcionarios;
}

public int getFk_setor(){
    return fk_setor;
}

public void setFk_setor(int fk_setor){
    this.fk_setor = fk_setor;
}

public String getData_pagto(){
    return data_pagto;
}

public void setData_pagto(String data_pagto){
    this.data_pagto = data_pagto;
}

public double getSalario_base(){
    return salario_base;
}

public void setSalario_base(double salario_base){
    this.salario_base = salario_base;
}

public double getDescontos(){
    return descontos;
}

public void setDescontos(double descontos){
    this.descontos = descontos;
}

public double getValor_liquido(){
    return valor_liquido;
}

public void setValor_liquido(double valor_liquido){
    this.valor_liquido = valor_liquido;
}

public boolean getStatus (){
    return status;
}

public void setStatus(boolean status){
    this.status = status;

}


}