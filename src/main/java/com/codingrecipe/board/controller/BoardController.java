package com.codingrecipe.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.dto.BoardFileDTO;
import com.codingrecipe.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor  //final이 붙은 필드만 가지고 생성자를 만들어줌
public class BoardController {
    // 생성자 주입 방식을 사용하는 형태임.
    // 컨트롤러는 서비스와 연동하므로
    private final BoardService boardService;
    //slf4j 로그 사용
    private final Logger logger = LoggerFactory.getLogger(BoardController.class);

    //https://www.youtube.com/watch?v=7sFim7OUBws
    // 게시글 작성을 위한 부분
    @GetMapping("/save")
    public String save(){
        return "save";
    }

    // https://www.youtube.com/watch?v=Q05iptwogqU
    // 게시글 저장을 위한 부분
    @PostMapping("/save")
    // @ModelAttribute 일반적으로 dto를 사용하기 위해서
    // 1. html을 BoardDTO에 맞게 만듬
    // 2. 생성한 BoardDTO를 @ModelAttribute를 통하여
    // 3. BoardService에 전달함(Controller는 Service를 참조함 / Service는 Repository를 참조함 )
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        // boardService의 save를 호출함(boardDTO)를 파라미터로 넘겨줌
        boardService.save(boardDTO);
        //return "save";
        return "redirect:/list";
    }

    // 리스트 조회
    @GetMapping("/list")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    // 상세 조회
    @GetMapping("{id}")
    // @PathVariable사용시 반드시 필드명을 기술하도록 습관적으로 연습
    public String findById(@PathVariable("id") Long id, Model model){
        // 조회수 처리
        boardService.updateHits(id);

        // 상세내용
        BoardDTO boardDTO = boardService.findById(id);
        // 여기에서 model.addAttribute("board", boardDTO);처럼 첫 번째 인자에 속성 이름을 지정해야 합니다.
        // 첫번째 인자를 쓰지않고 그냥 boardDTO만 넘겨서 오류가 html파일에서 발생했음...
        model.addAttribute("board", boardDTO);
        // 로그추가
        logger.info("board: {}", boardDTO);

        // 첨부 파일이 있을 경우(detail.html에서 사용할 이름 : <td th:each="boardFile: ${boardFileList}"> )
        if (boardDTO.getFileAttached()==1){
            // 단일 파일일 경우 처리
//          BoardFileDTO boardFileDTO = boardService.findFile(id);
//          model.addAttribute("boardFile", boardFileDTO);

            // 다중 파일일 경우의 처리 : List타입으로 가져온다.
            // 그 다음 BoardService의 save로 가서 BoardFile가져올 경우는
            //  if (boardDTO.getBoardFile().get(0).isEmpty()){ 이런식으로 가져오고
            // 단일 파일일 경우는
            // if (boardDTO.getBoardFile().isEmpty()){ 이렇게 가져온다.
            // 그리고  MultipartFile boardFile = boardDTO.getBoardFile();를
            // for (MultipartFile boardFile: boardDTO.getBoardFile()) { 게 바꿔주면 된다.

            // 그리고 findFile도 List로 리턴하도록 수정
            // public List<BoardFileDTO> findFile(Long id) {

            // 그리고 BoardRepository의 public List<BoardFileDTO> findFile(Long id) {
            // findFile도 List로 바꿔야 함.
            // selectOne이 아니고 selectList로 바꿔줌 return sql.selectList("Board.findFile", id);

            // 그 다음 BoardDTO에 가서
            // private List<MultipartFile> boardFile; 이렇게 List로 가져오도록 수정한다.

            List<BoardFileDTO> boardFileDTOList = boardService.findFile(id);
            model.addAttribute("boardFileList", boardFileDTOList);

            // 로그추가
            logger.info("boardFileList: {}", boardFileDTOList);
        }

        return "detail";
    }

    // 수정처리하기 위한 id조회하여 페이지 불러오기 처리 ( 좌변을 자동으로 만들어주는 방법 alt + enter )
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model){
        // 우선 수정하려는 id를 찾아오기(dto를 받아서 처리함)
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update";
    }

    // 실제 수정 처리 하기
    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO, Model model){
        // 실제 업데이트 처리
        boardService.update(boardDTO);
        // 수정된 내용을 가져오기(업데이트가 반영된 데이터를 가져옴)
        BoardDTO dto = boardService.findById(boardDTO.getId());
        model.addAttribute("board",dto);
        return "detail";
    }

    // 삭제 처리하기
    // PostMapping하기위해서는 post로 보낸 form이 있어야 됨
    // list.html에 하단의 코드로 post로 받을수 있도록 만듬.
    //      <td>
    //        <!-- 삭제 버튼 -->
    //        <form th:action="@{/delete/{id}(id=${board.id})}" method="post" style="display: inline;">
    //          <button type="submit" class="btn btn-danger btn-sm">삭제</button>
    //        </form>
    //      </td>
    // GetMapping시는 form같은거 필요없고 그냥 삭제처리하고 list.html을 보여주면 됨
    // redirect하는것은 수정/삭제 되었으니 새로고침하는 효과이므로 수정/삭제시는 redirect로 사용하면 됨
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        boardService.delete(id);
        return "redirect:/list";
    }


}
