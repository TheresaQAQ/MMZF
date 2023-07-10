package com.rachein.mmzf2.core.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachein.mmzf2.core.service.*;
import com.rachein.mmzf2.entity.DB.*;
import com.rachein.mmzf2.entity.DTO.vx.msg.ArticleReleaseByOpenidDTO;
import com.rachein.mmzf2.entity.DTO.vx.msg.ArticleVX;
import com.rachein.mmzf2.entity.RO.DraftCheckRo;
import com.rachein.mmzf2.entity.enums.DraftMethodEnum;
import com.rachein.mmzf2.entity.enums.DraftStateEnum;
import com.rachein.mmzf2.entity.enums.IdentityEnum;
import com.rachein.mmzf2.exception.GlobalException;
import com.rachein.mmzf2.redis.RedisService;
import com.rachein.mmzf2.redis.myPrefixKey.DraftStatusMessionKey;
import com.rachein.mmzf2.result.CodeMsg;
import com.rachein.mmzf2.utils.AccessTokenUtil;
import com.rachein.mmzf2.utils.HttpRequestUtils;
import com.rachein.mmzf2.utils.ImgSrcReplaceUtils;
import com.rachein.mmzf2.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/9/22
 * @Description
 */
@Service
@Transactional
@Slf4j
public class DraftServiceImpl extends ServiceImpl<BaseMapper<Draft>, Draft> implements IDraftService {

    @Value("${wechat.msg.application}")
    private String application_msg_template_id;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IDraftReleaseMethodService methodService;

    @Autowired
    private IDraftService draftService;

    @Autowired
    private IArticlePhotosService articlePhotosService;

    @Autowired
    private IDraftReleaseTagService releaseService;

    @Autowired
    private IDraftReleaseLogService releaseLogService;

    @Autowired
    private RedisService redisService;

    @Override
    public void application(ReleaseTag ro) {
        //从数据库中找到对应的推文是否存在
        if (!lambdaQuery().eq(Draft::getId, ro.getDraftId()).exists()) {
            throw new GlobalException(CodeMsg.DRAFT_NOT_FOUND);
        }
        log.info(ro.toString());
        //获取当前申请人
//        String applicantOpenid = StpUtil.getLoginIdAsString();
        //根据tag保存到对应的数据库
//        ro.setApplicantOpenid(applicantOpenid);        //设置申请人
        releaseService.save(ro);
        //对应推文设置'待审核'状态
        lambdaUpdate().eq(Draft::getId, ro.getDraftId()).set(Draft::getState, DraftStateEnum.PADDING).set(Draft::getMethod, ro.getIsAll()?DraftMethodEnum.ALL:DraftMethodEnum.TAG).update();
        //保存记录
        DraftReleaseLog log = new DraftReleaseLog();
        log.setDraftId(ro.getDraftId());
        log.setState(DraftStateEnum.PADDING);
        releaseLogService.save(log);
        //微信服务器发送通知，表示申请成功，等待审核
//        MessageUtil.applicationResult("审核已提交", "等待一级管理员审核", "无", applicantOpenid, application_msg_template_id, LocalDateTime.now());
    }


