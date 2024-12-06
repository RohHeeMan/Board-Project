package com.codingrecipe.board.repository;

import com.codingrecipe.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

// 레파지토리에서는 매퍼를 호출해서 최종 저장함.
@Repository
@RequiredArgsConstructor
public class BoardRepository {
    // mybatis에서 제공하는 클래스 주입
    private final SqlSessionTemplate sql;

    public void save(BoardDTO boardDTO) {
        // Board ==> <mapper namespace="Board"> 설정된 이름
        // save ==> mapper에서 만든 함수
        sql.insert("Board.save",boardDTO);
    }


    public List<BoardDTO> findAll() {
        // 반환을 해주어야 하니 return으로 조회된 내용을 전달해 줌
        //return sql.selectList("Board.findAll");
        return sql.selectList("Board.findAll");
    }

    public void updateHits(Long id) {
        sql.update("Board.updateHits", id);
    }

    public BoardDTO findById(Long id) {
        return sql.selectOne("Board.findById", id);
    }

    public void update(BoardDTO boardDTO) {
        sql.update("Board.update", boardDTO);
    }

    public void delete(Long id) {
        sql.delete("Board.delete", id);
    }
}
