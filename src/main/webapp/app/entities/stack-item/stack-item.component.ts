import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStackItem } from 'app/shared/model/stack-item.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { StackItemService } from './stack-item.service';
import { StackItemDeleteDialogComponent } from './stack-item-delete-dialog.component';

@Component({
  selector: 'jhi-stack-item',
  templateUrl: './stack-item.component.html',
})
export class StackItemComponent implements OnInit, OnDestroy {
  stackItems: IStackItem[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected stackItemService: StackItemService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.stackItems = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.stackItemService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IStackItem[]>) => this.paginateStackItems(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.stackItems = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStackItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStackItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStackItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('stackItemListModification', () => this.reset());
  }

  delete(stackItem: IStackItem): void {
    const modalRef = this.modalService.open(StackItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.stackItem = stackItem;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateStackItems(data: IStackItem[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.stackItems.push(data[i]);
      }
    }
  }
}
