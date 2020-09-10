package com.visiongraphics_inc.alert_system.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StackItemMapperTest {

    private StackItemMapper stackItemMapper;

    @BeforeEach
    public void setUp() {
        stackItemMapper = new StackItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(stackItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stackItemMapper.fromId(null)).isNull();
    }
}
