package com.visiongraphics_inc.alert_system.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.visiongraphics_inc.alert_system.web.rest.TestUtil;

public class StackItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StackItem.class);
        StackItem stackItem1 = new StackItem();
        stackItem1.setId(1L);
        StackItem stackItem2 = new StackItem();
        stackItem2.setId(stackItem1.getId());
        assertThat(stackItem1).isEqualTo(stackItem2);
        stackItem2.setId(2L);
        assertThat(stackItem1).isNotEqualTo(stackItem2);
        stackItem1.setId(null);
        assertThat(stackItem1).isNotEqualTo(stackItem2);
    }
}
