在开发应用时我们会发现其实很多功能是相似的，而如果每开发一个项目都要重新去编写相关的代码的话会很费时间，如果把每个单独的功能都封装成一个组件，每个业务都封装成模块的话就可以有效的提高开发效率，以及代码的复用率。

- 组件：单一的功能组件，如视频组件，支付组件，路由组件，每个组件都能单独抽出来制作SDK
- 模块：独立的业务模块，如直播模块，首页模块，即时通信模块，模块可包含不同的组件。

在搭建新工程时可考虑以下问题。

- 将公用的代码提取出来放作为基础库 Base module
- 将单独的功能封装到 Library module 中
- 多 module 划分业务和基础功能

## 关于聚合和解耦

自打入坑面向对象编程时听到最多的一句话就是高内低耦合，现在搭建工程也可以用到这个思想。

- Android Studio 通过依赖的方式为每个 module 之间提供了沟通和交流的渠道从而形成聚合。
- 当一个模块和非常多的模块交流时，如果有一天我们需要换掉这个模块，移除它和别的模块的依赖关系那么后果将是灾难性的，所以需要通过解耦来找到一个为移除或者替换某个个体的行为付出最少的代价的方案。

##### 聚合和解耦是项目架构的基础，设计模块时应思考以下问题。

- 如何让每个个体产生最大的作用
- 如何让个体间的交流通畅
- 如何让每个个体付出最小的消耗来完成任务，并产生更大的集体利益
- 如何统筹更大的集体利益

## 如何使用依赖

![](https://upload-images.jianshu.io/upload_images/3304008-b75327358c079dfc.gif?imageMogr2/auto-orient/strip)

- Library dependency 可依赖第三方库（本地或者网络的）
- Jar dependency 引入 lib 文件夹中所有 .jar 后缀的文件，还能引用 .aar 后缀的文件。
- Module dependency 库依赖

下面演示建一个基础库并在项目中依赖它

![](https://upload-images.jianshu.io/upload_images/3304008-7549ee2afc909cde.gif?imageMogr2/auto-orient/strip)

## AndroidManifest.xml 文件

- AndroidManifest 是 Android 项目的声明配置文件,用来声明配置四大主键 Activity、Service、BroadcastReceiver、ContentProvicer ，以及自定义的 Application。
- 每个 module 都有一份配合的  AndroidManifest 文件来记载配置信息。
- 最终生成 App 的时候只有一份 AndroidManifest 来指导 App 应该如何配置，这是会将每个 module 中的 AndroidManifest 合并成一个。

![](https://upload-images.jianshu.io/upload_images/3304008-cc86b63e1ccff0de.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## AndroidManifest 属性汇总

编译主 module 时会将这些功能 module 重新编译，而每个 module 可单独编译成一个 aar 文件

![](https://upload-images.jianshu.io/upload_images/3304008-b863109c05d265d5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

使用时直接将编译好的 aar 文件拷贝到新的工程里就可以直接用了。

![](https://upload-images.jianshu.io/upload_images/3304008-97d3c0032b16fd53.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

[Demo地址](https://github.com/Demons96/TestPrograms/tree/master/TestZhuJianHua)
