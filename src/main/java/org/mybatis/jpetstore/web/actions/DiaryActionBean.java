package org.mybatis.jpetstore.web.actions;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.mybatis.jpetstore.domain.Comments;
import org.mybatis.jpetstore.domain.Diary;
import org.mybatis.jpetstore.domain.Likes;
import org.mybatis.jpetstore.service.DiaryService;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SessionScope
public class DiaryActionBean extends AbstractActionBean{

    private static final long serialVersionUID = -934545943912607823L;

    @SpringBean
    private transient DiaryService diaryService;

    private static final String VIEW_PET_DIARY_BOARD = "/WEB-INF/jsp/diary/PetDiary.jsp";
    private static final String VIEW_DIARY_CONTENT = "/WEB-INF/jsp/diary/PetDiaryContent.jsp";
    private static final String VIEW_NEW_DIARY_FORM="/WEB-INF/jsp/diary/NewDiaryForm.jsp";
    private static final String VIEW_EDIT_DIARY_FORM="/WEB-INF/jsp/diary/EditDiaryForm.jsp";
    private static final String VIEW_EDIT_COMMENTS_FORM="/WEB-INF/jsp/diary/UpdateCommentForm.jsp";
    private static final String MAIN="/WEB-INF/jsp/catalog/Main.jsp";

    //diary no
    private int no;
    private int page;
    private int page2;

    // 업로드된 이미지
    private FileBean petImage;
    // paging 처리
    private int totalCount;
    private int beginPage;
    private int endPage;
    private boolean next;
    private boolean prev;
    private Likes likes = new Likes();
    private int clickedLike;

    // paging 처리(현재 다이어리의 작성자가 쓴 다이어리 리스트)
    private int totalCount2;
    private int beginPage2;
    private int endPage2;
    private boolean next2;
    private boolean prev2;

    // 정렬 기준 가지고 있는 변수
    private String orderCategory;
    private String orderLikesOrComments;
    private Diary diary;
    private List<Diary> diaryList;
    private String myUserid;
    // 작성한 덧글
    private Comments comments = new Comments();
    private List<Comments> commentsList;
    public int getNo() {return no;}
    public void setNo(int no) {this.no = no;}
    public String getOrderCategory() {return orderCategory;}
    public void setOrderCategory(String orderCategory) {this.orderCategory = orderCategory;}
    public String getOrderLikesOrComments() {return orderLikesOrComments; }
    public void setOrderLikesOrComments(String orderLikesOrComments) {this.orderLikesOrComments = orderLikesOrComments;}

    public boolean getNext() {return next;}
    public boolean getPrev() {return prev;}
    public int getBeginPage() {return beginPage;}
    public int getEndPage() {return endPage;}
    public void setPage(int page){this.page=page;}
    public int getPage(){return this.page;}
    public boolean getNext2() {return next2;}
    public boolean getPrev2() {return prev2;}
    public int getBeginPage2() {return beginPage2;}
    public int getEndPage2() {return endPage2;}
    public void setPage2(int page2){this.page2=page2;}
    public int getPage2(){return this.page2;}

    public void setClickedLike(int clickedLike){this.clickedLike=clickedLike;}
    public int getClickedLike(){return clickedLike;}
    public Comments getComments() {return comments;}
    public void setComments(Comments comments) {this.comments = comments;}
    public void setC_no(int c_no){comments.setC_no(c_no);}
    public int getC_no(){return comments.getC_no();}
    public String getMyUserid(){return myUserid;}
    public Diary getDiary(){return diary;}
    public void setDiary(Diary diary){this.diary=diary;}
    public List<Diary> getDiaryList(){return diaryList;}
    public List<Comments> getCommentsList(){return commentsList;}
    private List<Diary> diaryListByUserid;
    public List<Diary> getDiaryListByUserid(){return diaryListByUserid;}
    public String getImgurl(){return diary.getImgurl();}
    public void setImgurl(String imgurl){diary.setImgurl(imgurl);}
    public FileBean getPetImage() {return petImage;}
    public void setPetImage(FileBean petImage) {this.petImage = petImage;}
    private String keyword;
    public void setKeyword(String keyword){this.keyword=keyword;}
    public String getKeyword(){return keyword;}
    public int reset=0;

