package waterfall.flowfall.security.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import waterfall.flowfall.model.Board;
import waterfall.flowfall.service.BoardService;

import java.util.List;

@Component
public class BoardPermissions extends Permissions<Board> {

    private BoardService boardService;

    @Autowired
    public BoardPermissions(BoardService boardService) {
        this.boardService = boardService;
    }

    @Override
    public boolean isOwner(Long targetId) {
        Board board = boardService.findById(targetId).get();
        return isOwner(board);
    }

    @Override
    public boolean isOwner(Board targetObject) {
        boolean hasPermission = false;
        if(targetObject != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            hasPermission = authentication.getPrincipal().equals(targetObject.getUser().getEmail());
        }

        return hasPermission;
    }

    @Override
    public boolean isOwner(List<Board> targetObjects) {
        boolean hasPermission = false;
        if(targetObjects != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            for(Board board: targetObjects) {
                hasPermission = authentication.getPrincipal().equals(board.getUser().getEmail());
            }
        }

        return hasPermission;
    }

    @Override
    public boolean isCollaborator(Long targetId) {
        Board board = boardService.findById(targetId).get();
        return isCollaborator(board);
    }

    @Override
    public boolean isCollaborator(Board targetObject) {
        boolean hasPermission = false;
        if(targetObject != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            hasPermission = targetObject.getCollaboratorsNames().contains(authentication.getPrincipal());
        }

        return hasPermission;
    }
}
