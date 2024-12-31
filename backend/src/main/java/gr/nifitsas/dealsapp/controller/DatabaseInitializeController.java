package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductReadOnlyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/initialize")
public class DatabaseInitializeController {

  private final JdbcTemplate jdbcTemplate;

  /**
   * Initializes the database by executing the SQL statements
   * from a file located at the specified path.
   *
   * @return A ResponseEntity containing a success message on success,
   *         or an error message with the cause on failure.
   */
  //OpenAPI Annotations
  @Operation(summary = "Initializes the database by executing the SQL statements\n" +
    "   * from a file located at the specified path" )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Database succesfully initialized",
      content = { @Content(mediaType = "text/plain",
        schema = @Schema(implementation = String.class)) }),
    @ApiResponse(responseCode = "500", description = "Error while reading the SQL File",
      content = @Content)})
  //Controller
  @GetMapping
  public ResponseEntity<Map<String, String>> initializeDatabase() {
    try {
      String sql = loadSqlFromFile("sql/start.sql");
      jdbcTemplate.execute(sql);

      Map<String, String> response = new HashMap<>();
      response.put("message", "Database initialized successfully");
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", "Error initializing database: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }
  /**
   * Loads the SQL script content from a file located on the classpath.
   *
   * @param path The path to the SQL script file (relative path).
   * @return The content of the SQL script as a String.
   * @throws IOException If there is an error reading the file.
   */
  private String loadSqlFromFile(String path) throws IOException {
    ClassPathResource resource = new ClassPathResource(path);
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
      return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }
  }
}
