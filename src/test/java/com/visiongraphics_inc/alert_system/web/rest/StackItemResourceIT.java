package com.visiongraphics_inc.alert_system.web.rest;

import com.visiongraphics_inc.alert_system.VisionAlertSystemApp;
import com.visiongraphics_inc.alert_system.domain.StackItem;
import com.visiongraphics_inc.alert_system.domain.AlertEvent;
import com.visiongraphics_inc.alert_system.repository.StackItemRepository;
import com.visiongraphics_inc.alert_system.service.StackItemService;
import com.visiongraphics_inc.alert_system.service.dto.StackItemDTO;
import com.visiongraphics_inc.alert_system.service.mapper.StackItemMapper;
import com.visiongraphics_inc.alert_system.service.dto.StackItemCriteria;
import com.visiongraphics_inc.alert_system.service.StackItemQueryService;

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

/**
 * Integration tests for the {@link StackItemResource} REST controller.
 */
@SpringBootTest(classes = VisionAlertSystemApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StackItemResourceIT {

    private static final Long DEFAULT_SEQUENCE_NO = 1L;
    private static final Long UPDATED_SEQUENCE_NO = 2L;
    private static final Long SMALLER_SEQUENCE_NO = 1L - 1L;

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLASS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_METHOD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_METHOD_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_LINE_NUMBER = 1L;
    private static final Long UPDATED_LINE_NUMBER = 2L;
    private static final Long SMALLER_LINE_NUMBER = 1L - 1L;

    @Autowired
    private StackItemRepository stackItemRepository;

    @Autowired
    private StackItemMapper stackItemMapper;

    @Autowired
    private StackItemService stackItemService;

    @Autowired
    private StackItemQueryService stackItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStackItemMockMvc;

    private StackItem stackItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StackItem createEntity(EntityManager em) {
        StackItem stackItem = new StackItem()
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .fileName(DEFAULT_FILE_NAME)
            .className(DEFAULT_CLASS_NAME)
            .methodName(DEFAULT_METHOD_NAME)
            .lineNumber(DEFAULT_LINE_NUMBER);
        return stackItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StackItem createUpdatedEntity(EntityManager em) {
        StackItem stackItem = new StackItem()
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .fileName(UPDATED_FILE_NAME)
            .className(UPDATED_CLASS_NAME)
            .methodName(UPDATED_METHOD_NAME)
            .lineNumber(UPDATED_LINE_NUMBER);
        return stackItem;
    }

    @BeforeEach
    public void initTest() {
        stackItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createStackItem() throws Exception {
        int databaseSizeBeforeCreate = stackItemRepository.findAll().size();
        // Create the StackItem
        StackItemDTO stackItemDTO = stackItemMapper.toDto(stackItem);
        restStackItemMockMvc.perform(post("/api/stack-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stackItemDTO)))
            .andExpect(status().isCreated());

        // Validate the StackItem in the database
        List<StackItem> stackItemList = stackItemRepository.findAll();
        assertThat(stackItemList).hasSize(databaseSizeBeforeCreate + 1);
        StackItem testStackItem = stackItemList.get(stackItemList.size() - 1);
        assertThat(testStackItem.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testStackItem.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testStackItem.getClassName()).isEqualTo(DEFAULT_CLASS_NAME);
        assertThat(testStackItem.getMethodName()).isEqualTo(DEFAULT_METHOD_NAME);
        assertThat(testStackItem.getLineNumber()).isEqualTo(DEFAULT_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void createStackItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stackItemRepository.findAll().size();

        // Create the StackItem with an existing ID
        stackItem.setId(1L);
        StackItemDTO stackItemDTO = stackItemMapper.toDto(stackItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStackItemMockMvc.perform(post("/api/stack-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stackItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StackItem in the database
        List<StackItem> stackItemList = stackItemRepository.findAll();
        assertThat(stackItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stackItemRepository.findAll().size();
        // set the field null
        stackItem.setFileName(null);

        // Create the StackItem, which fails.
        StackItemDTO stackItemDTO = stackItemMapper.toDto(stackItem);


        restStackItemMockMvc.perform(post("/api/stack-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stackItemDTO)))
            .andExpect(status().isBadRequest());

        List<StackItem> stackItemList = stackItemRepository.findAll();
        assertThat(stackItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClassNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stackItemRepository.findAll().size();
        // set the field null
        stackItem.setClassName(null);

        // Create the StackItem, which fails.
        StackItemDTO stackItemDTO = stackItemMapper.toDto(stackItem);


        restStackItemMockMvc.perform(post("/api/stack-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stackItemDTO)))
            .andExpect(status().isBadRequest());

        List<StackItem> stackItemList = stackItemRepository.findAll();
        assertThat(stackItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMethodNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stackItemRepository.findAll().size();
        // set the field null
        stackItem.setMethodName(null);

        // Create the StackItem, which fails.
        StackItemDTO stackItemDTO = stackItemMapper.toDto(stackItem);


        restStackItemMockMvc.perform(post("/api/stack-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stackItemDTO)))
            .andExpect(status().isBadRequest());

        List<StackItem> stackItemList = stackItemRepository.findAll();
        assertThat(stackItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLineNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = stackItemRepository.findAll().size();
        // set the field null
        stackItem.setLineNumber(null);

        // Create the StackItem, which fails.
        StackItemDTO stackItemDTO = stackItemMapper.toDto(stackItem);


        restStackItemMockMvc.perform(post("/api/stack-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stackItemDTO)))
            .andExpect(status().isBadRequest());

        List<StackItem> stackItemList = stackItemRepository.findAll();
        assertThat(stackItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStackItems() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList
        restStackItemMockMvc.perform(get("/api/stack-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stackItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO.intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].className").value(hasItem(DEFAULT_CLASS_NAME)))
            .andExpect(jsonPath("$.[*].methodName").value(hasItem(DEFAULT_METHOD_NAME)))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER.intValue())));
    }
    
    @Test
    @Transactional
    public void getStackItem() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get the stackItem
        restStackItemMockMvc.perform(get("/api/stack-items/{id}", stackItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stackItem.getId().intValue()))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO.intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.className").value(DEFAULT_CLASS_NAME))
            .andExpect(jsonPath("$.methodName").value(DEFAULT_METHOD_NAME))
            .andExpect(jsonPath("$.lineNumber").value(DEFAULT_LINE_NUMBER.intValue()));
    }


    @Test
    @Transactional
    public void getStackItemsByIdFiltering() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        Long id = stackItem.getId();

        defaultStackItemShouldBeFound("id.equals=" + id);
        defaultStackItemShouldNotBeFound("id.notEquals=" + id);

        defaultStackItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStackItemShouldNotBeFound("id.greaterThan=" + id);

        defaultStackItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStackItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStackItemsBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultStackItemShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the stackItemList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultStackItemShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllStackItemsBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultStackItemShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the stackItemList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultStackItemShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllStackItemsBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultStackItemShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the stackItemList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultStackItemShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllStackItemsBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where sequenceNo is not null
        defaultStackItemShouldBeFound("sequenceNo.specified=true");

        // Get all the stackItemList where sequenceNo is null
        defaultStackItemShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllStackItemsBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultStackItemShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the stackItemList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultStackItemShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllStackItemsBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultStackItemShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the stackItemList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultStackItemShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllStackItemsBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultStackItemShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the stackItemList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultStackItemShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllStackItemsBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultStackItemShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the stackItemList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultStackItemShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllStackItemsByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where fileName equals to DEFAULT_FILE_NAME
        defaultStackItemShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the stackItemList where fileName equals to UPDATED_FILE_NAME
        defaultStackItemShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllStackItemsByFileNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where fileName not equals to DEFAULT_FILE_NAME
        defaultStackItemShouldNotBeFound("fileName.notEquals=" + DEFAULT_FILE_NAME);

        // Get all the stackItemList where fileName not equals to UPDATED_FILE_NAME
        defaultStackItemShouldBeFound("fileName.notEquals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllStackItemsByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultStackItemShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the stackItemList where fileName equals to UPDATED_FILE_NAME
        defaultStackItemShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllStackItemsByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where fileName is not null
        defaultStackItemShouldBeFound("fileName.specified=true");

        // Get all the stackItemList where fileName is null
        defaultStackItemShouldNotBeFound("fileName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStackItemsByFileNameContainsSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where fileName contains DEFAULT_FILE_NAME
        defaultStackItemShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the stackItemList where fileName contains UPDATED_FILE_NAME
        defaultStackItemShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllStackItemsByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where fileName does not contain DEFAULT_FILE_NAME
        defaultStackItemShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the stackItemList where fileName does not contain UPDATED_FILE_NAME
        defaultStackItemShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }


    @Test
    @Transactional
    public void getAllStackItemsByClassNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where className equals to DEFAULT_CLASS_NAME
        defaultStackItemShouldBeFound("className.equals=" + DEFAULT_CLASS_NAME);

        // Get all the stackItemList where className equals to UPDATED_CLASS_NAME
        defaultStackItemShouldNotBeFound("className.equals=" + UPDATED_CLASS_NAME);
    }

    @Test
    @Transactional
    public void getAllStackItemsByClassNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where className not equals to DEFAULT_CLASS_NAME
        defaultStackItemShouldNotBeFound("className.notEquals=" + DEFAULT_CLASS_NAME);

        // Get all the stackItemList where className not equals to UPDATED_CLASS_NAME
        defaultStackItemShouldBeFound("className.notEquals=" + UPDATED_CLASS_NAME);
    }

    @Test
    @Transactional
    public void getAllStackItemsByClassNameIsInShouldWork() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where className in DEFAULT_CLASS_NAME or UPDATED_CLASS_NAME
        defaultStackItemShouldBeFound("className.in=" + DEFAULT_CLASS_NAME + "," + UPDATED_CLASS_NAME);

        // Get all the stackItemList where className equals to UPDATED_CLASS_NAME
        defaultStackItemShouldNotBeFound("className.in=" + UPDATED_CLASS_NAME);
    }

    @Test
    @Transactional
    public void getAllStackItemsByClassNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where className is not null
        defaultStackItemShouldBeFound("className.specified=true");

        // Get all the stackItemList where className is null
        defaultStackItemShouldNotBeFound("className.specified=false");
    }
                @Test
    @Transactional
    public void getAllStackItemsByClassNameContainsSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where className contains DEFAULT_CLASS_NAME
        defaultStackItemShouldBeFound("className.contains=" + DEFAULT_CLASS_NAME);

        // Get all the stackItemList where className contains UPDATED_CLASS_NAME
        defaultStackItemShouldNotBeFound("className.contains=" + UPDATED_CLASS_NAME);
    }

    @Test
    @Transactional
    public void getAllStackItemsByClassNameNotContainsSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where className does not contain DEFAULT_CLASS_NAME
        defaultStackItemShouldNotBeFound("className.doesNotContain=" + DEFAULT_CLASS_NAME);

        // Get all the stackItemList where className does not contain UPDATED_CLASS_NAME
        defaultStackItemShouldBeFound("className.doesNotContain=" + UPDATED_CLASS_NAME);
    }


    @Test
    @Transactional
    public void getAllStackItemsByMethodNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where methodName equals to DEFAULT_METHOD_NAME
        defaultStackItemShouldBeFound("methodName.equals=" + DEFAULT_METHOD_NAME);

        // Get all the stackItemList where methodName equals to UPDATED_METHOD_NAME
        defaultStackItemShouldNotBeFound("methodName.equals=" + UPDATED_METHOD_NAME);
    }

    @Test
    @Transactional
    public void getAllStackItemsByMethodNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where methodName not equals to DEFAULT_METHOD_NAME
        defaultStackItemShouldNotBeFound("methodName.notEquals=" + DEFAULT_METHOD_NAME);

        // Get all the stackItemList where methodName not equals to UPDATED_METHOD_NAME
        defaultStackItemShouldBeFound("methodName.notEquals=" + UPDATED_METHOD_NAME);
    }

    @Test
    @Transactional
    public void getAllStackItemsByMethodNameIsInShouldWork() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where methodName in DEFAULT_METHOD_NAME or UPDATED_METHOD_NAME
        defaultStackItemShouldBeFound("methodName.in=" + DEFAULT_METHOD_NAME + "," + UPDATED_METHOD_NAME);

        // Get all the stackItemList where methodName equals to UPDATED_METHOD_NAME
        defaultStackItemShouldNotBeFound("methodName.in=" + UPDATED_METHOD_NAME);
    }

    @Test
    @Transactional
    public void getAllStackItemsByMethodNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where methodName is not null
        defaultStackItemShouldBeFound("methodName.specified=true");

        // Get all the stackItemList where methodName is null
        defaultStackItemShouldNotBeFound("methodName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStackItemsByMethodNameContainsSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where methodName contains DEFAULT_METHOD_NAME
        defaultStackItemShouldBeFound("methodName.contains=" + DEFAULT_METHOD_NAME);

        // Get all the stackItemList where methodName contains UPDATED_METHOD_NAME
        defaultStackItemShouldNotBeFound("methodName.contains=" + UPDATED_METHOD_NAME);
    }

    @Test
    @Transactional
    public void getAllStackItemsByMethodNameNotContainsSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where methodName does not contain DEFAULT_METHOD_NAME
        defaultStackItemShouldNotBeFound("methodName.doesNotContain=" + DEFAULT_METHOD_NAME);

        // Get all the stackItemList where methodName does not contain UPDATED_METHOD_NAME
        defaultStackItemShouldBeFound("methodName.doesNotContain=" + UPDATED_METHOD_NAME);
    }


    @Test
    @Transactional
    public void getAllStackItemsByLineNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where lineNumber equals to DEFAULT_LINE_NUMBER
        defaultStackItemShouldBeFound("lineNumber.equals=" + DEFAULT_LINE_NUMBER);

        // Get all the stackItemList where lineNumber equals to UPDATED_LINE_NUMBER
        defaultStackItemShouldNotBeFound("lineNumber.equals=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStackItemsByLineNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where lineNumber not equals to DEFAULT_LINE_NUMBER
        defaultStackItemShouldNotBeFound("lineNumber.notEquals=" + DEFAULT_LINE_NUMBER);

        // Get all the stackItemList where lineNumber not equals to UPDATED_LINE_NUMBER
        defaultStackItemShouldBeFound("lineNumber.notEquals=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStackItemsByLineNumberIsInShouldWork() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where lineNumber in DEFAULT_LINE_NUMBER or UPDATED_LINE_NUMBER
        defaultStackItemShouldBeFound("lineNumber.in=" + DEFAULT_LINE_NUMBER + "," + UPDATED_LINE_NUMBER);

        // Get all the stackItemList where lineNumber equals to UPDATED_LINE_NUMBER
        defaultStackItemShouldNotBeFound("lineNumber.in=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStackItemsByLineNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where lineNumber is not null
        defaultStackItemShouldBeFound("lineNumber.specified=true");

        // Get all the stackItemList where lineNumber is null
        defaultStackItemShouldNotBeFound("lineNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllStackItemsByLineNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where lineNumber is greater than or equal to DEFAULT_LINE_NUMBER
        defaultStackItemShouldBeFound("lineNumber.greaterThanOrEqual=" + DEFAULT_LINE_NUMBER);

        // Get all the stackItemList where lineNumber is greater than or equal to UPDATED_LINE_NUMBER
        defaultStackItemShouldNotBeFound("lineNumber.greaterThanOrEqual=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStackItemsByLineNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where lineNumber is less than or equal to DEFAULT_LINE_NUMBER
        defaultStackItemShouldBeFound("lineNumber.lessThanOrEqual=" + DEFAULT_LINE_NUMBER);

        // Get all the stackItemList where lineNumber is less than or equal to SMALLER_LINE_NUMBER
        defaultStackItemShouldNotBeFound("lineNumber.lessThanOrEqual=" + SMALLER_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStackItemsByLineNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where lineNumber is less than DEFAULT_LINE_NUMBER
        defaultStackItemShouldNotBeFound("lineNumber.lessThan=" + DEFAULT_LINE_NUMBER);

        // Get all the stackItemList where lineNumber is less than UPDATED_LINE_NUMBER
        defaultStackItemShouldBeFound("lineNumber.lessThan=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStackItemsByLineNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        // Get all the stackItemList where lineNumber is greater than DEFAULT_LINE_NUMBER
        defaultStackItemShouldNotBeFound("lineNumber.greaterThan=" + DEFAULT_LINE_NUMBER);

        // Get all the stackItemList where lineNumber is greater than SMALLER_LINE_NUMBER
        defaultStackItemShouldBeFound("lineNumber.greaterThan=" + SMALLER_LINE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllStackItemsByAlertEventIsEqualToSomething() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);
        AlertEvent alertEvent = AlertEventResourceIT.createEntity(em);
        em.persist(alertEvent);
        em.flush();
        stackItem.setAlertEvent(alertEvent);
        stackItemRepository.saveAndFlush(stackItem);
        Long alertEventId = alertEvent.getId();

        // Get all the stackItemList where alertEvent equals to alertEventId
        defaultStackItemShouldBeFound("alertEventId.equals=" + alertEventId);

        // Get all the stackItemList where alertEvent equals to alertEventId + 1
        defaultStackItemShouldNotBeFound("alertEventId.equals=" + (alertEventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStackItemShouldBeFound(String filter) throws Exception {
        restStackItemMockMvc.perform(get("/api/stack-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stackItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO.intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].className").value(hasItem(DEFAULT_CLASS_NAME)))
            .andExpect(jsonPath("$.[*].methodName").value(hasItem(DEFAULT_METHOD_NAME)))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER.intValue())));

        // Check, that the count call also returns 1
        restStackItemMockMvc.perform(get("/api/stack-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStackItemShouldNotBeFound(String filter) throws Exception {
        restStackItemMockMvc.perform(get("/api/stack-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStackItemMockMvc.perform(get("/api/stack-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStackItem() throws Exception {
        // Get the stackItem
        restStackItemMockMvc.perform(get("/api/stack-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStackItem() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        int databaseSizeBeforeUpdate = stackItemRepository.findAll().size();

        // Update the stackItem
        StackItem updatedStackItem = stackItemRepository.findById(stackItem.getId()).get();
        // Disconnect from session so that the updates on updatedStackItem are not directly saved in db
        em.detach(updatedStackItem);
        updatedStackItem
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .fileName(UPDATED_FILE_NAME)
            .className(UPDATED_CLASS_NAME)
            .methodName(UPDATED_METHOD_NAME)
            .lineNumber(UPDATED_LINE_NUMBER);
        StackItemDTO stackItemDTO = stackItemMapper.toDto(updatedStackItem);

        restStackItemMockMvc.perform(put("/api/stack-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stackItemDTO)))
            .andExpect(status().isOk());

        // Validate the StackItem in the database
        List<StackItem> stackItemList = stackItemRepository.findAll();
        assertThat(stackItemList).hasSize(databaseSizeBeforeUpdate);
        StackItem testStackItem = stackItemList.get(stackItemList.size() - 1);
        assertThat(testStackItem.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testStackItem.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testStackItem.getClassName()).isEqualTo(UPDATED_CLASS_NAME);
        assertThat(testStackItem.getMethodName()).isEqualTo(UPDATED_METHOD_NAME);
        assertThat(testStackItem.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingStackItem() throws Exception {
        int databaseSizeBeforeUpdate = stackItemRepository.findAll().size();

        // Create the StackItem
        StackItemDTO stackItemDTO = stackItemMapper.toDto(stackItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStackItemMockMvc.perform(put("/api/stack-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stackItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StackItem in the database
        List<StackItem> stackItemList = stackItemRepository.findAll();
        assertThat(stackItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStackItem() throws Exception {
        // Initialize the database
        stackItemRepository.saveAndFlush(stackItem);

        int databaseSizeBeforeDelete = stackItemRepository.findAll().size();

        // Delete the stackItem
        restStackItemMockMvc.perform(delete("/api/stack-items/{id}", stackItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StackItem> stackItemList = stackItemRepository.findAll();
        assertThat(stackItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
