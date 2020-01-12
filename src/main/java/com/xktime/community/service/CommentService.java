package com.xktime.community.service;

import com.xktime.community.model.dto.CommentDTO;
import com.xktime.community.model.entity.Comment;
import com.xktime.community.model.entity.User;
import com.xktime.community.repository.CommentRepository;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    public void saveComment(Comment comment) {
        commentRepository.saveComment(comment);
    }

    public List<Comment> getCommentByArticleId(int articleId) {
        return commentRepository.getCommentByArticleId(articleId);
    }

    public List<Comment> getCommentByAccountId(String accountId) {
        return commentRepository.getCommentByAccountId(accountId);
    }

    public int getCommentCount(int articleId) {
        return commentRepository.getCommentCountByArticleId(articleId);
    }


    public List<CommentDTO> getCommentDTOList(int articleId) {
        List<Comment> commentList = getCommentByArticleId(articleId);
        return transferCommentListToCommentDTOList(commentList);
    }

    public CommentDTO transferCommentToCommentDTO(@NonNull Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        User user = userService.findByAccountId(comment.getAuthorAccountId());
        if (user != null) {
            commentDTO.setAuthor(userService.transferUserToUserDTO(user));
        }
        BeanUtils.copyProperties(comment, commentDTO);
        return commentDTO;
    }

    public List<CommentDTO> transferCommentListToCommentDTOList(List<Comment> commentList) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        if (commentList != null && !commentList.isEmpty()) {
            for (Comment comment : commentList) {
                commentDTOList.add(transferCommentToCommentDTO(comment));
            }
        }
        return commentDTOList;
    }
}
