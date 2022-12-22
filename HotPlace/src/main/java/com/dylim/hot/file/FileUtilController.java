package com.dylim.hot.file;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dylim.hot.file.service.FileUtilService;

@Controller
public class FileUtilController {
	
	@Autowired
	private FileUtilService fileUtilService;
	
	//@PostMapping("/file/saveImages.do")
	@PostMapping("/file")
	@ResponseBody
	public FileVO dropZoneUpload(MultipartHttpServletRequest request, String attchFileMasterId) throws Exception {
		FileVO result = new FileVO();
		String fileMasterId = fileUtilService.dropZoneUpload(request, attchFileMasterId);
		result.setAttachFileMasterId(fileMasterId);
		return result;
    }
	
	public String multiFileUpload(List<MultipartFile> multipartFiles) throws Exception {
		return fileUtilService.multiFileUpload(multipartFiles);
    }
	
	//@GetMapping("/file/getImage.do")
	@GetMapping("/file/{attachFileId}")
	public String getImage(@PathVariable String attachFileId, HttpServletResponse response) throws Exception {
		
		if(attachFileId.isEmpty()) {
			return null;
		}
		FileVO file = fileUtilService.getImage(attachFileId);
		
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
	@DeleteMapping("/file/{attachFileId}")
	@ResponseBody
	public String deleteImage(@PathVariable String attachFileId) throws Exception {
		fileUtilService.deleteImage(attachFileId);
        return null;
	}
	
}
