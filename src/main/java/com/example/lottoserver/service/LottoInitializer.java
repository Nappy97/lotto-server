package com.example.lottoserver.service;

import com.example.lottoserver.model.Lotto;
import com.example.lottoserver.repository.LottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
class LottoInitializer {

    private final LottoRepository repository;

    @Autowired
    public LottoInitializer(LottoRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initialize() {
        List<Lotto> lottos = readCsvFile();
        repository.saveAll(lottos);
    }

    private List<Lotto> readCsvFile() {
        InputStream inputStream = getClass().getResourceAsStream("/lotto.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<Lotto> lottos = reader.lines().skip(1).map(line -> {
            String[] fields = line.split(",");
            return Lotto.builder()
                    .turn(Integer.parseInt(fields[0]))
                    .num1(Integer.parseInt(fields[1]))
                    .num2(Integer.parseInt(fields[2]))
                    .num3(Integer.parseInt(fields[3]))
                    .num4(Integer.parseInt(fields[4]))
                    .num5(Integer.parseInt(fields[5]))
                    .num6(Integer.parseInt(fields[6]))
                    .bonusNum(Integer.parseInt(fields[7]))
                    .build();
        }).collect(Collectors.toList());
        return lottos;
    }

}
