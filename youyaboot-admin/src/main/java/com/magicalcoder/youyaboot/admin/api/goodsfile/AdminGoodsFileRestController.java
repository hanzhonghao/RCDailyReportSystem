package com.magicalcoder.youyaboot.admin.api.goodsfile;

import com.magicalcoder.youyaboot.core.service.CommonRestController;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import com.magicalcoder.youyaboot.core.common.constant.PageConstant;
import com.magicalcoder.youyaboot.core.common.exception.BusinessException;
import com.magicalcoder.youyaboot.core.serialize.ResponseMsg;
import com.magicalcoder.youyaboot.model.GoodsFile;
import com.magicalcoder.youyaboot.service.goodsfile.service.GoodsFileService;

import com.magicalcoder.youyaboot.core.utils.ListUtil;
import com.magicalcoder.youyaboot.core.utils.MapUtil;
import com.magicalcoder.youyaboot.core.utils.StringUtil;


/**
* 代码为自动生成 Created by www.magicalcoder.com
* 软件作者：何栋宇 qq:709876443
* 如果你改变了此类 read 请将此行删除
* 欢迎加入官方QQ群:648595928
*/

@RequestMapping("/admin/goods_file_rest/")
@RestController
public class AdminGoodsFileRestController extends CommonRestController<GoodsFile,Long> implements InitializingBean
{

    @Resource
    private GoodsFileService goodsFileService;

        //分页查询
    @RequestMapping(value={"page"}, method={RequestMethod.GET})
    public ResponseMsg page(
        @RequestParam(required = false,value ="goodsIdFirst")                            Long goodsIdFirst ,
        @RequestParam int page,@RequestParam int limit,@RequestParam(required = false) String safeOrderBy
        ,HttpServletResponse response,@RequestParam(required = false) Integer queryType
    ){
        Map<String,Object> query = new HashMap();
        query.put("goodsIdFirst",goodsIdFirst);
        Integer count = goodsFileService.getModelListCount(query);
        if(StringUtil.isBlank(safeOrderBy)){
            query.put("notSafeOrderBy","file_id desc");
        }else{
            query.put("safeOrderBy",safeOrderBy);
        }
        if(queryType==null || queryType == QUERY_TYPE_SEARCH){//普通查询
            limit = Math.min(limit, PageConstant.MAX_LIMIT);
            query.put("start",(page - 1) * limit);query.put("limit",limit);
            return new ResponseMsg(count,goodsFileService.getModelList(query));
        }else if(queryType == QUERY_TYPE_EXPORT_EXCEL){
            query.put("start",(page - 1) * limit);query.put("limit",limit);
            exportExcel(response,goodsFileService.getModelList(query),"商品附件",
            new String[]{"主键","文件地址","所属商品"},
            new String[]{"","",""});
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.commonService = goodsFileService;
        super.primaryKey = "fileId";//硬编码此实体的主键名称
    }
}
