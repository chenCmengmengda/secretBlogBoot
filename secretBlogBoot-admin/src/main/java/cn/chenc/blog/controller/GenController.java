package cn.chenc.blog.controller;

import cn.chenc.blog.business.entity.TableInfo;
import cn.chenc.blog.business.service.GenService;
import cn.chenc.blog.file.util.FileUtil;
import cn.chenc.blog.framework.object.ResponseVO;
import cn.chenc.blog.utils.ResultUtil;
import cn.chenc.blog.utils.ZipFileUtils;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/6/7 20:39
 *
 */
@Controller
@RequestMapping("/genController")
public class GenController {

    @Autowired
    private GenService genService;

    @ResponseBody
    @RequestMapping("/list")
    public ResponseVO queryTablePage(int page,int size){
        IPage<TableInfo> iPage=genService.queryTablePage(page,size);
        return ResultUtil.success(iPage);
    }

    @RequestMapping("/gen")
    public String gen(HttpServletRequest request, HttpServletResponse response){
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String dir=System.getProperty("user.dir")+"/code-gen";
        gc.setOutputDir(dir);
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor("陈_C");// 作者

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("111111");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/secretblogboot?useUnicode=true&useSSL=false&characterEncoding=utf8");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(new String[] { "" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[] { "article","article_category","article_label_key" ,"article_label"}); // 需要生成的表

        strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass(null);
        strategy.setSuperMapperClass(null);

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.chenc.blog");
        pc.setController("controller");
        pc.setService("business.service");
        pc.setServiceImpl("business.service.impl");
        pc.setMapper("business.mapper");
        pc.setEntity("business.entity");
        pc.setXml("xml");
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();
        //打包下载
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename=src.zip");
        try {
            ZipFileUtils zip = new ZipFileUtils();
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            String fileName = dir;
            File ff = new File(fileName);
            if(!ff.exists()){
                ff.mkdirs();
            }
            zip.zip(ff,zos,"");
            zos.flush();
            zos.close();
            //删除目录
            FileUtil.del(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
