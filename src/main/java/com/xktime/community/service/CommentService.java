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

    public List<Comment> findByArticleId(int articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public List<Comment> findByAccountId(@NonNull String accountId) {
        return commentRepository.findByAccountId(accountId);
    }

    /**
     * 获得文章的评论数量
     * @param articleId
     * @return
     */
    public int getCommentsCount(int articleId) {
        return commentRepository.getCommentCountByArticleId(articleId);
    }


    public List<CommentDTO> getCommentDTOList(int articleId) {
        List<Comment> commentList = findByArticleId(articleId);
        return transferCommentListToCommentDTOList(commentList);
    }

    /**
     * 把后端数据转换成前端显示数据类型
     */
    public CommentDTO transferCommentToCommentDTO(@NonNull Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        User user = userService.findByAccountId(comment.getAuthorAccountId());
        if (user != null) {
            commentDTO.setAuthor(userService.transferUserToUserDTO(user));
        }
        BeanUtils.copyProperties(comment, commentDTO);
        return commentDTO;
    }

    /**
     * 把后端数据转换成前端显示数据类型
     */
    public List<CommentDTO> transferCommentListToCommentDTOList(@NonNull List<Comment> commentList) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        if (!commentList.isEmpty()) {
            for (Comment comment : commentList) {
                commentDTOList.add(transferCommentToCommentDTO(comment));
            }
        }
        return commentDTOList;
    }
}
