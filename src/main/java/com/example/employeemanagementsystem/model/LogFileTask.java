package com.example.employeemanagementsystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@Getter
@Setter
@RequiredArgsConstructor
public class LogFileTask {
    private final String taskId;
    private String status = "PENDING";
    private Path filePath;
    private String errorMessage;
}