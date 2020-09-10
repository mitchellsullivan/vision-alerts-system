import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VisionAlertSystemTestModule } from '../../../test.module';
import { StackItemUpdateComponent } from 'app/entities/stack-item/stack-item-update.component';
import { StackItemService } from 'app/entities/stack-item/stack-item.service';
import { StackItem } from 'app/shared/model/stack-item.model';

describe('Component Tests', () => {
  describe('StackItem Management Update Component', () => {
    let comp: StackItemUpdateComponent;
    let fixture: ComponentFixture<StackItemUpdateComponent>;
    let service: StackItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VisionAlertSystemTestModule],
        declarations: [StackItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(StackItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StackItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StackItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StackItem(123);
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
        const entity = new StackItem();
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
