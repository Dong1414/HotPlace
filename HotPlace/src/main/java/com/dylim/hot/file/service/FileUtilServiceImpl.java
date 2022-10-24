package com.dylim.hot.file.service;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dylim.hot.file.FileUtilController;
import com.dylim.hot.file.FileVO;
import com.dylim.hot.file.mapper.FileUtilMapper;

@Service
public class FileUtilServiceImpl implements FileUtilService {
	//로컬
	private static final String SAVE_PATH = "/Users/dylim/upload/images/mapImg/";
	private static final String PREFIX_URL = "/Users/dylim/upload/images/mapImg/";
	
	private static final Logger log = LoggerFactory.getLogger(FileUtilController.class);
	
	@Autowired
	private FileUtilMapper fileUtilMapper;
	
	public void saveFileMaster(String fileMsterId) throws Exception {
		fileUtilMapper.saveFileMaster(fileMsterId);
	}
	
	public void saveFile(FileVO file) throws Exception {
		fileUtilMapper.saveFile(file);
	};
	
	public String multiFileUpload(List<MultipartFile> multipartFiles) throws Exception {
		System.out.println("bbbb");
		// upload
        String fileMsterId = UUID.randomUUID().toString();
        try {
        	// 파일마스터 저장
            saveFileMaster(fileMsterId);
            for(MultipartFile file : multipartFiles) {
            	log.debug("Uploading.. {}", " / " + file.getOriginalFilename());
	            // 파일 정보
	            String originFilename = file.getOriginalFilename();
	            String extName = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
	            long size = file.getSize();
	
	            // 서버에서 저장 할 파일 이름 ( attachFileId, fildNm );
	            String saveFileName = UUID.randomUUID().toString();            
	            
	
	            FileVO fileInfo = new FileVO();
	            fileInfo.setAttachFileMaster(fileMsterId);
	            fileInfo.setAttachFileId(saveFileName);
	            fileInfo.setOriginFileName(originFilename);
	            fileInfo.setSaveFileName(saveFileName);
	            fileInfo.setFileSize(size);
	            fileInfo.setFileExt(extName);
	            fileInfo.setSavePath(SAVE_PATH + saveFileName);
	            fileInfo.setPrefixPath(PREFIX_URL + saveFileName);
	            
	            // 파일저장
	            saveFile(fileInfo);
	            // 실제 파일 저장
	            boolean fileSaveYn = writeFile(file, saveFileName);
	
	            if (fileSaveYn) {
	                throw new Exception("Error on File Writing..");
	            }
	
				writeFile(file, saveFileName);
            }
            return fileMsterId;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	// 파일을 실제로 write 하는 메서드
    public boolean writeFile(MultipartFile multipartFile, String saveFileName)throws IOException {
    	
    	String path = SAVE_PATH; //폴더 경로
    	File Folder = new File(path);

    	// 해당 디렉토리가 없다면 디렉토리를 생성.
    	if (!Folder.exists()) {
    		try{
    		    Folder.mkdir(); //폴더 생성합니다. ("새폴더"만 생성)
    		    System.out.println("폴더생성완료.");
    	        } 
    	        catch(Exception e){
    		    e.getStackTrace();
    		}        
        }
        boolean result = false;
        
        byte[] data = multipartFile.getBytes();
        FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
        fos.write(data);
        fos.close();

        return result;
    }
    
    public FileVO getImage(String attachFileId) throws Exception{
    	return fileUtilMapper.getImage(attachFileId); 
    };
    
    public List<FileVO> getImages(String attachFileMasterId) throws Exception{
    	return fileUtilMapper.getImages(attachFileMasterId);
    };
}
