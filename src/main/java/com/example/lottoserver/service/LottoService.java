package com.example.lottoserver.service;


import com.example.lottoserver.dto.LottoResultDTO;
import com.example.lottoserver.model.Lotto;
import com.example.lottoserver.repository.LottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LottoService {

    private final LottoRepository repository;

    @Autowired
    public LottoService(LottoRepository repository) {
        this.repository = repository;
    }

    public LottoResultDTO getLottoResult(List<Integer> numbers) {
        List<Lotto> lottos = repository.findAll();
        int maxMatchingCount = 0;
        int maxMatchingTurn = 0;
        int bonusNum = 0;
        for (Lotto lotto : lottos) {
            int matchingCount = 0;
            if (numbers.contains(lotto.getNum1())) matchingCount++;
            if (numbers.contains(lotto.getNum2())) matchingCount++;
            if (numbers.contains(lotto.getNum3())) matchingCount++;
            if (numbers.contains(lotto.getNum4())) matchingCount++;
            if (numbers.contains(lotto.getNum5())) matchingCount++;
            if (numbers.contains(lotto.getNum6())) matchingCount++;
            if (matchingCount > maxMatchingCount) {
                maxMatchingCount = matchingCount;
                maxMatchingTurn = lotto.getTurn();
                bonusNum = lotto.getBonusNum();
            } else if (matchingCount == maxMatchingCount && lotto.getTurn() > maxMatchingTurn) {
                maxMatchingTurn = lotto.getTurn();
                bonusNum = lotto.getBonusNum();
            }
        }
        int ranking;
        if (maxMatchingCount == 6)
            ranking = 1;
        else if (maxMatchingCount == 5 && numbers.contains(bonusNum))
            ranking = 2;
        else if (maxMatchingCount == 5) ranking = 2;
        else if (maxMatchingCount == 4) ranking = 3;
        else if (maxMatchingCount == 3) ranking = 4;
        else ranking = 5;
        List<Integer> matchedNumbers = new ArrayList<>(numbers);
        matchedNumbers.retainAll(getWinningNumbers(maxMatchingTurn));
        List<Integer> unmatchedNumbers = new ArrayList<>(numbers);
        unmatchedNumbers.removeAll(getWinningNumbers(maxMatchingTurn));
        return LottoResultDTO.builder()
                .turn(maxMatchingTurn)
                .ranking(ranking)
                .matchedNumbers(matchedNumbers)
                .unmatchedNumbers(unmatchedNumbers)
                .build();
    }

    private List<Integer> getWinningNumbers(int turn) {
        Lotto lotto = repository.findByTurn(turn);
        return List.of(lotto.getNum1(), lotto.getNum2(), lotto.getNum3(), lotto.getNum4(), lotto.getNum5(), lotto.getNum6());
    }
}