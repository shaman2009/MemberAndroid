MemberAndroid
=============

Android client of memberapp


依赖于项目

- [JakeWharton/ActionBarSherlock](https://github.com/JakeWharton/ActionBarSherlock)
- [jfeinstein10/SlidingMenu](https://github.com/jfeinstein10/SlidingMenu)
- [qiniu / android-sdk](https://github.com/qiniu/android-sdk/tree/v6.0.0)


引用Jar包
- [square/okhttp](https://github.com/square/okhttp)
- [square/picasso](https://github.com/square/picasso)



How to run this Android Project

1. 导入ActionBarSherlock Library
2. 导入SlidingMenu Library
3. SlidingMenu 依赖 ActionBarSherlock  并且删掉自带的android-support-v4.jar 使用 ActionBarSherlock 中的 ActionBarSherlock
4. Inside the SlidingMenu library, edit the class SlidingFragmentActivity to extend SherlockFragmentActivity (like below). Then clean and rebuild, this method should now be found. 
5. MemberAndroid 依赖 SlidingMenu Library 
