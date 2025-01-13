package com.dw.gameshop.repository;

import com.dw.gameshop.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