    /**
     * param1 : no
     * @return Forward, 양육일기 상세 정보
     */
    @DefaultHandler
    public Resolution getDiaryContent(){
        int exist=diaryService.doesDiaryExist(no);
        if (no == 0 || exist == 0)
            // no 파라미터가 넘어오지 않았을 경우, 잘못된 접근임!!
            return new RedirectResolution(DiaryActionBean.class, "viewDiaryBoard");

        diary=diaryService.getDiary(no);
        paging2();
        int offset = (page2-1) * 5;
        diaryListByUserid = diaryService.getDiaryListByUserid(diary.getUserid(), offset);

        commentsList = diaryService.getCommentsList(no);
        clickedLike = 0;
        if(isAuthenticated()) {
            likes = new Likes();
            likes.setUserid(myUserid);
            likes.setD_no(diary.getNo());
            clickedLike= diaryService.didClickedLike(likes);
        }
        return new ForwardResolution(VIEW_DIARY_CONTENT);
    }
    public String getUserid(){return diary.getUserid();}
    public void setUserid(String userid){diary.setUserid(userid);}

    public String getDate(){return diary.getDate();}
    public void setDate(String date){diary.setDate(date);}

    public String getTitle(){return diary.getTitle();}
    public void setTitle(String title){diary.setTitle(title);}

    public String getContent(){return diary.getContent();}
    public void setContent(String content){diary.setContent(content);}

    public int getViews(){return diary.getViews();}
    public void setViews(int views){diary.setViews(views);}

    public String getCategoryid(){return diary.getCategoryid();}
    public void setCategoryid(String categoryid){diary.setCategoryid(categoryid);}
    private int doLike=0;
    public void setDoLike(int doLike){this.doLike=doLike;}

    public Resolution viewMyDiary(){
        if (!isAuthenticated())
            return new RedirectResolution(DiaryActionBean.class, "viewDiaryBoard");
        int myCnt = diaryService.getDiaryCountByUserid(myUserid);
        if(myCnt>0){
            int memory =diaryService.getLatestMyDiaryNo(myUserid);
            clear();
            no = memory;
            return new RedirectResolution(DiaryActionBean.class);
        }
        else{
            return new RedirectResolution(DiaryActionBean.class, "viewDiaryBoard");
        }
    }

