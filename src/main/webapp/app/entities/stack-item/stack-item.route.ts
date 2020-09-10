import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStackItem, StackItem } from 'app/shared/model/stack-item.model';
import { StackItemService } from './stack-item.service';
import { StackItemComponent } from './stack-item.component';
import { StackItemDetailComponent } from './stack-item-detail.component';
import { StackItemUpdateComponent } from './stack-item-update.component';

@Injectable({ providedIn: 'root' })
export class StackItemResolve implements Resolve<IStackItem> {
  constructor(private service: StackItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStackItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((stackItem: HttpResponse<StackItem>) => {
          if (stackItem.body) {
            return of(stackItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StackItem());
  }
}

export const stackItemRoute: Routes = [
  {
    path: '',
    component: StackItemComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'visionAlertSystemApp.stackItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StackItemDetailComponent,
    resolve: {
      stackItem: StackItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'visionAlertSystemApp.stackItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StackItemUpdateComponent,
    resolve: {
      stackItem: StackItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'visionAlertSystemApp.stackItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StackItemUpdateComponent,
    resolve: {
      stackItem: StackItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'visionAlertSystemApp.stackItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