    @Override
    public void check(DraftCheckRo ro) {
        System.out.println(ro.toString());
        //获取审核人员：
//        StpUtil.getSession()
        //更新状态
        lambdaUpdate().eq(Draft::getId, ro.getDraftId())
                .set(Draft::getState, ro.getResult() ? DraftStateEnum.RELEASED : DraftStateEnum.DRAWING)
                .update();
        //找到申请的人
        Draft db = lambdaQuery().eq(Draft::getId, ro.getDraftId()).one();
        //开始发送到微信服务器
        if (ro.getResult()) {
            String publishId = "";
            //绑定的活动叶同时进行通过审核
//            //先放到草稿箱中：获取media_id
//            String media_id = "kYfwkiVvC5IFc_sNjR30nJ3rT__aTYbNvnZl4xubArna-24JOlh3OBKF2-dMIKQt";
            String media_id = toDrawing(ro.getDraftId());
            log.info(">>>>>> media_id = " + media_id);
            if (StringUtils.isBlank(media_id)) {
                throw new GlobalException(CodeMsg.DRAFT_RELEASE_FAIL);
            }
//            //如果是群发:
            if (db.getMethod() == DraftMethodEnum.ALL) {
                log.info("【推文】群发>>>>>>>>>>");
                String url = "https://api.weixin.qq.com/cgi-bin/freepublish/submit?access_token=" + AccessTokenUtil.getToken();
                Map<String, String> map = new HashMap<>();
                map.put("media_id", media_id);
                Response response = HttpRequestUtils.post(url, map);
                try {
                    JSONObject jsonObject = JSON.parseObject(response.body().string());
                    if (!jsonObject.getString("errcode").equals("0")) {
                        throw new GlobalException(CodeMsg.DRAFT_RELEASE_FAIL);
                    }
//                    publishId =
                    redisService.set(DraftStatusMessionKey.get, publishId, publishId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //按标签发
            else {
                log.info("【推文】按标签发>>>>>>>>>>");
                //从release_method表中找到对应的发送标签
                try {
//                    DraftReleaseMethod tag = methodService.lambdaQuery()
//                            .eq(DraftReleaseMethod::getDraftId, ro.getDraftId())
//                            .one();
                    //从user表中找到对应的人群
                    Set<String> toUsers = new HashSet<>();
                    toUsers.add("o611U6tHLILanViepWbx27xQ3X40");
                    toUsers.add("o611U6k70FMdnmljcXZptO3CDzDs");
                    toUsers.add("o611U6sYjJLuvjBKQMJOHCbsCkEY");
                    toUsers.add("o611U6lquyBknX5FCKAfHnFmAkI4");
                    toUsers.add("o611U6mUTXASY5WXVIzdXlqo91Y4");
                    toUsers.add("o611U6oPouFlr52S7j2KFX0UjDiI");
                    toUsers.add("o611U6iBMr165HquznTR5fJlamQ4");
                    toUsers.add("o611U6vFvW8TXr2y36OJhlkAw6F4");
                    toUsers.add("o611U6lUmXZQU0O1VudrII_kVwj8");
                    toUsers.add("o611U6mpf_czvC6zZn5mZJbickkI");
                    toUsers.add("o611U6uJoFMDga88ufYS1UyO9ELA");

//                    Arrays.stream(tag.getIdentity_list().split(",")).map(val -> {
//                        return IdentityEnum.valueOf("I" + String.valueOf(val));
//                    }).collect(Collectors.toList())
//                            .forEach(anEnum -> {
//                                if (anEnum == IdentityEnum.I0) {
//                                    toUsers.addAll(studentLow12InfoService.lambdaQuery()
//                                            .select(StudentLow12Info::getOpenid)
//                                            .le(!Objects.isNull(tag.getAgeLower()), StudentLow12Info::getAge, tag.getAgeLower())
//                                            .ge(!Objects.isNull(tag.getAgeUpper()), StudentLow12Info::getAge, tag.getAgeUpper())
//                                            .list()
//                                            .stream().map(StudentLow12Info::getOpenid)
//                                            .collect(Collectors.toSet()));
//                                }
//                                else if (anEnum == IdentityEnum.I1) {
//                                    toUsers.addAll(studentLow3InfoService.lambdaQuery()
//                                            .select(StudentLow3Info::getOpenid)
//                                            .le(!Objects.isNull(tag.getAgeLower()), StudentLow3Info::getAge, tag.getAgeLower())
//                                            .ge(!Objects.isNull(tag.getAgeUpper()), StudentLow3Info::getAge, tag.getAgeUpper())
//                                            .list()
//                                            .stream().map(StudentLow3Info::getOpenid)
//                                            .collect(Collectors.toSet()));
//                                }
//                                else if (anEnum == IdentityEnum.I2) {
//                                    toUsers.addAll(studentHighInfoService.lambdaQuery()
//                                            .select(StudentHighInfo::getOpenid)
//                                            .eq(!Objects.isNull(tag.getMajor()), StudentHighInfo::getMajorName, tag.getMajor())
//                                            .le(!Objects.isNull(tag.getAgeLower()), StudentHighInfo::getAge, tag.getAgeLower())
//                                            .ge(!Objects.isNull(tag.getAgeUpper()), StudentHighInfo::getAge, tag.getAgeUpper())
//                                            .list()
//                                            .stream().map(StudentHighInfo::getOpenid)
//                                            .collect(Collectors.toSet()));
//                                }
//                            });
                    //调用微信服务器的api：
                    ArticleReleaseByOpenidDTO sendByOid = new ArticleReleaseByOpenidDTO();
                    Map<String, String> mpnews = new HashMap<>();
                    //按照toUser进行发送：
                    mpnews.put("media_id", media_id);
                    sendByOid.setMpnews(mpnews);
                    sendByOid.setTouser(toUsers);
                    log.error(JSON.toJSONString(sendByOid));
                    String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=" + AccessTokenUtil.getToken();
                    Response response = HttpRequestUtils.post(url, sendByOid);
                    String response_json = response.body().string();
                    log.info(response_json);
                    if (!JSON.parseObject(response_json).get("errcode").equals(0)) {
                        throw new GlobalException(CodeMsg.DRAFT_RELEASE_FAIL);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new GlobalException(CodeMsg.DRAFT_RELEASE_FAIL);
                }
            }

        }
        //发微信公众号消息：
        MessageUtil.applicationResult("推文审核", (ro.getResult() ? "成功" : "驳回"), ro.getRemark(), db.getApplicantId(), application_msg_template_id, LocalDateTime.now(), null);

    }

    @Override
    public List<DraftReleaseLog> listApplication() {
        //从draft表中查询对应的数据
        return releaseLogService.lambdaQuery()
                .eq(DraftReleaseLog::getState, DraftStateEnum.PADDING)
                .or()
                .eq(DraftReleaseLog::getState, DraftStateEnum.RELEASED)
                .orderByAsc(DraftReleaseLog::getGmtCreate)
                .list();
    }

    @Override
    public Map<String, Object> listTag(Long draftId) {
        DraftReleaseMethod method = methodService.lambdaQuery()
                .eq(DraftReleaseMethod::getDraftId, draftId)
                .one();
        Map<String, Integer> age = new HashMap<>();
        age.put("upper", method.getAgeUpper());
        age.put("lower", method.getAgeLower());
        IdentityEnum identity = method.getIdentity();
        String major = method.getMajor();
        Map<String, Object> map = new HashMap<>();
        map.put("age", age);
        map.put("identity", identity);
        map.put("major", major);
        return map;
    }

    @Override
    public List<Map<String, Object>> listDrawing() {
        //集装箱
        List<Map<String, Object>> ans = new ArrayList<>();
        //先获取状态为未发布的推文id
        List<Long> draftIds = draftService.lambdaQuery()
                .eq(Draft::getState, "未发布")
                .select(Draft::getId)
                .list()
                .stream().map(Draft::getId)
                .collect(Collectors.toList());
        //从文章表中获取对应头节点的子节点们
        draftIds.forEach(id -> {
            Map<String, Object> draft = new HashMap<>();
            List<Article> list = articleService.lambdaQuery()
                    .eq(Article::getDraftId, id)
                    .select(Article::getId, Article::getCoverUrl, Article::getTitle)
                    .list();
            draft.put("draftId", id);
            draft.put("articles", list);
            ans.add(draft);
        });
        return ans;
    }


    private void turn() {
        /**
         * 将推文的img标签的图片进行字符串切分，获取集合，同时替换为%s的格式
         */
    }

    public String toDrawing(Long draftId) {
        //从数据库中查询article的photos
        Map<Long, Map<String, String>> articlePhotos = new HashMap<>();
        articleService.lambdaQuery()
                .eq(Article::getDraftId, draftId).select(Article::getId)
                .list().stream().map(Article::getId)
                .forEach(articleId -> {
                    articlePhotos.put(articleId, articlePhotosService.lambdaQuery()
                            .eq(ArticlePhotos::getArticleId, articleId)
                            .select(ArticlePhotos::getRelativePath, ArticlePhotos::getVxUrl).list()
                            .stream().collect(Collectors.toMap((ArticlePhotos::getRelativePath), ArticlePhotos::getVxUrl)));
                });

        //从数据库中查询article
        List<ArticleVX> articleVXList = articleService.lambdaQuery()
                .eq(Article::getDraftId, draftId)
                .list()
                .stream().map(source -> {
                    //富文本img转换
                    source.setContent(ImgSrcReplaceUtils.replace(source.getContent(), articlePhotos.get(source.getId())));
                    ArticleVX target = new ArticleVX();
                    BeanUtils.copyProperties(source, target);
                    return target;
                })
                .collect(Collectors.toList());
        Map<String, List<ArticleVX>> map = new HashMap<>();
        map.put("articles", articleVXList);
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String s = JSON.toJSONString(map, config);
        String url = "https://api.weixin.qq.com/cgi-bin/draft/add?access_token=" + AccessTokenUtil.getToken();
        try {
            JSONObject jsonObject = JSON.parseObject(Objects.requireNonNull(HttpRequestUtils.post(url, s).body()).string());
            log.info("【草稿箱保存结果】 " + jsonObject.toJSONString());
            return jsonObject.getString("media_id");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("【草稿箱】保存失败!");
            throw new GlobalException(CodeMsg.DRAFT_SAVE_TO_VX_ERROR);
        }
    }

    @Override
    public Map<String, Object> add() {
        Draft draft = new Draft();
        draftService.save(draft);
        //同时再创建一个文章:
        Article article = new Article();
        article.setDraftId(draft.getId());
        article.setTitle("请点击设置标题");
        articleService.save(article);
        //
        Map<String, Object> map = new HashMap<>();
        map.put("draftId", draft.getId().toString());
        map.put("articleId", article.getId().toString());
        return map;
    }

    @Override
    public void getPublicURL(String publishId) {
        String requestURL = "https://api.weixin.qq.com/cgi-bin/freepublish/get?" + AccessTokenUtil.getToken();
        Map<String, String> map = new HashMap<>();
        map.put("publish_id", publishId);
        try {
            String responseText = HttpRequestUtils.post(requestURL, map).body().string();
            JSONObject jsonObject = JSON.parseObject(responseText);
            String publish_status = jsonObject.getString("publish_status");
            if (publish_status.equals("0")) {
                //成功
//                return
            }
            else if (publish_status.equals("1")) {
                //审核中

            }
            else if (publish_status.equals("2")) {
                //失败

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
