# DBScan
利用freemarker和Java反射等知识实现：实体生成器。

首先，描述下项目的实现过程：

1.获取数据库连接的要素：url、password....

2.打开数据库连接

3.利用DatabaseMetaData获得数据库的表信息（如所有的表名）

4.有了表名，我们再通过表名获取表中的字段

5.数据处理（比如要把数据库中varchar的数据类型转换成String）再装入List

6.把处理好的数据给freemarker进行模板生成Java实体文件



ps:
1.项目编写时不小心删除MANIFEST.MF文件，所以打包后无法运行，暂未修复

2.暂时只支持MySQL的生成，未来有时间可以学习下其他的

####resouces内存放生成模板，默认是生成有get/set/constructors的文件，如需自定义请修改这里，不会请看freemarker文档
