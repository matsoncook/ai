rm -rf bin
mkdir bin
cd src
find -name "*.java" > sources.txt
javac -d ../bin @sources.txt
cd ../swing
find -name "*.java" > sources.txt
javac -d ../bin -classpath ../src @sources.txt
cd ../bin
echo "Main-Class: ai.game.dodgum.swing.BoardSwingApp" >> META-INF/MANIFEST.MF
echo "" >> Manifest.txt
jar cvf ../ai.jar *
cd ..
