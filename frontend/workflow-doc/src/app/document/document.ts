import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-document',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './document.html'
})

export class Document {
  documents: any[] = [];
  title = '';
  apiUrl = 'http://localhost:8080/documents';
  isAdmin = true;
  selectedDocument: any = null;

  constructor(private http: HttpClient) {
    this.loadDocuments();
  }
  

  loadDocuments() {
    this.http.get<any[]>(this.apiUrl).subscribe(data => {
      this.documents = data;
    });
  }

  createDocument() {
    if (!this.title.trim()) return;
    this.http.post(this.apiUrl, { title: this.title }).subscribe(() => {
      this.title = '';
      this.loadDocuments();
    });
  }

  submit(id: number) {
    this.http.post(`${this.apiUrl}/${id}/submit`, {}).subscribe(() => this.loadDocuments());
  }

  approve(id: number) {
    this.http.post(`${this.apiUrl}/${id}/approve`, {}).subscribe(() => this.loadDocuments());
  }

  archive(id: number) {
    this.http.post(`${this.apiUrl}/${id}/archive`, {}).subscribe(() => this.loadDocuments());
  }
 reset(id: number, state: string) {
  const headers = { 'X-ROLE': 'admin' }; // Simulating local admin role
  this.http.post(`${this.apiUrl}/${id}/reset?state=${state}`, {}, { headers })
    .subscribe({
      next: () => this.loadDocuments(),
      error: err => {
        alert(err.error || 'You are not authorized to reset this document.');
      }
    });
}


  confirmDelete(id: number) {
    if (confirm('Are you sure you want to delete this document?')) {
      this.http.delete(`${this.apiUrl}/${id}`).subscribe(() => this.loadDocuments());
    }
  }

  viewDocument(id: number) {
  this.http.get(`${this.apiUrl}/${id}`).subscribe((data: any) => {
    this.selectedDocument = data;
  });
}
}
