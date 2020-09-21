package com.imooc.controller;
import com.imooc.pojo.Messages;
import com.imooc.service.MessageService;
import enums.MsgStatusEnum;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utils.JSONResult;
import utils.PagedResult;
import java.io.File;
import java.util.Date;

@RestController
@Api(value="新闻业务的接口", tags= {"新闻相关业务的controller"})
@RequestMapping("/message")
public class MessageController extends BasicController {

    @Autowired
    private MessageService msgService;

    @Autowired
    private Sid sid;

    @PostMapping("/addMsg")
    public JSONResult uploadFace(String title, String sourceName, @RequestParam("file") MultipartFile[] file) throws Exception {
        // 保存到数据库中的相对路径
        String id=sid.nextShort();

        String uploadPathDBCommon = File.separator+"msg"+File.separator+id;

        String htmlPathDB="";
        String coverPathDB="";
        String iconPathDB="";

        try {
            if (file!=null&&file.length>0) {

                String htmlFileName = file[0].getOriginalFilename();
                String coverFileName = file[1].getOriginalFilename();
                String iconFileName = file[2].getOriginalFilename();

                if (StringUtils.isNotBlank(htmlFileName)) {
                    // 设置数据库保存的路径
                    htmlPathDB = uploadPathDBCommon+(File.separator + "html"+File.separator+htmlFileName);
                    coverPathDB=uploadPathDBCommon+(File.separator + "cover"+File.separator+coverFileName);
                    iconPathDB=uploadPathDBCommon+(File.separator + "icon"+File.separator+iconFileName);

                    // 文件上传的最终保存路径
                    String finalHtmlPath = FILE_SPACE + htmlPathDB;
                    String finalCoverPath = FILE_SPACE + coverPathDB;
                    String finalIconPath = FILE_SPACE + iconPathDB;

                    saveFile(finalHtmlPath,file[0]);
                    saveFile(finalCoverPath,file[1]);
                    saveFile(finalIconPath,file[2]);

                }

            } else {
                return JSONResult.errorMsg("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.errorMsg("上传出错...");
        }
        Messages msg = new Messages();
        msg.setId(id);
        msg.setPath(htmlPathDB);
        msg.setTitle(title);
        msg.setCoverPath(coverPathDB);
        msg.setCreateTime(new Date());
        msg.setStatus(MsgStatusEnum.SUCCESS.value);
        msg.setSourceIcon(iconPathDB);
        msg.setSourceName(sourceName);
        msgService.addMsg(msg);
        return JSONResult.ok(msg);
    }

    /**
     *
     * @Description: 分页和搜索查询新闻列表
     * isSaveRecord：1 - 需要保存
     * 				 0 - 不需要保存 ，或者为空的时候
     */
    @PostMapping(value="/showAll")
    public JSONResult showAll(@RequestBody Messages msg, Integer isSaveRecord,
                              Integer page, Integer pageSize) throws Exception {

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedResult result = msgService.getAllMsgs(msg, isSaveRecord, page, pageSize);
        return JSONResult.ok(result);
    }
    /**
     *
     * @Description: 分页和搜索查询新闻列表
     * isSaveRecord：1 - 需要保存
     * 				 0 - 不需要保存 ，或者为空的时候
     */
    @PostMapping(value="/getHTML")
    public JSONResult getHTML(String id) throws Exception {
        String dbPath=msgService.getMsgPath(id);
        String finalPath=FILE_SPACE+dbPath+File.separator+"html"+File.separator+"index.html";
        String html=readFile(finalPath);
        return JSONResult.ok(html);
    }

    @PostMapping(value="/hot")
    public JSONResult hot() throws Exception {
        return JSONResult.ok(msgService.getHotwords());
    }
}
