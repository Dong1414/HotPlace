package com.dylim.hot.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.dylim.hot.file.FileVO;

@Mapper
public interface FileUtilMapper {
	
	void saveFileMaster(String fileMsterId) throws Exception;

	void saveFile(FileVO file) throws Exception;

	FileVO getImage(String attachFileId) throws Exception;

	List<FileVO> getImages(String attachFileMasterId) throws Exception;

	void deleteImage(String attachFileId) throws Exception;

	void fileModify(MultipartFile meltipartFile, String attachFileMasterId) throws Exception;
	
}
