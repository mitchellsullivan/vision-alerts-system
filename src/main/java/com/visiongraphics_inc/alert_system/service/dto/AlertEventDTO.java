package com.visiongraphics_inc.alert_system.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.visiongraphics_inc.alert_system.domain.AlertEvent;
import com.visiongraphics_inc.alert_system.domain.enumeration.AlertPriority;

/**
 * A DTO for the {@link com.visiongraphics_inc.alert_system.domain.AlertEvent} entity.
 */
public class AlertEventDTO implements Serializable {

    private Long id;

    @NotNull
    private String applicationName;

    private String moduleName;

    private String actionName;

    private AlertPriority suggestedPriority;

    @NotNull
    private String message;

    private List<StackItemDTO> stackItems;

    public AlertEventDTO() {
        this.stackItems = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public AlertPriority getSuggestedPriority() {
        return suggestedPriority;
    }

    public void setSuggestedPriority(AlertPriority suggestedPriority) {
        this.suggestedPriority = suggestedPriority;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StackItemDTO> getStackItems() {
        return stackItems;
    }

    public void setStackItems(List<StackItemDTO> stackItems) {
        this.stackItems = stackItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlertEventDTO)) {
            return false;
        }

        return id != null && id.equals(((AlertEventDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlertEventDTO{" +
            "id=" + getId() +
            ", applicationName='" + getApplicationName() + "'" +
            ", moduleName='" + getModuleName() + "'" +
            ", actionName='" + getActionName() + "'" +
            ", suggestedPriority='" + getSuggestedPriority() + "'" +
            ", message='" + getMessage() + "'" +
            ", stackItems='" + getStackItems() + "'" +
            "}";
    }
}
