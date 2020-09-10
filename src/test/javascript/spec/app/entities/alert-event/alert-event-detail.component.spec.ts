import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisionAlertSystemTestModule } from '../../../test.module';
import { AlertEventDetailComponent } from 'app/entities/alert-event/alert-event-detail.component';
import { AlertEvent } from 'app/shared/model/alert-event.model';

describe('Component Tests', () => {
  describe('AlertEvent Management Detail Component', () => {
    let comp: AlertEventDetailComponent;
    let fixture: ComponentFixture<AlertEventDetailComponent>;
    const route = ({ data: of({ alertEvent: new AlertEvent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VisionAlertSystemTestModule],
        declarations: [AlertEventDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AlertEventDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlertEventDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alertEvent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alertEvent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
