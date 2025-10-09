package com.diogo.agendadortarefas.infrastructure.repository;

import com.diogo.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.diogo.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import org.springframework.cglib.core.Local;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefasRepository extends MongoRepository<TarefasEntity,String> {

    List<TarefasEntity> findByDataEvetoBetweenAndStatusNotificacaoEnum(LocalDateTime datainicial,
                                                                       LocalDateTime datafinal,
                                                                       StatusNotificacaoEnum status);

    List<TarefasEntity> findByEmailUsuario(String email);
}
