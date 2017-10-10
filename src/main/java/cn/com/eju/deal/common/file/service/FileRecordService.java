package cn.com.eju.deal.common.file.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.com.eju.deal.base.service.BaseService;
import cn.com.eju.deal.common.file.dao.FileRecordMapper;
import cn.com.eju.deal.common.file.dto.FileRecordDto;
import cn.com.eju.deal.common.file.model.FileRecord;
import cn.com.eju.deal.core.support.QueryConst;
import cn.com.eju.deal.core.support.ResultData;
import cn.com.eju.deal.core.util.StringUtil;

@Service("fileRecordService")
public class FileRecordService extends BaseService<Object>
{
    
    @Resource(name = "fileRecordMapper")
    private FileRecordMapper fileRecordMapper;
    
    /** 
    * 查询
    * @param id
    * @return
    */
    
    public FileRecord getById(int id)
        throws Exception
    {
        FileRecord fileRecord = fileRecordMapper.getById(id);
        return fileRecord;
    }
    
    /** 
     * 查询-根据relateId
     * @param id
     * @return
     */
    
    public FileRecord getByRelateId(String relateId)
        throws Exception
    {
        FileRecord fileRecord = fileRecordMapper.getByRelateId(relateId);
        return fileRecord;
    }
    
    /** 
     * 查询-list
     * @param queryParam
     * @return
     */
    
    public ResultData<List<FileRecordDto>> queryList(Map<?, ?> param)
        throws Exception
    {
        
        //构建返回
        ResultData<List<FileRecordDto>> resultData = new ResultData<List<FileRecordDto>>();
        
        //查询
        final List<FileRecord> moList = fileRecordMapper.queryList(param);
        
        //转换
        List<FileRecordDto> dtoList = convertData(moList);
        
        resultData.setTotalCount((String)param.get(QueryConst.TOTAL_COUNT));
        
        resultData.setReturnData(dtoList);
        
        return resultData;
    }
    
    /** 
     * 创建
     * @param param
     * @return
     */
    public int create(FileRecord fileRecord)
    {
        int count = fileRecordMapper.create(fileRecord);
        return count;
    }
    
    /** 
     * 更新
     * @param param
     * @return
     */
    
    public int update(FileRecord fileRecord)
        throws Exception
    {
        int count = fileRecordMapper.update(fileRecord);
        return count;
    }
    
    /** 
    * 删除 by fileNo
    * @param id 
    * @param operateId 更新人
    * @return
    */
    
    public int deleteByFileNo(String fileNo)
        throws Exception
    {
        int count = fileRecordMapper.deleteByFileNo(fileNo);
        return count;
    }
    
    /** 
     * 对象转换MO--DTO
     * @param stuList
     * @return List<StudentDto>
     */
    private List<FileRecordDto> convertData(List<FileRecord> moList)
        throws Exception
    {
        List<FileRecordDto> dtoList = new ArrayList<FileRecordDto>();
        
        if (null != moList && !moList.isEmpty())
        {
            FileRecordDto dto = null;
            for (FileRecord mo : moList)
            {
                dto = new FileRecordDto();
                BeanUtils.copyProperties(mo, dto);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
    
    /**
     * 根据关系id和文件类型查询文件记录
     * @param queryMap
     * @return
     * @throws Exception 
     */
    public FileRecord getByRelateIdAndFileTypeCode(String relateId, String fileTypeCode)
        throws Exception
    {
        FileRecord fileRecord = fileRecordMapper.getByRelateIdAndFileTypeCode(relateId, fileTypeCode);
        return fileRecord;
    }
    
    /**
     * 根据关系id和文件类型查询文件记录
     * @param queryMap
     * @return
     * @throws Exception
     */
    public List<FileRecord> getByRelateIdsAndFileTypeCode(String relateId, String fileTypeCode)
        throws Exception
    {
        List<FileRecord> fileRecords = fileRecordMapper.getByRelateIdsAndFileTypeCode(relateId, fileTypeCode);
        return fileRecords;
    }
    
    /**
     * 图片更新
     * @param fileRecord  文件实体
     * @param fileOldNo 文件旧编号
     * @throws Exception
     */
    public void updateFileRecord(FileRecord fileRecord, String fileOldNo)
        throws Exception
    {
        updateFileRecord(fileRecord.getRelateId(),
            fileRecord.getFileNo(),
            fileRecord.getFileTypeCode(),
            fileOldNo,
            fileRecord.getUserCreate(),
            fileRecord.getFileName());
    }
    
    /**
     * 图片更新
     * @param relateId 关系id
     * @param fileNo 文件编号
     * @param fileOldNo 文件旧编号
     * @param fileTypeCode 文件类型
     * @param userId 创建或者更新人
     * @param fileName 文件名称 
     * @throws Exception
     */
    public void updateFileRecord(String relateId, String fileNo, String fileOldNo, String fileTypeCode, Integer userId,
        String fileName)
        throws Exception
    {
        if (StringUtil.isNotEmpty(fileNo) && !fileNo.equals(fileOldNo))
        {
            //如果修改更改了图片,查询当前物业的图片记录
            //如果有该条记录则将条记录更新为无用状态,最后再添加一条可用记录;否则添加一条新的可用条记录
            FileRecord fileRecord = getByRelateIdAndFileTypeCode(relateId, fileTypeCode);
            if (fileRecord != null)
            {
                fileRecord.setDelFlag(true);
                //更新无用状态
                update(fileRecord);
            }
            else
            {
                fileRecord = new FileRecord();
            }
            
            fileRecord.setUserCreate(userId);
            fileRecord.setRelateId(relateId);
            fileRecord.setFileName(fileName);
            fileRecord.setDateCreate(new Date());
            fileRecord.setFileNo(fileNo);
            fileRecord.setFileTypeCode(fileTypeCode);
            //添加一个可用状态记录
            fileRecord.setDelFlag(false);
            create(fileRecord);
        }
    }
}
