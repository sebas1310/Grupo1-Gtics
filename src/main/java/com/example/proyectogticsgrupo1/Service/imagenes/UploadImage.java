package com.example.proyectogticsgrupo1.Service.imagenes;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UploadImage implements UploadInter{

    @Override
    public String subirimagen(ImagenSubir imagenSubir) {
        String result = "";
        String storeConnection = "DefaultEndpointsProtocol=https;AccountName=lafe;AccountKey=1W+J24np4EQlq+n0IjaSo/Rjw4dkoMKMecPKX01+/U32mHyiTY25S09N350kon/znQqnvqsB5jLl+AStOTuUNA==;EndpointSuffix=core.windows.net";
        String nombreContenedor = "clinicalafe";
        System.out.println("llego");
        try{
            CloudStorageAccount account = CloudStorageAccount.parse(storeConnection);
            CloudBlobClient cliente = account.createCloudBlobClient();
            CloudBlobContainer contenedor = cliente.getContainerReference(nombreContenedor);
            CloudBlob blob;
            blob = contenedor.getBlockBlobReference(imagenSubir.getFilename());
            String extension = imagenSubir.getFilename().split("\\.")[1];
            blob.getProperties().setContentType("image/"+ extension);
            byte[] imagenbyte = Base64.getDecoder().decode(imagenSubir.getFilebase64());
            blob.uploadFromByteArray(imagenbyte, 0, imagenbyte.length);
            result = "ok";
        }catch (Exception e){
            result = e.getMessage();
            System.out.println(result);
        }
        return result;
    }
}
