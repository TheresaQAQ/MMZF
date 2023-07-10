package com.rachein.mmzf2.utils;

import com.rachein.mmzf2.core.service.ICollegeStudentService;
import com.rachein.mmzf2.core.service.IGraduatePersonService;
import com.rachein.mmzf2.core.service.ISeniorStudentService;
import com.rachein.mmzf2.core.service.IUserService;
import com.rachein.mmzf2.entity.DB.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2023/1/6
 * @Description
 */
public class ReleaseConditionSelectUtils {

    public static ICollegeStudentService collegeStudentService;

    public static ISeniorStudentService seniorStudentService;

    public static IGraduatePersonService graduatePersonService;

    public static IUserService userService;

    public static List<String> listUserIdsByCondition(ReleaseTag releaseTag) {
        List<String> res = new ArrayList<>();
        if (releaseTag.getIsAll()) {
            res = userService.lambdaQuery().select(User::getOpenid).list().stream().map(User::getOpenid).collect(Collectors.toList());
        }
        else {
            if (releaseTag.getCollegeIsTo()) {
                List<String> list = collegeStudentService.lambdaQuery()
                        .select(CollegeStudent::getOpenid)
                        .in(releaseTag.getCollegeAddress() != null, CollegeStudent::getAddress, releaseTag.getCollegeAddress())
                        .in(releaseTag.getCollegeHuixiangyiyuan() != null, CollegeStudent::getHuixiangyiyuan, releaseTag.getCollegeHuixiangyiyuan())
                        .in(releaseTag.getCollegeJiuyefangxiang() != null, CollegeStudent::getJiuyefangxiang, releaseTag.getCollegeJiuyefangxiang())
                        .in(releaseTag.getCollegeZhengzhimianmao() != null, CollegeStudent::getZhengzhimianmao, releaseTag.getCollegeZhengzhimianmao())
                        .in(releaseTag.getCollegeStage() != null, CollegeStudent::getAddress, releaseTag.getCollegeStage())
                        .in(releaseTag.getCollegeMajor() != null, CollegeStudent::getMajor, releaseTag.getCollegeMajor())
                        .between(releaseTag.getCollegeBirthday() != null, CollegeStudent::getBirthday, releaseTag.getCollegeBirthday().get(0), releaseTag.getCollegeBirthday().get(1))
                        .between(releaseTag.getCollegeGraduateDate() != null, CollegeStudent::getGraduateDate, releaseTag.getCollegeGraduateDate().get(0), releaseTag.getCollegeGraduateDate().get(1))
                        .in(releaseTag.getCollegeMajor() != null, CollegeStudent::getMajor, releaseTag.getCollegeMajor())
                        .list()
                        .stream().filter(collegeStudent -> collegeStudent.getXiwangjieshouxingxi().retainAll(releaseTag.getCollegeXiwangjieshouxingxi()))
                        .map(CollegeStudent::getOpenid)
                        .collect(Collectors.toList());
                res = CollectionUtil.combine(list, res);
            }
            if (releaseTag.getGraduateIsTo()) {
                List<String> list = graduatePersonService.lambdaQuery()
                        .select(GraduatePerson::getOpenid)
                        .in(releaseTag.getGraduateAddress() != null, GraduatePerson::getAddress, releaseTag.getGraduateAddress())
                        .in(releaseTag.getGraduateNowIn() != null, GraduatePerson::getNowIn, releaseTag.getGraduateNowIn())
                        .in(releaseTag.getGraduateHuixiangyiyuan() != null, GraduatePerson::getHuixiangfazhanyiyuan, releaseTag.getGraduateHuixiangyiyuan())
                        .in(releaseTag.getGraduateJiuyeqingkuang() != null, GraduatePerson::getJiuyeqingkuang, releaseTag.getGraduateJiuyeqingkuang())
                        .in(releaseTag.getGraduateZhengzhimianmao() != null, GraduatePerson::getZhengzhimianmao, releaseTag.getCollegeZhengzhimianmao())
//                    .in(releaseTag.getGraduateStage() != null, GraduatePerson::get, releaseTag.getCollegeStage())
                        .in(releaseTag.getGraduateMajor() != null, GraduatePerson::getMajor, releaseTag.getCollegeMajor())
                        .between(releaseTag.getGraduateBirthday() != null, GraduatePerson::getBirthday, releaseTag.getGraduateBirthday().get(0), releaseTag.getGraduateBirthday().get(1))
                        .between(releaseTag.getGraduateGraduateDate() != null, GraduatePerson::getGraduateDate, releaseTag.getGraduateGraduateDate().get(0), releaseTag.getGraduateGraduateDate().get(1))
                        .in(releaseTag.getCollegeMajor() != null, GraduatePerson::getMajor, releaseTag.getCollegeMajor())
                        .list()
                        .stream().filter(GraduatePerson -> GraduatePerson.getXiwangxinxi().retainAll(releaseTag.getGraduateXiwangjieshouxingxi()))
                        .map(GraduatePerson::getOpenid)
                        .collect(Collectors.toList());
                res = CollectionUtil.combine(list, res);
            }
            if (releaseTag.getHighIsTo()) {
                List<String> list = seniorStudentService.lambdaQuery()
                        .select(SeniorStudent::getOpenid)
                        .between(releaseTag.getHighGraduate() != null, SeniorStudent::getGraduateYear, releaseTag.getHighGraduate().get(0), releaseTag.getHighGraduate().get(1))
                        .in(releaseTag.getHighMajor()!=null, SeniorStudent::getMajor, releaseTag.getHighMajor())
                        .list().stream()
                        .map(SeniorStudent::getOpenid)
                        .collect(Collectors.toList());
                res = CollectionUtil.combine(list, res);
            }
        }
        return res;
    }
}
