package com.visiongraphics_inc.alert_system.service.mapper;


import com.visiongraphics_inc.alert_system.domain.*;
import com.visiongraphics_inc.alert_system.service.dto.StackItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StackItem} and its DTO {@link StackItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlertEventMapper.class})
public interface StackItemMapper extends EntityMapper<StackItemDTO, StackItem> {

    @Mapping(source = "alertEvent.id", target = "alertEventId")
    StackItemDTO toDto(StackItem stackItem);

    @Mapping(source = "alertEventId", target = "alertEvent")
    StackItem toEntity(StackItemDTO stackItemDTO);

    default StackItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        StackItem stackItem = new StackItem();
        stackItem.setId(id);
        return stackItem;
    }
}
