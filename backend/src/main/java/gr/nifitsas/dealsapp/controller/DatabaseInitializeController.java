package gr.nifitsas.dealsapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

  @RequiredArgsConstructor
  @RestController
  @RequestMapping("/initialize")
  public class DatabaseInitializeController {
    private final JdbcTemplate jdbcTemplate;

    @GetMapping("")
    public String initializeDatabase() {
      System.out.println("Initializing database...");
      try {
        ClassPathResource resource = new ClassPathResource("sql/start.sql");
        String sql = new BufferedReader(
          new InputStreamReader(resource.getInputStream()))
          .lines()
          .collect(Collectors.joining("\n"));

        jdbcTemplate.execute(sql);
        return "Database initialized successfully.";
      } catch (IOException e) {
        return "Error initializing database: " + e.getMessage();
      }
    }
    }


