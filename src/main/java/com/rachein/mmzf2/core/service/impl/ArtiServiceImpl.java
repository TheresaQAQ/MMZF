package com.rachein.mmzf2.core.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachein.mmzf2.core.service.*;
import com.rachein.mmzf2.entity.DB.*;
import com.rachein.mmzf2.entity.RO.ArticleAddRo;
import com.rachein.mmzf2.entity.RO.UploadNetImgRo;
import com.rachein.mmzf2.entity.VO.ActivityVo;
import com.rachein.mmzf2.entity.VO.ArticleInfoVo;
import com.rachein.mmzf2.entity.VO.ArticleVo;
import com.rachein.mmzf2.entity.VO.FileVo;
import com.rachein.mmzf2.exception.GlobalException;
import com.rachein.mmzf2.redis.RedisService;
import com.rachein.mmzf2.redis.myPrefixKey.ArticleKey;
import com.rachein.mmzf2.result.CodeMsg;
import com.rachein.mmzf2.utils.FileUtils;
import com.rachein.mmzf2.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 物联网工程系 ITAEM 唐奕泽 | 计算机科学系 ITAEM 吴远健
 * @Description
 * @date 2022/9/20 21:40
 */
@Slf4j
@Transactional
@Service
public class ArtiServiceImpl extends ServiceImpl<BaseMapper<Article>, Article> implements IArticleService {

    @Autowired
    private IDraftService draftService;

    @Autowired
    private IArticlePhotosService articlePhotosService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private VXServiceImpl vxService;

    @Autowired
    RedisService redisService;

    @Value("resource.article.defaultCoverUrl")
    private String default_coverUrl;


    @Override
    public FileVo coverUpload(MultipartFile multipartFile, Long articleId) {
        // 校验文件
        FileUtils.judge(multipartFile, 6400L, "img");
        // 保存到本地
        Map<String, Object> map = FileUtils.save2(multipartFile);
        File file = (File) map.get("file");
        String relative_path = (String) map.get("relative_path");
        String media_id = "";
        // 上传到微信服务器上
        media_id = vxService.uploadCover(file);
        // 文章表更新
        String network_path = FileUtils.local_url + FileUtils.reflect_path_prefix + relative_path;
//        System.out.println("media_id =" + media_id);
//        System.out.println("network_path =" + network_path);
        articleService.lambdaUpdate()
                .eq(Article::getId, articleId)
                .set(Article::getThumbMediaId, media_id)
                .set(Article::getCoverUrl, network_path)
                .update();
        FileVo vo = new FileVo();
        vo.setUrl(network_path);
        return vo;
    }


    @Override
    public FileVo materialUpload(MultipartFile multipartFile, Long articleId) {
        // 保存到本地
        Map<String, Object> map = FileUtils.save2(multipartFile);
        File file = (File) map.get("file");
        String relative_path = (String) map.get("relative_path");
        String network_path = FileUtils.local_url + FileUtils.reflect_path_prefix + relative_path;
        String vxURL = vxService.articleMaterialUpload(file);
        //备份到article_photos表中
        ArticlePhotos articlePhotos = new ArticlePhotos();
        articlePhotos.setArticleId(articleId);
        articlePhotos.setRelativePath("/" + relative_path);
        articlePhotos.setVxUrl(vxURL);
        articlePhotosService.save(articlePhotos);
        FileVo vo = new FileVo();
        vo.setUrl(network_path);
        return vo;
    }

    @Override
    public Article createArticle(ArticleAddRo ro) {
        //先从数据库中，找到对应的推文
        if (!draftService.lambdaQuery().eq(Draft::getId, ro.getDraftId()).exists()) {
            throw new GlobalException(CodeMsg.DRAFT_NOT_FOUND);
        }
        //数据库创建article:
        Article newArticle = new Article();
        newArticle.setDraftId(ro.getDraftId());
        newArticle.setTitle("请点击设置标题");
        save(newArticle);
        return newArticle;
    }

    @Override
    public void updateContentById(ArticleAddRo ro) {
        //复制
//        Article article = new Article();
        lambdaUpdate().eq(Article::getId, ro.getId()).set(Article::getContent, ro.getContent()).update();
//        BeanUtils.copyProperties(ro, article);
        //更新到redis中
//        redisService.set(ArticleKey.getById, article.getId().toString(), article);
    }

    @Override
    public Map<Long, List<ArticleVo>> listDraft() {
        //获取数据库中所有的推文的id（未发布的）
        Map<Long, List<ArticleVo>> map = new HashMap<>();
        Set<Long> draftIds = draftService.lambdaQuery()

                .select(Draft::getId)
                .list()
                .stream().map(Draft::getId)
                .collect(Collectors.toSet());
        //从article表中
        for (Long draftId : draftIds) {
            List<ArticleVo> articleVos = listArticleByDraftId(draftId);
            if (Objects.isNull(articleVos)) {
                continue;
            }
            map.put(draftId, articleVos);
        }
        return map;
    }


