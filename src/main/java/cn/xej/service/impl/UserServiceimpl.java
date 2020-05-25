package cn.xej.service.impl;

import cn.xej.mapper.UserDao;
import cn.xej.pojo.User;
import cn.xej.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUserId(String userId) {

        return userDao.findByUserId(userId);
    }

    @Override
    public List<String> getRolesIdByUserId(String userId) {
        return userDao.queryRolesIdByUserId(userId);
    }

    /**
     * 图片下载
     * @param response
     */
    @Override
    public void imageDownload(HttpServletResponse response) {
        try {
            File file = ResourceUtils.getFile( "classpath:static/statics/images/2.png");

            if(file.exists()){ //判断文件是否存在
                response.setContentType("image/png");
                response.setCharacterEncoding("UTF-8");
                // response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(file.getName(),"UTF-8"));

                byte[] buffer = new byte[1024];
                FileInputStream fis = null; //文件输入流
                BufferedInputStream bis = null;

                OutputStream os = null; //输出流
                try {
                    os = response.getOutputStream();
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    int i = bis.read(buffer);
                    while(i != -1){
                        os.write(buffer);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    bis.close();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }


}
