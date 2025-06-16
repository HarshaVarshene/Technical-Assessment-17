export type DocumentState = 'DRAFT' | 'SUBMITTED' | 'APPROVED' | 'ARCHIVED';

export interface DocumentHistory {
  id: number;
  state: DocumentState;
  timestamp: string;
}

export interface Document {
  id: number;
  title: string;
  state: DocumentState;
  history: DocumentHistory[];
}
