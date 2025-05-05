package com.example.employeemanagementsystem.model;

import java.nio.file.Path;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class LogFileTask {
    private final String taskId;
    private String status = "PENDING";
    private Path filePath;
    private String errorMessage;
}