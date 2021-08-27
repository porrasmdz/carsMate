/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.model;

import ec.edu.espol.util.Rol;
import ec.edu.espol.util.Util;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Josue Vera + Andres Porras
 */
public class Comprador extends Persona implements Serializable{
    
    private static final long serialVersionUID = -5892814637268092625L;
    
    public Comprador(String nombres, String apellidos, String correo, String organizacion, String clave){
        super(nombres,apellidos,correo,organizacion,clave, Rol.COMPRADOR);
    }
    
    //FUNCION LISTA PERO SERA MODIFICADA EN EL JAVAFX
    public static void registarComprador(Scanner sc)
    {
        //Esto va a cambiar
        System.out.println("Ingrese sus nombres: ");
        String nombre = sc.nextLine();
        System.out.println("Ingrese sus Apellidos: ");
        String apellidos = sc.nextLine();
        System.out.println("Ingrese la Organizacion donde trabaja: ");
        String organizacion = sc.nextLine();
        System.out.println("Ingrese su correo electronico: ");
        String correo = sc.nextLine();
        while (!Util.correoEsValido(correo))
        {
            System.out.println("Ingrese correo electronico valido: ");
            correo = sc.nextLine();
        }
        
        while (Util.correoExiste("usuarios.ser", correo))
        {
            System.out.println("El correo ingresado ya esta registrado, ingrese otro correo valido: ");
            correo = sc.nextLine();
            
        }
        
        //Modificar con los campos del javaFX
        System.out.println("Ingrese su clave de acceso: ");
        String contrasena = sc.nextLine();

        
        
        System.out.println("Comprador registrado correctamente");
        
        try
        {
            Comprador c = new Comprador(nombre,apellidos,correo,organizacion,contrasena);
            c.saveFile("usuarios.ser");
        }
        catch(CorreoException e)
        {
            System.out.println("El correo electronico no es valido. Por favor intente de nuevo");
        }
    }
    
    //FUNCION LISTA COMPROBADA
    public void saveFile(String filename) 
    {
        ArrayList<Persona> usuarios = new ArrayList<>();
        usuarios = Util.readPersonasFile(filename);
        
        try(ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            usuarios.add(this);    
            outStream.writeObject(usuarios);
            outStream.close();
        }
        catch(FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        }
        catch(IOException ioe)
        {
            //ioe.printStackTrace();
        }
    }
    
