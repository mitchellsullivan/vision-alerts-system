import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStackItem } from 'app/shared/model/stack-item.model';

type EntityResponseType = HttpResponse<IStackItem>;
type EntityArrayResponseType = HttpResponse<IStackItem[]>;

@Injectable({ providedIn: 'root' })
export class StackItemService {
  public resourceUrl = SERVER_API_URL + 'api/stack-items';

  constructor(protected http: HttpClient) {}

  create(stackItem: IStackItem): Observable<EntityResponseType> {
    return this.http.post<IStackItem>(this.resourceUrl, stackItem, { observe: 'response' });
  }

  update(stackItem: IStackItem): Observable<EntityResponseType> {
    return this.http.put<IStackItem>(this.resourceUrl, stackItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStackItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStackItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
