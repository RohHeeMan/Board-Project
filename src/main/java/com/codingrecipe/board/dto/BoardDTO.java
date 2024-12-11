package com.codingrecipe.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardDTO {
    // 필드 정리
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private String createdAt;
    // 파일 첨부 관련 변수 설정
    private int fileAttached; // 존재 유무
    // MultipartFile 스프링에서 제공하는 인터페이스 / 여러개 파일 사용하기 위해 List사용
    private List<MultipartFile> boardFile;
}
