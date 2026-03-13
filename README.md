### 基于SpringBoot + Vue的商品选购、订货系统、企业采购.

企业采购管理系统、智能采购服务平台

#### 管理员功能模块介绍：
###### 收货地址：管理企业用户的默认或多个收货地址信息。预警配置：设置库存不足、订单异常等自动提醒规则。公告管理：发布采购政策、系统通知或供应商相关公告。商品管理：维护企业可采购的商品目录及详细参数信息。商品折扣：配置批量采购、协议价或限时优惠等折扣策略。商品类型：分类管理商品，如办公用品、IT设备、耗材等。配送物流：跟踪订单发货状态，协调物流与签收流程。订单管理：审核、处理并监控所有企业采购订单全流程。库房出库：记录商品出库时间、数量、领用部门及操作人。库房入库：登记采购到货商品的验收与入库明细数据。库存预警：自动提示库存低于安全阈值的商品信息。库房管理：维护多个仓库信息，包括位置、容量与负责人。库房记录：汇总出入库、调拨、盘点等完整操作日志。用户管理：管理企业账号、联系人及角色权限分配。数据统计：分析采购频次、热门商品、供应商表现等指标。订单年统计：生成年度采购总额、订单量及趋势分析报表。订单月统计：按月汇总订单数量、支出及品类分布情况。

#### 用户（企业）功能模块介绍：
###### 个人信息：维护企业联系人、部门、联系方式等基本资料。收货地址：添加、编辑或选择用于收货的多个配送地址。订单物流：实时查看已下单商品的发货与物流跟踪信息。订单管理：提交、查询或取消本企业的采购订单及状态。商品选购：浏览商品目录，筛选并加入采购清单进行下单。邮件通知：接收订单确认、审批结果、发货提醒等邮件。

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、
前端：Vue、Apex、Antd、Axios

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok

##### 管理员：
收货地址、预警配置、公告管理、商品管理、商品折扣、商品类型、配送物流、订单管理、库房出库、库房入库、库存预警、库房管理、库房记录、用户管理、数据统计、订单年统计、订单月统计

##### 用户（企业）：
个人信息、收货地址、订单物流、订单管理、商品选购、邮件通知


#### 前台启动方式
安装所需文件 yarn install 
运行 yarn run dev

#### 默认后台账户密码
[管理员]
admin
1234qwer

[用户]
fank
1234qwer
#### 项目截图

|  |  |
|---------------------|---------------------|
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724163842940.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724164056131.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724163823376.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724164024329.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724163813056.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724164012814.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724163770938.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724163997617.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724164442822.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724163964368.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724164427269.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724163941639.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724164199384.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724163915206.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724164177340.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724163904112.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724164120157.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724163889722.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724164105989.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724163873611.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724164095248.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724163854188.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1724164074310.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/work/936e9baf53eb9a217af4f89c616dc19.png) |


#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解😭`

> 1.项目纯原创，不做二手贩子 2.一次购买终身有效 3.项目讲解持续到答辩结束 4.非常负责的答辩指导 5.**黑奴价格**

> 项目部署调试不好包退！功能逻辑没讲明白包退！

#### 其它资源

[2025年-答辩顺利通过-客户评价🍜](https://berserker287.github.io/2025/06/18/2025%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2024年-答辩顺利通过-客户评价👻](https://berserker287.github.io/2024/06/06/2024%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2023年-答辩顺利通过-客户评价🐢](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年-答辩通过率100%-客户评价🐣](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)

#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

`网站建设、小程序、H5、APP、各种系统 选题+开题报告+任务书+程序定制+安装调试+项目讲解+论文+答辩PPT`

More info: [悲伤的橘子树](https://berserker287.github.io/)

<p><img align="center" src="https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/%E5%90%88%E4%BD%9C%E7%89%A9%E6%96%99%E6%A0%B7%E5%BC%8F%20(3).png" alt="fankekeke" /></p>
