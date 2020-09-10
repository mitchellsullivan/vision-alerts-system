package com.visiongraphics_inc.alert_system.service;

import com.visiongraphics_inc.alert_system.domain.AlertEvent;
import com.visiongraphics_inc.alert_system.repository.AlertEventRepository;
import com.visiongraphics_inc.alert_system.service.dto.AlertEventDTO;
import com.visiongraphics_inc.alert_system.service.mapper.AlertEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AlertEvent}.
 */
@Service
@Transactional
public class AlertEventService {

    private final Logger log = LoggerFactory.getLogger(AlertEventService.class);

    private final AlertEventRepository alertEventRepository;

    private final AlertEventMapper alertEventMapper;

    public AlertEventService(AlertEventRepository alertEventRepository, AlertEventMapper alertEventMapper) {
        this.alertEventRepository = alertEventRepository;
        this.alertEventMapper = alertEventMapper;
    }

    public AlertEventDTO save(AlertEventDTO alertEventDTO) {
        log.debug("Request to save AlertEvent : {}", alertEventDTO);
        AlertEvent alertEvent = alertEventMapper.toEntity(alertEventDTO);
        alertEvent = alertEventRepository.save(alertEvent);
        return alertEventMapper.toDto(alertEvent);
    }

    @Transactional(readOnly = true)
    public Page<AlertEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AlertEvents");
        return alertEventRepository.findAll(pageable)
            .map(alertEventMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<AlertEventDTO> findOne(Long id) {
        log.debug("Request to get AlertEvent : {}", id);
        return alertEventRepository.findById(id)
            .map(alertEventMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete AlertEvent : {}", id);
        alertEventRepository.deleteById(id);
    }
}
