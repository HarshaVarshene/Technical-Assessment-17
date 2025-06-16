import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Document } from './document';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

describe('DocumentComponent', () => {
  let component: Document;
  let fixture: ComponentFixture<Document>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        Document, // since it's standalone
        HttpClientTestingModule, // for mocking HTTP requests
        FormsModule,
        CommonModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Document);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
