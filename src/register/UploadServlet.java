package register;

import BusinessTier.VerifyOnline;
import MyException.MyException;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import until.Constant;
import uploadServlet.UploadService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //首先判断用户登陆没
        //拿到cookie值
        String tableName;
        System.out.println(request.getCharacterEncoding());
        request.setCharacterEncoding("utf-8");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        VerifyOnline verifyOnline = new VerifyOnline();
        JSONObject json = new JSONObject();
        try {
            verifyOnline.verifyOnlineByCookie(request);
            response.setContentType("text/html;charset=utf-8");
            String rootURI = this.getServletContext().getRealPath("/WEB-INF/files/");
            DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 100, new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload sfu = new ServletFileUpload(factory);
            sfu.setFileSizeMax(1024 * 1024 * 10);
            sfu.setHeaderEncoding("UTF-8");
            List<FileItem> list = sfu.parseRequest(request);
            FileItem fileItem = list.get(0);
            tableName = fileItem.getString("utf-8");
            System.out.println("表格名字：" + tableName);
            FileItem fi = list.get(1);
            String filename = fi.getName();
            System.out.println("文件名字" + filename);
            int index = filename.lastIndexOf("\\");
            if (index != -1) {
                filename = filename.substring(index + 1);
            }
            int hCode = filename.hashCode();
            String hex = Integer.toHexString(hCode);
            File dirFile = new File(rootURI, hex.charAt(0) + "/" + hex.charAt(1));
            dirFile.mkdirs();

            File destFile = new File(dirFile, filename);
            fi.write(destFile);
            UploadService uploadServlet = new UploadService(destFile.getAbsolutePath());
            System.out.println(destFile.getAbsolutePath());
            uploadServlet.saveData(tableName, verifyOnline.getCookie());
            json.put(Constant.CODE, 407);
            json.put(Constant.MESSAGE, "表格上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();

            if (e instanceof MyException) {
                json.put("code", 408);
                json.put("msg", e.getMessage());
            } else {
                json.put("code", 408);
                json.put("msg", "上传文件失败");
            }
        }
        try {
            response.getWriter().print(json.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }


}
