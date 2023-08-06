package com.example.PixelPro.service;

import com.example.PixelPro.Bean.InboxBean;
import com.example.PixelPro.entity.Inbox;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import com.example.PixelPro.repository.MailRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailRepository mailrepository;

    public void save(Inbox inbox) {
        mailrepository.save(inbox);
    }

    public int maxInum() {
        return mailrepository.findByMaxId();
    }

    public List<Inbox> findByRecipientOrderBySenddateDesc(String email) {
        return mailrepository.findByRecipientOrderBySenddateDesc(email);
    }

    public void saveDate(int inum){
        mailrepository.saveDate(inum);
    }

    public Inbox findByInum(int inum) {
        Inbox inbox = mailrepository.findByInum(inum);
        return inbox;
    }

    public List<Inbox> findByRecipientAndTrashOrderBySenddateDesc(String recipient, String trash) {
        List<Inbox> list = mailrepository.findByRecipientAndTrashOrderBySenddateDesc(recipient, trash);
        return list;
    }

    public void updateInbox(Integer inum) {
        try {
            mailrepository.updateInbox(inum);
        }catch (Exception e){
            System.out.println("service 예외처리");
            return;
        }
    }

    public void updateUnread(int inum){
        mailrepository.updateUnread(inum);
    }

    public void updateRead(int inum) {
        mailrepository.updateInbox(inum);
    }

    public List<Inbox> getSendBoxList(String email) {
        List<Inbox> list = mailrepository.getSendBoxList(email);
        return list;
    }

    public void delOne(int inum) {
        mailrepository.deleteById(inum);
    }

    public List<Inbox> getSendBoxListO(String email) {
        List<Inbox> list = mailrepository.getSendBoxListO(email);
        return list;
    }

    public List<Inbox> findByRecipientAndTrashOrderBySenddate(String email, String trash) {
        List<Inbox> list = mailrepository.findByRecipientAndTrashOrderBySenddate(email, trash);
        return list;
    }
}
