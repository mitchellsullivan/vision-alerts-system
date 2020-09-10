import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlertEvent } from 'app/shared/model/alert-event.model';
import { AlertEventService } from './alert-event.service';

@Component({
  templateUrl: './alert-event-delete-dialog.component.html',
})
export class AlertEventDeleteDialogComponent {
  alertEvent?: IAlertEvent;

  constructor(
    protected alertEventService: AlertEventService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alertEventService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alertEventListModification');
      this.activeModal.close();
    });
  }
}
