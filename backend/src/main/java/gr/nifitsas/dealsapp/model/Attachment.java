package gr.nifitsas.dealsapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @Table(name = "attachments")
  public class Attachment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String savedName;
    private String filePath;
    private String contentType;
    private String extension;


  }
