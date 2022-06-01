package com.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.web.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> { 
	@Modifying
	@Query(value="update board set count=count+1 where id=?1", nativeQuery=true)
	int countUp(int boardId); 
	
	@Query(value="select @num\\:=@num+1 as num, id,content,count,createDate,title,userId,notice"
			+ " from (select @num\\:=0) a, board b order by notice desc, createDate desc", nativeQuery=true)
	Page<Board> findList(Pageable pageable);
}