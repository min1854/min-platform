[TOC]



# min-platform


I didn't feel comfortable with the usual scaffolding, so I built one. It has the same function as ruoyi, achieving 90 percent of ruoyi functions. Advantages: Plug-in middleware integration, the implementation of json object enumeration serialization and reverse sequence, implementation of 'BaseEnum' interface can be realized, the default file service provides db mode; Provide three file upload services; Selected by the configuration file, abstracts the logon related logic of the user, and provides the default implementation filter and interceptor for permission verification.

Built in generator, configured through yml, and is most suitable for mybatis field generation, can automatically map entity classes to enumeration fragments, strict code specification. Tree table and single table function, can be a key to complete the function.

ui is the original page of ruoyi, rewritten and adapted.

Technology stack：
- jdk17
- mysql
- redis
- apiAssert
- lombok
- spring boot 3.0
- mybatis-plus