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

import com.visiongraphics_inc.alert_system.domain.AlertEvent;
import com.visiongraphics_inc.alert_system.domain.*; // for static metamodels
import com.visiongraphics_inc.alert_system.repository.AlertEventRepository;
import com.visiongraphics_inc.alert_system.service.dto.AlertEventCriteria;
import com.visiongraphics_inc.alert_system.service.dto.AlertEventDTO;
import com.visiongraphics_inc.alert_system.service.mapper.AlertEventMapper;

/**
 * Service for executing complex queries for {@link AlertEvent} entities in the database.
 * The main input is a {@link AlertEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AlertEventDTO} or a {@link Page} of {@link AlertEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlertEventQueryService extends QueryService<AlertEvent> {

    private final Logger log = LoggerFactory.getLogger(AlertEventQueryService.class);

    private final AlertEventRepository alertEventRepository;

    private final AlertEventMapper alertEventMapper;

    public AlertEventQueryService(AlertEventRepository alertEventRepository, AlertEventMapper alertEventMapper) {
        this.alertEventRepository = alertEventRepository;
        this.alertEventMapper = alertEventMapper;
    }

    /**
     * Return a {@link List} of {@link AlertEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AlertEventDTO> findByCriteria(AlertEventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AlertEvent> specification = createSpecification(criteria);
        return alertEventMapper.toDto(alertEventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AlertEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlertEventDTO> findByCriteria(AlertEventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AlertEvent> specification = createSpecification(criteria);
        return alertEventRepository.findAll(specification, page)
            .map(alertEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlertEventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AlertEvent> specification = createSpecification(criteria);
        return alertEventRepository.count(specification);
    }

    /**
     * Function to convert {@link AlertEventCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AlertEvent> createSpecification(AlertEventCriteria criteria) {
        Specification<AlertEvent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AlertEvent_.id));
            }
            if (criteria.getApplicationName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApplicationName(), AlertEvent_.applicationName));
            }
            if (criteria.getModuleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModuleName(), AlertEvent_.moduleName));
            }
            if (criteria.getActionName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActionName(), AlertEvent_.actionName));
            }
            if (criteria.getSuggestedPriority() != null) {
                specification = specification.and(buildSpecification(criteria.getSuggestedPriority(), AlertEvent_.suggestedPriority));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), AlertEvent_.message));
            }
            if (criteria.getStackItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStackItemId(),
                    root -> root.join(AlertEvent_.stackItems, JoinType.LEFT).get(StackItem_.id)));
            }
        }
        return specification;
    }
}
