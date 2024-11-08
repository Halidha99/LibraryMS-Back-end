package edu.icet.repository;


import edu.icet.entity.BorrowEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BorrowRepository extends JpaRepository<BorrowEntity,Integer> {
//    List<BorrowEntity> findByMemberId(Integer memberId);
//    List<BorrowEntity> findByBookId(Integer bId);

}
