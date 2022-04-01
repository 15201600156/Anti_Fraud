# Anti_Fraud
FZ
# 爬虫项目
主要采用的爬虫技术有：Jsoup、htmlunit、webmagic-selenium

## 采集服务 antifraud_collect_service
此服务当中，主要就是接受我们要爬取的网站信息，将信息推送当Kafka当中

## 公共服务 antifraud_common
此服务当中，主要放入的是一些公共的包和工具类

## 爬虫服务 antifraud_reptile_service 
此服务当中，主要做的是：

1、消费kafka消息

2、先进行Jsoup进行静态爬取，爬取成功后放入ES进行存储，失败的情况下放入Kafka消息当中等待Htmlunit的爬取   Jsoup 爬取速度快，

3、htmlunit 是一款开源的java 页面分析工具，读取页面后，可以有效的使用htmlunit分析页面上的内容。项目可以模拟浏览器运行，被誉为java浏览器的开源实现。是一个没有界面的浏览器，运行速度迅速。 

4、如果htmlunit进行无界面爬取，爬取成功后放入ES进行存储，失败的情况下放入Kafka消息当中等待webmagic的爬取 

5、进行webmagic（会开启一个浏览器，模拟浏览器访问，比较消耗资源）爬取，爬取成功后放入ES进行存储。 




## 后续
后续还会持续更新

1、爬取页面后，页面内链接模拟点击，进行数据爬取

2、模拟登陆后，进行数据爬取

3、爬取的页面进行截图

4、爬取的页面进行下载

5、目前是直接通过采集链接后，自己定义xpath进行规则匹配后爬取，后续改为接口形式，传递采集链接和需要采集的内容，更加智能化

## 测试链接
https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E6%B5%8B%E8%AF%95&fenlei=256&oq=kibana%2520%25E6%259F%25A5%25E8%25AF%25A220%25E6%259D%25A1es%25E6%2595%25B0%25E6%258D%25AE&rsv_pq=a81c38870006b06f&rsv_t=2ae49ZQQZzLSMnLtZuIcRHjh1x2A7Rlp5vZgzu9a13xXkT6NGpqE46cgNoM&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_btype=t&inputT=1886&rsv_sug3=44&rsv_sug1=43&rsv_sug7=100&rsv_sug2=0&prefixsug=%25E6%25B5%258B%25E8%25AF%2595&rsp=1&rsv_sug4=1886&rsv_sug=1

可在百度上随意搜索你想要的关键字，将地址调用采集的接口就可以在ES当中看到采集的信息

![百度上随便搜索](https://user-images.githubusercontent.com/35587796/161026038-ccbb1b15-eedd-4d81-b420-595817fb80a7.png)
