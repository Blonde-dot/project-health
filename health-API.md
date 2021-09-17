## /health-API
```text
健康预约体检系统 接口文档
```
#### 
## /health-API/登录
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/login.do

#### 请求方式
> POST

#### Content-Type
> application/x-www-form-urlencoded

#### 预执行脚本
```javascript
apt.setRequestBody("username", 'admin');
apt.setRequestBody("password", 'admin');
```
#### 
## /health-API/登出账户
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/logout.do

#### 请求方式
> GET

#### Content-Type
> application/x-www-form-urlencoded





## /health-API/查询平台概览信息
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/report/getBasicData.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 成功响应示例
```javascript
{
	"data": {
		"totalDoctor": 2,
		"todayOrder": 1,
		"todayMember": 0,
		"todayArrive": 0,
		"todayVisit": 12,
		"totalOrder": 8,
		"totalUser": 1,
		"totalMember": 9
	},
	"flag": true,
	"message": "系统基本数据信息查询成功"
}
```
## /health-API/health-member-API
```text
会员模块接口
```


## /health-API/health-member-API/分页查询-会员列表
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/member/findPage.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"currentPage":1,"pageSize":10}
```
#### 成功响应示例
```javascript
{"rows":[{"birthday":"2000-08-01","email":"1201589745@qq.com","fileNumber":null,"height":"170","id":8,"idCard":"123456789012345","img":null,"name":"大王","password":null,"phoneNumber":"13513131313","regTime":"2021-08-12","remark":null,"sex":"1","weight":"60"},{"birthday":"1988-01-11","email":null,"fileNumber":null,"height":"165","id":9,"idCard":"123456789456123123","img":null,"name":"小红","password":null,"phoneNumber":"13713111111","regTime":"2021-08-08","remark":null,"sex":"2","weight":"50"},{"birthday":"1995-06-12","email":null,"fileNumber":null,"height":"175","id":10,"idCard":"789456123012345123","img":null,"name":"小蓝","password":null,"phoneNumber":"13713112211","regTime":"2021-08-08","remark":null,"sex":"2","weight":"55"},{"birthday":"2005-11-21","email":null,"fileNumber":null,"height":"182","id":11,"idCard":"222222222222222123","img":null,"name":"小绿","password":null,"phoneNumber":"13753535353","regTime":"2021-08-09","remark":null,"sex":"1","weight":"70"},{"birthday":"2010-12-01","email":null,"fileNumber":null,"height":null,"id":1001,"idCard":"111111111111111132","img":null,"name":"test","password":null,"phoneNumber":"13587964654","regTime":"2021-07-16","remark":null,"sex":"1","weight":null},{"birthday":null,"email":null,"fileNumber":null,"height":null,"id":1002,"idCard":"112311112222021548","img":null,"name":"测试会员2","password":null,"phoneNumber":"12365478989","regTime":"2021-07-03","remark":null,"sex":"2","weight":null},{"birthday":"2011-05-11","email":null,"fileNumber":null,"height":null,"id":10034,"idCard":"333333333333333333","img":null,"name":"测试会员","password":null,"phoneNumber":"12345678989","regTime":"2021-05-11","remark":null,"sex":"1","weight":null},{"birthday":"2020-06-14","email":"1257879564@qq.com","fileNumber":null,"height":"175","id":10036,"idCard":"123456789124568123","img":"0d00e8f3-584e-4b84-9bf0-99e5e784d5101.jpg","name":"测试会员","password":"$2a$10$mMJruSW.zIEqkmNX5XtS..lPDnsp71Zav2oyf4DrHnlMMrRbqZ7Je","phoneNumber":"13513846826","regTime":"2021-08-17","remark":null,"sex":"2","weight":"52"},{"birthday":"2006-09-03","email":"1234557896@qq.com","fileNumber":null,"height":"182","id":10037,"idCard":"441411122222222222","img":"cb5b0131-ad4a-4fe9-8020-bbc7734cfe6al.jpg","name":"测试会员3","password":"$2a$10$b48dB/9c43U46tMAs0IjUOtbpzTx2Xflt/DGv.ogZV78Ika0N.tg.","phoneNumber":"13511112222","regTime":"2021-09-14","remark":null,"sex":"2","weight":"68"}],"total":9}
```
## /health-API/health-member-API/条件查询-会员列表
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/member/findPage.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"currentPage":1,"pageSize":10,"queryString":"大王"}
```
#### 成功响应示例
```javascript
{"rows":[{"birthday":"2000-08-01","email":"1201589745@qq.com","fileNumber":null,"height":"170","id":8,"idCard":"123456789012345","img":null,"name":"大王","password":null,"phoneNumber":"13513131313","regTime":"2021-08-12","remark":null,"sex":"1","weight":"60"}],"total":1}
```
## /health-API/health-member-API/新增会员
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/member/add.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"name":"测试用例","phoneNumber":"13588889999","sex":"1","birthday":"1999-12-5","regTime":"2021-03-16"}
```
#### 成功响应示例
```javascript
{"data":null,"flag":true,"message":"新增会员成功"}
```
## /health-API/health-member-API/编辑会员
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/member/edit.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"birthday":"2000-08-01","email":"1201589745@qq.com","fileNumber":null,"height":"170","id":8,"idCard":"123456789012345","img":null,"name":"大王","password":null,"phoneNumber":"13513131313","regTime":"2021-08-13","remark":null,"sex":"1","weight":"60"}
```
#### 成功响应示例
```javascript
{"data":null,"flag":true,"message":"编辑会员成功"}
```
## /health-API/health-member-API/删除会员
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/member/delete.do?id=1002

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1002 | Text | 是 | -
||||
#### 成功响应示例
```javascript
{"data":null,"flag":true,"message":"删除会员成功"}
```
## /health-API/health-member-API/查询会员基本信息
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/member/findById.do?id=8

