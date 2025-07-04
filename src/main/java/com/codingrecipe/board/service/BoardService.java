package com.codingrecipe.board.service;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.dto.BoardFileDTO;
import com.codingrecipe.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    // 생성자 주입 방식
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        // 단일 파일일 경우는 아래처럼 가져온다.
        //if (boardDTO.getBoardFile().isEmpty()) {
        // 다중 파일 등록이므로 아래처럼 가져온다.
        if (boardDTO.getBoardFile().get(0).isEmpty()){
            // 파일 없다.
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        } else {
            // 파일 있다.
            boardDTO.setFileAttached(1);
            // 게시글 저장 후 id값 활용을 위해 리턴 받음.
            BoardDTO savedBoard = boardRepository.save(boardDTO);

            // 하나일 경우의 처리
            // MultipartFile boardFile = boardDTO.getBoardFile();

            // 반복문을 통해서 가져오기 ( 파일이 여러개인 경우의 처리 )
            for (MultipartFile boardFile: boardDTO.getBoardFile()) {
                // 파일 이름 가져오기
                String originalFilename = boardFile.getOriginalFilename();
                System.out.println("originalFilename = " + originalFilename);
                // 저장용 이름 만들기
                System.out.println(System.currentTimeMillis());
                String storedFileName = System.currentTimeMillis() + "-" + originalFilename;
                System.out.println("storedFileName = " + storedFileName);
                // BoardFileDTO 세팅
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFilename);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(savedBoard.getId());
                // 파일 저장용 폴더에 파일 저장 처리
                //String savePath = "/Users/codingrecipe/development/intellij_community/spring_upload_files/" + storedFileName; // mac
                //String savePath = "C:/development/intellij_community/spring_upload_files/" + storedFileName;
                String savePath = "C:/Users/tfo/Desktop/spring_upload_files/" + storedFileName;
                boardFile.transferTo(new File(savePath));
                // board_file_table 저장 처리
                boardRepository.saveFile(boardFileDTO);
            }
        }
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }


    public void delete(Long id) {
        boardRepository.delete(id);
    }

//    public BoardFileDTO findFile(Long id) {
//        return boardRepository.findFile(id);
//    }

    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository.findFile(id);
    }
}
