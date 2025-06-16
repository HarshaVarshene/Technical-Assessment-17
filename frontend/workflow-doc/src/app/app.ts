import { Component } from '@angular/core';
import { Document} from './document/document';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [Document],
  template: `<app-document></app-document>`,
})
export class App {}
