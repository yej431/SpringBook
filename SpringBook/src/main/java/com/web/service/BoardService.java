package com.web.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.config.paging.Criteria;
import com.web.dao.ReplySaveRequestDto;
import com.web.model.Board;
import com.web.model.BoardReply;
import com.web.model.User;
import com.web.repository.BoardReplyRepository;
import com.web.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final BoardReplyRepository replyRepository;
	
	@Transactional
	public void 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		if(user.getUserId().equals("admin")) {
			board.setNotice(1);
		}
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}	
	
	public List<BoardReply> getRepliesPaging(int boardId, Criteria criteria) throws Exception {		
		return replyRepository.listPaging(boardId, criteria.getPageStart(), criteria.getPerPageNum());
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); 
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {			
		int result=replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(),
				replySaveRequestDto.getContent());
		System.out.println("ChatService : "+result);
	}
	
	@Transactional
	public void 댓글삭제하기(int replyId) {
		replyRepository.deleteById(replyId);
	}

	@Transactional
	public void countUp(int boardId) {	
		boardRepository.countUp(boardId);
	}
	
	public int countReplies(int boardId) throws Exception {
		return replyRepository.countReplies(boardId);
	}
}
