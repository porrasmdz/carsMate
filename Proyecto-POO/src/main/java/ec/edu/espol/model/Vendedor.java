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
import javax.mail.MessagingException;

/**
 *
 * @author Josue Vera + Andres Porras
 */
public class Vendedor extends Persona implements Serializable{

    private static final long serialVersionUID = -5892814637268092625L;
    public Vendedor(String nombres, String apellidos, String correo, String organizacion, String clave) {
        super(nombres, apellidos, correo, organizacion, clave, Rol.VENDEDOR);
        
    }

    //FUNCION LISTA
    public static void RegistarVendedor(Scanner sc)
    {
     
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
            System.out.println("El correo ingresado ya esta registrado, ingrese correo electronico valido: ");
            correo = sc.nextLine();
            
        }
        System.out.println("Ingrese su clave de acceso: ");
        String contrasena = sc.nextLine();
        try
        {
            Vendedor v = new Vendedor(nombre, apellidos, correo, organizacion, contrasena);
            v.saveFile("usuarios.ser");
        }
        catch(CorreoException e)
        {
            System.out.println("El correo electronico no es valido. Por favor intente de nuevo");
        }
    }

    //FUNCION LISTA Y COMPROBADA
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
            ioe.printStackTrace();
        }
    }

    //FUNCION LISTA - NO REVISADA TODA
    public static void RegistrarVehiculo(Scanner sc) 
    {
        System.out.println("Ingrese su correo electronico: ");
        String correo = sc.nextLine();
        System.out.println("Ingrese su contraseña: ");
        String contrasenia = sc.nextLine();
        
        while (!Util.credencialEsValida("usuarios.ser", correo, contrasenia)) 
        {
            System.out.println("Ingrese su correo electronico: ");
            correo = sc.nextLine();
            System.out.println("Ingrese su contraseña: ");
            contrasenia = sc.nextLine();
        }
        
        System.out.println("Ingrese el tipo de vehiculo que quiere registrar: ");
        String tipo = sc.nextLine();
        
        while ((tipo.equalsIgnoreCase("auto") || tipo.equalsIgnoreCase("moto") || tipo.equalsIgnoreCase("camioneta")) == false) {
            System.out.println("Ingrese si su vehiculo es: auto, moto o camioneta");
            tipo = sc.nextLine();
            
        }
        
        if (tipo.equalsIgnoreCase("auto")) {
            Autos.DatosAuto(sc);
        }
        if (tipo.equalsIgnoreCase("moto")) {
            Motos.DatosMoto(sc);
        }
        if (tipo.equalsIgnoreCase("camioneta")) {
            Camioneta.DatosCamioneta(sc);
        }
    }
    
 
    //FUNCION LISTA - NO REVISADA POSIBLEMENTE SIN PROPOSITO
    public static ArrayList<Vendedor> extraerVendedores(String filename)
    {
        ArrayList<Persona> listaPersonas = new ArrayList<>();
        ArrayList<Vendedor> vendedores = new ArrayList<>();
        try(ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(new File(filename))))
        {
            listaPersonas = (ArrayList<Persona>) inStream.readObject();
            for (Persona p : listaPersonas)
            {
                if (p.getRol()== Rol.VENDEDOR || p.getRol() == Rol.AMBOS)
                {
                    vendedores.add((Vendedor) p);
                }
            }
        }
        catch(IOException e)
        {
            //e.printStackTrace();
        } 
        catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        }
        return vendedores;
    }


    //FUNCION LISTA - CORREGIDA
    public static void AceptarOferta(Scanner sc)
    {
        System.out.println("Ingrese su correo electronico: ");
        String correo = sc.nextLine();
        System.out.println("Ingrese su contraseña: ");
        String contrasenia = sc.nextLine();
        
        while (!Util.credencialEsValida("usuarios.ser", correo, contrasenia)) {
            System.out.println("El usuario o contraseña ingresados son incorrectos, ingrese creedenciales nuevamente: ");
            System.out.println("Ingrese su correo electronico: ");
            correo = sc.nextLine();
            System.out.println("Ingrese su contraseña: ");
            contrasenia = sc.nextLine();
            
        }
        int indice = 0;
        boolean siguiente = true;
        System.out.println("Placas de vehiculos con ofertas");
        for(Oferta o : Util.readOfertasFile("ofertas.ser"))
        {
            System.out.println(o.getPlaca());
        }
        System.out.println("Ingrese placa del vehiculo: ");
        String placa = sc.nextLine();
        ArrayList<Oferta> ofertas = Util.readOfertasFile("ofertas.ser");
        System.out.println(ofertas);
        
        ArrayList<Oferta> Ofertasseleccionadas = new ArrayList<>();
        for (Oferta o : ofertas) {
            if (o.getPlaca().equals(placa)) {
                Ofertasseleccionadas.add(o);
            }
        }
        if (Ofertasseleccionadas.size() < 1)
        {
            System.out.println("No hay ofertas realizadas por este vehiculo");
            return;
        }
        for (Oferta o : Ofertasseleccionadas) 
        {
            indice++;
            System.out.printf("Oferta %d%n", (indice));
            System.out.println("Correo: " + o.getCorreo());
            System.out.println("Precio Ofertado : " + o.getPrecio());
            System.out.println("Dese aceptar esta oferta (s/n)?");
            String decision = sc.nextLine();
            if((decision.toLowerCase()).equals("s"))
            {
                try
                {
                    Util.enviarCorreo(o.getCorreo(), correo);// (correoDestino, correoEmisor)
                    Oferta.removerOferta("ofertas.ser", o);
                }
                catch(MessagingException mex)
                {
                    mex.printStackTrace();
                }
                
                return;
            }


        }
        System.out.println("Ya no hay mas ofertas por el momento.");
    }

    @Override
    public String toString()
    {
        return "Vendedor| Nombres: " + this.nombres + " Apellidos: " + this.apellidos + " Correo electronico: " + this.correo + " Organizacion: " + this.organizacion + "\n";
    }

}
