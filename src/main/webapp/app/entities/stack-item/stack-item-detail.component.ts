import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStackItem } from 'app/shared/model/stack-item.model';

@Component({
  selector: 'jhi-stack-item-detail',
  templateUrl: './stack-item-detail.component.html',
})
export class StackItemDetailComponent implements OnInit {
  stackItem: IStackItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stackItem }) => (this.stackItem = stackItem));
  }

  previousState(): void {
    window.history.back();
  }
}
