package org.example.rentalservice;

import lombok.RequiredArgsConstructor;
import org.example.rentalservice.models.RealEstate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rental/")
public class RentalController {
    private final RestTemplate restTemplate;
    @GetMapping
    public ResponseEntity<String> get() {
        // TODO: implements this
        var response = restTemplate.getForObject(
                "http://localhost:9001/api/listing/{id}",
                RealEstate.class,
                1 );
        return ResponseEntity.ok().body(response.getName());
    }
}
