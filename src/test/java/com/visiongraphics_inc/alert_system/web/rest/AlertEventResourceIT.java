package com.visiongraphics_inc.alert_system.web.rest;

import com.visiongraphics_inc.alert_system.VisionAlertSystemApp;
import com.visiongraphics_inc.alert_system.domain.AlertEvent;
import com.visiongraphics_inc.alert_system.domain.StackItem;
import com.visiongraphics_inc.alert_system.repository.AlertEventRepository;
import com.visiongraphics_inc.alert_system.service.AlertEventService;
import com.visiongraphics_inc.alert_system.service.dto.AlertEventDTO;
import com.visiongraphics_inc.alert_system.service.mapper.AlertEventMapper;
import com.visiongraphics_inc.alert_system.service.dto.AlertEventCriteria;
import com.visiongraphics_inc.alert_system.service.AlertEventQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.visiongraphics_inc.alert_system.domain.enumeration.AlertPriority;
/**
 * Integration tests for the {@link AlertEventResource} REST controller.
 */
@SpringBootTest(classes = VisionAlertSystemApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlertEventResourceIT {

    private static final String DEFAULT_APPLICATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODULE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MODULE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_NAME = "BBBBBBBBBB";

    private static final AlertPriority DEFAULT_SUGGESTED_PRIORITY = AlertPriority.TRACE;
    private static final AlertPriority UPDATED_SUGGESTED_PRIORITY = AlertPriority.DEBUG;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private AlertEventRepository alertEventRepository;

    @Autowired
    private AlertEventMapper alertEventMapper;

    @Autowired
    private AlertEventService alertEventService;

    @Autowired
    private AlertEventQueryService alertEventQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertEventMockMvc;

    private AlertEvent alertEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertEvent createEntity(EntityManager em) {
        AlertEvent alertEvent = new AlertEvent()
            .applicationName(DEFAULT_APPLICATION_NAME)
            .moduleName(DEFAULT_MODULE_NAME)
            .actionName(DEFAULT_ACTION_NAME)
            .suggestedPriority(DEFAULT_SUGGESTED_PRIORITY)
            .message(DEFAULT_MESSAGE);
        return alertEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertEvent createUpdatedEntity(EntityManager em) {
        AlertEvent alertEvent = new AlertEvent()
            .applicationName(UPDATED_APPLICATION_NAME)
            .moduleName(UPDATED_MODULE_NAME)
            .actionName(UPDATED_ACTION_NAME)
            .suggestedPriority(UPDATED_SUGGESTED_PRIORITY)
            .message(UPDATED_MESSAGE);
        return alertEvent;
    }

    @BeforeEach
    public void initTest() {
        alertEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlertEvent() throws Exception {
        int databaseSizeBeforeCreate = alertEventRepository.findAll().size();
        // Create the AlertEvent
        AlertEventDTO alertEventDTO = alertEventMapper.toDto(alertEvent);
        restAlertEventMockMvc.perform(post("/api/alert-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertEventDTO)))
            .andExpect(status().isCreated());

        // Validate the AlertEvent in the database
        List<AlertEvent> alertEventList = alertEventRepository.findAll();
        assertThat(alertEventList).hasSize(databaseSizeBeforeCreate + 1);
        AlertEvent testAlertEvent = alertEventList.get(alertEventList.size() - 1);
        assertThat(testAlertEvent.getApplicationName()).isEqualTo(DEFAULT_APPLICATION_NAME);
        assertThat(testAlertEvent.getModuleName()).isEqualTo(DEFAULT_MODULE_NAME);
        assertThat(testAlertEvent.getActionName()).isEqualTo(DEFAULT_ACTION_NAME);
        assertThat(testAlertEvent.getSuggestedPriority()).isEqualTo(DEFAULT_SUGGESTED_PRIORITY);
        assertThat(testAlertEvent.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void createAlertEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertEventRepository.findAll().size();

        // Create the AlertEvent with an existing ID
        alertEvent.setId(1L);
        AlertEventDTO alertEventDTO = alertEventMapper.toDto(alertEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertEventMockMvc.perform(post("/api/alert-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertEvent in the database
        List<AlertEvent> alertEventList = alertEventRepository.findAll();
        assertThat(alertEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkApplicationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertEventRepository.findAll().size();
        // set the field null
        alertEvent.setApplicationName(null);

        // Create the AlertEvent, which fails.
        AlertEventDTO alertEventDTO = alertEventMapper.toDto(alertEvent);


        restAlertEventMockMvc.perform(post("/api/alert-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertEventDTO)))
            .andExpect(status().isBadRequest());

        List<AlertEvent> alertEventList = alertEventRepository.findAll();
        assertThat(alertEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertEventRepository.findAll().size();
        // set the field null
        alertEvent.setMessage(null);

        // Create the AlertEvent, which fails.
        AlertEventDTO alertEventDTO = alertEventMapper.toDto(alertEvent);


        restAlertEventMockMvc.perform(post("/api/alert-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertEventDTO)))
            .andExpect(status().isBadRequest());

        List<AlertEvent> alertEventList = alertEventRepository.findAll();
        assertThat(alertEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlertEvents() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList
        restAlertEventMockMvc.perform(get("/api/alert-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationName").value(hasItem(DEFAULT_APPLICATION_NAME)))
            .andExpect(jsonPath("$.[*].moduleName").value(hasItem(DEFAULT_MODULE_NAME)))
            .andExpect(jsonPath("$.[*].actionName").value(hasItem(DEFAULT_ACTION_NAME)))
            .andExpect(jsonPath("$.[*].suggestedPriority").value(hasItem(DEFAULT_SUGGESTED_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }
    
    @Test
    @Transactional
    public void getAlertEvent() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get the alertEvent
        restAlertEventMockMvc.perform(get("/api/alert-events/{id}", alertEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alertEvent.getId().intValue()))
            .andExpect(jsonPath("$.applicationName").value(DEFAULT_APPLICATION_NAME))
            .andExpect(jsonPath("$.moduleName").value(DEFAULT_MODULE_NAME))
            .andExpect(jsonPath("$.actionName").value(DEFAULT_ACTION_NAME))
            .andExpect(jsonPath("$.suggestedPriority").value(DEFAULT_SUGGESTED_PRIORITY.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }


    @Test
    @Transactional
    public void getAlertEventsByIdFiltering() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        Long id = alertEvent.getId();

        defaultAlertEventShouldBeFound("id.equals=" + id);
        defaultAlertEventShouldNotBeFound("id.notEquals=" + id);

        defaultAlertEventShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlertEventShouldNotBeFound("id.greaterThan=" + id);

        defaultAlertEventShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlertEventShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlertEventsByApplicationNameIsEqualToSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where applicationName equals to DEFAULT_APPLICATION_NAME
        defaultAlertEventShouldBeFound("applicationName.equals=" + DEFAULT_APPLICATION_NAME);

        // Get all the alertEventList where applicationName equals to UPDATED_APPLICATION_NAME
        defaultAlertEventShouldNotBeFound("applicationName.equals=" + UPDATED_APPLICATION_NAME);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByApplicationNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where applicationName not equals to DEFAULT_APPLICATION_NAME
        defaultAlertEventShouldNotBeFound("applicationName.notEquals=" + DEFAULT_APPLICATION_NAME);

        // Get all the alertEventList where applicationName not equals to UPDATED_APPLICATION_NAME
        defaultAlertEventShouldBeFound("applicationName.notEquals=" + UPDATED_APPLICATION_NAME);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByApplicationNameIsInShouldWork() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where applicationName in DEFAULT_APPLICATION_NAME or UPDATED_APPLICATION_NAME
        defaultAlertEventShouldBeFound("applicationName.in=" + DEFAULT_APPLICATION_NAME + "," + UPDATED_APPLICATION_NAME);

        // Get all the alertEventList where applicationName equals to UPDATED_APPLICATION_NAME
        defaultAlertEventShouldNotBeFound("applicationName.in=" + UPDATED_APPLICATION_NAME);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByApplicationNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where applicationName is not null
        defaultAlertEventShouldBeFound("applicationName.specified=true");

        // Get all the alertEventList where applicationName is null
        defaultAlertEventShouldNotBeFound("applicationName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertEventsByApplicationNameContainsSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where applicationName contains DEFAULT_APPLICATION_NAME
        defaultAlertEventShouldBeFound("applicationName.contains=" + DEFAULT_APPLICATION_NAME);

        // Get all the alertEventList where applicationName contains UPDATED_APPLICATION_NAME
        defaultAlertEventShouldNotBeFound("applicationName.contains=" + UPDATED_APPLICATION_NAME);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByApplicationNameNotContainsSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where applicationName does not contain DEFAULT_APPLICATION_NAME
        defaultAlertEventShouldNotBeFound("applicationName.doesNotContain=" + DEFAULT_APPLICATION_NAME);

        // Get all the alertEventList where applicationName does not contain UPDATED_APPLICATION_NAME
        defaultAlertEventShouldBeFound("applicationName.doesNotContain=" + UPDATED_APPLICATION_NAME);
    }


    @Test
    @Transactional
    public void getAllAlertEventsByModuleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where moduleName equals to DEFAULT_MODULE_NAME
        defaultAlertEventShouldBeFound("moduleName.equals=" + DEFAULT_MODULE_NAME);

        // Get all the alertEventList where moduleName equals to UPDATED_MODULE_NAME
        defaultAlertEventShouldNotBeFound("moduleName.equals=" + UPDATED_MODULE_NAME);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByModuleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where moduleName not equals to DEFAULT_MODULE_NAME
        defaultAlertEventShouldNotBeFound("moduleName.notEquals=" + DEFAULT_MODULE_NAME);

        // Get all the alertEventList where moduleName not equals to UPDATED_MODULE_NAME
        defaultAlertEventShouldBeFound("moduleName.notEquals=" + UPDATED_MODULE_NAME);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByModuleNameIsInShouldWork() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where moduleName in DEFAULT_MODULE_NAME or UPDATED_MODULE_NAME
        defaultAlertEventShouldBeFound("moduleName.in=" + DEFAULT_MODULE_NAME + "," + UPDATED_MODULE_NAME);

        // Get all the alertEventList where moduleName equals to UPDATED_MODULE_NAME
        defaultAlertEventShouldNotBeFound("moduleName.in=" + UPDATED_MODULE_NAME);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByModuleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where moduleName is not null
        defaultAlertEventShouldBeFound("moduleName.specified=true");

        // Get all the alertEventList where moduleName is null
        defaultAlertEventShouldNotBeFound("moduleName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertEventsByModuleNameContainsSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where moduleName contains DEFAULT_MODULE_NAME
        defaultAlertEventShouldBeFound("moduleName.contains=" + DEFAULT_MODULE_NAME);

        // Get all the alertEventList where moduleName contains UPDATED_MODULE_NAME
        defaultAlertEventShouldNotBeFound("moduleName.contains=" + UPDATED_MODULE_NAME);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByModuleNameNotContainsSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where moduleName does not contain DEFAULT_MODULE_NAME
        defaultAlertEventShouldNotBeFound("moduleName.doesNotContain=" + DEFAULT_MODULE_NAME);

        // Get all the alertEventList where moduleName does not contain UPDATED_MODULE_NAME
        defaultAlertEventShouldBeFound("moduleName.doesNotContain=" + UPDATED_MODULE_NAME);
    }


    @Test
    @Transactional
    public void getAllAlertEventsByActionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where actionName equals to DEFAULT_ACTION_NAME
        defaultAlertEventShouldBeFound("actionName.equals=" + DEFAULT_ACTION_NAME);

        // Get all the alertEventList where actionName equals to UPDATED_ACTION_NAME
        defaultAlertEventShouldNotBeFound("actionName.equals=" + UPDATED_ACTION_NAME);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByActionNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where actionName not equals to DEFAULT_ACTION_NAME
        defaultAlertEventShouldNotBeFound("actionName.notEquals=" + DEFAULT_ACTION_NAME);

        // Get all the alertEventList where actionName not equals to UPDATED_ACTION_NAME
        defaultAlertEventShouldBeFound("actionName.notEquals=" + UPDATED_ACTION_NAME);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByActionNameIsInShouldWork() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where actionName in DEFAULT_ACTION_NAME or UPDATED_ACTION_NAME
        defaultAlertEventShouldBeFound("actionName.in=" + DEFAULT_ACTION_NAME + "," + UPDATED_ACTION_NAME);

        // Get all the alertEventList where actionName equals to UPDATED_ACTION_NAME
        defaultAlertEventShouldNotBeFound("actionName.in=" + UPDATED_ACTION_NAME);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByActionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where actionName is not null
        defaultAlertEventShouldBeFound("actionName.specified=true");

        // Get all the alertEventList where actionName is null
        defaultAlertEventShouldNotBeFound("actionName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertEventsByActionNameContainsSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where actionName contains DEFAULT_ACTION_NAME
        defaultAlertEventShouldBeFound("actionName.contains=" + DEFAULT_ACTION_NAME);

        // Get all the alertEventList where actionName contains UPDATED_ACTION_NAME
        defaultAlertEventShouldNotBeFound("actionName.contains=" + UPDATED_ACTION_NAME);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByActionNameNotContainsSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where actionName does not contain DEFAULT_ACTION_NAME
        defaultAlertEventShouldNotBeFound("actionName.doesNotContain=" + DEFAULT_ACTION_NAME);

        // Get all the alertEventList where actionName does not contain UPDATED_ACTION_NAME
        defaultAlertEventShouldBeFound("actionName.doesNotContain=" + UPDATED_ACTION_NAME);
    }


    @Test
    @Transactional
    public void getAllAlertEventsBySuggestedPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where suggestedPriority equals to DEFAULT_SUGGESTED_PRIORITY
        defaultAlertEventShouldBeFound("suggestedPriority.equals=" + DEFAULT_SUGGESTED_PRIORITY);

        // Get all the alertEventList where suggestedPriority equals to UPDATED_SUGGESTED_PRIORITY
        defaultAlertEventShouldNotBeFound("suggestedPriority.equals=" + UPDATED_SUGGESTED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllAlertEventsBySuggestedPriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where suggestedPriority not equals to DEFAULT_SUGGESTED_PRIORITY
        defaultAlertEventShouldNotBeFound("suggestedPriority.notEquals=" + DEFAULT_SUGGESTED_PRIORITY);

        // Get all the alertEventList where suggestedPriority not equals to UPDATED_SUGGESTED_PRIORITY
        defaultAlertEventShouldBeFound("suggestedPriority.notEquals=" + UPDATED_SUGGESTED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllAlertEventsBySuggestedPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where suggestedPriority in DEFAULT_SUGGESTED_PRIORITY or UPDATED_SUGGESTED_PRIORITY
        defaultAlertEventShouldBeFound("suggestedPriority.in=" + DEFAULT_SUGGESTED_PRIORITY + "," + UPDATED_SUGGESTED_PRIORITY);

        // Get all the alertEventList where suggestedPriority equals to UPDATED_SUGGESTED_PRIORITY
        defaultAlertEventShouldNotBeFound("suggestedPriority.in=" + UPDATED_SUGGESTED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllAlertEventsBySuggestedPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where suggestedPriority is not null
        defaultAlertEventShouldBeFound("suggestedPriority.specified=true");

        // Get all the alertEventList where suggestedPriority is null
        defaultAlertEventShouldNotBeFound("suggestedPriority.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlertEventsByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where message equals to DEFAULT_MESSAGE
        defaultAlertEventShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the alertEventList where message equals to UPDATED_MESSAGE
        defaultAlertEventShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where message not equals to DEFAULT_MESSAGE
        defaultAlertEventShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the alertEventList where message not equals to UPDATED_MESSAGE
        defaultAlertEventShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultAlertEventShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the alertEventList where message equals to UPDATED_MESSAGE
        defaultAlertEventShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where message is not null
        defaultAlertEventShouldBeFound("message.specified=true");

        // Get all the alertEventList where message is null
        defaultAlertEventShouldNotBeFound("message.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertEventsByMessageContainsSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where message contains DEFAULT_MESSAGE
        defaultAlertEventShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the alertEventList where message contains UPDATED_MESSAGE
        defaultAlertEventShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllAlertEventsByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        // Get all the alertEventList where message does not contain DEFAULT_MESSAGE
        defaultAlertEventShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the alertEventList where message does not contain UPDATED_MESSAGE
        defaultAlertEventShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }


    @Test
    @Transactional
    public void getAllAlertEventsByStackItemIsEqualToSomething() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);
        StackItem stackItem = StackItemResourceIT.createEntity(em);
        em.persist(stackItem);
        em.flush();
        alertEvent.addStackItem(stackItem);
        alertEventRepository.saveAndFlush(alertEvent);
        Long stackItemId = stackItem.getId();

        // Get all the alertEventList where stackItem equals to stackItemId
        defaultAlertEventShouldBeFound("stackItemId.equals=" + stackItemId);

        // Get all the alertEventList where stackItem equals to stackItemId + 1
        defaultAlertEventShouldNotBeFound("stackItemId.equals=" + (stackItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlertEventShouldBeFound(String filter) throws Exception {
        restAlertEventMockMvc.perform(get("/api/alert-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationName").value(hasItem(DEFAULT_APPLICATION_NAME)))
            .andExpect(jsonPath("$.[*].moduleName").value(hasItem(DEFAULT_MODULE_NAME)))
            .andExpect(jsonPath("$.[*].actionName").value(hasItem(DEFAULT_ACTION_NAME)))
            .andExpect(jsonPath("$.[*].suggestedPriority").value(hasItem(DEFAULT_SUGGESTED_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));

        // Check, that the count call also returns 1
        restAlertEventMockMvc.perform(get("/api/alert-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlertEventShouldNotBeFound(String filter) throws Exception {
        restAlertEventMockMvc.perform(get("/api/alert-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlertEventMockMvc.perform(get("/api/alert-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAlertEvent() throws Exception {
        // Get the alertEvent
        restAlertEventMockMvc.perform(get("/api/alert-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlertEvent() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        int databaseSizeBeforeUpdate = alertEventRepository.findAll().size();

        // Update the alertEvent
        AlertEvent updatedAlertEvent = alertEventRepository.findById(alertEvent.getId()).get();
        // Disconnect from session so that the updates on updatedAlertEvent are not directly saved in db
        em.detach(updatedAlertEvent);
        updatedAlertEvent
            .applicationName(UPDATED_APPLICATION_NAME)
            .moduleName(UPDATED_MODULE_NAME)
            .actionName(UPDATED_ACTION_NAME)
            .suggestedPriority(UPDATED_SUGGESTED_PRIORITY)
            .message(UPDATED_MESSAGE);
        AlertEventDTO alertEventDTO = alertEventMapper.toDto(updatedAlertEvent);

        restAlertEventMockMvc.perform(put("/api/alert-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertEventDTO)))
            .andExpect(status().isOk());

        // Validate the AlertEvent in the database
        List<AlertEvent> alertEventList = alertEventRepository.findAll();
        assertThat(alertEventList).hasSize(databaseSizeBeforeUpdate);
        AlertEvent testAlertEvent = alertEventList.get(alertEventList.size() - 1);
        assertThat(testAlertEvent.getApplicationName()).isEqualTo(UPDATED_APPLICATION_NAME);
        assertThat(testAlertEvent.getModuleName()).isEqualTo(UPDATED_MODULE_NAME);
        assertThat(testAlertEvent.getActionName()).isEqualTo(UPDATED_ACTION_NAME);
        assertThat(testAlertEvent.getSuggestedPriority()).isEqualTo(UPDATED_SUGGESTED_PRIORITY);
        assertThat(testAlertEvent.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingAlertEvent() throws Exception {
        int databaseSizeBeforeUpdate = alertEventRepository.findAll().size();

        // Create the AlertEvent
        AlertEventDTO alertEventDTO = alertEventMapper.toDto(alertEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertEventMockMvc.perform(put("/api/alert-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertEvent in the database
        List<AlertEvent> alertEventList = alertEventRepository.findAll();
        assertThat(alertEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlertEvent() throws Exception {
        // Initialize the database
        alertEventRepository.saveAndFlush(alertEvent);

        int databaseSizeBeforeDelete = alertEventRepository.findAll().size();

        // Delete the alertEvent
        restAlertEventMockMvc.perform(delete("/api/alert-events/{id}", alertEvent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlertEvent> alertEventList = alertEventRepository.findAll();
        assertThat(alertEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
