package com.visiongraphics_inc.alert_system.web.rest;

import com.visiongraphics_inc.alert_system.service.AlertEventService;
import com.visiongraphics_inc.alert_system.web.rest.errors.BadRequestAlertException;
import com.visiongraphics_inc.alert_system.service.dto.AlertEventDTO;
import com.visiongraphics_inc.alert_system.service.dto.AlertEventCriteria;
import com.visiongraphics_inc.alert_system.service.AlertEventQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.visiongraphics_inc.alert_system.domain.AlertEvent}.
 */
@RestController
@RequestMapping("/api")
public class AlertEventResource {

    private final Logger log = LoggerFactory.getLogger(AlertEventResource.class);

    private static final String ENTITY_NAME = "alertEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlertEventService alertEventService;

    private final AlertEventQueryService alertEventQueryService;

    public AlertEventResource(AlertEventService alertEventService, AlertEventQueryService alertEventQueryService) {
        this.alertEventService = alertEventService;
        this.alertEventQueryService = alertEventQueryService;
    }

    /**
     * {@code POST  /alert-events} : Create a new alertEvent.
     *
     * @param alertEventDTO the alertEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alertEventDTO, or with status {@code 400 (Bad Request)} if the alertEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alert-events")
    public ResponseEntity<AlertEventDTO> createAlertEvent(@Valid @RequestBody AlertEventDTO alertEventDTO) throws URISyntaxException {
        log.debug("REST request to save AlertEvent : {}", alertEventDTO);
        if (alertEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new alertEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlertEventDTO result = alertEventService.save(alertEventDTO);
        return ResponseEntity.created(new URI("/api/alert-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alert-events} : Updates an existing alertEvent.
     *
     * @param alertEventDTO the alertEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alertEventDTO,
     * or with status {@code 400 (Bad Request)} if the alertEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alertEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alert-events")
    public ResponseEntity<AlertEventDTO> updateAlertEvent(@Valid @RequestBody AlertEventDTO alertEventDTO) throws URISyntaxException {
        log.debug("REST request to update AlertEvent : {}", alertEventDTO);
        if (alertEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlertEventDTO result = alertEventService.save(alertEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alertEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alert-events} : get all the alertEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alertEvents in body.
     */
    @GetMapping("/alert-events")
    public ResponseEntity<List<AlertEventDTO>> getAllAlertEvents(AlertEventCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AlertEvents by criteria: {}", criteria);
        Page<AlertEventDTO> page = alertEventQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alert-events/count} : count all the alertEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/alert-events/count")
    public ResponseEntity<Long> countAlertEvents(AlertEventCriteria criteria) {
        log.debug("REST request to count AlertEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(alertEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /alert-events/:id} : get the "id" alertEvent.
     *
     * @param id the id of the alertEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alertEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alert-events/{id}")
    public ResponseEntity<AlertEventDTO> getAlertEvent(@PathVariable Long id) {
        log.debug("REST request to get AlertEvent : {}", id);
        Optional<AlertEventDTO> alertEventDTO = alertEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alertEventDTO);
    }

    /**
     * {@code DELETE  /alert-events/:id} : delete the "id" alertEvent.
     *
     * @param id the id of the alertEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alert-events/{id}")
    public ResponseEntity<Void> deleteAlertEvent(@PathVariable Long id) {
        log.debug("REST request to delete AlertEvent : {}", id);
        alertEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
