package com.example.PixelPro.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PixelPro.entity.Inbox;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public interface MailRepository extends JpaRepository<Inbox, Integer> {

    @Query(value = "select max(inum) from inbox", nativeQuery = true)
    int findByMaxId();

    List<Inbox> findByRecipientOrderBySenddateDesc(String email);

    @Query(value = "update inbox set senddate=sysdate where inum = :inum", nativeQuery = true)
    void saveDate(@Param("inum") Integer inum);


    Inbox findByInum(int inum);

    List<Inbox> findByRecipientAndTrashOrderBySenddateDesc(String recipient, String trash);
    @Query(value = "select * from inbox i where trash = 'N' and recipient = :recipient and i.email != recipient order by senddate desc", nativeQuery = true)
    List<Inbox> getSendBoxList(@Param("recipient") String recipient);

    @Query(value = "update inbox set readdate=sysdate, status='read' where inum = :inum", nativeQuery = true)
    void updateInbox(@Param("inum") Integer inum);

    @Query(value = "update inbox set readdate=null, status='unread' where inum = :inum", nativeQuery = true)
    void updateUnread(@Param("inum") int inum);

    @Query(value = "select * from inbox i where trash = 'N' and recipient = :recipient and i.email != recipient order by senddate", nativeQuery = true)
    List<Inbox> getSendBoxListO(@Param("recipient") String recipient);

    List<Inbox> findByRecipientAndTrashOrderBySenddate(String recipient, String trash);
}
