package com.visiongraphics_inc.alert_system.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.visiongraphics_inc.alert_system.domain.enumeration.AlertPriority;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.visiongraphics_inc.alert_system.domain.AlertEvent} entity. This class is used
 * in {@link com.visiongraphics_inc.alert_system.web.rest.AlertEventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /alert-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AlertEventCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AlertPriority
     */
    public static class AlertPriorityFilter extends Filter<AlertPriority> {

        public AlertPriorityFilter() {
        }

        public AlertPriorityFilter(AlertPriorityFilter filter) {
            super(filter);
        }

        @Override
        public AlertPriorityFilter copy() {
            return new AlertPriorityFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter applicationName;

    private StringFilter moduleName;

    private StringFilter actionName;

    private AlertPriorityFilter suggestedPriority;

    private StringFilter message;

    private LongFilter stackItemId;

    public AlertEventCriteria() {
    }

    public AlertEventCriteria(AlertEventCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.applicationName = other.applicationName == null ? null : other.applicationName.copy();
        this.moduleName = other.moduleName == null ? null : other.moduleName.copy();
        this.actionName = other.actionName == null ? null : other.actionName.copy();
        this.suggestedPriority = other.suggestedPriority == null ? null : other.suggestedPriority.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.stackItemId = other.stackItemId == null ? null : other.stackItemId.copy();
    }

    @Override
    public AlertEventCriteria copy() {
        return new AlertEventCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(StringFilter applicationName) {
        this.applicationName = applicationName;
    }

    public StringFilter getModuleName() {
        return moduleName;
    }

    public void setModuleName(StringFilter moduleName) {
        this.moduleName = moduleName;
    }

    public StringFilter getActionName() {
        return actionName;
    }

    public void setActionName(StringFilter actionName) {
        this.actionName = actionName;
    }

    public AlertPriorityFilter getSuggestedPriority() {
        return suggestedPriority;
    }

    public void setSuggestedPriority(AlertPriorityFilter suggestedPriority) {
        this.suggestedPriority = suggestedPriority;
    }

    public StringFilter getMessage() {
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public LongFilter getStackItemId() {
        return stackItemId;
    }

    public void setStackItemId(LongFilter stackItemId) {
        this.stackItemId = stackItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AlertEventCriteria that = (AlertEventCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(applicationName, that.applicationName) &&
            Objects.equals(moduleName, that.moduleName) &&
            Objects.equals(actionName, that.actionName) &&
            Objects.equals(suggestedPriority, that.suggestedPriority) &&
            Objects.equals(message, that.message) &&
            Objects.equals(stackItemId, that.stackItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        applicationName,
        moduleName,
        actionName,
        suggestedPriority,
        message,
        stackItemId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlertEventCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (applicationName != null ? "applicationName=" + applicationName + ", " : "") +
                (moduleName != null ? "moduleName=" + moduleName + ", " : "") +
                (actionName != null ? "actionName=" + actionName + ", " : "") +
                (suggestedPriority != null ? "suggestedPriority=" + suggestedPriority + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (stackItemId != null ? "stackItemId=" + stackItemId + ", " : "") +
            "}";
    }

}
