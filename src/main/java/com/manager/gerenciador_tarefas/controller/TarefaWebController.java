package com.manager.gerenciador_tarefas.controller;

import com.manager.gerenciador_tarefas.model.Tarefa;
import com.manager.gerenciador_tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TarefaWebController {

    @Autowired
    private TarefaRepository tarefaRepository;

    // lista todas as tarefas
    @GetMapping("/")
    public String listarTarefas(Model model) {
        model.addAttribute("tarefas", tarefaRepository.findAll());
        return "index"; 
    }

    // formulario para adicionar uma nova tarefa
    @GetMapping("/novaTarefa")
    public String mostrarFormularioDeNovaTarefa(Model model) {
        model.addAttribute("tarefa", new Tarefa()); 
        return "form-tarefa"; 
    }

    // Mesmo formulario porém para editar uma tarefa existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicao(@PathVariable Long id, Model model) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID da Tarefa inválido:" + id));
        model.addAttribute("tarefa", tarefa);
        return "form-tarefa";
    }

    // Salva a tarefa (nova ou editada)
    @PostMapping("/salvar")
    public String salvarTarefa(Tarefa tarefa) {
        tarefaRepository.save(tarefa);
        return "redirect:/"; // Redireciona para a página principal
    }

    // Deleta uma tarefa
    @PostMapping("/deletar/{id}")
    public String deletarTarefa(@PathVariable Long id) {
        tarefaRepository.deleteById(id);
        return "redirect:/";
    }

    // Marca como concluida/pendente
    @PostMapping("/concluir/{id}")
    public String concluirTarefa(@PathVariable Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID da Tarefa inválido:" + id));
        tarefa.setConcluida(!tarefa.isConcluida()); 
        tarefaRepository.save(tarefa);
        return "redirect:/";
    }
}