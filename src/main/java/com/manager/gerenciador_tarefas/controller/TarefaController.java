package com.manager.gerenciador_tarefas.controller;

import com.manager.gerenciador_tarefas.model.Tarefa;
import com.manager.gerenciador_tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController 
@RequestMapping("/api/tarefas") // caminho base para as requisições 
public class TarefaController {

    @Autowired 
    private TarefaRepository tarefaRepository;

    // CRUD - Create, Read, Update, Delete
    // --- 1. Create
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaRepository.save(tarefa);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED); 
    }

    // --- 2. Read 
    // Lista todas as tarefas
    @GetMapping
    public List<Tarefa> listarTodasAsTarefas() {
        return tarefaRepository.findAll();
    }

    // Buscar uma tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);

        if (tarefa.isPresent()) {
            return new ResponseEntity<>(tarefa.get(), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }

    // --- 3. Update
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa detalhesTarefa) {
        return tarefaRepository.findById(id)
                .map(tarefaExistente -> {
                    tarefaExistente.setTitulo(detalhesTarefa.getTitulo());
                    tarefaExistente.setDescricao(detalhesTarefa.getDescricao());
                    tarefaExistente.setConcluida(detalhesTarefa.isConcluida());
                    Tarefa tarefaAtualizada = tarefaRepository.save(tarefaExistente);
                    return new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // --- 4. Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        return tarefaRepository.findById(id)
                .map(tarefa -> {
                    tarefaRepository.delete(tarefa);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}