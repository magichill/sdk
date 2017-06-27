package com.bingdou.oss.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.oss.constant.AliyunConstant;
import com.bingdou.tools.JsonUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static com.bingdou.oss.constant.AliyunConstant.*;


/**
 * Created by gaoshan on 17/6/10.
 */
@Controller
public class UploadController {

    private static String UPLOADED_FOLDER = "/tmp/upload/";

    @RequestMapping("/upload_index")
    public String index() {
        return "upload";
    }

    @RequestMapping(value = "/upload_img", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult home(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = System.currentTimeMillis()+suffix;
            String uploadFile = UPLOADED_FOLDER + fileName;
            Path path = Paths.get(uploadFile);
            Files.write(path, bytes);
            aliyunService(uploadFile,fileName);
            String imgUrl = AliyunConstant.domain+fileName;
            Map result = Maps.newHashMap();
            result.put("imgUrl",imgUrl);
            return ServiceResultUtil.success(JsonUtil.bean2JsonTree(result));
        } catch (IOException e) {
            e.printStackTrace();
            return ServiceResultUtil.illegal("fail");
        }
    }

    private boolean aliyunService(String uploadFile,String fileName) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try{
            // 上传文件
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, new File(uploadFile));
            return true;
        }catch(ClientException ce){
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
            return false;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        } finally {
            ossClient.shutdown();
        }
    }
}
