﻿# TreeHelperTest
多级可展开的树状结构
树状就是多了个缩进
将普通的数据转化为Node，设置好Node之间的关系
点击的时候展开或者收缩，设置Node的isExpand状态，重新过滤可见的Node集合，然后notifyDataSetChanged.
排序的id，pid，name等用了注解和反射来从实体获得，这样不管什么样的实体都适应。
![效果图](https://github.com/zrzhong/TreeHelperTest/raw/master/screenshots/sc_tree.png)