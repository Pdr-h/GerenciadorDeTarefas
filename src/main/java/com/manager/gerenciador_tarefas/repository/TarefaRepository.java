package com.manager.gerenciador_tarefas.repository;

import com.manager.gerenciador_tarefas.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    //herdando metodos do Repository para operações CRUD 
}