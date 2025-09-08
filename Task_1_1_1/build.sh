javac -d ./shbuild/classes/ ./src/main/java/sys/pro/*
javadoc -sourcepath ./src/main/java -d ./shbuild/doc/ sys.pro
cd ./shbuild/classes/ || exit
jar -cfe ../build.jar sys/pro/Main ./*
cd ..
java -jar ./build.jar
cd ..