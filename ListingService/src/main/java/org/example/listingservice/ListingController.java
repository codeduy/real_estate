package org.example.listingservice;

import org.example.listingservice.models.RealEstate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/listing/")
public class ListingController {
    @GetMapping
    public ResponseEntity<List<RealEstate>> getAll() {
        // TODO: return all posts
        var realEstate = RealEstate
                .builder()
                .id(1L)
                .title("Title").build();
        var list = Arrays.asList(realEstate);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<RealEstate> getDoc(@PathVariable("id") Long id) {
        // TODO: return all posts
        var realEstate = RealEstate
                .builder()
                .id(1L)
                .title("Lim's house").build();
        return ResponseEntity.ok().body(realEstate);
    }

    @GetMapping("doc")
    public ResponseEntity<String> getDoc() {
        return ResponseEntity.ok().body("Return doc here");
    }
}
