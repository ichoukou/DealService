package cn.com.eju.deal.common.file.dao;

import org.apache.ibatis.annotations.Param;

import cn.com.eju.deal.common.file.model.FileRecord;
import cn.com.eju.deal.core.dao.IDao;

import java.util.List;

public interface FileRecordMapper extends IDao<FileRecord>
{
    /** 
    * 根据relateId获取FileRecord
    * @param relateId
    * @return
    * @throws Exception
    */
    FileRecord getByRelateId(String relateId)
        throws Exception;
    
    /** 
    * 根据关系RelateId和文件类型FileTypeCode获取FileRecord
    * @param relateId
    * @param fileTypeCode
    * @return
    * @throws Exception
    */
    FileRecord getByRelateIdAndFileTypeCode(@Param("relateId")String relateId, @Param("fileTypeCode")String fileTypeCode)
        throws Exception;
    
    /** 
    * 删除 by fileNo
    * @param fileNo
    * @param operateId
    * @return
    * @throws Exception
    */
    int deleteByFileNo(String fileNo)
        throws Exception;
    
    /** 
     * 删除 by RelateId
     * @return
     * @throws Exception
     */
    int deleteByRelateId(String relateId)
        throws Exception;

    /**
     * 根据关系RelateId和文件类型FileTypeCode获取FileRecord
     * @param relateId
     * @param fileTypeCode
     * @return
     * @throws Exception
     */
    List<FileRecord> getByRelateIdsAndFileTypeCode(@Param("relateId")String relateId, @Param("fileTypeCode")String fileTypeCode);
}