package com.dylim.hot.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dylim.hot.file.FileVO;

public interface FileUtilService {

	void saveFileMaster(String fileMsterId) throws Exception;

	void saveFile(FileVO file) throws Exception;
	
	String multiFileUpload(List<MultipartFile> multipartFiles) throws Exception;
	
	boolean writeFile(MultipartFile multipartFile, String saveFileName) throws Exception;

	FileVO getImage(String attachFileId) throws Exception;

	List<FileVO> getImages(String attachFileMasterId) throws Exception;

	void deleteImage(String attachFileId) throws Exception;

	String dropZoneUpload(MultipartHttpServletRequest request, String attchFileMasterId) throws Exception;
	
}
