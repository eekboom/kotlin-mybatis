Trying out Kotlin with MyBatis DB mapper.

I can't seem to get collection mapping right. The code fails with

`org.apache.ibatis.reflection.ReflectionException: Error instantiating class de.eekboom.kotlin_mybatis.AppUser with invalid types (int,String,String,List) or values (1,Foo Bar,foo@bar.de,ADMIN). Cause: java.lang.IllegalArgumentException: argument type mismatch`

To test: `./gradlew run`
