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
public class Camioneta extends Vehiculo implements Serializable
{
    protected String vidrios;
    protected String transmision;
    protected String traccion;
    private static final long serialVersionUID = -12345L;

    public Camioneta(String vidrios, String placa, String marca, String modelo, String motor, int año, double recorrido, String color, String combustible, double precio, String transmision, String traccion) {
        super(placa, marca, modelo, motor, año, recorrido, color, combustible, precio);
        this.vidrios= vidrios;
        this.transmision= transmision;
        this.traccion= traccion;

    }

    public String getVidrios() {
        return vidrios;
    }

    public void setVidrios(String vidrios) {
        this.vidrios = vidrios;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public String getTraccion() {
        return traccion;
    }

    public void setTraccion(String traccion) {
        this.traccion = traccion;
    }
    
    public static void DatosCamioneta(Scanner sc){
        //sc.nextLine();
        System.out.println("Ingrese la placa del vehiculo: ");
        String placa =sc.nextLine();
        
        while(!Util.placaEsValida("vehiculos.ser",placa))
        {
            System.out.println("Placa ya registrada en el sistema, por favor ingrese otra: ");
            placa = sc.nextLine();
        }
        System.out.println("Ingrese la marca del vehiculo: ");
        String marca= sc.nextLine();
        System.out.println("Ingrese el modelo del vehiculo: ");
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
        System.out.println("Ingrese la transmision del vehiculo: ");
        String transmision=sc.nextLine();
        System.out.println("Ingrese que tipos de vidrio tine su vehiculo: ");
        String vidrios= sc.nextLine();
        System.out.println("Ingrese la traccion del vehiculo: ");
        String traccion=sc.nextLine();
        Vehiculo camioneta= new Camioneta(vidrios,placa,marca,modelo,motor,año,recorrido,color,combustible,precio,transmision,traccion);
        camioneta.saveFile("vehiculos.ser"); 
        camioneta.saveFile("camionetas.ser");
    }
}
