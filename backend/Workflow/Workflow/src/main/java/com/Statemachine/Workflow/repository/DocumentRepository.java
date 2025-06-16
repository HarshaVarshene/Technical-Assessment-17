package com.Statemachine.Workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Statemachine.Workflow.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long>{

}
