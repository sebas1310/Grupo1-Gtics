package com.example.proyectogticsgrupo1.Controller;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Optional;

@Controller
@RequestMapping("/imagen")
public class ImageController {

    @PostMapping("/guardarImagen")
    public String guardarImagenEvento(@RequestParam("file") MultipartFile file, @RequestParam("id") int id, RedirectAttributes attr) {
        System.out.println("llega a guardar");
        StringBuilder fileNames = new StringBuilder();
        String nombreArchivo= "foto-evento-" + id;
        System.out.println("nombre en guardar"+nombreArchivo);
        uploadObject(file,nombreArchivo, "gigacontrol", "l5-20203368-2023-1-gtics");
        return "redirect:/paciente/perfil";
    }

    public static void uploadObject
            (MultipartFile multipartFile, String fileName, String projectId, String gcpBucketId) {
        try {
            byte[] fileData = FileUtils.readFileToByteArray(convertFile(multipartFile));
            Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
            Bucket bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());
//            RandomString id = new RandomString(6, ThreadLocalRandom.current());
            Blob blob = bucket.create("proyecto" + "/" + fileName + checkFileExtension(fileName), fileData);

            if (blob != null) {
                System.out.println("errro?");
               /* LOGGER.debug("File successfully uploaded to GCS");
                return new FileDto(blob.getName(), blob.getMediaLink());*/
            }
        } catch (Exception e) {
            System.out.println("errro?2");
//            LOGGER.error("An error occurred while uploading data. Exception: ", e);
            throw new RuntimeException("An error occurred while storing data to GCS");
        }
    }

    private static File convertFile(MultipartFile file) {

        try {
            if (file.getOriginalFilename() == null) {
            }
            File convertedFile = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            return convertedFile;
        } catch (Exception e) {
            throw new RuntimeException("An error has occurred while converting the file");
        }
    }

    private static String checkFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            String[] extensionList = {".png", ".jpeg", ".pdf", ".doc", ".mp3"};

            for (String extension : extensionList) {
                if (fileName.endsWith(extension)) {
//                    LOGGER.debug("Accepted file type : {}", extension);
                    return extension;
                }
            }
        }
        return ".jpeg";
    }

}
