package com.example.board.controller;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.dto.ErrorResponseDto;
import com.example.board.exception.AuthorizeException;
import com.example.board.exception.PostNotFoundException;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/boards")
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto requestDto) {
        BoardResponseDto responseDto = boardService.createBoard(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long id) {
        BoardResponseDto responseDto = boardService.getBoard(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/boards")
    public ResponseEntity<List<BoardResponseDto>> getBoards() {
        List<BoardResponseDto> responseDto = boardService.getBoards();
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        BoardResponseDto responseDto = boardService.updateBoard(id, requestDto.getPassword(), requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {

        boardService.deleteBoard(id, requestDto.getPassword());
        return ResponseEntity.noContent().build();

    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> postNotFoundExceptionHandler(PostNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()
                )
        );
    }

    @ExceptionHandler(AuthorizeException.class)
    public ResponseEntity<ErrorResponseDto> postNotFoundExceptionHandler(AuthorizeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponseDto(
                        HttpStatus.UNAUTHORIZED.value(),
                        ex.getMessage()
                )
        );
    }

}
