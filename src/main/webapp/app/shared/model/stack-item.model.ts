export interface IStackItem {
  id?: number;
  sequenceNo?: number;
  fileName?: string;
  className?: string;
  methodName?: string;
  lineNumber?: number;
  alertEventId?: number;
}

export class StackItem implements IStackItem {
  constructor(
    public id?: number,
    public sequenceNo?: number,
    public fileName?: string,
    public className?: string,
    public methodName?: string,
    public lineNumber?: number,
    public alertEventId?: number
  ) {}
}
