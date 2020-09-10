import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisionAlertSystemSharedModule } from 'app/shared/shared.module';
import { AlertEventComponent } from './alert-event.component';
import { AlertEventDetailComponent } from './alert-event-detail.component';
import { AlertEventUpdateComponent } from './alert-event-update.component';
import { AlertEventDeleteDialogComponent } from './alert-event-delete-dialog.component';
import { alertEventRoute } from './alert-event.route';

@NgModule({
  imports: [VisionAlertSystemSharedModule, RouterModule.forChild(alertEventRoute)],
  declarations: [AlertEventComponent, AlertEventDetailComponent, AlertEventUpdateComponent, AlertEventDeleteDialogComponent],
  entryComponents: [AlertEventDeleteDialogComponent],
})
export class VisionAlertSystemAlertEventModule {}