#### 请求方式
> GET

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 8 | Text | 是 | -
||||
#### 成功响应示例
```javascript
{"data":{"birthday":"2000-08-01","email":"1201589745@qq.com","fileNumber":null,"height":"170","id":8,"idCard":"123456789012345","img":null,"name":"大王","password":null,"phoneNumber":"13513131313","regTime":"2021-08-12","remark":null,"sex":"1","weight":"60"},"flag":true,"message":"获取会员统计数据成功"}
```
## /health-API/health-user-API
```text
用户模块接口
```
## /health-API/health-user-API/新增用户
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/user/add.do?roleIds=1

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
roleIds | 1 | Text | 是 | -
||||
#### 请求Body参数

```javascript
{"username":"stuff","station":"1","gender":"1"}
```
#### 成功响应示例
```javascript
{"data":null,"flag":true,"message":"添加用户成功"}
```
## /health-API/health-user-API/编辑用户
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/user/edit.do?roleIds=1

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
roleIds | 1 | Text | 是 | -
||||
#### 请求Body参数

```javascript
{"birthday":null,"gender":"2","id":9,"password":"$2a$10$zPKyM9LZ5yDmJ6xfh4eGzuaNYwxO2JDvrcbw6buzUhHQj81ZoK/Dy","remark":null,"roles":[],"station":"1","telephone":null,"username":"stuff2"}
```
#### 成功响应示例
```javascript
{"data":null,"flag":true,"message":"用户信息编辑成功"}
```
## /health-API/health-user-API/删除用户
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/user/delete.do?id=10

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 10 | Text | 是 | -
||||
#### 成功响应示例
```javascript
{"data":null,"flag":true,"message":"用户信息删除成功"}
```
## /health-API/health-user-API/查询用户关联角色
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/permission/findRoleByUser.do?id=1