    /**
     * param1 : no
     * @return Forward, 양육일기 수정 폼
     */
    public Resolution getEditDiaryForm(){
        int exist=diaryService.doesDiaryExist(no);
        if (!isAuthenticated() || !isMyDiaryOrComments(diaryService.getDiaryUser(no)) || no == 0 || exist==0)
            return new RedirectResolution(DiaryActionBean.class, "viewDiaryBoard");
        diary=diaryService.getDiary(no);
        return new ForwardResolution(VIEW_EDIT_DIARY_FORM);
    }
    /**
     * @return Forward, 양육일기 작성 폼
     */
    public Resolution getNewDiaryForm() {
        if (!isAuthenticated())
            return new RedirectResolution(AccountActionBean.class);
        clear();
        return new ForwardResolution(VIEW_NEW_DIARY_FORM);
    }
    /**
     * param : Diary[imgurl, title, content, categoryid, userid]
     * @return Redirect, 양육일기 작성
     */
    //임시로 삽입, 삭제, 수정 작업 후 메인 페이지로 이동
    public Resolution insertDiary(){
        if (!isAuthenticated())
            return new RedirectResolution(DiaryActionBean.class, "viewDiaryBoard");
//        테스트할 때는 톰캣이 종료 및 실행될때마다 정적 리소스가 초기화 되기 때문에, diary.imgurl = "default.png"로 다 넣으시면 됩니다
//        try {
//            fileUpload();
//        } catch (IOException e) {
//            setMessage("Image File Error");
//            return new ForwardResolution(MAIN);
//        }
        diary.setImgurl("default.png");
        diaryService.insertDiary(diary);
        int memory = diary.getNo();
        clear();
        no=memory;
        return new RedirectResolution(DiaryActionBean.class);
    }
    /**
     * param : Diary[imgurl, title, content, categoryid, userid]
     * @return Redirect, 양육일기 수정
     */
    public Resolution updateDiary(){
        if (!isAuthenticated() || !isMyDiaryOrComments(diary.getUserid()) || diary == null)
            return new RedirectResolution(DiaryActionBean.class, "viewDiaryBoard");
        //테스트할 때는 톰캣이 종료 및 실행될때마다 정적 리소스가 초기화 되기 때문에, diary.imgurl = "default.png"로 다 넣으시면 됩니다
//        try {
//            fileUpload();
//        } catch (IOException e) {
//            setMessage("Image File Error");
//            return new ForwardResolution(MAIN);
//        }
        diary.setImgurl("default.png");
        diaryService.updateDiary(diary);
        int memory = diary.getNo();
        clear();
        no = memory;
        return new RedirectResolution(DiaryActionBean.class);
    }
    /**
     * param : no
     * @return Redirect, 양육일기 삭제
     */
    public Resolution deleteDiary(){
        if (!isAuthenticated() || !isMyDiaryOrComments(diary.getUserid()) || no == 0)
            return new ForwardResolution(MAIN);
        //파일 삭제 메소드가 추가되었습니다. 최종적으로 주석을 해제하시면 됩니다.
        //fileDelete(diaryService.getFilename(no));
        diaryService.deleteDiary(no);
        clear();
        return new RedirectResolution(DiaryActionBean.class, "viewDiaryBoard");
    }
    /**
     * param : page, orderCategory, orderLikesOrComments
     * @return Forward, 양육일기 리스트 조회
     */
    public ForwardResolution viewDiaryBoard(){
        if(reset == 1 || page == 0) {
            clear();
        }
        if (orderLikesOrComments == null) {
            orderLikesOrComments = "likes";
        }
        paging();
        int offset = (page - 1) * 6;
        diaryList = diaryService.getDiaryList(offset, orderCategory, orderLikesOrComments, keyword);
        return new ForwardResolution(VIEW_PET_DIARY_BOARD);
    }

    public void paging() {
        totalCount = diaryService.getDiaryCount(orderCategory, keyword);

        endPage = ((int)Math.ceil(page / (double)10)) * 10;

        beginPage = endPage - (10 - 1);

        int totalPage = (int)Math.ceil(totalCount / (double)6);

        if (totalPage < endPage) {
            endPage = totalPage;
            next = false;
        } else {
            next = true;
        }
        prev = beginPage!=1;
    }

    public void paging2() {
        totalCount2 = diaryService.getDiaryCountByUserid(diary.getUserid());
        endPage2 = ((int)Math.ceil(page2 / (double)10)) * 10;

        beginPage2 = endPage2 - (10 - 1);

        int totalPage = (int)Math.ceil(totalCount2 / (double)5);

        if (totalPage < endPage2) {
            endPage2 = totalPage;
            next2 = false;
        } else {
            next2 = true;
        }
        prev2 = beginPage2!=1;
    }

