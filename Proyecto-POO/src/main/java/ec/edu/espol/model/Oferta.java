/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.model;

import ec.edu.espol.util.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Josue Vera + Andres Porras
 */
public class Oferta implements Serializable
{
    protected String correo;
    protected double precio;
    private String placa;
    
    private static final long serialVersionUID = -12345L;

  public Oferta(String correo, double precio, String placa){
      this.correo=  correo;
      this.precio= precio;
      this.placa= placa;
  }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
  
   public void saveFile(String filename) 
   {
       
        ArrayList<Oferta> ofertas = new ArrayList<>();
        ofertas = Util.readOfertasFile(filename);
        
        try(ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            ofertas.add(this);    
            outStream.writeObject(ofertas);
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
   
    public static void removerOferta(String filename, Oferta oferta) 
    {
        ArrayList<Oferta> ofertas = new ArrayList<>();
        ofertas = Util.readOfertasFile(filename);
        
        ArrayList<Oferta> ofertasMod = new ArrayList<>();
        
        for(Oferta o : ofertas)
        {
            if(o.getCorreo().equals(oferta.getCorreo()) && o.getPlaca().equals(oferta.getPlaca()))
            {
               
            }
            else
            {
                ofertasMod.add(o);
            }
        }

        
        
        try(ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(filename)))
        {    
            outStream.writeObject(ofertasMod);
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
    
    @Override
    public String toString()
    {
        String res = String.format("Placa: %s%nCorreo: %s%nPrecio Ofertado: %.2f", this.getPlaca(),
                this.getCorreo(), this.getPrecio());
        return res;
    }
}
