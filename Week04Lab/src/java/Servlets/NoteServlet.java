package Servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Note;

public class NoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path;
        Note note;
        
        try {
        path = getServletContext().getRealPath("/WEB-INF/note.txt");
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        note = new Note(br.readLine(), br.readLine());
        request.setAttribute("note", note);
        br.close();
         
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String edit = request.getParameter("edit");
        if (edit != null){ 
            getServletContext().getRequestDispatcher("/WEB-INF/editnote.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp").forward(request, response);
        }      
       
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String nTitle = request.getParameter("title");
       String nContent = request.getParameter("content");
       String path;
       Note note;
       note = new Note(nTitle, nContent);
       
       try {
           path = getServletContext().getRealPath("/WEB-INF/note.txt");
           PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path, false)));
           pw.write(note.getTitle() + "\n");
           pw.write(note.getContent());
           pw.flush();
           pw.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
       note = new Note(nTitle, nContent);
       request.setAttribute("note", note);
       getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp").forward(request, response);
    }
}
