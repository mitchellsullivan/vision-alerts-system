package com.visiongraphics_inc.alert_system.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.visiongraphics_inc.alert_system.web.rest.TestUtil;

public class StackItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StackItemDTO.class);
        StackItemDTO stackItemDTO1 = new StackItemDTO();
        stackItemDTO1.setId(1L);
        StackItemDTO stackItemDTO2 = new StackItemDTO();
        assertThat(stackItemDTO1).isNotEqualTo(stackItemDTO2);
        stackItemDTO2.setId(stackItemDTO1.getId());
        assertThat(stackItemDTO1).isEqualTo(stackItemDTO2);
        stackItemDTO2.setId(2L);
        assertThat(stackItemDTO1).isNotEqualTo(stackItemDTO2);
        stackItemDTO1.setId(null);
        assertThat(stackItemDTO1).isNotEqualTo(stackItemDTO2);
    }
}
