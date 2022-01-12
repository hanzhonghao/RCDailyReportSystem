package com.magicalcoder.youyaboot.admin.api.goods;

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
import com.magicalcoder.youyaboot.model.Goods;
import com.magicalcoder.youyaboot.service.goods.service.GoodsService;

import com.magicalcoder.youyaboot.core.utils.ListUtil;
import com.magicalcoder.youyaboot.core.utils.MapUtil;
import com.magicalcoder.youyaboot.core.utils.StringUtil;


/**
* 代码为自动生成 Created by www.magicalcoder.com
* 软件作者：何栋宇 qq:709876443
* 如果你改变了此类 read 请将此行删除
* 欢迎加入官方QQ群:648595928
*/

@RequestMapping("/admin/goods_rest/")
@RestController
public class AdminGoodsRestController extends CommonRestController<Goods,Long> implements InitializingBean
{

    @Resource
    private GoodsService goodsService;

        //外键下拉查询接口
    @RequestMapping(value = "search")
    public ResponseMsg search(
        @RequestParam(required = false) String uniqueField,
        @RequestParam(required = false,value = "uniqueValue[]") Set<Long> uniqueValue,//主键多个值
        @RequestParam(required = false,defaultValue = "20") Integer limit,
        @RequestParam(required = false) String keyword
    ){
        limit = Math.min(PageConstant.MAX_LIMIT,limit);
        List<Goods> list = null;
        Map<String,Object> query = new HashMap();
        query.put("limit",limit);
        query.put("notSafeOrderBy","create_time desc");
        if(uniqueValue!=null){//说明是来初始化的
            list = goodsService.getModelInList(uniqueValue);
        }else {//正常搜索
            if(ListUtil.isBlank(list)){
                query.put("goodsNameFirst",keyword);
                list = goodsService.getModelList(query);
                query.remove("goodsNameFirst");
            }
        }
        return new ResponseMsg(list);
    }
    //分页查询
    @RequestMapping(value={"page"}, method={RequestMethod.GET})
    public ResponseMsg page(
        @RequestParam(required = false,value ="goodsNameFirst")                            String goodsNameFirst ,
        @RequestParam(required = false,value ="goodsStatusFirst")                            Byte goodsStatusFirst ,
        @RequestParam(required = false,value ="createTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Timestamp createTimeFirst ,
        @RequestParam(required = false,value ="createTimeSecond")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Timestamp createTimeSecond ,
        @RequestParam(required = false,value ="goodsCategoryIdFirst")                            Long goodsCategoryIdFirst ,
        @RequestParam int page,@RequestParam int limit,@RequestParam(required = false) String safeOrderBy
        ,HttpServletResponse response,@RequestParam(required = false) Integer queryType
    ){
        Map<String,Object> query = new HashMap();
        query.put("goodsNameFirst",coverBlankToNull(goodsNameFirst));
        query.put("goodsStatusFirst",goodsStatusFirst);
        query.put("createTimeFirst",createTimeFirst);
        query.put("createTimeSecond",createTimeSecond);
        query.put("goodsCategoryIdFirst",goodsCategoryIdFirst);
        Integer count = goodsService.getModelListCount(query);
        if(StringUtil.isBlank(safeOrderBy)){
            query.put("notSafeOrderBy","create_time desc");
        }else{
            query.put("safeOrderBy",safeOrderBy);
        }
        if(queryType==null || queryType == QUERY_TYPE_SEARCH){//普通查询
            limit = Math.min(limit, PageConstant.MAX_LIMIT);
            query.put("start",(page - 1) * limit);query.put("limit",limit);
            return new ResponseMsg(count,goodsService.getModelList(query));
        }else if(queryType == QUERY_TYPE_EXPORT_EXCEL){
            query.put("start",(page - 1) * limit);query.put("limit",limit);
            exportExcel(response,goodsService.getModelList(query),"商品",
            new String[]{"主键","商品名","是否发布","商品状态","价格","库存","简介","商品描述","创建时间","更新时间","商品图片","商品分类"},
            new String[]{"","","[{\"value\":\"请选择\",\"key\":\"\"},{\"value\":\"禁用\",\"key\":\"0\"},{\"value\":\"启用\",\"key\":\"1\"}]","[{\"value\":\"请选择\",\"key\":\"\"},{\"value\":\"下架\",\"key\":\"0\"},{\"value\":\"上架\",\"key\":\"1\"}]","","","","","","","",""});
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.commonService = goodsService;
        super.primaryKey = "id";//硬编码此实体的主键名称
    }
}
