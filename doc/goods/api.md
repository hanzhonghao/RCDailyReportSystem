
# goods 商品模块 API
## 1.1 查询商品详情

> **描述**：根据主键查询商品详情。

> **url**：/admin/goods_rest/get/{id}
>
> **method**：GET

> **输入**

| 序号 | 字段名称 | 字段类型 | 字段描述     |
| ---- | -------- | -------- | ------------ |
| 1    | id       | Long     | 主键，必填 |

> **输出**

| 序号     | 字段名称      | 字段类型 | 字段描述                |
| -------- | ------------- | -------- | ----------------------- |
| 1        | flag          | boolean  | 接口调用，成功/失败标识 |
| 2        | code          | int      | 错误码，成功时=0        |
| 3        | desc          | string   | 结果描述                |
| 4        | data          | object   | 商品详情对象            |
|          |               |          |                         |
| 字段解释 |               |          |                         |
| 1 | id              | Long          | 主键 |
| 2 | goodsName              | String          | 商品名 |
| 3 | publishStatus              | String          | 是否发布 |
| 4 | goodsStatus              | Byte          | 商品状态 |
| 5 | price              | BigDecimal          | 价格 |
| 6 | storeCount              | Integer          | 库存 |
| 7 | shortBrief              | String          | 简介 |
| 8 | goodsDescription              | String          | 商品描述 |
| 9 | createTime              | Timestamp          | 创建时间 |
| 10 | updateTime              | Timestamp          | 更新时间 |
| 11 | imgSrc              | String          | 商品图片 |
| 12 | goodsCategoryId              | Long          | 商品分类 |

## 1.2 保存商品详情

> **描述**：保存商品详情。

> **url**：/admin/goods_rest/save
>
> **method**：POST

> **输入**

| 序号 | 字段名称 | 字段类型 | 字段描述     |
| ---- | -------- | -------- | ------------ |
| 1| id  | Long  | 主键 |
| 2| goodsName  | String  | 商品名 |
| 3| publishStatus  | String  | 是否发布 |
| 4| goodsStatus  | Byte  | 商品状态 |
| 5| price  | BigDecimal  | 价格 |
| 6| storeCount  | Integer  | 库存 |
| 7| shortBrief  | String  | 简介 |
| 8| goodsDescription  | String  | 商品描述 |
| 9| createTime  | Timestamp  | 创建时间 |
| 10| updateTime  | Timestamp  | 更新时间 |
| 11| imgSrc  | String  | 商品图片 |
| 12| goodsCategoryId  | Long  | 商品分类 |

> **输出**

| 序号     | 字段名称      | 字段类型 | 字段描述                |
| -------- | ------------- | -------- | ----------------------- |
| 1        | flag          | boolean  | 接口调用，成功/失败标识 |
| 2        | code          | int      | 错误码，成功时=0        |
| 3        | desc          | string   | 结果描述                |

## 1.3 更新商品详情

> **描述**：根据主键更新商品的任意属性值，确保要更新的参数不能为null。

> **url**：/admin/goods_rest/update/{id}
>
> **method**：POST

> **输入**

| 序号 | 字段名称 | 字段类型 | 字段描述     |
| ---- | -------- | -------- | ------------ |
| 1| id  | Long  | 主键 |
| 2| goodsName  | String  | 商品名 |
| 3| publishStatus  | String  | 是否发布 |
| 4| goodsStatus  | Byte  | 商品状态 |
| 5| price  | BigDecimal  | 价格 |
| 6| storeCount  | Integer  | 库存 |
| 7| shortBrief  | String  | 简介 |
| 8| goodsDescription  | String  | 商品描述 |
| 9| createTime  | Timestamp  | 创建时间 |
| 10| updateTime  | Timestamp  | 更新时间 |
| 11| imgSrc  | String  | 商品图片 |
| 12| goodsCategoryId  | Long  | 商品分类 |

> **输出**

| 序号     | 字段名称      | 字段类型 | 字段描述                |
| -------- | ------------- | -------- | ----------------------- |
| 1        | flag          | boolean  | 接口调用，成功/失败标识 |
| 2        | code          | int      | 错误码，成功时=0        |
| 3        | desc          | string   | 结果描述                |

## 1.4 删除一条商品记录

> **描述**：根据主键删除一条商品记录。

> **url**：/admin/goods_rest/delete/{id}
>
> **method**：POST

> **输入**

| 序号 | 字段名称 | 字段类型 | 字段描述     |
| ---- | -------- | -------- | ------------ |
| 1    | id       | Long     | 主键，必填 |

> **输出**

| 序号     | 字段名称      | 字段类型 | 字段描述                |
| -------- | ------------- | -------- | ----------------------- |
| 1        | flag          | boolean  | 接口调用，成功/失败标识 |
| 2        | code          | int      | 错误码，成功时=0        |
| 3        | desc          | string   | 结果描述                |


## 1.5 批量删除多条商品记录

> **描述**：根据多个主键删除多条商品记录。

> **url**：/admin/goods_rest/batch_delete
>
> **method**：POST

> **输入**

| 序号 | 字段名称 | 字段类型 | 字段描述     |
| ---- | -------- | -------- | ------------ |
| 1    | ids[]       | Long     | 主键，必填 |
| 2    | ids[]       | Long     | 主键，必填 |
| ...    | ids[]       | Long     | 主键，必填 |
| n    | ids[]       | Long     | 主键，必填 |

> **输出**

| 序号     | 字段名称      | 字段类型 | 字段描述                |
| -------- | ------------- | -------- | ----------------------- |
| 1        | flag          | boolean  | 接口调用，成功/失败标识 |
| 2        | code          | int      | 错误码，成功时=0        |
| 3        | desc          | string   | 结果描述                |


## 1.6 分页查询商品

> **描述**：分页查询商品。

> **url**：/admin/goods_rest/page
>
> **method**：GET

> **输入**

| 序号 | 字段名称 | 字段类型 | 字段描述     |
| ---- | -------- | -------- | ------------ |
| 1    |page      |int       |第几页 [1,n)   |
| 2    |limit      |int       |每页多少条 [0,100)   |
| 3    |safeOrderBy      |int       | 排序 例如 数据库字段名称 desc或asc   |
                |4|goodsNameFirst|   String   |商品名|
                |5|goodsStatusFirst|   Byte   |商品状态|
                |6|createTimeFirst|   Timestamp   |创建时间|
                |7|createTimeSecond|   Timestamp   |创建时间|
                |8|goodsCategoryIdFirst|   Long   |商品分类|

> **输出**

| 序号     | 字段名称      | 字段类型 | 字段描述                |
| -------- | ------------- | -------- | ----------------------- |
| 1        | flag          | boolean  | 接口调用，成功/失败标识 |
| 2        | code          | int      | 错误码，成功时=0        |
| 3        | desc          | string   | 结果描述                |
| 4        | data          | array   | 商品详情数组对象            |
|          |               |          |                         |
| 字段解释 |               |          |                         |
| 1 | id              | Long          | 主键 |
| 2 | goodsName              | String          | 商品名 |
| 3 | publishStatus              | String          | 是否发布 |
| 4 | goodsStatus              | Byte          | 商品状态 |
| 5 | price              | BigDecimal          | 价格 |
| 6 | storeCount              | Integer          | 库存 |
| 7 | shortBrief              | String          | 简介 |
| 8 | goodsDescription              | String          | 商品描述 |
| 9 | createTime              | Timestamp          | 创建时间 |
| 10 | updateTime              | Timestamp          | 更新时间 |
| 11 | imgSrc              | String          | 商品图片 |
| 12 | goodsCategoryId              | Long          | 商品分类 |
