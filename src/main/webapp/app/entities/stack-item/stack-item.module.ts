import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisionAlertSystemSharedModule } from 'app/shared/shared.module';
import { StackItemComponent } from './stack-item.component';
import { StackItemDetailComponent } from './stack-item-detail.component';
import { StackItemUpdateComponent } from './stack-item-update.component';
import { StackItemDeleteDialogComponent } from './stack-item-delete-dialog.component';
import { stackItemRoute } from './stack-item.route';

@NgModule({
  imports: [VisionAlertSystemSharedModule, RouterModule.forChild(stackItemRoute)],
  declarations: [StackItemComponent, StackItemDetailComponent, StackItemUpdateComponent, StackItemDeleteDialogComponent],
  entryComponents: [StackItemDeleteDialogComponent],
})
export class VisionAlertSystemStackItemModule {}
