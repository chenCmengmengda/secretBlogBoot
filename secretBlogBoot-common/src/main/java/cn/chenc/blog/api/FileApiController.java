package cn.chenc.blog.api;


import cn.chenc.blog.business.enums.ConfigKeyEnum;
import cn.chenc.blog.business.enums.ConfigTypeEnum;
import cn.chenc.blog.business.plugin.file.GlobalFileUploader;
import cn.chenc.blog.business.service.SysConfigService;
import cn.chenc.blog.file.model.FileUploadPath;
import cn.chenc.blog.file.response.FileResponse;
import cn.chenc.blog.file.response.FileUploadResponse;
import cn.chenc.blog.file.uploader.FileUploader;
import cn.chenc.blog.file.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件服务器
 */
@Slf4j
@Controller
@RequestMapping("/api/file")
public class FileApiController {
    @Autowired
    protected HttpServletRequest request;
    Boolean useNginx,useSm;
    String rootPath,nginxUrl,serverUrl;
    @Autowired
    SysConfigService configService;
    // 首页
    /*@GetMapping({"/", "/index"})
    public String index() {
        return "index.html";
    }*/
    private Map<String,String> configMap = new HashMap<>();
//    @Autowired
//    FileService fileService;

    @PostConstruct
    public void init(){
        configMap = configService.getConfigs(ConfigTypeEnum.UPLOAD.getType());

        this.useNginx = configMap.get(ConfigKeyEnum.USE_NGINX.getKey()).equals("1")?true:false;
        this.useSm = configMap.get(ConfigKeyEnum.USE_SM.getKey()).equals("1")?true:false;
        this.nginxUrl = configMap.get(ConfigKeyEnum.NGINX_URL.getKey());
        this.serverUrl = configMap.get(ConfigKeyEnum.SERVER_URL.getKey());
        this.rootPath = configMap.get(ConfigKeyEnum.ROOT_PATH.getKey());
    }
    /**
     * 上传文件
     */
    @ResponseBody
    @PostMapping("/upload")
    public Object upload(@RequestParam MultipartFile file){
        //String storageType = request.getParameter("storageType")==null?"local":request.getParameter("storageType");
        String dir = request.getParameter("dir");
        //uploadType = uploadType.startsWith("/")?uploadType.substring(1):uploadType;
        FileUploader uploader = new GlobalFileUploader();
        FileUploadResponse response = uploader.upload(file, dir);
        return response;
    }
    /**
     * 查看原文件
     */
    @GetMapping("/{type}/{file:.+}")
    public String file(@PathVariable("type") String type,@PathVariable("file") String filename, HttpServletResponse response) {
        String filePath = type+"/"+ filename;
        if (useNginx) {
            return redirectNginxFile(filePath,nginxUrl);
        }
        outputFile(this.rootPath + filePath, response);
        return null;
    }

    /**
     * 查看缩略图
     */
    @GetMapping("/sm/{type}/{file:.+}")
    public String fileSm(@PathVariable("type") String type, @PathVariable("file") String filename, HttpServletResponse response) {
        String filePath = "sm/"+ type +"/"+ filename;
        if (useNginx) {
            return redirectNginxFile(filePath,nginxUrl);
        }
        outputFile(rootPath + filePath, response);
        return null;
    }

    private String redirectNginxFile(String filePath,String nginxUrl){
        if (nginxUrl == null) {
            nginxUrl = "/";
        }
        if (!nginxUrl.endsWith("/")) {
            nginxUrl += "/";
        }
        String newName;
        try {
            newName = URLEncoder.encode(filePath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            newName = filePath;
        }
        return "redirect:" + nginxUrl + newName;
    }
    // 输出文件流
    private void outputFile(String file, HttpServletResponse response) {
        // 判断文件是否存在
        File inFile = new File(file);
        if (!inFile.exists()) {
            PrintWriter writer = null;
            try {
                response.setContentType("text/html;charset=UTF-8");
                writer = response.getWriter();
                writer.write("<!doctype html><title>404 Not Found</title><h1 style=\"text-align: center\">404 Not Found</h1><hr/><p style=\"text-align: center\">Javabb File Server</p>");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        // 获取文件类型
        String contentType = null;
        try {
            // Path path = Paths.get(inFile.getName());
            // contentType = Files.probeContentType(path);
            contentType = new Tika().detect(inFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (contentType != null) {
            response.setContentType(contentType);
        } else {
            response.setContentType("application/force-download");
            String newName;
            try {
                newName = URLEncoder.encode(inFile.getName(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                newName = inFile.getName();
            }
            response.setHeader("Content-Disposition", "attachment;fileName=" + newName);
        }
        // 输出文件流
        OutputStream os = null;
        FileInputStream is = null;
        try {
            is = new FileInputStream(inFile);
            os = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取全部文件
     */
    @ResponseBody
    @GetMapping("/list")
    public Object list(String dir, String accept, String exts) {
        FileUploader uploader = new GlobalFileUploader();
        FileResponse response = uploader.listFiles(dir,accept,exts, FileUploadPath.COMMEN.getPath());
        return response;
    }

    /**
     * @description: 创建目录
     * @param [folderName]
     * @return java.lang.Object
     * @throws
     * @author 陈_C
     * @date 2020/3/28 陈_C
     */
    @ResponseBody
    @GetMapping("/newFolder")
    public Object newFolder(String folderName){
        if(folderName !=null && folderName !=""){
            folderName = this.rootPath.endsWith("/")?this.rootPath+folderName:this.rootPath+"/"+folderName;
            System.out.println(folderName);
            FileUtil.mkdir(folderName);
            return new FileResponse(200,"新建成功").setData(folderName);
        }
        return new FileResponse(500,"创建文件夹失败");
    }
    /**
     * 删除
     */
    @ResponseBody
    @GetMapping("/del")
    public Object del(String file) {
        //configProperties.setStorageType("local");
        String dir = request.getParameter("dir");
        if (file != null && !file.isEmpty()) {
            FileUploader uploader = new GlobalFileUploader();
            if(uploader.removeFile(file)){
                return new FileResponse(200,"删除成功");
            }
        }
        return new FileResponse(500,"删除失败");
    }

    @ResponseBody
    @GetMapping("/delDir")
    public Object delDir(String file) {
        //configProperties.setStorageType("local");
        String dir = request.getParameter("dir");
        if (file != null && !file.isEmpty()) {
            FileUploader uploader = new GlobalFileUploader();
            if(uploader.removeDir(file)){
                return new FileResponse(200,"删除成功");
            }
        }
        return new FileResponse(500,"删除失败");
    }

    /**
     * @description: 文件重命名
     * @param [path, oldname, newname]
     * @return java.lang.Object
     * @throws
     * @author 陈_C
     * @date 2020/3/29 陈_C
     */
    @ResponseBody
    @GetMapping("/rename")
    public Object rename(String path, String oldname, String newname){
        FileUploader uploader = new GlobalFileUploader();
        FileResponse fileResponse = uploader.rename(path,oldname,newname);
        return fileResponse;
    }

}
