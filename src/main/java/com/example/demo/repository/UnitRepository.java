package com.example.demo.repository;

import com.example.demo.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    List<Unit> findByGameIdAndColor(Long gameId, String color);
    Unit findByXAndY(int x, int y);

  //  List<Unit> findUnitAtPosition(int x, int y);
}