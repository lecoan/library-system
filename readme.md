# LibrarySystem

### 使用说明
* 使用Java编写，运行前请确保计算机上有安装有Java且JDK版本不小于1.8
* bookSpider目录下为在豆瓣上爬取的图书信息，使用前可先将App.java下被注释的代码进行读取， 完成图书库的初始化

### 编译器
IntelliJ IDEA

### 可运行平台
* Linux
* Windows
* Mac OS
### 架构
**MVC + Swing**

### Date Plan
1.  确定功能，完成需求分析 17.3.15
2. 完成概要设计 17.3.25
3. 完成详细设计 17.4.10
4. 桌面版基本完成 17.4.15
5. 安卓版开发完成 17.5.15

### Feature

##### 用户
- 查看所有图书
- 通过名称、ISBN编码、作者、出版社查询书本
- 借阅/预约/归还图书
- 查看用户详细信息
  - 总借书本数
  - 剩余借书本数
  - 借书时间
  - 查看已借图书及归还时间

##### 管理员
- 查看所有图书及详细信息
  - 图书借阅率
  - 图书借阅情况
- 查看/管理日志
- 查看所有用户及用户详细情况
  - 借书权限
  - 已借书本
  - 借书超期情况
- 添加图书/删除图书
- 管理用户（冻结用户）