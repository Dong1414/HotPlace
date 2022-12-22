package com.dylim.hot.file.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dylim.hot.file.FileUtilController;
import com.dylim.hot.file.FileVO;
import com.dylim.hot.file.mapper.FileUtilMapper;

@Service
public class FileUtilServiceImpl implements FileUtilService {
	//로컬
	private static final String SAVE_PATH = "/Users/dylim/upload/images/mapImg/";
	private static final String PREFIX_URL = "/Users/dylim/upload/images/mapImg/";
	
	//운영
	//private static final String SAVE_PATH = "/home/ubuntu/upload/images/";
	//private static final String PREFIX_URL = "/home/ubuntu/upload/images/";
	
	private static final Logger log = LoggerFactory.getLogger(FileUtilController.class);
	
	@Autowired
	private FileUtilMapper fileUtilMapper;
	
	
	//드롭존 파일 업로드 로직
	public String dropZoneUpload(MultipartHttpServletRequest request, String attachFileMasterId) throws Exception{
		
		Map<String, MultipartFile> fileMap = request.getFileMap();
		 
		String fileMsterId = attachFileMasterId;
		
		//파일 마스터 아이디가 없는 경우 새로 생성
		if(Strings.isEmpty(attachFileMasterId)){
			fileMsterId = UUID.randomUUID().toString();
			saveFileMaster(fileMsterId);
		}
		
        try {
        	for (MultipartFile file : fileMap.values()) {
	            // 파일 정보
	            String originFilename = file.getOriginalFilename();
	            String extName = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
	            long size = file.getSize();
	
	            // 서버에서 저장 할 파일 이름 ( attachFileId, fildNm );
	            String saveFileName = UUID.randomUUID().toString();            
	            
	
	            FileVO fileInfo = new FileVO();
	            fileInfo.setAttachFileMasterId(fileMsterId);
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
	};
	
	//파일 마스터 아이디 저장
	public void saveFileMaster(String fileMsterId) throws Exception {
		fileUtilMapper.saveFileMaster(fileMsterId);
	}
	
	//파일저장
	public void saveFile(FileVO file) throws Exception {
		fileUtilMapper.saveFile(file);
	};
	
	//멀티파일 업로드
	public String multiFileUpload(List<MultipartFile> multipartFiles) throws Exception {
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
	            fileInfo.setAttachFileMasterId(fileMsterId);
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
	
	//단일파일 업로드
	public String fileUpload(MultipartFile multipartFile, String attachFileMasterId) throws Exception {
		// upload
		
		String fileMsterId = attachFileMasterId;
		System.out.println(attachFileMasterId);
		if(Strings.isEmpty(fileMsterId)){
			fileMsterId = UUID.randomUUID().toString();
			saveFileMaster(fileMsterId);
		}
		
        try {
            	log.debug("Uploading.. {}", " / " + multipartFile.getOriginalFilename());
	            // 파일 정보
	            String originFilename = multipartFile.getOriginalFilename();
	            String extName = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
	            long size = multipartFile.getSize();
	
	            // 서버에서 저장 할 파일 이름 ( attachFileId, fildNm );
	            String saveFileName = UUID.randomUUID().toString();            
	            
	
	            FileVO fileInfo = new FileVO();
	            fileInfo.setAttachFileMasterId(fileMsterId);
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
	            boolean fileSaveYn = writeFile(multipartFile, saveFileName);
	
	            if (fileSaveYn) {
	                throw new Exception("Error on File Writing..");
	            }
	
				writeFile(multipartFile, saveFileName);
            return fileMsterId;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	// 파일을 실제로 write 하는 메서드
    public boolean writeFile(MultipartFile multipartFile, String saveFileName)throws IOException {
    	//private static final String SAVE_PATH = "/asd/qwe/zxc/sdf/qwe/";
    	//String path = SAVE_PATH; //폴더 경로
    	Path path = Paths.get(SAVE_PATH);
    	// 해당 디렉토리가 없다면 디렉토리를 생성.
//    	if (!Folder.exists()) {
//    		try{
//    		    boolean asd = Folder.createDirectories(path); //폴더 생성합니다. ("새폴더"만 생성)
//    		    System.out.println(Folder.toString());
//    		    System.out.println("폴더생성완료." + asd);
//    	        } 
//    	        catch(Exception e){
//    		    e.getStackTrace();
//    		}        
//        }
    	
    	if (Files.notExists(path)) {
            Files.createDirectories(path);
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
    
    public void deleteImage(String attachFileId) throws Exception{
    	fileUtilMapper.deleteImage(attachFileId);
    };
    
    public void fileModify(MultipartFile meltipartFile, String attachFileMasterId) throws Exception{
    	fileUtilMapper.fileModify(meltipartFile, attachFileMasterId);
    };
}

