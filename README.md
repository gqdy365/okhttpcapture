# 简介
基于OKHttp拦截器的实用抓包工具
项目基于这个开源库：https://github.com/DingProg/NetworkCaptureSelf

# 功能改进
- 增加初始化，删除自带的通过ContentProvider初始化Context。因为使用过程中发现有Context为空的问题。
- 修改文件夹命名，之前是url的md5，测试人员反馈看不懂，修改为url的path
- 界面增加按钮，打开列表。Android手机现在都是左右滑动返回，与之前列表打开方式冲突。

# 主要功能
- 自带分类接口
- 抓包数据以时间为纬度,默认存储到手机缓存下 /Android/Data/包名/Cache/capture/ 下
- 支持Http/Https协议的抓包，分类请求方式/请求URL/请求Header/请求体/响应状态/响应Header/响应体
- 支持一键复制对应的状态
- 响应体如果是JSON，支持自动格式化
- 抓包数据，默认缓存一天
