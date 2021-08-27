/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.ProyectoPOO;
import ec.edu.espol.model.Comprador;
import ec.edu.espol.model.Persona;
import ec.edu.espol.model.Vendedor;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner; 

/**
 *
 * @author Axel
 * @author Josue
 * @author Jose
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        sc.useDelimiter("\n");
        sc.useLocale(Locale.US);
//        Comprador.OfertarVehiculo(sc);
        //Vendedor.RegistrarVehiculo(sc);
        //Vendedor.RegistarVendedor(sc);
        //ArrayList<Vendedor> vendedores=Vendedor.readFile("ArchivoVendedores.txt");
        //System.out.println(vendedores);
        //System.out.println(Persona.convertirSHA256("prueba1"));
        //System.out.println(v);
        //System.out.println(Menu.cargarMenu(sc));
        //System.out.println(v);
        //Guarda la informacion del vendedor
        //v.saveFile("ArchivoVendedores.txt");
        //Guarda la informacion del comprador    
        //Comprador c=Comprador.RegistarComprador(sc);
        //System.out.println(c);
        //c.saveFile("ArchivoVendedores.txt");
        //c.saveFile("ArchivoCompradores.txt");
            int opcion =0;
            do{
               System.out.println("---------------------------------");
               System.out.println("-- Menu de opciones --");
               System.out.println("1. Vendedor");
               System.out.println("2. Comprador");
               System.out.println("3. Salir");
               System.out.println("Ingrese una de las opciones: ");
               opcion = convertirStringAInt(sc.nextLine());
               
               switch (opcion){
                case 1:
                   int opcionv=0;
                   do{
                       System.out.println("---------------------------------");
                       System.out.println("-- Menu Vendedor --");
                       System.out.println("1. Registrar un nuevo vendedor");
                       System.out.println("2. Ingresar un nuevo vehiculo");
                       System.out.println("3. Aceptar oferta");
                       System.out.println("4. Regresar");
                       System.out.println("Ingrese una opcion: ");
                       opcionv = convertirStringAInt(sc.nextLine());
                       switch(opcionv){
                           case 1:
                               Vendedor.RegistarVendedor(sc);
                               break;
                           case 2:
                               Vendedor.RegistrarVehiculo(sc);
                               break;
                           case 3:
                               Vendedor.AceptarOferta(sc);
                               break;
                           case 4:
                               break;
                       }
                   }
                   while(opcionv !=4);
                   break;
                case 2:
                   int opcionc=0;
                   do{
                       System.out.println("---------------------------------");
                       System.out.println("-- Menu Comprador --");
                       System.out.println("1. Registrar un nuevo comprador");
                       System.out.println("2. Ofertar por un vehiculo");
                       System.out.println("3. Regresar");
                       System.out.println("Ingrese opcion: ");
                       opcionc = sc.nextInt();
                       switch(opcionc){
                           case 1:
                               Comprador.registarComprador(sc);
                               break;
                           case 2:
                               Comprador.OfertarVehiculo(sc);
                               break;
                           case 3:
                               break;
                       }
                   }
                   while(opcionc !=3);
                   break;
                case 3:
                   System.out.println("Gracias por su atención");
                   break;
               }
            }while (opcion!=3);
           
    }
    
    public static int convertirStringAInt(String cadena)
    {
        try
        {
            int valor = Integer.parseInt(cadena);
            return valor;
        }
        catch(NumberFormatException ime)
        {
            System.out.println("No es un entero");
            return -1;
        }
    }
}
