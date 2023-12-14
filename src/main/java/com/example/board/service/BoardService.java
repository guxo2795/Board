package com.example.board.service;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.entity.Board;
import com.example.board.exception.AuthorizeException;
import com.example.board.exception.PostNotFoundException;
import com.example.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        // RequestDto -> Entity
        Board board = new Board(requestDto);

        // DB 저장
        Board saveBoard = boardRepository.save(board);

        // Entity -> ResponseDto
        BoardResponseDto boardResponseDto = new BoardResponseDto(saveBoard);

        return boardResponseDto;
    }

    public BoardResponseDto getBoard(Long id) {
        Board board = findBoard(id);

        return new BoardResponseDto(board);
    }


    public List<BoardResponseDto> getBoards() {
        // 게시글 목록 조회
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, String password, BoardRequestDto requestDto) {
        Board board = findBoard(id);

        verifyPassword(board, password);

        board.update(requestDto);
        return new BoardResponseDto(board);
    }

    public void deleteBoard(Long id, String password) {
        Board board = findBoard(id);

        verifyPassword(board, password);

        boardRepository.delete(board);
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new PostNotFoundException("해당 게시글을 찾을 수 없습니다."));
    }

    private static void verifyPassword(Board board, String password) {
        if (!board.getPassword().equals(password)) {
            throw new AuthorizeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
