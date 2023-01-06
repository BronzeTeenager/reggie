package top.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.HttpResource;
import top.api.common.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;


    /**
     * 上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        // file是一个临时文件,需要转存到指定路径,否则本次请求完成后临时文件会被删除

        //获取原始文件名称
        String originalFilename = file.getOriginalFilename();
        // 获取格式
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 使用uuid生成文件名,防止文件名重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        //创建一个目录对象,判断该目录是否存在,不存在则创建
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success("ok",fileName);
    }


    /**
     * 下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try ( // JDK7 这里放资源对象，用完会自动close关闭 (出现异常也会关闭)
                //使用缓冲字节输入流包原始字节输入流,读取文件内容
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(basePath + name));

                //获取输出流,通过输出流将问写回浏览器,在浏览器展示图片
                ServletOutputStream outputStream = response.getOutputStream();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        ) {
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, len);
                //刷新
                bufferedOutputStream.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
