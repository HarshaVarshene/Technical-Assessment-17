package com.Statemachine.Workflow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Statemachine.Workflow.model.Document;
import com.Statemachine.Workflow.model.DocumentState;
import com.Statemachine.Workflow.service.DocumentService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document request) {
        Document created = documentService.createDocument(request.getTitle());
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getDocument(id));
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<Document> submit(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.submit(id));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Document> approve(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.approve(id));
    }

    @PostMapping("/{id}/archive")
    public ResponseEntity<Document> archive(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.archive(id));
    }

    @PostMapping("/{id}/reset")
    public ResponseEntity<?> reset(@PathVariable Long id, 
                                   @RequestParam DocumentState state,
                                   @RequestHeader(value = "X-ROLE", required = false) String role) {
        if (role == null || !role.equalsIgnoreCase("admin")) {
            return ResponseEntity.status(403).body("Forbidden: Only admin can reset the document state.");
        }

        return ResponseEntity.ok(documentService.resetState(id, state));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

}
