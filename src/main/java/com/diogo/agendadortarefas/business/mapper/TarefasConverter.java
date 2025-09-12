package com.diogo.agendadortarefas.business.mapper;

import com.diogo.agendadortarefas.business.dto.TarefasDTO;
import com.diogo.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefasEntity(TarefasDTO dto);

    TarefasDTO paraTarefasDTO (TarefasEntity tarefasEntity);
}
