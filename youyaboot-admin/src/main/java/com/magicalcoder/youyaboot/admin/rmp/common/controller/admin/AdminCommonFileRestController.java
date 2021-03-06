package com.magicalcoder.youyaboot.admin.rmp.common.controller.admin;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.magicalcoder.youyaboot.admin.common.config.UploadFilePathConfig;
import com.magicalcoder.youyaboot.core.common.exception.BusinessException;
import com.magicalcoder.youyaboot.core.serialize.CommonReturnCode;
import com.magicalcoder.youyaboot.core.serialize.ResponseMsg;
import com.magicalcoder.youyaboot.core.utils.ListUtil;
import com.magicalcoder.youyaboot.core.utils.MapUtil;
import com.magicalcoder.youyaboot.core.utils.StringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by magicalcoder.com on 2015/9/8.
 * 799374340@qq.com
 */
@RestController
@RequestMapping(value="/admin/common_file_rest/")
public class AdminCommonFileRestController {

    @Resource
    private UploadFilePathConfig uploadFilePathConfig;

    private String originFilePath(String originFile){
        if(StringUtil.isBlank(originFile) || originFile.startsWith("http://") || originFile.startsWith("https://")){
           return "";
        }
        String[] arr = originFile.split("/");
        StringBuilder pathBuilder = new StringBuilder();
        for(int i=0;i<arr.length;i++){
            if(i!=arr.length-1){
                pathBuilder.append(arr[i]).append("/");
            }
        }

        String path = pathBuilder.toString();
        String extraPrefix = uploadFilePathConfig.getFileExtraAddPrefix();

        String prefix = extraPrefix;
        if(StringUtil.isNotBlank(prefix)){
            if(path.startsWith(prefix)){
                path = path.substring(prefix.length());//????????????????????????
            }
        }
        if("/".equals(path)){
            path = "";
        }
        return path;
    }

    /**
     *
     * @param file ?????????????????????
     * @param originFile ??????????????????
     * @param from ????????????????????????  ??????????????????????????????????????????
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public ResponseMsg fileUpload(@RequestParam MultipartFile[] file,
                                  @RequestParam(required = false,value = "originFile") String originFile,
                                  @RequestParam(required = false,value = "from") String from,
                                  HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String originFilePath = originFilePath(originFile);
        String realPath = uploadFilePathConfig.getUploadDiskFolder()+ originFilePath ;
        //???????????????????????????????????????????????? ????????????????????????????????????????????????
        File dirPath = new File(realPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        String originalFilename = null;
        List<Map> returnUrls = new ArrayList<>();
        for (MultipartFile myfile : file) {
            if (!myfile.isEmpty()) {
                // ???????????????
                originalFilename = myfile.getOriginalFilename();
                String returnUrl = "";
                boolean streamUsed = false;
                File storeFile = null;
                // ????????????????????? ??????uuid??????????????????????????? ????????????
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = UUID.randomUUID().toString() + suffix;
                if(uploadFilePathConfig.getUseDisk()){//????????????
                    storeFile = new File(realPath, newFileName);
                    if(!storeFile.getParentFile().exists()){
                        storeFile.getParentFile().mkdirs();
                    }
                    FileUtils.copyInputStreamToFile(
                        myfile.getInputStream(), storeFile);//????????????????????? ??????????????????
                    streamUsed = true;
                    String prefix = uploadFilePathConfig.getFileExtraAddPrefix() +originFilePath;
                    String src = prefix+newFileName;
                    while (src.contains("//")){
                        src = src.replace("//","/");
                    }
                    if(uploadFilePathConfig.getUseDiskReturnUrl()){
                        returnUrl = src;
                    }
                }
                if(uploadFilePathConfig.getUseAliyunOss()){//???????????????
                    if(streamUsed){//????????????????????????
                        uploadFilePathConfig.ossClient().putObject(uploadFilePathConfig.getBucketName(),newFileName,storeFile);
                    }else {//???????????????????????? ?????????????????????????????????
                        uploadFilePathConfig.ossClient().putObject(uploadFilePathConfig.getBucketName(),newFileName,myfile.getInputStream());
                    }
                    if(uploadFilePathConfig.getUseAliyunOssReturnUrl()){
                        returnUrl = uploadFilePathConfig.getAliyunImgDomain()+newFileName;
                    }
                }
                returnUrls.add(MapUtil.buildMap("src",returnUrl,"title",originalFilename));
            }
        }
        if(ListUtil.isNotBlank(returnUrls)){
            if(returnUrls.size()==1){//?????????????????? ??????1?????????
                return new ResponseMsg(returnUrls.get(0));
            }
            //???????????? ??????????????????
            return new ResponseMsg(returnUrls);
        }
        throw new BusinessException(CommonReturnCode.FILE_UPLOAD_NO_FILE);
    }

}
