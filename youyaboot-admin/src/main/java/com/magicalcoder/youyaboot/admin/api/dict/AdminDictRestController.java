package com.magicalcoder.youyaboot.admin.api.dict;

import com.magicalcoder.youyaboot.core.service.CommonRestController;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.math.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import com.magicalcoder.youyaboot.core.common.constant.PageConstant;
import com.magicalcoder.youyaboot.core.common.exception.BusinessException;
import com.magicalcoder.youyaboot.core.serialize.ResponseMsg;
import com.magicalcoder.youyaboot.model.Dict;
import com.magicalcoder.youyaboot.service.dict.service.DictService;

import com.magicalcoder.youyaboot.core.utils.ListUtil;
import com.magicalcoder.youyaboot.core.utils.MapUtil;
import com.magicalcoder.youyaboot.core.utils.StringUtil;


/**
* 代码为自动生成 Created by www.magicalcoder.com
* 软件作者：何栋宇 qq:709876443
* 如果你改变了此类 read 请将此行删除
* 欢迎加入官方QQ群:648595928
*/

@RequestMapping("/admin/dict_rest/")
@RestController
public class AdminDictRestController extends CommonRestController<Dict,Long> implements InitializingBean
{

    @Resource
    private DictService dictService;

        //外键下拉查询接口
    @RequestMapping(value = "search")
    public ResponseMsg search(
        @RequestParam(required = false) String uniqueField,
        @RequestParam(required = false,value = "uniqueValue[]") Set<Long> uniqueValue,//主键多个值
        @RequestParam(required = false,defaultValue = "20") Integer limit,
        @RequestParam(required = false) String keyword
    ){
        limit = Math.min(PageConstant.MAX_LIMIT,limit);
        List<Dict> list = null;
        Map<String,Object> query = new HashMap();
        query.put("limit",limit);
        query.put("notSafeOrderBy","id desc,order_no asc");
        if(uniqueValue!=null){//说明是来初始化的
            list = dictService.getModelInList(uniqueValue);
        }else {//正常搜索
            if(ListUtil.isBlank(list)){
                query.put("codeFirst",keyword);
                list = dictService.getModelList(query);
                query.remove("codeFirst");
            }
        }
        return new ResponseMsg(list);
    }
    //分页查询
    @RequestMapping(value={"page"}, method={RequestMethod.GET})
    public ResponseMsg page(
        @RequestParam(required = false,value ="idFirst")                            Long idFirst ,
        @RequestParam(required = false,value ="dictCategoryFirst")                            String dictCategoryFirst ,
        @RequestParam(required = false,value ="codeFirst")                            String codeFirst ,
        @RequestParam(required = false,value ="parentIdFirst")                            Long parentIdFirst ,
        @RequestParam int page,@RequestParam int limit,@RequestParam(required = false) String safeOrderBy
        ,HttpServletResponse response,@RequestParam(required = false) Integer queryType
    ){
        Map<String,Object> query = new HashMap();
        query.put("idFirst",idFirst);
        query.put("dictCategoryFirst",coverBlankToNull(dictCategoryFirst));
        query.put("codeFirst",coverBlankToNull(codeFirst));
        query.put("parentIdFirst",parentIdFirst);
        Integer count = dictService.getModelListCount(query);
        if(StringUtil.isBlank(safeOrderBy)){
            query.put("notSafeOrderBy","id desc,order_no asc");
        }else{
            query.put("safeOrderBy",safeOrderBy);
        }
        if(queryType==null || queryType == QUERY_TYPE_SEARCH){//普通查询
            limit = Math.min(limit, PageConstant.MAX_LIMIT);
            query.put("start",(page - 1) * limit);query.put("limit",limit);
            return new ResponseMsg(count,dictService.getModelList(query));
        }else if(queryType == QUERY_TYPE_EXPORT_EXCEL){
            query.put("start",(page - 1) * limit);query.put("limit",limit);
            exportExcel(response,dictService.getModelList(query),"字典",
            new String[]{"主键","字典大类","编码","名称","描述","所属父类","创建时间","更新时间","序号"},
            new String[]{"","","","","","","","",""});
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.commonService = dictService;
        super.primaryKey = "id";//硬编码此实体的主键名称
    }
}
