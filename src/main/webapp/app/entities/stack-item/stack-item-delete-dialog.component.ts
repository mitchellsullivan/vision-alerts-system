import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStackItem } from 'app/shared/model/stack-item.model';
import { StackItemService } from './stack-item.service';

@Component({
  templateUrl: './stack-item-delete-dialog.component.html',
})
export class StackItemDeleteDialogComponent {
  stackItem?: IStackItem;

  constructor(protected stackItemService: StackItemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stackItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('stackItemListModification');
      this.activeModal.close();
    });
  }
}
