package org.example.listingservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.listingservice.models.RealEstate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listing/")
public class ListingController {
    @GetMapping
    public ResponseEntity<List<RealEstate>> getAll(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        // todo: call api gateway to get user info
        // TODO: return all posts
        var realEstate = RealEstate
                .builder()
                .id(1L)
                .name("Title").build();
        var list = List.of(realEstate);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<RealEstate> getDoc(@PathVariable("id") Long id) {
        // TODO: return all posts
        var realEstate = RealEstate
                .builder()
                .id(1L)
                .name("Lim's house").build();
        return ResponseEntity.ok().body(realEstate);
    }
}
