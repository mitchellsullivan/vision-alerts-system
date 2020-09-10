import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'alert-event',
        loadChildren: () => import('./alert-event/alert-event.module').then(m => m.VisionAlertSystemAlertEventModule),
      },
      {
        path: 'stack-item',
        loadChildren: () => import('./stack-item/stack-item.module').then(m => m.VisionAlertSystemStackItemModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class VisionAlertSystemEntityModule {}
