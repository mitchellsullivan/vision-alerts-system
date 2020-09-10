package com.visiongraphics_inc.alert_system.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.visiongraphics_inc.alert_system.web.rest.TestUtil;

public class AlertEventDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertEventDTO.class);
        AlertEventDTO alertEventDTO1 = new AlertEventDTO();
        alertEventDTO1.setId(1L);
        AlertEventDTO alertEventDTO2 = new AlertEventDTO();
        assertThat(alertEventDTO1).isNotEqualTo(alertEventDTO2);
        alertEventDTO2.setId(alertEventDTO1.getId());
        assertThat(alertEventDTO1).isEqualTo(alertEventDTO2);
        alertEventDTO2.setId(2L);
        assertThat(alertEventDTO1).isNotEqualTo(alertEventDTO2);
        alertEventDTO1.setId(null);
        assertThat(alertEventDTO1).isNotEqualTo(alertEventDTO2);
    }
}
