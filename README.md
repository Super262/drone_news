# drone_news
The drone_news is both the web server and the administration site for [DroneNews](https://github.com/Super262/DroneNews), which is developed on MySQL and SpringBoot.

## 1. APIs

### 1.1 Login

```
a) register：用户注册（参数：包含用户信息的封装类）
b) login：用户登录（参数：包含用户信息的封装类）
c) logout：用户退出登录（参数：用户ID）
```  

### 1.2 News

```
a) addNews：添加新闻（参数：标题、来源和包含新闻内容的文件）
b) showAllNews：返回所有新闻
c) getHTML：返回新闻内容（参数：新闻ID）
d) hotwords：返回热搜词
```  

### 1.3 Videos
```
a) upload：上传视频（参数：用户ID、标题和视频文件）
b) showMyFollow：返回我关注的人上传的视频（参数：用户ID）
c) showMyLike：返回我收藏的视频（参数：用户ID）
d) hotWords：返回热搜词
e) markAsLike：接收用户的收藏请求（参数：用户ID、视频ID和视频作者的ID）
f) markAsDislike：接收用户的取消收藏请求（参数：用户ID、视频ID和视频作者
的ID）
g) addComment：接收用户的评论（参数：评论内容、父评论的ID和当前用户的
ID）
h) getVideoComments：获取某视频的所有评论（参数：视频ID）
```  

### 1.4 Account
```
a) uploadFace：上传头像（参数：用户ID和头像文件）
b) queryUser：查询用户信息（参数：用户ID）
c) queryPublisher：查询视频发布者的信息（参数：当前用户ID、视频ID和视频
作者的ID）
d) beYourFans：接收关注请求（参数：用户ID,视频作者ID）
e) dontBeYourFans：接收取消关注的请求（参数：用户ID,视频作者ID）
f) reportIssues：举报用户（参数：包括举报内容的封装类）
```

## 2. Database Patterns
![image](https://github.com/Super262/DroneNews/blob/master/screenshots/pic00014.png)  

## 3. User Interfaces

### 3.1 Login

![image](https://github.com/Super262/DroneNews/blob/master/screenshots/pic00028.png)

### 3.2 Home

![image](https://github.com/Super262/DroneNews/blob/master/screenshots/pic00029.png)

### 3.3 Users

![image](https://github.com/Super262/DroneNews/blob/master/screenshots/pic00030.png)

### 3.4 Issues

![image](https://github.com/Super262/DroneNews/blob/master/screenshots/pic00031.png)

### 3.5 Videos

![image](https://github.com/Super262/DroneNews/blob/master/screenshots/pic00032.png)

### 3.6 News

![image](https://github.com/Super262/DroneNews/blob/master/screenshots/pic00033.png)  

![image](https://github.com/Super262/DroneNews/blob/master/screenshots/pic00034.png)
