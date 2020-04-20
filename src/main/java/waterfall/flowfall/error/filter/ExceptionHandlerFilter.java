package waterfall.flowfall.error.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;
import waterfall.flowfall.model.dto.ErrorResponseDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            sendError(response, e.getMessage(), FORBIDDEN.getReasonPhrase(), FORBIDDEN.value());
        } catch (RuntimeException e) {
            sendError(response, e.getMessage(), INTERNAL_SERVER_ERROR.getReasonPhrase(), INTERNAL_SERVER_ERROR.value());
        }
    }

    private void sendError(HttpServletResponse response, String message, String type, int status) throws IOException {
        ErrorResponseDto errorResponse = new ErrorResponseDto(message, type, status);

        response.setStatus(status);
        response.setContentType("application/json");

        response.getWriter().write(convertObjectToJson(errorResponse));
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
