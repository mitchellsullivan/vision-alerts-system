import { IStackItem } from 'app/shared/model/stack-item.model';
import { AlertPriority } from 'app/shared/model/enumerations/alert-priority.model';

export interface IAlertEvent {
  id?: number;
  applicationName?: string;
  moduleName?: string;
  actionName?: string;
  suggestedPriority?: AlertPriority;
  message?: string;
  stackItems?: IStackItem[];
}

export class AlertEvent implements IAlertEvent {
  constructor(
    public id?: number,
    public applicationName?: string,
    public moduleName?: string,
    public actionName?: string,
    public suggestedPriority?: AlertPriority,
    public message?: string,
    public stackItems?: IStackItem[]
  ) {}
}
