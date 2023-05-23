package com.example.proyectogticsgrupo1.Service;

import java.io.File;

public interface EmailService {
    void sendEmail(String toUser, String subject, String msj);

    void sendEmailWithFile(String toUser, String subject, String msj, File file);
}
