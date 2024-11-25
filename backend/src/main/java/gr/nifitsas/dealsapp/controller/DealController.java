package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.dto.DealReadOnlyDTO;
import gr.nifitsas.dealsapp.model.Deal;
import gr.nifitsas.dealsapp.service.DealService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/deals")
@RequiredArgsConstructor
public class DealController {

  //@autowired
  private final DealService dealService;
  private static final Logger LOGGER = LoggerFactory.getLogger(DealController.class);


//  @GetMapping("")
//  public ResponseEntity<List<DealReadOnlyDTO>> getProductDeals() {
//
//
//  }
}
