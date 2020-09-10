package com.visiongraphics_inc.alert_system.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.visiongraphics_inc.alert_system.web.rest.TestUtil;

public class AlertEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertEvent.class);
        AlertEvent alertEvent1 = new AlertEvent();
        alertEvent1.setId(1L);
        AlertEvent alertEvent2 = new AlertEvent();
        alertEvent2.setId(alertEvent1.getId());
        assertThat(alertEvent1).isEqualTo(alertEvent2);
        alertEvent2.setId(2L);
        assertThat(alertEvent1).isNotEqualTo(alertEvent2);
        alertEvent1.setId(null);
        assertThat(alertEvent1).isNotEqualTo(alertEvent2);
    }
}
