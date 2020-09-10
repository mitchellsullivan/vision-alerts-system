package com.visiongraphics_inc.alert_system.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.visiongraphics_inc.alert_system.domain.StackItem} entity.
 */
public class StackItemDTO implements Serializable {
    
    private Long id;

    private Long sequenceNo;

    @NotNull
    private String fileName;

    @NotNull
    private String className;

    @NotNull
    private String methodName;

    @NotNull
    private Long lineNumber;


    private Long alertEventId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Long sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Long getAlertEventId() {
        return alertEventId;
    }

    public void setAlertEventId(Long alertEventId) {
        this.alertEventId = alertEventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StackItemDTO)) {
            return false;
        }

        return id != null && id.equals(((StackItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StackItemDTO{" +
            "id=" + getId() +
            ", sequenceNo=" + getSequenceNo() +
            ", fileName='" + getFileName() + "'" +
            ", className='" + getClassName() + "'" +
            ", methodName='" + getMethodName() + "'" +
            ", lineNumber=" + getLineNumber() +
            ", alertEventId=" + getAlertEventId() +
            "}";
    }
}
