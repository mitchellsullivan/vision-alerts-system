package com.visiongraphics_inc.alert_system.web.rest;

import com.visiongraphics_inc.alert_system.service.StackItemService;
import com.visiongraphics_inc.alert_system.web.rest.errors.BadRequestAlertException;
import com.visiongraphics_inc.alert_system.service.dto.StackItemDTO;
import com.visiongraphics_inc.alert_system.service.dto.StackItemCriteria;
import com.visiongraphics_inc.alert_system.service.StackItemQueryService;

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
 * REST controller for managing {@link com.visiongraphics_inc.alert_system.domain.StackItem}.
 */
@RestController
@RequestMapping("/api")
public class StackItemResource {

    private final Logger log = LoggerFactory.getLogger(StackItemResource.class);

    private static final String ENTITY_NAME = "stackItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StackItemService stackItemService;

    private final StackItemQueryService stackItemQueryService;

    public StackItemResource(StackItemService stackItemService, StackItemQueryService stackItemQueryService) {
        this.stackItemService = stackItemService;
        this.stackItemQueryService = stackItemQueryService;
    }

    /**
     * {@code POST  /stack-items} : Create a new stackItem.
     *
     * @param stackItemDTO the stackItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stackItemDTO, or with status {@code 400 (Bad Request)} if the stackItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stack-items")
    public ResponseEntity<StackItemDTO> createStackItem(@Valid @RequestBody StackItemDTO stackItemDTO) throws URISyntaxException {
        log.debug("REST request to save StackItem : {}", stackItemDTO);
        if (stackItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new stackItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StackItemDTO result = stackItemService.save(stackItemDTO);
        return ResponseEntity.created(new URI("/api/stack-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stack-items} : Updates an existing stackItem.
     *
     * @param stackItemDTO the stackItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stackItemDTO,
     * or with status {@code 400 (Bad Request)} if the stackItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stackItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stack-items")
    public ResponseEntity<StackItemDTO> updateStackItem(@Valid @RequestBody StackItemDTO stackItemDTO) throws URISyntaxException {
        log.debug("REST request to update StackItem : {}", stackItemDTO);
        if (stackItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StackItemDTO result = stackItemService.save(stackItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stackItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stack-items} : get all the stackItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stackItems in body.
     */
    @GetMapping("/stack-items")
    public ResponseEntity<List<StackItemDTO>> getAllStackItems(StackItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StackItems by criteria: {}", criteria);
        Page<StackItemDTO> page = stackItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stack-items/count} : count all the stackItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/stack-items/count")
    public ResponseEntity<Long> countStackItems(StackItemCriteria criteria) {
        log.debug("REST request to count StackItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(stackItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /stack-items/:id} : get the "id" stackItem.
     *
     * @param id the id of the stackItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stackItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stack-items/{id}")
    public ResponseEntity<StackItemDTO> getStackItem(@PathVariable Long id) {
        log.debug("REST request to get StackItem : {}", id);
        Optional<StackItemDTO> stackItemDTO = stackItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stackItemDTO);
    }

    /**
     * {@code DELETE  /stack-items/:id} : delete the "id" stackItem.
     *
     * @param id the id of the stackItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stack-items/{id}")
    public ResponseEntity<Void> deleteStackItem(@PathVariable Long id) {
        log.debug("REST request to delete StackItem : {}", id);
        stackItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
