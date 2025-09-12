package com.diogo.agendadortarefas.business;


import com.diogo.agendadortarefas.business.dto.TarefasDTO;
import com.diogo.agendadortarefas.business.mapper.TarefasConverter;
import com.diogo.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.diogo.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.diogo.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.diogo.agendadortarefas.infrastructure.security.JwtUtil;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefasEntity entity = tarefasConverter.paraTarefasEntity(dto);

        return tarefasConverter.paraTarefasDTO(
                tarefasRepository.save(entity));
    }

}
