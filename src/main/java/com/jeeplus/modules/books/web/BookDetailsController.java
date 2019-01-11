package com.jeeplus.modules.books.web;

import com.jeeplus.modules.book_chapter.entity.Chapter;
import com.jeeplus.modules.books.entity.DetailsDTO;
import com.jeeplus.modules.books.service.BookDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class BookDetailsController {
    @Autowired
    private BookDetailsService bookDetailsService;

    @RequestMapping("/selectOneBookDetails")
    public String selectOneBookDetails(String id, HttpSession session, Map map) {


        System.out.println("id =======================> " + id);

//        BooksDetailsDTO booksDetailsDTO = bookDetailsService.selectOneBookDetails(id);
//        map.put("DTO", booksDetailsDTO);
        DetailsDTO detailsDTO = bookDetailsService.selectOneBooksDetails(id);
        map.put("DTO", detailsDTO);
        System.out.println("detailsDTO  ===>  " + detailsDTO);


//        session.setAttribute("DTO", booksDetailsDTO);
//        System.out.println("DTOid"+booksDetailsDTO.getBooks().getId());


//        session.setAttribute("id", id);
//        System.out.println(session.getAttribute("DTO"));


        return "forward:/webpage/E-books/book-details-a.jsp";
    }


    @RequestMapping("/selectOneBookDetailsb")
    public String selectOneBookDetailsb(String id, Map map) {
        System.out.println("id===---===> " + id);  //selectOneBooksDetails
        DetailsDTO detailsDTO = bookDetailsService.selectOneBooksDetails(id);
        map.put("DTO2", detailsDTO);
        System.out.println("detailsDTO  ===>>  " + detailsDTO);
        return "forward:/webpage/E-books/book-details-b.jsp";
    }


    @RequestMapping("/pageSkip")
    public String pageSkip(String id, Map map) {
        Chapter chapter = bookDetailsService.selectOneChapter(id);
        map.put("ids", id);
        map.put("chapter", chapter);
        System.out.println("id  =*********=> " + id);
        System.out.println("chapter  =*********=> " + chapter);


        return "forward:/webpage/E-books/book-details-c.jsp";
//        return null;
    }

}
