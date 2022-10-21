package com.dylim.hot.file.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dylim.hot.file.FileVO;

@Mapper
public interface FileUtilMapper {
	
	void saveFileMaster(String fileMsterId) throws Exception;

	void saveFile(FileVO file) throws Exception;

	FileVO getImage(String attachFileId) throws Exception;
}
