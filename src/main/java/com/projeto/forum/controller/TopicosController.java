package com.projeto.forum.controller;

import com.projeto.forum.controller.dto.DetalhesTopicoDto;
import com.projeto.forum.controller.dto.TopicoAtulizarDto;
import com.projeto.forum.controller.dto.TopicoDto;
import com.projeto.forum.controller.dto.TopicoPostDto;
import com.projeto.forum.modelo.Topico;
import com.projeto.forum.repositories.CursoRepository;
import com.projeto.forum.repositories.TopicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
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
    @Cacheable(value = "listTopicos") //id do Cache = listTopicos - não esqueça de habilitar na class main
    public Page<TopicoDto> getLista(@RequestParam(required = false) String nomeCurso, //required = false, o nomeCurso é opcional na requisição
                                    @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        //Page 1 -
        // @RequestParam int page,
//        @RequestParam int qtd,
//        @RequestParam String ordenacao
        //Paginação do JPA/Spring Data
        //Page retorna apenas um determinada quantidade por página ao contrário do List que retorna tudo
        //http://localhost:8080/topicos?page=0&qtd=2&ordenacao=id ordenacao pelo campo id
        //Ordenacao - pode fazer um if/else para ser ASC ou DESC
        //Pageable pageable = PageRequest.of(page, qtd, Sort.Direction.ASC, ordenacao);

        //Page 2 - add apegas o Pageable no param do metodo que tem todas as info necessárias
        //habilitar modulo do Spring Data - Classe main @EnableSpringDataWebSupport
        //http://localhost:8080/topicos?page=0&size=2&sort=id,asc - os atributos precisam estar em ingles e sort=att,(virgula)asc/desc
        //ordenando por dois/mais campos - &sort=att, quantos sorts forem necessários - http://localhost:8080/topicos?page=0&size=3&sort=titulo,asc&sort=dataCriacao,desc
        //Fixando - @PageableDefault - @PageableDefault(sort = "id", direction = Sort.Direction.DESC)


        //Lógica
        if(nomeCurso == null){
            Page<Topico> topicos = topicoRepository.findAll(pageable);
            return TopicoDto.listTopico(topicos); //list o DTO
        } else {
            //Curso Entidade que tem relacionamento com a Entidade Topico (Repository)
            //se usar findByCursoNome, mesmo que seja topicoRepository,
            // o JPA irá encontrar o nome (atributo) de Curso por conta do relacionamento
            //desde que não tenha nenhum atributo "cursoNome" na entidade Topico
            // com Curso_Nome (Curso é o relacionamento de topico e Nome é o atributo de curso)
            Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, pageable);
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
    @CacheEvict(value = "listTopicos", allEntries = true) //limpa o cache toda vez que houver uma transação, para evitar conflito
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoPostDto topicoPostDto, UriComponentsBuilder uriComponentsBuilder){
        Topico topico = topicoPostDto.converter(cursoRepository); //metodo que converte o topicoForm em topico, passando um curso
        topicoRepository.save(topico);
        URI uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> atualizar(@PathVariable("id") Long id, @RequestBody @Valid TopicoAtulizarDto topicoAtulizar){
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()){
            Topico topico = topicoAtulizar.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listTopicos", allEntries = true)
    public ResponseEntity<?> remover(@PathVariable("id") Long id){
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()){
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
