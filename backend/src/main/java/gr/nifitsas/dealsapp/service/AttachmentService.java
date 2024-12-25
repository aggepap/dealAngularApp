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

  /**
   * Saves an image attachment for an entity that implements the `ImageAttachable` interface.
   *
   * This method saves the uploaded image file and creates an Attachment object to represent it.
   * The attachment object is then set on the provided entity.
   *
   * @param <T> The type of the entity that implements the `ImageAttachable` interface.
   * @param entity The entity to attach the image to.
   * @param image The MultipartFile containing the image data to save.
   * @throws IOException If there's an error saving the image file.
   */
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
  /**
   * Extracts the file extension from a filename string.
   *
   * @param filename The filename to extract the extension from.
   * @return The file extension (e.g., ".jpg", ".png"), or an empty string if no extension is found.
   */
  private String getFileExtension(String filename) {
    if (filename != null && filename.contains(".")) {
      return filename.substring(filename.lastIndexOf("."));
    }
    return "";
  }

}
