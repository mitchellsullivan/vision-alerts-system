package com.visiongraphics_inc.alert_system.service;

import com.visiongraphics_inc.alert_system.domain.StackItem;
import com.visiongraphics_inc.alert_system.repository.StackItemRepository;
import com.visiongraphics_inc.alert_system.service.dto.StackItemDTO;
import com.visiongraphics_inc.alert_system.service.mapper.StackItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StackItemService {

    private final Logger log = LoggerFactory.getLogger(StackItemService.class);

    private final StackItemRepository stackItemRepository;

    private final StackItemMapper stackItemMapper;

    public StackItemService(StackItemRepository stackItemRepository, StackItemMapper stackItemMapper) {
        this.stackItemRepository = stackItemRepository;
        this.stackItemMapper = stackItemMapper;
    }

    public StackItemDTO save(StackItemDTO stackItemDTO) {
        log.debug("Request to save StackItem : {}", stackItemDTO);
        StackItem stackItem = stackItemMapper.toEntity(stackItemDTO);
        stackItem = stackItemRepository.save(stackItem);
        return stackItemMapper.toDto(stackItem);
    }

    @Transactional(readOnly = true)
    public Page<StackItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StackItems");
        return stackItemRepository.findAll(pageable)
            .map(stackItemMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<StackItemDTO> findOne(Long id) {
        log.debug("Request to get StackItem : {}", id);
        return stackItemRepository.findById(id)
            .map(stackItemMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete StackItem : {}", id);
        stackItemRepository.deleteById(id);
    }
}
