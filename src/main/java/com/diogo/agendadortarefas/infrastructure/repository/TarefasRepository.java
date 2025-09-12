package com.diogo.agendadortarefas.infrastructure.repository;

import com.diogo.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.springframework.cglib.core.Local;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefasRepository extends MongoRepository<TarefasEntity,String> {

    List<TarefasEntity> findByDataEvetoBetween(LocalDateTime datainicial, LocalDateTime datafinal);

    List<TarefasEntity> findByEmailUsuario(String email);
}
