package com.visiongraphics_inc.alert_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A StackItem.
 */
@Entity
@Table(name = "stack_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StackItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sequence_no")
    private Long sequenceNo;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NotNull
    @Column(name = "class_name", nullable = false)
    private String className;

    @NotNull
    @Column(name = "method_name", nullable = false)
    private String methodName;

    @NotNull
    @Column(name = "line_number", nullable = false)
    private Long lineNumber;

    @ManyToOne()
    @JsonIgnoreProperties(value = "stackItems", allowSetters = true)
    private AlertEvent alertEvent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSequenceNo() {
        return sequenceNo;
    }

    public StackItem sequenceNo(Long sequenceNo) {
        this.sequenceNo = sequenceNo;
        return this;
    }

    public void setSequenceNo(Long sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getFileName() {
        return fileName;
    }

    public StackItem fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getClassName() {
        return className;
    }

    public StackItem className(String className) {
        this.className = className;
        return this;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public StackItem methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getLineNumber() {
        return lineNumber;
    }

    public StackItem lineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public void setLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public AlertEvent getAlertEvent() {
        return alertEvent;
    }

    public StackItem alertEvent(AlertEvent alertEvent) {
        this.alertEvent = alertEvent;
        return this;
    }

    public void setAlertEvent(AlertEvent alertEvent) {
        this.alertEvent = alertEvent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StackItem)) {
            return false;
        }
        return id != null && id.equals(((StackItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StackItem{" +
            "id=" + getId() +
            ", sequenceNo=" + getSequenceNo() +
            ", fileName='" + getFileName() + "'" +
            ", className='" + getClassName() + "'" +
            ", methodName='" + getMethodName() + "'" +
            ", lineNumber=" + getLineNumber() +
            "}";
    }
}
