package com.Upload;

import com.entity.Student;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import sun.nio.ch.IOUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
//        toUpload(request,response);
//        toUpload2(request,response);
//        toDownload3(request,response);
        toDownload4(request,response );
    }


    private void toDownload4(HttpServletRequest request, HttpServletResponse response) {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        List<FileItem> list= null;
        Student stu = new Student();
        try {
            list = servletFileUpload.parseRequest(request);
            for (FileItem fi :list){
                if (!fi.isFormField()){
                    UUID uuid = UUID.randomUUID();
                    System.out.println(uuid.toString());
                    String realPath = getServletContext().getRealPath("/Upload");
                    File file = new File(realPath);
                    if (!file.exists()){
                        file.mkdirs();
                    }
                    System.out.println(fi.getFieldName());
                    File file1 = new File(file, uuid.toString() +"_"+ fi.getName());
                    IOUtils.copy(fi.getInputStream(),new FileOutputStream(file1) );
                    stu.setUrl("Upload/"+uuid.toString()+"_"+fi.getName());
                }else {
                    String username = fi.getString();
                    stu.setUsername(username);
                }
            }
            request.setAttribute("stu",stu );
            request.getRequestDispatcher("show.jsp").forward(request,response );
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }


    //保存到本地
    private void toUpload(HttpServletRequest request, HttpServletResponse response) {
        //创建本地磁盘对象
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        //通过磁盘对象创建上传对象
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        //定义一个FileItem的泛型集合
        List<FileItem> FIlist = null;
        try {
            //把所有的上传对象赋值给集合
            FIlist = fileUpload.parseRequest(request);
            //遍历集合
            for (FileItem fi:FIlist){
                //判断上传的文件表单类型是否为普通类型
                if(!fi.isFormField()){
                    //转换为流文件
                    InputStream inputStream = fi.getInputStream();
                    //创建输出流
                    FileOutputStream fileOutputStream = new FileOutputStream("F:\\"+fi.getName());
                    byte[] b= new byte[1024];
                    int len =0;
                    while((len= inputStream.read(b))!=-1){
                        //(字节型数组，偏移量，长度)
                        fileOutputStream.write(b,0,len);
                    }
                    fileOutputStream.close();
                    inputStream.close();
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
    }

    //保存到服务器
    private void toUpload2(HttpServletRequest request, HttpServletResponse response) {
        //创建本地磁盘对象
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        //通过磁盘对象创建上传对象
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        //解决中文乱码问题
        fileUpload.setHeaderEncoding("utf-8");
        //定义一个FileItem的泛型集合
        List<FileItem> FIlist = null;
        try {
            //把所有的上传对象赋值给集合
            FIlist = fileUpload.parseRequest(request);
            //遍历集合
            for (FileItem fi:FIlist){
                //判断上传的文件表单类型是否为普通类型
                if(!fi.isFormField()){
                    String realPath = getServletContext().getRealPath("/Upload");
                    File file = new File(realPath,fi.getName());
//                    if (!file.exists()){
//                        file.mkdirs();
//                    }
//                    File file1 = new File(file,fi.getName());
//                    File[] files = file.listFiles();
//                    for (File fl:files){
//                        if (file1.equals(new File(file,fl.getName()))){
//                            request.setAttribute("msg","文件名已存在");
//                            request.getRequestDispatcher("Register.jsp").forward(request,response);
//                        }else {
//                            IOUtils.copy(fi.getInputStream(), new FileOutputStream(file1));
//                        }
//                    }
                    //判断传入的文件名是否为空，或者未上传
                    if("".equals(fi.getName())||fi.getName()==null){
                        request.setAttribute("msg","请选择文件上传");
                        request.getRequestDispatcher("Register.jsp").forward(request,response);
                        return;
                    }else {
                        new File(realPath).mkdirs();
                        if (file.exists()){
                            request.setAttribute("msg","请重新命名文件");
                            request.getRequestDispatcher("Register.jsp").forward(request,response);
                            return;
                        }else {
                            IOUtils.copy(fi.getInputStream(),new FileOutputStream(file));
                        }
                    }
                }
            }
        } catch (FileUploadException | IOException | ServletException e) {
            e.printStackTrace();
        }
    }
}
