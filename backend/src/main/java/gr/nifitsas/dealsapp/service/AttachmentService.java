package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.model.Attachment;
import gr.nifitsas.dealsapp.model.ImageAttachable;
import gr.nifitsas.dealsapp.model.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service

public class AttachmentService {

  @Transactional(rollbackOn = Exception.class)
  public <T extends ImageAttachable> void saveImage(T entity, MultipartFile image) throws IOException {

    if (image != null && !image.isEmpty()) {

      String originalFileName = image.getOriginalFilename();
      String savedName = UUID.randomUUID() + getFileExtension(originalFileName);
      String uploadDir = "./uploads/";
      Path filepath = Paths.get(uploadDir + savedName);
      Files.createDirectories(filepath.getParent());
      Files.write(filepath, image.getBytes());

      Attachment attachment = new Attachment();
      attachment.setFilename(originalFileName);
      attachment.setSavedName(savedName);
      attachment.setFilePath(filepath.toString());
      attachment.setContentType(image.getContentType());
      attachment.setExtension(getFileExtension(originalFileName));

      entity.setImage(attachment);
    }

  }

  private String getFileExtension(String filename) {
    if (filename != null && filename.contains(".")) {
      return filename.substring(filename.lastIndexOf("."));
    }
    return "";
  }

}
