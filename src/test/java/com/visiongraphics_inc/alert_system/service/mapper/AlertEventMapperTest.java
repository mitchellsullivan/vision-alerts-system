package com.visiongraphics_inc.alert_system.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlertEventMapperTest {

    private AlertEventMapper alertEventMapper;

    @BeforeEach
    public void setUp() {
        alertEventMapper = new AlertEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(alertEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(alertEventMapper.fromId(null)).isNull();
    }
}
