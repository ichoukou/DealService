package cn.com.eju.deal.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.eju.deal.base.controller.BaseController;
import cn.com.eju.deal.base.helper.LogHelper;
import cn.com.eju.deal.common.jms.rabbitmq.producer.Product;
import cn.com.eju.deal.core.support.ResultData;
import cn.com.eju.deal.core.util.JsonUtil;
import cn.com.eju.deal.dto.student.StudentDto;
import cn.com.eju.deal.student.model.Student;
import cn.com.eju.deal.student.service.StudentService;

/**
 * 服务层
 *
 * @author (li_xiaodong)
 * @date 2016年1月19日 下午6:05:44
 */

@RestController
@RequestMapping(value = "students")
public class StudentController extends BaseController
{
    
    /**
     * 日志
     */
    private final LogHelper logger = LogHelper.getLogger(this.getClass());
    
    @Resource(name = "studentService")
    private StudentService studentService;
    
    @Autowired
    private Product producer;
    
    /**
     * 查询-对象
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable int id)
    {
        //构建返回
        ResultData<StudentDto> resultData = new ResultData<StudentDto>();
        
        // 获取远程服务代理
//        IDemoService demoService = (IDemoService)SpringConfigHelper.getBean("demoService");
//        
//        String hello = demoService.sayHello("world"); // 执行远程方法
        //System.out.println(hello);
        
        try
        {
            String queueId = "test-mq";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data11", "hello rabbitmq 22");
            producer.sendQueue(queueId + "_exchange", queueId + "_patt", map);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        //查询
        try
        {
            Student mo = studentService.getById(id);
            
            //Model转换Dto
            StudentDto dto = new StudentDto();
            
            BeanUtils.copyProperties(mo, dto);
            
            resultData.setReturnData(dto);
            
        }
        catch (Exception e)
        {
            logger.error("Student", "StudentController", "getById", "", 0, "", "", e);
            
            resultData.setFail();
        }
        
        return resultData.toString();
    }
    
    /**
     * 查询-list
     *
     * @param param 查询条件
     * @return
     */
    @RequestMapping(value = "/q/{param}", method = RequestMethod.GET)
    public String list(@PathVariable String param)
    {
        //构建返回
        ResultData<List<StudentDto>> resultData = new ResultData<List<StudentDto>>();
        
        try
        {
            Map<?, ?> queryParam = JsonUtil.parseToObject(param, Map.class);
            
            resultData = studentService.queryList(queryParam);
        }
        catch (Exception e)
        {
            logger.error("Student", "StudentController", "list", "", 0, "", "", e);
            
            resultData.setFail();
        }
        
        return resultData.toString();
    }
    
    /**
     * 创建
     *
     * @param jsonDto 对象字符串
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(@RequestBody String jsonDto)
    {
        
        //构建返回
        ResultData<StudentDto> resultData = new ResultData<StudentDto>();
        
        try
        {
            StudentDto dto = JsonUtil.parseToObject(jsonDto, StudentDto.class);
            
            Student mo = new Student();
            
            //赋值
            BeanUtils.copyProperties(dto, mo);
            
            int count = studentService.create(mo);
            if (count <= 0)
            {
                resultData.setFail();
            }
        }
        catch (Exception e)
        {
            logger.error("Student", "StudentController", "create", "", 0, "", "", e);
            
            resultData.setFail();
        }
        
        return resultData.toString();
    }
    
    /**
     * 更新
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String update(@RequestBody String studentDtoJson)
    {
        
        //构建返回
        ResultData<StudentDto> resultData = new ResultData<StudentDto>();
        
        try
        {
            StudentDto dto = JsonUtil.parseToObject(studentDtoJson, StudentDto.class);
            
            Student mo = new Student();
            
            //赋值
            BeanUtils.copyProperties(dto, mo);
            
            int count = studentService.update(mo);
            if (count <= 0)
            {
                resultData.setFail();
            }
        }
        catch (Exception e)
        {
            logger.error("Student", "StudentController", "update", "", 0, "", "", e);
            
            resultData.setFail();
        }
        
        return resultData.toString();
    }
    
    /**
     * 删除
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/{id}/{updateId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id, @PathVariable int updateId)
    {
        //构建返回
        ResultData<StudentDto> resultData = new ResultData<StudentDto>();
        
        try
        {
            studentService.delete(id, updateId);
        }
        catch (Exception e)
        {
            
            logger.error("Student", "StudentController", "delete", "", 0, "", "", e);
            
            resultData.setFail();
        }
        
        return resultData.toString();
    }
    
}
