package com.hlytec.cloud.biz.system.file.controller;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import com.hlytec.cloud.biz.asset.model.entity.Asset;
import com.hlytec.cloud.biz.asset.service.AssetService;
import com.hlytec.cloud.common.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hlytec.cloud.biz.system.file.service.FileService;
import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.biz.system.user.service.UserService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.common.result.CommonResult;

/**
 * @description: FileController
 * @author: zero
 * @date: 2021/6/16 15:02
 */
@RestController
@RequestMapping("/sys/file")
public class FileController extends BaseController {

    private static final String BUCKET = "netservice";
    private static final String FILE = "file/";
    private static final String PHOTO = "photo/";
    private static final List<String> PHOTO_TYPES = Arrays.asList("png","jpeg","jpg");

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private AssetService assetService;

    @PostMapping("/uploadPhoto")
    public CommonResult<Object> upload(@RequestParam("file") MultipartFile file, String userId) {
        String name = file.getOriginalFilename();
        String suffix = name.split("\\.")[1];
        if (!PHOTO_TYPES.contains(suffix)) {
            throw new NetServiceException(ResultEnum.PHOTO_FORMAT_ERROR);
        }
        fileService.putObject(BUCKET, file, PHOTO + file.getOriginalFilename());
        String objectUrl = fileService.getObjectUrl(BUCKET, PHOTO + file.getOriginalFilename());
        // 更新用户头像
        userService.updateById(User.builder().id(userId).photo(objectUrl).build());
        return success(objectUrl);
    }

    @PostMapping("/uploadFile")
    public CommonResult<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        fileService.putObject(BUCKET, file, FILE + file.getOriginalFilename());
        String objectUrl = fileService.getObjectUrl(BUCKET, FILE + file.getOriginalFilename());
        return success(objectUrl);
    }

    @PostMapping("/uploadPic")
    public CommonResult<Object> upLoadPhoto(@RequestParam("file") MultipartFile file,String assetId){
        String name = file.getOriginalFilename();
        String suffix = name.split("\\.")[1];
        if (!PHOTO_TYPES.contains(suffix)){
            throw new NetServiceException(ResultEnum.PHOTO_FORMAT_ERROR);
        }
        fileService.putObject(BUCKET,file,PHOTO+file.getOriginalFilename());
        String objectUrl = fileService.getObjectUrl(BUCKET,PHOTO + file.getOriginalFilename());
        assetService.updateById(Asset.builder().id(assetId).photo(objectUrl).build());
        return success(objectUrl);
    }
}
