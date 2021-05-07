# RxOkRetrofit
[![](https://jitpack.io/v/feijeff0486/TvRecyclerView.svg)](https://jitpack.io/#feijeff0486/TvRecyclerView)
大麦盒子网络请求框架，jFrog迁移版

## 修改：
- 使用RxJava2替换RxJava，升级架包
- miniSdkVersion修改为15
- 后续在jitpack.io上发布

## 如何依赖
1. Add it in your root build.gradle at the end of repositories:
```groovy
	allprojects {
		repositories {
			//...
			maven { url 'https://jitpack.io' }
		}
	}
```

2. Add the dependency
```groovy
	dependencies {
         implementation 'com.github.feijeff0486:TvRecyclerView:master-SNAPSHOT'
	}
```
