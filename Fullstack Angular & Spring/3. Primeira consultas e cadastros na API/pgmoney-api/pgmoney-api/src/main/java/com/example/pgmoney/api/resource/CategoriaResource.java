package com.example.pgmoney.api.resource;

import com.example.pgmoney.api.model.Categoria;
import com.example.pgmoney.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> listar(){
        return  categoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> criar( @RequestBody Categoria categoria, HttpServletResponse response){
       Categoria categoriaSalva = categoriaRepository.save(categoria);

       URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
               .buildAndExpand(categoriaSalva.getCodigo()).toUri();

       response.setHeader("Location", uri.toASCIIString());

       return ResponseEntity.created(uri).body(categoriaSalva);
    }

    @GetMapping("/{codigo}")
    public Optional<Categoria> buscarPeloCodigo(@PathVariable Long codigo){
        return categoriaRepository.findById(codigo);
    }
}
