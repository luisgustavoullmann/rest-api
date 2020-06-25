package com.projeto.forum.controller;

import com.projeto.forum.controller.dto.DetalhesTopicoDto;
import com.projeto.forum.controller.dto.TopicoAtulizar;
import com.projeto.forum.controller.dto.TopicoDto;
import com.projeto.forum.controller.dto.TopicoPostDto;
import com.projeto.forum.modelo.Topico;
import com.projeto.forum.repositories.CursoRepository;
import com.projeto.forum.repositories.TopicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Created by Luis Gustavo Ullmann on 24/06/2020
 */
@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicosController {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> getLista(String nomeCurso){
        if(nomeCurso == null){
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.listTopico(topicos);
        } else {
            //Curso Entidade que tem relacionamento com a Entidade Topico (Repository)
            //se usar findByCursoNome, mesmo que seja topicoRepository,
            // o JPA irá encontrar o nome (atributo) de Curso por conta do relacionamento
            //desde que não tenha nenhum atributo "cursoNome" na entidade Topico
            // com Curso_Nome (Curso é o relacionamento de topico e Nome é o atributo de curso)
            List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
            return TopicoDto.listTopico(topicos);
        }
    }

    @GetMapping("/{id}")
    //Pode retornar o TopicoDto padrão (retornando apenas os campos que julgar necessário)
    //ou pode retornar um outro Dto e retonar novo que você criar
    public ResponseEntity<Object> detalhar(@PathVariable("id") Long id){
        Optional<Topico> topico = topicoRepository.findById(id);
        if(topico.isPresent()){
            return ResponseEntity.ok(new DetalhesTopicoDto(topico.get())); //converte em Dto o topico
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoPostDto topicoPostDto, UriComponentsBuilder uriComponentsBuilder){
        Topico topico = topicoPostDto.converter(cursoRepository); //metodo que converte o topicoForm em topico, passando um curso
        topicoRepository.save(topico);
        URI uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable("id") Long id, @RequestBody @Valid TopicoAtulizar topicoAtulizar){
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()){
            Topico topico = topicoAtulizar.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable("id") Long id){
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()){
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
