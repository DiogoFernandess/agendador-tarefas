
package com.diogo.agendadortarefas.business;

import com.diogo.agendadortarefas.business.dto.TarefasDTO;
import com.diogo.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.diogo.agendadortarefas.business.mapper.TarefasConverter;
import com.diogo.agendadortarefas.infrastructure.Exceptions.ResourceNotFoundException;
import com.diogo.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.diogo.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.diogo.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.diogo.agendadortarefas.infrastructure.security.JwtUtil;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7)); //pega email do usuário para salvar junto
        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefasEntity entity = tarefasConverter.paraTarefasEntity(dto);

        return tarefasConverter.paraTarefasDTO(
                tarefasRepository.save(entity));
    }
    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime datainicial, LocalDateTime datafinal){
        return tarefasConverter.paraListaTarefasDTO(tarefasRepository.findByDataEvetoBetweenAndStatusNotificacaoEnum(datainicial, datafinal, StatusNotificacaoEnum.PENDENTE));
    }


     public List<TarefasDTO> buscaTarefasPorEmail(String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefasConverter.paraListaTarefasDTO(listaTarefas);
    }

    public void deletaTarefaPorId(String id){
        try{
        tarefasRepository.deleteById(id);
    }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, id inexistende" + id, e.getCause());
        }
    }

    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id){
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
            entity.setStatusNotificacaoEnum(status);
            return   tarefasConverter.paraTarefasDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar Status da Tarefa" + e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id){
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
            tarefaUpdateConverter.updateTarefas(dto, entity);
            return tarefasConverter.paraTarefasDTO(tarefasRepository.save(entity));

        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar Status da Tarefa" + e.getCause());
        }
    }


}