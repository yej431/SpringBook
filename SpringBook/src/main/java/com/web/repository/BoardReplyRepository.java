package com.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.web.model.BoardReply;

public interface BoardReplyRepository extends JpaRepository<BoardReply,Integer> {

	@Modifying
	@Query(value="insert into boardreply(userId, boardId, content, createDate) values(?1,?2,?3,now())", nativeQuery=true)
	int mSave(int userId, int boardId, String content); //업데이트된 행의 개수를 리턴해줌.
	
	@Query(value="select id,content,createDate,boardId,userId from boardreply where boardId = ?1"
			+ " order by id desc limit ?2, ?3", nativeQuery=true)
	List<BoardReply> listPaging(int boardId,int getPageStart, int perPageNum);
	
	@Query(value="select count(boardId) from boardreply where boardId=?1", nativeQuery=true)
	int countReplies(int boardId);
}
