package com.visiongraphics_inc.alert_system.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.visiongraphics_inc.alert_system.domain.enumeration.AlertPriority;

/**
 * A AlertEvent.
 */
@Entity
@Table(name = "alert_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlertEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "application_name", nullable = false)
    private String applicationName;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "action_name")
    private String actionName;

    @Enumerated(EnumType.STRING)
    @Column(name = "suggested_priority")
    private AlertPriority suggestedPriority;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @OneToMany(mappedBy = "alertEvent", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<StackItem> stackItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public AlertEvent applicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public AlertEvent moduleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getActionName() {
        return actionName;
    }

    public AlertEvent actionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public AlertPriority getSuggestedPriority() {
        return suggestedPriority;
    }

    public AlertEvent suggestedPriority(AlertPriority suggestedPriority) {
        this.suggestedPriority = suggestedPriority;
        return this;
    }

    public void setSuggestedPriority(AlertPriority suggestedPriority) {
        this.suggestedPriority = suggestedPriority;
    }

    public String getMessage() {
        return message;
    }

    public AlertEvent message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<StackItem> getStackItems() {
        return stackItems;
    }

    public AlertEvent stackItems(List<StackItem> stackItems) {
        this.stackItems.clear();
        for (StackItem stackItem: stackItems) {
            this.addStackItem(stackItem);
        }
        return this;
    }

    public AlertEvent addStackItem(StackItem stackItem) {
        this.stackItems.add(stackItem);
        stackItem.setAlertEvent(this);
        stackItem.setSequenceNo((long)this.stackItems.size());
        return this;
    }

    public AlertEvent removeStackItem(StackItem stackItem) {
        this.stackItems.remove(stackItem);
        stackItem.setAlertEvent(null);
        return this;
    }

    public void setStackItems(List<StackItem> stackItems) {
        this.stackItems.clear();
        for (StackItem stackItem: stackItems) {
            this.addStackItem(stackItem);
        }
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlertEvent)) {
            return false;
        }
        return id != null && id.equals(((AlertEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlertEvent{" +
            "id=" + getId() +
            ", applicationName='" + getApplicationName() + "'" +
            ", moduleName='" + getModuleName() + "'" +
            ", actionName='" + getActionName() + "'" +
            ", suggestedPriority='" + getSuggestedPriority() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
