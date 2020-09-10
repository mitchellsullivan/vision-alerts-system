import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStackItem, StackItem } from 'app/shared/model/stack-item.model';
import { StackItemService } from './stack-item.service';
import { IAlertEvent } from 'app/shared/model/alert-event.model';
import { AlertEventService } from 'app/entities/alert-event/alert-event.service';

@Component({
  selector: 'jhi-stack-item-update',
  templateUrl: './stack-item-update.component.html',
})
export class StackItemUpdateComponent implements OnInit {
  isSaving = false;
  alertevents: IAlertEvent[] = [];

  editForm = this.fb.group({
    id: [],
    sequenceNo: [],
    fileName: [null, [Validators.required]],
    className: [null, [Validators.required]],
    methodName: [null, [Validators.required]],
    lineNumber: [null, [Validators.required]],
    alertEventId: [],
  });

  constructor(
    protected stackItemService: StackItemService,
    protected alertEventService: AlertEventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stackItem }) => {
      this.updateForm(stackItem);

      this.alertEventService.query().subscribe((res: HttpResponse<IAlertEvent[]>) => (this.alertevents = res.body || []));
    });
  }

  updateForm(stackItem: IStackItem): void {
    this.editForm.patchValue({
      id: stackItem.id,
      sequenceNo: stackItem.sequenceNo,
      fileName: stackItem.fileName,
      className: stackItem.className,
      methodName: stackItem.methodName,
      lineNumber: stackItem.lineNumber,
      alertEventId: stackItem.alertEventId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stackItem = this.createFromForm();
    if (stackItem.id !== undefined) {
      this.subscribeToSaveResponse(this.stackItemService.update(stackItem));
    } else {
      this.subscribeToSaveResponse(this.stackItemService.create(stackItem));
    }
  }

  private createFromForm(): IStackItem {
    return {
      ...new StackItem(),
      id: this.editForm.get(['id'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      fileName: this.editForm.get(['fileName'])!.value,
      className: this.editForm.get(['className'])!.value,
      methodName: this.editForm.get(['methodName'])!.value,
      lineNumber: this.editForm.get(['lineNumber'])!.value,
      alertEventId: this.editForm.get(['alertEventId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStackItem>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IAlertEvent): any {
    return item.id;
  }
}