#### 请求方式
> GET

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1 | Text | 是 | -
||||
#### 成功响应示例
```javascript
{"data":[1,2],"flag":true,"message":"用户关联角色查询成功"}
```
## /health-API/health-user-API/条件查询-用户列表
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/user/findPage.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"currentPage":1,"pageSize":10,"queryString":"admin"}
```
#### 成功响应示例
```javascript
{"rows":[{"birthday":null,"gender":"1","id":1,"password":"$2a$10$LpTRMAQbGxkjapnGEzoayehFN05WHCoFo4Prp0NK8V7OM/EGXHpCy","remark":null,"roles":[],"station":"1","telephone":null,"username":"admin"}],"total":1}
```
## /health-API/health-user-API/分页查询-用户列表
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/user/findPage.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"currentPage":1,"pageSize":10,"queryString":null}
```
#### 成功响应示例
```javascript
{"rows":[{"birthday":null,"gender":"1","id":1,"password":"$2a$10$LpTRMAQbGxkjapnGEzoayehFN05WHCoFo4Prp0NK8V7OM/EGXHpCy","remark":null,"roles":[],"station":"1","telephone":null,"username":"admin"},{"birthday":null,"gender":"1","id":5,"password":"$2a$10$.gE1xWUdPW/gtQbT.8UYm..0232pEO0DdA3rolLfjFELzJaJ4KsAi","remark":null,"roles":[],"station":"1","telephone":null,"username":"test"},{"birthday":null,"gender":"2","id":7,"password":"$2a$10$sTE5LpWa.M/Z0RmZ561KsOBImUMUQHtQBM.CJa1VKb3dtZ6VqiAs2","remark":null,"roles":[],"station":"1","telephone":null,"username":"doctor"},{"birthday":null,"gender":"1","id":8,"password":"$2a$10$hFxFEh/FruQayWZp2xApTu3T8kwmA.3X4A4CmMjHFAQcgcS9/AZ9e","remark":null,"roles":[],"station":"1","telephone":null,"username":"stuff"}],"total":4}
```
## /health-API/health-user-API/查询用户名称
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/user/getUsername.do

#### 请求方式
> GET

#### Content-Type
> application/json;charset=utf-8

## /health-API/health-checkitem-API
```text
检查项模块接口
```
## /health-API/health-checkitem-API/分页查询-检查项
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/checkitem/findPage.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"currentPage":1,"pageSize":10,"queryString":null}
```
#### 成功响应示例
```javascript
{"rows":[{"age":"0-100","attention":"无","code":"0003","id":30,"name":"体重指数","price":10.0,"remark":"体重指数","sex":"0","type":"1"},{"age":"0-100","attention":"无","code":"0004","id":31,"name":"收缩压","price":5.0,"remark":"收缩压","sex":"0","type":"1"},{"age":"0-100","attention":"无","code":"0005","id":32,"name":"舒张压","price":5.0,"remark":"舒张压","sex":"0","type":"1"},{"age":"0-100","attention":"无","code":"0006","id":33,"name":"裸视力（右）","price":5.0,"remark":"裸视力（右）","sex":"0","type":"1"},{"age":"0-100","attention":"无","code":"0007","id":34,"name":"裸视力（左）","price":5.0,"remark":"裸视力（左）","sex":"0","type":"1"},{"age":"0-100","attention":"无","code":"0008","id":35,"name":"矫正视力（右）","price":5.0,"remark":"矫正视力（右）","sex":"0","type":"1"},{"age":"0-100","attention":"无","code":"0009","id":36,"name":"矫正视力（左）","price":5.0,"remark":"矫正视力（左）","sex":"0","type":"1"},{"age":"0-100","attention":"无","code":"0010","id":37,"name":"色觉","price":5.0,"remark":"色觉","sex":"0","type":"1"},{"age":"0-100","attention":"无","code":"0011","id":38,"name":"白细胞计数","price":10.0,"remark":"白细胞计数","sex":"0","type":"2"},{"age":"0-100","attention":null,"code":"0012","id":39,"name":"红细胞计数","price":10.0,"remark":"红细胞计数","sex":"0","type":"2"}],"total":62}
```
## /health-API/health-checkitem-API/条件查询-检查项
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/checkitem/findPage.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"currentPage":1,"pageSize":10,"queryString":"0003"}
```
#### 成功响应示例
```javascript
{"rows":[{"age":"0-100","attention":"无","code":"0003","id":30,"name":"体重指数","price":10.0,"remark":"体重指数","sex":"0","type":"1"}],"total":1}
```
## /health-API/health-checkitem-API/删除检查项
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/checkitem/delete.do?id=93

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 93 | Text | 是 | -
||||
#### 成功响应示例
```javascript
{"data":null,"flag":true,"message":"删除检查项成功"}
```
## /health-API/health-checkitem-API/新增检查项
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/checkitem/add.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"code":"0001","name":"测试项"}
```
#### 成功响应示例
```javascript
{"data":null,"flag":true,"message":"新增检查项成功"}
```
## /health-API/health-checkitem-API/编辑检查项
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/checkitem/edit.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"age":null,"attention":null,"code":"0001","id":94,"name":"测试项","price":null,"remark":null,"sex":"0","type":null}
```
#### 成功响应示例
```javascript
{"data":null,"flag":true,"message":"编辑检查项成功"}
```
## /health-API/health-checkgroup-API
```text
检查组模块接口
```
## /health-API/health-checkgroup-API/分页查询-检查组
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/checkgroup/findPage.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"currentPage":1,"pageSize":10,"queryString":null}
```
#### 成功响应示例
```javascript
{"rows":[{"attention":"","checkItems":null,"code":"0001","helpCode":"YBJC","id":5,"name":"一般检查","remark":"一般检查","sex":"0"},{"attention":null,"checkItems":null,"code":"0002","helpCode":"SLSJ","id":6,"name":"视力色觉","remark":"视力色觉","sex":"0"},{"attention":null,"checkItems":null,"code":"0003","helpCode":"XCG","id":7,"name":"血常规","remark":"血常规1","sex":"0"},{"attention":null,"checkItems":null,"code":"0004","helpCode":"NCG","id":8,"name":"尿常规","remark":"尿常规","sex":"0"},{"attention":null,"checkItems":null,"code":"0005","helpCode":"GGSX","id":9,"name":"肝功三项","remark":"肝功三项","sex":"0"},{"attention":null,"checkItems":null,"code":"0006","helpCode":"NGSX","id":10,"name":"肾功三项","remark":"肾功三项","sex":"0"},{"attention":null,"checkItems":null,"code":"0007","helpCode":"XZSX","id":11,"name":"血脂四项","remark":"血脂四项","sex":"0"},{"attention":null,"checkItems":null,"code":"0008","helpCode":"XJMSX","id":12,"name":"心肌酶三项","remark":"心肌酶三项","sex":"0"},{"attention":null,"checkItems":null,"code":"0009","helpCode":"JGSX","id":13,"name":"甲功三项","remark":"甲功三项","sex":"0"},{"attention":null,"checkItems":null,"code":"0010","helpCode":"ZGFJCC","id":14,"name":"子宫附件彩超","remark":"子宫附件彩超","sex":"2"}],"total":11}
```
## /health-API/health-checkgroup-API/条件查询-检查组
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/checkgroup/findPage.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"currentPage":1,"pageSize":10,"queryString":"0001"}
```
#### 成功响应示例
```javascript
{
	"rows": [
		{
			"attention": "",
			"checkItems": null,
			"code": "0001",
			"helpCode": "YBJC",
			"id": 5,
			"name": "一般检查",
			"remark": "一般检查",
			"sex": "0"
		}
	],
	"total": 1
}
```
## /health-API/health-checkgroup-API/新增检查组
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/checkgroup/add.do?checkitemIds=30,31

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
checkitemIds | 30,31 | Text | 是 | -
||||
#### 请求Body参数

