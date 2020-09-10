import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisionAlertSystemTestModule } from '../../../test.module';
import { StackItemDetailComponent } from 'app/entities/stack-item/stack-item-detail.component';
import { StackItem } from 'app/shared/model/stack-item.model';

describe('Component Tests', () => {
  describe('StackItem Management Detail Component', () => {
    let comp: StackItemDetailComponent;
    let fixture: ComponentFixture<StackItemDetailComponent>;
    const route = ({ data: of({ stackItem: new StackItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VisionAlertSystemTestModule],
        declarations: [StackItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(StackItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StackItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load stackItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stackItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
