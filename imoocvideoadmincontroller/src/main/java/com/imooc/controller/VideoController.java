package com.imooc.controller;

import com.imooc.enums.VideoStatusEnum;
import com.imooc.pojo.Bgm;
import com.imooc.service.VideoService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.PagedResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author erpljq
 * @date 2018/10/9
 */
@Controller
@RequestMapping("/video")
public class VideoController {

    @Value("${save-user-video-path}")
    private String fileSpace;

    @Autowired
    private VideoService videoService;

    @GetMapping("/showAddBgm")
    public String showAddBgm(){
        return "center/video/addBgm";
    }

    @GetMapping("/showBgmList")
    public String showBgmList(){
        return "center/video/bgmList";
    }

    @GetMapping("/showReportList")
    public String showReportList(){
        return "center/video/reportList";
    }

    @PostMapping("/reportList")
    @ResponseBody
    public PagedResult reportList(@RequestParam(defaultValue = "1") Integer page){
        PagedResult result = videoService.queryReportList(page, 10);
        return result;
    }

    @PostMapping("/forbidVideo")
    @ResponseBody
    public IMoocJSONResult forbidVideo(String videoId){
        videoService.updateVideoStatus(videoId, VideoStatusEnum.FORBIN.value);
        return IMoocJSONResult.ok();
    }

    @PostMapping("/queryBgmList")
    @ResponseBody
    public PagedResult queryBgmList(@RequestParam(defaultValue = "1") Integer page){
        return videoService.queryBgmList(page, 10);
    }

    @PostMapping("/addBgm")
    @ResponseBody
    public IMoocJSONResult addBgm(Bgm bgm){
        videoService.addBgm(bgm);
        return IMoocJSONResult.ok();
    }

    @PostMapping("/delBgm")
    @ResponseBody
    public IMoocJSONResult delBgm(String bgmId){
        videoService.deleteBgm(bgmId);
        return IMoocJSONResult.ok();
    }

    @ResponseBody
    @PostMapping("/bgmUpload")
    public IMoocJSONResult bgmUpload(@RequestParam("file") MultipartFile[] files) throws Exception{

        // 文件保存的命名空间
        //fileSpace = File.separator + fileSpace + File.separator + "mvc-bgm";
        String fileSpace = this.fileSpace;
        //保存到数据库中的相对路径
        String uploadPathDb = File.separator + "bgm";
        // 声明一个文件输出流
        FileOutputStream fileOutputStream = null;
        // 声明一个文件输入流
        InputStream inputStream = null;
        try {
            //判断上传的文件组不等空, 并且文件组的长度大于0
            if (files != null && files.length > 0) {
                //获取上传的文件名
                String filename = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(filename)){
                    // 文件上传的最终保存路径
                    String finalPath = fileSpace + uploadPathDb + File.separator + filename;
                    //设置数据库保存的路径
                    uploadPathDb += (File.separator + filename);
                    //创建该文件
                    File outFile = new File(finalPath);
                    //判断该文件上一个目录存不存在, 并且上一个文件不等于文件, 如果是文件则创建该目录
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()){
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    // 设置输出到该文件
                    fileOutputStream = new FileOutputStream(outFile);
                    // 在上传的文件中读入到输入流
                    inputStream = files[0].getInputStream();
                    // 使用工具类把输入流传到输出流变为文件
                    IOUtils.copy(inputStream,fileOutputStream);
                }
            } else {
                return IMoocJSONResult.errorMsg("上传出错...");
            }
        }catch (Exception e){
            e.printStackTrace();
            return IMoocJSONResult.errorMsg("上传出错...");
        } finally {
            //关闭流
            if (fileOutputStream != null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }


        return IMoocJSONResult.ok(uploadPathDb);
    }
}
