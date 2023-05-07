/*
package com.example.proyectogticsgrupo1.Entity;

import com.example.gigacontrol_g2.beans.BUsuarios;
import com.example.gigacontrol_g2.beans.MailCorreo;
import java.util.Properties;

 */

/*
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;

public class DaoCorreo {

    private final Properties properties = new Properties();

    private String password;
    private Session session;

    private void init(){
        properties.put("mail.stmp.host","mail.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port",587);
        properties.put("mail.smtp.mail.sender","serviciocorreogigacontrol@gmail.com");
        properties.put("mail.smtp.user", "usuario");
        properties.put("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(properties);
    }

    private void sendEmail(){
        init();

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("anaissalvadorrodriguez@gmail.com"));
            message.setSubject("Prueba");
            message.setText("Texto");
            Transport t = session.getTransport("smtp");
            t.connect((String)properties.get("mail.smtp.user"), "hlbldqnwfvsxasdv");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    /*
    public ArrayList<MailCorreo> obtenerCorreo(){
        ArrayList<MailCorreo> listaCorreo = new ArrayList<>();
        String sql = "SELECT idUsuario, Correo FROM usuario";
        try (Connection connection = this.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)
        ){
            while (resultSet.next()){
                MailCorreo mailCorreo = new MailCorreo();
                mailCorreo.setIdCorreo(resultSet.getInt(1));
                mailCorreo.setCorreo(resultSet.getString(4));
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return listaCorreo;
    }

    //obtener correo

 */
/*
    public ArrayList<BUsuarios> obtenerCorreoUsuarios(){
        ArrayList<BUsuarios> listaCorreo = new ArrayList<>();

        String sql = "SELECT idUsuario, Correo FROM usuario";
        try(Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)
        ){
            while (rs.next()){
                BUsuarios usuarios = new BUsuarios();
                usuarios.setIdUsuario(rs.getInt(1));
                usuarios.setCorreo(rs.getString(4));
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return listaCorreo;
    }

} */
