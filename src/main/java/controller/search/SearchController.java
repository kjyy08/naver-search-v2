package controller.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.handler.CustomExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.NaverAPIResultItem;
import service.search.SearchService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/search", "/pages/search/naver"})
public class SearchController extends HttpServlet {
    private final SearchService searchService;

    public SearchController() {
        this.searchService = new SearchService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            dispatchRequest(request, response);
        } catch (Exception e) {
            handleErrorResponse(response, e);
        }
    }

    private void dispatchRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/pages/search/naver" -> renderHtml(request, response);
            case "/search" -> responseRestApi(request, response);
        }
    }

    private void renderHtml(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/news.html").forward(request, response);
    }

    private void responseRestApi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String searchKeyword = request.getParameter("keyword");
        List<NaverAPIResultItem> naverAPIResultItems = searchService.searchByKeyword(searchKeyword);
        String json = createJson(naverAPIResultItems);
        createResponse(response, json);
    }

    private String createJson(List<NaverAPIResultItem> naverAPIResultItems) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(naverAPIResultItems);
    }

    private void createResponse(HttpServletResponse response, String json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.print(json);
            out.flush();
        }
    }

    private void handleErrorResponse(HttpServletResponse response, Exception e) {
        CustomExceptionHandler.handleErrorResponse(response, e);
    }

}
