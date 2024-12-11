package com.codingrecipe.board.repository;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.dto.BoardFileDTO;
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

    // 파일 첨부 없이 처리할 경우
    // 원래는 리턴이 없으니 void 이고 첨부파일은 dto를 넘겨 받아서 boardDTO를 리턴한다.
//    public void save(BoardDTO boardDTO) {
//        // Board ==> <mapper namespace="Board"> 설정된 이름
//        // save ==> mapper에서 만든 함수
//        sql.insert("Board.save",boardDTO);
//    }

    // 파일 첨부 할 경우 ( 원래는 BoardDTO로 받은것이 아니고 그냥 void로 받았지만 첨부파일 기능을 하기 위해서 BoardDTO로 받았다 )
    // 그래서 BoardController에서 throws IOException을 만들어 넣어 줘야 한다.
    public BoardDTO save(BoardDTO boardDTO) {
        // Board ==> <mapper namespace="Board"> 설정된 이름
        // save ==> mapper에서 만든 함수
        sql.insert("Board.save",boardDTO);
        return boardDTO;
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

    // 첨부 파일 처리
    public void saveFile(BoardFileDTO boardFileDTO) {
        sql.insert("Board.saveFile", boardFileDTO);
    }

//    public BoardFileDTO findFile(Long id) {
//        return sql.selectOne("Board.findFile", id);
//    }

    public List<BoardFileDTO> findFile(Long id) {
        return sql.selectList("Board.findFile", id);
    }
}
