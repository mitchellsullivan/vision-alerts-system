import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlertEvent, AlertEvent } from 'app/shared/model/alert-event.model';
import { AlertEventService } from './alert-event.service';
import { AlertEventComponent } from './alert-event.component';
import { AlertEventDetailComponent } from './alert-event-detail.component';
import { AlertEventUpdateComponent } from './alert-event-update.component';

@Injectable({ providedIn: 'root' })
export class AlertEventResolve implements Resolve<IAlertEvent> {
  constructor(private service: AlertEventService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlertEvent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alertEvent: HttpResponse<AlertEvent>) => {
          if (alertEvent.body) {
            return of(alertEvent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AlertEvent());
  }
}

export const alertEventRoute: Routes = [
  {
    path: '',
    component: AlertEventComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'visionAlertSystemApp.alertEvent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlertEventDetailComponent,
    resolve: {
      alertEvent: AlertEventResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'visionAlertSystemApp.alertEvent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlertEventUpdateComponent,
    resolve: {
      alertEvent: AlertEventResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'visionAlertSystemApp.alertEvent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlertEventUpdateComponent,
    resolve: {
      alertEvent: AlertEventResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'visionAlertSystemApp.alertEvent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
