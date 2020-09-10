import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlertEvent } from 'app/shared/model/alert-event.model';

type EntityResponseType = HttpResponse<IAlertEvent>;
type EntityArrayResponseType = HttpResponse<IAlertEvent[]>;

@Injectable({ providedIn: 'root' })
export class AlertEventService {
  public resourceUrl = SERVER_API_URL + 'api/alert-events';

  constructor(protected http: HttpClient) {}

  create(alertEvent: IAlertEvent): Observable<EntityResponseType> {
    return this.http.post<IAlertEvent>(this.resourceUrl, alertEvent, { observe: 'response' });
  }

  update(alertEvent: IAlertEvent): Observable<EntityResponseType> {
    return this.http.put<IAlertEvent>(this.resourceUrl, alertEvent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlertEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlertEvent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
