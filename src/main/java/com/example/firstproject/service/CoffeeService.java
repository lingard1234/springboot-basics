package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    public Coffee show(Long id){
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee create(CoffeeDto dto){
        Coffee coffee = dto.toEntity();
        return coffeeRepository.save(coffee);
    }

    public Coffee update(Long id, CoffeeDto dto){
        // 1. dto -> 엔티티 변환하기
        Coffee coffee = dto.toEntity();
        // 2. 타깃 조회하기
        Coffee target = coffeeRepository.findById(id).orElse(null);
        // 3. 잘못된 요청 처리하기
        if (target == null || id != target.getId()) {
            log.info("잘못된 요청 ! id: {}, article: {}", id, coffee);
            return null;
        }
        // 4. 업데이트 하기
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return coffeeRepository.save(updated);
    }

    public Coffee delete(Long id){
        Coffee target =  coffeeRepository.findById(id).orElse(null);
        if (target == null){
            return null;
        }
        coffeeRepository.delete(target);
        return target;
    }

}
