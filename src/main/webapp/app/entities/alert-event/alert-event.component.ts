import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlertEvent } from 'app/shared/model/alert-event.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AlertEventService } from './alert-event.service';
import { AlertEventDeleteDialogComponent } from './alert-event-delete-dialog.component';

@Component({
  selector: 'jhi-alert-event',
  templateUrl: './alert-event.component.html',
})
export class AlertEventComponent implements OnInit, OnDestroy {
  alertEvents: IAlertEvent[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected alertEventService: AlertEventService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.alertEvents = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.alertEventService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IAlertEvent[]>) => this.paginateAlertEvents(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.alertEvents = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAlertEvents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAlertEvent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAlertEvents(): void {
    this.eventSubscriber = this.eventManager.subscribe('alertEventListModification', () => this.reset());
  }

  delete(alertEvent: IAlertEvent): void {
    const modalRef = this.modalService.open(AlertEventDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.alertEvent = alertEvent;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAlertEvents(data: IAlertEvent[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.alertEvents.push(data[i]);
      }
    }
  }
}
