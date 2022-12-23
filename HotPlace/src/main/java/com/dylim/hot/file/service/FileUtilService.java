package com.dylim.hot.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dylim.hot.file.FileVO;

public interface FileUtilService {

	void saveFileMaster(String fileMsterId, String loginMemberId) throws Exception;

	void saveFile(FileVO file, String loginMemberId) throws Exception;
	
	String multiFileUpload(List<MultipartFile> multipartFiles, String loginMemberId) throws Exception;
	
	String fileUpload(MultipartFile multipartFile, String attachFileMasterId, String loginMemberId) throws Exception;
	
	boolean writeFile(MultipartFile multipartFile, String saveFileName) throws Exception;

	FileVO getImage(String attachFileId) throws Exception;

	List<FileVO> getImages(String attachFileMasterId) throws Exception;

	void deleteImage(String attachFileId, String loginMemberId) throws Exception;

	String dropZoneUpload(MultipartHttpServletRequest request, String attachFileMasterId, String loginMemberId) throws Exception;

}
