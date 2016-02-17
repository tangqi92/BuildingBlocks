# Building Blocks

[中文介绍](README.z.md)

![logo](http://7xk54v.com1.z0.glb.clouddn.com/images/logo/icon.png)

> **Building Blocks** with Zhihu Daily API as a source of data; OptionsMenu as extensions of the entry; Design Support Library as UI design leader.I know, it sounds cool！

### How to use

> dev branch is currently maintained by [troyliu0105](https://github.com/troyliu0105), he will continue to reconstruct the building blocks and increase the fun of the function, if you also like, then welcome **Star** and **Fork** this branch!

Well, actually, I have to help you complete the overall framework set up, you need to do is just based on actual demand, simply replace data at source, and modify UI, you can quickly complete a new application development！

Think about it, it is not very exciting? So hurry **Star** and **Fork** it! Your support will be my greatest motivation and praise!

## Update Log

### 0.7.0

- New - night mode
- New - crash log collect
- New - auto update
- New - `html+`mode（actually we just modified the html）
- FIX - auto clear bug
- FIX - MainActivity flicker bug
- Modify - parts of UI

##### Known Bugs

- **Location** in Android 6.0 will crash


- problems with showing some article (`html+` mode isn't influenced)

### v0.6.0

- New - use FAB to refresh
- New - WebView cache at `html` mode
- New - json mode(faster, but has some view bugs)
- New - auto clear out of date cache
- New - use SQLite to store data
- Reconstruction - Refactoring with MVP
- New - Logo Support by [Mao](http://weibo.com/cat93/)
- Modify - parts of UI

### v0.5.1

- Abandon - CardView, return to simple, more in line with the design specifications
- Abandon - third party DrawerLayout, flashy guy
- Abandon - WebView article title bar at the top, instead of sharing buttons
- New - Search function, display the search results page jump
- New - Right slide gestures, return to the previous page function (Bug v0.5.0 appearing been fixed)
- Optimization - Extended capabilities as a drawer sub-menu options
- Reconstruction -  As far as possible, the code is written and structured elegance

## Demo

[Download](http://7xk54v.com1.z0.glb.clouddn.com/app/bb/0.7.0.apk)

## Screenshots

<img src="/screenshots/s1.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/s1-1.png" alt="screenshot" title="screenshot" width="270" height="486" />

<img src="/screenshots/s1-2.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/s2.png" alt="screenshot" title="screenshot" width="270" height="486" />

<img src="/screenshots/s3.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/s4.png" alt="screenshot" title="screenshot" width="270" height="486" />  

<img src="/screenshots/s5.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/s6.png" alt="screenshot" title="screenshot" width="270" height="486" />  

<img src="/screenshots/s7.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/s8.png" alt="screenshot" title="screenshot" width="270" height="486" />

<img src="/screenshots/s9.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/s9-1.png" alt="screenshot" title="screenshot" width="270" height="486" /> 

## Dependencies

- [glide](https://github.com/bumptech/glide)
- [butterknife](https://github.com/JakeWharton/butterknife)
- [android-crop](https://github.com/jdamcd/android-crop)
- [android-async-http](https://github.com/loopj/android-async-http)
- [SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)
- [Jsoup](http://http://jsoup.org/)

## Thanks

- Thank [drakeet](https://github.com/drakeet) and his [妹纸&gank.io](https://github.com/drakeet/Meizhi), the code he wrote is very beautiful：）, I learned a lot and applied to the project.
- Thank [Izzy Leung](https://github.com/izzyleung) and his [知乎日报·净化](https://github.com/izzyleung/ZhihuDailyPurify)，the initial prototype project will come from this.

## Contributors

- dev version: [troyliu0105](https://github.com/troyliu0105)
- Logo: [Mao](http://weibo.com/cat93/) & [troyliu0105](https://github.com/troyliu0105)

## Contact Me

Born in 1992, now a student of Southeast University, master of software engineerin. Loving technology, programming, reading and sports.

I will graduate in June 2017, expect the internship or full-time job in Android or iOS.

If you have any questions or want to make friends with me, please feel free to contact me : [imtangqi#gmail.com](mailto:imtangqi@gmail.com "Welcome to contact me")

## Project Home

[http://itangqi.me/2015/09/03/building-blocks/](http://itangqi.me/2015/09/03/building-blocks/)

## License

[GPLv3](/LICENSE)