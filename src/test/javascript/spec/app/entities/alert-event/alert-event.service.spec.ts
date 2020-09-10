import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AlertEventService } from 'app/entities/alert-event/alert-event.service';
import { IAlertEvent, AlertEvent } from 'app/shared/model/alert-event.model';
import { AlertPriority } from 'app/shared/model/enumerations/alert-priority.model';

describe('Service Tests', () => {
  describe('AlertEvent Service', () => {
    let injector: TestBed;
    let service: AlertEventService;
    let httpMock: HttpTestingController;
    let elemDefault: IAlertEvent;
    let expectedResult: IAlertEvent | IAlertEvent[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AlertEventService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AlertEvent(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', AlertPriority.TRACE, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AlertEvent', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AlertEvent()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AlertEvent', () => {
        const returnedFromService = Object.assign(
          {
            applicationName: 'BBBBBB',
            moduleName: 'BBBBBB',
            actionName: 'BBBBBB',
            suggestedPriority: 'BBBBBB',
            message: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AlertEvent', () => {
        const returnedFromService = Object.assign(
          {
            applicationName: 'BBBBBB',
            moduleName: 'BBBBBB',
            actionName: 'BBBBBB',
            suggestedPriority: 'BBBBBB',
            message: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AlertEvent', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
