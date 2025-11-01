package com.github.gabmldev.app.services.impl;

import org.springframework.stereotype.Service;

import com.github.gabmldev.app.repository.WorkflowRepository;
import com.github.gabmldev.app.services.WorkflowService;

@Service
public class WorkflowServiceImpl implements WorkflowService {
    private WorkflowRepository repository;
}
