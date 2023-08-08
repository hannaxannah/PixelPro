package com.example.PixelPro.service;

import com.example.PixelPro.Bean.NoticeBean;
import com.example.PixelPro.entity.Notice;
import com.example.PixelPro.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoticeService {
    @Autowired
    private final NoticeRepository noticeRepository;

    /*목록*/
    @Transactional
    public Page<Notice> findByOrderByNnumDescNimportantDesc(Pageable pageable) {
        List<Notice> notices = noticeRepository.findByOrderByNnumDescNimportantDesc();
            /* List<Notice> noticeBeanList = new ArrayList<>();
           for (Notice notice : notices) {
                Notice bean = Notice.builder()
                        .nnum(notice.getNnum())
                        .mbname(notice.getMbname())
                        .ntitle(notice.getNtitle())
                        .ndetail(notice.getNdetail())
                        .ndate(notice.getNdate())
                        .build();

                noticeBeanList.add(bean);
            }*/
        return noticeRepository.findAll(pageable);
    }

    /*글작성*/
/*    @Transactional
    public void saveNotice(Notice notice, MultipartFile file) throws Exception {

        *//*우리의 프로젝트경로를 담아주게 된다 - 저장할 경로를 지정*//*
        String projectPath = System.getProperty("user.dir") + "\\PixelPro\\src\\main\\resources\\static\\sign\\";

        *//*식별자 . 랜덤으로 이름 만들어줌*//*
        UUID uuid = UUID.randomUUID();

        *//*랜덤식별자_원래파일이름 = 저장될 파일이름 지정*//*
        String fileName = file.getOriginalFilename();
        String filePath = uuid + "_" + file.getOriginalFilename();
        *//*빈 껍데기 생성*//*
        *//*File을 생성할건데, 이름은 "name" 으로 할거고, projectPath 라는 경로에 담긴다는 뜻*//*
        File saveFile = new File(projectPath, filePath);

        file.transferTo(saveFile);


        //디비에 파일 넣기
        notice.setFilename(fileName);
        //저장되는 경로
        notice.setFilepath("/sign/"+filePath); //저장된파일의이름,저장된파일의경로


        *//*파일 저장*//*
        noticeRepository.save(notice);

        NoticeBean noticeBean = NoticeBean.builder()
                .nnum(notice.getNnum())
                .mbname(notice.getMbname())
                .ntitle(notice.getNtitle())
                .ndetail(notice.getNdetail())
                .filename(notice.getFilename())
                .filepath(notice.getFilepath())
                .build();

    }*/

    /*상세보기*/
    public Notice getNoticeByNnum(int nnum) {
        Notice notice = noticeRepository.findByNnum(nnum);
        return notice;
    }

    public void noticeDelete(int nnum) {
        Notice notice = noticeRepository.findByNnum(nnum);
        noticeRepository.delete(notice);
    }

    public Notice findByNnum(int nnum) {
        Notice notice = noticeRepository.findByNnum(nnum);
        return notice;
    }


    public void deleteAllByNnum(List<Integer> row) {
        noticeRepository.deleteAllById(row);
    }


    public void saveNotice(Notice notice) {
        noticeRepository.save(notice);
    }
}