```javascript
{"code":"00002","name":"测试组"}
```
#### 成功响应示例
```javascript
{
	"data": null,
	"flag": true,
	"message": "新增检查组成功"
}
```
## /health-API/health-checkgroup-API/删除检查组
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/checkgroup/delete.do?id=17

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 17 | Text | 是 | -
||||
#### 成功响应示例
```javascript
{
	"data": null,
	"flag": true,
	"message": "删除检查组成功"
}
```
## /health-API/health-checkgroup-API/编辑检查组
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/checkgroup/edit.do?checkitemIds=30,31,32

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
checkitemIds | 30,31,32 | Text | 是 | -
||||
#### 请求Body参数

```javascript
{"attention":null,"checkItems":null,"code":"00003","helpCode":"000003","id":18,"name":"测试组","remark":null,"sex":"1"}
```
#### 成功响应示例
```javascript
{
	"data": null,
	"flag": true,
	"message": "编辑检查组成功"
}
```
## /health-API/health-checkgroup-API/查询检查组关联检查项
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/checkgroup/findCheckItemIdsByCheckGroupId.do?id=18

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 18 | Text | 是 | -
||||
#### 成功响应示例
```javascript
{
	"data": [
		30,
		31,
		32
	],
	"flag": true,
	"message": "查询检查项成功"
}
```
## /health-API/health-setmeal-API
```text
套餐模块接口
```
## /health-API/health-setmeal-API/分页查询-套餐
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/setmeal/findPage.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"currentPage":1,"pageSize":10,"queryString":null}
```
#### 成功响应示例
```javascript
{
	"rows": [
		{
			"age": "18-60",
			"attention": "无",
			"checkGroups": null,
			"code": "001",
			"helpCode": "RZJC",
			"id": 1,
			"img": "125d4908-cdea-47de-b057-d36491f1e9b21.jpg",
			"name": "入职检查套餐",
			"price": 300,
			"remark": "入职检查",
			"sex": "0"
		},
		{
			"age": "20-60",
			"attention": null,
			"checkGroups": null,
			"code": "002",
			"helpCode": "YCJC",
			"id": 3,
			"img": "dc897887-5f1f-4383-867b-6fd33cdf7ae42.jpg",
			"name": "孕前检查",
			"price": 500,
			"remark": "孕前检查",
			"sex": "2"
		},
		{
			"age": "0-100",
			"attention": "无",
			"checkGroups": null,
			"code": "003",
			"helpCode": "test01",
			"id": 4,
			"img": "5eefa28a-a9ae-4a10-bc42-925a38232e55c.jpeg",
			"name": "测试套餐",
			"price": 120,
			"remark": "测试套餐",
			"sex": "0"
		}
	],
	"total": 3
}
```
## /health-API/health-setmeal-API/条件查询
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/setmeal/findPage.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"currentPage":1,"pageSize":10,"queryString":"001"}
```
## /health-API/health-setmeal-API/新增套餐
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/setmeal/add.do?checkgroupIds=5

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
checkgroupIds | 5 | Text | 是 | -
||||
#### 请求Body参数

