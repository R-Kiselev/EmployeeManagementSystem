package com.example.employeemanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/logs")
@Tag(name = "Log Controller", description = "API для работы с лог-файлами")
public class LogsController {
    private static final String LOG_FILE_PATH = "logs/employee-management.log";
    private static final String ARCHIVE_LOG_FILE_PATTERN = "logs/employee-management-%s.log";
    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("/download")
    @Operation(summary = "Скачать лог-файл", description = "Скачивает логи за указанную дату.")
    @ApiResponse(responseCode = "200", description = "Логи успешно загружены")
    @ApiResponse(responseCode = "404", description = "Логи не найдены")
    @ApiResponse(responseCode = "400", description = "Неверный формат даты")
    public ResponseEntity<Resource> downloadLogFile(
        @Parameter(description = "Дата в формате yyyy-MM-dd", required = true,
            example = "2025-04-01")
        @RequestParam(name = "date") String dateStr) throws IOException {
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
        Path logPath = getLogFilePath(date);
        if (!Files.exists(logPath)) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new UrlResource(logPath.toUri());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + logPath.getFileName())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource);
    }

    @GetMapping("/view")
    @Operation(summary = "Просмотреть логи",
        description = "Возвращает логи за указанную дату в виде текста.")
    @ApiResponse(responseCode = "200", description = "Логи успешно получены")
    @ApiResponse(responseCode = "404", description = "Логи не найдены")
    @ApiResponse(responseCode = "400", description = "Неверный формат даты")
    public ResponseEntity<String> viewLogFile(
        @Parameter(description = "Дата в формате yyyy-MM-dd",
            required = true, example = "2025-04-01")
        @RequestParam(name = "date") String dateStr) throws IOException {
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
        Path logPath = getLogFilePath(date);
        if (!Files.exists(logPath)) {
            return ResponseEntity.notFound().build();
        }
        String logContent = Files.readString(logPath);

        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .body(logContent);
    }

    private Path getLogFilePath(LocalDate date) {
        String fileName = String.format(ARCHIVE_LOG_FILE_PATTERN, date.format(DATE_FORMATTER));
        return Paths.get(fileName);
    }
}