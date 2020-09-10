import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VisionAlertSystemTestModule } from '../../../test.module';
import { AlertEventUpdateComponent } from 'app/entities/alert-event/alert-event-update.component';
import { AlertEventService } from 'app/entities/alert-event/alert-event.service';
import { AlertEvent } from 'app/shared/model/alert-event.model';

describe('Component Tests', () => {
  describe('AlertEvent Management Update Component', () => {
    let comp: AlertEventUpdateComponent;
    let fixture: ComponentFixture<AlertEventUpdateComponent>;
    let service: AlertEventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VisionAlertSystemTestModule],
        declarations: [AlertEventUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AlertEventUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlertEventUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlertEventService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AlertEvent(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AlertEvent();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
