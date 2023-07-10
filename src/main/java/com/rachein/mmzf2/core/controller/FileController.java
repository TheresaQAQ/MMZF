package com.rachein.mmzf2.core.controller;

import com.rachein.mmzf2.result.Result;
import com.rachein.mmzf2.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 文件 前端控制器
 * </p>
 *
 * @author 吴远健
 * @since 2022-09-30
 */
@Api(tags = "文件模块")
@RestController
@RequestMapping("file")
public class FileController {

    @ApiOperation("上传【返回url】")
    @PostMapping("/upload")
    public Result<Map<String, String>> upload(MultipartFile file) {
        System.out.println(Objects.isNull(file));
        Map<String, String> map = new HashMap<>();
        map.put("url", FileUtils.save(file));
        return Result.success("success", map);
    }

}
