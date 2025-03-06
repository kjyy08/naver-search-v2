package controller.search;

import exception.handler.CustomExceptionHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.NaverAPIResultItem;
import service.search.SearchService;

import java.util.List;

@WebServlet("/")
public class SearchController extends HttpServlet {
    private final SearchService searchService;

    public SearchController() {
        this.searchService = new SearchService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String searchKeyword = request.getParameter("keyword");

        try {
            // SearchService를 통해 결과를 가져옵니다.
            List<NaverAPIResultItem> naverAPIResultItems = searchService.searchByKeyword(searchKeyword);

            // 검색 결과를 request에 추가합니다.
            request.setAttribute("searchResults", naverAPIResultItems);
            request.setAttribute("keyword", searchKeyword);

            // 결과를 naver.jsp로 전달합니다.
            request.getRequestDispatcher("/WEB-INF/views/naver.jsp").forward(request, response);
        } catch (Exception e) {
            handleErrorResponse(response, e);
        }
    }

    private void handleErrorResponse(HttpServletResponse response, Exception e) {
        CustomExceptionHandler.handleErrorResponse(response, e);
    }
}

