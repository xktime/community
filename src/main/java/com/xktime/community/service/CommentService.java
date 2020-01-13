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

    public List<Comment> findByArticleId(@NonNull int articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public List<Comment> findByAccountId(@NonNull String accountId) {
        return commentRepository.findByAccountId(accountId);
    }

    public List<Comment> findByArticleIdAndPage(int pageStartIndex, int pageShowNum, int articleId) {
        return commentRepository.findByArticleIdAndPage(pageStartIndex, pageShowNum, articleId);
    }
    /**
     * 获得帖子的评论数量
     * @param articleId
     * @return
     */
    public int getCommentsCountByArticleId(int articleId) {
        return commentRepository.getCommentCountByArticleId(articleId);
    }

    /**
     * 获得文章的评论数量
     * @param accountId
     * @return
     */
    public int getCommentsCountByAccountId(String accountId) {
        return commentRepository.getCommentCountByAccountId(accountId);
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
