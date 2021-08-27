/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;


import ec.edu.espol.model.Oferta;
import ec.edu.espol.model.Persona;
import ec.edu.espol.model.Vehiculo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
        
        
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author ANDRES PORRAS
 */
public class Util {
    
    
    public static boolean placaEsValida(String nomfile,String placa)
    {
        ArrayList<Vehiculo> vehiculos = Util.readVehiclesFile(nomfile);
        for (Vehiculo v : vehiculos)
        {
            if(v.getPlaca().equals(placa)){
                return false;
            }
        }
        return true;
    }
    
    public static ArrayList<Oferta> readOfertasFile(String filename)
    {
        ArrayList<Oferta> offs = new ArrayList<>();
        
        try(ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(new File(filename))))
        {
            offs = (ArrayList<Oferta>) inStream.readObject();
        }
        catch(IOException e)
        {
            //e.printStackTrace();
        } 
        catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        }
        return offs;
    }
    
    public static ArrayList<Vehiculo> readVehiclesFile(String filename)
    {
        ArrayList<Vehiculo> listaVehs = new ArrayList<>();
        
        try(ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(new File(filename))))
        {
            listaVehs = (ArrayList<Vehiculo>) inStream.readObject();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        } 
        catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        }
        return listaVehs;
    }
    public static ArrayList<Persona> readPersonasFile(String filename)
    {
        ArrayList<Persona> listaPersonas = new ArrayList<>();
        
        try(ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(new File(filename))))
        {
            listaPersonas = (ArrayList<Persona>) inStream.readObject();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        } 
        catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        }
        return listaPersonas;
    }
    
    public static boolean credencialEsValida(String filename, String correo, String contrasenia)
    {
        ArrayList<Persona> usuarios = new ArrayList<>();
        usuarios = Util.readPersonasFile(filename);

        String clave = convertirSHA256(contrasenia);
        for (Persona p : usuarios)
        {
            
            if (p.getCorreo().equals(correo))
            {
                if (p.getClave().equals(clave))
                {
                    return true;
                }
                else
                {
                    System.out.println("Clave incorrecta");
                    return false;
                }
            }
        }
        System.out.println("No existe el correo ingresado");
        return false;
    }
    
    public static boolean correoEsValido(String email){

        Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");   
        Matcher mat = pat.matcher(email);
        return mat.find();
        
    }
    
    public static boolean correoExiste(String filename,String email)
    {
        ArrayList<Persona> listaPersonas = readPersonasFile(filename);
        for (Persona p : listaPersonas)
        {
            if(p.getCorreo().equals(email))
            {
                return true;
            }
        }
        
        return false;
    }

    public static String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
                md = MessageDigest.getInstance("SHA-256");
        } 
        catch (NoSuchAlgorithmException e) {		
                e.printStackTrace();
                return null;
        }

            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            String res = toHexString(hash);

            return res;
        }
        
    public static String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash); 
  
        StringBuilder hexString = new StringBuilder(number.toString(16)); 
  
        while (hexString.length() < 32) 
        { 
            hexString.insert(0, '0'); 
        } 
  
        return hexString.toString(); 
    }
    
    public static void enviarCorreo(String correo, String correoEmisor) throws MessagingException
    {

        String to = correo;
        String from = "freddyprueba75";//correoEmisor;
        
//String host = "localhost";
        
        
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.clave", "proyecto8");    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
        // Get the default Session object.
        Session session = Session.getDefaultInstance(props);

        try 
        {
           MimeMessage message = new MimeMessage(session);
           message.setFrom(new InternetAddress(from));

           message.addRecipients(Message.RecipientType.TO, to);

           message.setSubject("Su oferta ha sido ha aceptada!");
           message.setText("Felicidades, su oferta ha sido aceptada por un vendedor.");
           // Send message
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", from, "proyecto8");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
           System.out.println("Mensaje enviado exitosamente!!");
        } 
        catch (MessagingException mex) {
           throw mex;
        }
    }
}
