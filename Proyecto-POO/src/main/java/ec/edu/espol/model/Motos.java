/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.model;

import ec.edu.espol.util.Util;
import java.io.Serializable;
import java.util.Scanner;


/**
 *
 * @author Josue Vera
 */
public class Motos extends Vehiculo implements Serializable{

    private static final long serialVersionUID = -12345L;

    public Motos(String placa, String marca, String modelo, String motor, int año, double recorrido, String color, String combustible, double precio) {
        super(placa, marca, modelo, motor, año, recorrido, color, combustible, precio);
    }
    public static void DatosMoto(Scanner sc){
        //sc.nextLine();
        System.out.println("Ingrese la placa del vehiculo: ");
        String placa =sc.nextLine();
        
        while(!Util.placaEsValida("vehiculos.ser",placa)){
            System.out.println("Placa ya registrada en el sistema, por favor ingrese otra: ");
            placa=sc.nextLine();
        }
        System.out.println("Ingrese la marca del marca: ");
        String marca= sc.nextLine();
        System.out.println("Ingrese el modelo del modelo: ");
        String modelo= sc.nextLine();
        System.out.println("Ingrese el tipo de motor que tiene el vehiculo: ");
        String motor=sc.nextLine();
        System.out.println("Ingrese el año del vehiculo: ");
        int año= sc.nextInt();
        System.out.println("Ingrese el kilometraje del vehiculo: ");
        double recorrido=sc.nextDouble();
        sc.nextLine();
        System.out.println("Ingrese el color del vehiculo: ");
        String color=sc.nextLine();
        System.out.println("Ingrese el tipo de combustible que usa el vehiculo: ");
        String combustible=sc.nextLine();
        System.out.println("Ingrese el precio de su vehiculo: ");
        double precio= sc.nextDouble();
        Vehiculo moto= new Motos(placa,marca,modelo,motor,año,recorrido,color,combustible,precio);
        moto.saveFile("vehiculos.ser");
        moto.saveFile("motos.ser");
    }

    }
    
  
