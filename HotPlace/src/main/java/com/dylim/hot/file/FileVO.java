package com.dylim.hot.file;

public class FileVO {
    String attachFileId;     
    String attachFileMaster;     
    String saveFileName;
    String originFileName;
    String fileExt;
    long fileSize;	
    String registId;
    String registDt;
    String updateId;    
    String updateDt;
    String savePath;
    String prefixPath;
    
	public String getAttachFileId() {
		return attachFileId;
	}
	public void setAttachFileId(String attachFileId) {
		this.attachFileId = attachFileId;
	}
	public String getAttachFileMaster() {
		return attachFileMaster;
	}
	public void setAttachFileMaster(String attachFileMaster) {
		this.attachFileMaster = attachFileMaster;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getOriginFileName() {
		return originFileName;
	}
	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public String getRegistId() {
		return registId;
	}
	public void setRegistId(String registId) {
		this.registId = registId;
	}
	public String getRegistDt() {
		return registDt;
	}
	public void setRegistDt(String registDt) {
		this.registDt = registDt;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public String getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	public String getPrefixPath() {
		return prefixPath;
	}
	public void setPrefixPath(String prefixPath) {
		this.prefixPath = prefixPath;
	}
	@Override
	public String toString() {
		return "FileVO [attachFileId=" + attachFileId + ", attachFileMaster=" + attachFileMaster + ", saveFileName="
				+ saveFileName + ", originFileName=" + originFileName + ", fileExt=" + fileExt + ", fileSize="
				+ fileSize + ", registId=" + registId + ", registDt=" + registDt + ", updateId=" + updateId
				+ ", updateDt=" + updateDt + "]";
	}
    
}