```javascript
{"code":"005","name":"test1","helpCode":"test"}
```
#### 成功响应示例
```javascript
{
	"data": null,
	"flag": true,
	"message": "新增套餐成功"
}
```
## /health-API/health-setmeal-API/编辑套餐
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/setmeal/edit.do?checkgroupIds=5,6,7,8

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
checkgroupIds | 5,6,7,8 | Text | 是 | -
||||
#### 请求Body参数

```javascript
{"age":null,"attention":null,"checkGroups":[{"attention":"","checkItems":[{"age":"0-100","attention":"无","code":"0003","id":30,"name":"体重指数","price":10,"remark":"体重指数","sex":"0","type":"1"},{"age":"0-100","attention":"无","code":"0004","id":31,"name":"收缩压","price":5,"remark":"收缩压","sex":"0","type":"1"},{"age":"0-100","attention":"无","code":"0005","id":32,"name":"舒张压","price":5,"remark":"舒张压","sex":"0","type":"1"}],"code":"0001","helpCode":"YBJC","id":5,"name":"一般检查","remark":"一般检查","sex":"0"}],"code":"004","helpCode":"test01","id":5,"img":null,"name":"test","price":null,"remark":null,"sex":null}
```
#### 成功响应示例
```javascript
{
	"data": null,
	"flag": true,
	"message": "编辑套餐成功"
}
```
## /health-API/health-setmeal-API/删除套餐
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/setmeal/delete.do?id=6

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 6 | Text | 是 | -
||||
#### 成功响应示例
```javascript
{
	"data": null,
	"flag": true,
	"message": "删除套餐成功"
}
```
## /health-API/health-setmeal-API/查询套餐关联检查组号
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/setmeal/findCheckGroupIdsById.do?id=4

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 4 | Text | 是 | -
||||
#### 成功响应示例
```javascript
{
	"data": [
		5,
		6,
		7,
		8
	],
	"flag": true,
	"message": "查询检查组成功"
}
```
## /health-API/health-permission-API
```text
权限模块API
```
## /health-API/health-permission-API/分页查询-权限
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/permission/findPage.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"currentPage":1,"pageSize":10,"queryString":null}
```
#### 成功响应示例
```javascript
{
	"rows": [
		{
			"description": "新增检查项",
			"id": 1,
			"keyword": "CHECKITEM_ADD",
			"name": "新增检查项",
			"roles": []
		},
		{
			"description": "删除检查项",
			"id": 2,
			"keyword": "CHECKITEM_DELETE",
			"name": "删除检查项",
			"roles": []
		},
		{
			"description": "编辑检查项",
			"id": 3,
			"keyword": "CHECKITEM_EDIT",
			"name": "编辑检查项",
			"roles": []
		},
		{
			"description": null,
			"id": 4,
			"keyword": "CHECKITEM_QUERY",
			"name": "查询检查项",
			"roles": []
		},
		{
			"description": null,
			"id": 5,
			"keyword": "CHECKGROUP_ADD",
			"name": "新增检查组",
			"roles": []
		},
		{
			"description": null,
			"id": 6,
			"keyword": "CHECKGROUP_DELETE",
			"name": "删除检查组",
			"roles": []
		},
		{
			"description": null,
			"id": 7,
			"keyword": "CHECKGROUP_EDIT",
			"name": "编辑检查组",
			"roles": []
		},
		{
			"description": null,
			"id": 8,
			"keyword": "CHECKGROUP_QUERY",
			"name": "查询检查组",
			"roles": []
		},
		{
			"description": null,
			"id": 9,
			"keyword": "SETMEAL_ADD",
			"name": "新增套餐",
			"roles": []
		},
		{
			"description": null,
			"id": 10,
			"keyword": "SETMEAL_DELETE",
			"name": "删除套餐",
			"roles": []
		}
	],
	"total": 36
}
```
## /health-API/health-permission-API/新增权限
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/permission/add.do?roleIds=1,2

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
roleIds | 1,2 | Text | 是 | -
||||
#### 请求Body参数

```javascript
{"name":"测试","keyword":"test"}
```
#### 成功响应示例
```javascript
{
	"data": null,
	"flag": true,
	"message": "添加权限成功"
}
```
## /health-API/health-permission-API/删除权限
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/permission/delete.do?id=38

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 38 | Text | 是 | -
||||
#### 成功响应示例
```javascript
{
	"data": null,
	"flag": true,
	"message": "权限信息删除成功"
}
```
## /health-API/health-permission-API/编辑权限
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/permission/edit.do?roleIds=1,2

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
roleIds | 1,2 | Text | 是 | -
||||
#### 请求Body参数

```javascript
{"description":null,"id":37,"keyword":"PERMISSION_DELETE","name":"删除权限","roles":[]}
```
#### 成功响应示例
```javascript
{
	"data": null,
	"flag": true,
	"message": "权限信息编辑成功"
}
```
## /health-API/health-order-API
```text
订单模块
```
## /health-API/health-order-API/根据月份查询可预约量
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/ordersetting/getOrderSettingByMonth.do?date=2021-9

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
date | 2021-9 | Text | 是 | -
||||
#### 成功响应示例
```javascript
{
	"data": [
		{
			"date": 1,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 2,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 3,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 4,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 5,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 6,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 7,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 8,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 9,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 10,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 11,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 12,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 13,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 14,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 15,
			"number": 50,
			"reservations": 1
		},
		{
			"date": 16,
			"number": 300,
			"reservations": 1
		},
		{
			"date": 17,
			"number": 50,
			"reservations": 0
		},
		{
			"date": 18,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 19,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 20,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 21,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 22,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 23,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 24,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 25,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 26,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 27,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 28,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 29,
			"number": 300,
			"reservations": 0
		},
		{
			"date": 30,
			"number": 300,
			"reservations": 0
		}
	],
	"flag": true,
	"message": "获取预约设置数据成功"
}
```
## /health-API/health-order-API/按日设置可预约量
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/ordersetting/editNumberByDate.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"number":"100","orderDate":"2021-09-19"}
```
#### 成功响应示例
```javascript
{
	"data": null,
	"flag": true,
	"message": "预约设置成功"
}
```
## /health-API/health-order-API/分页查询-预约订单
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/order/findPage.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"currentPage":1,"pageSize":10,"queryString":null}
```
#### 成功响应示例
```javascript
{
	"rows": [
		{
			"memberName": "大王",
			"orderDate": "2021-08-12",
			"orderId": 1,
			"orderStatus": "未到诊",
			"orderType": "微信预约",
			"phoneNumber": "13513131313",
			"setmealName": "入职检查套餐"
		},
		{
			"memberName": "小红",
			"orderDate": "2021-08-12",
			"orderId": 2,
			"orderStatus": "未到诊",
			"orderType": "微信预约",
			"phoneNumber": "13713111111",
			"setmealName": "孕前检查"
		},
		{
			"memberName": "小蓝",
			"orderDate": "2021-08-11",
			"orderId": 3,
			"orderStatus": "未到诊",
			"orderType": "微信预约",
			"phoneNumber": "13713112211",
			"setmealName": "孕前检查"
		},
		{
			"memberName": "小绿",
			"orderDate": "2021-08-14",
			"orderId": 7,
			"orderStatus": "已到诊",
			"orderType": "电话预约",
			"phoneNumber": "13753535353",
			"setmealName": "入职检查套餐"
		},
		{
			"memberName": "小蓝",
			"orderDate": "2021-08-10",
			"orderId": 8,
			"orderStatus": "已到诊",
			"orderType": "微信预约",
			"phoneNumber": "13713112211",
			"setmealName": "孕前检查"
		},
		{
			"memberName": "测试会员3",
			"orderDate": "2021-10-14",
			"orderId": 9,
			"orderStatus": "未到诊",
			"orderType": "微信预约",
			"phoneNumber": "13511112222",
			"setmealName": "入职检查套餐"
		},
		{
			"memberName": "测试会员3",
			"orderDate": "2021-09-15",
			"orderId": 10,
			"orderStatus": "未到诊",
			"orderType": "微信预约",
			"phoneNumber": "13511112222",
			"setmealName": "入职检查套餐"
		},
		{
			"memberName": "测试会员3",
			"orderDate": "2021-09-16",
			"orderId": 11,
			"orderStatus": "未到诊",
			"orderType": "微信预约",
			"phoneNumber": "13511112222",
			"setmealName": "测试套餐"
		}
	],
	"total": 8
}
```
## /health-API/health-order-API/新增订单
```text
暂无描述
```
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/order/add.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"phoneNumber":"13511112222","memberName":"测试会员3","orderDate":"2021-09-19","orderType":"电话预约","orderStatus":"未到诊","setmealName":"入职体检套餐"}
```
#### 成功响应示例
```javascript
{
	"data": null,
	"flag": true,
	"message": "新增预约信息成功"
}
```
## /health-API/health-order-API/编辑预约订单
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/order/edit.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 请求Body参数
```javascript
{"memberName":"测试会员3","orderDate":"2021-09-16","orderId":11,"orderStatus":"已到诊","orderType":"微信预约","phoneNumber":"13511112222","setmealName":"测试套餐"}
```
#### 成功响应示例
```javascript
{
	"data": null,
	"flag": true,
	"message": "编辑预约信息成功"
}
```
## /health-API/health-report-API
```text
报表模块API
```
## /health-API/health-report-API/查询套餐数据
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/report/getSetmealReport.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 成功响应示例
```javascript
{
	"data": {
		"setmealNames": [
			"入职检查套餐",
			"孕前检查",
			"测试套餐"
		],
		"setmealCount": [
			{
				"name": "入职检查套餐",
				"value": 4
			},
			{
				"name": "孕前检查",
				"value": 3
			},
			{
				"name": "测试套餐",
				"value": 1
			}
		]
	},
	"flag": true,
	"message": "获取套餐统计数据成功"
}
```
## /health-API/health-report-API/查询平台运营数据
```text
暂无描述
```
#### 接口状态
> 开发中

#### 接口URL
> http://47.103.4.117:8082/report/getBusinessReportData.do

#### 请求方式
> POST

#### Content-Type
> application/json;charset=utf-8

#### 成功响应示例
```javascript
{
	"data": {
		"todayVisitsNumber": 0,
		"reportDate": "2021-09-17",
		"todayNewMember": 0,
		"thisWeekVisitsNumber": 0,
		"hotSetmeal": [
			{
				"proportion": 0.4,
				"name": "入职检查套餐",
				"setmeal_count": 4
			},
			{
				"proportion": 0.3,
				"name": "孕前检查",
				"setmeal_count": 3
			},
			{
				"proportion": 0.1,
				"name": "测试套餐",
				"setmeal_count": 1
			}
		],
		"thisMonthNewMember": 1,
		"thisWeekNewMember": 1,
		"totalMember": 11,
		"thisMonthOrderNumber": 5,
		"thisMonthVisitsNumber": 0,
		"todayOrderNumber": 0,
		"thisWeekOrderNumber": 5
	},
	"flag": true,
	"message": "获取运营统计数据成功"
}
```