    public void fileUpload() throws IOException {
        String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());  //현재시간
        String saveDir = context.getRequest().getSession().getServletContext().getRealPath("/static");
        File dir = new File(saveDir);
        diary.setImgurl("default.png");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (petImage != null && petImage.getSize() != 0) {
            try {
                String fileName = petImage.getFileName();
                int i = -1;
                i = fileName.lastIndexOf("."); // 파일 확장자 위치
                String realFileName = now + fileName.substring(i, fileName.length());  //현재시간과 확장자 합치기
                diary.setImgurl(realFileName);
                //System.out.println(saveDir + "/" + realFileName);
                petImage.save(new File(saveDir + "/" + realFileName));
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void fileDelete(String filename){
        String saveDir = context.getRequest().getSession().getServletContext().getRealPath("/static");
        File file = new File(saveDir + "/" + filename);
        if (file.exists()) {
            file.delete();
        }
    }

    public boolean isAuthenticated() {
        HttpSession session = context.getRequest().getSession();
        AccountActionBean accountBean = (AccountActionBean) session.getAttribute("/actions/Account.action");
        if (accountBean == null || !accountBean.isAuthenticated()) {
            //setMessage("You must sign on before attempting to check out.  Please sign on and try checking out again.");
            return false;
        } else if (accountBean.isAuthenticated()) {
            myUserid = accountBean.getAccount().getUsername();
            return true;
        } else {
            return false;
        }
    }

    public boolean isMyDiaryOrComments(String userid) {
        if (myUserid == null)
            return false;
        else if (myUserid.equals(userid))
            return true;
        else
            return false;
    }
    /**
     * param : no
     * @return Redirect, 좋아요 삽입
     */
    public Resolution insertLike(){
        if (!isAuthenticated() || no == 0)
            return new RedirectResolution(DiaryActionBean.class);
        likes.setUserid(myUserid);
        likes.setD_no(no);
        clickedLike=diaryService.didClickedLike(likes);
        if(clickedLike==0){
            diaryService.insertLike(likes);
        }
        int memory = no;
        clear();
        no = memory;
        return new RedirectResolution(DiaryActionBean.class);
    }
    /**
     * param : no
     * @return Redirect, 좋아요 삭제
     */
    public Resolution deleteLike(){
        if (!isAuthenticated() || no == 0)
            return new RedirectResolution(DiaryActionBean.class);
        likes.setUserid(myUserid);
        likes.setD_no(no);
        clickedLike=diaryService.didClickedLike(likes);
        if(clickedLike==1){
            diaryService.deleteLike(likes);
        }
        int memory = no;
        clear();
        no = memory;
        return new RedirectResolution(DiaryActionBean.class);
    }
    /**
     * param : no
     * @return Redirect, 덧글 입력
     */
    public Resolution insertComment(){
        if (!isAuthenticated() || no == 0)
            return new RedirectResolution(DiaryActionBean.class);
        String cont = comments.getComment();
        cont.replace("\n","<br>");
        comments.setComment(cont);
        comments.setUserid(myUserid);
        comments.setD_no(no);
        diaryService.insertComment(comments);
        int memory = no;
        clear();
        no = memory;
        return new RedirectResolution(DiaryActionBean.class);
    }
    public Resolution getUpdateCommentForm(){
        if (!isAuthenticated() || comments.getC_no() == 0 || !isMyDiaryOrComments(diaryService.getCommentUser(comments.getC_no())) || no == 0)
            return new RedirectResolution(DiaryActionBean.class, "getDiaryContent");

        comments = diaryService.getComment(comments.getC_no());
        return new ForwardResolution(VIEW_EDIT_COMMENTS_FORM);
    }
    /**
     * param : no, Comments[c_no]
     * @return Redirect, 덧글 수정
     */
    public Resolution updateComment(){
        if (!isAuthenticated() || no == 0 || comments.getC_no() == 0 || !isMyDiaryOrComments(diaryService.getCommentUser(comments.getC_no())))
            return new RedirectResolution(DiaryActionBean.class);
        String cont = comments.getComment();
        cont.replace("\n","<br>");
        comments.setComment(cont);
        comments.setUserid(myUserid);
        diaryService.updateComment(comments);
        int memory = no;
        clear();
        no = memory;
        return new RedirectResolution(DiaryActionBean.class);
    }
    /**
     * param : no, Comments[c_no]
     * @return Redirect, 덧글 삭제
     */
    public Resolution deleteComment(){
        if (!isAuthenticated() && isMyDiaryOrComments(diaryService.getCommentUser(comments.getC_no())))
            return new RedirectResolution(DiaryActionBean.class);
        diaryService.deleteComment(comments);
        int memory = no;
        clear();
        no = memory;
        return new RedirectResolution(DiaryActionBean.class);
    }

    public void clear() {
        no = 0;
        page = 1;
        page2 = 1;

        petImage = null;

        totalCount = 0;
        beginPage = 0;
        endPage = 0;
        next = false;
        prev = false;

        totalCount2 = 0;
        beginPage2 = 0;
        endPage2 = 0;
        next2 = false;
        prev2 = false;

        likes = new Likes();
        comments = new Comments();
        clickedLike = 0;

        orderCategory = null;
        orderLikesOrComments = null;

        diary = null;
        diaryList = null;

        myUserid = null;

        keyword=null;
        reset = 0;
    }
}
