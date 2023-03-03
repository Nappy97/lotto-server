package com.example.lottoserver.repository;

import com.example.lottoserver.model.Lotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LottoRepository extends JpaRepository<Lotto, Long> {
    Lotto findByTurn(int turn);
}

