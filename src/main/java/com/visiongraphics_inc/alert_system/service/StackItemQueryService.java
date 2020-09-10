package com.visiongraphics_inc.alert_system.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.visiongraphics_inc.alert_system.domain.StackItem;
import com.visiongraphics_inc.alert_system.domain.*; // for static metamodels
import com.visiongraphics_inc.alert_system.repository.StackItemRepository;
import com.visiongraphics_inc.alert_system.service.dto.StackItemCriteria;
import com.visiongraphics_inc.alert_system.service.dto.StackItemDTO;
import com.visiongraphics_inc.alert_system.service.mapper.StackItemMapper;

/**
 * Service for executing complex queries for {@link StackItem} entities in the database.
 * The main input is a {@link StackItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StackItemDTO} or a {@link Page} of {@link StackItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StackItemQueryService extends QueryService<StackItem> {

    private final Logger log = LoggerFactory.getLogger(StackItemQueryService.class);

    private final StackItemRepository stackItemRepository;

    private final StackItemMapper stackItemMapper;

    public StackItemQueryService(StackItemRepository stackItemRepository, StackItemMapper stackItemMapper) {
        this.stackItemRepository = stackItemRepository;
        this.stackItemMapper = stackItemMapper;
    }

    /**
     * Return a {@link List} of {@link StackItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StackItemDTO> findByCriteria(StackItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StackItem> specification = createSpecification(criteria);
        return stackItemMapper.toDto(stackItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StackItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StackItemDTO> findByCriteria(StackItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StackItem> specification = createSpecification(criteria);
        return stackItemRepository.findAll(specification, page)
            .map(stackItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StackItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StackItem> specification = createSpecification(criteria);
        return stackItemRepository.count(specification);
    }

    /**
     * Function to convert {@link StackItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StackItem> createSpecification(StackItemCriteria criteria) {
        Specification<StackItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StackItem_.id));
            }
            if (criteria.getSequenceNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequenceNo(), StackItem_.sequenceNo));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), StackItem_.fileName));
            }
            if (criteria.getClassName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassName(), StackItem_.className));
            }
            if (criteria.getMethodName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMethodName(), StackItem_.methodName));
            }
            if (criteria.getLineNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNumber(), StackItem_.lineNumber));
            }
            if (criteria.getAlertEventId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlertEventId(),
                    root -> root.join(StackItem_.alertEvent, JoinType.LEFT).get(AlertEvent_.id)));
            }
        }
        return specification;
    }
}
