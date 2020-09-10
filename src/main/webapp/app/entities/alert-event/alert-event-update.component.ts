import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAlertEvent, AlertEvent } from 'app/shared/model/alert-event.model';
import { AlertEventService } from './alert-event.service';

@Component({
  selector: 'jhi-alert-event-update',
  templateUrl: './alert-event-update.component.html',
})
export class AlertEventUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    applicationName: [null, [Validators.required]],
    moduleName: [],
    actionName: [],
    suggestedPriority: [],
    message: [null, [Validators.required]],
  });

  constructor(protected alertEventService: AlertEventService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alertEvent }) => {
      this.updateForm(alertEvent);
    });
  }

  updateForm(alertEvent: IAlertEvent): void {
    this.editForm.patchValue({
      id: alertEvent.id,
      applicationName: alertEvent.applicationName,
      moduleName: alertEvent.moduleName,
      actionName: alertEvent.actionName,
      suggestedPriority: alertEvent.suggestedPriority,
      message: alertEvent.message,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alertEvent = this.createFromForm();
    if (alertEvent.id !== undefined) {
      this.subscribeToSaveResponse(this.alertEventService.update(alertEvent));
    } else {
      this.subscribeToSaveResponse(this.alertEventService.create(alertEvent));
    }
  }

  private createFromForm(): IAlertEvent {
    return {
      ...new AlertEvent(),
      id: this.editForm.get(['id'])!.value,
      applicationName: this.editForm.get(['applicationName'])!.value,
      moduleName: this.editForm.get(['moduleName'])!.value,
      actionName: this.editForm.get(['actionName'])!.value,
      suggestedPriority: this.editForm.get(['suggestedPriority'])!.value,
      message: this.editForm.get(['message'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlertEvent>>): void {
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
}
