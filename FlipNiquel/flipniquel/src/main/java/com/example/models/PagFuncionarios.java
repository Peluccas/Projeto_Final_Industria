package com.example.models;
import java.sql.Date;


public class PagFuncionarios {
    private int id;
    private String fk_funcionarios;
    private String fk_setor;
    private Date data_pagto;
    private double salario_base;
    private double descontos;
    private double valor_liquido;
    private boolean status;


public PagFuncionarios(int id, String fk_funcionarios, String fk_setor, Date data_pagto, double salario_base, double descontos, double valor_liquido, boolean status){
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

public String getFk_funcionarios(){
    return fk_funcionarios;
}

public void setFk_funcionarios(String fk_funcionarios){
    this.fk_funcionarios = fk_funcionarios;
}

public String getFk_setor(){
    return fk_setor;
}

public void setFk_setor(String fk_setor){
    this.fk_setor = fk_setor;
}

public Date getData_pagto(){
    return data_pagto;
}

public void setData_pagto(Date data_pagto){
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