    //FUNCION LISTA NO PROBADA
    public static ArrayList<Comprador> extraerCompradores(String filename)
    {
        ArrayList<Persona> listaPersonas = new ArrayList<>();
        ArrayList<Comprador> compradores = new ArrayList<>();
        try(ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(new File(filename))))
        {
            listaPersonas = (ArrayList<Persona>) inStream.readObject();
            for (Persona p : listaPersonas)
            {
                if (p.getRol()== Rol.COMPRADOR || p.getRol() == Rol.AMBOS)
                {
                    compradores.add((Comprador) p);
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        } 
        catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        }
        return compradores;
    }
    
    
    //REVISAR ESTA FUNCION - NO ESTA LISTA Y ESTA MAL HECHA
    public static void OfertarVehiculo(Scanner sc)
    {
        System.out.println("Ingrese tipo de vehiculo de su interes: ");
        String tipo=sc.nextLine();
        System.out.println("Ingrese rango de recorrido de busqueda");
        System.out.println("rango Minimo: ");
        double recorridoMin=sc.nextDouble();
        System.out.println("rango Maximo: ");
        double recorridoMax=sc.nextDouble();
        System.out.println("Ingrese rango del Año de busqueda");
        System.out.println("rango Minimo: ");
        int añoMin=sc.nextInt();
        System.out.println("rango Maximo: ");
        int añoMax=sc.nextInt();
        System.out.println("Ingrese rango del precio de busqueda");
        System.out.println("rango Minimo: ");
        double precioMin=sc.nextDouble();
        System.out.println("rango Maximo: ");
        double precioMax=sc.nextDouble();
        ArrayList<Vehiculo>vehiculosCondicion = new ArrayList<>();
        ArrayList<Vehiculo>VehiculossinRestriccion = Util.readVehiclesFile("vehiculos.ser");
        boolean siguiente=true;
        int indice=0;
        double valor;
            ////////////////////////////////////////////////////////////
            
        if(tipo.equalsIgnoreCase("auto")||tipo.equalsIgnoreCase("moto")||tipo.equalsIgnoreCase("camioneta"))
        {
            ArrayList<Vehiculo>VehiculoxTipo = Util.readVehiclesFile(tipo.toLowerCase()+"s.ser");
            if(recorridoMin >= 0 && recorridoMax > recorridoMin && 
                    añoMin >= 0 && añoMax > añoMin && precioMin >= 0 &&
                        precioMax>precioMin)
            {
                for(Vehiculo v:VehiculoxTipo)
                {
                    if(v.recorrido>=recorridoMin && v.recorrido<=recorridoMax && v.año>=añoMin &&v.año<=añoMax &&v.precio>=precioMin && v.precio<=precioMax)
                    {
                        vehiculosCondicion.add(v);
                    }
                }
                
                while(siguiente=true)    
                {
                    System.out.println("Vehiculo # "+indice+1);
                    System.out.println("Placa: "+vehiculosCondicion.get(indice).getPlaca());
                    System.out.println("Marca: "+vehiculosCondicion.get(indice).getMarca());
                    System.out.println("Modelo: "+vehiculosCondicion.get(indice).getModelo());
                    System.out.println("Tipo de motor: "+vehiculosCondicion.get(indice).getMotor());
                    System.out.println("Año: "+vehiculosCondicion.get(indice).getAño());
                    System.out.println("Recorrido: "+vehiculosCondicion.get(indice).getRecorrido());
                    System.out.println("Color: "+vehiculosCondicion.get(indice).getColor());
                    System.out.println("Tipo de Combustible: "+vehiculosCondicion.get(indice).getCombustible());
                    System.out.println("Precio: "+vehiculosCondicion.get(indice).getPrecio());
                    System.out.println("OFERTAR?: si desea ofertar pulse S, si no desea ofertar ingrese cualquier otra letra");
                    
                    String ofertar=sc.nextLine();
            ///////////////////////HASTA AQUI YA SE OBTUVO LA LISTA DE VEHICULOS/////////////////////
            
                    if(ofertar.equals("S")||ofertar.equals("s"))
                    {
                        System.out.println("Ingrese su correo: ");
                        String correo=sc.nextLine();
                        
                        while(!Util.correoExiste("usuarios.ser",correo))
                        {
                            System.out.println("Correo no existe");
                            correo = sc.nextLine();
                            
                        }
                        
                        System.out.println("Ingrese valor a ofertar: ");
                        valor = sc.nextDouble();
                        Oferta o = new Oferta(correo,valor,vehiculosCondicion.get(indice).getPlaca());
                       
                        /////////////////MODIFICAR A SERIALIZABLE/////////////
                        o.saveFile("ofertas.ser");
                        //Un comprador deberia poder ofertar mas veces por el mismo vehiculo no?
                        //vehiculosCondicion.remove(indice);
                    }   
                    
                    if(vehiculosCondicion.size()>1)
                    {
                        if(indice<vehiculosCondicion.size() &&indice==0)
                        {
                            System.out.println("Continuar? de ser asi marque S: ");
                            String desicion = sc.nextLine();
                            if(desicion.equals("S") ||desicion.equals("s"))
                                indice++;

                            System.out.println("Placa: "+vehiculosCondicion.get(indice).getPlaca());
                            System.out.println("Marca: "+vehiculosCondicion.get(indice).getMarca());
                            System.out.println("Modelo: "+vehiculosCondicion.get(indice).getModelo());
                            System.out.println("Tipo de motor: "+vehiculosCondicion.get(indice).getMotor());
                            System.out.println("Año: "+vehiculosCondicion.get(indice).getAño());
                            System.out.println("Recorrido: "+vehiculosCondicion.get(indice).getRecorrido());
                            System.out.println("Color: "+vehiculosCondicion.get(indice).getColor());
                            System.out.println("Tipo de Combustible: "+vehiculosCondicion.get(indice).getCombustible());
                            System.out.println("Precio: "+vehiculosCondicion.get(indice).getPrecio());    
                        }
                        if(indice>0 && indice<vehiculosCondicion.size()-1){
                            System.out.println("Continuar? de ser asi marque S, si desea regresar al vehiculo anterior marque R: ");
                            String desicion=sc.nextLine();
                            if(desicion.equals("S") ||desicion.equals("s"))
                                indice++;
                            if(desicion.equals("R") ||desicion.equals("r"))
                                indice--;

                            System.out.println("Placa: "+vehiculosCondicion.get(indice).getPlaca());
                            System.out.println("Marca: "+vehiculosCondicion.get(indice).getMarca());
                            System.out.println("Modelo: "+vehiculosCondicion.get(indice).getModelo());
                            System.out.println("Tipo de motor: "+vehiculosCondicion.get(indice).getMotor());
                            System.out.println("Año: "+vehiculosCondicion.get(indice).getAño());
                            System.out.println("Recorrido: "+vehiculosCondicion.get(indice).getRecorrido());
                            System.out.println("Color: "+vehiculosCondicion.get(indice).getColor());
                            System.out.println("Tipo de Combustible: "+vehiculosCondicion.get(indice).getCombustible());
                            System.out.println("Precio: "+vehiculosCondicion.get(indice).getPrecio());
                        }
                        else
                        {
                            System.out.println("Desea retroceder o salir?  marque R para retroceder o E para salir: ");
                            String desicion=sc.nextLine();
                            if(desicion.equals("R") ||desicion.equals("r"))
                            {
                                indice--;
                            }
                            if(desicion.equals("E") ||desicion.equals("e"))
                            {
                                System.out.println("Regresando a inicio...");
                                break;
                            }
                        }   
                    }
                }
            }
        }
        
        else    //ESTE ELSE SE EJECUTA SI EL TIPO NO ES NI AUTOS,NI MOTOS, NI CAMIONETAS
        {
            while(siguiente=true)
            {
                System.out.println("Vehiculo # "+indice+1);
                System.out.println("Placa: "+vehiculosCondicion.get(indice).getPlaca());
                System.out.println("Marca: "+vehiculosCondicion.get(indice).getMarca());
                System.out.println("Modelo: "+vehiculosCondicion.get(indice).getModelo());
                System.out.println("Tipo de motor: "+vehiculosCondicion.get(indice).getMotor());
                System.out.println("Año: "+vehiculosCondicion.get(indice).getAño());
                System.out.println("Recorrido: "+vehiculosCondicion.get(indice).getRecorrido());
                System.out.println("Color: "+vehiculosCondicion.get(indice).getColor());
                System.out.println("Tipo de Combustible: "+vehiculosCondicion.get(indice).getCombustible());
                System.out.println("Precio: "+vehiculosCondicion.get(indice).getPrecio());
                if(VehiculossinRestriccion.size()>1)
                {
                    if(indice<VehiculossinRestriccion.size() &&indice==0)
                    {
                        System.out.println("Continuar? de ser asi marque S: ");
                        String desicion=sc.next();
                        if(desicion.equals("S") ||desicion.equals("s"))
                            indice++;

                        System.out.println("Placa: "+vehiculosCondicion.get(indice).getPlaca());
                        System.out.println("Marca: "+vehiculosCondicion.get(indice).getMarca());
                        System.out.println("Modelo: "+vehiculosCondicion.get(indice).getModelo());
                        System.out.println("Tipo de motor: "+vehiculosCondicion.get(indice).getMotor());
                        System.out.println("Año: "+vehiculosCondicion.get(indice).getAño());
                        System.out.println("Recorrido: "+vehiculosCondicion.get(indice).getRecorrido());
                        System.out.println("Color: "+vehiculosCondicion.get(indice).getColor());
                        System.out.println("Tipo de Combustible: "+vehiculosCondicion.get(indice).getCombustible());
                        System.out.println("Precio: "+vehiculosCondicion.get(indice).getPrecio());    
                    }
                    
                    if(indice>0 && indice<VehiculossinRestriccion.size()-1){
                        System.out.println("Continuar? de ser asi marque S, si desea regresar al vehiculo anterior marque R: ");
                        String desicion=sc.next();
                        if(desicion.equals("S") ||desicion.equals("s"))
                            indice++;
                        if(desicion.equals("R") ||desicion.equals("r"))
                            indice--;

                        System.out.println("Placa: "+vehiculosCondicion.get(indice).getPlaca());
                        System.out.println("Marca: "+vehiculosCondicion.get(indice).getMarca());
                        System.out.println("Modelo: "+vehiculosCondicion.get(indice).getModelo());
                        System.out.println("Tipo de motor: "+vehiculosCondicion.get(indice).getMotor());
                        System.out.println("Año: "+vehiculosCondicion.get(indice).getAño());
                        System.out.println("Recorrido: "+vehiculosCondicion.get(indice).getRecorrido());
                        System.out.println("Color: "+vehiculosCondicion.get(indice).getColor());
                        System.out.println("Tipo de Combustible: "+vehiculosCondicion.get(indice).getCombustible());
                        System.out.println("Precio: "+vehiculosCondicion.get(indice).getPrecio());
                    }
                    else
                    {
                        System.out.println("Desea retroceder o salir?  marque R para retroceder o E para salir: ");
                        String desicion=sc.next();
                        if(desicion.equals("R") ||desicion.equals("r"))
                        {
                            indice--;
                        }
                        if(desicion.equals("E") ||desicion.equals("e"))
                        {
                            System.out.println("Fin del programa");
                            break;
                        }
                    }
                }
            }
        }
    }
            
    @Override
    public String toString(){
            return "Comprador| Nombres: "+this.nombres+" Apellidos: "+this.apellidos+" Correo electronico: "+this.correo+" Organizacion: "+this.organizacion + "\n";
        }         
    }

