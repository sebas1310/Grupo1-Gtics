package com.example.proyectogticsgrupo1.Controller;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.ByteBuffer;

@RestController
@RequestMapping("/gcp")
public class GcsController {

    public static byte[] downloadObject
            (String projectId, String bucketName, String blobName) throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, blobName);

        try (ReadChannel reader = storage.reader(blobId)) {
            ByteBuffer bytes = ByteBuffer.allocate(64 * 1024);
            while (reader.read(bytes) > 0) {
                bytes.flip();
                bytes.clear();
            }
            byte[] image = bytes.array();
            return image;
        }

    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> displayItemImage() throws IOException {
        System.out.println("se usa este?");
        byte[] image = downloadObject("gigacontrol", "l5-20203368-2023-1-gtics", "image.jpeg");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping("/imagenEvento")
    public ResponseEntity<byte[]> displayItemImage(@RequestParam("id") int id) throws IOException {
        String blobName = "proyecto/foto-evento-" + id + ".jpeg";
        byte[] image = GcsController.downloadObject("gigacontrol", "l5-20203368-2023-1-gtics", blobName);
        System.out.println("entra aca?");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}