    @Override
    public void updateTitle(Long articleId, String newTile) {
        lambdaUpdate().eq(Article::getId, articleId).set(Article::getTitle, newTile).update();

    }

    @Override
    public void updateAuthor(Long articleId, String author) {
        lambdaUpdate().eq(Article::getId, articleId).set(Article::getAuthor, author).update();
    }

    @Override
    public String materialUpload(UploadNetImgRo ro) {
        String fileUrl = ro.getUrl();
        String path = null;
        String netURL = null;
        if (!StringUtils.isBlank(fileUrl)) {
            //下载时文件名称
            String extension = fileUrl.substring(fileUrl.lastIndexOf("."));
            try {
                //newFileName:
                String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + File.separator +
                        UUIDUtil.uuid().substring(6) + extension;
                path = FileUtils.local_path + newFileName;
                HttpUtil.downloadFile(fileUrl, path);
                //上传到微信服务器中：
                File file = new File(path);
                String vxURL = vxService.articleMaterialUpload(file);
                //记录到数据库中
                ArticlePhotos articlePhotos = new ArticlePhotos();
//                articlePhotos.setArticleId(Long.parseLong());
                articlePhotos.setRelativePath(File.separator + newFileName);
                articlePhotos.setVxUrl(vxURL);
                articlePhotosService.save(articlePhotos);
                //网络地址
                netURL = FileUtils.local_url  + FileUtils.reflect_path_prefix + newFileName;
                log.info("[保存图片] {路径:" + fileUrl + "},\n {网络地址: " + netURL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return netURL;
    }

    @Override
    public void removeArticleById(Long articleId) {
//        //从redis中清除
//        redisService.delete(ArticleKey.getById,articleId.toString());
        //从文章表中清除
        lambdaUpdate().eq(Article::getId, articleId).remove();
        //清楚
    }

    @Override
    public ArticleInfoVo getArticleInfoById(String articleId) {
        Article article = lambdaQuery().eq(Article::getId, articleId).one();
        ArticleInfoVo articleInfoVo = new ArticleInfoVo();
        BeanUtils.copyProperties(article, articleInfoVo);
        //同时获取绑定的活动
        Activity activity = activityService.lambdaQuery().eq(Activity::getArticleId, articleId).one();
        if (!Objects.isNull(activity)) {
            ActivityVo activityVo = new ActivityVo();
            BeanUtils.copyProperties(activity, activityVo);
            articleInfoVo.setActivity(activityVo);
        }
        return articleInfoVo;
    }


    @Override
    /**
     * //数据库创建Draft
     * //return id
     */
    public Long createDraft() {
        Draft draft = new Draft();
        draftService.save(draft);
//        //同时创建自动创建一个文章：
        Article a = new Article();
        a.setDraftId(draft.getId());
        save(a);
        return draft.getId();
    }

    @Override
    public List<ArticleVo> listArticleByDraftId(Long draftId) {
        List<ArticleVo> articleVoList = lambdaQuery().select(Article::getCoverUrl, Article::getTitle, Article::getId).eq(Article::getDraftId, draftId).list()
                .stream().map(db -> {
                    ArticleVo articleVo = new ArticleVo();
                    BeanUtils.copyProperties(db, articleVo);
                    return articleVo;
                }).collect(Collectors.toList());
        if (articleVoList.size() == 0) {
            throw new GlobalException(CodeMsg.DRAFT_EMPTY);
        }
        return articleVoList;
    }

    @Override
    public void removeDraftByDraftId(Long draftId) {
//        从draft表中移除
//        draftService.lambdaUpdate().eq(Draft::getId, draftId).remove();
//        //从中间表中
//        //搜索关联的article的id
//        List<Long> articleIds = draftArticleService.lambdaQuery()
//                .eq(DraftArticleRelation::getDraftId, draftId)
//                .select(DraftArticleRelation::getArticleId)
//                .list()
//                .stream()
//                .map(t -> {
//                    return t.getArticleId();
//                })
//                .collect(Collectors.toList());
//        draftArticleService.lambdaUpdate().eq(DraftArticleRelation::getDraftId, draftId).remove();
//        //从article表中移除
//        lambdaUpdate().eq(Article::getId, articleIds).remove();

        /**
         * 1、先从article表中删除 draft_id = draftId（先保存id -》 List集合）
         * 2 从draft 删除id
         * 3 redis 删除对应 （根据list集合删除）
         */

        List<Article> articleList = lambdaQuery().eq(Article::getDraftId, draftId).list();
        draftService.lambdaUpdate().eq(Draft::getId, draftId).remove();
        articleList.forEach(l -> {
            redisService.delete(ArticleKey.getById, l.getId().toString());
            lambdaUpdate().eq(Article::getId, l.getId()).remove();
        });
    }


    @Override
    public void send(String media_id, String tag) {

    }

    @Override
    public void send(String media_id) {

    }
}
