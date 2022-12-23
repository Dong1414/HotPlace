package com.dylim.hot.file;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dylim.hot.SessionConstants;
import com.dylim.hot.file.service.FileUtilService;
import com.dylim.hot.member.MemberVO;

@Controller
public class FileUtilController {
	
	@Autowired
	private FileUtilService fileUtilService;
	
	//@PostMapping("/file/saveImages.do")
	@PostMapping("/file")
	@ResponseBody
	public FileVO dropZoneUpload(MultipartHttpServletRequest request, String attchFileMasterId, 
			@SessionAttribute(name = SessionConstants.LOGIN_MEMBER_ID, required = false)String loginMemberId) throws Exception {
		FileVO result = new FileVO();
		String fileMasterId = fileUtilService.dropZoneUpload(request, attchFileMasterId, loginMemberId);
		result.setAttachFileMasterId(fileMasterId);
		return result;
    }
	
	public String multiFileUpload(List<MultipartFile> multipartFiles,
			@SessionAttribute(name = SessionConstants.LOGIN_MEMBER_ID, required = false)String loginMemberId) throws Exception {
		return fileUtilService.multiFileUpload(multipartFiles, loginMemberId);
    }
	
	//@GetMapping("/file/getImage.do")
	@GetMapping("/file")
	public String getImage(@RequestParam String attachFileId, HttpServletResponse response) throws Exception {
		
		if(StringUtils.isEmpty(attachFileId)) {
			return null;
		}
		FileVO file = fileUtilService.getImage(attachFileId);
		
		if(file == null) {
			return null;
		}
		
		response.setContentType("image/jpg");
        ServletOutputStream bout = response.getOutputStream();

        //파일의 경로
        String imgpath = file.getPrefixPath();
        
        FileInputStream f = new FileInputStream(imgpath);
        int length;
        byte[] buffer = new byte[10];
        while((length=f.read(buffer)) != -1){
        	bout.write(buffer,0,length);
        }
        return null;
	}
	
	//@PostMapping("/file/deleteImage.do")
	@DeleteMapping("/file")
	@ResponseBody
	public String deleteImage(@RequestParam String attachFileId,
			@SessionAttribute(name = SessionConstants.LOGIN_MEMBER_ID, required = false)String loginMemberId) throws Exception {
		
		if(StringUtils.isEmpty(attachFileId)) {
			return null;
		}
		
		fileUtilService.deleteImage(attachFileId, loginMemberId);
        return null;
	}
	
}
