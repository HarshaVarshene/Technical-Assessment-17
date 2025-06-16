package com.Statemachine.Workflow.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Statemachine.Workflow.model.Document;
import com.Statemachine.Workflow.model.DocumentState;
import com.Statemachine.Workflow.repository.DocumentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DocumentService {

	private final DocumentRepository documentRepository;
	
	DocumentService(DocumentRepository documentRepository){
		this.documentRepository=documentRepository;
	}
	
	public Document createDocument(String title) {
		
		Document doc= new Document();
		doc.setTitle(title);
		doc.setState(DocumentState.DRAFT);
		doc.setCreatedAt(LocalDateTime.now());
		doc.setUpdatedAt(LocalDateTime.now());		
		return documentRepository.save(doc);
	}
	
	public Document getDocument(Long id) {
		return documentRepository.findById(id)
			.orElseThrow(()-> new EntityNotFoundException("Document Not Found"));
	}
	
	public List<Document> getAllDocuments(){
		return documentRepository.findAll();
	}
	
	public Document submit(Long id) {
		Document doc=getDocument(id);
		if(doc.getState()!=DocumentState.DRAFT) {
			throw new IllegalStateException("Only Draft documents can be submitted");
		}
		doc.setState(DocumentState.SUBMITTED);
		doc.setCreatedAt(LocalDateTime.now());
		doc.addHistory(DocumentState.SUBMITTED);
		return documentRepository.save(doc);
	}
	
	public Document approve(Long id) {
		Document doc=getDocument(id);
		if(doc.getState()!=DocumentState.SUBMITTED) {
			throw new IllegalStateException("Only submitted documents can be approved");
		}
		doc.setState(DocumentState.APPROVED);
		doc.setCreatedAt(LocalDateTime.now());
		doc.addHistory(DocumentState.APPROVED);
		return documentRepository.save(doc);
	}
	public Document archive(Long id) {
		Document doc=getDocument(id);
		if(doc.getState()!=DocumentState.APPROVED) {
			throw new IllegalStateException("Only Approved documents can be archived");
		}
		doc.setState(DocumentState.ARCHIVED);
		doc.setCreatedAt(LocalDateTime.now());
		doc.addHistory(DocumentState.ARCHIVED);
		return documentRepository.save(doc);
	}
	
	public Document resetState(Long id,DocumentState newState) {
		Document doc = getDocument(id);
        doc.setState(newState);
        doc.setUpdatedAt(LocalDateTime.now());
        return documentRepository.save(doc);
		
	}
	public void deleteDocument(Long id) {
	    documentRepository.deleteById(id);
	}

}
