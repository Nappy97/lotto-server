package com.example.lottoserver.controller;

import com.example.lottoserver.dto.LottoResultDTO;
import com.example.lottoserver.service.LottoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
class LottoController {
    private final LottoService service;

    @Autowired
    public LottoController(LottoService service) {
        this.service = service;
    }

    @PostMapping("/result")
    public LottoResultDTO getLottoResult(@RequestBody List<Integer> numbers) {
        log.info("numbers.size() = {}", numbers.size());
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("Invalid number of numbers");
        }
        return service.getLottoResult(numbers);
    }
}