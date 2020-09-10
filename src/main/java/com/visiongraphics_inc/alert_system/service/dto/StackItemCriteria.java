package com.visiongraphics_inc.alert_system.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.visiongraphics_inc.alert_system.domain.StackItem} entity. This class is used
 * in {@link com.visiongraphics_inc.alert_system.web.rest.StackItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stack-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StackItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter sequenceNo;

    private StringFilter fileName;

    private StringFilter className;

    private StringFilter methodName;

    private LongFilter lineNumber;

    private LongFilter alertEventId;

    public StackItemCriteria() {
    }

    public StackItemCriteria(StackItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sequenceNo = other.sequenceNo == null ? null : other.sequenceNo.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.className = other.className == null ? null : other.className.copy();
        this.methodName = other.methodName == null ? null : other.methodName.copy();
        this.lineNumber = other.lineNumber == null ? null : other.lineNumber.copy();
        this.alertEventId = other.alertEventId == null ? null : other.alertEventId.copy();
    }

    @Override
    public StackItemCriteria copy() {
        return new StackItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(LongFilter sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getClassName() {
        return className;
    }

    public void setClassName(StringFilter className) {
        this.className = className;
    }

    public StringFilter getMethodName() {
        return methodName;
    }

    public void setMethodName(StringFilter methodName) {
        this.methodName = methodName;
    }

    public LongFilter getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(LongFilter lineNumber) {
        this.lineNumber = lineNumber;
    }

    public LongFilter getAlertEventId() {
        return alertEventId;
    }

    public void setAlertEventId(LongFilter alertEventId) {
        this.alertEventId = alertEventId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StackItemCriteria that = (StackItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sequenceNo, that.sequenceNo) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(className, that.className) &&
            Objects.equals(methodName, that.methodName) &&
            Objects.equals(lineNumber, that.lineNumber) &&
            Objects.equals(alertEventId, that.alertEventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sequenceNo,
        fileName,
        className,
        methodName,
        lineNumber,
        alertEventId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StackItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sequenceNo != null ? "sequenceNo=" + sequenceNo + ", " : "") +
                (fileName != null ? "fileName=" + fileName + ", " : "") +
                (className != null ? "className=" + className + ", " : "") +
                (methodName != null ? "methodName=" + methodName + ", " : "") +
                (lineNumber != null ? "lineNumber=" + lineNumber + ", " : "") +
                (alertEventId != null ? "alertEventId=" + alertEventId + ", " : "") +
            "}";
    }

}
