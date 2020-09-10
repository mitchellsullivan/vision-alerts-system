package com.visiongraphics_inc.alert_system.service.mapper;


import com.visiongraphics_inc.alert_system.domain.*;
import com.visiongraphics_inc.alert_system.service.dto.AlertEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlertEvent} and its DTO {@link AlertEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlertEventMapper extends EntityMapper<AlertEventDTO, AlertEvent> {

    @Mapping(target = "stackItems", ignore = false)
    @Mapping(target = "removeStackItem", ignore = true)
    AlertEvent toEntity(AlertEventDTO alertEventDTO);

    default AlertEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        AlertEvent alertEvent = new AlertEvent();
        alertEvent.setId(id);
        return alertEvent;
    }
}
